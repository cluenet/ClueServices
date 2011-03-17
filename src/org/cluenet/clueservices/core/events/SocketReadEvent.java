package org.cluenet.clueservices.core.events;

import org.cluenet.clueservices.core.AbstractEvent;


public class SocketReadEvent extends AbstractEvent {
	private String line;
	
	public SocketReadEvent( String line ) {
		this.line = line;
	}
	
	@Override
	public String getParameters() {
		return line;
	}
}
