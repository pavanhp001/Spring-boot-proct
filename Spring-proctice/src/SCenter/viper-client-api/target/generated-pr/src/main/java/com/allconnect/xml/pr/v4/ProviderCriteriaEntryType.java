
package com.A.xml.pr.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for providerCriteriaEntryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="providerCriteriaEntryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="criteriaName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="criteriaValue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "providerCriteriaEntryType")
public class ProviderCriteriaEntryType {

    @XmlAttribute(name = "criteriaName", required = true)
    protected String criteriaName;
    @XmlAttribute(name = "criteriaValue")
    protected String criteriaValue;

    /**
     * Gets the value of the criteriaName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCriteriaName() {
        return criteriaName;
    }

    /**
     * Sets the value of the criteriaName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCriteriaName(String value) {
        this.criteriaName = value;
    }

    /**
     * Gets the value of the criteriaValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCriteriaValue() {
        return criteriaValue;
    }

    /**
     * Sets the value of the criteriaValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCriteriaValue(String value) {
        this.criteriaValue = value;
    }

}
