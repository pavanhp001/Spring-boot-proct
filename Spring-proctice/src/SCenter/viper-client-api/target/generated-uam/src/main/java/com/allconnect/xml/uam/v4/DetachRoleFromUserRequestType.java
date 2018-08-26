
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetachRoleFromUserRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetachRoleFromUserRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element name="detachRoleFromUserRequest" type="{http://xml.A.com/v4}DetachRoleFromUserRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetachRoleFromUserRequestType", propOrder = {
    "detachRoleFromUserRequest"
})
public class DetachRoleFromUserRequestType
    extends AbstractRequestType
{

    @XmlElement(required = true)
    protected DetachRoleFromUserRequest detachRoleFromUserRequest;

    /**
     * Gets the value of the detachRoleFromUserRequest property.
     * 
     * @return
     *     possible object is
     *     {@link DetachRoleFromUserRequest }
     *     
     */
    public DetachRoleFromUserRequest getDetachRoleFromUserRequest() {
        return detachRoleFromUserRequest;
    }

    /**
     * Sets the value of the detachRoleFromUserRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetachRoleFromUserRequest }
     *     
     */
    public void setDetachRoleFromUserRequest(DetachRoleFromUserRequest value) {
        this.detachRoleFromUserRequest = value;
    }

}
