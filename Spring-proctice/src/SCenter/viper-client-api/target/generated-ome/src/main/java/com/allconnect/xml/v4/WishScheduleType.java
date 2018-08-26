
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for wishScheduleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wishScheduleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="ScheduleByDayAndTime" type="{http://xml.A.com/v4}DayAndTimeType"/>
 *           &lt;element name="timeSlot" type="{http://xml.A.com/v4}TimePeriodType"/>
 *         &lt;/choice>
 *         &lt;element name="schedule" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="appointmentDate" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *                 &lt;attribute name="startHour" type="{http://xml.A.com/v4}MilitaryHour" />
 *                 &lt;attribute name="endHour" type="{http://xml.A.com/v4}MilitaryHour" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wishScheduleType", propOrder = {
    "scheduleByDayAndTime",
    "timeSlot",
    "schedule"
})
public class WishScheduleType {

    @XmlElement(name = "ScheduleByDayAndTime")
    protected DayAndTimeType scheduleByDayAndTime;
    protected String timeSlot;
    protected List<WishScheduleType.Schedule> schedule;

    /**
     * Gets the value of the scheduleByDayAndTime property.
     * 
     * @return
     *     possible object is
     *     {@link DayAndTimeType }
     *     
     */
    public DayAndTimeType getScheduleByDayAndTime() {
        return scheduleByDayAndTime;
    }

    /**
     * Sets the value of the scheduleByDayAndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link DayAndTimeType }
     *     
     */
    public void setScheduleByDayAndTime(DayAndTimeType value) {
        this.scheduleByDayAndTime = value;
    }

    /**
     * Gets the value of the timeSlot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeSlot() {
        return timeSlot;
    }

    /**
     * Sets the value of the timeSlot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeSlot(String value) {
        this.timeSlot = value;
    }

    /**
     * Gets the value of the schedule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the schedule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSchedule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WishScheduleType.Schedule }
     * 
     * 
     */
    public List<WishScheduleType.Schedule> getSchedule() {
        if (schedule == null) {
            schedule = new ArrayList<WishScheduleType.Schedule>();
        }
        return this.schedule;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="appointmentDate" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
     *       &lt;attribute name="startHour" type="{http://xml.A.com/v4}MilitaryHour" />
     *       &lt;attribute name="endHour" type="{http://xml.A.com/v4}MilitaryHour" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Schedule {

        @XmlAttribute(name = "appointmentDate", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar appointmentDate;
        @XmlAttribute(name = "startHour")
        protected String startHour;
        @XmlAttribute(name = "endHour")
        protected String endHour;

        /**
         * Gets the value of the appointmentDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getAppointmentDate() {
            return appointmentDate;
        }

        /**
         * Sets the value of the appointmentDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setAppointmentDate(XMLGregorianCalendar value) {
            this.appointmentDate = value;
        }

        /**
         * Gets the value of the startHour property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStartHour() {
            return startHour;
        }

        /**
         * Sets the value of the startHour property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStartHour(String value) {
            this.startHour = value;
        }

        /**
         * Gets the value of the endHour property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEndHour() {
            return endHour;
        }

        /**
         * Sets the value of the endHour property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEndHour(String value) {
            this.endHour = value;
        }

    }

}
