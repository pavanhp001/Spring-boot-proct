package com.A.validation.impl;

import java.util.Arrays;

import com.A.validation.Message;
import com.A.validation.ReportFactory;
import com.A.validation.ValidationReport;

/**
 * @author ebthomas
 * 
 */
public class DefaultReportFactory implements ReportFactory {

	/**
	 * Default Report Factory.
	 */
	public DefaultReportFactory() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public Message createMessage(Message.Type type, Long code, String key,
			Object... context) {
		if (type == null) {
			type = Message.Type.ERROR;
		}
		if (code == null) {
			code = 999L;
		}

		if (key == null) {
			key = "error with missing code and key";
		}
		return new DefaultMessage(type, code, key, Arrays.asList(context));
	}

	/**
	 * {@inheritDoc}
	 */
	public ValidationReport createValidationReport() {
		return new DefaultValidationReport();
	}
}
