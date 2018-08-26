
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditQualificationRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditQualificationRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4/OrderProvisioning/}ProvisioningRequest">
 *       &lt;sequence>
 *         &lt;element name="customerInfo" type="{http://xml.A.com/v4/OrderProvisioning/}OpCustomerInfo"/>
 *         &lt;element name="accountHolder" type="{http://xml.A.com/v4}accountHolderType"/>
 *         &lt;element name="productLineItem" type="{http://xml.A.com/v4/OrderProvisioning/}OpProductLineItem" minOccurs="0"/>
 *         &lt;element name="transientRequestContainer" type="{http://xml.A.com/v4}transientRequestContainerType" minOccurs="0"/>
 *         &lt;element name="skipCreditCheck" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hasLivedTwoYears" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="providerAttributes" type="{http://xml.A.com/v4}ProviderAttributes" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="verificationQuestion" type="{http://xml.A.com/v4}verificationQuestion" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="verificationAnswer" type="{http://xml.A.com/v4}verificationAnswer" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditQualificationRequest", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "customerInfo",
    "accountHolder",
    "productLineItem",
    "transientRequestContainer",
    "skipCreditCheck",
    "hasLivedTwoYears",
    "providerAttributes",
    "verificationQuestion",
    "verificationAnswer"
})
public class CreditQualificationRequest
    extends ProvisioningRequest
{

    @XmlElement(namespace = "", required = true)
    protected OpCustomerInfo customerInfo;
    @XmlElement(namespace = "", required = true)
    protected AccountHolderType accountHolder;
    @XmlElement(namespace = "")
    protected OpProductLineItem productLineItem;
    @XmlElement(namespace = "")
    protected TransientRequestContainerType transientRequestContainer;
    @XmlElement(namespace = "")
    protected Boolean skipCreditCheck;
    @XmlElement(namespace = "")
    protected Boolean hasLivedTwoYears;
    @XmlElement(namespace = "")
    protected List<ProviderAttributes> providerAttributes;
    @XmlElement(namespace = "")
    protected List<VerificationQuestion> verificationQuestion;
    @XmlElement(namespace = "")
    protected List<VerificationAnswer> verificationAnswer;

    /**
     * Gets the value of the customerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OpCustomerInfo }
     *     
     */
    public OpCustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    /**
     * Sets the value of the customerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpCustomerInfo }
     *     
     */
    public void setCustomerInfo(OpCustomerInfo value) {
        this.customerInfo = value;
    }

    /**
     * Gets the value of the accountHolder property.
     * 
     * @return
     *     possible object is
     *     {@link AccountHolderType }
     *     
     */
    public AccountHolderType getAccountHolder() {
        return accountHolder;
    }

    /**
     * Sets the value of the accountHolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountHolderType }
     *     
     */
    public void setAccountHolder(AccountHolderType value) {
        this.accountHolder = value;
    }

    /**
     * Gets the value of the productLineItem property.
     * 
     * @return
     *     possible object is
     *     {@link OpProductLineItem }
     *     
     */
    public OpProductLineItem getProductLineItem() {
        return productLineItem;
    }

    /**
     * Sets the value of the productLineItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpProductLineItem }
     *     
     */
    public void setProductLineItem(OpProductLineItem value) {
        this.productLineItem = value;
    }

    /**
     * Gets the value of the transientRequestContainer property.
     * 
     * @return
     *     possible object is
     *     {@link TransientRequestContainerType }
     *     
     */
    public TransientRequestContainerType getTransientRequestContainer() {
        return transientRequestContainer;
    }

    /**
     * Sets the value of the transientRequestContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransientRequestContainerType }
     *     
     */
    public void setTransientRequestContainer(TransientRequestContainerType value) {
        this.transientRequestContainer = value;
    }

    /**
     * Gets the value of the skipCreditCheck property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSkipCreditCheck() {
        return skipCreditCheck;
    }

    /**
     * Sets the value of the skipCreditCheck property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSkipCreditCheck(Boolean value) {
        this.skipCreditCheck = value;
    }

    /**
     * Gets the value of the hasLivedTwoYears property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHasLivedTwoYears() {
        return hasLivedTwoYears;
    }

    /**
     * Sets the value of the hasLivedTwoYears property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHasLivedTwoYears(Boolean value) {
        this.hasLivedTwoYears = value;
    }

    /**
     * Gets the value of the providerAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the providerAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProviderAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProviderAttributes }
     * 
     * 
     */
    public List<ProviderAttributes> getProviderAttributes() {
        if (providerAttributes == null) {
            providerAttributes = new ArrayList<ProviderAttributes>();
        }
        return this.providerAttributes;
    }

    /**
     * Gets the value of the verificationQuestion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the verificationQuestion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVerificationQuestion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VerificationQuestion }
     * 
     * 
     */
    public List<VerificationQuestion> getVerificationQuestion() {
        if (verificationQuestion == null) {
            verificationQuestion = new ArrayList<VerificationQuestion>();
        }
        return this.verificationQuestion;
    }

    /**
     * Gets the value of the verificationAnswer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the verificationAnswer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVerificationAnswer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VerificationAnswer }
     * 
     * 
     */
    public List<VerificationAnswer> getVerificationAnswer() {
        if (verificationAnswer == null) {
            verificationAnswer = new ArrayList<VerificationAnswer>();
        }
        return this.verificationAnswer;
    }

}
