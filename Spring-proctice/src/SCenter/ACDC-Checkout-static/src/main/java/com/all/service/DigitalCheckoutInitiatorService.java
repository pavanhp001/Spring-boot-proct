package com.AL.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.DataDialogueVO;
import com.AL.ui.DataGroupVO;
import com.AL.ui.InstallationVO;
import com.AL.ui.ProductVO;
import com.AL.ui.QualificationVO;
import com.AL.ui.builder.LineItemBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.DigitalCacheKeys;
import com.AL.ui.factory.CKOLineItemFactory;
import com.AL.ui.factory.DialogueDataFeildFactory;
import com.AL.ui.factory.PromotionHandlerFactory;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.ProductServiceUI;
import com.AL.ui.service.V.impl.CKOCacheService;
import com.AL.ui.service.V.impl.DialogCacheService;
import com.AL.ui.service.V.impl.StaticCKOCacheManager;
import com.AL.ui.service.workflow.Intent;
import com.AL.ui.service.workflow.ViewFlow;
import com.AL.ui.util.DialogueUtil;
import com.AL.ui.util.OrderUtil;
import com.AL.ui.util.SalesContextUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.DataFeildFeatureVO;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.PromotionVO;
import com.AL.ui.vo.SessionVO;
import com.AL.V.domain.SalesContext;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.di.v4.DataFieldRefType;
import com.AL.xml.di.v4.DataGroupType;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DialogueType;
import com.AL.xml.di.v4.TagType;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.ItemCategory;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;


@Service
public class DigitalCKOInitiatorService {

	private static final Logger logger = Logger.getLogger(DigitalCKOInitiatorService.class);

	//private static final JaxbUtil<EnterpriseResponseDocumentType> util = new JaxbUtil<EnterpriseResponseDocumentType>();


