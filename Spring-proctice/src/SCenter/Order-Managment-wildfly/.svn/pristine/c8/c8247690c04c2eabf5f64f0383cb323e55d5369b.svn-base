package com.AL.activity;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.SalesContextEntityType;

/**
 * @author maheshnag
 *
 */
public enum ActivitySalesContextEval {

	INSTANCE;
	
	private static final String NULL_STRING = null;
	
	public String getSource(final OrderManagementRequestResponseDocument orderDoc) {
		//"notes_source" is used to get source from FA and "source" for transactions from other modules
		//No meaning for entity(second param "source") since we are not validating that
		String source =  getValue( orderDoc, "source",   "notes_source") ;
		if (StringUtils.isEmpty(source)) {
			source = getValue( orderDoc, "source",   "source") ;
		}
		return source;
	}
	
	public String getValue(final OrderManagementRequestResponseDocument orderDoc, String entity, String name) {

		if ((orderDoc == null) || (orderDoc.getOrderManagementRequestResponse() == null)
				|| (orderDoc.getOrderManagementRequestResponse().getRequest() == null)) {
			return NULL_STRING;
		}

		OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request orderReq = orderDoc.getOrderManagementRequestResponse()
				.getRequest();

		if ((orderReq != null) && (orderReq.getSalesContext() != null)) {
			List<SalesContextEntityType> salesContextList = orderReq.getSalesContext().getEntityList();

			if ((salesContextList == null) || (salesContextList.size() == 0)) {
				return NULL_STRING;
			}

			for (SalesContextEntityType context : salesContextList) {
				if (context != null) {
					for (NameValuePairType nvpt : context.getAttributeList()) {
						if (name.equals(nvpt.getName())) {
							return nvpt.getValue();
						}
					}
				}
			}
		}
		return NULL_STRING;
	}
}
