
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for applicableType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="applicableType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="appliesTo" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *         &lt;element name="isAppliesToInternal" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="promotion" type="{http://xml.A.com/v4}productPromotionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "applicableType", propOrder = {
    "appliesTo",
    "isAppliesToInternal",
    "promotion"
})
public class ApplicableType {

    @XmlElement(type = Integer.class)
    protected List<Integer> appliesTo;
    protected Boolean isAppliesToInternal;
    protected ProductPromotionType promotion;

    /**
     * Gets the value of the appliesTo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the appliesTo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAppliesTo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getAppliesTo() {
        if (appliesTo == null) {
            appliesTo = new ArrayList<Integer>();
        }
        return this.appliesTo;
    }

    /**
     * Gets the value of the isAppliesToInternal property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAppliesToInternal() {
        return isAppliesToInternal;
    }

    /**
     * Sets the value of the isAppliesToInternal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAppliesToInternal(Boolean value) {
        this.isAppliesToInternal = value;
    }

    /**
     * Gets the value of the promotion property.
     * 
     * @return
     *     possible object is
     *     {@link ProductPromotionType }
     *     
     */
    public ProductPromotionType getPromotion() {
        return promotion;
    }

    /**
     * Sets the value of the promotion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductPromotionType }
     *     
     */
    public void setPromotion(ProductPromotionType value) {
        this.promotion = value;
    }

}
