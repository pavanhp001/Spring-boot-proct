package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table(name = "CM_ACCOUNT")
public class Account implements CommonBeanInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1489118524301384799L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "accountSequence" )
	@SequenceGenerator(name = "accountSequence", sequenceName = "CM_ACCOUNT_SEQ",allocationSize = 1)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "CONSUMER_ID", nullable = true)
	private Consumer consumer;

	@Column(name = "EXTERNAL_ID")
	private Long externalId;
	
	@Column(name = "BILLING_PHONE")
	private String billingPhone;

	@Column(name = "PROVIDER_TYPE")
	private String providerType;

	@Column(name = "BILL_ACCT_NUM")
	private String billingAccountNumber;

	@Column(name = "ADDR_REF")
	private String addressExtId;
	
	@Column(name = "CREDIT_CARD_REF")
	private String creditCardRef;

	@Column(name = "CONSUMER_REF")
	private String consumerExtId;

	@Column(name = "PIN")
	private String pin;

	@Column(name = "SEC_QUESTION")
	private String securityQuestion;

	@Column(name = "SEC_ANS")
	private String securityAnswer;

	@Column(name = "PARTIAL_SSN")
	private String ssn;

	@Column(name = "SEC_CreditCard")
	private String securityCreditCard;

	@Column(name = "PREV_ADDR_IND")
	private String previousAddressIndicator;

	@Column(name = "CREDIT_CHECK")
	private String creditCheck;

	@Column(name = "CREDIT_ALERT")
	private String creditAlert;

	@Column(name = "SUPP_INDICATOR")
	private String suppressionIndicator;

	@Column(name = "CONSENT_TO_CC")
	private String consentToCC;

	@Column(name = "ACCT_STATUS")
	private String accountStatus;

	@Column(name = "ACCT_TYPE")
	private String accountType;
	
	@Column(name = "IS_AUTHORIZED")
	private int isAuthorized;
	
	@Column(name = "SEC_TC_ACCEPT")
	private String securityTCAccepted;
	
	@Column(name = "ACCT_TYPE_IND")
	private String accountTypeIndicator;
	
	
	 
	

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

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBillingAccountNumber() {
		return billingAccountNumber;
	}

	public void setBillingAccountNumber(String billingAccountNumber) {
		this.billingAccountNumber = billingAccountNumber;
	}

	public String getAddressExtId() {
		return addressExtId;
	}

	public void setAddressExtId(String addressExtId) {
		this.addressExtId = addressExtId;
	}

	public String getConsumerExtId() {
		return consumerExtId;
	}

	public void setConsumerExtId(String consumerExtId) {
		this.consumerExtId = consumerExtId;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getSecurityCreditCard() {
		return securityCreditCard;
	}

	public void setSecurityCreditCard(String securityCreditCard) {
		this.securityCreditCard = securityCreditCard;
	}

	public String getPreviousAddressIndicator() {
		return previousAddressIndicator;
	}

	public void setPreviousAddressIndicator(String previousAddressIndicator) {
		this.previousAddressIndicator = previousAddressIndicator;
	}

	public String getCreditCheck() {
		return creditCheck;
	}

	public void setCreditCheck(String creditCheck) {
		this.creditCheck = creditCheck;
	}

	public String getCreditAlert() {
		return creditAlert;
	}

	public void setCreditAlert(String creditAlert) {
		this.creditAlert = creditAlert;
	}

	public String getSuppressionIndicator() {
		return suppressionIndicator;
	}

	public void setSuppressionIndicator(String suppressionIndicator) {
		this.suppressionIndicator = suppressionIndicator;
	}

	public String getConsentToCC() {
		return consentToCC;
	}

	public void setConsentToCC(String consentToCC) {
		this.consentToCC = consentToCC;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getBillingPhone() {
		return billingPhone;
	}

	public void setBillingPhone(String billingPhone) {
		this.billingPhone = billingPhone;
	}

	public String getCreditCardRef() {
		return creditCardRef;
	}

	public void setCreditCardRef(String creditCardRef) {
		this.creditCardRef = creditCardRef;
	}

	public int getIsAuthorized() {
		return isAuthorized;
	}

	public void setIsAuthorized(int isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	public String getSecurityTCAccepted() {
		return securityTCAccepted;
	}

	public void setSecurityTCAccepted(String securityTCAccepted) {
		this.securityTCAccepted = securityTCAccepted;
	}

	public String getAccountTypeIndicator() {
		return accountTypeIndicator;
	}

	public void setAccountTypeIndicator(String accountTypeIndicator) {
		this.accountTypeIndicator = accountTypeIndicator;
	}
	
	

}
