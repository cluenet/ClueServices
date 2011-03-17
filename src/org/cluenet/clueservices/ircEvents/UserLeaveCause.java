package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.Event;


public interface UserLeaveCause extends Event {
	public String getReason();
}
