
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
 * <p>Java class for OpCustomerInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OpCustomerInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="middleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nameSuffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bestPhoneContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bestEmailContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primaryEmailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondaryEmailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dob" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="cellPhoneNumber" type="{http://xml.A.com/v4/OrderProvisioning/}opPhoneNumber" minOccurs="0"/>
 *         &lt;element name="homePhoneNumber" type="{http://xml.A.com/v4/OrderProvisioning/}opPhoneNumber" minOccurs="0"/>
 *         &lt;element name="workPhoneNumber" type="{http://xml.A.com/v4/OrderProvisioning/}opPhoneNumber" minOccurs="0"/>
 *         &lt;element name="driversLicense" type="{http://xml.A.com/v4}driverLicenseType" minOccurs="0"/>
 *         &lt;element name="address" type="{http://xml.A.com/v4}custAddress" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="billingInfo" type="{http://xml.A.com/v4}custBillingInfoType" minOccurs="0"/>
 *         &lt;element name="primaryLanguage" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="securityVerificationInfo" type="{http://xml.A.com/v4}securityVerificationType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpCustomerInfo", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "externalId",
    "firstName",
    "lastName",
    "middleName",
    "nameSuffix",
    "title",
    "bestPhoneContact",
    "secondPhone",
    "ssn",
    "bestEmailContact",
    "primaryEmailAddress",
    "secondaryEmailAddress",
    "dob",
    "cellPhoneNumber",
    "homePhoneNumber",
    "workPhoneNumber",
    "driversLicense",
    "address",
    "billingInfo",
    "primaryLanguage",
    "securityVerificationInfo"
})
public class OpCustomerInfo {

    @XmlElement(namespace = "")
    protected Long externalId;
    @XmlElement(namespace = "")
    protected String firstName;
    @XmlElement(namespace = "")
    protected String lastName;
    @XmlElement(namespace = "")
    protected String middleName;
    @XmlElement(namespace = "")
    protected String nameSuffix;
    @XmlElement(namespace = "")
    protected String title;
    @XmlElement(namespace = "")
    protected String bestPhoneContact;
    @XmlElement(namespace = "")
    protected String secondPhone;
    @XmlElement(namespace = "")
    protected String ssn;
    @XmlElement(namespace = "")
    protected String bestEmailContact;
    @XmlElement(namespace = "")
    protected String primaryEmailAddress;
    @XmlElement(namespace = "")
    protected String secondaryEmailAddress;
    @XmlElement(namespace = "")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dob;
    @XmlElement(namespace = "")
    protected String cellPhoneNumber;
    @XmlElement(namespace = "")
    protected String homePhoneNumber;
    @XmlElement(namespace = "")
    protected String workPhoneNumber;
    @XmlElement(namespace = "")
    protected DriverLicenseType driversLicense;
    @XmlElement(namespace = "")
    protected List<CustAddress> address;
    @XmlElement(namespace = "")
    protected CustBillingInfoType billingInfo;
    @XmlElement(namespace = "", defaultValue = "0")
    protected Integer primaryLanguage;
    @XmlElement(namespace = "")
    protected SecurityVerificationType securityVerificationInfo;

    /**
     * Gets the value of the externalId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setExternalId(Long value) {
        this.externalId = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the nameSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameSuffix() {
        return nameSuffix;
    }

    /**
     * Sets the value of the nameSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameSuffix(String value) {
        this.nameSuffix = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the bestPhoneContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBestPhoneContact() {
        return bestPhoneContact;
    }

    /**
     * Sets the value of the bestPhoneContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBestPhoneContact(String value) {
        this.bestPhoneContact = value;
    }

    /**
     * Gets the value of the secondPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondPhone() {
        return secondPhone;
    }

    /**
     * Sets the value of the secondPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondPhone(String value) {
        this.secondPhone = value;
    }

    /**
     * Gets the value of the ssn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Sets the value of the ssn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSsn(String value) {
        this.ssn = value;
    }

    /**
     * Gets the value of the bestEmailContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBestEmailContact() {
        return bestEmailContact;
    }

    /**
     * Sets the value of the bestEmailContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBestEmailContact(String value) {
        this.bestEmailContact = value;
    }

    /**
     * Gets the value of the primaryEmailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryEmailAddress() {
        return primaryEmailAddress;
    }

    /**
     * Sets the value of the primaryEmailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryEmailAddress(String value) {
        this.primaryEmailAddress = value;
    }

    /**
     * Gets the value of the secondaryEmailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondaryEmailAddress() {
        return secondaryEmailAddress;
    }

    /**
     * Sets the value of the secondaryEmailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondaryEmailAddress(String value) {
        this.secondaryEmailAddress = value;
    }

    /**
     * Gets the value of the dob property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDob() {
        return dob;
    }

    /**
     * Sets the value of the dob property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDob(XMLGregorianCalendar value) {
        this.dob = value;
    }

    /**
     * Gets the value of the cellPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    /**
     * Sets the value of the cellPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellPhoneNumber(String value) {
        this.cellPhoneNumber = value;
    }

    /**
     * Gets the value of the homePhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    /**
     * Sets the value of the homePhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHomePhoneNumber(String value) {
        this.homePhoneNumber = value;
    }

    /**
     * Gets the value of the workPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    /**
     * Sets the value of the workPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkPhoneNumber(String value) {
        this.workPhoneNumber = value;
    }

    /**
     * Gets the value of the driversLicense property.
     * 
     * @return
     *     possible object is
     *     {@link DriverLicenseType }
     *     
     */
    public DriverLicenseType getDriversLicense() {
        return driversLicense;
    }

    /**
     * Sets the value of the driversLicense property.
     * 
     * @param value
     *     allowed object is
     *     {@link DriverLicenseType }
     *     
     */
    public void setDriversLicense(DriverLicenseType value) {
        this.driversLicense = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the address property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustAddress }
     * 
     * 
     */
    public List<CustAddress> getAddress() {
        if (address == null) {
            address = new ArrayList<CustAddress>();
        }
        return this.address;
    }

    /**
     * Gets the value of the billingInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustBillingInfoType }
     *     
     */
    public CustBillingInfoType getBillingInfo() {
        return billingInfo;
    }

    /**
     * Sets the value of the billingInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustBillingInfoType }
     *     
     */
    public void setBillingInfo(CustBillingInfoType value) {
        this.billingInfo = value;
    }

    /**
     * Gets the value of the primaryLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPrimaryLanguage() {
        return primaryLanguage;
    }

    /**
     * Sets the value of the primaryLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPrimaryLanguage(Integer value) {
        this.primaryLanguage = value;
    }

    /**
     * Gets the value of the securityVerificationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityVerificationType }
     *     
     */
    public SecurityVerificationType getSecurityVerificationInfo() {
        return securityVerificationInfo;
    }

    /**
     * Sets the value of the securityVerificationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityVerificationType }
     *     
     */
    public void setSecurityVerificationInfo(SecurityVerificationType value) {
        this.securityVerificationInfo = value;
    }

}
