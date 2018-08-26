package com.AL.ui.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.util.Utils;
import com.AL.ui.constants.Constants;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.service.V.ProductService;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.domain.SalesContext;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;
import com.AL.productResults.vo.ProductSummaryVO;

/**
 * @author Preetam
 *
 */
public class CartLineItemUtil extends LineItemUtil{



	private static final Logger logger = Logger.getLogger(CartLineItemUtil.class);

	/**
	 * Generates the BaseMontlyPrice(Excluding features) as Map object with key as its respective LineItem ExteranalId 
	 * 
	 * @param order
	 * @return Map<Long,String>
	 */
	public static Map<Long,String> extractBaseMontlyPrice(OrderType order){
		List<LineItemType> lineItems = order.getLineItems().getLineItem();
		Map<Long,String> baseMonthlyPriceMap = new HashMap<Long, String>();
		if(lineItems != null){
			for(LineItemType lineItem : lineItems){
				if(isProduct(lineItem)){
					String providerId = lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId();

					if(isStaticProduct(lineItem) && !Constants.DIRECTSTAR.equals(providerId) && !Constants.ADT_INTEGRATION.equals(providerId))
					{
						Double selectedFeturesPrice = PriceUtil.INSTANCE.getSelectedFeaturesPrice(lineItem);
						Double baseRecurringPrice = lineItem.getLineItemPriceInfo().getBaseRecurringPrice();
						baseRecurringPrice = baseRecurringPrice - selectedFeturesPrice;
						baseMonthlyPriceMap.put(lineItem.getExternalId(), baseRecurringPrice.toString());
						logger.debug("baseRecurringPrice="+baseRecurringPrice);
					}
					else
					{
						if(Constants.VERIZON.equals(providerId) || Constants.CENTURY_LINK.equals(providerId))
						{
							Double selectedFeturesPrice = PriceUtil.INSTANCE.getSelectedFeaturesPrice(lineItem);
							Double baseRecurringPrice = lineItem.getLineItemPriceInfo().getBaseRecurringPrice();
							baseRecurringPrice = baseRecurringPrice - selectedFeturesPrice;
							baseMonthlyPriceMap.put(lineItem.getExternalId(), baseRecurringPrice.toString());
							logger.debug("baseRecurringPrice="+baseRecurringPrice);
						}
						else
						{
							String baseMonthlyFee = CartLineItemFactory.INSTANCE.getLineItemAttr(lineItem,Constants.CKO,Constants.BASEMONTHLYFEE);
							baseMonthlyPriceMap.put(lineItem.getExternalId(), baseMonthlyFee);
							logger.debug("baseRecurringPrice_Real-Time="+baseMonthlyFee);
						}
					}
				}
			}
		}

		return baseMonthlyPriceMap;	
	}





	/**
	 * @param customer
	 * @param key
	 * @return CustAddress
	 */
	public static com.AL.xml.v4.CustAddress getAddress(Customer customer, final String key) {
		boolean isRole = Boolean.FALSE;
		if ((customer != null) && (customer.getAddressList() != null)) {
			List<com.AL.xml.v4.CustAddress> custAddressList = customer.getAddressList().getCustomerAddress();
			if (custAddressList != null) {
				for (com.AL.xml.v4.CustAddress custAddress : custAddressList) {
					if ((custAddress != null)
							&& (custAddress.getAddressRoles() != null)) {
						List<com.AL.xml.v4.RoleType> roleTypeList = custAddress.getAddressRoles().getRole();
						for (com.AL.xml.v4.RoleType roleType : roleTypeList) {
							if (key.equals(roleType.name())) {
								isRole = Boolean.TRUE;
								break;
							}
						}
						if (isRole) {
							return custAddress;
						}
					}
				}
			}
		}
		return new com.AL.xml.v4.CustAddress();
	}

