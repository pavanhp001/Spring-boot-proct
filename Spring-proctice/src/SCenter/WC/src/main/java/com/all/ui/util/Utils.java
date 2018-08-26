/**
 * 
 */
package com.A.ui.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.A.managers.CustomerTrackerManager;
import com.A.productResults.managers.ProductResultsManager;
import com.A.productResults.managers.RESTClientForDrupal;
import com.A.productResults.vo.ProductSearchIface;
import com.A.productResults.vo.ProductSummaryVO;
import com.A.ui.constants.Constants;
import com.A.ui.dao.CustomerTrackerDao;
import com.A.ui.domain.CustomerTracker;
import com.A.ui.domain.CustomerTrackerConversion;
import com.A.ui.domain.CustomerTrackerDetails;
import com.A.ui.factory.CartLineItemFactory;
import com.A.ui.service.config.ConfigRepo;
import com.A.ui.vo.ProductVOJSON;
import com.A.xml.pr.v4.FeatureType;
import com.A.xml.pr.v4.ProductPromotionType;
import com.A.xml.v4.LineItemStatusCodesType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderType;
import com.A.xml.pr.v4.ProductInfoType;
import com.google.gson.Gson;

/**
 * @author Nanda Kishore Palapala
 *
 */
public class Utils {
	
	private static final Logger logger = Logger.getLogger(Utils.class);
	
	private static ExecutorService pool;
	
	public static boolean isAlphabetic(String target){
		return target.matches("[a-zA-Z]*");
	}

	public static boolean isNumeric(String target) {  
		return target.matches("[-+]?\\d*\\.?\\d+");
	}

	public static boolean isValidZipCode(String target) {  
		return target.matches("^\\d{5}\\p{Punct}?\\s?(?:\\d{4})?$");
	}

	public static boolean isValidPhoneNumber(String target) {  
		return target.matches("(\\d-)?(\\d{3}-)?\\d{3}-\\d{4}");
	}

	public static boolean isValidEmail(String target) {  
		return target.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}
	
	public static boolean isAlphaNumeric(String target){
	    return (target.matches("^[a-zA-Z0-9]*$"));  
	}
	/**
	 * Checks if string is <code>null</code> or empty.
	 *
	 * @param str String to check.
	 * @return if string is <code>null</code> or empty, <code>false</code> otherwise.
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	/*	public static List<FeatureType> sort(List<FeatureType> list){
		Collections.sort(list, new Comparator<FeatureType>() {
			public int compare(FeatureType o1, FeatureType o2) {
				int vo1 = getFeatureTypeOrder(o1);
				int vo2 = getFeatureTypeOrder(o2);
				if (vo1 == vo2) {

				} else {
					return vo1 - vo2;
				}
				String type1 = null;
				if (o1.getType() != null) {
					type1 = o1.getType();
				}
				String type2 = null;
				if (o2.getType() != null) {
					type2 = o2.getType();
				}
				if (type1 == null && type2 == null) {
					return 0;
				} else if (type1 == null) {
					return -1;
				} else if (type2 == null) {
					return 1;
				} else {
					return type1.compareTo(type2);
				}
			}
		});
		return list;
	}
	 */

	public static List<FeatureType> sort(List<FeatureType> list){
		Collections.sort(list, new Comparator<FeatureType>() {
			public int compare(FeatureType o1, FeatureType o2) {
				/*				int vo1 = getFeatureTypeOrder(o1);
				int vo2 = getFeatureTypeOrder(o2);
				if (vo1 == vo2) {

				} else {
					return vo1 - vo2;
				}*/
				String tag1 = null;
				if (o1.getTags() != null) {
					tag1 = o1.getTags();
				}
				String tag2 = null;
				if (o2.getTags() != null) {
					tag2 = o2.getTags();
				}
				if (tag1 == null && tag2 == null) {
					return 0;
				} else if (tag1 == null) {
					return -1;
				} else if (tag2 == null) {
					return 1;
				} else {
					return tag1.compareTo(tag2);
				}
			}
		});
		return list;
	}

