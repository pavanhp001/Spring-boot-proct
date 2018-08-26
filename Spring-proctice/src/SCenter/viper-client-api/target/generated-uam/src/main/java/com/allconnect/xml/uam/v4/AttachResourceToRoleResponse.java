
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachResourceToRoleResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachResourceToRoleResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resourceAttachedToRole" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachResourceToRoleResponse", propOrder = {
    "resourceAttachedToRole"
})
public class AttachResourceToRoleResponse {

    protected boolean resourceAttachedToRole;

    /**
     * Gets the value of the resourceAttachedToRole property.
     * 
     */
    public boolean isResourceAttachedToRole() {
        return resourceAttachedToRole;
    }

    /**
     * Sets the value of the resourceAttachedToRole property.
     * 
     */
    public void setResourceAttachedToRole(boolean value) {
        this.resourceAttachedToRole = value;
    }

}
