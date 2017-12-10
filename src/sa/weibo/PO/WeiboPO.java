package sa.weibo.PO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.management.relation.Relation;

public class WeiboPO implements Serializable
{
	private long weiboId;
	private long userId;
	private Timestamp releasedTime;
	private String content;
	private long clickCount;
	
	public WeiboPO()
	{
		// TODO Auto-generated constructor stub
	}
	
	public long getWeiboId()
	{
		return this.weiboId;
	}
	
	public void setWeiboId(Long weiboId)
	{
		this.weiboId = weiboId;
	}
	
	public long getUserId()
	{
		return userId;
	}
	
	public void setUserId(Long userId)
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
	public long getClickCount()
	{
		return clickCount;
	}
	public void setClickCount(Long clickCount)
	{
		this.clickCount = clickCount;
	}
}
