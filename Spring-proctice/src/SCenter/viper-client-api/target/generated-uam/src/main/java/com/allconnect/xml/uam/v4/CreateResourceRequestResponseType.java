
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateResourceRequestResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateResourceRequestResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://xml.A.com/v4}CreateResourceRequestType" minOccurs="0"/>
 *         &lt;element name="response" type="{http://xml.A.com/v4}CreateResourceResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateResourceRequestResponseType", propOrder = {
    "request",
    "response"
})
public class CreateResourceRequestResponseType {

    protected CreateResourceRequestType request;
    protected CreateResourceResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link CreateResourceRequestType }
     *     
     */
    public CreateResourceRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateResourceRequestType }
     *     
     */
    public void setRequest(CreateResourceRequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link CreateResourceResponseType }
     *     
     */
    public CreateResourceResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateResourceResponseType }
     *     
     */
    public void setResponse(CreateResourceResponseType value) {
        this.response = value;
    }

}
