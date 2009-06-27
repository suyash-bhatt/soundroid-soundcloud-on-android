package org.soundroid.adapters.views;

import java.io.IOException;

import org.soundroid.models.Track;
import org.soundroid.util.BitmapUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * 	TODO:
 *  		+ Improve behaviour when the user plays several tracks
 *  		+ Response is not so good as desiderable
 *  		+ UI is slow
 *  		+ Show song length and position, pause and similar
 *  
 * @author Antonio Hinojo
 *
 */
public class TrackAdapterView extends LinearLayout {
	
	private PlayerTask player;
	private MediaPlayer mp;
	private TextView trackControl;
	private TextView authorControl;
	private ImageView waveformImg;
	private Button playButton;

	public TrackAdapterView(final Context context, final Track track) {
		super(context);

		this.setOrientation(VERTICAL);

		// Track Title
		LinearLayout titleLayout = new LinearLayout(context);
		titleLayout.setOrientation(LinearLayout.HORIZONTAL);
		titleLayout.setGravity(Gravity.FILL);

		trackControl = new TextView(context);
		trackControl.setText(track.getTitle());
		trackControl.setTextColor(Color.WHITE);
		titleLayout.addView(trackControl);

		// Track Author
		LinearLayout authorLayout = new LinearLayout(context);
		authorLayout.setOrientation(LinearLayout.HORIZONTAL);
		authorLayout.setGravity(Gravity.FILL);

		authorControl = new TextView(context);
		authorControl.setText(track.getUser().getUsername());
		authorControl.setTextColor(Color.WHITE);
		authorLayout.addView(authorControl);

		// Waveform image
		LinearLayout waveformLayout = new LinearLayout(context);
		waveformLayout.setOrientation(LinearLayout.HORIZONTAL);
		waveformLayout.setGravity(Gravity.CENTER);

		waveformImg = new ImageView(context);
		waveformImg.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
		waveformImg.setEnabled(true);
		waveformImg.setAdjustViewBounds(true);
		Bitmap bitmap = (Bitmap)(new DownloadImageTask()).doInBackground(track.getWaveform_url());
		// waveformImg.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 310, 50,
		// true));
		waveformImg.setImageBitmap(bitmap);
		waveformLayout.addView(waveformImg);

		// Play Button
		LinearLayout playLayout = new LinearLayout(context);
		playLayout.setOrientation(LinearLayout.HORIZONTAL);
		playLayout.setGravity(Gravity.CENTER);

		playButton = new Button(context);
		playButton.setVisibility(View.VISIBLE);
		playButton.setText("PLAY");
		playButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (player == null) {
					player = new PlayerTask();
				}
				player.doInBackground(track, context);
			}

		});
		playLayout.addView(playButton);

		addView(titleLayout);
		addView(authorLayout);
		addView(waveformLayout);
		addView(playLayout);

	}

	private class PlayerTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... args) {
			Track track = (Track) args[0];
			final Context context = (Context) args[1];

			if (mp == null) {
				mp = new MediaPlayer();
			}
			if (mp.isPlaying()) {
				mp.stop();
				mp.reset();
				playButton.setText("PLAY");
			} else {
				try {
					mp.setDataSource(track.getStream_url());
					playButton.setText("STOP");
        		 	mp.prepare();									
					mp.start();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}
	}
	
	private class DownloadImageTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... args) {			
			Bitmap bmImg = null;
			String imageUrl = (String) args[0];				
			bmImg = BitmapUtils.loadBitmap(imageUrl);	
			return bmImg;
		}
	}
}
