package com.AL.ui.util;

import static com.AL.ui.constants.Constants.CARD_HOLDER_NAME;
import static com.AL.ui.constants.Constants.CARD_NUMBER;
import static com.AL.ui.constants.Constants.CARD_TYPE;
import static com.AL.ui.constants.Constants.CC_EXP_MONTH;
import static com.AL.ui.constants.Constants.CC_EXP_YEAR;
import static com.AL.ui.constants.Constants.CREDIT_CARD_EXP_FORMAT;
import static com.AL.ui.constants.Constants.CVV;
import static com.AL.ui.constants.Constants.GUID;
import static com.AL.ui.constants.Constants.ORDER_QUAL_STATUS;
import static com.AL.ui.constants.Constants.SALES_CONTEXT_ENTITY;
import static com.AL.ui.constants.Constants.STR_TRUE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.AL.html.Fieldset;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.builder.LineItemBuilder;
import com.AL.ui.builder.LineItemSelectionBuilder;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.factory.HtmlFactory;
import com.AL.ui.factory.PrepopulateDialogue;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.OrderServiceUI;
import com.AL.ui.service.V.ProductServiceUI;
import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.DialogueVO;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.OrderSubmitVO;
import com.AL.ui.vo.StaticCKOVO;
import com.AL.util.DateUtil;
import com.AL.V.domain.SalesContext;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.cm.v4.CreditCardTypeType;
import com.AL.xml.cm.v4.CustBillingInfoType;
import com.AL.xml.cm.v4.PaymentEventType;
import com.AL.xml.cm.v4.PaymentEventTypeType;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.ChoiceType;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.CustomSelectionsType;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.CustomizationType;
import com.AL.xml.v4.DateTimeOrTimeRangeType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemSelectionsType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.PriceInfoType;
import com.AL.xml.v4.RoleType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SchedulingInfoType;

public enum OrderUtil {
	
	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(OrderUtil.class);
	
	public Map<String, PaymentEventType> createPaymentEvent(CustomizationType customization) {
		double amount = 0d;
		Map<String, PaymentEventType> paymentEvents = new HashMap<String, PaymentEventType>();
		for(ChoiceType choice : customization.getOptions().getChoice()) {
			amount = 0d;
			if(choice.getPriceInfo() != null) {
				PriceInfoType price = choice.getPriceInfo();
				if(price.getBaseNonRecurringPrice() != null) {
					amount = amount + price.getBaseNonRecurringPrice();
				}
				if(price.getBaseRecurringPrice() != null) {
					amount = amount + price.getBaseRecurringPrice();
				}
			}
			if(amount > 0d) {
				PaymentEventType paymentEvent = new PaymentEventType();
				paymentEvent.setEvent(PaymentEventTypeType.fromValue(
						choice.getPaymentEventType().name()));
				paymentEvent.setAmount(new BigDecimal(amount));
				paymentEvent.setTransactionDate(DateUtil.getCurrentXMLDate());
				paymentEvent.setConfirmNumber(String.valueOf(Integer.MAX_VALUE % System.currentTimeMillis()));
				paymentEvent.setCustAgreedCCDisclosure(STR_TRUE);
				paymentEvents.put(choice.getExternalId(), paymentEvent);
			}
		}
		return paymentEvents;
	}

	public CustBillingInfoType createBillingInfo(Map<String, String> requestParamMap, String addressRef) {
		CustBillingInfoType billingInfo = new CustBillingInfoType();
		billingInfo.setCreditCardNumber((requestParamMap.get(CARD_NUMBER)));
		billingInfo.setCardHolderName((requestParamMap.get(CARD_HOLDER_NAME)));
		billingInfo.setVerificationCode((requestParamMap.get(CVV)));
		String expYear = requestParamMap.get(CC_EXP_YEAR);
		String expMonth = requestParamMap.get(CC_EXP_MONTH);
		if(!StringUtils.isEmpty(expYear) && 
				!StringUtils.isEmpty(expMonth)) {
			String expYearMonth = expYear + "-" + expMonth;
			billingInfo.setExpirationYearMonth(DateUtil.asXMLGregorianCalendar
					(expYearMonth, CREDIT_CARD_EXP_FORMAT));
		}
		String cardType = requestParamMap.get(CARD_TYPE);
		billingInfo.setCreditCardType(CreditCardTypeType.fromValue(cardType));
		billingInfo.setBillingUniqueId(String.valueOf(System.currentTimeMillis()));
		billingInfo.setAddressRef(addressRef);
		return billingInfo;
	}

