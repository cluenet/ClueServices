package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.AbstractEvent;
import org.cluenet.clueservices.core.Event;


public class ProtocolRequestEvent extends AbstractEvent {
	private Event event;
	
	
	public ProtocolRequestEvent( Event event ) {
		this.event = event;
	}
	
	public Event getEvent() {
		return event;
	}
	
	@Override
	public Event getParameters() {
		return getEvent();
	}
	
}
