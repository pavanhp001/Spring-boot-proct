package com.A.ui.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.A.V.domain.SalesContext;
import com.A.xml.pr.v4.ProductInfoType;
import com.A.xml.pr.v4.ProductRequestType.ProviderList;

public class ServiceabilityResultVo {

	private List<ProductInfoType> productList;

	private ProviderList providerList;
	private String guid;
	private SalesContext salesContext;
	private String postalCode;
	private com.A.xml.se.v4.ServiceabilityResponse2.ProviderList serviceabilityResponseList;
	Map<String, String> realTimePendingProviderList = new HashMap<String, String>();
	

	public ProviderList getProviderList() {
		return providerList;
	}

	public void setProviderList(ProviderList providerList) {
		this.providerList = providerList;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public SalesContext getSalesContext() {
		return salesContext;
	}

	public void setSalesContext(SalesContext salesContext) {
		this.salesContext = salesContext;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public List<ProductInfoType> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductInfoType> productList) {
		this.productList = productList;
	}

	public com.A.xml.se.v4.ServiceabilityResponse2.ProviderList getServiceabilityResponseList() {
		return serviceabilityResponseList;
	}

	public void setServiceabilityResponseList(
			com.A.xml.se.v4.ServiceabilityResponse2.ProviderList serviceabilityResponseList) {
		this.serviceabilityResponseList = serviceabilityResponseList;
	}

	public Map<String, String> getRealTimePendingProviderList() {
		return realTimePendingProviderList;
	}

	public void setRealTimePendingProviderList(
			Map<String, String> realTimePendingProviderList) {
		this.realTimePendingProviderList = realTimePendingProviderList;
	}

	
	
}
