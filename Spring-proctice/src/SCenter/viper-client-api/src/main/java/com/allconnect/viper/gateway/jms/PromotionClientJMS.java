/**
 * 
 */
package com.A.V.gateway.jms;


import java.util.Map;

import org.apache.log4j.Logger;
import com.A.xml.pr.v4.ProductResponseType;

/**
 * @author spolepalli
 * 
 */
public class PromotionClientJMS extends BaseClientJMS<ProductResponseType> {

	private static final Logger logger = Logger
			.getLogger(PromotionClientJMS.class);
	private static final int TIMEOUT = 60000;
	private static final String PRODUCT_NAMESPACE = "jms";
	private static final String END_POINT_NAME = "endpoint.promotion.in";


	public String send( String promotionId, Map<String,String> headers) {

		String responseFromJMS = send(PRODUCT_NAMESPACE,
				END_POINT_NAME,TIMEOUT, promotionId, headers);

		return responseFromJMS;

	}

}
