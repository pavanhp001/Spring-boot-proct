
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CPOSOrderQualResponseContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CPOSOrderQualResponseContainer">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}ProviderResponseContainer">
 *       &lt;sequence>
 *         &lt;element name="contractAcceptance" type="{http://xml.A.com/v4}ContractAcceptance" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mitigateResult" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="chargeNrc" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="chargeRc" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="product" type="{http://xml.A.com/v4}productInfoType" minOccurs="0"/>
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
@XmlType(name = "CPOSOrderQualResponseContainer", propOrder = {
    "contractAcceptance",
    "mitigateResult",
    "chargeNrc",
    "chargeRc",
    "product",
    "providerAttributes"
})
public class CPOSOrderQualResponseContainer
    extends ProviderResponseContainer
{

    protected List<ContractAcceptance> contractAcceptance;
    protected Boolean mitigateResult;
    protected Double chargeNrc;
    protected Double chargeRc;
    protected ProductInfoType product;
    protected List<ProviderAttributes> providerAttributes;

    /**
     * Gets the value of the contractAcceptance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contractAcceptance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContractAcceptance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContractAcceptance }
     * 
     * 
     */
    public List<ContractAcceptance> getContractAcceptance() {
        if (contractAcceptance == null) {
            contractAcceptance = new ArrayList<ContractAcceptance>();
        }
        return this.contractAcceptance;
    }

    /**
     * Gets the value of the mitigateResult property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMitigateResult() {
        return mitigateResult;
    }

    /**
     * Sets the value of the mitigateResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMitigateResult(Boolean value) {
        this.mitigateResult = value;
    }

    /**
     * Gets the value of the chargeNrc property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getChargeNrc() {
        return chargeNrc;
    }

    /**
     * Sets the value of the chargeNrc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setChargeNrc(Double value) {
        this.chargeNrc = value;
    }

    /**
     * Gets the value of the chargeRc property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getChargeRc() {
        return chargeRc;
    }

    /**
     * Sets the value of the chargeRc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setChargeRc(Double value) {
        this.chargeRc = value;
    }

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link ProductInfoType }
     *     
     */
    public ProductInfoType getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductInfoType }
     *     
     */
    public void setProduct(ProductInfoType value) {
        this.product = value;
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
