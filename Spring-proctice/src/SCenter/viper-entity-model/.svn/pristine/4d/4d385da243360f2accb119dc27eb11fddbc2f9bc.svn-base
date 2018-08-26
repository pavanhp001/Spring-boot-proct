package com.A.V.beans.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.IndexColumn;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ethomas
 * 
 */

@Entity
@Table(name = "CM_PAYEVENT")
public class CustomerPaymentEvent implements CommonBeanInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4559099095484643069L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "paymentEventSequence")
	@SequenceGenerator(name = "paymentEventSequence", sequenceName = "CM_PAYEVENT_SEQ",allocationSize = 1)
	private long id;

	@Column(name = "EXTERNAL_ID")
	private String externalId;
	

	
	@Column(name = "CONSUMER_ID")
	private Long consumerId;
 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BILLING_INFO_ID", nullable = true)
	private BillingInformation billingInfo;
	
	
	@Column(name = "BILLING_INFO_EX_ID")
	private String billingInfoId;

	@Column(name = "EVENT_TYPE")
	private String eventType;

	@Transient 
	private String cvv;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "CONFIRM_NUM")
	private String confirmNum;

	@Column(name = "TRANS_DATE")
	private Date transDate;

	@Column(name = "PAY_STATUS")
	private String payStatus;

	@Column(name = "ORDER_ID")
	private String orderId;

	@Column(name = "LINEITEM_ID")
	private String lineItemId;

	@Column(name = "CC_DISCLOSURE")
	private Boolean custAgreedCCDisclosure;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "CM_PAYEVENT_CM_PAY_EVENT_STAT", joinColumns = @JoinColumn(name = "CM_PAYEVENT_ID"))
	@IndexColumn(name = "listOrder", base = 0)
	private List<CustomerPaymentEventStatus> paymentStatusHistory;
	
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final long getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setId(final long id) {
		this.id = id;
	}

	public BillingInformation getBillingInfo() {
		return billingInfo;
	}

	public void setBillingInfo(BillingInformation billingInfo) {
		this.billingInfo = billingInfo;
	}

	public String getBillingInfoId() {
		return billingInfoId;
	}

	public void setBillingInfoId(String billingInfoId) {
		this.billingInfoId = billingInfoId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getConfirmNum() {
		return confirmNum;
	}

	public void setConfirmNum(String confirmNum) {
		this.confirmNum = confirmNum;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(String lineItemId) {
		this.lineItemId = lineItemId;
	}

	public Boolean getCustAgreedCCDisclosure() {
		return custAgreedCCDisclosure;
	}

	public void setCustAgreedCCDisclosure(Boolean custAgreedCCDisclosure) {
		this.custAgreedCCDisclosure = custAgreedCCDisclosure;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public List<CustomerPaymentEventStatus> getPaymentStatusHistory() {
		
		if (paymentStatusHistory == null) {
			paymentStatusHistory = new ArrayList<CustomerPaymentEventStatus>();
		}
		return paymentStatusHistory;
	}

	public void setPaymentStatusHistory(
			List<CustomerPaymentEventStatus> paymentStatusHistory) {
		this.paymentStatusHistory = paymentStatusHistory;
	}

	public Long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}

	 
	 

}
