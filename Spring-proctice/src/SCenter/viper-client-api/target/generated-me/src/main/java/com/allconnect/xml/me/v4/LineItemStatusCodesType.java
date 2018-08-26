
package com.A.xml.me.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for lineItemStatusCodesType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="lineItemStatusCodesType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="cancelled_removed"/>
 *     &lt;enumeration value="hold_authorization_pending"/>
 *     &lt;enumeration value="hold_customer_action"/>
 *     &lt;enumeration value="hold_order"/>
 *     &lt;enumeration value="hold_order_pending_problem"/>
 *     &lt;enumeration value="hold_provider"/>
 *     &lt;enumeration value="processing_aged"/>
 *     &lt;enumeration value="processing_schedule_pending"/>
 *     &lt;enumeration value="processing_schedule_confirmed"/>
 *     &lt;enumeration value="processing_cancelled"/>
 *     &lt;enumeration value="processing_connected"/>
 *     &lt;enumeration value="processing_disconnected"/>
 *     &lt;enumeration value="processing_problem_ordered_vru_failed"/>
 *     &lt;enumeration value="sales_new_order"/>
 *     &lt;enumeration value="sales_pre_order"/>
 *     &lt;enumeration value="sales_submitted"/>
 *     &lt;enumeration value="provision_ready"/>
 *     &lt;enumeration value="provision_problem"/>
 *     &lt;enumeration value="test_order"/>
 *     &lt;enumeration value="sales_defer"/>
 *     &lt;enumeration value="submit_failed"/>
 *     &lt;enumeration value="reverse_processing_aged"/>
 *     &lt;enumeration value="reverse_processing_schedule_pending"/>
 *     &lt;enumeration value="reverse_processing_schedule_confirmed"/>
 *     &lt;enumeration value="reverse_processing_connected"/>
 *     &lt;enumeration value="reverse_processing_disconnected"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "lineItemStatusCodesType")
@XmlEnum
public enum LineItemStatusCodesType {

    @XmlEnumValue("cancelled_removed")
    CANCELLED_REMOVED("cancelled_removed"),
    @XmlEnumValue("hold_authorization_pending")
    HOLD_AUTHORIZATION_PENDING("hold_authorization_pending"),
    @XmlEnumValue("hold_customer_action")
    HOLD_CUSTOMER_ACTION("hold_customer_action"),
    @XmlEnumValue("hold_order")
    HOLD_ORDER("hold_order"),
    @XmlEnumValue("hold_order_pending_problem")
    HOLD_ORDER_PENDING_PROBLEM("hold_order_pending_problem"),
    @XmlEnumValue("hold_provider")
    HOLD_PROVIDER("hold_provider"),
    @XmlEnumValue("processing_aged")
    PROCESSING_AGED("processing_aged"),
    @XmlEnumValue("processing_schedule_pending")
    PROCESSING_SCHEDULE_PENDING("processing_schedule_pending"),
    @XmlEnumValue("processing_schedule_confirmed")
    PROCESSING_SCHEDULE_CONFIRMED("processing_schedule_confirmed"),
    @XmlEnumValue("processing_cancelled")
    PROCESSING_CANCELLED("processing_cancelled"),
    @XmlEnumValue("processing_connected")
    PROCESSING_CONNECTED("processing_connected"),
    @XmlEnumValue("processing_disconnected")
    PROCESSING_DISCONNECTED("processing_disconnected"),
    @XmlEnumValue("processing_problem_ordered_vru_failed")
    PROCESSING_PROBLEM_ORDERED_VRU_FAILED("processing_problem_ordered_vru_failed"),
    @XmlEnumValue("sales_new_order")
    SALES_NEW_ORDER("sales_new_order"),
    @XmlEnumValue("sales_pre_order")
    SALES_PRE_ORDER("sales_pre_order"),
    @XmlEnumValue("sales_submitted")
    SALES_SUBMITTED("sales_submitted"),
    @XmlEnumValue("provision_ready")
    PROVISION_READY("provision_ready"),
    @XmlEnumValue("provision_problem")
    PROVISION_PROBLEM("provision_problem"),
    @XmlEnumValue("test_order")
    TEST_ORDER("test_order"),
    @XmlEnumValue("sales_defer")
    SALES_DEFER("sales_defer"),
    @XmlEnumValue("submit_failed")
    SUBMIT_FAILED("submit_failed"),
    @XmlEnumValue("reverse_processing_aged")
    REVERSE_PROCESSING_AGED("reverse_processing_aged"),
    @XmlEnumValue("reverse_processing_schedule_pending")
    REVERSE_PROCESSING_SCHEDULE_PENDING("reverse_processing_schedule_pending"),
    @XmlEnumValue("reverse_processing_schedule_confirmed")
    REVERSE_PROCESSING_SCHEDULE_CONFIRMED("reverse_processing_schedule_confirmed"),
    @XmlEnumValue("reverse_processing_connected")
    REVERSE_PROCESSING_CONNECTED("reverse_processing_connected"),
    @XmlEnumValue("reverse_processing_disconnected")
    REVERSE_PROCESSING_DISCONNECTED("reverse_processing_disconnected");
    private final String value;

    LineItemStatusCodesType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LineItemStatusCodesType fromValue(String v) {
        for (LineItemStatusCodesType c: LineItemStatusCodesType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
