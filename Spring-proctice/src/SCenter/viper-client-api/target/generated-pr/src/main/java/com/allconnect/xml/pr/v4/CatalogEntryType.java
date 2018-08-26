
package com.A.xml.pr.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for catalogEntryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="catalogEntryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="status">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="success" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="errorDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="catalogId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="externalId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="providerExternalId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="instanceId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catalogEntryType", propOrder = {
    "status"
})
public class CatalogEntryType {

    @XmlElement(required = true)
    protected CatalogEntryType.Status status;
    @XmlAttribute(name = "catalogId", required = true)
    protected String catalogId;
    @XmlAttribute(name = "externalId", required = true)
    protected String externalId;
    @XmlAttribute(name = "providerExternalId", required = true)
    protected String providerExternalId;
    @XmlAttribute(name = "instanceId", required = true)
    protected String instanceId;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link CatalogEntryType.Status }
     *     
     */
    public CatalogEntryType.Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link CatalogEntryType.Status }
     *     
     */
    public void setStatus(CatalogEntryType.Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the catalogId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCatalogId() {
        return catalogId;
    }

    /**
     * Sets the value of the catalogId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCatalogId(String value) {
        this.catalogId = value;
    }

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

    /**
     * Gets the value of the instanceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * Sets the value of the instanceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstanceId(String value) {
        this.instanceId = value;
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
     *       &lt;attribute name="success" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="errorDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Status {

        @XmlAttribute(name = "success", required = true)
        protected boolean success;
        @XmlAttribute(name = "errorCode")
        protected String errorCode;
        @XmlAttribute(name = "errorDescription")
        protected String errorDescription;

        /**
         * Gets the value of the success property.
         * 
         */
        public boolean isSuccess() {
            return success;
        }

        /**
         * Sets the value of the success property.
         * 
         */
        public void setSuccess(boolean value) {
            this.success = value;
        }

        /**
         * Gets the value of the errorCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getErrorCode() {
            return errorCode;
        }

        /**
         * Sets the value of the errorCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setErrorCode(String value) {
            this.errorCode = value;
        }

        /**
         * Gets the value of the errorDescription property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getErrorDescription() {
            return errorDescription;
        }

        /**
         * Sets the value of the errorDescription property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setErrorDescription(String value) {
            this.errorDescription = value;
        }

    }

}
