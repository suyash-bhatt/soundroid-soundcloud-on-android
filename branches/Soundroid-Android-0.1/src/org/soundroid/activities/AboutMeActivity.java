package org.soundroid.activities;


import org.soundroid.models.User;
import org.soundroid.oauth.Response;
import org.soundroid.util.BitmapUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * TODO Add more information on the screen, like fans and contacts. In general all the info related with the user.
 * 
 * @author Antonio Hinojo
 *
 */
public class AboutMeActivity extends AbstractActivity {
	public static final String ACTION_ABOUT = "org.soundroid.ABOUT";
	public static final Intent INTENT = new Intent(ACTION_ABOUT);

	private LinearLayout mainLayout;
	private ScrollView scroll;
	private ImageView iv;

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

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Response<User> response = SoundroidActivity.client.getInfoAboutMe();
		User user = response.getData();

		scroll = new ScrollView(this);
		
		mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
	
		Bitmap bitMap = BitmapUtils.loadBitmap(user.getAvatar_url());
		
		if(bitMap != null){
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.CENTER);
			
			iv = new ImageView(this);
			iv.setEnabled(true);
			iv.setImageBitmap(bitMap);
			layout.addView(iv);		
			
			mainLayout.addView(layout);
		}
				

		if(user.getFullName() != null){
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.TOP);
			
			TextView fullnameLabel = new TextView(this);
			fullnameLabel.setText("Full Name");
			
			fullNameField = new TextView(this);
			fullNameField.setText(user.getFullName());
			
			layout.addView(fullnameLabel);
			layout.addView(fullNameField);
			
			mainLayout.addView(layout);
		}
		
		if(user.getUsername() != null){
			
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.TOP);
			
			TextView usernameLabel = new TextView(this);
			usernameLabel.setText("User Name: ");
			
			usernameField = new TextView(this);
			usernameField.setText(user.getUsername());
			
			layout.addView(usernameLabel);
			layout.addView(usernameField);
			
			mainLayout.addView(layout);
		}
		
		if(user.getCity() != null){
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.TOP);
			
			TextView cityLabel = new TextView(this);
			cityLabel.setText("City: ");
			
			cityField = new TextView(this);
			cityField.setText(user.getCity());
			
			layout.addView(cityLabel);
			layout.addView(cityField);
			
			mainLayout.addView(layout);		
		}
		
		if(user.getTrack_count() != null){
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.TOP);
			
			TextView trackCountLabel = new TextView(this);
			trackCountLabel.setText("Tracks: ");
			
			trackCountField = new TextView(this);
			trackCountField.setText(user.getTrack_count());
			
			layout.addView(trackCountLabel);
			layout.addView(trackCountField);
			
			mainLayout.addView(layout);		
		}
		
		if(user.getOnline() != null){
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.TOP);
			
			TextView onlineLabelLabel = new TextView(this);
			onlineLabelLabel.setText("Online: ");
			
			onlineField = new TextView(this);
			onlineField.setText(user.getOnline());
			
			layout.addView(onlineLabelLabel);
			layout.addView(onlineField);
			
			mainLayout.addView(layout);		
		}
		
		if(user.getCountry() != null){
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.TOP);
			
			TextView countryLabel = new TextView(this);
			countryLabel.setText("Country: ");
			
			countryField = new TextView(this);
			countryField.setText(user.getCountry());
			
			layout.addView(countryLabel);
			layout.addView(countryField);
			
			mainLayout.addView(layout);		
		}		

		if(user.getDescription() != null){
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.TOP);
			
			TextView descriptionLabel = new TextView(this);
			descriptionLabel.setText("Description: ");
			
			descriptionField = new TextView(this);
			descriptionField.setText(user.getDescription());
			
			layout.addView(descriptionLabel);
			layout.addView(descriptionField);
			
			mainLayout.addView(layout);		
		}
		
		if(user.getDiscogs_name() != null){
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.TOP);
			
			TextView discographyLabel = new TextView(this);
			discographyLabel.setText("Discography: ");
			
			discogsNameField = new TextView(this);
			discogsNameField.setText(user.getDiscogs_name());
			
			layout.addView(discographyLabel);
			layout.addView(discogsNameField);
			
			mainLayout.addView(layout);		
		}

		if(user.getMyspace_name() != null){
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.TOP);
			
			TextView mySpaceLabel = new TextView(this);
			mySpaceLabel.setText("My Space: ");
			
			myspaceNameField = new TextView(this);
			myspaceNameField.setText(user.getMyspace_name());
			
			layout.addView(mySpaceLabel);
			layout.addView(myspaceNameField);
			
			mainLayout.addView(layout);		
		}	

		if(user.getPermalink() != null){
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.TOP);
			
			TextView permalinkLabel = new TextView(this);
			permalinkLabel.setText("Permalink: ");
			
			permalinkField = new TextView(this);
			permalinkField.setText(user.getPermalink());
			
			layout.addView(permalinkLabel);
			layout.addView(permalinkField);
			
			mainLayout.addView(layout);		
		}
		

		if(user.getWebsite() != null){
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.TOP);
			
			TextView webSiteLabel = new TextView(this);
			webSiteLabel.setText("Web: ");
			
			websiteField = new TextView(this);
			websiteField.setText(user.getWebsite());
			
			layout.addView(webSiteLabel);
			layout.addView(websiteField);
			
			mainLayout.addView(layout);		
		}
		

//		websiteTitleField = new TextView(this);
//		websiteTitleField.setText(user.getWebsite_title());
//
//		permalinkUrlField = new TextView(this);
//		permalinkUrlField.setText(user.getPermalink_url());
//
//		uriField = new TextView(this);
//		uriField.setText(user.getUri());

	
//		layout.addView(cityField);
//		layout.addView(trackCountField);
//		layout.addView(avatarUrlField);
//		layout.addView(onlineField);
//		layout.addView(countryField);
//		layout.addView(descriptionField);
//		layout.addView(discogsNameField);
//		layout.addView(myspaceNameField);
//		layout.addView(permalinkField);
//		layout.addView(websiteField);
//		layout.addView(websiteTitleField);
//		layout.addView(permalinkUrlField);
//		layout.addView(uriField);

		
		
//		DownloadImageTask dit = new DownloadImageTask();
//		try {
//			Bitmap bmImg = (Bitmap) dit.execute(avatarUrlField).get();
//			iv.setImageBitmap(bmImg);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		layout.addView(iv);

		scroll.addView(mainLayout);
		setContentView(scroll);
		
	}
	
	private class DownloadImageTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... args) {
			
			Bitmap bmImg = null;
			TextView tv = (TextView) args[0];
					
			
			bmImg = BitmapUtils.loadBitmap((String) tv.getText());
	
			return bmImg;
		}
	}
}
