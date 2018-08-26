
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for providerSourceBaseType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="providerSourceBaseType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="internal"/>
 *     &lt;enumeration value="realtime"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "providerSourceBaseType")
@XmlEnum
public enum ProviderSourceBaseType {

    @XmlEnumValue("internal")
    INTERNAL("internal"),
    @XmlEnumValue("realtime")
    REALTIME("realtime");
    private final String value;

    ProviderSourceBaseType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProviderSourceBaseType fromValue(String v) {
        for (ProviderSourceBaseType c: ProviderSourceBaseType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
