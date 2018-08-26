
package com.A.xml.se.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for providerCriteriaListEntityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="providerCriteriaListEntityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="criteria" type="{http://xml.A.com/v4}providerCriteriaEntryType" maxOccurs="unbounded"/>
 *         &lt;element name="phone" type="{http://xml.A.com/v4}customerPhoneEntry" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "providerCriteriaListEntityType", propOrder = {
    "criterias",
    "phone"
})
public class ProviderCriteriaListEntityType {

    @XmlElement(name = "criteria", required = true)
    protected List<ProviderCriteriaEntryType> criterias;
    protected CustomerPhoneEntry phone;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Gets the value of the criterias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the criterias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCriterias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProviderCriteriaEntryType }
     * 
     * 
     */
    public List<ProviderCriteriaEntryType> getCriterias() {
        if (criterias == null) {
            criterias = new ArrayList<ProviderCriteriaEntryType>();
        }
        return this.criterias;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerPhoneEntry }
     *     
     */
    public CustomerPhoneEntry getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerPhoneEntry }
     *     
     */
    public void setPhone(CustomerPhoneEntry value) {
        this.phone = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
