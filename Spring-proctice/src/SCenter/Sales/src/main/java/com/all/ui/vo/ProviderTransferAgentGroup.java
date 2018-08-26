package com.AL.ui.vo;

 


import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author hestacio
 * 
 */

@Entity
@Table(name = "PROVIDER_TRANSFER_AGENTGROUP")
public class ProviderTransferAgentGroup implements Serializable {


	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "PROVIDER_IDS")
	private String providerId;
	
	@Column(name = "AGENT_GROUP")
	private String agentGroup;
	
	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getAgentGroup() {
		return agentGroup;
	}

	public void setAgentGroup(String agentGroup) {
		this.agentGroup = agentGroup;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	@Override
	public String toString() {
		return "provider_transfer_agentGroup [id=" + id + ",agentGroup=" + agentGroup + "]";
	}

	 
	 

	 
	
	
	

}
