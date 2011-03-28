package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.UserFactory.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class UserSignonEvent extends UserEvent {
	public UserSignonEvent( User u ) {
		super( u );
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "UserSignonEvent" ) );
		e.appendChild( doc.createElement( "User" ) ).appendChild( getParameters().toXML( doc ) );
		return e;
	}
}
