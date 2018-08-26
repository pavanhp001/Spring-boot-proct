
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for creditStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="creditStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Approved"/>
 *     &lt;enumeration value="Deposit"/>
 *     &lt;enumeration value="Pending"/>
 *     &lt;enumeration value="Review"/>
 *     &lt;enumeration value="Declined"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "creditStatus")
@XmlEnum
public enum CreditStatus {

    @XmlEnumValue("Approved")
    APPROVED("Approved"),
    @XmlEnumValue("Deposit")
    DEPOSIT("Deposit"),
    @XmlEnumValue("Pending")
    PENDING("Pending"),
    @XmlEnumValue("Review")
    REVIEW("Review"),
    @XmlEnumValue("Declined")
    DECLINED("Declined");
    private final String value;

    CreditStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CreditStatus fromValue(String v) {
        for (CreditStatus c: CreditStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
