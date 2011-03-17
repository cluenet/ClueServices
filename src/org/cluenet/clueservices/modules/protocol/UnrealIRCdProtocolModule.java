package org.cluenet.clueservices.modules.protocol;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.cluenet.clueservices.core.Core;
import org.cluenet.clueservices.core.Event;
import org.cluenet.clueservices.core.Module;
import org.cluenet.clueservices.core.events.SocketConnectedEvent;
import org.cluenet.clueservices.core.events.SocketReadEvent;
import org.cluenet.clueservices.core.events.SocketWriteRequestEvent;
import org.cluenet.clueservices.ircEvents.ChannelCreationCause;
import org.cluenet.clueservices.ircEvents.ChannelDestructionEvent;
import org.cluenet.clueservices.ircEvents.NewChannelEvent;
import org.cluenet.clueservices.ircEvents.NewServerEvent;
import org.cluenet.clueservices.ircEvents.NoticeEvent;
import org.cluenet.clueservices.ircEvents.PrivmsgEvent;
import org.cluenet.clueservices.ircEvents.ProtocolRequestEvent;
import org.cluenet.clueservices.ircEvents.ServerSyncEvent;
import org.cluenet.clueservices.ircEvents.UserAwayEvent;
import org.cluenet.clueservices.ircEvents.UserEnterEvent;
import org.cluenet.clueservices.ircEvents.UserJoinEvent;
import org.cluenet.clueservices.ircEvents.UserLeaveCause;
import org.cluenet.clueservices.ircEvents.UserLeaveEvent;
import org.cluenet.clueservices.ircEvents.UserNickChangeEvent;
import org.cluenet.clueservices.ircEvents.UserPartEvent;
import org.cluenet.clueservices.ircEvents.UserQuitEvent;
import org.cluenet.clueservices.ircEvents.UserSignonEvent;
import org.cluenet.clueservices.ircEvents.UserSyncJoinEvent;
import org.cluenet.clueservices.ircEvents.UserUnAwayEvent;
import org.cluenet.clueservices.ircObjects.ChannelFactory;
import org.cluenet.clueservices.ircObjects.IrcObject;
import org.cluenet.clueservices.ircObjects.IrcSource;
import org.cluenet.clueservices.ircObjects.ServerFactory;
import org.cluenet.clueservices.ircObjects.UserFactory;
import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.cluenet.clueservices.ircObjects.ServerFactory.Server;
import org.cluenet.clueservices.ircObjects.UserFactory.User;
import org.cluenet.clueservices.misc.Config;
import org.cluenet.clueservices.misc.Strings;
import org.cluenet.clueservices.modules.utility.ParsedIRCLine;
import org.cluenet.clueservices.servicesObjects.AccountFactory;


public class UnrealIRCdProtocolModule extends Module {
	private Server self;
	
	@Override
	protected void event( Event e ) {
		if( e instanceof SocketReadEvent )
			parseInput( ( (SocketReadEvent) e ).getParameters() );
		else if( e instanceof SocketConnectedEvent )
			connected();
		else if( e instanceof ProtocolRequestEvent )
			processRequest( (ProtocolRequestEvent) e );
	}
	
	private void write( String line ) {
		Core.fireEvent( new SocketWriteRequestEvent( line + "\r\n" ) );
	}
	
	private void connected() {
		write( "PASS " + Config.get( "server", "pass" ) );
		write( "PROTOCTL NICKv2 NICKIP" );
		write( "SERVER " + Config.get( "server", "name" ) + " " + Config.get( "server", "numeric" ) + " :" + Config.get( "server", "description" ) );
		self = ServerFactory.create( Config.get( "server", "name" ), null, Config.get( "server", "description" ) );
	}

