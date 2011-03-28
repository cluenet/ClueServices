package org.cluenet.clueservices.core.events;

import org.cluenet.clueservices.core.AbstractEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class SocketEOFEvent extends AbstractEvent {
	
	@Override
	public Object getParameters() {
		return null;
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "SocketEOFEvent" ) );
		return e;
	}
	
}
