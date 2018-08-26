
package com.A.xml.se.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for providerCriteriaListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="providerCriteriaListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="provider" type="{http://xml.A.com/v4}providerCriteriaListEntityType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "providerCriteriaListType", propOrder = {
    "providers"
})
public class ProviderCriteriaListType {

    @XmlElement(name = "provider", required = true)
    protected List<ProviderCriteriaListEntityType> providers;

    /**
     * Gets the value of the providers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the providers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProviders().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProviderCriteriaListEntityType }
     * 
     * 
     */
    public List<ProviderCriteriaListEntityType> getProviders() {
        if (providers == null) {
            providers = new ArrayList<ProviderCriteriaListEntityType>();
        }
        return this.providers;
    }

}
