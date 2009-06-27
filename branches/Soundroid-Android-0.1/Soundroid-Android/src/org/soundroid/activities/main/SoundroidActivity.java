package org.soundroid.activities.main;

import org.soundroid.activities.abstracts.AbstractActivity;
import org.soundroid.activities.you.AboutMeActivity;
import org.soundroid.activities.you.TracksActivity;
import org.soundroid.activities.you.YouMenuActivity;
import org.soundroid.application.SoundroidApplication;
import org.soundroid.client.SoundcloudClientJSON;
import org.soundroid.database.DbAdapter;
import org.soundroid.lib.util.ClientSettings;
import org.soundroid.services.BackgroundService;
import org.soundroid.util.Preferences;
import org.soundroid.util.Util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
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

	public static SoundcloudClientJSON client;
	private ClientSettings settings;
	
	private Button btnDashboard;
	private Button btnYou;
	private Button btnTracks;
	private Button btnPeople;
	private Button btnGroups;
	private Button btnUpload;
	
	private DbAdapter mDbHelper;

	public SoundroidActivity() {
		super();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		super.onCreateOptionsMenu(menu);

		MenuItem settings = menu.add(Menu.NONE, ID_SETTINGS, Menu.NONE, "Settings");
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
		//setContentView(R.layout.soundroidactivity);
		
		mDbHelper = new DbAdapter(this);
        mDbHelper.open();
        	
		client = SoundroidApplication.createSoundcloudClient();

		settings = client.getClientSettings();
		
		addDashboard();
		addYou();
		addTracks();
		addPeople();
		addGroups();
		addUpload();

		if (Util.isCallback(this.getIntent())) {

			client.setRequestToken(Preferences.getRequestToken());
			
			  Cursor cursor = mDbHelper.fetchAllNotes();
			  
			  
			  //If there is no token we need to request one
		        if(cursor.getCount() == 0){
		        	fetchAccessToken();
		        	mDbHelper.createToken(client.getToken().getSecret(), "");
		        }else{	        	
		        	cursor.moveToFirst();
		        	String savedKey = cursor.getString(1);
		        	client.getToken().setSecret(savedKey);
		        }	
			
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

					openWebBrowser(url);
					//openWebBrowser("soundroid-app://callback");

					//SoundroidActivity.this.finish();

				}
			});
			alert.show();

		}

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		if (settings.getUserSpecificToken().isValid()) {

			layout.addView(btnDashboard);
			layout.addView(btnYou);
			layout.addView(btnTracks);
			layout.addView(btnPeople);
			layout.addView(btnGroups);
			layout.addView(btnUpload);
            
            btnDashboard.setVisibility(View.VISIBLE);
            btnYou.setVisibility(View.VISIBLE);
            btnTracks.setVisibility(View.VISIBLE);
            btnPeople.setVisibility(View.VISIBLE);
            btnGroups.setVisibility(View.VISIBLE);            
            btnUpload.setVisibility(View.VISIBLE);
		}

		setContentView(layout);
	}
	
	private void addDashboard(){
		btnDashboard = new Button(this);
		btnDashboard.setText("Dashboard");
		btnDashboard.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {		    	
				SoundroidActivity.this.startActivity(AboutMeActivity.INTENT);
			}

		});
	}

	private void addYou(){
		btnYou = new Button(this);
		btnYou.setText("You");
		btnYou.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {		    	
				SoundroidActivity.this.startActivity(YouMenuActivity.INTENT);
			}

		});
	}
	
	private void addTracks(){
		btnTracks = new Button(this);
		btnTracks.setText("Tracks");
		btnTracks.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {		    	
				SoundroidActivity.this.startActivity(AboutMeActivity.INTENT);
			}

		});
	}
	
	private void addPeople(){
		btnPeople = new Button(this);
		btnPeople.setText("People");
		btnPeople.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {		    	
				SoundroidActivity.this.startActivity(AboutMeActivity.INTENT);
			}

		});
	}
	
	private void addGroups(){
		btnGroups = new Button(this);
		btnGroups.setText("Groups");
		btnGroups.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {		    	
				SoundroidActivity.this.startActivity(AboutMeActivity.INTENT);
			}

		});
	}
	
	private void addUpload(){
		btnUpload = new Button(this);
		btnUpload.setText("Upload");
		btnUpload.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {		    	
				SoundroidActivity.this.startActivity(AboutMeActivity.INTENT);
			}

		});
	}

	private void addTracksButton() {
		btnTracks = new Button(this);
		btnTracks.setText("Tracks");
		btnTracks.setVisibility(View.GONE);
		btnTracks.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {				
				SoundroidActivity.this.startActivity(TracksActivity.INTENT);
			}

		});
	}

	protected void fetchAccessToken() {

		client.fetchAccessToken();

		Preferences.updateUserSpecificAccessToken(client.getClientSettings().getUserSpecificToken());
		Preferences.commit();

	}

	protected void onDestroy() {
		super.onDestroy();

		if (client != null) {
			client.shutdown();
		}
	}

}
