package org.cluenet.clueservices.ircObjects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public interface IrcSource {
	public abstract Element toXML( Document doc );
}
