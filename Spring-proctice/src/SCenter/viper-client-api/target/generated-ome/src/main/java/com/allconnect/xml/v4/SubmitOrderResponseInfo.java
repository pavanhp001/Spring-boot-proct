
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubmitOrderResponseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubmitOrderResponseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trackingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interval" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dueDateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="providerResponseInfo" type="{http://xml.A.com/v4}BillingAttributeType" minOccurs="0"/>
 *         &lt;element name="ccProcessingResponse" type="{http://xml.A.com/v4}CCProcessingResponseInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubmitOrderResponseInfo", propOrder = {
    "code",
    "message",
    "trackingNbr",
    "orderType",
    "dueDate",
    "interval",
    "dueDateCode",
    "providerResponseInfo",
    "ccProcessingResponse"
})
public class SubmitOrderResponseInfo {

    protected String code;
    protected String message;
    protected String trackingNbr;
    protected String orderType;
    protected String dueDate;
    protected String interval;
    protected String dueDateCode;
    protected BillingAttributeType providerResponseInfo;
    protected CCProcessingResponseInfo ccProcessingResponse;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the trackingNbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingNbr() {
        return trackingNbr;
    }

    /**
     * Sets the value of the trackingNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingNbr(String value) {
        this.trackingNbr = value;
    }

    /**
     * Gets the value of the orderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Sets the value of the orderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderType(String value) {
        this.orderType = value;
    }

    /**
     * Gets the value of the dueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets the value of the dueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDueDate(String value) {
        this.dueDate = value;
    }

    /**
     * Gets the value of the interval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterval() {
        return interval;
    }

    /**
     * Sets the value of the interval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterval(String value) {
        this.interval = value;
    }

    /**
     * Gets the value of the dueDateCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDueDateCode() {
        return dueDateCode;
    }

    /**
     * Sets the value of the dueDateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDueDateCode(String value) {
        this.dueDateCode = value;
    }

    /**
     * Gets the value of the providerResponseInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BillingAttributeType }
     *     
     */
    public BillingAttributeType getProviderResponseInfo() {
        return providerResponseInfo;
    }

    /**
     * Sets the value of the providerResponseInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingAttributeType }
     *     
     */
    public void setProviderResponseInfo(BillingAttributeType value) {
        this.providerResponseInfo = value;
    }

    /**
     * Gets the value of the ccProcessingResponse property.
     * 
     * @return
     *     possible object is
     *     {@link CCProcessingResponseInfo }
     *     
     */
    public CCProcessingResponseInfo getCcProcessingResponse() {
        return ccProcessingResponse;
    }

    /**
     * Sets the value of the ccProcessingResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCProcessingResponseInfo }
     *     
     */
    public void setCcProcessingResponse(CCProcessingResponseInfo value) {
        this.ccProcessingResponse = value;
    }

}
