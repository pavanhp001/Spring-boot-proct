
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidatePaymentResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidatePaymentResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4/OrderProvisioning/}ProvisioningResponse">
 *       &lt;sequence>
 *         &lt;element name="transientResponseContainer" type="{http://xml.A.com/v4}transientResponseContainerType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidatePaymentResponse", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "transientResponseContainer"
})
public class ValidatePaymentResponse
    extends ProvisioningResponse
{

    @XmlElement(namespace = "")
    protected TransientResponseContainerType transientResponseContainer;

    /**
     * Gets the value of the transientResponseContainer property.
     * 
     * @return
     *     possible object is
     *     {@link TransientResponseContainerType }
     *     
     */
    public TransientResponseContainerType getTransientResponseContainer() {
        return transientResponseContainer;
    }

    /**
     * Sets the value of the transientResponseContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransientResponseContainerType }
     *     
     */
    public void setTransientResponseContainer(TransientResponseContainerType value) {
        this.transientResponseContainer = value;
    }

}
