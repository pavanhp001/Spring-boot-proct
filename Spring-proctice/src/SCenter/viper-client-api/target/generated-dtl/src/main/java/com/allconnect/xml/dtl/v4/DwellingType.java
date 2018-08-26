
package com.A.xml.dtl.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dwellingType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dwellingType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="apartment"/>
 *     &lt;enumeration value="house"/>
 *     &lt;enumeration value="new_construction"/>
 *     &lt;enumeration value="condo/townhouse"/>
 *     &lt;enumeration value="business"/>
 *     &lt;enumeration value="duplex"/>
 *     &lt;enumeration value="mobile home"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "dwellingType")
@XmlEnum
public enum DwellingType {

    @XmlEnumValue("apartment")
    APARTMENT("apartment"),
    @XmlEnumValue("house")
    HOUSE("house"),
    @XmlEnumValue("new_construction")
    NEW_CONSTRUCTION("new_construction"),
    @XmlEnumValue("condo/townhouse")
    CONDO_TOWNHOUSE("condo/townhouse"),
    @XmlEnumValue("business")
    BUSINESS("business"),
    @XmlEnumValue("duplex")
    DUPLEX("duplex"),
    @XmlEnumValue("mobile home")
    MOBILE_HOME("mobile home");
    private final String value;

    DwellingType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DwellingType fromValue(String v) {
        for (DwellingType c: DwellingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