	/**
	 * Cuts the value to LineItemAttribut Max Length
	 * @param value
	 * @return String
	 */
	public static String lineItemAttributeMaxLength(String value){
		return ((value != null) && (value.length() > 500)) ? value = value.substring(0,400) : value;
	}

	/**
	 * @param value
	 * @param request
	 * @return String
	 */
	public static String getRequestParmeterValue(String value, HttpServletRequest request){

		String result = request.getParameter(value);

		if(Utils.isBlank(result)){
			result = (String) request.getAttribute(value);
		}

		if(Utils.isBlank(result)){
			result = (String) request.getSession().getAttribute(value);
		}

		return Utils.isBlank(result) ? null : result;
	}

	/**
	 * @param providerName
	 * @return String
	 */
	public static String validateProviderName(String providerName){

		if(providerName.equalsIgnoreCase("ATTSTI")){
			providerName = Constants.ATT;
		}else if(providerName.equalsIgnoreCase("G2B-Comcast")){
			providerName = Constants.Comcast;
		}else if(providerName.equalsIgnoreCase("Dish Network")){
			providerName = Constants.Dish;
		}

		return providerName;
	}
	public static Map<Long,Boolean> hasFeatures(OrderType order){
		Map<Long,Boolean> featuresmap = new HashMap<Long,Boolean>();
		for(LineItemType lineitem : order.getLineItems().getLineItem()){
			featuresmap.put(lineitem.getExternalId(), Boolean.FALSE);
			if(lineitem.getSelectedFeatures() != null){
				List<FeatureValueType> features = lineitem.getSelectedFeatures().getFeatures().getFeatureValue();
				if(features != null )
				{
					for(FeatureValueType featureValueType : features){
						if(featureValueType.getIncluded() == null){
							featuresmap.put(lineitem.getExternalId(), Boolean.TRUE);
							break;
						}
					}
				}
				List<FeatureGroup> featureGroups = lineitem.getSelectedFeatures().getFeatureGroup();
				if(featureGroups != null){
					for(FeatureGroup featureGroup:featureGroups){
						features = featureGroup.getFeatureValue();
						if(features != null )
						{
							for(FeatureValueType feature:features){
								if(feature.getIncluded() == null){
									featuresmap.put(lineitem.getExternalId(), Boolean.TRUE);
									break;
								}
							}
						}
					}
				}	
			}
			if(lineitem.getCustomSelections()!=null){
				com.AL.xml.v4.LineItemSelectionsType lineItemSelection =  lineitem.getCustomSelections().getSelections();
				if(lineItemSelection!=null){
					if(lineItemSelection.getSelection()!=null && !lineItemSelection.getSelection().isEmpty()){
						featuresmap.put(lineitem.getExternalId(), Boolean.TRUE);
					}
				}
			}
		}
		return featuresmap;
	}

	public static Map<String, Map<String, String>> createSalesContext(String dwellingType, HttpServletRequest request) {
		Map<String, Map<String, String>> salesContextData = new HashMap<String, Map<String, String>>();
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		Map<String, String> context = new HashMap<String, String>();
		context.put("context.mode", "production");
		salesContextData.put("context", context);	

		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("orderSource.source", "123");
		orderSource.put("orderSource.channel", "1");
		orderSource.put("orderSource.referrer",salesCenterVo.getValueByName("DT Partner"));

		salesContextData.put("orderSource", orderSource);

		Map<String, String> consumer = new HashMap<String, String>();
		consumer.put("consumer.creditScore", Constants.CONSUMER_CREDITSCORE);
		salesContextData.put("consumer", consumer);

		if (dwellingType != null && dwellingType.equalsIgnoreCase("Apartment")) {
			dwellingType = "apartment";
		}else{
			dwellingType = "house";
		}
		Map<String, String> dwelling = new HashMap<String, String>();
		dwelling.put("dwelling.dwellingType", dwellingType);
		dwelling.put("dwelling.stateOrProvince", salesCenterVo.getValueByName("state"));
		salesContextData.put("dwelling", dwelling);

		Map<String, String> salesFlow = new HashMap<String, String>();
		salesFlow.put("salesFlow.dialogueType", "core");
		salesFlow.put("salesFlow.forceNonConfirm", "false");
		salesContextData.put("salesFlow", salesFlow);

		Map<String, String> agent = new HashMap<String, String>();
		agent.put("agent.capability", "advanced");
		salesContextData.put("agent", agent);

		return salesContextData;
	}




