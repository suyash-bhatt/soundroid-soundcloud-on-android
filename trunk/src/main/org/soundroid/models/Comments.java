package org.soundroid.models;

import java.util.ArrayList;

public class Comments {
	private ArrayList<Comment> comments = new ArrayList<Comment>();

	public Comments() {
		super();
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
}
