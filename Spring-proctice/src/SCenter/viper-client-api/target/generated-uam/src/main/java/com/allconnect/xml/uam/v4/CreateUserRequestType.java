
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateUserRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateUserRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element name="createUserRequest" type="{http://xml.A.com/v4}CreateUserRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateUserRequestType", propOrder = {
    "createUserRequest"
})
public class CreateUserRequestType
    extends AbstractRequestType
{

    @XmlElement(required = true)
    protected CreateUserRequest createUserRequest;

    /**
     * Gets the value of the createUserRequest property.
     * 
     * @return
     *     possible object is
     *     {@link CreateUserRequest }
     *     
     */
    public CreateUserRequest getCreateUserRequest() {
        return createUserRequest;
    }

    /**
     * Sets the value of the createUserRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateUserRequest }
     *     
     */
    public void setCreateUserRequest(CreateUserRequest value) {
        this.createUserRequest = value;
    }

}
