package org.soundroid.xml.models;

import java.util.ArrayList;

public class Users {

	private ArrayList<User> users = new ArrayList<User>();

	public Users() {
		super();
	}

	public ArrayList<User> getTracks() {
		return users;
	}

	public void setTracks(ArrayList<User> users) {
		this.users = users;
	}
}
