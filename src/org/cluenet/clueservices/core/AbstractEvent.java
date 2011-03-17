package org.cluenet.clueservices.core;


public abstract class AbstractEvent implements Event {
	protected String prefix;
	
	@Override
	public String getPrefix() {
		return prefix;
	}
	
	@Override
	public void setPrefix( String str ) {
		prefix = str;
	}
	
	public String getType() {
		String name = this.getClass().getSimpleName();
		if( name == null )
			return "";
		return name;
	}
	
	public String toString() {
		if( getParameters() != null )
			return getType() + ":" + getParameters().toString();
		else
			return getType();
	}

}
