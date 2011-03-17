package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.ServerFactory.Server;


public class NewServerEvent extends ServerEvent {

	public NewServerEvent( Server s ) {
		super( s );
	}
	
}
