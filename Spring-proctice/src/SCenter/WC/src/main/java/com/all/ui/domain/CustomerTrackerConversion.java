package com.A.ui.domain;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 * 
 */


public class CustomerTrackerConversion implements Serializable{

	
	private Integer totalSales;
	
	private Float conversion;

	private Float GRPC;
	
	private Float totalActualPoints;
	
	private Integer totalUtilityProducts;
	
	private Float totalUtilityPoints;
	
	private Integer totalOrders;
	
	private Integer totalLineItems;
	
	private Integer totalCallNumbers;
	
	private Integer concertCallCount;	
	
	private Integer actualCallCount;	
	
	private Integer utilityPitched;
	
	private Float utilityConversion;


	@Override
	public String toString() {
		return "CustomerTrackerConversion [totalSales=" + totalSales
				+ ", conversion=" + conversion + ", GRPC=" + GRPC
				+ ", totalActualPoints=" + totalActualPoints
				+ ", totalUtilityProducts=" + totalUtilityProducts
				+ ", totalUtilityPoints=" + totalUtilityPoints
				+ ", totalOrders=" + totalOrders + ", totalLineItems="
				+ totalLineItems + ", totalCallNumbers=" + totalCallNumbers
				+ ", concertCallCount=" + concertCallCount
				+ ", actualCallCount=" + actualCallCount + ", utilityPitched="
				+ utilityPitched + ", utilityConversion=" + utilityConversion
				+ "]";
	}

	public Integer getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Integer totalSales) {
		this.totalSales = totalSales;
	}

	public Float getConversion() {
		return conversion;
	}

	public void setConversion(Float conversion) {
		this.conversion = conversion;
	}

	public Float getGRPC() {
		return GRPC;
	}

	public void setGRPC(Float gRPC) {
		GRPC = gRPC;
	}

	public Float getTotalActualPoints() {
		return totalActualPoints;
	}

	public void setTotalActualPoints(Float totalActualPoints) {
		this.totalActualPoints = totalActualPoints;
	}

	public Integer getTotalUtilityProducts() {
		return totalUtilityProducts;
	}

	public void setTotalUtilityProducts(Integer totalUtilityProducts) {
		this.totalUtilityProducts = totalUtilityProducts;
	}

	public Float getTotalUtilityPoints() {
		return totalUtilityPoints;
	}

	public void setTotalUtilityPoints(Float totalUtilityPoints) {
		this.totalUtilityPoints = totalUtilityPoints;
	}

	public Integer getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(Integer totalOrders) {
		this.totalOrders = totalOrders;
	}

	public Integer getTotalLineItems() {
		return totalLineItems;
	}

	public void setTotalLineItems(Integer totalLineItems) {
		this.totalLineItems = totalLineItems;
	}

	public Integer getTotalCallNumbers() {
		return totalCallNumbers;
	}

	public void setTotalCallNumbers(Integer totalCallNumbers) {
		this.totalCallNumbers = totalCallNumbers;
	}

	public Integer getUtilityPitched() {
		return utilityPitched;
	}

	public void setUtilityPitched(Integer utilityPitched) {
		this.utilityPitched = utilityPitched;
	}

	public Float getUtilityConversion() {
		return utilityConversion;
	}

	public void setUtilityConversion(Float utilityConversion) {
		this.utilityConversion = utilityConversion;
	}

	public Integer getConcertCallCount() {
		return concertCallCount;
	}

	public void setConcertCallCount(Integer concertCallCount) {
		this.concertCallCount = concertCallCount;
	}

	public Integer getActualCallCount() {
		return actualCallCount;
	}

	public void setActualCallCount(Integer actualCallCount) {
		this.actualCallCount = actualCallCount;
	}
}
