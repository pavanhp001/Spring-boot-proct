package com.A.V.gateway.jms;

import java.util.Map;

import javax.xml.bind.JAXBElement;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;
import com.A.V.gateway.ProductClient;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.pr.v4.EnterpriseResponseDocumentType;
import com.A.xml.pr.v4.ProductResponseType;

public class ProductClientJMS extends BaseClientJMS<ProductResponseType>
		implements ProductClient<String> {

	private static final Logger logger = Logger.getLogger(ProductClientJMS.class);
	private static final int TIMEOUT = 60000;
	private static final String PRODUCT_NAMESPACE = "jms";
	private static final String END_POINT_NAME = "endpoint.product.in";
	private int i = 0;

	private static final JaxbUtil<EnterpriseResponseDocumentType> util = new JaxbUtil<EnterpriseResponseDocumentType>();

	public EnterpriseResponseDocumentType send(String namespace,
			String endpointName, String requestAsString, Map<String,String> headers) {
		
		if(i ==0){
			logger.info("getting Product " +requestAsString);
			i++;
		}
		
		
		String responseFromJMS = send(namespace, endpointName, TIMEOUT,
				requestAsString, headers);
		String responseExtracted = extract(responseFromJMS);
		
		//logger.info(responseExtracted);

		JAXBElement<EnterpriseResponseDocumentType> product = util.toObject(
				responseExtracted, "com.A.xml.pr.v4");

		return product.getValue();

	}

	public String extract(String orderRR) {

		int indexStart = orderRR.indexOf("<v4:productEnterpriseResponse>")
				+ "<ac:productEnterpriseResponse>".length();
		int indexEnd = orderRR.indexOf("</v4:productEnterpriseResponse>",
				indexStart + 1);

		if ((indexStart != -1) && (indexEnd != -1)) {

			orderRR = "<v4:productEnterpriseResponse xmlns:ac=\"http://xml.A.com/v4\">"
					+ orderRR.substring(indexStart, indexEnd)
					+ "</v4:productEnterpriseResponse>";
		}

		return StringUtils.replace(orderRR, "v4:", "ac:");

	}

	public EnterpriseResponseDocumentType send(String productRequestAsString, Map<String,String> headers) {

		logger.debug("getting product");

		EnterpriseResponseDocumentType productResponse = send(PRODUCT_NAMESPACE,
				END_POINT_NAME, productRequestAsString, headers);

		return productResponse;
	}

}