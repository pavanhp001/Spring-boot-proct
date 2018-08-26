
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for creditClassType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="creditClassType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="low"/>
 *     &lt;enumeration value="medium"/>
 *     &lt;enumeration value="high"/>
 *     &lt;enumeration value="exceptional"/>
 *     &lt;enumeration value="unknown"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "creditClassType")
@XmlEnum
public enum CreditClassType {

    @XmlEnumValue("low")
    LOW("low"),
    @XmlEnumValue("medium")
    MEDIUM("medium"),
    @XmlEnumValue("high")
    HIGH("high"),
    @XmlEnumValue("exceptional")
    EXCEPTIONAL("exceptional"),
    @XmlEnumValue("unknown")
    UNKNOWN("unknown");
    private final String value;

    CreditClassType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CreditClassType fromValue(String v) {
        for (CreditClassType c: CreditClassType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
