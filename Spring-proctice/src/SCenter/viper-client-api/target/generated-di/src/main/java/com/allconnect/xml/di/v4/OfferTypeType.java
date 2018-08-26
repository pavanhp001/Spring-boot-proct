
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for offerTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="offerTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="new"/>
 *     &lt;enumeration value="transfer"/>
 *     &lt;enumeration value="upgrade"/>
 *     &lt;enumeration value="asis"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "offerTypeType")
@XmlEnum
public enum OfferTypeType {

    @XmlEnumValue("new")
    NEW("new"),
    @XmlEnumValue("transfer")
    TRANSFER("transfer"),
    @XmlEnumValue("upgrade")
    UPGRADE("upgrade"),
    @XmlEnumValue("asis")
    ASIS("asis");
    private final String value;

    OfferTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OfferTypeType fromValue(String v) {
        for (OfferTypeType c: OfferTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
