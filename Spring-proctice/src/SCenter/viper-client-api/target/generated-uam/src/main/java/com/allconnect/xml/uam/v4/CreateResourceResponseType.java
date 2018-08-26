
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateResourceResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateResourceResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="createResourceResponse" type="{http://xml.A.com/v4}CreateResourceResponse"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateResourceResponseType", propOrder = {
    "createResourceResponse"
})
public class CreateResourceResponseType
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected CreateResourceResponse createResourceResponse;

    /**
     * Gets the value of the createResourceResponse property.
     * 
     * @return
     *     possible object is
     *     {@link CreateResourceResponse }
     *     
     */
    public CreateResourceResponse getCreateResourceResponse() {
        return createResourceResponse;
    }

    /**
     * Sets the value of the createResourceResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateResourceResponse }
     *     
     */
    public void setCreateResourceResponse(CreateResourceResponse value) {
        this.createResourceResponse = value;
    }

}
