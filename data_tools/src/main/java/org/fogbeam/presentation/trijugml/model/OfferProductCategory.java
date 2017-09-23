package org.fogbeam.presentation.trijugml.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name="offer_product_categories")
public class OfferProductCategory implements Serializable
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column( name="offer_product_category_name")
	private String offerProductCategoryName;
	
	
	
	public Long getId()
	{
		return id;
	}


	public void setId( Long id )
	{
		this.id = id;
	}


	public String getOfferProductCategoryName()
	{
		return offerProductCategoryName;
	}


	public void setOfferProductCategoryName( String offerProductCategoryName )
	{
		this.offerProductCategoryName = offerProductCategoryName;
	}


	public String toString() 
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
