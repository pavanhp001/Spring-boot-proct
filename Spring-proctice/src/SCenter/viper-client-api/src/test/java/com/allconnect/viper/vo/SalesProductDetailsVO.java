package com.A.V.vo;
 

import java.util.List;
import java.util.Map;

public class SalesProductDetailsVO {

	
	private String externalId;
	private String itemExternalId;
	private String name;
	
	private String productCategory;
	private List<String> channelList;
	private String source;
	
	private String parent;
	private Double baseNonRecurringPrice;
	private Double baseRecurringPrice;
	private Double score;
	private Map<String,String> capabilityMap;
	
	private String provierName;
	private String provierExternalId;	
	
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getItemExternalId() {
		return itemExternalId;
	}
	public void setItemExternalId(String itemExternalId) {
		this.itemExternalId = itemExternalId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Double getBaseNonRecurringPrice() {
		return baseNonRecurringPrice;
	}
	public void setBaseNonRecurringPrice(Double baseNonRecurringPrice) {
		this.baseNonRecurringPrice = baseNonRecurringPrice;
	}
	public Double getBaseRecurringPrice() {
		return baseRecurringPrice;
	}
	public void setBaseRecurringPrice(Double baseRecurringPrice) {
		this.baseRecurringPrice = baseRecurringPrice;
	}
	
	public String getProvierName() {
		return provierName;
	}
	public void setProvierName(String provierName) {
		this.provierName = provierName;
	}
	
	public List<String> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<String> channelList) {
		this.channelList = channelList;
	}

	@Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();

        buf.append(getClass().getName());
        buf.append(" [externalId=").append(externalId);
        buf.append(", itemExternalId='").append(itemExternalId).append('\'');
        buf.append(", name='").append(name).append('\'');
        buf.append(", productCategoryList='").append(productCategory).append('\'');
        buf.append(" channelList=").append(channelList);
        buf.append(", source='").append(source).append('\'');
        buf.append(", parent='").append(parent).append('\'');
        buf.append(", baseNonRecurringPrice='").append(baseNonRecurringPrice).append('\'');
        buf.append(" baseRecurringPrice=").append(baseRecurringPrice);
        buf.append(", score='").append(score).append('\'');
        buf.append(", String='").append(capabilityMap).append('\'');
        buf.append(", provierName='").append(provierName).append('\'');
	    buf.append(']');

        return buf.toString();
    }
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProvierExternalId(String provierExternalId) {
		this.provierExternalId = provierExternalId;
	}
	public String getProvierExternalId() {
		return provierExternalId;
	}
	public Map<String, String> getCapabilityMap() {
		return capabilityMap;
	}
	public void setCapabilityMap(Map<String, String> capabilityMap) {
		this.capabilityMap = capabilityMap;
	}}
