
package com.A.xml.me.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for merchandisingResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="merchandisingResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="salesContextLog" type="{http://xml.A.com/v4}salesContextType" minOccurs="0"/>
 *         &lt;element name="productList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="merchandisedProduct" type="{http://xml.A.com/v4}merchandisedProductType" maxOccurs="unbounded"/>
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
@XmlType(name = "merchandisingResponseType", propOrder = {
    "salesContextLog",
    "productList"
})
public class MerchandisingResponseType
    extends AbstractResponseType
{

    protected SalesContextType salesContextLog;
    @XmlElement(required = true)
    protected MerchandisingResponseType.ProductList productList;

    /**
     * Gets the value of the salesContextLog property.
     * 
     * @return
     *     possible object is
     *     {@link SalesContextType }
     *     
     */
    public SalesContextType getSalesContextLog() {
        return salesContextLog;
    }

    /**
     * Sets the value of the salesContextLog property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesContextType }
     *     
     */
    public void setSalesContextLog(SalesContextType value) {
        this.salesContextLog = value;
    }

    /**
     * Gets the value of the productList property.
     * 
     * @return
     *     possible object is
     *     {@link MerchandisingResponseType.ProductList }
     *     
     */
    public MerchandisingResponseType.ProductList getProductList() {
        return productList;
    }

    /**
     * Sets the value of the productList property.
     * 
     * @param value
     *     allowed object is
     *     {@link MerchandisingResponseType.ProductList }
     *     
     */
    public void setProductList(MerchandisingResponseType.ProductList value) {
        this.productList = value;
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
     *         &lt;element name="merchandisedProduct" type="{http://xml.A.com/v4}merchandisedProductType" maxOccurs="unbounded"/>
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
        "merchandisedProduct"
    })
    public static class ProductList {

        @XmlElement(required = true)
        protected List<MerchandisedProductType> merchandisedProduct;

        /**
         * Gets the value of the merchandisedProduct property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the merchandisedProduct property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMerchandisedProduct().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MerchandisedProductType }
         * 
         * 
         */
        public List<MerchandisedProductType> getMerchandisedProduct() {
            if (merchandisedProduct == null) {
                merchandisedProduct = new ArrayList<MerchandisedProductType>();
            }
            return this.merchandisedProduct;
        }

    }

}
