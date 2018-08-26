
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RtimRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RtimRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="context" type="{http://xml.A.com/common}RequestContext"/>
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
@XmlType(name = "RtimRequest", namespace = "http://xml.A.com/v4/VendorRequestResponse/", propOrder = {
    "context",
    "applicationType"
})
@XmlSeeAlso({
    RtimProvisioningRequest.class
})
public abstract class RtimRequest {

    @XmlElement(namespace = "", required = true)
    protected RequestContext context;
    @XmlElement(namespace = "", required = true)
    protected ApplicationType applicationType;

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link RequestContext }
     *     
     */
    public RequestContext getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestContext }
     *     
     */
    public void setContext(RequestContext value) {
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
