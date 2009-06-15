package org.soundroid.xml.factory;



import org.soundroid.constants.SoundroidConstants;
import org.soundroid.xml.models.Comment;
import org.soundroid.xml.models.Comments;
import org.soundroid.xml.models.Event;
import org.soundroid.xml.models.Events;
import org.soundroid.xml.models.Track;
import org.soundroid.xml.models.Tracks;
import org.soundroid.xml.models.User;
import org.soundroid.xml.models.Users;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.ToStringConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamFactory {
	public static XStream createXStream(int requestType) {
		XStream xstream = null;

		switch (requestType) {
			case SoundroidConstants.ME:
				xstream = me();
				break;
			case SoundroidConstants.TRACKS:
				xstream = tracks();
				break;
			case SoundroidConstants.COMMENTS:
				xstream = comments();
				break;
			case SoundroidConstants.EVENTS:
				xstream = events();
				break;
			case SoundroidConstants.CONTACTS:
				xstream = contacts();
				break;
		}

		return xstream;
	}

	private static XStream me() {
		XStream xstream = new XStream(new DomDriver());

		xstream.aliasField("city", User.class, "city");
		xstream.aliasField("description ", User.class, "description ");
		xstream.aliasField("discogs-name", User.class, "discogsName");
		xstream.aliasField("id", User.class, "id");
		xstream.aliasField("myspace-name", User.class, "myspaceName");
		xstream.aliasField("permalink", User.class, "permalink");
		xstream.aliasField("username", User.class, "username");
		xstream.aliasField("website", User.class, "website");
		xstream.aliasField("website-title", User.class, "websiteTitle");
		xstream.aliasField("full-name", User.class, "fullName");
		xstream.aliasField("country", User.class, "country");
		xstream.aliasField("online", User.class, "online");
		xstream.aliasField("permalink-url", User.class, "permalinkUrl");
		xstream.aliasField("avatar-url", User.class, "avatarUrl");
		xstream.aliasField("uri", User.class, "uri");
		xstream.aliasField("track-count", User.class, "trackCount");

		xstream.alias("user", User.class);

		return xstream;
	}
	
	private static XStream tracks() {
		XStream xstream = new XStream(new DomDriver());

//		try
//		{ 
//			xstream.registerConverter(new ToStringConverter(User.class), XStream.PRIORITY_VERY_HIGH);
//			
//		} 
// 		catch (NoSuchMethodException e)
//		{
//			throw new RuntimeException(e);
//		}
 			
 		try {
			xstream.registerConverter(new ToStringConverter(User.class));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
 		xstream.addImplicitCollection(Tracks.class, "tracks", Track.class);
 		 
		//xstream.registerConverter(new CalendarConverter(), XStream.PRIORITY_VERY_HIGH);
		//xstream.registerConverter(new TracksConverter(xstream.getMapper()), XStream.PRIORITY_VERY_HIGH);
			
		xstream.alias("tracks", Tracks.class);
		xstream.alias("track", Track.class);
		xstream.alias("user", User.class);		
		
		xstream.aliasField("bpm", Track.class, "bpm");
		xstream.aliasField("created-at", Track.class, "createAt");
		xstream.aliasField("description", Track.class, "description");
		xstream.aliasField("downloadable", Track.class, "downloadable");
		xstream.aliasField("duration", Track.class, "duration");
		xstream.aliasField("genre", Track.class, "genre");
		xstream.aliasField("id", Track.class, "id");
		xstream.aliasField("isrc", Track.class, "isrc");
		xstream.aliasField("permalink", Track.class, "permalink");
		xstream.aliasField("purchase-url", Track.class, "purchaseUrl");
		xstream.aliasField("release", Track.class, "release");
		xstream.aliasField("release-day", Track.class, "releaseDay");
		xstream.aliasField("release-month", Track.class, "releaseMonth");
		xstream.aliasField("release-year", Track.class, "releaseYear");
		xstream.aliasField("streamable", Track.class, "streamable");
		xstream.aliasField("user-id", Track.class, "userId");
		xstream.aliasField("sharing", Track.class, "sharing");
		xstream.aliasField("uri", Track.class, "uri");		
		xstream.aliasField("title", Track.class, "title");
		xstream.aliasField("permalink-url", Track.class, "permalinkUrl");
		xstream.aliasField("playback-count", Track.class, "playbackCount");
		xstream.aliasField("download-count", Track.class, "downloadCount");
		xstream.aliasField("comment-count", Track.class, "commentCount");
		xstream.aliasField("artwork-url", Track.class, "artworkUrl");
		xstream.aliasField("waveform-url", Track.class, "waveformUrl");
		xstream.aliasField("artwork-url", Track.class, "artworkUrl");		
		xstream.aliasField("stream-url", Track.class, "streamUrl");
		xstream.aliasField("user-playback-count", Track.class, "userPlaybackCount");
		xstream.aliasField("user-favorite", Track.class, "userFavorite");
		
		

		return xstream;
	}
	
	private static XStream comments() {
		XStream xstream = new XStream(new DomDriver());
	
 		xstream.addImplicitCollection(Comments.class, "comments", Comment.class);
 		 
		//xstream.registerConverter(new CalendarConverter(), XStream.PRIORITY_VERY_HIGH);
		//xstream.registerConverter(new UserConverter(xstream.getMapper(),xstream.getReflectionProvider()), XStream.PRIORITY_VERY_HIGH);
			
 		
		xstream.alias("comments", Comments.class);
		xstream.alias("comment", Comment.class);	
		
		xstream.aliasField("body", Comment.class, "body");
		xstream.aliasField("created-at", Comment.class, "createdAt");
		xstream.aliasField("id", Comment.class, "id");
		xstream.aliasField("timestamp", Comment.class, "timestamp");
		xstream.aliasField("track-id", Comment.class, "trackId");
		xstream.aliasField("user-id", Comment.class, "userId");
		xstream.aliasField("uri", Comment.class, "uri");	

		return xstream;
	}
	
	private static XStream events() {
		XStream xstream = new XStream(new DomDriver());
	
 		xstream.addImplicitCollection(Events.class, "events", Event.class);
 		 
		//xstream.registerConverter(new CalendarConverter(), XStream.PRIORITY_VERY_HIGH);
		//xstream.registerConverter(new UserConverter(xstream.getMapper(),xstream.getReflectionProvider()), XStream.PRIORITY_VERY_HIGH);
			
 		xstream.alias("events", Events.class);
		xstream.alias("event", Event.class);
		xstream.alias("track", Track.class);
		xstream.alias("comment", Comment.class);			
			  
		xstream.aliasField("created-at", Event.class, "createdAt");
		xstream.aliasField("id", Event.class, "id");
		xstream.aliasField("uri", Event.class, "resourceId");

		return xstream;
	}
	
	private static XStream contacts() {
		XStream xstream = new XStream(new DomDriver());
	
 		xstream.addImplicitCollection(Users.class, "users", User.class);
			
 		xstream.alias("users", Users.class);
 		xstream.alias("user", User.class);			
			  
 		xstream.aliasField("city", User.class, "city");
		xstream.aliasField("description ", User.class, "description ");
		xstream.aliasField("discogs-name", User.class, "discogsName");
		xstream.aliasField("id", User.class, "id");
		xstream.aliasField("myspace-name", User.class, "myspaceName");
		xstream.aliasField("permalink", User.class, "permalink");
		xstream.aliasField("username", User.class, "username");
		xstream.aliasField("website", User.class, "website");
		xstream.aliasField("website-title", User.class, "websiteTitle");
		xstream.aliasField("full-name", User.class, "fullName");
		xstream.aliasField("country", User.class, "country");
		xstream.aliasField("online", User.class, "online");
		xstream.aliasField("permalink-url", User.class, "permalinkUrl");
		xstream.aliasField("avatar-url", User.class, "avatarUrl");
		xstream.aliasField("uri", User.class, "uri");
		xstream.aliasField("track-count", User.class, "trackCount");

		return xstream;
	}
}
