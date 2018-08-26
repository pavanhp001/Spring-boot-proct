
package com.A.xml.pr.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for productTransactionTypeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="productTransactionTypeType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractTransactionTypeType">
 *       &lt;sequence>
 *         &lt;element name="productTransactionType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="getProducts"/>
 *               &lt;enumeration value="getProductDetails"/>
 *               &lt;enumeration value="catalogProducts"/>
 *               &lt;enumeration value="getProductCatalogDetails"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
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
@XmlType(name = "productTransactionTypeType", propOrder = {
    "productTransactionType"
})
public class ProductTransactionTypeType
    extends AbstractTransactionTypeType
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String productTransactionType;

    /**
     * Gets the value of the productTransactionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductTransactionType() {
        return productTransactionType;
    }

    /**
     * Sets the value of the productTransactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductTransactionType(String value) {
        this.productTransactionType = value;
    }

}
