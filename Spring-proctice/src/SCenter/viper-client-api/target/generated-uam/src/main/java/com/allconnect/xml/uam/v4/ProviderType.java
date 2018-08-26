
package com.A.xml.uam.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines a provider record for response from
 * 				Serviceability API (using an address as input)
 * 				and as an input into
 * 				Product APIs
 * 
 * <p>Java class for providerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="providerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="source" type="{http://xml.A.com/v4}providerSourceType" minOccurs="0"/>
 *         &lt;element name="parent" type="{http://xml.A.com/v4}providerType" minOccurs="0"/>
 *         &lt;element name="capabilityList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="capability" type="{http://xml.A.com/v4}capabilityType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="externalId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="offerType" type="{http://xml.A.com/v4}offerTypeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "providerType", propOrder = {
    "source",
    "parent",
    "capabilityList"
})
public class ProviderType {

    protected ProviderSourceType source;
    protected ProviderType parent;
    protected ProviderType.CapabilityList capabilityList;
    @XmlAttribute(name = "externalId", required = true)
    protected String externalId;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "offerType")
    protected OfferTypeType offerType;

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link ProviderSourceType }
     *     
     */
    public ProviderSourceType getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderSourceType }
     *     
     */
    public void setSource(ProviderSourceType value) {
        this.source = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link ProviderType }
     *     
     */
    public ProviderType getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderType }
     *     
     */
    public void setParent(ProviderType value) {
        this.parent = value;
    }

    /**
     * Gets the value of the capabilityList property.
     * 
     * @return
     *     possible object is
     *     {@link ProviderType.CapabilityList }
     *     
     */
    public ProviderType.CapabilityList getCapabilityList() {
        return capabilityList;
    }

    /**
     * Sets the value of the capabilityList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderType.CapabilityList }
     *     
     */
    public void setCapabilityList(ProviderType.CapabilityList value) {
        this.capabilityList = value;
    }

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
     * Gets the value of the offerType property.
     * 
     * @return
     *     possible object is
     *     {@link OfferTypeType }
     *     
     */
    public OfferTypeType getOfferType() {
        return offerType;
    }

    /**
     * Sets the value of the offerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfferTypeType }
     *     
     */
    public void setOfferType(OfferTypeType value) {
        this.offerType = value;
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
     *       &lt;sequence>
     *         &lt;element name="capability" type="{http://xml.A.com/v4}capabilityType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "capability"
    })
    public static class CapabilityList {

        protected List<CapabilityType> capability;

        /**
         * Gets the value of the capability property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the capability property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCapability().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CapabilityType }
         * 
         * 
         */
        public List<CapabilityType> getCapability() {
            if (capability == null) {
                capability = new ArrayList<CapabilityType>();
            }
            return this.capability;
        }

    }

}
