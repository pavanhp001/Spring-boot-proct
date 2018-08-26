
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateRoleRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateRoleRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element name="createRoleRequest" type="{http://xml.A.com/v4}CreateRoleRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateRoleRequestType", propOrder = {
    "createRoleRequest"
})
public class CreateRoleRequestType
    extends AbstractRequestType
{

    @XmlElement(required = true)
    protected CreateRoleRequest createRoleRequest;

    /**
     * Gets the value of the createRoleRequest property.
     * 
     * @return
     *     possible object is
     *     {@link CreateRoleRequest }
     *     
     */
    public CreateRoleRequest getCreateRoleRequest() {
        return createRoleRequest;
    }

    /**
     * Sets the value of the createRoleRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateRoleRequest }
     *     
     */
    public void setCreateRoleRequest(CreateRoleRequest value) {
        this.createRoleRequest = value;
    }

}
