package org.cluenet.clueservices.modules.services;

import org.cluenet.clueservices.core.Core;
import org.cluenet.clueservices.core.Event;
import org.cluenet.clueservices.core.Module;
import org.cluenet.clueservices.ircEvents.PrivmsgEvent;
import org.cluenet.clueservices.ircEvents.ProtocolRequestEvent;
import org.cluenet.clueservices.ircEvents.ServerSyncEvent;
import org.cluenet.clueservices.ircEvents.UserJoinEvent;
import org.cluenet.clueservices.ircEvents.UserSignonEvent;
import org.cluenet.clueservices.ircObjects.ChannelFactory;
import org.cluenet.clueservices.ircObjects.UserFactory;
import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.cluenet.clueservices.ircObjects.UserFactory.User;


public class TestingModule extends Module {
	
	@Override
	protected void event( Event take ) {
		if( take instanceof ServerSyncEvent ) {
			if( ( (ServerSyncEvent) take ).getParameters().toString().equals( "theta.cluenet.org" ) ) {
				Core.fireEvent( new ProtocolRequestEvent( new UserSignonEvent( UserFactory.fake( "FooBar", "FooBar", "Foo.Bar", "Foo Bar", "+", "0.0.0.0", null ) ) ) );
			}
		} else if( take instanceof UserSignonEvent ) {
			User u = ( (UserSignonEvent) take ).getParameters();
			Channel c = ChannelFactory.find( "#clueirc" );
			if( u.getNick().equals( "FooBar" ) ) {
				Core.fireEvent( new ProtocolRequestEvent( new UserJoinEvent( u, c ) ) );
				Core.fireEvent( new ProtocolRequestEvent( new PrivmsgEvent( u, c, "Hello, world!" ) ) );
			}
		} else if( take instanceof PrivmsgEvent ) {
			PrivmsgEvent evt = (PrivmsgEvent) take;
			if( evt.getSource() instanceof User && evt.getTarget() instanceof Channel ) {
				User u = (User) evt.getSource();
				Channel c = (Channel) evt.getTarget();
				String data = evt.getParameters();
				if( data.equals( "!testing" ) )
					Core.fireEvent( new ProtocolRequestEvent( new PrivmsgEvent( UserFactory.find( "FooBar" ), c, "Testing." ) ) );
				else if( data.equals( "!force" ) )
					Core.fireEvent( new ProtocolRequestEvent( new UserJoinEvent( u, ChannelFactory.find( "#cluebotng" ) ) ) );
			}
		}
	}
	
	@Override
	protected Boolean init() {
		return true;
	}
	
}
