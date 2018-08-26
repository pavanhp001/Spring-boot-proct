
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for orderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lineItems" type="{http://xml.A.com/v4}lineItemCollectionType"/>
 *         &lt;element ref="{http://xml.A.com/v4}orderStatus"/>
 *         &lt;element name="orderStatusHistory" type="{http://xml.A.com/v4}orderStatusHistoryType"/>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="referrerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="associatedWithMove" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="moveDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="whenToCallBack" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="campaignId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}customerInformation"/>
 *         &lt;element name="agentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="agentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AConfirmationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ACustomerAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalPriceInfo" type="{http://xml.A.com/v4}priceInfoType"/>
 *         &lt;element name="guid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isZipOnlySearch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="accountHolder" type="{http://xml.A.com/v4}accountHolderType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="programExternalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ordersourceExternalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inboundVdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderType", propOrder = {
    "lineItems",
    "orderStatus",
    "orderStatusHistory",
    "externalId",
    "source",
    "orderDate",
    "referrerId",
    "associatedWithMove",
    "moveDate",
    "whenToCallBack",
    "campaignId",
    "customerInformation",
    "agentId",
    "agentName",
    "AConfirmationNumber",
    "ACustomerAccountNumber",
    "totalPriceInfo",
    "guid",
    "isZipOnlySearch",
    "accountHolder",
    "programExternalId",
    "ordersourceExternalId",
    "inboundVdn"
})
@XmlSeeAlso({
    Order.class
})
public class OrderType {

    @XmlElement(required = true)
    protected LineItemCollectionType lineItems;
    @XmlElement(required = true)
    protected OrderStatusWithTypeType orderStatus;
    @XmlElement(required = true)
    protected OrderStatusHistoryType orderStatusHistory;
    protected long externalId;
    @XmlElement(required = true)
    protected String source;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orderDate;
    @XmlElement(required = true)
    protected String referrerId;
    protected boolean associatedWithMove;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar moveDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar whenToCallBack;
    protected String campaignId;
    @XmlElement(required = true)
    protected CustomerInformation customerInformation;
    @XmlElement(required = true)
    protected String agentId;
    protected String agentName;
    protected String AConfirmationNumber;
    protected String ACustomerAccountNumber;
    @XmlElement(required = true)
    protected PriceInfoType totalPriceInfo;
    protected String guid;
    protected int isZipOnlySearch;
    protected List<AccountHolderType> accountHolder;
    protected String programExternalId;
    protected String ordersourceExternalId;
    protected String inboundVdn;

    /**
     * Gets the value of the lineItems property.
     * 
     * @return
     *     possible object is
     *     {@link LineItemCollectionType }
     *     
     */
    public LineItemCollectionType getLineItems() {
        return lineItems;
    }

    /**
     * Sets the value of the lineItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineItemCollectionType }
     *     
     */
    public void setLineItems(LineItemCollectionType value) {
        this.lineItems = value;
    }

    /**
     * Gets the value of the orderStatus property.
     * 
     * @return
     *     possible object is
     *     {@link OrderStatusWithTypeType }
     *     
     */
    public OrderStatusWithTypeType getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the value of the orderStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderStatusWithTypeType }
     *     
     */
    public void setOrderStatus(OrderStatusWithTypeType value) {
        this.orderStatus = value;
    }

    /**
     * Gets the value of the orderStatusHistory property.
     * 
     * @return
     *     possible object is
     *     {@link OrderStatusHistoryType }
     *     
     */
    public OrderStatusHistoryType getOrderStatusHistory() {
        return orderStatusHistory;
    }

    /**
     * Sets the value of the orderStatusHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderStatusHistoryType }
     *     
     */
    public void setOrderStatusHistory(OrderStatusHistoryType value) {
        this.orderStatusHistory = value;
    }

    /**
     * Gets the value of the externalId property.
     * 
     */
    public long getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     */
    public void setExternalId(long value) {
        this.externalId = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the orderDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the value of the orderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrderDate(XMLGregorianCalendar value) {
        this.orderDate = value;
    }

    /**
     * Gets the value of the referrerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferrerId() {
        return referrerId;
    }

    /**
     * Sets the value of the referrerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferrerId(String value) {
        this.referrerId = value;
    }

    /**
     * Gets the value of the associatedWithMove property.
     * 
     */
    public boolean isAssociatedWithMove() {
        return associatedWithMove;
    }

    /**
     * Sets the value of the associatedWithMove property.
     * 
     */
    public void setAssociatedWithMove(boolean value) {
        this.associatedWithMove = value;
    }

    /**
     * Gets the value of the moveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMoveDate() {
        return moveDate;
    }

    /**
     * Sets the value of the moveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMoveDate(XMLGregorianCalendar value) {
        this.moveDate = value;
    }

    /**
     * Gets the value of the whenToCallBack property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getWhenToCallBack() {
        return whenToCallBack;
    }

    /**
     * Sets the value of the whenToCallBack property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setWhenToCallBack(XMLGregorianCalendar value) {
        this.whenToCallBack = value;
    }

    /**
     * Gets the value of the campaignId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCampaignId() {
        return campaignId;
    }

    /**
     * Sets the value of the campaignId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCampaignId(String value) {
        this.campaignId = value;
    }

    /**
     * Gets the value of the customerInformation property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerInformation }
     *     
     */
    public CustomerInformation getCustomerInformation() {
        return customerInformation;
    }

    /**
     * Sets the value of the customerInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerInformation }
     *     
     */
    public void setCustomerInformation(CustomerInformation value) {
        this.customerInformation = value;
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
     * Gets the value of the agentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * Sets the value of the agentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentName(String value) {
        this.agentName = value;
    }

    /**
     * Gets the value of the AConfirmationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAConfirmationNumber() {
        return AConfirmationNumber;
    }

    /**
     * Sets the value of the AConfirmationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAConfirmationNumber(String value) {
        this.AConfirmationNumber = value;
    }

    /**
     * Gets the value of the ACustomerAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACustomerAccountNumber() {
        return ACustomerAccountNumber;
    }

    /**
     * Sets the value of the ACustomerAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACustomerAccountNumber(String value) {
        this.ACustomerAccountNumber = value;
    }

    /**
     * Gets the value of the totalPriceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PriceInfoType }
     *     
     */
    public PriceInfoType getTotalPriceInfo() {
        return totalPriceInfo;
    }

    /**
     * Sets the value of the totalPriceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceInfoType }
     *     
     */
    public void setTotalPriceInfo(PriceInfoType value) {
        this.totalPriceInfo = value;
    }

    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuid() {
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
    public void setGuid(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the isZipOnlySearch property.
     * 
     */
    public int getIsZipOnlySearch() {
        return isZipOnlySearch;
    }

    /**
     * Sets the value of the isZipOnlySearch property.
     * 
     */
    public void setIsZipOnlySearch(int value) {
        this.isZipOnlySearch = value;
    }

    /**
     * Gets the value of the accountHolder property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accountHolder property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccountHolder().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccountHolderType }
     * 
     * 
     */
    public List<AccountHolderType> getAccountHolder() {
        if (accountHolder == null) {
            accountHolder = new ArrayList<AccountHolderType>();
        }
        return this.accountHolder;
    }

    /**
     * Gets the value of the programExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgramExternalId() {
        return programExternalId;
    }

    /**
     * Sets the value of the programExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgramExternalId(String value) {
        this.programExternalId = value;
    }

    /**
     * Gets the value of the ordersourceExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdersourceExternalId() {
        return ordersourceExternalId;
    }

    /**
     * Sets the value of the ordersourceExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdersourceExternalId(String value) {
        this.ordersourceExternalId = value;
    }

    /**
     * Gets the value of the inboundVdn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInboundVdn() {
        return inboundVdn;
    }

    /**
     * Sets the value of the inboundVdn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInboundVdn(String value) {
        this.inboundVdn = value;
    }

}
