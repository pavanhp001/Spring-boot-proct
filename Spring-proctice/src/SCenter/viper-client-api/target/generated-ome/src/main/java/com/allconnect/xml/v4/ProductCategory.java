
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductCategory.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProductCategory">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="LocalPhone"/>
 *     &lt;enumeration value="DSL"/>
 *     &lt;enumeration value="DTV"/>
 *     &lt;enumeration value="Wireless"/>
 *     &lt;enumeration value="Fiber"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProductCategory")
@XmlEnum
public enum ProductCategory {

    @XmlEnumValue("LocalPhone")
    LOCAL_PHONE("LocalPhone"),
    DSL("DSL"),
    DTV("DTV"),
    @XmlEnumValue("Wireless")
    WIRELESS("Wireless"),
    @XmlEnumValue("Fiber")
    FIBER("Fiber");
    private final String value;

    ProductCategory(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProductCategory fromValue(String v) {
        for (ProductCategory c: ProductCategory.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
