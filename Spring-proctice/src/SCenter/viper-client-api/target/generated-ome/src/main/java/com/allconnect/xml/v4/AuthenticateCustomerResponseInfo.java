
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticateCustomerResponseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticateCustomerResponseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billingAddress" type="{http://xml.A.com/v4}billingAddress"/>
 *         &lt;element name="regionalProgramming" type="{http://xml.A.com/v4}RegionalProgramming" minOccurs="0"/>
 *         &lt;element name="localProgramming" type="{http://xml.A.com/v4}LocalProgramming" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticateCustomerResponseInfo", propOrder = {
    "billingAddress",
    "regionalProgramming",
    "localProgramming"
})
public class AuthenticateCustomerResponseInfo {

    @XmlElement(required = true)
    protected BillingAddress billingAddress;
    protected RegionalProgramming regionalProgramming;
    protected LocalProgramming localProgramming;

    /**
     * Gets the value of the billingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link BillingAddress }
     *     
     */
    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    /**
     * Sets the value of the billingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingAddress }
     *     
     */
    public void setBillingAddress(BillingAddress value) {
        this.billingAddress = value;
    }

    /**
     * Gets the value of the regionalProgramming property.
     * 
     * @return
     *     possible object is
     *     {@link RegionalProgramming }
     *     
     */
    public RegionalProgramming getRegionalProgramming() {
        return regionalProgramming;
    }

    /**
     * Sets the value of the regionalProgramming property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegionalProgramming }
     *     
     */
    public void setRegionalProgramming(RegionalProgramming value) {
        this.regionalProgramming = value;
    }

    /**
     * Gets the value of the localProgramming property.
     * 
     * @return
     *     possible object is
     *     {@link LocalProgramming }
     *     
     */
    public LocalProgramming getLocalProgramming() {
        return localProgramming;
    }

    /**
     * Sets the value of the localProgramming property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalProgramming }
     *     
     */
    public void setLocalProgramming(LocalProgramming value) {
        this.localProgramming = value;
    }

}
