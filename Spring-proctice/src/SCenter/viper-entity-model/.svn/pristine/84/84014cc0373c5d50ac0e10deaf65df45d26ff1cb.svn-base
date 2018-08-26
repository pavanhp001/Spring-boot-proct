package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table( name = "saleschannel" )
public class SalesChannel implements CommonBeanInterface {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8530884252402550699L;
	
	@Id
    @GeneratedValue( generator = "channelSequence" )
    @SequenceGenerator( name = "channelSequence", sequenceName = "SALESCHANNEL_SEQ" )
	private long id;
	private String description;
	private String externalId;

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getExternalId() {
		return externalId;
	}


	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	
}
