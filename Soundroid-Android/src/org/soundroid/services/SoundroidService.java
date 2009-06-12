package org.soundroid.services;


import org.soundroid.application.SoundroidApplication;
import org.soundroid.client.SoundcloudClient;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class SoundroidService extends Service implements android.location.LocationListener {
	private SoundcloudClient client;

	static public Intent getIntent(Context ctx) {
		Intent i = new Intent();
		ComponentName component = new ComponentName(ctx, SoundroidService.class);
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
		Log.i("Soundroid", "background service onCreate called");
	}

	@Override
	public void onStart(Intent i, int startId) {
		super.onStart(i, startId);
		Log.i("Soundroid", "background service onStart called");
		client = SoundroidApplication.createSoundcloudClient();
	}

	@Override
	public void onDestroy() {
		Log.i("Soundroid", "background service onDestroy called");
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
	}

	public void shutdownClient() {
		try {
			client.shutdown();
		} catch (Exception ignored) {
			// ignore
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
}
