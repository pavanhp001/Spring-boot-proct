
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachResourceToRoleResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachResourceToRoleResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="attachResourceToRoleResponse" type="{http://xml.A.com/v4}AttachResourceToRoleResponse"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachResourceToRoleResponseType", propOrder = {
    "attachResourceToRoleResponse"
})
public class AttachResourceToRoleResponseType
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected AttachResourceToRoleResponse attachResourceToRoleResponse;

    /**
     * Gets the value of the attachResourceToRoleResponse property.
     * 
     * @return
     *     possible object is
     *     {@link AttachResourceToRoleResponse }
     *     
     */
    public AttachResourceToRoleResponse getAttachResourceToRoleResponse() {
        return attachResourceToRoleResponse;
    }

    /**
     * Sets the value of the attachResourceToRoleResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachResourceToRoleResponse }
     *     
     */
    public void setAttachResourceToRoleResponse(AttachResourceToRoleResponse value) {
        this.attachResourceToRoleResponse = value;
    }

}
