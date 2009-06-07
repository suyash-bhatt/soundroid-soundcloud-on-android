package org.soundroid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class IntentReceiver extends BroadcastReceiver {
	public IntentReceiver() {
		super();
	}

	@Override
	public void onReceive(Context ctx, Intent intent) {
		String action = intent.getAction();
		Log.i("Soundcloud", "IntentReceiver action = " + action);
		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			onBootCompleted(ctx, intent);
		}
	}

	protected void onBootCompleted(Context ctx, Intent intent) {

		Log.i("Soundcloud", "about to start background service");
		ComponentName result = ctx.startService(SoundroidService.getIntent(ctx));
		if (result == null) {
			Log.e("Soundcloud", "unable to start background service");
		}
	}
}
