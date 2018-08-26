
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 ATT STI Customer CreditInfo, Disposition and Product Scheduling Information.
 *             
 * 
 * <p>Java class for CustomerOrderInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerOrderInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderCustomizations" type="{http://xml.A.com/v4}customizationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Disposition" type="{http://xml.A.com/v4}Disposition"/>
 *         &lt;element name="ProductSchedule" type="{http://xml.A.com/v4}ProductSchedule" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerOrderInfo", propOrder = {
    "orderCustomizations",
    "disposition",
    "productSchedule"
})
public class CustomerOrderInfo {

    @XmlElement(name = "OrderCustomizations")
    protected List<CustomizationType> orderCustomizations;
    @XmlElement(name = "Disposition", required = true)
    protected Disposition disposition;
    @XmlElement(name = "ProductSchedule")
    protected List<ProductSchedule> productSchedule;

    /**
     * Gets the value of the orderCustomizations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderCustomizations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderCustomizations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomizationType }
     * 
     * 
     */
    public List<CustomizationType> getOrderCustomizations() {
        if (orderCustomizations == null) {
            orderCustomizations = new ArrayList<CustomizationType>();
        }
        return this.orderCustomizations;
    }

    /**
     * Gets the value of the disposition property.
     * 
     * @return
     *     possible object is
     *     {@link Disposition }
     *     
     */
    public Disposition getDisposition() {
        return disposition;
    }

    /**
     * Sets the value of the disposition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Disposition }
     *     
     */
    public void setDisposition(Disposition value) {
        this.disposition = value;
    }

    /**
     * Gets the value of the productSchedule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productSchedule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductSchedule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductSchedule }
     * 
     * 
     */
    public List<ProductSchedule> getProductSchedule() {
        if (productSchedule == null) {
            productSchedule = new ArrayList<ProductSchedule>();
        }
        return this.productSchedule;
    }

}
