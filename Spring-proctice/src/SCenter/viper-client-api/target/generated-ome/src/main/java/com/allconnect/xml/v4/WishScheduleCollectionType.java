
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wishScheduleCollectionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wishScheduleCollectionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ScheduleAsSoonAsPossible" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="wishSchedule" type="{http://xml.A.com/v4}wishScheduleType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wishScheduleCollectionType", propOrder = {
    "scheduleAsSoonAsPossible",
    "wishSchedule"
})
public class WishScheduleCollectionType {

    @XmlElement(name = "ScheduleAsSoonAsPossible")
    protected boolean scheduleAsSoonAsPossible;
    protected List<WishScheduleType> wishSchedule;

    /**
     * Gets the value of the scheduleAsSoonAsPossible property.
     * 
     */
    public boolean isScheduleAsSoonAsPossible() {
        return scheduleAsSoonAsPossible;
    }

    /**
     * Sets the value of the scheduleAsSoonAsPossible property.
     * 
     */
    public void setScheduleAsSoonAsPossible(boolean value) {
        this.scheduleAsSoonAsPossible = value;
    }

    /**
     * Gets the value of the wishSchedule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wishSchedule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWishSchedule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WishScheduleType }
     * 
     * 
     */
    public List<WishScheduleType> getWishSchedule() {
        if (wishSchedule == null) {
            wishSchedule = new ArrayList<WishScheduleType>();
        }
        return this.wishSchedule;
    }

}
