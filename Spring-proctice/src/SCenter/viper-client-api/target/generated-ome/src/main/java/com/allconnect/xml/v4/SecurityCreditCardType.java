
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SecurityCreditCardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecurityCreditCardType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="securityTCAccepted" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isAuthorized" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="creditCardRef" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecurityCreditCardType", propOrder = {
    "securityTCAccepted",
    "isAuthorized",
    "creditCardRef"
})
public class SecurityCreditCardType {

    @XmlElement(required = true)
    protected String securityTCAccepted;
    @XmlElement(required = true)
    protected String isAuthorized;
    @XmlElement(required = true)
    protected String creditCardRef;

    /**
     * Gets the value of the securityTCAccepted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityTCAccepted() {
        return securityTCAccepted;
    }

    /**
     * Sets the value of the securityTCAccepted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityTCAccepted(String value) {
        this.securityTCAccepted = value;
    }

    /**
     * Gets the value of the isAuthorized property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAuthorized() {
        return isAuthorized;
    }

    /**
     * Sets the value of the isAuthorized property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAuthorized(String value) {
        this.isAuthorized = value;
    }

    /**
     * Gets the value of the creditCardRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardRef() {
        return creditCardRef;
    }

    /**
     * Sets the value of the creditCardRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardRef(String value) {
        this.creditCardRef = value;
    }

}
