
package com.A.xml.dtl.v4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for dataConstraintType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataConstraintType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="booleanConstraint">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="value" minOccurs="0">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                           &lt;enumeration value="T"/>
 *                           &lt;enumeration value="F"/>
 *                           &lt;enumeration value="true"/>
 *                           &lt;enumeration value="false"/>
 *                           &lt;enumeration value="True"/>
 *                           &lt;enumeration value="False"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;element name="default" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="stringConstraint">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="length" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                     &lt;choice minOccurs="0">
 *                       &lt;sequence>
 *                         &lt;element name="listOfValues">
 *                           &lt;complexType>
 *                             &lt;complexContent>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                 &lt;sequence>
 *                                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                                 &lt;/sequence>
 *                               &lt;/restriction>
 *                             &lt;/complexContent>
 *                           &lt;/complexType>
 *                         &lt;/element>
 *                       &lt;/sequence>
 *                       &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                     &lt;/choice>
 *                     &lt;element name="default" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;/sequence>
 *                   &lt;attribute name="comparableValue" type="{http://www.w3.org/2001/XMLSchema}float" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="integerConstraint">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;choice minOccurs="0">
 *                       &lt;sequence>
 *                         &lt;element name="listOfValues">
 *                           &lt;complexType>
 *                             &lt;complexContent>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                 &lt;sequence>
 *                                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="unbounded"/>
 *                                 &lt;/sequence>
 *                               &lt;/restriction>
 *                             &lt;/complexContent>
 *                           &lt;/complexType>
 *                         &lt;/element>
 *                       &lt;/sequence>
 *                       &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                       &lt;sequence>
 *                         &lt;element name="minValue" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                         &lt;element name="maxValue" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                       &lt;/sequence>
 *                     &lt;/choice>
 *                     &lt;element name="default" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;/sequence>
 *                   &lt;attribute name="unlimited" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                   &lt;attribute name="unit" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataConstraintType", propOrder = {
    "booleanConstraint",
    "stringConstraint",
    "integerConstraint"
})
public class DataConstraintType {

    protected DataConstraintType.BooleanConstraint booleanConstraint;
    protected DataConstraintType.StringConstraint stringConstraint;
    protected DataConstraintType.IntegerConstraint integerConstraint;

    /**
     * Gets the value of the booleanConstraint property.
     * 
     * @return
     *     possible object is
     *     {@link DataConstraintType.BooleanConstraint }
     *     
     */
    public DataConstraintType.BooleanConstraint getBooleanConstraint() {
        return booleanConstraint;
    }

    /**
     * Sets the value of the booleanConstraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataConstraintType.BooleanConstraint }
     *     
     */
    public void setBooleanConstraint(DataConstraintType.BooleanConstraint value) {
        this.booleanConstraint = value;
    }

    /**
     * Gets the value of the stringConstraint property.
     * 
     * @return
     *     possible object is
     *     {@link DataConstraintType.StringConstraint }
     *     
     */
    public DataConstraintType.StringConstraint getStringConstraint() {
        return stringConstraint;
    }

    /**
     * Sets the value of the stringConstraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataConstraintType.StringConstraint }
     *     
     */
    public void setStringConstraint(DataConstraintType.StringConstraint value) {
        this.stringConstraint = value;
    }

    /**
     * Gets the value of the integerConstraint property.
     * 
     * @return
     *     possible object is
     *     {@link DataConstraintType.IntegerConstraint }
     *     
     */
    public DataConstraintType.IntegerConstraint getIntegerConstraint() {
        return integerConstraint;
    }

