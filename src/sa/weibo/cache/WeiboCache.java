package sa.weibo.cache;

import java.net.InetSocketAddress;
import java.nio.charset.MalformedInputException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Future;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.spy.memcached.MemcachedClient;
import sa.weibo.PO.User;
import sa.weibo.PO.WeiboPO;
import sa.weibo.control.Weibo;
import sa.weibo.dao.UserDAO;
import sa.weibo.dao.WeiboDAO;

public class WeiboCache
{
	final static String MinClick = "MINCLICK";
	final static String CACHE = "CACHE";
	private UserDAO userDAO;
	private WeiboDAO weiboDAO;

	public WeiboCache()
	{
		userDAO = new UserDAO();
		weiboDAO = new WeiboDAO();
	}

	// memcached 设置缓存key-value
	public void setCache(String key, int ttl, Object value)
	{
		try
		{
			// 连接本地的 Memcached 服务
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			// System.out.println("Connection to server sucessful.");

			Future fo = mcc.set(key, ttl, value);

			System.out.println("set status:" + fo.get());
			System.out.println("set value in cache");
			mcc.shutdown();

		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	/*
	 * 缓存点击量排名前100的用户的前100条微博
	 */
	public void cacheClickTop100UserAndWeibo()
	{
		ArrayList<User> users = userDAO.getClickTop100Users();
		for (User user : users)
		{
//			System.out.println("Cache:user:"+user.getUserid());
			ArrayList<WeiboPO> weibos = weiboDAO.getDateTop100WeibosByUserid(user.getUserid());
			for (WeiboPO weibo : weibos)
			{
				HashMap<String, Object> map = new HashMap<>();
				map.put("weiboid", weibo.getWeiboId());
				map.put("userid", weibo.getUserId());
				map.put("time", weibo.getReleasedTime());
				map.put("content", weibo.getContent());
				map.put("count", weibo.getClickCount());
				setCache(Long.toString(weibo.getWeiboId()), 0, map);
			}
		}
	}

	/*
	 * 获取缓存
	 * */
//	public HashMap<Long, WeiboPO> getCache(){
	public HashMap<String, Object> getCache(long weiboid){
		HashMap<String, Object> map = new HashMap<>();
		try
		{
			// 连接本地的 Memcached 服务
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			System.out.println("Connection to server sucessful.");

			System.out.println("get value in cache - ");
			map = (HashMap<String, Object>)mcc.get(Long.toString(weiboid));
			
			// 关闭连接
			mcc.shutdown();

		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return map;
	}
	
	/*
	 * 更新缓存
	 * */
	public void updateCache(Long weiboid, String content)
	{
		HashMap<String, Object> map = new HashMap<>();
		try
		{
			// 连接本地的 Memcached 服务
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			System.out.println("update value in cache - ");
			map = (HashMap<String, Object>) mcc.get(Long.toString(weiboid));
			if(map!=null){
				map.put("content", content);
				setCache(Long.toString(weiboid), 0, map);
			}
			System.out.println("UPDATE CACHE:weiboid:"+weiboid+",content:"+content);
			// 关闭连接
			mcc.shutdown();

		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	/*
	 * 如果缓存中有，从缓存中取，否则从数据库取
	 * */
	public WeiboPO getWeiboByWeiboid(long weiboid)
	{
		WeiboPO weiboPO;
		long timeCache1 = System.currentTimeMillis();
		HashMap<String, Object> map = getCache(weiboid);
		if(map!=null)
		{
			weiboPO = new WeiboPO();
			weiboPO.setWeiboId(weiboid);
			weiboPO.setClickCount((Long)map.get("count"));
			weiboPO.setUserId((Long)map.get("userid"));
			weiboPO.setContent((String)map.get("content"));
			weiboPO.setReleasedTime((Timestamp)map.get("time"));
		}
		long timeCache2 = System.currentTimeMillis();
		System.err.println("缓存中获取数据耗时:"+(timeCache2-timeCache1));
		
		long timeDao1 = System.currentTimeMillis();
		weiboPO = weiboDAO.getWeibo(weiboid);
		long timeDao2 = System.currentTimeMillis();
		System.out.println("数据库获取数据耗时:"+(timeDao2-timeDao1));
		
		return weiboPO;
	}
	/*
	 * 删除缓存中的一条微博
	 * */
	public void deleteWeiboFromCache(long weiboid){
		HashMap<String, Object> map = new HashMap<>();
		try
		{
			// 连接本地的 Memcached 服务
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			System.out.println("delete value in cache - ");
			mcc.delete(Long.toString(weiboid));
			System.out.println("DELETE FROM CACHE:weiboid:"+weiboid);
			// 关闭连接
			mcc.shutdown();
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
//	/*
//	 * 获取缓存中的所有微博
//	 * */
//	public ArrayList<WeiboPO> getAllWeiboFromCache(){
//		HashMap<Long, Object> map = getCache();
//		ArrayList<WeiboPO> weiboPOs = new ArrayList<>();
//		if(map != null){
//			Iterator iterator = map.entrySet().iterator();
//			while(iterator.hasNext()){
//				Map.Entry entry = (Entry) iterator.next();
//				weiboPOs.add((WeiboPO) entry.getValue());
//			}
//		}
//		return weiboPOs;
//	}
	
	
	
	
	
	
	/*
	 * 获取缓存中点击量最小的用户的信息，也就是前100名中的第100名的用户信息
	 */
	public User getMinClickUser()
	{
		User user = null;
		try
		{
			// 连接本地的 Memcached 服务
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			System.out.println("Connection to server sucessful.");

			System.out.println("get value in cache - ");
			String result = (String) mcc.get(MinClick);
			Gson gson = new Gson();
			user = gson.fromJson(result, User.class);
			// 关闭连接
			mcc.shutdown();

		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return user;
	}

	/*
	 * 设置缓存中最小点击量的用户
	 */
	public void setMinClickUser(User user)
	{
		Gson gson = new Gson();
		String userJson = gson.toJson(user);
		setCache(MinClick, 0, userJson);
	}

	/*
	 * 删除userid对应用户的缓存
	 */
	public void deleteUserCache(Long userid)
	{
		try
		{
			// 连接本地的 Memcached 服务
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			// System.out.println("Connection to server sucessful.");

			Future fo = mcc.delete(Long.toString(userid));

			System.out.println("delete status:" + fo.get());
			// System.out.println("delete value in cache");
			mcc.shutdown();

		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	/*
	 * 清空所有缓存
	 */
	public void flushCache()
	{

	}

}
