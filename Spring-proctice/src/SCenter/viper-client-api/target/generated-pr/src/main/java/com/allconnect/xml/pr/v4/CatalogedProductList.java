
package com.A.xml.pr.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="productCatalogEntry" type="{http://xml.A.com/v4}catalogEntryType" maxOccurs="unbounded"/>
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
    "productCatalogEntry"
})
@XmlRootElement(name = "catalogedProductList")
public class CatalogedProductList {

    @XmlElement(required = true)
    protected List<CatalogEntryType> productCatalogEntry;

    /**
     * Gets the value of the productCatalogEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productCatalogEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductCatalogEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CatalogEntryType }
     * 
     * 
     */
    public List<CatalogEntryType> getProductCatalogEntry() {
        if (productCatalogEntry == null) {
            productCatalogEntry = new ArrayList<CatalogEntryType>();
        }
        return this.productCatalogEntry;
    }

}
