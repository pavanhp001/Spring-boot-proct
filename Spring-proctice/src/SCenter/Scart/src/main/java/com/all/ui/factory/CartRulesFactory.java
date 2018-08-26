package com.AL.ui.factory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.AL.ui.constants.Constants;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author PreetamMyneni
 *
 */
public enum CartRulesFactory {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(CartRulesFactory.class);

	/**
	 * Checks whether the Product is in Cart or Not.
	 * 
	 * @param uniqueId
	 * @param order
	 * @return returns boolean, returns true if Product there in Cart or else false
	 */
	public boolean hasSameProductInCart(String uniqueId, OrderType order)
	{


		List<LineItemType> lineItems = order.getLineItems().getLineItem();
		if(lineItems != null)
		{
			for(LineItemType lineItem : lineItems)
			{
				if(CartLineItemUtil.isProduct(lineItem))
				{
					/*	
					 * uniqueId of the LineItem in Order.			 
					 */
					String linItemUniqueId =lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId()+"_"+ 
					lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId();
					/*	
					 * If uniqueId of the Product which is being added match with the linItemUniqueId 
					 * then the Cart Rule are not satisfied.			 
					 */
					if(linItemUniqueId.equals(uniqueId))
					{
						/*	
						 * Product already in Cart.			 
						 */
						return true;
					}
				}
			}
		}
		/*	
		 * Product not in Cart.			 
		 */
		return false;

	}
	
	/**
	 * Validates the Cart Rules, Whether the Product is already in Cart or Not.
	 * 
	 * @param selectedArray
	 * @param orderId
	 * @param session
	 * @return returns JSONArray, if the product is new returns ProductJson else ErrorMsgJson
	 * @throws JSONException
	 */
	public JSONArray cartRules(JSONArray selectedArray, String orderId, HttpSession session) throws JSONException {
		
        long startTimer = 0;
		StopWatch timer = new StopWatch();
		timer.start();    
		/*	
		 * Getting the AgentId				 
		 */
		String agentId = CartUtil.INSTANCE.getAgentId(session);
		/*	
		 * Getting SalesContext from the session				 
		 */
		SalesContextType updateSalesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
		/*	
		 * Getting the Order				 
		 */
		startTimer = timer.getTime();
		OrderType order = CartUtil.INSTANCE.getOrder(orderId,agentId,updateSalesContext);
		logger.info("TimeTakenforOrderServicecall="+(timer.getTime()-startTimer));
		timer.stop();
		
		JSONArray success = new JSONArray();

		for(int i=0;i < selectedArray.length(); i++)
		{

			String jsonString=(String)selectedArray.get(i);

			JSONObject feedback =new JSONObject(jsonString);
			JSONObject error =new JSONObject();

			boolean isPromotion = feedback.getBoolean(Constants.IS_Promotion);
			com.AL.xml.v4.CustAddress cust = CartLineItemUtil.getAddress(order.getCustomerInformation().getCustomer(),
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());

			feedback.put(Constants.ADDRESS_ID, String.valueOf(cust.getAddress().getExternalId()));
			feedback.put(Constants.BILLINGINFO_ID, String.valueOf(session.getAttribute("billingInfoExternalId")));
			// Checking whether LineItem is Promotion or Not
			if(isPromotion)
			{
				feedback.put(Constants.APPLIES_TO, 0);
				feedback.put(Constants.APPLIES_TO_INTERNAL, true);
				success.put(feedback);
			}
			else
			{

				String uniqueId = feedback.getString(Constants.PRODUCT_ID)+"_"+feedback.getString(Constants.PARTNER_ID);
				/*	
				 * Checking whether the product is in Cart.			 
				 */
				if(CartRulesFactory.INSTANCE.hasSameProductInCart(uniqueId, order))
				{
					error.put("error"+i, Constants.ERROR_MSG);
					error.put("isAppliesTo", getLineItemNumberIfProductInCart(uniqueId, order));
					success.put(error);
				}
				else
				{
					success.put(feedback);
				}
			}
		}
		return success;
	}

