package sa.weibo.control;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.DefaultTableModel;

import sa.weibo.test.JMSProducer;

public class WeiboLogger implements Observer
{
	private DefaultTableModel tableModel;
	private boolean hasTableModel = false;
	
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
			for (Object object : args)
			{
				logString += (String)object;
			}
			logString = logString.substring(3);
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
