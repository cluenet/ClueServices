package org.cluenet.clueservices.ircObjects;


public abstract class IrcObject {
	public static IrcSource getSourceFromName( String name ) {
		IrcSource src;
		src = UserFactory.find( name );
		if( src == null )
			src = ServerFactory.find( name );
		return src;
	}

	public static IrcTarget getTargetFromName( String target ) {
		IrcTarget tgt;
		tgt = UserFactory.find( target );
		if( tgt == null )
			tgt = ChannelFactory.find( target );
		return tgt;
	}
}
