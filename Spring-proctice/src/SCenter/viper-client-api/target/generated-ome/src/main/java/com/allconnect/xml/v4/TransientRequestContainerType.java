
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transientRequestContainerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transientRequestContainerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transientRequest" type="{http://xml.A.com/v4}transientRequestType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transientRequestContainerType", propOrder = {
    "transientRequest"
})
public class TransientRequestContainerType {

    protected TransientRequestType transientRequest;

    /**
     * Gets the value of the transientRequest property.
     * 
     * @return
     *     possible object is
     *     {@link TransientRequestType }
     *     
     */
    public TransientRequestType getTransientRequest() {
        return transientRequest;
    }

    /**
     * Sets the value of the transientRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransientRequestType }
     *     
     */
    public void setTransientRequest(TransientRequestType value) {
        this.transientRequest = value;
    }

}
