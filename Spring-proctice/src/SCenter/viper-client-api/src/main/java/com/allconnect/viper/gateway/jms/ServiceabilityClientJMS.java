package com.A.V.gateway.jms;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;

import com.A.xml.se.v4.ServiceabilityEnterpriseResponse;
import com.A.xml.se.v4.ServiceabilityRequest2;
import com.A.ui.template.ServiceabilityTemplateConstant;
import com.A.V.gateway.ServiceabilityClient;


public class ServiceabilityClientJMS extends BaseClientJMS<ServiceabilityRequest2>
		implements ServiceabilityClient<String> {

	private static final Logger logger = Logger
			.getLogger(DialogClientJMS.class);
	private static final int TIMEOUT = 120000;
	
	private static final String SE_NAMESPACE = "jms";
	private static final String END_POINT_NAME = "endpoint.serviceability.in";

	public ServiceabilityEnterpriseResponse send(String namespace,
			String endpointName, String requestAsString) {

		logger.debug("getting serviceability");
		logger.info("Request:");
		logger.info(requestAsString);
		ServiceabilityEnterpriseResponse seResponse = null;
		try{
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("GUID", null);
			String responseFromJMS = send(namespace, endpointName, TIMEOUT,
					requestAsString, headers);
			logger.info("Response:");
			logger.debug(responseFromJMS);
			if(responseFromJMS != null && !responseFromJMS.equals("")){
				String responseExtracted = extract(responseFromJMS);
				logger.debug(responseExtracted);
				seResponse = ServiceabilityTemplateConstant.
				INSTANCE.toObject(responseExtracted);
				logger.info(seResponse.getGUID());
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return seResponse;
	}

	public String extract(String orderRR) {

		int indexStart = orderRR.indexOf("<v4:serviceabilityEnterpriseResponse>")
				+ "<ac:serviceabilityEnterpriseResponse>".length();
		int indexEnd = orderRR.indexOf("</v4:serviceabilityEnterpriseResponse>",
				indexStart + 1);

		if ((indexStart != -1) && (indexEnd != -1)) {

			orderRR = "<v4:serviceabilityEnterpriseResponse xmlns:ac=\"http://xml.A.com/v4\">"
					+ orderRR.substring(indexStart, indexEnd)
					+ "</v4:serviceabilityEnterpriseResponse>";
		}

		return StringUtils.replace(orderRR, "v4:", "ac:");

	}
	
	public ServiceabilityEnterpriseResponse send(String serviceabilityRequestAsString) {

		logger.debug("getting serviceability response");

		ServiceabilityEnterpriseResponse serviceabilityResponse = send(SE_NAMESPACE,
				END_POINT_NAME, serviceabilityRequestAsString);

		return serviceabilityResponse;
	}

}
