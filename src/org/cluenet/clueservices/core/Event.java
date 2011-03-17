package org.cluenet.clueservices.core;


public interface Event {
	public void setPrefix( String str );
	public Object getParameters();
	public String getType();
	public String getPrefix();
}
