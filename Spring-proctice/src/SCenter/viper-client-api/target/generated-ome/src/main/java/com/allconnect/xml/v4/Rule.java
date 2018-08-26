
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Rule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Rule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Inclusion" type="{http://xml.A.com/v4}Inclusion" minOccurs="0"/>
 *         &lt;element name="Exclusion" type="{http://xml.A.com/v4}Exclusion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Rule", propOrder = {
    "inclusion",
    "exclusion"
})
public class Rule {

    @XmlElement(name = "Inclusion")
    protected Inclusion inclusion;
    @XmlElement(name = "Exclusion")
    protected Exclusion exclusion;

    /**
     * Gets the value of the inclusion property.
     * 
     * @return
     *     possible object is
     *     {@link Inclusion }
     *     
     */
    public Inclusion getInclusion() {
        return inclusion;
    }

    /**
     * Sets the value of the inclusion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Inclusion }
     *     
     */
    public void setInclusion(Inclusion value) {
        this.inclusion = value;
    }

    /**
     * Gets the value of the exclusion property.
     * 
     * @return
     *     possible object is
     *     {@link Exclusion }
     *     
     */
    public Exclusion getExclusion() {
        return exclusion;
    }

    /**
     * Sets the value of the exclusion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Exclusion }
     *     
     */
    public void setExclusion(Exclusion value) {
        this.exclusion = value;
    }

}
