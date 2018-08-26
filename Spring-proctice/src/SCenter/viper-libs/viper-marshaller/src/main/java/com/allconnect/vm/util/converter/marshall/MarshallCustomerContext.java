package com.A.vm.util.converter.marshall;

import java.util.List;

import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerContext;
import com.A.xml.v4.CustomerContextEntityType;
import com.A.xml.v4.CustomerContextType;
import com.A.xml.v4.CustomerManagementRequestResponseDocument.CustomerManagementRequestResponse.Response;
import com.A.xml.v4.NameValuePairType;

public class MarshallCustomerContext {

    public static void buildCustomerContext(Response response, Consumer consumer) {
	CustomerContextType ctxType = response.addNewCustomerContext();
	List<CustomerContext> contextList = consumer.getCustomerContexts();

	int i = 0;
	String entityName = "";

	for (CustomerContext srcCtx : contextList) {
	    CustomerContextEntityType entityType = null;

	    if (i == 0) {
		entityType = ctxType.addNewEntity();
		entityName = srcCtx.getEntityName();
		entityType.setName(entityName);
		NameValuePairType nvType = entityType.addNewAttribute();
		nvType.setName(srcCtx.getName());
		nvType.setValue(srcCtx.getValue());
		i++;
	    }
	    else if (srcCtx != null && isEnityTypeExist(ctxType.getEntityList(), srcCtx.getEntityName())) {
		List<CustomerContextEntityType> ctxTypeLIst = ctxType.getEntityList();
		entityType = getEnityType(ctxTypeLIst, srcCtx.getEntityName());
		entityName = srcCtx.getEntityName();
		entityType.setName(entityName);
		NameValuePairType nvType = entityType.addNewAttribute();
		nvType.setName(srcCtx.getName());
		nvType.setValue(srcCtx.getValue());
	    }
	    else {
		entityType = ctxType.addNewEntity();
		entityName = srcCtx.getEntityName();
		entityType.setName(entityName);
		NameValuePairType nvType = entityType.addNewAttribute();
		nvType.setName(srcCtx.getName());
		nvType.setValue(srcCtx.getValue());
		i++;
	    }

	}
    }

    private static Boolean isEnityTypeExist(List<CustomerContextEntityType> ccEntityList, String entityName) {
	for (CustomerContextEntityType entType : ccEntityList) {
	    if (entityName.equalsIgnoreCase(entType.getName())) {
		return true;
	    }
	}
	return false;
    }

    private static CustomerContextEntityType getEnityType(List<CustomerContextEntityType> ccEntityList, String entityName) {
	for (CustomerContextEntityType entType : ccEntityList) {
	    if (entityName.equalsIgnoreCase(entType.getName())) {
		return entType;
	    }
	}
	return null;
    }
}
