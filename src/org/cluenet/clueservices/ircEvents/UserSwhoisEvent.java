package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.UserFactory.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class UserSwhoisEvent extends UserEvent {
	
	public UserSwhoisEvent( User u ) {
		super( u );
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "UserSwhoisEvent" ) );
		e.appendChild( doc.createElement( "User" ) ).appendChild( getUser().toXML( doc ) );
		return e;
	}
	
}
