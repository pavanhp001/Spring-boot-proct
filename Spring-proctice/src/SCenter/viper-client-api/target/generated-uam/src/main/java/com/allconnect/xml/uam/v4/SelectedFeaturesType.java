
package com.A.xml.uam.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
    "featureGroup"
})
public class SelectedFeaturesType {

    protected SelectedFeaturesType.Features features;
    protected List<SelectedFeaturesType.FeatureGroup> featureGroup;

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
     * Gets the value of the featureGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the featureGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeatureGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SelectedFeaturesType.FeatureGroup }
     * 
     * 
     */
    public List<SelectedFeaturesType.FeatureGroup> getFeatureGroup() {
        if (featureGroup == null) {
            featureGroup = new ArrayList<SelectedFeaturesType.FeatureGroup>();
        }
        return this.featureGroup;
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
        "featureValue"
    })
    public static class FeatureGroup {

        protected List<FeatureValueType> featureValue;
        @XmlAttribute(name = "externalId")
        protected String externalId;
        @XmlAttribute(name = "groupType")
        protected Integer groupType;

        /**
         * Gets the value of the featureValue property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the featureValue property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFeatureValue().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FeatureValueType }
         * 
         * 
         */
        public List<FeatureValueType> getFeatureValue() {
            if (featureValue == null) {
                featureValue = new ArrayList<FeatureValueType>();
            }
            return this.featureValue;
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
        "featureValue"
    })
    public static class Features {

        protected List<FeatureValueType> featureValue;

        /**
         * Gets the value of the featureValue property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the featureValue property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFeatureValue().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FeatureValueType }
         * 
         * 
         */
        public List<FeatureValueType> getFeatureValue() {
            if (featureValue == null) {
                featureValue = new ArrayList<FeatureValueType>();
            }
            return this.featureValue;
        }

    }

}
