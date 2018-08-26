package com.A.V.beans.entity;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author seshagirirao.k
 */
@Entity
@Table(name = "uam_agent_group_audit")
@SequenceGenerator(name = "uamAgentGroupAuditSequence", sequenceName = "uam_agent_group_audit_seq")
public class AgentGroupAudit implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 9136604413352641513L;

	@Id
	@Column(name = "agent_group_audit_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uamAgentGroupAuditSequence")
	private Long agentGroupAuditId;
	
	@Column(name = "value_changed", nullable = false, length = 65)
	private String valueChanged;
	
	@Column(name = "old_value", nullable = true)
	private String oldValue;
	
	@Column(name = "new_value", nullable = true)
	private String newValue;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date_time")
	private Date modifiedDateTime;
	
	@Column(name = "modified_user", nullable = false, length = 65)
	private String modifiedUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private AgentGroup agentGroup;
	
	public AgentGroupAudit() {
	}
	
	/**
	 * @param agentGroupAuditId
	 * @param valueChanged
	 * @param oldValue
	 * @param newValue
	 * @param modifiedDateTime
	 * @param modifiedUser
	 * @param agentGroup
	 */
	public AgentGroupAudit(Long agentGroupAuditId, String valueChanged,
			String oldValue, String newValue, Date modifiedDateTime,
			String modifiedUser, AgentGroup agentGroup) {
		super();
		this.agentGroupAuditId = agentGroupAuditId;
		this.valueChanged = valueChanged;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.modifiedDateTime = modifiedDateTime;
		this.modifiedUser = modifiedUser;
		this.agentGroup = agentGroup;
	}

	
	/**
	 * @return the agentGroupAuditId
	 */
	public Long getAgentGroupAuditId() {
		return agentGroupAuditId;
	}

	/**
	 * @param agentGroupAuditId the agentGroupAuditId to set
	 */
	public void setAgentGroupAuditId(Long agentGroupAuditId) {
		this.agentGroupAuditId = agentGroupAuditId;
	}

	/**
	 * @return the valueChanged
	 */
	public String getValueChanged() {
		return valueChanged;
	}

	/**
	 * @param valueChanged the valueChanged to set
	 */
	public void setValueChanged(String valueChanged) {
		this.valueChanged = valueChanged;
	}

	/**
	 * @return the oldValue
	 */
	public String getOldValue() {
		return oldValue;
	}

	/**
	 * @param oldValue the oldValue to set
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	/**
	 * @return the newValue
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/**
	 * @return the modifiedDateTime
	 */
	public Date getModifiedDateTime() {
		return modifiedDateTime;
	}


	/**
	 * @param modifiedDateTime the modifiedDateTime to set
	 */
	public void setModifiedDateTime(Date modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	/**
	 * @return the modifiedUser
	 */
	public String getModifiedUser() {
		return modifiedUser;
	}

	/**
	 * @param modifiedUser the modifiedUser to set
	 */
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
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
		final AgentGroupAudit other = (AgentGroupAudit) obj;
		if (getAgentGroupAuditId() == null) {
			if (other.getAgentGroupAuditId() != null) {
				return false;
			}
		} else if (!getAgentGroupAuditId().equals(other.getAgentGroupAuditId())) {
			return false;
		}
		if ((getAgentGroupAuditId() == null || getAgentGroupAuditId().equals(0L))) {
			return super.equals(obj);
		}
		return true;
	}
	
	@Override
	public final int hashCode() {
		if ((getAgentGroupAuditId() != null && !getAgentGroupAuditId().equals(0L))) {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((getAgentGroupAuditId() == null) ? 0 : getAgentGroupAuditId().hashCode());
			return result;
		}
		return super.hashCode();
	}
	
}
