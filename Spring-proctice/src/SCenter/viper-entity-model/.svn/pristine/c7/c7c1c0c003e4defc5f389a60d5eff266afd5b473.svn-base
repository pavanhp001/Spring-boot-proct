/**
 * 
 */
package com.A.V.beans.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author augaas
 * 
 */
@Entity
@Table(name = "UAM_PROGRAM")
@SequenceGenerator(name = "programSequence", sequenceName = "PROGRAM_SEQUENCE")
public class Program implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -24040814862504210L;
	
	long programId;
	String programName;
	String clientId;
	String programDesc;
	Date programStartDate;
	Date programStopDate;
	int changeStamp;

	/**
	 * @return the changeStamp
	 */
	@Column(name = "CHANGE_STAMP")
	public int getChangeStamp() {
		return changeStamp;
	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "PROGRAM_ID")
	//@GeneratedValue(strategy = GenerationType.AUTO) 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "programSequence")
	public long getProgramId() {
		return programId;
	}

	/**
	 * @return the programDesc
	 */
	@Column(name = "PROGRAM_DESC")
	public String getProgramDesc() {
		return programDesc;
	}

	/**
	 * @return the programName
	 */
	@Column(name = "PROGRAM_NAME")
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param changeStamp
	 *            the changeStamp to set
	 */
	public void setChangeStamp(int changeStamp) {
		this.changeStamp = changeStamp;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setProgramId(long programId) {
		this.programId = programId;
	}

	@Column(name = "CLIENT_ID")
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Column(name = "PROGRAM_START_DT")
	public Date getProgramStartDate() {
		return programStartDate;
	}

	public void setProgramStartDate(Date programStartDate) {
		this.programStartDate = programStartDate;
	}

	
	@Column(name = "PROGRAM_STOP_DT")
	public Date getProgramStopDate() {
		return programStopDate;
	}

	public void setProgramStopDate(Date programStopDate) {
		this.programStopDate = programStopDate;
	}

	/**
	 * @param programDesc
	 *            the programDesc to set
	 */
	public void setProgramDesc(String programDesc) {
		this.programDesc = programDesc;
	}

	/**
	 * @param programName
	 *            the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}
}
