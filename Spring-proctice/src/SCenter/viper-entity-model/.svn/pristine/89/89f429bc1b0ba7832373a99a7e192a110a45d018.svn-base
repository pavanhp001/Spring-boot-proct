package com.A.V.beans.entity;

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
@Table(name = "uam_user_agent_group")
@SequenceGenerator(name = "uamUserAgentGroupSequence", sequenceName = "uam_user_agent_group_seq")
public class UserAgentGroup implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7593122794218545436L;

	@Id
	@Column(name = "user_agent_group_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uamUserAgentGroupSequence")
	private Long userAgentGroupId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private AgentGroup agentGroup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public UserAgentGroup(){
		
	}
	
	public UserAgentGroup(Long userAgentGroupId, AgentGroup agentGroup,
			User user) {
		this.userAgentGroupId = userAgentGroupId;
		this.agentGroup = agentGroup;
		this.user = user;
	}

	/**
	 * @return the userAgentGroupId
	 */
	public Long getUserAgentGroupId() {
		return userAgentGroupId;
	}

	/**
	 * @param userAgentGroupId the userAgentGroupId to set
	 */
	public void setUserAgentGroupId(Long userAgentGroupId) {
		this.userAgentGroupId = userAgentGroupId;
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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
		final UserAgentGroup other = (UserAgentGroup) obj;
		if (getUserAgentGroupId() == null) {
			if (other.getUserAgentGroupId() != null) {
				return false;
			}
		} else if (!getUserAgentGroupId().equals(other.getUserAgentGroupId())) {
			return false;
		}
		if ((getUserAgentGroupId() == null || getUserAgentGroupId().equals(0L))) {
			return super.equals(obj);
		}
		return true;
	}
	
	@Override
	public final int hashCode() {
		if ((getUserAgentGroupId() != null && !getUserAgentGroupId().equals(0L))) {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((getUserAgentGroupId() == null) ? 0 : getUserAgentGroupId().hashCode());
			return result;
		}
		return super.hashCode();
	}
	
}
