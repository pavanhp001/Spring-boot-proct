
package com.A.xml.pr.v4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
public class ProductRequestType
    extends AbstractRequestType
{

    protected AddressType correctedAddress;
    protected ProductDetailType requestedDetails;
    protected ProductRequestType.ProviderList providerList;
    protected ProductRequestType.ProductList productList;
    protected ProductRequestType.ProductCatalogList productCatalogList;

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
     *     {@link ProductRequestType.ProviderList }
     *     
     */
    public ProductRequestType.ProviderList getProviderList() {
        return providerList;
    }

    /**
     * Sets the value of the providerList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductRequestType.ProviderList }
     *     
     */
    public void setProviderList(ProductRequestType.ProviderList value) {
        this.providerList = value;
    }

    /**
     * Gets the value of the productList property.
     * 
     * @return
     *     possible object is
     *     {@link ProductRequestType.ProductList }
     *     
     */
    public ProductRequestType.ProductList getProductList() {
        return productList;
    }

    /**
     * Sets the value of the productList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductRequestType.ProductList }
     *     
     */
    public void setProductList(ProductRequestType.ProductList value) {
        this.productList = value;
    }

    /**
     * Gets the value of the productCatalogList property.
     * 
     * @return
     *     possible object is
     *     {@link ProductRequestType.ProductCatalogList }
     *     
     */
    public ProductRequestType.ProductCatalogList getProductCatalogList() {
        return productCatalogList;
    }

    /**
     * Sets the value of the productCatalogList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductRequestType.ProductCatalogList }
     *     
     */
    public void setProductCatalogList(ProductRequestType.ProductCatalogList value) {
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
        "catalogId"
    })
    public static class ProductCatalogList {

        @XmlElement(required = true)
        protected List<BigInteger> catalogId;

        /**
         * Gets the value of the catalogId property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the catalogId property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCatalogId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BigInteger }
         * 
         * 
         */
        public List<BigInteger> getCatalogId() {
            if (catalogId == null) {
                catalogId = new ArrayList<BigInteger>();
            }
            return this.catalogId;
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
        "productId"
    })
    public static class ProductList {

        @XmlElement(required = true)
        protected List<ProductRequestType.ProductList.ProductId> productId;

        /**
         * Gets the value of the productId property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the productId property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProductId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProductRequestType.ProductList.ProductId }
         * 
         * 
         */
        public List<ProductRequestType.ProductList.ProductId> getProductId() {
            if (productId == null) {
                productId = new ArrayList<ProductRequestType.ProductList.ProductId>();
            }
            return this.productId;
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
        "provider"
    })
    public static class ProviderList {

        @XmlElement(required = true)
        protected List<ProviderType> provider;

        /**
         * Gets the value of the provider property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the provider property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProvider().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProviderType }
         * 
         * 
         */
        public List<ProviderType> getProvider() {
            if (provider == null) {
                provider = new ArrayList<ProviderType>();
            }
            return this.provider;
        }

    }

}
