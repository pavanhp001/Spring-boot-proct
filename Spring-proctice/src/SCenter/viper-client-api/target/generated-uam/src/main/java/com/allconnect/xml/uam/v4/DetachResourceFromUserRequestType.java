
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetachResourceFromUserRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetachResourceFromUserRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element name="detachResourceFromUserRequest" type="{http://xml.A.com/v4}DetachResourceFromUserRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetachResourceFromUserRequestType", propOrder = {
    "detachResourceFromUserRequest"
})
public class DetachResourceFromUserRequestType
    extends AbstractRequestType
{

    @XmlElement(required = true)
    protected DetachResourceFromUserRequest detachResourceFromUserRequest;

    /**
     * Gets the value of the detachResourceFromUserRequest property.
     * 
     * @return
     *     possible object is
     *     {@link DetachResourceFromUserRequest }
     *     
     */
    public DetachResourceFromUserRequest getDetachResourceFromUserRequest() {
        return detachResourceFromUserRequest;
    }

    /**
     * Sets the value of the detachResourceFromUserRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetachResourceFromUserRequest }
     *     
     */
    public void setDetachResourceFromUserRequest(DetachResourceFromUserRequest value) {
        this.detachResourceFromUserRequest = value;
    }

}
