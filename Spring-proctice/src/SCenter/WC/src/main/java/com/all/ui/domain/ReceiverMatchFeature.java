package com.A.ui.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "RECEIVER_MATCH_FEATURE")
public class ReceiverMatchFeature {

	@Id
	@Column(name = "feature_externalid")
	private String featureExternalId;

	public String getFeatureExternalId() {
		return featureExternalId;
	}

	public void setFeatureExternalId(String featureExternalId) {
		this.featureExternalId = featureExternalId;
	}

	@Override
	public String toString() {
		return "Receivers [featureExternalId=" + featureExternalId + "]";
	}
}
