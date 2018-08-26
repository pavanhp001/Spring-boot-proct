
package com.A.xml.me.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				This container contains all of the pricing
 * 				information used by various
 * 				entities.
 * 				each contains a dollar amount
 * 				representing the one-time price and the
 * 				(typically monthly) recurring
 * 				price of the service.
 * 			
 * 
 * <p>Java class for priceInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="priceInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="baseNonRecurringPrice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="baseNonRecurringPriceUnits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="baseRecurringPrice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="baseRecurringPriceUnits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="includeInTotalPrice" type="{http://xml.A.com/v4}emptyElementType" minOccurs="0"/>
 *         &lt;element name="energyPriceInfo" type="{http://xml.A.com/v4}energyPriceInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "priceInfoType", propOrder = {
    "baseNonRecurringPrice",
    "baseNonRecurringPriceUnits",
    "baseRecurringPrice",
    "baseRecurringPriceUnits",
    "includeInTotalPrice",
    "energyPriceInfo"
})
public class PriceInfoType {

    protected Double baseNonRecurringPrice;
    protected String baseNonRecurringPriceUnits;
    protected Double baseRecurringPrice;
    protected String baseRecurringPriceUnits;
    protected EmptyElementType includeInTotalPrice;
    protected EnergyPriceInfoType energyPriceInfo;

    /**
     * Gets the value of the baseNonRecurringPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBaseNonRecurringPrice() {
        return baseNonRecurringPrice;
    }

    /**
     * Sets the value of the baseNonRecurringPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBaseNonRecurringPrice(Double value) {
        this.baseNonRecurringPrice = value;
    }

    /**
     * Gets the value of the baseNonRecurringPriceUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseNonRecurringPriceUnits() {
        return baseNonRecurringPriceUnits;
    }

    /**
     * Sets the value of the baseNonRecurringPriceUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseNonRecurringPriceUnits(String value) {
        this.baseNonRecurringPriceUnits = value;
    }

    /**
     * Gets the value of the baseRecurringPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBaseRecurringPrice() {
        return baseRecurringPrice;
    }

    /**
     * Sets the value of the baseRecurringPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBaseRecurringPrice(Double value) {
        this.baseRecurringPrice = value;
    }

    /**
     * Gets the value of the baseRecurringPriceUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseRecurringPriceUnits() {
        return baseRecurringPriceUnits;
    }

    /**
     * Sets the value of the baseRecurringPriceUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseRecurringPriceUnits(String value) {
        this.baseRecurringPriceUnits = value;
    }

    /**
     * Gets the value of the includeInTotalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link EmptyElementType }
     *     
     */
    public EmptyElementType getIncludeInTotalPrice() {
        return includeInTotalPrice;
    }

    /**
     * Sets the value of the includeInTotalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmptyElementType }
     *     
     */
    public void setIncludeInTotalPrice(EmptyElementType value) {
        this.includeInTotalPrice = value;
    }

    /**
     * Gets the value of the energyPriceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link EnergyPriceInfoType }
     *     
     */
    public EnergyPriceInfoType getEnergyPriceInfo() {
        return energyPriceInfo;
    }

    /**
     * Sets the value of the energyPriceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnergyPriceInfoType }
     *     
     */
    public void setEnergyPriceInfo(EnergyPriceInfoType value) {
        this.energyPriceInfo = value;
    }

}
