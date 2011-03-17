package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.AbstractEvent;
import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;


public class ChannelDestructionEvent extends AbstractEvent {
	private Channel channel;
	private UserLeaveEvent cause;
	
	public ChannelDestructionEvent( Channel chan, UserLeaveEvent cause ) {
		this.channel = chan;
		this.cause = cause;
	}
	
	public UserLeaveEvent getCause() {
		return cause;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	@Override
	public Channel getParameters() {
		return channel;
	}
	
}
