
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetachResourceFromRoleResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetachResourceFromRoleResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resourceDetachedFromRole" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetachResourceFromRoleResponse", propOrder = {
    "resourceDetachedFromRole"
})
public class DetachResourceFromRoleResponse {

    protected boolean resourceDetachedFromRole;

    /**
     * Gets the value of the resourceDetachedFromRole property.
     * 
     */
    public boolean isResourceDetachedFromRole() {
        return resourceDetachedFromRole;
    }

    /**
     * Sets the value of the resourceDetachedFromRole property.
     * 
     */
    public void setResourceDetachedFromRole(boolean value) {
        this.resourceDetachedFromRole = value;
    }

}
