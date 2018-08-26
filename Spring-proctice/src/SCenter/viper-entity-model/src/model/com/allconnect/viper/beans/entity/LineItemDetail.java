package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table(name = "OM_LINE_ITEM_DTL")
public class LineItemDetail implements CommonBeanInterface {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8984549415773068359L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "lineItemDetailBeanSequence")
	@SequenceGenerator(name = "lineItemDetailBeanSequence", sequenceName = "OM_LINE_ITEM_DETAIL_BEAN_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "LINE_ITEM_TYPE", length = 50)
	private String type;

	@Column(name = "LINEITEM_DETAIL_EXT_ID", length = 50)
	private String lineItemDetailExternalId;

	@Column(name = "APPLIES_TO")
	private String appliesTo;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "PRODUCT_UNIQUE_ID")
	private Long productUniqueId;

	@Override
	public final long getId() {
		return id;
	}

	@Override
	public final void setId(final long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLineItemDetailExternalId() {
		return lineItemDetailExternalId;
	}

	public void setLineItemDetailExternalId(String lineItemDetailExternalId) {
		this.lineItemDetailExternalId = lineItemDetailExternalId;
	}

	public String getAppliesTo() {
		return appliesTo;
	}

	public void setAppliesTo(String appliesTo) {
		this.appliesTo = appliesTo;
	}

	public void setAppliesToList(int[] newAppliesToList) {
		StringBuilder sb = new StringBuilder();
		for (int appliesTo : newAppliesToList) {
			sb.append(appliesTo).append(",");
		}

		appliesTo = sb.substring(0, sb.length() - 1);
	}

	public void setAppliesToList(List<Integer> appliesToList) {
		StringBuilder sb = new StringBuilder();
		for (Integer appliesTo : appliesToList) {
			sb.append(appliesTo).append(",");
		}

		appliesTo = sb.substring(0, sb.length() - 1);
	}

	public List<Integer> getAppliesToList() {
		return null;
	}

	public Long getProductUniqueId() {
		return productUniqueId;
	}

	public void setProductUniqueId(Long productUniqueId) {
		this.productUniqueId = productUniqueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
}
