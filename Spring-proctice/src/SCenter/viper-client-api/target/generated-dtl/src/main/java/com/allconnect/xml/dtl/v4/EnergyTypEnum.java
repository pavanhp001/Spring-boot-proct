
package com.A.xml.dtl.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnergyTypEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EnergyTypEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="TRADITIONAL"/>
 *     &lt;enumeration value="RENEWABLE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EnergyTypEnum")
@XmlEnum
public enum EnergyTypEnum {

    NONE,
    TRADITIONAL,
    RENEWABLE;

    public String value() {
        return name();
    }

    public static EnergyTypEnum fromValue(String v) {
        return valueOf(v);
    }

}
