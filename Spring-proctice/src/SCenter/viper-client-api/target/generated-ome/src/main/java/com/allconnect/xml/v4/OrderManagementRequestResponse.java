
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="correlationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transactionTimeStamp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="affiliateName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="salesCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transactionType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="addLineItem"/>
 *               &lt;enumeration value="updateLineItemStatus"/>
 *               &lt;enumeration value="updateLineItem"/>
 *               &lt;enumeration value="updateLineItemLW"/>
 *               &lt;enumeration value="createOrder"/>
 *               &lt;enumeration value="getOrderByDate"/>
 *               &lt;enumeration value="getOrderByScheduleDate"/>
 *               &lt;enumeration value="delete"/>
 *               &lt;enumeration value="updateOrderStatus"/>
 *               &lt;enumeration value="updateOrder"/>
 *               &lt;enumeration value="getOrder"/>
 *               &lt;enumeration value="getOrderByStatus"/>
 *               &lt;enumeration value="getOrderByCustomer"/>
 *               &lt;enumeration value="getOrderByConfirmationNumber"/>
 *               &lt;enumeration value="submit"/>
 *               &lt;enumeration value="eventNotification"/>
 *               &lt;enumeration value="getOrderByLineItem"/>
 *               &lt;enumeration value="getOrderByProvider"/>
 *               &lt;enumeration value="orderSearch"/>
 *               &lt;enumeration value="taskJob"/>
 *               &lt;enumeration value="getAllAccountHolders"/>
 *               &lt;enumeration value="getMetrics"/>
 *               &lt;enumeration value="rules"/>
 *               &lt;enumeration value="orderQualification"/>
 *               &lt;enumeration value="orderSubmission"/>
 *               &lt;enumeration value="orderStatus"/>
 *               &lt;enumeration value="getProductCatalog"/>
 *               &lt;enumeration value="serviceQualification"/>
 *               &lt;enumeration value="creditQualification"/>
 *               &lt;enumeration value="updateOrder"/>
 *               &lt;enumeration value="orderDateQualification"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="status" type="{http://xml.A.com/v4}statusType" minOccurs="0"/>
 *         &lt;element name="pagingDetail" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="offSet" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="totalRows" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="request">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="lineitemId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="rulesAction" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                         &lt;enumeration value="clearMemoryCache"/>
 *                         &lt;enumeration value="clearStorageCache"/>
 *                         &lt;enumeration value="validate"/>
 *                         &lt;enumeration value="find"/>
 *                         &lt;enumeration value="findById"/>
 *                         &lt;enumeration value="findByName"/>
 *                         &lt;enumeration value="getAll"/>
 *                         &lt;enumeration value="update"/>
 *                         &lt;enumeration value="save"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="jobAction" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                         &lt;enumeration value="update"/>
 *                         &lt;enumeration value="save"/>
 *                         &lt;enumeration value="deleteAll"/>
 *                         &lt;enumeration value="deleteJob"/>
 *                         &lt;enumeration value="lock"/>
 *                         &lt;enumeration value="isLocked"/>
 *                         &lt;enumeration value="findNextAndOffer"/>
 *                         &lt;enumeration value="findNextAndProcess"/>
 *                         &lt;enumeration value="findByExternalId"/>
 *                         &lt;enumeration value="findByContextStatus"/>
 *                         &lt;enumeration value="findByOrderLineItem"/>
 *                         &lt;enumeration value="cancel"/>
 *                         &lt;enumeration value="complete"/>
 *                         &lt;enumeration value="activate"/>
 *                         &lt;enumeration value="schedule"/>
 *                         &lt;enumeration value="create"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="jobs" type="{http://xml.A.com/v4}jobCollectionType" minOccurs="0"/>
 *                   &lt;element name="salesContext" type="{http://xml.A.com/v4}salesContextType" minOccurs="0"/>
 *                   &lt;element name="notificationEvents" type="{http://xml.A.com/v4}notificationEventCollectionType" minOccurs="0"/>
 *                   &lt;element name="dateFilter" type="{http://xml.A.com/v4}dateTimeOrTimeRangeType" minOccurs="0"/>
 *                   &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="agentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="customerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="confirmationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="criteria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="channelType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;choice minOccurs="0">
 *                     &lt;sequence>
 *                       &lt;element name="afterLineItemNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                       &lt;element name="isAppliesToLineItemIncluded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                       &lt;element name="newLineItems" type="{http://xml.A.com/v4}lineItemCollectionType"/>
 *                     &lt;/sequence>
 *                     &lt;sequence>
 *                       &lt;element name="scheduleDate" type="{http://xml.A.com/v4}dateTimeOrTimeRangeType" minOccurs="0"/>
 *                       &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                       &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                       &lt;element name="scheduledStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                       &lt;element name="orderDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                       &lt;element name="lineItemCreateDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                     &lt;/sequence>
 *                     &lt;sequence>
 *                       &lt;element name="orderInfo" type="{http://xml.A.com/v4}orderType"/>
 *                     &lt;/sequence>
 *                     &lt;sequence>
 *                       &lt;element name="newOrderStatus" type="{http://xml.A.com/v4}orderStatusWithTypeType"/>
 *                     &lt;/sequence>
 *                     &lt;sequence>
 *                       &lt;element name="lineItemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                       &lt;element name="newLineItemStatus" type="{http://xml.A.com/v4}lineItemStatusType"/>
 *                     &lt;/sequence>
 *                     &lt;sequence>
 *                       &lt;element name="updateOrderInfo" type="{http://xml.A.com/v4}orderType"/>
 *                     &lt;/sequence>
 *                     &lt;sequence>
 *                       &lt;choice>
 *                         &lt;element name="updatedLineItemInfo" type="{http://xml.A.com/v4}lineItemCollectionType"/>
 *                       &lt;/choice>
 *                     &lt;/sequence>
 *                   &lt;/choice>
 *                   &lt;element name="accountHolder" type="{http://xml.A.com/v4}accountHolderType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="includeAccountHolders" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
 *                   &lt;element name="salesContext" type="{http://xml.A.com/v4}salesContextType"/>
 *                   &lt;element name="notificationEvents" type="{http://xml.A.com/v4}notificationEventCollectionType" minOccurs="0"/>
 *                   &lt;element name="jobs" type="{http://xml.A.com/v4}jobCollectionType" minOccurs="0"/>
 *                   &lt;choice>
 *                     &lt;element name="orderInfo" type="{http://xml.A.com/v4}orderType" maxOccurs="unbounded"/>
 *                     &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                     &lt;element name="orderSearchResult" type="{http://xml.A.com/v4}OrderSearch"/>
 *                   &lt;/choice>
 *                   &lt;element name="accountHolder" type="{http://xml.A.com/v4}accountHolderType" maxOccurs="unbounded" minOccurs="0"/>
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
    "correlationId",
    "transactionTimeStamp",
    "sessionId",
    "transactionId",
    "region",
    "affiliateName",
    "salesCode",
    "transactionType",
    "status",
    "pagingDetail",
    "request",
    "response"
})
@XmlRootElement(name = "orderManagementRequestResponse")
public class OrderManagementRequestResponse {