	/**
	 *
	 * @param col
	 * @return FIXDOC
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Collection col) {
		return col == null || col.isEmpty() == true;
	}

	/**
	 *
	 * @param map
	 * @return FIXDOC
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Map map) {
		return map == null || map.isEmpty() == true;
	}

	public static boolean isNumberofChannelsPassed(long productValue,String parameterValue) {
		long startPoint = 0;
		long endPoint = 0;
		String parameterValues[] = parameterValue.split("-");
		if(parameterValues.length==2)
		{
			startPoint = Long.parseLong(parameterValues[0]);
			endPoint = Long.parseLong(parameterValues[1]);
		}
		
		if( startPoint <= productValue && productValue <= endPoint)
		{
			return true;
		}
		
		return false;
	}

	
	public static boolean isInternetSpeedPassed(double productValue,String parameterValue) {
		double startPoint = 0;
		double endPoint = 0;
		String parameterValues[] = parameterValue.split("-");
		if(parameterValues.length==2)
		{
			startPoint = Double.parseDouble(parameterValues[0]);
			endPoint = Double.parseDouble(parameterValues[1]);
		}
		
		if( startPoint <= productValue && productValue <= endPoint)
		{
			return true;
		}
		
		return false;
	}
	
	public static Map<String, Map<String, String>> createSalesContext(String dwellingType, HttpServletRequest request) {
		Map<String, Map<String, String>> salesContextData = new HashMap<String, Map<String, String>>();
		Map<String, String> context = new HashMap<String, String>();
		context.put("context.mode", "production");
		salesContextData.put("context", context);	
		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("orderSource.source", "123");
		orderSource.put("orderSource.channel", "1");
		orderSource.put("orderSource.referrer", "");

		salesContextData.put("orderSource", orderSource);

		Map<String, String> consumer = new HashMap<String, String>();
		consumer.put("consumer.creditScore", "650");
		salesContextData.put("consumer", consumer);

		if (dwellingType != null && dwellingType.equalsIgnoreCase(ProductSearchIface.APARTMENT)) {
			dwellingType = "apartment";
		} else if (dwellingType != null && dwellingType.equalsIgnoreCase(ProductSearchIface.CONDO)) {
			dwellingType = "condo/townhouse";
		} else if (dwellingType != null && dwellingType.equalsIgnoreCase(ProductSearchIface.DUPLEX)) {
			dwellingType = "duplex";
		} else if (dwellingType != null && dwellingType.equalsIgnoreCase(ProductSearchIface.MOBILE_HOME)) {
			dwellingType = "mobile home";
		}
		else{
			dwellingType = "house";
		}
		Map<String, String> dwelling = new HashMap<String, String>();
		dwelling.put("dwelling.dwellingType", dwellingType);
		dwelling.put("dwelling.stateOrProvince", "VA");
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
	
	public static long dateDiffInDays(Date date1, Date date2)
	{
		long diff =  date1.getTime() - date2.getTime();
		return  diff/ (24 * 60 * 60 * 1000);
	}
	
	public static float getTotalPoints(OrderType order, HttpSession session) {
		float totalPoints = 0.00f;
		try {
			ProductResultsManager productResultManager = (ProductResultsManager)session.getAttribute("productResultManager");
			if(productResultManager != null && productResultManager.context.getAllProductList() != null && order.getLineItems()!= null){
				List<LineItemType> lineItems = order.getLineItems().getLineItem();
				if(lineItems != null){
					logger.info("total_lineItems_size="+lineItems.size());
					for(LineItemType lineItem : lineItems){
						boolean isProductFound = false;
						if(isProduct(lineItem)){
							if(lineItem != null && lineItem.getLineItemDetail() != null && lineItem.getLineItemDetail().getDetail() != null 
									&& lineItem.getLineItemDetail().getDetail().getProductLineItem() != null && lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId() != null){
								for(ProductSummaryVO product : productResultManager.context.getAllProductList()){
									if(product.getExternalId().equalsIgnoreCase(lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId())){
										if(product != null && !Float.isNaN(product.getPoints())  && product.getPoints() >= 0.0){
											totalPoints = totalPoints + product.getPoints();
										}
										isProductFound = true;
										break;
									}
								}
								if(! isProductFound && (lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals(Constants.ATTV6))){
									float attV6Points = productResultManager.getATTV6NewLineItemProductPoints(lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId());
									if(!Float.isNaN(attV6Points)  && attV6Points >= 0.0){
										logger.info("attV6Points="+attV6Points);
										totalPoints = totalPoints + attV6Points;
									}
								}
							}
						}
					}
				}
			}
			logger.info("totalPoints="+totalPoints);
			session.setAttribute("totalPoints",Float.toString(totalPoints));
		}  
		catch (NumberFormatException e) {
			logger.error("Error_while_getting_product_points"+e.getMessage());
		}
		return totalPoints;
	}
	public static boolean isProduct(LineItemType lineItem)
	{

		if(isProductIncludingRemoved(lineItem) && 
				lineItem.getLineItemStatus().getStatusCode() != null &&
				!lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.CANCELLED_REMOVED))
		{
			return true;
		}
		return false;
	}
	public static boolean isProductIncludingRemoved(LineItemType lineItem)
	{

		if(lineItem.getLineItemDetail().getDetailType().equals("product") && (!isOffer(lineItem)))
		{
			return true;
		}
		return false;
	}
	public static boolean isOffer(LineItemType lineItem)
	{
		String productType = CartLineItemFactory.INSTANCE.getLineItemAttr(lineItem,Constants.PRODUCT_TYPE,"TYPE");

		if(lineItem.getLineItemDetail().getDetailType().equalsIgnoreCase("product") &&
				!Utils.isBlank(productType))
		{
			return true;
		}
		return false;
	}
	
	public static String toJson(Object obj){
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	public static String escapeSpecialCharacters(String str){
		if(str!=null){
			str = str.replaceAll("&amp;", "&");
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("&quot;", "&#34;");

			str = str.replaceAll("&#10;", "&nbsp;");
			str = str.replaceAll("\u00a0", "&nbsp;");
			//this is for - mark
			str = str.replaceAll("\u2013", "&#8211;");
			//this is for trademark
			str = str.replaceAll("\u2122", "&#8482;");

			//this is for Copyright mark
			str = str.replaceAll("\u00a9", "&#169;");
			//this is for Registered trade mark
			str = str.replaceAll("\u00ae", "&#174;");

			//this is for bullet point
			str = str.replaceAll("\u2022", "&#8226;");
			//this is for exclamation point
			str = str.replaceAll("\u0021", "&#33;");
			//this is for colon
			str = str.replaceAll("\u003a", "&#58;");
			//this is for inverted question mark
			str = str.replaceAll("\u00bf", "&#191;");

			//this is for right single quotation mark
			str = str.replaceAll("\u2019", "&#8217;");
			//this is for left single quotation mark
			str = str.replaceAll("\u2018", "&#8216;");
			//this is for left double quotation mark
			str = str.replaceAll("\u201C", "&#8220;");
			//this is for right double quotation mark
			str = str.replaceAll("\u201D", "&#8221;");
		}
		return str;
	}
	
	public static boolean isDisplayPricingGridNotEmpty(String displayPricingGrid) {
		
		JSONObject jsonObj = null;
		try {
			jsonObj = new  JSONObject(displayPricingGrid);
			if(jsonObj.length() != 0){
				return true;
			}
		} catch (JSONException e) {
			logger.warn("Error getting while DisplayPricingGrid"+e.getMessage());
		}
		return false;
	}
	
	public static boolean isProductVideoCapable(ProductSummaryVO product) {
		if((product.getCapabilityMap().get("iptv") != null ||
				product.getCapabilityMap().get("ipDslamIptv") != null ||
				product.getCapabilityMap().get("analogCable") != null ||
				product.getCapabilityMap().get("digitalCable") != null ||
				product.getCapabilityMap().get("satellite") != null)) {
			return true;
		}
		return false;
	}
	
	public static boolean isProductInternetCapable(ProductSummaryVO product) {
		if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
				product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
				product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
				product.getCapabilityMap().get("dialUpInternet") != null)) {
			return true;
		}
		return false;
	}

	public static boolean isProductPhoneCapable(ProductSummaryVO product) {
		if((product.getCapabilityMap().get("voip") != null || //phone conditions
				product.getCapabilityMap().get("ipDslamVoip") != null ||
				product.getCapabilityMap().get("localPhone") != null ||
				product.getCapabilityMap().get("longDistancePhone") != null ||
				product.getCapabilityMap().get("wirelessPhone") != null)) {
			return true;
		}
		return false;
	}
	
	/** To check base monthly promotion is available
	 * @param product
	 * @return
	 */
	public static ProductPromotionType isBaseMonthlyAvailable(ProductSummaryVO product){
		for(ProductPromotionType promotion : product.getPromotions()) {
			if (Constants.BASE_MONTHLY_DISCOUNT.equalsIgnoreCase(promotion.getType())){
				return promotion;
			}
		}
		return null;
	}

