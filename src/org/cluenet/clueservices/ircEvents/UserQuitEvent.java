package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.UserFactory.User;


public class UserQuitEvent extends UserSignoffEvent {
	public UserQuitEvent( User u, String reason ) {
		super( u, reason );
	}
}
