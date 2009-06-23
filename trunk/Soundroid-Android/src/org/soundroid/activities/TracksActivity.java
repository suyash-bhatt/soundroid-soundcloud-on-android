package org.soundroid.activities;

import org.soundroid.R;
import org.soundroid.adapters.TrackAdapter;
import org.soundroid.models.Tracks;
import org.soundroid.oauth.Response;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

/**
 * TODO Add more information on the screen.
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
	    
	       TrackAdapter trackAdapter = new TrackAdapter(this, tracks.getTracks()); 
	      
	       setListAdapter(trackAdapter);
	       
//		super.onCreate(savedInstanceState);
//
//		Response<Tracks> response = SoundroidActivity.client.getTracks();
//		Tracks tracks = response.getData();
//
//		setListAdapter(new ArrayAdapter<Track>(this,
//				android.R.layout.simple_list_item_1, tracks.getTracks()));

		// for (Track track : tracks.getTracks()) {
		//			
		// Bitmap waveForm = BitmapUtils.loadBitmap(track.getWaveform_url());
		//			
		// if(waveForm != null){
		// LinearLayout layout = new LinearLayout(this);
		// layout.setOrientation(LinearLayout.HORIZONTAL);
		// layout.setGravity(Gravity.FILL);
		//				
		// waveform = new ImageView(this);
		// waveform.setEnabled(true);
		// waveform.setImageBitmap(waveForm);
		// layout.addView(waveform);
		//				
		// mainLayout.addView(layout);
		// }
		//		
		// }

//		setContentView(R.layout.list);

	}
}
