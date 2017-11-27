package sa.weibo.PO;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.management.relation.Relation;

public class WeiboPO
{
	private int weiboId;
	private int userId;
	private Timestamp releasedTime;
	private String content;
	private int clickCount;
	
	public WeiboPO()
	{
		// TODO Auto-generated constructor stub
	}
	
	public int getWeiboId()
	{
		return this.weiboId;
	}
	
	public void setWeiboId(int weiboId)
	{
		this.weiboId = weiboId;
	}
	
	public int getUserId()
	{
		return userId;
	}
	
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	
	public Timestamp getReleasedTime()
	{
		return releasedTime;
	}
	
	public void setReleasedTime(Timestamp releasedTime)
	{
		this.releasedTime = releasedTime;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	public int getClickCount()
	{
		return clickCount;
	}
	public void setClickCount(int clickCount)
	{
		this.clickCount = clickCount;
	}
}
