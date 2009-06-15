package org.soundroid.client;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.client.OAuthClient;
import net.oauth.client.httpclient4.HttpClient4;
import net.oauth.client.httpclient4.HttpClientPool;
import net.oauth.http.HttpMessageDecoder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.soundroid.constants.SoundroidConstants;
import org.soundroid.exceptions.SoundroidException;
import org.soundroid.exceptions.UserAuthorizationRequiredException;
import org.soundroid.oauth.Response;
import org.soundroid.oauth.Token;
import org.soundroid.util.ClientSettings;
import org.soundroid.xml.factory.XStreamFactory;
import org.soundroid.xml.models.Comments;
import org.soundroid.xml.models.Events;
import org.soundroid.xml.models.Tracks;
import org.soundroid.xml.models.User;
import org.soundroid.xml.models.Users;

import com.thoughtworks.xstream.XStream;

public class SoundcloudClient {
		
	private XStream xstream;
	private ClientSettings settings;
	private HttpClient httpClient;
	private OAuthClient authClient;
	private OAuthServiceProvider authProvider;
	private Token requestToken = new Token();
	private boolean compressionEnabled = false;
	private HttpClientPool pool;

	//CONSTRUCTORS
	public SoundcloudClient() {
		this(new DefaultHttpClient(), null);
	}

	public SoundcloudClient(ClientSettings cs) {
		this(new DefaultHttpClient(), cs);

	}

	public SoundcloudClient(final HttpClient hClient) {
		this(hClient, null);
	}

	public SoundcloudClient(final HttpClient hClient, ClientSettings cs) {
		this.httpClient = hClient;

		this.setClientSettings(cs);

		setUserAgent("soundroid");
		setConnectionTimeout(15 * 1000);
		setSocketTimeout(30 * 1000);

		this.pool = new HttpClientPool() {

			public HttpClient getHttpClient(URL u) {
				return httpClient;
			}
		};
	}
	
	//REST CALL METHODS
	///////////////////////////
	// /me
	@SuppressWarnings("unchecked")
	public Response<User> getInfoAboutMe() {
		Response<User> response = (Response<User>) sendSoundcloudRequest("GET", "/me.xml", SoundroidConstants.ME, null, this.getUserSpecificAccessToken()); 
		return response;
	}
	
	// /me/tracks
	@SuppressWarnings("unchecked")
	public Response<Tracks> getTracks() {
		Response<Tracks> response = (Response<Tracks>) sendSoundcloudRequest("GET", "/me/tracks.xml", SoundroidConstants.TRACKS, null, this.getUserSpecificAccessToken());
		return response;
	}
	
	// /me/comments
	@SuppressWarnings("unchecked")
	public Response<Comments> getComments() {
		Response<Comments> response = (Response<Comments>) sendSoundcloudRequest("GET", "/me/comments.xml", SoundroidConstants.COMMENTS, null, this.getUserSpecificAccessToken());
		return response;
	}
	
	// /me/contacts
	@SuppressWarnings("unchecked")
	public Response<Users> getContacts() {
		Response<Users> response = (Response<Users>) sendSoundcloudRequest("GET", "/me/contacts.xml", SoundroidConstants.CONTACTS, null, this.getUserSpecificAccessToken());
		return response;
	}
	
	// /events
	@SuppressWarnings("unchecked")
	public Response<Events> getEvents(String filterValue) {
		Map<String, String> params = null;
		
		if(filterValue != null && !"".equals(filterValue)){
			params = new HashMap<String, String>();
			params.put("filter", filterValue);
		}
		
		Response<Events> response = (Response<Events>) sendSoundcloudRequest("GET", "/events.xml", SoundroidConstants.EVENTS, params, this.getUserSpecificAccessToken());
		
		return response;
	}

	//END REST CALL METHODS
	///////////////////////////
	
	/////////////////////////////////
	/////////////////////////////////
	/////////////////////////////////
	protected Token getUserSpecificAccessToken() {
		return this.getClientSettings().getUserSpecificToken();
	}

	public ClientSettings getClientSettings() {
		if (this.settings == null) {
			this.settings = new ClientSettings();
		}
		return this.settings;
	}

