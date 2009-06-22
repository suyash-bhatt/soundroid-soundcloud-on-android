package org.soundroid.services;

import org.soundroid.application.SoundroidApplication;
import org.soundroid.client.SoundcloudClientJSON;
import org.soundroid.lib.util.ClientSettings;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class BackgroundService extends Service {
	private SoundcloudClientJSON client;
	private ClientSettings settings;

	static public Intent getIntent(Context ctx) {
		Intent i = new Intent();

		ComponentName component = new ComponentName(ctx,
				BackgroundService.class);

		i.setComponent(component);

		return i;
	}

	static public ComponentName start(Context ctx) {
		return ctx.startService(getIntent(ctx));
	}

	static public boolean stop(Context ctx) {
		return ctx.stopService(getIntent(ctx));
	}

	static public ComponentName restart(Context ctx) {
		stop(ctx);
		return start(ctx);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("Soundcloud", "background service onCreate called");
	}

	@Override
	public void onStart(Intent i, int startId) {
		super.onStart(i, startId);
		Log.i("Soundcloud", "background service onStart called");
		client = SoundroidApplication.createSoundcloudClient();
		settings = client.getClientSettings();
	}

	@Override
	public void onDestroy() {
		Log.i("Soundcloud", "background service onDestroy called");
		super.onDestroy();
		shutdown();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		shutdown();
	}

	public void shutdown() {
		shutdownClient();
		client = null;
		settings = null;
	}

	public void shutdownClient() {
		try {
			client.shutdown();
		} catch (Exception ignored) {
			// ignore
		}
	}

	public void onProviderDisabled(String provider) {
		// todo : code here?
	}

	public void onProviderEnabled(String provider) {
		// todo : code here?
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// todo : code here
	}
}
