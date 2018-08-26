
package com.A.xml.cm.v4;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for customerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="customerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="referrerId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="referrerGeneralName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partnerAccountId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtAgentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtCreated" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="agentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="agentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}title" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}firstName"/>
 *         &lt;element ref="{http://xml.A.com/v4}lastName"/>
 *         &lt;element ref="{http://xml.A.com/v4}middleName" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}nameSuffix" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}gender" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}dob" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}ssn" minOccurs="0"/>
 *         &lt;element name="nonBuyerWebOptIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="directMailOptIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="eMailOptIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="phoneContactOptIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="eMailProductUpdatesOptIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="marketingOptIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="offersPresented" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="howLongAtPreviousAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bestTimeToCall1" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="bestTimeToCall2" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="bestTimeToCallPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bestEmailContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bestPhoneContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partnerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partnerSSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contractAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ACustomerNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="homePhoneNumber" type="{http://xml.A.com/v4}phoneNumberType" minOccurs="0"/>
 *         &lt;element name="cellPhoneNumber" type="{http://xml.A.com/v4}phoneNumberType" minOccurs="0"/>
 *         &lt;element name="workPhoneNumber" type="{http://xml.A.com/v4}phoneNumberType" minOccurs="0"/>
 *         &lt;element name="workPhoneNumberExtn" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="homeEMail" type="{http://xml.A.com/v4}eMailAddressType" minOccurs="0"/>
 *         &lt;element name="workEMail" type="{http://xml.A.com/v4}eMailAddressType" minOccurs="0"/>
 *         &lt;element name="region" type="{http://xml.A.com/v4}regionType" minOccurs="0"/>
 *         &lt;element name="landlordInfo" type="{http://xml.A.com/v4}landlordInfoType" minOccurs="0"/>
 *         &lt;element name="driverLicense" type="{http://xml.A.com/v4}driverLicenseType" minOccurs="0"/>
 *         &lt;element name="stateId" type="{http://xml.A.com/v4}stateIdType" minOccurs="0"/>
 *         &lt;element name="primaryLanguage" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="payments" type="{http://xml.A.com/v4}paymentsType" minOccurs="0"/>
 *         &lt;element name="csatSurveys" type="{http://xml.A.com/v4}csatSurveysType" minOccurs="0"/>
 *         &lt;element name="providerCustomerType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="NEW"/>
 *               &lt;enumeration value="EXISTING"/>
 *               &lt;enumeration value="WINBACK"/>
 *               &lt;enumeration value="TRANSFER"/>
 *               &lt;enumeration value="DISCONNECT"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="accounts" type="{http://xml.A.com/v4}accounts" minOccurs="0"/>
 *         &lt;element name="attributes" type="{http://xml.A.com/v4}attributes" minOccurs="0"/>
 *         &lt;element name="financialInfo" type="{http://xml.A.com/v4}customerFinancialInfoType" minOccurs="0"/>
 *         &lt;element name="billingInfoList" type="{http://xml.A.com/v4}billingInfoList"/>
 *         &lt;element name="addressList" type="{http://xml.A.com/v4}addressListType"/>
 *         &lt;element name="securityVerificationInfo" type="{http://xml.A.com/v4}securityVerificationType"/>
 *         &lt;element name="billingDeliveryPreference">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="paper"/>
 *               &lt;enumeration value="electronic"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customerInteractionList" type="{http://xml.A.com/v4}customerInteractionList"/>
 *         &lt;element name="eventNotification" type="{http://xml.A.com/v4}notificationEventType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customerType", propOrder = {
    "externalId",
    "referrerId",
    "referrerGeneralName",
    "partnerAccountId",
    "dtAgentId",
    "dtCreated",
    "agentId",
    "agentName",
    "title",
    "firstName",
    "lastName",
    "middleName",
    "nameSuffix",
    "gender",
    "dob",
    "ssn",
    "nonBuyerWebOptIn",
    "directMailOptIn",
    "eMailOptIn",
    "phoneContactOptIn",
    "eMailProductUpdatesOptIn",
    "marketingOptIn",
    "offersPresented",
    "howLongAtPreviousAddress",
    "bestTimeToCall1",
    "bestTimeToCall2",
    "bestTimeToCallPhone",
    "bestEmailContact",
    "bestPhoneContact",
    "partnerName",
    "partnerSSN",
    "secondPhone",
    "contractAccountNumber",
    "ACustomerNumber",
    "homePhoneNumber",
    "cellPhoneNumber",
    "workPhoneNumber",
    "workPhoneNumberExtn",
    "homeEMail",
    "workEMail",
    "region",
    "landlordInfo",
    "driverLicense",
    "stateId",
    "primaryLanguage",
    "payments",
    "csatSurveys",
    "providerCustomerType",
    "accounts",
    "attributes",
    "financialInfo",
    "billingInfoList",
    "addressList",
    "securityVerificationInfo",
    "billingDeliveryPreference",
    "customerInteractionList",
    "eventNotification"
})
@XmlSeeAlso({
    Customer.class
})
public class CustomerType {

    protected Long externalId;
    protected Long referrerId;
    protected String referrerGeneralName;
    protected String partnerAccountId;
    protected String dtAgentId;
    @XmlElementRef(name = "dtCreated", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> dtCreated;
    @XmlElement(required = true)
    protected String agentId;
    protected String agentName;
    protected String title;
    @XmlElement(required = true)
    protected String firstName;
    @XmlElement(required = true)
    protected String lastName;
    protected String middleName;
    protected String nameSuffix;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String gender;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dob;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String ssn;
    protected boolean nonBuyerWebOptIn;
    protected boolean directMailOptIn;
    protected boolean eMailOptIn;
    protected boolean phoneContactOptIn;
    protected boolean eMailProductUpdatesOptIn;
    protected boolean marketingOptIn;
    @XmlElementRef(name = "offersPresented", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected JAXBElement<String> offersPresented;
    protected String howLongAtPreviousAddress;
    @XmlElementRef(name = "bestTimeToCall1", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> bestTimeToCall1;
    @XmlElementRef(name = "bestTimeToCall2", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> bestTimeToCall2;
    protected String bestTimeToCallPhone;
    protected String bestEmailContact;
    protected String bestPhoneContact;
    protected String partnerName;
    protected String partnerSSN;
    protected String secondPhone;
    protected String contractAccountNumber;
    protected String ACustomerNumber;
    protected PhoneNumberType homePhoneNumber;
    protected PhoneNumberType cellPhoneNumber;
    protected PhoneNumberType workPhoneNumber;
    protected Integer workPhoneNumberExtn;
    protected EMailAddressType homeEMail;
    protected EMailAddressType workEMail;
    protected RegionType region;
    protected LandlordInfoType landlordInfo;
    protected DriverLicenseType driverLicense;
    protected StateIdType stateId;
    @XmlElement(defaultValue = "0")
    protected Integer primaryLanguage;
    protected PaymentsType payments;
    protected CsatSurveysType csatSurveys;
    @XmlElement(required = true)
    protected String providerCustomerType;
    protected Accounts accounts;
    protected Attributes attributes;
    protected CustomerFinancialInfoType financialInfo;
    @XmlElement(required = true)
    protected BillingInfoList billingInfoList;
    @XmlElement(required = true)
    protected AddressListType addressList;
    @XmlElement(required = true)
    protected SecurityVerificationType securityVerificationInfo;
    @XmlElement(required = true)
    protected String billingDeliveryPreference;
    @XmlElement(required = true)
    protected CustomerInteractionList customerInteractionList;
    protected NotificationEventType eventNotification;

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
     * Gets the value of the referrerId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReferrerId() {
        return referrerId;
    }

    /**
     * Sets the value of the referrerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReferrerId(Long value) {
        this.referrerId = value;
    }

    /**
     * Gets the value of the referrerGeneralName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferrerGeneralName() {
        return referrerGeneralName;
    }

    /**
     * Sets the value of the referrerGeneralName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferrerGeneralName(String value) {
        this.referrerGeneralName = value;
    }

    /**
     * Gets the value of the partnerAccountId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerAccountId() {
        return partnerAccountId;
    }

    /**
     * Sets the value of the partnerAccountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerAccountId(String value) {
        this.partnerAccountId = value;
    }

    /**
     * Gets the value of the dtAgentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtAgentId() {
        return dtAgentId;
    }

    /**
     * Sets the value of the dtAgentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtAgentId(String value) {
        this.dtAgentId = value;
    }

    /**
     * Gets the value of the dtCreated property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDtCreated() {
        return dtCreated;
    }

    /**
     * Sets the value of the dtCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDtCreated(JAXBElement<XMLGregorianCalendar> value) {
        this.dtCreated = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the agentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Sets the value of the agentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentId(String value) {
        this.agentId = value;
    }

    /**
     * Gets the value of the agentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * Sets the value of the agentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentName(String value) {
        this.agentName = value;
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
     * Customer's first name.
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
     * Customer's last name
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
     * Customer's middle name
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
     * Customer's geneder.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Customer's date of birth. Valid format for date
     * 						is yyyy-MM-dd. For eg. 1968-10-03
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
     * Gets the value of the nonBuyerWebOptIn property.
     * 
     */
    public boolean isNonBuyerWebOptIn() {
        return nonBuyerWebOptIn;
    }

    /**
     * Sets the value of the nonBuyerWebOptIn property.
     * 
     */
    public void setNonBuyerWebOptIn(boolean value) {
        this.nonBuyerWebOptIn = value;
    }

    /**
     * Gets the value of the directMailOptIn property.
     * 
     */
    public boolean isDirectMailOptIn() {
        return directMailOptIn;
    }

    /**
     * Sets the value of the directMailOptIn property.
     * 
     */
    public void setDirectMailOptIn(boolean value) {
        this.directMailOptIn = value;
    }

    /**
     * Gets the value of the eMailOptIn property.
     * 
     */
    public boolean isEMailOptIn() {
        return eMailOptIn;
    }

    /**
     * Sets the value of the eMailOptIn property.
     * 
     */
    public void setEMailOptIn(boolean value) {
        this.eMailOptIn = value;
    }

    /**
     * Gets the value of the phoneContactOptIn property.
     * 
     */
    public boolean isPhoneContactOptIn() {
        return phoneContactOptIn;
    }

    /**
     * Sets the value of the phoneContactOptIn property.
     * 
     */
    public void setPhoneContactOptIn(boolean value) {
        this.phoneContactOptIn = value;
    }

    /**
     * Gets the value of the eMailProductUpdatesOptIn property.
     * 
     */
    public boolean isEMailProductUpdatesOptIn() {
        return eMailProductUpdatesOptIn;
    }

    /**
     * Sets the value of the eMailProductUpdatesOptIn property.
     * 
     */
    public void setEMailProductUpdatesOptIn(boolean value) {
        this.eMailProductUpdatesOptIn = value;
    }

    /**
     * Gets the value of the marketingOptIn property.
     * 
     */
    public boolean isMarketingOptIn() {
        return marketingOptIn;
    }

    /**
     * Sets the value of the marketingOptIn property.
     * 
     */
    public void setMarketingOptIn(boolean value) {
        this.marketingOptIn = value;
    }

    /**
     * Gets the value of the offersPresented property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOffersPresented() {
        return offersPresented;
    }

    /**
     * Sets the value of the offersPresented property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOffersPresented(JAXBElement<String> value) {
        this.offersPresented = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the howLongAtPreviousAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHowLongAtPreviousAddress() {
        return howLongAtPreviousAddress;
    }

    /**
     * Sets the value of the howLongAtPreviousAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHowLongAtPreviousAddress(String value) {
        this.howLongAtPreviousAddress = value;
    }

    /**
     * Gets the value of the bestTimeToCall1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getBestTimeToCall1() {
        return bestTimeToCall1;
    }

    /**
     * Sets the value of the bestTimeToCall1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setBestTimeToCall1(JAXBElement<XMLGregorianCalendar> value) {
        this.bestTimeToCall1 = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the bestTimeToCall2 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getBestTimeToCall2() {
        return bestTimeToCall2;
    }

    /**
     * Sets the value of the bestTimeToCall2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setBestTimeToCall2(JAXBElement<XMLGregorianCalendar> value) {
        this.bestTimeToCall2 = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the bestTimeToCallPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBestTimeToCallPhone() {
        return bestTimeToCallPhone;
    }

    /**
     * Sets the value of the bestTimeToCallPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBestTimeToCallPhone(String value) {
        this.bestTimeToCallPhone = value;
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
     * Gets the value of the partnerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerName() {
        return partnerName;
    }

    /**
     * Sets the value of the partnerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerName(String value) {
        this.partnerName = value;
    }

    /**
     * Gets the value of the partnerSSN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerSSN() {
        return partnerSSN;
    }

    /**
     * Sets the value of the partnerSSN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerSSN(String value) {
        this.partnerSSN = value;
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
     * Gets the value of the contractAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractAccountNumber() {
        return contractAccountNumber;
    }

    /**
     * Sets the value of the contractAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractAccountNumber(String value) {
        this.contractAccountNumber = value;
    }

    /**
     * Gets the value of the ACustomerNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACustomerNumber() {
        return ACustomerNumber;
    }

    /**
     * Sets the value of the ACustomerNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACustomerNumber(String value) {
        this.ACustomerNumber = value;
    }

    /**
     * Gets the value of the homePhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumberType }
     *     
     */
    public PhoneNumberType getHomePhoneNumber() {
        return homePhoneNumber;
    }

    /**
     * Sets the value of the homePhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumberType }
     *     
     */
    public void setHomePhoneNumber(PhoneNumberType value) {
        this.homePhoneNumber = value;
    }

    /**
     * Gets the value of the cellPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumberType }
     *     
     */
    public PhoneNumberType getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    /**
     * Sets the value of the cellPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumberType }
     *     
     */
    public void setCellPhoneNumber(PhoneNumberType value) {
        this.cellPhoneNumber = value;
    }

    /**
     * Gets the value of the workPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumberType }
     *     
     */
    public PhoneNumberType getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    /**
     * Sets the value of the workPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumberType }
     *     
     */
    public void setWorkPhoneNumber(PhoneNumberType value) {
        this.workPhoneNumber = value;
    }

    /**
     * Gets the value of the workPhoneNumberExtn property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWorkPhoneNumberExtn() {
        return workPhoneNumberExtn;
    }

    /**
     * Sets the value of the workPhoneNumberExtn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWorkPhoneNumberExtn(Integer value) {
        this.workPhoneNumberExtn = value;
    }

    /**
     * Gets the value of the homeEMail property.
     * 
     * @return
     *     possible object is
     *     {@link EMailAddressType }
     *     
     */
    public EMailAddressType getHomeEMail() {
        return homeEMail;
    }

    /**
     * Sets the value of the homeEMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link EMailAddressType }
     *     
     */
    public void setHomeEMail(EMailAddressType value) {
        this.homeEMail = value;
    }

    /**
     * Gets the value of the workEMail property.
     * 
     * @return
     *     possible object is
     *     {@link EMailAddressType }
     *     
     */
    public EMailAddressType getWorkEMail() {
        return workEMail;
    }

    /**
     * Sets the value of the workEMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link EMailAddressType }
     *     
     */
    public void setWorkEMail(EMailAddressType value) {
        this.workEMail = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link RegionType }
     *     
     */
    public RegionType getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegionType }
     *     
     */
    public void setRegion(RegionType value) {
        this.region = value;
    }

    /**
     * Gets the value of the landlordInfo property.
     * 
     * @return
     *     possible object is
     *     {@link LandlordInfoType }
     *     
     */
    public LandlordInfoType getLandlordInfo() {
        return landlordInfo;
    }

    /**
     * Sets the value of the landlordInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link LandlordInfoType }
     *     
     */
    public void setLandlordInfo(LandlordInfoType value) {
        this.landlordInfo = value;
    }

    /**
     * Gets the value of the driverLicense property.
     * 
     * @return
     *     possible object is
     *     {@link DriverLicenseType }
     *     
     */
    public DriverLicenseType getDriverLicense() {
        return driverLicense;
    }

    /**
     * Sets the value of the driverLicense property.
     * 
     * @param value
     *     allowed object is
     *     {@link DriverLicenseType }
     *     
     */
    public void setDriverLicense(DriverLicenseType value) {
        this.driverLicense = value;
    }

    /**
     * Gets the value of the stateId property.
     * 
     * @return
     *     possible object is
     *     {@link StateIdType }
     *     
     */
    public StateIdType getStateId() {
        return stateId;
    }

    /**
     * Sets the value of the stateId property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateIdType }
     *     
     */
    public void setStateId(StateIdType value) {
        this.stateId = value;
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
     * Gets the value of the payments property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentsType }
     *     
     */
    public PaymentsType getPayments() {
        return payments;
    }

    /**
     * Sets the value of the payments property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentsType }
     *     
     */
    public void setPayments(PaymentsType value) {
        this.payments = value;
    }

    /**
     * Gets the value of the csatSurveys property.
     * 
     * @return
     *     possible object is
     *     {@link CsatSurveysType }
     *     
     */
    public CsatSurveysType getCsatSurveys() {
        return csatSurveys;
    }

    /**
     * Sets the value of the csatSurveys property.
     * 
     * @param value
     *     allowed object is
     *     {@link CsatSurveysType }
     *     
     */
    public void setCsatSurveys(CsatSurveysType value) {
        this.csatSurveys = value;
    }

    /**
     * Gets the value of the providerCustomerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderCustomerType() {
        return providerCustomerType;
    }

    /**
     * Sets the value of the providerCustomerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderCustomerType(String value) {
        this.providerCustomerType = value;
    }

    /**
     * Gets the value of the accounts property.
     * 
     * @return
     *     possible object is
     *     {@link Accounts }
     *     
     */
    public Accounts getAccounts() {
        return accounts;
    }

    /**
     * Sets the value of the accounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Accounts }
     *     
     */
    public void setAccounts(Accounts value) {
        this.accounts = value;
    }

    /**
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link Attributes }
     *     
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Attributes }
     *     
     */
    public void setAttributes(Attributes value) {
        this.attributes = value;
    }

    /**
     * Gets the value of the financialInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerFinancialInfoType }
     *     
     */
    public CustomerFinancialInfoType getFinancialInfo() {
        return financialInfo;
    }

    /**
     * Sets the value of the financialInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerFinancialInfoType }
     *     
     */
    public void setFinancialInfo(CustomerFinancialInfoType value) {
        this.financialInfo = value;
    }

    /**
     * Gets the value of the billingInfoList property.
     * 
     * @return
     *     possible object is
     *     {@link BillingInfoList }
     *     
     */
    public BillingInfoList getBillingInfoList() {
        return billingInfoList;
    }

    /**
     * Sets the value of the billingInfoList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingInfoList }
     *     
     */
    public void setBillingInfoList(BillingInfoList value) {
        this.billingInfoList = value;
    }

    /**
     * Gets the value of the addressList property.
     * 
     * @return
     *     possible object is
     *     {@link AddressListType }
     *     
     */
    public AddressListType getAddressList() {
        return addressList;
    }

    /**
     * Sets the value of the addressList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressListType }
     *     
     */
    public void setAddressList(AddressListType value) {
        this.addressList = value;
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
     * Gets the value of the billingDeliveryPreference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingDeliveryPreference() {
        return billingDeliveryPreference;
    }

    /**
     * Sets the value of the billingDeliveryPreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingDeliveryPreference(String value) {
        this.billingDeliveryPreference = value;
    }

    /**
     * Gets the value of the customerInteractionList property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerInteractionList }
     *     
     */
    public CustomerInteractionList getCustomerInteractionList() {
        return customerInteractionList;
    }

    /**
     * Sets the value of the customerInteractionList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerInteractionList }
     *     
     */
    public void setCustomerInteractionList(CustomerInteractionList value) {
        this.customerInteractionList = value;
    }

    /**
     * Gets the value of the eventNotification property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationEventType }
     *     
     */
    public NotificationEventType getEventNotification() {
        return eventNotification;
    }

    /**
     * Sets the value of the eventNotification property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationEventType }
     *     
     */
    public void setEventNotification(NotificationEventType value) {
        this.eventNotification = value;
    }

}
