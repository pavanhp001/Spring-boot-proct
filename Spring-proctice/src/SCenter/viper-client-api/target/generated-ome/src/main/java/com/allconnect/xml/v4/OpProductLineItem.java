
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OpProductLineItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OpProductLineItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lineItemExternalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="lineItemNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="productExternalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="selectedFeatures" type="{http://xml.A.com/v4}selectedFeaturesType" minOccurs="0"/>
 *         &lt;element name="customSelections" type="{http://xml.A.com/v4}customSelectionsType" minOccurs="0"/>
 *         &lt;element name="activeDialogs" type="{http://xml.A.com/v4}selectedDialogsType" minOccurs="0"/>
 *         &lt;element name="lineItemAttributes" type="{http://xml.A.com/v4}lineItemAttributeType" minOccurs="0"/>
 *         &lt;element name="billingInfoId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="applicablePromotion" type="{http://xml.A.com/v4/OrderProvisioning/}ApplicablePromotion" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="priceInfo" type="{http://xml.A.com/v4}priceInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpProductLineItem", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "lineItemExternalId",
    "lineItemNumber",
    "productExternalId",
    "productName",
    "selectedFeatures",
    "customSelections",
    "activeDialogs",
    "lineItemAttributes",
    "billingInfoId",
    "applicablePromotion",
    "priceInfo"
})
public class OpProductLineItem {

    @XmlElement(namespace = "")
    protected long lineItemExternalId;
    @XmlElement(namespace = "")
    protected int lineItemNumber;
    @XmlElement(namespace = "", required = true)
    protected String productExternalId;
    @XmlElement(namespace = "")
    protected String productName;
    @XmlElement(namespace = "")
    protected SelectedFeaturesType selectedFeatures;
    @XmlElement(namespace = "")
    protected CustomSelectionsType customSelections;
    @XmlElement(namespace = "")
    protected SelectedDialogsType activeDialogs;
    @XmlElement(namespace = "")
    protected LineItemAttributeType lineItemAttributes;
    @XmlElement(namespace = "")
    protected String billingInfoId;
    @XmlElement(namespace = "")
    protected List<ApplicablePromotion> applicablePromotion;
    @XmlElement(namespace = "")
    protected PriceInfoType priceInfo;

    /**
     * Gets the value of the lineItemExternalId property.
     * 
     */
    public long getLineItemExternalId() {
        return lineItemExternalId;
    }

    /**
     * Sets the value of the lineItemExternalId property.
     * 
     */
    public void setLineItemExternalId(long value) {
        this.lineItemExternalId = value;
    }

    /**
     * Gets the value of the lineItemNumber property.
     * 
     */
    public int getLineItemNumber() {
        return lineItemNumber;
    }

    /**
     * Sets the value of the lineItemNumber property.
     * 
     */
    public void setLineItemNumber(int value) {
        this.lineItemNumber = value;
    }

    /**
     * Gets the value of the productExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductExternalId() {
        return productExternalId;
    }

    /**
     * Sets the value of the productExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductExternalId(String value) {
        this.productExternalId = value;
    }

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
     * Gets the value of the selectedFeatures property.
     * 
     * @return
     *     possible object is
     *     {@link SelectedFeaturesType }
     *     
     */
    public SelectedFeaturesType getSelectedFeatures() {
        return selectedFeatures;
    }

    /**
     * Sets the value of the selectedFeatures property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelectedFeaturesType }
     *     
     */
    public void setSelectedFeatures(SelectedFeaturesType value) {
        this.selectedFeatures = value;
    }

    /**
     * Gets the value of the customSelections property.
     * 
     * @return
     *     possible object is
     *     {@link CustomSelectionsType }
     *     
     */
    public CustomSelectionsType getCustomSelections() {
        return customSelections;
    }

    /**
     * Sets the value of the customSelections property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomSelectionsType }
     *     
     */
    public void setCustomSelections(CustomSelectionsType value) {
        this.customSelections = value;
    }

    /**
     * Gets the value of the activeDialogs property.
     * 
     * @return
     *     possible object is
     *     {@link SelectedDialogsType }
     *     
     */
    public SelectedDialogsType getActiveDialogs() {
        return activeDialogs;
    }

    /**
     * Sets the value of the activeDialogs property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelectedDialogsType }
     *     
     */
    public void setActiveDialogs(SelectedDialogsType value) {
        this.activeDialogs = value;
    }

    /**
     * Gets the value of the lineItemAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link LineItemAttributeType }
     *     
     */
    public LineItemAttributeType getLineItemAttributes() {
        return lineItemAttributes;
    }

    /**
     * Sets the value of the lineItemAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineItemAttributeType }
     *     
     */
    public void setLineItemAttributes(LineItemAttributeType value) {
        this.lineItemAttributes = value;
    }

    /**
     * Gets the value of the billingInfoId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingInfoId() {
        return billingInfoId;
    }

    /**
     * Sets the value of the billingInfoId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingInfoId(String value) {
        this.billingInfoId = value;
    }

    /**
     * Gets the value of the applicablePromotion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applicablePromotion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplicablePromotion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ApplicablePromotion }
     * 
     * 
     */
    public List<ApplicablePromotion> getApplicablePromotion() {
        if (applicablePromotion == null) {
            applicablePromotion = new ArrayList<ApplicablePromotion>();
        }
        return this.applicablePromotion;
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

}
