
package com.A.xml.se.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for YesNoEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="YesNoEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="YES"/>
 *     &lt;enumeration value="NO"/>
 *     &lt;enumeration value="MAYBE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "YesNoEnum")
@XmlEnum
public enum YesNoEnum {

    NONE,
    YES,
    NO,
    MAYBE;

    public String value() {
        return name();
    }

    public static YesNoEnum fromValue(String v) {
        return valueOf(v);
    }

}
