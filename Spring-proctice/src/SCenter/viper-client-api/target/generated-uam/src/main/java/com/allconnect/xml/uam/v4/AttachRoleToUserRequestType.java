
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachRoleToUserRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachRoleToUserRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element name="attachRoleToUserRequest" type="{http://xml.A.com/v4}AttachRoleToUserRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachRoleToUserRequestType", propOrder = {
    "attachRoleToUserRequest"
})
public class AttachRoleToUserRequestType
    extends AbstractRequestType
{

    @XmlElement(required = true)
    protected AttachRoleToUserRequest attachRoleToUserRequest;

    /**
     * Gets the value of the attachRoleToUserRequest property.
     * 
     * @return
     *     possible object is
     *     {@link AttachRoleToUserRequest }
     *     
     */
    public AttachRoleToUserRequest getAttachRoleToUserRequest() {
        return attachRoleToUserRequest;
    }

    /**
     * Sets the value of the attachRoleToUserRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachRoleToUserRequest }
     *     
     */
    public void setAttachRoleToUserRequest(AttachRoleToUserRequest value) {
        this.attachRoleToUserRequest = value;
    }

}
