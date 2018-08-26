
package com.A.xml.se.v4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for productRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="productRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element name="correctedAddress" type="{http://xml.A.com/v4}addressType" minOccurs="0"/>
 *         &lt;element name="requestedDetails" type="{http://xml.A.com/v4}productDetailType" minOccurs="0"/>
 *         &lt;element name="providerList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="provider" type="{http://xml.A.com/v4}providerType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="productList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="productId" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="externalId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="providerExternalId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="instanceId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="offerType" type="{http://xml.A.com/v4}offerTypeType" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="productCatalogList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="catalogId" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "productRequestType", propOrder = {
    "correctedAddress",
    "requestedDetails",
    "providerList",
    "productList",
    "productCatalogList"
})
@XmlRootElement(name = "productRequest")
public class ProductRequest
    extends AbstractRequestType
{

    protected AddressType correctedAddress;
    protected ProductDetailType requestedDetails;
    protected ProductRequest.ProviderList providerList;
    protected ProductRequest.ProductList productList;
    protected ProductRequest.ProductCatalogList productCatalogList;

    /**
     * Gets the value of the correctedAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getCorrectedAddress() {
        return correctedAddress;
    }

    /**
     * Sets the value of the correctedAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setCorrectedAddress(AddressType value) {
        this.correctedAddress = value;
    }

    /**
     * Gets the value of the requestedDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ProductDetailType }
     *     
     */
    public ProductDetailType getRequestedDetails() {
        return requestedDetails;
    }

    /**
     * Sets the value of the requestedDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductDetailType }
     *     
     */
    public void setRequestedDetails(ProductDetailType value) {
        this.requestedDetails = value;
    }

    /**
     * Gets the value of the providerList property.
     * 
     * @return
     *     possible object is
     *     {@link ProductRequest.ProviderList }
     *     
     */
    public ProductRequest.ProviderList getProviderList() {
        return providerList;
    }

    /**
     * Sets the value of the providerList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductRequest.ProviderList }
     *     
     */
    public void setProviderList(ProductRequest.ProviderList value) {
        this.providerList = value;
    }

    /**
     * Gets the value of the productList property.
     * 
     * @return
     *     possible object is
     *     {@link ProductRequest.ProductList }
     *     
     */
    public ProductRequest.ProductList getProductList() {
        return productList;
    }

    /**
     * Sets the value of the productList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductRequest.ProductList }
     *     
     */
    public void setProductList(ProductRequest.ProductList value) {
        this.productList = value;
    }

    /**
     * Gets the value of the productCatalogList property.
     * 
     * @return
     *     possible object is
     *     {@link ProductRequest.ProductCatalogList }
     *     
     */
    public ProductRequest.ProductCatalogList getProductCatalogList() {
        return productCatalogList;
    }

    /**
     * Sets the value of the productCatalogList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductRequest.ProductCatalogList }
     *     
     */
    public void setProductCatalogList(ProductRequest.ProductCatalogList value) {
        this.productCatalogList = value;
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
     *         &lt;element name="catalogId" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="unbounded"/>
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
        "catalogIds"
    })
    public static class ProductCatalogList {

        @XmlElement(name = "catalogId", required = true)
        protected List<BigInteger> catalogIds;

        /**
         * Gets the value of the catalogIds property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the catalogIds property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCatalogIds().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BigInteger }
         * 
         * 
         */
        public List<BigInteger> getCatalogIds() {
            if (catalogIds == null) {
                catalogIds = new ArrayList<BigInteger>();
            }
            return this.catalogIds;
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
     *         &lt;element name="productId" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="externalId" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="providerExternalId" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="instanceId" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="offerType" type="{http://xml.A.com/v4}offerTypeType" />
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
    @XmlType(name = "", propOrder = {
        "productIds"
    })
    public static class ProductList {

        @XmlElement(name = "productId", required = true)
        protected List<ProductRequest.ProductList.ProductId> productIds;

        /**
         * Gets the value of the productIds property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the productIds property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProductIds().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProductRequest.ProductList.ProductId }
         * 
         * 
         */
        public List<ProductRequest.ProductList.ProductId> getProductIds() {
            if (productIds == null) {
                productIds = new ArrayList<ProductRequest.ProductList.ProductId>();
            }
            return this.productIds;
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
         *       &lt;attribute name="externalId" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="providerExternalId" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="instanceId" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="offerType" type="{http://xml.A.com/v4}offerTypeType" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class ProductId {

            @XmlAttribute(name = "externalId")
            protected String externalId;
            @XmlAttribute(name = "providerExternalId")
            protected String providerExternalId;
            @XmlAttribute(name = "instanceId")
            protected String instanceId;
            @XmlAttribute(name = "offerType")
            protected OfferTypeType offerType;

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
             * Gets the value of the providerExternalId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProviderExternalId() {
                return providerExternalId;
            }

            /**
             * Sets the value of the providerExternalId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProviderExternalId(String value) {
                this.providerExternalId = value;
            }

            /**
             * Gets the value of the instanceId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInstanceId() {
                return instanceId;
            }

            /**
             * Sets the value of the instanceId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInstanceId(String value) {
                this.instanceId = value;
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
     *         &lt;element name="provider" type="{http://xml.A.com/v4}providerType" maxOccurs="unbounded"/>
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
        "providers"
    })
    public static class ProviderList {

        @XmlElement(name = "provider", required = true)
        protected List<ProviderType> providers;

        /**
         * Gets the value of the providers property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the providers property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProviders().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProviderType }
         * 
         * 
         */
        public List<ProviderType> getProviders() {
            if (providers == null) {
                providers = new ArrayList<ProviderType>();
            }
            return this.providers;
        }

    }

}
