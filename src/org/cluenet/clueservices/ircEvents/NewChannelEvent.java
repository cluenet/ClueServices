package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.AbstractEvent;
import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;


public class NewChannelEvent extends AbstractEvent {
	private Channel channel;
	private ChannelCreationCause cause;
	
	public NewChannelEvent( Channel chan, ChannelCreationCause cause ) {
		this.channel = chan;
		this.cause = cause;
	}
	
	public ChannelCreationCause getCause() {
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
