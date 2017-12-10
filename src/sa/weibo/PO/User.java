package sa.weibo.PO;

import java.io.Serializable;

public class User implements Serializable
{
	private long userid;
	private long clickCount;
	public long getUserid()
	{
		return userid;
	}
	public void setUserid(Long userid)
	{
		this.userid = userid;
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
