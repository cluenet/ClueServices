package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.cluenet.clueservices.ircObjects.UserFactory.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class UserSyncJoinEvent extends UserEnterEvent {
	
	public UserSyncJoinEvent( User u, Channel c ) {
		super( u, c );
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "UserSyncJoinEvent" ) );
		e.appendChild( doc.createElement( "User" ) ).appendChild( getUser().toXML( doc ) );
		e.appendChild( doc.createElement( "Channel" ) ).appendChild( getChannel().toXML( doc ) );
		return e;
	}
	
}
