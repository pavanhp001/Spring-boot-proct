
package com.A.xml.me.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for customSelectionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="customSelectionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="selections" type="{http://xml.A.com/v4}lineItemSelectionsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customSelectionsType", propOrder = {
    "selections"
})
public class CustomSelectionsType {

    protected LineItemSelectionsType selections;

    /**
     * Gets the value of the selections property.
     * 
     * @return
     *     possible object is
     *     {@link LineItemSelectionsType }
     *     
     */
    public LineItemSelectionsType getSelections() {
        return selections;
    }

    /**
     * Sets the value of the selections property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineItemSelectionsType }
     *     
     */
    public void setSelections(LineItemSelectionsType value) {
        this.selections = value;
    }

}
