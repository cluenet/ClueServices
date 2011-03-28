package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.UserFactory.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class UserQuitEvent extends UserSignoffEvent {
	public UserQuitEvent( User u, String reason ) {
		super( u, reason );
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "UserQuitEvent" ) );
		e.appendChild( doc.createElement( "User" ) ).appendChild( getUser().toXML( doc ) );
		e.appendChild( doc.createElement( "Reason" ) ).appendChild( doc.createTextNode( getReason() ) );
		return e;
	}
}
