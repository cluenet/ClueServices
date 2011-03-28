package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.IrcSource;
import org.cluenet.clueservices.ircObjects.UserFactory.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class UserKillEvent extends UserSignoffEvent {
	IrcSource src;
	
	public UserKillEvent( User u, IrcSource from, String reason ) {
		super( u, reason );
		src = from;
	}
	
	public IrcSource getKiller() {
		return src;
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "UserKillEvent" ) );
		e.appendChild( doc.createElement( "User" ) ).appendChild( getUser().toXML( doc ) );
		e.appendChild( doc.createElement( "Killer" ) ).appendChild( getKiller().toXML( doc ) );
		e.appendChild( doc.createElement( "Reason" ) ).appendChild( doc.createTextNode( getReason() ) );
		return e;
	}
}
