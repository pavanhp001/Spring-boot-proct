
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 			Contains the specialized result type for the requested type and external id.
 * 			
 * 
 * <p>Java class for detailResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="itemResult" type="{http://xml.A.com/v4}itemDetailType"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="marketItemResult" type="{http://xml.A.com/v4}marketItemDetailType"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="businessPartyResult" type="{http://xml.A.com/v4}businessPartyDetailResultType"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="productCatalogResult" type="{http://xml.A.com/v4}marketItemDetailType"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="referenceResult" type="{http://xml.A.com/v4}referenceResultType"/>
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
@XmlType(name = "detailResultType", propOrder = {
    "referenceResult",
    "productCatalogResult",
    "businessPartyResult",
    "marketItemResult",
    "itemResult"
})
public class DetailResultType {

    protected ReferenceResultType referenceResult;
    protected MarketItemDetail productCatalogResult;
    protected BusinessPartyDetailResultType businessPartyResult;
    protected MarketItemDetail marketItemResult;
    protected ItemDetailType itemResult;

    /**
     * Gets the value of the referenceResult property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceResultType }
     *     
     */
    public ReferenceResultType getReferenceResult() {
        return referenceResult;
    }

    /**
     * Sets the value of the referenceResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceResultType }
     *     
     */
    public void setReferenceResult(ReferenceResultType value) {
        this.referenceResult = value;
    }

    /**
     * Gets the value of the productCatalogResult property.
     * 
     * @return
     *     possible object is
     *     {@link MarketItemDetail }
     *     
     */
    public MarketItemDetail getProductCatalogResult() {
        return productCatalogResult;
    }

    /**
     * Sets the value of the productCatalogResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarketItemDetail }
     *     
     */
    public void setProductCatalogResult(MarketItemDetail value) {
        this.productCatalogResult = value;
    }

    /**
     * Gets the value of the businessPartyResult property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessPartyDetailResultType }
     *     
     */
    public BusinessPartyDetailResultType getBusinessPartyResult() {
        return businessPartyResult;
    }

    /**
     * Sets the value of the businessPartyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessPartyDetailResultType }
     *     
     */
    public void setBusinessPartyResult(BusinessPartyDetailResultType value) {
        this.businessPartyResult = value;
    }

    /**
     * Gets the value of the marketItemResult property.
     * 
     * @return
     *     possible object is
     *     {@link MarketItemDetail }
     *     
     */
    public MarketItemDetail getMarketItemResult() {
        return marketItemResult;
    }

    /**
     * Sets the value of the marketItemResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarketItemDetail }
     *     
     */
    public void setMarketItemResult(MarketItemDetail value) {
        this.marketItemResult = value;
    }

    /**
     * Gets the value of the itemResult property.
     * 
     * @return
     *     possible object is
     *     {@link ItemDetailType }
     *     
     */
    public ItemDetailType getItemResult() {
        return itemResult;
    }

    /**
     * Sets the value of the itemResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemDetailType }
     *     
     */
    public void setItemResult(ItemDetailType value) {
        this.itemResult = value;
    }

}
