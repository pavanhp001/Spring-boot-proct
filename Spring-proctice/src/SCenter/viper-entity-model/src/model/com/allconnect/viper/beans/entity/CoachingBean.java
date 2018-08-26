/**
 *
 */
package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author 
 *
 */

@Entity
@Table(name = "COACHING")
public class CoachingBean implements CommonBeanInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8198299338622557247L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "coachingBeanSequence")
	@SequenceGenerator(name = "coachingBeanSequence", sequenceName = "COACHING_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private long id;

	@Column(name = "COACHING_ID")
	private Long coachingId;

	@Column(name = "COACHING_GROUP_ID")
	private Long coachingGroupId;

	@Column(name = "LINE_ITEM_ID")
	private Long lineItemId;

	@Column(name = "AGENTID")
	private String agentId;
	
	@Transient
	private String coachingType;

	@Transient
	private String coachingDesc;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCoachingId() {
		return coachingId;
	}

	public void setCoachingId(Long coachingId) {
		this.coachingId = coachingId;
	}

	public Long getCoachingGroupId() {
		return coachingGroupId;
	}

	public void setCoachingGroupId(Long coachingGroupId) {
		this.coachingGroupId = coachingGroupId;
	}

	public Long getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(Long lineItemId) {
		this.lineItemId = lineItemId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getCoachingType() {
		return coachingType;
	}

	public void setCoachingType(String coachingType) {
		this.coachingType = coachingType;
	}

	public String getCoachingDesc() {
		return coachingDesc;
	}

	public void setCoachingDesc(String coachingDesc) {
		this.coachingDesc = coachingDesc;
	}
	
	/**
    *
    */
	public CoachingBean() {
	}

}
