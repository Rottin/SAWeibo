package sa.weibo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.soap.SOAPBinding.Use;

import sa.weibo.PO.User;
import sa.weibo.PO.WeiboPO;

public class UserDAO
{
	// 驱动引擎
    private static String jd = "com.mysql.jdbc.Driver";
    // 连接MySQL的连接
    private static String url = "jdbc:mysql://localhost:8066/Weibo";
    // MySQL的用户名
    private static String user = "root";
    // MySQL的密码
    private static String password = "123456";
    
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
    
    public ArrayList<User> getClickTop100Users(){
    	ArrayList<User> users = new ArrayList<User>();
		try (Connection connection = getConn();)
		{
			String sql = "";
			sql = "select * from user order by count desc limit 100;";
		    PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				User user = new User();
				user.setUserid(resultSet.getLong(1));
				user.setClickCount(resultSet.getLong(3));
				users.add(user);
			}
			return users;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    public void addClickCount(long userid){
    	try (Connection connection = getConn();)
		{
			String sql = "";
			sql = "update user set count = count+1 where id = " + userid;
		    PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
		    statement.executeUpdate();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
