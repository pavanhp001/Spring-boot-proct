package com.A.ui.vo;

import java.io.Serializable;

public class ProductDetailVo implements Serializable {
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String partnerExternalId;
	private String partnerName;
	private String productName;
	private String productExternalId;
	
	public ProductDetailVo(String partnerExternalId, String partnerName, String productName, String productExternalId) {
		this.partnerExternalId = partnerExternalId;
		this.productExternalId = productExternalId;
		this.partnerName = partnerName;
		this.productName = productName;
	}

	public String getPartnerExternalId() {
		return partnerExternalId;
	}

	public void setPartnerExternalId(String partnerExternalId) {
		this.partnerExternalId = partnerExternalId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductExternalId() {
		return productExternalId;
	}

	public void setProductExternalId(String productExternalId) {
		this.productExternalId = productExternalId;
	}

	@Override
	public String toString() {
		return "ProductDetailVo [partnerExternalId=" + partnerExternalId
				+ ", partnerName=" + partnerName + ", productName="
				+ productName + ", productExternalId=" + productExternalId
				+ "]";
	}
	
	
	
}
