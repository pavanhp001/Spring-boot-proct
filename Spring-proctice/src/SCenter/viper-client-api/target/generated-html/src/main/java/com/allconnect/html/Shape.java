
package com.A.html;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Shape.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Shape">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="rect"/>
 *     &lt;enumeration value="circle"/>
 *     &lt;enumeration value="poly"/>
 *     &lt;enumeration value="default"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Shape")
@XmlEnum
public enum Shape {

    @XmlEnumValue("rect")
    RECT("rect"),
    @XmlEnumValue("circle")
    CIRCLE("circle"),
    @XmlEnumValue("poly")
    POLY("poly"),
    @XmlEnumValue("default")
    DEFAULT("default");
    private final String value;

    Shape(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Shape fromValue(String v) {
        for (Shape c: Shape.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
