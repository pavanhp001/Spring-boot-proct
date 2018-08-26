
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dialogueTransactionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dialogueTransactionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="getDialogues"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "dialogueTransactionType")
@XmlEnum
public enum DialogueTransactionType {


    /**
     * 
     * 						The basic transaction/operation type for the Dialogue service.  For this transaction
     * 						type, the service will return the appropriate dialogue(s) based on the passed Criteria.
     * 					
     * 
     */
    @XmlEnumValue("getDialogues")
    GET_DIALOGUES("getDialogues");
    private final String value;

    DialogueTransactionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DialogueTransactionType fromValue(String v) {
        for (DialogueTransactionType c: DialogueTransactionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
