
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for energyUnitType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="energyUnitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="therm"/>
 *     &lt;enumeration value="kwh"/>
 *     &lt;enumeration value="ccf"/>
 *     &lt;enumeration value="mcf"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "energyUnitType")
@XmlEnum
public enum EnergyUnitType {

    @XmlEnumValue("therm")
    THERM("therm"),
    @XmlEnumValue("kwh")
    KWH("kwh"),
    @XmlEnumValue("ccf")
    CCF("ccf"),
    @XmlEnumValue("mcf")
    MCF("mcf");
    private final String value;

    EnergyUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnergyUnitType fromValue(String v) {
        for (EnergyUnitType c: EnergyUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
