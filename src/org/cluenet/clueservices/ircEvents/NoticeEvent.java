package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.ircObjects.IrcSource;
import org.cluenet.clueservices.ircObjects.IrcTarget;


public class NoticeEvent extends SourceTargetStringEvent {

	public NoticeEvent( IrcSource src, IrcTarget tgt, String str ) {
		super( src, tgt, str );
	}
	
}
