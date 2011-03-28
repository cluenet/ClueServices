package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.AbstractEvent;
import org.cluenet.clueservices.ircObjects.UserFactory.User;


public abstract class UserEvent extends AbstractEvent {
	private User user;
	
	protected UserEvent( User u ) {
		user = u;
	}
	
	public User getUser() {
		return user;
	}
	
	@Override
	public User getParameters() {
		return user;
	}
	
}
