package com.A.V.factory;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.A.V.domain.SalesContext;
import com.A.xml.di.v4.NameValuePairType;
import com.A.xml.di.v4.SalesContextEntityType;
import com.A.xml.v4.SalesContextType;

public enum SalesContextFactory {

	INSTANCE;
	
	public static final String CONTEXT = "SALES_CONTEXT";

	/**
	 * @param salesContext
	 * @return
	 */
	public String toString(SalesContext salesContext) {

		StringBuilder sb = new StringBuilder();

		List<SalesContextEntityType> scet = salesContext.getEntity();

		for (SalesContextEntityType salesContextEntityType : scet) {

			sb.append("e-" + salesContextEntityType.getName());

			List<NameValuePairType> attrMap = salesContextEntityType
					.getAttribute();

			for (NameValuePairType nameValue : attrMap) {

				sb.append("k#").append(nameValue.getName());
				sb.append("v#").append(nameValue.getValue());
			}
		}

		return sb.toString();
	}

	/**
	 * @param data
	 * @return
	 */
	public String toString(Map<String, Map<String, String>> data) {

		StringBuilder sb = new StringBuilder();

		for (String entityName : data.keySet()) {

			sb.append("e-" + entityName);

			Map<String, String> attrMap = data.get(entityName);

			for (String keyName : attrMap.keySet()) {
				String value = attrMap.get(keyName);
				sb.append("k#").append(keyName);
				sb.append("v#").append(value);
			}
		}

		return sb.toString();
	}

	/**
	 * @param salesContext
	 * @param data
	 * @return
	 */
	public SalesContext getSalesContext(SalesContext salesContext,
			Map<String, Map<String, String>> data) {

		if (data == null) {
			return salesContext;
		}

		if (salesContext == null) {
			salesContext = new SalesContext();
		}

		for (String entityName : data.keySet()) {

			SalesContextEntityType entity = new SalesContextEntityType();
			salesContext.getEntity().add(entity);
			entity.setName(entityName);

			List<NameValuePairType> nvPairList = entity.getAttribute();

			Map<String, String> attrMap = data.get(entityName);

			for (String keyName : attrMap.keySet()) {
				String value = attrMap.get(keyName);

				NameValuePairType nvPairType = new NameValuePairType();
				nvPairList.add(nvPairType);
				nvPairType.setName(keyName);
				nvPairType.setValue(value);

			}
		}

		return salesContext;
	}

	/**
	 * @param data
	 * @return
	 */
	public SalesContext getSalesContext(Map<String, Map<String, String>> data) {

		SalesContext salesContext = new SalesContext();

		return getSalesContext(salesContext, data);
	}
	
	/**
	 * To Construct SalesContextType of type in xml.v4 package
	 * 
	 * @param data
	 * @return SalesContextType
	 */
	public SalesContextType getXMLV4SalesContext(Map<String, Map<String, String>> data) {

		SalesContextType salesContext = new SalesContextType();
		if(data == null){
			return salesContext;
		}

		for (String entityName : data.keySet()) {

			com.A.xml.v4.SalesContextEntityType entity = new com.A.xml.v4.SalesContextEntityType();
			salesContext.getEntity().add(entity);
			entity.setName(entityName);

			List<com.A.xml.v4.NameValuePairType> nvPairList = entity.getAttribute();

			Map<String, String> attrMap = data.get(entityName);

			for (String keyName : attrMap.keySet()) {
				String value = attrMap.get(keyName);

				com.A.xml.v4.NameValuePairType nvPairType = new com.A.xml.v4.NameValuePairType();
				nvPairList.add(nvPairType);
				nvPairType.setName(keyName);
				nvPairType.setValue(value);
			}
		}

		return salesContext;
	}

	/**
	 * @param entityName
	 * @param attrName
	 * @param attrValue
	 * @return
	 */
	public SalesContextType getSalesContext(String entityName, String attrName, String attrValue) {

		SalesContextType salesContextType = new SalesContextType();

		com.A.xml.v4.SalesContextEntityType salesContextEntityType = new com.A.xml.v4.SalesContextEntityType();
		salesContextEntityType.setName(entityName);

		com.A.xml.v4.NameValuePairType nameValuePairType = new com.A.xml.v4.NameValuePairType();
		nameValuePairType.setName(attrName);
		nameValuePairType.setValue(attrValue);
		salesContextEntityType.getAttribute().add(nameValuePairType);
		salesContextType.getEntity().add(salesContextEntityType);
		
		return salesContextType;
	}
	
	/**
	 * Get attribute value
	 * 
	 * @param salesContext
	 * @param entityName
	 * @param attrName
	 * @return
	 */
	public String getAttribute(SalesContextType salesContext, String entityName, String attrName){
		if(salesContext != null){
			for(com.A.xml.v4.SalesContextEntityType entityType : salesContext.getEntity()){
				if(entityType.getName().equals(entityName)){
					for(com.A.xml.v4.NameValuePairType nameValuePairType:  entityType.getAttribute()){
						if(nameValuePairType.getName().equals(attrName)){
							return nameValuePairType.getValue();
						}
					}
				}
			}
		}
		
		return "";
	}
	
	/**
	 * Get attribute value
	 * 
	 * @param salesContext
	 * @param attrName
	 * @return
	 */
	public String getAttribute(SalesContextType salesContext, String attrName){
		if(salesContext != null){
			for(com.A.xml.v4.SalesContextEntityType entityType : salesContext.getEntity()){
				for(com.A.xml.v4.NameValuePairType nameValuePairType:  entityType.getAttribute()){
					if(nameValuePairType.getName().equals(attrName)){
						String value = nameValuePairType.getValue();
						if(StringUtils.isNotBlank(value)){
							return value;
						}
					}
				}
			}
		}
		
		return "";
	}
	
	/**
	 * Get SalesContextType from session
	 * 
	 * @param session
	 * @return
	 */
	public SalesContextType getContextFromSession(HttpSession session){
		if(session == null){
			return new SalesContextType();
		}
		return (SalesContextType)session.getAttribute(CONTEXT);
	}
	
	/**
	 * Set SalesContextType In Session
	 * 
	 * @param session
	 * @param context
	 */
	public void setContextInSession(HttpSession session, SalesContextType context){
		if(session != null && context != null){
			session.setAttribute(CONTEXT, context);
		}
	}

}