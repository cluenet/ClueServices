package org.cluenet.clueservices.ircObjects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.cluenet.clueservices.ircObjects.ServerFactory.Server;
import org.cluenet.clueservices.servicesObjects.AccountFactory.Account;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class UserFactory {
	private static UserFactory factory = new UserFactory();
	private static Map< String, User > map = Collections.synchronizedMap( new HashMap< String, User >() );
	
	public class User extends IrcObject implements IrcSource, IrcTarget {
		private String nick;
		private String user;
		private String host;
		private String real;
		private String modes;
		private String ip;
		private String swhois;
		private Server server;
		private Account account;
		private Boolean isAway;
		private String awayReason;
		private Map< String, Channel > channels = Collections.synchronizedMap( new HashMap< String, Channel >() );
		
		private User( String nick, String user, String host, String real, String modes, String ip, Server server ) {
			this.nick = nick;
			this.user = user;
			this.host = host;
			this.real = real;
			this.modes = modes;
			this.ip = ip;
			this.server = server;
			this.account = null;
			isAway = false;
			awayReason = "";
			swhois = "";
		}

		public void setAccount( Account account ) {
			this.account = account;
		}

		public void addChannel( Channel chan ) {
			channels.put( chan.getName(), chan );
		}

		public Server getServer() {
			return server;
		}

		public String getNick() {
			return nick;
		}
		
		public String getUser() {
			return user;
		}
		
		public String getHost() {
			return host;
		}
		
		public String getRealName() {
			return real;
		}
		
		public String getModes() {
			return modes;
		}
		
		public String getIp() {
			return ip;
		}

		public Map< String, Channel > getChannels() {
			return channels;
		}

		public void setAway( Boolean away ) {
			isAway = away;
		}
		
		public void setAway( String str ) {
			isAway = true;
			awayReason = str;
		}
		
		public void setSwhois( String str ) {
			swhois = str;
		}
		
		public String getSwhois() {
			return swhois;
		}

		public void delChannel( Channel c ) {
			channels.remove( c.getName() );
		}
		
		public String toString() {
			return getNick();
		}

		@Override
		public Element toXML( Document doc ) {
			Element e = doc.createElement( "User" );
			e.appendChild( doc.createElement( "Nick" ) ).appendChild( doc.createTextNode( nick ) );
			e.appendChild( doc.createElement( "Username" ) ).appendChild( doc.createTextNode( user ) );
			e.appendChild( doc.createElement( "Host" ) ).appendChild( doc.createTextNode( host ) );
			e.appendChild( doc.createElement( "Real" ) ).appendChild( doc.createTextNode( real ) );
			e.appendChild( doc.createElement( "Modes" ) ).appendChild( doc.createTextNode( modes ) );
			e.appendChild( doc.createElement( "IP" ) ).appendChild( doc.createTextNode( ip ) );
			e.appendChild( doc.createElement( "Server" ) ).appendChild( doc.createTextNode( server.getName() ) );
			if( account != null )
				e.appendChild( doc.createElement( "Account" ) ).appendChild( doc.createTextNode( account.getName() ) );
			e.appendChild( doc.createElement( "IsAway" ) ).appendChild( doc.createTextNode( ( isAway ? "True" : "False" ) ) );
			e.appendChild( doc.createElement( "AwayReason" ) ).appendChild( doc.createTextNode( awayReason ) );
			e.appendChild( doc.createElement( "Swhois" ) ).appendChild( doc.createTextNode( swhois ) );
			Node u = e.appendChild( doc.createElement( "Channels" ) );
			for( String c : channels.keySet() )
				u.appendChild( doc.createElement( "ChannelName" ) ).appendChild( doc.createTextNode( c ) );
			return e;
		}
	}

	
	private UserFactory() {
		
	}

	public static User fake( String nick, String user, String host, String real, String modes, String ip, Server server ) {
		return factory.new User( nick, user, host, real, modes, ip, server );
	}

	public static User create( String nick, String user, String host, String real, String modes, String ip, Server server ) {
		User u = factory.new User( nick, user, host, real, modes, ip, server );
		map.put( nick, u );
		return u;
	}


	public static User find( String name ) {
		return map.get( name );
	}


	public static void destroy( User u ) {
		map.remove( u );
	}


	public static void renameUser( User u, String newNick ) {
		map.put( newNick, u );
		String oldNick = u.getNick();
		u.nick = newNick;
		map.remove( oldNick );
		for( Channel c : u.getChannels().values() )
			c.renameUser( oldNick, newNick );
	}
}
