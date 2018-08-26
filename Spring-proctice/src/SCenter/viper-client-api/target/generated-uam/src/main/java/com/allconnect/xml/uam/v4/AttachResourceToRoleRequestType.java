
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachResourceToRoleRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachResourceToRoleRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element name="attachResourceToRoleRequest" type="{http://xml.A.com/v4}AttachResourceToRoleRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachResourceToRoleRequestType", propOrder = {
    "attachResourceToRoleRequest"
})
public class AttachResourceToRoleRequestType
    extends AbstractRequestType
{

    @XmlElement(required = true)
    protected AttachResourceToRoleRequest attachResourceToRoleRequest;

    /**
     * Gets the value of the attachResourceToRoleRequest property.
     * 
     * @return
     *     possible object is
     *     {@link AttachResourceToRoleRequest }
     *     
     */
    public AttachResourceToRoleRequest getAttachResourceToRoleRequest() {
        return attachResourceToRoleRequest;
    }

    /**
     * Sets the value of the attachResourceToRoleRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachResourceToRoleRequest }
     *     
     */
    public void setAttachResourceToRoleRequest(AttachResourceToRoleRequest value) {
        this.attachResourceToRoleRequest = value;
    }

}