    /**
     * Sets the value of the integerConstraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataConstraintType.IntegerConstraint }
     *     
     */
    public void setIntegerConstraint(DataConstraintType.IntegerConstraint value) {
        this.integerConstraint = value;
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
     *         &lt;element name="value" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
     *               &lt;enumeration value="T"/>
     *               &lt;enumeration value="F"/>
     *               &lt;enumeration value="true"/>
     *               &lt;enumeration value="false"/>
     *               &lt;enumeration value="True"/>
     *               &lt;enumeration value="False"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="default" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "value",
        "_default"
    })
    public static class BooleanConstraint {

        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String value;
        @XmlElement(name = "default")
        protected Boolean _default;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the default property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isDefault() {
            return _default;
        }

        /**
         * Sets the value of the default property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setDefault(Boolean value) {
            this._default = value;
        }

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
     *         &lt;choice minOccurs="0">
     *           &lt;sequence>
     *             &lt;element name="listOfValues">
     *               &lt;complexType>
     *                 &lt;complexContent>
     *                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                     &lt;sequence>
     *                       &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="unbounded"/>
     *                     &lt;/sequence>
     *                   &lt;/restriction>
     *                 &lt;/complexContent>
     *               &lt;/complexType>
     *             &lt;/element>
     *           &lt;/sequence>
     *           &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *           &lt;sequence>
     *             &lt;element name="minValue" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *             &lt;element name="maxValue" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *           &lt;/sequence>
     *         &lt;/choice>
     *         &lt;element name="default" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="unlimited" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="unit" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "listOfValues",
        "value",
        "minValue",
        "maxValue",
        "_default"
    })
    public static class IntegerConstraint {

        protected DataConstraintType.IntegerConstraint.ListOfValues listOfValues;
        protected BigInteger value;
        protected BigInteger minValue;
        protected BigInteger maxValue;
        @XmlElement(name = "default")
        protected BigInteger _default;
        @XmlAttribute(name = "unlimited")
        protected Boolean unlimited;
        @XmlAttribute(name = "unit")
        protected String unit;

        /**
         * Gets the value of the listOfValues property.
         * 
         * @return
         *     possible object is
         *     {@link DataConstraintType.IntegerConstraint.ListOfValues }
         *     
         */
        public DataConstraintType.IntegerConstraint.ListOfValues getListOfValues() {
            return listOfValues;
        }

        /**
         * Sets the value of the listOfValues property.
         * 
         * @param value
         *     allowed object is
         *     {@link DataConstraintType.IntegerConstraint.ListOfValues }
         *     
         */
        public void setListOfValues(DataConstraintType.IntegerConstraint.ListOfValues value) {
            this.listOfValues = value;
        }

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setValue(BigInteger value) {
            this.value = value;
        }

        /**
         * Gets the value of the minValue property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMinValue() {
            return minValue;
        }

        /**
         * Sets the value of the minValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMinValue(BigInteger value) {
            this.minValue = value;
        }

        /**
         * Gets the value of the maxValue property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMaxValue() {
            return maxValue;
        }

        /**
         * Sets the value of the maxValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMaxValue(BigInteger value) {
            this.maxValue = value;
        }

        /**
         * Gets the value of the default property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDefault() {
            return _default;
        }

        /**
         * Sets the value of the default property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDefault(BigInteger value) {
            this._default = value;
        }

        /**
         * Gets the value of the unlimited property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isUnlimited() {
            return unlimited;
        }

        /**
         * Sets the value of the unlimited property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setUnlimited(Boolean value) {
            this.unlimited = value;
        }

        /**
         * Gets the value of the unit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnit() {
            return unit;
        }

        /**
         * Sets the value of the unit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnit(String value) {
            this.unit = value;
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
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="unbounded"/>
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
            "value"
        })
        public static class ListOfValues {

            @XmlElement(required = true)
            protected List<BigInteger> value;

            /**
             * Gets the value of the value property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the value property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getValue().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link BigInteger }
             * 
             * 
             */
            public List<BigInteger> getValue() {
                if (value == null) {
                    value = new ArrayList<BigInteger>();
                }
                return this.value;
            }

        }

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
     *         &lt;element name="length" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;choice minOccurs="0">
     *           &lt;sequence>
     *             &lt;element name="listOfValues">
     *               &lt;complexType>
     *                 &lt;complexContent>
     *                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                     &lt;sequence>
     *                       &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
     *                     &lt;/sequence>
     *                   &lt;/restriction>
     *                 &lt;/complexContent>
     *               &lt;/complexType>
     *             &lt;/element>
     *           &lt;/sequence>
     *           &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;/choice>
     *         &lt;element name="default" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="comparableValue" type="{http://www.w3.org/2001/XMLSchema}float" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "length",
        "listOfValues",
        "value",
        "_default"
    })
    public static class StringConstraint {

        protected BigInteger length;
        protected DataConstraintType.StringConstraint.ListOfValues listOfValues;
        protected String value;
        @XmlElement(name = "default")
        protected String _default;
        @XmlAttribute(name = "comparableValue")
        protected Float comparableValue;

        /**
         * Gets the value of the length property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLength() {
            return length;
        }

        /**
         * Sets the value of the length property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLength(BigInteger value) {
            this.length = value;
        }

        /**
         * Gets the value of the listOfValues property.
         * 
         * @return
         *     possible object is
         *     {@link DataConstraintType.StringConstraint.ListOfValues }
         *     
         */
        public DataConstraintType.StringConstraint.ListOfValues getListOfValues() {
            return listOfValues;
        }

        /**
         * Sets the value of the listOfValues property.
         * 
         * @param value
         *     allowed object is
         *     {@link DataConstraintType.StringConstraint.ListOfValues }
         *     
         */
        public void setListOfValues(DataConstraintType.StringConstraint.ListOfValues value) {
            this.listOfValues = value;
        }

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the default property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDefault() {
            return _default;
        }

        /**
         * Sets the value of the default property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDefault(String value) {
            this._default = value;
        }

        /**
         * Gets the value of the comparableValue property.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getComparableValue() {
            return comparableValue;
        }

        /**
         * Sets the value of the comparableValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setComparableValue(Float value) {
            this.comparableValue = value;
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
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
            "value"
        })
        public static class ListOfValues {

            @XmlElement(required = true)
            protected List<String> value;

            /**
             * Gets the value of the value property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the value property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getValue().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getValue() {
                if (value == null) {
                    value = new ArrayList<String>();
                }
                return this.value;
            }

        }

    }

}
