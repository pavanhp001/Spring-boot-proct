
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RtimProvisioningRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RtimProvisioningRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4/VendorRequestResponse/}RtimRequest">
 *       &lt;sequence>
 *         &lt;element name="provisioningRequest" type="{http://xml.A.com/v4/OrderProvisioning/}ProvisioningRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RtimProvisioningRequest", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "provisioningRequest"
})
public class RtimProvisioningRequest
    extends RtimRequest
{

    @XmlElement(namespace = "", required = true)
    protected ProvisioningRequest provisioningRequest;

    /**
     * Gets the value of the provisioningRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ProvisioningRequest }
     *     
     */
    public ProvisioningRequest getProvisioningRequest() {
        return provisioningRequest;
    }

    /**
     * Sets the value of the provisioningRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvisioningRequest }
     *     
     */
    public void setProvisioningRequest(ProvisioningRequest value) {
        this.provisioningRequest = value;
    }

}
