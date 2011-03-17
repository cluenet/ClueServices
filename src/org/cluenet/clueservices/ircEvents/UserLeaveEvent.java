package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.AbstractEvent;
import org.cluenet.clueservices.ircObjects.IrcObject;
import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.cluenet.clueservices.ircObjects.UserFactory.User;


public class UserLeaveEvent extends AbstractEvent implements UserLeaveCause {
	protected User user;
	protected Channel channel;
	protected UserLeaveCause cause;
	
	public UserLeaveEvent( User u, Channel c, UserLeaveCause cause ) {
		user = u;
		channel = c;
		this.cause = cause;
	}
	
	public String getReason() {
		return "";
	}
	
	public User getUser() {
		return user;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public UserLeaveCause getCause() {
		return cause;
	}
	
	@Override
	public IrcObject[] getParameters() {
		return new IrcObject[] { user, channel };
	}
	
}
