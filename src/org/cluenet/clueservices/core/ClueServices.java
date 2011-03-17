/**
 * 
 */
package org.cluenet.clueservices.core;


/**
 * @author cobi
 *
 */
public class ClueServices {
	
	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		ThreadGroup coreGroup = new ThreadGroup( "CoreGroup" );
		Thread core = new Thread( coreGroup, new Core(), "Core" );
		core.start();
	}
	
}
