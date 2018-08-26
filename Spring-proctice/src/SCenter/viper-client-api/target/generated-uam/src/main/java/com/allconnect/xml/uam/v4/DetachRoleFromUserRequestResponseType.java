
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetachRoleFromUserRequestResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetachRoleFromUserRequestResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://xml.A.com/v4}DetachRoleFromUserRequestType" minOccurs="0"/>
 *         &lt;element name="response" type="{http://xml.A.com/v4}DetachRoleFromUserResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetachRoleFromUserRequestResponseType", propOrder = {
    "request",
    "response"
})
public class DetachRoleFromUserRequestResponseType {

    protected DetachRoleFromUserRequestType request;
    protected DetachRoleFromUserResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link DetachRoleFromUserRequestType }
     *     
     */
    public DetachRoleFromUserRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetachRoleFromUserRequestType }
     *     
     */
    public void setRequest(DetachRoleFromUserRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link DetachRoleFromUserResponseType }
     *     
     */
    public DetachRoleFromUserResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetachRoleFromUserResponseType }
     *     
     */
    public void setResponse(DetachRoleFromUserResponseType value) {
        this.response = value;
    }

}
