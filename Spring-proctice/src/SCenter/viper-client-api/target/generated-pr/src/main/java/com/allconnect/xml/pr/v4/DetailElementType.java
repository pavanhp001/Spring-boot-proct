
package com.A.xml.pr.v4;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for detailElementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="detailType">
 *             &lt;complexType>
 *               &lt;simpleContent>
 *                 &lt;extension base="&lt;http://xml.A.com/v4>detailObjectType">
 *                   &lt;attribute name="externalId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                   &lt;attribute name="realTime" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                   &lt;attribute name="catalogId" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;/extension>
 *               &lt;/simpleContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="referenceType">
 *             &lt;simpleType>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                 &lt;enumeration value="itemCategories"/>
 *                 &lt;enumeration value="referrers"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *           &lt;/element>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailElementType", propOrder = {
    "detailType",
    "referenceType"
})
public class DetailElementType {

    protected DetailElementType.DetailType detailType;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String referenceType;

    /**
     * Gets the value of the detailType property.
     * 
     * @return
     *     possible object is
     *     {@link DetailElementType.DetailType }
     *     
     */
    public DetailElementType.DetailType getDetailType() {
        return detailType;
    }

    /**
     * Sets the value of the detailType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailElementType.DetailType }
     *     
     */
    public void setDetailType(DetailElementType.DetailType value) {
        this.detailType = value;
    }

    /**
     * Gets the value of the referenceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * Sets the value of the referenceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceType(String value) {
        this.referenceType = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://xml.A.com/v4>detailObjectType">
     *       &lt;attribute name="externalId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="realTime" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="catalogId" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class DetailType {

        @XmlValue
        protected DetailObjectType value;
        @XmlAttribute(name = "externalId", required = true)
        protected String externalId;
        @XmlAttribute(name = "realTime")
        protected Boolean realTime;
        @XmlAttribute(name = "catalogId", required = true)
        protected BigInteger catalogId;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link DetailObjectType }
         *     
         */
        public DetailObjectType getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link DetailObjectType }
         *     
         */
        public void setValue(DetailObjectType value) {
            this.value = value;
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
         * Gets the value of the realTime property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isRealTime() {
            return realTime;
        }

        /**
         * Sets the value of the realTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setRealTime(Boolean value) {
            this.realTime = value;
        }

        /**
         * Gets the value of the catalogId property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getCatalogId() {
            return catalogId;
        }

        /**
         * Sets the value of the catalogId property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setCatalogId(BigInteger value) {
            this.catalogId = value;
        }

    }

}
