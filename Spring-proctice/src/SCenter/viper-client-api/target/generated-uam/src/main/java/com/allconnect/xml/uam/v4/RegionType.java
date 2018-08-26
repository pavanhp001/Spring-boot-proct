
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for regionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="regionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SOUTHWEST"/>
 *     &lt;enumeration value="WEST"/>
 *     &lt;enumeration value="MIDWEST"/>
 *     &lt;enumeration value="EAST"/>
 *     &lt;enumeration value="SOUTHEAST"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "regionType")
@XmlEnum
public enum RegionType {

    SOUTHWEST,
    WEST,
    MIDWEST,
    EAST,
    SOUTHEAST;

    public String value() {
        return name();
    }

    public static RegionType fromValue(String v) {
        return valueOf(v);
    }

}
