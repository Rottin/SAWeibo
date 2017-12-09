package sa.weibo.cache;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

import com.google.gson.Gson;

import net.spy.memcached.MemcachedClient;
import sa.weibo.PO.User;

public class WeiboCache
{
	final static String MinClick = "MINCLICK";
	/*
	 * 获取缓存中点击量最小的用户的信息，也就是前100名中的第100名的用户信息
	 * */
	public User getMinClickUser(){
		User user = null;
		try
		{
			// 连接本地的 Memcached 服务
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			System.out.println("Connection to server sucessful.");

			System.out.println("get value in cache - ");
			String result = (String)mcc.get(MinClick);
			Gson gson = new Gson();
			user = gson.fromJson(result,User.class);
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
	 * 设置（更新）缓存中最小点击量的用户
	 * */
	public void setMinClickUser(User user){
		
		setUserData(MinClick, 0, user);
	}
	
	//memcached 设置缓存key-value
	public void setUserData(String key, int ttl, User user)
	{
		try
		{
			// 连接本地的 Memcached 服务
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
//			System.out.println("Connection to server sucessful.");

			Gson gson = new Gson();
			String userJson = gson.toJson(user);
			Future fo = mcc.set(Integer.toString(user.getUserid()), ttl, userJson);

			System.out.println("set status:" + fo.get());
			System.out.println("set value in cache");
			mcc.shutdown();

		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}
