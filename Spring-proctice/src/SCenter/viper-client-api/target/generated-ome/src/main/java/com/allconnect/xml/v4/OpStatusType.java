
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OpStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OpStatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="SUCCESS"/>
 *     &lt;enumeration value="INFO"/>
 *     &lt;enumeration value="ERROR"/>
 *     &lt;enumeration value="WARN"/>
 *     &lt;enumeration value="FATAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OpStatusType", namespace = "http://xml.A.com/common")
@XmlEnum
public enum OpStatusType {

    SUCCESS,
    INFO,
    ERROR,
    WARN,
    FATAL;

    public String value() {
        return name();
    }

    public static OpStatusType fromValue(String v) {
        return valueOf(v);
    }

}
