/**
 * 
 */
package org.cluenet.clueservices.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.cluenet.clueservices.core.events.SocketEOFEvent;
import org.cluenet.clueservices.core.modules.SocketModule;
import org.cluenet.clueservices.misc.Config;


/**
 * @author cobi
 *
 */
public class Core implements Runnable {
	private static ArrayBlockingQueue< Event > queue = new ArrayBlockingQueue< Event >( 100 );
	private Map< String, IModule > modules = Collections.synchronizedMap( new HashMap< String, IModule >() );
	private Boolean done = false;
	private ModuleLoader moduleLoader;
	
	public static synchronized void fireEvent( Event event ) {
		//System.out.println( "[Core] Event recv: " + event.toString() );
		queue.add( event );
	}
	
	public void startModule( IModule module ) {
		module.start();
		insertModule( module );
	}
	
	public void insertModule( IModule module ) {
		modules.put( module.name(), module );
	}
	
	public void stopModule( IModule module ) {
		modules.remove( module.name() );
		module.stop();
	}
	
	@SuppressWarnings( "unchecked" )
	public void startModule( String name ) {
		Class< IModule > moduleClass;
		try {
			moduleClass = (Class< IModule >) moduleLoader.loadClass( name );
			IModule module = moduleClass.newInstance();
			startModule( module );
		} catch( ClassNotFoundException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch( InstantiationException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch( IllegalAccessException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopModule( String name ) {
		IModule module = modules.remove( name );
		module.stop();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		ClassLoader loader = ModuleLoader.class.getClassLoader();
		moduleLoader = new ModuleLoader( loader );
		
		startModule( "org.cluenet.clueservices.modules.services.TestingModule" );
		startModule( "org.cluenet.clueservices.modules.protocol.UnrealIRCdProtocolModule" );
		startModule( new SocketModule( Config.get( "server", "ip" ), Integer.parseInt( Config.get( "server", "port" ), 10 ) ) );
		
		while( !done )
			try {
				Event e = queue.take();
				//System.out.println( "[Core] Event sending: " + e.toString() );
				for( IModule module : modules.values() )
					module.notifyEvent( e );
				//System.out.println( "[Core] Event sent: " + e.toString() );
				if( e instanceof SocketEOFEvent )
					done = true;
			} catch( InterruptedException e1 ) {
			}
	}
	
}
