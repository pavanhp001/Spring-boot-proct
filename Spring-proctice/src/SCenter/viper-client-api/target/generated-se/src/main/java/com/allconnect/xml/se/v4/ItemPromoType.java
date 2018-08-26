
package com.A.xml.se.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for itemPromoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="itemPromoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestedFor" type="{http://xml.A.com/v4}promotionElementType"/>
 *         &lt;sequence>
 *           &lt;element name="promotion" type="{http://xml.A.com/v4}promotionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itemPromoType", propOrder = {
    "requestedFor",
    "promotions"
})
public class ItemPromoType {

    @XmlElement(required = true)
    protected PromotionElementType requestedFor;
    @XmlElement(name = "promotion")
    protected List<PromotionType> promotions;

    /**
     * Gets the value of the requestedFor property.
     * 
     * @return
     *     possible object is
     *     {@link PromotionElementType }
     *     
     */
    public PromotionElementType getRequestedFor() {
        return requestedFor;
    }

    /**
     * Sets the value of the requestedFor property.
     * 
     * @param value
     *     allowed object is
     *     {@link PromotionElementType }
     *     
     */
    public void setRequestedFor(PromotionElementType value) {
        this.requestedFor = value;
    }

    /**
     * Gets the value of the promotions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the promotions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPromotions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PromotionType }
     * 
     * 
     */
    public List<PromotionType> getPromotions() {
        if (promotions == null) {
            promotions = new ArrayList<PromotionType>();
        }
        return this.promotions;
    }

}
