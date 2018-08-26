
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paymentEventTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="paymentEventTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ADVANCED"/>
 *     &lt;enumeration value="AUTO"/>
 *     &lt;enumeration value="DEPOSIT"/>
 *     &lt;enumeration value="ONETIME"/>
 *     &lt;enumeration value="ONETIME_DTV"/>
 *     &lt;enumeration value="RECURRING"/>
 *     &lt;enumeration value="OUTSTANDING_BALANCE"/>
 *     &lt;enumeration value="HOUSE_DEBT"/>
 *     &lt;enumeration value="CUSTOMER_DEBT"/>
 *     &lt;enumeration value="PAST_DUE"/>
 *     &lt;enumeration value="EQUIPMENT"/>
 *     &lt;enumeration value="PRE_PAYMENT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "paymentEventTypeType")
@XmlEnum
public enum PaymentEventTypeType {

    ADVANCED,
    AUTO,
    DEPOSIT,
    ONETIME,
    ONETIME_DTV,
    RECURRING,
    OUTSTANDING_BALANCE,
    HOUSE_DEBT,
    CUSTOMER_DEBT,
    PAST_DUE,
    EQUIPMENT,
    PRE_PAYMENT;

    public String value() {
        return name();
    }

    public static PaymentEventTypeType fromValue(String v) {
        return valueOf(v);
    }

}
