package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.UserFactory.User;


public class UserSignonEvent extends UserEvent {
	public UserSignonEvent( User u ) {
		super( u );
	}
}
