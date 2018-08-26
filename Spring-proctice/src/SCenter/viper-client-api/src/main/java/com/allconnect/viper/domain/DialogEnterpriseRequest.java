package com.A.V.domain;
 

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.A.xml.di.v4.AbstractRequestType;
import com.A.xml.di.v4.SalesContextType;


/**
 * <p>Java class for enterpriseRequestDocumentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="enterpriseRequestDocumentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="salesContext" type="{http://xml.A.com/v4}salesContextType" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}request"/>
 *       &lt;/sequence>
 *       &lt;attribute name="debug" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dialogEnterpriseRequest", propOrder = {
    "guid",
    "salesContext",
    "request"
})
public class DialogEnterpriseRequest {

    @XmlElement(name = "GUID", required = true)
    protected String guid;
    protected SalesContextType salesContext;
    @XmlElement(required = true)
    protected String request="test";
    @XmlAttribute(name = "debug")
    protected Boolean debug;

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
     * Gets the value of the salesContext property.
     * 
     * @return
     *     possible object is
     *     {@link SalesContextType }
     *     
     */
    public SalesContextType getSalesContext() {
        return salesContext;
    }

    /**
     * Sets the value of the salesContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesContextType }
     *     
     */
    public void setSalesContext(SalesContextType value) {
        this.salesContext = value;
    }

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link AbstractRequestType }
     *     
     */
    public String getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbstractRequestType }
     *     
     */
    public void setRequest(String value) {
        this.request = value;
    }

    /**
     * Gets the value of the debug property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDebug() {
        return debug;
    }

    /**
     * Sets the value of the debug property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDebug(Boolean value) {
        this.debug = value;
    }

}
