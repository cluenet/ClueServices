package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.UserFactory.User;


public class UserSignoffEvent extends UserStringEvent implements UserLeaveCause {
	public UserSignoffEvent( User u, String reason ) {
		super( u, reason );
	}
	
	public String getReason() {
		return getString();
	}
}
