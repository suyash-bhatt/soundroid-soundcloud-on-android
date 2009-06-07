package org.soundroid;

import android.content.Intent;
import android.net.Uri;

public class Util {
	public static String getCallbackUrlScheme() {
		return SoundroidConstants.CALLBACK_URL_SCHEME;
	}

	public static String getCallbackUrl() {
		return SoundroidConstants.CALLBACK_URL_SCHEME + SoundroidConstants.OAUTH_CALLBACK_URL;
	}

	public static boolean isCallback(Intent i) {
		if (i == null) {
			return false;
		}

		Uri uri = i.getData();

		return isCallback(uri);
	}

	public static boolean isCallback(Uri u) {
		if (u == null) {
			return false;
		} else {
			return u.toString().startsWith(getCallbackUrl());
		}
	}

	public static String getToken(Uri u) {
		return u.getQueryParameter("oauth_token");
	}
}
