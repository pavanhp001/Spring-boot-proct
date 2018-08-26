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
 * @author karteek
 *
 */

@Entity
@Table(name = "uam_impacted_area")
@SequenceGenerator(name = "uamImpactedAreaSequence", sequenceName = "uam_impacted_area_seq")
public class ImpactedArea implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7005780021017339555L;

	@Id
	@Column(name = "impacted_area_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uamImpactedAreaSequence")
	private Long impactedAreaId;
	
	@Column(name = "impacted_area_name", nullable = false, length = 65)
	private String impactedAreaName;

	/**
	 * @return the impactedAreaId
	 */
	public Long getImpactedAreaId() {
		return impactedAreaId;
	}

	/**
	 * @param impactedAreaId the impactedAreaId to set
	 */
	public void setImpactedAreaId(Long impactedAreaId) {
		this.impactedAreaId = impactedAreaId;
	}

	/**
	 * @return the impactedAreaName
	 */
	public String getImpactedAreaName() {
		return impactedAreaName;
	}

	/**
	 * @param impactedAreaName the impactedAreaName to set
	 */
	public void setImpactedAreaName(String impactedAreaName) {
		this.impactedAreaName = impactedAreaName;
	}
}
