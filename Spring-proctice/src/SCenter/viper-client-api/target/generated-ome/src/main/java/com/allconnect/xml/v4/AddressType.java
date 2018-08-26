
package com.A.xml.v4;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for addressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="prefixDirectional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="streetNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="streetName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="streetType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postfixDirectional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="stateOrProvince" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countyParishBorough" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addressBlock" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="howLongAtAddress" type="{http://www.w3.org/2001/XMLSchema}duration" minOccurs="0"/>
 *         &lt;element name="dwellingType" type="{http://xml.A.com/v4}dwellingType" minOccurs="0"/>
 *         &lt;element name="inEffect" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="gasStartAt" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="electricityStartAt" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="expiration" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="changeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hasHadServicePreviously" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="cityAlias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="providerExternalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addressOwnership" type="{http://xml.A.com/v4}ownershipType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addressType", propOrder = {
    "externalId",
    "prefixDirectional",
    "streetNumber",
    "streetName",
    "streetType",
    "line2",
    "postfixDirectional",
    "city",
    "stateOrProvince",
    "postalCode",
    "country",
    "countyParishBorough",
    "addressBlock",
    "howLongAtAddress",
    "dwellingType",
    "inEffect",
    "gasStartAt",
    "electricityStartAt",
    "expiration",
    "changeType",
    "hasHadServicePreviously",
    "cityAlias",
    "providerExternalId",
    "addressOwnership"
})
@XmlSeeAlso({
    MailingAddressType.class
})
public class AddressType {

    protected long externalId;
    protected String prefixDirectional;
    @XmlElement(required = true)
    protected String streetNumber;
    @XmlElement(required = true)
    protected String streetName;
    protected String streetType;
    protected String line2;
    protected String postfixDirectional;
    @XmlElement(required = true)
    protected String city;
    @XmlElement(required = true)
    protected String stateOrProvince;
    @XmlElement(required = true)
    protected String postalCode;
    protected String country;
    protected String countyParishBorough;
    protected String addressBlock;
    protected Duration howLongAtAddress;
    protected DwellingType dwellingType;
    @XmlElementRef(name = "inEffect", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> inEffect;
    @XmlElementRef(name = "gasStartAt", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> gasStartAt;
    @XmlElementRef(name = "electricityStartAt", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> electricityStartAt;
    @XmlElementRef(name = "expiration", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> expiration;
    protected String changeType;
    protected Boolean hasHadServicePreviously;
    protected String cityAlias;
    protected String providerExternalId;
    @XmlElement(required = true)
    protected OwnershipType addressOwnership;

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
     * Gets the value of the prefixDirectional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefixDirectional() {
        return prefixDirectional;
    }

    /**
     * Sets the value of the prefixDirectional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefixDirectional(String value) {
        this.prefixDirectional = value;
    }

    /**
     * Gets the value of the streetNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Sets the value of the streetNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetNumber(String value) {
        this.streetNumber = value;
    }

    /**
     * Gets the value of the streetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the value of the streetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetName(String value) {
        this.streetName = value;
    }

    /**
     * Gets the value of the streetType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetType() {
        return streetType;
    }

    /**
     * Sets the value of the streetType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetType(String value) {
        this.streetType = value;
    }

    /**
     * Gets the value of the line2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLine2() {
        return line2;
    }

    /**
     * Sets the value of the line2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLine2(String value) {
        this.line2 = value;
    }

    /**
     * Gets the value of the postfixDirectional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostfixDirectional() {
        return postfixDirectional;
    }

    /**
     * Sets the value of the postfixDirectional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostfixDirectional(String value) {
        this.postfixDirectional = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the stateOrProvince property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateOrProvince() {
        return stateOrProvince;
    }

    /**
     * Sets the value of the stateOrProvince property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateOrProvince(String value) {
        this.stateOrProvince = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the countyParishBorough property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountyParishBorough() {
        return countyParishBorough;
    }

    /**
     * Sets the value of the countyParishBorough property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountyParishBorough(String value) {
        this.countyParishBorough = value;
    }

    /**
     * Gets the value of the addressBlock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressBlock() {
        return addressBlock;
    }

    /**
     * Sets the value of the addressBlock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressBlock(String value) {
        this.addressBlock = value;
    }

    /**
     * Gets the value of the howLongAtAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getHowLongAtAddress() {
        return howLongAtAddress;
    }

    /**
     * Sets the value of the howLongAtAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setHowLongAtAddress(Duration value) {
        this.howLongAtAddress = value;
    }

    /**
     * Gets the value of the dwellingType property.
     * 
     * @return
     *     possible object is
     *     {@link DwellingType }
     *     
     */
    public DwellingType getDwellingType() {
        return dwellingType;
    }

    /**
     * Sets the value of the dwellingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DwellingType }
     *     
     */
    public void setDwellingType(DwellingType value) {
        this.dwellingType = value;
    }

    /**
     * Gets the value of the inEffect property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getInEffect() {
        return inEffect;
    }

    /**
     * Sets the value of the inEffect property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setInEffect(JAXBElement<XMLGregorianCalendar> value) {
        this.inEffect = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the gasStartAt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getGasStartAt() {
        return gasStartAt;
    }

    /**
     * Sets the value of the gasStartAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setGasStartAt(JAXBElement<XMLGregorianCalendar> value) {
        this.gasStartAt = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the electricityStartAt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getElectricityStartAt() {
        return electricityStartAt;
    }

    /**
     * Sets the value of the electricityStartAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setElectricityStartAt(JAXBElement<XMLGregorianCalendar> value) {
        this.electricityStartAt = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the expiration property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getExpiration() {
        return expiration;
    }

    /**
     * Sets the value of the expiration property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setExpiration(JAXBElement<XMLGregorianCalendar> value) {
        this.expiration = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the changeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangeType() {
        return changeType;
    }

    /**
     * Sets the value of the changeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangeType(String value) {
        this.changeType = value;
    }

    /**
     * Gets the value of the hasHadServicePreviously property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHasHadServicePreviously() {
        return hasHadServicePreviously;
    }

    /**
     * Sets the value of the hasHadServicePreviously property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHasHadServicePreviously(Boolean value) {
        this.hasHadServicePreviously = value;
    }

    /**
     * Gets the value of the cityAlias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityAlias() {
        return cityAlias;
    }

    /**
     * Sets the value of the cityAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityAlias(String value) {
        this.cityAlias = value;
    }

    /**
     * Gets the value of the providerExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderExternalId() {
        return providerExternalId;
    }

    /**
     * Sets the value of the providerExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderExternalId(String value) {
        this.providerExternalId = value;
    }

    /**
     * Gets the value of the addressOwnership property.
     * 
     * @return
     *     possible object is
     *     {@link OwnershipType }
     *     
     */
    public OwnershipType getAddressOwnership() {
        return addressOwnership;
    }

    /**
     * Sets the value of the addressOwnership property.
     * 
     * @param value
     *     allowed object is
     *     {@link OwnershipType }
     *     
     */
    public void setAddressOwnership(OwnershipType value) {
        this.addressOwnership = value;
    }

}
