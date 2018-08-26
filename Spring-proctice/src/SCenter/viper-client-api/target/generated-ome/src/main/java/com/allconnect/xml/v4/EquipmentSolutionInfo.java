
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EquipmentSolutionInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EquipmentSolutionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EquipmentCountInfo" type="{http://xml.A.com/v4}EquipmentCountInfo"/>
 *         &lt;element name="SolutionLineItem" type="{http://xml.A.com/v4}SolutionLineItem" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EquipmentSolutionInfo", propOrder = {
    "name",
    "equipmentCountInfo",
    "solutionLineItem"
})
public class EquipmentSolutionInfo {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(name = "EquipmentCountInfo", required = true)
    protected EquipmentCountInfo equipmentCountInfo;
    @XmlElement(name = "SolutionLineItem", required = true)
    protected List<SolutionLineItem> solutionLineItem;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the equipmentCountInfo property.
     * 
     * @return
     *     possible object is
     *     {@link EquipmentCountInfo }
     *     
     */
    public EquipmentCountInfo getEquipmentCountInfo() {
        return equipmentCountInfo;
    }

    /**
     * Sets the value of the equipmentCountInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link EquipmentCountInfo }
     *     
     */
    public void setEquipmentCountInfo(EquipmentCountInfo value) {
        this.equipmentCountInfo = value;
    }

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

}