	protected OAuthServiceProvider createOAuthServiceProvider() {

		OAuthServiceProvider p = new OAuthServiceProvider(this
				.getClientSettings().getOAuthRequestTokenUrl(), this
				.getClientSettings().getOAuthAuthorizeUrl(), this
				.getClientSettings().getOAuthAccessTokenUrl());

		return p;

	}

	protected OAuthServiceProvider getOAuthServiceProvider() {
		if (authProvider == null) {
			authProvider = createOAuthServiceProvider();
		}
		return authProvider;
	}

	protected OAuthAccessor createOAuthAccessor() {

		Token consumerToken = this.getConsumerToken();

		String oauthCallBackUrl = this.getClientSettings()
				.getOAuthCallbackUrl();

		OAuthConsumer c = new OAuthConsumer(oauthCallBackUrl, consumerToken
				.getPublicKey(), consumerToken.getSecret(),
				getOAuthServiceProvider());

		if (this.compressionEnabled) {
			c.setProperty(OAuthClient.ACCEPT_ENCODING,
					HttpMessageDecoder.ACCEPTED);
		}

		OAuthAccessor a = new OAuthAccessor(c);

		return a;

	}

	protected OAuthClient createOAuthClient() {

		OAuthClient authClient = new OAuthClient(new HttpClient4(this.pool));

		return authClient;
	}

	protected OAuthClient getOAuthClient() {

		if (authClient == null) {
			authClient = createOAuthClient();
		}

		return authClient;
	}

	protected Response<?> sendSoundcloudRequest(String method, String service, int requestType, java.util.Map<String, String> params, Token token) {
		checkUserAccessToken();

		String xml = sendHttpRequest(this.getClientSettings().getWebServiceUrl() + service, method, params, token);

		Response<?> r = fromXml(xml, requestType);

		return r;
	}

	protected void checkUserAccessToken() {
		if (hasValidUserAccessToken() == false) {

			if (this.requestToken.isValid()) {
				fetchAccessToken();
			} else {
				String userAuthorizationUrl = getUserAuthorizationUrl();

				throw new UserAuthorizationRequiredException(
						userAuthorizationUrl);
			}
		}
	}

	protected Token getConsumerToken() {
		return this.getClientSettings().getConsumerToken();
	}

	@SuppressWarnings("unchecked")
	public void fetchAccessToken() {

		// System.out.println("fetchAccessToken: requestToken=" +
		// this.getRequestToken());

		OAuthClient client = getOAuthClient();

		OAuthMessage responseMsg = null;

		OAuthAccessor access = this.createOAuthAccessor();

		try {

			//
			// request the Access token
			//

			access.accessToken = requestToken.getPublicKey();
			access.requestToken = requestToken.getPublicKey();
			access.tokenSecret = requestToken.getSecret();

			responseMsg = client.invoke(access, "GET", this.getClientSettings()
					.getOAuthAccessTokenUrl(), null);

			this.getUserSpecificAccessToken().setPublicKey(
					responseMsg.getParameter("oauth_token"));
			this.getUserSpecificAccessToken().setSecret(
					responseMsg.getParameter("oauth_token_secret"));

		} catch (Exception e) {
			throw new SoundroidException(e);
		}
	}

	public String getUserAuthorizationUrl() {

		String url = null;

		OAuthClient client = getOAuthClient();

		OAuthAccessor access = this.createOAuthAccessor();

		try {
			this.requestToken = null;
			access.requestToken = null;
			access.accessToken = null;
			access.tokenSecret = null;

			//
			// send GET request to the Request Token URL
			//

			client.getRequestToken(access);

			// store the Request Token values
			requestToken = new Token();
			requestToken.setPublicKey(access.requestToken);
			requestToken.setSecret(access.tokenSecret);

			//
			//
			// build user authorization URL
			//
			//

			url = this.getClientSettings().getOAuthAuthorizeUrl()
					+ "?oauth_token=" + requestToken.getPublicKey();

			if (settings.getOAuthCallbackUrl() != null) {
				url = url + "&oauth_callback=" + settings.getOAuthCallbackUrl();
			}

			return url;

		} catch (Exception e) {
			throw new SoundroidException(e);
		}

	}

