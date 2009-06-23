package org.soundroid.application;

import org.soundroid.client.SoundcloudClientJSON;
import org.soundroid.constants.SoundroidConstants;
import org.soundroid.factory.ClientFactory;
import org.soundroid.lib.util.ClientSettings;
import org.soundroid.oauth.Token;
import org.soundroid.util.Preferences;
import org.soundroid.util.Util;

import android.app.Application;



public class SoundroidApplication extends Application {

	private static SoundroidApplication appInstance;

	public static Token getConsumerToken() {
		Token consumerToken = new Token();
		consumerToken.setPublicKey(SoundroidConstants.OAUTH_CONSUMER_KEY);
		consumerToken.setSecret(SoundroidConstants.OAUTH_CONSUMER_SECRET);
		return consumerToken;
	}

	public static SoundcloudClientJSON createSoundcloudClient() {

		// todo (?) : store SoundcloudClient in a static variable		
		SoundcloudClientJSON client = ClientFactory.createJSONClient();
		
		//SoundcloudClientJSON client = new SoundcloudClientJSON();
		
		client.setCompressionEnabled(false); // todo : use true

		ClientSettings settings = new ClientSettings();
		settings.setOAuthCallbackUrl(Util.getCallbackUrl());
		settings.setUserSpecificToken(Preferences.getUserSpecificAccessToken());
		settings.setGeneralToken(Preferences.getGeneralAccessToken());
		settings.setConsumerToken(getConsumerToken());

		client.setClientSettings(settings);

		return client;
	}

	public void onCreate() {
		super.onCreate();
		appInstance = this;
		Preferences.reload(this);
	}

	public static SoundroidApplication get() {
		return appInstance;
	}

	public void onTerminate() {
		super.onTerminate();
	}
}
