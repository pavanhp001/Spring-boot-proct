package com.A.V.gateway.jms;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;

import com.A.V.gateway.MerchandisingClient;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.me.v4.EnterpriseResponseDocumentType;
import com.A.xml.me.v4.MerchandisingResponseType;

public class MerchandisingClientJMS extends BaseClientJMS<MerchandisingResponseType>
		implements MerchandisingClient<String> {

	private static final Logger logger = Logger
			.getLogger(MerchandisingClientJMS.class);
	private static final int TIMEOUT = 60000;
	private static final String MERCHANDISING_NAMESPACE = "jms";
	private static final String END_POINT_NAME = "endpoint.merchandising.in";

	private static final JaxbUtil<EnterpriseResponseDocumentType> util = new JaxbUtil<EnterpriseResponseDocumentType>();

	public EnterpriseResponseDocumentType send(String namespace,
			String endpointName, String requestAsString) {

		logger.debug("getting Merchandising jms response");
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("GUID", null);
		String responseFromJMS = send(namespace, endpointName, TIMEOUT,
				requestAsString, headers);
		String responseExtracted = extract(responseFromJMS);
		
		logger.info(responseExtracted);

		JAXBElement<EnterpriseResponseDocumentType> product = util.toObject(
				responseExtracted, "com.A.xml.me.v4");

		return product.getValue();

	}

	public String extract(String merchRR) {

		int indexStart = merchRR.indexOf("<v4:merchandisingEnterpriseResponse>")
				+ "<ac:merchandisingEnterpriseResponse>".length();
		int indexEnd = merchRR.indexOf("</v4:merchandisingEnterpriseResponse>",
				indexStart + 1);

		if ((indexStart != -1) && (indexEnd != -1)) {

			merchRR = "<v4:merchandisingEnterpriseResponse xmlns:ac=\"http://xml.A.com/v4\">"
					+ merchRR.substring(indexStart, indexEnd)
					+ "</v4:merchandisingEnterpriseResponse>";
		}

		return StringUtils.replace(merchRR, "v4:", "ac:");

	}

	public EnterpriseResponseDocumentType send(String merchRequestAsString) {

		logger.debug("getting merchandising response");

		EnterpriseResponseDocumentType merchResponse = send(MERCHANDISING_NAMESPACE,
				END_POINT_NAME, merchRequestAsString);

		return merchResponse;
	}

}