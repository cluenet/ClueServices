package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.Event;
import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;


public interface ChannelCreationCause extends Event {
	public Channel getChannel();
}
