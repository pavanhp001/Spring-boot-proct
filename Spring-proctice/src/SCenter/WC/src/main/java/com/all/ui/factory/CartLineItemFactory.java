package com.A.ui.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.A.productResults.managers.ProductResultsManager;
import com.A.productResults.vo.ProductSummaryVO;
import com.A.ui.constants.Constants;
import com.A.ui.constants.ControllerConstants;
import com.A.ui.domain.WarmtransferTpvRepo;
import com.A.ui.util.LineItemUtil;
import com.A.ui.util.Utils;
import com.A.ui.vo.FeaturesAndSelecetionsVO;
import com.A.ui.vo.LineItemTypeVO;
import com.A.ui.vo.SalesCenterVO;
import com.A.ui.vo.SessionVO;
import com.A.V.exception.UnRecoverableException;
import com.A.xml.cm.v4.CustomerAttributeDetailType;
import com.A.xml.cm.v4.CustomerAttributeEntityType;
import com.A.xml.v4.AccountHolderType;
import com.A.xml.v4.AttributeDetailType;
import com.A.xml.v4.AttributeEntityType;
import com.A.xml.v4.FeatureValueType;
import com.A.xml.v4.LineItemAttributeType;
import com.A.xml.v4.LineItemSelectionType;
import com.A.xml.v4.LineItemStatusCodesType;
import com.A.xml.v4.LineItemStatusType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.ObjectFactory;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.ProviderSourceBaseType;
import com.A.xml.v4.SelectionChoiceType;
import com.A.xml.v4.SelectedFeaturesType.FeatureGroup;

/**
 * @author Preetam
 *
 */
public enum CartLineItemFactory 
{

	INSTANCE;

	private static final Logger logger = Logger.getLogger(CartLineItemFactory.class);

	private ObjectFactory oFactory = new ObjectFactory();

	private static final String FALSE = "false";
	public static final String PROVIDER_FEEDBACK = "provider_feedback";
	private static final String DISPLAY = "Display";
	private static final String PRODUCT_TYPE = "productType";
	private static final String BASE_MONTHLY_FEE = "baseMonthlyFee";
	private static final String ONE_TIME_FEE = "OneTimeFee";
	private static final String MONTHLY_PRICE_AFTER_PROMO = "MonthlyPriceAfterPromo";
	private static final String ELIGIBLE_PLANS = "EligiblePlans";
	private static final String PRODUCT_NAME = "PRODUCT_NAME";
	private static final String NAME = "name";
	private static final String PROVIDER_NAME = "PROVIDER_NAME";
	private static final String IMAGE_ID = "IMAGE_ID";
	private static final String ID = "id";
	private static final String SECOND_DESIRED_DATE = "SECOND_DESIRED_DATE";
	private static final String FIRST_INSTALLATION_TIME = "FIRST_INSTALLATION_TIME";
	private static final String DATE = "DATE";
	private static final String TIME = "TIME";
	private static final String IS_FALLOUT_ORDER = "isFalloutOrder";
	private static final String IPAD = "ipad";
	private static final String PRODUCT_CATEGORY = "PRODUCT_CATEGORY";
	private static final String THERM_RATE = "THERM_RATE";
	private static final String GUARANTEED_BILL_AMOUNT = "GUARANTEED_BILL_AMOUNT";
	private static final String PREPAY_DEPOSIT = "PREPAY_DEPOSIT";
	private static final String BASE_NON_RECURRING_PRICE = "BASE_NON_RECURRING_PRICE";
	private static final String BASE_RECURRING_PRICE = "BASE_RECURRING_PRICE";

	
	private static final String STATUS = "STATUS";
	private static final String CKO = "CKO";
	private static final String CKO_READY = "CKOReady";
	private static final String CKO_COMPLETE = "CKOComplete";
	private static final String CKO_ERROR = "CKOError";
	private static final String CKO_INCOMPLETE = "CKOInComplete";
	private static final String REMOVED = "Removed";
	
	private static final String DISH = "27010360";
	private static final String ADT = "4142189";
	private static final String DIRECTSTAR = "2314635";
	private static final String CSP = "30698347";//COMCAST SALES PORTAL
	private static final String DISHTRANSFER = "30671669";
	private static final String VERIZON = "4353598";
	private static final String ATT = "24699452";
	private static final String ATTV6 = "15500201";
	private static final String COMCAST = "26069942";
	private static final String CABLEVISION = "5793";
	private static final String CENTURY_LINK = "32416075";
	private static final String CENTURY_LINK_SALES_CODE = "9999499";
	private static final String FRONTIER = "32937483";
	private static final String QWEST_SALES_CODE = "AFFH7CB";
	private static final String SALES_CODE = "salesCode";
	private static final String COX_RTS_PROVIDER_ID = "15499341";
	private static final String HUGHESNET_RTS_PROVIDER_ID = "15500621";
	private static final String PROMOTION = "productPromotion";
	
	private static final String TRUE = "true";
	
	private static final String PRODUCT_TYPE_1 = "productType";
	
	private static final String DOUBLE_PLAY_VIDEO_INTERNET = "DOUBLE_PLAY_VIDEO_INTERNET";
	private static final String DOUBLE_PLAY_VIDEO_PHONE = "DOUBLE_PLAY_VIDEO_PHONE";
	private static final String DOUBLE_PLAY_INTERNET_PHONE = "DOUBLE_PLAY_INTERNET_PHONE";
	
	private static String NATURALGAS = "NATURALGAS";
	private static String ELECTRICITY = "ELECTRICITY";
	private static final String ENERGY_RATE = "2000";

	private static final String ATT_DIRECTV = "ATT_DirecTV";
	
	private static final String DOUBLE_PLAY = "DOUBLE_PLAY";
	
	private static final String TWC_RTS = "15500221";

