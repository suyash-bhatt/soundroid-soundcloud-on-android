package org.soundroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
	private static SharedPreferences prefs;
	private static SharedPreferences.Editor editor;

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

	public static Token getRequestToken() {
		return getToken(REQUEST_TOKEN_NAME);
	}

	public static void updateRequestToken(Token t) {
		updateToken(REQUEST_TOKEN_NAME, t);
	}

	public static Token getUserSpecificAccessToken() {
		return getToken(USER_SPECIFIC_ACCESS_TOKEN_NAME);
	}

	public static void updateUserSpecificAccessToken(Token t) {
		updateToken(USER_SPECIFIC_ACCESS_TOKEN_NAME, t);
	}

	public static Token getGeneralAccessToken() {
		return getToken(GENERAL_ACCESS_TOKEN_NAME);
	}

	public static void updateGeneralAccessToken(Token t) {
		updateToken(GENERAL_ACCESS_TOKEN_NAME, t);
	}

	public static void commit() {
		editor.commit();
	}
}
