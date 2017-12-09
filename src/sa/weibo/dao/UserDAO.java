package sa.weibo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sa.weibo.PO.User;
import sa.weibo.PO.WeiboPO;

public class UserDAO
{
	// 驱动引擎
    private static String jd = "com.mysql.jdbc.Driver";
    // 连接MySQL的连接
    private static String url = "jdbc:mysql://139.196.139.218:3306/Userdb";
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
    
//    public ArrayList<User> getClickTop100Users(){
//    	ArrayList<User> users = new ArrayList<User>();
//		try (Connection connection = getConn();)
//		{
//			String sql = "";
//			sql = "select * from weibo where userid = "+userid;
//		    PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
//			ResultSet resultSet = statement.executeQuery();
//			while(resultSet.next()){
//				WeiboPO weiboPO = new WeiboPO();
//				weiboPO.setWeiboId(resultSet.getInt(1));
//				weiboPO.setUserId(resultSet.getInt(2));
//				weiboPO.setContent(resultSet.getString(3));
//				weiboPO.setReleasedTime(resultSet.getTimestamp(4));
//				weibos.add(weiboPO);
//			}
//			return users;
//		}
//		catch (SQLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//    }
    
}
