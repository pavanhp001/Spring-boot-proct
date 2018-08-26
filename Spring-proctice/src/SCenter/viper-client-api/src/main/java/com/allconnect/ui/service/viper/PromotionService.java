/**
 * 
 */
package com.A.ui.service.V;

import java.util.HashMap;
import java.util.Map;

import com.A.V.gateway.jms.PromotionClientJMS;

/**
 * @author spolepalli
 * 
 */
public enum PromotionService {

	INSTANCE;

	public String getPromotionInfo(String promotionId) {

		PromotionClientJMS promotionClientJMS = new PromotionClientJMS();
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("GUID", null);
		String promotionResponse = promotionClientJMS.send(promotionId, headers);

		return promotionResponse;
	}

}
