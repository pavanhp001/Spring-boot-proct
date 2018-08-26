
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachResourceToUserResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachResourceToUserResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resourceAttachedToUser" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachResourceToUserResponse", propOrder = {
    "resourceAttachedToUser"
})
public class AttachResourceToUserResponse {

    protected boolean resourceAttachedToUser;

    /**
     * Gets the value of the resourceAttachedToUser property.
     * 
     */
    public boolean isResourceAttachedToUser() {
        return resourceAttachedToUser;
    }

    /**
     * Sets the value of the resourceAttachedToUser property.
     * 
     */
    public void setResourceAttachedToUser(boolean value) {
        this.resourceAttachedToUser = value;
    }

}
