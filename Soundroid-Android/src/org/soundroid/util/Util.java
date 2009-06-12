package org.soundroid.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.soundroid.oauth.Token;

import android.content.Intent;
import android.net.Uri;

public class Util {

	static public void printTime(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		System.out.println(fmt.format(c.getTime()));

	}

	static public ClientSettings loadSettings(String file) {
		return loadSettings(new File(file));
	}

	static public ClientSettings loadSettings(File f) {
		if (f.exists() == false) {
			return new ClientSettings();
		}

		Properties props = new Properties();

		FileInputStream fis = null;

		try {
			fis = new FileInputStream(f);

			ClientSettings settings = loadSettings(fis);

			fis.close();

			return settings;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

	}

	static public ClientSettings loadSettings(InputStream input) {
		Properties props = new Properties();

		try {
			props.load(input);

			return loadSettings(props);

		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	static public ClientSettings loadSettings(Properties props) {
		ClientSettings settings = new ClientSettings();

		Token user = new Token();
		user.setPublicKey(props.getProperty("user.token"));
		user.setSecret(props.getProperty("user.secret"));

		if (user.isValid()) {
			settings.setUserSpecificToken(user);
		}

		Token general = new Token();
		general.setPublicKey(props.getProperty("general.token"));
		general.setSecret(props.getProperty("general.secret"));

		settings.setGeneralToken(general);

		Token consumer = new Token();
		consumer.setPublicKey(props.getProperty("consumer.key"));
		consumer.setSecret(props.getProperty("consumer.secret"));

		settings.setConsumerToken(consumer);

		return settings;
	}

	static public File saveSettings(ClientSettings settings, String filename) {
		File f = new File(filename);

		return saveSettings(settings, f);
	}

	static public File saveSettings(ClientSettings settings, File f) {
		Properties props = new Properties();

		props.setProperty("user.token", settings.getUserSpecificToken()
				.getPublicKey());
		props.setProperty("user.secret", settings.getUserSpecificToken()
				.getSecret());

		props.setProperty("general.token", settings.getGeneralToken()
				.getPublicKey());
		props.setProperty("general.secret", settings.getGeneralToken()
				.getSecret());

		props.setProperty("consumer.key", settings.getConsumerToken()
				.getPublicKey());
		props.setProperty("consumer.secret", settings.getConsumerToken()
				.getSecret());

		try {
			FileOutputStream fos = new FileOutputStream(f);
			props.store(fos, "");

			fos.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		return f;

	}

	static public String getCallbackUrlScheme() {
		return "soundroid-app";
	}

	static public String getCallbackUrl() {
		return getCallbackUrlScheme() + "://callback";
	}

	static public boolean isCallback(Intent i) {
		if (i == null) {
			return false;
		}

		Uri uri = i.getData();

		return isCallback(uri);
	}

	static public boolean isCallback(Uri u) {
		if (u == null) {
			return false;
		} else {
			return u.toString().startsWith(getCallbackUrl());
		}
	}

	static public String getToken(Uri u) {
		return u.getQueryParameter("oauth_token");
	}

}
