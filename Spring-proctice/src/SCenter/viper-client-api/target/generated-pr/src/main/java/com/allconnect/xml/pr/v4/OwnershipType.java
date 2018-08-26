
package com.A.xml.pr.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ownershipType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ownershipType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="own"/>
 *     &lt;enumeration value="rent"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ownershipType")
@XmlEnum
public enum OwnershipType {

    @XmlEnumValue("own")
    OWN("own"),
    @XmlEnumValue("rent")
    RENT("rent");
    private final String value;

    OwnershipType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OwnershipType fromValue(String v) {
        for (OwnershipType c: OwnershipType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
