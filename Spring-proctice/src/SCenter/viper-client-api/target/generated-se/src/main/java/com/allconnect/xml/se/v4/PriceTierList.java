
package com.A.xml.se.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for priceTierListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="priceTierListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="priceTier" type="{http://xml.A.com/v4}priceTierType" maxOccurs="10"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "priceTierListType", propOrder = {
    "priceTiers"
})
@XmlRootElement(name = "priceTierList")
public class PriceTierList {

    @XmlElement(name = "priceTier", required = true)
    protected List<PriceTierType> priceTiers;

    /**
     * Gets the value of the priceTiers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the priceTiers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPriceTiers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PriceTierType }
     * 
     * 
     */
    public List<PriceTierType> getPriceTiers() {
        if (priceTiers == null) {
            priceTiers = new ArrayList<PriceTierType>();
        }
        return this.priceTiers;
    }

}
