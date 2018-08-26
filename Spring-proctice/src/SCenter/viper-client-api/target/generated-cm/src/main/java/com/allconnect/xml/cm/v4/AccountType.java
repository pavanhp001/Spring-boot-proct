
package com.A.xml.cm.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="providerType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accountType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="billingAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="billingAccountTelephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="addressExtId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="consumerExtId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authentication" type="{http://xml.A.com/v4}securityVerificationType" minOccurs="0"/>
 *         &lt;element name="existingLast4SSN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="securityCreditCard" type="{http://xml.A.com/v4}SecurityCreditCardType"/>
 *         &lt;element name="prevAddressIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="creditCheck" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="creditAlert" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="suppressionIndicator" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="consentToCC" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="accountStatus" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="accountTypeIndicator" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountType", propOrder = {
    "externalId",
    "providerType",
    "accountType",
    "billingAccountNumber",
    "billingAccountTelephoneNumber",
    "addressExtId",
    "consumerExtId",
    "authentication",
    "existingLast4SSN",
    "securityCreditCard",
    "prevAddressIndicator",
    "creditCheck",
    "creditAlert"
})
public class AccountType {

    @XmlElement(required = true)
    protected String externalId;
    @XmlElement(required = true)
    protected String providerType;
    @XmlElement(required = true)
    protected String accountType;
    @XmlElement(required = true)
    protected String billingAccountNumber;
    @XmlElement(required = true)
    protected String billingAccountTelephoneNumber;
    @XmlElement(required = true)
    protected String addressExtId;
    @XmlElement(required = true)
    protected String consumerExtId;
    protected SecurityVerificationType authentication;
    @XmlElement(required = true)
    protected String existingLast4SSN;
    @XmlElement(required = true)
    protected SecurityCreditCardType securityCreditCard;
    protected boolean prevAddressIndicator;
    @XmlElement(required = true)
    protected String creditCheck;
    @XmlElement(required = true)
    protected String creditAlert;
    @XmlAttribute(name = "suppressionIndicator")
    protected String suppressionIndicator;
    @XmlAttribute(name = "consentToCC")
    protected String consentToCC;
    @XmlAttribute(name = "accountStatus")
    protected String accountStatus;
    @XmlAttribute(name = "accountTypeIndicator", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String accountTypeIndicator;

    /**
     * Gets the value of the externalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalId(String value) {
        this.externalId = value;
    }

    /**
     * Gets the value of the providerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderType() {
        return providerType;
    }

    /**
     * Sets the value of the providerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderType(String value) {
        this.providerType = value;
    }

    /**
     * Gets the value of the accountType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Sets the value of the accountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountType(String value) {
        this.accountType = value;
    }

    /**
     * Gets the value of the billingAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingAccountNumber() {
        return billingAccountNumber;
    }

    /**
     * Sets the value of the billingAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingAccountNumber(String value) {
        this.billingAccountNumber = value;
    }

    /**
     * Gets the value of the billingAccountTelephoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingAccountTelephoneNumber() {
        return billingAccountTelephoneNumber;
    }

    /**
     * Sets the value of the billingAccountTelephoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingAccountTelephoneNumber(String value) {
        this.billingAccountTelephoneNumber = value;
    }

    /**
     * Gets the value of the addressExtId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressExtId() {
        return addressExtId;
    }

    /**
     * Sets the value of the addressExtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressExtId(String value) {
        this.addressExtId = value;
    }

    /**
     * Gets the value of the consumerExtId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsumerExtId() {
        return consumerExtId;
    }

    /**
     * Sets the value of the consumerExtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsumerExtId(String value) {
        this.consumerExtId = value;
    }

    /**
     * Gets the value of the authentication property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityVerificationType }
     *     
     */
    public SecurityVerificationType getAuthentication() {
        return authentication;
    }

    /**
     * Sets the value of the authentication property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityVerificationType }
     *     
     */
    public void setAuthentication(SecurityVerificationType value) {
        this.authentication = value;
    }

    /**
     * Gets the value of the existingLast4SSN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistingLast4SSN() {
        return existingLast4SSN;
    }

    /**
     * Sets the value of the existingLast4SSN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistingLast4SSN(String value) {
        this.existingLast4SSN = value;
    }

    /**
     * Gets the value of the securityCreditCard property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityCreditCardType }
     *     
     */
    public SecurityCreditCardType getSecurityCreditCard() {
        return securityCreditCard;
    }

    /**
     * Sets the value of the securityCreditCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityCreditCardType }
     *     
     */
    public void setSecurityCreditCard(SecurityCreditCardType value) {
        this.securityCreditCard = value;
    }

    /**
     * Gets the value of the prevAddressIndicator property.
     * 
     */
    public boolean isPrevAddressIndicator() {
        return prevAddressIndicator;
    }

    /**
     * Sets the value of the prevAddressIndicator property.
     * 
     */
    public void setPrevAddressIndicator(boolean value) {
        this.prevAddressIndicator = value;
    }

    /**
     * Gets the value of the creditCheck property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCheck() {
        return creditCheck;
    }

    /**
     * Sets the value of the creditCheck property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCheck(String value) {
        this.creditCheck = value;
    }

    /**
     * Gets the value of the creditAlert property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditAlert() {
        return creditAlert;
    }

    /**
     * Sets the value of the creditAlert property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditAlert(String value) {
        this.creditAlert = value;
    }

    /**
     * Gets the value of the suppressionIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuppressionIndicator() {
        return suppressionIndicator;
    }

    /**
     * Sets the value of the suppressionIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuppressionIndicator(String value) {
        this.suppressionIndicator = value;
    }

    /**
     * Gets the value of the consentToCC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsentToCC() {
        return consentToCC;
    }

    /**
     * Sets the value of the consentToCC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsentToCC(String value) {
        this.consentToCC = value;
    }

    /**
     * Gets the value of the accountStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountStatus() {
        return accountStatus;
    }

    /**
     * Sets the value of the accountStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountStatus(String value) {
        this.accountStatus = value;
    }

    /**
     * Gets the value of the accountTypeIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountTypeIndicator() {
        return accountTypeIndicator;
    }

    /**
     * Sets the value of the accountTypeIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountTypeIndicator(String value) {
        this.accountTypeIndicator = value;
    }

}