	/**
	 * @param product
	 * @return
	 */
	public static String getInformationalPromoShortDescription(ProductSummaryVO product){
		if(product.getPromotions() != null
				&& !product.getPromotions().isEmpty()){
			for(ProductPromotionType promo : product.getPromotions()) {
				if(promo != null 
						&& !Utils.isBlank(promo.getType()) 
						&& Constants.INFORMATIONAL_PROMOTION.equalsIgnoreCase(promo.getType())){
					return promo.getShortDescription();
				}
			}
		}
		return "";
	}
	
	/**
	 * @param product
	 * @return
	 */
	public static String getPromoSummary(ProductSummaryVO product){
		try{
			if(product.getPromotions() != null
					&& !product.getPromotions().isEmpty()){
				for(ProductPromotionType promo : product.getPromotions()) {
					if(promo.getMetaData()!= null){
						List<String> mDataList = promo.getMetaData().getMetaData();
						for (String mdata : mDataList) {
							if(mdata.indexOf('=') > -1){
								String[] nameValuePair = mdata.split("=");
								if (nameValuePair.length == 2){
									if (nameValuePair[0].equalsIgnoreCase("PROMO_SUMMARY")){
										return nameValuePair[1];
									}
								}
							}
						}
					}
				}
			}
			if(product.getPromotionMetaDataList()!=null && !product.getPromotionMetaDataList().isEmpty()){
				for(String metaData :product.getPromotionMetaDataList()){
					if (metaData != null &&  metaData.contains("=")){
						String[] nameValuePair = metaData.split("=");
						if (nameValuePair.length == 2){
							if (nameValuePair[0].equalsIgnoreCase("PROMO_SUMMARY")){
								return nameValuePair[1];
							}
						}
					}
				}
			}

		}
		catch(Exception e){
			logger.warn("Invalid display promo summary"+e.getMessage());
		}
		return "";
	}
	
