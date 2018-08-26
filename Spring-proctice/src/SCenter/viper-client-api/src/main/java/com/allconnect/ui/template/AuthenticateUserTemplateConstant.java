package com.A.ui.template;

import java.util.List;

import org.apache.commons.lang.StringUtils;

//TODO:read from database or properties file
public enum AuthenticateUserTemplateConstant {
	
	INSTANCE;
	
	 
	
	public final String SOAP_ENVELOPE_TEMPLATE_START = 
		"<soapenv:envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:header/><soapenv:body>";
	
	public final String SOAP_ENVELOPE_TEMPLATE_END =  "</soapenv:body></soapenv:envelope>";
	
	public final String UAM_REQUEST_TEMPLATE = 
	 
	"      <v4:uamEnterpriseRequest debug=\"false\" "+
	"    	  xsi:schemaLocation=\"http://xml.A.com/v4 userAccessManagement.xsd \"  "+
	"    	  xmlns:v4=\"http://xml.A.com/v4\"  "+
	"    	  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "+
	"         <v4:GUID>#!GUID!#</v4:GUID> "+
	"         <v4:transactionType xsi:type=\"v4:uamTransactionTypeType\"> "+
	"            <v4:uamTransactionType>authenticateUser</v4:uamTransactionType> "+
	"         </v4:transactionType> "+
	"         <v4:request xsi:type=\"v4:AuthenticateUserRequestType\"> "+
	"    		<v4:authenticateUserRequest> "+
	"    			<v4:userName>#!USERNAME!#</v4:userName> "+
	"    			<v4:credentials> "+
	"      				<v4:credentialName>#!CREDENTIAL!#</v4:credentialName> "+
	"    			</v4:credentials> "+
	"               <v4:superVisorCredentials>"+
	"                   <v4:credentialName/>"+
    "               </v4:superVisorCredentials>"+
	"  		</v4:authenticateUserRequest> "+
	"         </v4:request> "+
	"      </v4:uamEnterpriseRequest> ";
	
	public final String UAM_JMS_HEADER_START =
	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
	"<ac:acMessage xmlns:ac=\"http://xml.A.com/v4\">"+
    "<ac:msgType>request</ac:msgType>"+
    "<ac:actionType>query</ac:actionType>"+
    "<ac:payloadType>UamRequestDocument</ac:payloadType>"+
    "<ac:payload>";
        
	public final String UAM_JMS_HEADER_END =
    "</ac:payload>"+
	"</ac:acMessage>";
	
	public final String UAM_REQUEST_USER_LOGIN_TEMPLATE = 
			 
			"      <v4:uamEnterpriseRequest debug=\"false\" "+
			"    	  xsi:schemaLocation=\"http://xml.A.com/v4 userAccessManagement.xsd \"  "+
			"    	  xmlns:v4=\"http://xml.A.com/v4\"  "+
			"    	  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "+
			"         <v4:GUID>#!GUID!#</v4:GUID> "+
			"         <v4:transactionType xsi:type=\"v4:uamTransactionTypeType\"> "+
			"            <v4:uamTransactionType>validateUser</v4:uamTransactionType> "+
			"         </v4:transactionType> "+
			"         <v4:request xsi:type=\"v4:ValidateUserRequestType\"> "+
			"    		<v4:validateUserRequest> ";
	

	
	public final String UAM_REQUEST_USER_LOGIN_TEMPLATE_END =
			"		</v4:validateUserRequest> "+
			"         </v4:request> "+
			"      </v4:uamEnterpriseRequest> ";
	
	public final String UAM_REQUEST_USER_LOGIN_TEMPLATE_MIDDLE = " <v4:userLogin>#!USERLogin#</v4:userLogin>";
	
	public final String UAM_JMS  = UAM_JMS_HEADER_START+ UAM_REQUEST_TEMPLATE + UAM_JMS_HEADER_END;

	public final String TEMPLATE = SOAP_ENVELOPE_TEMPLATE_START+UAM_REQUEST_TEMPLATE+SOAP_ENVELOPE_TEMPLATE_END;
	
	public final String getUAMJMSWithUserLoginList(List<String> userLogins, String correlationId){
		String template = UAM_JMS_HEADER_START + UAM_REQUEST_USER_LOGIN_TEMPLATE;
		
		for(String userLogin : userLogins){
			template = StringUtils.replace(template + UAM_REQUEST_USER_LOGIN_TEMPLATE_MIDDLE, "#!USERLogin#", userLogin);
		}
		
		template = template + UAM_REQUEST_USER_LOGIN_TEMPLATE_END + UAM_JMS_HEADER_END;
		
		return template;
	}

}
