
package org.soundroid.lib.util;

import org.soundroid.constants.SoundroidConstants;
import org.soundroid.models.UserCredentials;
import org.soundroid.oauth.Token;

public class ClientSettings
{
	private Token userSpecificToken;
	private Token generalToken;
	private Token consumerToken;
	private String webServiceUrl = SoundroidConstants.WEB_SERVICE_BASE_URL;
	private String oAuthRequestTokenUrl = SoundroidConstants.OAUTH_REQUEST_TOKEN_URL;
	private String oAuthAuthorizeUrl = SoundroidConstants.OAUTH_AUTHORIZE_URL;
	private String oAuthAccessTokenUrl = SoundroidConstants.OAUTH_ACCESS_TOKEN_URL;
	private String oAuthCallbackUrl = null;
	private UserCredentials credentials;

	public Token getConsumerToken()
	{
		return consumerToken;
	}

	public void setConsumerToken(Token t)
	{
		this.consumerToken = t;
	}

	public Token getUserSpecificToken()
	{
		if (userSpecificToken == null)
		{
			userSpecificToken = new Token();
		}
		return userSpecificToken;
	}

	public void setUserSpecificToken(Token t)
	{
		this.userSpecificToken = t;
	}

	public Token getGeneralToken()
	{
		if (generalToken == null)
		{
			generalToken = new Token();
		}
		
		return generalToken;
	}

	public void setGeneralToken(Token t)
	{
		this.generalToken = t;
	}

	public String getWebServiceUrl()
	{
		return webServiceUrl;
	}

	public void setWebServiceUrl(String webServiceUrl)
	{
		this.webServiceUrl = webServiceUrl;
	}

	public String getOAuthRequestTokenUrl()
	{
		return oAuthRequestTokenUrl;
	}

	public void setOAuthRequestTokenUrl(String authRequestTokenUrl)
	{
		oAuthRequestTokenUrl = authRequestTokenUrl;
	}

	public String getOAuthAuthorizeUrl()
	{
		return oAuthAuthorizeUrl;
	}

	public void setOAuthAuthorizeUrl(String authAuthorizeUrl)
	{
		oAuthAuthorizeUrl = authAuthorizeUrl;
	}

	public String getOAuthAccessTokenUrl()
	{
		return oAuthAccessTokenUrl;
	}

	public void setOAuthAccessTokenUrl(String authAccessTokenUrl)
	{
		oAuthAccessTokenUrl = authAccessTokenUrl;
	}



	public UserCredentials getCredentials()
	{
		return credentials;
	}

	public void setCredentials(UserCredentials credentials)
	{
		this.credentials = credentials;
	}

	public String toString()
	{
		return "todo";
	}

	public String getOAuthCallbackUrl()
	{
		return oAuthCallbackUrl;
	}
	
	public void setOAuthCallbackUrl(String u)
	{
		this.oAuthCallbackUrl = u;
	}
	
}
