package com.A.V.gateway.jms;

import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;

import com.A.V.gateway.DialogClient;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.di.v4.DialogueResponseType;
import com.A.xml.di.v4.EnterpriseResponseDocumentType;

public class DialogClientJMS extends BaseClientJMS<DialogueResponseType>
		implements DialogClient<String> {

	private static final Logger logger = Logger
			.getLogger(DialogClientJMS.class);
	private static final int TIMEOUT = 60000;
	private static final String UAM_NAMESPACE = "dialog";
	private static final String DIALOG_NAMESPACE = "jms";
	private static final String END_POINT_NAME = "endpoint.dialogue.in";

	private static final JaxbUtil<EnterpriseResponseDocumentType> util = new JaxbUtil<EnterpriseResponseDocumentType>();

	public EnterpriseResponseDocumentType send(String namespace,
			String endpointName, String requestAsString,Map<String,String> headers) {

		logger.debug("getting Dialog");
		
		logger.info(requestAsString);
		
		String responseFromJMS = send(namespace, endpointName, TIMEOUT,
				requestAsString,headers);
		String responseExtracted = extract(responseFromJMS);
		
		logger.debug(responseExtracted);

		JAXBElement<EnterpriseResponseDocumentType> dialog = util.toObject(
				responseExtracted, "com.A.xml.di.v4");

		return dialog.getValue();

	}
	public static String extractXmlValue(String source, String from, String to) {



		if (source.indexOf(from) != -1 && source.indexOf(to) != -1) {
			int startPos = source.indexOf(from) + from.length();
			int endPos = source.indexOf(to);
			return source.substring(startPos, endPos);
		} else
			return null;

	}
	public String extract(String orderRR) {

		int indexStart = orderRR.indexOf("<v4:dialogueEnterpriseResponse>")
				+ "<ac:dialogueEnterpriseResponse>".length();
		int indexEnd = orderRR.indexOf("</v4:dialogueEnterpriseResponse>",
				indexStart + 1);

		if ((indexStart != -1) && (indexEnd != -1)) {

			orderRR = "<v4:dialogueEnterpriseResponse xmlns:ac=\"http://xml.A.com/v4\">"
					+ orderRR.substring(indexStart, indexEnd)
					+ "</v4:dialogueEnterpriseResponse>";
		}

		return StringUtils.replace(orderRR, "v4:", "ac:");

	}

	public EnterpriseResponseDocumentType send(String uamRequestAsString,Map<String,String> headers) {

		logger.debug("getting dialog");

		EnterpriseResponseDocumentType dialogueResponse = send(UAM_NAMESPACE,
				END_POINT_NAME, uamRequestAsString,headers);

		return dialogueResponse;
	}
	
	public EnterpriseResponseDocumentType send(String uamRequestAsString,Map<String,String> headers,String namespace) {

		logger.debug("getting dialog");

		if(namespace == null){
			namespace = DIALOG_NAMESPACE;
		}
		EnterpriseResponseDocumentType dialogueResponse = send(namespace,
				END_POINT_NAME, uamRequestAsString,headers);

		return dialogueResponse;
	}	

}