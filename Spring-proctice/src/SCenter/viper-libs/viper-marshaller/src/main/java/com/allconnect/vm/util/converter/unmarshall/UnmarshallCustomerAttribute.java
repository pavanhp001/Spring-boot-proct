package com.A.vm.util.converter.unmarshall;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerAttribute;
import com.A.xml.v4.CustomerAttributeDetailType;
import com.A.xml.v4.CustomerAttributeEntityType;
import com.A.xml.v4.CustomerAttributeType;
import com.A.xml.v4.CustomerType;

public class UnmarshallCustomerAttribute {

	private static final Logger logger = Logger
			.getLogger(UnmarshallCustomerAttribute.class);

	public static final class Builder {


		private Builder() {
			super();
		}

		public static void copy(CustomerType xmlCustomerSrc,
				Consumer domainCustomerdest, boolean isUpdateRequest) {

			logger.debug("Unmarshalling customer attributes");

			if(xmlCustomerSrc.getAttributes() != null && xmlCustomerSrc.getAttributes().getAttributeList() != null){
				List<CustomerAttributeType> custAttribList = xmlCustomerSrc.getAttributes().getAttributeList();
				if (domainCustomerdest.getCustomerAttributes() == null) {
					domainCustomerdest
							.setCustomerAttributes(new HashSet<CustomerAttribute>());
				}
				//copyAttributes(domainCustomerdest, custAttribList);

				Set<CustomerAttribute> destCustAttribSet = new HashSet<CustomerAttribute>();
				for (CustomerAttributeType attrib : custAttribList) {
					if(attrib.getEntityList() != null){
						List<CustomerAttributeEntityType> attribEntityList = attrib.getEntityList();
						for(CustomerAttributeEntityType attrbEntityType : attribEntityList ){
							String source = attrbEntityType.getSource();
							List<CustomerAttributeDetailType> detailList = attrbEntityType.getAttributeList();
							for(CustomerAttributeDetailType attrbDetailType : detailList){
								CustomerAttribute domainAttrib = new CustomerAttribute();
								domainAttrib.setSource(source);
								domainAttrib.setName(attrbDetailType.getName());
								domainAttrib.setValue(attrbDetailType.getValue());
								domainAttrib.setDescription(attrbDetailType.getDescription());
								//domainCustomerdest.getCustomerAttributes().add(domainAttrib);
								destCustAttribSet.add(domainAttrib);
							}
						}

					}

				}

				if(isUpdateRequest){
					logger.debug("Updating customer attributes");
					updateExistingAttributes(domainCustomerdest,destCustAttribSet);
				}else{
					domainCustomerdest.setCustomerAttributes(destCustAttribSet);
				}
			}

		}


		/**
		 * Method to update existing customer attributes
		 * @param domainCustomerdest
		 * @param destCustAttribSet
		 */
		private static void updateExistingAttributes(
				Consumer domainCustomerdest,
				Set<CustomerAttribute> destCustAttribSet) {

			Set<CustomerAttribute> finalAttrbList = new HashSet<CustomerAttribute>();

			Set<CustomerAttribute> existingCustAttrSet = domainCustomerdest.getCustomerAttributes();

			if(existingCustAttrSet != null && !existingCustAttrSet.isEmpty()){
				for(CustomerAttribute extCustAttrib : existingCustAttrSet){
					Iterator<CustomerAttribute> newAttribIterator = destCustAttribSet.iterator();
					while(newAttribIterator.hasNext()){
						CustomerAttribute newAttrib = newAttribIterator.next();
						String extName = extCustAttrib.getName();
						String extSource = extCustAttrib.getSource();

						if (extName != null && extSource != null) {
							if (extName.equalsIgnoreCase(newAttrib.getName()
									.trim())
									&& extSource.equalsIgnoreCase(newAttrib
											.getSource().trim())) {
								extCustAttrib.setName(newAttrib.getName());
								extCustAttrib.setValue(newAttrib.getValue());
								extCustAttrib.setDescription(newAttrib
										.getDescription() != null ? newAttrib.getDescription() : extCustAttrib.getDescription() );
								newAttribIterator.remove();
							}
						}else{
							logger.warn("Update customer request contains either null attribute name or source.");
						}
					}
				}
			}

			finalAttrbList.addAll(existingCustAttrSet);
			finalAttrbList.addAll(destCustAttribSet);
			logger.debug("Final Customer Attribute set : " + finalAttrbList);
			domainCustomerdest.setCustomerAttributes(finalAttrbList);

		}




//		private static void copyAttributes(Consumer domainCustomerdest,
//				List<CustomerAttributeType> attribList) {
//			for (CustomerAttributeType attrib : attribList) {
//				if(attrib.getEntityList() != null){
//					List<CustomerAttributeEntityType> attribEntityList = attrib.getEntityList();
//					for(CustomerAttributeEntityType attrbEntityType : attribEntityList ){
//						String source = attrbEntityType.getSource();
//						List<CustomerAttributeDetailType> detailList = attrbEntityType.getAttributeList();
//						for(CustomerAttributeDetailType attrbDetailType : detailList){
//							CustomerAttribute domainAttrib = new CustomerAttribute();
//							domainAttrib.setSource(source);
//							domainAttrib.setName(attrbDetailType.getName());
//							domainAttrib.setValue(attrbDetailType.getValue());
//							domainAttrib.setDescription(attrbDetailType.getDescription());
//							domainCustomerdest.getCustomerAttributes().add(domainAttrib);
//						}
//					}
//
//				}
//
//			}
//		}
	}
}