	private void parseInput( String line ) {
		ParsedIRCLine data = new ParsedIRCLine( line );
		
		if( data.type.equals( ParsedIRCLine.Type.DIRECT ) ) {
			if( data.command.equals( "ping" ) && data.pieces.size() > 0 )
				write( "PONG :" + data.pieces.get( 0 ) );
			
			else if( data.command.equals( "nick" ) && data.pieces.size() > 10 ) {
				
				byte[] binaryIP = DatatypeConverter.parseBase64Binary( data.pieces.get( 9 ) );
				String ip = "";
				if( binaryIP.length > 4 ) {
					ArrayList< String > ipParts = new ArrayList< String >();
					for( int i = 0 ; i < binaryIP.length ; i += 2 )
						ipParts.add( Integer.toHexString( binaryIP[ i ] << 8 | binaryIP[ i + 1 ] ) );
					ip = Strings.join( ipParts, ":" );
				} else {
					ArrayList< String > ipParts = new ArrayList< String >();
					for( byte b : binaryIP )
						ipParts.add( Integer.toString( b ) );
					ip = Strings.join( ipParts, "." );
				}
				
				User user = UserFactory.create(
						data.pieces.get( 0 ),		// Nick
						data.pieces.get( 3 ),		// User
						data.pieces.get( 4 ),		// Host
						data.pieces.get( 10 ),		// Real
						data.pieces.get( 7 ),		// Modes
						ip,							// IP
						ServerFactory.find( data.pieces.get( 5 ) )
				);
				
				if( data.pieces.get( 6 ) != "0" )
					user.setAccount( AccountFactory.find( Integer.parseInt( data.pieces.get( 6 ), 10 ) ) );
				
				Core.fireEvent( new UserSignonEvent( user ) );
			} else if( data.command.equals( "pass" ) ) {
				// TODO: Implement.
			} else if( data.command.equals( "server" ) && data.pieces.size() > 2 ) {
				Server newSrv = ServerFactory.create( data.pieces.get( 0 ), self, data.pieces.get( 2 ) );
				Core.fireEvent( new NewServerEvent( newSrv ) );
			} else if( data.command.equals( "protoctl" ) ) {
				// TODO: Implement.
			} else if( data.command.equals( "swhois" ) ) {
				// TODO: Implement.
				unhandled( data );
			} else if( data.command.equals( "topic" ) ) {
				// TODO: Implement.
				unhandled( data );
			} else if( data.command.equals( "netinfo" ) ) {
				// TODO: Implement.
			} else
				unhandled( data );
		} else if( data.type.equals( ParsedIRCLine.Type.RELAYED ) ) {
			IrcSource src = IrcObject.getSourceFromName( data.source );
			if( data.command.equals( "notice" ) && data.pieces.size() > 0 ) {
				Core.fireEvent( new NoticeEvent( src, IrcObject.getTargetFromName( data.target ), data.pieces.get( 0 ) ) );
			} else if( data.command.equals( "privmsg" ) && data.pieces.size() > 0 ) {
				Core.fireEvent( new PrivmsgEvent( src, IrcObject.getTargetFromName( data.target ), data.pieces.get( 0 ) ) );
			} else if( data.command.equals( "smo" ) ) {
				// TODO: Implement.
				unhandled( data );
			} else if( data.command.equals( "server" ) && data.pieces.size() > 1 ) {
				Server parent = ServerFactory.find( data.source );
				Server newSrv = ServerFactory.create( data.target, parent, data.pieces.get( 1 ) );
				Core.fireEvent( new NewServerEvent( newSrv ) );
			} else if( data.command.equals( "eos" ) ) {
				Server srv = ServerFactory.find( data.source );
				Core.fireEvent( new ServerSyncEvent( srv ) );
			} else if( data.command.equals( "join" ) ) {
				for( String channelName : data.target.split( "," ) ) {
					User u = UserFactory.find( data.source );
					
					if( channelName.startsWith( "0" ) ) {
						synchronized( u.getChannels() ) {
							for( Channel c : u.getChannels().values() )
								processPart( u, c, "User left all channels." );
						}
						continue;
					}
					
					Channel chan = ChannelFactory.find( channelName );
					Boolean created = false;
					if( chan == null ) {
						chan = ChannelFactory.create( channelName );
						created = true;
					}
					u.addChannel( chan );
					chan.addUser( u );
					ChannelCreationCause evt;
					if( u.getServer().isSynchronized() )
						evt = new UserJoinEvent( u, chan );
					else
						evt = new UserSyncJoinEvent( u, chan );
					if( created )
						Core.fireEvent( new NewChannelEvent( chan, evt ) );
					Core.fireEvent( evt );
				}
			} else if( data.command.equals( "part" ) ) {
				for( String channelName : data.target.split( "," ) ) {
					User u = UserFactory.find( data.source );
					Channel chan = ChannelFactory.find( channelName );
					processPart( u, chan, data.pieces.size() > 0 ? data.pieces.get( 0 ) : "" );
				}
			} else if( data.command.equals( "away" ) ) {
				User u = UserFactory.find( data.source );
				Event evt;
				if( data.target.equals( "" ) ) {
					evt = new UserUnAwayEvent( u );
					u.setAway( false );
				} else {
					evt = new UserAwayEvent( u, data.target );
					u.setAway( data.target );
				}
				Core.fireEvent( evt );
			} else if( data.command.equals( "mode" ) ) {
				// TODO: Implement.
				unhandled( data );
			} else if( data.command.equals( "topic" ) ) {
				// TODO: Implement.
				unhandled( data );
			} else if( data.command.equals( "tkl" ) ) {
				// TODO: Implement.
				unhandled( data );
			} else if( data.command.equals( "nick" ) ) {
				User u = UserFactory.find( data.source );
				String oldNick = u.getNick();
				
				UserFactory.renameUser( u, data.target );
				
				Core.fireEvent( new UserNickChangeEvent( u, oldNick ) );
			} else if( data.command.equals( "quit" ) ) {
				User u = UserFactory.find( data.source );
				Map< String, Channel > map = u.getChannels();
				UserLeaveCause evt = new UserQuitEvent( u, data.target );
				synchronized( map ) {
					for( Channel c : map.values() ) {
						c.delUser( u );
						UserLeaveEvent leaveEvt = new UserLeaveEvent( u, c, evt );
						Core.fireEvent( leaveEvt );
						if( c.isEmpty() ) {
							ChannelFactory.destroy( c );
							Core.fireEvent( new ChannelDestructionEvent( c, leaveEvt ) );
						}
					}
				}
				UserFactory.destroy( u );
				Core.fireEvent( evt );
			} else
				unhandled( data );
		}
		/*
		 * TODO:
		 * svsnick
		 * kill
		 * svskill
		 * invite
		 * kick
		 */

	}

