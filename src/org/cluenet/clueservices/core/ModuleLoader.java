package org.cluenet.clueservices.core;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

public class ModuleLoader extends ClassLoader {
	
	public ModuleLoader() {
		
	}
	
	public ModuleLoader( ClassLoader parent ) {
		super( parent );
	}

	@Override
	public Class< ? > loadClass( String name ) throws ClassNotFoundException {
		if( !Pattern.compile( "^org.cluenet.clueservices.(core.)?modules..*$" ).matcher( name ).matches() )
			return super.loadClass( name );
		try {
			String path = "file:" + "/home/cobi/workspace/ClueServices/bin/" + name.replace( '.', '/' ) + ".class";
			URL url = new URL( path );
			URLConnection conn = url.openConnection();
			InputStream input = conn.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			
			int data = input.read();
			while( data != -1 ) {
				buffer.write( data );
				data = input.read();
			}
			
			input.close();
			
			byte[] classByteCode = buffer.toByteArray();
			
			return defineClass( name, classByteCode, 0, classByteCode.length );
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return null;
	}
	
}
