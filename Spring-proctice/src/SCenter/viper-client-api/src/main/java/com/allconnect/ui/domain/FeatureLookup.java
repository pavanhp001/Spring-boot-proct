package com.A.ui.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FEATURE_LOOKUP")
public class FeatureLookup {
	private static final long serialVersionUID = 553127845394L;

	@Id
	@GeneratedValue(generator = "featureLookupSeq")
	@SequenceGenerator(name = "featureLookupSeq", sequenceName = "feature_lookup_seq")
	private long id;
  
	@Column(name = "PROMOTIONID")
	private String promotionId;
	
	@Column(name = "FEATUREID")
	private String featureId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

}
