package org.soundroid.xml.models;

import java.util.ArrayList;

public class Events {
	public static final String ALL = "all";
	public static final String COMMENT  = "comment";
	public static final String TRACK  = "track";
	public static final String SHARED_TO = "shared_to";
	public static final String DROP  = "drop";
	public static final String FAVORITE ="favorite";
	public static final String FAN = "fan";
	
	private ArrayList<Event> events = new ArrayList<Event>();

	public Events() {
		super();
	}

	public ArrayList<Event> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}
}
