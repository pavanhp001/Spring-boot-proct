package com.AL.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import com.AL.ui.DataDialogueVO;
import com.AL.ui.DataGroupVO;
import com.AL.ui.InstallationVO;
import com.AL.ui.OrderRecapVO;
import com.AL.ui.ProductVO;
import com.AL.ui.builder.LineItemBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.DigitalCacheKeys;
import com.AL.ui.factory.CKOLineItemFactory;
import com.AL.ui.factory.DialogueDataFeildFactory;
import com.AL.ui.factory.LineItemSelectionFactory;
import com.AL.ui.factory.PrepopulateDialogue;
import com.AL.ui.service.V.OrderServiceUI;
import com.AL.ui.service.V.impl.StaticCKOCacheManager;
import com.AL.ui.util.OrderUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.AddressFields;
import com.AL.ui.vo.CustomerInfoVO;
import com.AL.ui.vo.DataFeildFeatureVO;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.SessionVO;
import com.AL.util.DateUtil;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.ApplicableType;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.DateTimeOrTimeRangeType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.RoleType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SchedulingInfoType;
import com.AL.xml.v4.SelectedDialogsType;
import com.AL.xml.v4.SelectedFeaturesType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;

/**
 * @author Sravan Kumar Nalajala
 *
 */
@Service
public class InstallationService {

	private static final Logger logger = Logger.getLogger(InstallationService.class);

