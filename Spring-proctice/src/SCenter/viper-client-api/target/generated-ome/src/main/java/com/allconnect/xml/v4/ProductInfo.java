
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="productId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="productRule" type="{http://xml.A.com/v4}ProductRule"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductInfo", propOrder = {
    "productId",
    "productRule"
})
public class ProductInfo {

    protected int productId;
    @XmlElement(required = true)
    protected ProductRule productRule;

    /**
     * Gets the value of the productId property.
     * 
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets the value of the productId property.
     * 
     */
    public void setProductId(int value) {
        this.productId = value;
    }

    /**
     * Gets the value of the productRule property.
     * 
     * @return
     *     possible object is
     *     {@link ProductRule }
     *     
     */
    public ProductRule getProductRule() {
        return productRule;
    }

    /**
     * Sets the value of the productRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductRule }
     *     
     */
    public void setProductRule(ProductRule value) {
        this.productRule = value;
    }

}
