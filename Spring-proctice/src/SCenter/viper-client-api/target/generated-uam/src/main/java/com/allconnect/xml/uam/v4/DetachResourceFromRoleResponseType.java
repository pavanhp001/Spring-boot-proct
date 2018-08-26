
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetachResourceFromRoleResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetachResourceFromRoleResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="detachResourceFromRoleResponse" type="{http://xml.A.com/v4}DetachResourceFromRoleResponse"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetachResourceFromRoleResponseType", propOrder = {
    "detachResourceFromRoleResponse"
})
public class DetachResourceFromRoleResponseType
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected DetachResourceFromRoleResponse detachResourceFromRoleResponse;

    /**
     * Gets the value of the detachResourceFromRoleResponse property.
     * 
     * @return
     *     possible object is
     *     {@link DetachResourceFromRoleResponse }
     *     
     */
    public DetachResourceFromRoleResponse getDetachResourceFromRoleResponse() {
        return detachResourceFromRoleResponse;
    }

    /**
     * Sets the value of the detachResourceFromRoleResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetachResourceFromRoleResponse }
     *     
     */
    public void setDetachResourceFromRoleResponse(DetachResourceFromRoleResponse value) {
        this.detachResourceFromRoleResponse = value;
    }

}
