
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CPOSOrderQualRequestContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CPOSOrderQualRequestContainer">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}ProviderRequestContainer">
 *       &lt;sequence>
 *         &lt;element name="timeSlotRefId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="installPriceRefId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="providerAttributes" type="{http://xml.A.com/v4}ProviderAttributes" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CPOSOrderQualRequestContainer", propOrder = {
    "timeSlotRefId",
    "installPriceRefId",
    "providerAttributes"
})
public class CPOSOrderQualRequestContainer
    extends ProviderRequestContainer
{

    @XmlElementRef(name = "timeSlotRefId", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected JAXBElement<String> timeSlotRefId;
    protected String installPriceRefId;
    protected List<ProviderAttributes> providerAttributes;

    /**
     * Gets the value of the timeSlotRefId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTimeSlotRefId() {
        return timeSlotRefId;
    }

    /**
     * Sets the value of the timeSlotRefId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTimeSlotRefId(JAXBElement<String> value) {
        this.timeSlotRefId = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the installPriceRefId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstallPriceRefId() {
        return installPriceRefId;
    }

    /**
     * Sets the value of the installPriceRefId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstallPriceRefId(String value) {
        this.installPriceRefId = value;
    }

    /**
     * Gets the value of the providerAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the providerAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProviderAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProviderAttributes }
     * 
     * 
     */
    public List<ProviderAttributes> getProviderAttributes() {
        if (providerAttributes == null) {
            providerAttributes = new ArrayList<ProviderAttributes>();
        }
        return this.providerAttributes;
    }

}
