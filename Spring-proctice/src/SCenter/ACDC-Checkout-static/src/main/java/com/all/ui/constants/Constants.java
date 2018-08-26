package com.AL.ui.constants;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.AL.xml.v4.CreditCardTypeType;

public class Constants {

	public static final String AUTHENTICATE_URI = "http://host:portcontext/uam_stub_response.html";

	public static final String[] BILLING_ADDR_ROLE_LIST = {"BillingAddress"};

	public static final String[] PREVIOUS_ADDR_ROLE_LIST = {"PreviousAddress"};

	public static final String[] SHIPPING_ADDR_ROLE_LIST = {"ShippingAddress"};
	
	public static final String[] CUSTOMER_CUSTOMIZE_LIST = {"firstName","lastName","middleName","emailAddress","BestContactOnDayOfInstallWeb"};
	
	public static final List<String> CREDIT_CARD_TYPES = new ArrayList<String>();

	static {
		for(CreditCardTypeType cardType : CreditCardTypeType.values()) {
			CREDIT_CARD_TYPES.add(cardType.value());
		}
	}

	//Request and session parameter constants
	public static final String SSN = "ssn";
	public static final String DOB = "dob";
	public static final String SAME_BILLING_ADDRESS_FLAG = "sameBillAddrFlag";
	public static final String BILL_STREET_ADDRESS = "billStreetAddress";
	public static final String BILL_CITY = "billCity";
	public static final String BILL_STATE = "billState";
	public static final String BILL_ZIP = "billZip";
	public static final String BILL_ZIP4 = "billZip4";
	public static final String SECURITY_PIN = "securityPin";
	public static final String SECURITY_QUESTION = "securityQuestion";
	public static final String SECURITY_ANSWER = "securityAnswer";
	public static final String SECOND_CONTACT = "secondContact";
	public static final String SECOND_CONTACT_EXT = "secondContactExt"; 

	public static final String ORDER_QUAL_VO = "orderQualVO";
	public static final String PAYMENT_EVENTS = "paymentEvent";
	public static final String INSTALL_DATE = "installDate";
	public static final String INSTALL_TIME = "installTime";
	public static final String CREDIT_CARD_ID = "creditcardId";
	public static final String ORDER_SUBMIT_VO = "orderSubmitVO"; 
	public static final String CARD_NUMBER = "cardNumber";
	public static final String CARD_HOLDER_NAME = "cardHolderName";
	public static final String CVV = "cvv";
	public static final String CC_EXP_YEAR = "expYear";
	public static final String CC_EXP_MONTH = "expMonth";
	public static final String CARD_TYPE = "cardType";


	public static final String DOB_DATE_FORMAT = "MM/dd/yyyy";
	public static final String INSTALL_DATE_FORMAT = "MM/dd/yyyy";
	public static final String INSTALL_DATE_UI_FORMAT = "M/d/yyyy";
	public static final String INSTALL_TIME_FORMAT = "hh:mm a";
	public static final String CREDIT_CARD_EXP_FORMAT = "yyyy-MM";
	public static final String ORDER_DATE_FORMAT = "yyyy-MM-dd";


