
package com.A.xml.se.v4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for featureGroupType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="featureGroupType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{http://xml.A.com/v4}price" minOccurs="0"/>
 *         &lt;element name="included" type="{http://xml.A.com/v4}emptyElementType" minOccurs="0"/>
 *         &lt;element name="required" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="available" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="doNotDisplay" type="{http://xml.A.com/v4}emptyElementType" minOccurs="0"/>
 *         &lt;element name="dataConstraints" type="{http://xml.A.com/v4}dataConstraintType" minOccurs="0"/>
 *         &lt;element name="capabilities" type="{http://xml.A.com/v4}featureCapabilityListType" minOccurs="0"/>
 *         &lt;element name="dependencies" type="{http://xml.A.com/v4}featureDependencyListType" minOccurs="0"/>
 *         &lt;element name="feature" type="{http://xml.A.com/v4}featureType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="selectionType">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="pickAll" type="{http://xml.A.com/v4}emptyElementType"/>
 *                   &lt;element name="pickOne" type="{http://xml.A.com/v4}emptyElementType"/>
 *                   &lt;element name="pickUpToN" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *                   &lt;element name="pickN" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "featureGroupType", propOrder = {
    "externalId",
    "price",
    "included",
    "required",
    "available",
    "doNotDisplay",
    "dataConstraints",
    "capabilities",
    "dependencies",
    "features",
    "selectionType"
})
public class FeatureGroupType {

    @XmlElement(required = true)
    protected String externalId;
    protected Price price;
    protected EmptyElementType included;
    protected boolean required;
    protected Boolean available;
    protected EmptyElementType doNotDisplay;
    protected DataConstraintType dataConstraints;
    protected FeatureCapabilityListType capabilities;
    protected FeatureDependencyListType dependencies;
    @XmlElement(name = "feature")
    protected List<FeatureType> features;
    @XmlElement(required = true)
    protected FeatureGroupType.SelectionType selectionType;
    @XmlAttribute(name = "type", required = true)
    protected String type;
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
     *     {@link Price }
     *     
     */
    public Price getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link Price }
     *     
     */
    public void setPrice(Price value) {
        this.price = value;
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
     * Gets the value of the features property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the features property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeatures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FeatureType }
     * 
     * 
     */
    public List<FeatureType> getFeatures() {
        if (features == null) {
            features = new ArrayList<FeatureType>();
        }
        return this.features;
    }

    /**
     * Gets the value of the selectionType property.
     * 
     * @return
     *     possible object is
     *     {@link FeatureGroupType.SelectionType }
     *     
     */
    public FeatureGroupType.SelectionType getSelectionType() {
        return selectionType;
    }

    /**
     * Sets the value of the selectionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FeatureGroupType.SelectionType }
     *     
     */
    public void setSelectionType(FeatureGroupType.SelectionType value) {
        this.selectionType = value;
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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice>
     *         &lt;element name="pickAll" type="{http://xml.A.com/v4}emptyElementType"/>
     *         &lt;element name="pickOne" type="{http://xml.A.com/v4}emptyElementType"/>
     *         &lt;element name="pickUpToN" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
     *         &lt;element name="pickN" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "pickN",
        "pickUpToN",
        "pickOne",
        "pickAll"
    })
    public static class SelectionType {

        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger pickN;
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger pickUpToN;
        protected EmptyElementType pickOne;
        protected EmptyElementType pickAll;

        /**
         * Gets the value of the pickN property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getPickN() {
            return pickN;
        }

        /**
         * Sets the value of the pickN property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPickN(BigInteger value) {
            this.pickN = value;
        }

        /**
         * Gets the value of the pickUpToN property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getPickUpToN() {
            return pickUpToN;
        }

        /**
         * Sets the value of the pickUpToN property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPickUpToN(BigInteger value) {
            this.pickUpToN = value;
        }

        /**
         * Gets the value of the pickOne property.
         * 
         * @return
         *     possible object is
         *     {@link EmptyElementType }
         *     
         */
        public EmptyElementType getPickOne() {
            return pickOne;
        }

        /**
         * Sets the value of the pickOne property.
         * 
         * @param value
         *     allowed object is
         *     {@link EmptyElementType }
         *     
         */
        public void setPickOne(EmptyElementType value) {
            this.pickOne = value;
        }

        /**
         * Gets the value of the pickAll property.
         * 
         * @return
         *     possible object is
         *     {@link EmptyElementType }
         *     
         */
        public EmptyElementType getPickAll() {
            return pickAll;
        }

        /**
         * Sets the value of the pickAll property.
         * 
         * @param value
         *     allowed object is
         *     {@link EmptyElementType }
         *     
         */
        public void setPickAll(EmptyElementType value) {
            this.pickAll = value;
        }

    }

}
