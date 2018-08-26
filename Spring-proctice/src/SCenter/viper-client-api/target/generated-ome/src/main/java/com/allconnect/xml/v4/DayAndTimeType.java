
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DayAndTimeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DayAndTimeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="Monday" type="{http://xml.A.com/v4}TimePeriodType" minOccurs="0"/>
 *           &lt;element name="Tuesday" type="{http://xml.A.com/v4}TimePeriodType" minOccurs="0"/>
 *           &lt;element name="Wednesday" type="{http://xml.A.com/v4}TimePeriodType" minOccurs="0"/>
 *           &lt;element name="Thursday" type="{http://xml.A.com/v4}TimePeriodType" minOccurs="0"/>
 *           &lt;element name="Friday" type="{http://xml.A.com/v4}TimePeriodType" minOccurs="0"/>
 *           &lt;element name="Saturday" type="{http://xml.A.com/v4}TimePeriodType" minOccurs="0"/>
 *           &lt;element name="Sunday" type="{http://xml.A.com/v4}TimePeriodType" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;element name="AnyDayOfTheWeek" type="{http://xml.A.com/v4}TimePeriodType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DayAndTimeType", propOrder = {
    "monday",
    "tuesday",
    "wednesday",
    "thursday",
    "friday",
    "saturday",
    "sunday",
    "anyDayOfTheWeek"
})
public class DayAndTimeType {

    @XmlElement(name = "Monday")
    protected String monday;
    @XmlElement(name = "Tuesday")
    protected String tuesday;
    @XmlElement(name = "Wednesday")
    protected String wednesday;
    @XmlElement(name = "Thursday")
    protected String thursday;
    @XmlElement(name = "Friday")
    protected String friday;
    @XmlElement(name = "Saturday")
    protected String saturday;
    @XmlElement(name = "Sunday")
    protected String sunday;
    @XmlElement(name = "AnyDayOfTheWeek")
    protected String anyDayOfTheWeek;

    /**
     * Gets the value of the monday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonday() {
        return monday;
    }

    /**
     * Sets the value of the monday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonday(String value) {
        this.monday = value;
    }

    /**
     * Gets the value of the tuesday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTuesday() {
        return tuesday;
    }

    /**
     * Sets the value of the tuesday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTuesday(String value) {
        this.tuesday = value;
    }

    /**
     * Gets the value of the wednesday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWednesday() {
        return wednesday;
    }

    /**
     * Sets the value of the wednesday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWednesday(String value) {
        this.wednesday = value;
    }

    /**
     * Gets the value of the thursday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThursday() {
        return thursday;
    }

    /**
     * Sets the value of the thursday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThursday(String value) {
        this.thursday = value;
    }

    /**
     * Gets the value of the friday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFriday() {
        return friday;
    }

    /**
     * Sets the value of the friday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFriday(String value) {
        this.friday = value;
    }

    /**
     * Gets the value of the saturday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSaturday() {
        return saturday;
    }

    /**
     * Sets the value of the saturday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaturday(String value) {
        this.saturday = value;
    }

    /**
     * Gets the value of the sunday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSunday() {
        return sunday;
    }

    /**
     * Sets the value of the sunday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSunday(String value) {
        this.sunday = value;
    }

    /**
     * Gets the value of the anyDayOfTheWeek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnyDayOfTheWeek() {
        return anyDayOfTheWeek;
    }

    /**
     * Sets the value of the anyDayOfTheWeek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnyDayOfTheWeek(String value) {
        this.anyDayOfTheWeek = value;
    }

}
