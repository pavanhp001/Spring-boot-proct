
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for linkableType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="linkableType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}productType">
 *       &lt;sequence>
 *         &lt;element name="appliesToProduct" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="isAppliesToInternalProduct" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "linkableType", propOrder = {
    "appliesToProduct",
    "isAppliesToInternalProduct"
})
public class LinkableType
    extends ProductType
{

    @XmlElement(type = Integer.class)
    protected List<Integer> appliesToProduct;
    protected Boolean isAppliesToInternalProduct;

    /**
     * Gets the value of the appliesToProduct property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the appliesToProduct property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAppliesToProduct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getAppliesToProduct() {
        if (appliesToProduct == null) {
            appliesToProduct = new ArrayList<Integer>();
        }
        return this.appliesToProduct;
    }

    /**
     * Gets the value of the isAppliesToInternalProduct property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAppliesToInternalProduct() {
        return isAppliesToInternalProduct;
    }

    /**
     * Sets the value of the isAppliesToInternalProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAppliesToInternalProduct(Boolean value) {
        this.isAppliesToInternalProduct = value;
    }

}
