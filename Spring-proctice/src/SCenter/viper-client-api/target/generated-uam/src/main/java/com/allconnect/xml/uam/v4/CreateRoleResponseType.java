
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateRoleResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateRoleResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="createRoleResponse" type="{http://xml.A.com/v4}CreateRoleResponse"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateRoleResponseType", propOrder = {
    "createRoleResponse"
})
public class CreateRoleResponseType
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected CreateRoleResponse createRoleResponse;

    /**
     * Gets the value of the createRoleResponse property.
     * 
     * @return
     *     possible object is
     *     {@link CreateRoleResponse }
     *     
     */
    public CreateRoleResponse getCreateRoleResponse() {
        return createRoleResponse;
    }

    /**
     * Sets the value of the createRoleResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateRoleResponse }
     *     
     */
    public void setCreateRoleResponse(CreateRoleResponse value) {
        this.createRoleResponse = value;
    }

}
