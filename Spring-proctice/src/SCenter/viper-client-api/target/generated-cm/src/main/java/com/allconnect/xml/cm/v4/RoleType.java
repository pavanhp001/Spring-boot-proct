
package com.A.xml.cm.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for roleType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="roleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CurrentAddress"/>
 *     &lt;enumeration value="ServiceAddress"/>
 *     &lt;enumeration value="BillingAddress"/>
 *     &lt;enumeration value="PreviousAddress"/>
 *     &lt;enumeration value="MailingAddress"/>
 *     &lt;enumeration value="HomeAddress"/>
 *     &lt;enumeration value="ShippingAddress"/>
 *     &lt;enumeration value="CorrectedAddress"/>
 *     &lt;enumeration value="DTAddress"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "roleType")
@XmlEnum
public enum RoleType {

    @XmlEnumValue("CurrentAddress")
    CURRENT_ADDRESS("CurrentAddress"),
    @XmlEnumValue("ServiceAddress")
    SERVICE_ADDRESS("ServiceAddress"),
    @XmlEnumValue("BillingAddress")
    BILLING_ADDRESS("BillingAddress"),
    @XmlEnumValue("PreviousAddress")
    PREVIOUS_ADDRESS("PreviousAddress"),
    @XmlEnumValue("MailingAddress")
    MAILING_ADDRESS("MailingAddress"),
    @XmlEnumValue("HomeAddress")
    HOME_ADDRESS("HomeAddress"),
    @XmlEnumValue("ShippingAddress")
    SHIPPING_ADDRESS("ShippingAddress"),
    @XmlEnumValue("CorrectedAddress")
    CORRECTED_ADDRESS("CorrectedAddress"),
    @XmlEnumValue("DTAddress")
    DT_ADDRESS("DTAddress");
    private final String value;

    RoleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RoleType fromValue(String v) {
        for (RoleType c: RoleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