	/**
	 * takes the sales context and iterate over the context nameValue pair and returns the guid
	 * 
	 * @param salesContext
	 * @return string
	 */
	public String getGUID(SalesContextType salesContext) {
		String guid = "";
		if(salesContext != null) {
			for(SalesContextEntityType entity : salesContext.getEntity()) {
				if(SALES_CONTEXT_ENTITY.equalsIgnoreCase(entity.getName())) {
					for(NameValuePairType pair : entity.getAttribute()) {
						if(GUID.equalsIgnoreCase(pair.getName())) {
							guid = pair.getValue();
						}
					}
				}
			}
		}
		return guid;
	}

	public OrderType getOrder(OrderManagementRequestResponse orderRequestResponse) {
		OrderType order = null;
		if(orderRequestResponse != null) {
			Response response = orderRequestResponse.getResponse();
			if((response != null) && (response.getOrderInfo() != null)) {
				if(response.getOrderInfo().size() > 0) {
					order = response.getOrderInfo().get(0);
				}
			}
		}
		return order;
	}

	public LineItemType createLineItem(OrderQualVO orderQualVO, LineItemType lineItem) {
		if(lineItem == null) {
			lineItem = new LineItemType();
		}
		lineItem.setExternalId(orderQualVO.getLineItemExternalId());
		lineItem.setLeadId(999L);
		CustomSelectionsType selections = new CustomSelectionsType();
		selections.setSelections(orderQualVO.getLineItemSelections());
		lineItem.setCustomSelections(selections);
		return lineItem;
	}

	public LineItemType createLineItem(OrderSubmitVO orderSubmitVO, Long lineItemExternalId) {
		LineItemType lineItem = new LineItemType();
		lineItem.setExternalId(lineItemExternalId);
		lineItem.setLeadId(999L);
		lineItem.setLineItemAttributes(createLineItemAttribute(ORDER_QUAL_STATUS, 
				orderSubmitVO.getOrderQualStatusCode()));
		if(orderSubmitVO.getInstallDate() != null) {
			SchedulingInfoType info = new SchedulingInfoType();
			info.setInstallationFee(BigDecimal.ZERO);
			DateTimeOrTimeRangeType dateTime = new DateTimeOrTimeRangeType();
			dateTime.setDate(orderSubmitVO.getInstallDate());
			dateTime.setStartTime(orderSubmitVO.getStartTime());
			dateTime.setEndTime(orderSubmitVO.getEndTime());
			info.setDesiredStartDate(dateTime);
			lineItem.setSchedulingInfo(info);
		}
		return lineItem;
	}

	public LineItemAttributeType createLineItemAttribute(Map<String, String> nameValuePairs) {
		LineItemAttributeType lineItemAttribute = new LineItemAttributeType();
		AttributeEntityType entity = new AttributeEntityType();
		entity.setSource("CKO");
		for (Map.Entry<String, String> entry : nameValuePairs.entrySet()) {
			AttributeDetailType detail = new AttributeDetailType();
			detail.setName(entry.getKey());
			detail.setValue(entry.getValue());
			entity.getAttribute().add(detail);
		}
		lineItemAttribute.getEntity().add(entity);
		return lineItemAttribute;
	}

	public LineItemAttributeType createLineItemAttribute(String name, String value) {
		LineItemAttributeType lineItemAttribute = new LineItemAttributeType();
		AttributeEntityType entity = new AttributeEntityType();
		entity.setSource("CKO");
		AttributeDetailType detail = new AttributeDetailType();
		detail.setName(name);
		detail.setValue(value);
		entity.getAttribute().add(detail);
		lineItemAttribute.getEntity().add(entity);
		return lineItemAttribute;
	}

	/**
	 * returns the product_external_id of the product on the current line_item
	 * @param order
	 * @param lineItemId
	 * @return productExternalID
	 */
	public String getProductExternalId(OrderType order, long lineItemId) {
		String productExternalId = "";

		if((order != null) && (order.getLineItems() != null)) {
			for(LineItemType lineItem : order.getLineItems().getLineItem()) {
				if(lineItemId == lineItem.getExternalId()) {
					if((lineItem.getLineItemDetail() != null) && 
							(lineItem.getLineItemDetail().getDetail().getProductLineItem() != null)) {
						productExternalId = lineItem.getLineItemDetail().getDetail().
						getProductLineItem().getExternalId();
					}
				}
			}
		}
		return productExternalId;
	}

