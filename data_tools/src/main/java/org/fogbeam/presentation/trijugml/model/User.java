package org.fogbeam.presentation.trijugml.model;

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
@Table(name="users")
public class User 
{
		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column( name="username")
	private String userName;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="state_id")	
	private State state;
	
	
	
	public Long getId()
	{
		return id;
	}

	
	public void setId( Long id )
	{
		this.id = id;
	}


	public String getUserName()
	{
		return userName;
	}


	public void setUserName( String userName )
	{
		this.userName = userName;
	}


	public State getState()
	{
		return state;
	}


	public void setState( State state )
	{
		this.state = state;
	}


	public String toString() 
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
