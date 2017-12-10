package sa.weibo.test;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.dao.support.DaoSupport;
import org.w3c.dom.css.Counter;

import sa.weibo.PO.User;
import sa.weibo.PO.WeiboPO;
import sa.weibo.cache.WeiboCache;
import sa.weibo.control.Weibo;
import sa.weibo.control.WeiboCounter;
import sa.weibo.control.WeiboLogger;
import sa.weibo.dao.UserDAO;
import sa.weibo.dao.WeiboDAO;
import sa.weibo.ui.WeiboClient;

public class Demo
{
	public static void main(String[] args)
	{
//		WeiboCache cache = new WeiboCache();
//		User minclickUser = new User();
//		minclickUser.setClickCount(120);
//		minclickUser.setUserid(1234);
//		cache.setMinClickUser(minclickUser);
//		User result = cache.getMinClickUser();
//		System.out.println(result.getUserid()+"click:"+result.getClickCount());
		
		
		WeiboDAO dao = new WeiboDAO();
//		ArrayList<WeiboPO> weiboPOs = dao.getDateTop100WeibosByUserid(1L);
//		for(WeiboPO weibopo:weiboPOs)
//			System.out.println(weibopo.getWeiboId());
//		
//		UserDAO userDAO = new UserDAO();
//		for( User user :userDAO.getClickTop100Users())
//			System.out.println(user.getUserid());
		
		
//		Random random = new Random();
//	    for(int i=0;i<100;i++)
//	    	System.out.println(random.nextInt(2));
//	    
//	    HashMap< Integer, WeiboPO> map = new HashMap<>();
//		WeiboCache cache = new WeiboCache();
////		cache.cacheClickTop100UserAndWeibo();
//		WeiboPO weiboPO = new WeiboPO();
//		
//		cache.getWeiboByWeiboid(118L);
//		
//	    Weibo weibo = new Weibo(1L);
//	    System.out.println(weibo.getWeibo(118L));
		dao.insert();
		
	}
	
	
	
}
