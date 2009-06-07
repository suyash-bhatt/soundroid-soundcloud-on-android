package org.soundroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
	private static SharedPreferences prefs;
	private static SharedPreferences.Editor editor;

	private final static String TRANSMIT_FREQUENCY = "fireeagle.transmit.frequency.minutes";

	private final static String REQUEST_TOKEN_NAME = "request";
	private final static String USER_SPECIFIC_ACCESS_TOKEN_NAME = "user-specific-access";
	private final static String GENERAL_ACCESS_TOKEN_NAME = "general-access";
	private final static String TOKEN_PREFIX = "oauth.token";
	private final static String TOKEN_PUBLIC_KEY = "public";
	private final static String TOKEN_SECRET = "secret";

	public static void reload(Context ctx) {
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		editor = prefs.edit();
	}

	private static Token getToken(String name) {
		Token t = new Token();

		t.setConsumerKey((prefs.getString(TOKEN_PREFIX + "." + name + "." + TOKEN_PUBLIC_KEY, null)));
		t.setConsumerSecret(prefs.getString(TOKEN_PREFIX + "." + name + "." + TOKEN_SECRET, null));

		System.out.println("got yer Token: " + name + " --- " + t.toString());

		return t;
	}

	private static void updateToken(String name, Token t) {
		editor.putString(TOKEN_PREFIX + "." + name + "." + TOKEN_PUBLIC_KEY, t.getConsumerKey());
		editor.putString(TOKEN_PREFIX + "." + name + "." + TOKEN_SECRET, t.getConsumerSecret());

		System.out.println("updated Token: " + name + " --- " + t.toString());
	}

	static public Token getRequestToken() {
		return getToken(REQUEST_TOKEN_NAME);
	}

	static public void updateRequestToken(Token t) {
		updateToken(REQUEST_TOKEN_NAME, t);
	}

	static public Token getUserSpecificAccessToken() {
		return getToken(USER_SPECIFIC_ACCESS_TOKEN_NAME);
	}

	static public void updateUserSpecificAccessToken(Token t) {
		updateToken(USER_SPECIFIC_ACCESS_TOKEN_NAME, t);
	}

	static public Token getGeneralAccessToken() {
		return getToken(GENERAL_ACCESS_TOKEN_NAME);
	}

	static public void updateGeneralAccessToken(Token t) {
		updateToken(GENERAL_ACCESS_TOKEN_NAME, t);
	}

	static public void updateTransmitFrequency(int minutes) {
		editor.putInt(TRANSMIT_FREQUENCY, minutes);
	}

	static public void commit() {
		editor.commit();
	}
}
