
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RtimResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RtimResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rtimErrors" type="{http://xml.A.com/common}RtimErrors"/>
 *         &lt;element name="responseStatus" type="{http://xml.A.com/common}OpResponseStatus" minOccurs="0"/>
 *         &lt;element name="context" type="{http://xml.A.com/common}ResponseContext"/>
 *         &lt;element name="applicationType" type="{http://xml.A.com/common}ApplicationType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RtimResponse", namespace = "http://xml.A.com/v4/VendorRequestResponse/", propOrder = {
    "rtimErrors",
    "responseStatus",
    "context",
    "applicationType"
})
@XmlSeeAlso({
    RtimProvisioningResponse.class
})
public abstract class RtimResponse {

    @XmlElement(namespace = "", required = true)
    protected RtimErrors rtimErrors;
    @XmlElement(namespace = "")
    protected OpResponseStatus responseStatus;
    @XmlElement(namespace = "", required = true)
    protected ResponseContext context;
    @XmlElement(namespace = "", required = true)
    protected ApplicationType applicationType;

    /**
     * Gets the value of the rtimErrors property.
     * 
     * @return
     *     possible object is
     *     {@link RtimErrors }
     *     
     */
    public RtimErrors getRtimErrors() {
        return rtimErrors;
    }

    /**
     * Sets the value of the rtimErrors property.
     * 
     * @param value
     *     allowed object is
     *     {@link RtimErrors }
     *     
     */
    public void setRtimErrors(RtimErrors value) {
        this.rtimErrors = value;
    }

    /**
     * Gets the value of the responseStatus property.
     * 
     * @return
     *     possible object is
     *     {@link OpResponseStatus }
     *     
     */
    public OpResponseStatus getResponseStatus() {
        return responseStatus;
    }

    /**
     * Sets the value of the responseStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpResponseStatus }
     *     
     */
    public void setResponseStatus(OpResponseStatus value) {
        this.responseStatus = value;
    }

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseContext }
     *     
     */
    public ResponseContext getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseContext }
     *     
     */
    public void setContext(ResponseContext value) {
        this.context = value;
    }

    /**
     * Gets the value of the applicationType property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationType }
     *     
     */
    public ApplicationType getApplicationType() {
        return applicationType;
    }

    /**
     * Sets the value of the applicationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationType }
     *     
     */
    public void setApplicationType(ApplicationType value) {
        this.applicationType = value;
    }

}
