package org.cluenet.clueservices.core;

import java.util.Map;


public class GenericEvent extends AbstractEvent {
	private String event;
	private Map< String, Object > parameters;
	
	public GenericEvent( String event, Map< String, Object > parameters ) {
		this.event = event;
		this.parameters = parameters;
	}
	
	public String getEvent() {
		return prefix + event;
	}
	
	public Map< String, Object > getParameters() {
		return parameters;
	}
	
	public String toString() {
		return getEvent();
	}

	@Override
	public String getType() {
		return event;
	}
}
