package org.soundroid.activities.you;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class YouMenuActivity extends Activity {
	public static final String ACTION_YOU = "org.soundroid.YOU";
	public static final Intent INTENT = new Intent(ACTION_YOU);
	
	private LinearLayout layout;
	
	private Button btnTracks;
	private Button btnSets;
	private Button btnComments;
	private Button btnFavorites;
	private Button btnGroups;
	private Button btnProfile;
	
	public YouMenuActivity() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		addTracksMenu();
		addSetsMenu();
		addCommentsMenu();
		addFavoritesMenu();
		addGroupsMenu();
		addPublicProfileMenu();
		
		setContentView(layout);
	}
	
	private void addTracksMenu(){
		btnTracks = new Button(this);
		btnTracks.setText("Tracks");
		btnTracks.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {		    	
				YouMenuActivity.this.startActivity(TracksActivity.INTENT);
			}

		});
		
		layout.addView(btnTracks);
		btnTracks.setVisibility(View.VISIBLE);
	}
	
	private void addSetsMenu(){
		
	}
	
	private void addCommentsMenu(){
		
	}
	
	private void addFavoritesMenu(){
		
	}
	
	private void addGroupsMenu(){
	
	}
	
	private void addPublicProfileMenu(){
		btnProfile = new Button(this);
		btnProfile.setText("Me");
		btnProfile.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {		    	
				YouMenuActivity.this.startActivity(AboutMeActivity.INTENT);
			}
	
		});
		
		layout.addView(btnProfile);
		btnProfile.setVisibility(View.VISIBLE);
	}
	
//	private void addMeButton() {
//	btnMe = new Button(this);
//	btnMe.setText("Me");
//	btnMe.setVisibility(View.GONE);
//	btnMe.setOnClickListener(new OnClickListener() {
//
//		public void onClick(View v) {		    	
//			SoundroidActivity.this.startActivity(AboutMeActivity.INTENT);
//		}
//
//	});
//
//}
//	private void addCommentsButton() {
//	btnComments = new Button(this);
//	btnComments.setText("Comments");
//	btnComments.setVisibility(View.GONE);
//	btnComments.setOnClickListener(new OnClickListener() {
//
//		public void onClick(View v) {
//			Response<Comments> response = client.getComments();
//			Comments comments = response.getData();
//
//			for (Comment c : comments.getComments()) {
//				String duration = c.getTimestamp();
//			}
//			
//		}
//
//	});
//}
//
//private void addContactsButton() {
//	btnContacts = new Button(this);
//	btnContacts.setText("Contacts");
//	btnContacts.setVisibility(View.GONE);
//	btnContacts.setOnClickListener(new OnClickListener() {
//
//		public void onClick(View v) {
//			
//			SoundroidActivity.this.startActivity(ContactsActivity.INTENT);			
//		}
//
//	});
//}

}
