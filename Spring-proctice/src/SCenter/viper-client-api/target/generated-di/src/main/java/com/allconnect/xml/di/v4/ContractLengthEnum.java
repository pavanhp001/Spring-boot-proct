
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContractLengthEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContractLengthEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CT_NONE"/>
 *     &lt;enumeration value="CT_M2M"/>
 *     &lt;enumeration value="CT_1MONTH"/>
 *     &lt;enumeration value="CT_2MONTH"/>
 *     &lt;enumeration value="CT_3MONTH"/>
 *     &lt;enumeration value="CT_4MONTH"/>
 *     &lt;enumeration value="CT_5MONTH"/>
 *     &lt;enumeration value="CT_6MONTH"/>
 *     &lt;enumeration value="CT_7MONTH"/>
 *     &lt;enumeration value="CT_8MONTH"/>
 *     &lt;enumeration value="CT_9MONTH"/>
 *     &lt;enumeration value="CT_10MONTH"/>
 *     &lt;enumeration value="CT_11MONTH"/>
 *     &lt;enumeration value="CT_12MONTH"/>
 *     &lt;enumeration value="CT_13MONTH"/>
 *     &lt;enumeration value="CT_14MONTH"/>
 *     &lt;enumeration value="CT_15MONTH"/>
 *     &lt;enumeration value="CT_16MONTH"/>
 *     &lt;enumeration value="CT_17MONTH"/>
 *     &lt;enumeration value="CT_18MONTH"/>
 *     &lt;enumeration value="CT_19MONTH"/>
 *     &lt;enumeration value="CT_20MONTH"/>
 *     &lt;enumeration value="CT_21MONTH"/>
 *     &lt;enumeration value="CT_22MONTH"/>
 *     &lt;enumeration value="CT_23MONTH"/>
 *     &lt;enumeration value="CT_24MONTH"/>
 *     &lt;enumeration value="CT_25MONTH"/>
 *     &lt;enumeration value="CT_26MONTH"/>
 *     &lt;enumeration value="CT_27MONTH"/>
 *     &lt;enumeration value="CT_28MONTH"/>
 *     &lt;enumeration value="CT_29MONTH"/>
 *     &lt;enumeration value="CT_30MONTH"/>
 *     &lt;enumeration value="CT_36MONTH"/>
 *     &lt;enumeration value="CT_48MONTH"/>
 *     &lt;enumeration value="CT_60MONTH"/>
 *     &lt;enumeration value="CT_1YEAR"/>
 *     &lt;enumeration value="CT_2YEAR"/>
 *     &lt;enumeration value="CT_3YEAR"/>
 *     &lt;enumeration value="CT_4YEAR"/>
 *     &lt;enumeration value="CT_5YEAR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ContractLengthEnum")
@XmlEnum
public enum ContractLengthEnum {

    CT_NONE("CT_NONE"),
    @XmlEnumValue("CT_M2M")
    CT_M_2_M("CT_M2M"),
    @XmlEnumValue("CT_1MONTH")
    CT_1_MONTH("CT_1MONTH"),
    @XmlEnumValue("CT_2MONTH")
    CT_2_MONTH("CT_2MONTH"),
    @XmlEnumValue("CT_3MONTH")
    CT_3_MONTH("CT_3MONTH"),
    @XmlEnumValue("CT_4MONTH")
    CT_4_MONTH("CT_4MONTH"),
    @XmlEnumValue("CT_5MONTH")
    CT_5_MONTH("CT_5MONTH"),
    @XmlEnumValue("CT_6MONTH")
    CT_6_MONTH("CT_6MONTH"),
    @XmlEnumValue("CT_7MONTH")
    CT_7_MONTH("CT_7MONTH"),
    @XmlEnumValue("CT_8MONTH")
    CT_8_MONTH("CT_8MONTH"),
    @XmlEnumValue("CT_9MONTH")
    CT_9_MONTH("CT_9MONTH"),
    @XmlEnumValue("CT_10MONTH")
    CT_10_MONTH("CT_10MONTH"),
    @XmlEnumValue("CT_11MONTH")
    CT_11_MONTH("CT_11MONTH"),
    @XmlEnumValue("CT_12MONTH")
    CT_12_MONTH("CT_12MONTH"),
    @XmlEnumValue("CT_13MONTH")
    CT_13_MONTH("CT_13MONTH"),
    @XmlEnumValue("CT_14MONTH")
    CT_14_MONTH("CT_14MONTH"),
    @XmlEnumValue("CT_15MONTH")
    CT_15_MONTH("CT_15MONTH"),
    @XmlEnumValue("CT_16MONTH")
    CT_16_MONTH("CT_16MONTH"),
    @XmlEnumValue("CT_17MONTH")
    CT_17_MONTH("CT_17MONTH"),
    @XmlEnumValue("CT_18MONTH")
    CT_18_MONTH("CT_18MONTH"),
    @XmlEnumValue("CT_19MONTH")
    CT_19_MONTH("CT_19MONTH"),
    @XmlEnumValue("CT_20MONTH")
    CT_20_MONTH("CT_20MONTH"),
    @XmlEnumValue("CT_21MONTH")
    CT_21_MONTH("CT_21MONTH"),
    @XmlEnumValue("CT_22MONTH")
    CT_22_MONTH("CT_22MONTH"),
    @XmlEnumValue("CT_23MONTH")
    CT_23_MONTH("CT_23MONTH"),
    @XmlEnumValue("CT_24MONTH")
    CT_24_MONTH("CT_24MONTH"),
    @XmlEnumValue("CT_25MONTH")
    CT_25_MONTH("CT_25MONTH"),
    @XmlEnumValue("CT_26MONTH")
    CT_26_MONTH("CT_26MONTH"),
    @XmlEnumValue("CT_27MONTH")
    CT_27_MONTH("CT_27MONTH"),
    @XmlEnumValue("CT_28MONTH")
    CT_28_MONTH("CT_28MONTH"),
    @XmlEnumValue("CT_29MONTH")
    CT_29_MONTH("CT_29MONTH"),
    @XmlEnumValue("CT_30MONTH")
    CT_30_MONTH("CT_30MONTH"),
    @XmlEnumValue("CT_36MONTH")
    CT_36_MONTH("CT_36MONTH"),
    @XmlEnumValue("CT_48MONTH")
    CT_48_MONTH("CT_48MONTH"),
    @XmlEnumValue("CT_60MONTH")
    CT_60_MONTH("CT_60MONTH"),
    @XmlEnumValue("CT_1YEAR")
    CT_1_YEAR("CT_1YEAR"),
    @XmlEnumValue("CT_2YEAR")
    CT_2_YEAR("CT_2YEAR"),
    @XmlEnumValue("CT_3YEAR")
    CT_3_YEAR("CT_3YEAR"),
    @XmlEnumValue("CT_4YEAR")
    CT_4_YEAR("CT_4YEAR"),
    @XmlEnumValue("CT_5YEAR")
    CT_5_YEAR("CT_5YEAR");
    private final String value;

    ContractLengthEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ContractLengthEnum fromValue(String v) {
        for (ContractLengthEnum c: ContractLengthEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