	/**
	 * @param product
	 * @return
	 */
	public static String getViewDetailsPromoSummary(ProductInfoType product){
		try{
			if(product.getProductDetails().getPromotion() != null
					&& !product.getProductDetails().getPromotion().isEmpty()){
				for(ProductPromotionType promo : product.getProductDetails().getPromotion()) {
					if(promo.getMetaData()!= null){
						List<String> mDataList = promo.getMetaData().getMetaData();
						for (String mdata : mDataList) {
							if(mdata.indexOf('=') > -1){
								String[] nameValuePair = mdata.split("=");
								if (nameValuePair.length == 2){
									if (nameValuePair[0].equalsIgnoreCase("PROMO_SUMMARY")){
										return nameValuePair[1];
									}
								}
							}
						}
					}
				}
			}
			if (product.getProductDetails().getMetaData() != null 
					&& product.getProductDetails().getMetaData().getMetaData() != null 
					&& product.getProductDetails().getMetaData().getMetaData().size() > 0){
				List<String> mDataList = product.getProductDetails().getMetaData().getMetaData();
				for(String metaData : mDataList){
					if (metaData != null &&  metaData.contains("=")){
						String[] nameValuePair = metaData.split("=");
						if (nameValuePair.length == 2){
							if (nameValuePair[0].equalsIgnoreCase("PROMO_SUMMARY")){
								return nameValuePair[1];
							}
						}
					}
				}
			}

		}
		catch(Exception e){
			logger.warn("Invalid display promo summary"+e.getMessage());
		}
		return "";
	}
	
