
package com.A.xml.me.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{http://xml.A.com/v4}transactionType"/>
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enterpriseRequestDocumentType", propOrder = {
    "guid",
    "transactionType",
    "salesContext",
    "request"
})
public class EnterpriseRequestDocumentType {

    @XmlElement(name = "GUID", required = true)
    protected String guid;
    @XmlElement(required = true)
    protected AbstractTransactionTypeType transactionType;
    protected SalesContextType salesContext;
    @XmlElement(required = true)
    protected AbstractRequestType request;
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
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link AbstractTransactionTypeType }
     *     
     */
    public AbstractTransactionTypeType getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbstractTransactionTypeType }
     *     
     */
    public void setTransactionType(AbstractTransactionTypeType value) {
        this.transactionType = value;
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
    public AbstractRequestType getRequest() {
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
    public void setRequest(AbstractRequestType value) {
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
