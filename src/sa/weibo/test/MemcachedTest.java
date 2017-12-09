package sa.weibo.test;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;

public class MemcachedTest
{
	public void connect()
	{
		try
		{
			// 本地连接 Memcached 服务
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			System.out.println("Connection to server sucessful.");

			// 关闭连接
			mcc.shutdown();

		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	public void setData(String key, int ttl, Object value)
	{
		try
		{
			// 连接本地的 Memcached 服务
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			System.out.println("Connection to server sucessful.");

			// 存储数据
			Future fo = mcc.set(key, ttl, value);

			// 查看存储状态
			System.out.println("set status:" + fo.get());

			// 输出值
			System.out.println("set value in cache - ");

			// 关闭连接
			mcc.shutdown();

		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	public void appendData(String key, Object value)
	{
		try
		{
			// 连接本地的 Memcached 服务
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			System.out.println("Connection to server sucessful.");
			
			// 存储数据
			Future fo = mcc.append(key, value);

			// 查看存储状态
			System.out.println("append status:" + fo.get());

			// 输出值
			System.out.println("append value in cache - ");

			// 关闭连接
			mcc.shutdown();

		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	public Object getData(String key)
	{
		Object result = null;
		try
		{

			// 连接本地的 Memcached 服务
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			System.out.println("Connection to server sucessful.");

//			// 添加数据
//			Future fo = mcc.set("runoob", 900, "Free Education");
//
//			// 输出执行 set 方法后的状态
//			System.out.println("set status:" + fo.get());

			// 使用 get 方法获取数据
			System.out.println("get value in cache - ");

			result = mcc.get(key);
			// 关闭连接
			mcc.shutdown();
			
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return result;
	}

}
