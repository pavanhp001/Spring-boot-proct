
package com.A.xml.se.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for marketItemWithCapabilitiesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="marketItemWithCapabilitiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}coreMarketItemType">
 *       &lt;sequence>
 *         &lt;element name="item" type="{http://xml.A.com/v4}externalItemType"/>
 *         &lt;element name="capabilityList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="capability" type="{http://xml.A.com/v4}capabilityType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "marketItemWithCapabilitiesType", propOrder = {
    "item",
    "capabilityList"
})
public class MarketItemWithCapabilitiesType
    extends CoreMarketItemType
{

    @XmlElement(required = true)
    protected ExternalItemType item;
    @XmlElement(required = true)
    protected MarketItemWithCapabilitiesType.CapabilityList capabilityList;

    /**
     * Gets the value of the item property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalItemType }
     *     
     */
    public ExternalItemType getItem() {
        return item;
    }

    /**
     * Sets the value of the item property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalItemType }
     *     
     */
    public void setItem(ExternalItemType value) {
        this.item = value;
    }

    /**
     * Gets the value of the capabilityList property.
     * 
     * @return
     *     possible object is
     *     {@link MarketItemWithCapabilitiesType.CapabilityList }
     *     
     */
    public MarketItemWithCapabilitiesType.CapabilityList getCapabilityList() {
        return capabilityList;
    }

    /**
     * Sets the value of the capabilityList property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarketItemWithCapabilitiesType.CapabilityList }
     *     
     */
    public void setCapabilityList(MarketItemWithCapabilitiesType.CapabilityList value) {
        this.capabilityList = value;
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
     *         &lt;element name="capability" type="{http://xml.A.com/v4}capabilityType" maxOccurs="unbounded" minOccurs="0"/>
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
        "capabilities"
    })
    public static class CapabilityList {

        @XmlElement(name = "capability")
        protected List<CapabilityType> capabilities;

        /**
         * Gets the value of the capabilities property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the capabilities property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCapabilities().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CapabilityType }
         * 
         * 
         */
        public List<CapabilityType> getCapabilities() {
            if (capabilities == null) {
                capabilities = new ArrayList<CapabilityType>();
            }
            return this.capabilities;
        }

    }

}
