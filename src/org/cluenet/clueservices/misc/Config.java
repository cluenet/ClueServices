package org.cluenet.clueservices.misc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Config {
	private Map< String, Map< String, String > > map = new HashMap< String, Map< String, String > >();
	private static Config cfg = new Config();
	
	private Config() {
		Map< String, String > server = new HashMap< String, String >(); // Hack until better solution.
		server.put( "ip", "theta.internal.cluenet.org" );
		server.put( "port", "6667" );
		try {
			BufferedReader passBR = new BufferedReader( new FileReader( "/home/cobi/clueservices.password" ) );
			server.put( "pass", passBR.readLine() );
		} catch( IOException e ) {
			e.printStackTrace();
		}
		server.put( "name", "clueservices.cluenet.org" );
		server.put( "numeric", "50" );
		server.put( "description", "ClueServices IRC Services - Version: GIT.MASTER.HEAD - Maintainer: Cobi" );
		map.put( "server", server );
	}

	public static String get( String group, String key ) {
		return cfg.map.get( group ).get( key );
	}
}
