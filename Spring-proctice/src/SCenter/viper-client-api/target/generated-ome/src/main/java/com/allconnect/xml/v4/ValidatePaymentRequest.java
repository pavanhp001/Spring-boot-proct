
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidatePaymentRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidatePaymentRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4/OrderProvisioning/}ProvisioningRequest">
 *       &lt;sequence>
 *         &lt;element name="productLineItem" type="{http://xml.A.com/v4/OrderProvisioning/}OpProductLineItem" minOccurs="0"/>
 *         &lt;element name="customerInfo" type="{http://xml.A.com/v4/OrderProvisioning/}OpCustomerInfo" minOccurs="0"/>
 *         &lt;element name="payments" type="{http://xml.A.com/v4}paymentsType"/>
 *         &lt;element name="transientRequestContainer" type="{http://xml.A.com/v4}transientRequestContainerType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidatePaymentRequest", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "productLineItem",
    "customerInfo",
    "payments",
    "transientRequestContainer"
})
public class ValidatePaymentRequest
    extends ProvisioningRequest
{

    @XmlElement(namespace = "")
    protected OpProductLineItem productLineItem;
    @XmlElement(namespace = "")
    protected OpCustomerInfo customerInfo;
    @XmlElement(namespace = "", required = true)
    protected PaymentsType payments;
    @XmlElement(namespace = "")
    protected TransientRequestContainerType transientRequestContainer;

    /**
     * Gets the value of the productLineItem property.
     * 
     * @return
     *     possible object is
     *     {@link OpProductLineItem }
     *     
     */
    public OpProductLineItem getProductLineItem() {
        return productLineItem;
    }

    /**
     * Sets the value of the productLineItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpProductLineItem }
     *     
     */
    public void setProductLineItem(OpProductLineItem value) {
        this.productLineItem = value;
    }

    /**
     * Gets the value of the customerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OpCustomerInfo }
     *     
     */
    public OpCustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    /**
     * Sets the value of the customerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpCustomerInfo }
     *     
     */
    public void setCustomerInfo(OpCustomerInfo value) {
        this.customerInfo = value;
    }

    /**
     * Gets the value of the payments property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentsType }
     *     
     */
    public PaymentsType getPayments() {
        return payments;
    }

    /**
     * Sets the value of the payments property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentsType }
     *     
     */
    public void setPayments(PaymentsType value) {
        this.payments = value;
    }

    /**
     * Gets the value of the transientRequestContainer property.
     * 
     * @return
     *     possible object is
     *     {@link TransientRequestContainerType }
     *     
     */
    public TransientRequestContainerType getTransientRequestContainer() {
        return transientRequestContainer;
    }

    /**
     * Sets the value of the transientRequestContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransientRequestContainerType }
     *     
     */
    public void setTransientRequestContainer(TransientRequestContainerType value) {
        this.transientRequestContainer = value;
    }

}
