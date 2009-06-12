
package org.soundroid.exceptions;

public class SoundroidException extends RuntimeException
{
	public SoundroidException(Throwable cause)
	{
		super(cause);
	}

	public SoundroidException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
	
	
	public SoundroidException(String msg)
	{
		super(msg);
	}
	
}