	/**
	 * Validates the Cart Rules, Whether the Product is already in Cart or Not.
	 * 
	 * @param orderId
	 * @param lineItemId
	 * @param session
	 * @return returns String, if the product is new returns null else ERROR_MSG
	 */
	public String cartRulesReAdd(String orderId,String lineItemId, HttpSession session)
	{
		long startTimer = 0;
		StopWatch timer = new StopWatch();
		timer.start();
		/*	
		 * Getting the AgentId				 
		 */
		String agentId = CartUtil.INSTANCE.getAgentId(session); 
		/*	
		 * Getting SalesContext from the session				 
		 */
		SalesContextType updateSalesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
		/*	
		 * Getting the Order				 
		 */
		startTimer = timer.getTime();
		OrderType order = CartUtil.INSTANCE.getOrder(orderId,agentId,updateSalesContext);
		logger.info("TimeTakenforOrderServicecall="+(timer.getTime()-startTimer));
		timer.stop();
		
		if(order.getLineItems() != null && order.getLineItems().getLineItem() != null)
		{
			for(LineItemType lineItem : order.getLineItems().getLineItem())
			{
				if(lineItemId.equals(String.valueOf(lineItem.getExternalId())))
				{
					String uniqueId =lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId()+"_"+ 
					lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId();
					/*	
					 * Checking whether the product is in Cart.			 
					 */
					if(hasSameProductInCart(uniqueId,order))
					{
						
						return Constants.ERROR_MSG;
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param orderId
	 * @param lineItemId
	 * @param session 
	 * @return String
	 */
	@Deprecated
	public String cartRulesReAddValidator(String orderId,String lineItemId, HttpSession session)
	{
		logger.info("Validating_Cart_Rules");
		boolean hasTriplePlay = false;
		boolean hasDoublePlayInternetPhone = false;
		boolean hasDoublePlayTvPhone = false;
		boolean hasDoublePlayTvInternet = false;
		boolean hasInternet = false;
		boolean hasTv = false;
		boolean hasPhone = false;
		boolean hasHomeSecurity = false;
		boolean hasElectricity = false;
		boolean hasGas = false;
		boolean hasWater = false;
		boolean hasWaste = false;
		List<String> realTimeList = new ArrayList<String>();
		String agentId = CartUtil.INSTANCE.getAgentId(session); 
		SalesContextType updateSalesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);

		OrderType order = CartUtil.INSTANCE.getOrder(orderId,agentId,updateSalesContext);

		String category = null;
		String productTypeCategory = null;
		String partnerExternalId = null;
		String providerId = null;
		int addedLineItems = order.getLineItems().getLineItem().size();
		if(addedLineItems != 0)
		{
			for(LineItemType lineItem : order.getLineItems().getLineItem())
			{
				if(lineItem.getLineItemDetail().getDetailType().equals("product"))
				{
					for(AttributeEntityType entity : lineItem.getLineItemAttributes().getEntity())
					{
						if(entity!=null && entity.getSource()!=null &&
								entity.getSource().equalsIgnoreCase(Constants.PRODUCT_CATEGORY))
						{
								for(AttributeDetailType attribute : entity.getAttribute())
								{
									if(attribute.getName().equals(Constants.PRODUCT_TYPE))
									{
										category = attribute.getValue();
									}
								}
						}
					}
					providerId = lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId();
					if(String.valueOf(lineItem.getExternalId()).equals(lineItemId))
					{
						productTypeCategory = category;
						partnerExternalId = providerId;
					}
					if(category != null && !lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.CANCELLED_REMOVED))
					{
						updateRealTimeList(providerId, realTimeList);

						if(category.equals(Constants.TRIPLE_PLAY))
						{
							hasTriplePlay = true;
						}
						else if(category.equals(Constants.DOUBLE_PLAY_INTERNET_PHONE))
						{
							hasDoublePlayInternetPhone = true;
						}
						else if(category.equals(Constants.DOUBLE_PLAY_VIDEO_PHONE))
						{
							hasDoublePlayTvPhone = true;
						}
						else if(category.equals(Constants.DOUBLE_PLAY_VIDEO_INTERNET))
						{
							hasDoublePlayTvInternet = true;
						}
						else if(category.equals(Constants.INTERNET))
						{
							hasInternet = true;
						}
						else if(category.equals(Constants.PHONE))
						{
							hasPhone = true;
						}
						else if(category.equals(Constants.VIDEO))
						{
							hasTv = true;
						}
						else if(category.equals(Constants.HOMESECURITY))
						{
							hasHomeSecurity = true;
						}
						else if(category.equals(Constants.ELECTRICITY))
						{
							hasElectricity = true;
						}
						else if(category.equals(Constants.WATER))
						{
							hasWater = true;
						}
						else if(category.equals(Constants.NATURALGAS))
						{
							hasGas = true;
						}
						else if(category.equals(Constants.WASTEREMOVAL))
						{
							hasWaste = true;
						}
						else
						{
							logger.debug("others");
						}
					}
				}
			}
		}

		String error = null;

		if(productTypeCategory != null){
			boolean realCheck = realTimeCheck(partnerExternalId, realTimeList);
			if(productTypeCategory.equals(Constants.TRIPLE_PLAY))
			{
				if(hasTriplePlay || hasDoublePlayInternetPhone || hasDoublePlayTvPhone || hasDoublePlayTvInternet || 
						hasInternet || hasTv || hasPhone || realCheck)
				{
					error = Constants.ERROR_MSG;
				}
			}
			else if(productTypeCategory.equals(Constants.DOUBLE_PLAY_INTERNET_PHONE))
			{
				if(hasTriplePlay || hasDoublePlayInternetPhone || hasDoublePlayTvPhone ||
						hasDoublePlayTvInternet || hasInternet || hasPhone || realCheck)
				{
					error = Constants.ERROR_MSG;
				}
			}
			else if(productTypeCategory.equals(Constants.DOUBLE_PLAY_VIDEO_PHONE))
			{
				if(hasTriplePlay  || hasDoublePlayInternetPhone || hasDoublePlayTvPhone ||
						hasDoublePlayTvInternet || hasTv || hasPhone || realCheck)
				{
					error = Constants.ERROR_MSG;
				}
			}
			else if(productTypeCategory.equals(Constants.DOUBLE_PLAY_VIDEO_INTERNET))
			{
				if(hasTriplePlay ||  hasDoublePlayInternetPhone || hasDoublePlayTvPhone ||
						hasDoublePlayTvInternet ||	hasInternet || hasTv || realCheck)
				{
					error = Constants.ERROR_MSG;
				}
			}
			else if(productTypeCategory.equals(Constants.INTERNET))
			{
				if(hasTriplePlay || hasDoublePlayInternetPhone  || hasDoublePlayTvInternet
						|| hasInternet || realCheck)
				{
					error = Constants.ERROR_MSG;
				}
			}
			else if(productTypeCategory.equals(Constants.PHONE)){
				if(hasTriplePlay || hasDoublePlayInternetPhone || hasDoublePlayTvPhone || hasPhone || realCheck)
				{
					error = Constants.ERROR_MSG;
				}
			}else if(productTypeCategory.equals(Constants.VIDEO))
			{
				if(hasTriplePlay || hasDoublePlayTvPhone || hasDoublePlayTvInternet || hasTv || realCheck)
				{
					error = Constants.ERROR_MSG;
				}
			}
			else if(productTypeCategory.equals(Constants.HOMESECURITY )|| realCheck)
			{
				if(hasHomeSecurity)
				{
					error = Constants.ERROR_MSG;
				}
			}
			else if(productTypeCategory.equals(Constants.ELECTRICITY)|| realCheck)
			{
				if(hasElectricity)
				{
					error = Constants.ERROR_MSG;
				}
			}
			else if(productTypeCategory.equals(Constants.WATER)|| realCheck)
			{
				if(hasWater)
				{
					error = Constants.ERROR_MSG;
				}
			}
			else if(productTypeCategory.equals(Constants.NATURALGAS)|| realCheck)
			{
				if(hasGas)
				{
					error = Constants.ERROR_MSG;
				}
			}
			else if(productTypeCategory.equals(Constants.WASTEREMOVAL)|| realCheck)
			{
				if(hasWaste)
				{
					error = Constants.ERROR_MSG;
				}
			}
			else
			{
				if(realCheck)
				{
					error = Constants.ERROR_MSG;
				}
			}
		}
		return error;
	}

	/**
	 * @param selectedArray
	 * @param orderId
	 * @param session 
	 * @return JSONArray
	 * @throws JSONException
	 */
	@Deprecated
	public JSONArray cartRulesValidator(JSONArray selectedArray, String orderId, HttpSession session) throws JSONException {

		logger.debug("orderId="+ orderId);

		boolean hasTriplePlay = false;
		boolean hasDoublePlayInternetPhone = false;
		boolean hasDoublePlayTvPhone = false;
		boolean hasDoublePlayTvInternet = false;
		boolean hasInternet = false;
		boolean hasTv = false;
		boolean hasPhone = false;
		boolean hasHomeSecurity = false;
		boolean hasElectricity = false;
		boolean hasGas = false;
		boolean hasWater = false;
		boolean hasWaste = false;
		List<String> realTimeList = new ArrayList<String>();

		String agentId = CartUtil.INSTANCE.getAgentId(session); 
		SalesContextType updateSalesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);

		OrderType order = CartUtil.INSTANCE.getOrder(orderId,agentId,updateSalesContext);

		String category = null;
		int addedLineItems = order.getLineItems().getLineItem().size();
		if(addedLineItems != 0)
		{
			for(LineItemType lineItem : order.getLineItems().getLineItem())
			{
				if(lineItem.getLineItemDetail().getDetailType().equals("product"))
				{
					for(AttributeEntityType entity : lineItem.getLineItemAttributes().getEntity())
					{
						if(entity!=null && entity.getSource()!=null && 
								entity.getSource().equalsIgnoreCase(Constants.PRODUCT_CATEGORY))
						{
								for(AttributeDetailType attribute : entity.getAttribute())
								{
									if(attribute.getName().equals(Constants.PRODUCT_TYPE))
									{
										category = attribute.getValue();
									}
								}
						}
					}
					String providerId = lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId();
					logger.debug("category="+category);
					if(category != null && 
							!lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.CANCELLED_REMOVED))
					{
						updateRealTimeList(providerId, realTimeList);

						if(category.equals(Constants.TRIPLE_PLAY))
						{
							hasTriplePlay = true;
						}
						else if(category.equals(Constants.DOUBLE_PLAY_INTERNET_PHONE))
						{
							hasDoublePlayInternetPhone = true;
						}
						else if(category.equals(Constants.DOUBLE_PLAY_VIDEO_PHONE))
						{
							hasDoublePlayTvPhone = true;
						}
						else if(category.equals(Constants.DOUBLE_PLAY_VIDEO_INTERNET))
						{
							hasDoublePlayTvInternet = true;
						}
						else if(category.equals(Constants.INTERNET))
						{
							hasInternet = true;
						}
						else if(category.equals(Constants.PHONE))
						{
							hasPhone = true;
						}
						else if(category.equals(Constants.VIDEO))
						{
							hasTv = true;
						}
						else if(category.equals(Constants.HOMESECURITY))
						{
							hasHomeSecurity = true;
						}
						else if(category.equals(Constants.ELECTRICITY))
						{
							hasElectricity = true;
						}
						else if(category.equals(Constants.WATER))
						{
							hasWater = true;
						}
						else if(category.equals(Constants.NATURALGAS))
						{
							hasGas = true;
						}
						else if(category.equals(Constants.WASTEREMOVAL))
						{
							hasWaste = true;
						}
						else 
						{
							logger.debug("others");
						}
					}
				}
			}
		}
		JSONArray success = new JSONArray();
		JSONObject error =new JSONObject();
		for(int i=0;i < selectedArray.length(); i++)
		{
			String jsonString=(String)selectedArray.get(i);
			JSONObject feedback =new JSONObject(jsonString);
			boolean isPromotion = feedback.getBoolean("isPromotion");
			com.AL.xml.v4.CustAddress cust = CartLineItemUtil.getAddress(order.getCustomerInformation().getCustomer(),
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());

			feedback.put("svcAddressExtId", String.valueOf(cust.getAddress().getExternalId()));
			feedback.put("billingInfoExtId", String.valueOf(order.getCustomerInformation().getCustomer()
					.getBillingInfoList().getBillingInfo().get(0).getExternalId()));
			// Checking whether LineItem is Promotion or Not
			if(isPromotion)
			{
				JSONObject previousJSON = (JSONObject) success.get(i-1);
				if(previousJSON.has("error"+(i-1)))
				{
					error.put("error"+i, Constants.ERROR_MSG);
					success.put(error);
				}
				else
				{
					feedback.put("appliesTo", 0);
					feedback.put("isAppliesToInternal", true);
					success.put(feedback);
				}
			}
			else
			{
				JSONObject capabilityMap =  feedback.getJSONObject("capabilityMap");
				String productTypeCategory = feedback.getString("productType");
				String partnerExternalId = feedback.getString("partnerExternalId"); 

				if(productTypeCategory != null)
				{
					boolean status = false;
					boolean realCheck = realTimeCheck(partnerExternalId, realTimeList);
					if(productTypeCategory.equals(Constants.TRIPLE_PLAY))
					{
						if(!realCheck && !hasTriplePlay && !hasDoublePlayInternetPhone && !hasDoublePlayTvPhone && !hasDoublePlayTvInternet && 
								!hasInternet && !hasTv && !hasPhone)
						{
							status = true;
						}
						hasTriplePlay = true;
					}
					else if(productTypeCategory.equals(Constants.DOUBLE_PLAY))
					{
						String doublePlay= getDoublePlay(capabilityMap);
						if(doublePlay.equals(Constants.DOUBLE_PLAY_INTERNET_PHONE))
						{
							if(!realCheck && !hasTriplePlay && !hasDoublePlayInternetPhone && !hasDoublePlayTvPhone && !hasDoublePlayTvInternet && !hasInternet && !hasPhone )
							{
								status = true;
							}
							hasDoublePlayInternetPhone = true;
						}else if(doublePlay.equals(Constants.DOUBLE_PLAY_VIDEO_PHONE))
						{
							if(!realCheck && !hasTriplePlay  && !hasDoublePlayInternetPhone && !hasDoublePlayTvPhone && !hasDoublePlayTvInternet && !hasTv && !hasPhone)
							{
								status = true;
							}
							hasDoublePlayTvPhone = true;
						}else if(doublePlay.equals(Constants.DOUBLE_PLAY_VIDEO_INTERNET))
						{
							if(!realCheck && !hasTriplePlay &&  !hasDoublePlayInternetPhone && !hasDoublePlayTvPhone && !hasDoublePlayTvInternet &&	!hasInternet && !hasTv )
							{
								status = true;
							}
							hasDoublePlayTvInternet = true;
						}

					}else if(productTypeCategory.equals(Constants.INTERNET))
					{
						if(!realCheck && !hasTriplePlay && !hasDoublePlayInternetPhone  && !hasDoublePlayTvInternet && !hasInternet )
						{
							status = true;
						}
						hasInternet = true;
					}else if(productTypeCategory.equals(Constants.PHONE))
					{
						if(!realCheck && !hasTriplePlay && !hasDoublePlayInternetPhone && !hasDoublePlayTvPhone && !hasPhone)
						{
							status = true;
						}
						hasPhone = true;
					}else if(productTypeCategory.equals(Constants.VIDEO))
					{
						if(!realCheck && !hasTriplePlay&& !hasDoublePlayTvPhone && !hasDoublePlayTvInternet && !hasTv )
						{
							status = true;
						}
						hasTv = true;
					}
					else if(productTypeCategory.equals(Constants.HOMESECURITY))
					{
						if(!realCheck && !hasHomeSecurity)
						{
							status = true;
						}
						hasHomeSecurity = true;
					}
					else if(productTypeCategory.equals(Constants.ELECTRICITY))
					{
						if(!realCheck && !hasElectricity)
						{
							status = true;
						}
						hasElectricity = true;
					}
					else if(productTypeCategory.equals(Constants.WATER))
					{
						if(!realCheck && !hasWater)
						{
							status = true;
						}
						hasWater = true;
					}
					else if(productTypeCategory.equals(Constants.NATURALGAS))
					{
						if(!realCheck && !hasGas)
						{
							status = true;
						}
						hasGas = true;
					}
					else if(productTypeCategory.equals(Constants.WASTEREMOVAL))
					{
						if(!realCheck && !hasWaste)
						{
							status = true;
						}
						hasWaste = true;
					} 
					else 
					{
						if(!realCheck)
						{
							status = true;
						}
					}

					if(status)
					{
						success.put(feedback);
					}
					else 
					{
						error.put("error"+i, Constants.ERROR_MSG);
						success.put(error);
					}
				}
			}
		}
		return success;
	}

	/**
	 * @param providerId
	 * @param realTimeList
	 */
	private void updateRealTimeList(String providerId, List<String> realTimeList) 
	{
		if(providerId.equals("15500201") || providerId.equals("24699452") )
		{
			realTimeList.add("hasAtt");
		}
		else if(providerId.equals("4353598"))
		{
			realTimeList.add("hasVerizon");
		}
		else if(providerId.equals("26069940"))
		{
			realTimeList.add("hasG2b");
		}
		else if(providerId.equals("27010360"))
		{
			realTimeList.add("hasDish");
		}
		else if(providerId.equals("2314635"))
		{
			realTimeList.add("hasDirectStar");
		}
		else if(providerId.equals("19824709"))
		{
			realTimeList.add("hasAdvancedSupport");
		}
		else if(providerId.equals("15498481"))
		{
			realTimeList.add("hasAdt");
		}
		else if(providerId.equals("15499381"))
		{
			realTimeList.add("hasTxu");
		}
		
	}

	/**
	 * @param partnerExternalId
	 * @param realTimeList
	 * @return
	 */
	private boolean realTimeCheck(String partnerExternalId, List<String> realTimeList)
	{
		boolean status = false;
		if((partnerExternalId.equals("15500201") || partnerExternalId.equals("24699452")) && realTimeList.contains("hasAtt"))
		{
			status = true;
		} 
		else if(partnerExternalId.equals("4353598") && realTimeList.contains("hasVerizon"))
		{
			status = true;
		} 
		else if(partnerExternalId.equals("26069940") && realTimeList.contains("hasG2b"))
		{
			status = true;
		} 
		else if(partnerExternalId.equals("27010360") && realTimeList.contains("hasDish"))
		{
			status = true;
		} 
		else if(partnerExternalId.equals("2314635") && realTimeList.contains("hasDirectStar"))
		{
			status = true;
		}
		else if(partnerExternalId.equals("19824709") && realTimeList.contains("hasAdvancedSupport"))
		{
			status = true;
		}else if(partnerExternalId.equals("15498481") && realTimeList.contains("hasAdt"))
		{
			status = true;
		}
		else if(partnerExternalId.equals("15499381") && realTimeList.contains("hasTxu"))
		{
			status = true;
		}

		//If we add more that one product at a time
		updateRealTimeList(partnerExternalId, realTimeList);
		return status;
	}

	/**
	 * @param capabilityMap
	 * @return String
	 */
	public String getDoublePlay(JSONObject capabilityMap){
		String doublePlay = "";

		if((capabilityMap.has("fiberDataDownSpeed") || capabilityMap.has("ipDslamDataDownSpeed") ||  
				capabilityMap.has("wiredDataDownSpeed")||  capabilityMap.has("dialUpInternet"))
				&&(capabilityMap.has("voip") || capabilityMap.has("ipDslamVoip") ||
						capabilityMap.has("localPhone") ||capabilityMap.has("longDistancePhone")||capabilityMap.has("wirelessPhone")))
		{
			doublePlay = Constants.DOUBLE_PLAY_INTERNET_PHONE;
		}
		else if((capabilityMap.has("voip") || capabilityMap.has("ipDslamVoip") ||
				capabilityMap.has("localPhone") ||capabilityMap.has("longDistancePhone")||capabilityMap.has("wirelessPhone"))
				&&(capabilityMap.has("iptv") || capabilityMap.has("ipDslamIptv") ||capabilityMap.has("analogCable") ||
						capabilityMap.has("digitalCable") ||capabilityMap.has("satellite")))
		{
			doublePlay = Constants.DOUBLE_PLAY_VIDEO_PHONE;

		}
		else if((capabilityMap.has("fiberDataDownSpeed") || capabilityMap.has("ipDslamDataDownSpeed") ||
				capabilityMap.has("wiredDataDownSpeed")||  capabilityMap.has("dialUpInternet"))
				&& (capabilityMap.has("iptv") || capabilityMap.has("ipDslamIptv") ||
						capabilityMap.has("analogCable") ||capabilityMap.has("digitalCable") ||capabilityMap.has("satellite")))
		{
			doublePlay = Constants.DOUBLE_PLAY_VIDEO_INTERNET;
		}
		
		return doublePlay;
	}
	
	
	
	
	/**
	 * Getting the line item number if Product is in Cart.
	 * 
	 * @param uniqueId
	 * @param order
	 * @return returns Integer
	 */
	public Integer getLineItemNumberIfProductInCart(String uniqueId, OrderType order)
	{
		List<LineItemType> lineItems = order.getLineItems().getLineItem();
		if(lineItems != null)
		{
			for(LineItemType lineItem : lineItems)
			{
				if(CartLineItemUtil.isProduct(lineItem))
				{
					/*	
					 * uniqueId of the LineItem in Order.			 
					 */
					String linItemUniqueId =lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId()+"_"+ 
					lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId();
					/*	
					 * If uniqueId of the Product which is being added match with the linItemUniqueId 
					 * then the Cart Rule are not satisfied.			 
					 */
					if(linItemUniqueId.equals(uniqueId))
					{
						return lineItem.getLineItemNumber();
					}
				}
			}
		}
		/*	
		 * Product not in Cart.			 
		 */
		return null;

	}

}
