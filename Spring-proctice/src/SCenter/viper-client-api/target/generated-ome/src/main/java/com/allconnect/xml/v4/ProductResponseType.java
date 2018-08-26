
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for productResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="productResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="rtimRequestResponseCriteria" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="providerCriteria" type="{http://xml.A.com/v4}providerCriteriaListType" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element ref="{http://xml.A.com/v4}providerResults" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{http://xml.A.com/v4}productCatalogResults" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="productBundles" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="bundleInfo" type="{http://xml.A.com/v4}bundleInfoType" maxOccurs="unbounded"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element ref="{http://xml.A.com/v4}catalogedProductList" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "productResponseType", propOrder = {
    "rtimRequestResponseCriteria",
    "providerResults",
    "productCatalogResults",
    "productBundles",
    "catalogedProductList"
})
public class ProductResponseType
    extends AbstractResponseType
{

    protected ProductResponseType.RtimRequestResponseCriteria rtimRequestResponseCriteria;
    protected List<ProviderResults> providerResults;
    protected List<ProductCatalogResults> productCatalogResults;
    protected ProductResponseType.ProductBundles productBundles;
    protected List<CatalogedProductList> catalogedProductList;

    /**
     * Gets the value of the rtimRequestResponseCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link ProductResponseType.RtimRequestResponseCriteria }
     *     
     */
    public ProductResponseType.RtimRequestResponseCriteria getRtimRequestResponseCriteria() {
        return rtimRequestResponseCriteria;
    }

    /**
     * Sets the value of the rtimRequestResponseCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductResponseType.RtimRequestResponseCriteria }
     *     
     */
    public void setRtimRequestResponseCriteria(ProductResponseType.RtimRequestResponseCriteria value) {
        this.rtimRequestResponseCriteria = value;
    }

    /**
     * Gets the value of the providerResults property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the providerResults property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProviderResults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProviderResults }
     * 
     * 
     */
    public List<ProviderResults> getProviderResults() {
        if (providerResults == null) {
            providerResults = new ArrayList<ProviderResults>();
        }
        return this.providerResults;
    }

    /**
     * Gets the value of the productCatalogResults property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productCatalogResults property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductCatalogResults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductCatalogResults }
     * 
     * 
     */
    public List<ProductCatalogResults> getProductCatalogResults() {
        if (productCatalogResults == null) {
            productCatalogResults = new ArrayList<ProductCatalogResults>();
        }
        return this.productCatalogResults;
    }

    /**
     * Gets the value of the productBundles property.
     * 
     * @return
     *     possible object is
     *     {@link ProductResponseType.ProductBundles }
     *     
     */
    public ProductResponseType.ProductBundles getProductBundles() {
        return productBundles;
    }

    /**
     * Sets the value of the productBundles property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductResponseType.ProductBundles }
     *     
     */
    public void setProductBundles(ProductResponseType.ProductBundles value) {
        this.productBundles = value;
    }

    /**
     * Gets the value of the catalogedProductList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the catalogedProductList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCatalogedProductList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CatalogedProductList }
     * 
     * 
     */
    public List<CatalogedProductList> getCatalogedProductList() {
        if (catalogedProductList == null) {
            catalogedProductList = new ArrayList<CatalogedProductList>();
        }
        return this.catalogedProductList;
    }


    /**
     * 
     * 										A bundle is a set of products that are sold together.
     *                                     
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="bundleInfo" type="{http://xml.A.com/v4}bundleInfoType" maxOccurs="unbounded"/>
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
        "bundleInfo"
    })
    public static class ProductBundles {

        @XmlElement(required = true)
        protected List<BundleInfoType> bundleInfo;

        /**
         * Gets the value of the bundleInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the bundleInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBundleInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BundleInfoType }
         * 
         * 
         */
        public List<BundleInfoType> getBundleInfo() {
            if (bundleInfo == null) {
                bundleInfo = new ArrayList<BundleInfoType>();
            }
            return this.bundleInfo;
        }

    }


    /**
     * 
     * 										A list of rtim response statuses.
     * 									
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="providerCriteria" type="{http://xml.A.com/v4}providerCriteriaListType" minOccurs="0"/>
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
        "providerCriteria"
    })
    public static class RtimRequestResponseCriteria {

        protected ProviderCriteriaListType providerCriteria;

        /**
         * Gets the value of the providerCriteria property.
         * 
         * @return
         *     possible object is
         *     {@link ProviderCriteriaListType }
         *     
         */
        public ProviderCriteriaListType getProviderCriteria() {
            return providerCriteria;
        }

        /**
         * Sets the value of the providerCriteria property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProviderCriteriaListType }
         *     
         */
        public void setProviderCriteria(ProviderCriteriaListType value) {
            this.providerCriteria = value;
        }

    }

}
