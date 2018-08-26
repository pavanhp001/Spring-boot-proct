
package com.A.xml.dtl.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for featureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="featureType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice>
 *           &lt;element ref="{http://xml.A.com/v4}price"/>
 *           &lt;element ref="{http://xml.A.com/v4}priceTierList"/>
 *         &lt;/choice>
 *         &lt;element name="included" type="{http://xml.A.com/v4}emptyElementType" minOccurs="0"/>
 *         &lt;element name="required" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="available" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="doNotDisplay" type="{http://xml.A.com/v4}emptyElementType" minOccurs="0"/>
 *         &lt;element name="dataConstraints" type="{http://xml.A.com/v4}dataConstraintType" minOccurs="0"/>
 *         &lt;element name="capabilities" type="{http://xml.A.com/v4}featureCapabilityListType" minOccurs="0"/>
 *         &lt;element name="dependencies" type="{http://xml.A.com/v4}featureDependencyListType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tags" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "featureType", propOrder = {
    "externalId",
    "price",
    "priceTierList",
    "included",
    "required",
    "available",
    "doNotDisplay",
    "dataConstraints",
    "capabilities",
    "dependencies"
})
public class FeatureType {

    @XmlElement(required = true)
    protected String externalId;
    protected PriceInfoType price;
    protected PriceTierListType priceTierList;
    protected EmptyElementType included;
    protected boolean required;
    protected Boolean available;
    protected EmptyElementType doNotDisplay;
    protected DataConstraintType dataConstraints;
    protected FeatureCapabilityListType capabilities;
    protected FeatureDependencyListType dependencies;
    @XmlAttribute(name = "type", required = true)
    protected String type;
    @XmlAttribute(name = "subType")
    protected String subType;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "tags")
    protected String tags;

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
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link PriceInfoType }
     *     
     */
    public PriceInfoType getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceInfoType }
     *     
     */
    public void setPrice(PriceInfoType value) {
        this.price = value;
    }

    /**
     * Gets the value of the priceTierList property.
     * 
     * @return
     *     possible object is
     *     {@link PriceTierListType }
     *     
     */
    public PriceTierListType getPriceTierList() {
        return priceTierList;
    }

    /**
     * Sets the value of the priceTierList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceTierListType }
     *     
     */
    public void setPriceTierList(PriceTierListType value) {
        this.priceTierList = value;
    }

    /**
     * Gets the value of the included property.
     * 
     * @return
     *     possible object is
     *     {@link EmptyElementType }
     *     
     */
    public EmptyElementType getIncluded() {
        return included;
    }

    /**
     * Sets the value of the included property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmptyElementType }
     *     
     */
    public void setIncluded(EmptyElementType value) {
        this.included = value;
    }

    /**
     * Gets the value of the required property.
     * 
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets the value of the required property.
     * 
     */
    public void setRequired(boolean value) {
        this.required = value;
    }

    /**
     * Gets the value of the available property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAvailable() {
        return available;
    }

    /**
     * Sets the value of the available property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAvailable(Boolean value) {
        this.available = value;
    }

    /**
     * Gets the value of the doNotDisplay property.
     * 
     * @return
     *     possible object is
     *     {@link EmptyElementType }
     *     
     */
    public EmptyElementType getDoNotDisplay() {
        return doNotDisplay;
    }

    /**
     * Sets the value of the doNotDisplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmptyElementType }
     *     
     */
    public void setDoNotDisplay(EmptyElementType value) {
        this.doNotDisplay = value;
    }

    /**
     * Gets the value of the dataConstraints property.
     * 
     * @return
     *     possible object is
     *     {@link DataConstraintType }
     *     
     */
    public DataConstraintType getDataConstraints() {
        return dataConstraints;
    }

    /**
     * Sets the value of the dataConstraints property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataConstraintType }
     *     
     */
    public void setDataConstraints(DataConstraintType value) {
        this.dataConstraints = value;
    }

    /**
     * Gets the value of the capabilities property.
     * 
     * @return
     *     possible object is
     *     {@link FeatureCapabilityListType }
     *     
     */
    public FeatureCapabilityListType getCapabilities() {
        return capabilities;
    }

    /**
     * Sets the value of the capabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link FeatureCapabilityListType }
     *     
     */
    public void setCapabilities(FeatureCapabilityListType value) {
        this.capabilities = value;
    }

    /**
     * Gets the value of the dependencies property.
     * 
     * @return
     *     possible object is
     *     {@link FeatureDependencyListType }
     *     
     */
    public FeatureDependencyListType getDependencies() {
        return dependencies;
    }

    /**
     * Sets the value of the dependencies property.
     * 
     * @param value
     *     allowed object is
     *     {@link FeatureDependencyListType }
     *     
     */
    public void setDependencies(FeatureDependencyListType value) {
        this.dependencies = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the subType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubType() {
        return subType;
    }

    /**
     * Sets the value of the subType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubType(String value) {
        this.subType = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the tags property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTags() {
        return tags;
    }

    /**
     * Sets the value of the tags property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTags(String value) {
        this.tags = value;
    }

}
