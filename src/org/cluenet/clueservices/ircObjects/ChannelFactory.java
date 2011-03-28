package org.cluenet.clueservices.ircObjects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.cluenet.clueservices.ircObjects.UserFactory.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class ChannelFactory {
	private static ChannelFactory factory = new ChannelFactory();
	private static Map< String, Channel > map = Collections.synchronizedMap( new HashMap< String, Channel >() );
	
	public class Channel extends IrcObject implements IrcSource, IrcTarget {
		private String name;
		private String topic;
		private Map< String, User > users = Collections.synchronizedMap( new HashMap< String, User >() );
		
		private Channel( String channelName ) {
			name = channelName;
			topic = "";
		}

		public void addUser( User u ) {
			users.put( u.getNick(), u );
		}
		
		public Boolean isEmpty() {
			return users.size() == 0;
		}
		
		public Map< String, User > getUsers() {
			return users;
		}

		public String getName() {
			return name;
		}

		public void delUser( User u ) {
			users.remove( u.getNick() );
		}
		
		public String toString() {
			return getName();
		}

		public Element toXML( Document doc ) {
			Element e = doc.createElement( "Channel" );
			e.appendChild( doc.createElement( "Name" ) ).appendChild( doc.createTextNode( name ) );
			e.appendChild( doc.createElement( "Topic" ) ).appendChild( doc.createTextNode( topic ) );
			Node u = e.appendChild( doc.createElement( "Users" ) );
			for( String nick : users.keySet() )
				u.appendChild( doc.createElement( "Nick" ) ).appendChild( doc.createTextNode( nick ) );
			return e;
		}

		public void renameUser( String oldNick, String newNick ) {
			User u = users.get( oldNick );
			if( u == null )
				return;
			users.put( newNick, u );
			users.remove( oldNick );
		}
	}
	
	private ChannelFactory() {
		
	}

	public static Channel find( String name ) {
		return map.get( name );
	}

	public static Channel fake( String channelName ) {
		return factory.new Channel( channelName );
	}
	
	public static Channel create( String channelName ) {
		Channel c = factory.new Channel( channelName );
		map.put( channelName, c );
		return c;
	}
	
	public static void destroy( Channel c ) {
		map.remove( c );
	}
}
