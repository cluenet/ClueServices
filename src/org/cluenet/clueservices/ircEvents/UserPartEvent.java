package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.cluenet.clueservices.ircObjects.UserFactory.User;


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
	
}
