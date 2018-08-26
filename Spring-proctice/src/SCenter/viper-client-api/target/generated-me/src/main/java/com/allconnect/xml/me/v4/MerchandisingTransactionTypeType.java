
package com.A.xml.me.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for merchandisingTransactionTypeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="merchandisingTransactionTypeType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractTransactionTypeType">
 *       &lt;sequence>
 *         &lt;element name="merchandisingTransactionType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="processAlgorithm"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "merchandisingTransactionTypeType", propOrder = {
    "merchandisingTransactionType"
})
public class MerchandisingTransactionTypeType
    extends AbstractTransactionTypeType
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String merchandisingTransactionType;

    /**
     * Gets the value of the merchandisingTransactionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchandisingTransactionType() {
        return merchandisingTransactionType;
    }

    /**
     * Sets the value of the merchandisingTransactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchandisingTransactionType(String value) {
        this.merchandisingTransactionType = value;
    }

}
