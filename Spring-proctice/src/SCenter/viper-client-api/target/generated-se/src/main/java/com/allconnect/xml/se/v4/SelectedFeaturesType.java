
package com.A.xml.se.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for selectedFeaturesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="selectedFeaturesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="features" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="featureValue" type="{http://xml.A.com/v4}featureValueType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="featureGroup" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="featureValue" type="{http://xml.A.com/v4}featureValueType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="externalId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="groupType" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "selectedFeaturesType", propOrder = {
    "features",
    "featureGroups"
})
public class SelectedFeaturesType {

    protected SelectedFeaturesType.Features features;
    @XmlElement(name = "featureGroup")
    protected List<SelectedFeaturesType.FeatureGroup> featureGroups;

    /**
     * Gets the value of the features property.
     * 
     * @return
     *     possible object is
     *     {@link SelectedFeaturesType.Features }
     *     
     */
    public SelectedFeaturesType.Features getFeatures() {
        return features;
    }

    /**
     * Sets the value of the features property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelectedFeaturesType.Features }
     *     
     */
    public void setFeatures(SelectedFeaturesType.Features value) {
        this.features = value;
    }

    /**
     * Gets the value of the featureGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the featureGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeatureGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SelectedFeaturesType.FeatureGroup }
     * 
     * 
     */
    public List<SelectedFeaturesType.FeatureGroup> getFeatureGroups() {
        if (featureGroups == null) {
            featureGroups = new ArrayList<SelectedFeaturesType.FeatureGroup>();
        }
        return this.featureGroups;
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
     *         &lt;element name="featureValue" type="{http://xml.A.com/v4}featureValueType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="externalId" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="groupType" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "featureValues"
    })
    public static class FeatureGroup {

        @XmlElement(name = "featureValue")
        protected List<FeatureValueType> featureValues;
        @XmlAttribute(name = "externalId")
        protected String externalId;
        @XmlAttribute(name = "groupType")
        protected Integer groupType;

        /**
         * Gets the value of the featureValues property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the featureValues property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFeatureValues().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FeatureValueType }
         * 
         * 
         */
        public List<FeatureValueType> getFeatureValues() {
            if (featureValues == null) {
                featureValues = new ArrayList<FeatureValueType>();
            }
            return this.featureValues;
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
         * Gets the value of the groupType property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public int getGroupType() {
            if (groupType == null) {
                return  0;
            } else {
                return groupType;
            }
        }

        /**
         * Sets the value of the groupType property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setGroupType(Integer value) {
            this.groupType = value;
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
     *         &lt;element name="featureValue" type="{http://xml.A.com/v4}featureValueType" maxOccurs="unbounded" minOccurs="0"/>
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
        "featureValues"
    })
    public static class Features {

        @XmlElement(name = "featureValue")
        protected List<FeatureValueType> featureValues;

        /**
         * Gets the value of the featureValues property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the featureValues property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFeatureValues().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FeatureValueType }
         * 
         * 
         */
        public List<FeatureValueType> getFeatureValues() {
            if (featureValues == null) {
                featureValues = new ArrayList<FeatureValueType>();
            }
            return this.featureValues;
        }

    }

}