	/**
	 * @param product
	 * @return
	 */
	public static void insertAndUpdateCustomerTrackerData(OrderType order, HttpSession session, CustomerTrackerDao customerTrackerDao){
		try{
			if(pool == null)
			{
				int i = ConfigRepo.getInt("*.thread_pool_size") == 0 ? 500 : ConfigRepo.getInt("*.thread_pool_size");
				logger.info("threadPoolSizeInInsertAndUpdateCustomerTrackerData="+i);
				pool = Executors.newFixedThreadPool(i);
			}
			CustomerTrackerManager customerTrackerManager = new CustomerTrackerManager();
			customerTrackerManager.setOrder(order);
			customerTrackerManager.setSession(session);
			customerTrackerManager.setCustomerTrackerDao(customerTrackerDao);
			pool.execute(customerTrackerManager);
		}
		catch(Exception e){
			logger.warn("Exception_in_insertAndUpdateCustomerTrackerData"+e.getMessage());
		}
	}
	
	public static void postVZSaveSmartCartProduct(OrderType order, HttpSession session){
		try{
			
			Boolean isVZSmartCartSaveEnable = Boolean.valueOf(ConfigRepo.getString("*.VZSmartCartSave_enable"));
			String URL_ENDPOINT = ConfigRepo.getString("*.VZSmartCartSave_Url") == null ? null : ConfigRepo.getString("*.VZSmartCartSave_Url");
			if(isVZSmartCartSaveEnable && !Utils.isBlank(URL_ENDPOINT)){
				if(isVZSmartCartSaveEnable && LineItemUtil.isVZLineItemNotInProvisionReady(order)){
					try{
						RESTClientForDrupal.INSTANCE.postVZSaveSmartCartProduct(Long.toString(order.getExternalId()),
								(String)session.getAttribute("GUID"), URL_ENDPOINT);
						session.setAttribute("populatedVerizonSmartCartOrderId", Long.toString(order.getExternalId()));
					}catch(Exception e){
						logger.error("Error in postVZSaveSmartCartProduct "+e.getMessage());
					}
				}
			}
		}
		catch(Exception e){
			logger.warn("Exception_in_post VZ SaveSmartCartProduct"+e.getMessage());
		}
	}
	
