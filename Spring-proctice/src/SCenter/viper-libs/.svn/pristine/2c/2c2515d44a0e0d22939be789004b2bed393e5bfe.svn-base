package com.A.vm.util.converter.unmarshall;

import java.util.Calendar;

import org.apache.xmlbeans.XmlCursor;

import com.A.comm.manager.jms.util.StringUtil;
import com.A.xml.v4.AddressType;
import com.A.xml.v4.CustomerType;

public enum UnmarshallUtil {

	INSTANCE;
	
	public   String getValue(XmlCursor cursor, String elementName) {

		
		
		 

		while (cursor.hasNextToken()) {
			String xmlText = cursor.xmlText();
			int indexOf = cursor.xmlText().indexOf(":");
			String startElement = cursor.xmlText().substring(0, indexOf + 1);
			if (xmlText != null
					&& xmlText.startsWith(startElement + elementName)) {
				String textValue = cursor.getTextValue();
				// In case of empty elements
				if (textValue != null && textValue.length() > 0) {
					return textValue.substring(0, 10);

				}

			}
			cursor.toNextToken();
		}
		cursor.dispose();

		return null;
	}

	public   Calendar getDate(final CustomerType src, String field) {
		String dateDob = getValue(src.newCursor(), "dob");

		// DOB
		if (dateDob != null) {

			String[] dob = StringUtil.tokenize(dateDob, "-");
			if (dob.length >= 0) {
				int year = Integer.valueOf(dob[0]);
				int month = Integer.valueOf(dob[1]);
				int day = Integer.valueOf(dob[2]);

				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month-1);
				cal.set(Calendar.DAY_OF_MONTH, day);

				return cal;
			}

		}
		return null;

	}
	
	public Calendar getDate(final AddressType src, String date) {
		if (date != null) {
			String[] dateArr = StringUtil.tokenize(date, "-");
			if (dateArr.length >= 0) {
				int year = Integer.valueOf(dateArr[0]);
				int month = Integer.valueOf(dateArr[1]);
				int day = Integer.valueOf(dateArr[2]);

				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month-1);
				cal.set(Calendar.DAY_OF_MONTH, day);

				return cal;
			}

		}
		return null;

	}
}
