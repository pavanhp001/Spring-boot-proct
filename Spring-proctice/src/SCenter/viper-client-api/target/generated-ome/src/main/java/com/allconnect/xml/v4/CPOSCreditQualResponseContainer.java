
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CPOSCreditQualResponseContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CPOSCreditQualResponseContainer">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}ProviderResponseContainer">
 *       &lt;sequence>
 *         &lt;element name="depositAmount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="houseDebtAmount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="customerDebt" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="pastDueAmount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="payNowOneTimeCharges" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
@XmlType(name = "CPOSCreditQualResponseContainer", propOrder = {
    "depositAmount",
    "houseDebtAmount",
    "customerDebt",
    "pastDueAmount",
    "payNowOneTimeCharges",
    "result",
    "providerAttributes"
})
public class CPOSCreditQualResponseContainer
    extends ProviderResponseContainer
{

    protected Double depositAmount;
    protected Double houseDebtAmount;
    protected Double customerDebt;
    protected Double pastDueAmount;
    protected Double payNowOneTimeCharges;
    protected Boolean result;
    protected List<ProviderAttributes> providerAttributes;

    /**
     * Gets the value of the depositAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDepositAmount() {
        return depositAmount;
    }

    /**
     * Sets the value of the depositAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDepositAmount(Double value) {
        this.depositAmount = value;
    }

    /**
     * Gets the value of the houseDebtAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHouseDebtAmount() {
        return houseDebtAmount;
    }

    /**
     * Sets the value of the houseDebtAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHouseDebtAmount(Double value) {
        this.houseDebtAmount = value;
    }

    /**
     * Gets the value of the customerDebt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCustomerDebt() {
        return customerDebt;
    }

    /**
     * Sets the value of the customerDebt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCustomerDebt(Double value) {
        this.customerDebt = value;
    }

    /**
     * Gets the value of the pastDueAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPastDueAmount() {
        return pastDueAmount;
    }

    /**
     * Sets the value of the pastDueAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPastDueAmount(Double value) {
        this.pastDueAmount = value;
    }

    /**
     * Gets the value of the payNowOneTimeCharges property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPayNowOneTimeCharges() {
        return payNowOneTimeCharges;
    }

    /**
     * Sets the value of the payNowOneTimeCharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPayNowOneTimeCharges(Double value) {
        this.payNowOneTimeCharges = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResult(Boolean value) {
        this.result = value;
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
