
package com.A.xml.v4;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SchedulingInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SchedulingInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ScheduleAsSoonAsPossible" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;choice>
 *           &lt;element name="ActualSchedule">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attribute name="actualAppointmentDate" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *                   &lt;attribute name="actualAppointmentStartHour" type="{http://xml.A.com/v4}MilitaryHour" />
 *                   &lt;attribute name="actualAppointmentEndHour" type="{http://xml.A.com/v4}MilitaryHour" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;element name="wishScheduleCollection" type="{http://xml.A.com/v4}wishScheduleCollectionType"/>
 *         &lt;element name="desiredStartDate" type="{http://xml.A.com/v4}dateTimeOrTimeRangeType" minOccurs="0"/>
 *         &lt;element name="scheduledStartDate" type="{http://xml.A.com/v4}dateTimeOrTimeRangeType" minOccurs="0"/>
 *         &lt;element name="actualStartDate" type="{http://xml.A.com/v4}dateTimeOrTimeRangeType" minOccurs="0"/>
 *         &lt;element name="disconnectDate" type="{http://xml.A.com/v4}dateTimeOrTimeRangeType" minOccurs="0"/>
 *         &lt;element name="orderDate" type="{http://xml.A.com/v4}dateTimeOrTimeRangeType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="billingInstallments" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="installationFee" type="{http://xml.A.com/v4}currencyType" />
 *       &lt;attribute name="appointmentComment" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="earlierAppointmentDate" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="residenceType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SchedulingInfoType", propOrder = {
    "scheduleAsSoonAsPossible",
    "actualSchedule",
    "wishScheduleCollection",
    "desiredStartDate",
    "scheduledStartDate",
    "actualStartDate",
    "disconnectDate",
    "orderDate"
})
public class SchedulingInfoType {

    @XmlElement(name = "ScheduleAsSoonAsPossible")
    protected boolean scheduleAsSoonAsPossible;
    @XmlElement(name = "ActualSchedule")
    protected SchedulingInfoType.ActualSchedule actualSchedule;
    @XmlElement(required = true)
    protected WishScheduleCollectionType wishScheduleCollection;
    protected DateTimeOrTimeRangeType desiredStartDate;
    protected DateTimeOrTimeRangeType scheduledStartDate;
    protected DateTimeOrTimeRangeType actualStartDate;
    protected DateTimeOrTimeRangeType disconnectDate;
    protected DateTimeOrTimeRangeType orderDate;
    @XmlAttribute(name = "billingInstallments")
    protected Boolean billingInstallments;
    @XmlAttribute(name = "installationFee")
    protected BigDecimal installationFee;
    @XmlAttribute(name = "appointmentComment")
    protected String appointmentComment;
    @XmlAttribute(name = "earlierAppointmentDate")
    protected Boolean earlierAppointmentDate;
    @XmlAttribute(name = "residenceType")
    protected String residenceType;

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
     * Gets the value of the actualSchedule property.
     * 
     * @return
     *     possible object is
     *     {@link SchedulingInfoType.ActualSchedule }
     *     
     */
    public SchedulingInfoType.ActualSchedule getActualSchedule() {
        return actualSchedule;
    }

    /**
     * Sets the value of the actualSchedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchedulingInfoType.ActualSchedule }
     *     
     */
    public void setActualSchedule(SchedulingInfoType.ActualSchedule value) {
        this.actualSchedule = value;
    }

    /**
     * Gets the value of the wishScheduleCollection property.
     * 
     * @return
     *     possible object is
     *     {@link WishScheduleCollectionType }
     *     
     */
    public WishScheduleCollectionType getWishScheduleCollection() {
        return wishScheduleCollection;
    }

    /**
     * Sets the value of the wishScheduleCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link WishScheduleCollectionType }
     *     
     */
    public void setWishScheduleCollection(WishScheduleCollectionType value) {
        this.wishScheduleCollection = value;
    }

    /**
     * Gets the value of the desiredStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeOrTimeRangeType }
     *     
     */
    public DateTimeOrTimeRangeType getDesiredStartDate() {
        return desiredStartDate;
    }

    /**
     * Sets the value of the desiredStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeOrTimeRangeType }
     *     
     */
    public void setDesiredStartDate(DateTimeOrTimeRangeType value) {
        this.desiredStartDate = value;
    }

