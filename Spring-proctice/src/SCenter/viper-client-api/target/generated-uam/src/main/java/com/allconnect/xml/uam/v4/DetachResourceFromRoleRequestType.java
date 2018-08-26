
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetachResourceFromRoleRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetachResourceFromRoleRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element name="detachResourceFromRoleRequest" type="{http://xml.A.com/v4}DetachResourceFromRoleRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetachResourceFromRoleRequestType", propOrder = {
    "detachResourceFromRoleRequest"
})
public class DetachResourceFromRoleRequestType
    extends AbstractRequestType
{

    @XmlElement(required = true)
    protected DetachResourceFromRoleRequest detachResourceFromRoleRequest;

    /**
     * Gets the value of the detachResourceFromRoleRequest property.
     * 
     * @return
     *     possible object is
     *     {@link DetachResourceFromRoleRequest }
     *     
     */
    public DetachResourceFromRoleRequest getDetachResourceFromRoleRequest() {
        return detachResourceFromRoleRequest;
    }

    /**
     * Sets the value of the detachResourceFromRoleRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetachResourceFromRoleRequest }
     *     
     */
    public void setDetachResourceFromRoleRequest(DetachResourceFromRoleRequest value) {
        this.detachResourceFromRoleRequest = value;
    }

}
