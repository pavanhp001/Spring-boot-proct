
package com.A.xml.se.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for referenceResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="referenceResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestedFor" type="{http://xml.A.com/v4}detailElementType"/>
 *         &lt;sequence>
 *           &lt;choice>
 *             &lt;element name="itemCategory" type="{http://xml.A.com/v4}itemCategory" maxOccurs="unbounded"/>
 *             &lt;element name="referrers">
 *               &lt;complexType>
 *                 &lt;complexContent>
 *                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                     &lt;sequence>
 *                       &lt;element name="referrer" type="{http://xml.A.com/v4}businessPartyDetailType" maxOccurs="unbounded"/>
 *                     &lt;/sequence>
 *                   &lt;/restriction>
 *                 &lt;/complexContent>
 *               &lt;/complexType>
 *             &lt;/element>
 *           &lt;/choice>
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
@XmlType(name = "referenceResultType", propOrder = {
    "requestedFor",
    "referrers",
    "itemCategories"
})
public class ReferenceResultType {

    @XmlElement(required = true)
    protected DetailElementType requestedFor;
    protected ReferenceResultType.Referrers referrers;
    @XmlElement(name = "itemCategory")
    protected List<ItemCategory> itemCategories;

    /**
     * Gets the value of the requestedFor property.
     * 
     * @return
     *     possible object is
     *     {@link DetailElementType }
     *     
     */
    public DetailElementType getRequestedFor() {
        return requestedFor;
    }

    /**
     * Sets the value of the requestedFor property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailElementType }
     *     
     */
    public void setRequestedFor(DetailElementType value) {
        this.requestedFor = value;
    }

    /**
     * Gets the value of the referrers property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceResultType.Referrers }
     *     
     */
    public ReferenceResultType.Referrers getReferrers() {
        return referrers;
    }

    /**
     * Sets the value of the referrers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceResultType.Referrers }
     *     
     */
    public void setReferrers(ReferenceResultType.Referrers value) {
        this.referrers = value;
    }

    /**
     * Gets the value of the itemCategories property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemCategories property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemCategories().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemCategory }
     * 
     * 
     */
    public List<ItemCategory> getItemCategories() {
        if (itemCategories == null) {
            itemCategories = new ArrayList<ItemCategory>();
        }
        return this.itemCategories;
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
     *       &lt;sequence>
     *         &lt;element name="referrer" type="{http://xml.A.com/v4}businessPartyDetailType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "referrers"
    })
    public static class Referrers {

        @XmlElement(name = "referrer", required = true)
        protected List<BusinessPartyDetailType> referrers;

        /**
         * Gets the value of the referrers property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the referrers property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReferrers().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BusinessPartyDetailType }
         * 
         * 
         */
        public List<BusinessPartyDetailType> getReferrers() {
            if (referrers == null) {
                referrers = new ArrayList<BusinessPartyDetailType>();
            }
            return this.referrers;
        }

    }

}
