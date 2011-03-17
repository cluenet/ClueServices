package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.cluenet.clueservices.ircObjects.UserFactory.User;


public class UserJoinEvent extends UserEnterEvent {
	
	public UserJoinEvent( User u, Channel c ) {
		super( u, c );
	}
	
}
