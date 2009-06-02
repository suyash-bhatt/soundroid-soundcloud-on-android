package org.soundroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
 * 03/06/2009
 *
 */
public class Main {

	public static void main(String[] args) {
		String CONSUMER_KEY = "YOUR_CONSUMER_KEY";
		String CONSUMER_SECRET = "YOUR_CONSUMER_SECRET";

		String REQUEST_TOKEN_ENDPOINT_URL = "http://api.soundcloud.com/oauth/request_token";
		String ACCESS_TOKEN_ENDPOINT_URL = "http://api.soundcloud.com/oauth/access_token";
		String AUTHORIZE_WEBSITE_URL = "http://soundcloud.com/oauth/authorize";
		
		OAuthConsumer consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET, SignatureMethod.HMAC_SHA1);
		OAuthProvider provider = new DefaultOAuthProvider(consumer, REQUEST_TOKEN_ENDPOINT_URL, ACCESS_TOKEN_ENDPOINT_URL, AUTHORIZE_WEBSITE_URL);
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request;
		
		try {

			// First i need to make a request to obtain the request token
			String url = provider.retrieveRequestToken(null);
			
			// If yor app is web you can put here the url fo the callback
			// String url = provider.retrieveRequestToken(CALLBACK_URL);

			// I confirm that I have received the request token
			request = new HttpGet(url);
			// Next I have to sign the request
			consumer.sign(request);

			httpClient = new DefaultHttpClient();
			HttpResponse resp = httpClient.execute(request);

			System.out.println("----------------------------------------");
			System.out.println("Status Code: " + resp.getStatusLine().getStatusCode());
			System.out.println("----------------------------------------");

			// Now the user must go to the specified url (url variable) and
			// login and accept the access to the user account
			System.out.println("Please go to the url: "
							+ url
							+ " before get the access token and  login and accept the access to your account");
			System.out.println("Hit ENTER when you're done");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			br.readLine();

			// Next we need to get the access token
			provider.retrieveAccessToken();

			// Now we cn access to secured resource without any problem

			// create an HTTP request to a protected resource
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			request = new HttpGet("http://api.soundcloud.com/me");

			// sign the request
			consumer.sign(request);

			// send the request
			httpClient = new DefaultHttpClient();

			// Print the result
			System.out.println(httpClient.execute(request, responseHandler));

			System.out.println("----------------------------------------");
			System.out.println("----------------------------------------");
			request = new HttpGet("http://api.soundcloud.com/me");

			// sign the request
			consumer.sign(request);

			// send the request
			httpClient = new DefaultHttpClient();

			// Print the result
			System.out.println(httpClient.execute(request, responseHandler));

		} catch (OAuthExpectationFailedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (OAuthMessageSignerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (OAuthNotAuthorizedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (OAuthCommunicationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}