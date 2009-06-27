package org.soundroid.activities.abstracts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class AbstractActivity extends Activity {

	protected void openWebBrowser(String url) {
		Log.i(this.getClass().getName(), "about to launch browser, url: " + url);
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

	protected void startThread(String name, Runnable r) {
		Thread t = new Thread(r);
		t.setName(name);
		t.start();
	}

	protected void startThread(Runnable r) {
		String name = this.getClass().getName() + " thread";
		startThread(name, r);
	}
}
