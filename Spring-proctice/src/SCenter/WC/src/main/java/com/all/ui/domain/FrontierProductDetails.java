package com.A.ui.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "frontier_product_details")
public class FrontierProductDetails{

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "external_id")
	private String externalId;
	
	@Column(name = "type")
	private String type;

	@Override
	public String toString() {
		return "FrontierProductDetails [id=" + id + ", externalId="
				+ externalId + ", type=" + type + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}