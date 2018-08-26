package com.A.V.beans.entity;

import java.util.Date;

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
import javax.persistence.Transient;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table( name = "CM_BILLING_INFORMATION" )
public class BillingInformation implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3302666612458360162L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "billingInformationBeanSequence" )
    @SequenceGenerator( name = "billingInformationBeanSequence", sequenceName = "CM_BILLING_INFO_BEAN_SEQ" ,allocationSize = 1)
    private long id;

    @Column(name = "BILLING_METHOD")
    private String billingMethod;
    
    @Column(name = "CREDIT_CARD_NUMBER")
    private String creditCardNumber;
    
    @Column(name = "VERIFICATION_CODE")
    private String verificationCode;
    
    @Column(name = "CHECKING_ACCOUNT_NUMBER" )
    private String checkingAccountNumber;
    
    @Column(name = "ROUTING_NUMBER")
    private String routingNumber;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "ADDRESS_EXTERNAL_ID", nullable=false)
    private Long addressExternalId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CONSUMER_ID", nullable = true)
    private Consumer consumer;
    
    @Column(name = "ACCT_HOLDER_UNIQUE_ID")
    private String accountHolderUniqueId;
     
    @Column(name="EXTERNAL_ID")
    private Long externalId;
    
    @Transient
    private String addressRefId;
    
    @Column(name = "BILLING_UNIQUE_ID")
    private String billingUniqueId;

    @Column(name = "CARD_TYPE")
    private String cardType;

    @Transient
    private Date expirationDate;
    
	@Column(name = "EXPIRE_MONTH")
	private String expireMonth;
	
	@Column(name = "EXPIRE_YEAR")
	private String expireYear;
    
    @Column(name = "CARD_HOLDER_NAME")
    private String cardHolderName;
    
    @Column(name = "IS_CHECKING")
	private int isChecking;
    
    /**
     * @return id the id of the object
     * @inheritDoc
     */
    @Override
    public final long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     * @inheritDoc
     */
    @Override
    public final void setId( final long id )
    {
        this.id = id;
    }

    /**
     * @return the billingMethod
     */
    public final String getBillingMethod()
    {
        return billingMethod;
    }

    /**
     * @param billingMethod
     *            the billingMethod to set
     */
    public final void setBillingMethod( final String billingMethod )
    {
        this.billingMethod = billingMethod;
    }

    /**
     * @return the creditCardNumber
     */
    public final String getCreditCardNumber()
    {
        return creditCardNumber;
    }

    /**
     * @param creditCardNumber
     *            the creditCardNumber to set
     */
    public final void setCreditCardNumber( final String creditCardNumber )
    {
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * @return the verificationCode
     */
    public final String getVerificationCode()
    {
        return verificationCode;
    }

    /**
     * @param verificationCode
     *            the verificationCode to set
     */
    public final void setVerificationCode( final String verificationCode )
    {
        this.verificationCode = verificationCode;
    }

    /**
     * @return the checkingAccountNumber
     */
    public final String getCheckingAccountNumber()
    {
        return checkingAccountNumber;
    }

    /**
     * @param checkingAccountNumber
     *            the checkingAccountNumber to set
     */
    public final void setCheckingAccountNumber( final String checkingAccountNumber )
    {
        this.checkingAccountNumber = checkingAccountNumber;
    }

    /**
     * @return the routingNumber
     */
    public final String getRoutingNumber()
    {
        return routingNumber;
    }

    /**
     * @param routingNumber
     *            the routingNumber to set
     */
    public final void setRoutingNumber( final String routingNumber )
    {
        this.routingNumber = routingNumber;
    }

    public Long getAddressExternalId() {
		return addressExternalId;
	}

	public void setAddressExternalId(Long addressExternalId) {
		this.addressExternalId = addressExternalId;
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

	public String getAddressRefId() {
		return addressRefId;
	}

	public void setAddressRefId(String addressRefId) {
		this.addressRefId = addressRefId;
	}

	public String getBillingUniqueId() {
		return billingUniqueId;
	}

	public void setBillingUniqueId(String billingUniqueId) {
		this.billingUniqueId = billingUniqueId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getExpireMonth() {
		return expireMonth;
	}

	public void setExpireMonth(String expireMonth) {
		this.expireMonth = expireMonth;
	}

	public String getExpireYear() {
		return expireYear;
	}

	public void setExpireYear(String expireYear) {
		this.expireYear = expireYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getIsChecking() {
		return isChecking;
	}

	public void setIsChecking(int isChecking) {
		this.isChecking = isChecking;
	}

	public String getAccountHolderUniqueId() {
		return accountHolderUniqueId;
	}

	public void setAccountHolderUniqueId(String accountHolderUniqueId) {
		this.accountHolderUniqueId = accountHolderUniqueId;
	}

}
