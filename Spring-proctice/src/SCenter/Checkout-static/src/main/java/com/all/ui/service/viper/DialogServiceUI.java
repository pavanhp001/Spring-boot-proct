package com.AL.ui.service.V;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.AL.ui.builder.DialogueVOBuilder;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.mapper.DialogueMapper;
import com.AL.ui.service.V.impl.DialogCacheService;
import com.AL.ui.util.DialogueUtil;
import com.AL.ui.util.OrderUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.DialogueVO;
import com.AL.ui.vo.OrderQualVO;
import com.AL.V.domain.SalesContext;
import com.AL.V.exception.RecoverableException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.di.v4.DialogueListType;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.NameValuePairType;
import com.AL.xml.di.v4.SalesContextEntityType;
import com.AL.xml.di.v4.DialogueResponseType.Results;
import com.AL.xml.pr.v4.ItemCategory;
import com.AL.xml.pr.v4.ProductInfoType;

public enum DialogServiceUI{

	INSTANCE;
	private static final Logger logger = Logger.getLogger(DialogServiceUI.class);
	public DialogueVO getSampleDialoguesByProductId(String productId){

		DialogueListType dialogues = getStaticSampleDialogues();

		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogues);

		DialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList, new DialogueVO(), productId);

		return dialogueVO;
	}

	/**
	 * builds sales context and does provisioning call and return DialogueVO object
	 * @param productInfo
	 * @param isASISPlan
	 * @param orderQualVO 
	 * @return DialogueVO
	 * @throws Exception 
	 */
	public DialogueVO getDialoguesByProductId(ProductInfoType productInfo, boolean isASISPlan, OrderQualVO orderQualVO) throws Exception{

		DialogueVO dialogueVO = null;
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
			Map<String, Map<String, String>> dataMap = null;


			/**
			 * check whether the parentID is present on the productInfo, if present, we use this parentID for provisioning call
			 * else we use businessParty as parentId for Provisioning Call
			 */

			if(productInfo.getProduct().getProvider().getParent() != null){
				parent = productInfo.getProduct().getProvider().getParent().getExternalId();
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
			String serviceCategory = returnServiceCategory(lstProdCatList);

			/**
			 * creates a Map<String, Map<String, String>> which contains the data for salesFlow and provisioning entities 
			 */
			if(parent != null){
				dataMap = createSalesContext(providerExternalID, parent, marketItem, item, serviceCategory, isASISPlan, orderQualVO); 
			}
			else{
				dataMap = createSalesContext(providerExternalID, providerExternalID, marketItem, item, serviceCategory, isASISPlan, orderQualVO);
			}

			/**
			 * return the salesContext object based on the dataMap inputs
			 */
			SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);

			/**
			 * adding a new entity (GUID) for sales context. this guid is sent as header map during the service call
			 */
			salesContext = OrderUtil.INSTANCE.addGuidEntityToSalesContext(orderQualVO.getGUID(), salesContext);

			/**
			 * check whether the dialogue response is already present in the cache service 
			 * if the dialogue is present, return the dialogue response else, make a provisioning call 
			 * and get the dialogue response
			 */
			dialogResponse = DialogueUtil.returnDialoguesVoObject(salesContext, extId);

		}
		dialogueVO = DialogueUtil.buildDialogueVO(dialogResponse, extId);

		return dialogueVO;
	}

	/**
	 * return serviceCategory based on the product category
	 * @param lstProdCatList
	 * @return
	 */
	private String returnServiceCategory(List<ItemCategory> lstProdCatList) {
		String serviceCategory = "";
		for(ItemCategory prodItemType:lstProdCatList){
			serviceCategory = prodItemType.getValue();

			if(serviceCategory != null){
				switch (categories.valueOf(serviceCategory)){
				case localPhone: serviceCategory = categories.localPhone.value; 
				break ;
				case electricity: serviceCategory = categories.electricity.value; 
				break ;
				case naturalGas: serviceCategory = categories.naturalGas.value; 
				break ;
				case longDistancePhone: serviceCategory = categories.longDistancePhone.value; 
				break ;
				case cableTV: serviceCategory = categories.cableTV.value; 
				break ;
				case highSpeedInternet: serviceCategory = categories.highSpeedInternet.value; 
				break ;
				case washerDryerRental: serviceCategory = categories.washerDryerRental.value; 
				break ;
				case homeSecurity: serviceCategory = categories.homeSecurity.value; 
				break ;
				case water: serviceCategory = categories.water.value; 
				break ;
				case localNewspaper: serviceCategory = categories.localNewspaper.value; 
				break ;
				case wasteRemoval: serviceCategory = categories.wasteRemoval.value; 
				break ;
				case bundles: serviceCategory = categories.bundles.value; 
				break ;
				case personalChecks: serviceCategory = categories.personalChecks.value; 
				break ;
				case applianceProtection: serviceCategory = categories.applianceProtection.value; 
				break ;
				case warranty: serviceCategory = categories.warranty.value; 
				break ;
				case wirelessPhone: serviceCategory = categories.wirelessPhone.value; 
				break ;
				case energyConservation: serviceCategory = categories.energyConservation.value; 
				break ;
				case homeWireProtection: serviceCategory = categories.homeWireProtection.value; 
				break ;
				case satelliteTV: serviceCategory = categories.satelliteTV.value; 
				break ;
				case dialUpInternet: serviceCategory = categories.dialUpInternet.value; 
				break ;
				case nationalNewspaper: serviceCategory = categories.nationalNewspaper.value; 
				break ;
				case offers: serviceCategory = categories.offers.value; 
				break ;
				case secondCall: serviceCategory = categories.secondCall.value; 
				break ;
				case techSupport: serviceCategory = categories.techSupport.value;

				break ;
				}
			}
		}

		return serviceCategory;
	}

	public DialogueVO getDialoguesByProductIdFromCache(String productId){

		DialogueResponseType dialogResponse = DialogCacheService.INSTANCE.getFromCache(productId);
		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogResponse);
		DialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new DialogueVO(),productId);

		return dialogueVO;
	}

	public DialogueListType getStaticSampleDialogues(){
		File file = new File("C:/Users/charter2/Desktop/New folder (2)/02-01-2013/Responses/CHTR-MNNE-TRP_DialogueResponse.xml");
		//		InputStream is = Constants.class.getResourceAsStream(Constants.UTILITY_CHECK_XML);
		String xmlInput = DialogServiceUI.INSTANCE.readFileToString(file);
		logger.info("XML INPUT :::::::::::::::: "+xmlInput);
		Results result = toObject(xmlInput);
		logger.info(result.getDialogueList() != null);
		DialogueListType dialogues = result.getDialogueList();
		logger.info("DIALOGUES :::::::::::::: "+ (dialogues.getDialogue()!= null));
		return dialogues;
	}


	public String readFileToString(File aFileName) {
		StringBuffer sb = new StringBuffer();
		String line = null;

		try {
			FileReader inputFile = new FileReader(aFileName);
			BufferedReader inputBuffer = new BufferedReader(inputFile);

			while ((line = inputBuffer.readLine()) != null)
				sb.append(line);

			inputBuffer.close();
			inputFile.close();

		}
		// Catches any error conditions
		catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public Results toObject(String xmlInput)  {
		Results result = null;
		StringReader sr = null;
		try {
			JAXBContext orderTypeJxbContext = JAXBContext.newInstance(Results.class);
			sr = new StringReader( xmlInput );
			Unmarshaller unmarshaller = orderTypeJxbContext.createUnmarshaller();
			JAXBElement<Results> b = unmarshaller.unmarshal(new StreamSource(sr),	Results.class);
			result = b.getValue();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sr.close();
		}
		return result;
	}

	/**
	 * builds data map for getting sales context
	 * 
	 * @param businessParty
	 * @param parent
	 * @param marketItem
	 * @param item
	 * @param serviceCategory
	 * @param isASISPlan
	 * @param string 
	 * @return
	 */
	public Map<String, Map<String, String>> createSalesContext(String businessParty, 
			String parent, 
			String marketItem, 
			String item,
			String serviceCategory, boolean isASISPlan,OrderQualVO orderQualVO) {
		Map<String, Map<String, String>> salesContextData = new HashMap<String, Map<String, String>>();

		Map<String, String> salesFlow = new HashMap<String, String>();
		if(serviceCategory.equals("11001")){
			salesFlow.put("salesFlow.dialogueType", "UtilityOffer");
		}
		else{
			salesFlow.put("salesFlow.dialogueType", "Provisioning");
		}
		salesFlow.put("GUID", orderQualVO.getGUID());
		if(orderQualVO.getNameValue()!= null && orderQualVO.getNameValue().get("PriorEnrollSurge") != null){
			salesFlow.put("salesFlow.priorEnrollSurge", orderQualVO.getNameValue().get("PriorEnrollSurge"));
		}
		salesContextData.put("salesFlow", salesFlow);

		Map<String, String> provisioning = new HashMap<String, String>();
		provisioning.put("provisioning.businessParty", businessParty);
		provisioning.put("provisioning.businessParty.parent", parent);
		provisioning.put("provisioning.marketItem", marketItem);
		provisioning.put("provisioning.item", item);
		provisioning.put("provisioning.serviceCategory", serviceCategory);
		if(isASISPlan){
			provisioning.put("provisioning.item.asis", "asis");
		}
		salesContextData.put("provisioning", provisioning);

		return salesContextData;
	}

	public enum categories{

		localPhone("23"), electricity("24"), naturalGas("25"), longDistancePhone("26"), cableTV("27"), 
		highSpeedInternet("29"), washerDryerRental("32"), homeSecurity("33"), water("35"), 
		localNewspaper("10232"), wasteRemoval("10271"), bundles("10311"), personalChecks("10351"), 
		applianceProtection("10371"), warranty("10432"), wirelessPhone("10531"), energyConservation("10552"), 
		homeWireProtection("10553"), satelliteTV("10574"), dialUpInternet("10575"), nationalNewspaper("10576"), 
		offers("11001"), secondCall("11023"), techSupport("11084");

		private String value;

		private categories(String value){
			this.value = value;
		}
	}

	public Map<String, Map<String, String>> getData(String externalId) {
		Map<String, Map<String, String>> context = new HashMap<String, Map<String, String>>();
		Map<String, String> salesFlow = new HashMap<String, String>();
		salesFlow.put("salesFlow.dialogueType", "Provisioning");

		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("orderSource.channel", "CallCenter");

		Map<String, String> provisioning = new HashMap<String, String>();
		provisioning.put("provisioning.marketItem", externalId);
		provisioning.put("provisioning.businessParty", "24699452");
		provisioning.put("provisioning.businessParty.parent", "100");
		provisioning.put("provisioning.item", "");
		provisioning.put("provisioning.serviceCategory", "10311");
		//provisioning.put("provisioning.marketItem", "TWC-WCAR-CI");

		context.put("salesFlow", salesFlow);
		context.put("orderSource", orderSource);
		context.put("provisioning", provisioning);
		return context;
	}

	public DialogueResponseType getDialoguesByContext(Map<String, Map<String, String>> dynamicFlowContext) throws RecoverableException, UnRecoverableException 
	{
		SalesContext salesContext = getSalesContextForOrderRecap(dynamicFlowContext);
		DialogueResponseType dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);	
		return dialogResponse;
	}

	private SalesContext getSalesContextForOrderRecap(
			Map<String, Map<String, String>> dynamicFlowContext) 
	{
		SalesContext salesContext = new SalesContext();
		if (dynamicFlowContext == null) 
		{
			return salesContext;
		}
		
		for (String entityName : dynamicFlowContext.keySet())
		{
			SalesContextEntityType entity = new SalesContextEntityType();
			salesContext.getEntity().add(entity);
			entity.setName(entityName);

			List<NameValuePairType> nvPairList = entity.getAttribute();
			Map<String, String> attrMap = dynamicFlowContext.get(entityName);

			for (String keyName : attrMap.keySet()) 
			{
				String value = attrMap.get(keyName);

				NameValuePairType nvPairType = new NameValuePairType();
				nvPairList.add(nvPairType);
				nvPairType.setName(keyName);
				nvPairType.setValue(value);
			}
		}
		return salesContext;
	}
	
	
}
