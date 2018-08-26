
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachRoleToUserResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachRoleToUserResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="attachRoleToUserResponse" type="{http://xml.A.com/v4}AttachRoleToUserResponse"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachRoleToUserResponseType", propOrder = {
    "attachRoleToUserResponse"
})
public class AttachRoleToUserResponseType
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected AttachRoleToUserResponse attachRoleToUserResponse;

    /**
     * Gets the value of the attachRoleToUserResponse property.
     * 
     * @return
     *     possible object is
     *     {@link AttachRoleToUserResponse }
     *     
     */
    public AttachRoleToUserResponse getAttachRoleToUserResponse() {
        return attachRoleToUserResponse;
    }

    /**
     * Sets the value of the attachRoleToUserResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachRoleToUserResponse }
     *     
     */
    public void setAttachRoleToUserResponse(AttachRoleToUserResponse value) {
        this.attachRoleToUserResponse = value;
    }

}
