package com.A.ui.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author 
 * 
 */

@Entity
@Table(name = "customer_tracker")
public class CustomerTracker implements Serializable{

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "AGENT_ID")
	private String agentId;
	
	@Column(name = "CALL_NUMBER_ID")
	private Integer callNumberId;

	@Column(name = "ORDER_ID")
	private Long orderId;
	
	@Column(name = "LINEITEM_ID")
	private Long lineItemId;

	@Column(name = "REFERRER")
	private String referrer;
	
	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	
	@Column(name = "DWELLING_TYPE")
	private String dwellingType;
	
	@Column(name = "OWN")
	private String own;
	
	@Column(name = "PROVIDER_PARENT_ID")
	private Long providerParentId;
	
	@Column(name = "PROVIDER_NAME")
	private String providerName;
	
	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	@Column(name = "PRODUCT_TYPE")
	private String productType;
	
	@Column(name = "CONCERT_POINTS")
	private float concertPoints;
	
	@Column(name = "ACTUAL_POINTS")
	private float actualPoints;
	
	@Column(name = "IS_POINTS_UPDATED")
	private Integer isPointsUpdated;
	
	@Column(name = "CREATE_DATE")
	private Calendar createDate;

	@Column(name = "UPDATED_DATE")
	private Calendar updatedDate;
	
	@Column(name = "MOVE_DATE")
	private Calendar moveDate;

	@Column(name = "INSTALL_DATE")
	private Calendar installDate;

	@Transient 
	private String noOfDays;
	

	@Override
	public String toString() {
		return "CustomerTracker [id=" + id + ", agentId=" + agentId
				+ ", callNumberId=" + callNumberId + ", orderId=" + orderId
				+ ", lineItemId=" + lineItemId 
				+ ", referrer=" + referrer + ", customerName=" + customerName
				+ ", dwellingType=" + dwellingType + ", own=" + own
				+ ", providerParentId=" + providerParentId + ", providerName="
				+ providerName + ", productName=" + productName
				+ ", productType=" + productType + ", concertPoints="
				+ concertPoints + ", actualPoints=" + actualPoints
				+ ", isPointsUpdated=" + isPointsUpdated + ", createDate="
				+ createDate + ", updatedDate=" + updatedDate + ", noOfDays="+ noOfDays +", moveDate=" + moveDate + ", installDate=" + installDate +"]";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public Integer getCallNumberId() {
		return callNumberId;
	}

	public void setCallNumberId(Integer callNumberId) {
		this.callNumberId = callNumberId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(Long lineItemId) {
		this.lineItemId = lineItemId;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDwellingType() {
		return dwellingType;
	}

	public void setDwellingType(String dwellingType) {
		this.dwellingType = dwellingType;
	}

	public String getOwn() {
		return own;
	}

	public void setOwn(String own) {
		this.own = own;
	}

	public Long getProviderParentId() {
		return providerParentId;
	}

	public void setProviderParentId(Long providerParentId) {
		this.providerParentId = providerParentId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public float getConcertPoints() {
		return concertPoints;
	}

	public void setConcertPoints(float concertPoints) {
		this.concertPoints = concertPoints;
	}

	public float getActualPoints() {
		return actualPoints;
	}

	public void setActualPoints(float actualPoints) {
		this.actualPoints = actualPoints;
	}

	public Integer getIsPointsUpdated() {
		return isPointsUpdated;
	}

	public void setIsPointsUpdated(Integer isPointsUpdated) {
		this.isPointsUpdated = isPointsUpdated;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public Calendar getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Calendar updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	public Calendar getMoveDate() {
		return moveDate;
	}

	public void setMoveDate(Calendar moveDate) {
		this.moveDate = moveDate;
	}

	public Calendar getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Calendar installDate) {
		this.installDate = installDate;
	}
}
