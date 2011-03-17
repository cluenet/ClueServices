package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.UserFactory.User;


public class UserUnAwayEvent extends UserEvent {
	public UserUnAwayEvent( User u ) {
		super( u );
	}
}
