
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateResourceRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateResourceRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resourceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resourceDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resourceRule" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="providerExternalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateResourceRequest", propOrder = {
    "resourceName",
    "resourceDesc",
    "resourceRule",
    "providerExternalId"
})
public class CreateResourceRequest {

    @XmlElement(required = true)
    protected String resourceName;
    @XmlElement(required = true)
    protected String resourceDesc;
    @XmlElement(required = true)
    protected String resourceRule;
    @XmlElement(required = true)
    protected String providerExternalId;

    /**
     * Gets the value of the resourceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Sets the value of the resourceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceName(String value) {
        this.resourceName = value;
    }

    /**
     * Gets the value of the resourceDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceDesc() {
        return resourceDesc;
    }

    /**
     * Sets the value of the resourceDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceDesc(String value) {
        this.resourceDesc = value;
    }

    /**
     * Gets the value of the resourceRule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceRule() {
        return resourceRule;
    }

    /**
     * Sets the value of the resourceRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceRule(String value) {
        this.resourceRule = value;
    }

    /**
     * Gets the value of the providerExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderExternalId() {
        return providerExternalId;
    }

    /**
     * Sets the value of the providerExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderExternalId(String value) {
        this.providerExternalId = value;
    }

}
