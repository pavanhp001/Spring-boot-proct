package com.A.ui.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DRUPAL_DIALOGUE_FLOW")
public class DrupalDialogueEntity {

	@Id
	@Column(name = "FLOW_TYPE")
	private String referrerFlowType;
	
	@Column(name = "DRUPAL")
	private String displayValue;

	public String getReferrerFlowType() {
		return referrerFlowType;
	}

	public void setReferrerFlowType(String referrerFlowType) {
		this.referrerFlowType = referrerFlowType;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
  	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "DrupalDialogueEntity [referrerFlowType="+referrerFlowType+", displayValue="+displayValue+"]";
	}
}
