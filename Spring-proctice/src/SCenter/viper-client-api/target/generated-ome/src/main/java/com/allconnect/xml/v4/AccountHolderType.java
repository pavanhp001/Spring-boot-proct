
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
 * <p>Java class for accountHolderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="accountHolderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="middleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nameSuffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bestContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bestContactPhoneType" type="{http://xml.A.com/common}ContactPhoneType" minOccurs="0"/>
 *         &lt;element name="bestEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dob" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="dtCustomer" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="secondContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondContactPhoneType" type="{http://xml.A.com/common}ContactPhoneType" minOccurs="0"/>
 *         &lt;element name="accountHolderUniqueId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="driversLicense" type="{http://xml.A.com/v4}driverLicenseType" minOccurs="0"/>
 *         &lt;element name="securityVerificationInfo" type="{http://xml.A.com/v4}securityVerificationType" minOccurs="0"/>
 *         &lt;element name="lineItemExternalId" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accountHolderType", propOrder = {
    "externalId",
    "firstName",
    "lastName",
    "middleName",
    "nameSuffix",
    "bestContact",
    "bestContactPhoneType",
    "bestEmail",
    "ssn",
    "dob",
    "dtCustomer",
    "secondContact",
    "secondContactPhoneType",
    "accountHolderUniqueId",
    "driversLicense",
    "securityVerificationInfo",
    "lineItemExternalId"
})
public class AccountHolderType {

    protected long externalId;
    @XmlElement(required = true)
    protected String firstName;
    @XmlElement(required = true)
    protected String lastName;
    protected String middleName;
    protected String nameSuffix;
    protected String bestContact;
    protected ContactPhoneType bestContactPhoneType;
    protected String bestEmail;
    protected String ssn;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dob;
    protected boolean dtCustomer;
    protected String secondContact;
    protected ContactPhoneType secondContactPhoneType;
    protected String accountHolderUniqueId;
    protected DriverLicenseType driversLicense;
    protected SecurityVerificationType securityVerificationInfo;
    @XmlElement(type = Long.class)
    protected List<Long> lineItemExternalId;

    /**
     * Gets the value of the externalId property.
     * 
     */
    public long getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     */
    public void setExternalId(long value) {
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
     * Gets the value of the bestContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBestContact() {
        return bestContact;
    }

    /**
     * Sets the value of the bestContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBestContact(String value) {
        this.bestContact = value;
    }

    /**
     * Gets the value of the bestContactPhoneType property.
     * 
     * @return
     *     possible object is
     *     {@link ContactPhoneType }
     *     
     */
    public ContactPhoneType getBestContactPhoneType() {
        return bestContactPhoneType;
    }

    /**
     * Sets the value of the bestContactPhoneType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactPhoneType }
     *     
     */
    public void setBestContactPhoneType(ContactPhoneType value) {
        this.bestContactPhoneType = value;
    }

    /**
     * Gets the value of the bestEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBestEmail() {
        return bestEmail;
    }

    /**
     * Sets the value of the bestEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBestEmail(String value) {
        this.bestEmail = value;
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
     * Gets the value of the dtCustomer property.
     * 
     */
    public boolean isDtCustomer() {
        return dtCustomer;
    }

    /**
     * Sets the value of the dtCustomer property.
     * 
     */
    public void setDtCustomer(boolean value) {
        this.dtCustomer = value;
    }

    /**
     * Gets the value of the secondContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondContact() {
        return secondContact;
    }

    /**
     * Sets the value of the secondContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondContact(String value) {
        this.secondContact = value;
    }

    /**
     * Gets the value of the secondContactPhoneType property.
     * 
     * @return
     *     possible object is
     *     {@link ContactPhoneType }
     *     
     */
    public ContactPhoneType getSecondContactPhoneType() {
        return secondContactPhoneType;
    }

    /**
     * Sets the value of the secondContactPhoneType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactPhoneType }
     *     
     */
    public void setSecondContactPhoneType(ContactPhoneType value) {
        this.secondContactPhoneType = value;
    }

    /**
     * Gets the value of the accountHolderUniqueId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountHolderUniqueId() {
        return accountHolderUniqueId;
    }

    /**
     * Sets the value of the accountHolderUniqueId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountHolderUniqueId(String value) {
        this.accountHolderUniqueId = value;
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

    /**
     * Gets the value of the lineItemExternalId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineItemExternalId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineItemExternalId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getLineItemExternalId() {
        if (lineItemExternalId == null) {
            lineItemExternalId = new ArrayList<Long>();
        }
        return this.lineItemExternalId;
    }

}
