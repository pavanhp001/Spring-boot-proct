
package com.A.xml.dtl.v4;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="correlationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transactionType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="getAllDetails"/>
 *               &lt;enumeration value="getFeatureDetails"/>
 *               &lt;enumeration value="getDescriptiveInfoDetails"/>
 *               &lt;enumeration value="getMarketingHighlightDetails"/>
 *               &lt;enumeration value="getMetaDataDetails"/>
 *               &lt;enumeration value="getAllOrderSources"/>
 *               &lt;enumeration value="getOrderSources"/>
 *               &lt;enumeration value="getOrderSourceByVDN"/>
 *               &lt;enumeration value="getOrderSourceByExternalId"/>
 *               &lt;enumeration value="getReferenceData"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="status" type="{http://xml.A.com/v4}statusType" minOccurs="0"/>
 *         &lt;element name="request">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;sequence>
 *                     &lt;element name="detailRequestElement" type="{http://xml.A.com/v4}detailElementType" maxOccurs="unbounded"/>
 *                   &lt;/sequence>
 *                   &lt;sequence>
 *                     &lt;element name="orderSourceRequestElement" type="{http://xml.A.com/v4}orderSourceRequestElementType" maxOccurs="unbounded"/>
 *                   &lt;/sequence>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="response" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;sequence maxOccurs="unbounded">
 *                     &lt;element name="detailResultElement" type="{http://xml.A.com/v4}detailResultType" maxOccurs="unbounded"/>
 *                   &lt;/sequence>
 *                   &lt;sequence maxOccurs="unbounded">
 *                     &lt;element name="orderSourceResultElement" type="{http://xml.A.com/v4}orderSourceResultType" maxOccurs="unbounded"/>
 *                   &lt;/sequence>
 *                 &lt;/choice>
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
    "transactionType",
    "status",
    "request",
    "response"
})
@XmlRootElement(name = "detailsRequestResponse")
public class DetailsRequestResponse {

    protected String correlationId;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String transactionType;
    protected StatusType status;
    @XmlElement(required = true)
    protected DetailsRequestResponse.Request request;
    protected DetailsRequestResponse.Response response;

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
     *     {@link DetailsRequestResponse.Request }
     *     
     */
    public DetailsRequestResponse.Request getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailsRequestResponse.Request }
     *     
     */
    public void setRequest(DetailsRequestResponse.Request value) {
        this.request = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link DetailsRequestResponse.Response }
     *     
     */
    public DetailsRequestResponse.Response getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailsRequestResponse.Response }
     *     
     */
    public void setResponse(DetailsRequestResponse.Response value) {
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
     *       &lt;choice>
     *         &lt;sequence>
     *           &lt;element name="detailRequestElement" type="{http://xml.A.com/v4}detailElementType" maxOccurs="unbounded"/>
     *         &lt;/sequence>
     *         &lt;sequence>
     *           &lt;element name="orderSourceRequestElement" type="{http://xml.A.com/v4}orderSourceRequestElementType" maxOccurs="unbounded"/>
     *         &lt;/sequence>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "detailRequestElement",
        "orderSourceRequestElement"
    })
    public static class Request {

        protected List<DetailElementType> detailRequestElement;
        protected List<OrderSourceRequestElementType> orderSourceRequestElement;

        /**
         * Gets the value of the detailRequestElement property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the detailRequestElement property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDetailRequestElement().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DetailElementType }
         * 
         * 
         */
        public List<DetailElementType> getDetailRequestElement() {
            if (detailRequestElement == null) {
                detailRequestElement = new ArrayList<DetailElementType>();
            }
            return this.detailRequestElement;
        }

        /**
         * Gets the value of the orderSourceRequestElement property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the orderSourceRequestElement property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOrderSourceRequestElement().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OrderSourceRequestElementType }
         * 
         * 
         */
        public List<OrderSourceRequestElementType> getOrderSourceRequestElement() {
            if (orderSourceRequestElement == null) {
                orderSourceRequestElement = new ArrayList<OrderSourceRequestElementType>();
            }
            return this.orderSourceRequestElement;
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
     *       &lt;choice>
     *         &lt;sequence maxOccurs="unbounded">
     *           &lt;element name="detailResultElement" type="{http://xml.A.com/v4}detailResultType" maxOccurs="unbounded"/>
     *         &lt;/sequence>
     *         &lt;sequence maxOccurs="unbounded">
     *           &lt;element name="orderSourceResultElement" type="{http://xml.A.com/v4}orderSourceResultType" maxOccurs="unbounded"/>
     *         &lt;/sequence>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "detailResultElement",
        "orderSourceResultElement"
    })
    public static class Response {

        protected List<DetailResultType> detailResultElement;
        protected List<OrderSourceResultType> orderSourceResultElement;

        /**
         * Gets the value of the detailResultElement property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the detailResultElement property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDetailResultElement().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DetailResultType }
         * 
         * 
         */
        public List<DetailResultType> getDetailResultElement() {
            if (detailResultElement == null) {
                detailResultElement = new ArrayList<DetailResultType>();
            }
            return this.detailResultElement;
        }

        /**
         * Gets the value of the orderSourceResultElement property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the orderSourceResultElement property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOrderSourceResultElement().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OrderSourceResultType }
         * 
         * 
         */
        public List<OrderSourceResultType> getOrderSourceResultElement() {
            if (orderSourceResultElement == null) {
                orderSourceResultElement = new ArrayList<OrderSourceResultType>();
            }
            return this.orderSourceResultElement;
        }

    }

}
