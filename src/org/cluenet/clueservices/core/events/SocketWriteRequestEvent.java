package org.cluenet.clueservices.core.events;

import org.cluenet.clueservices.core.AbstractEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class SocketWriteRequestEvent extends AbstractEvent {
	private String line;
	
	public SocketWriteRequestEvent( String line ) {
		this.line = line;
	}

	@Override
	public String getParameters() {
		return line;
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "SocketWriteRequestEvent" ) );
		e.appendChild( doc.createElement( "Data" ) ).appendChild( doc.createTextNode( line ) );
		return e;
	}
	
}
