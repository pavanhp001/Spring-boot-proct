
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for businessPartyDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="businessPartyDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logoImagePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currentAddress" type="{http://xml.A.com/v4}addressType" minOccurs="0"/>
 *         &lt;element name="coBrandName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlForOrdering" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="phoneNumForOrdering" type="{http://xml.A.com/v4}phoneNumberValueType" minOccurs="0"/>
 *         &lt;element name="phoneNumForCustomerCare" type="{http://xml.A.com/v4}phoneNumberValueType" minOccurs="0"/>
 *         &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="referrer" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="descriptiveInfo" type="{http://xml.A.com/v4}descriptiveInfoType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "businessPartyDetailType", propOrder = {
    "externalId",
    "name",
    "parent",
    "logoImagePath",
    "currentAddress",
    "coBrandName",
    "urlForOrdering",
    "phoneNumForOrdering",
    "phoneNumForCustomerCare",
    "provider",
    "referrer",
    "descriptiveInfo"
})
@XmlSeeAlso({
    BusinessPartyDetailResultType.class
})
public class BusinessPartyDetailType {

    @XmlElement(required = true)
    protected String externalId;
    protected String name;
    protected String parent;
    protected String logoImagePath;
    protected AddressType currentAddress;
    protected String coBrandName;
    @XmlSchemaType(name = "anyURI")
    protected String urlForOrdering;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String phoneNumForOrdering;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String phoneNumForCustomerCare;
    protected Boolean provider;
    protected Boolean referrer;
    protected List<DescriptiveInfoType> descriptiveInfo;

    /**
     * Gets the value of the externalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalId(String value) {
        this.externalId = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParent(String value) {
        this.parent = value;
    }

    /**
     * Gets the value of the logoImagePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogoImagePath() {
        return logoImagePath;
    }

    /**
     * Sets the value of the logoImagePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogoImagePath(String value) {
        this.logoImagePath = value;
    }

    /**
     * Gets the value of the currentAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getCurrentAddress() {
        return currentAddress;
    }

    /**
     * Sets the value of the currentAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setCurrentAddress(AddressType value) {
        this.currentAddress = value;
    }

    /**
     * Gets the value of the coBrandName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoBrandName() {
        return coBrandName;
    }

    /**
     * Sets the value of the coBrandName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoBrandName(String value) {
        this.coBrandName = value;
    }

    /**
     * Gets the value of the urlForOrdering property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlForOrdering() {
        return urlForOrdering;
    }

    /**
     * Sets the value of the urlForOrdering property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlForOrdering(String value) {
        this.urlForOrdering = value;
    }

    /**
     * Gets the value of the phoneNumForOrdering property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumForOrdering() {
        return phoneNumForOrdering;
    }

    /**
     * Sets the value of the phoneNumForOrdering property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumForOrdering(String value) {
        this.phoneNumForOrdering = value;
    }

    /**
     * Gets the value of the phoneNumForCustomerCare property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumForCustomerCare() {
        return phoneNumForCustomerCare;
    }

    /**
     * Sets the value of the phoneNumForCustomerCare property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumForCustomerCare(String value) {
        this.phoneNumForCustomerCare = value;
    }

    /**
     * Gets the value of the provider property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProvider(Boolean value) {
        this.provider = value;
    }

    /**
     * Gets the value of the referrer property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReferrer() {
        return referrer;
    }

    /**
     * Sets the value of the referrer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReferrer(Boolean value) {
        this.referrer = value;
    }

    /**
     * Gets the value of the descriptiveInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the descriptiveInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescriptiveInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptiveInfoType }
     * 
     * 
     */
    public List<DescriptiveInfoType> getDescriptiveInfo() {
        if (descriptiveInfo == null) {
            descriptiveInfo = new ArrayList<DescriptiveInfoType>();
        }
        return this.descriptiveInfo;
    }

}
