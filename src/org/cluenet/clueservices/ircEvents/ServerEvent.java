package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.AbstractEvent;
import org.cluenet.clueservices.ircObjects.ServerFactory.Server;


public abstract class ServerEvent extends AbstractEvent {
	private Server server;
	
	protected ServerEvent( Server s ) {
		server = s;
	}
	
	@Override
	public Server getParameters() {
		return server;
	}
	
}