	/**
	 * Sorts the given Order based on the LineItemAttribute CKO/STATUS
	 * 
	 * Also Implemented Warm Transfer Rules for placing the warm transfer products
	 * at the Bottom
	 * 
	 * @param order
	 * @return List<LineItemType>
	 */
	public  List<LineItemType> sortLineItemBasedOnStatus(OrderType order)
	{
		List<LineItemType> mainList = new ArrayList<LineItemType>();
		Map<String,LineItemType> tmpMap = new TreeMap<String,LineItemType>();
		int i=0;
		Double startTime = Double.valueOf(System.currentTimeMillis());
		for(LineItemType lineItem: order.getLineItems().getLineItem())
		{
			String status = null;
			for(AttributeEntityType entity : lineItem.getLineItemAttributes().getEntity())
			{
				if(entity!=null && entity.getSource()!=null){
					if(entity.getSource().equalsIgnoreCase(CKO))
					{
						for(AttributeDetailType attribute : entity.getAttribute())
						{
							if(attribute.getName().equals(STATUS))
							{
								status = attribute.getValue();
							}
						}	
					}
				}
			}
			if(status == null)
			{
				status="OTHERS";
			}
			if(status.equals(CKO_READY))
			{
				if(lineItem.getLineItemDetail().getDetail()
						.getProductLineItem().getProvider().getExternalId().equals(DISH))
				{
					//Adding Dish Products to List
					tmpMap.put("AB"+i, lineItem);
				}
				else
				{
					//Warm Transfer/ TPV shopping cart rules
					if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(CABLEVISION))
					{
						//Direct Star  
						tmpMap.put("AD"+i, lineItem);
					}
					else if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(DIRECTSTAR))
					{	//Direct TV
						tmpMap.put("AE"+i, lineItem);
					}else if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(DISHTRANSFER))
					{
						//Dish Transfer
						tmpMap.put("AF"+i, lineItem);
					}
					else if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(ADT))
					{
						//ADT Security choice 
						tmpMap.put("AG"+i, lineItem);
					}
					else if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(CSP))
					{
						//Comcast  product
						tmpMap.put("AH"+i, lineItem);
					}
					else
					{
						//Adding remaining Products
						tmpMap.put("AC"+i, lineItem);
					}
				}
				i++;
			}
			else if(status.equals(CKO_COMPLETE))
			{
				//Warm Transfer/ TPV shopping cart rules
				if(lineItem.getLineItemDetail().getDetail()
						.getProductLineItem().getProvider().getExternalId().equals(CABLEVISION))
				{
					//Direct Star  
					tmpMap.put("BC"+i, lineItem);
				}
				else if(lineItem.getLineItemDetail().getDetail()
						.getProductLineItem().getProvider().getExternalId().equals(DIRECTSTAR))
				{
					//Direct Star
					tmpMap.put("BD"+i, lineItem);
				}else if(lineItem.getLineItemDetail().getDetail()
						.getProductLineItem().getProvider().getExternalId().equals(DISHTRANSFER))
				{
					//Dish Transfer
					tmpMap.put("BE"+i, lineItem);
				}
				else if(lineItem.getLineItemDetail().getDetail()
						.getProductLineItem().getProvider().getExternalId().equals(ADT))
				{
					//ADT Security choice 
					tmpMap.put("BF"+i, lineItem);
				}else if(lineItem.getLineItemDetail().getDetail()
						.getProductLineItem().getProvider().getExternalId().equals(CSP))
				{
					//Comcast product
					tmpMap.put("BG"+i, lineItem);
				}
				else
				{
					//Adding remaining Products
					tmpMap.put("BB"+i, lineItem);
				}
				i++;
			}
			else if(status.equals(CKO_INCOMPLETE))
			{

				if(lineItem.getLineItemDetail().getDetail().
						getProductLineItem().getProvider().getExternalId().equals(DISH))
				{
					//Adding Dish Products to List
					tmpMap.put("CE"+i, lineItem);
				}else
				{
					//Warm Transfer/ TPV shopping cart rules
					if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(CABLEVISION))
					{
						//Direct Star  
						tmpMap.put("CG"+i, lineItem);
					}
					else if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(DIRECTSTAR))
					{
						//Direct Star  
						tmpMap.put("CH"+i, lineItem);
					}else if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(DISHTRANSFER))
					{
						//Direct Transfer
						tmpMap.put("CI"+i, lineItem);
					}
					else if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(ADT))
					{
						//ADT Security choice 
						tmpMap.put("CJ"+i, lineItem);
					}else if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(CSP))
					{
						//Comcast product
						tmpMap.put("CK"+i, lineItem);
					}else
					{
						//Adding remaining Products
						tmpMap.put("CF"+i, lineItem);
					}
				}
				i++;
			}
			else if(status.equals(CKO_ERROR))
			{

				if(lineItem.getLineItemDetail().getDetail().
						getProductLineItem().getProvider().getExternalId().equals(DISH))
				{
					//Adding Dish Products to List
					tmpMap.put("DE"+i, lineItem);
				}
				else
				{
					//Warm Transfer/ TPV shopping cart rules
					if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(DIRECTSTAR))
					{
						//Direct TV
						tmpMap.put("DG"+i, lineItem);
					}else if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(DISHTRANSFER))
					{
						//Dish Transfer
						tmpMap.put("DH"+i, lineItem);
					}
					else if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(ADT))
					{
						//ADT Security choice 
						//Comcast G2B product
						tmpMap.put("DI"+i, lineItem);
					}else if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(CSP))
					{
						//Comcast product
						tmpMap.put("DJ"+i, lineItem);
					}else
					{
						//Adding remaining Products
						tmpMap.put("DF"+i, lineItem);
					}
				}

				i++;

			}else if(status.equals(REMOVED))
			{
				tmpMap.put("E"+i, lineItem);
				i++;
			}else
			{
				tmpMap.put("F"+i, lineItem);
				i++;
			}

		}
		for (Entry<String,LineItemType> entry : tmpMap.entrySet()) 
		{
			mainList.add(entry.getValue());
		}
		logger.info("Time_Taken_for_sorting_by_status_"+(Double.valueOf(System.currentTimeMillis()) - startTime) / 1000);
		return mainList;
	}

	/**
	 * @param name
	 * @param value
	 * @param description
	 * @param source
	 * @return AttributeEntityType
	 */
	public AttributeEntityType setAttributeEntityType(String name, String value, String description, String source)
	{

		AttributeDetailType attribute = oFactory.createAttributeDetailType();
		attribute.setName(name);
		attribute.setValue(value);
		attribute.setDescription(description);

		AttributeEntityType entity =oFactory.createAttributeEntityType();
		entity.setSource(source);
		entity.getAttribute().add(attribute);

		return entity;
	}

	/**
	 * @param name
	 * @param value
	 * @param description
	 * @param source
	 * @return AttributeEntityType
	 */
	public CustomerAttributeEntityType setCustomerAttributeEntityType(String name, String value, String description, String source)
	{
		com.A.xml.cm.v4.ObjectFactory cmObjectFactory = new com.A.xml.cm.v4.ObjectFactory();
		CustomerAttributeDetailType attribute = cmObjectFactory.createCustomerAttributeDetailType();
		attribute.setName(name);
		attribute.setValue(value);
		attribute.setDescription(description);

		CustomerAttributeEntityType entity =cmObjectFactory.createCustomerAttributeEntityType();
		entity.setSource(source);
		entity.getAttribute().add(attribute);

		return entity;
	}	

	/**
	 * Maps LineItemAttributes from Java.util.Map<String,String> and returns the AttributeEntityType
	 * 
	 * @param map
	 * @param source
	 * @return
	 */
	public AttributeEntityType setAttributeEntityType(Map<String, String> map, String source)
	{

		AttributeEntityType entity =oFactory.createAttributeEntityType();
		entity.setSource(source);

		for (Entry<String, String> entry : map.entrySet()) 
		{
			AttributeDetailType attribute = oFactory.createAttributeDetailType();
			attribute.setName(entry.getKey());
			attribute.setValue(entry.getValue());
			entity.getAttribute().add(attribute);
		}

		return entity;
	}


	/**
	 * @param order
	 * @param productUniqueIdMap
	 * @param errorReasonMap
	 * @return List<String>
	 */
	public  List<String> addToOrderResult(OrderType order, Map<String,String> productUniqueIdMap, Map<Integer, String> errorReasonMap, String totalPoints)
	{
		try{

			List<String> result= new ArrayList<String>();

			if(order == null || order.getExternalId() == 0){
				result = null;

			} else {
				logger.info("productUniqueIdMap="+productUniqueIdMap);
				for(LineItemType lineItem:order.getLineItems().getLineItem())
				{
					if(LineItemUtil.isProduct(lineItem) &&
							!Utils.isBlank(productUniqueIdMap.
									get(lineItem.getPartnerExternalId()+"_"+
											lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId())))
					{
						Map<Integer,Boolean> promMap = CartLineItemFactory.INSTANCE.hasLineItemPromtions(order);
						boolean hasPromtion = false; 
						if(promMap.get(lineItem.getLineItemNumber()) != null)
						{
							hasPromtion = promMap.get(lineItem.getLineItemNumber());
						}
						result.add(lineItem.getLineItemNumber()+"|"+
								lineItem.getExternalId()+"|"+
								lineItem.getLineItemStatus().getStatusCode().name()+"|"+
								lineItem.getLineItemPriceInfo().getBaseRecurringPrice()+"|"+
								lineItem.getLineItemPriceInfo().getBaseNonRecurringPrice()+"|"+hasPromtion+"|"+
								totalPoints);
					}
				}
				result = addToOrderErrorResult(errorReasonMap, result);
			}
			return result;
		}
		catch(Exception e)
		{
			logger.warn("Error_in_addToOrderResult",e);
			return null;
		}
	}

	/**
	 * @param errorReasonMap
	 * @param result
	 * @return List<String>
	 */
	public  List<String> addToOrderErrorResult(Map<Integer, String> errorReasonMap ,List<String> result)
	{
		if(errorReasonMap.size()!=0)
		{
			for(Entry<Integer, String> reason:errorReasonMap.entrySet())
			{
				logger.info(reason.getKey()+"_reason_"+reason.getValue());
				result.add(reason.getKey(),reason.getValue());	
			}
		}
		return result;
	}



	/**
	 * Checks whether the product is ready to Place Order or Not(Mostly used by Order Summary Page)
	 * 
	 * @param order
	 * @return boolean: returns true if the product is ready for Place Order or else false
	 */
	public  boolean allowPlaceOrder(OrderType order)
	{
		List<String> statusList = new ArrayList<String>();
		int isRemoved = 0;
		int isCKOComplete = 0;
		boolean isPlaceOrder = false;

		if(order.getLineItems().getLineItem() == null ||
				order.getLineItems().getLineItem().size() == 0)
		{
			return false;
		}

		for(LineItemType lineItem: order.getLineItems().getLineItem())
		{

			/*	
			 * Stopping the Promotions and Offers 
			 * 
			 */

			if( LineItemUtil.isProductIncludingRemoved(lineItem))
			{
				entityFor: for(AttributeEntityType entity : lineItem.getLineItemAttributes().getEntity())
				{

					if(entity != null && entity.getSource()!= null)
					{

						String status = null;

						if(entity.getSource().equalsIgnoreCase(CKO))
						{

							attributeFor: for(AttributeDetailType attribute : entity.getAttribute())
							{
								if(attribute.getName().equals(STATUS))
								{
									status = attribute.getValue();
									statusList.add(status);
									if(status.equals(CKO_COMPLETE))
									{
										isCKOComplete++;
									}
									if(status.equals(REMOVED))
									{
										isRemoved++;
									}
									break attributeFor;
								}
							}

						}

						if(!Utils.isBlank(status))
						{
							break entityFor;
						}
					}

				}
			}
		}

		/*	
		 * 
		 * Should allow the Place Order if all the Products in Cart are CKO_complete status OR 
		 * at least one Product in Cart is in CKO_complete and the rest of the Products in Removed status
		 * 
		 */
		if((statusList.size() == isCKOComplete) ||
				((statusList.size() == isCKOComplete + isRemoved) && (isCKOComplete != 0)))
		{
			isPlaceOrder = true;	
		}
		else
		{
			isPlaceOrder = false;	
		}

		return isPlaceOrder;
	}

	/**
	 * @param value
	 * @return ProviderSourceBaseType
	 */
	public ProviderSourceBaseType getProviderSourceBaseType(String value)
	{
		for (ProviderSourceBaseType c: ProviderSourceBaseType.values()) 
		{
			if (c.toString().equals(value)) 
			{
				return c;
			}
		}
		return null;
	}


	/**
	 * Gets the LineItemAttribute Value Based on Source and Name 
	 * 
	 * @param lineItem
	 * @param entitySrc
	 * @param attrName
	 * @return
	 */
	public String getLineItemAttr(final LineItemType lineItem,
			final String entitySrc,
			final String attrName)

	{
		LineItemAttributeType liAttrib = lineItem.getLineItemAttributes();

		if ( liAttrib == null )
		{
			return "";
		}

		List<AttributeEntityType> entities = liAttrib.getEntity();

		for(AttributeEntityType entity : entities)
		{
			if (entity.getSource() != null
					&& entity.getSource().equalsIgnoreCase(entitySrc))
			{
				for(AttributeDetailType attr : entity.getAttribute())
				{
					if (attr.getName() != null && attr.getName().equalsIgnoreCase(attrName))
					{
						return attr.getValue();
					}
				}
			}
		}

		return "";
	}

	/**
	 * Sorts the LineItems Based on the ProviderId.Specially used for validating WarmTransferRules 
	 * 
	 * @param order
	 * @return List<LineItemType>
	 */
	public List<LineItemType> sortLineItemProducts(OrderType order, HttpServletRequest request)
	{
		//Getting the LineItem in the Order
		List<LineItemType> lineItems = order.getLineItems().getLineItem();
		List<LineItemType> sortedLineItems = new ArrayList<LineItemType>();
		Map<Integer,LineItemType> tmpMap = new TreeMap<Integer,LineItemType>();
		List<LineItemType> wtOtherLineItems = new ArrayList<LineItemType>();
		Double startTime = Double.valueOf(System.currentTimeMillis());
		List<String> wtDisabledProviderIDList = (List<String>) request.getSession().getAttribute("wtDisabledProviderIDList");
		if(lineItems != null){
			for(LineItemType lineItem: lineItems)
			{	
				//LineItem Excluding Promotion and LineItems Which has Status Cancelled_Removed
				if( lineItem.getEventType()!=null 
						&& lineItem.getEventType().intValue() == 2
						&& lineItem.getLineItemStatus().getStatusCode() != null 
						&& !LineItemStatusCodesType.SALES_NEW_ORDER.equals(lineItem.getLineItemStatus().getStatusCode())
						&& !LineItemStatusCodesType.CANCELLED_REMOVED.equals(lineItem.getLineItemStatus().getStatusCode()))
				{
					if(WarmtransferTpvRepo.wtProviderMap.get(lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId())!= null ){
						if((wtDisabledProviderIDList == null) || (!wtDisabledProviderIDList.contains(lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId()))){
							tmpMap.put(WarmtransferTpvRepo.wtProviderMap.get(lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId()), lineItem);
						}
					}else if(LineItemUtil.isProduct(lineItem)){
						wtOtherLineItems.add(lineItem);
					}
				}
			}
		}
		//Tree Map Sorts the Data and inserts into ArrayList in the sorted form
		if(tmpMap != null && !tmpMap.isEmpty()){
			for (Entry<Integer,LineItemType> entry : tmpMap.entrySet()) 
			{
				sortedLineItems.add(entry.getValue());
				break;
			}
		}else{
			sortedLineItems.addAll(wtOtherLineItems);
		}
		logger.info("Time_Taken_for_sorting_by_provider_Id"+(Double.valueOf(System.currentTimeMillis()) - startTime) / 1000);
		return sortedLineItems;
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	public Map<Integer,Boolean> hasLineItemPromtions(OrderType order)
	{
		Map<Integer,Boolean> lineitemPromotionMap = new HashMap<Integer,Boolean>();
		List<LineItemType> lineItems = order.getLineItems().getLineItem();
		if(lineItems != null)
		{
			for(LineItemType lineItem : lineItems)
			{
				lineitemPromotionMap.put(Integer.valueOf(lineItem.getLineItemNumber()), Boolean.FALSE);
				if(LineItemUtil.isPromotion(lineItem))
				{
					for(Integer applies : lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo())
					{
						lineitemPromotionMap.put(applies, Boolean.TRUE);
					}
				}
			}
		}
		return lineitemPromotionMap;
	}

	public void setComCastOrderNumberInSession(OrderType order, HttpServletRequest request)
	{
		SessionVO sessionVO = (SessionVO)request.getSession().getAttribute("sessionVO");
		if(sessionVO==null)
			sessionVO = new SessionVO(request.getSession().getId());
		List<LineItemType> lineItems = order.getLineItems().getLineItem();
		SalesCenterVO salesCenterVo = (SalesCenterVO) sessionVO.get(ControllerConstants.SALESCONTEXT);
		if(lineItems != null)
		{
			for(LineItemType lineItem : lineItems)
			{
				if(LineItemUtil.isSubmittedProduct(lineItem))
				{
					if(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId().equals(CSP))
					{
						if(!Utils.isBlank(lineItem.getProviderConfirmationNumber()))
						{
							if(salesCenterVo != null)
							{
								salesCenterVo.setValueByName("comcast.order.number", 
										lineItem.getProviderConfirmationNumber());
								request.getSession().setAttribute(ControllerConstants.SALESCONTEXT, salesCenterVo);
								sessionVO.getData().put(ControllerConstants.SALESCONTEXT, salesCenterVo);
								request.getSession().setAttribute("sessionVO", sessionVO);
							}
						}
					}
				}
			}
		}
	}
	public void setRTIMOrderNumberInSession(OrderType order, HttpServletRequest request)
	{
		try{
			SessionVO sessionVO = (SessionVO)request.getSession().getAttribute("sessionVO");
			if(sessionVO==null)
				sessionVO = new SessionVO(request.getSession().getId());
			List<LineItemType> lineItems = order.getLineItems().getLineItem();
			SalesCenterVO salesCenterVo = (SalesCenterVO) sessionVO.get(ControllerConstants.SALESCONTEXT);
			if(salesCenterVo == null) {
				salesCenterVo = (SalesCenterVO) request.getSession().getAttribute(ControllerConstants.SALESCONTEXT);
				logger.info("getting salesCenterVo from request.getSession because SessionVO is empty");
			}

			if(order != null) {
				logger.info("setRTIMOrderNumberInSession_order=" + order.getExternalId());
			} else {
				logger.info("order is null!");
			}
			if(lineItems != null)
			{
				for(LineItemType lineItem : lineItems)
				{
					logger.info("LineItemExternalId : "+lineItem.getExternalId());
					if(LineItemUtil.isSubmittedProduct(lineItem))
					{
						logger.info("SubmittedProductLineItemExternalId : "+lineItem.getExternalId());
						logger.info("providerExtId="+ lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId());
						logger.info("line providerConfirmationNumber="+ lineItem.getProviderConfirmationNumber());
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(COMCAST))
						{
							if(!Utils.isBlank(lineItem.getProviderConfirmationNumber()))
							{
								if(salesCenterVo != null)
								{
									salesCenterVo.setValueByName("comcast.order.number", 
											lineItem.getProviderConfirmationNumber());
									request.getSession().setAttribute(ControllerConstants.SALESCONTEXT, salesCenterVo);
									sessionVO.getData().put(ControllerConstants.SALESCONTEXT, salesCenterVo);								
								} else {
									logger.info("salesCenterVo is null");
									request.getSession().setAttribute("comcast.order.number", lineItem.getProviderConfirmationNumber());
								}
							}
						}
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(DISH))
						{
							if(!Utils.isBlank(lineItem.getProviderConfirmationNumber()))
							{
								if(salesCenterVo != null)
								{
									salesCenterVo.setValueByName("dish.order.number", 
											lineItem.getProviderConfirmationNumber());
									request.getSession().setAttribute(ControllerConstants.SALESCONTEXT, salesCenterVo);
									sessionVO.getData().put(ControllerConstants.SALESCONTEXT, salesCenterVo);
								} else {
									logger.info("salesCenterVo is null");
									request.getSession().setAttribute("dish.order.number", lineItem.getProviderConfirmationNumber());
								}
							}
						}
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(FRONTIER))
						{
							if(!Utils.isBlank(lineItem.getProviderConfirmationNumber()))
							{
								if(salesCenterVo != null)
								{
									salesCenterVo.setValueByName("frontier.order.number", 
											lineItem.getProviderConfirmationNumber());
									request.getSession().setAttribute(ControllerConstants.SALESCONTEXT, salesCenterVo);
									sessionVO.getData().put(ControllerConstants.SALESCONTEXT, salesCenterVo);
								} else {
									logger.info("salesCenterVo is null");
									request.getSession().setAttribute("frontier.order.number", lineItem.getProviderConfirmationNumber());
								}
							}
						}			
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(VERIZON))
						{
							if(!Utils.isBlank(lineItem.getProviderConfirmationNumber()))
							{
								if(salesCenterVo != null)
								{
									salesCenterVo.setValueByName("verizon.order.number", 
											lineItem.getProviderConfirmationNumber());
									request.getSession().setAttribute(ControllerConstants.SALESCONTEXT, salesCenterVo);
									sessionVO.getData().put(ControllerConstants.SALESCONTEXT, salesCenterVo);
								} else {
									logger.info("salesCenterVo is null");
									request.getSession().setAttribute("verizon.order.number", lineItem.getProviderConfirmationNumber());
								}
							}
						}
						if(lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals(ATTV6)
							|| lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals(ATT))
						{
							if(!Utils.isBlank(lineItem.getProviderConfirmationNumber()))
							{
								if(salesCenterVo != null)
								{
									salesCenterVo.setValueByName("att.order.number", 
											lineItem.getProviderConfirmationNumber());
									request.getSession().setAttribute(ControllerConstants.SALESCONTEXT, salesCenterVo);
									sessionVO.getData().put(ControllerConstants.SALESCONTEXT, salesCenterVo);
								} else {
									logger.info("salesCenterVo is null");
									request.getSession().setAttribute("att.order.number", lineItem.getProviderConfirmationNumber());
								}
							}
						}
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(CENTURY_LINK))
						{
							if(!Utils.isBlank(lineItem.getProviderConfirmationNumber()))
							{
								if(salesCenterVo != null)
								{
									int isCLOrQwest = isCenturyLinkOrQwestProduct(lineItem);

									if(isCLOrQwest==1)
									{
										salesCenterVo.setValueByName("cl.order.number", 
												lineItem.getProviderConfirmationNumber());
										request.getSession().setAttribute(ControllerConstants.SALESCONTEXT, salesCenterVo);
										sessionVO.getData().put(ControllerConstants.SALESCONTEXT, salesCenterVo);
									}
									else if(isCLOrQwest==2)
									{
										salesCenterVo.setValueByName("qwest.order.number", 
												lineItem.getProviderConfirmationNumber());
										request.getSession().setAttribute(ControllerConstants.SALESCONTEXT, salesCenterVo);
										sessionVO.getData().put(ControllerConstants.SALESCONTEXT, salesCenterVo);
									}
								} else {
									int isCLOrQwest = isCenturyLinkOrQwestProduct(lineItem);
									if(isCLOrQwest==1)
									{
										logger.info("salesCenterVo is null");
										request.getSession().setAttribute("cl.order.number", lineItem.getProviderConfirmationNumber());
									}
									else if(isCLOrQwest==2)
									{
										logger.info("salesCenterVo is null");
										request.getSession().setAttribute("qwest.order.number", lineItem.getProviderConfirmationNumber());
									}
								}
							}
						}
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(COX_RTS_PROVIDER_ID))
						{
							if(!Utils.isBlank(lineItem.getProviderConfirmationNumber()))
							{
								if(salesCenterVo != null)
								{
									salesCenterVo.setValueByName("cox.order.number", 
											lineItem.getProviderConfirmationNumber());
									request.getSession().setAttribute(ControllerConstants.SALESCONTEXT, salesCenterVo);
									sessionVO.getData().put(ControllerConstants.SALESCONTEXT, salesCenterVo);
								} else {
									logger.info("salesCenterVo is null");
									request.getSession().setAttribute("cox.order.number", lineItem.getProviderConfirmationNumber());
								}
							}
						}
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(TWC_RTS))
						{
							if(!Utils.isBlank(lineItem.getProviderConfirmationNumber()))
							{
								if(salesCenterVo != null)
								{
									salesCenterVo.setValueByName("AcctNum", lineItem.getProviderConfirmationNumber());
									salesCenterVo.setValueByName("INstallDate", LineItemUtil.getLineItemDesireDate(order));
									salesCenterVo.setValueByName("Phone Number", order.getCustomerInformation().getCustomer().getBestPhoneContact());
									request.getSession().setAttribute(ControllerConstants.SALESCONTEXT, salesCenterVo);
									sessionVO.getData().put(ControllerConstants.SALESCONTEXT, salesCenterVo);
								} else {
									logger.info("salesCenterVo is null");
									request.getSession().setAttribute("AcctNum", lineItem.getProviderConfirmationNumber());
								}
							}
						}
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(HUGHESNET_RTS_PROVIDER_ID))
						{
							if(!Utils.isBlank(lineItem.getProviderConfirmationNumber()))
							{
								if(salesCenterVo != null)
								{
									salesCenterVo.setValueByName("hughesnet.order.number", 
											lineItem.getProviderConfirmationNumber());
									request.getSession().setAttribute(ControllerConstants.SALESCONTEXT, salesCenterVo);
									sessionVO.getData().put(ControllerConstants.SALESCONTEXT, salesCenterVo);
								} else {
									logger.info("salesCenterVo is null");
									request.getSession().setAttribute("hughesnet.order.number", lineItem.getProviderConfirmationNumber());
								}
							}
						}	

					}
				}
			} else {
				logger.info("no lineitems found=" + order.getLineItems());
			}
			request.getSession().setAttribute("sessionVO", sessionVO);
		}catch (Exception e) {
			logger.warn("Error_in_setRTIMOrderNumberInSession",e);
		}
	}


	/**
	 * Verifying line item is contains Century link or qwest product. Returns 1 if its is century link. Returns 2 if it is qwest.
	 * @param lineItem
	 * @return int
	 */
	public int isCenturyLinkOrQwestProduct(LineItemType lineItem)
	{
		int isCenturyLinkOrQwest = 0;

		if(lineItem.getLineItemAttributes()!=null && !lineItem.getLineItemAttributes().getEntity().isEmpty())
		{
			for(AttributeEntityType entityType :lineItem.getLineItemAttributes().getEntity())
			{
				if(CKO.equalsIgnoreCase(entityType.getSource()))
				{
					if(entityType.getAttribute()!=null && !entityType.getAttribute().isEmpty())
					{
						for(AttributeDetailType attribute: entityType.getAttribute())
						{
							if( !Utils.isBlank(attribute.getName()) 
									&& attribute.getName().equalsIgnoreCase(SALES_CODE)
									&& !Utils.isBlank(attribute.getValue()) )
							{
								if( attribute.getValue().equalsIgnoreCase(CENTURY_LINK_SALES_CODE) )
								{
									isCenturyLinkOrQwest = 1;
								}
								if( attribute.getValue().equalsIgnoreCase(QWEST_SALES_CODE) )
								{
									isCenturyLinkOrQwest = 2;
								}
								break;
							}
						}
					}
					break;
				}
			}
		}
		return isCenturyLinkOrQwest;
	}



	/**
	 * 
	 * Getting LineItemType list from which LineItemType has TPV event as well as Line Item Status is in provision_ready state on the Order.
	 * 
	 * @param order
	 * @return LineItemType list
	 */
	public List<LineItemType> getTPVLineItemsWithSortingOrder(OrderType order) 
	{
		List<LineItemType> lineItems = new ArrayList<LineItemType>();
		Map<Integer,LineItemType> tmpMap = new TreeMap<Integer,LineItemType>();
		List<LineItemType> otherTpvlineItems = new ArrayList<LineItemType>();
		List<LineItemType> orderLineItems = order.getLineItems().getLineItem();

		if(lineItems != null)
		{
			for(LineItemType lineItem : orderLineItems)
			{
				//TPV rules
				if(lineItem.getEventType()!=null 
						&& lineItem.getEventType().intValue() == 1
						&& lineItem.getLineItemStatus()!=null
						&& lineItem.getLineItemStatus().getStatusCode()!=null
						&& lineItem.getLineItemStatus().getStatusCode().value().
						equals(LineItemStatusCodesType.PROVISION_READY.value()) )
				{
					if(WarmtransferTpvRepo.tpvProviderMap.get(lineItem.getLineItemDetail().getDetail()
							.getProductLineItem().getProvider().getExternalId())!= null){

						tmpMap.put(WarmtransferTpvRepo.tpvProviderMap.get(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId()), lineItem);
					}
					else if(lineItem.getEventType()!= null && lineItem.getEventType().intValue() == 1){
						otherTpvlineItems.add(lineItem);
					}
				}
			}

			if(tmpMap != null && !tmpMap.isEmpty()){
				for (Entry<Integer,LineItemType> entry : tmpMap.entrySet()) 
				{
					lineItems.add(entry.getValue());
					break;
				}
			}
			else{
				lineItems.addAll(otherTpvlineItems);
			}
		}

		return lineItems;
	}

	/**
	 * Preparing list of LineItemTypeVO from List of LineItemType.
	 * And sorting selected features and custom selections based on BaseRecurringPrice in descending order.
	 * 
	 * @param lineItemTypes
	 * @return lineItemTypeVOList: returns list of  LineItemTypeVOs.
	 */
	public List<LineItemTypeVO> sortLineItemFeaturesAndSelections(List<LineItemType> lineItemTypes, List<AccountHolderType> accountHolderTypes,ProductResultsManager productResultManager) throws UnRecoverableException
	{
		List<LineItemTypeVO> itemTypeList = new ArrayList<LineItemTypeVO>();
		for (LineItemType lineItemType : lineItemTypes) 
		{
			LineItemTypeVO lineItemTypeVO = new LineItemTypeVO();

			if(lineItemType.getAccountHolderExternalId()!=null
					&& accountHolderTypes!=null && !accountHolderTypes.isEmpty() )
			{
				for (AccountHolderType accountHolderType : accountHolderTypes)
				{
					if (accountHolderType.getExternalId() == lineItemType.getAccountHolderExternalId().longValue())
					{
						lineItemTypeVO.setAccountHolderFirstName(accountHolderType.getFirstName());
						lineItemTypeVO.setAccountHolderLastName(accountHolderType.getLastName());
						lineItemTypeVO.setAccountHolderMiddleName(accountHolderType.getMiddleName());
						lineItemTypeVO.setAccountHolderNameSuffix(accountHolderType.getNameSuffix());
					}
				}
			}
			lineItemTypeVO.setCategory(LineItemUtil.getLineItemAttr(lineItemType,PRODUCT_CATEGORY,PRODUCT_TYPE_1));
			lineItemTypeVO.setLineItemExternalId(lineItemType.getExternalId());
			if(lineItemType.getLineItemDetail()!=null 
					&& lineItemType.getLineItemDetail().getDetail()!=null 
					&& lineItemType.getLineItemDetail().getDetail().getProductLineItem()!=null)
			{
				lineItemTypeVO.setProductName(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getName());
				lineItemTypeVO.setProductExternalId(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getExternalId());
				if(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProductCategoryList() != null 
						&& lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProductCategoryList().getProductCategory() != null
						&& !lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProductCategoryList().getProductCategory().isEmpty()){
					String category = lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProductCategoryList().getProductCategory().get(0).getDisplayName();
					if(productResultManager != null 
							&& !Utils.isBlank(category) 
							&& !Utils.isBlank(lineItemTypeVO.getProductExternalId()) 
							&& (NATURALGAS.equalsIgnoreCase(category.toUpperCase()) 
									|| ELECTRICITY.equalsIgnoreCase(category.toUpperCase()))
									&& productResultManager.getProductByIconMap().get(category.toUpperCase()) != null){
						for(ProductSummaryVO summaryVO : productResultManager.getProductByIconMap().get(category.toUpperCase())){
							if(lineItemTypeVO.getProductExternalId().equalsIgnoreCase(summaryVO.getExternalId())){
								if(!Utils.isBlank(summaryVO.getEnergyUnitName())){
									lineItemTypeVO.setEnergyUnitName(summaryVO.getEnergyUnitName());
								}
								if(summaryVO.getEnergyTierMap() != null && !summaryVO.getEnergyTierMap().isEmpty()){
									if(summaryVO.getEnergyTierMap().get(ENERGY_RATE) != null){
										lineItemTypeVO.setThermRate(String.valueOf(summaryVO.getEnergyTierMap().get(ENERGY_RATE)));
									}else{
										for(Entry<String,Double> map : summaryVO.getEnergyTierMap().entrySet()){
											lineItemTypeVO.setThermRate(String.valueOf(map.getValue()));
											break;
										}
									}
								}
							}
						}
					}
				}
				if(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider()!=null){
					lineItemTypeVO.setProviderName(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getName());
					lineItemTypeVO.setProviderExtId(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId());
				}
			}

			lineItemTypeVO.setLineItemDetailType(lineItemType.getLineItemDetail().getDetailType());
			lineItemTypeVO.setProductSorce(lineItemType.getProductDatasource().getValue().name());
			lineItemTypeVO.setLineItemStatus(lineItemType.getLineItemStatus().getStatusCode().name());
			lineItemTypeVO.setMonthlyTotal(lineItemType.getLineItemPriceInfo().getBaseRecurringPrice());
			lineItemTypeVO.setInstalationTotal(lineItemType.getLineItemPriceInfo().getBaseNonRecurringPrice());

			List<String> previousLineItemStatus = new ArrayList<String>();
			if(lineItemType.getLineItemStatusHistory().getPreviousStatus()!=null 
					&& !lineItemType.getLineItemStatusHistory().getPreviousStatus().isEmpty())
			{
				for (LineItemStatusType lineItemStatusType : lineItemType.getLineItemStatusHistory().getPreviousStatus()) 
				{
					previousLineItemStatus.add(lineItemStatusType.getStatusCode().name());
				}
			}
			lineItemTypeVO.setPreviousStatus(previousLineItemStatus);

			lineItemTypeVO.setLineItemNumber(lineItemType.getLineItemNumber());

			if(lineItemTypeVO.getLineItemDetailType().equalsIgnoreCase(PROMOTION))
			{
				lineItemTypeVO.setDisplayLineItem(FALSE);
			}
			else
			{
				lineItemTypeVO.setDisplayLineItem(TRUE);
			}

			//Injecting lineItem dates to lineItemVo.
			setDateValuesToLineItemVoFromLineItem(lineItemType,lineItemTypeVO);

			//Injecting lineItem attributes to lineItemVo.
			setValuesToLineItemVoFromLineItemAttributes(lineItemType, lineItemTypeVO);

			//Injecting lineItem features and selections to lineItemVo.
			setFeaturesAndCustomSelectionsInSortingOrder(lineItemType, lineItemTypeVO);
			lineItemTypeVO.setImageID(lineItemTypeVO.getParentExternalId());
			if (ATTV6.equals(lineItemTypeVO.getParentExternalId()) && !Utils.isBlank(lineItemTypeVO.getCategory()) 
					&& (lineItemTypeVO.getCategory().toUpperCase().equalsIgnoreCase(DOUBLE_PLAY_INTERNET_PHONE)
							|| lineItemTypeVO.getCategory().toUpperCase().equalsIgnoreCase(DOUBLE_PLAY_VIDEO_PHONE) 
							|| lineItemTypeVO.getCategory().toUpperCase().equalsIgnoreCase(DOUBLE_PLAY_VIDEO_INTERNET))){

				lineItemTypeVO.setCategory(DOUBLE_PLAY);
			}
			//overriding with ATT_DirecTV logo for ATT V6 when lineitem has satellite capability 
			if(ATTV6.equals(lineItemTypeVO.getParentExternalId()) 
					&& productResultManager != null 
					&& !Utils.isBlank(lineItemTypeVO.getProductExternalId()) 
			){
				for(Entry<String, List<ProductSummaryVO>> entry:productResultManager.getProductByIconMap().entrySet()){
					for(ProductSummaryVO summaryVO : entry.getValue()){
						if(ATTV6.equals(summaryVO.getProviderExternalId()) 
								&& lineItemTypeVO.getProductExternalId().equalsIgnoreCase(summaryVO.getExternalId())){
							if(productResultManager.isATTProductHasSatellite(summaryVO)){
								lineItemTypeVO.setImageID(ATT_DIRECTV);
							}
						}else if(!Utils.isBlank(lineItemTypeVO.getProductExternalId())
								&& ATTV6.equals(summaryVO.getProviderExternalId())
								&& lineItemTypeVO.getProductExternalId().indexOf("DTV") > 0) {
							lineItemTypeVO.setImageID(ATT_DIRECTV);
						}
					}
				}
			}
			itemTypeList.add(lineItemTypeVO);
		}
		return itemTypeList;
	}

	/**
	 * Setting date values to LineItemTypeVO from LineItemType.
	 * 
	 * @param lineItemType
	 * @param lineItemTypeVO
	 * @return 
	 */
	private void setDateValuesToLineItemVoFromLineItem(
			LineItemType lineItemType, LineItemTypeVO lineItemTypeVO) throws UnRecoverableException
			{
		if(lineItemType.getSchedulingInfo().getDesiredStartDate() !=null
				&& lineItemType.getSchedulingInfo().getDesiredStartDate().getDate() !=null)
		{
			lineItemTypeVO.setSchedulingStartDate(lineItemType.getSchedulingInfo().getDesiredStartDate().getDate());
			lineItemTypeVO.setSchedulingStartTime(lineItemType.getSchedulingInfo().getDesiredStartDate().getTime());
			lineItemTypeVO.setSchedulingStartDateStartTime(lineItemType.getSchedulingInfo().getDesiredStartDate().getStartTime());
			lineItemTypeVO.setSchedulingStartDateEndTime(lineItemType.getSchedulingInfo().getDesiredStartDate().getEndTime());
		}
		if(lineItemType.getSchedulingInfo().getScheduledStartDate() !=null
				&& lineItemType.getSchedulingInfo().getScheduledStartDate().getDate() !=null)
		{
			lineItemTypeVO.setScheduledStartDate(lineItemType.getSchedulingInfo().getScheduledStartDate().getDate());
			lineItemTypeVO.setScheduledStartTime(lineItemType.getSchedulingInfo().getScheduledStartDate().getTime());
			lineItemTypeVO.setScheduledStartDateStartTime(lineItemType.getSchedulingInfo().getScheduledStartDate().getStartTime());
			lineItemTypeVO.setScheduledStartDateEndTime(lineItemType.getSchedulingInfo().getScheduledStartDate().getEndTime());
		}
		if(lineItemType.getSchedulingInfo().getDisconnectDate() !=null
				&& lineItemType.getSchedulingInfo().getDisconnectDate().getDate() !=null)
		{
			lineItemTypeVO.setDisconnectStartDate(lineItemType.getSchedulingInfo().getDisconnectDate().getDate());
			lineItemTypeVO.setDisconnectStartTime(lineItemType.getSchedulingInfo().getDisconnectDate().getTime());
			lineItemTypeVO.setDisconnectStartDateStartTime(lineItemType.getSchedulingInfo().getDisconnectDate().getStartTime());
			lineItemTypeVO.setDisconnectStartDateEndTime(lineItemType.getSchedulingInfo().getDisconnectDate().getEndTime());
		}
		if(lineItemType.getSchedulingInfo().getActualStartDate() !=null
				&& lineItemType.getSchedulingInfo().getActualStartDate().getDate() !=null)
		{
			lineItemTypeVO.setActualStartDate(lineItemType.getSchedulingInfo().getActualStartDate().getDate());
			lineItemTypeVO.setActualStartTime(lineItemType.getSchedulingInfo().getActualStartDate().getTime());
			lineItemTypeVO.setActualStartDateStartTime(lineItemType.getSchedulingInfo().getActualStartDate().getStartTime());
			lineItemTypeVO.setActualStartDateEndTime(lineItemType.getSchedulingInfo().getActualStartDate().getEndTime());
		}
			}

	/**
	 * Setting all selected features and custom selections to LineItemTypeVO from LineItemType 
	 * and sorting based on BaseRecurringPrice in descending order.
	 * 
	 * @param lineItemType
	 * @param lineItemTypeVO
	 * @return 
	 */
	private void setFeaturesAndCustomSelectionsInSortingOrder(
			LineItemType lineItemType, LineItemTypeVO lineItemTypeVO)throws UnRecoverableException 
			{
		List<FeaturesAndSelecetionsVO> featuresAndSelecetionsList = new ArrayList<FeaturesAndSelecetionsVO>();

		//Setting selected features values to LineItemTypeVO from LineItemType
		if(lineItemType.getSelectedFeatures() != null 
				&& lineItemType.getSelectedFeatures().getFeatures() != null
				&& lineItemType.getSelectedFeatures().getFeatures().getFeatureValue()!=null
				&& !lineItemType.getSelectedFeatures().getFeatures().getFeatureValue().isEmpty())
		{
			for (FeatureValueType featureValues : lineItemType.getSelectedFeatures().getFeatures().getFeatureValue())
			{
				if(featureValues.getIncluded() == null)
				{
					/*
					 * Below condition is added for 115021 ticket.
					 * When the dish line item contains "ipad" related feature on oredre then we don't display it in order summary/order recap pages.
					 */
					if(!(featureValues.getExternalId().toLowerCase().startsWith(IPAD))
							|| !lineItemTypeVO.getProviderExtId().equalsIgnoreCase(DISH))
					{
						FeaturesAndSelecetionsVO featuresAndSelecetionsVO = new FeaturesAndSelecetionsVO();
						featuresAndSelecetionsVO.setFeatureExtrnalId(featureValues.getExternalId());
						featuresAndSelecetionsVO.setFeatureValue(featureValues.getValue());
						featuresAndSelecetionsVO.setBaseRecurringPrice(featureValues.getPrice().getBaseRecurringPrice());
						featuresAndSelecetionsVO.setBaseNonRecurringPrice(featureValues.getPrice().getBaseNonRecurringPrice());
						featuresAndSelecetionsList.add(featuresAndSelecetionsVO);
					}
				}
			}
		}

		// Setting selected feature groups values to LineItemTypeVO from LineItemType
		if(lineItemType.getSelectedFeatures() != null 
				&& lineItemType.getSelectedFeatures().getFeatureGroup() != null
				&& !lineItemType.getSelectedFeatures().getFeatureGroup().isEmpty())
		{
			for (FeatureGroup featureGroup : lineItemType.getSelectedFeatures().getFeatureGroup())
			{
				if(featureGroup.getFeatureValue() != null 
						&& !featureGroup.getFeatureValue().isEmpty())
				{
					for (FeatureValueType featureValues : featureGroup.getFeatureValue())
					{
						if(featureValues.getIncluded() == null)
						{
							FeaturesAndSelecetionsVO featuresAndSelecetionsVO = new FeaturesAndSelecetionsVO();
							featuresAndSelecetionsVO.setFeatureExtrnalId(featureValues.getExternalId());
							featuresAndSelecetionsVO.setFeatureValue(featureValues.getValue());
							featuresAndSelecetionsVO.setBaseRecurringPrice(featureValues.getPrice().getBaseRecurringPrice());
							featuresAndSelecetionsVO.setBaseNonRecurringPrice(featureValues.getPrice().getBaseNonRecurringPrice());
							featuresAndSelecetionsList.add(featuresAndSelecetionsVO);
						}
					}
				}
			}
		}

		//Setting selected custom selections values to LineItemTypeVO from LineItemType
		if(lineItemType.getCustomSelections() != null 
				&& lineItemType.getCustomSelections().getSelections() != null
				&& lineItemType.getCustomSelections().getSelections().getSelection()!=null
				&& !lineItemType.getCustomSelections().getSelections().getSelection().isEmpty())
		{
			for (LineItemSelectionType selectionType : lineItemType.getCustomSelections().getSelections().getSelection())
			{
				if(selectionType.getSelectionChoice() != null 
						&& !selectionType.getSelectionChoice().isEmpty())
				{
					for (SelectionChoiceType selectionChoiceType : selectionType.getSelectionChoice())
					{
						FeaturesAndSelecetionsVO featuresAndSelecetionsVO = new FeaturesAndSelecetionsVO();
						featuresAndSelecetionsVO.setFeatureExtrnalId(selectionType.getName());
						featuresAndSelecetionsVO.setFeatureValue(selectionChoiceType.getShortDescription());
						featuresAndSelecetionsVO.setBaseRecurringPrice(selectionChoiceType.getPrice().getBaseRecurringPrice());
						featuresAndSelecetionsVO.setBaseNonRecurringPrice(selectionChoiceType.getPrice().getBaseNonRecurringPrice());
						featuresAndSelecetionsList.add(featuresAndSelecetionsVO);
					}
				}
			}
		}
		if(!featuresAndSelecetionsList.isEmpty())
		{
			Collections.sort(featuresAndSelecetionsList);
		}
		lineItemTypeVO.setFeaturesAndSelecetionsVO(featuresAndSelecetionsList);
			}

	/**
	 * Setting line item attribute values to LineItemTypeVO from LineItemType.
	 * 
	 * @param lineItemType
	 * @param lineItemTypeVO
	 * @return 
	 */
	private void setValuesToLineItemVoFromLineItemAttributes(LineItemType lineItemType,LineItemTypeVO lineItemTypeVO) throws UnRecoverableException
	{
		if(lineItemType.getLineItemAttributes() != null 
				&& lineItemType.getLineItemAttributes().getEntity() != null
				&& !lineItemType.getLineItemAttributes().getEntity().isEmpty())
		{
			for (AttributeEntityType attributeEntityType : lineItemType.getLineItemAttributes().getEntity())
			{
				if(PRODUCT_CATEGORY.equalsIgnoreCase(attributeEntityType.getSource())
						&& attributeEntityType.getAttribute() !=null
						&&  !attributeEntityType.getAttribute().isEmpty())
				{
					for (AttributeDetailType detailType : attributeEntityType.getAttribute())
					{
						if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& PRODUCT_TYPE.equalsIgnoreCase(detailType.getName()) )
						{
							lineItemTypeVO.setProductCategoryType(detailType.getValue());
						}
					}
				}
				if(PROVIDER_FEEDBACK.equalsIgnoreCase(attributeEntityType.getSource())
						&& attributeEntityType.getAttribute() !=null
						&&  !attributeEntityType.getAttribute().isEmpty())
				{
					for (AttributeDetailType detailType : attributeEntityType.getAttribute())
					{
						if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& DISPLAY.equalsIgnoreCase(detailType.getName()) 
								&& FALSE.equalsIgnoreCase(detailType.getValue()))
						{
							lineItemTypeVO.setDisplayLineItem(FALSE);
						}
					}
				}
				else if(CKO.equalsIgnoreCase(attributeEntityType.getSource())
						&& attributeEntityType.getAttribute() !=null
						&&  !attributeEntityType.getAttribute().isEmpty())
				{
					for (AttributeDetailType detailType : attributeEntityType.getAttribute())
					{
						if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& STATUS.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setLineItemStatusAttribute(detailType.getValue());
						}
						else if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& BASE_MONTHLY_FEE.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setBaseMonthlyNonPromotionPrice(detailType.getValue());
						}
						else if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& ONE_TIME_FEE.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setOneTimePriceValue(detailType.getValue());
						}
						else if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& MONTHLY_PRICE_AFTER_PROMO.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setMonthlyPriceAfterPromoEnds(detailType.getValue());
						}else if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& IS_FALLOUT_ORDER.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setFallOutOrder(detailType.getValue());
						}
						else if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& THERM_RATE.equalsIgnoreCase(detailType.getName())
								&& Utils.isBlank(lineItemTypeVO.getThermRate()))
						{
							lineItemTypeVO.setThermRate(detailType.getValue());
						}
						else if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& GUARANTEED_BILL_AMOUNT.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setGuaranteeAmount(detailType.getValue());
						}
						else if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& PREPAY_DEPOSIT.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setPrepaidAmount(detailType.getValue());
						}
						else if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& BASE_NON_RECURRING_PRICE.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setBaseNonRecurringPriceForNaturalGas(detailType.getValue());
						}
						else if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& BASE_RECURRING_PRICE.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setBaseRecurringPriceForNaturalGas(detailType.getValue());
						}
					}
				}
				else if(PRODUCT_NAME.equalsIgnoreCase(attributeEntityType.getSource())
						&& attributeEntityType.getAttribute() !=null
						&&  !attributeEntityType.getAttribute().isEmpty())
				{
					for (AttributeDetailType detailType : attributeEntityType.getAttribute())
					{
						if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& NAME.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setLineItemName(detailType.getValue());
						}
					}
				}
				else if(PROVIDER_NAME.equalsIgnoreCase(attributeEntityType.getSource())
						&& attributeEntityType.getAttribute() !=null
						&&  !attributeEntityType.getAttribute().isEmpty())
				{
					for (AttributeDetailType detailType : attributeEntityType.getAttribute())
					{
						if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& NAME.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setLineItemProviderName(detailType.getValue());
						}
					}
				}
				else if(IMAGE_ID.equalsIgnoreCase(attributeEntityType.getSource())
						&& attributeEntityType.getAttribute() !=null
						&&  !attributeEntityType.getAttribute().isEmpty())
				{
					for (AttributeDetailType detailType : attributeEntityType.getAttribute())
					{
						if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& ID.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setParentExternalId(detailType.getValue());
						}
					}
				}
				else if(SECOND_DESIRED_DATE.equalsIgnoreCase(attributeEntityType.getSource())
						&& attributeEntityType.getAttribute() !=null
						&&  !attributeEntityType.getAttribute().isEmpty())
				{
					for (AttributeDetailType detailType : attributeEntityType.getAttribute())
					{
						if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& DATE.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setSecondDesiredDate(detailType.getValue());
						}
						else if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& TIME.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setSecondDesiredTime(detailType.getValue());
						}
					}
				}
				else if(FIRST_INSTALLATION_TIME.equalsIgnoreCase(attributeEntityType.getSource())
						&& attributeEntityType.getAttribute() !=null
						&&  !attributeEntityType.getAttribute().isEmpty())
				{
					for (AttributeDetailType detailType : attributeEntityType.getAttribute())
					{
						if(!Utils.isBlank(detailType.getName()) && !Utils.isBlank(detailType.getValue())
								&& TIME.equalsIgnoreCase(detailType.getName()))
						{
							lineItemTypeVO.setFirstDesiredTime(detailType.getValue());
						}
					}
				}
			}
		}
	}
}
