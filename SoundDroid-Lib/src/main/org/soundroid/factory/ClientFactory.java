package org.soundroid.factory;

import org.soundroid.client.SoundcloudClientJSON;
import org.soundroid.client.SoundcloudClientXML;

public class ClientFactory {

	public static SoundcloudClientXML createXMLClient(){
		return new SoundcloudClientXML();
	}
	
	public static SoundcloudClientJSON createJSONClient(){
		return new SoundcloudClientJSON();
	}
}
