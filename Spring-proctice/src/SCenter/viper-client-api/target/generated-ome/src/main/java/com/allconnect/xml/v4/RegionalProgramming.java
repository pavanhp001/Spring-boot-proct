
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RegionalProgramming complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegionalProgramming">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="localPackage" type="{http://xml.A.com/v4}package" minOccurs="0"/>
 *         &lt;element name="distantLocalPackage" type="{http://xml.A.com/v4}package" minOccurs="0"/>
 *         &lt;element name="distantLocalAlaCarte" type="{http://xml.A.com/v4}package" minOccurs="0"/>
 *         &lt;element name="superstationPackage" type="{http://xml.A.com/v4}package" minOccurs="0"/>
 *         &lt;element name="stateWideLocal" type="{http://xml.A.com/v4}package" minOccurs="0"/>
 *         &lt;element name="nationalPbs" type="{http://xml.A.com/v4}package" minOccurs="0"/>
 *         &lt;element name="regionalSports" type="{http://xml.A.com/v4}package" minOccurs="0"/>
 *         &lt;element name="superStationAlaCarte" type="{http://xml.A.com/v4}package" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegionalProgramming", propOrder = {
    "localPackage",
    "distantLocalPackage",
    "distantLocalAlaCarte",
    "superstationPackage",
    "stateWideLocal",
    "nationalPbs",
    "regionalSports",
    "superStationAlaCarte"
})
public class RegionalProgramming {

    protected Package localPackage;
    protected Package distantLocalPackage;
    protected Package distantLocalAlaCarte;
    protected Package superstationPackage;
    protected Package stateWideLocal;
    protected Package nationalPbs;
    protected Package regionalSports;
    protected Package superStationAlaCarte;

    /**
     * Gets the value of the localPackage property.
     * 
     * @return
     *     possible object is
     *     {@link Package }
     *     
     */
    public Package getLocalPackage() {
        return localPackage;
    }

    /**
     * Sets the value of the localPackage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Package }
     *     
     */
    public void setLocalPackage(Package value) {
        this.localPackage = value;
    }

    /**
     * Gets the value of the distantLocalPackage property.
     * 
     * @return
     *     possible object is
     *     {@link Package }
     *     
     */
    public Package getDistantLocalPackage() {
        return distantLocalPackage;
    }

    /**
     * Sets the value of the distantLocalPackage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Package }
     *     
     */
    public void setDistantLocalPackage(Package value) {
        this.distantLocalPackage = value;
    }

    /**
     * Gets the value of the distantLocalAlaCarte property.
     * 
     * @return
     *     possible object is
     *     {@link Package }
     *     
     */
    public Package getDistantLocalAlaCarte() {
        return distantLocalAlaCarte;
    }

    /**
     * Sets the value of the distantLocalAlaCarte property.
     * 
     * @param value
     *     allowed object is
     *     {@link Package }
     *     
     */
    public void setDistantLocalAlaCarte(Package value) {
        this.distantLocalAlaCarte = value;
    }

    /**
     * Gets the value of the superstationPackage property.
     * 
     * @return
     *     possible object is
     *     {@link Package }
     *     
     */
    public Package getSuperstationPackage() {
        return superstationPackage;
    }

    /**
     * Sets the value of the superstationPackage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Package }
     *     
     */
    public void setSuperstationPackage(Package value) {
        this.superstationPackage = value;
    }

    /**
     * Gets the value of the stateWideLocal property.
     * 
     * @return
     *     possible object is
     *     {@link Package }
     *     
     */
    public Package getStateWideLocal() {
        return stateWideLocal;
    }

    /**
     * Sets the value of the stateWideLocal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Package }
     *     
     */
    public void setStateWideLocal(Package value) {
        this.stateWideLocal = value;
    }

    /**
     * Gets the value of the nationalPbs property.
     * 
     * @return
     *     possible object is
     *     {@link Package }
     *     
     */
    public Package getNationalPbs() {
        return nationalPbs;
    }

    /**
     * Sets the value of the nationalPbs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Package }
     *     
     */
    public void setNationalPbs(Package value) {
        this.nationalPbs = value;
    }

    /**
     * Gets the value of the regionalSports property.
     * 
     * @return
     *     possible object is
     *     {@link Package }
     *     
     */
    public Package getRegionalSports() {
        return regionalSports;
    }

    /**
     * Sets the value of the regionalSports property.
     * 
     * @param value
     *     allowed object is
     *     {@link Package }
     *     
     */
    public void setRegionalSports(Package value) {
        this.regionalSports = value;
    }

    /**
     * Gets the value of the superStationAlaCarte property.
     * 
     * @return
     *     possible object is
     *     {@link Package }
     *     
     */
    public Package getSuperStationAlaCarte() {
        return superStationAlaCarte;
    }

    /**
     * Sets the value of the superStationAlaCarte property.
     * 
     * @param value
     *     allowed object is
     *     {@link Package }
     *     
     */
    public void setSuperStationAlaCarte(Package value) {
        this.superStationAlaCarte = value;
    }

}
