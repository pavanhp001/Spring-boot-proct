
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for promotionElementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="promotionElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="promotionType">
 *             &lt;complexType>
 *               &lt;simpleContent>
 *                 &lt;extension base="&lt;http://xml.A.com/v4>promotionObjectType">
 *                   &lt;attribute name="externalId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                   &lt;attribute name="rtsSessionId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                   &lt;attribute name="rtsEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;/extension>
 *               &lt;/simpleContent>
 *             &lt;/complexType>
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
@XmlType(name = "promotionElementType", propOrder = {
    "promotionType"
})
public class PromotionElementType {

    protected PromotionElementType.PromotionType promotionType;

    /**
     * Gets the value of the promotionType property.
     * 
     * @return
     *     possible object is
     *     {@link PromotionElementType.PromotionType }
     *     
     */
    public PromotionElementType.PromotionType getPromotionType() {
        return promotionType;
    }

    /**
     * Sets the value of the promotionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PromotionElementType.PromotionType }
     *     
     */
    public void setPromotionType(PromotionElementType.PromotionType value) {
        this.promotionType = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://xml.A.com/v4>promotionObjectType">
     *       &lt;attribute name="externalId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="rtsSessionId" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="rtsEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
    public static class PromotionType {

        @XmlValue
        protected PromotionObjectType value;
        @XmlAttribute(name = "externalId", required = true)
        protected String externalId;
        @XmlAttribute(name = "rtsSessionId")
        protected String rtsSessionId;
        @XmlAttribute(name = "rtsEnabled")
        protected Boolean rtsEnabled;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link PromotionObjectType }
         *     
         */
        public PromotionObjectType getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link PromotionObjectType }
         *     
         */
        public void setValue(PromotionObjectType value) {
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
         * Gets the value of the rtsSessionId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRtsSessionId() {
            return rtsSessionId;
        }

        /**
         * Sets the value of the rtsSessionId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRtsSessionId(String value) {
            this.rtsSessionId = value;
        }

        /**
         * Gets the value of the rtsEnabled property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isRtsEnabled() {
            return rtsEnabled;
        }

        /**
         * Sets the value of the rtsEnabled property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setRtsEnabled(Boolean value) {
            this.rtsEnabled = value;
        }

    }

}
