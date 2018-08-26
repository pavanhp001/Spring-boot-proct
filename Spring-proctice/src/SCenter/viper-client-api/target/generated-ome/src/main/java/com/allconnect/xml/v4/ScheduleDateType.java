
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * This element contains an install date
 *             
 * 
 * <p>Java class for ScheduleDateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScheduleDateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="installDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="activationDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="disconnectDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="installTimeSlot" type="{http://xml.A.com/v4}ScheduleTimeSlots" maxOccurs="unbounded"/>
 *         &lt;element name="activationTimeSlot" type="{http://xml.A.com/common}TimeSlots" maxOccurs="unbounded"/>
 *         &lt;element name="disconnectTimeSlot" type="{http://xml.A.com/common}TimeSlots" maxOccurs="unbounded"/>
 *         &lt;element name="timeDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hasPremiumTimesslots" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleDateType", propOrder = {
    "installDate",
    "activationDate",
    "disconnectDate",
    "installTimeSlot",
    "activationTimeSlot",
    "disconnectTimeSlot",
    "timeDesc",
    "hasPremiumTimesslots"
})
public class ScheduleDateType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar installDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar activationDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar disconnectDate;
    @XmlElement(required = true)
    protected List<ScheduleTimeSlots> installTimeSlot;
    @XmlElement(required = true)
    protected List<TimeSlots> activationTimeSlot;
    @XmlElement(required = true)
    protected List<TimeSlots> disconnectTimeSlot;
    @XmlElement(required = true)
    protected String timeDesc;
    protected String hasPremiumTimesslots;

    /**
     * Gets the value of the installDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInstallDate() {
        return installDate;
    }

    /**
     * Sets the value of the installDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInstallDate(XMLGregorianCalendar value) {
        this.installDate = value;
    }

    /**
     * Gets the value of the activationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActivationDate() {
        return activationDate;
    }

    /**
     * Sets the value of the activationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActivationDate(XMLGregorianCalendar value) {
        this.activationDate = value;
    }

    /**
     * Gets the value of the disconnectDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDisconnectDate() {
        return disconnectDate;
    }

    /**
     * Sets the value of the disconnectDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDisconnectDate(XMLGregorianCalendar value) {
        this.disconnectDate = value;
    }

    /**
     * Gets the value of the installTimeSlot property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the installTimeSlot property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstallTimeSlot().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScheduleTimeSlots }
     * 
     * 
     */
    public List<ScheduleTimeSlots> getInstallTimeSlot() {
        if (installTimeSlot == null) {
            installTimeSlot = new ArrayList<ScheduleTimeSlots>();
        }
        return this.installTimeSlot;
    }

    /**
     * Gets the value of the activationTimeSlot property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the activationTimeSlot property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActivationTimeSlot().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimeSlots }
     * 
     * 
     */
    public List<TimeSlots> getActivationTimeSlot() {
        if (activationTimeSlot == null) {
            activationTimeSlot = new ArrayList<TimeSlots>();
        }
        return this.activationTimeSlot;
    }

    /**
     * Gets the value of the disconnectTimeSlot property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the disconnectTimeSlot property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisconnectTimeSlot().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimeSlots }
     * 
     * 
     */
    public List<TimeSlots> getDisconnectTimeSlot() {
        if (disconnectTimeSlot == null) {
            disconnectTimeSlot = new ArrayList<TimeSlots>();
        }
        return this.disconnectTimeSlot;
    }

    /**
     * Gets the value of the timeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeDesc() {
        return timeDesc;
    }

    /**
     * Sets the value of the timeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeDesc(String value) {
        this.timeDesc = value;
    }

    /**
     * Gets the value of the hasPremiumTimesslots property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasPremiumTimesslots() {
        return hasPremiumTimesslots;
    }

    /**
     * Sets the value of the hasPremiumTimesslots property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasPremiumTimesslots(String value) {
        this.hasPremiumTimesslots = value;
    }

}
