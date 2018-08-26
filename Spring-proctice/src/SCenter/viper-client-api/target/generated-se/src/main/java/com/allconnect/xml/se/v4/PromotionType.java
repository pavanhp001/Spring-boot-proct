
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for promotionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="promotionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="promoCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inEffect" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="expiration" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="shortDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qualification" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="conditions" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="preconditions" type="{http://xml.A.com/v4}preconditionsType" minOccurs="0"/>
 *         &lt;element name="type">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="informationalPromotion"/>
 *               &lt;enumeration value="baseMonthlyDiscount"/>
 *               &lt;enumeration value="oneTimeFeeDiscount"/>
 *               &lt;enumeration value="unspecifiedType"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="priceValueType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="absolute"/>
 *               &lt;enumeration value="relative"/>
 *               &lt;enumeration value="relativePercentage"/>
 *               &lt;enumeration value="unspecifiedPriceValueType"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="priceValue" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="promotionDuration" type="{http://www.w3.org/2001/XMLSchema}duration"/>
 *         &lt;element name="metaData" type="{http://xml.A.com/v4}metaDataType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "promotionType", propOrder = {
    "externalId",
    "promoCode",
    "inEffect",
    "expiration",
    "shortDescription",
    "description",
    "qualification",
    "conditions",
    "preconditions",
    "type",
    "priceValueType",
    "priceValue",
    "promotionDuration",
    "metaData"
})
public class PromotionType {

    @XmlElement(required = true)
    protected String externalId;
    @XmlElement(required = true)
    protected String promoCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar inEffect;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expiration;
    @XmlElement(required = true)
    protected String shortDescription;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String qualification;
    @XmlElement(required = true)
    protected String conditions;
    protected PreconditionsType preconditions;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String priceValueType;
    protected Float priceValue;
    @XmlElement(required = true, nillable = true)
    protected Duration promotionDuration;
    protected MetaDataType metaData;

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
     * Gets the value of the promoCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromoCode() {
        return promoCode;
    }

    /**
     * Sets the value of the promoCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromoCode(String value) {
        this.promoCode = value;
    }

    /**
     * Gets the value of the inEffect property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInEffect() {
        return inEffect;
    }

    /**
     * Sets the value of the inEffect property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInEffect(XMLGregorianCalendar value) {
        this.inEffect = value;
    }

    /**
     * Gets the value of the expiration property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpiration() {
        return expiration;
    }

    /**
     * Sets the value of the expiration property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpiration(XMLGregorianCalendar value) {
        this.expiration = value;
    }

    /**
     * Gets the value of the shortDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortDescription(String value) {
        this.shortDescription = value;
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
     * Gets the value of the qualification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * Sets the value of the qualification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualification(String value) {
        this.qualification = value;
    }

    /**
     * Gets the value of the conditions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditions() {
        return conditions;
    }

    /**
     * Sets the value of the conditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditions(String value) {
        this.conditions = value;
    }

    /**
     * Gets the value of the preconditions property.
     * 
     * @return
     *     possible object is
     *     {@link PreconditionsType }
     *     
     */
    public PreconditionsType getPreconditions() {
        return preconditions;
    }

    /**
     * Sets the value of the preconditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreconditionsType }
     *     
     */
    public void setPreconditions(PreconditionsType value) {
        this.preconditions = value;
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
     * Gets the value of the priceValueType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceValueType() {
        return priceValueType;
    }

    /**
     * Sets the value of the priceValueType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceValueType(String value) {
        this.priceValueType = value;
    }

    /**
     * Gets the value of the priceValue property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPriceValue() {
        return priceValue;
    }

    /**
     * Sets the value of the priceValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPriceValue(Float value) {
        this.priceValue = value;
    }

    /**
     * Gets the value of the promotionDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getPromotionDuration() {
        return promotionDuration;
    }

    /**
     * Sets the value of the promotionDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setPromotionDuration(Duration value) {
        this.promotionDuration = value;
    }

    /**
     * Gets the value of the metaData property.
     * 
     * @return
     *     possible object is
     *     {@link MetaDataType }
     *     
     */
    public MetaDataType getMetaData() {
        return metaData;
    }

    /**
     * Sets the value of the metaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetaDataType }
     *     
     */
    public void setMetaData(MetaDataType value) {
        this.metaData = value;
    }

}
