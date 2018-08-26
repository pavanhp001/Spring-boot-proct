
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MasterOrderNumberResponseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MasterOrderNumberResponseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MasterOrderNumber" type="{http://xml.A.com/v4}ArrayOfMON" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MasterOrderNumberResponseInfo", propOrder = {
    "code",
    "message",
    "masterOrderNumber"
})
public class MasterOrderNumberResponseInfo {

    protected String code;
    protected String message;
    @XmlElement(name = "MasterOrderNumber")
    protected ArrayOfMON masterOrderNumber;

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
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the masterOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMON }
     *     
     */
    public ArrayOfMON getMasterOrderNumber() {
        return masterOrderNumber;
    }

    /**
     * Sets the value of the masterOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMON }
     *     
     */
    public void setMasterOrderNumber(ArrayOfMON value) {
        this.masterOrderNumber = value;
    }

}
