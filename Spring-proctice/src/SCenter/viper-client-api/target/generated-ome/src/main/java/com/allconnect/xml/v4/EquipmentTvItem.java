
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EquipmentTvItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EquipmentTvItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TvNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HdFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DvrFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="TvsSupported" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TunersSupported" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EquipmentTvItem", propOrder = {
    "tvNumber",
    "hdFlag",
    "dvrFlag",
    "tvsSupported",
    "tunersSupported"
})
public class EquipmentTvItem {

    @XmlElement(name = "TvNumber")
    protected int tvNumber;
    @XmlElement(name = "HdFlag")
    protected boolean hdFlag;
    @XmlElement(name = "DvrFlag")
    protected boolean dvrFlag;
    @XmlElement(name = "TvsSupported")
    protected int tvsSupported;
    @XmlElement(name = "TunersSupported")
    protected int tunersSupported;

    /**
     * Gets the value of the tvNumber property.
     * 
     */
    public int getTvNumber() {
        return tvNumber;
    }

    /**
     * Sets the value of the tvNumber property.
     * 
     */
    public void setTvNumber(int value) {
        this.tvNumber = value;
    }

    /**
     * Gets the value of the hdFlag property.
     * 
     */
    public boolean isHdFlag() {
        return hdFlag;
    }

    /**
     * Sets the value of the hdFlag property.
     * 
     */
    public void setHdFlag(boolean value) {
        this.hdFlag = value;
    }

    /**
     * Gets the value of the dvrFlag property.
     * 
     */
    public boolean isDvrFlag() {
        return dvrFlag;
    }

    /**
     * Sets the value of the dvrFlag property.
     * 
     */
    public void setDvrFlag(boolean value) {
        this.dvrFlag = value;
    }

    /**
     * Gets the value of the tvsSupported property.
     * 
     */
    public int getTvsSupported() {
        return tvsSupported;
    }

    /**
     * Sets the value of the tvsSupported property.
     * 
     */
    public void setTvsSupported(int value) {
        this.tvsSupported = value;
    }

    /**
     * Gets the value of the tunersSupported property.
     * 
     */
    public int getTunersSupported() {
        return tunersSupported;
    }

    /**
     * Sets the value of the tunersSupported property.
     * 
     */
    public void setTunersSupported(int value) {
        this.tunersSupported = value;
    }

}
