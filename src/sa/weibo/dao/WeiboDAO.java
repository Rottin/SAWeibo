package sa.weibo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.PreparedStatement;

//import com.mysql.jdbc.PreparedStatement;

import sa.weibo.PO.WeiboPO;

public class WeiboDAO
{	
    // 驱动引擎
    private static String jd = "com.mysql.jdbc.Driver";
    // 连接MySQL的连接
    private static String url = "jdbc:mysql://139.196.139.218:3306/Weibodb";
    // MySQL的用户名
    private static String user = "root";
    // MySQL的密码
    private static String password = "Openninja@163.com";
   
	
    private static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(jd); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
	
	public WeiboPO getWeibo(int weiboid)
	{
		WeiboPO weiboPO = new WeiboPO();
//		Class.forName(driver);
		try (Connection connection = getConn();)
		{
			String sql = "";
			sql = "select * from weibo where weiboid = " + weiboid;
			PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			weiboPO.setWeiboId(weiboid);
			weiboPO.setUserId(resultSet.getInt(1));
			weiboPO.setContent(resultSet.getString(2));
			weiboPO.setReleasedTime(resultSet.getTimestamp(3));
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
		try (Connection connection = getConn();)
		{
			String sql = "";
			sql = "select * from weibo;";
		    PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				WeiboPO weiboPO = new WeiboPO();
				weiboPO.setWeiboId(resultSet.getInt(1));
				weiboPO.setUserId(resultSet.getInt(2));
				weiboPO.setContent(resultSet.getString(3));
				weiboPO.setReleasedTime(resultSet.getTimestamp(4));
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
		try (Connection connection = getConn();)
		{
		    int i = 0;
		    String sql = "";
		    sql = "insert into weibo (userid,content,time,clickcount) values(?,?,?,?)";
		    PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
		    Timestamp ts = new Timestamp(System.currentTimeMillis());
			//System.out.println("添加微博时间:"+ts);
		    try {
		    	statement.setInt(1, userid);
		    	statement.setString(2, content);
		    	statement.setTimestamp(3, ts);
		    	statement.setInt(4, 0);
		        i = statement.executeUpdate();
		        statement.close();
		        connection.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    if (i == 1)
			{
//				System.out.println("插入成功");
				return true;
			}
			else {
				System.out.println("WeiboDAO:插入失败，i为："+i);
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
//		Class.forName(driver);
		try (Connection connection = getConn();)
		{
			String sql = "";
//			System.out.println("删除微博"+weiboid);
			sql = "delete from weibo where weiboid = " + weiboid;
		    PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
		    int i = statement.executeUpdate();
			if (i == 1)
			{
//				System.out.println("删除成功");
				return true;
			}
			else {
				System.out.println("WeiboDAO:删除失败，i为："+i);
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
		try (Connection connection = getConn();)
		{
			String sql = "";
//			System.out.println("更新微博"+weiboid);
			sql = "update weibo set content = '" + content + "' where weiboid = " + weiboid;
		    PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
		    int i = statement.executeUpdate();
			if (i == 1)
			{
//				System.out.println("更新成功");
				return true;
			}
			else {
				System.out.println("WeiboDAO:更新失败，i为："+i);
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
		int count = 0;
//		Class.forName(driver);
		try (Connection connection = getConn();)
		{
			String sql = "";
			sql = "select weibo.clickcount from weibo where weiboid = " + weiboid;
		    PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
		    ResultSet resultSet = statement.executeQuery();
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
		try (Connection connection = getConn();)
		{
			String sql = "";
			sql = "update weibo set clickcount = clickcount+1 where weiboid = " + weiboid;
		    PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
		    statement.executeUpdate();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<WeiboPO> getWeibosByUserid(int userid){
		ArrayList<WeiboPO> weibos = new ArrayList<WeiboPO>();
		try (Connection connection = getConn();)
		{
			String sql = "";
			sql = "select * from weibo where userid = "+userid;
		    PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				WeiboPO weiboPO = new WeiboPO();
				weiboPO.setWeiboId(resultSet.getInt(1));
				weiboPO.setUserId(resultSet.getInt(2));
				weiboPO.setContent(resultSet.getString(3));
				weiboPO.setReleasedTime(resultSet.getTimestamp(4));
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

}
