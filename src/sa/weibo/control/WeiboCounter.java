package sa.weibo.control;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.DefaultTableModel;

import sa.weibo.test.JMSProducer;

public class WeiboCounter implements Observer
{
	private DefaultTableModel tableModel;
	private boolean hasTableModel = false;
	private JMSProducer producer;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	
	public WeiboCounter()
	{
		// TODO Auto-generated constructor stub
	}
	
	public WeiboCounter(DefaultTableModel tableModel)
	{
		// TODO Auto-generated constructor stub
		this.tableModel = tableModel;
		hasTableModel = true;
		producer = new JMSProducer();
	}
	

	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
		ArrayList<Object> args = (ArrayList<Object>) arg;
		if (((String)args.get(0)).equals("count"))
		{
//			System.out.println("COUNT UPDATE");
			String countString = "";
			countString  = "点击：WeiboID = " + args.get(1) + "，此微博总点击次数：" + args.get(2);
			Date curDate = new Date(System.currentTimeMillis());
            String timeString = dateFormat.format(curDate);
            countString = timeString+" " +countString;
			if(hasTableModel){
				tableModel.addRow(new Object[]{args.get(1), args.get(2)});
			}
			
			//activeMQ
			producer.send(countString);
		}
	}
}
