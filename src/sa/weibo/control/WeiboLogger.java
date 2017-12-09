package sa.weibo.control;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.DefaultTableModel;

import sa.weibo.test.JMSProducer;

public class WeiboLogger implements Observer
{
	private DefaultTableModel tableModel;
	private boolean hasTableModel = false;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	
	public WeiboLogger()
	{
		// TODO Auto-generated constructor stub
	}
	
	public WeiboLogger(DefaultTableModel tableModel)
	{
		// TODO Auto-generated constructor stub
		this.tableModel = tableModel;
		hasTableModel = true;
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
		ArrayList<Object> args = (ArrayList<Object>) arg;
		if (((String)args.get(0)).equals("log"))
		{
			String logString = "";
//			System.out.println("Logger:\n"+observabl.toString());
			Date curDate = new Date(System.currentTimeMillis());
            String timeString = dateFormat.format(curDate);
			for (Object object : args)
			{
				logString += (String)object;
			}
			logString = logString.substring(3);
			logString = timeString + " "+ logString;
			System.out.println(logString);
			if(hasTableModel){
				tableModel.addRow(new Object[]{logString});
			}
			
			//activeMQ
			JMSProducer producer = new JMSProducer();
			producer.send(logString);
		}
	}
}
