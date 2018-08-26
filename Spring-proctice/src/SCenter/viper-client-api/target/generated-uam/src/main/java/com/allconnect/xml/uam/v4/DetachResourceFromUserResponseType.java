
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetachResourceFromUserResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetachResourceFromUserResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="detachResourceFromUserResponse" type="{http://xml.A.com/v4}DetachResourceFromUserResponse"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetachResourceFromUserResponseType", propOrder = {
    "detachResourceFromUserResponse"
})
public class DetachResourceFromUserResponseType
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected DetachResourceFromUserResponse detachResourceFromUserResponse;

    /**
     * Gets the value of the detachResourceFromUserResponse property.
     * 
     * @return
     *     possible object is
     *     {@link DetachResourceFromUserResponse }
     *     
     */
    public DetachResourceFromUserResponse getDetachResourceFromUserResponse() {
        return detachResourceFromUserResponse;
    }

    /**
     * Sets the value of the detachResourceFromUserResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetachResourceFromUserResponse }
     *     
     */
    public void setDetachResourceFromUserResponse(DetachResourceFromUserResponse value) {
        this.detachResourceFromUserResponse = value;
    }

}
