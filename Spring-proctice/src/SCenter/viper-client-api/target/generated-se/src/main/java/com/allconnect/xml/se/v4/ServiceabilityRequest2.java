
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for serviceabilityRequestType2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serviceabilityRequestType2">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element ref="{http://xml.A.com/v4}inputAddressString"/>
 *         &lt;element ref="{http://xml.A.com/v4}serviceabilityTransactionType" minOccurs="0"/>
 *         &lt;element name="speedMode" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
 *         &lt;element ref="{http://xml.A.com/v4}correctedAddress" minOccurs="0"/>
 *         &lt;element name="status" type="{http://xml.A.com/v4}statusType" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}correlationId" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}messageOrigin" minOccurs="0"/>
 *         &lt;element ref="{http://xml.A.com/v4}candidateAddressList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceabilityRequestType2", propOrder = {
    "inputAddressString",
    "serviceabilityTransactionType",
    "speedMode",
    "rtimRequestResponseCriteria",
    "correctedAddress",
    "status",
    "correlationId",
    "messageOrigin",
    "candidateAddressList"
})
@XmlRootElement(name = "serviceabilityRequest2")
public class ServiceabilityRequest2
    extends AbstractRequestType
{

    @XmlElement(required = true)
    protected String inputAddressString;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String serviceabilityTransactionType;
    protected Boolean speedMode;
    @XmlElement(required = true)
    protected ServiceabilityRequest2 .RtimRequestResponseCriteria rtimRequestResponseCriteria;
    protected AddressType correctedAddress;
    protected StatusType status;
    protected String correlationId;
    protected String messageOrigin;
    protected CandidateAddressList candidateAddressList;

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
     * Gets the value of the speedMode property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSpeedMode() {
        return speedMode;
    }

    /**
     * Sets the value of the speedMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSpeedMode(Boolean value) {
        this.speedMode = value;
    }

    /**
     * Gets the value of the rtimRequestResponseCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceabilityRequest2 .RtimRequestResponseCriteria }
     *     
     */
    public ServiceabilityRequest2 .RtimRequestResponseCriteria getRtimRequestResponseCriteria() {
        return rtimRequestResponseCriteria;
    }

    /**
     * Sets the value of the rtimRequestResponseCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceabilityRequest2 .RtimRequestResponseCriteria }
     *     
     */
    public void setRtimRequestResponseCriteria(ServiceabilityRequest2 .RtimRequestResponseCriteria value) {
        this.rtimRequestResponseCriteria = value;
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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
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
     * 
     * 									A list of rtim provider specific requests that are sent.
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
