
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetachRoleFromUserResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetachRoleFromUserResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="roleDetachedFromUser" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetachRoleFromUserResponse", propOrder = {
    "roleDetachedFromUser"
})
public class DetachRoleFromUserResponse {

    protected boolean roleDetachedFromUser;

    /**
     * Gets the value of the roleDetachedFromUser property.
     * 
     */
    public boolean isRoleDetachedFromUser() {
        return roleDetachedFromUser;
    }

    /**
     * Sets the value of the roleDetachedFromUser property.
     * 
     */
    public void setRoleDetachedFromUser(boolean value) {
        this.roleDetachedFromUser = value;
    }

}
