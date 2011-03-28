package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.ServerFactory.Server;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ServerSyncEvent extends ServerEvent {

	public ServerSyncEvent( Server s ) {
		super( s );
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "ServerSyncEvent" ) );
		e.appendChild( doc.createElement( "Server" ) ).appendChild( getParameters().toXML( doc ) );
		return e;
	}
	
}