	public static List<CustomerTracker> sortCustomers(List<CustomerTracker> list){
		Collections.sort(list, new Comparator<CustomerTracker>() {
			public int compare(CustomerTracker o2, CustomerTracker o1) {
				Integer customerId1 = null;
				Integer customerId2 = null;

				if (o1.getCallNumberId() != null) {
					customerId1 = o1.getCallNumberId();
				}
				if (o2.getCallNumberId() != null) {
					customerId2 = o2.getCallNumberId();
				}
				if (customerId1 == null && customerId2 == null) {
					return 0;
				} else if (customerId1 == null) {
					return -1;
				} else if (customerId2 == null) {
					return 1;
				} else {
					return customerId1.compareTo(customerId2);
				}
			}
		});
		logger.info("CustomerTracker list size="+list.size());
		long lineItemNumber = 0;
		List<CustomerTracker> customerTrackerList = new ArrayList<CustomerTracker>();
		for(CustomerTracker customerTracker : list ){
			if(!(customerTracker.getLineItemId() == lineItemNumber)){
				lineItemNumber = customerTracker.getLineItemId();
				customerTrackerList.add(customerTracker);
			}
		}
		logger.info("customerTrackerList size="+customerTrackerList.size());
		return customerTrackerList;
	}
	public static void setAgentTracker(List<CustomerTracker> tsLineItemTrackerDataList,HttpSession session){
		 
		CustomerTrackerConversion customerTrackerJson = new CustomerTrackerConversion();
		CustomerTrackerDetails customerTrackerDetails = (CustomerTrackerDetails)session.getAttribute("customerTrackerDetails");
		int concertCallCount = 0;
		int actualCallCount = 0;
		float utilityPoints = 0;
		int utilityPitchedCount = 0;
		float totalActualPoints = 0;
		int totalUtilityProducts =0;
		Integer callNumberId = 0;
		float totalPoints = 0;
		if(customerTrackerDetails != null){
			concertCallCount = customerTrackerDetails.getConcertCallCount();
			if(customerTrackerDetails.getActualCallCount() != null){
				actualCallCount = customerTrackerDetails.getActualCallCount();
			}
			utilityPoints = customerTrackerDetails.getUtilityPoints();
			utilityPitchedCount = customerTrackerDetails.getUtilityPitchedCount();
		}
		
		try{
			
			if(session.getAttribute("updatedUtilityPoints")!= null){
				utilityPoints = Float.valueOf((String)session.getAttribute("updatedUtilityPoints"));
				customerTrackerDetails.setUtilityPoints(utilityPoints);
				totalPoints = totalPoints + utilityPoints;
				session.setAttribute("customerTrackerDetails",customerTrackerDetails);
			}else{
				totalPoints = totalPoints + utilityPoints;
			}
			session.setAttribute("updatedUtilityPoints",null);
			
			if(session.getAttribute("updatedCallCount")!= null){
				actualCallCount = Integer.valueOf((String)session.getAttribute("updatedCallCount"));
				customerTrackerDetails.setActualCallCount(actualCallCount);
				session.setAttribute("customerTrackerDetails",customerTrackerDetails);
				callNumberId = actualCallCount;
			}
			else if(actualCallCount >0){
				callNumberId = actualCallCount;
			}
			
			session.setAttribute("updatedCallCount",null);
			Set<Long> totalCallNumbers = new HashSet<Long>(); 
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			if(!tsLineItemTrackerDataList.isEmpty()){
				//ListIterator<CustomerTracker> itr = (ListIterator<CustomerTracker>) tsLineItemTrackerDataList.iterator();
				ListIterator<CustomerTracker> itr = tsLineItemTrackerDataList.listIterator();
				while(itr.hasNext()){
					CustomerTracker customerTrackerObject = (CustomerTracker) itr.next();
					if(customerTrackerObject.getProductType() != null && (customerTrackerObject.getProductType().equalsIgnoreCase("UtilityOffer"))){
						totalUtilityProducts++;
						//totalUtilityPoints = totalUtilityPoints + Float.valueOf(customerTrackerObject.getActualPoints());
						//totalPoints = totalPoints + Float.valueOf(customerTrackerObject.getActualPoints());
					}else{
						totalCallNumbers.add(customerTrackerObject.getOrderId());
						//totalCallNumbers.add(customerTrackerObject.getCallNumberId());
						totalActualPoints = totalActualPoints + Float.valueOf(customerTrackerObject.getActualPoints());
						
						totalPoints = totalPoints + Float.valueOf(customerTrackerObject.getActualPoints());
					}
					customerTrackerObject.setNoOfDays("N/A");
					if(customerTrackerObject.getMoveDate() != null && customerTrackerObject.getInstallDate() != null){
						 Date movingDate = customerTrackerObject.getMoveDate().getTime();
						 Date installDate = customerTrackerObject.getInstallDate().getTime();
						 Date installDateVal = sdf.parse(sdf.format(installDate));
						 Date movingDateVal = sdf.parse(sdf.format(movingDate));
						long noOfDaysDiff= Math.abs((installDateVal.getTime() - movingDateVal.getTime()) / (1000 * 60 * 60 * 24)); 
						customerTrackerObject.setNoOfDays(String.valueOf(noOfDaysDiff));
					}
				}
			}
			logger.info("totalCallNumbers size="+totalCallNumbers.size());
			logger.info("callNumberId="+callNumberId);
			customerTrackerJson.setTotalSales(totalCallNumbers.size());
			customerTrackerJson.setConcertCallCount(concertCallCount);
			customerTrackerJson.setActualCallCount(callNumberId);
			customerTrackerJson.setTotalActualPoints(totalPoints);
			customerTrackerJson.setTotalUtilityProducts(totalUtilityProducts);
			customerTrackerJson.setTotalUtilityPoints(utilityPoints);
			if(callNumberId > 0){
				customerTrackerJson.setGRPC((float)(totalPoints)/callNumberId);
				customerTrackerJson.setConversion(((float)totalCallNumbers.size() / callNumberId) * 100);
			}else{
				customerTrackerJson.setGRPC((float)callNumberId);
				customerTrackerJson.setConversion((float)callNumberId);
			}
			if(utilityPitchedCount > 0){
				customerTrackerJson.setUtilityPitched(utilityPitchedCount);
				if(totalUtilityProducts > 0 ){
					customerTrackerJson.setUtilityConversion(((float)totalUtilityProducts / utilityPitchedCount) * 100);
				}else{
					customerTrackerJson.setUtilityConversion(0.0f);
				}
			}else{
				customerTrackerJson.setUtilityPitched(0);
				customerTrackerJson.setUtilityConversion(0.0f);
			}
			session.setAttribute("customerTrackerJson", customerTrackerJson);
		}catch(Exception e){
			logger.info("error in customer tracker "+e.getMessage());
		}
	}
	public static boolean isHughesNetServiceable(Map<String, List<ProductVOJSON>> productsMap){
		if(productsMap != null && !productsMap.isEmpty()){
			for (Entry<String, List<ProductVOJSON>> categoryData :productsMap.entrySet()){
				List<ProductVOJSON> productsList = categoryData.getValue();
				if(productsList!= null && productsList.size()>0){
					for(ProductVOJSON productVOJSON: productsList){
						if(productVOJSON.getProviderExtId() != null && !(productVOJSON.getProviderExtId().equalsIgnoreCase(Constants.HUGHESNET) || productVOJSON.getProviderExtId().equalsIgnoreCase("15500581"))){
							if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("CONN_SPEED") != null && !productVOJSON.getFilterMetaDatMap().get("CONN_SPEED").equalsIgnoreCase("NA")){
								String connectionSpeed = productVOJSON.getFilterMetaDatMap().get("CONN_SPEED");
								if(!Utils.isBlank(connectionSpeed)){
									try{     
										double connectionSpeedVal = Double.valueOf(connectionSpeed);
										if(connectionSpeedVal >= 3 ){   
											return false;         
											}        
										}catch(Exception e){ 
											logger.error("error_while_to_convert_connectionSpeed_from_String_to_Double", e);    
									}              
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
	public static boolean isHughesNetAvailable(Map<String, List<ProductVOJSON>> productsMap){
		if(productsMap != null && !productsMap.isEmpty()){
			for (Entry<String, List<ProductVOJSON>> categoryData :productsMap.entrySet()){
				List<ProductVOJSON> productsList = categoryData.getValue();
				if(productsList!= null && productsList.size()>0){
					for(ProductVOJSON productVOJSON: productsList){
						if(productVOJSON.getProviderExtId() != null && (productVOJSON.getProviderExtId().equalsIgnoreCase(Constants.HUGHESNET) || productVOJSON.getProviderExtId().equalsIgnoreCase("15500581"))){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	public static boolean isHughesNetProductAvaialbelInServiceable(Map<String, List<ProductVOJSON>> productsMap,Map<Long, Long> rtProductCountMap){
		if(productsMap != null && !productsMap.isEmpty() && rtProductCountMap != null && !rtProductCountMap.isEmpty()){
			for (Entry<String, List<ProductVOJSON>> categoryData :productsMap.entrySet()){
				List<ProductVOJSON> productsList = categoryData.getValue();
				if(productsList!= null && productsList.size()>0){
					for(ProductVOJSON productVOJSON: productsList){
						if(productVOJSON.getProviderExtId() != null){
							for(Entry<Long, Long> entry : rtProductCountMap.entrySet()){
								if(entry.getKey().equals(Long.parseLong(productVOJSON.getProviderExtId()))){
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
}
