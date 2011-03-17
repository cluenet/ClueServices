package org.cluenet.clueservices.core.events;

import org.cluenet.clueservices.core.AbstractEvent;


public class SocketWriteRequestEvent extends AbstractEvent {
	private String line;
	
	public SocketWriteRequestEvent( String line ) {
		this.line = line;
	}

	@Override
	public String getParameters() {
		return line;
	}
	
}