	public static final String STATUS_ACTIVE = "active";
	public static final String INSTALL_TIME_TOKEN = " - ";
	public static final String SALES_CONTEXT_ENTITY = "CKO";
	public static final String ORDER_QUAL_STATUS = "ORDER_QUAL_STATUS";
	public static final String GUID = "GUID";
	public static final String AGENT_ID = "agentId";
	public static final String STR_TRUE = "true";


	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String MIDDLE_NAME = "middleName";
	public static final String TITLE = "title";
	public static final String NAME_SUFFIX = "nameSuffix";
	public static final String DRIVER_LICENSE = "driverLicense";
	public static final String STATE = "driverState";
	public static final String FINANCIAL_INSTITUTION_NAME = "financialInstitutionName";
	public static final String EMPLOYER_NAME = "employerName";
	public static final String EMAIL_ADDRESS = "emailAddress";
	public static final String EMPLOYER_TELEPHONE = "employerTelephone";
	public static final String OCCUPATION = "occupation";
	public static final String RETIRED = "retired";
	public static final String RENT_OR_OWN = "rentOrOwn";
	public static final String FULLTIME_STUDENT = "fullTimeStudent";
	public static final String OTHER_INCOMESOURCE = "otherIncomeSource";
	public static final String HOME_CONTACTNUMBER = "DisabledHomePhoneNum";
	public static final String WORK_CONTACT_NUMBER = "DisabledWorkPhoneNum";
	public static final String WORK_CONTACTNUMBER = "workPhoneNumber";
	public static final String WORK_EXT_NUMBER = "workExtensionNumber";
	public static final String CELL_CONTACTNUMBER = "DisabledCellPhoneNum";
	public static final String PHONE_OPT_IN = "SMSOptIn";
	public static final String MARKET_OPT_IN = "OfferPromotionOptIn";
	public static final String ADDRESS_UNIQUEID = "addressUniqueId";
	public static final String STATUS = "status";
	public static final String EXTERNAL_ID = "externalId";
	public static final String CUSTOMER_TYPE = "customerType";
	public static final String TN = "tn";
	public static final String TPV_HOLD_STATUS = "tpvHoldStatus";
	public static final String VALIDATION_LEVEL = "validationLevel";
	public static final String LINE_PROD_CATG_VALIDATION_LEVEL = "lineProdCatgValidationLevel";
	public static final String LINE_NUMBER = "lineNumber";
	public static final String SELECTED_FEATURES = "selectedFeatures";
	public static final String STREET_NUMBER = "streetNumber";
	public static final String STREET_NAME = "streetName";
	public static final String CITY = "city";
	public static final String STATE_OR_PROVINCE = "stateOrProvince";
	public static final String POSTAL_CODE = "postalCode";
	public static final String BTN = "btn";
	public static final String IS_EMPLOYED = "isEmployed";
	public static final String CREDIT_CHECK = "creditCheck";
	public static final String REQUESTTN_CHECK = "requestTNCheck";
	public static final String CC_TRANS_TYPE = "transactionType";
	public static final String CREDITCARD_PROCESSING_CHECK = "creditCardProcessingCheck";
	public static final String TRANSACTION_TYPE = "transactionType";
	public static final String CARD_NO = "cardNo";
	public static final String SECURITY_CODE ="securityCode";
	public static final String EXPIRATION_DATE ="expirationDate";
	public static final String ZIP_CODE = "zipCode";
	public static final String DUE_DATE = "dueDate";
	public static final String IS_ADDRESS = "sameBillAddrFlag";
	public static final String SERVC_ADDRESS_REF = "servAddrRef";

	/* Feedback Status constants here */

	public static final String PAGE_ID = "page_id";
	public static final String PROVIDER_ID = "provider_id";
	public static final String PRODUCT_EXTERNALID = "productExternalId";
	public static final String CERTIFICATE = "certificate";
	public static final String CKO_COMPLETE = "CKOComplete";
	public static final String CKO_ERROR = "CKOError";	
	public static final String CKO_READY = "CKOReady";	
	public static final String CKO_SUBMIT = "CKOSubmit";
	public static final String CKO_SUCCESS = "CKOSuccess";
	public static final String CKO_WARNING = "CKOWarning";
	public static final String CKO_INCOMPLETE = "CKOInComplete";
	public static final String REMOVED = "Removed";

	public static final String PRODUCT_INFO_PAGE_ID = "Product Info page";
	public static final String PRODUCT_INFO_PAGE_VAL = "101";	

	public static final String OQ_DEMO_CONTENTS_ID = "OQ Demo Contents page";
	public static final String OQ_DEMO_CONTENTS_VAL = "102";

