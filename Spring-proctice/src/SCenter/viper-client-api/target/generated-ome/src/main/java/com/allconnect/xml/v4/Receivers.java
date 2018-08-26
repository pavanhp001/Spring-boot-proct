
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Receivers complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Receivers">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ModelName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReceiverId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PurchaseAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Receivers", propOrder = {
    "modelName",
    "receiverId",
    "purchaseAllowed",
    "count"
})
public class Receivers {

    @XmlElement(name = "ModelName", required = true)
    protected String modelName;
    @XmlElement(name = "ReceiverId")
    protected int receiverId;
    @XmlElement(name = "PurchaseAllowed")
    protected boolean purchaseAllowed;
    protected int count;

    /**
     * Gets the value of the modelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Sets the value of the modelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelName(String value) {
        this.modelName = value;
    }

    /**
     * Gets the value of the receiverId property.
     * 
     */
    public int getReceiverId() {
        return receiverId;
    }

    /**
     * Sets the value of the receiverId property.
     * 
     */
    public void setReceiverId(int value) {
        this.receiverId = value;
    }

    /**
     * Gets the value of the purchaseAllowed property.
     * 
     */
    public boolean isPurchaseAllowed() {
        return purchaseAllowed;
    }

    /**
     * Sets the value of the purchaseAllowed property.
     * 
     */
    public void setPurchaseAllowed(boolean value) {
        this.purchaseAllowed = value;
    }

    /**
     * Gets the value of the count property.
     * 
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     */
    public void setCount(int value) {
        this.count = value;
    }

}
