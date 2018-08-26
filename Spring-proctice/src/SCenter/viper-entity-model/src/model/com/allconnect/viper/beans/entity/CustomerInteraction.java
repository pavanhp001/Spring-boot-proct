package com.A.V.beans.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table(name = "CM_CUSTOMER_INTERACTION")
public class CustomerInteraction implements CommonBeanInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3881560697042187648L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "customerInteractionSequence")
	@SequenceGenerator(name = "customerInteractionSequence", sequenceName = "CUSTOMER_INTER_SEQ",allocationSize = 1)
	private long id;

	@Column(name = "CUSTOMER_EXTERNAL_ID")
	private Long customerExternalId;


	@Column(name = "PROVIDER_ID")
	private Long providerId;

	@Column(name = "NOTES")
	private String notes;

	@Column(name = "INTERACTION_ON")
	private Calendar dateOfInteraction;

	@Column(name = "AGENT_EXT_ID")
	private String agentExternalId;

	// Referes to Business Party
	@Column(name = "SOURCE")
	private String source;

	// Referes to Business Party
	@Column(name = "IS_SYS_CREATED")
	private int isSystemCreated;

	@Column(name = "ORDER_EXT_ID")
	private Long orderExternalId;

	@Column(name = "LI_EXT_ID")
	private Long lineItemExternalId;

	@Transient
	private String agentName;

	@Transient
	private String customerName;


	@Column(name = "EXTERNAL_ID")
	private Long externalId;

	@Column(name = "SERVICE_TYPE")
	private String serviceType;


	/**
     *
     */
	public CustomerInteraction() {
	}

	/**
	 * {@inheritDoc}
	 */
	public final long getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setId(final long id) {
		this.id = id;
	}

	/**
	 * @return the dateOfInteraction
	 */
	public final Calendar getDateOfInteraction() {
		return dateOfInteraction;
	}

	/**
	 * @param dateOfInteraction
	 *            the dateOfInteraction to set
	 */
	public final void setDateOfInteraction(final Calendar dateOfInteraction) {
		this.dateOfInteraction = dateOfInteraction;
	}

	public Long getCustomerExternalId() {
		return customerExternalId;
	}

	public void setCustomerExternalId(Long customerExternalId) {
		this.customerExternalId = customerExternalId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAgentExternalId() {
		return agentExternalId;
	}

	public void setAgentExternalId(String agentExternalId) {
		this.agentExternalId = agentExternalId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getOrderExternalId() {
		return orderExternalId;
	}

	public void setOrderExternalId(Long orderExternalId) {
		this.orderExternalId = orderExternalId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public int getIsSystemCreated() {
		return isSystemCreated;
	}

	public void setIsSystemCreated(int isSystemCreated) {
		this.isSystemCreated = isSystemCreated;
	}



	public Long getLineItemExternalId() {
		return lineItemExternalId;
	}

	public void setLineItemExternalId(Long lineItemExternalId) {
		this.lineItemExternalId = lineItemExternalId;
	}



	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

	public String getServiceType() {
	    return serviceType;
	}

	public void setServiceType(String serviceType) {
	    this.serviceType = serviceType;
	}

	@Override
	public String toString() {
	    return "CustomerInteraction [id=" + id + ", customerExternalId=" + customerExternalId + ", providerId=" + providerId + ", notes=" + notes + ", dateOfInteraction="
		    + dateOfInteraction + ", agentExternalId=" + agentExternalId + ", source=" + source + ", isSystemCreated=" + isSystemCreated + ", orderExternalId="
		    + orderExternalId + ", lineItemExternalId=" + lineItemExternalId + ", agentName=" + agentName + ", customerName=" + customerName + ", externalId=" + externalId
		    + ", serviceType=" + serviceType + "]";
	}





}
