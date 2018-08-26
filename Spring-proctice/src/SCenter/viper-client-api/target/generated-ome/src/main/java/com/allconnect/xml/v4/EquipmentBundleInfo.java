
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EquipmentBundleInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EquipmentBundleInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SolutionLineItem" type="{http://xml.A.com/v4}SolutionLineItem" maxOccurs="unbounded"/>
 *         &lt;element name="PotentialAccessory" type="{http://xml.A.com/v4}PotentialAccessory" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EquipmentBundleInfo", propOrder = {
    "solutionLineItem",
    "potentialAccessory"
})
public class EquipmentBundleInfo {

    @XmlElement(name = "SolutionLineItem", required = true)
    protected List<SolutionLineItem> solutionLineItem;
    @XmlElement(name = "PotentialAccessory")
    protected List<PotentialAccessory> potentialAccessory;

    /**
     * Gets the value of the solutionLineItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the solutionLineItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSolutionLineItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SolutionLineItem }
     * 
     * 
     */
    public List<SolutionLineItem> getSolutionLineItem() {
        if (solutionLineItem == null) {
            solutionLineItem = new ArrayList<SolutionLineItem>();
        }
        return this.solutionLineItem;
    }

    /**
     * Gets the value of the potentialAccessory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the potentialAccessory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPotentialAccessory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PotentialAccessory }
     * 
     * 
     */
    public List<PotentialAccessory> getPotentialAccessory() {
        if (potentialAccessory == null) {
            potentialAccessory = new ArrayList<PotentialAccessory>();
        }
        return this.potentialAccessory;
    }

}
