
package com.A.xml.dtl.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for orderSourceBusinessPartyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orderSourceBusinessPartyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="metadataList" type="{http://xml.A.com/v4}metaDataListType" minOccurs="0"/>
 *         &lt;element name="urlForOrdering" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="phoneNumForOrdering" type="{http://xml.A.com/v4}phoneNumberValueType" minOccurs="0"/>
 *         &lt;element name="phoneNumForCustomerCare" type="{http://xml.A.com/v4}phoneNumberValueType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderSourceBusinessPartyType", propOrder = {
    "externalId",
    "name",
    "metadataList",
    "urlForOrdering",
    "phoneNumForOrdering",
    "phoneNumForCustomerCare"
})
public class OrderSourceBusinessPartyType {

    protected String externalId;
    protected String name;
    protected MetaDataListType metadataList;
    @XmlSchemaType(name = "anyURI")
    protected String urlForOrdering;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String phoneNumForOrdering;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String phoneNumForCustomerCare;

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
     * Gets the value of the metadataList property.
     * 
     * @return
     *     possible object is
     *     {@link MetaDataListType }
     *     
     */
    public MetaDataListType getMetadataList() {
        return metadataList;
    }

    /**
     * Sets the value of the metadataList property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetaDataListType }
     *     
     */
    public void setMetadataList(MetaDataListType value) {
        this.metadataList = value;
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

}
