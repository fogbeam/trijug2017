package org.fogbeam.experimental.storm;

import java.io.Serializable;

public class TextHolder implements Serializable
{
	private String text;
	
	public TextHolder()
	{}
	
	public TextHolder( final String text )
	{
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText( String text )
	{
		this.text = text;
	}
	
}
