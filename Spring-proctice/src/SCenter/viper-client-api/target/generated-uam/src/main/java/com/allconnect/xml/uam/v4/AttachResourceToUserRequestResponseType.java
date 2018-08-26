
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachResourceToUserRequestResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachResourceToUserRequestResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://xml.A.com/v4}AttachResourceToUserRequestType" minOccurs="0"/>
 *         &lt;element name="response" type="{http://xml.A.com/v4}AttachResourceToUserResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachResourceToUserRequestResponseType", propOrder = {
    "request",
    "response"
})
public class AttachResourceToUserRequestResponseType {

    protected AttachResourceToUserRequestType request;
    protected AttachResourceToUserResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link AttachResourceToUserRequestType }
     *     
     */
    public AttachResourceToUserRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachResourceToUserRequestType }
     *     
     */
    public void setRequest(AttachResourceToUserRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link AttachResourceToUserResponseType }
     *     
     */
    public AttachResourceToUserResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachResourceToUserResponseType }
     *     
     */
    public void setResponse(AttachResourceToUserResponseType value) {
        this.response = value;
    }

}
