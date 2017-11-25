package sa.weibo.control;

import javax.swing.table.DefaultTableModel;

public class WeiboLogger extends MyObserver
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
	public void update(MyObservable observable, Object[] args)
	{
		// TODO Auto-generated method stub
		if (((String)args[0]).equals("log"))
		{
			String logString = "";
			System.out.println("Logger:\n"+observable.toString());
			for (Object object : args)
			{
				logString += (String)object;
			}
			logString = logString.substring(3);
			System.out.println(logString);
			if(hasTableModel){
				tableModel.addRow(new Object[]{logString});
			}
		}
		System.out.println("");
	}
}
