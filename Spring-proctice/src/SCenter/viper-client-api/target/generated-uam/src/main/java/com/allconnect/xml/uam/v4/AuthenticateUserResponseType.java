
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticateUserResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticateUserResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="authenticateUserResponse" type="{http://xml.A.com/v4}AuthenticateUserResponse"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticateUserResponseType", propOrder = {
    "authenticateUserResponse"
})
public class AuthenticateUserResponseType
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected AuthenticateUserResponse authenticateUserResponse;

    /**
     * Gets the value of the authenticateUserResponse property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticateUserResponse }
     *     
     */
    public AuthenticateUserResponse getAuthenticateUserResponse() {
        return authenticateUserResponse;
    }

    /**
     * Sets the value of the authenticateUserResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticateUserResponse }
     *     
     */
    public void setAuthenticateUserResponse(AuthenticateUserResponse value) {
        this.authenticateUserResponse = value;
    }

}
