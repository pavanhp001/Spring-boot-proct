
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditQualificationResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditQualificationResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4/OrderProvisioning/}ProvisioningResponse">
 *       &lt;sequence>
 *         &lt;element name="schedulingInfo" type="{http://xml.A.com/v4/OrderProvisioning/}OpSchedulingInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="creditCustomizations" type="{http://xml.A.com/v4}customizationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transientResponseContainer" type="{http://xml.A.com/v4}transientResponseContainerType" minOccurs="0"/>
 *         &lt;element name="deposit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="houseDebt" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="customerDebt" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="pastDue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="payNowOneTimeCharges" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="videoDeposit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="dataDeposit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="voiceDeposit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="creditCheckPassed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="providerAttributes" type="{http://xml.A.com/v4}ProviderAttributes" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="verificationQuestion" type="{http://xml.A.com/v4}verificationQuestion" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditQualificationResponse", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "schedulingInfo",
    "creditCustomizations",
    "transientResponseContainer",
    "deposit",
    "houseDebt",
    "customerDebt",
    "pastDue",
    "payNowOneTimeCharges",
    "videoDeposit",
    "dataDeposit",
    "voiceDeposit",
    "creditCheckPassed",
    "providerAttributes",
    "verificationQuestion"
})
public class CreditQualificationResponse
    extends ProvisioningResponse
{

    @XmlElement(namespace = "")
    protected List<OpSchedulingInfo> schedulingInfo;
    @XmlElement(namespace = "")
    protected List<CustomizationType> creditCustomizations;
    @XmlElement(namespace = "")
    protected TransientResponseContainerType transientResponseContainer;
    @XmlElement(namespace = "")
    protected Double deposit;
    @XmlElement(namespace = "")
    protected Double houseDebt;
    @XmlElement(namespace = "")
    protected Double customerDebt;
    @XmlElement(namespace = "")
    protected Double pastDue;
    @XmlElement(namespace = "")
    protected Double payNowOneTimeCharges;
    @XmlElement(namespace = "")
    protected Double videoDeposit;
    @XmlElement(namespace = "")
    protected Double dataDeposit;
    @XmlElement(namespace = "")
    protected Double voiceDeposit;
    @XmlElement(namespace = "")
    protected Boolean creditCheckPassed;
    @XmlElement(namespace = "")
    protected List<ProviderAttributes> providerAttributes;
    @XmlElement(namespace = "")
    protected List<VerificationQuestion> verificationQuestion;

    /**
     * Gets the value of the schedulingInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the schedulingInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSchedulingInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OpSchedulingInfo }
     * 
     * 
     */
    public List<OpSchedulingInfo> getSchedulingInfo() {
        if (schedulingInfo == null) {
            schedulingInfo = new ArrayList<OpSchedulingInfo>();
        }
        return this.schedulingInfo;
    }

    /**
     * Gets the value of the creditCustomizations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the creditCustomizations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreditCustomizations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomizationType }
     * 
     * 
     */
    public List<CustomizationType> getCreditCustomizations() {
        if (creditCustomizations == null) {
            creditCustomizations = new ArrayList<CustomizationType>();
        }
        return this.creditCustomizations;
    }

    /**
     * Gets the value of the transientResponseContainer property.
     * 
     * @return
     *     possible object is
     *     {@link TransientResponseContainerType }
     *     
     */
    public TransientResponseContainerType getTransientResponseContainer() {
        return transientResponseContainer;
    }

    /**
     * Sets the value of the transientResponseContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransientResponseContainerType }
     *     
     */
    public void setTransientResponseContainer(TransientResponseContainerType value) {
        this.transientResponseContainer = value;
    }

    /**
     * Gets the value of the deposit property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDeposit() {
        return deposit;
    }

    /**
     * Sets the value of the deposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDeposit(Double value) {
        this.deposit = value;
    }

    /**
     * Gets the value of the houseDebt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHouseDebt() {
        return houseDebt;
    }

    /**
     * Sets the value of the houseDebt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHouseDebt(Double value) {
        this.houseDebt = value;
    }

    /**
     * Gets the value of the customerDebt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCustomerDebt() {
        return customerDebt;
    }

    /**
     * Sets the value of the customerDebt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCustomerDebt(Double value) {
        this.customerDebt = value;
    }

    /**
     * Gets the value of the pastDue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPastDue() {
        return pastDue;
    }

    /**
     * Sets the value of the pastDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPastDue(Double value) {
        this.pastDue = value;
    }

    /**
     * Gets the value of the payNowOneTimeCharges property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPayNowOneTimeCharges() {
        return payNowOneTimeCharges;
    }

    /**
     * Sets the value of the payNowOneTimeCharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPayNowOneTimeCharges(Double value) {
        this.payNowOneTimeCharges = value;
    }

    /**
     * Gets the value of the videoDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getVideoDeposit() {
        return videoDeposit;
    }

    /**
     * Sets the value of the videoDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setVideoDeposit(Double value) {
        this.videoDeposit = value;
    }

    /**
     * Gets the value of the dataDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDataDeposit() {
        return dataDeposit;
    }

    /**
     * Sets the value of the dataDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDataDeposit(Double value) {
        this.dataDeposit = value;
    }

    /**
     * Gets the value of the voiceDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getVoiceDeposit() {
        return voiceDeposit;
    }

    /**
     * Sets the value of the voiceDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setVoiceDeposit(Double value) {
        this.voiceDeposit = value;
    }

    /**
     * Gets the value of the creditCheckPassed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCreditCheckPassed() {
        return creditCheckPassed;
    }

    /**
     * Sets the value of the creditCheckPassed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCreditCheckPassed(Boolean value) {
        this.creditCheckPassed = value;
    }

    /**
     * Gets the value of the providerAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the providerAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProviderAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProviderAttributes }
     * 
     * 
     */
    public List<ProviderAttributes> getProviderAttributes() {
        if (providerAttributes == null) {
            providerAttributes = new ArrayList<ProviderAttributes>();
        }
        return this.providerAttributes;
    }

    /**
     * Gets the value of the verificationQuestion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the verificationQuestion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVerificationQuestion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VerificationQuestion }
     * 
     * 
     */
    public List<VerificationQuestion> getVerificationQuestion() {
        if (verificationQuestion == null) {
            verificationQuestion = new ArrayList<VerificationQuestion>();
        }
        return this.verificationQuestion;
    }

}
