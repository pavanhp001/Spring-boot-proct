
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="salesContext" type="{http://xml.A.com/v4}salesContextType" minOccurs="0"/>
 *         &lt;element name="transactionType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="processOrderBroadCast"/>
 *               &lt;enumeration value="sendACFeedBackNotification"/>
 *               &lt;enumeration value="resendOrderSummaryEmail"/>
 *               &lt;enumeration value="getOrderCommunicationEvents"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="status" type="{http://xml.A.com/v4}statusType" minOccurs="0"/>
 *         &lt;element name="request">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="orderManagementRequestResponse" type="{http://xml.A.com/v4}orderType" minOccurs="0"/>
 *                   &lt;element name="wdFeedBackNotification" type="{http://xml.A.com/v4}ccpEventNotificationType" minOccurs="0"/>
 *                   &lt;element name="emailContentId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="response" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="communicationEvents" type="{http://xml.A.com/v4}ccpCommunicationEventsType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "guid",
    "salesContext",
    "transactionType",
    "status",
    "request",
    "response"
})
@XmlRootElement(name = "ccpRequestResponse")
public class CcpRequestResponse {

    @XmlElement(name = "GUID", required = true)
    protected String guid;
    protected SalesContextType salesContext;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String transactionType;
    protected StatusType status;
    @XmlElement(required = true)
    protected CcpRequestResponse.Request request;
    protected CcpRequestResponse.Response response;

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
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionType(String value) {
        this.transactionType = value;
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
     *     {@link CcpRequestResponse.Request }
     *     
     */
    public CcpRequestResponse.Request getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link CcpRequestResponse.Request }
     *     
     */
    public void setRequest(CcpRequestResponse.Request value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link CcpRequestResponse.Response }
     *     
     */
    public CcpRequestResponse.Response getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link CcpRequestResponse.Response }
     *     
     */
    public void setResponse(CcpRequestResponse.Response value) {
        this.response = value;
    }


    /**
     * 
     * 	                         	Contains the information for requesting a calculation of price of
     * 	                         	of a set of order types.
     * 	                     	
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="orderManagementRequestResponse" type="{http://xml.A.com/v4}orderType" minOccurs="0"/>
     *         &lt;element name="wdFeedBackNotification" type="{http://xml.A.com/v4}ccpEventNotificationType" minOccurs="0"/>
     *         &lt;element name="emailContentId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "orderManagementRequestResponse",
        "wdFeedBackNotification",
        "emailContentId"
    })
    public static class Request {

        protected OrderType orderManagementRequestResponse;
        protected CcpEventNotificationType wdFeedBackNotification;
        protected Long emailContentId;

        /**
         * Gets the value of the orderManagementRequestResponse property.
         * 
         * @return
         *     possible object is
         *     {@link OrderType }
         *     
         */
        public OrderType getOrderManagementRequestResponse() {
            return orderManagementRequestResponse;
        }

        /**
         * Sets the value of the orderManagementRequestResponse property.
         * 
         * @param value
         *     allowed object is
         *     {@link OrderType }
         *     
         */
        public void setOrderManagementRequestResponse(OrderType value) {
            this.orderManagementRequestResponse = value;
        }

        /**
         * Gets the value of the wdFeedBackNotification property.
         * 
         * @return
         *     possible object is
         *     {@link CcpEventNotificationType }
         *     
         */
        public CcpEventNotificationType getWdFeedBackNotification() {
            return wdFeedBackNotification;
        }

        /**
         * Sets the value of the wdFeedBackNotification property.
         * 
         * @param value
         *     allowed object is
         *     {@link CcpEventNotificationType }
         *     
         */
        public void setWdFeedBackNotification(CcpEventNotificationType value) {
            this.wdFeedBackNotification = value;
        }

        /**
         * Gets the value of the emailContentId property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getEmailContentId() {
            return emailContentId;
        }

        /**
         * Sets the value of the emailContentId property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setEmailContentId(Long value) {
            this.emailContentId = value;
        }

    }


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
     *         &lt;element name="communicationEvents" type="{http://xml.A.com/v4}ccpCommunicationEventsType"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "communicationEvents"
    })
    public static class Response {

        @XmlElement(required = true)
        protected CcpCommunicationEventsType communicationEvents;

        /**
         * Gets the value of the communicationEvents property.
         * 
         * @return
         *     possible object is
         *     {@link CcpCommunicationEventsType }
         *     
         */
        public CcpCommunicationEventsType getCommunicationEvents() {
            return communicationEvents;
        }

        /**
         * Sets the value of the communicationEvents property.
         * 
         * @param value
         *     allowed object is
         *     {@link CcpCommunicationEventsType }
         *     
         */
        public void setCommunicationEvents(CcpCommunicationEventsType value) {
            this.communicationEvents = value;
        }

    }

}
