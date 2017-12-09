package sa.weibo.test;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.w3c.dom.css.Counter;

import sa.weibo.PO.User;
import sa.weibo.cache.WeiboCache;
import sa.weibo.control.Weibo;
import sa.weibo.control.WeiboCounter;
import sa.weibo.control.WeiboLogger;
import sa.weibo.dao.WeiboDAO;
import sa.weibo.ui.WeiboClient;

public class Demo
{
	public static void main(String[] args)
	{
		WeiboCache cache = new WeiboCache();
		User minclickUser = new User();
		minclickUser.setClickCount(120);
		minclickUser.setUserid(1234);
		cache.setMinClickUser(minclickUser);
		User result = cache.getMinClickUser();
		System.out.println(result.getUserid()+"click:"+result.getClickCount());
	}
	
}
