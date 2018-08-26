
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidateUserResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidateUserResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="validateUserResponse" type="{http://xml.A.com/v4}ValidateUserResponse"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateUserResponseType", propOrder = {
    "validateUserResponse"
})
public class ValidateUserResponseType
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected ValidateUserResponse validateUserResponse;

    /**
     * Gets the value of the validateUserResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ValidateUserResponse }
     *     
     */
    public ValidateUserResponse getValidateUserResponse() {
        return validateUserResponse;
    }

    /**
     * Sets the value of the validateUserResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidateUserResponse }
     *     
     */
    public void setValidateUserResponse(ValidateUserResponse value) {
        this.validateUserResponse = value;
    }

}
