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
@Table(name = "customer_calls_count")
public class CustomerCallsCount implements Serializable{

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "AGENT_ID")
	private String agentId;
	
	@Column(name = "CALL_NUMBER_ID")
	private Integer callNumberId;
	
	@Column(name = "CREATE_DATE")
	private Calendar createDate;

	@Override
	public String toString() {
		return "CustomerCallsCount [id=" + id + ", agentId=" + agentId
				+ ", callNumberId=" + callNumberId + ", createDate="
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

	public Integer getCallNumberId() {
		return callNumberId;
	}

	public void setCallNumberId(Integer callNumberId) {
		this.callNumberId = callNumberId;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

}
