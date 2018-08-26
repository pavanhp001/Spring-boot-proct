
package com.A.xml.pr.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PriceFieldNameEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PriceFieldNameEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="PRODUCT"/>
 *     &lt;enumeration value="HARDWARE"/>
 *     &lt;enumeration value="DEPOSIT"/>
 *     &lt;enumeration value="SHIPPING"/>
 *     &lt;enumeration value="ADDITIONAL_LINE"/>
 *     &lt;enumeration value="LONGDISTANCE"/>
 *     &lt;enumeration value="SERVICE_CHARGE"/>
 *     &lt;enumeration value="PROMOTION"/>
 *     &lt;enumeration value="INSTALLATION"/>
 *     &lt;enumeration value="INTERNATIONAL"/>
 *     &lt;enumeration value="ANSWERS"/>
 *     &lt;enumeration value="BEST_PRICE"/>
 *     &lt;enumeration value="RACK_RATE"/>
 *     &lt;enumeration value="ADVANCED"/>
 *     &lt;enumeration value="AUTO"/>
 *     &lt;enumeration value="ONE_TIME"/>
 *     &lt;enumeration value="BALANCE_DUE"/>
 *     &lt;enumeration value="SERVICE_ENERGY_CHARGE"/>
 *     &lt;enumeration value="SERVICE_TDSP_CHARGE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PriceFieldNameEnum")
@XmlEnum
public enum PriceFieldNameEnum {

    NONE,
    PRODUCT,
    HARDWARE,
    DEPOSIT,
    SHIPPING,
    ADDITIONAL_LINE,
    LONGDISTANCE,
    SERVICE_CHARGE,
    PROMOTION,
    INSTALLATION,
    INTERNATIONAL,
    ANSWERS,
    BEST_PRICE,
    RACK_RATE,
    ADVANCED,
    AUTO,
    ONE_TIME,
    BALANCE_DUE,
    SERVICE_ENERGY_CHARGE,
    SERVICE_TDSP_CHARGE;

    public String value() {
        return name();
    }

    public static PriceFieldNameEnum fromValue(String v) {
        return valueOf(v);
    }

}
