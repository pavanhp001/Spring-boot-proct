
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderQualificationResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderQualificationResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4/OrderProvisioning/}ProvisioningResponse">
 *       &lt;sequence>
 *         &lt;element name="schedulingInfo" type="{http://xml.A.com/v4/OrderProvisioning/}OpSchedulingInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="creditCustomizations" type="{http://xml.A.com/v4}customizationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transientResponseContainer" type="{http://xml.A.com/v4}transientResponseContainerType" minOccurs="0"/>
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
@XmlType(name = "OrderQualificationResponse", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "schedulingInfo",
    "creditCustomizations",
    "transientResponseContainer",
    "providerAttributes"
})
public class OrderQualificationResponse
    extends ProvisioningResponse
{

    @XmlElement(namespace = "")
    protected List<OpSchedulingInfo> schedulingInfo;
    @XmlElement(namespace = "")
    protected List<CustomizationType> creditCustomizations;
    @XmlElement(namespace = "")
    protected TransientResponseContainerType transientResponseContainer;
    @XmlElement(namespace = "")
    protected List<ProviderAttributes> providerAttributes;

    /**
     * Gets the value of the schedulingInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the schedulingInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSchedulingInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OpSchedulingInfo }
     * 
     * 
     */
    public List<OpSchedulingInfo> getSchedulingInfo() {
        if (schedulingInfo == null) {
            schedulingInfo = new ArrayList<OpSchedulingInfo>();
        }
        return this.schedulingInfo;
    }

    /**
     * Gets the value of the creditCustomizations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the creditCustomizations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreditCustomizations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomizationType }
     * 
     * 
     */
    public List<CustomizationType> getCreditCustomizations() {
        if (creditCustomizations == null) {
            creditCustomizations = new ArrayList<CustomizationType>();
        }
        return this.creditCustomizations;
    }

    /**
     * Gets the value of the transientResponseContainer property.
     * 
     * @return
     *     possible object is
     *     {@link TransientResponseContainerType }
     *     
     */
    public TransientResponseContainerType getTransientResponseContainer() {
        return transientResponseContainer;
    }

    /**
     * Sets the value of the transientResponseContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransientResponseContainerType }
     *     
     */
    public void setTransientResponseContainer(TransientResponseContainerType value) {
        this.transientResponseContainer = value;
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
