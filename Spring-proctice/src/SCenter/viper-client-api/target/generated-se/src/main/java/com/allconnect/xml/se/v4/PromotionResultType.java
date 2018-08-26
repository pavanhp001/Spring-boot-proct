
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 			Contains the specialized result type for the requested type and external id.
 * 			
 * 
 * <p>Java class for promotionResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="promotionResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="itemResult" type="{http://xml.A.com/v4}itemPromoType"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="marketItemResult" type="{http://xml.A.com/v4}marketItemPromoType"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="bundleResult" type="{http://xml.A.com/v4}bundlePromoType"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "promotionResultType", propOrder = {
    "bundleResult",
    "marketItemResult",
    "itemResult"
})
public class PromotionResultType {

    protected BundlePromoType bundleResult;
    protected MarketItemPromoType marketItemResult;
    protected ItemPromoType itemResult;

    /**
     * Gets the value of the bundleResult property.
     * 
     * @return
     *     possible object is
     *     {@link BundlePromoType }
     *     
     */
    public BundlePromoType getBundleResult() {
        return bundleResult;
    }

    /**
     * Sets the value of the bundleResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link BundlePromoType }
     *     
     */
    public void setBundleResult(BundlePromoType value) {
        this.bundleResult = value;
    }

    /**
     * Gets the value of the marketItemResult property.
     * 
     * @return
     *     possible object is
     *     {@link MarketItemPromoType }
     *     
     */
    public MarketItemPromoType getMarketItemResult() {
        return marketItemResult;
    }

    /**
     * Sets the value of the marketItemResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarketItemPromoType }
     *     
     */
    public void setMarketItemResult(MarketItemPromoType value) {
        this.marketItemResult = value;
    }

    /**
     * Gets the value of the itemResult property.
     * 
     * @return
     *     possible object is
     *     {@link ItemPromoType }
     *     
     */
    public ItemPromoType getItemResult() {
        return itemResult;
    }

    /**
     * Sets the value of the itemResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemPromoType }
     *     
     */
    public void setItemResult(ItemPromoType value) {
        this.itemResult = value;
    }

}
