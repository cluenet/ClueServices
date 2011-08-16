package org.cluenet.clueservices.misc;

import org.cluenet.clueservices.core.Core;
import org.cluenet.clueservices.ircEvents.PrivmsgEvent;
import org.cluenet.clueservices.ircEvents.ProtocolRequestEvent;
import org.cluenet.clueservices.ircEvents.UserJoinEvent;
import org.cluenet.clueservices.ircObjects.ChannelFactory;
import org.cluenet.clueservices.ircObjects.IrcObject;
import org.cluenet.clueservices.ircObjects.IrcSource;
import org.cluenet.clueservices.ircObjects.IrcTarget;
import org.cluenet.clueservices.ircObjects.UserFactory;
import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.cluenet.clueservices.ircObjects.UserFactory.User;


public class IRC {
	public static void msg( IrcSource src, IrcTarget tgt, String data ) {
		Core.fireEvent( new ProtocolRequestEvent( new PrivmsgEvent( src, tgt, data ) ) );
	}
	
	public static void msg( String src, String tgt, String data ) {
		msg( IrcObject.getSourceFromName( src ), IrcObject.getTargetFromName( tgt ), data );
	}
	
	public static void join( User src, Channel tgt ) {
		Core.fireEvent( new ProtocolRequestEvent( new UserJoinEvent( src, tgt ) ) );
	}
	
	public static void join( String src, String tgt ) {
		join( UserFactory.find( src ), ChannelFactory.fake( tgt ) );
	}
}
