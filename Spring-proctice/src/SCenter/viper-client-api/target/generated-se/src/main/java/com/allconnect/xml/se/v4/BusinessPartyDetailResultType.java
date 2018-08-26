
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for businessPartyDetailResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="businessPartyDetailResultType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}businessPartyDetailType">
 *       &lt;sequence>
 *         &lt;element name="requestedFor" type="{http://xml.A.com/v4}detailElementType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "businessPartyDetailResultType", propOrder = {
    "requestedFor"
})
public class BusinessPartyDetailResultType
    extends BusinessPartyDetailType
{

    @XmlElement(required = true)
    protected DetailElementType requestedFor;

    /**
     * Gets the value of the requestedFor property.
     * 
     * @return
     *     possible object is
     *     {@link DetailElementType }
     *     
     */
    public DetailElementType getRequestedFor() {
        return requestedFor;
    }

    /**
     * Sets the value of the requestedFor property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailElementType }
     *     
     */
    public void setRequestedFor(DetailElementType value) {
        this.requestedFor = value;
    }

}