	protected String sendHttpRequest(String baseUrl, String method,
			java.util.Map<String, String> params, Token token) {

		OAuthClient client = getOAuthClient();

		OAuthMessage responseMsg = null;

		OAuthAccessor access = this.createOAuthAccessor();

		access.accessToken = token.getPublicKey();
		access.tokenSecret = token.getSecret();

		if (params == null) {
			params = new HashMap<String, String>();
		}

		try {
			responseMsg = client.invoke(access, method, baseUrl, params.entrySet());
			return responseMsg.readBodyAsString();

		} catch (Exception e) {
			throw new SoundroidException(e);
		}

	}

	protected boolean isEmpty(String s) {
		if (s == null) {
			return true;
		} else if (s.length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void setClientSettings(ClientSettings s) {
		this.settings = s;
	}

	protected boolean hasValidUserAccessToken() {
		return this.getUserSpecificAccessToken().isValid();
	}

	protected Token getGeneralAccessToken() {
		return this.getClientSettings().getGeneralToken();
	}

	protected boolean generalAccessTokenIsValid() {
		return this.getGeneralAccessToken().isValid();
	}

	protected Response<?> fromXml(String xmlResponse, int requestType) {
		
		switch (requestType) {
			case SoundroidConstants.ME:
				XStream xstream = XStreamFactory.createXStream(requestType);	
				User user = (User) xstream.fromXML(xmlResponse);	
				Response<User> userResponse = new Response<User>();
				userResponse.setData(user);	
				return userResponse;
			
			case SoundroidConstants.TRACKS:	
				XStream xstreamTracks = XStreamFactory.createXStream(requestType);
				Tracks tracks = (Tracks) xstreamTracks.fromXML(xmlResponse);	
				Response<Tracks> trackResponse = new Response<Tracks>();
				trackResponse.setData(tracks);	
				return trackResponse;
				
			case SoundroidConstants.COMMENTS:	
				XStream xstreamComments = XStreamFactory.createXStream(requestType);
				Comments comments = (Comments) xstreamComments.fromXML(xmlResponse);	
				Response<Comments> commentsResponse = new Response<Comments>();
				commentsResponse.setData(comments);	
				return commentsResponse;
				
			case SoundroidConstants.EVENTS:	
				XStream xstreamEvents = XStreamFactory.createXStream(requestType);
				Events events = (Events) xstreamEvents.fromXML(xmlResponse);	
				Response<Events> eventsResponse = new Response<Events>();
				eventsResponse.setData(events);	
				return eventsResponse;
				
			case SoundroidConstants.CONTACTS:	
				XStream xstreamContacts = XStreamFactory.createXStream(requestType);
				Users contacts = (Users) xstreamContacts.fromXML(xmlResponse);	
				Response<Users> contactsResponse = new Response<Users>();
				contactsResponse.setData(contacts);	
				return contactsResponse;
				
			default: 
				return new Response();
		}
		
	}

	protected XStream getXStream() {

		return xstream;
	}

	public Token getToken() {
		return this.requestToken;
	}

	public void shutdown() {
		try {
			this.httpClient.getConnectionManager().shutdown();
		} catch (Exception ignore) {
			// ignored
		}

	}

	public void setUserAgent(String ua) {
		this.httpClient.getParams()
				.setParameter(AllClientPNames.USER_AGENT, ua);
	}

	public void setConnectionTimeout(int milliseconds) {
		httpClient.getParams().setIntParameter(
				AllClientPNames.CONNECTION_TIMEOUT, milliseconds);
	}

	public void setSocketTimeout(int milliseconds) {
		httpClient.getParams().setIntParameter(AllClientPNames.SO_TIMEOUT,
				milliseconds);
	}

	public void setCompressionEnabled(boolean b) {
		this.compressionEnabled = b;
	}

	public boolean getCompressionEnabled() {
		return this.compressionEnabled;
	}

	public Token getRequestToken() {
		return this.requestToken;
	}

	public void setRequestToken(Token t) {
		this.requestToken = t;
	}

}
