package com.A.ui.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "hughesnet_served_up_data")
public class HughesNetServedUpData implements Serializable{

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "guid")
	private String guid;
	
	@Column(name = "se2_pass")
	private String se2Pass;
	
	@Column(name = "product_display")
	private String productDisplay;
	
	@Column(name = "se2_fail_reason")
	private String se2FailReason;
	
	@Column(name = "CREATE_DATE")
	private Calendar createDate;
	
	public String getProductDisplay() {
		return productDisplay;
	}

	public void setProductDisplay(String productDisplay) {
		this.productDisplay = productDisplay;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getSe2Pass() {
		return se2Pass;
	}

	public void setSe2Pass(String se2Pass) {
		this.se2Pass = se2Pass;
	}

	public String getSe2FailReason() {
		return se2FailReason;
	}

	public void setSe2FailReason(String se2FailReason) {
		this.se2FailReason = se2FailReason;
	}

	
}