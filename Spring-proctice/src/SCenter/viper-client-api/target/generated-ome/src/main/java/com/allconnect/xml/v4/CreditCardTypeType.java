
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for creditCardTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="creditCardTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="American Express"/>
 *     &lt;enumeration value="Discover"/>
 *     &lt;enumeration value="Master Card"/>
 *     &lt;enumeration value="Visa"/>
 *     &lt;enumeration value="DINER'S CLUB"/>
 *     &lt;enumeration value="Optima"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "creditCardTypeType")
@XmlEnum
public enum CreditCardTypeType {

    @XmlEnumValue("American Express")
    AMERICAN_EXPRESS("American Express"),
    @XmlEnumValue("Discover")
    DISCOVER("Discover"),
    @XmlEnumValue("Master Card")
    MASTER_CARD("Master Card"),
    @XmlEnumValue("Visa")
    VISA("Visa"),
    @XmlEnumValue("DINER'S CLUB")
    DINER_S_CLUB("DINER'S CLUB"),
    @XmlEnumValue("Optima")
    OPTIMA("Optima");
    private final String value;

    CreditCardTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CreditCardTypeType fromValue(String v) {
        for (CreditCardTypeType c: CreditCardTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
