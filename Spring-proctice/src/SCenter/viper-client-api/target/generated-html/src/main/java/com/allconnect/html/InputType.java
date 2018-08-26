
package com.A.html;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InputType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="InputType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="text"/>
 *     &lt;enumeration value="password"/>
 *     &lt;enumeration value="checkbox"/>
 *     &lt;enumeration value="radio"/>
 *     &lt;enumeration value="submit"/>
 *     &lt;enumeration value="reset"/>
 *     &lt;enumeration value="file"/>
 *     &lt;enumeration value="hidden"/>
 *     &lt;enumeration value="image"/>
 *     &lt;enumeration value="button"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "InputType")
@XmlEnum
public enum InputType {

    @XmlEnumValue("text")
    TEXT("text"),
    @XmlEnumValue("password")
    PASSWORD("password"),
    @XmlEnumValue("checkbox")
    CHECKBOX("checkbox"),
    @XmlEnumValue("radio")
    RADIO("radio"),
    @XmlEnumValue("submit")
    SUBMIT("submit"),
    @XmlEnumValue("reset")
    RESET("reset"),
    @XmlEnumValue("file")
    FILE("file"),
    @XmlEnumValue("hidden")
    HIDDEN("hidden"),
    @XmlEnumValue("image")
    IMAGE("image"),
    @XmlEnumValue("button")
    BUTTON("button");
    private final String value;

    InputType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InputType fromValue(String v) {
        for (InputType c: InputType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
