package org.soundroid.activities;

import org.soundroid.xml.models.User;

import android.os.Bundle;
import android.widget.EditText;
//Need to study how to call an activity from other
public class AboutMeActivity extends AbstractActivity{
	private EditText usernameField;
	private EditText cityField;
	private EditText countryField;
	private User user;
	
	public AboutMeActivity(User user){
		this.user = user;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
//	private LinearLayout root;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//        LinearLayout.LayoutParams containerParams
//            = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.FILL_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                0.0F);
//
//        LinearLayout.LayoutParams widgetParams
//            = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.FILL_PARENT,
//                ViewGroup.LayoutParams.FILL_PARENT,
//                1.0F);
//
//        root = new LinearLayout(this);
//        root.setOrientation(LinearLayout.VERTICAL);
//        root.setBackgroundColor(Color.LTGRAY);
//        root.setLayoutParams(containerParams);
//
//        LinearLayout ll = new LinearLayout(this);
//        ll.setOrientation(LinearLayout.HORIZONTAL);
//        ll.setBackgroundColor(Color.GRAY);
//        ll.setLayoutParams(containerParams);
//        root.addView(ll);
//
//        EditText tb = new EditText(this);
//        tb.setText("");
//        tb.setFocusable(false);
//        tb.setLayoutParams(widgetParams);
//        ll.addView(tb);
//
//        tb = new EditText(this);
//        tb.setText("");
//        tb.setFocusable(false);
//        tb.setLayoutParams(widgetParams);
//        ll.addView(tb);
//
//        ll = new LinearLayout(this);
//        ll.setOrientation(LinearLayout.HORIZONTAL);
//        ll.setBackgroundColor(Color.DKGRAY);
//        ll.setLayoutParams(containerParams);
//        root.addView(ll);
//
//        Button b = new Button(this);
//        b.setText("");
//        b.setTextColor(Color.RED);
//        b.setLayoutParams(widgetParams);
//        ll.addView(b);
//
//        b = new Button(this);
//        b.setText("");
//        b.setTextColor(Color.GREEN);
//        b.setLayoutParams(widgetParams);
//        ll.addView(b);
//
//        setContentView(root);
//
//	}

}
