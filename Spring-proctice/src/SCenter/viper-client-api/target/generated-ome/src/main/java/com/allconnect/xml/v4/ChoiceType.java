
package com.A.xml.v4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for choiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="choiceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shortDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="longDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentEventType" type="{http://xml.A.com/v4}paymentEventTypeType" minOccurs="0"/>
 *         &lt;element name="priceInfo" type="{http://xml.A.com/v4}priceInfoType" minOccurs="0"/>
 *         &lt;element name="customization" type="{http://xml.A.com/v4}customizationType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="externalId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="displayOrder" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "choiceType", propOrder = {
    "name",
    "shortDescription",
    "longDescription",
    "paymentEventType",
    "priceInfo",
    "customization"
})
public class ChoiceType {

    @XmlElement(required = true)
    protected String name;
    protected String shortDescription;
    protected String longDescription;
    protected PaymentEventTypeType paymentEventType;
    protected PriceInfoType priceInfo;
    protected List<CustomizationType> customization;
    @XmlAttribute(name = "externalId", required = true)
    protected String externalId;
    @XmlAttribute(name = "displayOrder")
    protected BigInteger displayOrder;

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
     * Gets the value of the paymentEventType property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentEventTypeType }
     *     
     */
    public PaymentEventTypeType getPaymentEventType() {
        return paymentEventType;
    }

    /**
     * Sets the value of the paymentEventType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentEventTypeType }
     *     
     */
    public void setPaymentEventType(PaymentEventTypeType value) {
        this.paymentEventType = value;
    }

    /**
     * Gets the value of the priceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PriceInfoType }
     *     
     */
    public PriceInfoType getPriceInfo() {
        return priceInfo;
    }

    /**
     * Sets the value of the priceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceInfoType }
     *     
     */
    public void setPriceInfo(PriceInfoType value) {
        this.priceInfo = value;
    }

    /**
     * Gets the value of the customization property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customization property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomization().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomizationType }
     * 
     * 
     */
    public List<CustomizationType> getCustomization() {
        if (customization == null) {
            customization = new ArrayList<CustomizationType>();
        }
        return this.customization;
    }

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
     * Gets the value of the displayOrder property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDisplayOrder() {
        return displayOrder;
    }

    /**
     * Sets the value of the displayOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDisplayOrder(BigInteger value) {
        this.displayOrder = value;
    }

}
