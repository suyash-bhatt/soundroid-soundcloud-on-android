package org.soundroid;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Application;

public class SoundroidApplication extends Application {

	private static SoundroidApplication appInstance;

	public static SoundcloudClient createSoundcloudClient() {
		SoundcloudClient client = new SoundcloudClient(new DefaultHttpClient());		
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
