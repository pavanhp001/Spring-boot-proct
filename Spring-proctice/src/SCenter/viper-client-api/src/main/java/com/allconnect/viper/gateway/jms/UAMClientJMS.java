package com.A.V.gateway.jms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.A.comm.manager.CommunicationManager;
import com.A.comm.manager.jms.util.JMSConfigManager;
import com.A.ui.template.AuthenticateUserTemplateConstant;
import com.A.V.gateway.UAMClient;
import com.A.V.gateway.util.JaxbUtil;
import com.A.V.service.auth.AuthenticationFactory;
import com.A.V.service.auth.ValidatedUsersFactory;
import com.A.vo.UserAuthorization;
import com.A.xml.uam.v4.EnterpriseRequestDocumentType;
import com.A.xml.uam.v4.EnterpriseResponseDocumentType;
import com.A.xml.uam.v4.ValidatedUsers;

public class UAMClientJMS extends BaseClientJMS<UserAuthorization> implements UAMClient {

	private static final Logger logger = Logger.getLogger(UAMClientJMS.class);
	private static final int TIMEOUT = 60000;
	private static final String UAM_NAMESPACE = "uam";
	private static final String UAM_NAMESPACE1 = "jms";
	private static final String END_POINT_NAME = "endpoint.uam.in";

	private final CommunicationManager<javax.jms.Message, MessageListener> commManager = JMSConfigManager.INSTANCE
	.createCommunicationManager(UAM_NAMESPACE1);
	
	public UserAuthorization send( String namespace, String endpointName,  String uamRequestAsString) {
		
		logger.debug("getting user authorization");
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("GUID", null);
		//String responseFromJMS = send(  namespace,   endpointName,   TIMEOUT,   uamRequestAsString, headers);
		
		
		String responseFromJMS = ownUamService();
		
		/*String filename = "src\\main\\resources\\xml\\uam_user_mnagineni.xml";

		String responseFromJMS = FileUtil.INSTANCE.getStringContent(filename);*/
		
		logger.debug("JMS Response "+responseFromJMS);
		
		UserAuthorization response = AuthenticationFactory.create(null,
				responseFromJMS);
		
		Set<String> context = response.getPermissions().getRepository().keySet();
		
		response.setResources(context);

		return response;
		
	}
	
	public String ownUamService() {
		String str =null;
		try{
			File file = new File("D:\\config\\UamService.xml");
	 		BufferedReader br = new BufferedReader(new FileReader(file));
	 		String line;
	 		StringBuilder sb = new StringBuilder();
	
	 		while((line=br.readLine())!= null){
	 		    sb.append(line.trim());
	 		}
	 		
	 		str = sb.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	
	
	public UserAuthorization send(String uamRequestAsString) {
		
		logger.debug("getting user authorization");
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("GUID", null);
		String responseFromJMS = send(UAM_NAMESPACE, END_POINT_NAME, TIMEOUT,
				uamRequestAsString, headers);
		UserAuthorization response = AuthenticationFactory.create(null,
				responseFromJMS);

		return response;
	}

	public String extract(String orderRR) {

		int indexStart = orderRR.indexOf("<ac:orderManagementRequestResponse>");
		int indexEnd = orderRR.indexOf("</ac:payload>");

		if ((indexStart != -1) && (indexEnd != -1)) {

			return orderRR.substring(indexStart, indexEnd);
		}

		return orderRR;

	}
	
	
	public   String getAuthenticateRequest(  final String user,
			final String password) {

		String GUID = UUID.randomUUID().toString();

		String template = AuthenticateUserTemplateConstant.INSTANCE.UAM_JMS;

		template = template.replaceFirst("#!USERNAME!#", user)
				.replaceFirst("#!CREDENTIAL!#", password)
				.replaceFirst("#!GUID!#", GUID);

		return template;

	}
	
	public List<ValidatedUsers> sendValidateUsers( String namespace, String endpointName,  String uamRequestAsString) {
		
		logger.debug("getting user authorization");
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("GUID", null);
		String responseFromJMS = send(  namespace,   endpointName,   TIMEOUT,   uamRequestAsString, headers);
		
		/*String filename = "src\\main\\resources\\xml\\uam_user_mnagineni.xml";

		String responseFromJMS = FileUtil.INSTANCE.getStringContent(filename);*/
		
		logger.info("JMS Response "+responseFromJMS);
		
		List<ValidatedUsers> response = ValidatedUsersFactory.create(null,
				responseFromJMS);
		
		/*Set<String> context = response.getPermissions().getRepository().keySet();*/
		
		/*response.setResources(context);*/

		return response;
	}


	@Override
	public EnterpriseResponseDocumentType send(
			EnterpriseRequestDocumentType uamRequest) {

		JaxbUtil<EnterpriseRequestDocumentType> utilRequest = new JaxbUtil<EnterpriseRequestDocumentType>();
		JaxbUtil<EnterpriseResponseDocumentType> utilResponse = new JaxbUtil<EnterpriseResponseDocumentType>();

		logger.debug("invoked UAMClientJMS: send(EnterpriseRequestDocumentType order)...");

		TextMessage responseText = null;

		String uamRequestAsString = utilRequest.toString(uamRequest, EnterpriseRequestDocumentType.class);

		EnterpriseResponseDocumentType response =  null;

		try {
			logger.debug("send(): uamRequestString....."+uamRequestAsString);
			responseText = (TextMessage) commManager.sendSync(END_POINT_NAME, uamRequestAsString, TIMEOUT);

			if (responseText == null) {
				return null;
			}
			
			response = utilResponse.toObject(extract(responseText.getText()), EnterpriseResponseDocumentType.class);
			logger.debug("UAMClientJMS: JMS Response: " + responseText.getText());

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return response;
	}
}
