
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicablePromotion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicablePromotion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="promotion" type="{http://xml.A.com/v4}productPromotionType"/>
 *         &lt;element name="priceInfo" type="{http://xml.A.com/v4}priceInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicablePromotion", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "externalId",
    "promotion",
    "priceInfo"
})
public class ApplicablePromotion {

    @XmlElement(namespace = "")
    protected long externalId;
    @XmlElement(namespace = "", required = true)
    protected ProductPromotionType promotion;
    @XmlElement(namespace = "")
    protected PriceInfoType priceInfo;

    /**
     * Gets the value of the externalId property.
     * 
     */
    public long getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     */
    public void setExternalId(long value) {
        this.externalId = value;
    }

    /**
     * Gets the value of the promotion property.
     * 
     * @return
     *     possible object is
     *     {@link ProductPromotionType }
     *     
     */
    public ProductPromotionType getPromotion() {
        return promotion;
    }

    /**
     * Sets the value of the promotion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductPromotionType }
     *     
     */
    public void setPromotion(ProductPromotionType value) {
        this.promotion = value;
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

}
