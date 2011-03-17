package org.cluenet.clueservices.modules.utility;

import java.util.ArrayList;
import java.util.List;


public class ParsedIRCLine {
	public enum Type { RELAYED, DIRECT }
	
	public Type type;
	public List< String > rawPieces;
	public String source;
	public String command;
	public String target;
	public List< String > pieces;
	public String raw;
	
	public ParsedIRCLine( String input ) {
		raw = input;
		
		if( raw.charAt( 0 ) == ':' )
			type = Type.RELAYED;
		else
			type = Type.DIRECT;
		
		rawPieces = split( raw.substring( type.equals( Type.RELAYED ) ? 1 : 0 ) );
		
		if( type.equals( Type.RELAYED ) ) {
			if( rawPieces.size() > 0 )
				source = rawPieces.get( 0 );
			else
				source = "";
			if( rawPieces.size() > 1 )
				command = rawPieces.get( 1 ).toLowerCase();
			else
				command = "";
			if( rawPieces.size() > 2 )
				target = rawPieces.get( 2 );
			else
				target = "";
			if( rawPieces.size() > 3 )
				pieces = rawPieces.subList( 3, rawPieces.size() );
			else
				pieces = new ArrayList< String >();
		} else {
			source = "Server";
			target = "You";
			if( rawPieces.size() > 0 )
				command = rawPieces.get( 0 ).toLowerCase();
			else
				command = "";
			if( rawPieces.size() > 1 )
				pieces = rawPieces.subList( 1, rawPieces.size() );
			else
				pieces = new ArrayList< String >();
		}
	}
	
	private List< String > split( String line ) {
		List< String > pieces = new ArrayList< String >();
		StringBuilder temp = new StringBuilder();
		Boolean quotes = false;
		Boolean done = false;
		
		for( char c : line.toCharArray() )
			if( ( quotes && c != '"' ) || done )
				temp.append( c );
			else
				switch( c ) {
					case ' ':
						pieces.add( temp.toString() );
						temp = new StringBuilder();
						break;
					case '"':
						if( quotes || temp.length() == 0 ) {
							quotes = !quotes;
							break;
						}
					case ':':
						if( temp.length() == 0 ) {
							done = true;
							break;
						}
					default:
							temp.append( c );
				}
		
		if( temp.length() != 0 )
			pieces.add( temp.toString() );
		
		return pieces;
	}
	
	public String toString() {
		return "(ParsedIRCLine) { "
			+ "type: " + type.toString() + "; "
			+ "rawPieces: " + rawPieces.toString() + "; "
			+ "source: " + source + "; "
			+ "command: " + command + "; "
			+ "target: " + target + "; "
			+ "pieces: " + pieces.toString() + "; "
			+ "raw: " + raw + "; "
			+ "};";
	}
}
