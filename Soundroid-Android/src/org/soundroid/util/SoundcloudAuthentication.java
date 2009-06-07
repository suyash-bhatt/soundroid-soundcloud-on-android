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

package org.soundroid.util;

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
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 
 * @author Antonio Hinojo
 * 
 */
public class SoundcloudAuthentication {

	private String CONSUMER_KEY = "YOUR_CONSUMER_KEY";
	private String CONSUMER_SECRET = "YOUR_CONSUMER_SECRET"; 
	
	private String REQUEST_TOKEN_ENDPOINT_URL = "http://api.soundcloud.com/oauth/request_token";
	private String ACCESS_TOKEN_ENDPOINT_URL = "http://api.soundcloud.com/oauth/access_token";
	private String AUTHORIZE_WEBSITE_URL = "http://soundcloud.com/oauth/authorize";
	private OAuthConsumer consumer;
	private OAuthProvider provider;
	private HttpGet request;
	private HttpClient httpClient; 
	
	public SoundcloudAuthentication(SignatureMethod signatureMethod){
		consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET, signatureMethod);
		provider = new DefaultOAuthProvider(consumer, REQUEST_TOKEN_ENDPOINT_URL, ACCESS_TOKEN_ENDPOINT_URL, AUTHORIZE_WEBSITE_URL);
		httpClient = new DefaultHttpClient();
	}
	
	public SoundcloudAuthentication(SignatureMethod signatureMethod, String consumerKey, String consumerSecret, String requestTokenEndPoint, String accessTokenEndPoint, String authorizeWebsiteUrl){
		this(signatureMethod);
		
		CONSUMER_KEY = consumerKey;
		CONSUMER_SECRET = consumerSecret;
		REQUEST_TOKEN_ENDPOINT_URL = requestTokenEndPoint;
		ACCESS_TOKEN_ENDPOINT_URL = accessTokenEndPoint;
		AUTHORIZE_WEBSITE_URL = authorizeWebsiteUrl;
	}
	
	public String getRequestToken() throws OAuthExpectationFailedException, OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthCommunicationException{
		return provider.retrieveRequestToken(null);
	}
	
	public String getRequestToken(String callbackUrl) throws OAuthExpectationFailedException, OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthCommunicationException{
		return provider.retrieveRequestToken(callbackUrl);
	}
	
	/**
	 * Please note that user MUST be logged in the web app before call this method.
	 * 
	 * @return
	 * @throws OAuthExpectationFailedException
	 * @throws OAuthMessageSignerException
	 * @throws OAuthNotAuthorizedException
	 * @throws OAuthCommunicationException
	 */
	public String getAccessToken() throws OAuthExpectationFailedException, OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthCommunicationException{
		provider.retrieveAccessToken();
		
		return consumer.getTokenSecret();
	}
	
	public HttpResponse makeRequest(String url) throws OAuthMessageSignerException, ClientProtocolException, IOException{
		request = new HttpGet(url);
		consumer.sign(request);		
		return httpClient.execute(request);
	}
	
	public String makeRequestWithHandler(String url) throws OAuthMessageSignerException, ClientProtocolException, IOException{
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		request = new HttpGet(url);
		consumer.sign(request);		
		return httpClient.execute(request, responseHandler);
	}
	
	
	
	
	public String getCONSUMER_KEY() {
		return CONSUMER_KEY;
	}
	public void setCONSUMER_KEY(String consumer_key) {
		CONSUMER_KEY = consumer_key;
	}
	public String getCONSUMER_SECRET() {
		return CONSUMER_SECRET;
	}
	public void setCONSUMER_SECRET(String consumer_secret) {
		CONSUMER_SECRET = consumer_secret;
	}
	public String getREQUEST_TOKEN_ENDPOINT_URL() {
		return REQUEST_TOKEN_ENDPOINT_URL;
	}
	public void setREQUEST_TOKEN_ENDPOINT_URL(String request_token_endpoint_url) {
		REQUEST_TOKEN_ENDPOINT_URL = request_token_endpoint_url;
	}
	public String getACCESS_TOKEN_ENDPOINT_URL() {
		return ACCESS_TOKEN_ENDPOINT_URL;
	}
	public void setACCESS_TOKEN_ENDPOINT_URL(String access_token_endpoint_url) {
		ACCESS_TOKEN_ENDPOINT_URL = access_token_endpoint_url;
	}
	public String getAUTHORIZE_WEBSITE_URL() {
		return AUTHORIZE_WEBSITE_URL;
	}
	public void setAUTHORIZE_WEBSITE_URL(String authorize_website_url) {
		AUTHORIZE_WEBSITE_URL = authorize_website_url;
	}

	public OAuthProvider getProvider() {
		return provider;
	}

	public void setProvider(OAuthProvider provider) {
		this.provider = provider;
	}

	public OAuthConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(OAuthConsumer consumer) {
		this.consumer = consumer;
	}
}
