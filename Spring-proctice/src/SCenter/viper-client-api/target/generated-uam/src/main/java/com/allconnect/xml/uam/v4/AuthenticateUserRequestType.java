
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticateUserRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticateUserRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element name="authenticateUserRequest" type="{http://xml.A.com/v4}AuthenticateUserRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticateUserRequestType", propOrder = {
    "authenticateUserRequest"
})
public class AuthenticateUserRequestType
    extends AbstractRequestType
{

    @XmlElement(required = true)
    protected AuthenticateUserRequest authenticateUserRequest;

    /**
     * Gets the value of the authenticateUserRequest property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticateUserRequest }
     *     
     */
    public AuthenticateUserRequest getAuthenticateUserRequest() {
        return authenticateUserRequest;
    }

    /**
     * Sets the value of the authenticateUserRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticateUserRequest }
     *     
     */
    public void setAuthenticateUserRequest(AuthenticateUserRequest value) {
        this.authenticateUserRequest = value;
    }

}
