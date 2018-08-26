
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachRoleToUserRequestResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachRoleToUserRequestResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://xml.A.com/v4}AttachRoleToUserRequestType" minOccurs="0"/>
 *         &lt;element name="response" type="{http://xml.A.com/v4}AttachRoleToUserResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachRoleToUserRequestResponseType", propOrder = {
    "request",
    "response"
})
public class AttachRoleToUserRequestResponseType {

    protected AttachRoleToUserRequestType request;
    protected AttachRoleToUserResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link AttachRoleToUserRequestType }
     *     
     */
    public AttachRoleToUserRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachRoleToUserRequestType }
     *     
     */
    public void setRequest(AttachRoleToUserRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link AttachRoleToUserResponseType }
     *     
     */
    public AttachRoleToUserResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachRoleToUserResponseType }
     *     
     */
    public void setResponse(AttachRoleToUserResponseType value) {
        this.response = value;
    }

}
