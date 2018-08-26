
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticateCustomerRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticateCustomerRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4/OrderProvisioning/}ProvisioningRequest">
 *       &lt;sequence>
 *         &lt;element name="accountHolderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="last4ssn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerInfo" type="{http://xml.A.com/v4/OrderProvisioning/}OpCustomerInfo" minOccurs="0"/>
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
@XmlType(name = "AuthenticateCustomerRequest", namespace = "http://xml.A.com/v4/OrderProvisioning/", propOrder = {
    "accountHolderName",
    "accountNumber",
    "last4Ssn",
    "customerInfo",
    "providerAttributes"
})
public class AuthenticateCustomerRequest
    extends ProvisioningRequest
{

    @XmlElement(namespace = "")
    protected String accountHolderName;
    @XmlElement(namespace = "")
    protected String accountNumber;
    @XmlElement(name = "last4ssn", namespace = "")
    protected String last4Ssn;
    @XmlElement(namespace = "")
    protected OpCustomerInfo customerInfo;
    @XmlElement(namespace = "")
    protected List<ProviderAttributes> providerAttributes;

    /**
     * Gets the value of the accountHolderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }

    /**
     * Sets the value of the accountHolderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountHolderName(String value) {
        this.accountHolderName = value;
    }

    /**
     * Gets the value of the accountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    /**
     * Gets the value of the last4Ssn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLast4Ssn() {
        return last4Ssn;
    }

    /**
     * Sets the value of the last4Ssn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLast4Ssn(String value) {
        this.last4Ssn = value;
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
