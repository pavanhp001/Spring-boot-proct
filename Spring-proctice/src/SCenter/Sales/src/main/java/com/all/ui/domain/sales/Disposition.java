package com.AL.ui.domain.sales;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.AL.V.domain.IProvider;
import com.AL.V.domain.User;

/**
 * @author 
 * 
 */

@Entity
@Table(name = "DISPOSITION")
public class Disposition implements Serializable{

	@Id
	@Column(name = "DISPOSITIONID")
	private String dispositionId;

	@Column(name = "CODE")
	private String code;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ACTIVE")
	private Boolean active;


	@Column(name = "SEQUENCE_ORDER")
	private String sequenceOrder;

	 

	public static Disposition create(String dispositionId, String code, String description, Boolean active, String sequenceOrder) {

		Disposition disposition = new Disposition();
		disposition.setDispositionId(dispositionId);
		disposition.setCode(code);
		disposition.setDescription(description);
		disposition.setActive(active);
		disposition.setSequenceOrder(sequenceOrder);
		return disposition;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDispositionId() {
		return dispositionId;
	}


	public void setDispositionId(String dispositionId) {
		this.dispositionId = dispositionId;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getSequenceOrder() {
		return sequenceOrder;
	}


	public void setSequenceOrder(String sequenceOrder) {
		this.sequenceOrder = sequenceOrder;
	}
	
	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	@Override
	public String toString() {
		return "Disposition [dispositionId=" + dispositionId + ", code=" + code + ", description="
				+ description + ", active=" + active + ", sequenceOrder="
				+ sequenceOrder	+ "]";
	}

}