	public LineItemSelectionsType getLineItemSelections(OrderType order, long lineItemId) {
		LineItemSelectionsType lineItemSelections = null;
		if((order != null) && (order.getLineItems() != null)) {
			for(LineItemType lineItem : order.getLineItems().getLineItem()) {
				if(lineItemId == lineItem.getExternalId()) {
					lineItemSelections = lineItem.getCustomSelections().getSelections();
				}
			}
		}
		return lineItemSelections;
	}


	public String getProviderExternalId(OrderType order, long lineItemId) {
		String providerExternalId = "";

		if((order != null) && (order.getLineItems() != null)) {
			for(LineItemType lineItem : order.getLineItems().getLineItem()) {
				if(lineItemId == lineItem.getExternalId()) {
					if(lineItem.getLineItemDetail() != null){
						if(lineItem.getLineItemDetail().getDetailType().equalsIgnoreCase("product")){
							if(lineItem.getLineItemDetail().getDetail().getProductLineItem() != null) {
								providerExternalId = lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId();
							}
						}
					}
				}
			}
		}
		return providerExternalId;
	}

	public ModelAndView buildUtilityProductDialoues(OrderType order,
			OrderQualVO orderQualVO, Map<String, String> reuestParamMap, ModelAndView mav, 
			CKOInitialVo CKOVo, HttpServletRequest request) throws Exception {
		
		if(!Utils.isBlank((String)request.getSession().getAttribute("previouslyGivenDataId"))){
			logger.info("Customer Qualification Page entered Dialogues ACTION PAGE :::::::: "+(String) request.getSession().getAttribute("previouslyGivenDataId"));
			String prevEnteredData = (String)request.getSession().getAttribute("previouslyGivenDataId");
			mav.addObject("previouslyEnteredData",prevEnteredData);
		}
		
		StringBuilder events = new StringBuilder();
		
		List<String> errorStringList = new ArrayList<String>();

		List<String> errorLog = null;

		String jsonString = "";

		List<String> allDataFieldList = new ArrayList<String>();

		List<String> enabledDataFieldList = new ArrayList<String>();

		LineItemType item = null;
		if(orderQualVO.getLineItemType() == null){
			for(LineItemType lineItemTypes : order.getLineItems().getLineItem()){
				if(lineItemTypes.getPartnerExternalId() != null){
					if(lineItemTypes.getPartnerExternalId().equals(orderQualVO.getProviderExternalId())){
						item = lineItemTypes;
					}
				}
			}
			orderQualVO.setLineItemType(item);
		}
		else{
			item = orderQualVO.getLineItemType();
		}
		
		if(item ==  null){
			item = orderQualVO.getNewLineItemType();
		}

		Map<String, String> preSelectedMap = PrepopulateDialogue.INSTANCE.buildPreSelectedValues(order);
		
		ProductInfoType productInfo = ProductServiceUI.INSTANCE.getProduct(orderQualVO.getProductExternalId(), orderQualVO.getGUID(), orderQualVO.getProviderExternalId());
		
		events = new StringBuilder();

		List<DataField> dataFields = new ArrayList<DataField>();

		//DialogueResponseType dialogResponse = DialogCacheService.INSTANCE.getFromCache(orderQualVO.getProductExternalId());
		
		//DialogueVO dialogueVO = DialogueUtil.buildDialogueVO(dialogResponse, productInfo.getExternalId());		
		DialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByProductId(productInfo, false, orderQualVO);
		
		List<DataGroup> dataGroupList = new ArrayList<DataGroup>();
		for (Dialogue dialogue : dialogueVO.getDialogueNameList()) {

			String externalId = dialogue.getExternalId();
			dataGroupList.addAll(dialogue.getDataGroupList());
			Map<String, List<DataField>> dataFieldMap = dialogueVO.getDataFieldMap().get(externalId);

			for(Map.Entry<String, List<DataField>> fieldEntry : dataFieldMap.entrySet()) {
				dataFields.addAll(fieldEntry.getValue());
			}
		}
		Map<String, Map<String, List<String>>> enableDependencies = new HashMap<String, Map<String, List<String>>> (); 

		enableDependencies.putAll(DialogueUtil.buildDataFieldMatrices(dataGroupList, null));

		Map<String, Map<String, List<String>>> disableDependencies = new HashMap<String, Map<String, List<String>>>();

		disableDependencies.putAll(DialogueUtil.getDisableDialogueDependencies(dataGroupList, enableDependencies, null, null));
		
		String businessPartyName = null;  
		if(order.getLineItems().getLineItem().size() > 0){
			businessPartyName = order.getLineItems().getLineItem().get(0).getLineItemDetail().getDetail().getProductLineItem().getExternalId();
		}else{
			businessPartyName = orderQualVO.getProductExternalId();
		}

		orderQualVO.setEnableDependencyMap(enableDependencies);	

		LineItemSelectionsType lineItemSelections = orderQualVO.getLineItemSelections();
		if(reuestParamMap != null && !reuestParamMap.isEmpty()){
			if((lineItemSelections == null) || (lineItemSelections.getSelection().size() == 0)) {
				lineItemSelections = LineItemSelectionBuilder.getLineItemSelections(reuestParamMap, productInfo.getProductDetails().getCustomization());
				orderQualVO.setLineItemSelections(lineItemSelections);
			}
		}

		int i = 0;
		for(DataField df : dataFields){
			allDataFieldList.add(df.getExternalId());
			if(df.isEnabled() == true){
				String modifiedExternalID = i+":" + df.getExternalId();
				if(df.getInfoType() != null && df.getInfoType().equalsIgnoreCase("DISCLOSURE")){
					modifiedExternalID += "^disclosure"; 
				}
				enabledDataFieldList.add(modifiedExternalID);
				enabledDataFieldList.addAll(generateChildvalues(enableDependencies, modifiedExternalID, i));
				i++;
			}
		}
		StaticCKOVO stCkVO = new StaticCKOVO();
		Customer customer = order.getCustomerInformation().getCustomer();
		List<CustAddress> custAddressList = customer.getAddressList().getCustomerAddress();
		for(CustAddress custAddr : custAddressList){
			for(RoleType role : custAddr.getAddressRoles().getRole()){
				if(role.value().equals("ServiceAddress")){
					String electricStartDate = DateUtil.toDateString(custAddr.getAddress().getElectricityStartAt().getValue());
					stCkVO.setValueByName("electricService.startDate", electricStartDate);
				}
			}
		}
		stCkVO.setValueByName("businessParty.name", businessPartyName);
		stCkVO.setValueByName("consumer.name.nameBlock", customer.getFirstName()+" "+ customer.getLastName());
		stCkVO.setValueByName("referrer-general-name", customer.getFirstName()+" "+ customer.getLastName());
		
		stCkVO.setValueByName(" &lt", "<");
		stCkVO.setValueByName(" &gt", ">");
		String extID = "";
		List<String> sortedList = new ArrayList<String>();
		Map<String, Boolean> trueValueMap = new HashMap<String, Boolean>();
		for(String enablevalues : enabledDataFieldList){
			if(enablevalues.indexOf(":")>0){
				String[] arr = enablevalues.split(":");
				extID = arr[1];

				if(reuestParamMap.get(extID) != null && (reuestParamMap.get(extID).length() > 0)){
					enablevalues = enablevalues+"$"+reuestParamMap.get(extID);
					trueValueMap.put(enablevalues, true);
				}
				if(enablevalues.indexOf("^disclosure") > 0){
					enablevalues = enablevalues.substring(0, enablevalues.indexOf("^disclosure"));
					trueValueMap.put(enablevalues, true);
				}
			}
		}
		sortedList.addAll(buildSortedList(trueValueMap.keySet(), enableDependencies));


		for(Entry<String, Map<String, List<String>>> enableDependenciesEntry : enableDependencies.entrySet()){
			for(Entry<String, List<String>> enableDependenciesList : enableDependenciesEntry.getValue().entrySet()){
				for(String dependedEle : enableDependenciesList.getValue()){
					allDataFieldList.add(dependedEle);
				}
			}
		}
		
		List<Fieldset> fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(
				events, dialogueVO, enableDependencies, null, null, 
				null, null, preSelectedMap, request.getContextPath(), reuestParamMap, stCkVO, null);

		for (Fieldset fieldset1 : fieldsetList) {
			String element = HtmlBuilder.INSTANCE.toString(fieldset1);
			events.append(element);
		}
		orderQualVO.setDataField(events.toString());
		orderQualVO.setAvailableDataField(dataFields);
		orderQualVO.setDialogueVO(dialogueVO);

		if(errorLog != null && errorLog.size() > 0){

			mav.addObject("errorLog", errorLog);
		}
		if(!errorStringList.isEmpty() && (jsonString != null && jsonString.trim().length() >0)){
			mav.addObject("errors",errorStringList);
			mav.addObject("iData", jsonString);
		}
		if(productInfo.getProductDetails() != null && productInfo.getProductDetails().getMetaData() != null 
				&& productInfo.getProductDetails().getMetaData().getMetaData() != null){
			for(String meta : productInfo.getProductDetails().getMetaData().getMetaData()){
				if(meta.equals("OFFER_TYPE=UTILITY")){
					mav.addObject("isUtilityOffer", true);
				}
			}
		}
		mav.addObject("productInfo", productInfo);
		mav.addObject("showBackButton", false);
		mav.addObject("allDataFieldList", allDataFieldList);
		mav.addObject("dataField", events.toString());
		mav.addObject("productName", item.getLineItemDetail().getDetail().getProductLineItem().getName());
		mav.addObject("enableDialogueMap", enableDependencies);
		mav.addObject("disableDialogueMap", disableDependencies);
		mav.addObject("sortedList", sortedList);
		mav.addObject("productMonthlyPrice", productInfo.getProduct().getPriceInfo().getBaseRecurringPrice());
		mav.addObject("productInstallationPrice", productInfo.getProduct().getPriceInfo().getBaseNonRecurringPrice());
		
		return mav;
	}