	/*public static final String CREDIT_CHECK_PAGE_ID = "CreditCheckInfo page";
	public static final String CREDIT_CHECK_PAGE_VAL = "103";
	public static final String UPDATE_LINEITEM_FAIL_KEY = "OrderQual RequestTN page";
	public static final String UPDATE_LINEITEM_FAIL_VAL = "104";	
	public static final String ORDERQUAL_DISPALY_PAGE_ID = "OrderQual Display TN page";
	public static final String ORDERQUAL_DISPALY_PAGE_VAL = "105";
	public static final String PAYMENT_PAGE_ID = "OrderQual Payment Page";
	public static final String PAYMENT_PAGE_VAL = "106";
	public static final String ORDERQUAL_DUEDATE_PAGE_ID = "OrderQual Display DueDates page";
	public static final String ORDERQUAL_DUEDATE_PAGE_VAL = "107";	
	public static final String ORDERSUBMIT_PAGE_ID = "Order Submit page";
	public static final String ORDERSUBMIT_PAGE_VAL = "108";*/

	/* Error code constants declaration here */
	public static final String SUCCESS_ST_CODE_KEY = "Success";
	public static final String SUCCESS_ST_CODE_VAL = "0";	
	public static final String ERROR_ST_CODE_KEY = "Error";
	public static final String ERROR_ST_CODE_VAL = "100";	
	public static final String WARNING_ST_CODE_KEY = "Warning";
	public static final String WARNING_ST_CODE_VAL = "1000";

	public static final String I_DATA = "iData";	

	public static final Pattern NUMBER_PATTERN  = Pattern.compile("\\d+");


	public static final String NESTED_CUST_JAVASCRIPT = "/WEB-INF/classes/template/nested_customization.js";

	public static final String ATT_UVERSE_XML_EBAN = "/Users/ethomas/Documents/workspace-sts-3.0.0.RELEASE/CKO-static/src/main/resources/xml/UverseTripplePlay.xml";
	public static final String ATT_UVERSE_XML = ".\\src\\main\\resources\\xml\\UverseTripplePlay.xml";  

	public static final String PROMOTION_DETAILS = "PROMOTION_DETAILS";

	public static final int REASON_CODE = 0;

	public static final String DIGTIAL_KEY = "channelType=web";

	public static final String _2F = "%.2f";
	
	public static final String BOOLEAN_DATA_CONSTRAIN = "Boolean";
	public static final String STRING_DATA_CONSTRAIN = "String";
	public static final String INTEGER_DATA_CONSTRAIN = "Integer";

	public static final String DIALOGUE = "dialogue";
	public static final String CHECKBOX = "checkbox";
	public static final String TEXT = "text";
	public static final String DROP_DOWN = "dropDown";
	public static final String TICK = "tick";

	public static final String FEATURE_GROUP_TYPE = "FeatureGroupType";
	public static final String FEATURE = "Feature";
	
	public static final String PROMOTION = "promotion";
	public static final String RELATIVE = "relative";
	public static final String ABSOLUTE = "absolute";
	public static final String NON_RECURING_PRICE = "nonRecuringPrice";
	public static final String RECURING_PRICE = "recuringPrice";
	public static final String BASE_MONTHLY_DISCOUNT = "baseMonthlyDiscount";
	public static final String ONE_TIME_FEE_DISCOUNT = "oneTimeFeeDiscount";
	public static final String MOBILE_PHONE_NUMBER = "MobileNumber";
	public static final String PRIMARY_PHONE_NUMBER = "PrimaryNumber";
	
	public static final String CKO_INPUT = "CKOInput";
	public static final String CHANNEL_TYPE = "channelType";
	public static final String PHONE_NO = "phoneNo";
	public static final String CHANNEL_CSS = "channelCss";
	public static final String DISPLAY_TYPE = "displaytype";
	public static final String NAME = "name";
	public static final String VALUE = "value";
	public static final String VALUE_TARGET = "valueTarget";
	public static final String DTMURL = "dtmUrl";
	public static final String PROMO_SIGN = "promoWithSign";
	public static final String APPLIANCE_FLAG="applianceFlag";
}