    protected String correlationId;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar transactionTimeStamp;
    protected String sessionId;
    protected Integer transactionId;
    protected String region;
    protected String affiliateName;
    protected String salesCode;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String transactionType;
    protected StatusType status;
    protected OrderManagementRequestResponse.PagingDetail pagingDetail;
    @XmlElement(required = true)
    protected OrderManagementRequestResponse.Request request;
    protected OrderManagementRequestResponse.Response response;

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
     * Gets the value of the transactionTimeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTransactionTimeStamp() {
        return transactionTimeStamp;
    }

    /**
     * Sets the value of the transactionTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTransactionTimeStamp(XMLGregorianCalendar value) {
        this.transactionTimeStamp = value;
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
     * Gets the value of the transactionId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTransactionId(Integer value) {
        this.transactionId = value;
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
     * Gets the value of the pagingDetail property.
     * 
     * @return
     *     possible object is
     *     {@link OrderManagementRequestResponse.PagingDetail }
     *     
     */
    public OrderManagementRequestResponse.PagingDetail getPagingDetail() {
        return pagingDetail;
    }

    /**
     * Sets the value of the pagingDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderManagementRequestResponse.PagingDetail }
     *     
     */
    public void setPagingDetail(OrderManagementRequestResponse.PagingDetail value) {
        this.pagingDetail = value;
    }

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link OrderManagementRequestResponse.Request }
     *     
     */
    public OrderManagementRequestResponse.Request getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderManagementRequestResponse.Request }
     *     
     */
    public void setRequest(OrderManagementRequestResponse.Request value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link OrderManagementRequestResponse.Response }
     *     
     */
    public OrderManagementRequestResponse.Response getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderManagementRequestResponse.Response }
     *     
     */
    public void setResponse(OrderManagementRequestResponse.Response value) {
        this.response = value;
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
     *         &lt;element name="offSet" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="totalRows" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
        "offSet",
        "totalRows"
    })
    public static class PagingDetail {

        protected int offSet;
        protected int totalRows;

        /**
         * Gets the value of the offSet property.
         * 
         */
        public int getOffSet() {
            return offSet;
        }

        /**
         * Sets the value of the offSet property.
         * 
         */
        public void setOffSet(int value) {
            this.offSet = value;
        }

        /**
         * Gets the value of the totalRows property.
         * 
         */
        public int getTotalRows() {
            return totalRows;
        }

        /**
         * Sets the value of the totalRows property.
         * 
         */
        public void setTotalRows(int value) {
            this.totalRows = value;
        }

    }


    /**
     * 
     * 								The order id by itself is sufficient for the
     * 								delete and getOrder requests.
     * 								It is required, along with some
     * 								additional data (listed below) for addLineItem,
     * 								updateOrderStatus
     * 								and updateLineItemStatus.
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
     *         &lt;element name="lineitemId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="rulesAction" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *               &lt;enumeration value="clearMemoryCache"/>
     *               &lt;enumeration value="clearStorageCache"/>
     *               &lt;enumeration value="validate"/>
     *               &lt;enumeration value="find"/>
     *               &lt;enumeration value="findById"/>
     *               &lt;enumeration value="findByName"/>
     *               &lt;enumeration value="getAll"/>
     *               &lt;enumeration value="update"/>
     *               &lt;enumeration value="save"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="jobAction" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *               &lt;enumeration value="update"/>
     *               &lt;enumeration value="save"/>
     *               &lt;enumeration value="deleteAll"/>
     *               &lt;enumeration value="deleteJob"/>
     *               &lt;enumeration value="lock"/>
     *               &lt;enumeration value="isLocked"/>
     *               &lt;enumeration value="findNextAndOffer"/>
     *               &lt;enumeration value="findNextAndProcess"/>
     *               &lt;enumeration value="findByExternalId"/>
     *               &lt;enumeration value="findByContextStatus"/>
     *               &lt;enumeration value="findByOrderLineItem"/>
     *               &lt;enumeration value="cancel"/>
     *               &lt;enumeration value="complete"/>
     *               &lt;enumeration value="activate"/>
     *               &lt;enumeration value="schedule"/>
     *               &lt;enumeration value="create"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="jobs" type="{http://xml.A.com/v4}jobCollectionType" minOccurs="0"/>
     *         &lt;element name="salesContext" type="{http://xml.A.com/v4}salesContextType" minOccurs="0"/>
     *         &lt;element name="notificationEvents" type="{http://xml.A.com/v4}notificationEventCollectionType" minOccurs="0"/>
     *         &lt;element name="dateFilter" type="{http://xml.A.com/v4}dateTimeOrTimeRangeType" minOccurs="0"/>
     *         &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="agentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="customerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="confirmationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="criteria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="channelType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;choice minOccurs="0">
     *           &lt;sequence>
     *             &lt;element name="afterLineItemNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *             &lt;element name="isAppliesToLineItemIncluded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *             &lt;element name="newLineItems" type="{http://xml.A.com/v4}lineItemCollectionType"/>
     *           &lt;/sequence>
     *           &lt;sequence>
     *             &lt;element name="scheduleDate" type="{http://xml.A.com/v4}dateTimeOrTimeRangeType" minOccurs="0"/>
     *             &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *             &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *             &lt;element name="scheduledStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *             &lt;element name="orderDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *             &lt;element name="lineItemCreateDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *           &lt;/sequence>
     *           &lt;sequence>
     *             &lt;element name="orderInfo" type="{http://xml.A.com/v4}orderType"/>
     *           &lt;/sequence>
     *           &lt;sequence>
     *             &lt;element name="newOrderStatus" type="{http://xml.A.com/v4}orderStatusWithTypeType"/>
     *           &lt;/sequence>
     *           &lt;sequence>
     *             &lt;element name="lineItemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *             &lt;element name="newLineItemStatus" type="{http://xml.A.com/v4}lineItemStatusType"/>
     *           &lt;/sequence>
     *           &lt;sequence>
     *             &lt;element name="updateOrderInfo" type="{http://xml.A.com/v4}orderType"/>
     *           &lt;/sequence>
     *           &lt;sequence>
     *             &lt;choice>
     *               &lt;element name="updatedLineItemInfo" type="{http://xml.A.com/v4}lineItemCollectionType"/>
     *             &lt;/choice>
     *           &lt;/sequence>
     *         &lt;/choice>
     *         &lt;element name="accountHolder" type="{http://xml.A.com/v4}accountHolderType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="includeAccountHolders" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "lineitemId",
        "rulesAction",
        "jobAction",
        "jobs",
        "salesContext",
        "notificationEvents",
        "dateFilter",
        "orderId",
        "providerId",
        "agentId",
        "customerId",
        "confirmationNumber",
        "criteria",
        "channelType",
        "afterLineItemNumber",
        "isAppliesToLineItemIncluded",
        "newLineItems",
        "scheduleDate",
        "status",
        "reason",
        "scheduledStartDate",
        "orderDate",
        "lineItemCreateDate",
        "orderInfo",
        "newOrderStatus",
        "lineItemId",
        "newLineItemStatus",
        "updateOrderInfo",
        "updatedLineItemInfo",
        "accountHolder",
        "includeAccountHolders"
    })
    public static class Request {

        protected List<String> lineitemId;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String rulesAction;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String jobAction;
        protected JobCollectionType jobs;
        protected SalesContextType salesContext;
        protected NotificationEventCollectionType notificationEvents;
        protected DateTimeOrTimeRangeType dateFilter;
        @XmlElement(required = true)
        protected String orderId;
        @XmlElement(required = true)
        protected String providerId;
        @XmlElement(required = true)
        protected String agentId;
        protected String customerId;
        protected String confirmationNumber;
        protected String criteria;
        protected Integer channelType;
        protected Integer afterLineItemNumber;
        protected Boolean isAppliesToLineItemIncluded;
        protected LineItemCollectionType newLineItems;
        protected DateTimeOrTimeRangeType scheduleDate;
        protected List<String> status;
        protected List<String> reason;
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar scheduledStartDate;
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar orderDate;
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar lineItemCreateDate;
        protected OrderType orderInfo;
        protected OrderStatusWithTypeType newOrderStatus;
        protected String lineItemId;
        protected LineItemStatusType newLineItemStatus;
        protected OrderType updateOrderInfo;
        protected LineItemCollectionType updatedLineItemInfo;
        protected List<AccountHolderType> accountHolder;
        protected Boolean includeAccountHolders;

        /**
         * Gets the value of the lineitemId property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the lineitemId property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLineitemId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getLineitemId() {
            if (lineitemId == null) {
                lineitemId = new ArrayList<String>();
            }
            return this.lineitemId;
        }

        /**
         * Gets the value of the rulesAction property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRulesAction() {
            return rulesAction;
        }

        /**
         * Sets the value of the rulesAction property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRulesAction(String value) {
            this.rulesAction = value;
        }

        /**
         * Gets the value of the jobAction property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJobAction() {
            return jobAction;
        }

        /**
         * Sets the value of the jobAction property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJobAction(String value) {
            this.jobAction = value;
        }

        /**
         * Gets the value of the jobs property.
         * 
         * @return
         *     possible object is
         *     {@link JobCollectionType }
         *     
         */
        public JobCollectionType getJobs() {
            return jobs;
        }

        /**
         * Sets the value of the jobs property.
         * 
         * @param value
         *     allowed object is
         *     {@link JobCollectionType }
         *     
         */
        public void setJobs(JobCollectionType value) {
            this.jobs = value;
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
         * Gets the value of the notificationEvents property.
         * 
         * @return
         *     possible object is
         *     {@link NotificationEventCollectionType }
         *     
         */
        public NotificationEventCollectionType getNotificationEvents() {
            return notificationEvents;
        }

        /**
         * Sets the value of the notificationEvents property.
         * 
         * @param value
         *     allowed object is
         *     {@link NotificationEventCollectionType }
         *     
         */
        public void setNotificationEvents(NotificationEventCollectionType value) {
            this.notificationEvents = value;
        }

        /**
         * Gets the value of the dateFilter property.
         * 
         * @return
         *     possible object is
         *     {@link DateTimeOrTimeRangeType }
         *     
         */
        public DateTimeOrTimeRangeType getDateFilter() {
            return dateFilter;
        }

        /**
         * Sets the value of the dateFilter property.
         * 
         * @param value
         *     allowed object is
         *     {@link DateTimeOrTimeRangeType }
         *     
         */
        public void setDateFilter(DateTimeOrTimeRangeType value) {
            this.dateFilter = value;
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
         * Gets the value of the customerId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerId() {
            return customerId;
        }

        /**
         * Sets the value of the customerId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerId(String value) {
            this.customerId = value;
        }

        /**
         * Gets the value of the confirmationNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getConfirmationNumber() {
            return confirmationNumber;
        }

        /**
         * Sets the value of the confirmationNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setConfirmationNumber(String value) {
            this.confirmationNumber = value;
        }

        /**
         * Gets the value of the criteria property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCriteria() {
            return criteria;
        }

        /**
         * Sets the value of the criteria property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCriteria(String value) {
            this.criteria = value;
        }

        /**
         * Gets the value of the channelType property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getChannelType() {
            return channelType;
        }

        /**
         * Sets the value of the channelType property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setChannelType(Integer value) {
            this.channelType = value;
        }

        /**
         * Gets the value of the afterLineItemNumber property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getAfterLineItemNumber() {
            return afterLineItemNumber;
        }

        /**
         * Sets the value of the afterLineItemNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setAfterLineItemNumber(Integer value) {
            this.afterLineItemNumber = value;
        }

        /**
         * Gets the value of the isAppliesToLineItemIncluded property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isIsAppliesToLineItemIncluded() {
            return isAppliesToLineItemIncluded;
        }

        /**
         * Sets the value of the isAppliesToLineItemIncluded property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setIsAppliesToLineItemIncluded(Boolean value) {
            this.isAppliesToLineItemIncluded = value;
        }

        /**
         * Gets the value of the newLineItems property.
         * 
         * @return
         *     possible object is
         *     {@link LineItemCollectionType }
         *     
         */
        public LineItemCollectionType getNewLineItems() {
            return newLineItems;
        }

        /**
         * Sets the value of the newLineItems property.
         * 
         * @param value
         *     allowed object is
         *     {@link LineItemCollectionType }
         *     
         */
        public void setNewLineItems(LineItemCollectionType value) {
            this.newLineItems = value;
        }

        /**
         * Gets the value of the scheduleDate property.
         * 
         * @return
         *     possible object is
         *     {@link DateTimeOrTimeRangeType }
         *     
         */
        public DateTimeOrTimeRangeType getScheduleDate() {
            return scheduleDate;
        }

        /**
         * Sets the value of the scheduleDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link DateTimeOrTimeRangeType }
         *     
         */
        public void setScheduleDate(DateTimeOrTimeRangeType value) {
            this.scheduleDate = value;
        }

        /**
         * Gets the value of the status property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the status property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getStatus().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getStatus() {
            if (status == null) {
                status = new ArrayList<String>();
            }
            return this.status;
        }

        /**
         * Gets the value of the reason property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the reason property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReason().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getReason() {
            if (reason == null) {
                reason = new ArrayList<String>();
            }
            return this.reason;
        }

        /**
         * Gets the value of the scheduledStartDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getScheduledStartDate() {
            return scheduledStartDate;
        }

        /**
         * Sets the value of the scheduledStartDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setScheduledStartDate(XMLGregorianCalendar value) {
            this.scheduledStartDate = value;
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
         * Gets the value of the lineItemCreateDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getLineItemCreateDate() {
            return lineItemCreateDate;
        }

        /**
         * Sets the value of the lineItemCreateDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setLineItemCreateDate(XMLGregorianCalendar value) {
            this.lineItemCreateDate = value;
        }

        /**
         * Gets the value of the orderInfo property.
         * 
         * @return
         *     possible object is
         *     {@link OrderType }
         *     
         */
        public OrderType getOrderInfo() {
            return orderInfo;
        }

        /**
         * Sets the value of the orderInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link OrderType }
         *     
         */
        public void setOrderInfo(OrderType value) {
            this.orderInfo = value;
        }

        /**
         * Gets the value of the newOrderStatus property.
         * 
         * @return
         *     possible object is
         *     {@link OrderStatusWithTypeType }
         *     
         */
        public OrderStatusWithTypeType getNewOrderStatus() {
            return newOrderStatus;
        }

        /**
         * Sets the value of the newOrderStatus property.
         * 
         * @param value
         *     allowed object is
         *     {@link OrderStatusWithTypeType }
         *     
         */
        public void setNewOrderStatus(OrderStatusWithTypeType value) {
            this.newOrderStatus = value;
        }

        /**
         * Gets the value of the lineItemId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLineItemId() {
            return lineItemId;
        }

        /**
         * Sets the value of the lineItemId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLineItemId(String value) {
            this.lineItemId = value;
        }

        /**
         * Gets the value of the newLineItemStatus property.
         * 
         * @return
         *     possible object is
         *     {@link LineItemStatusType }
         *     
         */
        public LineItemStatusType getNewLineItemStatus() {
            return newLineItemStatus;
        }

        /**
         * Sets the value of the newLineItemStatus property.
         * 
         * @param value
         *     allowed object is
         *     {@link LineItemStatusType }
         *     
         */
        public void setNewLineItemStatus(LineItemStatusType value) {
            this.newLineItemStatus = value;
        }

        /**
         * Gets the value of the updateOrderInfo property.
         * 
         * @return
         *     possible object is
         *     {@link OrderType }
         *     
         */
        public OrderType getUpdateOrderInfo() {
            return updateOrderInfo;
        }

        /**
         * Sets the value of the updateOrderInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link OrderType }
         *     
         */
        public void setUpdateOrderInfo(OrderType value) {
            this.updateOrderInfo = value;
        }

        /**
         * Gets the value of the updatedLineItemInfo property.
         * 
         * @return
         *     possible object is
         *     {@link LineItemCollectionType }
         *     
         */
        public LineItemCollectionType getUpdatedLineItemInfo() {
            return updatedLineItemInfo;
        }

        /**
         * Sets the value of the updatedLineItemInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link LineItemCollectionType }
         *     
         */
        public void setUpdatedLineItemInfo(LineItemCollectionType value) {
            this.updatedLineItemInfo = value;
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
         * Gets the value of the includeAccountHolders property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isIncludeAccountHolders() {
            return includeAccountHolders;
        }

        /**
         * Sets the value of the includeAccountHolders property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setIncludeAccountHolders(Boolean value) {
            this.includeAccountHolders = value;
        }

    }


    /**
     * 
     * 								A response will consist of either the updated
     * 								order, or the id of the order.
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
     *         &lt;element name="salesContext" type="{http://xml.A.com/v4}salesContextType"/>
     *         &lt;element name="notificationEvents" type="{http://xml.A.com/v4}notificationEventCollectionType" minOccurs="0"/>
     *         &lt;element name="jobs" type="{http://xml.A.com/v4}jobCollectionType" minOccurs="0"/>
     *         &lt;choice>
     *           &lt;element name="orderInfo" type="{http://xml.A.com/v4}orderType" maxOccurs="unbounded"/>
     *           &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *           &lt;element name="orderSearchResult" type="{http://xml.A.com/v4}OrderSearch"/>
     *         &lt;/choice>
     *         &lt;element name="accountHolder" type="{http://xml.A.com/v4}accountHolderType" maxOccurs="unbounded" minOccurs="0"/>
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
        "salesContext",
        "notificationEvents",
        "jobs",
        "orderInfo",
        "orderId",
        "orderSearchResult",
        "accountHolder"
    })
    public static class Response {

        @XmlElement(required = true)
        protected SalesContextType salesContext;
        protected NotificationEventCollectionType notificationEvents;
        protected JobCollectionType jobs;
        protected List<OrderType> orderInfo;
        protected String orderId;
        protected OrderSearch orderSearchResult;
        protected List<AccountHolderType> accountHolder;

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
         * Gets the value of the notificationEvents property.
         * 
         * @return
         *     possible object is
         *     {@link NotificationEventCollectionType }
         *     
         */
        public NotificationEventCollectionType getNotificationEvents() {
            return notificationEvents;
        }

        /**
         * Sets the value of the notificationEvents property.
         * 
         * @param value
         *     allowed object is
         *     {@link NotificationEventCollectionType }
         *     
         */
        public void setNotificationEvents(NotificationEventCollectionType value) {
            this.notificationEvents = value;
        }

        /**
         * Gets the value of the jobs property.
         * 
         * @return
         *     possible object is
         *     {@link JobCollectionType }
         *     
         */
        public JobCollectionType getJobs() {
            return jobs;
        }

        /**
         * Sets the value of the jobs property.
         * 
         * @param value
         *     allowed object is
         *     {@link JobCollectionType }
         *     
         */
        public void setJobs(JobCollectionType value) {
            this.jobs = value;
        }

        /**
         * Gets the value of the orderInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the orderInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOrderInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OrderType }
         * 
         * 
         */
        public List<OrderType> getOrderInfo() {
            if (orderInfo == null) {
                orderInfo = new ArrayList<OrderType>();
            }
            return this.orderInfo;
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
         * Gets the value of the orderSearchResult property.
         * 
         * @return
         *     possible object is
         *     {@link OrderSearch }
         *     
         */
        public OrderSearch getOrderSearchResult() {
            return orderSearchResult;
        }

        /**
         * Sets the value of the orderSearchResult property.
         * 
         * @param value
         *     allowed object is
         *     {@link OrderSearch }
         *     
         */
        public void setOrderSearchResult(OrderSearch value) {
            this.orderSearchResult = value;
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

    }

}