	public List<String> generateChildvalues(Map<String, Map<String, List<String>>> enableDependencyMap, String parentNames, int i){
		int j = 1;
		String externalID = "";
		String prefix = "";

		if(parentNames.indexOf(":")>0){
			String[] arr = parentNames.split(":");
			prefix = arr[0];
			externalID = arr[1];
		}
		if(externalID.indexOf("^disclosure") > 0){
			externalID = externalID.substring(0, externalID.indexOf("^disclosure"));
		}
		List<String> enabledDataFieldList = new ArrayList<String>();
		for(Entry<String, Map<String, List<String>>> enableDependencyEntry : enableDependencyMap.entrySet()){
			if(enableDependencyEntry.getKey().equals(externalID)){
				for(Entry<String, List<String>> enableMapEntry : enableDependencyEntry.getValue().entrySet()){
					for(String enableEntry : enableMapEntry.getValue()){
						String value = prefix+"_"+j+":"+enableEntry;
						enabledDataFieldList.add(value);
						enabledDataFieldList.addAll(generateChildvalues(enableDependencyMap, value, j));
						j++;
					}
				}
			}
		}
		return enabledDataFieldList;
	}

	public List<String> buildSortedList(Set<String> unsortedSet, Map<String, Map<String, List<String>>> enableDependencies){
		List<String> sortedList = new ArrayList<String>();
		//String prefix = "";
		String externalID = "";
		for(String parentNames : unsortedSet){
			if(parentNames.indexOf(":")>0){
				String[] arr = parentNames.split(":");
				//	prefix = arr[0];
				externalID = arr[1];
				if(externalID.indexOf("$") > 0){
					for(Entry<String, Map<String, List<String>>> enableMapEntry : enableDependencies.entrySet()){
						Map<String, List<String>> subEnableMap = enableMapEntry.getValue();
						String selectedVal = "";
						String mainString = externalID.substring(0, externalID.indexOf("$"));

						selectedVal = externalID.substring(externalID.indexOf("$") + 1);
						if(mainString.equals(enableMapEntry.getKey()) && subEnableMap.get(selectedVal) != null){
							sortedList.addAll(subEnableMap.get(selectedVal));
						}
					}
				}
			}
		}
		return sortedList;
	}

