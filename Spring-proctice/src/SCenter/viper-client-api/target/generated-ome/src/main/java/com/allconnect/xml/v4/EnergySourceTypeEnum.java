
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnergySourceTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EnergySourceTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="WINDENERGY"/>
 *     &lt;enumeration value="WINDWATERENGERGY"/>
 *     &lt;enumeration value="SOLARWINDENERGY"/>
 *     &lt;enumeration value="NGENERGY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EnergySourceTypeEnum")
@XmlEnum
public enum EnergySourceTypeEnum {

    NONE,
    WINDENERGY,
    WINDWATERENGERGY,
    SOLARWINDENERGY,
    NGENERGY;

    public String value() {
        return name();
    }

    public static EnergySourceTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
