
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartialPaymentDueOption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartialPaymentDueOption">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amountDueNow" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="promptMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartialPaymentDueOption", propOrder = {
    "amountDueNow",
    "promptMessage"
})
public class PartialPaymentDueOption {

    protected float amountDueNow;
    @XmlElement(required = true)
    protected String promptMessage;

    /**
     * Gets the value of the amountDueNow property.
     * 
     */
    public float getAmountDueNow() {
        return amountDueNow;
    }

    /**
     * Sets the value of the amountDueNow property.
     * 
     */
    public void setAmountDueNow(float value) {
        this.amountDueNow = value;
    }

    /**
     * Gets the value of the promptMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptMessage() {
        return promptMessage;
    }

    /**
     * Sets the value of the promptMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptMessage(String value) {
        this.promptMessage = value;
    }

}
