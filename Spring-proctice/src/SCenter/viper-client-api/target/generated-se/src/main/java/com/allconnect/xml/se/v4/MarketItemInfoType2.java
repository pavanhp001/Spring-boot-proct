
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for marketItemInfoType2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="marketItemInfoType2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="marketItem" type="{http://xml.A.com/v4}marketItemWithCapabilitiesType"/>
 *         &lt;element name="serviceAddress" type="{http://xml.A.com/v4}addressType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "marketItemInfoType2", propOrder = {
    "marketItem",
    "serviceAddress"
})
public class MarketItemInfoType2 {

    @XmlElement(required = true)
    protected MarketItemWithCapabilitiesType marketItem;
    protected AddressType serviceAddress;

    /**
     * Gets the value of the marketItem property.
     * 
     * @return
     *     possible object is
     *     {@link MarketItemWithCapabilitiesType }
     *     
     */
    public MarketItemWithCapabilitiesType getMarketItem() {
        return marketItem;
    }

    /**
     * Sets the value of the marketItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarketItemWithCapabilitiesType }
     *     
     */
    public void setMarketItem(MarketItemWithCapabilitiesType value) {
        this.marketItem = value;
    }

    /**
     * Gets the value of the serviceAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getServiceAddress() {
        return serviceAddress;
    }

    /**
     * Sets the value of the serviceAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setServiceAddress(AddressType value) {
        this.serviceAddress = value;
    }

}