	public String getProviderExternalIdWithName(OrderType order, long lineItemId, String providerName) {
		String providerExternalId = "";

		if((order != null) && (order.getLineItems() != null)) {
			for(LineItemType lineItem : order.getLineItems().getLineItem()) {
				if(lineItemId == lineItem.getExternalId()) {
					if((lineItem.getLineItemDetail() != null) && 
							(lineItem.getLineItemDetail().getDetail().getProductLineItem() != null)) {
						providerExternalId = lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId();
						providerName = lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getName();
					}
				}
			}
		}
		return providerExternalId;
	}
	
	public List<String> generateReplayChildvalues(Map<String, Map<String, List<String>>> enableDependencyMap, String parentNames){
		String externalID = "";
		String selectedValue = "";
		if(parentNames.indexOf(":")>0){
			String[] arr = parentNames.split(":");
			selectedValue = arr[1];
			externalID = arr[0];
		}
		List<String> replayEnabledValuesList = new ArrayList<String>();
		for(Entry<String, Map<String, List<String>>> enableDependencyEntry : enableDependencyMap.entrySet()){
			if(externalID.contains(enableDependencyEntry.getKey())){
				for(Entry<String, List<String>> enableMapEntry : enableDependencyEntry.getValue().entrySet()){

					if(enableMapEntry.getKey().equals(selectedValue)){
						replayEnabledValuesList.add(enableDependencyEntry.getKey());
						replayEnabledValuesList.addAll(enableMapEntry.getValue());
					}
				}
			}
		}
		return replayEnabledValuesList;
	}
	
