
package com.A.xml.pr.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for productBundleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="productBundleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productCategoryList" type="{http://xml.A.com/v4}productCategoryListType"/>
 *         &lt;element name="channels" type="{http://xml.A.com/v4}channelType"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="providerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priceInfo" type="{http://xml.A.com/v4}priceInfoType"/>
 *         &lt;element name="bundledProducts">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
@XmlType(name = "productBundleType", propOrder = {
    "externalId",
    "productCategoryList",
    "channels",
    "name",
    "providerId",
    "providerName",
    "priceInfo",
    "bundledProducts"
})
public class ProductBundleType {

    @XmlElement(required = true)
    protected String externalId;
    @XmlElement(required = true)
    protected ProductCategoryListType productCategoryList;
    @XmlElement(required = true)
    protected ChannelType channels;
    @XmlElement(required = true)
    protected String name;
    protected String providerId;
    protected String providerName;
    @XmlElement(required = true)
    protected PriceInfoType priceInfo;
    @XmlElement(required = true)
    protected ProductBundleType.BundledProducts bundledProducts;

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
     * Gets the value of the productCategoryList property.
     * 
     * @return
     *     possible object is
     *     {@link ProductCategoryListType }
     *     
     */
    public ProductCategoryListType getProductCategoryList() {
        return productCategoryList;
    }

    /**
     * Sets the value of the productCategoryList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductCategoryListType }
     *     
     */
    public void setProductCategoryList(ProductCategoryListType value) {
        this.productCategoryList = value;
    }

    /**
     * Gets the value of the channels property.
     * 
     * @return
     *     possible object is
     *     {@link ChannelType }
     *     
     */
    public ChannelType getChannels() {
        return channels;
    }

    /**
     * Sets the value of the channels property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChannelType }
     *     
     */
    public void setChannels(ChannelType value) {
        this.channels = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the providerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * Sets the value of the providerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderId(String value) {
        this.providerId = value;
    }

    /**
     * Gets the value of the providerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * Sets the value of the providerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderName(String value) {
        this.providerName = value;
    }

    /**
     * Gets the value of the priceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PriceInfoType }
     *     
     */
    public PriceInfoType getPriceInfo() {
        return priceInfo;
    }

    /**
     * Sets the value of the priceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceInfoType }
     *     
     */
    public void setPriceInfo(PriceInfoType value) {
        this.priceInfo = value;
    }

    /**
     * Gets the value of the bundledProducts property.
     * 
     * @return
     *     possible object is
     *     {@link ProductBundleType.BundledProducts }
     *     
     */
    public ProductBundleType.BundledProducts getBundledProducts() {
        return bundledProducts;
    }

    /**
     * Sets the value of the bundledProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductBundleType.BundledProducts }
     *     
     */
    public void setBundledProducts(ProductBundleType.BundledProducts value) {
        this.bundledProducts = value;
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
     *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "externalId"
    })
    public static class BundledProducts {

        @XmlElement(required = true)
        protected List<String> externalId;

        /**
         * Gets the value of the externalId property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the externalId property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExternalId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getExternalId() {
            if (externalId == null) {
                externalId = new ArrayList<String>();
            }
            return this.externalId;
        }

    }

}
