package com.AL.ui.validation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.json.JSONException;
import org.json.JSONObject;

import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.exception.InvalidDataException;
import com.AL.ui.exception.InvalidFormatException;
import com.AL.ui.util.Utils;
import com.AL.util.DateUtil;
//import com.AL.ui.validation.EmailValidator;

/**
 * @author charter2
 *
 */
public enum CustomizationValidator {
	INSTANCE;

	private static String[] invalidAreaCodes =
	{
		"000",
		"111",
		"999"
	};

	/**
	 * Array of invalid last seven digits
	 */
	private static String[] invalidLastDigits =
	{
		"0000000",
		"1111111",
		"9999999"
	};

	private static String[] listOfCardTypes =
	{
		"Visa",
		"MasterCard",
		"Discover",
		"AmericanExpress"
	};

	/**
	 * Member Variables
	 */
	private static int AMEX_LOWER_BOUND_ONE     = 3400;
	private static int AMEX_UPPER_BOUND_ONE     = 3499;

	private static int AMEX_LOWER_BOUND_TWO     = 3700;
	private static int AMEX_UPPER_BOUND_TWO     = 3799;

	private static int VISA_LOWER_BOUND         = 4000;
	private static int VISA_UPPER_BOUND         = 4999;

	private static int MASTERCARD_LOWER_BOUND   = 5100;
	private static int MASTERCARD_UPPER_BOUND   = 5599;

	private static int DISCOVER_BOUND           = 6011;

	private static int MASTERCARD_LENGTH        = 16;

	private static int VISA_LENGTH_ONE          = 13;
	private static int VISA_LENGTH_TWO          = 16;

	private static int AMEX_LENGTH              = 15;

	private static int DISCOVER_LENGTH          = 16;

	public boolean validateDate(String date) throws InvalidDataException {

		Date endDate = DateUtil.fromStringToDate(date);

		Date sysDate = new Date();

		if(endDate.before(sysDate) || endDate.equals(sysDate)){
			throw new InvalidDataException("Please Enter a Valid End Date");
		}

		return true;
	}

	public List<String> validateEmail(Map<String, String> reqParamMap, String externalID, List<String> errorList) throws InvalidDataException, InvalidFormatException{

		if(reqParamMap.get(externalID) != null && !reqParamMap.get(externalID).trim().equals("")){
			String emailStr = reqParamMap.get(externalID); 
			int atIndex = emailStr.indexOf( "@" );
			String url = null;
			if ( !emailStr.equals("") ) {

				if ( atIndex <= 0 || (atIndex + 1) >= emailStr.length() ){
					errorList.add("E-mail not valid, missing or missplaced \"@\" symbol.");
				}
				else{
					url = emailStr.substring( atIndex + 1 );
					String address = emailStr.substring( 0, atIndex );
					if (!isProperAddress(url, true) || !isProperAddress(address, false)){
						errorList.add("E-mail not valid.");
					}
					if (!isValidDNS(url)) {
						errorList.add("Domain (" + url + ") does not have E-mail server.");
					}
				}
			}
		}
		return errorList;
	}

	public boolean isProperAddress( String str, boolean mustHaveDot )
	{
		if (str == null || str.length() == 0)
			return false;

		// check for dots
		// allow as many dots as they want,
		// just don't let them be adjacent to each other, or on the start or end.
		// loop over characters and check all of them
		boolean hasDot = false;
		boolean previousCharWasDot = false;
		int lastIndex = str.length()-1;
		for(int i=0; i<=lastIndex; i++)
		{
			char x = str.charAt(i);
			if(x == '.')
			{
				// check for first or last
				if(i == 0 || i == lastIndex)
				{
					return false;
				}
				// if 2 dots in a row, it's no good
				if(previousCharWasDot)
				{
					return false;
				}
				hasDot = true;
				previousCharWasDot = true;
			}
			else
			{
				if(!(Character.isLetterOrDigit(x) || x == '-' || x == '_'))
				{
					return false;
				}
				previousCharWasDot = false;
			}
		}
		return (mustHaveDot ? hasDot : true);
	}

	public boolean isValidDNS(String url) throws InvalidDataException {
		try {
			Hashtable <String, String> env = new Hashtable <String, String>();
			env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

			DirContext ictx = new InitialDirContext(env);
			Attributes mxAttr = ictx.getAttributes(url,
					new String[] {"MX"});
			if (mxAttr == null || mxAttr.size() == 0 || mxAttr.get("MX") == null) {
				return false;
			} else {
				return true;
			}
		} catch (NamingException e) {
			return false;
		}
	}

