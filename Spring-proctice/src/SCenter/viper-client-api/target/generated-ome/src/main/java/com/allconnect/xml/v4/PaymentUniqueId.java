
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentUniqueId.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PaymentUniqueId">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ADVANCED"/>
 *     &lt;enumeration value="AUTO"/>
 *     &lt;enumeration value="DEPOSIT"/>
 *     &lt;enumeration value="ONETIME"/>
 *     &lt;enumeration value="ONETIME_DTV"/>
 *     &lt;enumeration value="RECURRING"/>
 *     &lt;enumeration value="OUTSTANDING_BALANCE"/>
 *     &lt;enumeration value="EQUIPMENT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PaymentUniqueId")
@XmlEnum
public enum PaymentUniqueId {

    ADVANCED,
    AUTO,
    DEPOSIT,
    ONETIME,
    ONETIME_DTV,
    RECURRING,
    OUTSTANDING_BALANCE,
    EQUIPMENT;

    public String value() {
        return name();
    }

    public static PaymentUniqueId fromValue(String v) {
        return valueOf(v);
    }

}
