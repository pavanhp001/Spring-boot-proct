
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductSubTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProductSubTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="PHONE_COPPER"/>
 *     &lt;enumeration value="PHONE_FIBER"/>
 *     &lt;enumeration value="HSI_FIBER"/>
 *     &lt;enumeration value="VIDEO_FIBER"/>
 *     &lt;enumeration value="DSL"/>
 *     &lt;enumeration value="FIBER"/>
 *     &lt;enumeration value="CABLE"/>
 *     &lt;enumeration value="SAT"/>
 *     &lt;enumeration value="SPORTS_PKG"/>
 *     &lt;enumeration value="MOVIE_PKG"/>
 *     &lt;enumeration value="GAME_PKG"/>
 *     &lt;enumeration value="STDDVR"/>
 *     &lt;enumeration value="HDDVR"/>
 *     &lt;enumeration value="STB_NO_DVR"/>
 *     &lt;enumeration value="ADDON"/>
 *     &lt;enumeration value="ANTENNA"/>
 *     &lt;enumeration value="WIRELESS_ROUTER"/>
 *     &lt;enumeration value="ROUTER"/>
 *     &lt;enumeration value="JACK1"/>
 *     &lt;enumeration value="JACK2PLUS"/>
 *     &lt;enumeration value="PROTECTIONPLAN"/>
 *     &lt;enumeration value="NONLISTED"/>
 *     &lt;enumeration value="NONPUBLISHED"/>
 *     &lt;enumeration value="LISTEDPUBLISHED"/>
 *     &lt;enumeration value="FLATRATE"/>
 *     &lt;enumeration value="MSGRATE"/>
 *     &lt;enumeration value="UNLIMITED_LOCAL"/>
 *     &lt;enumeration value="FEATURE"/>
 *     &lt;enumeration value="INTERNATIONAL_CHANNEL"/>
 *     &lt;enumeration value="HW_CONNECTION_KIT"/>
 *     &lt;enumeration value="PREPAID"/>
 *     &lt;enumeration value="REGULATED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProductSubTypeEnum")
@XmlEnum
public enum ProductSubTypeEnum {

    NONE("NONE"),
    PHONE_COPPER("PHONE_COPPER"),
    PHONE_FIBER("PHONE_FIBER"),
    HSI_FIBER("HSI_FIBER"),
    VIDEO_FIBER("VIDEO_FIBER"),
    DSL("DSL"),
    FIBER("FIBER"),
    CABLE("CABLE"),
    SAT("SAT"),
    SPORTS_PKG("SPORTS_PKG"),
    MOVIE_PKG("MOVIE_PKG"),
    GAME_PKG("GAME_PKG"),
    STDDVR("STDDVR"),
    HDDVR("HDDVR"),
    STB_NO_DVR("STB_NO_DVR"),
    ADDON("ADDON"),
    ANTENNA("ANTENNA"),
    WIRELESS_ROUTER("WIRELESS_ROUTER"),
    ROUTER("ROUTER"),
    @XmlEnumValue("JACK1")
    JACK_1("JACK1"),
    @XmlEnumValue("JACK2PLUS")
    JACK_2_PLUS("JACK2PLUS"),
    PROTECTIONPLAN("PROTECTIONPLAN"),
    NONLISTED("NONLISTED"),
    NONPUBLISHED("NONPUBLISHED"),
    LISTEDPUBLISHED("LISTEDPUBLISHED"),
    FLATRATE("FLATRATE"),
    MSGRATE("MSGRATE"),
    UNLIMITED_LOCAL("UNLIMITED_LOCAL"),
    FEATURE("FEATURE"),
    INTERNATIONAL_CHANNEL("INTERNATIONAL_CHANNEL"),
    HW_CONNECTION_KIT("HW_CONNECTION_KIT"),
    PREPAID("PREPAID"),
    REGULATED("REGULATED");
    private final String value;

    ProductSubTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProductSubTypeEnum fromValue(String v) {
        for (ProductSubTypeEnum c: ProductSubTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
