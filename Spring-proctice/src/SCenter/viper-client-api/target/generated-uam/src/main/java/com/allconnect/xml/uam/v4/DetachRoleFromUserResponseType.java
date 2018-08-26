
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetachRoleFromUserResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetachRoleFromUserResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="detachRoleFromUserResponse" type="{http://xml.A.com/v4}DetachRoleFromUserResponse"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetachRoleFromUserResponseType", propOrder = {
    "detachRoleFromUserResponse"
})
public class DetachRoleFromUserResponseType
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected DetachRoleFromUserResponse detachRoleFromUserResponse;

    /**
     * Gets the value of the detachRoleFromUserResponse property.
     * 
     * @return
     *     possible object is
     *     {@link DetachRoleFromUserResponse }
     *     
     */
    public DetachRoleFromUserResponse getDetachRoleFromUserResponse() {
        return detachRoleFromUserResponse;
    }

    /**
     * Sets the value of the detachRoleFromUserResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetachRoleFromUserResponse }
     *     
     */
    public void setDetachRoleFromUserResponse(DetachRoleFromUserResponse value) {
        this.detachRoleFromUserResponse = value;
    }

}
