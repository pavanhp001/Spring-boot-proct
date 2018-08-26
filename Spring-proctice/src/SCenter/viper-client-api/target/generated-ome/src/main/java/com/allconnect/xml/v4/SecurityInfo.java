
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SecurityInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecurityInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PIN">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[0-9]{4}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PINHint">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="50"/>
 *               &lt;whiteSpace value="replace"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AuthorizedPersons" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="name" type="{http://xml.A.com/common}Name" maxOccurs="3"/>
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
@XmlType(name = "SecurityInfo", propOrder = {
    "pin",
    "pinHint",
    "authorizedPersons"
})
public class SecurityInfo {

    @XmlElement(name = "PIN", required = true)
    protected String pin;
    @XmlElement(name = "PINHint", required = true)
    protected String pinHint;
    @XmlElement(name = "AuthorizedPersons")
    protected SecurityInfo.AuthorizedPersons authorizedPersons;

    /**
     * Gets the value of the pin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPIN() {
        return pin;
    }

    /**
     * Sets the value of the pin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPIN(String value) {
        this.pin = value;
    }

    /**
     * Gets the value of the pinHint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPINHint() {
        return pinHint;
    }

    /**
     * Sets the value of the pinHint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPINHint(String value) {
        this.pinHint = value;
    }

    /**
     * Gets the value of the authorizedPersons property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityInfo.AuthorizedPersons }
     *     
     */
    public SecurityInfo.AuthorizedPersons getAuthorizedPersons() {
        return authorizedPersons;
    }

    /**
     * Sets the value of the authorizedPersons property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityInfo.AuthorizedPersons }
     *     
     */
    public void setAuthorizedPersons(SecurityInfo.AuthorizedPersons value) {
        this.authorizedPersons = value;
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
     *         &lt;element name="name" type="{http://xml.A.com/common}Name" maxOccurs="3"/>
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
        "name"
    })
    public static class AuthorizedPersons {

        @XmlElement(required = true)
        protected List<Name> name;

        /**
         * Gets the value of the name property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the name property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Name }
         * 
         * 
         */
        public List<Name> getName() {
            if (name == null) {
                name = new ArrayList<Name>();
            }
            return this.name;
        }

    }

}
