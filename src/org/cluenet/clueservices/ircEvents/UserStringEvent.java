package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.UserFactory.User;


public class UserStringEvent extends UserEvent {
	private String string;
	
	public UserStringEvent( User u, String str ) {
		super( u );
		this.string = str;
	}
	
	public String getString() {
		return string;
	}
}