	public List<String> validatePhoneNumber(Map<String, String>requestParamMap, String externalID, List<String> errorList) throws InvalidFormatException, InvalidDataException{

		if(requestParamMap.get(externalID) != null && !requestParamMap.get(externalID).trim().equals("")){
			String phoneNumber = requestParamMap.get(externalID);

			if(!(phoneNumber.length() < 10)){
				String areaCode = phoneNumber.substring(0,3);
				String lastDigits =  phoneNumber.substring(3);
				if(Arrays.asList(invalidAreaCodes).indexOf(areaCode) >= 0)
				{
					String codes = "";
					for(int i = 0; i < invalidAreaCodes.length - 1; i++)
					{
						codes += invalidAreaCodes[i] + ", ";
					}
					if(invalidAreaCodes.length > 1)
					{
						codes += "or ";
					}
					codes += invalidAreaCodes[invalidAreaCodes.length - 1];
					errorList.add("Invalid area code (cannot be " + codes + ")");
				}

				if(Arrays.asList(invalidLastDigits).indexOf(lastDigits) >= 0)
				{
					String codes = "";
					for(int i = 0; i < invalidLastDigits.length - 1; i++)
					{
						codes += invalidLastDigits[i] + ", ";
					}
					codes += invalidLastDigits[invalidLastDigits.length - 1];
					errorList.add("Invalid phone number (last seven digits cannot be " + codes + ")");
				}
			}	
			else{
				errorList.add("Invalid Phone Number. Phone number must contain 10 digits");
			}
		}
		return errorList;
	}

	public List<String> validateRoutingNumber(Map<String, String>requestParamMap, String externalID, List<String> errorList){
		//		List<String> errorList = new ArrayList<String>();
		int checkDigit = -1;
		if(requestParamMap.get(externalID) != null && !requestParamMap.get(externalID).trim().equals("")){
			String routingNumber = requestParamMap.get(externalID);
			if ( routingNumber != null ) {
				if ( routingNumber.length() == 9 ) {
					try{
						Integer.parseInt(routingNumber);
						int results = (3 * Character.getNumericValue(routingNumber.charAt(0)))
						+ (7 * Character.getNumericValue(routingNumber.charAt(1)))
						+ (1 * Character.getNumericValue(routingNumber.charAt(2)))
						+ (3 * Character.getNumericValue(routingNumber.charAt(3)))
						+ (7 * Character.getNumericValue(routingNumber.charAt(4)))
						+ (1 * Character.getNumericValue(routingNumber.charAt(5)))
						+ (3 * Character.getNumericValue(routingNumber.charAt(6)))
						+ (7 * Character.getNumericValue(routingNumber.charAt(7)));

						checkDigit = roundUpTens(results, false) - results;
						if (checkDigit == Character.getNumericValue(routingNumber.charAt(8))) {

						}else {
							errorList.add("Routing Number incorrect.");
						}
					}
					catch (Exception e) {
						errorList.add("Routing Number cannot contain alphabets");
						return errorList;
					}
					
				} else if (routingNumber.length() > 0 && routingNumber.length() != 9) {
					errorList.add("Routing Number has incorrect length.");
				}
			}
		}
		return errorList;

	}


	private static int roundUpTens(int p_num, boolean p_round_equal)
	{
		int value = p_num;
		int remainder = p_num % 10;

		if ((remainder == 0) && p_round_equal)
		{
			value = p_num + 10;
		}
		else if (remainder > 0)
		{
			value = p_num + (10 - remainder);
		}
		return value;
	}

