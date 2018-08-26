package com.A.util;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlCursor.TokenType;

public class XmlUtil {
	private static Logger logger = Logger.getLogger(XmlUtil.class);
	// private static final String START_ELEMENT = "<ac:";
	// public static final String SECOND_PHONE_NO = "ac:secondPhoneNumber";
	// public static final String FIRST_PHONE_NO = "ac:firstPhoneNumber";
	// public static final String WHEN_TO_CALLBACK = "ac:whenToCallBack";
	// public static final String SECOND_CALL_INFO = "ac:secondCallInfo";
	// public static final String CONSUMER = "ac:consumer";
	// public static final String BILLING_INFO = "ac:billingInfo";
	// public static final String PRICE_INFO = "ac:priceInfo";
	// public static final String BILLING_ADDR = "ac:billingAddress";
	// public static final String SVC_DELVRY_ADDR =
	// "ac:serviceOrDeliveryAddress";
	public static final String SELECTED_FEATURE = "selectedFeatures";

	// public static final String LINEITEM_DETAIL = "ac:lineItemDetail";
	// public static final String LINEITEM_STATUS = "ac:lineItemStatus";

	/**
	 * This method will check whether xml contains the element or not. If
	 * element not found then it will returns true else will return false.
	 * 
	 * @param cursor
	 * @param elementName
	 * @return
	 */

	public static boolean isElementPresent(String body, String elementName) {

		return (body.indexOf(elementName) != -1);
	}

	public static boolean isElementNull(XmlCursor cursor, String elementName) {
		try {
			boolean answer = Boolean.TRUE;

			while (cursor.hasNextToken()) {
				String xmlText = cursor.xmlText().toLowerCase();
				if (xmlText != null
						&& xmlText.contains(elementName.toLowerCase())) {
					answer = Boolean.FALSE;
					break;
				}
				cursor.toNextToken();
			}
			return answer;
		} finally {
			cursor.dispose();
		}

	}

	/**
	 * This method will check if element has no value in it or not. For eg.
	 * <element></element> is consider as empty element. In this case we will
	 * set the value to null for non-primitive type and zero for primitive type
	 * 
	 * @param cursor
	 * @param elementName
	 * @return
	 */
	public static boolean isElementNil(XmlCursor cursor, String elementName) {
		boolean answer = Boolean.FALSE;
		int indexOf = cursor.xmlText().indexOf(":");
		String startElement = cursor.xmlText().substring(0, indexOf + 1);
		logger.trace(startElement);

		try {

			while (cursor.hasNextToken()) {
				String xmlText = cursor.xmlText();
				if (xmlText != null
						&& xmlText.startsWith(startElement + elementName)) {
					String textValue = cursor.getTextValue();
					// In case of empty elements
					if (textValue != null && textValue.length() == 0) {
						logger.trace("Empty element (" + elementName
								+ ") found!!!");
						answer = Boolean.TRUE;
						break;
					} else
						break;
				}
				cursor.toNextToken();
			}
			return answer;
		} finally {
			cursor.dispose();
		}
	}

	/**
	 * This method will check whether xml contains the provided element value.
	 * If value not found then it will returns true else will return false.
	 * 
	 * @param cursor
	 * @param elementName
	 * @return
	 */
	public static boolean isElementValueExist(XmlCursor cursor,
			String elementValue) {
		boolean answer = Boolean.FALSE;

		try {
			while (cursor.hasNextToken()) {
				String xmlText = cursor.xmlText().toLowerCase().trim();
				if (xmlText != null
						&& xmlText.contains(elementValue.trim().toLowerCase())) {
					answer = Boolean.TRUE;
					break;
				}
				cursor.toNextToken();
			}
			return answer;
		} finally {
			cursor.dispose();
		}
	}
    
    /**
     * Utility method to check if element exist in xml or not. For accurate result, pass correct cursor. For eg. to check "value"
     * element in homeEmail is empty or nil, pass "custType.getHomeEMail().newCursor()" cursor. This way it will check "value"
     * element inside HomeEMail xml only and not whole customer xml.
     * @param cursor
     * @param elementName
     * @return
     */
    public static boolean checkEmptyOrNullElement(XmlCursor cursor, String elementName){
    	Boolean isEmpty = true;
    	
    	while (!cursor.toNextToken().isNone())
    	{
    	    /*
    	     * Use the intValue method to return the int corresponding to the
    		 * current token type. If it is the value for INT_START,
    		 * then you have a match.
    		 */
    	    switch (cursor.currentTokenType().intValue())
    	    {
    	        case TokenType.INT_START:
    	        	if(cursor.getName().getLocalPart().equalsIgnoreCase(elementName)){
    	        		String value = cursor.getTextValue();
    	        		if(value != null && value.trim().length() > 0){
    	        			isEmpty = false;
    	        		}
    	        	}
    	        break;
    	    }
    	    break;
    	}
    	cursor.dispose();
    	return isEmpty;
    }
}
