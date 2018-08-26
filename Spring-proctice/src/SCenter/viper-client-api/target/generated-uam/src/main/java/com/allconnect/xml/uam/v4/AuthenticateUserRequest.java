
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticateUserRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticateUserRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="credentials" type="{http://xml.A.com/v4}Credential"/>
 *         &lt;element name="superVisorUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="superVisorCredentials" type="{http://xml.A.com/v4}Credential" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticateUserRequest", propOrder = {
    "userName",
    "credentials",
    "superVisorUserName",
    "superVisorCredentials"
})
public class AuthenticateUserRequest {

    @XmlElement(required = true)
    protected String userName;
    @XmlElement(required = true)
    protected Credential credentials;
    protected String superVisorUserName;
    protected Credential superVisorCredentials;

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the credentials property.
     * 
     * @return
     *     possible object is
     *     {@link Credential }
     *     
     */
    public Credential getCredentials() {
        return credentials;
    }

    /**
     * Sets the value of the credentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link Credential }
     *     
     */
    public void setCredentials(Credential value) {
        this.credentials = value;
    }

    /**
     * Gets the value of the superVisorUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuperVisorUserName() {
        return superVisorUserName;
    }

    /**
     * Sets the value of the superVisorUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuperVisorUserName(String value) {
        this.superVisorUserName = value;
    }

    /**
     * Gets the value of the superVisorCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link Credential }
     *     
     */
    public Credential getSuperVisorCredentials() {
        return superVisorCredentials;
    }

    /**
     * Sets the value of the superVisorCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link Credential }
     *     
     */
    public void setSuperVisorCredentials(Credential value) {
        this.superVisorCredentials = value;
    }

}
