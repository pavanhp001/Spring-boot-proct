package com.A.ui.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

import java.util.Date;

@Entity
@Table(name = "GROSS_COMMISSIONABLE_REVENUE")
public class GrossCommissionableRevenue implements Serializable{
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "SPR_ID")
	private String sprId;

	@Column(name = "SPR_NAME")
	private String sprName;

	@Column(name = "SP_ID_STABLE")
	private String spIdStable;

	@Column(name = "SP_NAME")
	private String spName;

	@Column(name = "POINTS")
	private Float points;	
	
	@Column(name = "REV_COMMISSION")
	private Integer revCommission;
	
	@Column(name = "STATUS_RULE_ID_NET")
	private Integer statusRuleIdNet;
	
	@Column(name = "STATUS_RULE_ID_PROVISION")
	private Integer statusRuleIdProvision;
	
	@Column(name = "STATUS_RULE_ID_COMMISSION")
	private Integer statusRuleIdCommission;
	
	@Column(name = "STATUS_RULE_ID_TERMINAL")
	private Integer statusRuleIdTerminal;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getSprId() {
		return sprId;
	}

	public void setSprId(String sprId) {
		this.sprId = sprId;
	}	
	
	public String getSprName() {
		return sprName;
	}
	
	public void setSprName(String sprName) {
		this.sprName = sprName;
	}
	
	public String getSpIdStable() {
		return spIdStable;
	}
	
	public void setSpIdStable(String spIdStable) {
		this.spIdStable = spIdStable;
	}
	
	public String getSpName() {
		return spName;
	}
	
	public void setSpName(String spName) {
		this.spName = spName;
	}
	
	public Float getPoints() {
		return points;
	}
	
	public void setPoints(Float points) {
		this.points = points;
	}
	
	public Integer getRevCommission() {
		return revCommission;
	}
	
	public void setRevCommission(Integer revCommission) {
		this.revCommission = revCommission;
	}
	
	public Integer getStatusRuleIdNet() {
		return statusRuleIdNet;
	}
	
	public void setStatusRuleIdNet(Integer statusRuleIdNet) {
		this.statusRuleIdNet = statusRuleIdNet;
	}
	
	public Integer getStatusRuleIdProvision() {
		return statusRuleIdProvision;
	}
	
	public void setStatusRuleIdProvision(Integer statusRuleIdProvision) {
		this.statusRuleIdProvision = statusRuleIdProvision;
	}	
	
	public Integer getStatusRuleIdCommission() {
		return statusRuleIdCommission;
	}
	
	public void setStatusRuleIdCommission(Integer statusRuleIdCommission) {
		this.statusRuleIdCommission = statusRuleIdCommission;
	}		
	
	public Integer getStatusRuleIdTerminal() {
		return statusRuleIdTerminal;
	}
	
	public void setStatusRuleIdTerminal(Integer statusRuleIdTerminal) {
		this.statusRuleIdTerminal = statusRuleIdTerminal;
	}		
}