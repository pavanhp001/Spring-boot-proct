
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RtimProvisioningResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RtimProvisioningResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4/VendorRequestResponse/}RtimResponse">
 *       &lt;sequence>
 *         &lt;element name="ProvisioningResponse" type="{http://xml.A.com/v4/OrderProvisioning/}ProvisioningResponse"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RtimProvisioningResponse", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "provisioningResponse"
})
public class RtimProvisioningResponse
    extends RtimResponse
{

    @XmlElement(name = "ProvisioningResponse", namespace = "", required = true, nillable = true)
    protected ProvisioningResponse provisioningResponse;

    /**
     * Gets the value of the provisioningResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ProvisioningResponse }
     *     
     */
    public ProvisioningResponse getProvisioningResponse() {
        return provisioningResponse;
    }

    /**
     * Sets the value of the provisioningResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvisioningResponse }
     *     
     */
    public void setProvisioningResponse(ProvisioningResponse value) {
        this.provisioningResponse = value;
    }

}