	private void processPart( User u, Channel c, String reason ) {
		u.delChannel( c );
		c.delUser( u );
		
		UserLeaveEvent evt = new UserPartEvent( u, c, reason ); 
		
		Core.fireEvent( evt );
		
		if( c.isEmpty() ) {
			ChannelFactory.destroy( c );
			Core.fireEvent( new ChannelDestructionEvent( c, evt ) );
		}
	}

	private void unhandled( ParsedIRCLine data ) {
		System.out.println( "Unhandled IRC line: " + data );
		write( ":ClueServices.ClueNet.Org PRIVMSG #secretservices :Unhandled IRC Line: " + data );
	}

	@Override
	protected Boolean init() {
		return true;
	}
	
	private void processRequest( ProtocolRequestEvent evt ) {
		Event cause = evt.getEvent();
		processRequestEvent( cause );
	}
	
	private void processRequestEvent( Event evt ) {
		if( evt instanceof NewChannelEvent )
			processRequestEvent( ( (NewChannelEvent) evt ).getCause() );
		else if( evt instanceof UserEnterEvent )
			if( ( (UserEnterEvent) evt ).getUser().getServer() == self )
				write( ":" + ( (UserEnterEvent) evt ).getUser() + " JOIN " + ( (UserEnterEvent) evt ).getChannel() );
			else
				write( ":" + self + " SAJOIN " + ( (UserEnterEvent) evt ).getChannel() );
		else if( evt instanceof UserLeaveEvent )
			if( false ) {} // TODO: Change this for kick.
			else if( ( (UserLeaveEvent) evt ).getUser().getServer() == self )
				write( ":" + ( (UserLeaveEvent) evt ).getUser() + " PART " + ( (UserLeaveEvent) evt ).getChannel() + " :" + ( (UserLeaveEvent) evt ).getReason() );
			else {/*TODO:*/} // And on and on for part/kill/etc.
		else if( evt instanceof PrivmsgEvent )
			write( ":" + ( (PrivmsgEvent) evt ).getSource() + " PRIVMSG " + ( (PrivmsgEvent) evt ).getTarget() + " :" + ( (PrivmsgEvent) evt ).getParameters() );
		else if( evt instanceof UserSignonEvent ) {
			User u = ( (UserSignonEvent) evt ).getParameters();
			write( "NICK " + u.getNick() + " 1 " + ( System.currentTimeMillis() / 1000 ) + " " + u.getUser() + " " + u.getHost() + " " + u.getServer() + " 0 " + u.getModes() + " * AAAAAA== :" + u.getRealName() );
		}
			
		// TODO: And on and on and on
	}
	
}