	/**Use this method to display in OrderSummary page feature description for COX RTS
	 * @param order
	 * @param request
	 */
	public static void coxOrderSummaryFeatureDisplayNames(OrderType order,HttpServletRequest request) {
		List<LineItemType>  providerLineItemList = LineItemUtil.getLineItemsBasedOnProvider(order,Constants.COX_RTS_PROVIDER_ID);
		Map<String, Map<String, String>> productFeatureNameMap = (Map<String, Map<String,String>>)request.getSession().getAttribute("productFeatureNameMap");
		for(LineItemType lineItemType :providerLineItemList){
			if(lineItemType.getLineItemDetail() != null 
					&& lineItemType.getLineItemDetail().getDetail() != null
					&& lineItemType.getLineItemDetail().getDetail().getProductLineItem() != null
					&& !Utils.isBlank(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getExternalId())
					&& !Constants.CKO_READY.equalsIgnoreCase(LineItemUtil.getLineItemAttr(lineItemType,Constants.CKO,Constants.STATUS))){
				if(productFeatureNameMap != null && productFeatureNameMap.get(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getExternalId()) !=  null){
					SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(CartLineItemUtil.createSalesContext("House",request));
					String GUID = (String)request.getSession().getAttribute("GUID");
					String productExID = lineItemType.getLineItemDetail().getDetail().getProductLineItem().getExternalId();
					ProductInfoType product = null;
					Map<String,ProductInfoType> productDetailsMap = (Map<String,ProductInfoType>)request.getSession().getAttribute("coxProductDetailsData");
					if(productDetailsMap == null){
						productDetailsMap = new HashMap<String,ProductInfoType>();
						request.getSession().setAttribute("coxProductDetailsData", productDetailsMap);
					}
					if(productDetailsMap.get(productExID) == null){
						product = ProductService.INSTANCE.getProduct(LineItemUtil.getLineItemAttr(lineItemType,"IMAGE_ID", "id"),productExID,GUID,salesContext);
						productDetailsMap.put(productExID, product);
						request.getSession().setAttribute("coxProductDetailsData",productDetailsMap);
					}else{
						product = productDetailsMap.get(productExID);
					}
					Map<String,String> fetureMap = productFeatureNameMap.get(productExID);
					if(product != null 
							&& product.getProductDetails() != null 
							&& product.getProductDetails().getFeature() != null
							&& !product.getProductDetails().getFeature().isEmpty()){
						List<FeatureType> features =  product.getProductDetails().getFeature();
						for (FeatureType feature : features){
							if (feature != null 
									&& feature.getExternalId() != null 
									&& feature.getExternalId().startsWith("RTS:COX")){
								if (!Utils.isBlank(feature.getDescription())) {
									fetureMap.put(feature.getExternalId(),feature.getDescription());	
								}else{
									fetureMap.put(feature.getExternalId(),feature.getType());
								}
							}else{
								fetureMap.put(feature.getExternalId(),feature.getType());
							}
						}
					}
					if(product != null 
							&& product.getProductDetails() != null 
							&& product.getProductDetails().getFeatureGroup() != null
							&& !product.getProductDetails().getFeatureGroup().isEmpty()){
						for (FeatureGroupType feature :product.getProductDetails().getFeatureGroup()){
							if (feature != null && feature.getFeature() != null){
								for (FeatureType featuregrp : feature.getFeature()){
									if (featuregrp != null 
											&& featuregrp.getExternalId() != null 
											&& featuregrp.getExternalId().startsWith("RTS:COX")){
										if (!Utils.isBlank(featuregrp.getDescription())){
											fetureMap.put(featuregrp.getExternalId(),featuregrp.getDescription());	
										}else{
											fetureMap.put(featuregrp.getExternalId(),featuregrp.getType());
										}
									}else{
										fetureMap.put(featuregrp.getExternalId(),featuregrp.getType());
									}
								}	   
							}
						}
					}
					productFeatureNameMap.put(productExID, fetureMap);
					request.getSession().setAttribute("productFeatureNameMap",productFeatureNameMap);
				}
			}
		}
	}
	public static void getTotalPoints(LineItemType lineItemType, HttpSession session, String opt) {
		
		float productPoints = 0.00f;
		try {
			ProductResultsManager productResultManager = (ProductResultsManager)session.getAttribute("productResultManager");
			if(productResultManager != null 
				&& productResultManager.context.getAllProductList() != null 
				&& lineItemType != null 
				&& lineItemType.getLineItemDetail() != null 
				&& lineItemType.getLineItemDetail().getDetail() != null 
				&& lineItemType.getLineItemDetail().getDetail().getProductLineItem() != null 
				&& lineItemType.getLineItemDetail().getDetail().getProductLineItem().getExternalId() != null){
				for(ProductSummaryVO product : productResultManager.context.getAllProductList()){
					if(product.getExternalId().equalsIgnoreCase(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getExternalId())){
						if(product != null && !(product.getPoints() <= 0.0) && !Float.isNaN(productPoints)){
							productPoints = product.getPoints();
						}
						if(!Float.isNaN(productPoints)){
							if(opt.equals(Constants.ADD)){
								if(session.getAttribute("totalPoints") != null){
									productPoints += Float.parseFloat((String)session.getAttribute("totalPoints"));
								}
								session.setAttribute("totalPoints",Float.toString(productPoints));
							}
							else if(opt.equals(Constants.SUBTRACT)){
								Float totalPoints =0.00f;
								if(session.getAttribute("totalPoints") != null){
									totalPoints = Float.parseFloat((String)session.getAttribute("totalPoints"));
									totalPoints -= productPoints;
								}
								session.setAttribute("totalPoints",Float.toString(totalPoints));
							}
						}
						break;
					}
				}
			}
			
		} catch (NumberFormatException e) {
			logger.error("Error while getting product points"+e.getMessage());
		}
	}
	public static float getTotalPoints(OrderType order, HttpSession session) {
		float totalPoints = 0.00f;
		try {
			ProductResultsManager productResultManager = (ProductResultsManager)session.getAttribute("productResultManager");
			if(productResultManager != null && productResultManager.context.getAllProductList() != null && order.getLineItems()!= null){
				List<LineItemType> lineItems = order.getLineItems().getLineItem();
				if(lineItems != null){
					for(LineItemType lineItem : lineItems){
						if(isProduct(lineItem)){
							if(lineItem != null && lineItem.getLineItemDetail() != null && lineItem.getLineItemDetail().getDetail() != null 
									&& lineItem.getLineItemDetail().getDetail().getProductLineItem() != null && lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId() != null){
								for(ProductSummaryVO product : productResultManager.context.getAllProductList()){
									if(product.getExternalId().equalsIgnoreCase(lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId()) && !product.isSyntheticBundle()){
										if(product != null && !Float.isNaN(product.getPoints())  && product.getPoints() >= 0.0){
											totalPoints = totalPoints + product.getPoints();
										}
										break;
									}
								}
							}
						}
					}
				}
			}
		}  
		catch (NumberFormatException e) {
			logger.error("Error_while_getting_product_points"+e.getMessage());
		}
		logger.info("totalPoints="+totalPoints);
		session.setAttribute("totalPoints",Float.toString(totalPoints));
		return totalPoints;
	}
}
