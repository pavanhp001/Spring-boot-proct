
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateUserResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateUserResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="createUserResponse" type="{http://xml.A.com/v4}CreateUserResponse"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateUserResponseType", propOrder = {
    "createUserResponse"
})
public class CreateUserResponseType
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected CreateUserResponse createUserResponse;

    /**
     * Gets the value of the createUserResponse property.
     * 
     * @return
     *     possible object is
     *     {@link CreateUserResponse }
     *     
     */
    public CreateUserResponse getCreateUserResponse() {
        return createUserResponse;
    }

    /**
     * Sets the value of the createUserResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateUserResponse }
     *     
     */
    public void setCreateUserResponse(CreateUserResponse value) {
        this.createUserResponse = value;
    }

}
