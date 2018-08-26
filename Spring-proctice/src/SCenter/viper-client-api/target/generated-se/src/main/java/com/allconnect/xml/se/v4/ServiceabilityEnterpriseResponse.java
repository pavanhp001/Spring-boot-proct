
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for enterpriseResponseDocumentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="enterpriseResponseDocumentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://xml.A.com/v4}statusType"/>
 *         &lt;element name="request" type="{http://xml.A.com/v4}enterpriseRequestDocumentType" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}response" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enterpriseResponseDocumentType", propOrder = {
    "guid",
    "status",
    "request",
    "response"
})
@XmlRootElement(name = "serviceabilityEnterpriseResponse")
public class ServiceabilityEnterpriseResponse {

    @XmlElement(name = "GUID", required = true)
    protected String guid;
    @XmlElement(required = true)
    protected StatusType status;
    protected ServiceabilityEnterpriseRequest request;
    protected AbstractResponseType response;

    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGUID() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGUID(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceabilityEnterpriseRequest }
     *     
     */
    public ServiceabilityEnterpriseRequest getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceabilityEnterpriseRequest }
     *     
     */
    public void setRequest(ServiceabilityEnterpriseRequest value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link AbstractResponseType }
     *     
     */
    public AbstractResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbstractResponseType }
     *     
     */
    public void setResponse(AbstractResponseType value) {
        this.response = value;
    }

}
