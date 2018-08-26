
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductEstimate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductEstimate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="productDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productComponentType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productRate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="recurringAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="recurringAmountWithDiscount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nonRecurringAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nonRecurringAmountWithDiscount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isSOCDisplay" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isPrimaryService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isBundled" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductEstimate", propOrder = {
    "productDescription",
    "productComponentType",
    "productRate",
    "recurringAmount",
    "recurringAmountWithDiscount",
    "nonRecurringAmount",
    "nonRecurringAmountWithDiscount",
    "isSOCDisplay",
    "isPrimaryService",
    "isBundled"
})
public class ProductEstimate {

    @XmlElement(required = true)
    protected String productDescription;
    @XmlElement(required = true)
    protected String productComponentType;
    @XmlElement(required = true)
    protected String productRate;
    @XmlElement(required = true)
    protected String recurringAmount;
    @XmlElement(required = true)
    protected String recurringAmountWithDiscount;
    @XmlElement(required = true)
    protected String nonRecurringAmount;
    @XmlElement(required = true)
    protected String nonRecurringAmountWithDiscount;
    @XmlElement(required = true)
    protected String isSOCDisplay;
    @XmlElement(required = true)
    protected String isPrimaryService;
    @XmlElement(required = true)
    protected String isBundled;

    /**
     * Gets the value of the productDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Sets the value of the productDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductDescription(String value) {
        this.productDescription = value;
    }

    /**
     * Gets the value of the productComponentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductComponentType() {
        return productComponentType;
    }

    /**
     * Sets the value of the productComponentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductComponentType(String value) {
        this.productComponentType = value;
    }

    /**
     * Gets the value of the productRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductRate() {
        return productRate;
    }

    /**
     * Sets the value of the productRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductRate(String value) {
        this.productRate = value;
    }

    /**
     * Gets the value of the recurringAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecurringAmount() {
        return recurringAmount;
    }

    /**
     * Sets the value of the recurringAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecurringAmount(String value) {
        this.recurringAmount = value;
    }

    /**
     * Gets the value of the recurringAmountWithDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecurringAmountWithDiscount() {
        return recurringAmountWithDiscount;
    }

    /**
     * Sets the value of the recurringAmountWithDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecurringAmountWithDiscount(String value) {
        this.recurringAmountWithDiscount = value;
    }

    /**
     * Gets the value of the nonRecurringAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonRecurringAmount() {
        return nonRecurringAmount;
    }

    /**
     * Sets the value of the nonRecurringAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonRecurringAmount(String value) {
        this.nonRecurringAmount = value;
    }

    /**
     * Gets the value of the nonRecurringAmountWithDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonRecurringAmountWithDiscount() {
        return nonRecurringAmountWithDiscount;
    }

    /**
     * Sets the value of the nonRecurringAmountWithDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonRecurringAmountWithDiscount(String value) {
        this.nonRecurringAmountWithDiscount = value;
    }

    /**
     * Gets the value of the isSOCDisplay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsSOCDisplay() {
        return isSOCDisplay;
    }

    /**
     * Sets the value of the isSOCDisplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsSOCDisplay(String value) {
        this.isSOCDisplay = value;
    }

    /**
     * Gets the value of the isPrimaryService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPrimaryService() {
        return isPrimaryService;
    }

    /**
     * Sets the value of the isPrimaryService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPrimaryService(String value) {
        this.isPrimaryService = value;
    }

    /**
     * Gets the value of the isBundled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsBundled() {
        return isBundled;
    }

    /**
     * Sets the value of the isBundled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsBundled(String value) {
        this.isBundled = value;
    }

}
