
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PriceInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PriceInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="priceCategory" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="categoryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="categoryTotal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="productInfo" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="productName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="partialPaymentDueOption" type="{http://xml.A.com/v4}PartialPaymentDueOption" minOccurs="0"/>
 *         &lt;element name="amountDueNow" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PriceInfo", propOrder = {
    "priceCategory",
    "partialPaymentDueOption",
    "amountDueNow"
})
public class PriceInfo {

    @XmlElement(required = true)
    protected List<PriceInfo.PriceCategory> priceCategory;
    protected PartialPaymentDueOption partialPaymentDueOption;
    protected float amountDueNow;

    /**
     * Gets the value of the priceCategory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the priceCategory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPriceCategory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PriceInfo.PriceCategory }
     * 
     * 
     */
    public List<PriceInfo.PriceCategory> getPriceCategory() {
        if (priceCategory == null) {
            priceCategory = new ArrayList<PriceInfo.PriceCategory>();
        }
        return this.priceCategory;
    }

    /**
     * Gets the value of the partialPaymentDueOption property.
     * 
     * @return
     *     possible object is
     *     {@link PartialPaymentDueOption }
     *     
     */
    public PartialPaymentDueOption getPartialPaymentDueOption() {
        return partialPaymentDueOption;
    }

    /**
     * Sets the value of the partialPaymentDueOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartialPaymentDueOption }
     *     
     */
    public void setPartialPaymentDueOption(PartialPaymentDueOption value) {
        this.partialPaymentDueOption = value;
    }

    /**
     * Gets the value of the amountDueNow property.
     * 
     */
    public float getAmountDueNow() {
        return amountDueNow;
    }

    /**
     * Sets the value of the amountDueNow property.
     * 
     */
    public void setAmountDueNow(float value) {
        this.amountDueNow = value;
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
     *         &lt;element name="categoryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="categoryTotal" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="productInfo" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="productName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    @XmlType(name = "", propOrder = {
        "categoryName",
        "categoryTotal",
        "productInfo"
    })
    public static class PriceCategory {

        @XmlElement(required = true)
        protected String categoryName;
        @XmlElement(required = true)
        protected String categoryTotal;
        protected List<PriceInfo.PriceCategory.ProductInfo> productInfo;

        /**
         * Gets the value of the categoryName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCategoryName() {
            return categoryName;
        }

        /**
         * Sets the value of the categoryName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCategoryName(String value) {
            this.categoryName = value;
        }

        /**
         * Gets the value of the categoryTotal property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCategoryTotal() {
            return categoryTotal;
        }

        /**
         * Sets the value of the categoryTotal property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCategoryTotal(String value) {
            this.categoryTotal = value;
        }

        /**
         * Gets the value of the productInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the productInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProductInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PriceInfo.PriceCategory.ProductInfo }
         * 
         * 
         */
        public List<PriceInfo.PriceCategory.ProductInfo> getProductInfo() {
            if (productInfo == null) {
                productInfo = new ArrayList<PriceInfo.PriceCategory.ProductInfo>();
            }
            return this.productInfo;
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
         *         &lt;element name="productName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "productName",
            "price"
        })
        public static class ProductInfo {

            @XmlElement(required = true)
            protected String productName;
            @XmlElement(required = true)
            protected String price;

            /**
             * Gets the value of the productName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProductName() {
                return productName;
            }

            /**
             * Sets the value of the productName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProductName(String value) {
                this.productName = value;
            }

            /**
             * Gets the value of the price property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPrice() {
                return price;
            }

            /**
             * Sets the value of the price property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPrice(String value) {
                this.price = value;
            }

        }

    }

}
