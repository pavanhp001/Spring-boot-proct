
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EquipmentCountInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EquipmentCountInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="noOfTvs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="noOfReceivers" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="noOfHDs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="noOfDVRs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EquipmentCountInfo", propOrder = {
    "noOfTvs",
    "noOfReceivers",
    "noOfHDs",
    "noOfDVRs"
})
public class EquipmentCountInfo {

    protected int noOfTvs;
    protected int noOfReceivers;
    protected int noOfHDs;
    protected int noOfDVRs;

    /**
     * Gets the value of the noOfTvs property.
     * 
     */
    public int getNoOfTvs() {
        return noOfTvs;
    }

    /**
     * Sets the value of the noOfTvs property.
     * 
     */
    public void setNoOfTvs(int value) {
        this.noOfTvs = value;
    }

    /**
     * Gets the value of the noOfReceivers property.
     * 
     */
    public int getNoOfReceivers() {
        return noOfReceivers;
    }

    /**
     * Sets the value of the noOfReceivers property.
     * 
     */
    public void setNoOfReceivers(int value) {
        this.noOfReceivers = value;
    }

    /**
     * Gets the value of the noOfHDs property.
     * 
     */
    public int getNoOfHDs() {
        return noOfHDs;
    }

    /**
     * Sets the value of the noOfHDs property.
     * 
     */
    public void setNoOfHDs(int value) {
        this.noOfHDs = value;
    }

    /**
     * Gets the value of the noOfDVRs property.
     * 
     */
    public int getNoOfDVRs() {
        return noOfDVRs;
    }

    /**
     * Sets the value of the noOfDVRs property.
     * 
     */
    public void setNoOfDVRs(int value) {
        this.noOfDVRs = value;
    }

}
