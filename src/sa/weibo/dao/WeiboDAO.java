package sa.weibo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import org.apache.activemq.transport.stomp.Stomp.Headers.Connect;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import java.sql.PreparedStatement;

//import com.mysql.jdbc.PreparedStatement;

import sa.weibo.PO.WeiboPO;


public class WeiboDAO
{	
    // 驱动引擎
    private static String jd = "com.mysql.jdbc.Driver";
    // 连接MySQL的连接
    private static String url_phy0 = "jdbc:mysql://139.196.139.218:3306/Weibodb";
    private static String url_phy1 = "jdbc:mysql://139.196.139.218:3306/Weibodb1";
    private static String url = "jdbc:mysql://localhost:8066/Weibo";
    // MySQL的用户名
    private static String user = "root";
    // MySQL的密码
    private static String password = "123456";
    private static String password_phy = "Openninja@163.com";
    
	
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
    private static Connection getPhyConn0(){
    	Connection conn = null;
        try {
            Class.forName(jd); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url_phy0, user, password_phy);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    private static Connection getPhyConn1(){
    	Connection conn = null;
        try {
            Class.forName(jd); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url_phy1, user, password_phy);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    private static Connection getRandomConn(){
    	Random random = new Random();
	    int randomInt = random.nextInt(2);
	    if(randomInt == 0) return getPhyConn0();
	    else return getPhyConn1();
    }
	
	public WeiboPO getWeibo(Long weiboid)
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
			weiboPO.setUserId(resultSet.getLong(2));
			weiboPO.setContent(resultSet.getString(3));
			weiboPO.setReleasedTime(resultSet.getTimestamp(4));
			weiboPO.setClickCount(resultSet.getLong(5));
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
				weiboPO.setWeiboId(resultSet.getLong(1));
				weiboPO.setUserId(resultSet.getLong(2));
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
	
	public boolean addWeibo(Long userid, String content,Long count) throws Exception
	{
//		Class.forName(driver);
		try (Connection connection = getRandomConn();)
		{
		    int i = 0;
		    String sql = "";
		    sql = "insert into weibo (userid,content,time,count) values(?,?,?,?)";
		    
		    PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
		    Timestamp ts = new Timestamp(System.currentTimeMillis());
			//System.out.println("添加微博时间:"+ts);
		    try {
		    	statement.setLong(1, userid);
		    	statement.setString(2, content);
		    	statement.setTimestamp(3, ts);
		    	statement.setLong(4, count);
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
	
	public boolean deleteWeibo(Long weiboid)
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
	
	public boolean editWeibo(Long weiboid, String content)
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
	
	public int getClickCount(Long weiboid)
	{
		int count = 0;
//		Class.forName(driver);
		try (Connection connection = getConn();)
		{
			String sql = "";
			sql = "select weibo.count from weibo where weiboid = " + weiboid;
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
	
	public void addClickCount(Long weiboid){
		try (Connection connection = getConn();)
		{
			String sql = "";
			sql = "update weibo set count = count+1 where weiboid = " + weiboid;
		    PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
		    statement.executeUpdate();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<WeiboPO> getDateTop100WeibosByUserid(Long userid){
		ArrayList<WeiboPO> weibos = new ArrayList<WeiboPO>();
		try (Connection connection = getConn();)
		{
			String sql = "";
			sql = "select * from weibo where userid = "+userid+" order by time desc limit 100;";
		    PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				WeiboPO weiboPO = new WeiboPO();
				weiboPO.setWeiboId(resultSet.getLong(1));
				weiboPO.setUserId(resultSet.getLong(2));
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
	public ArrayList<WeiboPO> getLast100Weibos(){
		ArrayList<WeiboPO> weibos = new ArrayList<WeiboPO>();
		try (Connection connection = getConn();)
		{
			String sql = "";
			sql = "select * from weibo order by time desc limit 100;";
		    PreparedStatement statement = (PreparedStatement)connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				WeiboPO weiboPO = new WeiboPO();
				weiboPO.setWeiboId(resultSet.getLong(1));
				weiboPO.setUserId(resultSet.getLong(2));
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
	
	public void insert(){
        // sql前缀  
        String prefix = "insert into weibo (userid,content,time,count) values ";  
        Random random = new Random();
        try(Connection conn = getRandomConn();) {  
            StringBuffer suffix = new StringBuffer();  
            // 设置事务为非自动提交  
            conn.setAutoCommit(false);  
            PreparedStatement pst = conn.prepareStatement("");  
            for (int i = 1; i <= 50; i++) {  
            	System.out.println(i);
                for (int j = 1; j <= 10000; j++) {  
                    // 构建sql后缀  
                	int userid = random.nextInt(3)+1;
                	int count = random.nextInt(10000);
                	Timestamp ts = new Timestamp(System.currentTimeMillis());
                    suffix.append("(" + userid + ", \"测试微博"+((i-1)*10000+j)+"\", \'" + ts + "\',"+count+"),"); 
                }  
                // 构建完整sql  
                String sql = prefix + suffix.substring(0, suffix.length() - 1);  
                pst.addBatch(sql);  
                pst.executeBatch();  
                conn.commit();  
                // 清空上一次添加的数据  
                suffix = new StringBuffer();  
            } 
            pst.close();  
            conn.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } 
	}

}
