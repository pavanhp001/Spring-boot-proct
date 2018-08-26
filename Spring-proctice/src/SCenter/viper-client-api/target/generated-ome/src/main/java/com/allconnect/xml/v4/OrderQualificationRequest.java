
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderQualificationRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderQualificationRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4/OrderProvisioning/}ProvisioningRequest">
 *       &lt;sequence>
 *         &lt;element name="productLineItem" type="{http://xml.A.com/v4/OrderProvisioning/}OpProductLineItem" minOccurs="0"/>
 *         &lt;element name="customerInfo" type="{http://xml.A.com/v4/OrderProvisioning/}OpCustomerInfo" minOccurs="0"/>
 *         &lt;element name="accountHolder" type="{http://xml.A.com/v4}accountHolderType"/>
 *         &lt;element name="desiredInstallDate" type="{http://xml.A.com/v4}dateTimeOrTimeRangeType" minOccurs="0"/>
 *         &lt;element name="creditCustomizations" type="{http://xml.A.com/v4}customizationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="providerAttributes" type="{http://xml.A.com/v4}ProviderAttributes" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transientRequestContainer" type="{http://xml.A.com/v4}transientRequestContainerType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderQualificationRequest", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "productLineItem",
    "customerInfo",
    "accountHolder",
    "desiredInstallDate",
    "creditCustomizations",
    "providerAttributes",
    "transientRequestContainer"
})
public class OrderQualificationRequest
    extends ProvisioningRequest
{

    @XmlElement(namespace = "")
    protected OpProductLineItem productLineItem;
    @XmlElement(namespace = "")
    protected OpCustomerInfo customerInfo;
    @XmlElement(namespace = "", required = true)
    protected AccountHolderType accountHolder;
    @XmlElement(namespace = "")
    protected DateTimeOrTimeRangeType desiredInstallDate;
    @XmlElement(namespace = "")
    protected List<CustomizationType> creditCustomizations;
    @XmlElement(namespace = "")
    protected List<ProviderAttributes> providerAttributes;
    @XmlElement(namespace = "")
    protected TransientRequestContainerType transientRequestContainer;

    /**
     * Gets the value of the productLineItem property.
     * 
     * @return
     *     possible object is
     *     {@link OpProductLineItem }
     *     
     */
    public OpProductLineItem getProductLineItem() {
        return productLineItem;
    }

    /**
     * Sets the value of the productLineItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpProductLineItem }
     *     
     */
    public void setProductLineItem(OpProductLineItem value) {
        this.productLineItem = value;
    }

    /**
     * Gets the value of the customerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OpCustomerInfo }
     *     
     */
    public OpCustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    /**
     * Sets the value of the customerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpCustomerInfo }
     *     
     */
    public void setCustomerInfo(OpCustomerInfo value) {
        this.customerInfo = value;
    }

    /**
     * Gets the value of the accountHolder property.
     * 
     * @return
     *     possible object is
     *     {@link AccountHolderType }
     *     
     */
    public AccountHolderType getAccountHolder() {
        return accountHolder;
    }

    /**
     * Sets the value of the accountHolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountHolderType }
     *     
     */
    public void setAccountHolder(AccountHolderType value) {
        this.accountHolder = value;
    }

    /**
     * Gets the value of the desiredInstallDate property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeOrTimeRangeType }
     *     
     */
    public DateTimeOrTimeRangeType getDesiredInstallDate() {
        return desiredInstallDate;
    }

    /**
     * Sets the value of the desiredInstallDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeOrTimeRangeType }
     *     
     */
    public void setDesiredInstallDate(DateTimeOrTimeRangeType value) {
        this.desiredInstallDate = value;
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

    /**
     * Gets the value of the transientRequestContainer property.
     * 
     * @return
     *     possible object is
     *     {@link TransientRequestContainerType }
     *     
     */
    public TransientRequestContainerType getTransientRequestContainer() {
        return transientRequestContainer;
    }

    /**
     * Sets the value of the transientRequestContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransientRequestContainerType }
     *     
     */
    public void setTransientRequestContainer(TransientRequestContainerType value) {
        this.transientRequestContainer = value;
    }

}
