package org.cluenet.clueservices.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public interface Event {
	public void setPrefix( String str );
	public Object getParameters();
	public String getType();
	public String getPrefix();
	public Element toXML( Document doc );
}
