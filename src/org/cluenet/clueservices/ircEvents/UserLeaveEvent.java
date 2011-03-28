package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.AbstractEvent;
import org.cluenet.clueservices.ircObjects.IrcObject;
import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.cluenet.clueservices.ircObjects.UserFactory.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class UserLeaveEvent extends AbstractEvent implements UserLeaveCause {
	protected User user;
	protected Channel channel;
	protected UserLeaveCause cause;
	
	public UserLeaveEvent( User u, Channel c, UserLeaveCause cause ) {
		user = u;
		channel = c;
		this.cause = cause;
	}
	
	public String getReason() {
		return "";
	}
	
	public User getUser() {
		return user;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public UserLeaveCause getCause() {
		return cause;
	}
	
	@Override
	public IrcObject[] getParameters() {
		return new IrcObject[] { user, channel };
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "UserLeaveEvent" ) );
		e.appendChild( doc.createElement( "User" ) ).appendChild( getUser().toXML( doc ) );
		e.appendChild( doc.createElement( "Channel" ) ).appendChild( getChannel().toXML( doc ) );
		e.appendChild( doc.createElement( "Cause" ) ).appendChild( cause.toXML( doc ) );
		return e;
	}

}
