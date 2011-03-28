package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.AbstractEvent;
import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class NewChannelEvent extends AbstractEvent {
	private Channel channel;
	private ChannelCreationCause cause;
	
	public NewChannelEvent( Channel chan, ChannelCreationCause cause ) {
		this.channel = chan;
		this.cause = cause;
	}
	
	public ChannelCreationCause getCause() {
		return cause;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	@Override
	public Channel getParameters() {
		return channel;
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "ChannelCreationEvent" ) );
		e.appendChild( doc.createElement( "Cause" ) ).appendChild( cause.toXML( doc ) );
		e.appendChild( doc.createElement( "Channel" ) ).appendChild( channel.toXML( doc ) );
		return e;
	}
	
}
