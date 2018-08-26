
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransactionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="getAvailableProducts"/>
 *     &lt;enumeration value="getOrderFromProvider"/>
 *     &lt;enumeration value="getProviderAvailableStartDates"/>
 *     &lt;enumeration value="getProviderSpecificCustomerInformation"/>
 *     &lt;enumeration value="performProviderSpecificCreditCheck"/>
 *     &lt;enumeration value="submitOrderToProvider"/>
 *     &lt;enumeration value="updateOrder"/>
 *     &lt;enumeration value="validateAddressWithProvider"/>
 *     &lt;enumeration value="validateAndSubmitOrderWithProvider"/>
 *     &lt;enumeration value="validateOrderWithProvider"/>
 *     &lt;enumeration value="getDetails"/>
 *     &lt;enumeration value="getPromotions"/>
 *     &lt;enumeration value="serviceQualification"/>
 *     &lt;enumeration value="getProductCatalog"/>
 *     &lt;enumeration value="getDueDates"/>
 *     &lt;enumeration value="getTermsAndConditions"/>
 *     &lt;enumeration value="orderSubmission"/>
 *     &lt;enumeration value="creditQualification"/>
 *     &lt;enumeration value="orderQualification"/>
 *     &lt;enumeration value="getOfferFeatures"/>
 *     &lt;enumeration value="configureOfferFeatures"/>
 *     &lt;enumeration value="submitPayment"/>
 *     &lt;enumeration value="getInstallationDates"/>
 *     &lt;enumeration value="setInstallationDate"/>
 *     &lt;enumeration value="valiateOrder"/>
 *     &lt;enumeration value="reviewOrder"/>
 *     &lt;enumeration value="changeCustomerType"/>
 *     &lt;enumeration value="authenticateCustomer"/>
 *     &lt;enumeration value="validatePayment"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransactionType", namespace = "http://xml.A.com/common")
@XmlEnum
public enum TransactionType {

    @XmlEnumValue("getAvailableProducts")
    GET_AVAILABLE_PRODUCTS("getAvailableProducts"),
    @XmlEnumValue("getOrderFromProvider")
    GET_ORDER_FROM_PROVIDER("getOrderFromProvider"),
    @XmlEnumValue("getProviderAvailableStartDates")
    GET_PROVIDER_AVAILABLE_START_DATES("getProviderAvailableStartDates"),
    @XmlEnumValue("getProviderSpecificCustomerInformation")
    GET_PROVIDER_SPECIFIC_CUSTOMER_INFORMATION("getProviderSpecificCustomerInformation"),
    @XmlEnumValue("performProviderSpecificCreditCheck")
    PERFORM_PROVIDER_SPECIFIC_CREDIT_CHECK("performProviderSpecificCreditCheck"),
    @XmlEnumValue("submitOrderToProvider")
    SUBMIT_ORDER_TO_PROVIDER("submitOrderToProvider"),
    @XmlEnumValue("updateOrder")
    UPDATE_ORDER("updateOrder"),
    @XmlEnumValue("validateAddressWithProvider")
    VALIDATE_ADDRESS_WITH_PROVIDER("validateAddressWithProvider"),
    @XmlEnumValue("validateAndSubmitOrderWithProvider")
    VALIDATE_AND_SUBMIT_ORDER_WITH_PROVIDER("validateAndSubmitOrderWithProvider"),
    @XmlEnumValue("validateOrderWithProvider")
    VALIDATE_ORDER_WITH_PROVIDER("validateOrderWithProvider"),
    @XmlEnumValue("getDetails")
    GET_DETAILS("getDetails"),
    @XmlEnumValue("getPromotions")
    GET_PROMOTIONS("getPromotions"),
    @XmlEnumValue("serviceQualification")
    SERVICE_QUALIFICATION("serviceQualification"),
    @XmlEnumValue("getProductCatalog")
    GET_PRODUCT_CATALOG("getProductCatalog"),
    @XmlEnumValue("getDueDates")
    GET_DUE_DATES("getDueDates"),
    @XmlEnumValue("getTermsAndConditions")
    GET_TERMS_AND_CONDITIONS("getTermsAndConditions"),
    @XmlEnumValue("orderSubmission")
    ORDER_SUBMISSION("orderSubmission"),
    @XmlEnumValue("creditQualification")
    CREDIT_QUALIFICATION("creditQualification"),
    @XmlEnumValue("orderQualification")
    ORDER_QUALIFICATION("orderQualification"),
    @XmlEnumValue("getOfferFeatures")
    GET_OFFER_FEATURES("getOfferFeatures"),
    @XmlEnumValue("configureOfferFeatures")
    CONFIGURE_OFFER_FEATURES("configureOfferFeatures"),
    @XmlEnumValue("submitPayment")
    SUBMIT_PAYMENT("submitPayment"),
    @XmlEnumValue("getInstallationDates")
    GET_INSTALLATION_DATES("getInstallationDates"),
    @XmlEnumValue("setInstallationDate")
    SET_INSTALLATION_DATE("setInstallationDate"),
    @XmlEnumValue("valiateOrder")
    VALIATE_ORDER("valiateOrder"),
    @XmlEnumValue("reviewOrder")
    REVIEW_ORDER("reviewOrder"),
    @XmlEnumValue("changeCustomerType")
    CHANGE_CUSTOMER_TYPE("changeCustomerType"),
    @XmlEnumValue("authenticateCustomer")
    AUTHENTICATE_CUSTOMER("authenticateCustomer"),
    @XmlEnumValue("validatePayment")
    VALIDATE_PAYMENT("validatePayment");
    private final String value;

    TransactionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransactionType fromValue(String v) {
        for (TransactionType c: TransactionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
