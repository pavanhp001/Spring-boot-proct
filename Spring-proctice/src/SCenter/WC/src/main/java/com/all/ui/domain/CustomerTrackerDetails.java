package com.A.ui.domain;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 * 
 */

@Entity
@Table(name = "customer_tracker_details")
public class CustomerTrackerDetails implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "AGENT_ID")
	private String agentId;
	
	@Column(name = "CONCERT_CALL_COUNT")
	private Integer concertCallCount;
	
	@Column(name = "ACTUAL_CALL_COUNT")
	private Integer actualCallCount;

	@Column(name = "UTILITY_PITCHED_COUNT")
	private Integer utilityPitchedCount;
	
	@Column(name = "UTILITY_POINTS")
	private float utilityPoints;
	
	@Column(name = "CREATE_DATE")
	private Calendar createDate;

	@Override
	public String toString() {
		return "CustomerTrackerDetails [id=" + id + ", agentId=" + agentId
				+ ", concertCallCount=" + concertCallCount
				+ ", actualCallCount=" + actualCallCount
				+ ", utilityPitchedCount=" + utilityPitchedCount
				+ ", utilityPoints=" + utilityPoints + ", createDate="
				+ createDate + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public Integer getConcertCallCount() {
		return concertCallCount;
	}

	public void setConcertCallCount(Integer concertCallCount) {
		this.concertCallCount = concertCallCount;
	}

	public Integer getActualCallCount() {
		return actualCallCount;
	}

	public void setActualCallCount(Integer actualCallCount) {
		this.actualCallCount = actualCallCount;
	}

	public Integer getUtilityPitchedCount() {
		return utilityPitchedCount;
	}

	public void setUtilityPitchedCount(Integer utilityPitchedCount) {
		this.utilityPitchedCount = utilityPitchedCount;
	}

	public float getUtilityPoints() {
		return utilityPoints;
	}

	public void setUtilityPoints(float utilityPoints) {
		this.utilityPoints = utilityPoints;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}


}
