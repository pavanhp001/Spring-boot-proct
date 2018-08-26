
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataFieldType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataFieldType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="featureExternalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="required" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tags" type="{http://xml.A.com/v4}tagListType" minOccurs="0"/>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contentRefId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valueTarget" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataConstraints" type="{http://xml.A.com/v4}dataConstraintType" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="infoType" type="{http://xml.A.com/v4}infoType" minOccurs="0"/>
 *         &lt;element name="validation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" use="required" type="{http://xml.A.com/v4}dataFieldTypeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataFieldType", propOrder = {
    "externalId",
    "featureExternalId",
    "displayGroup",
    "enabled",
    "required",
    "tags",
    "text",
    "contentRefId",
    "valueTarget",
    "dataConstraints",
    "description",
    "infoType",
    "validation"
})
public class DataFieldType {

    @XmlElement(required = true)
    protected String externalId;
    protected String featureExternalId;
    protected String displayGroup;
    protected boolean enabled;
    protected Boolean required;
    protected TagListType tags;
    protected String text;
    protected String contentRefId;
    protected String valueTarget;
    protected DataConstraintType dataConstraints;
    protected String description;
    protected InfoType infoType;
    protected String validation;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "type", required = true)
    protected DataFieldTypeType type;

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
     * Gets the value of the featureExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeatureExternalId() {
        return featureExternalId;
    }

    /**
     * Sets the value of the featureExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeatureExternalId(String value) {
        this.featureExternalId = value;
    }

    /**
     * Gets the value of the displayGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayGroup() {
        return displayGroup;
    }

    /**
     * Sets the value of the displayGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayGroup(String value) {
        this.displayGroup = value;
    }

    /**
     * Gets the value of the enabled property.
     * 
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     */
    public void setEnabled(boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the required property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRequired() {
        return required;
    }

    /**
     * Sets the value of the required property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRequired(Boolean value) {
        this.required = value;
    }

    /**
     * Gets the value of the tags property.
     * 
     * @return
     *     possible object is
     *     {@link TagListType }
     *     
     */
    public TagListType getTags() {
        return tags;
    }

    /**
     * Sets the value of the tags property.
     * 
     * @param value
     *     allowed object is
     *     {@link TagListType }
     *     
     */
    public void setTags(TagListType value) {
        this.tags = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Gets the value of the contentRefId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentRefId() {
        return contentRefId;
    }

    /**
     * Sets the value of the contentRefId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentRefId(String value) {
        this.contentRefId = value;
    }

    /**
     * Gets the value of the valueTarget property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueTarget() {
        return valueTarget;
    }

    /**
     * Sets the value of the valueTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueTarget(String value) {
        this.valueTarget = value;
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
     * Gets the value of the infoType property.
     * 
     * @return
     *     possible object is
     *     {@link InfoType }
     *     
     */
    public InfoType getInfoType() {
        return infoType;
    }

    /**
     * Sets the value of the infoType property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoType }
     *     
     */
    public void setInfoType(InfoType value) {
        this.infoType = value;
    }

    /**
     * Gets the value of the validation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidation() {
        return validation;
    }

    /**
     * Sets the value of the validation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidation(String value) {
        this.validation = value;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link DataFieldTypeType }
     *     
     */
    public DataFieldTypeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataFieldTypeType }
     *     
     */
    public void setType(DataFieldTypeType value) {
        this.type = value;
    }

}
