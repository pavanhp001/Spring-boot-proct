
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateResourceRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateResourceRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractRequestType">
 *       &lt;sequence>
 *         &lt;element name="createResourceRequest" type="{http://xml.A.com/v4}CreateResourceRequest"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateResourceRequestType", propOrder = {
    "createResourceRequest"
})
public class CreateResourceRequestType
    extends AbstractRequestType
{

    @XmlElement(required = true)
    protected CreateResourceRequest createResourceRequest;

    /**
     * Gets the value of the createResourceRequest property.
     * 
     * @return
     *     possible object is
     *     {@link CreateResourceRequest }
     *     
     */
    public CreateResourceRequest getCreateResourceRequest() {
        return createResourceRequest;
    }

    /**
     * Sets the value of the createResourceRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateResourceRequest }
     *     
     */
    public void setCreateResourceRequest(CreateResourceRequest value) {
        this.createResourceRequest = value;
    }

}
