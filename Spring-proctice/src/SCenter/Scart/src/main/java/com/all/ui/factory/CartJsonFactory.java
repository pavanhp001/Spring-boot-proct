package com.AL.ui.factory;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 
 *
 */
public enum CartJsonFactory {
	
	INSTANCE;
	
	/**
	 * @param providerName
	 * @param partnerExternalId
	 * @param productUniqueId
	 * @param productExernalId
	 * @param productName
	 * @param price
	 * @param orderId
	 * @return Map<String,Object>
	 */
	public Map<String,Object> getMap(String providerName,String partnerExternalId,String productUniqueId,
			String productExernalId,String productName, String price, Long orderId)
			{
		Map<String, Object> obj=new HashMap<String, Object>();
		obj.put("providerName", providerName);
		obj.put("partnerExternalId", partnerExternalId);
		obj.put("productUniqueId", productUniqueId);
		obj.put("productExernalId", productExernalId);
		obj.put("price", price);

		JSONObject prodJson = new JSONObject();
		try 
		{
			prodJson.put("orderId", orderId);
			prodJson.put("partnerExternalId", escapeSpecialCharacters(partnerExternalId));
			prodJson.put("productUniqueId", escapeSpecialCharacters(productUniqueId));
			prodJson.put("productExernalId",escapeSpecialCharacters( productExernalId));
			prodJson.put("isPromotion", false);
			prodJson.put("appliesTo", 0L);
			prodJson.put("providerName", escapeSpecialCharacters(providerName));
			prodJson.put("productName", escapeSpecialCharacters(productName));

			obj.put("prodJson", prodJson);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}

		return obj;
	}
	/**
	 * @param str
	 * @return String
	 */
	private String escapeSpecialCharacters(String str)
	{
		str = str.replaceAll("&", " &#38;");
		str = str.replaceAll("'", " &#39;");

		return str;
	}


}
