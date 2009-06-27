package org.soundroid.util;

import org.soundroid.oauth.Token;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
	static private SharedPreferences prefs;
	static private SharedPreferences.Editor editor;

	static private final String REQUEST_TOKEN_NAME = "request";
	static private final String USER_SPECIFIC_ACCESS_TOKEN_NAME = "user-specific-access";
	static private final String GENERAL_ACCESS_TOKEN_NAME = "general-access";
	static private final String TOKEN_PREFIX = "oauth.token";
	static private final String TOKEN_PUBLIC_KEY = "public";
	static private final String TOKEN_SECRET = "secret";

	public static void reload(Context ctx) {
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		editor = prefs.edit();
	}

	static private Token getToken(String name) {
		Token t = new Token();

		t.setPublicKey(prefs.getString(TOKEN_PREFIX + "." + name + "."
				+ TOKEN_PUBLIC_KEY, null));
		t.setSecret(prefs.getString(TOKEN_PREFIX + "." + name + "."
				+ TOKEN_SECRET, null));

		System.out.println("got yer Token: " + name + " --- " + t.toString());

		return t;
	}

	static private void updateToken(String name, Token t) {
		editor.putString(TOKEN_PREFIX + "." + name + "." + TOKEN_PUBLIC_KEY, t
				.getPublicKey());
		editor.putString(TOKEN_PREFIX + "." + name + "." + TOKEN_SECRET, t
				.getSecret());

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

	static public void commit() {
		editor.commit();
	}


}
