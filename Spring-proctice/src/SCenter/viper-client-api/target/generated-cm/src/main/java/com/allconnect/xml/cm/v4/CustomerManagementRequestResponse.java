
package com.A.xml.cm.v4;

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
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="transactionType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="createCustomer"/>
 *               &lt;enumeration value="locateCustomer"/>
 *               &lt;enumeration value="deleteCustomer"/>
 *               &lt;enumeration value="updateCustomer"/>
 *               &lt;enumeration value="addAddress"/>
 *               &lt;enumeration value="getAddressById"/>
 *               &lt;enumeration value="getAddressByCustomerId"/>
 *               &lt;enumeration value="getCustomerById"/>
 *               &lt;enumeration value="getCustomerByCustomerNum"/>
 *               &lt;enumeration value="getContactInfoByCustomerId"/>
 *               &lt;enumeration value="getCustomerByContact"/>
 *               &lt;enumeration value="addCustomerInteraction"/>
 *               &lt;enumeration value="getInteractionByCustomerId"/>
 *               &lt;enumeration value="getInteractionByAgentId"/>
 *               &lt;enumeration value="searchCustomer"/>
 *               &lt;enumeration value="eventNotification"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="status" type="{http://xml.A.com/v4}statusType" minOccurs="0"/>
 *         &lt;element name="pagingDetail">
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
 *                   &lt;element name="customerContext" type="{http://xml.A.com/v4}customerContextType" minOccurs="0"/>
 *                   &lt;element name="notificationEventCollection" type="{http://xml.A.com/v4}notificationEventCollectionType"/>
 *                   &lt;element name="customerContactInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="customerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="addressId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="agentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;choice minOccurs="0">
 *                     &lt;sequence>
 *                       &lt;element name="addressInfo" type="{http://xml.A.com/v4}addressListType"/>
 *                     &lt;/sequence>
 *                     &lt;sequence>
 *                       &lt;element name="customerInfo" type="{http://xml.A.com/v4}customerType"/>
 *                     &lt;/sequence>
 *                     &lt;sequence>
 *                       &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                       &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                       &lt;element name="reasonCode" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                       &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                       &lt;element name="partialSSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                       &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                       &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                       &lt;element name="zipcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                       &lt;element name="streetAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                       &lt;element name="confirmationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                       &lt;element name="customerNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                       &lt;element name="providerExtId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                       &lt;element name="referrerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                       &lt;element name="orderStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                       &lt;element name="orderEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                       &lt;element name="emailId" minOccurs="0">
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                             &lt;pattern value="[A-Za-z0-9!#-'\*\+\-/=\?\^_`\{-~]+(\.[A-Za-z0-9!#-'\*\+\-/=\?\^_`\{-~]+)*@[A-Za-z0-9!#-'\*\+\-/=\?\^_`\{-~]+(\.[A-Za-z0-9!#-'\*\+\-/=\?\^_`\{-~]+)*"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                       &lt;/element>
 *                       &lt;element name="phoneNumber" minOccurs="0">
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                             &lt;pattern value="(1\s*[-]?)?(\((\d{3})\)|(\d{3}))\s*[-]?\s*(\d{3})\s*[-]?\s*(\d{4})\s*(([xX]|[eE][xX][tT])\.?\s*(\d+))*"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                       &lt;/element>
 *                       &lt;element name="isAccountHolderSearch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                     &lt;/sequence>
 *                   &lt;/choice>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="response">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="customerContext" type="{http://xml.A.com/v4}customerContextType" minOccurs="0"/>
 *                   &lt;element name="notificationEvents" type="{http://xml.A.com/v4}notificationEventCollectionType" minOccurs="0"/>
 *                   &lt;choice>
 *                     &lt;element name="customerSearchResult" type="{http://xml.A.com/v4}customerSearch"/>
 *                     &lt;element name="accountHolderSearchResult" type="{http://xml.A.com/v4}accountHolderSearch"/>
 *                     &lt;element name="customerInfo" type="{http://xml.A.com/v4}customerType" maxOccurs="unbounded"/>
 *                     &lt;element name="addressInfo" type="{http://xml.A.com/v4}addressType" maxOccurs="unbounded"/>
 *                     &lt;element name="customerContact" type="{http://xml.A.com/v4}contactType" minOccurs="0"/>
 *                   &lt;/choice>
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
    "transactionId",
    "transactionType",
    "status",
    "pagingDetail",
    "request",
    "response"
})
@XmlRootElement(name = "customerManagementRequestResponse")
public class CustomerManagementRequestResponse {

