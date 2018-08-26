
package com.A.xml.pr.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for preconditionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="preconditionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requiredFeatures" type="{http://xml.A.com/v4}requiredFeatureType" minOccurs="0"/>
 *         &lt;element name="conflicts" type="{http://xml.A.com/v4}promotionConflictsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "preconditionsType", propOrder = {
    "requiredFeatures",
    "conflicts"
})
public class PreconditionsType {

    protected RequiredFeatureType requiredFeatures;
    protected PromotionConflictsType conflicts;

    /**
     * Gets the value of the requiredFeatures property.
     * 
     * @return
     *     possible object is
     *     {@link RequiredFeatureType }
     *     
     */
    public RequiredFeatureType getRequiredFeatures() {
        return requiredFeatures;
    }

    /**
     * Sets the value of the requiredFeatures property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequiredFeatureType }
     *     
     */
    public void setRequiredFeatures(RequiredFeatureType value) {
        this.requiredFeatures = value;
    }

    /**
     * Gets the value of the conflicts property.
     * 
     * @return
     *     possible object is
     *     {@link PromotionConflictsType }
     *     
     */
    public PromotionConflictsType getConflicts() {
        return conflicts;
    }

    /**
     * Sets the value of the conflicts property.
     * 
     * @param value
     *     allowed object is
     *     {@link PromotionConflictsType }
     *     
     */
    public void setConflicts(PromotionConflictsType value) {
        this.conflicts = value;
    }

}
