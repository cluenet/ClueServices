package org.cluenet.clueservices.misc;

import java.util.Iterator;


public class Strings {
	public static String join( Iterable<?> src, CharSequence pattern ) {
		Iterator<?> it = src.iterator();
		StringBuilder dst = new StringBuilder();
		if( it.hasNext() )
			dst.append( it.next() );
		while( it.hasNext() )
			dst.append( pattern ).append( it.next() );
		return dst.toString();
	}
}
