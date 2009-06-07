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

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * 
 * 
 * @author Antonio Hinojo
 * 
 *         Main activity
 * 
 */
public class SoundroidActivity extends AbstractActivity {

	private SoundcloudClient client;

	public SoundroidActivity() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		client = SoundroidApplication.createSoundcloudClient();

		if (Util.isCallback(this.getIntent())) {

			client.setRequestToken(Preferences.getRequestToken());

			fetchAccessToken();

			SoundroidService.restart(this);

		} else {
			final AlertDialog alert = new AlertDialog.Builder(this).create();

			alert.setMessage("To get started, you will need to login to Soundcloud");

			alert.setButton("Login", new DialogInterface.OnClickListener() {
				String url;

				public void onClick(DialogInterface dialog, int whichButton) {
					alert.dismiss();

					try {
						url = client.getRequestToken(SoundroidConstants.OAUTH_CALLBACK_URL);
						Log.i(this.getClass().getName(), url);
						Preferences.updateRequestToken(client.getRequestToken());
						Preferences.commit();
						openWebBrowser(url);

					} catch (OAuthExpectationFailedException e) {
						e.printStackTrace();
					} catch (OAuthMessageSignerException e) {
						e.printStackTrace();
					} catch (OAuthNotAuthorizedException e) {
						e.printStackTrace();
					} catch (OAuthCommunicationException e) {
						e.printStackTrace();
					} finally {
						SoundroidActivity.this.finish();
					}
				}
			});
			alert.show();
		}
	}

	protected void fetchAccessToken() {
		try {
			Preferences.updateUserSpecificAccessToken(client.getAccessToken());
			Preferences.commit();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}