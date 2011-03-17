/**
 * 
 */
package org.cluenet.clueservices.core.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.cluenet.clueservices.core.Core;
import org.cluenet.clueservices.core.Event;
import org.cluenet.clueservices.core.Module;
import org.cluenet.clueservices.core.events.SocketConnectedEvent;
import org.cluenet.clueservices.core.events.SocketEOFEvent;
import org.cluenet.clueservices.core.events.SocketReadEvent;
import org.cluenet.clueservices.core.events.SocketWriteRequestEvent;


/**
 * @author cobi
 *
 */
public class SocketModule extends Module {
	private class ConnectionModule implements Runnable {
		
		@Override
		public void run() {
			BufferedReader in;
			try {
				in = new BufferedReader( new InputStreamReader( SocketModule.this.socket.getInputStream() ) );
			} catch( IOException e ) {
				return;
			}
			
			while( !SocketModule.this.socket.isClosed() ) {
				try {
					String line = in.readLine();
					if( line == null )
						throw new IOException();
					SocketModule.this.fireEvent( new SocketReadEvent( line ) );
				} catch( IOException e ) {
					SocketModule.this.fireEvent( new SocketEOFEvent() );
					return;
				}
			}
			SocketModule.this.fireEvent( new SocketEOFEvent() );
		}
		
	}
	
	private String host;
	private Integer port;
	private Socket socket;
	
	
	public void stop() {
		try {
			socket.close();
		} catch( IOException e ) {
			e.printStackTrace();
		}
		super.stop();
	}
	
	public SocketModule( String prefix, String host, Integer port ) {
		super( prefix );
		this.host = host;
		this.port = port;
	}
	
	public SocketModule( String host, Integer port ) {
		this( "", host, port );
	}

	/* (non-Javadoc)
	 * @see org.cluenet.clueservices.core.Module#event(org.cluenet.clueservices.core.Event)
	 */
	@Override
	protected void event( Event take ) {
		if( take instanceof SocketWriteRequestEvent ) {
			SocketWriteRequestEvent event = (SocketWriteRequestEvent) take;
			try {
				socket.getOutputStream().write( event.getParameters().getBytes() );
			} catch( IOException e ) {
				return;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.cluenet.clueservices.core.Module#init()
	 */
	@Override
	protected Boolean init() {
		try {
			socket = new Socket( host, port );
			Core.fireEvent( new SocketConnectedEvent() );
			Thread conn = new Thread( new ConnectionModule() );
			conn.setDaemon( true );
			conn.start();
		} catch( UnknownHostException e ) {
			return false;
		} catch( IOException e ) {
			return false;
		}
		return true;
	}
	
}
