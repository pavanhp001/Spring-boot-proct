package com.A.ui.domain;

 


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
@Table(name = "DF_DIALOG")
public class Dialog implements Serializable {


	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "DIALOG_ID")
	private Long dialogId;
	
	@Column(name = "USE_ENVIRONMENT")
	private String useEnvironment;
	
	@Column(name = "AGENT_TYPE")
	private String agentType;
	
	@Column(name = "VERSION_MAJOR")
	private int versionMajor;
	
	@Column(name = "VERSION_MINOR")
	private int versionMinor;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getDialogId() {
		return dialogId;
	}

	public void setDialogId(Long dialogId) {
		this.dialogId = dialogId;
	}

	public String getUseEnvironment() {
		return useEnvironment;
	}

	public void setUseEnvironment(String useEnvironment) {
		this.useEnvironment = useEnvironment;
	}
	
	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}
	
	public int getVersionMajor() {
		return versionMajor;
	}
	
	public void setVersionMajor(int versionMajor) {
		this.versionMajor = versionMajor;
	}
	
	public int getVersionMinor() {
		return versionMinor;
	}
	
	public void setVersionMinor(int versionMinor) {
		this.versionMinor = versionMinor;
	}	
	
	@Override
	public String toString() {
		return "Concert dialog [id=" + id + "]";
	}

	 
}
