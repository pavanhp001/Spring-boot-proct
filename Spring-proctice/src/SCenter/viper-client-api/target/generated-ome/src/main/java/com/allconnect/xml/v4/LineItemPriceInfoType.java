
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for lineItemPriceInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lineItemPriceInfoType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}priceInfoType">
 *       &lt;sequence>
 *         &lt;element name="priceInfoStatus" type="{http://xml.A.com/v4}statusType"/>
 *         &lt;element name="pricingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="onDeliveryPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lineItemPriceInfoType", propOrder = {
    "priceInfoStatus",
    "pricingDate",
    "onDeliveryPrice"
})
public class LineItemPriceInfoType
    extends PriceInfoType
{

    @XmlElement(required = true)
    protected StatusType priceInfoStatus;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar pricingDate;
    protected String onDeliveryPrice;

    /**
     * Gets the value of the priceInfoStatus property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getPriceInfoStatus() {
        return priceInfoStatus;
    }

    /**
     * Sets the value of the priceInfoStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setPriceInfoStatus(StatusType value) {
        this.priceInfoStatus = value;
    }

    /**
     * Gets the value of the pricingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPricingDate() {
        return pricingDate;
    }

    /**
     * Sets the value of the pricingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPricingDate(XMLGregorianCalendar value) {
        this.pricingDate = value;
    }

    /**
     * Gets the value of the onDeliveryPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnDeliveryPrice() {
        return onDeliveryPrice;
    }

    /**
     * Sets the value of the onDeliveryPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnDeliveryPrice(String value) {
        this.onDeliveryPrice = value;
    }

}
