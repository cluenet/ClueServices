package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.UserFactory.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class UserNickChangeEvent extends UserStringEvent {
	
	public UserNickChangeEvent( User u, String oldNick ) {
		super( u, oldNick );
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "UserLeaveEvent" ) );
		e.appendChild( doc.createElement( "User" ) ).appendChild( getUser().toXML( doc ) );
		e.appendChild( doc.createElement( "OldNick" ) ).appendChild( doc.createTextNode( getString() ) );
		return e;
	}
	
}
