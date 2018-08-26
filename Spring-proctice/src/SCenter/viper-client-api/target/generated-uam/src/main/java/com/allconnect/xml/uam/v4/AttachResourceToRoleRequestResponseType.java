
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachResourceToRoleRequestResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachResourceToRoleRequestResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://xml.A.com/v4}AttachResourceToRoleRequestType" minOccurs="0"/>
 *         &lt;element name="response" type="{http://xml.A.com/v4}AttachResourceToRoleResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachResourceToRoleRequestResponseType", propOrder = {
    "request",
    "response"
})
public class AttachResourceToRoleRequestResponseType {

    protected AttachResourceToRoleRequestType request;
    protected AttachResourceToRoleResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link AttachResourceToRoleRequestType }
     *     
     */
    public AttachResourceToRoleRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachResourceToRoleRequestType }
     *     
     */
    public void setRequest(AttachResourceToRoleRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link AttachResourceToRoleResponseType }
     *     
     */
    public AttachResourceToRoleResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachResourceToRoleResponseType }
     *     
     */
    public void setResponse(AttachResourceToRoleResponseType value) {
        this.response = value;
    }

}
