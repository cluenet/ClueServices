package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.IrcSource;
import org.cluenet.clueservices.ircObjects.IrcTarget;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class NoticeEvent extends SourceTargetStringEvent {

	public NoticeEvent( IrcSource src, IrcTarget tgt, String str ) {
		super( src, tgt, str );
	}

	@Override
	public Element toXML( Document doc ) {
		Element e = doc.createElement( "Event" );
		e.appendChild( doc.createElement( "Type" ) ).appendChild( doc.createTextNode( "NoticeEvent" ) );
		e.appendChild( doc.createElement( "Source" ) ).appendChild( getSource().toXML( doc ) );
		e.appendChild( doc.createElement( "Target" ) ).appendChild( getTarget().toXML( doc ) );
		e.appendChild( doc.createElement( "Data" ) ).appendChild( doc.createTextNode( getParameters() ) );
		return e;
	}
	
}
