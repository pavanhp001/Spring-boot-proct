
package com.A.xml.se.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for serviceabilityResponseType2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serviceabilityResponseType2">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element ref="{http://xml.A.com/v4}inputAddressString"/>
 *         &lt;element ref="{http://xml.A.com/v4}serviceabilityTransactionType" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}correctedAddress" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}correlationId" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}messageOrigin" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}candidateAddressList" minOccurs="0"/>
 *         &lt;element name="rtimRequestResponseCriteria">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="providerCriteria" type="{http://xml.A.com/v4}providerCriteriaType2" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceabilityResponseType2", propOrder = {
    "inputAddressString",
    "serviceabilityTransactionType",
    "correctedAddress",
    "correlationId",
    "messageOrigin",
    "candidateAddressList",
    "rtimRequestResponseCriteria",
    "providerList"
})
@XmlRootElement(name = "serviceabilityResponse2")
public class ServiceabilityResponse2
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected String inputAddressString;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String serviceabilityTransactionType;
    protected AddressType correctedAddress;
    protected String correlationId;
    protected String messageOrigin;
    protected CandidateAddressList candidateAddressList;
    @XmlElement(required = true)
    protected ServiceabilityResponse2 .RtimRequestResponseCriteria rtimRequestResponseCriteria;
    protected ServiceabilityResponse2 .ProviderList providerList;

    /**
     * Gets the value of the inputAddressString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputAddressString() {
        return inputAddressString;
    }

    /**
     * Sets the value of the inputAddressString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputAddressString(String value) {
        this.inputAddressString = value;
    }

    /**
     * Gets the value of the serviceabilityTransactionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceabilityTransactionType() {
        return serviceabilityTransactionType;
    }

    /**
     * Sets the value of the serviceabilityTransactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceabilityTransactionType(String value) {
        this.serviceabilityTransactionType = value;
    }

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
     * Gets the value of the correlationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Sets the value of the correlationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrelationId(String value) {
        this.correlationId = value;
    }

    /**
     * Gets the value of the messageOrigin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageOrigin() {
        return messageOrigin;
    }

    /**
     * Sets the value of the messageOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageOrigin(String value) {
        this.messageOrigin = value;
    }

    /**
     * Gets the value of the candidateAddressList property.
     * 
     * @return
     *     possible object is
     *     {@link CandidateAddressList }
     *     
     */
    public CandidateAddressList getCandidateAddressList() {
        return candidateAddressList;
    }

    /**
     * Sets the value of the candidateAddressList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CandidateAddressList }
     *     
     */
    public void setCandidateAddressList(CandidateAddressList value) {
        this.candidateAddressList = value;
    }

    /**
     * Gets the value of the rtimRequestResponseCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceabilityResponse2 .RtimRequestResponseCriteria }
     *     
     */
    public ServiceabilityResponse2 .RtimRequestResponseCriteria getRtimRequestResponseCriteria() {
        return rtimRequestResponseCriteria;
    }

    /**
     * Sets the value of the rtimRequestResponseCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceabilityResponse2 .RtimRequestResponseCriteria }
     *     
     */
    public void setRtimRequestResponseCriteria(ServiceabilityResponse2 .RtimRequestResponseCriteria value) {
        this.rtimRequestResponseCriteria = value;
    }

    /**
     * Gets the value of the providerList property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceabilityResponse2 .ProviderList }
     *     
     */
    public ServiceabilityResponse2 .ProviderList getProviderList() {
        return providerList;
    }

    /**
     * Sets the value of the providerList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceabilityResponse2 .ProviderList }
     *     
     */
    public void setProviderList(ServiceabilityResponse2 .ProviderList value) {
        this.providerList = value;
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
        "providers"
    })
    public static class ProviderList {

        @XmlElement(name = "provider", required = true)
        protected List<ProviderType> providers;

        /**
         * Gets the value of the providers property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the providers property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProviders().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProviderType }
         * 
         * 
         */
        public List<ProviderType> getProviders() {
            if (providers == null) {
                providers = new ArrayList<ProviderType>();
            }
            return this.providers;
        }

    }


    /**
     * 
     * 									A list of rtim response statuses.
     * 								
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="providerCriteria" type="{http://xml.A.com/v4}providerCriteriaType2" minOccurs="0"/>
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
        "providerCriteria"
    })
    public static class RtimRequestResponseCriteria {

        protected ProviderCriteriaType2 providerCriteria;

        /**
         * Gets the value of the providerCriteria property.
         * 
         * @return
         *     possible object is
         *     {@link ProviderCriteriaType2 }
         *     
         */
        public ProviderCriteriaType2 getProviderCriteria() {
            return providerCriteria;
        }

        /**
         * Sets the value of the providerCriteria property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProviderCriteriaType2 }
         *     
         */
        public void setProviderCriteria(ProviderCriteriaType2 value) {
            this.providerCriteria = value;
        }

    }

}