    /**
     * Gets the value of the scheduledStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeOrTimeRangeType }
     *     
     */
    public DateTimeOrTimeRangeType getScheduledStartDate() {
        return scheduledStartDate;
    }

    /**
     * Sets the value of the scheduledStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeOrTimeRangeType }
     *     
     */
    public void setScheduledStartDate(DateTimeOrTimeRangeType value) {
        this.scheduledStartDate = value;
    }

    /**
     * Gets the value of the actualStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeOrTimeRangeType }
     *     
     */
    public DateTimeOrTimeRangeType getActualStartDate() {
        return actualStartDate;
    }

    /**
     * Sets the value of the actualStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeOrTimeRangeType }
     *     
     */
    public void setActualStartDate(DateTimeOrTimeRangeType value) {
        this.actualStartDate = value;
    }

    /**
     * Gets the value of the disconnectDate property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeOrTimeRangeType }
     *     
     */
    public DateTimeOrTimeRangeType getDisconnectDate() {
        return disconnectDate;
    }

    /**
     * Sets the value of the disconnectDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeOrTimeRangeType }
     *     
     */
    public void setDisconnectDate(DateTimeOrTimeRangeType value) {
        this.disconnectDate = value;
    }

    /**
     * Gets the value of the orderDate property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeOrTimeRangeType }
     *     
     */
    public DateTimeOrTimeRangeType getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the value of the orderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeOrTimeRangeType }
     *     
     */
    public void setOrderDate(DateTimeOrTimeRangeType value) {
        this.orderDate = value;
    }

    /**
     * Gets the value of the billingInstallments property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBillingInstallments() {
        return billingInstallments;
    }

    /**
     * Sets the value of the billingInstallments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBillingInstallments(Boolean value) {
        this.billingInstallments = value;
    }

    /**
     * Gets the value of the installationFee property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getInstallationFee() {
        return installationFee;
    }

    /**
     * Sets the value of the installationFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setInstallationFee(BigDecimal value) {
        this.installationFee = value;
    }

    /**
     * Gets the value of the appointmentComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppointmentComment() {
        return appointmentComment;
    }

    /**
     * Sets the value of the appointmentComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppointmentComment(String value) {
        this.appointmentComment = value;
    }

    /**
     * Gets the value of the earlierAppointmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEarlierAppointmentDate() {
        return earlierAppointmentDate;
    }

    /**
     * Sets the value of the earlierAppointmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEarlierAppointmentDate(Boolean value) {
        this.earlierAppointmentDate = value;
    }

    /**
     * Gets the value of the residenceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidenceType() {
        return residenceType;
    }

    /**
     * Sets the value of the residenceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidenceType(String value) {
        this.residenceType = value;
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
     *       &lt;attribute name="actualAppointmentDate" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
     *       &lt;attribute name="actualAppointmentStartHour" type="{http://xml.A.com/v4}MilitaryHour" />
     *       &lt;attribute name="actualAppointmentEndHour" type="{http://xml.A.com/v4}MilitaryHour" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ActualSchedule {

        @XmlAttribute(name = "actualAppointmentDate", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar actualAppointmentDate;
        @XmlAttribute(name = "actualAppointmentStartHour")
        protected String actualAppointmentStartHour;
        @XmlAttribute(name = "actualAppointmentEndHour")
        protected String actualAppointmentEndHour;

        /**
         * Gets the value of the actualAppointmentDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getActualAppointmentDate() {
            return actualAppointmentDate;
        }

        /**
         * Sets the value of the actualAppointmentDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setActualAppointmentDate(XMLGregorianCalendar value) {
            this.actualAppointmentDate = value;
        }

        /**
         * Gets the value of the actualAppointmentStartHour property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getActualAppointmentStartHour() {
            return actualAppointmentStartHour;
        }

        /**
         * Sets the value of the actualAppointmentStartHour property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setActualAppointmentStartHour(String value) {
            this.actualAppointmentStartHour = value;
        }

        /**
         * Gets the value of the actualAppointmentEndHour property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getActualAppointmentEndHour() {
            return actualAppointmentEndHour;
        }

        /**
         * Sets the value of the actualAppointmentEndHour property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setActualAppointmentEndHour(String value) {
            this.actualAppointmentEndHour = value;
        }

    }

}
