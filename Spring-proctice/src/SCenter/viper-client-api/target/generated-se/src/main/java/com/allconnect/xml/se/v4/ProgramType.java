
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for programType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="programType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="metadataList" type="{http://xml.A.com/v4}metaDataListType" minOccurs="0"/>
 *         &lt;element name="inboundOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="callbackNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emailCallbackNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coBrandName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="baseURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refFacingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="flow" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="channel" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "programType", propOrder = {
    "externalId",
    "name",
    "metadataList",
    "inboundOrderNumber",
    "callbackNumber",
    "emailCallbackNumber",
    "coBrandName",
    "baseURL",
    "refFacingName"
})
public class ProgramType {

    protected String externalId;
    protected String name;
    protected MetaDataListType metadataList;
    protected String inboundOrderNumber;
    protected String callbackNumber;
    protected String emailCallbackNumber;
    protected String coBrandName;
    protected String baseURL;
    protected String refFacingName;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "flow")
    protected String flow;
    @XmlAttribute(name = "channel")
    protected String channel;

    /**
     * Gets the value of the externalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalId(String value) {
        this.externalId = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the metadataList property.
     * 
     * @return
     *     possible object is
     *     {@link MetaDataListType }
     *     
     */
    public MetaDataListType getMetadataList() {
        return metadataList;
    }

    /**
     * Sets the value of the metadataList property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetaDataListType }
     *     
     */
    public void setMetadataList(MetaDataListType value) {
        this.metadataList = value;
    }

    /**
     * Gets the value of the inboundOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInboundOrderNumber() {
        return inboundOrderNumber;
    }

    /**
     * Sets the value of the inboundOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInboundOrderNumber(String value) {
        this.inboundOrderNumber = value;
    }

    /**
     * Gets the value of the callbackNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallbackNumber() {
        return callbackNumber;
    }

    /**
     * Sets the value of the callbackNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallbackNumber(String value) {
        this.callbackNumber = value;
    }

    /**
     * Gets the value of the emailCallbackNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailCallbackNumber() {
        return emailCallbackNumber;
    }

    /**
     * Sets the value of the emailCallbackNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailCallbackNumber(String value) {
        this.emailCallbackNumber = value;
    }

    /**
     * Gets the value of the coBrandName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoBrandName() {
        return coBrandName;
    }

    /**
     * Sets the value of the coBrandName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoBrandName(String value) {
        this.coBrandName = value;
    }

    /**
     * Gets the value of the baseURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseURL() {
        return baseURL;
    }

    /**
     * Sets the value of the baseURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseURL(String value) {
        this.baseURL = value;
    }

    /**
     * Gets the value of the refFacingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefFacingName() {
        return refFacingName;
    }

    /**
     * Sets the value of the refFacingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefFacingName(String value) {
        this.refFacingName = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the flow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlow() {
        return flow;
    }

    /**
     * Sets the value of the flow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlow(String value) {
        this.flow = value;
    }

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

}
