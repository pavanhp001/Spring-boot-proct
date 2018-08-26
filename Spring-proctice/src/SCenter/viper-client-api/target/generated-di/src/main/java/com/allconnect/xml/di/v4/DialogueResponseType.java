
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dialogueResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dialogueResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}abstractResponseType">
 *       &lt;sequence>
 *         &lt;element name="results">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dialogueList" type="{http://xml.A.com/v4}dialogueListType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "dialogueResponseType", propOrder = {
    "results"
})
public class DialogueResponseType
    extends AbstractResponseType
{

    @XmlElement(required = true)
    protected DialogueResponseType.Results results;

    /**
     * Gets the value of the results property.
     * 
     * @return
     *     possible object is
     *     {@link DialogueResponseType.Results }
     *     
     */
    public DialogueResponseType.Results getResults() {
        return results;
    }

    /**
     * Sets the value of the results property.
     * 
     * @param value
     *     allowed object is
     *     {@link DialogueResponseType.Results }
     *     
     */
    public void setResults(DialogueResponseType.Results value) {
        this.results = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="dialogueList" type="{http://xml.A.com/v4}dialogueListType" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dialogueList"
    })
    public static class Results {

        protected DialogueListType dialogueList;

        /**
         * Gets the value of the dialogueList property.
         * 
         * @return
         *     possible object is
         *     {@link DialogueListType }
         *     
         */
        public DialogueListType getDialogueList() {
            return dialogueList;
        }

        /**
         * Sets the value of the dialogueList property.
         * 
         * @param value
         *     allowed object is
         *     {@link DialogueListType }
         *     
         */
        public void setDialogueList(DialogueListType value) {
            this.dialogueList = value;
        }

    }

}
