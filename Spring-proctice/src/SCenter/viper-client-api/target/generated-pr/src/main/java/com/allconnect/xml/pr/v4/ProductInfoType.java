
package com.A.xml.pr.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
        "feature",
        "featureGroup",
        "customization",
        "marketingHighlights",
        "descriptiveInfo",
        "metaData",
        "promotion"
    })
    public static class ProductDetails {

        protected List<FeatureType> feature;
        protected List<FeatureGroupType> featureGroup;
        protected List<CustomizationType> customization;
        protected MarketingHighlightType marketingHighlights;
        protected List<DescriptiveInfoType> descriptiveInfo;
        protected MetaDataType metaData;
        protected List<ProductPromotionType> promotion;

        /**
         * Gets the value of the feature property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the feature property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFeature().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FeatureType }
         * 
         * 
         */
        public List<FeatureType> getFeature() {
            if (feature == null) {
                feature = new ArrayList<FeatureType>();
            }
            return this.feature;
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
         * {@link FeatureGroupType }
         * 
         * 
         */
        public List<FeatureGroupType> getFeatureGroup() {
            if (featureGroup == null) {
                featureGroup = new ArrayList<FeatureGroupType>();
            }
            return this.featureGroup;
        }

        /**
         * Gets the value of the customization property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the customization property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCustomization().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CustomizationType }
         * 
         * 
         */
        public List<CustomizationType> getCustomization() {
            if (customization == null) {
                customization = new ArrayList<CustomizationType>();
            }
            return this.customization;
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
         * Gets the value of the descriptiveInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the descriptiveInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDescriptiveInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DescriptiveInfoType }
         * 
         * 
         */
        public List<DescriptiveInfoType> getDescriptiveInfo() {
            if (descriptiveInfo == null) {
                descriptiveInfo = new ArrayList<DescriptiveInfoType>();
            }
            return this.descriptiveInfo;
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
         * Gets the value of the promotion property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the promotion property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPromotion().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProductPromotionType }
         * 
         * 
         */
        public List<ProductPromotionType> getPromotion() {
            if (promotion == null) {
                promotion = new ArrayList<ProductPromotionType>();
            }
            return this.promotion;
        }

    }

}
