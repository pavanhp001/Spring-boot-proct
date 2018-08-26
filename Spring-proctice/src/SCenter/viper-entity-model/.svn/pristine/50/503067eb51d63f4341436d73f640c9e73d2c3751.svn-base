/**
 * 
 */
package com.A.V.beans.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author augaas
 * 
 */
@Entity
@Table(name = "UAM_RESOURCE")
@SequenceGenerator(name = "resourceSequence", sequenceName = "RESOURCE_SEQUENCE")
public class Resource implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2234569310373269922L;

	@Id
	@Column(name = "RESOURCE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resourceSequence")
	long resourceId;
	
	@Column(name = "RESOURCE_NAME")
	String resourceName;
	
	@Column(name = "RESOURCE_DESC")
	String resourceDesc;
	
	@Column(name = "RESOURCE_RULE")
	String resourceRule;
	
	@Column(name = "PROVIDER_EXTERNAL_ID")
	String providerExternalId;


	/**
	 * @return the resourceId
	 */
	
	public long getResourceId() {
		return resourceId;
	}


	/**
	 * @return the resourceEntityId
	 */
	
	public String getProviderExternalId() {
		return providerExternalId;
	}

	/**
	 * @return the resourceDesc
	 */
	public String getResourceDesc() {
		return resourceDesc;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @return the resourceRule
	 */
	public String getResourceRule() {
		return resourceRule;
	}


	/**
	 * @param resourceId
	 *            the resourceId to set
	 */
	public void setResourceId(long id) {
		this.resourceId = id;
	}


	/**
	 * @param resourceEntityId
	 *            the resourceEntityId to set
	 */
	public void setProviderExternalId(String providerEntityId) {
		this.providerExternalId = providerEntityId;
	}

	/**
	 * @param resourceDesc
	 *            the resourceDesc to set
	 */
	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	/**
	 * @param resourceName
	 *            the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @param resourceRule
	 *            the resourceRule to set
	 */
	public void setResourceRule(String resourceRule) {
		this.resourceRule = resourceRule;
	}
}
