
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for uamTransactionTypeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uamTransactionTypeType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractTransactionTypeType">
 *       &lt;sequence>
 *         &lt;element name="uamTransactionType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="authenticateUser"/>
 *               &lt;enumeration value="createUser"/>
 *               &lt;enumeration value="createResource"/>
 *               &lt;enumeration value="createRole"/>
 *               &lt;enumeration value="attachResourceToUser"/>
 *               &lt;enumeration value="attachResourceToRole"/>
 *               &lt;enumeration value="attachRoleToUser"/>
 *               &lt;enumeration value="detachResourceFromUser"/>
 *               &lt;enumeration value="detachResourceFromRole"/>
 *               &lt;enumeration value="detachRoleFromUser"/>
 *               &lt;enumeration value="validateUser"/>
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
@XmlType(name = "uamTransactionTypeType", propOrder = {
    "uamTransactionType"
})
public class UamTransactionTypeType
    extends AbstractTransactionTypeType
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String uamTransactionType;

    /**
     * Gets the value of the uamTransactionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUamTransactionType() {
        return uamTransactionType;
    }

    /**
     * Sets the value of the uamTransactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUamTransactionType(String value) {
        this.uamTransactionType = value;
    }

}
