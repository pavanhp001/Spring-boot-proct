
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidateUserRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidateUserRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element name="validateUserRequest" type="{http://xml.A.com/v4}ValidateUserRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateUserRequestType", propOrder = {
    "validateUserRequest"
})
public class ValidateUserRequestType
    extends AbstractRequestType
{

    @XmlElement(required = true)
    protected ValidateUserRequest validateUserRequest;

    /**
     * Gets the value of the validateUserRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ValidateUserRequest }
     *     
     */
    public ValidateUserRequest getValidateUserRequest() {
        return validateUserRequest;
    }

    /**
     * Sets the value of the validateUserRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidateUserRequest }
     *     
     */
    public void setValidateUserRequest(ValidateUserRequest value) {
        this.validateUserRequest = value;
    }

}
