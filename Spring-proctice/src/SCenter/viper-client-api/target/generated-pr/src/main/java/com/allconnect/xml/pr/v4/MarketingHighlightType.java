
package com.A.xml.pr.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for marketingHighlightType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="marketingHighlightType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="marketingHighlight" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="10" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "marketingHighlightType", propOrder = {
    "marketingHighlight"
})
public class MarketingHighlightType {

    @XmlElement(nillable = true)
    protected List<String> marketingHighlight;

    /**
     * Gets the value of the marketingHighlight property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the marketingHighlight property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMarketingHighlight().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMarketingHighlight() {
        if (marketingHighlight == null) {
            marketingHighlight = new ArrayList<String>();
        }
        return this.marketingHighlight;
    }

}
