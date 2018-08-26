
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for orderLineItemDetailTypeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orderLineItemDetailTypeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="productLineItem" type="{http://xml.A.com/v4}productType"/>
 *         &lt;element name="productBundleLineItem" type="{http://xml.A.com/v4}productBundleType"/>
 *         &lt;element name="promotionLineItem" type="{http://xml.A.com/v4}applicableType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderLineItemDetailTypeType", propOrder = {
    "productLineItem",
    "productBundleLineItem",
    "promotionLineItem"
})
public class OrderLineItemDetailTypeType {

    protected ProductType productLineItem;
    protected ProductBundleType productBundleLineItem;
    protected ApplicableType promotionLineItem;

    /**
     * Gets the value of the productLineItem property.
     * 
     * @return
     *     possible object is
     *     {@link ProductType }
     *     
     */
    public ProductType getProductLineItem() {
        return productLineItem;
    }

    /**
     * Sets the value of the productLineItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductType }
     *     
     */
    public void setProductLineItem(ProductType value) {
        this.productLineItem = value;
    }

    /**
     * Gets the value of the productBundleLineItem property.
     * 
     * @return
     *     possible object is
     *     {@link ProductBundleType }
     *     
     */
    public ProductBundleType getProductBundleLineItem() {
        return productBundleLineItem;
    }

    /**
     * Sets the value of the productBundleLineItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductBundleType }
     *     
     */
    public void setProductBundleLineItem(ProductBundleType value) {
        this.productBundleLineItem = value;
    }

    /**
     * Gets the value of the promotionLineItem property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicableType }
     *     
     */
    public ApplicableType getPromotionLineItem() {
        return promotionLineItem;
    }

    /**
     * Sets the value of the promotionLineItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicableType }
     *     
     */
    public void setPromotionLineItem(ApplicableType value) {
        this.promotionLineItem = value;
    }

}
