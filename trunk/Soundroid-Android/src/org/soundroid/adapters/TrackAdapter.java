package org.soundroid.adapters;

import java.util.List;

import org.soundroid.adapters.views.TrackAdapterView;
import org.soundroid.models.Track;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class TrackAdapter extends BaseAdapter {

	private Context context;
	private List<Track> trackList;

	public TrackAdapter(Context context, List<Track> trackList) {
		this.context = context;
		this.trackList = trackList;
	}

	public int getCount() {
		return trackList.size();
	}

	public Object getItem(int position) {
		return trackList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Track track = trackList.get(position);
		return new TrackAdapterView(this.context, track);
	}
}
