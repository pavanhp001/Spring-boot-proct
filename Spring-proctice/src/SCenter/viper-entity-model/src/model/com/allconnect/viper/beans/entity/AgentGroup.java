package com.A.V.beans.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * @author karteek
 *
 */
@Entity
@Table(name = "uam_agent_group", uniqueConstraints = @UniqueConstraint(columnNames = "group_name"))
@SequenceGenerator(name = "uamAgentGroupSequence", sequenceName = "uam_agent_group_seq")
public class AgentGroup implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6239267995836820681L;

	@Id
	@Column(name = "group_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uamAgentGroupSequence")
	private Long groupId;
	
	@Column(name = "group_name", unique = true, nullable = false, length = 65)
	private String groupName;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", nullable = false, length = 13)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", nullable = false, length = 13)
	private Date endDate;
	
	@Column(name = "group_description", nullable = false, length = 500)
	private String groupDescription;
	
	@Column(name = "created_by", length = 65)
	private String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", length = 29)
	private Date createdDate;
	
	@Column(name = "last_modified_by", length = 65)
	private String lastModifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified_date", length = 29)
	private Date lastModifiedDate;
	
	public AgentGroup() {
	}

	public AgentGroup(Long groupId, String groupName, Date startDate,
			Date endDate, String groupDescription, String createdBy,
			Date createdDate, String lastModifiedBy, Date lastModifiedDate) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.groupDescription = groupDescription;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}


	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the groupDescription
	 */
	public String getGroupDescription() {
		return groupDescription;
	}

	/**
	 * @param groupDescription the groupDescription to set
	 */
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
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
		final AgentGroup other = (AgentGroup) obj;
		if (getGroupId() == null) {
			if (other.getGroupId() != null) {
				return false;
			}
		} else if (!getGroupId().equals(other.getGroupId())) {
			return false;
		}
		if ((getGroupId() == null || getGroupId().equals(0L))) {
			return super.equals(obj);
		}
		return true;
	}
	
	@Override
	public final int hashCode() {
		if ((getGroupId() != null && !getGroupId().equals(0L))) {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
			return result;
		}
		return super.hashCode();
	}
	
}
