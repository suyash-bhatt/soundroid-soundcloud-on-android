package org.soundroid.activities.you;

import org.soundroid.R;
import org.soundroid.activities.main.SoundroidActivity;
import org.soundroid.adapters.TrackAdapter;
import org.soundroid.models.Tracks;
import org.soundroid.oauth.Response;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

/**
 * TODO Add more information on the screen.
 * 	+ Stop the thread when the user push the stop button
 * 
 * @author Antonio Hinojo
 * 
 */
public class TracksActivity extends ListActivity {
	public static final String ACTION_TRACKS = "org.soundroid.TRACKS";
	public static final Intent INTENT = new Intent(ACTION_TRACKS);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		Response<Tracks> response = SoundroidActivity.client.getTracks();
		Tracks tracks = response.getData();

		final TrackAdapter trackAdapter = new TrackAdapter(this, tracks.getTracks());
		setListAdapter(trackAdapter);

//		Thread pollThread = new Thread() {
//			public void run() {
//				while (true) {
//					runOnUiThread(new Runnable() {
//						public void run() {
//							trackAdapter.getTrackAdapterView().updatePlayedTime();
//						}
//					});
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		};
//		pollThread.start();
	}	
}
