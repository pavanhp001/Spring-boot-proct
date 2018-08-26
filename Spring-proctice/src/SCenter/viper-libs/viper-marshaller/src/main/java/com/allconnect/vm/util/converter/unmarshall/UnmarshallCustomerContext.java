package com.A.vm.util.converter.unmarshall;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerContext;
import com.A.xml.v4.CustomerContextEntityType;
import com.A.xml.v4.CustomerContextType;
import com.A.xml.v4.CustomerManagementRequestResponseDocument.CustomerManagementRequestResponse.Request;
import com.A.xml.v4.NameValuePairType;

public class UnmarshallCustomerContext {

    private static final Logger logger = Logger.getLogger(UnmarshallCustomerAttribute.class);
    public static final class Builder {


	private Builder() {
		super();
	}


	 public static void copyCustomerContext(Request request,Consumer consumer) {
		List<CustomerContext> existingContextList = consumer.getCustomerContexts();
		List<CustomerContext> newContextList = new ArrayList<CustomerContext>();
		if (request.getCustomerContext() != null) {
		    CustomerContextType contextType = request.getCustomerContext();
		    if (contextType.getEntityList() != null) {
			List<CustomerContextEntityType> contextList = contextType.getEntityList();
			for (CustomerContextEntityType entityType : contextList) {

			    List<NameValuePairType> nvPairList = entityType.getAttributeList();
			    for (NameValuePairType nvPairType : nvPairList) {
				CustomerContext destContext = new CustomerContext();
				destContext.setEntityName(entityType.getName());
				destContext.setName(nvPairType.getName());
				destContext.setValue(nvPairType.getValue());
				newContextList.add(destContext);
			    }
			}
		    }
		}

		//Filter old context attributes which is being passed in new request
		if(existingContextList != null && !existingContextList.isEmpty() && newContextList != null && !newContextList.isEmpty()) {
		    Iterator<CustomerContext> extContextIter = existingContextList.iterator();
		    while(extContextIter.hasNext()) {
			CustomerContext extContext = extContextIter.next();
			for(CustomerContext newContext : newContextList) {
			    if(newContext.getEntityName().equalsIgnoreCase(extContext.getEntityName()) && newContext.getName().equalsIgnoreCase(extContext.getName())) {
				extContextIter.remove();
				break;
			    }
			}
		    }
		}

		List<CustomerContext> finalContextList = new ArrayList<CustomerContext>();

		if(existingContextList != null) {
		    finalContextList.addAll(existingContextList);
		}
		finalContextList.addAll(newContextList);
		consumer.setCustomerContexts(finalContextList);
	    }

    }


}
