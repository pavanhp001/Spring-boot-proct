
package com.A.xml.cm.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for custBillingInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="custBillingInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addressRef" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="billingAccountType" type="{http://xml.A.com/v4}billingAccountTypeType"/>
 *         &lt;element name="billingMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditCardNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditCardType" type="{http://xml.A.com/v4}creditCardTypeType" minOccurs="0"/>
 *         &lt;element name="expirationYearMonth" type="{http://www.w3.org/2001/XMLSchema}gYearMonth" minOccurs="0"/>
 *         &lt;element name="cardHolderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="verificationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="checkingAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="routingNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isChecking" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="billingUniqueId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accountHolderUniqueId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "custBillingInfoType", propOrder = {
    "addressRef",
    "billingAccountType",
    "billingMethod",
    "creditCardNumber",
    "creditCardType",
    "expirationYearMonth",
    "cardHolderName",
    "verificationCode",
    "checkingAccountNumber",
    "routingNumber",
    "isChecking",
    "status",
    "externalId",
    "billingUniqueId",
    "accountHolderUniqueId"
})
public class CustBillingInfoType {

    @XmlElement(required = true)
    protected String addressRef;
    @XmlElement(required = true)
    protected BillingAccountTypeType billingAccountType;
    protected String billingMethod;
    protected String creditCardNumber;
    protected CreditCardTypeType creditCardType;
    @XmlSchemaType(name = "gYearMonth")
    protected XMLGregorianCalendar expirationYearMonth;
    protected String cardHolderName;
    protected String verificationCode;
    protected String checkingAccountNumber;
    protected String routingNumber;
    protected Integer isChecking;
    @XmlElement(required = true)
    protected String status;
    protected long externalId;
    @XmlElement(required = true)
    protected String billingUniqueId;
    protected String accountHolderUniqueId;

    /**
     * Gets the value of the addressRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressRef() {
        return addressRef;
    }

    /**
     * Sets the value of the addressRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressRef(String value) {
        this.addressRef = value;
    }

    /**
     * Gets the value of the billingAccountType property.
     * 
     * @return
     *     possible object is
     *     {@link BillingAccountTypeType }
     *     
     */
    public BillingAccountTypeType getBillingAccountType() {
        return billingAccountType;
    }

    /**
     * Sets the value of the billingAccountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingAccountTypeType }
     *     
     */
    public void setBillingAccountType(BillingAccountTypeType value) {
        this.billingAccountType = value;
    }

    /**
     * Gets the value of the billingMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingMethod() {
        return billingMethod;
    }

    /**
     * Sets the value of the billingMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingMethod(String value) {
        this.billingMethod = value;
    }

    /**
     * Gets the value of the creditCardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Sets the value of the creditCardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardNumber(String value) {
        this.creditCardNumber = value;
    }

    /**
     * Gets the value of the creditCardType property.
     * 
     * @return
     *     possible object is
     *     {@link CreditCardTypeType }
     *     
     */
    public CreditCardTypeType getCreditCardType() {
        return creditCardType;
    }

    /**
     * Sets the value of the creditCardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCardTypeType }
     *     
     */
    public void setCreditCardType(CreditCardTypeType value) {
        this.creditCardType = value;
    }

    /**
     * Gets the value of the expirationYearMonth property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationYearMonth() {
        return expirationYearMonth;
    }

    /**
     * Sets the value of the expirationYearMonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationYearMonth(XMLGregorianCalendar value) {
        this.expirationYearMonth = value;
    }

    /**
     * Gets the value of the cardHolderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     * Sets the value of the cardHolderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolderName(String value) {
        this.cardHolderName = value;
    }

    /**
     * Gets the value of the verificationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerificationCode() {
        return verificationCode;
    }

    /**
     * Sets the value of the verificationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerificationCode(String value) {
        this.verificationCode = value;
    }

    /**
     * Gets the value of the checkingAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckingAccountNumber() {
        return checkingAccountNumber;
    }

    /**
     * Sets the value of the checkingAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckingAccountNumber(String value) {
        this.checkingAccountNumber = value;
    }

    /**
     * Gets the value of the routingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingNumber() {
        return routingNumber;
    }

    /**
     * Sets the value of the routingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingNumber(String value) {
        this.routingNumber = value;
    }

    /**
     * Gets the value of the isChecking property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsChecking() {
        return isChecking;
    }

    /**
     * Sets the value of the isChecking property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsChecking(Integer value) {
        this.isChecking = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the externalId property.
     * 
     */
    public long getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     */
    public void setExternalId(long value) {
        this.externalId = value;
    }

    /**
     * Gets the value of the billingUniqueId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingUniqueId() {
        return billingUniqueId;
    }

    /**
     * Sets the value of the billingUniqueId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingUniqueId(String value) {
        this.billingUniqueId = value;
    }

    /**
     * Gets the value of the accountHolderUniqueId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountHolderUniqueId() {
        return accountHolderUniqueId;
    }

    /**
     * Sets the value of the accountHolderUniqueId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountHolderUniqueId(String value) {
        this.accountHolderUniqueId = value;
    }

}
