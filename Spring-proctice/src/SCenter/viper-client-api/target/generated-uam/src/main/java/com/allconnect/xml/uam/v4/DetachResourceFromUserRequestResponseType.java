
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetachResourceFromUserRequestResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetachResourceFromUserRequestResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://xml.A.com/v4}DetachResourceFromUserRequestType" minOccurs="0"/>
 *         &lt;element name="response" type="{http://xml.A.com/v4}DetachResourceFromUserResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetachResourceFromUserRequestResponseType", propOrder = {
    "request",
    "response"
})
public class DetachResourceFromUserRequestResponseType {

    protected DetachResourceFromUserRequestType request;
    protected DetachResourceFromUserResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link DetachResourceFromUserRequestType }
     *     
     */
    public DetachResourceFromUserRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetachResourceFromUserRequestType }
     *     
     */
    public void setRequest(DetachResourceFromUserRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link DetachResourceFromUserResponseType }
     *     
     */
    public DetachResourceFromUserResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetachResourceFromUserResponseType }
     *     
     */
    public void setResponse(DetachResourceFromUserResponseType value) {
        this.response = value;
    }

}
