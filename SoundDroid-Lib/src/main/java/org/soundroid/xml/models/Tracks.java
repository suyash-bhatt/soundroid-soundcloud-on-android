package org.soundroid.xml.models;

import java.util.ArrayList;

public class Tracks {

	private ArrayList<Track> tracks = new ArrayList<Track>();
	
	public Tracks(){
		super();
	}

	public ArrayList<Track> getTracks() {
		return tracks;
	}

	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}
}
