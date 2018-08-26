
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for promotionObjectType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="promotionObjectType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="item"/>
 *     &lt;enumeration value="marketItem"/>
 *     &lt;enumeration value="bundle"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "promotionObjectType")
@XmlEnum
public enum PromotionObjectType {

    @XmlEnumValue("item")
    ITEM("item"),
    @XmlEnumValue("marketItem")
    MARKET_ITEM("marketItem"),
    @XmlEnumValue("bundle")
    BUNDLE("bundle");
    private final String value;

    PromotionObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PromotionObjectType fromValue(String v) {
        for (PromotionObjectType c: PromotionObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
