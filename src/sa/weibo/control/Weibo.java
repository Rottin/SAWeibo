package sa.weibo.control;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.naming.spi.DirStateFactory.Result;

import sa.weibo.PO.WeiboPO;
import sa.weibo.dao.WeiboDAO;

public class Weibo extends Observable
{
	private int userId;
	private WeiboDAO dao;
	private ArrayList<MyObserver> observers;
	private final static String LOGGER = "log";
	private final static String COUNTER = "count";
	
	public Weibo(int userId)
	{
		// TODO Auto-generated constructor stub
		dao = new WeiboDAO();
		this.userId = userId;
		observers = new ArrayList<MyObserver>();
	}
	
	public WeiboPO getWeibo(int weiboId)
	{
		//TODO 从数据库获取对应的信息
		WeiboPO weiboPO = dao.getWeibo(weiboId);
		ArrayList<Object> arg = new ArrayList<>();
		arg.add(LOGGER);
		arg.add("查看微博:weiboID = "+weiboId);
//		notifyObservers(new Object[]{LOGGER,"查看微博:weiboID = "+weiboId});
		setChanged();
		notifyObservers(arg);
		return weiboPO;
	}
	
	public boolean editWeibo(int weiboId, String content)
	{
		//TODO 实现修改微博内容
		ArrayList<Object> arg = new ArrayList<>();
		boolean result = dao.editWeibo(weiboId, content);
		arg.add(LOGGER);
		if(result){
			arg.add("编辑微博:weiboID = "+weiboId);
		}
		else if (!result) {
			arg.add("编辑微博（失败）:weiboID = "+weiboId);
		}
		setChanged();
		notifyObservers(arg);
//		notifyObservers(new Object[]{LOGGER,"编辑微博:weiboID = "+weiboId});
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
			result = dao.addWeibo(this.userId, content);
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
	
	public boolean deleteWeibo(int weiboId)
	{
		ArrayList<Object> arg = new ArrayList<>();
		boolean result = dao.deleteWeibo(weiboId);
		arg.add(LOGGER);
		if(result){
			arg.add("删除微博:weiboID = "+weiboId);
		}else {
			arg.add("删除微博（失败）:weiboID = "+weiboId);
		}
		setChanged();
		notifyObservers(arg);
//		notifyObservers(new Object[]{LOGGER,"删除微博:weiboID = "+weiboId});
		return result;
	}
	
	public ArrayList<WeiboPO> getAllWeibos()
	{
		//TODO 从数据库获取所有微博
		ArrayList<WeiboPO> weiboPOs = dao.getAllWeibos();
//		notifyObservers(new Object[]{LOGGER,"获取微博列表"});
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
		ArrayList<Object> arg = new ArrayList<>();
		arg.add(COUNTER);
		arg.add(weiboid);
		arg.add(dao.getClickCount(weiboid));
		setChanged();
		notifyObservers(arg);
//		notifyObservers(new Object[]{COUNTER,weiboid,dao.getClickCount(weiboid)});
	}
}
