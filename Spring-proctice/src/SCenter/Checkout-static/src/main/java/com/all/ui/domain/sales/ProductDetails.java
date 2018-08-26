package com.AL.ui.domain.sales;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails  {
    private List<String> marketingHighlight;
    private List<String> metaData;
    private List<ProductDescriptiveInformation> descriptiveInfo;
 
	public List<String> getMarketingHighlight() {
		if (marketingHighlight == null) {
			marketingHighlight = new ArrayList<String>();
		}
		return marketingHighlight;
	}

	public void setMarketingHighlight(List<String> marketingHighlight) {
		this.marketingHighlight = marketingHighlight;
	}
	
	public void addMarketingHighlight(String highlight) {
		getMarketingHighlight().add(highlight);
	}
	
	public List<String> getMetaData() {
		if (metaData == null) {
			metaData = new ArrayList<String>();
		}
		return metaData;
	}
	
	public void addMetaData(String metadata) {
		getMetaData().add(metadata);
	}
	
	public void setMetaData(List<String> metaData) {
		this.metaData = metaData;
	}

	public List<ProductDescriptiveInformation> getDescriptiveInfo() {
		if (descriptiveInfo == null) {
			descriptiveInfo = new ArrayList<ProductDescriptiveInformation>();
		}
		return descriptiveInfo;
	}
	
	public void setDescriptiveInfo(List<ProductDescriptiveInformation> arg) {
		descriptiveInfo = arg;
	}

	public void addDescriptiveInfo(String type, String value, String description) {
		ProductDescriptiveInformation pdi = new ProductDescriptiveInformation();
		pdi.setDescription(description);
		pdi.setType(type);
		pdi.setValue(value);
		getDescriptiveInfo().add(pdi);
	}
}
