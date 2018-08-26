
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PriceField complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PriceField">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nameEnum" type="{http://xml.A.com/v4}PriceFieldNameEnum"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="frequency" type="{http://xml.A.com/v4}PriceFrequencyEnum"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priceText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minimumUsage" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PriceField", propOrder = {
    "nameEnum",
    "price",
    "frequency",
    "description",
    "priceText",
    "minimumUsage"
})
public class PriceField {

    @XmlElement(required = true)
    protected PriceFieldNameEnum nameEnum;
    @XmlElement(required = true)
    protected String price;
    @XmlElement(required = true)
    protected PriceFrequencyEnum frequency;
    protected String description;
    protected String priceText;
    protected Integer minimumUsage;

    /**
     * Gets the value of the nameEnum property.
     * 
     * @return
     *     possible object is
     *     {@link PriceFieldNameEnum }
     *     
     */
    public PriceFieldNameEnum getNameEnum() {
        return nameEnum;
    }

    /**
     * Sets the value of the nameEnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceFieldNameEnum }
     *     
     */
    public void setNameEnum(PriceFieldNameEnum value) {
        this.nameEnum = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrice(String value) {
        this.price = value;
    }

    /**
     * Gets the value of the frequency property.
     * 
     * @return
     *     possible object is
     *     {@link PriceFrequencyEnum }
     *     
     */
    public PriceFrequencyEnum getFrequency() {
        return frequency;
    }

    /**
     * Sets the value of the frequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceFrequencyEnum }
     *     
     */
    public void setFrequency(PriceFrequencyEnum value) {
        this.frequency = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the priceText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceText() {
        return priceText;
    }

    /**
     * Sets the value of the priceText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceText(String value) {
        this.priceText = value;
    }

    /**
     * Gets the value of the minimumUsage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinimumUsage() {
        return minimumUsage;
    }

    /**
     * Sets the value of the minimumUsage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinimumUsage(Integer value) {
        this.minimumUsage = value;
    }

}
