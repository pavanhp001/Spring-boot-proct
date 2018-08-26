
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PriceFrequencyEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PriceFrequencyEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="ONETIME"/>
 *     &lt;enumeration value="WEEKLY"/>
 *     &lt;enumeration value="MONTHLY"/>
 *     &lt;enumeration value="ANNUALLY"/>
 *     &lt;enumeration value="EVERY2MONTHS"/>
 *     &lt;enumeration value="EVERY3MONTHS"/>
 *     &lt;enumeration value="EVERY6MONTHS"/>
 *     &lt;enumeration value="EVERY2YEARS"/>
 *     &lt;enumeration value="EVERY3YEARS"/>
 *     &lt;enumeration value="PER_KWH"/>
 *     &lt;enumeration value="PER_THERM"/>
 *     &lt;enumeration value="PER_MCF"/>
 *     &lt;enumeration value="PER_CCF"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PriceFrequencyEnum")
@XmlEnum
public enum PriceFrequencyEnum {

    NONE("NONE"),
    ONETIME("ONETIME"),
    WEEKLY("WEEKLY"),
    MONTHLY("MONTHLY"),
    ANNUALLY("ANNUALLY"),
    @XmlEnumValue("EVERY2MONTHS")
    EVERY_2_MONTHS("EVERY2MONTHS"),
    @XmlEnumValue("EVERY3MONTHS")
    EVERY_3_MONTHS("EVERY3MONTHS"),
    @XmlEnumValue("EVERY6MONTHS")
    EVERY_6_MONTHS("EVERY6MONTHS"),
    @XmlEnumValue("EVERY2YEARS")
    EVERY_2_YEARS("EVERY2YEARS"),
    @XmlEnumValue("EVERY3YEARS")
    EVERY_3_YEARS("EVERY3YEARS"),
    PER_KWH("PER_KWH"),
    PER_THERM("PER_THERM"),
    PER_MCF("PER_MCF"),
    PER_CCF("PER_CCF");
    private final String value;

    PriceFrequencyEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PriceFrequencyEnum fromValue(String v) {
        for (PriceFrequencyEnum c: PriceFrequencyEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
