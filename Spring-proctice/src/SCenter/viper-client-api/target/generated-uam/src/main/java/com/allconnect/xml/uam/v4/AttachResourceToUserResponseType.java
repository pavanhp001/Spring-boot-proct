
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachResourceToUserResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachResourceToUserResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="attachResourceToUserResponse" type="{http://xml.A.com/v4}AttachResourceToUserResponse"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachResourceToUserResponseType", propOrder = {
    "attachResourceToUserResponse"
})
public class AttachResourceToUserResponseType
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected AttachResourceToUserResponse attachResourceToUserResponse;

    /**
     * Gets the value of the attachResourceToUserResponse property.
     * 
     * @return
     *     possible object is
     *     {@link AttachResourceToUserResponse }
     *     
     */
    public AttachResourceToUserResponse getAttachResourceToUserResponse() {
        return attachResourceToUserResponse;
    }

    /**
     * Sets the value of the attachResourceToUserResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachResourceToUserResponse }
     *     
     */
    public void setAttachResourceToUserResponse(AttachResourceToUserResponse value) {
        this.attachResourceToUserResponse = value;
    }

}
