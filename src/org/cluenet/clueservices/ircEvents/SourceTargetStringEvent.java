package org.cluenet.clueservices.ircEvents;

import org.cluenet.clueservices.core.AbstractEvent;
import org.cluenet.clueservices.ircObjects.IrcSource;
import org.cluenet.clueservices.ircObjects.IrcTarget;


public abstract class SourceTargetStringEvent extends AbstractEvent {
	private IrcSource source;
	private IrcTarget target;
	private String string;

	public SourceTargetStringEvent( IrcSource src, IrcTarget tgt, String str) {
		source = src;
		target = tgt;
		string = str;
	}
	
	@Override
	public String getParameters() {
		return string;
	}
	
	public IrcSource getSource() {
		return source;
	}
	
	public IrcTarget getTarget() {
		return target;
	}
	
}
