package com.A.V.beans.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table(name = "CM_PAY_EVT")
public class PaymentEvent implements CommonBeanInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2829551808045662803L;

	@Id
	@GeneratedValue(generator = "paymentEventSequence")
	@SequenceGenerator(name = "paymentEventSequence", sequenceName = "CM_PAY_EVT_SEQ")
	private long id;

	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "CONSUMER_ID", nullable = false)
	private Consumer consumer;

	@Column(name = "EXTERNAL_ID")
	private Long externalId;

	@Column(name = "BILL_INFO_ID")
	private Long billingInfoExtId;

	@Column(name = "PAY_EVT_TYPE")
	private String paymentEventType;

	@Transient 
	private String cvv;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "CONFIRM_NUM")
	private String confirmationNumber;

	@Column(name = "TRANS_DATE")
	@Temporal(TemporalType.DATE)
	private Date transactionDate;

	@Column(name = "PAY_STATUS")
	private String payStatus;

	@Column(name = "ORDER_EXT_ID")
	private Long orderExtId;

	@Column(name = "LI_EXT_ID")
	private Long lineItemExtId;

	@Column(name = "IS_DISCLOSURE")
	private int custAgreedCCDisclosure;

	@Override
	public final long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 * @inheritDoc
	 */
	@Override
	public final void setId(final long id) {
		this.id = id;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

}
