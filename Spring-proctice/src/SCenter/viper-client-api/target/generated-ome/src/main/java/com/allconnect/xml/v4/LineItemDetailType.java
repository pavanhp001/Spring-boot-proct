
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for lineItemDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lineItemDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="detailType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="product"/>
 *               &lt;enumeration value="productBundle"/>
 *               &lt;enumeration value="productPromotion"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="detail" type="{http://xml.A.com/v4}orderLineItemDetailTypeType"/>
 *         &lt;element name="productUniqueId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lineItemDetailType", propOrder = {
    "detailType",
    "detail",
    "productUniqueId"
})
public class LineItemDetailType {

    @XmlElement(required = true)
    protected String detailType;
    @XmlElement(required = true)
    protected OrderLineItemDetailTypeType detail;
    protected Long productUniqueId;

    /**
     * Gets the value of the detailType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailType() {
        return detailType;
    }

    /**
     * Sets the value of the detailType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailType(String value) {
        this.detailType = value;
    }

    /**
     * Gets the value of the detail property.
     * 
     * @return
     *     possible object is
     *     {@link OrderLineItemDetailTypeType }
     *     
     */
    public OrderLineItemDetailTypeType getDetail() {
        return detail;
    }

    /**
     * Sets the value of the detail property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderLineItemDetailTypeType }
     *     
     */
    public void setDetail(OrderLineItemDetailTypeType value) {
        this.detail = value;
    }

    /**
     * Gets the value of the productUniqueId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getProductUniqueId() {
        return productUniqueId;
    }

    /**
     * Sets the value of the productUniqueId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setProductUniqueId(Long value) {
        this.productUniqueId = value;
    }

}
