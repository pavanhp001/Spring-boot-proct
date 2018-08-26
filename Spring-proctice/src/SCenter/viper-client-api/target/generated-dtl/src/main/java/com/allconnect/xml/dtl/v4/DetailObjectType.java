
package com.A.xml.dtl.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for detailObjectType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="detailObjectType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="item"/>
 *     &lt;enumeration value="marketItem"/>
 *     &lt;enumeration value="businessParty"/>
 *     &lt;enumeration value="productCatalog"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "detailObjectType")
@XmlEnum
public enum DetailObjectType {

    @XmlEnumValue("item")
    ITEM("item"),
    @XmlEnumValue("marketItem")
    MARKET_ITEM("marketItem"),
    @XmlEnumValue("businessParty")
    BUSINESS_PARTY("businessParty"),
    @XmlEnumValue("productCatalog")
    PRODUCT_CATALOG("productCatalog");
    private final String value;

    DetailObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DetailObjectType fromValue(String v) {
        for (DetailObjectType c: DetailObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
