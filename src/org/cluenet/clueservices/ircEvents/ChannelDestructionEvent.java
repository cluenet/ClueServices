package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.AbstractEvent;
import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ChannelDestructionEvent extends AbstractEvent {
	private Channel channel;
	private UserLeaveEvent cause;
	
	public ChannelDestructionEvent( Channel chan, UserLeaveEvent cause ) {
		this.channel = chan;
		this.cause = cause;
	}
	
	public UserLeaveEvent getCause() {
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
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "ChannelDestructionEvent" ) );
		e.appendChild( doc.createElement( "Cause" ) ).appendChild( cause.toXML( doc ) );
		e.appendChild( doc.createElement( "Channel" ) ).appendChild( channel.toXML( doc ) );
		return e;
	}
	
}
