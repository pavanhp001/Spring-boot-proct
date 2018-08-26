
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachResourceToUserRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachResourceToUserRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element name="attachResourceToUserRequest" type="{http://xml.A.com/v4}AttachResourceToUserRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachResourceToUserRequestType", propOrder = {
    "attachResourceToUserRequest"
})
public class AttachResourceToUserRequestType
    extends AbstractRequestType
{

    @XmlElement(required = true)
    protected AttachResourceToUserRequest attachResourceToUserRequest;

    /**
     * Gets the value of the attachResourceToUserRequest property.
     * 
     * @return
     *     possible object is
     *     {@link AttachResourceToUserRequest }
     *     
     */
    public AttachResourceToUserRequest getAttachResourceToUserRequest() {
        return attachResourceToUserRequest;
    }

    /**
     * Sets the value of the attachResourceToUserRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachResourceToUserRequest }
     *     
     */
    public void setAttachResourceToUserRequest(AttachResourceToUserRequest value) {
        this.attachResourceToUserRequest = value;
    }

}
