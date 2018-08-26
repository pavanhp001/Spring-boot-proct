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
 * @author karteek
 *
 */
@Entity
@Table(name = "uam_group_impacted_area")
@SequenceGenerator(name = "uamGroupImpactedAreaSequence", sequenceName = "uam_group_impacted_area_seq")
public class GroupImpactedArea implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4956196699435968675L;

	@Id
	@Column(name = "group_impacted_area_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uamGroupImpactedAreaSequence")
	private Long groupImpactedAreaId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private AgentGroup agentGroup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "impacted_area_id", nullable = false)
	private ImpactedArea impactedArea;

	public GroupImpactedArea(){
		
	}
	
	public GroupImpactedArea(Long groupImpactedAreaId, AgentGroup agentGroup,
			ImpactedArea impactedArea) {
		this.groupImpactedAreaId = groupImpactedAreaId;
		this.agentGroup = agentGroup;
		this.impactedArea = impactedArea;
	}

	/**
	 * @return the groupImpactedAreaId
	 */
	public Long getGroupImpactedAreaId() {
		return groupImpactedAreaId;
	}

	/**
	 * @param groupImpactedAreaId the groupImpactedAreaId to set
	 */
	public void setGroupImpactedAreaId(Long groupImpactedAreaId) {
		this.groupImpactedAreaId = groupImpactedAreaId;
	}

	/**
	 * @return the agentGroup
	 */
	public AgentGroup getAgentGroup() {
		return agentGroup;
	}

	/**
	 * @param agentGroup the agentGroup to set
	 */
	public void setAgentGroup(AgentGroup agentGroup) {
		this.agentGroup = agentGroup;
	}

	/**
	 * @param impactedArea the impactedArea to set
	 */
	public void setImpactedArea(ImpactedArea impactedArea) {
		this.impactedArea = impactedArea;
	}

	/**
	 * @return the impactedArea
	 */
	public ImpactedArea getImpactedArea() {
		return impactedArea;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final GroupImpactedArea other = (GroupImpactedArea) obj;
		if (getGroupImpactedAreaId() == null) {
			if (other.getGroupImpactedAreaId() != null) {
				return false;
			}
		} else if (!getGroupImpactedAreaId().equals(other.getGroupImpactedAreaId())) {
			return false;
		}
		if ((getGroupImpactedAreaId() == null || getGroupImpactedAreaId().equals(0L))) {
			return super.equals(obj);
		}
		return true;
	}
	
	@Override
	public final int hashCode() {
		if ((getGroupImpactedAreaId() != null && !getGroupImpactedAreaId().equals(0L))) {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((getGroupImpactedAreaId() == null) ? 0 : getGroupImpactedAreaId().hashCode());
			return result;
		}
		return super.hashCode();
	}

}
