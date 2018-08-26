
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A specification of what the customer wants a single
 *                 TV to support.
 * 
 * <p>Java class for TvConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TvConfiguration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Label" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Hd" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Dvr" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TvConfiguration", propOrder = {
    "label",
    "hd",
    "dvr"
})
public class TvConfiguration {

    @XmlElement(name = "Label", required = true)
    protected String label;
    @XmlElement(name = "Hd")
    protected boolean hd;
    @XmlElement(name = "Dvr")
    protected boolean dvr;

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the hd property.
     * 
     */
    public boolean isHd() {
        return hd;
    }

    /**
     * Sets the value of the hd property.
     * 
     */
    public void setHd(boolean value) {
        this.hd = value;
    }

    /**
     * Gets the value of the dvr property.
     * 
     */
    public boolean isDvr() {
        return dvr;
    }

    /**
     * Sets the value of the dvr property.
     * 
     */
    public void setDvr(boolean value) {
        this.dvr = value;
    }

}
