
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataFieldTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dataFieldTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="text"/>
 *     &lt;enumeration value="feature"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "dataFieldTypeType")
@XmlEnum
public enum DataFieldTypeType {

    @XmlEnumValue("text")
    TEXT("text"),
    @XmlEnumValue("feature")
    FEATURE("feature");
    private final String value;

    DataFieldTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DataFieldTypeType fromValue(String v) {
        for (DataFieldTypeType c: DataFieldTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
