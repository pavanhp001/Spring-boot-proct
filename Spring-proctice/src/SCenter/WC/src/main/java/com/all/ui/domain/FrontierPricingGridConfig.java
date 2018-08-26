package com.A.ui.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "frontier_pricing_grid_config")
public class FrontierPricingGridConfig{

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "state")
	private String state;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "file")
	private String file;

	@Override
	public String toString() {
		return "FrontierPricingGridConfig [id=" + id + ", state=" + state
				+ ", type=" + type + ", file=" + file + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}