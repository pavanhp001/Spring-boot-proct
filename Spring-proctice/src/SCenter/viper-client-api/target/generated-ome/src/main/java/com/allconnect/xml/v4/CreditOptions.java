
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChoiceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="USOC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PaymentType" type="{http://xml.A.com/v4}PaymentUniqueId" minOccurs="0"/>
 *         &lt;element name="ShortDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LongDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fees" type="{http://xml.A.com/v4}CreditFees" minOccurs="0"/>
 *         &lt;element name="ChoiceType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="OptionalPackage"/>
 *               &lt;enumeration value="Equipment"/>
 *               &lt;enumeration value="Credit"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CreditDetails" type="{http://xml.A.com/v4}CreditDetails" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditOptions", propOrder = {
    "choiceCode",
    "name",
    "usoc",
    "paymentType",
    "shortDescription",
    "longDescription",
    "fees",
    "choiceType",
    "creditDetails"
})
public class CreditOptions {

    @XmlElement(name = "ChoiceCode", required = true)
    protected String choiceCode;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "USOC", required = true)
    protected String usoc;
    @XmlElement(name = "PaymentType")
    protected PaymentUniqueId paymentType;
    @XmlElement(name = "ShortDescription")
    protected String shortDescription;
    @XmlElement(name = "LongDescription")
    protected String longDescription;
    @XmlElement(name = "Fees")
    protected CreditFees fees;
    @XmlElement(name = "ChoiceType", required = true)
    protected String choiceType;
    @XmlElement(name = "CreditDetails")
    protected CreditDetails creditDetails;

    /**
     * Gets the value of the choiceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChoiceCode() {
        return choiceCode;
    }

    /**
     * Sets the value of the choiceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChoiceCode(String value) {
        this.choiceCode = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the usoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSOC() {
        return usoc;
    }

    /**
     * Sets the value of the usoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSOC(String value) {
        this.usoc = value;
    }

    /**
     * Gets the value of the paymentType property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentUniqueId }
     *     
     */
    public PaymentUniqueId getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentUniqueId }
     *     
     */
    public void setPaymentType(PaymentUniqueId value) {
        this.paymentType = value;
    }

    /**
     * Gets the value of the shortDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortDescription(String value) {
        this.shortDescription = value;
    }

    /**
     * Gets the value of the longDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * Sets the value of the longDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongDescription(String value) {
        this.longDescription = value;
    }

    /**
     * Gets the value of the fees property.
     * 
     * @return
     *     possible object is
     *     {@link CreditFees }
     *     
     */
    public CreditFees getFees() {
        return fees;
    }

    /**
     * Sets the value of the fees property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditFees }
     *     
     */
    public void setFees(CreditFees value) {
        this.fees = value;
    }

    /**
     * Gets the value of the choiceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChoiceType() {
        return choiceType;
    }

    /**
     * Sets the value of the choiceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChoiceType(String value) {
        this.choiceType = value;
    }

    /**
     * Gets the value of the creditDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CreditDetails }
     *     
     */
    public CreditDetails getCreditDetails() {
        return creditDetails;
    }

    /**
     * Sets the value of the creditDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditDetails }
     *     
     */
    public void setCreditDetails(CreditDetails value) {
        this.creditDetails = value;
    }

}
