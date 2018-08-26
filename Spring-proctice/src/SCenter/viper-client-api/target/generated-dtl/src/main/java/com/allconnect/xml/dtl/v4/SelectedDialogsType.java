
package com.A.xml.dtl.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
public class SelectedDialogsType {

    protected SelectedDialogsType.Dialogs dialogs;

    /**
     * Gets the value of the dialogs property.
     * 
     * @return
     *     possible object is
     *     {@link SelectedDialogsType.Dialogs }
     *     
     */
    public SelectedDialogsType.Dialogs getDialogs() {
        return dialogs;
    }

    /**
     * Sets the value of the dialogs property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelectedDialogsType.Dialogs }
     *     
     */
    public void setDialogs(SelectedDialogsType.Dialogs value) {
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
        "dialog"
    })
    public static class Dialogs {

        protected List<DialogValueType> dialog;

        /**
         * Gets the value of the dialog property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dialog property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDialog().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DialogValueType }
         * 
         * 
         */
        public List<DialogValueType> getDialog() {
            if (dialog == null) {
                dialog = new ArrayList<DialogValueType>();
            }
            return this.dialog;
        }

    }

}
