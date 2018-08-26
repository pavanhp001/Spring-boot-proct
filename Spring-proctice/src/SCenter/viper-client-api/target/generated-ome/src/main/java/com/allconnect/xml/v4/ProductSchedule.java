
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductSchedule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductSchedule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Scheduling" type="{http://xml.A.com/v4}ScheduleDateType" minOccurs="0"/>
 *         &lt;element name="Appointments" type="{http://xml.A.com/v4}AvailableAppointmentType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="productType" type="{http://xml.A.com/v4}ProductCategory" />
 *       &lt;attribute name="lineItem" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="installationDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="activationDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="disconnectDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="attOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="TnOrFtn" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductSchedule", propOrder = {
    "scheduling",
    "appointments"
})
public class ProductSchedule {

    @XmlElement(name = "Scheduling")
    protected ScheduleDateType scheduling;
    @XmlElement(name = "Appointments")
    protected AvailableAppointmentType appointments;
    @XmlAttribute(name = "productType")
    protected ProductCategory productType;
    @XmlAttribute(name = "lineItem")
    protected String lineItem;
    @XmlAttribute(name = "installationDate")
    protected String installationDate;
    @XmlAttribute(name = "activationDate")
    protected String activationDate;
    @XmlAttribute(name = "disconnectDate")
    protected String disconnectDate;
    @XmlAttribute(name = "attOrderNumber")
    protected String attOrderNumber;
    @XmlAttribute(name = "subOrderNumber")
    protected String subOrderNumber;
    @XmlAttribute(name = "TnOrFtn")
    protected String tnOrFtn;

    /**
     * Gets the value of the scheduling property.
     * 
     * @return
     *     possible object is
     *     {@link ScheduleDateType }
     *     
     */
    public ScheduleDateType getScheduling() {
        return scheduling;
    }

    /**
     * Sets the value of the scheduling property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleDateType }
     *     
     */
    public void setScheduling(ScheduleDateType value) {
        this.scheduling = value;
    }

    /**
     * Gets the value of the appointments property.
     * 
     * @return
     *     possible object is
     *     {@link AvailableAppointmentType }
     *     
     */
    public AvailableAppointmentType getAppointments() {
        return appointments;
    }

    /**
     * Sets the value of the appointments property.
     * 
     * @param value
     *     allowed object is
     *     {@link AvailableAppointmentType }
     *     
     */
    public void setAppointments(AvailableAppointmentType value) {
        this.appointments = value;
    }

    /**
     * Gets the value of the productType property.
     * 
     * @return
     *     possible object is
     *     {@link ProductCategory }
     *     
     */
    public ProductCategory getProductType() {
        return productType;
    }

    /**
     * Sets the value of the productType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductCategory }
     *     
     */
    public void setProductType(ProductCategory value) {
        this.productType = value;
    }

    /**
     * Gets the value of the lineItem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineItem() {
        return lineItem;
    }

    /**
     * Sets the value of the lineItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineItem(String value) {
        this.lineItem = value;
    }

    /**
     * Gets the value of the installationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstallationDate() {
        return installationDate;
    }

    /**
     * Sets the value of the installationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstallationDate(String value) {
        this.installationDate = value;
    }

    /**
     * Gets the value of the activationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivationDate() {
        return activationDate;
    }

    /**
     * Sets the value of the activationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivationDate(String value) {
        this.activationDate = value;
    }

    /**
     * Gets the value of the disconnectDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisconnectDate() {
        return disconnectDate;
    }

    /**
     * Sets the value of the disconnectDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisconnectDate(String value) {
        this.disconnectDate = value;
    }

    /**
     * Gets the value of the attOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttOrderNumber() {
        return attOrderNumber;
    }

    /**
     * Sets the value of the attOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttOrderNumber(String value) {
        this.attOrderNumber = value;
    }

    /**
     * Gets the value of the subOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubOrderNumber() {
        return subOrderNumber;
    }

    /**
     * Sets the value of the subOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubOrderNumber(String value) {
        this.subOrderNumber = value;
    }

    /**
     * Gets the value of the tnOrFtn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTnOrFtn() {
        return tnOrFtn;
    }

    /**
     * Sets the value of the tnOrFtn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTnOrFtn(String value) {
        this.tnOrFtn = value;
    }

}
