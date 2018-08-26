
package com.A.xml.pr.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnergyProduct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EnergyProduct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProductCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProductCode2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductSubType" type="{http://xml.A.com/v4}ProductSubTypeEnum" minOccurs="0"/>
 *         &lt;element name="OEIndex" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Shortname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Mediumname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Longname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShortDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LongDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Prices" type="{http://xml.A.com/v4}PriceFields" minOccurs="0"/>
 *         &lt;element name="ContractLength" type="{http://xml.A.com/v4}ContractLengthEnum" minOccurs="0"/>
 *         &lt;element name="EarlyTerminationFee" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisplayVisible" type="{http://xml.A.com/v4}YesNoEnum" minOccurs="0"/>
 *         &lt;element name="DisplaySelected" type="{http://xml.A.com/v4}YesNoEnum" minOccurs="0"/>
 *         &lt;element name="TermsAndConditions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanType" type="{http://xml.A.com/v4}EnergyPlanTypeEnum" minOccurs="0"/>
 *         &lt;element name="EnergyType" type="{http://xml.A.com/v4}EnergyTypEnum" minOccurs="0"/>
 *         &lt;element name="EnergySourceType" type="{http://xml.A.com/v4}EnergySourceTypeEnum" minOccurs="0"/>
 *         &lt;element name="CampaignCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PromotionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OfferSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductPriceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IncentiveCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MarketSegment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnergyProduct", propOrder = {
    "productCode",
    "productCode2",
    "productSubType",
    "oeIndex",
    "shortname",
    "mediumname",
    "longname",
    "shortDescription",
    "longDescription",
    "prices",
    "contractLength",
    "earlyTerminationFee",
    "displayVisible",
    "displaySelected",
    "termsAndConditions",
    "planType",
    "energyType",
    "energySourceType",
    "campaignCode",
    "promotionCode",
    "offerSequenceNumber",
    "productPriceCode",
    "incentiveCode",
    "marketSegment"
})
public class EnergyProduct {

    @XmlElement(name = "ProductCode", required = true)
    protected String productCode;
    @XmlElement(name = "ProductCode2")
    protected String productCode2;
    @XmlElement(name = "ProductSubType")
    protected ProductSubTypeEnum productSubType;
    @XmlElement(name = "OEIndex")
    protected Long oeIndex;
    @XmlElement(name = "Shortname", required = true)
    protected String shortname;
    @XmlElement(name = "Mediumname")
    protected String mediumname;
    @XmlElement(name = "Longname")
    protected String longname;
    @XmlElement(name = "ShortDescription", required = true)
    protected String shortDescription;
    @XmlElement(name = "LongDescription")
    protected String longDescription;
    @XmlElement(name = "Prices")
    protected PriceFields prices;
    @XmlElement(name = "ContractLength")
    protected ContractLengthEnum contractLength;
    @XmlElement(name = "EarlyTerminationFee")
    protected String earlyTerminationFee;
    @XmlElement(name = "DisplayVisible")
    protected YesNoEnum displayVisible;
    @XmlElement(name = "DisplaySelected")
    protected YesNoEnum displaySelected;
    @XmlElement(name = "TermsAndConditions")
    protected String termsAndConditions;
    @XmlElement(name = "PlanType")
    protected EnergyPlanTypeEnum planType;
    @XmlElement(name = "EnergyType")
    protected EnergyTypEnum energyType;
    @XmlElement(name = "EnergySourceType")
    protected EnergySourceTypeEnum energySourceType;
    @XmlElement(name = "CampaignCode")
    protected String campaignCode;
    @XmlElement(name = "PromotionCode")
    protected String promotionCode;
    @XmlElement(name = "OfferSequenceNumber")
    protected String offerSequenceNumber;
    @XmlElement(name = "ProductPriceCode")
    protected String productPriceCode;
    @XmlElement(name = "IncentiveCode")
    protected String incentiveCode;
    @XmlElement(name = "MarketSegment")
    protected String marketSegment;

    /**
     * Gets the value of the productCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets the value of the productCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCode(String value) {
        this.productCode = value;
    }

    /**
     * Gets the value of the productCode2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCode2() {
        return productCode2;
    }

    /**
     * Sets the value of the productCode2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCode2(String value) {
        this.productCode2 = value;
    }

    /**
     * Gets the value of the productSubType property.
     * 
     * @return
     *     possible object is
     *     {@link ProductSubTypeEnum }
     *     
     */
    public ProductSubTypeEnum getProductSubType() {
        return productSubType;
    }

    /**
     * Sets the value of the productSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductSubTypeEnum }
     *     
     */
    public void setProductSubType(ProductSubTypeEnum value) {
        this.productSubType = value;
    }

    /**
     * Gets the value of the oeIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOEIndex() {
        return oeIndex;
    }

    /**
     * Sets the value of the oeIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOEIndex(Long value) {
        this.oeIndex = value;
    }

    /**
     * Gets the value of the shortname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortname() {
        return shortname;
    }

    /**
     * Sets the value of the shortname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortname(String value) {
        this.shortname = value;
    }

    /**
     * Gets the value of the mediumname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediumname() {
        return mediumname;
    }

    /**
     * Sets the value of the mediumname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediumname(String value) {
        this.mediumname = value;
    }

    /**
     * Gets the value of the longname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongname() {
        return longname;
    }

    /**
     * Sets the value of the longname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongname(String value) {
        this.longname = value;
    }

    /**
     * Gets the value of the shortDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortDescription(String value) {
        this.shortDescription = value;
    }

    /**
     * Gets the value of the longDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * Sets the value of the longDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongDescription(String value) {
        this.longDescription = value;
    }

    /**
     * Gets the value of the prices property.
     * 
     * @return
     *     possible object is
     *     {@link PriceFields }
     *     
     */
    public PriceFields getPrices() {
        return prices;
    }

    /**
     * Sets the value of the prices property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceFields }
     *     
     */
    public void setPrices(PriceFields value) {
        this.prices = value;
    }

    /**
     * Gets the value of the contractLength property.
     * 
     * @return
     *     possible object is
     *     {@link ContractLengthEnum }
     *     
     */
    public ContractLengthEnum getContractLength() {
        return contractLength;
    }

    /**
     * Sets the value of the contractLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractLengthEnum }
     *     
     */
    public void setContractLength(ContractLengthEnum value) {
        this.contractLength = value;
    }

    /**
     * Gets the value of the earlyTerminationFee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEarlyTerminationFee() {
        return earlyTerminationFee;
    }

    /**
     * Sets the value of the earlyTerminationFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEarlyTerminationFee(String value) {
        this.earlyTerminationFee = value;
    }

    /**
     * Gets the value of the displayVisible property.
     * 
     * @return
     *     possible object is
     *     {@link YesNoEnum }
     *     
     */
    public YesNoEnum getDisplayVisible() {
        return displayVisible;
    }

    /**
     * Sets the value of the displayVisible property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesNoEnum }
     *     
     */
    public void setDisplayVisible(YesNoEnum value) {
        this.displayVisible = value;
    }

    /**
     * Gets the value of the displaySelected property.
     * 
     * @return
     *     possible object is
     *     {@link YesNoEnum }
     *     
     */
    public YesNoEnum getDisplaySelected() {
        return displaySelected;
    }

    /**
     * Sets the value of the displaySelected property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesNoEnum }
     *     
     */
    public void setDisplaySelected(YesNoEnum value) {
        this.displaySelected = value;
    }

    /**
     * Gets the value of the termsAndConditions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    /**
     * Sets the value of the termsAndConditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermsAndConditions(String value) {
        this.termsAndConditions = value;
    }

    /**
     * Gets the value of the planType property.
     * 
     * @return
     *     possible object is
     *     {@link EnergyPlanTypeEnum }
     *     
     */
    public EnergyPlanTypeEnum getPlanType() {
        return planType;
    }

    /**
     * Sets the value of the planType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnergyPlanTypeEnum }
     *     
     */
    public void setPlanType(EnergyPlanTypeEnum value) {
        this.planType = value;
    }

    /**
     * Gets the value of the energyType property.
     * 
     * @return
     *     possible object is
     *     {@link EnergyTypEnum }
     *     
     */
    public EnergyTypEnum getEnergyType() {
        return energyType;
    }

    /**
     * Sets the value of the energyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnergyTypEnum }
     *     
     */
    public void setEnergyType(EnergyTypEnum value) {
        this.energyType = value;
    }

    /**
     * Gets the value of the energySourceType property.
     * 
     * @return
     *     possible object is
     *     {@link EnergySourceTypeEnum }
     *     
     */
    public EnergySourceTypeEnum getEnergySourceType() {
        return energySourceType;
    }

    /**
     * Sets the value of the energySourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnergySourceTypeEnum }
     *     
     */
    public void setEnergySourceType(EnergySourceTypeEnum value) {
        this.energySourceType = value;
    }

    /**
     * Gets the value of the campaignCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCampaignCode() {
        return campaignCode;
    }

    /**
     * Sets the value of the campaignCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCampaignCode(String value) {
        this.campaignCode = value;
    }

    /**
     * Gets the value of the promotionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromotionCode() {
        return promotionCode;
    }

    /**
     * Sets the value of the promotionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromotionCode(String value) {
        this.promotionCode = value;
    }

    /**
     * Gets the value of the offerSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferSequenceNumber() {
        return offerSequenceNumber;
    }

    /**
     * Sets the value of the offerSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferSequenceNumber(String value) {
        this.offerSequenceNumber = value;
    }

    /**
     * Gets the value of the productPriceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductPriceCode() {
        return productPriceCode;
    }

    /**
     * Sets the value of the productPriceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductPriceCode(String value) {
        this.productPriceCode = value;
    }

    /**
     * Gets the value of the incentiveCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncentiveCode() {
        return incentiveCode;
    }

    /**
     * Sets the value of the incentiveCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncentiveCode(String value) {
        this.incentiveCode = value;
    }

    /**
     * Gets the value of the marketSegment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarketSegment() {
        return marketSegment;
    }

    /**
     * Sets the value of the marketSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarketSegment(String value) {
        this.marketSegment = value;
    }

}
