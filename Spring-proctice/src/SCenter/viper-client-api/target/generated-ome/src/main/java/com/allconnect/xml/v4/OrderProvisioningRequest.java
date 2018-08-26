
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="providerExternalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transactionType" type="{http://xml.A.com/common}TransactionType"/>
 *         &lt;element name="correlationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="agentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="request" type="{http://xml.A.com/v4/OrderProvisioning/}ProvisioningRequest"/>
 *         &lt;element name="opContext" type="{http://xml.A.com/v4}salesContextType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="persistCustomerInfo" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="persistProductInfo" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="persistPromoInfo" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "providerExternalId",
    "transactionType",
    "correlationId",
    "transactionId",
    "agentId",
    "orderId",
    "request",
    "opContext"
})
@XmlRootElement(name = "orderProvisioningRequest", namespace = "http://xml.A.com/v4/OrderProvisioning/")
public class OrderProvisioningRequest {

    @XmlElement(namespace = "", required = true)
    protected String providerExternalId;
    @XmlElement(namespace = "", required = true)
    protected TransactionType transactionType;
    @XmlElement(namespace = "", required = true)
    protected String correlationId;
    @XmlElement(namespace = "")
    protected int transactionId;
    @XmlElement(namespace = "", required = true)
    protected String agentId;
    @XmlElement(namespace = "", required = true)
    protected String orderId;
    @XmlElement(namespace = "", required = true)
    protected ProvisioningRequest request;
    @XmlElement(namespace = "")
    protected SalesContextType opContext;
    @XmlAttribute(name = "persistCustomerInfo")
    protected Boolean persistCustomerInfo;
    @XmlAttribute(name = "persistProductInfo")
    protected Boolean persistProductInfo;
    @XmlAttribute(name = "persistPromoInfo")
    protected Boolean persistPromoInfo;

    /**
     * Gets the value of the providerExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderExternalId() {
        return providerExternalId;
    }

    /**
     * Sets the value of the providerExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderExternalId(String value) {
        this.providerExternalId = value;
    }

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionType }
     *     
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionType }
     *     
     */
    public void setTransactionType(TransactionType value) {
        this.transactionType = value;
    }

    /**
     * Gets the value of the correlationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Sets the value of the correlationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrelationId(String value) {
        this.correlationId = value;
    }

    /**
     * Gets the value of the transactionId property.
     * 
     */
    public int getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     * 
     */
    public void setTransactionId(int value) {
        this.transactionId = value;
    }

    /**
     * Gets the value of the agentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Sets the value of the agentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentId(String value) {
        this.agentId = value;
    }

    /**
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderId(String value) {
        this.orderId = value;
    }

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link ProvisioningRequest }
     *     
     */
    public ProvisioningRequest getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvisioningRequest }
     *     
     */
    public void setRequest(ProvisioningRequest value) {
        this.request = value;
    }

    /**
     * Gets the value of the opContext property.
     * 
     * @return
     *     possible object is
     *     {@link SalesContextType }
     *     
     */
    public SalesContextType getOpContext() {
        return opContext;
    }

    /**
     * Sets the value of the opContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesContextType }
     *     
     */
    public void setOpContext(SalesContextType value) {
        this.opContext = value;
    }

    /**
     * Gets the value of the persistCustomerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isPersistCustomerInfo() {
        if (persistCustomerInfo == null) {
            return true;
        } else {
            return persistCustomerInfo;
        }
    }

    /**
     * Sets the value of the persistCustomerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPersistCustomerInfo(Boolean value) {
        this.persistCustomerInfo = value;
    }

    /**
     * Gets the value of the persistProductInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isPersistProductInfo() {
        if (persistProductInfo == null) {
            return true;
        } else {
            return persistProductInfo;
        }
    }

    /**
     * Sets the value of the persistProductInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPersistProductInfo(Boolean value) {
        this.persistProductInfo = value;
    }

    /**
     * Gets the value of the persistPromoInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isPersistPromoInfo() {
        if (persistPromoInfo == null) {
            return true;
        } else {
            return persistPromoInfo;
        }
    }

    /**
     * Sets the value of the persistPromoInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPersistPromoInfo(Boolean value) {
        this.persistPromoInfo = value;
    }

}
