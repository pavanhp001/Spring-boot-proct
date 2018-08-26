
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateRoleRequestResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateRoleRequestResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://xml.A.com/v4}CreateRoleRequestType" minOccurs="0"/>
 *         &lt;element name="response" type="{http://xml.A.com/v4}CreateRoleResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateRoleRequestResponseType", propOrder = {
    "request",
    "response"
})
public class CreateRoleRequestResponseType {

    protected CreateRoleRequestType request;
    protected CreateRoleResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link CreateRoleRequestType }
     *     
     */
    public CreateRoleRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateRoleRequestType }
     *     
     */
    public void setRequest(CreateRoleRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link CreateRoleResponseType }
     *     
     */
    public CreateRoleResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateRoleResponseType }
     *     
     */
    public void setResponse(CreateRoleResponseType value) {
        this.response = value;
    }

}
