
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetachResourceFromUserResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetachResourceFromUserResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resourceDetachedFromUser" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetachResourceFromUserResponse", propOrder = {
    "resourceDetachedFromUser"
})
public class DetachResourceFromUserResponse {

    protected boolean resourceDetachedFromUser;

    /**
     * Gets the value of the resourceDetachedFromUser property.
     * 
     */
    public boolean isResourceDetachedFromUser() {
        return resourceDetachedFromUser;
    }

    /**
     * Sets the value of the resourceDetachedFromUser property.
     * 
     */
    public void setResourceDetachedFromUser(boolean value) {
        this.resourceDetachedFromUser = value;
    }

}
