
package com.A.xml.se.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for selectedDialogsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="selectedDialogsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dialogs" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dialog" type="{http://xml.A.com/v4}dialogValueType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "selectedDialogsType", propOrder = {
    "dialogs"
})
@XmlRootElement(name = "dialog")
public class Dialog {

    protected Dialog.Dialogs dialogs;

    /**
     * Gets the value of the dialogs property.
     * 
     * @return
     *     possible object is
     *     {@link Dialog.Dialogs }
     *     
     */
    public Dialog.Dialogs getDialogs() {
        return dialogs;
    }

    /**
     * Sets the value of the dialogs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dialog.Dialogs }
     *     
     */
    public void setDialogs(Dialog.Dialogs value) {
        this.dialogs = value;
    }


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
     *         &lt;element name="dialog" type="{http://xml.A.com/v4}dialogValueType" maxOccurs="unbounded" minOccurs="0"/>
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
        "dialogs"
    })
    public static class Dialogs {

        @XmlElement(name = "dialog")
        protected List<DialogValueType> dialogs;

        /**
         * Gets the value of the dialogs property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dialogs property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDialogs().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DialogValueType }
         * 
         * 
         */
        public List<DialogValueType> getDialogs() {
            if (dialogs == null) {
                dialogs = new ArrayList<DialogValueType>();
            }
            return this.dialogs;
        }

    }

}
