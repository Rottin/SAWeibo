package sa.weibo.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.html.parser.ContentModel;

import sa.weibo.PO.WeiboPO;
import sa.weibo.control.Weibo;
import sa.weibo.control.WeiboCounter;
import sa.weibo.control.WeiboLogger;

public class WeiboClient
{

	private JFrame frame;
	private JTable table;//微博表
	private static JTable table_1;//log表
	private static JTable table_2;//count表
	private JTextArea textArea;
	private static Weibo weibo;
	
	private int currentWeiboID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					WeiboClient window = new WeiboClient();
					window.frame.setVisible(true);
					DefaultTableModel logTableModel = (DefaultTableModel)table_1.getModel();
					DefaultTableModel countTableModel = (DefaultTableModel)table_2.getModel();
					
					WeiboLogger logger = new WeiboLogger(logTableModel);
					weibo.addObserver(logger);
					WeiboCounter counter = new WeiboCounter(countTableModel);
					weibo.addObserver(counter);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WeiboClient()
	{
		weibo = new Weibo(123456);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 918, 610);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblWeibo = new JLabel("Weibo");
		lblWeibo.setBounds(6, 6, 54, 15);
		frame.getContentPane().add(lblWeibo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 30, 421, 463);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"WeiboID", "UserID", "Content", "Time"
			}
		));
		
		table.getColumnModel().getColumn(0).setPreferredWidth(55);
		table.getColumnModel().getColumn(1).setPreferredWidth(61);
		table.getColumnModel().getColumn(2).setPreferredWidth(211);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		scrollPane.setViewportView(table);
		
		//刷新微博界面
		refreshWeibo();
		
		JButton btnAddWeibo = new JButton("Add Weibo");
		btnAddWeibo.setBounds(12, 500, 106, 41);
		frame.getContentPane().add(btnAddWeibo);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(442, 30, 447, 148);
		frame.getContentPane().add(scrollPane_1);
		
		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(444, 217, 449, 171);
		frame.getContentPane().add(scrollPane_2);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Log"
			}
		));
		scrollPane_2.setViewportView(table_1);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(443, 424, 450, 110);
		frame.getContentPane().add(scrollPane_3);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"WeiboID", "Total Clicks"
			}
		));
		table_2.getColumnModel().getColumn(0).setPreferredWidth(136);
		table_2.getColumnModel().getColumn(1).setPreferredWidth(148);
		scrollPane_3.setViewportView(table_2);
		
		JLabel lblWeiboDetail = new JLabel("Weibo Detail");
		lblWeiboDetail.setBounds(444, 6, 105, 15);
		frame.getContentPane().add(lblWeiboDetail);
		
		JLabel lblLog = new JLabel("Log");
		lblLog.setBounds(452, 192, 54, 15);
		frame.getContentPane().add(lblLog);
		
		JLabel lblCount = new JLabel("Count");
		lblCount.setBounds(454, 399, 54, 15);
		frame.getContentPane().add(lblCount);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(130, 500, 106, 41);
		frame.getContentPane().add(btnEdit);
		
		JButton btnDelete_1 = new JButton("Delete");
		btnDelete_1.setBounds(246, 500, 106, 41);
		frame.getContentPane().add(btnDelete_1);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(362, 503, 54, 37);
		frame.getContentPane().add(btnNewButton);
		
		//显示时间的label
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(559, 6, 330, 21);
		frame.getContentPane().add(lblNewLabel);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem);
		
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				// TODO Auto-generated method stub
				if(e.getClickCount()==1){
					DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
					int row = ((JTable)e.getSource()).rowAtPoint(e.getPoint());
					int weiboid = (int)tableModel.getValueAt(row, 0);
					currentWeiboID = weiboid;
					int userid = (int)tableModel.getValueAt(row, 1);
					String content = (String)tableModel.getValueAt(row, 2);
					long time = (long)tableModel.getValueAt(row, 3);
					textArea.setText(content);
					Date date = new Date(time);
					SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
					String timeString = formatter.format(date);
					lblNewLabel.setText(timeString);
					//点击微博
					weibo.clickWeibo(weiboid);
				}
			}
		});
		//edit按钮
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String content = textArea.getText().trim();
				if(content == null || content.equals("")){
					JOptionPane.showMessageDialog(frame, "不能发布空微博");
				}
				else {
					if(currentWeiboID == 0){
						JOptionPane.showMessageDialog(frame, "选择一条微博以修改");
					}
					else {
						weibo.editWeibo(currentWeiboID, content);
						try
						{
							Thread.sleep(1000);
							refreshWeibo();
						}
						catch (InterruptedException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		//删除按钮
		btnDelete_1.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				if(currentWeiboID == 0){
					JOptionPane.showMessageDialog(frame, "选择一条微博以删除");
				}
				else {
					weibo.deleteWeibo(currentWeiboID);
					try
					{
						Thread.sleep(1000);
						refreshWeibo();
					}
					catch (InterruptedException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		//添加微博
		btnAddWeibo.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
//				WeiboEditor editor = new WeiboEditor();
//				editor.getFrame().setVisible(true);
				String content = textArea.getText().trim();
				if(content.equals("")||content == null){
					JOptionPane.showConfirmDialog(frame, "内容不能为空");
				}else {
					weibo.addWeibo(content);
				}
				try
				{
					Thread.sleep(1000);
					refreshWeibo();
				}
				catch (InterruptedException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	private void refreshWeibo(){
		ArrayList<WeiboPO> weiboPOs = weibo.getAllWeibos();
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		int rowCount = tableModel.getRowCount();
		for(int i=0;i<rowCount;i++){
			tableModel.removeRow(0);
		}
			
		for (WeiboPO weiboPO : weiboPOs)
		{
			tableModel.addRow(new Object[]{weiboPO.getWeiboId(),weiboPO.getUserId(),weiboPO.getContent(),weiboPO.getReleasedTime()});
		}
	}
}
