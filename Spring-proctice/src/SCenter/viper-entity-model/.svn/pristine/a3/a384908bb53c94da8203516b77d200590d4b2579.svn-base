/**
 * 
 */
package com.A.V.beans.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author augaas
 * 
 */
@Entity
@Table(name = "UAM_USER_RESOURCE")
@SequenceGenerator(name = "userResourceSequence", sequenceName = "USER_RESOURCE_SEQUENCE")
public class UserResource implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7290206288455497527L;

	@Id
	@Column(name = "USER_RESOURCE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userResourceSequence")
	private long userResourceId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RESOURCE_ID", nullable = false)
	private Resource resource;
	
	@Column(name = "USER_LEVEL")
	private Integer userLevel;

	public long getUserResourceId() {
		return userResourceId;
	}

	public void setUserResourceId(long userResourceId) {
		this.userResourceId = userResourceId;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}



	
}
