
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reasonList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reasonList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reasonDesc" type="{http://xml.A.com/v4}reasonDesc"/>
 *         &lt;element name="reasonType" type="{http://xml.A.com/v4}reasonType"/>
 *         &lt;element name="reasonCategory" type="{http://xml.A.com/v4}reasonCategory"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reasonList", propOrder = {
    "reasonDesc",
    "reasonType",
    "reasonCategory",
    "priority"
})
public class ReasonList {

    @XmlElement(required = true)
    protected ReasonDesc reasonDesc;
    @XmlElement(required = true)
    protected ReasonType reasonType;
    @XmlElement(required = true)
    protected ReasonCategory reasonCategory;
    protected int priority;

    /**
     * Gets the value of the reasonDesc property.
     * 
     * @return
     *     possible object is
     *     {@link ReasonDesc }
     *     
     */
    public ReasonDesc getReasonDesc() {
        return reasonDesc;
    }

    /**
     * Sets the value of the reasonDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReasonDesc }
     *     
     */
    public void setReasonDesc(ReasonDesc value) {
        this.reasonDesc = value;
    }

    /**
     * Gets the value of the reasonType property.
     * 
     * @return
     *     possible object is
     *     {@link ReasonType }
     *     
     */
    public ReasonType getReasonType() {
        return reasonType;
    }

    /**
     * Sets the value of the reasonType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReasonType }
     *     
     */
    public void setReasonType(ReasonType value) {
        this.reasonType = value;
    }

    /**
     * Gets the value of the reasonCategory property.
     * 
     * @return
     *     possible object is
     *     {@link ReasonCategory }
     *     
     */
    public ReasonCategory getReasonCategory() {
        return reasonCategory;
    }

    /**
     * Sets the value of the reasonCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReasonCategory }
     *     
     */
    public void setReasonCategory(ReasonCategory value) {
        this.reasonCategory = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     */
    public void setPriority(int value) {
        this.priority = value;
    }

}
