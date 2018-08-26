
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
 * This element contains install dates
 *         
 * 
 * <p>Java class for OpSchedulingInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OpSchedulingInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="installDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="installTimeSlots" type="{http://xml.A.com/v4/OrderProvisioning/}InstallTimeSlot" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="timeDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpSchedulingInfo", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "installDate",
    "installTimeSlots",
    "timeDesc"
})
public class OpSchedulingInfo {

    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar installDate;
    @XmlElement(namespace = "")
    protected List<InstallTimeSlot> installTimeSlots;
    @XmlElement(namespace = "")
    protected String timeDesc;

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
     * Gets the value of the installTimeSlots property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the installTimeSlots property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstallTimeSlots().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstallTimeSlot }
     * 
     * 
     */
    public List<InstallTimeSlot> getInstallTimeSlots() {
        if (installTimeSlots == null) {
            installTimeSlots = new ArrayList<InstallTimeSlot>();
        }
        return this.installTimeSlots;
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

}
