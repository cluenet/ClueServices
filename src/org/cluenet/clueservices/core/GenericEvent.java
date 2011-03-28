package org.cluenet.clueservices.core;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


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

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "GenericEvent" ) );
		e.appendChild( doc.createElement( "GenericType" ) ).appendChild( doc.createTextNode( event ) );
		Node p = e.appendChild( doc.createElement( "Parameters" ) );
		for( String k : parameters.keySet() )
			p.appendChild( doc.createElement( k ) ).appendChild( doc.createTextNode( parameters.get( k ).toString() ) );
		return e;
	}
}
