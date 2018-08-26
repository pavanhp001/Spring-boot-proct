
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ScheduleTimeSlots complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScheduleTimeSlots">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startHour" type="{http://www.w3.org/2001/XMLSchema}time"/>
 *         &lt;element name="endHour" type="{http://www.w3.org/2001/XMLSchema}time"/>
 *         &lt;element name="timeSlotRefId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isPremium" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleTimeSlots", propOrder = {
    "startHour",
    "endHour",
    "timeSlotRefId",
    "isPremium"
})
public class ScheduleTimeSlots {

    @XmlElement(required = true)
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar startHour;
    @XmlElement(required = true)
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar endHour;
    protected String timeSlotRefId;
    protected String isPremium;

    /**
     * Gets the value of the startHour property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartHour() {
        return startHour;
    }

    /**
     * Sets the value of the startHour property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartHour(XMLGregorianCalendar value) {
        this.startHour = value;
    }

    /**
     * Gets the value of the endHour property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndHour() {
        return endHour;
    }

    /**
     * Sets the value of the endHour property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndHour(XMLGregorianCalendar value) {
        this.endHour = value;
    }

    /**
     * Gets the value of the timeSlotRefId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeSlotRefId() {
        return timeSlotRefId;
    }

    /**
     * Sets the value of the timeSlotRefId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeSlotRefId(String value) {
        this.timeSlotRefId = value;
    }

    /**
     * Gets the value of the isPremium property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPremium() {
        return isPremium;
    }

    /**
     * Sets the value of the isPremium property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPremium(String value) {
        this.isPremium = value;
    }

}
