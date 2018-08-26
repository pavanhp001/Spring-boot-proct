
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="responseStatus" type="{http://xml.A.com/common}OpResponseStatus"/>
 *         &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lineItemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="providerExternalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="correlationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="response" type="{http://xml.A.com/v4/OrderProvisioning/}ProvisioningResponse"/>
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
    "responseStatus",
    "orderId",
    "lineItemId",
    "providerExternalId",
    "correlationId",
    "response"
})
@XmlRootElement(name = "OrderProvisioningResponse", namespace = "http://xml.A.com/v4/OrderProvisioning/")
public class OrderProvisioningResponse {

    @XmlElement(namespace = "", required = true)
    protected OpResponseStatus responseStatus;
    @XmlElement(namespace = "", required = true)
    protected String orderId;
    @XmlElement(namespace = "", required = true)
    protected String lineItemId;
    @XmlElement(namespace = "", required = true)
    protected String providerExternalId;
    @XmlElement(namespace = "", required = true)
    protected String correlationId;
    @XmlElement(namespace = "", required = true, nillable = true)
    protected ProvisioningResponse response;

    /**
     * Gets the value of the responseStatus property.
     * 
     * @return
     *     possible object is
     *     {@link OpResponseStatus }
     *     
     */
    public OpResponseStatus getResponseStatus() {
        return responseStatus;
    }

    /**
     * Sets the value of the responseStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpResponseStatus }
     *     
     */
    public void setResponseStatus(OpResponseStatus value) {
        this.responseStatus = value;
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
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link ProvisioningResponse }
     *     
     */
    public ProvisioningResponse getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvisioningResponse }
     *     
     */
    public void setResponse(ProvisioningResponse value) {
        this.response = value;
    }

}
