package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.cluenet.clueservices.ircObjects.UserFactory.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class UserPartEvent extends UserLeaveEvent {
	private String reason;
	
	public UserPartEvent( User u, Channel c, String reason ) {
		super( u, c, null );
		this.cause = this;
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "UserPartEvent" ) );
		e.appendChild( doc.createElement( "User" ) ).appendChild( getUser().toXML( doc ) );
		e.appendChild( doc.createElement( "Channel" ) ).appendChild( getChannel().toXML( doc ) );
		e.appendChild( doc.createElement( "Reason" ) ).appendChild( doc.createTextNode( reason ) );
		return e;
	}
	
}
