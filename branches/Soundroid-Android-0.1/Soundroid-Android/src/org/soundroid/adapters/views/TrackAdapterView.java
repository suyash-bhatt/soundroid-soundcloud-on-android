package org.soundroid.adapters.views;

import java.io.IOException;

import org.soundroid.models.Track;
import org.soundroid.util.BitmapUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * 
 * TODO: 
 * 	+ Improve behaviour when the user plays several tracks 
 * 	+ Response is not so good as desiderable 
 * 	+ UI is slow 
 * 	+ Show song length and position, pause and similar
 * 
 * 	+ The play time is not updated. See what it is happens with it
 * 
 * @author Antonio Hinojo
 * 
 */
public class TrackAdapterView extends LinearLayout {

	private PlayerTask playerTask;
	private MediaPlayer mp;
	private TextView trackControl;
	private TextView genreControl;
	private TextView bpmControl;
	private ImageView waveformImg;
	private Button playButton;
	private SeekBar seekBar;
	private TextView playedtime;
	private Track track;

	public TrackAdapterView(final Context context, final Track track) {
		super(context);	
		this.mp = new MediaPlayer();
		
		mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		mp.setOnCompletionListener(new OnCompletionListener(){
			@Override
            public void onCompletion(MediaPlayer arg0) {
            	mp.stop();
            }
       });
		
		this.track = track;
		setOrientation(VERTICAL);

		addTrackTitle();

		addTrackStyle();

		addWaveform();

		addPlayButton();
		
		addSeekBar();			
	}

	private void addTrackTitle(){
		// Track Title
		LinearLayout titleLayout = new LinearLayout(getContext());
		titleLayout.setOrientation(LinearLayout.HORIZONTAL);
		titleLayout.setGravity(Gravity.FILL);

		trackControl = new TextView(getContext());
		trackControl.setText(track.getTitle());
		trackControl.setTextColor(Color.WHITE);
		titleLayout.addView(trackControl);
		addView(titleLayout);

	}

	private void addTrackStyle(){
		// Track Author
		LinearLayout styleLayout = new LinearLayout(getContext());
		styleLayout.setOrientation(LinearLayout.HORIZONTAL);
		styleLayout.setGravity(Gravity.FILL);

		genreControl = new TextView(getContext());
		genreControl.setText(track.getGenre() + "  ");
		genreControl.setTextColor(Color.WHITE);
		
		styleLayout.addView(genreControl);
		
		if(track.getBpm() != null){
			bpmControl = new TextView(getContext());
			bpmControl.setText(track.getBpm() + " BPM");
			bpmControl.setTextColor(Color.WHITE);
			
			styleLayout.addView(bpmControl);
		}
		
		addView(styleLayout);
	}

	private void addWaveform(){
		// Waveform image
		LinearLayout waveformLayout = new LinearLayout(getContext());
		waveformLayout.setOrientation(LinearLayout.HORIZONTAL);
		waveformLayout.setGravity(Gravity.CENTER);

		waveformImg = new ImageView(getContext());
		// waveformImg.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
		waveformImg.setEnabled(true);
		waveformImg.setAdjustViewBounds(true);
		Bitmap bitmap = (Bitmap) (new DownloadImageTask()).doInBackground(track.getWaveform_url());
		// waveformImg.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 310, 50,
		// true));
		waveformImg.setImageBitmap(bitmap);
		waveformLayout.addView(waveformImg);
		addView(waveformLayout);
	}

	private void addPlayButton(){
		// Play Button
		LinearLayout playLayout = new LinearLayout(getContext());
		playLayout.setOrientation(LinearLayout.HORIZONTAL);
		playLayout.setGravity(Gravity.CENTER);

		playButton = new Button(getContext());
		playButton.setVisibility(View.VISIBLE);
		playButton.setText("PLAY");
		playButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (playerTask == null) {
					playerTask = new PlayerTask();
				}
				playerTask.doInBackground(track);
			}
		});
		playLayout.addView(playButton);
		addView(playLayout);
	}

	private void addSeekBar(){
		// SeekBar
		LinearLayout seekBarLayout = new LinearLayout(getContext());
		seekBarLayout.setOrientation(LinearLayout.VERTICAL);
		seekBarLayout.setGravity(Gravity.FILL);

		seekBar = new SeekBar(getContext());
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
				// TODO Auto-generated method stub
				if (fromTouch && mp.isPlaying()) {
					seek(progress);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
		});
				
		seekBarLayout.addView(seekBar);
		
		LinearLayout playedtimeLayout = new LinearLayout(getContext());
		playedtimeLayout.setOrientation(LinearLayout.HORIZONTAL);
		playedtimeLayout.setGravity(Gravity.LEFT);
		playedtime = new TextView(getContext());
		
		playedtimeLayout.addView(playedtime);
		
		seekBarLayout.addView(playedtimeLayout);
		
		addView(seekBarLayout);
		
		updatePlayedTime();
	
	}

	protected void seek(int progress) {
		float prg_float = progress;
		float totmsec = (float) mp.getDuration();
		int msec = (int) (totmsec * (prg_float / 1000));
		mp.seekTo(msec);
	}

	public void updatePlayedTime() {
		int played = 0;
		int total = 0;
		int playedsec = 0;
		int totalsec = 0;
		int playedmin = 0;
		int totalmin = 0;

		if (mp == null) {
			mp = new MediaPlayer();
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
		}
				
		if(mp.isPlaying()){
			played = mp.getCurrentPosition() < 0? 0 : mp.getCurrentPosition(); // the current position in ms
			total = mp.getDuration(); // total length in ms
			playedsec = played / 1000; // time in seconds
			totalsec = total / 1000;

			playedmin = playedsec / 60; // minutes played
			playedsec = playedsec - (playedmin * 60); // seconds played

			totalmin = totalsec / 60; // minutes total
			totalsec = totalsec - (totalmin * 60);// seconds total (sort of)

			float progresspromille = ((float) played / (float) total) * 1000;

			int progress = (int) progresspromille;
		
			seekBar.setProgress(progress);

			String text = null;
			text = String.format("%02d:%02d / %02d:%02d", playedmin, playedsec,	totalmin, totalsec);
			playedtime.setText(text);
		}else{
			playedtime.setText(String.format("%02d:%02d / %02d:%02d", 0, 0,	0, 0));
		}	
	}

	private class PlayerTask extends AsyncTask<Track,Track,Track> {

		protected Track doInBackground(Track... args) {
			Track track = args[0];
			
			if (mp == null) {
				mp = new MediaPlayer();
			}
			
			if (mp.isPlaying()) {
				mp.stop();
				mp.reset();
				playButton.setText("PLAY");
				seekBar.setProgress(0);
			}else {
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
			
			return track;
		}
	}
	
	private class RefreshCounter extends AsyncTask<MediaPlayer, Void, Void> {

		@Override
		protected Void doInBackground(MediaPlayer... args) {			
			
			updatePlayedTime();
						
			return null;
		}
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... args) {
			Bitmap bmImg = null;
			String imageUrl = args[0];
			bmImg = BitmapUtils.loadBitmap(imageUrl);
			return bmImg;
		}
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}
}
