package org.soundroid.adapters.views;

import org.soundroid.models.Track;
import org.soundroid.util.BitmapUtils;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TrackAdapterView extends LinearLayout {
	
	public TrackAdapterView(Context context, Track track) {
		super(context);

		this.setOrientation(HORIZONTAL);

		// Track Title
		LinearLayout.LayoutParams trackParams = new LinearLayout.LayoutParams(100, LayoutParams.WRAP_CONTENT);
		trackParams.setMargins(1, 1, 1, 1);
		TextView trackControl = new TextView(context);
		trackControl.setText(track.getTitle());
		trackControl.setTextSize(14f);
		trackControl.setTextColor(Color.WHITE);
		addView(trackControl, trackParams);

		// Track Author
		LinearLayout.LayoutParams temperatureParams = new LinearLayout.LayoutParams(20, LayoutParams.WRAP_CONTENT);
		temperatureParams.setMargins(1, 1, 1, 1);
		TextView authorControl = new TextView(context);
		authorControl.setText(track.getUser().getUsername());
		authorControl.setTextSize(14f);
		authorControl.setTextColor(Color.WHITE);
		addView(authorControl, temperatureParams);

		// Play button (For now is shown the user avatar)
		LinearLayout.LayoutParams skyParams = new LinearLayout.LayoutParams(25, LayoutParams.WRAP_CONTENT);
		ImageView playButton = new ImageView(context);
		playButton.setImageBitmap(BitmapUtils.loadBitmap(track.getUser().getAvatar_url()));
		addView(playButton, skyParams);
	}
}


