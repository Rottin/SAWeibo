package sa.weibo.control;

import javax.swing.table.DefaultTableModel;

public class WeiboCounter extends Observer
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
	public void update(Observable observable, Object[] args)
	{
		// TODO Auto-generated method stub
		if (((String)args[0]).equals("count"))
		{
//			System.out.println("COUNT UPDATE");
			String countString = "";
			countString  = "点击：WeiboID = " + args[1] + "，此微博总点击次数：" + args[2];
			if(hasTableModel){
				tableModel.addRow(new Object[]{args[1], args[2]});
			}
		}
	}
}
