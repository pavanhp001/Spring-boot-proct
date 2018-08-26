
package com.A.xml.se.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for marketItemDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="marketItemDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestedFor" type="{http://xml.A.com/v4}detailElementType"/>
 *         &lt;element name="feature" type="{http://xml.A.com/v4}featureType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="featureGroup" type="{http://xml.A.com/v4}featureGroupType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="marketingHighlights" type="{http://xml.A.com/v4}marketingHighlightType" minOccurs="0"/>
 *         &lt;element name="descriptiveInfo" type="{http://xml.A.com/v4}descriptiveInfoType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "marketItemDetailType", propOrder = {
    "requestedFor",
    "features",
    "featureGroups",
    "marketingHighlights",
    "descriptiveInfos",
    "metaData"
})
@XmlRootElement(name = "marketItemDetail")
public class MarketItemDetail {

    @XmlElement(required = true)
    protected DetailElementType requestedFor;
    @XmlElement(name = "feature")
    protected List<FeatureType> features;
    @XmlElement(name = "featureGroup")
    protected List<FeatureGroupType> featureGroups;
    protected MarketingHighlightType marketingHighlights;
    @XmlElement(name = "descriptiveInfo")
    protected List<DescriptiveInfoType> descriptiveInfos;
    protected MetaDataType metaData;

    /**
     * Gets the value of the requestedFor property.
     * 
     * @return
     *     possible object is
     *     {@link DetailElementType }
     *     
     */
    public DetailElementType getRequestedFor() {
        return requestedFor;
    }

    /**
     * Sets the value of the requestedFor property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailElementType }
     *     
     */
    public void setRequestedFor(DetailElementType value) {
        this.requestedFor = value;
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
     * {@link FeatureGroupType }
     * 
     * 
     */
    public List<FeatureGroupType> getFeatureGroups() {
        if (featureGroups == null) {
            featureGroups = new ArrayList<FeatureGroupType>();
        }
        return this.featureGroups;
    }

    /**
     * Gets the value of the marketingHighlights property.
     * 
     * @return
     *     possible object is
     *     {@link MarketingHighlightType }
     *     
     */
    public MarketingHighlightType getMarketingHighlights() {
        return marketingHighlights;
    }

    /**
     * Sets the value of the marketingHighlights property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarketingHighlightType }
     *     
     */
    public void setMarketingHighlights(MarketingHighlightType value) {
        this.marketingHighlights = value;
    }

    /**
     * Gets the value of the descriptiveInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the descriptiveInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescriptiveInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptiveInfoType }
     * 
     * 
     */
    public List<DescriptiveInfoType> getDescriptiveInfos() {
        if (descriptiveInfos == null) {
            descriptiveInfos = new ArrayList<DescriptiveInfoType>();
        }
        return this.descriptiveInfos;
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
