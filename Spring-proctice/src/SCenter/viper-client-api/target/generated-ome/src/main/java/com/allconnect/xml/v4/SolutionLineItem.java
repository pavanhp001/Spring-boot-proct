
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SolutionLineItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SolutionLineItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MonthlyPrice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="ReceiverId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Purchased" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="EquipmentTvItem" type="{http://xml.A.com/v4}EquipmentTvItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SolutionLineItem", propOrder = {
    "description",
    "monthlyPrice",
    "receiverId",
    "purchased",
    "equipmentTvItem"
})
public class SolutionLineItem {

    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "MonthlyPrice")
    protected float monthlyPrice;
    @XmlElement(name = "ReceiverId")
    protected Integer receiverId;
    @XmlElement(name = "Purchased")
    protected Boolean purchased;
    @XmlElement(name = "EquipmentTvItem")
    protected List<EquipmentTvItem> equipmentTvItem;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the monthlyPrice property.
     * 
     */
    public float getMonthlyPrice() {
        return monthlyPrice;
    }

    /**
     * Sets the value of the monthlyPrice property.
     * 
     */
    public void setMonthlyPrice(float value) {
        this.monthlyPrice = value;
    }

    /**
     * Gets the value of the receiverId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReceiverId() {
        return receiverId;
    }

    /**
     * Sets the value of the receiverId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReceiverId(Integer value) {
        this.receiverId = value;
    }

    /**
     * Gets the value of the purchased property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPurchased() {
        return purchased;
    }

    /**
     * Sets the value of the purchased property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPurchased(Boolean value) {
        this.purchased = value;
    }

    /**
     * Gets the value of the equipmentTvItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the equipmentTvItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEquipmentTvItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EquipmentTvItem }
     * 
     * 
     */
    public List<EquipmentTvItem> getEquipmentTvItem() {
        if (equipmentTvItem == null) {
            equipmentTvItem = new ArrayList<EquipmentTvItem>();
        }
        return this.equipmentTvItem;
    }

}
