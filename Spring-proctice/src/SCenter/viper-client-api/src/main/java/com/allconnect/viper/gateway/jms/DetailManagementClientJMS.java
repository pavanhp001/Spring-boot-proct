package com.A.V.gateway.jms;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;

import com.A.V.gateway.DetailManagementClient;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.dtl.v4.DetailsRequestResponse;
/**
 * @author spatil
 *
 */
public class DetailManagementClientJMS extends BaseClientJMS<DetailsRequestResponse>
		implements DetailManagementClient<String> {

	private static final Logger logger = Logger
			.getLogger(DialogClientJMS.class);
	private static final int TIMEOUT = 60000;
	
	private static final String DTL_NAMESPACE = "jms";
	private static final String END_POINT_NAME = "endpoint.detail.in";
	
	private static final JaxbUtil<DetailsRequestResponse> util = new JaxbUtil<DetailsRequestResponse>();	

	
	public DetailsRequestResponse send(String namespace,
			String endpointName, String requestAsString) {

		logger.debug("getting Detail jms response");
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("GUID", null);
		String responseFromJMS = send(namespace, endpointName, TIMEOUT,
				requestAsString, headers);
		String responseExtracted = extract(responseFromJMS);
		
		DetailsRequestResponse detailsResponse = util.toObject(
				responseExtracted, DetailsRequestResponse.class);

		return detailsResponse;

	}
	

	public String extract(String detailRR) {

		int indexStart = detailRR.indexOf("<v4:detailsRequestResponse>")
				+ "<ac:detailsRequestResponse>".length();
		int indexEnd = detailRR.indexOf("</v4:detailsRequestResponse>",
				indexStart + 1);

		if ((indexStart != -1) && (indexEnd != -1)) {

			detailRR = "<v4:detailsRequestResponse xmlns:ac=\"http://xml.A.com/v4\">"
					+ detailRR.substring(indexStart, indexEnd)
					+ "</v4:detailsRequestResponse>";
		}

		return StringUtils.replace(detailRR, "v4:", "ac:");

	}
	
	public DetailsRequestResponse send(String detailRequestAsString) {

		logger.debug("getting detailManagement response");

		DetailsRequestResponse detailsRequestResponse = send(DTL_NAMESPACE,
				END_POINT_NAME, detailRequestAsString);

		return detailsRequestResponse;
	}

}
