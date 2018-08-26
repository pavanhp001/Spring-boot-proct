
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachRoleToUserResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachRoleToUserResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="roleAttachedToUser" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachRoleToUserResponse", propOrder = {
    "roleAttachedToUser"
})
public class AttachRoleToUserResponse {

    protected boolean roleAttachedToUser;

    /**
     * Gets the value of the roleAttachedToUser property.
     * 
     */
    public boolean isRoleAttachedToUser() {
        return roleAttachedToUser;
    }

    /**
     * Sets the value of the roleAttachedToUser property.
     * 
     */
    public void setRoleAttachedToUser(boolean value) {
        this.roleAttachedToUser = value;
    }

}
