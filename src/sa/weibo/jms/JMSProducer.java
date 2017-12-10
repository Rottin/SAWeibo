package sa.weibo.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.memory.list.MessageList;
import org.apache.log4j.spi.LoggingEvent;

import com.sun.istack.logging.Logger;

/*
 * 消息生产者
 * */
public class JMSProducer
{
	//默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接地址
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    //发送的消息数量
    private static final int SENDNUM = 10;
    
 // TODO Auto-generated constructor stub
	//连接工厂
    ConnectionFactory connectionFactory;
    //连接
    Connection connection = null;
    //会话 接受或者发送消息的线程
    Session session;
    //消息的目的地
    Destination destination;
    //消息生产者
    MessageProducer messageProducer;
    
    MessageConsumer messageConsumer;
    
    public JMSProducer()
	{
		
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL+"?jms.useAsyncSend=true");
        try{
        	
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
	}
    
    public void sendLog(String logStr){
    	try{
    		connection = connectionFactory.createConnection();
        	connection.start();
        	session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        	destination = session.createQueue("logQueue");
        	messageProducer = session.createProducer(destination);
        	TextMessage message = session.createTextMessage(logStr);
        	messageProducer.send(message);
        	System.out.println("Send log:"+logStr);
        	
        	session.commit();
        	
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
