package sa.weibo.control;

import java.util.ArrayList;
import java.util.logging.Logger;

import sa.weibo.PO.WeiboPO;
import sa.weibo.dao.WeiboDAO;

public class Weibo extends Observable
{
	private int userId;
	private WeiboDAO dao;
	private ArrayList<Observer> observers;
	private final static String LOGGER = "log";
	private final static String COUNTER = "count";
	
	public Weibo(int userId)
	{
		// TODO Auto-generated constructor stub
		dao = new WeiboDAO();
		this.userId = userId;
		observers = new ArrayList<Observer>();
	}
	
	@Override
	public boolean addObserver(Observer observer)
	{
		// TODO Auto-generated method stub
		observers.add(observer);
		return true;
	}
	
	@Override
	public void notifyObservers(Object[] args)
	{
		// TODO Auto-generated method stub
		for (Observer observer : observers)
		{
			observer.update(Weibo.this, args);
		}
	}
	
	public WeiboPO getWeibo(int weiboId)
	{
		//TODO 从数据库获取对应的信息
		WeiboPO weiboPO = dao.getWeibo(weiboId);
		notifyObservers(new Object[]{LOGGER,"查看微博:weiboID = "+weiboId});
		return weiboPO;
	}
	
	public boolean editWeibo(int weiboId, String content)
	{
		//TODO 实现修改微博内容
		notifyObservers(new Object[]{LOGGER,"编辑微博:weiboID = "+weiboId});
		return dao.editWeibo(weiboId, content);
	}
	
	public boolean addWeibo(String content)
	{
		//TODO 返回新增的weibo的id
		notifyObservers(new Object[]{LOGGER,"添加微博"});
		try
		{
			return dao.addWeibo(this.userId, content);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteWeibo(int weiboId)
	{
		notifyObservers(new Object[]{LOGGER,"删除微博:weiboID = "+weiboId});
		return dao.deleteWeibo(weiboId);
	}
	
	public ArrayList<WeiboPO> getAllWeibos()
	{
		//TODO 从数据库获取所有微博
		ArrayList<WeiboPO> weiboPOs = dao.getAllWeibos();
		notifyObservers(new Object[]{LOGGER,"获取微博列表"});
		return weiboPOs;
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "Weibo:userID:"+this.userId;
	}
	
	public void clickWeibo(int weiboid)
	{
		dao.addClickCount(weiboid);
		notifyObservers(new Object[]{COUNTER,weiboid,dao.getClickCount(weiboid)});
	}
}
