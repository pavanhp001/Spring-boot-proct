
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Disposition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Disposition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MasterState" type="{http://xml.A.com/v4}OrderState"/>
 *         &lt;element name="State" type="{http://xml.A.com/v4}OrderState" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Disposition", propOrder = {
    "masterState",
    "state"
})
public class Disposition {

    @XmlElement(name = "MasterState", required = true)
    protected OrderState masterState;
    @XmlElement(name = "State", required = true)
    protected List<OrderState> state;

    /**
     * Gets the value of the masterState property.
     * 
     * @return
     *     possible object is
     *     {@link OrderState }
     *     
     */
    public OrderState getMasterState() {
        return masterState;
    }

    /**
     * Sets the value of the masterState property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderState }
     *     
     */
    public void setMasterState(OrderState value) {
        this.masterState = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the state property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getState().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderState }
     * 
     * 
     */
    public List<OrderState> getState() {
        if (state == null) {
            state = new ArrayList<OrderState>();
        }
        return this.state;
    }

}
