package sa.weibo.control;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.DefaultTableModel;

public class WeiboCounter implements Observer
{
	private DefaultTableModel tableModel;
	private boolean hasTableModel = false;
	
	public WeiboCounter()
	{
		// TODO Auto-generated constructor stub
	}
	
	public WeiboCounter(DefaultTableModel tableModel)
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
		if (((String)args.get(0)).equals("count"))
		{
//			System.out.println("COUNT UPDATE");
			String countString = "";
			countString  = "点击：WeiboID = " + args.get(1) + "，此微博总点击次数：" + args.get(2);
			if(hasTableModel){
				tableModel.addRow(new Object[]{args.get(1), args.get(2)});
			}
		}
	}
}
