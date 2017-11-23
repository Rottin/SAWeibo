package sa.weibo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import sa.weibo.PO.WeiboPO;

public class WeiboDAO
{
	private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=WeiboDB";
	private String user = "sa";
	private String pwd = "123456";
	private Connection connection;
	
	public boolean execute(String sql) throws Exception
	{
		Class.forName(driver);
		try (Connection connection = DriverManager.getConnection(url, user, pwd);
				Statement statement = connection.createStatement())
		{
			return statement.execute(sql);
		}
	}
	
	public WeiboPO getWeibo(int weiboid)
	{
		WeiboPO weiboPO = new WeiboPO();
//		Class.forName(driver);
		try (Connection connection = DriverManager.getConnection(url, user, pwd);
				Statement statement = connection.createStatement())
		{
			String sql = "";			
			sql = "SELECT FROM Weibo WHERE weiboid = " + weiboid;
			
			ResultSet resultSet = statement.executeQuery(sql);
			resultSet.next();
			weiboPO.setWeiboId(weiboid);
			weiboPO.setUserId(resultSet.getInt(1));
			weiboPO.setContent(resultSet.getString(2));
			weiboPO.setReleasedTime(Long.parseLong(resultSet.getString(3)));
			return weiboPO;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public ArrayList<WeiboPO> getAllWeibos()
	{
		ArrayList<WeiboPO> weibos = new ArrayList<WeiboPO>();
//		Class.forName(driver);
		try (Connection connection = DriverManager.getConnection(url, user, pwd);
				Statement statement = connection.createStatement())
		{
			String sql = "";			
			sql = "SELECT * FROM Weibo; ";
			
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
				WeiboPO weiboPO = new WeiboPO();
				weiboPO.setWeiboId(resultSet.getInt(1));
				weiboPO.setUserId(resultSet.getInt(2));
				weiboPO.setContent(resultSet.getString(3));
				weiboPO.setReleasedTime(Long.parseLong(resultSet.getString(4).trim()));
				weibos.add(weiboPO);
			}
			
			return weibos;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public boolean addWeibo(int userid, String content) throws Exception
	{
//		Class.forName(driver);
		try (Connection connection = DriverManager.getConnection(url, user, pwd);
				Statement statement = connection.createStatement())
		{
			String sql = "";
			System.out.println("添加微博时间:"+System.currentTimeMillis());
			
			sql = "INSERT INTO Weibo(userid, wcontent, time) "
					+ "VALUES(" + userid + " , '" + content + "','" + System.currentTimeMillis() + "');";
			int i = statement.executeUpdate(sql);
			if (i == 1)
			{
				System.out.println("插入成功");
				return true;
			}
			else {
				System.out.println("插入失败，i为："+i);
				return false;
			}
			
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteWeibo(int weiboid)
	{
		try (Connection connection = DriverManager.getConnection(url, user, pwd);
				Statement statement = connection.createStatement())
		{
			String sql = "";
			System.out.println("删除微博"+weiboid);
			
			sql = "DELETE FROM Weibo WHERE weiboid = " + weiboid;
			int i = statement.executeUpdate(sql);
			if (i == 1)
			{
				System.out.println("删除成功");
				return true;
			}
			else {
				System.out.println("删除失败，i为："+i);
				return false;
			}
			
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean editWeibo(int weiboid, String content)
	{
		try (Connection connection = DriverManager.getConnection(url, user, pwd);
				Statement statement = connection.createStatement())
		{
			String sql = "";
			System.out.println("更新微博"+weiboid);
			
			sql = "UPDATE Weibo SET wcontent = '" + content + "' WHERE weiboid = " + weiboid;
			int i = statement.executeUpdate(sql);
			if (i == 1)
			{
				System.out.println("更新成功");
				return true;
			}
			else {
				System.out.println("更新失败，i为："+i);
				return false;
			}
			
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public int getClickCount(int weiboid)
	{
		WeiboPO weiboPO = new WeiboPO();
		int count = 0;
//		Class.forName(driver);
		try (Connection connection = DriverManager.getConnection(url, user, pwd);
				Statement statement = connection.createStatement())
		{
			String sql = "";			
			sql = "SELECT Weibo.clickcount FROM Weibo WHERE weiboid = " + weiboid;
			
			ResultSet resultSet = statement.executeQuery(sql);
			resultSet.next();
			count = resultSet.getInt(1);
			return count;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public void addClickCount(int weiboid){
		try (Connection connection = DriverManager.getConnection(url, user, pwd);
				Statement statement = connection.createStatement())
		{
			String sql = "";			
			sql = "UPDATE Weibo SET clickcount = clickcount+1 WHERE weiboid = " + weiboid;
			
			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
