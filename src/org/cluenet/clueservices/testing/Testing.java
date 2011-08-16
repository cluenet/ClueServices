package org.cluenet.clueservices.testing;

import java.io.IOException;

import javax.script.ScriptException;

import com.caucho.quercus.QuercusEngine;


public class Testing {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ScriptException 
	 */
	public static void main( String[] args ) throws IOException {
		QuercusEngine qe = new QuercusEngine();
		qe.execute( "<?PHP import java.lang.System; System->out->println( 'Hello' ); ?>" );
	}
	
}
