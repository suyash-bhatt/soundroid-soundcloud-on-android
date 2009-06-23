
package org.soundroid.oauth;


public class Response<T>
{
	//Response status
	private String stat;

	//¿¿??
	private String query;

	//Kind of Callback: encapsulates the return data
	private T data;
	
	public Response()
	{
		super();
	}

	public String getStat()
	{
		return stat;
	}

	public void setStat(String stat)
	{
		this.stat = stat;
	}

	public T getData()
	{
		return data;
	}
	
	public void setData(T data)
	{
		this.data = data;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String q)
	{
		this.query = q;
	}

	public String toString()
	{
		return this.toString();
	}
	
	public boolean isOK()
	{
		return "ok".equalsIgnoreCase(this.getStat());
	}
}
