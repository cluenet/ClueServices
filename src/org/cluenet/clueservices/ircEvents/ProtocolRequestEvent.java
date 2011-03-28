package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.AbstractEvent;
import org.cluenet.clueservices.core.Event;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


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

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "ProtocolRequestEvent" ) );
		e.appendChild( doc.createElement( "Source" ) ).appendChild( event.toXML( doc ) );
		return e;
	}
	
}
