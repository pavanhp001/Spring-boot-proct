
package com.A.xml.se.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for productInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="productInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="product" type="{http://xml.A.com/v4}productType" minOccurs="0"/>
 *         &lt;element name="productDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="feature" type="{http://xml.A.com/v4}featureType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="featureGroup" type="{http://xml.A.com/v4}featureGroupType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="customization" type="{http://xml.A.com/v4}customizationType" maxOccurs="unbounded" minOccurs="0"/>
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
 *       &lt;attribute name="externalId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="offerType" type="{http://xml.A.com/v4}offerTypeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "productInfoType", propOrder = {
    "product",
    "productDetails"
})
@XmlSeeAlso({
    ProductCatalogType.class
})
public class ProductInfoType {

    protected ProductType product;
    protected ProductInfoType.ProductDetails productDetails;
    @XmlAttribute(name = "externalId")
    protected String externalId;
    @XmlAttribute(name = "offerType")
    protected OfferTypeType offerType;

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link ProductType }
     *     
     */
    public ProductType getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductType }
     *     
     */
    public void setProduct(ProductType value) {
        this.product = value;
    }

    /**
     * Gets the value of the productDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ProductInfoType.ProductDetails }
     *     
     */
    public ProductInfoType.ProductDetails getProductDetails() {
        return productDetails;
    }

    /**
     * Sets the value of the productDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductInfoType.ProductDetails }
     *     
     */
    public void setProductDetails(ProductInfoType.ProductDetails value) {
        this.productDetails = value;
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
     * Gets the value of the offerType property.
     * 
     * @return
     *     possible object is
     *     {@link OfferTypeType }
     *     
     */
    public OfferTypeType getOfferType() {
        return offerType;
    }

    /**
     * Sets the value of the offerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfferTypeType }
     *     
     */
    public void setOfferType(OfferTypeType value) {
        this.offerType = value;
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
     *         &lt;element name="feature" type="{http://xml.A.com/v4}featureType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="featureGroup" type="{http://xml.A.com/v4}featureGroupType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="customization" type="{http://xml.A.com/v4}customizationType" maxOccurs="unbounded" minOccurs="0"/>
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
        "features",
        "featureGroups",
        "customizations",
        "marketingHighlights",
        "descriptiveInfos",
        "metaData",
        "promotions"
    })
    public static class ProductDetails {

        @XmlElement(name = "feature")
        protected List<FeatureType> features;
        @XmlElement(name = "featureGroup")
        protected List<FeatureGroupType> featureGroups;
        @XmlElement(name = "customization")
        protected List<Customization> customizations;
        protected MarketingHighlightType marketingHighlights;
        @XmlElement(name = "descriptiveInfo")
        protected List<DescriptiveInfoType> descriptiveInfos;
        protected MetaDataType metaData;
        @XmlElement(name = "promotion")
        protected List<ProductPromotionType> promotions;

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
         * Gets the value of the customizations property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the customizations property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCustomizations().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Customization }
         * 
         * 
         */
        public List<Customization> getCustomizations() {
            if (customizations == null) {
                customizations = new ArrayList<Customization>();
            }
            return this.customizations;
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
