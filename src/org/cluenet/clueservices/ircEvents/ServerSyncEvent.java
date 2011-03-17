package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.ServerFactory.Server;


public class ServerSyncEvent extends ServerEvent {

	public ServerSyncEvent( Server s ) {
		super( s );
	}
	
}