	/**
	 * @param request
	 * @param orderQualVO
	 * @return
	 */
	public OrderType excuteUpdateDailog(HttpServletRequest request, OrderQualVO orderQualVO) {
		logger.info("start_excute_UpdateDailog");
		InstallationVO installationVO = (InstallationVO)StaticCKOCacheManager.INSTANCE.getObjectFromCache(DigitalCacheKeys.InstallationVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
		Map<String,DataFeildFeatureVO> dataFeildMap =  DialogueDataFeildFactory.INSTANCE.buildDataFeildFeatureVOMap(installationVO.getDialogueTypeList());
		String installationJSON  = request.getParameter("installationJSON");
		ObjectFactory oFactory = new ObjectFactory();
		LineItemAttributeType lineItemAttribute = oFactory.createLineItemAttributeType();
		SchedulingInfoType schedulingInfo = oFactory.createSchedulingInfoType();
		DateTimeOrTimeRangeType desiredStartDateTimeRange = oFactory.createDateTimeOrTimeRangeType();
		SalesContextType context = SalesContextFactory.INSTANCE.getContextFromSession(request.getSession());
		LineItemType  itemType = orderQualVO.getLineItemType();
		List<String> enterDataFeildList = new ArrayList<String>();
		OrderType updatedOrder = OrderUtil.INSTANCE.returnOrderType(orderQualVO.getOrderId());
		LineItemType orderLineItem = LineItemBuilder.INSTANCE.returnLineItemByLineItemID(updatedOrder,orderQualVO.getLineItemExternalId());
		Map<String, String> billingInfoMap = new HashMap<String, String>();
		Map<String,List<JSONObject>> tagrgetMap = new HashMap<String,List<JSONObject>>();
		CustomerInfoVO customerInfoVO = new CustomerInfoVO();
		SelectedDialogsType selectedDialogueType = null;
		SelectedDialogsType.Dialogs selectedDialogueTypeDialogue = oFactory.createSelectedDialogsTypeDialogs();
		if(orderLineItem.getActiveDialogs() != null){
			selectedDialogueType = orderLineItem.getActiveDialogs();
			SelectedDialogsType.Dialogs lineItemselectedDialogueTypeDialogue = orderLineItem.getActiveDialogs().getDialogs();
			if(lineItemselectedDialogueTypeDialogue != null ){
				selectedDialogueTypeDialogue = lineItemselectedDialogueTypeDialogue;
			}
		}else{
			selectedDialogueType = oFactory.createSelectedDialogsType();
		}
		//to display in place orderPage
		if(dataFeildMap.get("TWCBillingDisclosure") != null ){
			request.setAttribute("TWCBillingDisclosure", dataFeildMap.get("TWCBillingDisclosure").getDataFieldText());
		}
		try{
			Map<String,List<AttributeDetailType>> valueTargetKeyList = new HashMap<String,List<AttributeDetailType>>();
			valueTargetKeyList.put("FIRST_DESIRED_DATE",new ArrayList<AttributeDetailType>());
			valueTargetKeyList.put("SECOND_DESIRED_DATE",new ArrayList<AttributeDetailType>());
			JSONArray jsonArray = new JSONArray(installationJSON);
			for (int i = 0, size = jsonArray.length(); i < size; i++)
			{
				JSONObject objectInArray = jsonArray.getJSONObject(i);
				if(objectInArray.has(Constants.VALUE_TARGET)){
					buildValuTargetMap(objectInArray,tagrgetMap);
					buildBillIngInfoMap(billingInfoMap,objectInArray);
				}
				if(objectInArray.has("displaytype")){
					if(objectInArray.has(Constants.VALUE_TARGET)){
						String valueTarget = objectInArray.getString("valueTarget");
						if("lineItem.schedulingInfo.desiredStartDate.date".equalsIgnoreCase(valueTarget)){
							AttributeDetailType attr = LineItemSelectionFactory.INSTANCE.createLineitemAttribute("DATE","Customer first installation date",objectInArray.getString(Constants.VALUE),oFactory);
							valueTargetKeyList.get("FIRST_DESIRED_DATE").add(attr);
							desiredStartDateTimeRange.setDate(DateUtil.asXMLGregorianCalendar(objectInArray.getString(Constants.VALUE), "MM/dd/yyyy"));
							schedulingInfo.setDesiredStartDate(desiredStartDateTimeRange);
						}else if("lineItem.schedulingInfo.desiredStartDate2.date".equalsIgnoreCase(valueTarget)){
							AttributeDetailType attr = LineItemSelectionFactory.INSTANCE.createLineitemAttribute("DATE","Customer second installation date",objectInArray.getString(Constants.VALUE),oFactory);
							valueTargetKeyList.get("SECOND_DESIRED_DATE").add(attr);
						}else if("lineItem.schedulingInfo.desiredStartDate.time".equalsIgnoreCase(valueTarget)){
							AttributeDetailType attr = LineItemSelectionFactory.INSTANCE.createLineitemAttribute("TIME","Customer first installation time",objectInArray.getString(Constants.VALUE),oFactory);
							valueTargetKeyList.get("FIRST_DESIRED_DATE").add(attr);
						}else if("lineItem.schedulingInfo.desiredStartDate2.time".equalsIgnoreCase(valueTarget)){
							AttributeDetailType attr = LineItemSelectionFactory.INSTANCE.createLineitemAttribute("TIME","Customer second installation time",objectInArray.getString(Constants.VALUE),oFactory);
							valueTargetKeyList.get("SECOND_DESIRED_DATE").add(attr);
						}
					}
					DataFeildFeatureVO dataFeildFeatureVO = dataFeildMap.get(objectInArray.getString(Constants.NAME));
					if(dataFeildFeatureVO != null){
						selectedDialogueTypeDialogue.getDialog().add(LineItemSelectionFactory.INSTANCE.buildActiveDialogueType(dataFeildFeatureVO,oFactory,objectInArray));
						dataFeildFeatureVO.setEnteredValue(objectInArray.getString(Constants.VALUE));
						enterDataFeildList.add(objectInArray.getString(Constants.NAME));
					}
				}
			}
			for(Entry<String, DataFeildFeatureVO> entryVO:dataFeildMap.entrySet()){
				if(!enterDataFeildList.contains(entryVO.getKey())){
					DataFeildFeatureVO dataFeildFeatureVO = dataFeildMap.get(entryVO.getKey());
					dataFeildFeatureVO.setEnteredValue("");
				}
			}
			for(Entry<String, List<AttributeDetailType>> entryValueTarget :valueTargetKeyList.entrySet()){
				AttributeEntityType entity = oFactory.createAttributeEntityType();
				entity.setSource(entryValueTarget.getKey());
				for(AttributeDetailType attributeDetailType:entryValueTarget.getValue()){
					entity.getAttribute().add(attributeDetailType);
				}
				lineItemAttribute.getEntity().add(entity);
			}
			itemType.setLineItemAttributes(lineItemAttribute);
			itemType.setSchedulingInfo(schedulingInfo);
		}catch (Exception e) {
			e.printStackTrace();
		}
		selectedDialogueType.setDialogs(selectedDialogueTypeDialogue);
		itemType.setActiveDialogs(selectedDialogueType);
		OrderType orderType  = OrderServiceUI.INSTANCE.updateLineItem(orderQualVO,context,itemType);
		customerInfoVO.setFirstName(orderType.getCustomerInformation().getCustomer().getFirstName());
		customerInfoVO.setLastName(orderType.getCustomerInformation().getCustomer().getLastName());
		customerInfoVO.setMiddleName(orderType.getCustomerInformation().getCustomer().getMiddleName());
		AddressFields addrFields = buildAddressFeilds(tagrgetMap);
		customerInfoVO.setAddrFields(addrFields);
		//updateCustomer
		PrepopulateDialogue.INSTANCE.updateCustomer(updatedOrder,orderQualVO,customerInfoVO,context,billingInfoMap);
		logger.info("orderType"+orderType);
		logger.info("end_excute_UpdateDailog");
		return orderType;
	}

	/** This method is used to build OrderRecapVO object for place order page view
	 * @param orderType
	 * @param orderQualVO
	 * @return
	 */
	public OrderRecapVO buidlOrderRecapVO(HttpServletRequest request,OrderType orderType,OrderQualVO orderQualVO,SessionVO sessionVO) {

		Customer customer = orderType.getCustomerInformation().getCustomer();
		OrderRecapVO orderRecapVO = new OrderRecapVO();
		try{
			Double baseMonthlyPromotionPrice  = 0.00;
			Double installationTotalPrice  = 0.00;
			Double monthlyPrice  = 0.00;
			boolean isBaseMonthlyPromoHas = false;
			boolean isOneTimePromoHas = false;
			ProductVO productVO = (ProductVO)StaticCKOCacheManager.INSTANCE.getObjectFromCache(DigitalCacheKeys.ProductVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
			LineItemType orderLineItem = LineItemBuilder.INSTANCE.returnLineItemByLineItemID(orderType,orderQualVO.getLineItemExternalId());
			LineItemType orderRecapLineItemType = LineItemBuilder.INSTANCE.returnLineItemByLineItemID(orderType,orderQualVO.getLineItemExternalId());
			List<ApplicableType> promotionList = CKOLineItemFactory.INSTANCE.lineItemPromotionsApplicableType(orderType, orderRecapLineItemType);
			List<CustAddress>   customerAddressList = customer.getAddressList().getCustomerAddress();

			for(CustAddress custAddress:customerAddressList){
				for(RoleType role:custAddress.getAddressRoles().getRole()){
					if(RoleType.SERVICE_ADDRESS.value().equalsIgnoreCase(role.value())){
						orderRecapVO.setAddr1(custAddress.getAddress().getStreetNumber() +" "+ custAddress.getAddress().getStreetName() +" " +custAddress.getAddress().getStreetType()
								+", " + (!Utils.isBlank(custAddress.getAddress().getLine2()) ? custAddress.getAddress().getLine2() +", " : "" ));
						orderRecapVO.setAddr2(custAddress.getAddress().getCity() + ", " +custAddress.getAddress().getStateOrProvince() + " " +custAddress.getAddress().getPostalCode());
						break;
					}
				}
			}

			if(promotionList != null && !promotionList.isEmpty()){
				for(ApplicableType applicableType :promotionList){
					if(applicableType != null && applicableType.getPromotion() != null){
						if(applicableType.getPromotion().getPriceValue() != null){
							if (applicableType.getPromotion().getType() != null && Constants.BASE_MONTHLY_DISCOUNT.equalsIgnoreCase(applicableType.getPromotion().getType())){
								if(Constants.RELATIVE.equalsIgnoreCase(applicableType.getPromotion().getPriceValueType())){
									isBaseMonthlyPromoHas = true;
									baseMonthlyPromotionPrice = baseMonthlyPromotionPrice + (orderLineItem.getLineItemPriceInfo().getBaseRecurringPrice()-applicableType.getPromotion().getPriceValue());
								}else if (Constants.ABSOLUTE.equalsIgnoreCase(applicableType.getPromotion().getPriceValueType())) {
									isBaseMonthlyPromoHas = true;
									baseMonthlyPromotionPrice = baseMonthlyPromotionPrice + applicableType.getPromotion().getPriceValue();	
								}
							} else if (applicableType.getPromotion().getType() != null && Constants.ONE_TIME_FEE_DISCOUNT.equalsIgnoreCase(applicableType.getPromotion().getType())){
								if(Constants.RELATIVE.equalsIgnoreCase(applicableType.getPromotion().getPriceValueType())){
									isOneTimePromoHas = true;
									installationTotalPrice = installationTotalPrice + (orderLineItem.getLineItemPriceInfo().getBaseNonRecurringPrice()-applicableType.getPromotion().getPriceValue());
								}else if (Constants.ABSOLUTE.equalsIgnoreCase(applicableType.getPromotion().getPriceValueType())) {
									isOneTimePromoHas = true;
									installationTotalPrice = installationTotalPrice + applicableType.getPromotion().getPriceValue();	
								}
							}
						}
						applicableType.getPromotion().setDescription(skipSpecialCharacters(applicableType.getPromotion().getDescription()));
						applicableType.getPromotion().setShortDescription(skipSpecialCharacters(applicableType.getPromotion().getShortDescription()));
						applicableType.getPromotion().setConditions(skipSpecialCharacters(applicableType.getPromotion().getConditions()));
						applicableType.getPromotion().setQualification(skipSpecialCharacters(applicableType.getPromotion().getQualification()));
					}
				}
			}

			for(AttributeEntityType attributeType :orderLineItem.getLineItemAttributes().getEntity()){

				if(attributeType.getSource().equalsIgnoreCase("FIRST_DESIRED_DATE")){
					for(AttributeDetailType attType:attributeType.getAttribute()){
						if("time".equalsIgnoreCase(attType.getName())){
							orderRecapVO.setFisrInstallTime(attType.getValue());
						}
						if("date".equalsIgnoreCase(attType.getName())){
							orderRecapVO.setFisrInstallDate(attType.getValue());
						}
					}
				}
				if(attributeType.getSource().equalsIgnoreCase("SECOND_DESIRED_DATE")){

					for(AttributeDetailType attType:attributeType.getAttribute()){
						if("time".equalsIgnoreCase(attType.getName())){
							orderRecapVO.setSecondInstallTime(attType.getValue());
						}
						if("date".equalsIgnoreCase(attType.getName())){
							orderRecapVO.setSecondInstallDate(attType.getValue());
						}
					}

				}

			}
			Map<String,Map<String,DataFeildFeatureVO>>  featuerVOMap = getAllFeatuerVOList(productVO);
			Map<String,Double>  featurespriceMap = CKOLineItemFactory.INSTANCE.buildFeaturesPriceMap(orderLineItem);
			if(isBaseMonthlyPromoHas){
				monthlyPrice = monthlyPrice + baseMonthlyPromotionPrice;
				orderRecapVO.setBaseMonthlyPromotion(baseMonthlyPromotionPrice);
			}else{
				monthlyPrice = monthlyPrice + orderLineItem.getLineItemPriceInfo().getBaseRecurringPrice();
			}

			if(!isOneTimePromoHas){
				installationTotalPrice = installationTotalPrice + orderLineItem.getLineItemPriceInfo().getBaseNonRecurringPrice();	
			}
			monthlyPrice = monthlyPrice + featurespriceMap.get("baseRecurPrice");
			installationTotalPrice = installationTotalPrice + featurespriceMap.get("baseNonRecurPrice");
			orderRecapVO.setBillingDisclosure((String)request.getAttribute("TWCBillingDisclosure"));
			orderRecapVO.setFirstName(customer.getFirstName());
			orderRecapVO.setLastName(customer.getLastName());
			orderRecapVO.setMiddleName(customer.getMiddleName());
			orderRecapVO.setOrderDetialList(buildOrderDetails(orderRecapLineItemType,featuerVOMap));
			orderRecapVO.setInstallationTotal(installationTotalPrice);
			orderRecapVO.setMonthly(monthlyPrice);
			orderRecapVO.setBaseMonthlyWithOutPromotion(orderLineItem.getLineItemPriceInfo().getBaseRecurringPrice());
			orderRecapVO.setPromotionList(promotionList);
		}catch (Exception e) {
			logger.error("orderRecapVO_Error"+e);
			e.printStackTrace();
		}
		return orderRecapVO;
	}

	private String skipSpecialCharacters(String promotion) {
		if(!Utils.isBlank(promotion)){
			return Jsoup.parse(promotion, "UTF-8").text();
		}
		return promotion;
	}

	/**  This method is used to build AllFeatuerVOList with tag section to buildOrderDetails
	 * @param productVO
	 * @return
	 */
	private Map<String,Map<String,DataFeildFeatureVO>> getAllFeatuerVOList(ProductVO productVO) {
		Map<String,Map<String,DataFeildFeatureVO>> sectionDataFeildMap = new LinkedHashMap<String,Map<String,DataFeildFeatureVO>>();
		for(DataDialogueVO dataDialogueVO:productVO.getDialogueTypeList()){
			Map<String,DataFeildFeatureVO> dataFeildMap = new HashMap<String,DataFeildFeatureVO>();
			for(DataGroupVO dataGroupVO:dataDialogueVO.getDataGroupList()){
				for(DataFeildFeatureVO dataFeildFeatureVO:dataGroupVO.getDataFeildList()){
					if(dataFeildFeatureVO.getFeatureExID() != null){
						dataFeildMap.put(dataFeildFeatureVO.getFeatureExID(),dataFeildFeatureVO);
					}
				}
			}
			sectionDataFeildMap.put(dataDialogueVO.getTitle(), dataFeildMap);
		}
		return sectionDataFeildMap;
	}

	/** This method is used to build buildOrderDetails based on featuerVOSectionMap
	 * @param lineItem
	 * @param featuerVOSectionMap
	 * @return
	 */
	private List<DataFeildFeatureVO> buildOrderDetails(LineItemType lineItem,Map<String,Map<String,DataFeildFeatureVO>>  featuerVOSectionMap) {
		List<DataFeildFeatureVO> orderDetialList = new ArrayList<DataFeildFeatureVO>();
		Map<String,List<DataFeildFeatureVO>> sectionMapFeatureVOList = new LinkedHashMap<String,List<DataFeildFeatureVO>>();
		Map<String,DataFeildFeatureVO> allFeatuerVOMap = new HashMap<String,DataFeildFeatureVO>();
		SelectedFeaturesType selectedFeatures = lineItem.getSelectedFeatures();
		for(Entry<String,Map<String,DataFeildFeatureVO>> featuerVOSectMapEntry:featuerVOSectionMap.entrySet()){
			sectionMapFeatureVOList.put(featuerVOSectMapEntry.getKey(), new ArrayList<DataFeildFeatureVO>());
			allFeatuerVOMap.putAll(featuerVOSectMapEntry.getValue());
		}
		if(selectedFeatures != null)
		{
			List<FeatureValueType> features = selectedFeatures.getFeatures().getFeatureValue();
			if(features != null )
			{
				for(FeatureValueType featureValueType :features)
				{
					for(Entry<String, DataFeildFeatureVO> featuerVOEntry:allFeatuerVOMap.entrySet()){
						if(featuerVOEntry.getKey().equals(featureValueType.getExternalId())){
							featuerVOEntry.getValue().setBaseNonRecurringPrice(String.valueOf(featureValueType.getPrice().getBaseNonRecurringPrice()));
							featuerVOEntry.getValue().setBaseRecurringPrice(String.valueOf(featureValueType.getPrice().getBaseRecurringPrice()));
							sectionMapFeatureVOList.get(featuerVOEntry.getValue().getTagSectionName()).add(featuerVOEntry.getValue());
						}
					}

				}
			}

			List<FeatureGroup> featureGroups = selectedFeatures.getFeatureGroup();
			if(featureGroups != null)
			{
				for(FeatureGroup featureGroup:featureGroups)
				{
					for(Entry<String, DataFeildFeatureVO> featuerVOEntry:allFeatuerVOMap.entrySet()){
						if(featuerVOEntry.getKey().equals(featureGroup.getExternalId())){
							DataFeildFeatureVO dataFeildFeatureVOGroup = new  DataFeildFeatureVO();
							dataFeildFeatureVOGroup.setFeatureExID(featuerVOEntry.getValue().getFeatureExID());
							dataFeildFeatureVOGroup.setBaseNonRecurringPrice(featuerVOEntry.getValue().getBaseNonRecurringPrice());
							dataFeildFeatureVOGroup.setBaseRecurringPrice(featuerVOEntry.getValue().getBaseRecurringPrice());
							dataFeildFeatureVOGroup.setDataFieldText(featuerVOEntry.getValue().getDataFieldText());
							dataFeildFeatureVOGroup.setDataFieldName(featuerVOEntry.getValue().getDataFieldName());
							dataFeildFeatureVOGroup.setDescription(featuerVOEntry.getValue().getDescription());
							dataFeildFeatureVOGroup.setType(featuerVOEntry.getValue().getType());
							dataFeildFeatureVOGroup.setFeatureVOList(new ArrayList<DataFeildFeatureVO>());
							List<FeatureValueType> featuresGroupList  = featureGroup.getFeatureValue();
							if(featuresGroupList != null )
							{
								for(FeatureValueType featureValueType:featuresGroupList)
								{
									for(DataFeildFeatureVO groupVO : featuerVOEntry.getValue().getFeatureVOList()){
										if(groupVO.getFeatureExID().equals(featureValueType.getExternalId())){
											dataFeildFeatureVOGroup.getFeatureVOList().add(groupVO);
											sectionMapFeatureVOList.get(featuerVOEntry.getValue().getTagSectionName()).add(dataFeildFeatureVOGroup);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		for(Entry<String, List<DataFeildFeatureVO>> sectionMapListEntry:sectionMapFeatureVOList.entrySet()){
			orderDetialList.addAll(sectionMapListEntry.getValue());
		}
		return orderDetialList;
	}

	private void buildBillIngInfoMap(Map<String, String> billingInfoMap,JSONObject objectInArray) throws Exception {
		if(objectInArray.has(Constants.VALUE_TARGET) && objectInArray.getString(Constants.VALUE_TARGET).equalsIgnoreCase("consumerFinancialInfo.creditCard.number")){
			billingInfoMap.put("CCNumber", objectInArray.getString(Constants.VALUE));
		}else if(objectInArray.has(Constants.VALUE_TARGET) && objectInArray.getString(Constants.VALUE_TARGET).equalsIgnoreCase("consumerFinancialInfo.cardType")){
			billingInfoMap.put("CCType", objectInArray.getString(Constants.VALUE));
		}else if(objectInArray.has(Constants.VALUE_TARGET) && objectInArray.getString(Constants.VALUE_TARGET).equalsIgnoreCase("consumerFinancialInfo.creditCard.expirationDate")){
			billingInfoMap.put("CCExpDate", objectInArray.getString(Constants.VALUE));
		}
	}

	/**
	 * @param objectInArray
	 * @param tagrgetMap
	 * @throws Exception
	 */
	private void buildValuTargetMap(JSONObject objectInArray,Map<String,List<JSONObject>> tagrgetMap) throws Exception {
		String valueTarget = objectInArray.getString(Constants.VALUE_TARGET);
		if(valueTarget.contains("previousAddress")){
			if(tagrgetMap.get("previousAddress") == null){
				tagrgetMap.put("previousAddress", new ArrayList<JSONObject>());
			}
			tagrgetMap.get("previousAddress").add(objectInArray);

		}else if(valueTarget.contains("billingAddress")){
			if(tagrgetMap.get("billingAddress") == null){
				tagrgetMap.put("billingAddress", new ArrayList<JSONObject>());
			}
			tagrgetMap.get("billingAddress").add(objectInArray);
		}else if(valueTarget.contains("shippingAddress")){
			if(tagrgetMap.get("shippingAddress") == null){
				tagrgetMap.put("shippingAddress", new ArrayList<JSONObject>());
			}
			tagrgetMap.get("shippingAddress").add(objectInArray);
		}
	}
	/**
	 * @param tagrgetMap
	 * @return
	 */
	private AddressFields buildAddressFeilds(Map<String,List<JSONObject>> tagrgetMap){
		AddressFields addrFields = new AddressFields();
		try{
			for(Entry<String, List<JSONObject>> entryTarget:tagrgetMap.entrySet()){
				if(entryTarget.getKey().contains("previousAddress")){
					for(JSONObject jsonObject:entryTarget.getValue()){
						String valueTarget = jsonObject.getString(Constants.VALUE_TARGET);
						String valueTargetValue = jsonObject.getString(Constants.VALUE);
						if(valueTarget.equals("consumer.previousAddress.dwelling.prefixDirectional " +
								"||'sp'|| consumer.previousAddress.dwelling.streetNumber " +
								"||'sp'|| consumer.previousAddress.dwelling.streetName " +
						"||'sp'|| consumer.previousAddress.dwelling.streetType")){

							addrFields.setPrevAddrStreetName(valueTargetValue);
						}
						else if(valueTarget.equals("consumer.previousAddress.dwelling.line2")){
							addrFields.setPrevAddrLine2(valueTargetValue);
						}

						else if(valueTarget.equals("consumer.previousAddress.dwelling.line2info")){
							addrFields.setPrevAddrLine2Info(valueTargetValue);
						}

						else if(valueTarget.equals("consumer.previousAddress.dwelling.city")){
							addrFields.setPrevAddrCity(valueTargetValue);
						}
						else if(valueTarget.equals("consumer.previousAddress.dwelling.stateOrProvince")){
							addrFields.setPrevAddrStateOrPrivince(valueTargetValue);
						}
						else if(valueTarget.equals("consumer.previousAddress.dwelling.postalCode")){
							addrFields.setPrevAddrPostalCode(valueTargetValue);
						}
					}
				}else if(entryTarget.getKey().contains("billingAddress")){
					for(JSONObject jsonObject:entryTarget.getValue()){
						String valueTarget = jsonObject.getString(Constants.VALUE_TARGET);
						String valueTargetValue = jsonObject.getString(Constants.VALUE);
						if(valueTarget.equals("consumer.billingAddress.dwelling.prefixDirectional ||'sp'|| consumer.billingAddress.dwelling.streetNumber ||'sp'|| consumer.billingAddress.dwelling.streetName ||'sp'|| consumer.billingAddress.dwelling.streetType")){
							addrFields.setBillingAddressPresent(true);
							//addrFields.setBillingAddrStreetType(customerInfoVO.getBillingAddrStreetType());	
							addrFields.setBillingAddrStreetName(valueTargetValue);

						}
						else if(valueTarget.equals("consumer.billingAddress.dwelling.line2")){
							addrFields.setBillingAddrLine2(valueTargetValue);
						}

						else if(valueTarget.equals("consumer.billingAddress.dwelling.line2info")){
							addrFields.setBillingAddrLine2Info(valueTargetValue);
						}

						else if(valueTarget.equals("consumer.billingAddress.dwelling.city")){
							addrFields.setBillingAddrCity(valueTargetValue);
						}

						else if(valueTarget.equals("consumer.billingAddress.dwelling.stateOrProvince")){
							addrFields.setBillingAddrStateOrPrivince(valueTargetValue);
						}
						else if(valueTarget.equals("consumer.billingAddress.dwelling.postalCode")){
							addrFields.setBillingAddrPostalCode(valueTargetValue);
						}
					}
				}else if(entryTarget.getKey().contains("shippingAddress")){
					for(JSONObject jsonObject:entryTarget.getValue()){
						String valueTarget = jsonObject.getString(Constants.VALUE_TARGET);
						String valueTargetValue = jsonObject.getString(Constants.VALUE);
						if(valueTarget.equals("consumer.shippingAddress.dwelling.prefixDirectional ||'sp'|| consumer.shippingAddress.dwelling.streetNumber ||'sp'|| consumer.shippingAddress.dwelling.streetName ||'sp'|| consumer.shippingAddress.dwelling.streetType")){
							addrFields.setBillingAddrStreetNumber(valueTargetValue);
							addrFields.setBillingAddrStreetName(valueTargetValue);
						}
						else if(valueTarget.equals("consumer.shippingAddress.dwelling.line2")){
							addrFields.setShippingAddrLine2(valueTargetValue);
						}
						else if(valueTarget.equals("consumer.shippingAddress.dwelling.line2info")){
							addrFields.setShippingAddrLine2Info(valueTargetValue);
						}

						else if(valueTarget.equals("consumer.shippingAddress.dwelling.city")){
							addrFields.setShippingAddrCity(valueTargetValue);
						}

						else if(valueTarget.equals("consumer.shippingAddress.dwelling.stateOrProvince")){
							addrFields.setShippingAddrStateOrPrivince(valueTargetValue);
						}
						else if(valueTarget.equals("consumer.shippingAddress.dwelling.postalCode")){
							addrFields.setShippingAddrPostalCode(valueTargetValue);
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return addrFields;
	}
}


