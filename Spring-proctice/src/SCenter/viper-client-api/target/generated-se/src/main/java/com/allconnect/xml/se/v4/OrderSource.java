
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for orderSourceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orderSourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sourceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="program" type="{http://xml.A.com/v4}programType" minOccurs="0"/>
 *         &lt;element name="businessParty" type="{http://xml.A.com/v4}orderSourceBusinessPartyType" minOccurs="0"/>
 *         &lt;element name="telephonyList" type="{http://xml.A.com/v4}telephonyListType" minOccurs="0"/>
 *         &lt;element name="channel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="imageUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isDefault" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderSourceType", propOrder = {
    "sourceCode",
    "name",
    "program",
    "businessParty",
    "telephonyList",
    "channel",
    "imageUrl",
    "isDefault"
})
@XmlRootElement(name = "orderSource")
public class OrderSource {

    protected String sourceCode;
    protected String name;
    protected ProgramType program;
    protected OrderSourceBusinessPartyType businessParty;
    protected TelephonyListType telephonyList;
    protected String channel;
    protected String imageUrl;
    protected boolean isDefault;

    /**
     * Gets the value of the sourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceCode() {
        return sourceCode;
    }

    /**
     * Sets the value of the sourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceCode(String value) {
        this.sourceCode = value;
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
     * Gets the value of the program property.
     * 
     * @return
     *     possible object is
     *     {@link ProgramType }
     *     
     */
    public ProgramType getProgram() {
        return program;
    }

    /**
     * Sets the value of the program property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProgramType }
     *     
     */
    public void setProgram(ProgramType value) {
        this.program = value;
    }

    /**
     * Gets the value of the businessParty property.
     * 
     * @return
     *     possible object is
     *     {@link OrderSourceBusinessPartyType }
     *     
     */
    public OrderSourceBusinessPartyType getBusinessParty() {
        return businessParty;
    }

    /**
     * Sets the value of the businessParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderSourceBusinessPartyType }
     *     
     */
    public void setBusinessParty(OrderSourceBusinessPartyType value) {
        this.businessParty = value;
    }

    /**
     * Gets the value of the telephonyList property.
     * 
     * @return
     *     possible object is
     *     {@link TelephonyListType }
     *     
     */
    public TelephonyListType getTelephonyList() {
        return telephonyList;
    }

    /**
     * Sets the value of the telephonyList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelephonyListType }
     *     
     */
    public void setTelephonyList(TelephonyListType value) {
        this.telephonyList = value;
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

    /**
     * Gets the value of the imageUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the value of the imageUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageUrl(String value) {
        this.imageUrl = value;
    }

    /**
     * Gets the value of the isDefault property.
     * 
     */
    public boolean isIsDefault() {
        return isDefault;
    }

    /**
     * Sets the value of the isDefault property.
     * 
     */
    public void setIsDefault(boolean value) {
        this.isDefault = value;
    }

}
