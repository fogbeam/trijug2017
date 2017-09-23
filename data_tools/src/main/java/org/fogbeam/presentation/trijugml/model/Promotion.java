package org.fogbeam.presentation.trijugml.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name="promotions")
public class Promotion implements Serializable
{
	/*
  		id bigint,
  		primary_color text,
  		secondary_color text,
  		offer_type_id bigint
	*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column( name="primary_color")
	private String primaryColor;
	
	@Column( name="secondary_color")
	private String secondaryColor;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="offer_type_id")	
	private OfferType offerType;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="offer_category_id")
	private OfferProductCategory offerProductCategory;
	
	
	
	public Long getId()
	{
		return id;
	}

	
	public void setId( Long id )
	{
		this.id = id;
	}


	public String getPrimaryColor()
	{
		return primaryColor;
	}

	
	public void setPrimaryColor( String primaryColor )
	{
		this.primaryColor = primaryColor;
	}


	public String getSecondaryColor()
	{
		return secondaryColor;
	}


	public void setSecondaryColor( String secondaryColor )
	{
		this.secondaryColor = secondaryColor;
	}


	public OfferType getOfferType()
	{
		return offerType;
	}


	public void setOfferType( OfferType offerType )
	{
		this.offerType = offerType;
	}

	
	public OfferProductCategory getOfferProductCategory()
	{
		return offerProductCategory;
	}
	
	
	public void setOfferProductCategory(
			OfferProductCategory offerProductCategory )
	{
		this.offerProductCategory = offerProductCategory;
	}

	public String toString() 
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
