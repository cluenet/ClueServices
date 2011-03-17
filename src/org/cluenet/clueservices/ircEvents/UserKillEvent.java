package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.IrcSource;
import org.cluenet.clueservices.ircObjects.UserFactory.User;


public class UserKillEvent extends UserSignoffEvent {
	IrcSource src;
	
	public UserKillEvent( User u, IrcSource from, String reason ) {
		super( u, reason );
		src = from;
	}
	
	public IrcSource getKiller() {
		return src;
	}
}
