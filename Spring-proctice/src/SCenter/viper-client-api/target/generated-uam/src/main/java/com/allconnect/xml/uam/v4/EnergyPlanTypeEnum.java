
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnergyPlanTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EnergyPlanTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="VARIABLE"/>
 *     &lt;enumeration value="FIXED"/>
 *     &lt;enumeration value="PREPAY"/>
 *     &lt;enumeration value="VARIABLEPREPAY"/>
 *     &lt;enumeration value="INDEXED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EnergyPlanTypeEnum")
@XmlEnum
public enum EnergyPlanTypeEnum {

    NONE,
    VARIABLE,
    FIXED,
    PREPAY,
    VARIABLEPREPAY,
    INDEXED;

    public String value() {
        return name();
    }

    public static EnergyPlanTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
