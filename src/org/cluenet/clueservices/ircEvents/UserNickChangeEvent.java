package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.UserFactory.User;


public class UserNickChangeEvent extends UserStringEvent {
	
	public UserNickChangeEvent( User u, String oldNick ) {
		super( u, oldNick );
	}
	
}
