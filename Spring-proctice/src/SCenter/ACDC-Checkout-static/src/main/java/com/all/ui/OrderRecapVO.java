package com.AL.ui;

import java.util.List;

import com.AL.ui.vo.DataFeildFeatureVO;
import com.AL.xml.v4.ApplicableType;

public class OrderRecapVO {
	private String firstName;
	private String lastName;
	private String middleName;
	private String serviceAddressBlock;
	private String fisrInstallDate;
	private String fisrInstallTime;
	private String secondInstallDate;
	private String secondInstallTime;
	
	private String addr1;
	private String addr2;
	private String billingDisclosure;
	private Double baseMonthlyWithOutPromotion;
	private Double baseMonthlyPromotion;
	private Double monthly;
	private Double installationTotal;
	
	private List<DataFeildFeatureVO> orderDetialList;
	
	private List<ApplicableType> promotionList;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getServiceAddressBlock() {
		return serviceAddressBlock;
	}
	public void setServiceAddressBlock(String serviceAddressBlock) {
		this.serviceAddressBlock = serviceAddressBlock;
	}
	public String getFisrInstallDate() {
		return fisrInstallDate;
	}
	public void setFisrInstallDate(String fisrInstallDate) {
		this.fisrInstallDate = fisrInstallDate;
	}
	public String getSecondInstallDate() {
		return secondInstallDate;
	}
	public void setSecondInstallDate(String secondInstallDate) {
		this.secondInstallDate = secondInstallDate;
	}
	public Double getBaseMonthlyWithOutpromotion() {
		return baseMonthlyWithOutPromotion;
	}
	public void setBaseMonthlyWithOutPromotion(Double baseMonthlyWithOutPromotion) {
		this.baseMonthlyWithOutPromotion = baseMonthlyWithOutPromotion;
	}
	public Double getBaseMonthlyPromotion() {
		return baseMonthlyPromotion;
	}
	public void setBaseMonthlyPromotion(Double baseMonthlyPromotion) {
		this.baseMonthlyPromotion = baseMonthlyPromotion;
	}
	public Double getMonthly() {
		return monthly;
	}
	public void setMonthly(Double monthly) {
		this.monthly = monthly;
	}
	public Double getInstallationTotal() {
		return installationTotal;
	}
	public void setInstallationTotal(Double installationTotal) {
		this.installationTotal = installationTotal;
	}
	public String getFisrInstallTime() {
		return fisrInstallTime;
	}
	public void setFisrInstallTime(String fisrInstallTime) {
		this.fisrInstallTime = fisrInstallTime;
	}
	public String getSecondInstallTime() {
		return secondInstallTime;
	}
	public void setSecondInstallTime(String secondInstallTime) {
		this.secondInstallTime = secondInstallTime;
	}
	public List<DataFeildFeatureVO> getOrderDetialList() {
		return orderDetialList;
	}
	public void setOrderDetialList(List<DataFeildFeatureVO> orderDetialList) {
		this.orderDetialList = orderDetialList;
	}
	public List<ApplicableType> getPromotionList() {
		return promotionList;
	}
	public void setPromotionList(List<ApplicableType> promotionList) {
		this.promotionList = promotionList;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getBillingDisclosure() {
		return billingDisclosure;
	}
	public void setBillingDisclosure(String billingDisclosure) {
		this.billingDisclosure = billingDisclosure;
	}
	
}
