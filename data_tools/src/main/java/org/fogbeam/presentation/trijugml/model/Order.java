package org.fogbeam.presentation.trijugml.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name="orders")
public class Order implements Serializable
{
	
	
	/*
  id bigint,
  user_id bigint,
  promotion_id bigint,
  total_amount bigint,
  submitted_time timestamp,
  promotion_added boolean
	 */
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="total_amount")
	private Long totalAmount;
	
	@Column( name="submitted_time")
	private Date submittedTime; 
	
	@Column( name="promotion_added")
	private Boolean promotionAdded;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="promotion_id")	
	private Promotion promotion;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")	
	private User user;
	
	
	
	public Long getId()
	{
		return id;
	}



	public void setId( Long id )
	{
		this.id = id;
	}



	public Long getTotalAmount()
	{
		return totalAmount;
	}


	public void setTotalAmount( Long totalAmount )
	{
		this.totalAmount = totalAmount;
	}


	public Date getSubmittedTime()
	{
		return submittedTime;
	}


	public void setSubmittedTime( Date submittedTime )
	{
		this.submittedTime = submittedTime;
	}


	public Boolean getPromotionAdded()
	{
		return promotionAdded;
	}


	public void setPromotionAdded( Boolean promotionAdded )
	{
		this.promotionAdded = promotionAdded;
	}


	public Promotion getPromotion()
	{
		return promotion;
	}


	public void setPromotion( Promotion promotion )
	{
		this.promotion = promotion;
	}


	public User getUser()
	{
		return user;
	}


	public void setUser( User user )
	{
		this.user = user;
	}


	public String toString() 
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
