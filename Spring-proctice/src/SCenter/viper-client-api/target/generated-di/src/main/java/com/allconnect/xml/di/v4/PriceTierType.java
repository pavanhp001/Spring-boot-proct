
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for priceTierType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="priceTierType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="price" type="{http://xml.A.com/v4}priceInfoType"/>
 *         &lt;element name="rangeStart" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rangeEnd" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "priceTierType", propOrder = {
    "price",
    "rangeStart",
    "rangeEnd"
})
public class PriceTierType {

    @XmlElement(required = true)
    protected PriceInfoType price;
    protected int rangeStart;
    protected Integer rangeEnd;

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link PriceInfoType }
     *     
     */
    public PriceInfoType getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceInfoType }
     *     
     */
    public void setPrice(PriceInfoType value) {
        this.price = value;
    }

    /**
     * Gets the value of the rangeStart property.
     * 
     */
    public int getRangeStart() {
        return rangeStart;
    }

    /**
     * Sets the value of the rangeStart property.
     * 
     */
    public void setRangeStart(int value) {
        this.rangeStart = value;
    }

    /**
     * Gets the value of the rangeEnd property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRangeEnd() {
        return rangeEnd;
    }

    /**
     * Sets the value of the rangeEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRangeEnd(Integer value) {
        this.rangeEnd = value;
    }

}
