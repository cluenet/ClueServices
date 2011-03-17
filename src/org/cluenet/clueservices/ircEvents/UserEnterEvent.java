package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.AbstractEvent;
import org.cluenet.clueservices.ircObjects.IrcObject;
import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.cluenet.clueservices.ircObjects.UserFactory.User;


public abstract class UserEnterEvent extends AbstractEvent implements ChannelCreationCause {
	protected User user;
	protected Channel channel;
	
	protected UserEnterEvent( User u, Channel c ) {
		user = u;
		channel = c;
	}
	
	public User getUser() {
		return user;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	@Override
	public IrcObject[] getParameters() {
		return new IrcObject[] { user, channel };
	}
	
}
