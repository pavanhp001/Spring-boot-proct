
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 			Contains the specialized result type for the requested type and external id.
 * 			
 * 
 * <p>Java class for orderSourceResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orderSourceResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderSource" type="{http://xml.A.com/v4}orderSourceType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderSourceResultType", propOrder = {
    "orderSource"
})
public class OrderSourceResultType {

    @XmlElement(required = true)
    protected OrderSource orderSource;

    /**
     * Gets the value of the orderSource property.
     * 
     * @return
     *     possible object is
     *     {@link OrderSource }
     *     
     */
    public OrderSource getOrderSource() {
        return orderSource;
    }

    /**
     * Sets the value of the orderSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderSource }
     *     
     */
    public void setOrderSource(OrderSource value) {
        this.orderSource = value;
    }

}
