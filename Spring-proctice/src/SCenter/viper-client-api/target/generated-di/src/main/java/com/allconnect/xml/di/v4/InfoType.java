
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for infoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="infoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="disclosure"/>
 *     &lt;enumeration value="informational"/>
 *     &lt;enumeration value="notable"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "infoType")
@XmlEnum
public enum InfoType {

    @XmlEnumValue("disclosure")
    DISCLOSURE("disclosure"),
    @XmlEnumValue("informational")
    INFORMATIONAL("informational"),
    @XmlEnumValue("notable")
    NOTABLE("notable"),
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    InfoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InfoType fromValue(String v) {
        for (InfoType c: InfoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
