
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetachResourceFromRoleRequestResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetachResourceFromRoleRequestResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://xml.A.com/v4}DetachResourceFromRoleRequestType" minOccurs="0"/>
 *         &lt;element name="response" type="{http://xml.A.com/v4}DetachResourceFromRoleResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetachResourceFromRoleRequestResponseType", propOrder = {
    "request",
    "response"
})
public class DetachResourceFromRoleRequestResponseType {

    protected DetachResourceFromRoleRequestType request;
    protected DetachResourceFromRoleResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link DetachResourceFromRoleRequestType }
     *     
     */
    public DetachResourceFromRoleRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetachResourceFromRoleRequestType }
     *     
     */
    public void setRequest(DetachResourceFromRoleRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link DetachResourceFromRoleResponseType }
     *     
     */
    public DetachResourceFromRoleResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetachResourceFromRoleResponseType }
     *     
     */
    public void setResponse(DetachResourceFromRoleResponseType value) {
        this.response = value;
    }

}