	/*
		Visa and Visa Electron: 13 or 16
		Mastercard: 16
		Discover: 16
		American Express: 15
	 */
	public List<String> validateCreditCardDetails(Map<String, String> reqParamMap, String externalID, List<DataField> availableDataFieldList) throws InvalidDataException, ParseException{
		List<String> errorList = new ArrayList<String>();
		int prefix = 0;
		int cardLength = 0;
		String cardType = "";

		if(reqParamMap != null && reqParamMap.get(externalID) != null){

			String cardNumber = reqParamMap.get(externalID);
			
			if(reqParamMap.get(externalID+"_JSONVAL") != null && reqParamMap.get(externalID+"_JSONVAL").trim().length() > 0){
				String strJsonVal = reqParamMap.get(externalID+"_JSONVAL");
				try {
					JSONObject jsonObject = new JSONObject(strJsonVal);
					if(jsonObject.get("enteredValue") != null){
						String enteredValue = (String) jsonObject.get("enteredValue");
						cardNumber = enteredValue;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			cardLength = cardNumber.length();

			if(cardNumber == null || cardNumber.trim().equals("")){
				errorList.add("Card type not selected.");
			}

			else if(cardNumber != null && !(cardNumber.trim().startsWith("*"))){

				if(Utils.isValidNumber(cardNumber) && cardNumber.length() > 4){
					prefix = Integer.parseInt(cardNumber.substring(0, 4));
				}
				else
				{
					errorList.add("Card number not completed.");
				}
				String creditCardTypeExternalID = "";
				for(DataField df : availableDataFieldList){
					if(df.getValueTarget() != null && df.getValueTarget().equals("consumerFinancialInfo.cardType")){
						creditCardTypeExternalID = df.getExternalId();
					}
				}
				if(reqParamMap.get(creditCardTypeExternalID) != null && !reqParamMap.get(creditCardTypeExternalID).trim().equals("")){
					cardType = reqParamMap.get(creditCardTypeExternalID);
					if(Arrays.asList(listOfCardTypes).indexOf(cardType)  >= 0 ){

						if(cardType.equals(listOfCardTypes[0])){
							if (prefix >= VISA_LOWER_BOUND && prefix <= VISA_UPPER_BOUND)
							{
								if (cardLength != VISA_LENGTH_ONE && cardLength != VISA_LENGTH_TWO) {
									errorList.add("VISA Card number incorrect length.");
								}
							}
							else
							{
								errorList.add("VISA Card number incorrect.");
							}
						}
						else if(cardType.equals(listOfCardTypes[1])){
							if (prefix >= MASTERCARD_LOWER_BOUND && prefix <= MASTERCARD_UPPER_BOUND)
							{
								if (cardLength != MASTERCARD_LENGTH) {
									errorList.add("Mastercard number incorrect length.");
								}
							}
							else
							{
								errorList.add("Mastercard number incorrect.");
							}
						}
						else if(cardType.equals(listOfCardTypes[2])){
							if (prefix == DISCOVER_BOUND) {
								if (cardLength != DISCOVER_LENGTH) {
									errorList.add("Discover card number incorrect length.");
								}
							}
							else
							{
								errorList.add("Discover card number incorrect.");
							}
						}
						else if(cardType.equals(listOfCardTypes[3])){
							if ((prefix >= AMEX_LOWER_BOUND_ONE && prefix <= AMEX_UPPER_BOUND_ONE) ||
									(prefix >= AMEX_LOWER_BOUND_TWO && prefix <= AMEX_UPPER_BOUND_TWO))
							{
								if (cardLength != AMEX_LENGTH) {
									errorList.add("AMEX Card number incorrect length.");
								}
							}
							else
							{
								errorList.add("AMEX Card number incorrect.");
							}
						}
					}
				}


				/*if (prefix >= VISA_LOWER_BOUND && prefix <= VISA_UPPER_BOUND)
				{
					if (cardLength != VISA_LENGTH_ONE && cardLength != VISA_LENGTH_TWO) {
						errorList.add("VISA Card number incorrect length.");
					}
				}
				else if (prefix >= MASTERCARD_LOWER_BOUND && prefix <= MASTERCARD_UPPER_BOUND)
				{
					if (cardLength != MASTERCARD_LENGTH) {
						errorList.add("Mastercard number incorrect length.");
					}
				}
				else if (prefix == DISCOVER_BOUND) {
					if (cardLength != DISCOVER_LENGTH) {
						errorList.add("Discover card number incorrect length.");
					}
				}
				else if ((prefix >= AMEX_LOWER_BOUND_ONE && prefix <= AMEX_UPPER_BOUND_ONE) ||
						(prefix >= AMEX_LOWER_BOUND_TWO && prefix <= AMEX_UPPER_BOUND_TWO))
				{
					if (cardLength != AMEX_LENGTH) {
						errorList.add("AMEX Card number incorrect length.");
					}
				}
				else{
					errorList.add("Card Number doesnot belong to any of the card types selected. Please enter a valid Card Number");
				}*/
			}
		}
		else{
			errorList.add("Card Number must not be null");
		}

		return errorList;
	}

	public List<String> validateSocialSecurityNumber(Map<String, String> reqParamMap, String externalID) throws InvalidDataException
	{
		List<String> errorList = new ArrayList<String>();

		if(externalID != null && !externalID.trim().equals("")){
			if(reqParamMap.get(externalID) != null){
				String ssn = reqParamMap.get(externalID);
				if(reqParamMap.get(externalID+"_JSONVAL") != null && reqParamMap.get(externalID+"_JSONVAL").trim().length() > 0){
					String strJsonVal = reqParamMap.get(externalID+"_JSONVAL");
					try {
						JSONObject jsonObject = new JSONObject(strJsonVal);
						if(jsonObject.get("enteredValue") != null){
							String enteredValue = (String) jsonObject.get("enteredValue");
							ssn = enteredValue;
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				errorList = SocialSecurityNumber.INSTANCE.isValid(ssn);
			}
		}
		else{
			errorList.add("SSN Should not be null");
		}
		return errorList;
	}
	
	public List<String> validateCalDetails(Map<String, String> reqParamMap, String externalID)
	{
		List<String> errorList = new ArrayList<String>();
		//		Map<String, String> errorMap = new HashMap<String, String>();

		if(externalID != null && !externalID.trim().equals("")){
			if(reqParamMap.get(externalID) != null){
				String enteredCALValue = reqParamMap.get(externalID);
				if(enteredCALValue.length() != 10){
					errorList.add("Invalid date.  Enter date in mm/dd/yyyy format.");
				}
			}
		}
		else{
			errorList.add("Date value should not be null");
		}
		return errorList;
	}
	
}

