package org.soundroid.application;

import org.soundroid.client.SoundcloudClient;
import org.soundroid.constants.SoundroidConstants;
import org.soundroid.oauth.Token;
import org.soundroid.util.ClientSettings;
import org.soundroid.util.Preferences;
import org.soundroid.util.Util;

import android.app.Application;

public class SoundroidApplication extends Application {

	static private SoundroidApplication appInstance;

	static public Token getConsumerToken() {
		Token consumerToken = new Token();
		consumerToken.setPublicKey(SoundroidConstants.OAUTH_CONSUMER_KEY);
		consumerToken.setSecret(SoundroidConstants.OAUTH_CONSUMER_SECRET);
		return consumerToken;
	}

	static public SoundcloudClient createSoundcloudClient() {

		// todo (?) : store SoundcloudClient in a static variable
		SoundcloudClient client = new SoundcloudClient();
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
