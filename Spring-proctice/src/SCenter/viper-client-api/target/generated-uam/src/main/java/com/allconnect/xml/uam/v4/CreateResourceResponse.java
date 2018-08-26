
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateResourceResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateResourceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resourceCreated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateResourceResponse", propOrder = {
    "resourceCreated"
})
public class CreateResourceResponse {

    protected boolean resourceCreated;

    /**
     * Gets the value of the resourceCreated property.
     * 
     */
    public boolean isResourceCreated() {
        return resourceCreated;
    }

    /**
     * Sets the value of the resourceCreated property.
     * 
     */
    public void setResourceCreated(boolean value) {
        this.resourceCreated = value;
    }

}
