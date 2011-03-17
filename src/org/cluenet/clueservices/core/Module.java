package org.cluenet.clueservices.core;

import java.util.concurrent.ArrayBlockingQueue;


public abstract class Module implements Runnable, IModule {
	private static ThreadGroup tg = null;
	private static Integer i = 0;
	private ArrayBlockingQueue< Event > queue = new ArrayBlockingQueue< Event >( 10000 );
	private Thread self = null;
	protected String eventPrefix = "";
	private volatile boolean shouldStop = false;
	
	public Module() {
		this.eventPrefix = "";
	}
	
	public Module( String eventPrefix ) {
		this.eventPrefix = eventPrefix;
	}
	
	protected void fireEvent( Event event ) {
		event.setPrefix( eventPrefix );
		Core.fireEvent( event );
	}
	
	public Thread getThread() {
		return self;
	}

	public void notifyEvent( Event event ) {
		this.queue.add( event );
	}
	
	public void start() {
		if( tg == null )
			tg = new ThreadGroup( "Modules" );
		String name = this.getClass().getCanonicalName();
		if( name == null )
			name = "Anonymous-" + i++;
		self = new Thread( tg, this, "Module-" + name );
		self.setDaemon( true );
		self.start();
	}
	
	public void stop() {
		shouldStop = true;
		if( self.isAlive() )
			self.interrupt();
		while( self.isAlive() );
	}
	
	public String name() {
		String name = this.getClass().getCanonicalName();
		if( name.equals( "" ) )
			name = "Anonymous-" + i++;
		return name;
	}
	
	@Override
	public void run() {
		if( !init() )
			return;
		while( !shouldStop )
			try {
				this.event( this.queue.take() );
			} catch( InterruptedException e ) {
			}
	}

	protected abstract Boolean init();
	protected abstract void event( Event take );
	
}