	/** 
	 * @param orderQualVO
	 * @param request
	 * @param intent
	 * @param CKOVo
	 * @param sessionVO
	 * @return
	 */
	public ViewFlow digitalCKOInitiatService(final OrderQualVO orderQualVO, HttpServletRequest request, final Intent intent, final CKOInitialVo CKOVo,SessionVO sessionVO,ProductInfoType productInfo)throws Exception{
		logger.info("start_digitalCKO_InitiatService");
		ModelAndView mav = new ModelAndView("product_Json");
		ViewFlow viewFlow = ViewFlow.create(mav, null);

		ProductVO productVO = new ProductVO();
		QualificationVO  qualificationVO = new QualificationVO();
		InstallationVO installationVO = new InstallationVO();
		OrderType orderType = null;
		try{
			/*String productExternalId = orderQualVO.getProductExternalId();
			String providerExternalID = orderQualVO.getProviderExternalId();*/
			String guid = null;
			LineItemType liType = null;
			String providerName = "";
			String providerID = "";
			OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(orderQualVO.getOrderId(), Boolean.TRUE);
			if(orderManagementRequestResponse.getResponse().getSalesContext() != null){
				SalesContextFactory.INSTANCE.setContextInSession(request.getSession(), orderManagementRequestResponse.getResponse().getSalesContext());
				guid = OrderUtil.INSTANCE.getGUID(orderManagementRequestResponse.getResponse().getSalesContext());
			}
			if(orderManagementRequestResponse.getResponse().getOrderInfo() != null){
				if ((orderManagementRequestResponse.getResponse().getOrderInfo() != null) && (orderManagementRequestResponse.getResponse().getOrderInfo().size() > 0)) {
					orderType = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
				}
			}

			if(orderType.getLineItems().getLineItem()!= null && orderType.getLineItems().getLineItem().size() > 0){
				liType = LineItemBuilder.INSTANCE.returnLineItemByLineItemID(orderType,orderQualVO.getLineItemExternalId());
			}
			//String  readXml = readFile("D:\\Chrarter Dialogue Resp.xml");
			//logger.info(readXml);
			//String responseExtracted = extract(readXml);
			//logger.info(responseExtracted);
			//ProductInfoType productInfo = ProductServiceUI.INSTANCE.getProduct(productExternalId, guid, providerExternalID);
			if(productInfo.getProduct() != null && productInfo.getProduct().getProvider()!= null){
				if(productInfo.getProduct().getProvider().getParent() != null ){
					providerName = productInfo.getProduct().getProvider().getParent().getName();
					providerID = productInfo.getProduct().getProvider().getParent().getExternalId();
				}else{
					providerName = productInfo.getProduct().getProvider().getName();
					providerID = productInfo.getProduct().getProvider().getExternalId();
				}
			}

			//set values in orderQualVO
			orderQualVO.setBaseRecurringPrice(String.valueOf(productInfo.getProduct().getPriceInfo().getBaseRecurringPrice()));
			orderQualVO.setBaseNonRecurringPrice(String.valueOf(productInfo.getProduct().getPriceInfo().getBaseNonRecurringPrice()));
			orderQualVO.setGUID(guid);
			if(liType != null){
				orderQualVO.setLineItemNumber(liType.getLineItemNumber());
				orderQualVO.setNewLineItemType(LineItemBuilder.INSTANCE.buildNewLineItem(liType));
				orderQualVO.setLineItemType(liType);
				orderQualVO.setAgentId(orderType.getAgentId());
				orderQualVO.setCustomerExternalId(String.valueOf(orderType.getCustomerInformation().getCustomer().getExternalId()));
				request.getSession().setAttribute("lineItemExID", liType.getExternalId());
				request.getSession().setAttribute("orderID", orderQualVO.getOrderId());
				request.getSession().setAttribute("lineItemNumber", liType.getLineItemNumber());
				request.getSession().setAttribute("product_base_recurring_price",liType.getLineItemPriceInfo().getBaseRecurringPrice());
				request.getSession().setAttribute("product_base_nonrecurring_price",liType.getLineItemPriceInfo().getBaseNonRecurringPrice());
			}
			Map<String,Long>  lineItemTypePromotionMap = PromotionHandlerFactory.INSTANCE.getLineItemTypePromotionMap(orderType,orderQualVO);
			List<PromotionVO> promotionVOList = PromotionHandlerFactory.INSTANCE.buildPromotioVOList(productInfo,lineItemTypePromotionMap);

			List<FeatureType>  featurList = productInfo.getProductDetails().getFeature();
			List<FeatureGroupType>  featurGroupList = productInfo.getProductDetails().getFeatureGroup();
			//EnterpriseResponseDocumentType dialog = util.toObject(responseExtracted, "com.AL.xml.di.v4").getValue();
			List<DataDialogueVO> productDataDialogueVOList = new ArrayList<DataDialogueVO>();
			List<DataDialogueVO> qualificationDataDialogueVOList = new ArrayList<DataDialogueVO>();
			List<DataDialogueVO> installationDataDialogueVOList = new ArrayList<DataDialogueVO>();
			DialogueResponseType dialogResponse = (DialogueResponseType) getDialoguesByProductId(productInfo,false,orderQualVO);

			logger.info("dialogResponse"+dialogResponse);

			Map<String,Map<String,List<DataFieldRefType>>> productDataFieldMatrixMap = new HashMap<String,Map<String,List<DataFieldRefType>>>();
			Map<String,Map<String,List<DataFieldRefType>>> qualificationDataFieldMatrixMap = new HashMap<String,Map<String,List<DataFieldRefType>>>();
			Map<String,Map<String,List<DataFieldRefType>>> installationDataFieldMatrixMap = new HashMap<String,Map<String,List<DataFieldRefType>>>();
			Map<String,DataDialogueVO>  combinedDataGroupMap = new HashMap<String,DataDialogueVO>();
			if(dialogResponse != null && dialogResponse.getResults() != null
					&& dialogResponse.getResults().getDialogueList() != null
					&& dialogResponse.getResults().getDialogueList().getDialogue() != null){
				for (DialogueType dialogueType : dialogResponse.getResults().getDialogueList().getDialogue()) {
					if (dialogueType != null && dialogueType.getTags() != null && dialogueType.getTags().getTag() != null){
						for(TagType tagType:dialogueType.getTags().getTag()){
							if("page".equalsIgnoreCase(tagType.getName())){
								if("Qualification".equalsIgnoreCase(tagType.getValue())){
									com.AL.xml.v4.CustAddress cust = CKOLineItemFactory.INSTANCE.getAddress(orderType.getCustomerInformation().getCustomer(),
											com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
									
									String serviceAddress=cust.getAddress().getAddressBlock();
									logger.info("serviceAddress="+serviceAddress);
									sessionVO.getData().put("serviceAddress", serviceAddress);
									//request.getSession().setAttribute("serviceAddress", serviceAddress);
									mav.addObject("serviceAddress",serviceAddress);
									DataDialogueVO dialogueVO = buildDataDialogueVO(dialogueType,qualificationDataFieldMatrixMap,providerName,orderQualVO,serviceAddress);
									DataGroupVO dataGroupVO = new DataGroupVO();
									List<DataFeildFeatureVO> dataFeildCustomerVOList = DialogueDataFeildFactory.INSTANCE.buildCustomizeCustomerInformation(Constants.CUSTOMER_CUSTOMIZE_LIST,orderType,orderQualVO);
									dataGroupVO.setSubTitle("Contact Information");
									dataGroupVO.setDataFeildList(dataFeildCustomerVOList);
									dialogueVO.getDataGroupList().add(dataGroupVO);
									qualificationDataDialogueVOList.add(dialogueVO);
								}else if("Installation".equalsIgnoreCase(tagType.getValue())){
									DataDialogueVO dialogueVO =  buildDataDialogueVO(dialogueType,installationDataFieldMatrixMap,providerName,orderQualVO);
									installationDataDialogueVOList.add(dialogueVO);
								}else if("Customization".equalsIgnoreCase(tagType.getValue())){
									for(TagType tagTypeValue:dialogueType.getTags().getTag()){
										if("section".equalsIgnoreCase(tagTypeValue.getName())){
											// build combinedDataGroupMap
											DataDialogueVO dialogueVO = DialogueDataFeildFactory.INSTANCE.buildFeaturDialogueVO(dialogueType,featurList,featurGroupList,tagTypeValue.getValue(),productDataFieldMatrixMap,orderQualVO);
											DialogueDataFeildFactory.INSTANCE.combinedDataGroupSection(combinedDataGroupMap,dialogueVO,tagTypeValue.getValue());
										}
									}
								}
							}
						}
					}
				}
			}
			// build productDataVO for combinedDataGroupSection
			for(Entry<String, DataDialogueVO> dialogueTypeEntry:new TreeMap<String,DataDialogueVO>(combinedDataGroupMap).entrySet()){
				productDataDialogueVOList.add(dialogueTypeEntry.getValue());
			}
			for(String paramVal:CKOVo.getParams()){
				String paramArray[] = paramVal.split("=");
				if(Constants.CHANNEL_TYPE.equalsIgnoreCase(paramArray[0]))
				{
					sessionVO.put(Constants.CHANNEL_TYPE, paramArray[1]);
				}
				else if(Constants.CHANNEL_CSS.equalsIgnoreCase(paramArray[0]))
				{
					sessionVO.put(Constants.CHANNEL_CSS, paramArray[1]);
				}
			}
			productVO.setDialogueTypeList(productDataDialogueVOList);
			productVO.setPromotionVOList(promotionVOList);
			productVO.setProductName(providerName);
			productVO.setProviderID(providerID);
			productVO.setDataFieldMatrixMap(productDataFieldMatrixMap);

			qualificationVO.setDialogueTypeList(qualificationDataDialogueVOList);
			qualificationVO.setProductName(providerName);
			qualificationVO.setProviderID(providerID);
			qualificationVO.setShowSmsOptIn(orderType.getCustomerInformation().getCustomer().isPhoneContactOptIn());
			qualificationVO.setShowOffersOptIn(orderType.getCustomerInformation().getCustomer().isMarketingOptIn());
			qualificationVO.setDataFieldMatrixMap(qualificationDataFieldMatrixMap);
			//if(installationDataDialogueVOList != null && !installationDataDialogueVOList.isEmpty()){
				installationVO.setDialogueTypeList(installationDataDialogueVOList);
				installationVO.setProductName(providerName);
				installationVO.setProviderID(providerID);
				installationVO.setDataFieldMatrixMap(installationDataFieldMatrixMap);
			//}
			
			StaticCKOCacheManager.INSTANCE.store(productVO, DigitalCacheKeys.ProductVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
			StaticCKOCacheManager.INSTANCE.store(qualificationVO, DigitalCacheKeys.QualificationVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
			StaticCKOCacheManager.INSTANCE.store(installationVO, DigitalCacheKeys.InstallationVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());

			/*sessionVO.put(SessionKeys.initiator.name(),CKOVo);
			sessionVO.put(SessionKeys.orderQualVo.name(), orderQualVO);*/

			logger.info("sessionId=" + request.getSession().getId());
			logger.info("sessionID from sessionVO=" + sessionVO.getSessionId());
			CKOCacheService.INSTANCE.store(sessionVO);
			if(Utils.isEmpty(featurList) || Utils.isEmpty(featurGroupList) || Utils.isEmpty(promotionVOList)){
				mav.addObject("customerInformation", true);
				mav.addObject("qualificationVO", Utils.convertJSONString(qualificationVO));
			}else{
				mav.addObject("productVO", Utils.convertJSONString(productVO));
			}
			logger.info("End_digitalCKO_InitiatService");
		}catch(Exception e){
			logger.error("error_while_excute_digitalCKOInitiatService",e);
			throw new Exception(e.getMessage());
		}
		return viewFlow;
	}

	/**  This method is used to build DataDialogueVO based on dialogue service response
	 * @param dialogueType
	 * @param dataFieldMatrixMap
	 * @return
	 * @throws Exception
	 */
	private DataDialogueVO buildDataDialogueVO(DialogueType dialogueType,Map<String,Map<String,List<DataFieldRefType>>> dataFieldMatrixMap,String providerName,OrderQualVO orderQualVO,String... serviceAddress ) throws Exception {
		List<DataGroupVO> dataGroupVOList = new ArrayList<DataGroupVO>();
		DataDialogueVO dialogueVO = new DataDialogueVO();
		if(dialogueType.getDataGroupList()!= null && dialogueType.getDataGroupList().getDataGroup() != null
				&& dialogueType.getDataGroupList().getDataGroup() != null){
			for(DataGroupType dataGroupType:dialogueType.getDataGroupList().getDataGroup()){
				if(dataGroupType.getDataFieldList() != null 
						&& dataGroupType.getDataFieldList().getDataField() != null 
						&& !dataGroupType.getDataFieldList().getDataField().isEmpty()){
					DataGroupVO dataGroupVO = new DataGroupVO();
					List<DataFeildFeatureVO> dataFeildFeatureVOList= DialogueDataFeildFactory.INSTANCE.buildDataFeildData(dataGroupType,providerName,orderQualVO,serviceAddress);
					Map<String,Map<String,List<DataFieldRefType>>>  allDataMap = DialogueDataFeildFactory.INSTANCE.buildEnableAndDisableDataGroup(dataGroupType);
					dataFieldMatrixMap.putAll(allDataMap);
					dataGroupVO.setSubTitle(dataGroupType.getDisplayName());
					dataGroupVO.setDataFeildList(dataFeildFeatureVOList);
					dataGroupVOList.add(dataGroupVO);
				}
			}
		}
		dialogueVO.setDataGroupList(dataGroupVOList);
		dialogueVO.setTitle(dialogueType.getName());
		return dialogueVO;
	}

	/**
	 * @param path
	 * @return
	 */
	/*public String readFile(String path){
		StringBuilder sb = new StringBuilder();
		try{ 
			BufferedReader br = new BufferedReader(new FileReader(path));
			String str = null; 
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}*/


	/**
	 * @param orderRR
	 * @return
	 *//*
	public String extract(String orderRR) {

		int indexStart = orderRR.indexOf("<v4:dialogueEnterpriseResponse>")
				+ "<ac:dialogueEnterpriseResponse>".length();
		int indexEnd = orderRR.indexOf("</v4:dialogueEnterpriseResponse>",
				indexStart + 1);

		if ((indexStart != -1) && (indexEnd != -1)) {

			orderRR = "<v4:dialogueEnterpriseResponse xmlns:ac=\"http://xml.AL.com/v4\">"
					+ orderRR.substring(indexStart, indexEnd)
					+ "</v4:dialogueEnterpriseResponse>";
		}

		return StringUtils.replace(orderRR, "v4:", "ac:");

	}*/


	/**
	 * builds sales context and does provisioning call and return DialogueVO object
	 * @param productInfo
	 * @param isASISPlan
	 * @param orderQualVO 
	 * @return DialogueVO
	 * @throws Exception 
	 */
	public DialogueResponseType getDialoguesByProductId(ProductInfoType productInfo, boolean isASISPlan, OrderQualVO orderQualVO) throws Exception{

		String extId = productInfo.getExternalId();
		DialogueResponseType dialogResponse = DialogCacheService.INSTANCE.getFromCache(orderQualVO.getProductExternalId());

		/**
		 * get providerExternalId from orderQualVo Object, if the providerExternalId is not present on the orderQualVo Object, 
		 * get the providerExternalId from productInfo Object
		 */

		String providerExternalID = orderQualVO.getProviderExternalId(); 
		if(Utils.isBlank(providerExternalID)){
			providerExternalID = productInfo.getProduct().getProvider().getExternalId();
			orderQualVO.setProviderExternalId(providerExternalID);
		}

		if(dialogResponse == null){
			String parent = null;
			String providerExtId = null;
			Map<String, Map<String, String>> dataMap = null;


			/**
			 * check whether the parentID is present on the productInfo, if present, we use this parentID for provisioning call
			 * else we use businessParty as parentId for Provisioning Call
			 */

			if(productInfo.getProduct().getProvider().getParent() != null){
				parent = productInfo.getProduct().getProvider().getParent().getExternalId();
			}
			
			if(productInfo.getProduct().getProvider().getExternalId() != null){
				providerExtId = productInfo.getProduct().getProvider().getExternalId();
			}

			String marketItem = productInfo.getExternalId();
			String item = productInfo.getProduct().getItemExternalId(); 

			/**
			 * return the productCategoryList for getting category of the product
			 */
			List<ItemCategory> lstProdCatList = productInfo.getProduct().getProductCategoryList()
			.getProductCategory();

			/**
			 * iterate over the product category list and return the value of the particular service category
			 */
			String serviceCategory = DialogServiceUI.INSTANCE.returnServiceCategory(lstProdCatList);

			/**
			 * creates a Map<String, Map<String, String>> which contains the data for salesFlow and provisioning entities 
			 */
			if(parent != null){
				
				dataMap =  DialogServiceUI.INSTANCE.createSalesContext(providerExternalID, parent, marketItem, item, serviceCategory, isASISPlan, orderQualVO.getGUID(), providerExtId); 
			}
			else{
				dataMap = DialogServiceUI.INSTANCE.createSalesContext(providerExternalID, providerExternalID, marketItem, item, serviceCategory, isASISPlan, orderQualVO.getGUID(), providerExtId);
			}

			/**
			 * return the salesContext object based on the dataMap inputs
			 */
			SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);

			/**
			 * adding a new entity (GUID) for sales context. this guid is sent as header map during the service call
			 */
			salesContext = OrderUtil.INSTANCE.addGuidEntityToSalesContext(orderQualVO.getGUID(), salesContext);
			Map<String,String> attributeMap = new HashMap<String, String>();
			attributeMap.put("orderSource.channel", "2");
			salesContext.getEntity().add(SalesContextUtil.buidlSalesContextEntity("orderSource", attributeMap));
			/**
			 * check whether the dialogue response is already present in the cache service 
			 * if the dialogue is present, return the dialogue response else, make a provisioning call 
			 * and get the dialogue response
			 */
			dialogResponse = DialogueUtil.returnDialoguesVoObject(salesContext, extId);

		}
		return dialogResponse;
	}


	public ProductVO getCacheVOObjects(OrderQualVO orderQualObj) {
		return (ProductVO)StaticCKOCacheManager.INSTANCE.getObjectFromCache(DigitalCacheKeys.ProductVO+"_"+orderQualObj.getOrderId()+"_"+orderQualObj.getLineItemExternalId());
	}

	public void updateOrderQualVO(HttpServletRequest request, OrderQualVO orderQualVO) {
		String productExternalId = orderQualVO.getProductExternalId();
		String providerExternalID = orderQualVO.getProviderExternalId();
		String guid = "";
		LineItemType liType = null;
		OrderType orderType = null;
		OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(orderQualVO.getOrderId(), Boolean.TRUE);
		if(orderManagementRequestResponse.getResponse().getSalesContext() != null){
			SalesContextFactory.INSTANCE.setContextInSession(request.getSession(), orderManagementRequestResponse.getResponse().getSalesContext());
			orderQualVO.setGUID(OrderUtil.INSTANCE.getGUID(orderManagementRequestResponse.getResponse().getSalesContext()));
			guid = OrderUtil.INSTANCE.getGUID(orderManagementRequestResponse.getResponse().getSalesContext());
		}
		if(orderManagementRequestResponse.getResponse().getOrderInfo() != null){
			if ((orderManagementRequestResponse.getResponse().getOrderInfo() != null) && (orderManagementRequestResponse.getResponse().getOrderInfo().size() > 0)) {
				orderType = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
			}
		}
		if(orderType != null && orderType.getLineItems().getLineItem()!= null && orderType.getLineItems().getLineItem().size() > 0){
			liType = LineItemBuilder.INSTANCE.returnLineItemByLineItemID(orderType,orderQualVO.getLineItemExternalId());
		}

		ProductInfoType productInfo = ProductServiceUI.INSTANCE.getProduct(productExternalId, guid, providerExternalID);	
		//set values in orderQualVO
		orderQualVO.setBaseRecurringPrice(String.valueOf(productInfo.getProduct().getPriceInfo().getBaseRecurringPrice()));
		orderQualVO.setBaseNonRecurringPrice(String.valueOf(productInfo.getProduct().getPriceInfo().getBaseNonRecurringPrice()));
		orderQualVO.setGUID(guid);
		if(liType != null){
			orderQualVO.setLineItemNumber(liType.getLineItemNumber());
			orderQualVO.setNewLineItemType(LineItemBuilder.INSTANCE.buildNewLineItem(liType));
			orderQualVO.setLineItemType(liType);
			orderQualVO.setAgentId(orderType.getAgentId());
			orderQualVO.setCustomerExternalId(String.valueOf(orderType.getCustomerInformation().getCustomer().getExternalId()));
		}
	}
}
