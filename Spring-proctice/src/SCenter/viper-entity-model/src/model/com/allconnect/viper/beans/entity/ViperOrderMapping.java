package com.A.V.beans.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table(name = "OM_RTIM_ORDER_MAPPING")
public class VOrderMapping implements CommonBeanInterface {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1938543559151551810L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "VOrderMappingSequence")
	@SequenceGenerator(name = "VOrderMappingSequence", sequenceName = "OM_RTIM_ORDER_MAPPING_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "V_ORDER_NO")
	private String VOrderNo;

	@Column(name = "ORDER_EXT_ID")
	private Long orderExtId;

	@Column(name = "LI_EXT_ID")
	private Long liExtId;

	@Column(name = "PROVIDER_EXT_ID")
	private String providerExtId;

	@Column(name = "CONTEXT")
	private String context;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DT_CREATED")
	private Calendar dateCreated;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public String getVOrderNo() {
		return VOrderNo;
	}

	public void setVOrderNo(String VOrderNo) {
		this.VOrderNo = VOrderNo;
	}

	public Long getOrderExtId() {
		return orderExtId;
	}

	public void setOrderExtId(Long orderExtId) {
		this.orderExtId = orderExtId;
	}

	public Long getLiExtId() {
		return liExtId;
	}

	public void setLiExtId(Long liExtId) {
		this.liExtId = liExtId;
	}

	public String getProviderExtId() {
		return providerExtId;
	}

	public void setProviderExtId(String providerExtId) {
		this.providerExtId = providerExtId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
	    return "VOrderMapping [id=" + id + ", VOrderNo=" + VOrderNo + ", orderExtId=" + orderExtId + ", liExtId=" + liExtId + ", providerExtId=" + providerExtId
		    + ", context=" + context + ", description=" + description + ", dateCreated=" + dateCreated + "]";
	}



}
