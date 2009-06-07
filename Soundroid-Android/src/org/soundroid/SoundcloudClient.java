/*
 * Copyright (C) 2009 SIAHM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.soundroid;

import java.io.IOException;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.impl.DefaultOAuthConsumer;
import oauth.signpost.impl.DefaultOAuthProvider;
import oauth.signpost.signature.SignatureMethod;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 
 * @author Antonio Hinojo
 * 
 */
public class SoundcloudClient {

	private OAuthConsumer consumer;
	private OAuthProvider provider;
	private HttpGet request;
	private HttpClient httpClient;
	private Token requestToken;

	public SoundcloudClient(HttpClient httpClient) {

		if(httpClient != null){
			this.httpClient = httpClient;
		}else{
			this.httpClient = new DefaultHttpClient();
		}		

		this.requestToken = new Token();
		this.setUserAgent("Android");
		this.setConnectionTimeout(15 * 1000);
		this.setSocketTimeout(30 * 1000);
		this.provider = new DefaultOAuthProvider(getOAuthConsumer(), SoundroidConstants.OAUTH_REQUEST_TOKEN_URL, SoundroidConstants.OAUTH_ACCESS_TOKEN_URL, SoundroidConstants.OAUTH_AUTHORIZE_URL);
	}

	public void setUserAgent(String ua) {
		this.httpClient.getParams().setParameter(AllClientPNames.USER_AGENT, ua);
	}

	public void setConnectionTimeout(int milliseconds) {
		httpClient.getParams().setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, milliseconds);
	}

	
	public void setSocketTimeout(int milliseconds) {
		httpClient.getParams().setIntParameter(AllClientPNames.SO_TIMEOUT, milliseconds);
	}

	
	protected OAuthConsumer createOAuthConsumer() {
		consumer = new DefaultOAuthConsumer(SoundroidConstants.OAUTH_CONSUMER_KEY, SoundroidConstants.OAUTH_CONSUMER_SECRET, SignatureMethod.HMAC_SHA1);
		return consumer;
	}

	
	protected OAuthConsumer getOAuthConsumer() {
		if (consumer == null) {
			consumer = createOAuthConsumer();
		}
		
		return consumer;
	}

	
	public void setRequestToken(Token requestToken) {
		this.requestToken = requestToken;
	}

	public Token getRequestToken() throws OAuthExpectationFailedException,	OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthCommunicationException {
//		 provider.retrieveRequestToken(null);
//		
//		 requestToken.setConsumerKey(SoundroidConstants.OAUTH_CONSUMER_KEY);
//		 requestToken.setConsumerSecret(SoundroidConstants.OAUTH_CONSUMER_SECRET);
		if(requestToken == null){
			requestToken = new Token();
		}
		
		String url = provider.retrieveRequestToken(null);		
		String[] array = url.split("=");		
		String requestTokenStr = (array[1].split("&"))[0];
		
		requestToken.setRequestToken(requestTokenStr);
		
		if(requestToken.getConsumerKey() == null || "".equals(requestToken.getConsumerKey())){
			requestToken.setConsumerKey(SoundroidConstants.OAUTH_CONSUMER_KEY);
		}
		
		if(requestToken.getConsumerSecret() == null || "".equals(requestToken.getConsumerSecret())){
			requestToken.setConsumerSecret(SoundroidConstants.OAUTH_CONSUMER_SECRET);
		}	
		 
		return requestToken;
	}

	public String getRequestToken(String callbackUrl) throws OAuthExpectationFailedException, OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthCommunicationException {
		if(requestToken == null){
			requestToken = new Token();
		}
		
		String url = provider.retrieveRequestToken(null);		
		String[] array = url.split("=");		
		String requestTokenStr = (array[1].split("&"))[0];
		
		requestToken.setRequestToken(requestTokenStr);
		
		if(requestToken.getConsumerKey() == null || "".equals(requestToken.getConsumerKey())){
			requestToken.setConsumerKey(SoundroidConstants.OAUTH_CONSUMER_KEY);
		}
		
		if(requestToken.getConsumerSecret() == null || "".equals(requestToken.getConsumerSecret())){
			requestToken.setConsumerSecret(SoundroidConstants.OAUTH_CONSUMER_SECRET);
		}	
		 
		
		return provider.retrieveRequestToken(callbackUrl);
	}

	/**
	 * Please note that user MUST be logged in the web app before call this
	 * method.
	 * 
	 * @return
	 * @throws OAuthExpectationFailedException
	 * @throws OAuthMessageSignerException
	 * @throws OAuthNotAuthorizedException
	 * @throws OAuthCommunicationException
	 */
	public Token getAccessToken() throws OAuthExpectationFailedException, OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthCommunicationException {
				
		if(requestToken == null){
			requestToken = new Token();
		}
		
		provider.retrieveAccessToken();
		
		requestToken.setAccessToken(consumer.getTokenSecret());
		
		if(requestToken.getConsumerKey() == null || "".equals(requestToken.getConsumerKey())){
			requestToken.setConsumerKey(SoundroidConstants.OAUTH_CONSUMER_KEY);
		}
		
		if(requestToken.getConsumerSecret() == null || "".equals(requestToken.getConsumerSecret())){
			requestToken.setConsumerSecret(SoundroidConstants.OAUTH_CONSUMER_SECRET);
		}	
		
		return requestToken;
	}

	public HttpResponse makeRequest(String url) throws OAuthMessageSignerException, ClientProtocolException, IOException {
		request = new HttpGet(url);
		consumer.sign(request);
		return httpClient.execute(request);
	}

	public String makeRequestWithHandler(String url) throws OAuthMessageSignerException, ClientProtocolException, IOException {
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		request = new HttpGet(url);
		consumer.sign(request);
		return httpClient.execute(request, responseHandler);
	}

	public OAuthProvider getProvider() {
		return provider;
	}

	public OAuthConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(OAuthConsumer consumer) {
		this.consumer = consumer;
	}
	
	public void shutdown(){
		try{
			this.httpClient.getConnectionManager().shutdown();
		}
		catch (Exception ignore)
		{

		}		
	}
}
