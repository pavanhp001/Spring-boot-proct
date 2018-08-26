
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestContext complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestContext">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="correlationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="salesCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="affiliateName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="channell" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transactionType" type="{http://xml.A.com/common}TransactionType"/>
 *         &lt;element name="clientInfo" type="{http://xml.A.com/common}ClientInfo"/>
 *         &lt;element name="newCustomer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="moveDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestContext", namespace = "http://xml.A.com/common", propOrder = {
    "correlationId",
    "providerId",
    "sessionId",
    "salesCode",
    "affiliateName",
    "orderNumber",
    "channell",
    "region",
    "requestDate",
    "transactionType",
    "clientInfo",
    "newCustomer",
    "moveDate"
})
@XmlRootElement(name = "requestContext", namespace = "http://xml.A.com/common")
public class RequestContext {

    @XmlElement(required = true)
    protected String correlationId;
    @XmlElement(required = true)
    protected String providerId;
    @XmlElement(required = true)
    protected String sessionId;
    @XmlElement(required = true)
    protected String salesCode;
    @XmlElement(required = true)
    protected String affiliateName;
    @XmlElement(required = true)
    protected String orderNumber;
    @XmlElement(required = true)
    protected String channell;
    @XmlElement(required = true)
    protected String region;
    @XmlElement(required = true)
    protected String requestDate;
    @XmlElement(required = true)
    protected TransactionType2 transactionType;
    @XmlElement(required = true)
    protected ClientInfo clientInfo;
    @XmlElement(required = true)
    protected String newCustomer;
    @XmlElement(required = true)
    protected String moveDate;

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
     * Gets the value of the providerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * Sets the value of the providerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderId(String value) {
        this.providerId = value;
    }

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the salesCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesCode() {
        return salesCode;
    }

    /**
     * Sets the value of the salesCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesCode(String value) {
        this.salesCode = value;
    }

    /**
     * Gets the value of the affiliateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffiliateName() {
        return affiliateName;
    }

    /**
     * Sets the value of the affiliateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffiliateName(String value) {
        this.affiliateName = value;
    }

    /**
     * Gets the value of the orderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the value of the orderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderNumber(String value) {
        this.orderNumber = value;
    }

    /**
     * Gets the value of the channell property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannell() {
        return channell;
    }

    /**
     * Sets the value of the channell property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannell(String value) {
        this.channell = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * Gets the value of the requestDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * Sets the value of the requestDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestDate(String value) {
        this.requestDate = value;
    }

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionType2 }
     *     
     */
    public TransactionType2 getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionType2 }
     *     
     */
    public void setTransactionType(TransactionType2 value) {
        this.transactionType = value;
    }

    /**
     * Gets the value of the clientInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ClientInfo }
     *     
     */
    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    /**
     * Sets the value of the clientInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientInfo }
     *     
     */
    public void setClientInfo(ClientInfo value) {
        this.clientInfo = value;
    }

    /**
     * Gets the value of the newCustomer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewCustomer() {
        return newCustomer;
    }

    /**
     * Sets the value of the newCustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewCustomer(String value) {
        this.newCustomer = value;
    }

    /**
     * Gets the value of the moveDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoveDate() {
        return moveDate;
    }

    /**
     * Sets the value of the moveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoveDate(String value) {
        this.moveDate = value;
    }

}
