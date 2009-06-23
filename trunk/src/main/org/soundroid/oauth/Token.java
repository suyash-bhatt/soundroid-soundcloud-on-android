
package org.soundroid.oauth;

public class Token implements java.io.Serializable
{
	private String publicKey;
	private String secret;
	
	public String getPublicKey()
	{
		return publicKey;
	}
	
	public String getSecret()
	{
		return secret;
	}
	
	public void setPublicKey(String value)
	{
		this.publicKey = value;
	}
	
	public void setSecret(String value)
	{
		this.secret = value;
	}
	
	public boolean isValid()
	{
		if (this.getPublicKey() == null)
		{
			return false;
		}
		else if (this.getPublicKey().trim().length() == 0)
		{
			return false;
		}
		else if (this.getSecret() == null)
		{
			return false;
		}
		else if (this.getSecret().trim().length() == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	public String toString()
	{
		return "public='" 
				+ this.getPublicKey()
				+ "', secret='" + this.getSecret() + "'";
	}
	
}