    protected String correlationId;
    protected Integer transactionId;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String transactionType;
    protected StatusType status;
    @XmlElement(required = true)
    protected CustomerManagementRequestResponse.PagingDetail pagingDetail;
    @XmlElement(required = true)
    protected CustomerManagementRequestResponse.Request request;
    @XmlElement(required = true)
    protected CustomerManagementRequestResponse.Response response;

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
     *     {@link CustomerManagementRequestResponse.PagingDetail }
     *     
     */
    public CustomerManagementRequestResponse.PagingDetail getPagingDetail() {
        return pagingDetail;
    }

    /**
     * Sets the value of the pagingDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerManagementRequestResponse.PagingDetail }
     *     
     */
    public void setPagingDetail(CustomerManagementRequestResponse.PagingDetail value) {
        this.pagingDetail = value;
    }

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerManagementRequestResponse.Request }
     *     
     */
    public CustomerManagementRequestResponse.Request getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerManagementRequestResponse.Request }
     *     
     */
    public void setRequest(CustomerManagementRequestResponse.Request value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerManagementRequestResponse.Response }
     *     
     */
    public CustomerManagementRequestResponse.Response getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerManagementRequestResponse.Response }
     *     
     */
    public void setResponse(CustomerManagementRequestResponse.Response value) {
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="customerContext" type="{http://xml.A.com/v4}customerContextType" minOccurs="0"/>
     *         &lt;element name="notificationEventCollection" type="{http://xml.A.com/v4}notificationEventCollectionType"/>
     *         &lt;element name="customerContactInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="customerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="addressId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="agentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;choice minOccurs="0">
     *           &lt;sequence>
     *             &lt;element name="addressInfo" type="{http://xml.A.com/v4}addressListType"/>
     *           &lt;/sequence>
     *           &lt;sequence>
     *             &lt;element name="customerInfo" type="{http://xml.A.com/v4}customerType"/>
     *           &lt;/sequence>
     *           &lt;sequence>
     *             &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *             &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *             &lt;element name="reasonCode" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *             &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *             &lt;element name="partialSSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *             &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *             &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *             &lt;element name="zipcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *             &lt;element name="streetAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *             &lt;element name="confirmationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *             &lt;element name="customerNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *             &lt;element name="providerExtId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *             &lt;element name="referrerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *             &lt;element name="orderStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *             &lt;element name="orderEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *             &lt;element name="emailId" minOccurs="0">
     *               &lt;simpleType>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                   &lt;pattern value="[A-Za-z0-9!#-'\*\+\-/=\?\^_`\{-~]+(\.[A-Za-z0-9!#-'\*\+\-/=\?\^_`\{-~]+)*@[A-Za-z0-9!#-'\*\+\-/=\?\^_`\{-~]+(\.[A-Za-z0-9!#-'\*\+\-/=\?\^_`\{-~]+)*"/>
     *                 &lt;/restriction>
     *               &lt;/simpleType>
     *             &lt;/element>
     *             &lt;element name="phoneNumber" minOccurs="0">
     *               &lt;simpleType>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *                   &lt;pattern value="(1\s*[-]?)?(\((\d{3})\)|(\d{3}))\s*[-]?\s*(\d{3})\s*[-]?\s*(\d{4})\s*(([xX]|[eE][xX][tT])\.?\s*(\d+))*"/>
     *                 &lt;/restriction>
     *               &lt;/simpleType>
     *             &lt;/element>
     *             &lt;element name="isAccountHolderSearch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *           &lt;/sequence>
     *         &lt;/choice>
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
        "customerContext",
        "notificationEventCollection",
        "customerContactInfo",
        "customerId",
        "addressId",
        "agentId",
        "addressInfo",
        "customerInfo",
        "firstName",
        "status",
        "reasonCode",
        "lastName",
        "partialSSN",
        "city",
        "state",
        "zipcode",
        "streetAddress",
        "confirmationNumber",
        "customerNumber",
        "providerExtId",
        "referrerId",
        "orderStartDate",
        "orderEndDate",
        "emailId",
        "phoneNumber",
        "isAccountHolderSearch"
    })
    public static class Request {

        protected CustomerContextType customerContext;
        @XmlElement(required = true)
        protected NotificationEventCollectionType notificationEventCollection;
        protected String customerContactInfo;
        protected String customerId;
        protected String addressId;
        protected String agentId;
        protected AddressListType addressInfo;
        protected CustomerType customerInfo;
        protected String firstName;
        protected List<String> status;
        protected List<String> reasonCode;
        protected String lastName;
        protected String partialSSN;
        protected String city;
        protected String state;
        protected String zipcode;
        protected String streetAddress;
        protected String confirmationNumber;
        protected String customerNumber;
        protected String providerExtId;
        protected String referrerId;
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar orderStartDate;
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar orderEndDate;
        protected String emailId;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String phoneNumber;
        protected String isAccountHolderSearch;

        /**
         * Gets the value of the customerContext property.
         * 
         * @return
         *     possible object is
         *     {@link CustomerContextType }
         *     
         */
        public CustomerContextType getCustomerContext() {
            return customerContext;
        }

        /**
         * Sets the value of the customerContext property.
         * 
         * @param value
         *     allowed object is
         *     {@link CustomerContextType }
         *     
         */
        public void setCustomerContext(CustomerContextType value) {
            this.customerContext = value;
        }

        /**
         * Gets the value of the notificationEventCollection property.
         * 
         * @return
         *     possible object is
         *     {@link NotificationEventCollectionType }
         *     
         */
        public NotificationEventCollectionType getNotificationEventCollection() {
            return notificationEventCollection;
        }

        /**
         * Sets the value of the notificationEventCollection property.
         * 
         * @param value
         *     allowed object is
         *     {@link NotificationEventCollectionType }
         *     
         */
        public void setNotificationEventCollection(NotificationEventCollectionType value) {
            this.notificationEventCollection = value;
        }

        /**
         * Gets the value of the customerContactInfo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerContactInfo() {
            return customerContactInfo;
        }

        /**
         * Sets the value of the customerContactInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerContactInfo(String value) {
            this.customerContactInfo = value;
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
         * Gets the value of the addressId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAddressId() {
            return addressId;
        }

        /**
         * Sets the value of the addressId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAddressId(String value) {
            this.addressId = value;
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
         * Gets the value of the addressInfo property.
         * 
         * @return
         *     possible object is
         *     {@link AddressListType }
         *     
         */
        public AddressListType getAddressInfo() {
            return addressInfo;
        }

        /**
         * Sets the value of the addressInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link AddressListType }
         *     
         */
        public void setAddressInfo(AddressListType value) {
            this.addressInfo = value;
        }

        /**
         * Gets the value of the customerInfo property.
         * 
         * @return
         *     possible object is
         *     {@link CustomerType }
         *     
         */
        public CustomerType getCustomerInfo() {
            return customerInfo;
        }

        /**
         * Sets the value of the customerInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link CustomerType }
         *     
         */
        public void setCustomerInfo(CustomerType value) {
            this.customerInfo = value;
        }

        /**
         * Gets the value of the firstName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * Sets the value of the firstName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFirstName(String value) {
            this.firstName = value;
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
         * Gets the value of the reasonCode property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the reasonCode property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReasonCode().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getReasonCode() {
            if (reasonCode == null) {
                reasonCode = new ArrayList<String>();
            }
            return this.reasonCode;
        }

        /**
         * Gets the value of the lastName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * Sets the value of the lastName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLastName(String value) {
            this.lastName = value;
        }

        /**
         * Gets the value of the partialSSN property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPartialSSN() {
            return partialSSN;
        }

        /**
         * Sets the value of the partialSSN property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPartialSSN(String value) {
            this.partialSSN = value;
        }

        /**
         * Gets the value of the city property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCity() {
            return city;
        }

        /**
         * Sets the value of the city property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCity(String value) {
            this.city = value;
        }

        /**
         * Gets the value of the state property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getState() {
            return state;
        }

        /**
         * Sets the value of the state property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setState(String value) {
            this.state = value;
        }

        /**
         * Gets the value of the zipcode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZipcode() {
            return zipcode;
        }

        /**
         * Sets the value of the zipcode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZipcode(String value) {
            this.zipcode = value;
        }

        /**
         * Gets the value of the streetAddress property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStreetAddress() {
            return streetAddress;
        }

        /**
         * Sets the value of the streetAddress property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStreetAddress(String value) {
            this.streetAddress = value;
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
         * Gets the value of the customerNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerNumber() {
            return customerNumber;
        }

        /**
         * Sets the value of the customerNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerNumber(String value) {
            this.customerNumber = value;
        }

        /**
         * Gets the value of the providerExtId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProviderExtId() {
            return providerExtId;
        }

        /**
         * Sets the value of the providerExtId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProviderExtId(String value) {
            this.providerExtId = value;
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
         * Gets the value of the orderStartDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getOrderStartDate() {
            return orderStartDate;
        }

        /**
         * Sets the value of the orderStartDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setOrderStartDate(XMLGregorianCalendar value) {
            this.orderStartDate = value;
        }

        /**
         * Gets the value of the orderEndDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getOrderEndDate() {
            return orderEndDate;
        }

        /**
         * Sets the value of the orderEndDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setOrderEndDate(XMLGregorianCalendar value) {
            this.orderEndDate = value;
        }

        /**
         * Gets the value of the emailId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEmailId() {
            return emailId;
        }

        /**
         * Sets the value of the emailId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEmailId(String value) {
            this.emailId = value;
        }

        /**
         * Gets the value of the phoneNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPhoneNumber() {
            return phoneNumber;
        }

        /**
         * Sets the value of the phoneNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPhoneNumber(String value) {
            this.phoneNumber = value;
        }

        /**
         * Gets the value of the isAccountHolderSearch property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsAccountHolderSearch() {
            return isAccountHolderSearch;
        }

        /**
         * Sets the value of the isAccountHolderSearch property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsAccountHolderSearch(String value) {
            this.isAccountHolderSearch = value;
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
     *         &lt;element name="customerContext" type="{http://xml.A.com/v4}customerContextType" minOccurs="0"/>
     *         &lt;element name="notificationEvents" type="{http://xml.A.com/v4}notificationEventCollectionType" minOccurs="0"/>
     *         &lt;choice>
     *           &lt;element name="customerSearchResult" type="{http://xml.A.com/v4}customerSearch"/>
     *           &lt;element name="accountHolderSearchResult" type="{http://xml.A.com/v4}accountHolderSearch"/>
     *           &lt;element name="customerInfo" type="{http://xml.A.com/v4}customerType" maxOccurs="unbounded"/>
     *           &lt;element name="addressInfo" type="{http://xml.A.com/v4}addressType" maxOccurs="unbounded"/>
     *           &lt;element name="customerContact" type="{http://xml.A.com/v4}contactType" minOccurs="0"/>
     *         &lt;/choice>
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
        "customerContext",
        "notificationEvents",
        "customerSearchResult",
        "accountHolderSearchResult",
        "customerInfo",
        "addressInfo",
        "customerContact"
    })
    public static class Response {

        protected CustomerContextType customerContext;
        protected NotificationEventCollectionType notificationEvents;
        protected CustomerSearch customerSearchResult;
        protected AccountHolderSearch accountHolderSearchResult;
        protected List<CustomerType> customerInfo;
        protected List<AddressType> addressInfo;
        protected ContactType customerContact;

        /**
         * Gets the value of the customerContext property.
         * 
         * @return
         *     possible object is
         *     {@link CustomerContextType }
         *     
         */
        public CustomerContextType getCustomerContext() {
            return customerContext;
        }

        /**
         * Sets the value of the customerContext property.
         * 
         * @param value
         *     allowed object is
         *     {@link CustomerContextType }
         *     
         */
        public void setCustomerContext(CustomerContextType value) {
            this.customerContext = value;
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
         * Gets the value of the customerSearchResult property.
         * 
         * @return
         *     possible object is
         *     {@link CustomerSearch }
         *     
         */
        public CustomerSearch getCustomerSearchResult() {
            return customerSearchResult;
        }

        /**
         * Sets the value of the customerSearchResult property.
         * 
         * @param value
         *     allowed object is
         *     {@link CustomerSearch }
         *     
         */
        public void setCustomerSearchResult(CustomerSearch value) {
            this.customerSearchResult = value;
        }

        /**
         * Gets the value of the accountHolderSearchResult property.
         * 
         * @return
         *     possible object is
         *     {@link AccountHolderSearch }
         *     
         */
        public AccountHolderSearch getAccountHolderSearchResult() {
            return accountHolderSearchResult;
        }

        /**
         * Sets the value of the accountHolderSearchResult property.
         * 
         * @param value
         *     allowed object is
         *     {@link AccountHolderSearch }
         *     
         */
        public void setAccountHolderSearchResult(AccountHolderSearch value) {
            this.accountHolderSearchResult = value;
        }

        /**
         * Gets the value of the customerInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the customerInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCustomerInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CustomerType }
         * 
         * 
         */
        public List<CustomerType> getCustomerInfo() {
            if (customerInfo == null) {
                customerInfo = new ArrayList<CustomerType>();
            }
            return this.customerInfo;
        }

        /**
         * Gets the value of the addressInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the addressInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAddressInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AddressType }
         * 
         * 
         */
        public List<AddressType> getAddressInfo() {
            if (addressInfo == null) {
                addressInfo = new ArrayList<AddressType>();
            }
            return this.addressInfo;
        }

        /**
         * Gets the value of the customerContact property.
         * 
         * @return
         *     possible object is
         *     {@link ContactType }
         *     
         */
        public ContactType getCustomerContact() {
            return customerContact;
        }

        /**
         * Sets the value of the customerContact property.
         * 
         * @param value
         *     allowed object is
         *     {@link ContactType }
         *     
         */
        public void setCustomerContact(ContactType value) {
            this.customerContact = value;
        }

    }

}
