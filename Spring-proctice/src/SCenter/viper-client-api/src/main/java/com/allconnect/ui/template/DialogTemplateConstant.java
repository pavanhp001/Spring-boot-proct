package com.A.ui.template;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.A.V.domain.SalesContext;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.di.v4.NameValuePairType;
import com.A.xml.di.v4.SalesContextEntityType;

 
 
public enum DialogTemplateConstant {
	
	INSTANCE;
	
	private static final JaxbUtil<SalesContext> util = new JaxbUtil<SalesContext>();
	 
	
	public final String SOAP_ENVELOPE_TEMPLATE_START = 
		"<soapenv:envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:header/><soapenv:body>";
	
	public final String SOAP_ENVELOPE_TEMPLATE_END =  "</soapenv:body></soapenv:envelope>";
	
	public final String DIALOG_REQUEST_TEMPLATE = 
	 
	"<ac:dialogueEnterpriseRequest debug=\"false\"> " +
    "<ac:GUID>#!GUID!#</ac:GUID> " +
    "<ac:transactionType xsi:type=\"ac:dialogueTransactionTypeType\"> " +
    "<ac:dialogueTransactionType> " +
    "getDialogues " +
    "</ac:dialogueTransactionType> " +
    "</ac:transactionType> " +
    "#!SALES!# " +
    "</ac:dialogueEnterpriseRequest> ";


 
	
	
	public final String DIALOG_JMS_HEADER_START =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
		"<ac:acMessage xmlns:ac=\"http://xml.A.com/v4\">"+
	    "<ac:msgType>request</ac:msgType>"+
	    "<ac:actionType>query</ac:actionType>"+
	    "<ac:payloadType>DialogueEnterpriseRequestDocument</ac:payloadType>"+
	    "<ac:payload>";
	        
		public final String DIALOG_JMS_HEADER_END =
	    "</ac:payload>"+
		"</ac:acMessage>";
		
		
		
	
 
	
	public final String DIALOG_JMS  = DIALOG_JMS_HEADER_START+ DIALOG_REQUEST_TEMPLATE + DIALOG_JMS_HEADER_END;
	public final String DIALOG_SOAP  = SOAP_ENVELOPE_TEMPLATE_START+ DIALOG_REQUEST_TEMPLATE + SOAP_ENVELOPE_TEMPLATE_END;
	
	
	public String getDialogRequest(SalesContext salesContext) {
		
		String salesContextAsString = util.toString(salesContext,SalesContext.class, Boolean.FALSE);
		String guid = UUID.randomUUID().toString();
		if(salesContext.getEntity() != null){
			for(SalesContextEntityType entityType : salesContext.getEntity()){
				boolean isGuidFind = false;
				if(entityType != null){
					for(NameValuePairType nameValuePairType : entityType.getAttribute()){
						if(nameValuePairType.getName().equals("GUID")){
							guid = nameValuePairType.getValue();
							isGuidFind = true;
							break;
						}
					}
				}
				if(isGuidFind){
					break;
				}
			}
		}
		String dialogTemplate = StringUtils.replace(
				DialogTemplateConstant.INSTANCE.DIALOG_JMS, "#!GUID!#",guid);
		return StringUtils.replace(dialogTemplate, "#!SALES!#",
				salesContextAsString);
		
		
		
		
	}

	
public String getSoapDialogRequest(SalesContext salesContext) {
		
		String salesContextAsString = util.toString(salesContext,SalesContext.class, Boolean.FALSE);
		String guid = UUID.randomUUID().toString();
		if(salesContext.getEntity() != null){
			for(SalesContextEntityType entityType : salesContext.getEntity()){
				boolean isGuidFind = false;
				if(entityType != null){
					for(NameValuePairType nameValuePairType : entityType.getAttribute()){
						if(nameValuePairType.getName().equals("GUID")){
							guid = nameValuePairType.getValue();
							isGuidFind = true;
							break;
						}
					}
				}
				if(isGuidFind){
					break;
				}
			}
		}
		System.out.println("GUID IN DialogTemplateConstant 117:::::::::::::::"+guid);
		String dialogTemplate = StringUtils.replace(
				DialogTemplateConstant.INSTANCE.DIALOG_SOAP, "#!GUID!#",guid);
		return StringUtils.replace(dialogTemplate, "#!SALES!#",
				salesContextAsString);
		
		
		
		
	}
	   



	public final String TEMPLATE = SOAP_ENVELOPE_TEMPLATE_START+DIALOG_REQUEST_TEMPLATE+SOAP_ENVELOPE_TEMPLATE_END;

}
