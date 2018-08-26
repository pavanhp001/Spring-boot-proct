
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transientResponseContainerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transientResponseContainerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transientResponse" type="{http://xml.A.com/v4}transientResponseType" minOccurs="0"/>
 *         &lt;element name="lineItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transientResponseContainerType", propOrder = {
    "transientResponse",
    "lineItemId"
})
public class TransientResponseContainerType {

    protected TransientResponseType transientResponse;
    protected String lineItemId;

    /**
     * Gets the value of the transientResponse property.
     * 
     * @return
     *     possible object is
     *     {@link TransientResponseType }
     *     
     */
    public TransientResponseType getTransientResponse() {
        return transientResponse;
    }

    /**
     * Sets the value of the transientResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransientResponseType }
     *     
     */
    public void setTransientResponse(TransientResponseType value) {
        this.transientResponse = value;
    }

    /**
     * Gets the value of the lineItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineItemId() {
        return lineItemId;
    }

    /**
     * Sets the value of the lineItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineItemId(String value) {
        this.lineItemId = value;
    }

}
