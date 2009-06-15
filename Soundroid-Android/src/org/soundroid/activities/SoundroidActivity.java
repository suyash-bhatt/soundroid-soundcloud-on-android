package org.soundroid.activities;

import org.soundroid.application.SoundroidApplication;
import org.soundroid.client.SoundcloudClient;
import org.soundroid.oauth.Response;
import org.soundroid.services.BackgroundService;
import org.soundroid.util.ClientSettings;
import org.soundroid.util.Preferences;
import org.soundroid.util.Util;
import org.soundroid.xml.models.Comment;
import org.soundroid.xml.models.Comments;
import org.soundroid.xml.models.Track;
import org.soundroid.xml.models.Tracks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class SoundroidActivity extends AbstractActivity {
	static final int ID_SETTINGS = Menu.FIRST;
	static final int ID_ABOUT = Menu.FIRST + 1;

	public static SoundcloudClient client;
	private ClientSettings settings;
	private Button btnMe;
	private Button btnTracks;
	private Button btnComments;
	private Button btnContacts;

	public SoundroidActivity() {
		super();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		super.onCreateOptionsMenu(menu);

		MenuItem settings = menu.add(Menu.NONE, ID_SETTINGS, Menu.NONE,
				"Settings");
		settings.setIcon(android.R.drawable.ic_menu_preferences);
		settings.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			public boolean onMenuItemClick(MenuItem item) {
				//Intent i = new Intent();
				// i.setClass(getApplicationContext(),
				// EditPreferencesActivity.class);
				//SoundroidActivity.this.startActivity(i);
				return true;
			}

		});

		MenuItem about = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "About");
		about.setIcon(android.R.drawable.ic_menu_info_details);
		about.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			public boolean onMenuItemClick(MenuItem item) {
				// SoundroidActivity.this.startActivity(AboutActivity.INTENT);

				return true;
			}

		});

		return true;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		client = SoundroidApplication.createSoundcloudClient();

		settings = client.getClientSettings();

		addMeButton();
		addTracksButton();
		addCommentsButton();
		addContactsButton();

		if (Util.isCallback(this.getIntent())) {

			client.setRequestToken(Preferences.getRequestToken());

			fetchAccessToken();

			BackgroundService.restart(this);

		} else if (!settings.getUserSpecificToken().isValid()) {
			final AlertDialog alert = new AlertDialog.Builder(this).create();
			alert.setMessage("To get started, you will need to login to Soundcloud");
			alert.setButton("Login", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int whichButton) {
					alert.dismiss();

					final String url = client.getUserAuthorizationUrl();

					Log.i(this.getClass().getName(), url);

					Preferences.updateRequestToken(client.getRequestToken());
					Preferences.commit();

					//openWebBrowser(url);
					openWebBrowser("soundroid-app://callback");

					//SoundroidActivity.this.finish();

				}
			});
			alert.show();

		}

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		if (settings.getUserSpecificToken().isValid()) {

			layout.addView(btnMe);
            layout.addView(btnTracks);
            layout.addView(btnComments);
            layout.addView(btnContacts);
            
			btnMe.setVisibility(View.VISIBLE);
			btnTracks.setVisibility(View.VISIBLE);
			btnComments.setVisibility(View.VISIBLE);
			btnContacts.setVisibility(View.VISIBLE);
		}

		setContentView(layout);
	}

	private void addMeButton() {
		btnMe = new Button(this);
		btnMe.setText("Me");
		btnMe.setVisibility(View.GONE);
		btnMe.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
//				Response<User> response = client.getInfoAboutMe();
//				User user = response.getData();	
				//AboutMeActivity am = new AboutMeActivity(user);
		    	
				SoundroidActivity.this.startActivity(AboutMeActivity.INTENT);
				
				//setContentView(R.layout.aboutmeactivity);
			//	((LinearLayout) findViewById(R.id.android_aboutMeLayout)).addView(dotView, 0);

				
//				Intent i = new Intent(getApplicationContext(),	AboutMeActivity.class);
//				SoundroidActivity.this.startActivity(i);
				
				//startActivity(new Intent(SoundroidActivity.this, AboutMeActivity.class));

				
//				Runnable runner = new Runnable() {
//					public void run() {
//						try {
//							Response<User> response = client.getInfoAboutMe();
//							User user = response.getData();					    	
//					    	
//							setContentView(R.layout.activity_list_item);
//
//
//						} catch (Throwable t) {
//							t.printStackTrace(); // todo : improve this
//						}
//					}
//				};
//				startThread("about me thread", runner);
			}

		});

	}

	private void addTracksButton() {
		btnTracks = new Button(this);
		btnTracks.setText("Tracks");
		btnTracks.setVisibility(View.GONE);
		btnTracks.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Runnable runner = new Runnable() {
					public void run() {
						try {
							Response<Tracks> response = client.getTracks();
							Tracks tracks = response.getData();

							for (Track e : tracks.getTracks()) {
								String duration = e.getBpm();
							}
						} catch (Throwable t) {
							t.printStackTrace(); // todo : improve this
						}
					}
				};
				startThread("tracks thread", runner);
			}

		});
	}

	private void addCommentsButton() {
		btnComments = new Button(this);
		btnComments.setText("Comments");
		btnComments.setVisibility(View.GONE);
		btnComments.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Response<Comments> response = client.getComments();
				Comments comments = response.getData();

				for (Comment c : comments.getComments()) {
					String duration = c.getTimestamp();
				}
				
			}

		});
	}
	
	private void addContactsButton() {
		btnContacts = new Button(this);
		btnContacts.setText("Contacts");
		btnContacts.setVisibility(View.GONE);
		btnContacts.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				SoundroidActivity.this.startActivity(ContactsActivity.INTENT);
			
			}

		});
	}

	protected void fetchAccessToken() {

		client.fetchAccessToken();

		Preferences.updateUserSpecificAccessToken(client.getClientSettings()
				.getUserSpecificToken());
		Preferences.commit();

	}

	protected void onDestroy() {
		super.onDestroy();

		if (this.client != null) {
			this.client.shutdown();
		}
	}

}
