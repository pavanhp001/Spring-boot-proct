
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LocalProgramming complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocalProgramming">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="superStationAlaCarte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocalProgramming", propOrder = {
    "superStationAlaCarte"
})
public class LocalProgramming {

    protected String superStationAlaCarte;

    /**
     * Gets the value of the superStationAlaCarte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuperStationAlaCarte() {
        return superStationAlaCarte;
    }

    /**
     * Sets the value of the superStationAlaCarte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuperStationAlaCarte(String value) {
        this.superStationAlaCarte = value;
    }

}
