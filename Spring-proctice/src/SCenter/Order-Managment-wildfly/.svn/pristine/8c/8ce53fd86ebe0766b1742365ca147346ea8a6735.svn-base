package com.AL.task.util;

import java.util.List;

import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;

public enum SalesContextUtil {

	INSTANCE;

	private static final String SOURCE_NAME = "source";
	private static final String ACCORD_VALUE = "accord";

	private static final String ENTITY_SYP= "SYP";
	private static final String UPDATED_BY_ATTRIB_NAME = "updatedBy";
	private static final String UPDATED_BY_VALUE = "fa";
	private static final String SESSION_STATUS_ATTRIBUTE_NAME = "sessionStatus";
	private static final String SESSION_STATUS_ATTRIBUTE_VALUE = "completed";
	public static final String SESSION_STATUS_KEY = "sessionStatusKey";
	public static final String PRICING_REQUIRED = "pricingRequired";
	public static final String PRICING_REQUIRED_VALUE = "true";
	private static final String UPDATED_BY_EXT_VALUE = "ext_process";

	public String getSource(OrderManagementRequestResponseDocument orderDocument) {

		String source= "unknown";

		if ((orderDocument != null)
				&& (orderDocument.getOrderManagementRequestResponse() != null)) {

			  source = SalesContextUtil.INSTANCE.getValue(
					orderDocument.getOrderManagementRequestResponse(),
					SOURCE_NAME);

		}

		return source;
	}

	/**
	 * Check is req came from encore or not
	 * @param oDoc
	 * @return
	 */
	public boolean isEncore(OrderManagementRequestResponseDocument oDoc) {

		boolean isEncore = Boolean.FALSE;

		if ((oDoc != null)
				&& (oDoc.getOrderManagementRequestResponse() != null) && (oDoc.getOrderManagementRequestResponse().getRequest() != null) &&
				(oDoc.getOrderManagementRequestResponse().getRequest().getSalesContext() != null)) {

			SalesContextType scType = oDoc.getOrderManagementRequestResponse().getRequest().getSalesContext();
			if(scType != null && scType.getEntityList() != null) {
			    List<SalesContextEntityType> entList = scType.getEntityList();
			    for(SalesContextEntityType sceType : entList) {
				if(sceType.getName().equalsIgnoreCase(ENTITY_SYP) && sceType.getAttributeList() != null) {
				    List<NameValuePairType> attrList = sceType.getAttributeList();
				    for(NameValuePairType nvType : attrList) {
					if(nvType.getName().equalsIgnoreCase(UPDATED_BY_ATTRIB_NAME) && nvType.getValue().equalsIgnoreCase(UPDATED_BY_VALUE)) {
					    isEncore = true;
					    break;
					}
				    }
				}
			    }
			}
		}

		return isEncore;
	}
	
	public boolean isSessionStatusCompleted(SalesContextType salesContext) {
		boolean isSessionStatusCompleted = false;
		if((salesContext != null) && (salesContext.getEntityList()!= null)) {
			entityListFor : for(SalesContextEntityType entity : salesContext.getEntityList()) {
				if(ENTITY_SYP.equalsIgnoreCase(entity.getName()) && (entity.getAttributeList() != null)) {
					for(NameValuePairType nameValuePair : entity.getAttributeList()) {
						if((nameValuePair != null) && 
								SESSION_STATUS_ATTRIBUTE_NAME.equalsIgnoreCase(nameValuePair.getName()) 
								&& SESSION_STATUS_ATTRIBUTE_VALUE.equalsIgnoreCase(nameValuePair.getValue())) {
							isSessionStatusCompleted = true;
							break entityListFor;
						}
					}
				}
			}
		}
		return isSessionStatusCompleted;
	}

	public String getAgentExtId(Request request) {

		String agentId = "";

		if (request.getOrderInfo() != null) {

			agentId = request.getOrderInfo().getAgentId();
		}

		return agentId;
	}
	public String getValue(OrderManagementRequestResponse omrr, String name) {

		String value = null;

		if ((omrr != null) && (name != null)) {

			OrderManagementRequestResponse.Request request = omrr.getRequest();
			return getValue(request, name);

		}
		return value;
	}

	public String getValue(OrderManagementRequestResponse.Request request,
			String name) {
		String value = null;

		if ((request != null) && (name != null)) {

			return getValue(request.getSalesContext(), name);
		}

		return value;
	}





	public String getValue(SalesContextType salesContext, String name) {
		String value = null;

		if ((salesContext != null) && (name != null)
				&& (salesContext.getEntityList() != null)
				&& (salesContext.getEntityList().size() > 0)) {

			value = getValue(salesContext.getEntityList(), name);

		}

		return value;
	}

	public String getValue(List<SalesContextEntityType> salesContextEntityList,
			String name) {
		String value = null;

		if (salesContextEntityList != null) {

			for (SalesContextEntityType salesContextEntity : salesContextEntityList) {
				if ((name != null) && (salesContextEntity != null)
						&& (salesContextEntity.getAttributeList() != null)
						&& (salesContextEntity.getAttributeList().size() > 0)) {

					value = getNameValue(salesContextEntity.getAttributeList(),
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
						&& (name.equalsIgnoreCase(nameValue.getName()))) {
					value = nameValue.getValue();
				}

			}
		}
		return value;

	}

	public boolean isPricingRequired(OrderManagementRequestResponseDocument orderDocument) {

		boolean isPricingRequired = Boolean.FALSE;
		if ((orderDocument != null) && (orderDocument.getOrderManagementRequestResponse() != null)) {

			String source = SalesContextUtil.INSTANCE.getValue(orderDocument.getOrderManagementRequestResponse(), PRICING_REQUIRED);
			isPricingRequired = ((source != null) && (PRICING_REQUIRED_VALUE.equals(source.toLowerCase())));
		}

		return isPricingRequired;
	}
	
	/**
	 * Check if the request made client is External Process
	 * @param oDoc
	 * @return boolean
	 */
	public boolean isExternal(OrderManagementRequestResponseDocument oDoc) {

		boolean isExternal = Boolean.FALSE;
		if ((oDoc != null)
				&& (oDoc.getOrderManagementRequestResponse() != null) && (oDoc.getOrderManagementRequestResponse().getRequest() != null) &&
				(oDoc.getOrderManagementRequestResponse().getRequest().getSalesContext() != null)) {

			SalesContextType scType = oDoc.getOrderManagementRequestResponse().getRequest().getSalesContext();
			if(scType != null && scType.getEntityList() != null) {
			    List<SalesContextEntityType> entList = scType.getEntityList();
			    for(SalesContextEntityType sceType : entList) {
					if(sceType.getName().equalsIgnoreCase(ENTITY_SYP) && sceType.getAttributeList() != null) {
					    List<NameValuePairType> attrList = sceType.getAttributeList();
					    for(NameValuePairType nvType : attrList) {
							if(nvType.getName().equalsIgnoreCase(UPDATED_BY_ATTRIB_NAME) && nvType.getValue().equalsIgnoreCase(UPDATED_BY_EXT_VALUE)) {
								isExternal = true;
							    break;
							}
					    }
					}
			    }
			}
		}

		return isExternal;
	}
}
