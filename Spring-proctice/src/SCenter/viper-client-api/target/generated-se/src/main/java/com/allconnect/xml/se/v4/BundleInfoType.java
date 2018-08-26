
package com.A.xml.se.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bundleInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bundleInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="productBundle" type="{http://xml.A.com/v4}productBundleType"/>
 *         &lt;element name="bundleDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="marketingHighlights" type="{http://xml.A.com/v4}marketingHighlightType" minOccurs="0"/>
 *                   &lt;element name="descriptiveInfo" type="{http://xml.A.com/v4}descriptiveInfoType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="metaData" type="{http://xml.A.com/v4}metaDataType" minOccurs="0"/>
 *                   &lt;element name="promotion" type="{http://xml.A.com/v4}productPromotionType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
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
@XmlType(name = "bundleInfoType", propOrder = {
    "productBundle",
    "bundleDetails"
})
public class BundleInfoType {

    @XmlElement(required = true)
    protected ProductBundleType productBundle;
    protected BundleInfoType.BundleDetails bundleDetails;

    /**
     * Gets the value of the productBundle property.
     * 
     * @return
     *     possible object is
     *     {@link ProductBundleType }
     *     
     */
    public ProductBundleType getProductBundle() {
        return productBundle;
    }

    /**
     * Sets the value of the productBundle property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductBundleType }
     *     
     */
    public void setProductBundle(ProductBundleType value) {
        this.productBundle = value;
    }

    /**
     * Gets the value of the bundleDetails property.
     * 
     * @return
     *     possible object is
     *     {@link BundleInfoType.BundleDetails }
     *     
     */
    public BundleInfoType.BundleDetails getBundleDetails() {
        return bundleDetails;
    }

    /**
     * Sets the value of the bundleDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link BundleInfoType.BundleDetails }
     *     
     */
    public void setBundleDetails(BundleInfoType.BundleDetails value) {
        this.bundleDetails = value;
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
     *         &lt;element name="marketingHighlights" type="{http://xml.A.com/v4}marketingHighlightType" minOccurs="0"/>
     *         &lt;element name="descriptiveInfo" type="{http://xml.A.com/v4}descriptiveInfoType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="metaData" type="{http://xml.A.com/v4}metaDataType" minOccurs="0"/>
     *         &lt;element name="promotion" type="{http://xml.A.com/v4}productPromotionType" maxOccurs="unbounded" minOccurs="0"/>
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
        "marketingHighlights",
        "descriptiveInfos",
        "metaData",
        "promotions"
    })
    public static class BundleDetails {

        protected MarketingHighlightType marketingHighlights;
        @XmlElement(name = "descriptiveInfo")
        protected List<DescriptiveInfoType> descriptiveInfos;
        protected MetaDataType metaData;
        @XmlElement(name = "promotion")
        protected List<ProductPromotionType> promotions;

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

        /**
         * Gets the value of the promotions property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the promotions property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPromotions().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProductPromotionType }
         * 
         * 
         */
        public List<ProductPromotionType> getPromotions() {
            if (promotions == null) {
                promotions = new ArrayList<ProductPromotionType>();
            }
            return this.promotions;
        }

    }

}
