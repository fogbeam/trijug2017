package org.fogbeam.presentation.trijugml.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name="offer_types")
public class OfferType
{
	
	/*
		id bigint,
		offer_type_name text
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column( name="offer_type_name")
	private String offerTypeName;
		
	
	public Long getId()
	{
		return id;
	}


	public void setId( Long id )
	{
		this.id = id;
	}


	public String getOfferTypeName()
	{
		return offerTypeName;
	}


	public void setOfferTypeName( String offerTypeName )
	{
		this.offerTypeName = offerTypeName;
	}


	public String toString() 
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
