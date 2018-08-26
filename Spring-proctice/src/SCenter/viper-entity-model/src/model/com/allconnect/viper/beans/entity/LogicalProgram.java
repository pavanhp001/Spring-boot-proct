package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table ( name = "PROGRAM")
public class LogicalProgram implements CommonBeanInterface{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8957888177417242383L;

	@Id
	@Column(name = "ID")
	private long id;
	
	@Column(name = "EXTERNALID")
	private String externalId;
	
	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(name ="CHANNEL")
	private String channel;
	
	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}
}
