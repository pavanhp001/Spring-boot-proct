
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditQualResponseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditQualResponseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="additionalInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rtsAllowed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="videoDeposit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="voiceDeposit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fiosDataDepositCap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataDeposit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hsiDeposit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spendingLimit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="riskLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="finalBillPayAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="finalBillAmountRequired" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="finalBillAccTN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="finalBillAccID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditQualResponseInfo", propOrder = {
    "code",
    "reason",
    "additionalInfo",
    "creditRef",
    "rtsAllowed",
    "videoDeposit",
    "voiceDeposit",
    "fiosDataDepositCap",
    "dataDeposit",
    "hsiDeposit",
    "spendingLimit",
    "paymentMethod",
    "riskLevel",
    "finalBillPayAmount",
    "finalBillAmountRequired",
    "finalBillAccTN",
    "finalBillAccID"
})
public class CreditQualResponseInfo {

    protected String code;
    protected String reason;
    protected String additionalInfo;
    protected String creditRef;
    protected String rtsAllowed;
    protected String videoDeposit;
    protected String voiceDeposit;
    protected String fiosDataDepositCap;
    protected String dataDeposit;
    protected String hsiDeposit;
    protected String spendingLimit;
    protected String paymentMethod;
    protected String riskLevel;
    protected String finalBillPayAmount;
    protected String finalBillAmountRequired;
    protected String finalBillAccTN;
    protected String finalBillAccID;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the additionalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets the value of the additionalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInfo(String value) {
        this.additionalInfo = value;
    }

    /**
     * Gets the value of the creditRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditRef() {
        return creditRef;
    }

    /**
     * Sets the value of the creditRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditRef(String value) {
        this.creditRef = value;
    }

    /**
     * Gets the value of the rtsAllowed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRtsAllowed() {
        return rtsAllowed;
    }

    /**
     * Sets the value of the rtsAllowed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRtsAllowed(String value) {
        this.rtsAllowed = value;
    }

    /**
     * Gets the value of the videoDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideoDeposit() {
        return videoDeposit;
    }

    /**
     * Sets the value of the videoDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideoDeposit(String value) {
        this.videoDeposit = value;
    }

    /**
     * Gets the value of the voiceDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoiceDeposit() {
        return voiceDeposit;
    }

    /**
     * Sets the value of the voiceDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoiceDeposit(String value) {
        this.voiceDeposit = value;
    }

    /**
     * Gets the value of the fiosDataDepositCap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiosDataDepositCap() {
        return fiosDataDepositCap;
    }

    /**
     * Sets the value of the fiosDataDepositCap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiosDataDepositCap(String value) {
        this.fiosDataDepositCap = value;
    }

    /**
     * Gets the value of the dataDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataDeposit() {
        return dataDeposit;
    }

    /**
     * Sets the value of the dataDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataDeposit(String value) {
        this.dataDeposit = value;
    }

    /**
     * Gets the value of the hsiDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHsiDeposit() {
        return hsiDeposit;
    }

    /**
     * Sets the value of the hsiDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHsiDeposit(String value) {
        this.hsiDeposit = value;
    }

    /**
     * Gets the value of the spendingLimit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpendingLimit() {
        return spendingLimit;
    }

    /**
     * Sets the value of the spendingLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpendingLimit(String value) {
        this.spendingLimit = value;
    }

    /**
     * Gets the value of the paymentMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the value of the paymentMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentMethod(String value) {
        this.paymentMethod = value;
    }

    /**
     * Gets the value of the riskLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    /**
     * Sets the value of the riskLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskLevel(String value) {
        this.riskLevel = value;
    }

    /**
     * Gets the value of the finalBillPayAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinalBillPayAmount() {
        return finalBillPayAmount;
    }

    /**
     * Sets the value of the finalBillPayAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinalBillPayAmount(String value) {
        this.finalBillPayAmount = value;
    }

    /**
     * Gets the value of the finalBillAmountRequired property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinalBillAmountRequired() {
        return finalBillAmountRequired;
    }

    /**
     * Sets the value of the finalBillAmountRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinalBillAmountRequired(String value) {
        this.finalBillAmountRequired = value;
    }

    /**
     * Gets the value of the finalBillAccTN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinalBillAccTN() {
        return finalBillAccTN;
    }

    /**
     * Sets the value of the finalBillAccTN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinalBillAccTN(String value) {
        this.finalBillAccTN = value;
    }

    /**
     * Gets the value of the finalBillAccID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinalBillAccID() {
        return finalBillAccID;
    }

    /**
     * Sets the value of the finalBillAccID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinalBillAccID(String value) {
        this.finalBillAccID = value;
    }

}
