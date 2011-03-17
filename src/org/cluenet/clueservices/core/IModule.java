package org.cluenet.clueservices.core;


public interface IModule {
	public void start();
	public void notifyEvent( Event event );
	public void stop();
	public String name();
}
