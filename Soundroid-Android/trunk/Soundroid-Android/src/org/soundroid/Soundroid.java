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

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.signature.SignatureMethod;

import org.apache.http.client.ClientProtocolException;
import org.soundroid.util.SoundcloudAuthentication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 
 * 
 * @author Antonio Hinojo
 * 
 * Main activity
 * 
 */
public class Soundroid extends Activity {

	private SoundroidDbAdapter mDbHelper;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		final Button button = (Button) findViewById(R.id.Button01);
	        
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                // Perform action on click
	            	authenticate();
	            }
	        });
		
		mDbHelper = new SoundroidDbAdapter(this);
		mDbHelper.open();
	}

	private void authenticate() {
		SoundcloudAuthentication auth;

		try {

			auth = new SoundcloudAuthentication(SignatureMethod.HMAC_SHA1);

			// Ask for request token
			String url = auth.getRequestToken();

			// Confirm the request token has been received
			auth.makeRequest(url);

			// The user must be redirected to soundcloud auth and user must
			// allow the app to access the protected data

			openUrl(url);
			
			// Ask for access token
			//First Ineed to:
			//1. Register a handler of a custom protocol
			//2. Try to authorize the application on Soundcloud
			//3. Manage the request token signed in the url of the new protocol
			//4. Swap the request token for access token
			auth.getAccessToken();

			String response = auth.makeRequestWithHandler("http://api.soundcloud.com/me");

			System.out.println(response);

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
	
	private void openUrl(String url){
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(i); 
	}
}