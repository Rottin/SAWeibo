package sa.weibo.control;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.naming.spi.DirStateFactory.Result;

import sa.weibo.PO.WeiboPO;
import sa.weibo.cache.WeiboCache;
import sa.weibo.dao.UserDAO;
import sa.weibo.dao.WeiboDAO;

public class Weibo extends Observable
{
	private Long userId;
	private WeiboDAO weiboDao;
	private ArrayList<MyObserver> observers;
	private final static String LOGGER = "log";
	private final static String COUNTER = "count";
	private WeiboCache weiboCache;
	private UserDAO userDAO;
	
	public Weibo(Long userId)
	{
		// TODO Auto-generated constructor stub
		weiboDao = new WeiboDAO();
		userDAO = new UserDAO();
		this.userId = userId;
		observers = new ArrayList<MyObserver>();
		weiboCache = new WeiboCache();
	}
	
	public WeiboPO getWeibo(Long weiboid)
	{
		//step1
//		WeiboPO weiboPO = weiboDao.getWeibo(weiboid); 
		//step2
		WeiboPO weiboPO = weiboCache.getWeiboByWeiboid(weiboid);
		ArrayList<Object> arg = new ArrayList<>();
		arg.add(LOGGER);
		arg.add("查看微博:weiboID = "+weiboid);
//		notifyObservers(new Object[]{LOGGER,"查看微博:weiboID = "+weiboid});
		setChanged();
		notifyObservers(arg);
		return weiboPO;
	}
	
	public boolean editWeibo(Long weiboid, String content)
	{
		//TODO 实现修改微博内容
		ArrayList<Object> arg = new ArrayList<>();
		boolean result = weiboDao.editWeibo(weiboid, content);
		
		weiboCache.updateCache(weiboid, content);
		
		arg.add(LOGGER);
		if(result){
			arg.add("编辑微博:weiboID = "+weiboid);
		}
		else if (!result) {
			arg.add("编辑微博（失败）:weiboID = "+weiboid);
		}
		setChanged();
		notifyObservers(arg);
//		notifyObservers(new Object[]{LOGGER,"编辑微博:weiboID = "+weiboid});
		return result;
	}
	
	public boolean addWeibo(String content)
	{
		//TODO 返回新增的weibo的id
		ArrayList<Object> arg = new ArrayList<>();
		boolean result;
//		notifyObservers(new Object[]{LOGGER,"添加微博"});
		try
		{
			result = weiboDao.addWeibo(this.userId, content,0L);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		arg.add(LOGGER);
		if(result){
			arg.add("添加微博");
		}else {
			arg.add("添加微博（失败）");
		}
		
		setChanged();
		notifyObservers(arg);
		return result;
	}
	
	public boolean deleteWeibo(Long weiboid)
	{
		ArrayList<Object> arg = new ArrayList<>();
		boolean result = weiboDao.deleteWeibo(weiboid);
		
		weiboCache.deleteWeiboFromCache(weiboid);
		
		arg.add(LOGGER);
		if(result){
			arg.add("删除微博:weiboID = "+weiboid);
		}else {
			arg.add("删除微博（失败）:weiboID = "+weiboid);
		}
		setChanged();
		notifyObservers(arg);
//		notifyObservers(new Object[]{LOGGER,"删除微博:weiboID = "+weiboid});
		return result;
	}
	
	public ArrayList<WeiboPO> getAllWeibos()
	{
		//TODO 从数据库获取所有微博
		ArrayList<WeiboPO> weiboPOs = weiboDao.getAllWeibos();
//		ArrayList<WeiboPO> weiboPOs = weiboCache.getAllWeiboFromCache();
//		notifyObservers(new Object[]{LOGGER,"获取微博列表"});
		return weiboPOs;
	}
	public ArrayList<WeiboPO> getLast100Weibos(){
		return weiboDao.getLast100Weibos();
	}
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "Weibo:userID:"+this.userId;
	}
	
	public void clickWeibo(Long weiboid)
	{
		weiboDao.addClickCount(weiboid);
//		WeiboPO weiboPO = weiboDao.getWeibo(weiboid);
		WeiboPO weiboPO = weiboCache.getWeiboByWeiboid(weiboid);
		
		userDAO.addClickCount(weiboPO.getUserId());
		
		ArrayList<Object> arg = new ArrayList<>();
		arg.add(COUNTER);
		arg.add(weiboid);
		arg.add(weiboDao.getClickCount(weiboid));
		setChanged();
		notifyObservers(arg);
//		notifyObservers(new Object[]{COUNTER,weiboid,weiboDao.getClickCount(weiboid)});
	}
}
