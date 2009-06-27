package org.soundroid.activities;



import org.soundroid.activities.abstracts.AbstractActivity;
import org.soundroid.activities.main.SoundroidActivity;
import org.soundroid.models.Users;
import org.soundroid.oauth.Response;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
//Need to study how to call an activity from other
public class ContactsActivity extends AbstractActivity{
	public static final String ACTION_ABOUT = "org.soundroid.CONTACTS";
	public static final Intent INTENT = new Intent(ACTION_ABOUT);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Response<Users> response = SoundroidActivity.client.getContacts();
		@SuppressWarnings("unused")
		Users users = response.getData();
		
		ScrollView scroll = new ScrollView(this);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		scroll.addView(layout);		
		
		setContentView(scroll);
	}

}
