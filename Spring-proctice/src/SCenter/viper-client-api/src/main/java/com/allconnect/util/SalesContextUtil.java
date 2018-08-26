package com.A.util;

import java.util.List;

import com.A.xml.v4.NameValuePairType;
import com.A.xml.v4.OrderManagementRequestResponse;
import com.A.xml.v4.SalesContextEntityType;
import com.A.xml.v4.SalesContextType;

public enum SalesContextUtil {

	INSTANCE;

	private static final String SOURCE_NAME = "source";
	private static final String ACCORD_VALUE = "accord";
	private static final String ACCORD_VALUE1 = "customer_care";
	private static final String ACCORD_VALUE2 = "fulfillment";

	public boolean isAccord(OrderManagementRequestResponse orderDocument) {

		boolean isAccord = Boolean.FALSE;

		if ((orderDocument != null) && (orderDocument.getResponse() != null)) {

			String source = SalesContextUtil.INSTANCE.getValue(orderDocument,
					SOURCE_NAME);

			isAccord = ((source != null) && (ACCORD_VALUE.equals(source.toLowerCase()) || ACCORD_VALUE1.equals(source.toLowerCase()) || ACCORD_VALUE2.equals(source.toLowerCase())));
		}

		return isAccord;
	}

	public String getValue(OrderManagementRequestResponse omrr, String name) {

		String value = null;

		if ((omrr != null) && (name != null)) {

			OrderManagementRequestResponse.Response response = omrr.getResponse();
			return getValue(response, name);
		}
		return value;
	}
	
	public String getValue(OrderManagementRequestResponse.Response response,
			String name) {
		String value = null;

		if ((response != null) && (name != null)) {

			return getValue(response.getSalesContext(), name);
		}

		return value;
	}

	public String getValue(SalesContextType salesContext, String name) {
		String value = null;

		if ((salesContext != null) && (name != null)
				&& (salesContext.getEntity() != null)
				&& (salesContext.getEntity().size() > 0)) {

			value = getValue(salesContext.getEntity(), name);

		}

		return value;
	}

	public String getValue(List<SalesContextEntityType> salesContextEntityList,
			String name) {
		String value = null;

		if (salesContextEntityList != null) {

			for (SalesContextEntityType salesContextEntity : salesContextEntityList) {
				if ((name != null) && (salesContextEntity != null)
						&& (salesContextEntity.getAttribute() != null)
						&& (salesContextEntity.getAttribute().size() > 0)) {

					value = getNameValue(salesContextEntity.getAttribute(),
							name);

					if (value != null) {
						return value;
					}
				}

			}

		}
		return value;

	}

	public String getNameValue(List<NameValuePairType> nameValueList,
			String name) {
		String value = null;

		if ((nameValueList != null) && (nameValueList.size() > 0)
				&& (name != null)) {
			for (NameValuePairType nameValue : nameValueList) {

				if ((name != null) && (nameValue != null)
						&& (name.equals(nameValue.getName()))) {
					value = nameValue.getValue();
				}

			}
		}
		return value;

	}

}
