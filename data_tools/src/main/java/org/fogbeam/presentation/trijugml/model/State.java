package org.fogbeam.presentation.trijugml.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table( name="states")
public class State implements Serializable
{
	/*

		fips_code int,
		name text,
		abbreviation text
	*/
	
	@Id
	@Column(name = "fips_code", updatable = false, nullable = false)
	private Long id;
	
	
	@Column( name="name")
	private String name;
	
	@Column( name="abbreviation")
	private String abbreviation;
	
		
	
	public Long getId()
	{
		return id;
	}


	public void setId( Long id )
	{
		this.id = id;
	}


	public String getName()
	{
		return name;
	}


	public void setName( String name )
	{
		this.name = name;
	}


	public String getAbbreviation()
	{
		return abbreviation;
	}


	public void setAbbreviation( String abbreviation )
	{
		this.abbreviation = abbreviation;
	}


	public String toString() 
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
