
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceiversSolution complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceiversSolution">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customReceivers" type="{http://xml.A.com/v4}CustomReceivers"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceiversSolution", propOrder = {
    "customReceivers"
})
public class ReceiversSolution {

    @XmlElement(required = true)
    protected CustomReceivers customReceivers;

    /**
     * Gets the value of the customReceivers property.
     * 
     * @return
     *     possible object is
     *     {@link CustomReceivers }
     *     
     */
    public CustomReceivers getCustomReceivers() {
        return customReceivers;
    }

    /**
     * Sets the value of the customReceivers property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomReceivers }
     *     
     */
    public void setCustomReceivers(CustomReceivers value) {
        this.customReceivers = value;
    }

}
