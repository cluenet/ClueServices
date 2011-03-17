package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.UserFactory.User;


public class UserAwayEvent extends UserStringEvent {
	public UserAwayEvent( User u, String reason ) {
		super( u, reason );
	}
}