	/**
	 * checks if the order is already present in the cache service if the order is present in cache 
	 * it returns the order, else it calls get Order and stores the order in the cache service
	 * 
	 * @param orderID
	 * @return OrderType
	 */
	public OrderType returnOrderType(String orderID){
		
		OrderType order = null;
		order = OrderServiceUI.INSTANCE.getOrder(orderID);
			
		return order;
	}
	
	/**
	 * check whether the sales context is present in the session, if it is present, we return the same,
	 * if the salesContext is not present in the session, we perform a getSalesContext call and return the salesContext
	 * 
	 * @param orderID
	 * @param agentId
	 * @return
	 */
	public SalesContextType returnSalesContext(String orderID, String agentId, HttpSession session){

		SalesContextType context = null;
		if(agentId != null && agentId.trim().length() > 0){
			context = OrderService.INSTANCE.getSalesContext(orderID, agentId);
		}
		else{
			context = OrderService.INSTANCE.getSalesContext(orderID, "default");	
		}
		session.setAttribute(SalesContextFactory.INSTANCE.CONTEXT, context);
		return context;
	}
	
	/**
	 * setting guid as new entity for displaying the guid in the provisioning call
	 * @param guid
	 * @param salesContext
	 * @return
	 */
	public SalesContext addGuidEntityToSalesContext(String guid, SalesContext salesContext ){
		com.AL.xml.di.v4.NameValuePairType nameValuePair = new com.AL.xml.di.v4.NameValuePairType();
		nameValuePair.setName("GUID");
		nameValuePair.setValue(guid);
		List<com.AL.xml.di.v4.NameValuePairType> nameValueTypeList = new ArrayList<com.AL.xml.di.v4.NameValuePairType> ();
		nameValueTypeList.add(nameValuePair);
		com.AL.xml.di.v4.SalesContextEntityType salesContextEntity = new com.AL.xml.di.v4.SalesContextEntityType();
		salesContextEntity.getAttribute().addAll(nameValueTypeList);
		salesContext.getEntity().add(salesContextEntity);
		return salesContext;
	}
	
	public void setOrderDataInQualVOObject(OrderQualVO orderQualVO,OrderType orderType){
		if(orderType != null && orderType.getLineItems().getLineItem()!= null && orderType.getLineItems().getLineItem().size() > 0){
			for(LineItemType liType : orderType.getLineItems().getLineItem()){
				if(liType.getExternalId() == orderQualVO.getLineItemExternalId()){
					orderQualVO.setLineItemNumber(liType.getLineItemNumber());
					orderQualVO.setNewLineItemType(LineItemBuilder.INSTANCE.buildNewLineItem(liType));
					orderQualVO.setLineItemType(liType);
					orderQualVO.setAgentId(orderType.getAgentId());
					orderQualVO.setCustomerExternalId(String.valueOf(orderType.getCustomerInformation().getCustomer().getExternalId()));
				}
			}
		}
	}
}