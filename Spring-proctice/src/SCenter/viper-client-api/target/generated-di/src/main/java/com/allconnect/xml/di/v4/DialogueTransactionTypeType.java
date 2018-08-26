
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for dialogueTransactionTypeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dialogueTransactionTypeType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractTransactionTypeType">
 *       &lt;sequence>
 *         &lt;element name="dialogueTransactionType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="getDialogues"/>
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
@XmlType(name = "dialogueTransactionTypeType", propOrder = {
    "dialogueTransactionType"
})
public class DialogueTransactionTypeType
    extends AbstractTransactionTypeType
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String dialogueTransactionType;

    /**
     * Gets the value of the dialogueTransactionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDialogueTransactionType() {
        return dialogueTransactionType;
    }

    /**
     * Sets the value of the dialogueTransactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDialogueTransactionType(String value) {
        this.dialogueTransactionType = value;
    }

}
