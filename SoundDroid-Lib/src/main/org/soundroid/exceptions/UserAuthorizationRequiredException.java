
package org.soundroid.exceptions;

public class UserAuthorizationRequiredException extends SoundroidException
{

	public UserAuthorizationRequiredException(String url)
	{
		super(url);
	}
	
	public String getUrl()
	{
		return this.getMessage();
	}

}
