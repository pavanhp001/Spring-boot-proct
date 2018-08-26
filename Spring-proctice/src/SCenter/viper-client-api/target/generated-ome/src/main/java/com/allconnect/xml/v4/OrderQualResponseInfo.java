
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderQualResponseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderQualResponseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attribute" type="{http://xml.A.com/v4}attributeDetailType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="orderType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dueDateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestTnResponse" type="{http://xml.A.com/v4}RequestTnResponseInfo" minOccurs="0"/>
 *         &lt;element name="validateOrderResponse" type="{http://xml.A.com/v4}ValidateOrderResponseInfo" minOccurs="0"/>
 *         &lt;element name="setDueDateResponse" type="{http://xml.A.com/v4}SetDueDateResponseInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderQualResponseInfo", propOrder = {
    "code",
    "reason",
    "status",
    "message",
    "attribute",
    "orderType",
    "dueDateCode",
    "requestTnResponse",
    "validateOrderResponse",
    "setDueDateResponse"
})
public class OrderQualResponseInfo {

    protected String code;
    protected String reason;
    protected String status;
    protected String message;
    @XmlElement(nillable = true)
    protected List<AttributeDetailType> attribute;
    protected String orderType;
    protected String dueDateCode;
    protected RequestTnResponseInfo requestTnResponse;
    protected ValidateOrderResponseInfo validateOrderResponse;
    protected SetDueDateResponseInfo setDueDateResponse;

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
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
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
     * Gets the value of the attribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeDetailType }
     * 
     * 
     */
    public List<AttributeDetailType> getAttribute() {
        if (attribute == null) {
            attribute = new ArrayList<AttributeDetailType>();
        }
        return this.attribute;
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
     * Gets the value of the requestTnResponse property.
     * 
     * @return
     *     possible object is
     *     {@link RequestTnResponseInfo }
     *     
     */
    public RequestTnResponseInfo getRequestTnResponse() {
        return requestTnResponse;
    }

    /**
     * Sets the value of the requestTnResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestTnResponseInfo }
     *     
     */
    public void setRequestTnResponse(RequestTnResponseInfo value) {
        this.requestTnResponse = value;
    }

    /**
     * Gets the value of the validateOrderResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ValidateOrderResponseInfo }
     *     
     */
    public ValidateOrderResponseInfo getValidateOrderResponse() {
        return validateOrderResponse;
    }

    /**
     * Sets the value of the validateOrderResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidateOrderResponseInfo }
     *     
     */
    public void setValidateOrderResponse(ValidateOrderResponseInfo value) {
        this.validateOrderResponse = value;
    }

    /**
     * Gets the value of the setDueDateResponse property.
     * 
     * @return
     *     possible object is
     *     {@link SetDueDateResponseInfo }
     *     
     */
    public SetDueDateResponseInfo getSetDueDateResponse() {
        return setDueDateResponse;
    }

    /**
     * Sets the value of the setDueDateResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link SetDueDateResponseInfo }
     *     
     */
    public void setSetDueDateResponse(SetDueDateResponseInfo value) {
        this.setDueDateResponse = value;
    }

}
