package com.A.vm.util.converter.marshall;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerAttribute;
import com.A.xml.v4.Attributes;
import com.A.xml.v4.CustomerAttributeDetailType;
import com.A.xml.v4.CustomerAttributeEntityType;
import com.A.xml.v4.CustomerAttributeType;

public class MarshallCustomerAttribute {
	private static final Logger logger = Logger.getLogger(MarshallCustomerAttribute.class);

	  public static final class Builder
	  {
	    public static void buildCustAttribute(Consumer domainCustomer, Attributes xmlAttribsType)
	    {
	      MarshallCustomerAttribute.logger.debug("Marshalling customer attributes");

	      if ((domainCustomer.getCustomerAttributes() != null) && (!domainCustomer.getCustomerAttributes().isEmpty())) {
	        CustomerAttributeType destCustAttribType = xmlAttribsType.addNewAttribute();
	        CustomerAttributeEntityType destCustAttribEntityType = destCustAttribType.addNewEntity();

	        Set domainAttribSet = domainCustomer.getCustomerAttributes();
	        Iterator iterator = domainAttribSet.iterator();
	        int counter = 0;
	        String infoSrc = "";

	        while (iterator.hasNext()) {
	          CustomerAttribute domainAttrib = (CustomerAttribute)iterator.next();
	          if (counter == 0) {
	            infoSrc = domainAttrib.getSource();
	            destCustAttribEntityType.setSource(domainAttrib.getSource());
	            CustomerAttributeDetailType destAttrDtlType = destCustAttribEntityType.addNewAttribute();
	            destAttrDtlType.setName(domainAttrib.getName());
	            destAttrDtlType.setValue(domainAttrib.getValue());
	            destAttrDtlType.setDescription(domainAttrib.getDescription());
	          } else if (infoSrc.equalsIgnoreCase(domainAttrib.getSource())) {
	            infoSrc = domainAttrib.getSource();
	            destCustAttribEntityType.setSource(domainAttrib.getSource());
	            CustomerAttributeDetailType destAttrDtlType = destCustAttribEntityType.addNewAttribute();
	            destAttrDtlType.setName(domainAttrib.getName());
	            destAttrDtlType.setValue(domainAttrib.getValue());
	            destAttrDtlType.setDescription(domainAttrib.getDescription());
	          } else {
	            destCustAttribEntityType = findExistingAttribute(domainAttrib.getSource(), destCustAttribType);
	            if (destCustAttribEntityType != null) {
	              infoSrc = domainAttrib.getSource();
	              destCustAttribEntityType.setSource(domainAttrib.getSource());
	              CustomerAttributeDetailType destAttrDtlType = destCustAttribEntityType.addNewAttribute();
	              destAttrDtlType.setName(domainAttrib.getName());
	              destAttrDtlType.setValue(domainAttrib.getValue());
	              destAttrDtlType.setDescription(domainAttrib.getDescription());
	            } else {
	              destCustAttribEntityType = destCustAttribType.addNewEntity();
	              infoSrc = domainAttrib.getSource();
	              destCustAttribEntityType.setSource(domainAttrib.getSource());
	              CustomerAttributeDetailType destAttrDtlType = destCustAttribEntityType.addNewAttribute();
	              destAttrDtlType.setName(domainAttrib.getName());
	              destAttrDtlType.setValue(domainAttrib.getValue());
	              destAttrDtlType.setDescription(domainAttrib.getDescription());
	            }
	          }
	          counter++;
	        }
	      }
	    }

	    private static CustomerAttributeEntityType findExistingAttribute(String source, CustomerAttributeType destCustAttribType)
	    {
	      if (destCustAttribType != null) {
	        List<CustomerAttributeEntityType> entityList = destCustAttribType.getEntityList();
	        for (CustomerAttributeEntityType entityType : entityList) {
	          if (entityType.getSource().equalsIgnoreCase(source)) {
	            return entityType;
	          }
	        }
	      }
	      return null;
	    }
	  }
}
