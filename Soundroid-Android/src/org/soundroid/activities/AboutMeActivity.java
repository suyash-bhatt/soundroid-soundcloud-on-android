package org.soundroid.activities;

import org.soundroid.oauth.Response;
import org.soundroid.xml.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
//Need to study how to call an activity from other
public class AboutMeActivity extends AbstractActivity{
	public static final String ACTION_ABOUT = "org.soundroid.ABOUT";
	public static final Intent INTENT = new Intent(ACTION_ABOUT);
	
	private TextView cityField;
	private TextView descriptionField;
	private TextView discogsNameField;
	private TextView idField;
	private TextView myspaceNameField;
	private TextView permalinkField;
	private TextView usernameField;
	private TextView websiteField;
	private TextView websiteTitleField;
	private TextView fullNameField;
	private TextView countryField;
	private TextView onlineField;
	private TextView permalinkUrlField;
	private TextView avatarUrlField;
	private TextView uriField;
	private TextView trackCountField;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Response<User> response = SoundroidActivity.client.getInfoAboutMe();
		User user = response.getData();
		
		ScrollView scroll = new ScrollView(this);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		scroll.addView(layout);		
		
		fullNameField = new TextView(this);
		fullNameField.setText(user.getFullName());
		
		usernameField = new TextView(this);
		usernameField.setText(user.getUsername());
		
		cityField = new TextView(this);
		cityField.setText(user.getCity());
		
		trackCountField = new TextView(this);
		trackCountField.setText(user.getTrackCount());
		
		avatarUrlField = new TextView(this);
		avatarUrlField.setText(user.getAvatarUrl());
		
		onlineField = new TextView(this);
		onlineField.setText(user.getOnline());
		
		countryField = new TextView(this);
		countryField.setText(user.getCountry());
		
		descriptionField = new TextView(this);
		descriptionField.setText(user.getDescription());
		
		discogsNameField = new TextView(this);
		discogsNameField.setText(user.getDiscogsName());
		
		myspaceNameField = new TextView(this);
		myspaceNameField.setText(user.getMyspaceName());
			
		permalinkField = new TextView(this);
		permalinkField.setText(user.getPermalink());
				
		websiteField = new TextView(this);
		websiteField.setText(user.getWebsite());
		
		websiteTitleField = new TextView(this);
		websiteTitleField.setText(user.getWebsiteTitle());
				
		permalinkUrlField = new TextView(this);
		permalinkUrlField.setText(user.getPermalinkUrl());		
		
		uriField = new TextView(this);
		uriField.setText(user.getUri());
			
		
		layout.addView(fullNameField);
		layout.addView(usernameField);
		layout.addView(cityField);
		layout.addView(trackCountField);
		layout.addView(avatarUrlField);		
		layout.addView(onlineField);
		layout.addView(countryField);
		layout.addView(descriptionField);
		layout.addView(discogsNameField);
		layout.addView(myspaceNameField);
		layout.addView(permalinkField);
		layout.addView(websiteField);
		layout.addView(websiteTitleField);
		layout.addView(permalinkUrlField);
		layout.addView(uriField);		
		
		setContentView(scroll);
	}

}
