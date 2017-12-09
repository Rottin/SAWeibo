package sa.weibo.test;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.w3c.dom.css.Counter;

import sa.weibo.control.Weibo;
import sa.weibo.control.WeiboCounter;
import sa.weibo.control.WeiboLogger;
import sa.weibo.dao.WeiboDAO;
import sa.weibo.ui.WeiboClient;

public class Demo
{
	public static void main(String[] args)
	{
//		Weibo weibo = new Weibo(1234567);
		MemcachedTest memcachedTest = new MemcachedTest();
		memcachedTest.connect();
		ArrayList<String> values = new ArrayList<>();
		values.add("string11");
		values.add("string12");
//		memcachedTest.setData("test",60*60*24,values);
//		memcachedTest.appendData("test", values);
//		memcachedTest.setData("test",60*60*24,"string2");
		ArrayList<String> results =(ArrayList<String>) memcachedTest.getData("test");
		for(String result:results)
			System.out.println("result:"+result);
	}
	
}
