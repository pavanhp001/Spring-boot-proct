package com.AL.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.html.Fieldset;
import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.managers.RESTClientForDrupal;
import com.AL.productResults.util.Utils;
import com.AL.productResults.vo.ProductSearchIface;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.roundrobin.rr.client.RoundRobinService;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.builder.LineItemStatusBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.dao.OperatingCompanyDao;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.SalesDialogueFactory;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.service.V.AddressService;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.ProductService;
import com.AL.ui.util.DialogueUtil;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.WarmTransferHtmlFactory;
import com.AL.ui.vo.Address;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.domain.SalesContext;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.CustomerFactory;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.cm.v4.CustomerContextType;
import com.AL.xml.cm.v4.ObjectFactory;
import com.AL.xml.cm.v4.RoleType;
import com.AL.xml.cm.v4.CustAddress.AddressRoles;
import com.AL.xml.di.v4.DataFieldMatrixType;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DataGroupType;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DialogueType;
import com.AL.xml.pr.v4.AddressType;
import com.AL.xml.pr.v4.CapabilityType;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.pr.v4.ProductResponseType;
import com.AL.xml.pr.v4.ProviderResults;
import com.AL.xml.pr.v4.ProviderSourceBaseType;
import com.AL.xml.pr.v4.ProviderSourceType;
import com.AL.xml.pr.v4.ProviderType;
import com.AL.xml.pr.v4.ProductRequestType.ProviderList;
import com.AL.xml.pr.v4.ProviderType.CapabilityList;
import com.AL.xml.se.v4.SalesContextEntityType;
import com.AL.xml.se.v4.ServiceabilityEnterpriseResponse;
import com.AL.xml.se.v4.ServiceabilityResponse2;
import com.AL.xml.v4.DialogValueType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemDetailType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemStatusType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.LinkableType;
import com.AL.xml.v4.OrderLineItemDetailTypeType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SelectedDialogsType;
import com.AL.xml.v4.ServiceType;

@Controller("SupplierController")
public class SupplierController {

	@Autowired
	private OperatingCompanyDao operatingCompanyDao;

	private static final Logger logger = Logger
			.getLogger(SupplierController.class);

	private static final String MEMBERTYPE = "SC PA";

	private static final Double OFFER_RATE = 0.93;

	private final static String OFFEREDMEMBERID = "<offeredMemberId>";
	private final static String OFFERTRACKID = "<offerTrackId>";
	private final static String OFFEREDMEMBERIDEND = "</offeredMemberId>";
	private final static String OFFERTRACKIDEND = "</offerTrackId>";


	public Event showSupplierData(RequestContext request)
			throws UnRecoverableException {
		HttpServletRequest httpRequest = (HttpServletRequest) request
				.getExternalContext().getNativeRequest();
		StopWatch timer = new StopWatch();
		long startTimer = 0;
		timer.start();
		try {
			logger.info("showSupplierData_begin");
			HttpSession session = httpRequest.getSession();
			SalesCenterVO salesCenterVo = (SalesCenterVO) httpRequest.getSession().getAttribute("salescontext");
			
			String operatingCompanyCode = salesCenterVo.getValueByName("simpleChoice.operatingCompanyCode");
			logger.info("operatingCompanyCode=" + operatingCompanyCode);
			/*if( !Utils.isBlank(operatingCompanyCode) )
			{
				String operatingCompanyName = operatingCompanyDao.getOperatingCompanyName(operatingCompanyCode);
				salesCenterVo.setValueByName("simpleChoice.operatingCompany",operatingCompanyName);
			}*/
			
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>) httpRequest.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			dynamicFlow.put("dynamicFlow.page", "SupplierSelection");
			dynamicFlow.put("GUID", salesCenterVo.getValueByName("GUID"));
			
			List<String> supplierList = new ArrayList<String>();
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");

			if(!dynamicFlow.get("dynamicFlow.flowType").contains("nonMovers") && !dynamicFlow.get("dynamicFlow.flowType").contains("peco") )
			{
				supplierList.addAll((List<String>) productResultManager.getSuppliersList());
				if(supplierList.isEmpty())
				{
					request.getFlowScope().put("isProductServiceCallCompleted","false");
				}
				logger.info("supplierList_for_Movers_From_ResultManager=" + supplierList);
			}
			else
			{
				if(dynamicFlow.get("dynamicFlow.flowType").contains("peco")){
					if(dynamicFlow.get("dynamicFlow.flowType").contains("nonMovers")) {
						
						getSupplierList(httpRequest,supplierList,productResultManager);	
					}
					if(supplierList != null && supplierList.isEmpty()){
						for (ProductSummaryVO productSummaryVO : productResultManager.getPecoOffersMap().get("PECO")) {
							supplierList.add(productSummaryVO.getName());
						}
						productResultManager.setSuppliersList(supplierList);
					}
					logger.info("Inside Peco");
					logger.info("productResultManager.getPecoOffersMap()="+productResultManager.getPecoOffersMap());
					String customerType = (String)httpRequest.getSession().getAttribute("customerType");
					Map<String, List<String>> pecoSuppliersMap = dividedSuppliersBasedonMetaData(productResultManager.getPecoOffersMap());
					supplierList = pecoSuppliersMap.get(customerType.toUpperCase());
					logger.info("pecoSuppliersMap="+pecoSuppliersMap);
					session.setAttribute("pecoSuppliersMap", pecoSuppliersMap);
				}
				else
				{
					supplierList = getSupplierList(httpRequest,supplierList,productResultManager);	
				}
				logger.info("supplierList_Nonmovers=" + supplierList);
			}
			
			if (supplierList.isEmpty()) 
			{
				request.getFlowScope().put("isProductServiceCallCompleted","false");
			}

			httpRequest.setAttribute("supplier_list", supplierList);
			httpRequest.getSession().setAttribute("supplier_list", supplierList);

			if (!Utils.isBlank(salesCenterVo.getValueByName("simpleChoice.currentRate")))
			{
				String defaultServiceRate = salesCenterVo.getValueByName("simpleChoice.currentRate");
				Double defaultServiceRateVal = Double.valueOf(defaultServiceRate);
				Double standardOfferVal = defaultServiceRateVal * OFFER_RATE * 100;
				DecimalFormat df = new DecimalFormat("#.0000");
				salesCenterVo.setValueByName("simpleChoice.standardOffer", String.valueOf(df.format(standardOfferVal)));
				salesCenterVo.setValueByName("simpleChoice.currentRate", df.format(defaultServiceRateVal * 100));
			}

			startTimer = timer.getTime();
			boolean isDialogueFromDialogueService = true;
			if (salesCenterVo.getValueByName("drupalContentUrl") != null 
					&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null ){
				isDialogueFromDialogueService = false;

				String dialoguesFromDrupal = DialogServiceUI.INSTANCE.getDialoguesByContextForDrupal(contextMap,salesCenterVo.getValueByName("ordersource.programId"));
	 			if (Utils.isBlank(dialoguesFromDrupal)){
	 				dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(DialogServiceUI.INSTANCE.generateDialogueCacheKeyForDrupal(contextMap, salesCenterVo.getValueByName("ordersource.programId"))
							, salesCenterVo.getValueByName("drupalContentUrl"));	
		 			if (Utils.isBlank(dialoguesFromDrupal)){
		 				dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(DialogServiceUI.INSTANCE.generateDialogueBackupDefaultUserGroupDrupal(contextMap, salesCenterVo.getValueByName("ordersource.programId"))
								, salesCenterVo.getValueByName("drupalContentUrl"));	
		 				DialogServiceUI.INSTANCE.storeDialoguesByContextForDrupalDefaultUserGroup(contextMap,dialoguesFromDrupal);
		 			} else {
		 				DialogServiceUI.INSTANCE.storeDialoguesByContextForDrupal(contextMap,dialoguesFromDrupal,salesCenterVo.getValueByName("ordersource.programId"));
		 			}
	 			}
				
	 			if (Utils.isBlank(dialoguesFromDrupal)){
					generateDialoguesFromService(contextMap,salesCenterVo,request);
					isDialogueFromDialogueService = true;
				}
				else{
					String allDependencyFields = RESTClientForDrupal.INSTANCE.generateDialoguesAll(dialoguesFromDrupal);
					String enableDependencyFields = RESTClientForDrupal.INSTANCE.generateDialoguesEnable(dialoguesFromDrupal);
					String disableDependencyFields = RESTClientForDrupal.INSTANCE.generateDialoguesDisable(dialoguesFromDrupal);
					if (Utils.isBlank(allDependencyFields)|| Utils.isBlank(enableDependencyFields)|| Utils.isBlank(disableDependencyFields)){
						generateDialoguesFromService(contextMap,salesCenterVo,request);
						isDialogueFromDialogueService = true;
					}else{
						StringBuffer finalOptions = new StringBuffer();
						for (String supplier : supplierList){
							finalOptions.append("<option value=\"");
							finalOptions.append(supplier);
							finalOptions.append("\">");
							finalOptions.append(supplier);
							finalOptions.append("</option>");
						}
						logger.info("finalOptions= "+finalOptions.toString());
						dialoguesFromDrupal = dialoguesFromDrupal.replace("{allSuppliers}", finalOptions.toString());

						allDependencyFields = allDependencyFields.replace("AllDependencyMap=", "");
						enableDependencyFields = enableDependencyFields.replace("EnableDependencyMap=", "");
						disableDependencyFields = disableDependencyFields.replace("DisableDependencyMap=", "");

						//dialoguesFromDrupal = salesCenterVo.replaceNamesWithValues(dialoguesFromDrupal);

						request.getFlowScope().put("dataField" , 
								StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(SalesUtil.INSTANCE.escapeSpecialCharacters(dialoguesFromDrupal))));

						allDependencyFields = allDependencyFields.replace("[", "");
						allDependencyFields = allDependencyFields.replace("]", "");

						List<String> allDataFieldList = new ArrayList<String>();
						String dataFieldExtIds[] =  allDependencyFields.split(",");
						for(String dataFieldExtId : dataFieldExtIds )
						{
							allDataFieldList.add(dataFieldExtId.trim());
						}
						logger.info("allDataFieldList="+allDataFieldList.size());
						logger.info("enableDependencyFields="+enableDependencyFields.length());
						logger.info("disableDependencyFields="+disableDependencyFields.length());
						request.getFlowScope().put("allDataFieldList", allDataFieldList);
						request.getFlowScope().put("enableDialogueMap", enableDependencyFields);
						request.getFlowScope().put("disableDialogueMap",disableDependencyFields);
						request.getFlashScope().put("referrerFlow", (String) httpRequest.getSession().getAttribute("referrerFlowAgentGroup"));
						request.getFlowScope().put("isFromDrupal",true);
					}
				}
			}
			else{
				generateDialoguesFromService(contextMap,salesCenterVo,request);
			}
			logger.info("TimeTakenforDialougeServicecall=" + (timer.getTime() - startTimer));
			OrderType order = (OrderType) httpRequest.getSession().getAttribute("order");
			com.AL.xml.v4.CustAddress custAdr = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
							com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
			request.getFlowScope().put("address", getAddress(custAdr));
			request.getFlashScope().put("CKOCompletedLineItems",LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
			logger.info("timeTakenForShowSupplierData=" + timer.getTime());
			logger.info("showSupplierData_end");
			if( isDialogueFromDialogueService )
			{
				return new Event(this, "supplierViewEvent");
			}
			else
			{
				return new Event(this, "supplierViewEventForDrupal");
			}
			
		} catch (Exception e) {
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",
					httpRequest.getParameter("pageTitle") != null ? httpRequest.getParameter("pageTitle") : "");
			logger.error(e + " in " + e.getStackTrace()[0].getClassName()
					+ " at line number " + e.getStackTrace()[0].getLineNumber());
			throw new UnRecoverableException(e.getMessage());
		}
		finally{
			timer.stop();
		}
	}

	
	private void generateDialoguesFromService(
			Map<String, Map<String, String>> contextMap,
			SalesCenterVO salesCenterVo, RequestContext request) throws UnRecoverableException {
		try {
			DialogueResponseType dialogueResponseType = SalesDialogueFactory.INSTANCE.getDialoguesByContext(contextMap);

			HttpServletRequest httpRequest = (HttpServletRequest) request.getExternalContext().getNativeRequest();

			List<DialogueType> dialogueVOList = new ArrayList<DialogueType>();
			dialogueVOList.addAll(dialogueResponseType.getResults().getDialogueList().getDialogue());
			List<DataFieldType> dataFieldList = new ArrayList<DataFieldType>();

			List<DataFieldMatrixType> dataFieldEnableList = new ArrayList<DataFieldMatrixType>();

			for (DialogueType dialoge : dialogueVOList) {
				for (DataGroupType dataGroup : dialoge.getDataGroupList().getDataGroup()) {
					dataFieldList.addAll(dataGroup.getDataFieldList().getDataField());
					dataFieldEnableList.add(dataGroup.getDataFieldMatrix());
				}
			}

			List<String> allDataFieldList = new ArrayList<String>();
			Map<String, Map<String, List<String>>> enableDependencyMap = DialogueUtil.INSTANCE.getEnableDependencies(dataFieldEnableList, httpRequest);
			Map<String, Map<String, List<String>>> disableDependencyMap = DialogueUtil.INSTANCE.getDisableDependencies(dataFieldList, enableDependencyMap,
					httpRequest);

			StringBuilder events = new StringBuilder();
			Map<String, String> dataFieldMap = new HashMap<String, String>();

			List<Fieldset> fieldsetList = WarmTransferHtmlFactory.INSTANCE.buildSupplierDialog(events, dialogueVOList,
					enableDependencyMap, allDataFieldList,
					dataFieldMap, httpRequest);

			for (Entry<String, Map<String, List<String>>> enableDependenciesEntry : enableDependencyMap.entrySet()) {
				for (Entry<String, List<String>> enableDependenciesList : enableDependenciesEntry.getValue().entrySet()) {
					for (String dependedEle : enableDependenciesList.getValue()) {
						allDataFieldList.add(dependedEle);
					}
				}
			}

			for (Fieldset fieldset : fieldsetList) {
				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				element = salesCenterVo.replaceNamesWithValues(element);
				element = SalesUtilsFactory.INSTANCE.parseHtmlTags(element);
				events.append(element);
			}
			request.getFlowScope().put("dataField", events.toString());
			request.getFlowScope().put("enableDialogueMap", enableDependencyMap);
			request.getFlowScope().put("disableDialogueMap",disableDependencyMap);
			request.getFlowScope().put("allDataFieldList", allDataFieldList);
			httpRequest.getSession().setAttribute("dataFieldMap", dataFieldMap);
			request.getFlowScope().put("isFromDrupal",false);
		}
		catch (BaseException e) {
			logger.error("Exception_in_SupplierController_getDialogues",e);
			throw new UnRecoverableException(e.getMessage());
		}

	}


	private Map<String, List<String>> dividedSuppliersBasedonMetaData(HashMap<String, List<ProductSummaryVO>> pecoOffersMap) throws JSONException
	{
		logger.info("preparing_suppliers_list_by_customerType_is_begin");
		Map<String, List<String>> pecoSuppliersMap = new HashMap<String, List<String>>();
		if( pecoOffersMap.get("PECO") != null )
		{
			for (ProductSummaryVO productVo :pecoOffersMap.get("PECO"))
			{
				if (productVo.getPromotionMetaDataList() != null && productVo.getPromotionMetaDataList().size() > 0)
				{
					for(String metaData :productVo.getPromotionMetaDataList())
					{
						if (metaData != null &&	metaData.equalsIgnoreCase("RESIDENTIAL"))
						{
							if(pecoSuppliersMap.get("RESIDENTIAL") != null )
							{
								List<String> pecoSuppliersList = pecoSuppliersMap.get("RESIDENTIAL");
								pecoSuppliersList.add(productVo.getName());
								pecoSuppliersMap.put("RESIDENTIAL", pecoSuppliersList);
							}
							else
							{
								List<String> pecoSuppliersList = new ArrayList<String>();
								pecoSuppliersList.add(productVo.getName());
								pecoSuppliersMap.put("RESIDENTIAL", pecoSuppliersList);
							}
						}	
						else if (metaData != null && metaData.equalsIgnoreCase("COMMERCIAL"))
						{
							if(pecoSuppliersMap.get("COMMERCIAL") != null )
							{
								List<String> pecoSuppliersList = pecoSuppliersMap.get("COMMERCIAL");
								pecoSuppliersList.add(productVo.getName());
								pecoSuppliersMap.put("COMMERCIAL", pecoSuppliersList);
							}
							else
							{
								List<String> pecoSuppliersList = new ArrayList<String>();
								pecoSuppliersList.add(productVo.getName());
								pecoSuppliersMap.put("COMMERCIAL", pecoSuppliersList);
							}
						}
					}
				}		
			}
		}
		logger.info("preparing_suppliers_list_by_customerType_is_completed");
		return pecoSuppliersMap;
	}

	private List<String> getSupplierList(HttpServletRequest httpRequest,
			List<String> supplierList,
			ProductResultsManager productResultManager) {
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		try{
		HashMap<String, List<ProductSummaryVO>> offersMap = new HashMap<String, List<ProductSummaryVO>>();
		offersMap.put("SAVERS", new ArrayList<ProductSummaryVO>());
		offersMap.put("SIMPLECHOICE", new ArrayList<ProductSummaryVO>());
		offersMap.put("PECO", new ArrayList<ProductSummaryVO>());
		// HashMap<String, List<ProductSummaryVO>> saversOfferMap = new
		// HashMap<String, List<ProductSummaryVO>>();
		ServiceabilityEnterpriseResponse sarRes = (ServiceabilityEnterpriseResponse) productResultManager.getSarRes();
		ProviderList providerList = new ProviderList();
		ServiceabilityResponse2 sre = (ServiceabilityResponse2) sarRes.getResponse();
		com.AL.xml.se.v4.ServiceabilityResponse2.ProviderList proList = sre.getProviderList();
		List<ProviderResults> results = null;
		startTimer = timer.getTime();
		logger.info("Calling ProductService for getSupplierList");
		com.AL.xml.pr.v4.EnterpriseResponseDocumentType response = ProductService.INSTANCE.getProducts(getProviderList(proList, providerList),
						getAddress(sre.getCorrectedAddress()),
						sarRes.getGUID(), getSalesContext(sarRes, httpRequest));
		logger.info("TimeTakenforProductServicecall=" + (timer.getTime() - startTimer));
		if (response != null) {
			ProductResponseType productResponseType = (ProductResponseType) response
					.getResponse();
			results = productResponseType.getProviderResults();
			for (ProviderResults rs : results) {
				for (ProductInfoType prodInfo : rs.getProductInfo()) {
					ProductSummaryVO productVo = getProduct(prodInfo);
					addProductToOffersMap(productVo, offersMap);
				}
			}
		}
		HashMap<String, List<ProductSummaryVO>> simpleChoiceOffersMap = new HashMap<String, List<ProductSummaryVO>>();
		simpleChoiceOffersMap.put("SIMPLECHOICE", offersMap.get("SIMPLECHOICE"));
		productResultManager.setSimpleChoiceOffersMap(simpleChoiceOffersMap);

		HashMap<String, List<ProductSummaryVO>> saversOfferMap = new HashMap<String, List<ProductSummaryVO>>();
		saversOfferMap.put("SAVERS", offersMap.get("SAVERS"));
		productResultManager.setSaversOfferMap(saversOfferMap);
		
		HashMap<String, List<ProductSummaryVO>> pecoOfferMap = new HashMap<String, List<ProductSummaryVO>>();
		pecoOfferMap.put("PECO", offersMap.get("PECO"));
		productResultManager.setPecoOffersMap(pecoOfferMap);


		// TO DO: loop through the list - offersMap.get("SIMPLECHOICE") - and
		// add productVo.getProviderName()
		// to the supplierList
		for (ProductSummaryVO productSummaryVO : simpleChoiceOffersMap.get("SIMPLECHOICE")) {
			supplierList.add(productSummaryVO.getProviderName());
		}
		productResultManager.setIsProductServiceCallCompleted(true);
		productResultManager.setSuppliersList(supplierList);
		
		logger.info("supplierList after ProductService call= " + supplierList);
		return supplierList;
		}finally{
			timer.stop();
		}
	}
	
	public Event determineSimpleChoiceSupplierView(RequestContext requestContext) throws UnRecoverableException{
		HttpServletRequest request =  (HttpServletRequest)requestContext.getExternalContext().getNativeRequest();
			List<String> supplierList = new ArrayList<String>();
			ProductResultsManager productResultManager = (ProductResultsManager) request.getSession().getAttribute("productResultManager");
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			supplierList.addAll((List<String>) productResultManager.getSuppliersList());
				if(supplierList.isEmpty()) {
					logger.info("No Suppliers for SimpleChoice");
					return new Event(this, "performUtilityOfferEvent");
				} else {
					logger.info("Suppliers for SimpleChoice found");
					logger.info("drupalContentUrl="+salesCenterVo.getValueByName("drupalContentUrl"));
					return new Event(this, "supplierViewEvent");
				}
	}

	public Address getAddress(com.AL.xml.v4.CustAddress custAdr) {
		Address addr = new Address();

		addr.setPostfixDirectional(custAdr.getAddress().getPostfixDirectional());
		addr.setPrefixDirectional(custAdr.getAddress().getPrefixDirectional());
		addr.setStreetName(custAdr.getAddress().getStreetName());
		addr.setStreetNumber(custAdr.getAddress().getStreetNumber());
		addr.setStreetType(custAdr.getAddress().getStreetType());
		addr.setLine2(custAdr.getAddress().getLine2());
		addr.setCity(custAdr.getAddress().getCity());
		addr.setStateOrProvince(custAdr.getAddress().getStateOrProvince());
		addr.setPostalCode(custAdr.getAddress().getPostalCode());
		return addr;
	}

	/**
	 * Get supplier list to populate when clicks on refresh supplier on the
	 * screen.
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSuppliersList")
	public @ResponseBody
	String getSuppliersList(HttpServletRequest request) throws Exception {
		String listOfSuppliers = null;
		ProductResultsManager productResultManager = (ProductResultsManager) request.getSession().getAttribute("productResultManager");
		
		String customerType = (String)request.getSession().getAttribute("customerType");
		if( !Utils.isBlank(customerType) )
		{
			logger.info("Getting_peco_suppliers_list");
			Map<String, List<String>> pecoSuppliersMap = dividedSuppliersBasedonMetaData(productResultManager.getPecoOffersMap());
			if( pecoSuppliersMap != null && !pecoSuppliersMap.isEmpty() 
				&& pecoSuppliersMap.get(customerType.toUpperCase()) != null )
			{
				JSONObject pecoSuppliersMapJson = new JSONObject(pecoSuppliersMap);
				JSONArray array = new JSONArray(pecoSuppliersMap.get(customerType.toUpperCase()));
				array.put(pecoSuppliersMapJson);
				listOfSuppliers = array.toString();
				request.getSession().setAttribute("pecoSuppliersMap",pecoSuppliersMap);
				logger.info("peco_suppliers_list="+listOfSuppliers);
			}
		}
		else
		{
			logger.info("Getting_first_energy_suppliers_list");
			if (!productResultManager.getSuppliersList().isEmpty()) 
			{
				JSONArray array = new JSONArray(productResultManager.getSuppliersList());
				listOfSuppliers = array.toString();
				logger.info("first_energy_suppliers_list="+listOfSuppliers);
			}
		}
		return listOfSuppliers;
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getRoundRobinSupplier")
	public @ResponseBody String getRoundRobinSupplier(HttpServletRequest request) throws Exception{
		ProductResultsManager productResultManager = (ProductResultsManager)request.getSession().getAttribute("productResultManager");
		RoundRobinService rrService = new RoundRobinService();
		logger.info("getRoundRobinSupplier_begin");
		List<String> supplierList = null;
		
		String customerType = request.getParameter("custType");
		
		if( !Utils.isBlank(customerType) )
		{
			customerType = (String)request.getSession().getAttribute("customerType");
			Map<String, List<String>> pecoSuppliersMap = (Map<String, List<String>>)request.getSession().getAttribute("pecoSuppliersMap");
			supplierList = pecoSuppliersMap.get(customerType.toUpperCase());
			logger.info("peco_suppliers_list_for_round_robin_service="+supplierList);
		}
		else
		{
			supplierList = productResultManager.getSuppliersList();
			logger.info("first_energy_suppliers_list_for_round_robin_service="+supplierList);
		}
		
	
		String offerTrackId = null;
		String roundRobinSupplier = null;
		if(supplierList!=null && !supplierList.isEmpty())
		{
			String salesSessionId = String.valueOf((Long)request.getSession().getAttribute("salesSessionId"));		
			roundRobinSupplier = rrService.getRoundRobinSupplier(salesSessionId, MEMBERTYPE, supplierList);
			logger.info("roundRobinSupplier="+roundRobinSupplier);
			if(!Utils.isBlank(roundRobinSupplier)) {
				int offerTrackIdBeginIndex = roundRobinSupplier.indexOf( OFFERTRACKID )
						+ OFFERTRACKID.length();
				int offerTrackIdEndIndex = roundRobinSupplier.indexOf( OFFERTRACKIDEND );
				offerTrackId = roundRobinSupplier.substring( offerTrackIdBeginIndex,
						offerTrackIdEndIndex );
				int supplierBeginIndex = roundRobinSupplier.indexOf( OFFEREDMEMBERID )
						+ OFFEREDMEMBERID.length();
				int supplierEndIndex = roundRobinSupplier.indexOf( OFFEREDMEMBERIDEND );
				if ( supplierEndIndex >= supplierBeginIndex )
				{
					roundRobinSupplier = roundRobinSupplier.substring( supplierBeginIndex,
							supplierEndIndex );
				}
			}
		}
		else
		{
			roundRobinSupplier = "";
		}
		request.getSession().setAttribute("mOfferTrackId",offerTrackId);
		return roundRobinSupplier;
	}

	private ProviderList getProviderList(
			com.AL.xml.se.v4.ServiceabilityResponse2.ProviderList proList,
			ProviderList providerList) {
		List<String> externalIdList = new ArrayList<String>();

		externalIdList.add("27010360");
		externalIdList.add("4353598");
		externalIdList.add("15500201");
		externalIdList.add("24699452");
		externalIdList.add("26069940");
		externalIdList.add("26069942");
		externalIdList.add("32416075");
		externalIdList.add("15499341");
		externalIdList.add("15499381");

		for (com.AL.xml.se.v4.ProviderType pr : proList.getProviders()) {
			ProviderType provider = new ProviderType();
			CapabilityList capabilityList = new CapabilityList();
			CapabilityType capability = new CapabilityType();
			for (com.AL.xml.se.v4.CapabilityType cab : pr
					.getCapabilityList().getCapabilities()) {
				capability.setName(cab.getName());
			}
			capabilityList.getCapability().add(capability);
			provider.setCapabilityList(capabilityList);
			provider.setExternalId(pr.getExternalId());
			provider.setName(pr.getName());
			ProviderSourceType source = new ProviderSourceType();
			source.setDatasource(pr.getSource().getDatasource());
			source.setValue(ProviderSourceBaseType.INTERNAL);
			provider.setSource(source);
			ProviderType parent = new ProviderType();
			parent.setName(pr.getParent().getName());
			parent.setExternalId(pr.getParent().getExternalId());
			provider.setParent(parent);
			if (externalIdList != null
					&& !externalIdList.contains(provider.getExternalId())) {
				providerList.getProvider().add(provider);
			}
		}
		return providerList;
	}

	public AddressType getAddress(
			com.AL.xml.se.v4.AddressType addressType) {
		AddressType address = new AddressType();
		address.setAddressBlock(addressType.getAddressBlock());
		address.setStreetNumber(addressType.getStreetNumber());
		address.setStreetName(addressType.getStreetName());
		address.setStreetType(addressType.getStreetType());
		address.setCity(addressType.getCity());
		address.setStateOrProvince(addressType.getStateOrProvince());
		address.setPostalCode(addressType.getPostalCode());
		return address;
	}

	private SalesContext getSalesContext(
			ServiceabilityEnterpriseResponse sarRes,
			HttpServletRequest httpRequest) {
		String dwellingType = "House";
		for (SalesContextEntityType sers : sarRes.getRequest()
				.getSalesContext().getEntities()) {
			if (sers.getName() != null
					&& sers.getName().equalsIgnoreCase("dwelling")) {
				for (com.AL.xml.se.v4.NameValuePairType nm : sers
						.getAttributes()) {
					if (nm.getName() != null
							&& nm.getName().equalsIgnoreCase(
									"dwelling.dwellingType")) {
						dwellingType = nm.getValue();
					}
				}
			}
		}
		return SalesContextFactory.INSTANCE.getSalesContext(createSalesContext(
				dwellingType, sarRes, httpRequest));

	}

	public Map<String, Map<String, String>> createSalesContext(
			String dwellingType, ServiceabilityEnterpriseResponse sarRes,
			HttpServletRequest request) {
		Map<String, Map<String, String>> salesContextData = new HashMap<String, Map<String, String>>();
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		String operatingCompany = salesCenterVo.getValueByName("simpleChoice.operatingCompanyCode");
		String rateCode = salesCenterVo.getValueByName("simpleChoice.rateCode");
		Map<String, String> context = new HashMap<String, String>();
		context.put("context.mode", "production");
		salesContextData.put("context", context);

		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("orderSource.source", "123");

		if (operatingCompany != null) {
			orderSource.put("orderSource.operatingCompany", operatingCompany);
		}
		if (rateCode != null) {
			orderSource.put("orderSource.rateCode", rateCode);
		}

		if (salesCenterVo != null && salesCenterVo.getValueByName("DT Partner") != null) {
			orderSource.put("orderSource.referrer", salesCenterVo.getValueByName("DT Partner"));
		} else {
			orderSource.put("orderSource.referrer", "utility");
		}
		orderSource.put("orderSource.channel", "1");
		salesContextData.put("orderSource", orderSource);

		Map<String, String> consumer = new HashMap<String, String>();
		consumer.put("consumer.creditScore", Constants.CONSUMER_CREDITSCORE);
		salesContextData.put("consumer", consumer);

		if (dwellingType != null && dwellingType.equalsIgnoreCase(ProductSearchIface.APARTMENT)) {
			dwellingType = "apartment";
		} else {
			dwellingType = "house";
		}
		Map<String, String> dwelling = new HashMap<String, String>();
		dwelling.put("dwelling.dwellingType", dwellingType);
		if (sarRes != null) {
			ServiceabilityResponse2 sre = (ServiceabilityResponse2) sarRes.getResponse();
			com.AL.xml.se.v4.AddressType sreAddress = sre.getCorrectedAddress();
			dwelling.put("dwelling.stateOrProvince", sreAddress.getStateOrProvince());
		}
		salesContextData.put("dwelling", dwelling);

		Map<String, String> salesFlow = new HashMap<String, String>();
		if (salesCenterVo != null && salesCenterVo.getValueByName("callType") != null) {
			salesFlow.put("salesFlow.referrer.callType", salesCenterVo.getValueByName("callType"));
		}
		if (salesCenterVo != null && salesCenterVo.getValueByName("dialogueType") != null) {
			salesFlow.put("salesFlow.dialogueType", salesCenterVo.getValueByName("dialogueType"));
		} else {
			salesFlow.put("salesFlow.dialogueType", "core");
		}
		if (salesCenterVo != null
				&& salesCenterVo.getValueByName("salesFlow.forceNonConfirm") != null) {
			salesFlow.put("salesFlow.forceNonConfirm", salesCenterVo.getValueByName("salesFlow.forceNonConfirm"));
		} else {
			salesFlow.put("salesFlow.forceNonConfirm", "false");
		}
		salesContextData.put("salesFlow", salesFlow);

		Map<String, String> agent = new HashMap<String, String>();
		agent.put("agent.capability", "advanced");
		salesContextData.put("agent", agent);
		logger.info("orderSource to product service= " + orderSource);

		return salesContextData;
	}

	private ProductSummaryVO getProduct(ProductInfoType prodInfo) {
		ProductSummaryVO productVo = new ProductSummaryVO();
		productVo.setExternalId(prodInfo.getExternalId());

		productVo.populateProductSummary(prodInfo.getProduct());
		productVo.populateCapabilities(prodInfo);
		if (prodInfo.getProductDetails() != null) {
			if (prodInfo.getProductDetails().getMarketingHighlights() != null) {
				productVo.setMarketingHighlightsList(prodInfo.getProductDetails().getMarketingHighlights().getMarketingHighlight());
			}
			if (prodInfo.getProductDetails().getDescriptiveInfo() != null
					&& prodInfo.getProductDetails().getDescriptiveInfo().size() > 0) {
				if (prodInfo.getProductDetails().getDescriptiveInfo().get(0) != null) {
					String result = prodInfo.getProductDetails().getDescriptiveInfo().get(0).getValue();
					if (prodInfo.getProductDetails().getDescriptiveInfo().get(0).getType() != null
							&& prodInfo.getProductDetails().getDescriptiveInfo().get(0).getType().equalsIgnoreCase("longDescription")) {
						if (result != null && result.contains("[Channels]")) {
							int index = result.indexOf("[Channels]");
							result = result.substring(0, index);
						}
					}
					productVo.setDescriptiveInfoValue(result);
				}
			}
			productVo.populatePromotions(prodInfo);
		}
		return productVo;
	}

	private void addProductToOffersMap(ProductSummaryVO productVo,
			HashMap<String, List<ProductSummaryVO>> offersMap) {
		if (productVo.getPromotionMetaDataList() != null
				&& productVo.getPromotionMetaDataList().size() > 0) {
			for (String metaData : productVo.getPromotionMetaDataList()) {
				if (metaData != null
						&& metaData.equalsIgnoreCase("SIMPLECHOICE")) {
					// TO DO: add a for loop on the list -
					// offersMap.get("SIMPLECHOICE") to see if this
					// product is already in the list; if not found, then add
					// it;
					boolean isProductFoundInList = false;
					for (ProductSummaryVO productSummaryVO : offersMap.get("SIMPLECHOICE")) {
						if (productSummaryVO != null && productSummaryVO.getExternalId() != null
								&& productSummaryVO.getExternalId().equalsIgnoreCase(
												productVo.getExternalId())) {
							isProductFoundInList = true;
							break;
						}
					}
					if (!isProductFoundInList) {
						offersMap.get("SIMPLECHOICE").add(productVo);
					}
				} else if (metaData != null
						&& metaData.equalsIgnoreCase("OFFER_TYPE=SAVERS")) {
					// TO DO: add a for loop on the list -
					// offersMap.get("SAVERS") to see if this
					// product is already in the list; if not found, then add
					// it;
					boolean isProductFoundInList = false;
					for (ProductSummaryVO productSummaryVO : offersMap.get("SAVERS")) {
						if (productSummaryVO != null && productSummaryVO.getExternalId() != null
								&& productSummaryVO.getExternalId().equalsIgnoreCase(productVo.getExternalId())) {
							isProductFoundInList = true;
							break;
						}
					}
					if (!isProductFoundInList) {
						offersMap.get("SAVERS").add(productVo);
					}
				}else if (metaData != null &&						
						(metaData.equalsIgnoreCase("RESIDENTIAL") || metaData.equalsIgnoreCase("COMMERCIAL")))
					{
					// TO DO: add a for loop on the list -
					// offersMap.get("RESIDENTIAL") or offersMap.get("COMMERCIAL") to see if this
					// product is already in the list; if not found, then add
					// it;
					boolean isProductFoundInList = false;
					for (ProductSummaryVO productSummaryVO : offersMap.get("PECO")) {
						if (productSummaryVO != null && productSummaryVO.getExternalId() != null
								&& productSummaryVO.getExternalId().equalsIgnoreCase(productVo.getExternalId())) {
							isProductFoundInList = true;
							break;
						}
					}
					if (!isProductFoundInList) {
						offersMap.get("PECO").add(productVo);
					}
				}
			}
		}
	}

	/**
	 * updates the supplier selected by the customer
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePreferredSupplier")
	public @ResponseBody
	String updatePreferredSupplierAccepted(HttpServletRequest request)
			throws Exception {
		RoundRobinService rrService = new RoundRobinService();
		String selectedSupplier = (String) request.getParameter("selectedSupplier");
		logger.info("selected_supplier_accepted=" + selectedSupplier);
		String salesSessionId = String.valueOf((Long) request.getSession().getAttribute("salesSessionId"));
		boolean isPreferredSupplierAccepted = rrService.updatePreferredSupplierAccepted(salesSessionId, MEMBERTYPE,selectedSupplier);
		logger.info("Preferred_Supplier_Accepted=" + isPreferredSupplierAccepted);
		return selectedSupplier;
	}

	/**
	 * rejects the supplier selected by the round robin
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/rejectSelectedSupplier")
	public @ResponseBody
	String rejectSelectedSupplier(HttpServletRequest request) throws Exception {
		RoundRobinService rrService = new RoundRobinService();
		String selectedSupplier = (String) request.getParameter("selectedSupplier");
		logger.info("round_robin_supplier_rejected=" + selectedSupplier);
		String salesSessionId = String.valueOf((Long) request.getSession().getAttribute("salesSessionId"));
		String offerTrackId = "";
		 if(request.getSession().getAttribute("mOfferTrackId")!=null)
		 {
			offerTrackId = (String)request.getSession().getAttribute("mOfferTrackId");
		 }
		boolean isRoundRobinSupplierRejected = rrService.updateRoundRobinRejected(salesSessionId, selectedSupplier, offerTrackId);
		logger.info("Round_Robin_Supplier_Rejected=" + isRoundRobinSupplierRejected);
		return selectedSupplier;
	}

	/**
	 * accepts the supplier selected by the round robin
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/roundRobinSupplierAccepted")
	public @ResponseBody
	String roundRobinSupplierAccepted(HttpServletRequest request)
			throws Exception {
		RoundRobinService rrService = new RoundRobinService();
		String selectedSupplier = (String) request.getParameter("selectedSupplier");
		String salesSessionId = String.valueOf((Long) request.getSession().getAttribute("salesSessionId"));
		logger.info("round_robin_supplier_accepted=" + selectedSupplier);
		String offerTrackId = "";
		 if(request.getSession().getAttribute("mOfferTrackId")!=null)
		 {
			offerTrackId = (String)request.getSession().getAttribute("mOfferTrackId");
		 }
		boolean isRoundRobinSupplierAccepted = rrService.updateRoundRobinAccepted(salesSessionId, selectedSupplier,offerTrackId);
		logger.info("Round_Robin_Supplier_Accepted="+ isRoundRobinSupplierAccepted);
		return selectedSupplier;
	}

	/**
	 * 
	 * Adding new line item on order for PECO supplier and also saving active
	 * dialogues.
	 * 
	 * @param requestContext
	 * @throws UnRecoverableException
	 */
	public void saveFirstEnergySupplierDataOnOrder(RequestContext requestContext)
			throws UnRecoverableException {
		HttpServletRequest httpRequest = (HttpServletRequest) requestContext.getExternalContext().getNativeRequest();
		StopWatch timer = new StopWatch();
		timer.start();
		try {

			SalesCenterVO salesCenterVo = (SalesCenterVO) httpRequest.getSession().getAttribute("salescontext");
			String agentId = salesCenterVo.getValueByName("Agent");
			String selectedSupplier = httpRequest.getParameter("selectedSupplierHiddenVal");
			String selectedValues = httpRequest.getParameter("selectedValues");

			String isSupplierAccepted = null;
			String isKnownSupplier = null;
			boolean isValidSupplierToAddLineItem = false;
			if (!Utils.isBlank(selectedValues)) {
				if (selectedValues.contains("Supplier_Selection_SCFE_Data_11")) {
					isSupplierAccepted = httpRequest.getParameter("Supplier_Selection_SCFE_Data_11");
				} else if (selectedValues.contains("Supplier_Selection_SCFE_Data_5")) {
					isKnownSupplier = httpRequest.getParameter("Supplier_Selection_SCFE_Data_5");
				}
			}

			logger.info("selected_Supplier=" + selectedSupplier
					+ ",isSupplierAccepted=" + isSupplierAccepted
					+ ",isKnownSupplier=" + isKnownSupplier);

			/*
			 * If the Agent coming from known supplier then "isKnownSupplier"
			 * value is "Yes". If the Agent coming from the round robin supplier
			 * then "isSupplierAccepted" value is "Yes". Either
			 * "isKnownSupplier" or "isSupplierAccepted" value is "Yes" and
			 * "selectedSupplier" is not empty then only product add to the
			 * order.
			 */
			if (!Utils.isBlank(selectedSupplier)
					&& (Constants.YES.equalsIgnoreCase(isSupplierAccepted) || Constants.YES.equalsIgnoreCase(isKnownSupplier))) {
				isValidSupplierToAddLineItem = true;
			}

			if (isValidSupplierToAddLineItem) {

				if (selectedSupplier.equalsIgnoreCase("Supplier Not Participating")) {
					List<String> feDefaultSupplierList = null;
					String feDefaultSupplierValues = ConfigRepo.getString("*.fe_default_supplier");
					feDefaultSupplierList = getPECOProductDetails(feDefaultSupplierValues);
					String standardOfferVal = salesCenterVo.getValueByName("simpleChoice.standardOffer");

					Long orderId = (Long) httpRequest.getSession().getAttribute("orderId");

					if (!feDefaultSupplierList.isEmpty()) {
						String productExernalId = feDefaultSupplierList.get(0);
						String partnerExternalId = feDefaultSupplierList.get(1);
						String productname = "ETFs for Simple Choice";

						httpRequest.setAttribute("productExernalId",productExernalId);
						httpRequest.setAttribute("partnerExternalId",partnerExternalId);
						httpRequest.setAttribute("productName", productname);

						httpRequest.setAttribute("orderExtId",orderId.toString());
						httpRequest.setAttribute("providerSourceBaseType","INTERNAL");
						httpRequest.setAttribute("standardOfferVal", standardOfferVal);

						/*
						 * creating new line item for supplier. Parameters are.
						 * 1) httpRequest 2) agentId 3) "true" when it is in
						 * peco flow otherwise "false" 4) "true" when supplier
						 * selected value is "supplier not participating"
						 * otherwise "false"
						 */
						createLineItem(httpRequest, agentId, false, true);

					} else {
						logger.info("FEDefaultSupplierValues=" + feDefaultSupplierValues);
						logger.info("Not_getting_fe_default_supplier_values_from_data_base");
					}
				} else {
					ProductResultsManager productResultManager = (ProductResultsManager) httpRequest.getSession().getAttribute("productResultManager");
					
					if( productResultManager.getSimpleChoiceOffersMap().get("SIMPLECHOICE") != null 
						&& !productResultManager.getSimpleChoiceOffersMap().get("SIMPLECHOICE").isEmpty() )
					{
						List<ProductSummaryVO> details = productResultManager.getSimpleChoiceOffersMap().get("SIMPLECHOICE");
						Long orderId = (Long) httpRequest.getSession().getAttribute("orderId");
						ProductSummaryVO productData = getSupplierProductVO(details,selectedSupplier,false);
						String standardOfferVal = salesCenterVo.getValueByName("simpleChoice.standardOffer");

						String orderExtId = null;
						if (!productData.equals("")) 
						{
							String productExernalId = escapeSpecialCharacters(productData.getExternalId());
							String partnerExternalId = escapeSpecialCharacters(productData.getProviderExternalId());
							String providerName = escapeSpecialCharacters(productData.getProviderName());
							String productName = escapeSpecialCharacters(productData.getName());
							orderExtId = orderId.toString();
							String providerSourceBaseType = productData.getSource();

							httpRequest.setAttribute("productExernalId", productExernalId);
							httpRequest.setAttribute("partnerExternalId", partnerExternalId);
							httpRequest.setAttribute("providerName", providerName);
							httpRequest.setAttribute("productName", productName);
							httpRequest.setAttribute("providerSourceBaseType", providerSourceBaseType);
							httpRequest.setAttribute("orderExtId", orderExtId);
							httpRequest.setAttribute("standardOfferVal", standardOfferVal);

							/*
							 * creating new line item for supplier. Parameters are.
							 * 1) httpRequest 2) agentId 3) "true" when it is in
							 * peco flow otherwise "false" 4) "true" when supplier
							 * selected value is "supplier not participating"
							 * otherwise "false"
							 */
							createLineItem(httpRequest, agentId, false, false);

							// creating mailing address.
							long startTimer = timer.getTime();
							addMailingAddress(httpRequest, salesCenterVo);
							logger.info("TimetakenforUpdateCustomer" + (timer.getTime() - startTimer));
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.warn("Error_in_SupplierController", e);
			requestContext.getFlowScope().put("message", e.getMessage());
			requestContext.getFlowScope().put("pageTitle",
					httpRequest.getParameter("pageTitle") != null ? httpRequest.getParameter("pageTitle") : "");
			logger.error(e + " in " + e.getStackTrace()[0].getClassName()
					+ " at line number " + e.getStackTrace()[0].getLineNumber());
			throw new UnRecoverableException(e.getMessage());
		}
		finally{
			timer.stop();
		}
	}

	/**
	 * 
	 * Adding new line item on order for PECO supplier and also saving active
	 * dialogues.
	 * 
	 * @param requestContext
	 * @throws UnRecoverableException
	 */
	public void savePECOSupplierDataOnOrder(RequestContext requestContext)
			throws UnRecoverableException {
		HttpServletRequest httpRequest = (HttpServletRequest) requestContext.getExternalContext().getNativeRequest();
		logger.info("savePECOSupplierDataOnOrder_begin");
		try {
			String pecoAcctNum = httpRequest.getParameter("Supplier_Selection_PECO_Acc_Num");

			if (!Utils.isBlank(pecoAcctNum)) 
			{
				
				logger.info("pecoAcctNum=" + pecoAcctNum);
				Long orderId = (Long) httpRequest.getSession().getAttribute("orderId");
				SalesCenterVO salesCenterVo = (SalesCenterVO) httpRequest.getSession().getAttribute("salescontext");
				String agentId = salesCenterVo.getValueByName("Agent");
				salesCenterVo.setValueByName("peco.acct.number", 
						pecoAcctNum);
				String selectedSupplier = httpRequest.getParameter("selectedSupplierHiddenVal");
				String selectedValues = httpRequest.getParameter("selectedValues");
				
				String isSupplierAccepted = null;
				String isKnownSupplier = null;
				boolean isValidSupplierToAddLineItem = false;
				
				if (!Utils.isBlank(selectedValues)) {
					if (selectedValues.contains("Supplier_Selection_PECO_Cust_Enrolled")) {
						isSupplierAccepted = httpRequest.getParameter("Supplier_Selection_PECO_Cust_Enrolled");
					} else if (selectedValues.contains("Supplier_Selection_PECO_Supplier_Quest_1")) {
						isKnownSupplier = httpRequest.getParameter("Supplier_Selection_PECO_Supplier_Quest_1");
					}
				}

				logger.info("selected_Supplier=" + selectedSupplier);
				logger.info("isSupplierAccepted=" + isSupplierAccepted);
				logger.info("isKnownSupplier=" + isKnownSupplier);
				
				/*
				 * If the Agent coming from known supplier then "isKnownSupplier"
				 * value is "Yes". If the Agent coming from the round robin supplier
				 * then "isSupplierAccepted" value is "Yes". Either
				 * "isKnownSupplier" or "isSupplierAccepted" value is "Yes" and
				 * "selectedSupplier" is not empty then only product add to the
				 * order.
				 */
				if (!Utils.isBlank(selectedSupplier)
						&& (Constants.YES.equalsIgnoreCase(isSupplierAccepted))) {
					isValidSupplierToAddLineItem = true;
				}
				
				if(isValidSupplierToAddLineItem)
				{
					if (selectedSupplier.equalsIgnoreCase("Supplier Not Listed")) 
					{
						String pecoDefaultSupplierValues = ConfigRepo.getString("*.peco_default_supplier");
						List<String> pecoDefaultSupplierList = getPECOProductDetails(pecoDefaultSupplierValues);
						
						if ( !pecoDefaultSupplierList.isEmpty() )
						{
							String productExernalId = pecoDefaultSupplierList.get(0);
							String partnerExternalId = pecoDefaultSupplierList.get(2);
							String productname = pecoDefaultSupplierList.get(1);

							httpRequest.setAttribute("productExernalId", productExernalId);
							httpRequest.setAttribute("partnerExternalId", partnerExternalId);
							httpRequest.setAttribute("productName", productname);
							httpRequest.setAttribute("orderExtId", orderId.toString());
							httpRequest.setAttribute("providerSourceBaseType", "INTERNAL");
							/*
							 * creating new line item for supplier. Parameters are.
							 * 1) httpRequest 2) agentId 3) "true" when it is in
							 * peco flow otherwise "false" 4) "true" when supplier
							 * selected value is "supplier not listed"
							 * otherwise "false"
							 */
							createLineItem(httpRequest, agentId, true, true);

						} else {
							logger.info("pecoDefaultSupplierValues=" + pecoDefaultSupplierValues);
							logger.info("Not_getting_peco_default_supplier_values_from_data_base");
						}
					} 
					else
					{
						ProductResultsManager productResultManager = (ProductResultsManager) httpRequest.getSession().getAttribute("productResultManager");
						if(productResultManager.getPecoOffersMap().get("PECO")!=null 
							&& !productResultManager.getPecoOffersMap().get("PECO").isEmpty())
						{
							String customerType = (String)httpRequest.getSession().getAttribute("customerType");
							logger.info("customerType="+customerType);
							Map<String, List<String>> pecoSuppliersMap = (Map<String, List<String>>)httpRequest.getSession().getAttribute("pecoSuppliersMap");
							logger.info("pecoSuppliersMap="+pecoSuppliersMap);
							if( !Utils.isBlank(customerType) && !Utils.isBlank(selectedSupplier) 
								&& pecoSuppliersMap != null && !pecoSuppliersMap.isEmpty() )
							{
								List<String> suppliersList = pecoSuppliersMap.get(customerType.toUpperCase());
								logger.info(customerType+"_customer_type_suppliersList="+suppliersList);
								if(suppliersList.contains(selectedSupplier))
								{
									logger.info("Selected"+selectedSupplier+"_is_matched_with_"+customerType+"customer_type_suppliersList");
									List<ProductSummaryVO> details = productResultManager.getPecoOffersMap().get("PECO");
									ProductSummaryVO productVO = getSupplierProductVO(details,selectedSupplier,true);
										
									String productExernalId = escapeSpecialCharacters(productVO.getExternalId());
									String partnerExternalId = escapeSpecialCharacters(productVO.getProviderExternalId());
									String providerName = escapeSpecialCharacters(productVO.getProviderName());
									String productName = escapeSpecialCharacters(productVO.getName());
									String providerSourceBaseType = productVO.getSource();

									httpRequest.setAttribute("productExernalId", productExernalId);
									httpRequest.setAttribute("partnerExternalId", partnerExternalId);
									httpRequest.setAttribute("providerName", providerName);
									httpRequest.setAttribute("productName", productName);
									httpRequest.setAttribute("providerSourceBaseType", providerSourceBaseType);
									httpRequest.setAttribute("orderExtId", orderId.toString());
										
									/*
									 * creating new line item for supplier. Parameters are.
									 * 1) httpRequest 2) agentId 3) "true" when it is in
									 * peco flow otherwise "false" 4) "false" for
									 * PecoSupplier lineitme_status is provision_ready
									 */
									createLineItem(httpRequest, agentId, true, false);
								}
								else
								{
									logger.info("Selected"+selectedSupplier+"_is_not_matched_with_"+customerType+"customer_type_suppliersList");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			requestContext.getFlowScope().put("message", e.getMessage());
			requestContext.getFlowScope().put("pageTitle", httpRequest.getParameter("pageTitle") != null ? httpRequest
							.getParameter("pageTitle") : "");
			logger.error(e + " in " + e.getStackTrace()[0].getClassName()
					+ " at line number " + e.getStackTrace()[0].getLineNumber());
			logger.error("Error_in_savePECOSupplierDataOnOrder",e);
			throw new UnRecoverableException(e.getMessage());
		}
	}

	
	/**
	 * 
	 * Creating new line item for supplier on order and also submitting that
	 * line item.
	 * 
	 * @param agentId
	 * @param httpRequest
	 * @param isPECOFlow
	 * 
	 */
	private void createLineItem(HttpServletRequest httpRequest, String agentId,
			boolean isPECOFlow, boolean isSupplierNotParticicpate)
			throws Exception {
		StopWatch timer = new StopWatch();
		timer.start();
		try{
		logger.info("Supplier create lineItem");
		com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
		LineItemCollectionType liCollection = oFactory
				.createLineItemCollectionType();

		String productExernalId = (String) httpRequest.getAttribute("productExernalId");
		String partnerExternalId = (String) httpRequest.getAttribute("partnerExternalId");
		String providerName = (String) httpRequest.getAttribute("providerName");
		String productname = (String) httpRequest.getAttribute("productName");
		String orderExtId = (String) httpRequest.getAttribute("orderExtId");
		String providerSourceBaseType = (String) httpRequest.getAttribute("providerSourceBaseType");
		String standardOfferVal = (String) httpRequest.getAttribute("standardOfferVal");
		JSONObject selectedDialogueValues = new JSONObject(	httpRequest.getParameter("selectedDialogueValues") );
		OrderType order2 = (OrderType) httpRequest.getSession().getAttribute("order");

		LineItemType lineItemType = oFactory.createLineItemType();

		prepareActiveDialoguesOnLineItem(oFactory, selectedDialogueValues, lineItemType);

		if (!Utils.isBlank(providerName)) {
			lineItemType.setPartnerName(providerName);
		}
		
		lineItemType.setLineItemNumber(0);
		lineItemType.setExternalId(0L);
		lineItemType.setService(ServiceType.BUSINESS);

		if (order2 != null) {
			com.AL.xml.v4.CustAddress cust = SalesUtil.INSTANCE.getAddress(order2.getCustomerInformation().getCustomer(),
							com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());

			String billingInfo = String.valueOf(order2.getCustomerInformation().getCustomer().getBillingInfoList().getBillingInfo().get(0).getExternalId());
			String svcAddressExtId = String.valueOf(cust.getAddress().getExternalId());
			lineItemType.setBillingInfoExtId(billingInfo);
			lineItemType.setSvcAddressExtId(svcAddressExtId);
		}

		LineItemAttributeType lineItemAttributeType = oFactory.createLineItemAttributeType();

		if (!isPECOFlow) 
		{
			Map<String, String> partnerSpecificDataMap = (Map<String, String>)httpRequest.getSession().getAttribute("partnerSpecificDataMap");
			if(partnerSpecificDataMap != null && partnerSpecificDataMap.size() > 0)
			{
				partnerSpecificDataMap.put("standardOffer", standardOfferVal);
				lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType(partnerSpecificDataMap,"cust_ref_rate"));
			}
		}
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("TYPE", "SimpleChoice", "", "PRODUCT_TYPE"));
		lineItemAttributeType.getEntity().add( CartLineItemFactory.INSTANCE.setAttributeEntityType("Display","false", "", "provider_feedback"));
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("STATUS",	"CKOComplete", "CKO COMPLETED", "CKO"));
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("name", providerName, "", "PROVIDER_NAME"));
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("name", productname, "", "PRODUCT_NAME"));
		
		lineItemType.setLineItemAttributes(lineItemAttributeType);

		com.AL.xml.v4.ProviderSourceType pstValue = oFactory.createProviderSourceType();
		if (!Utils.isBlank(providerName)) {
			pstValue.setDatasource(providerName);
		}
		pstValue.setValue(CartLineItemFactory.INSTANCE.getProviderSourceBaseType(providerSourceBaseType));
		lineItemType.setProductDatasource(pstValue);

		com.AL.xml.v4.ProviderType providerType = oFactory.createProviderType();
		providerType.setExternalId(partnerExternalId);
		if (!Utils.isBlank(providerName)) {
			providerType.setName(providerName);
		}

		LinkableType productType = oFactory.createLinkableType();
		productType.setExternalId(productExernalId);
		productType.setName(productname);
		productType.setProvider(providerType);

		OrderLineItemDetailTypeType orderLineItemDetailTypeType = oFactory
				.createOrderLineItemDetailTypeType();
		orderLineItemDetailTypeType.setProductLineItem(productType);

		LineItemDetailType lineItemDetailType = oFactory.createLineItemDetailType();
		lineItemDetailType.setDetailType("product");
		lineItemDetailType.setProductUniqueId(null);
		lineItemDetailType.setDetail(orderLineItemDetailTypeType);

		lineItemType.setLineItemDetail(lineItemDetailType);

		if (isPECOFlow) {
			String pecoAcctNum = httpRequest.getParameter("Supplier_Selection_PECO_Acc_Num");
			lineItemType.setProviderConfirmationNumber(pecoAcctNum);
		}

		/* Getting SalesContext from the session */

		SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(httpRequest.getSession());

		OrderType order = null;
		/* ServiceCall for adding LineItem to Order */

		if (isSupplierNotParticicpate) {
			List<Integer> reasons = new ArrayList<Integer>();
			reasons.add(ControllerConstants.REASON_CODE);

			LineItemStatusType liStatusType = LineItemStatusBuilder.INSTANCE.build(agentId, LineItemStatusCodesType.CANCELLED_REMOVED.value(), LineItemStatusCodesType.CANCELLED_REMOVED.value(), reasons);
			lineItemType.setLineItemStatus(liStatusType);
		}

//		if( isPECOFlow && !isSupplierNotParticicpate )
//		{
//			List<Integer> reasons = new ArrayList<Integer>();
//			reasons.add(ControllerConstants.REASON_CODE);
//			LineItemStatusType liStatusType = LineItemStatusBuilder.INSTANCE.build(agentId, LineItemStatusCodesType.PROVISION_READY.value(), LineItemStatusCodesType.PROVISION_READY.value(), reasons);
//			lineItemType.setLineItemStatus(liStatusType);
//		}
		
		liCollection.getLineItem().add(lineItemType);
		long startTimer = timer.getTime();
		order = LineItemService.INSTANCE.addLineItem(agentId, orderExtId,liCollection, salesContext, true);
		logger.info("TimeTakenforlineItemServicecall=" + (timer.getTime() - startTimer));

		if (order != null) {
			logger.info("Add_LineItem_is_success");
			if (!isSupplierNotParticicpate) 
			{
				List<String> listOflineItemIds = LineItemUtil.containsSupplierSelection(order);
				if (!listOflineItemIds.isEmpty()) {
					/*
					 * Service call for submitting the supplier selection on
					 * Order
					 */
					startTimer = timer.getTime();
					order = LineItemService.INSTANCE.submitMultipleLineItem(agentId, orderExtId, listOflineItemIds,salesContext);
					logger.info("TimeTakenforlineItemServicecall=" + (timer.getTime() - startTimer));
					if (order != null) {
						logger.info("Submit_LineItem_is_success");
					}
				}
			}
		}
		}finally{
			timer.stop();
		}
	}

	@SuppressWarnings("unchecked")
	private void prepareActiveDialoguesOnLineItem(
			com.AL.xml.v4.ObjectFactory oFactory,
			JSONObject selectedDialogueValues, LineItemType lineItemType)
			throws Exception {
		SelectedDialogsType selectedDialogueType = oFactory
				.createSelectedDialogsType();
		SelectedDialogsType.Dialogs selectedDialogueTypeDialogue = oFactory
				.createSelectedDialogsTypeDialogs();

		Iterator iterator = selectedDialogueValues.keys();

		while (iterator.hasNext()) {

			String dialogueExternalId = iterator.next().toString();
			DialogValueType.Value dialogueValueTypeValue = oFactory
					.createDialogValueTypeValue();
			dialogueValueTypeValue.setSelected(true);
			dialogueValueTypeValue.setType("string");
			dialogueValueTypeValue.setValue(String
					.valueOf(selectedDialogueValues.get(dialogueExternalId)));

			DialogValueType dialogueValueType = oFactory
					.createDialogValueType();
			dialogueValueType.setExternalId(dialogueExternalId);
			dialogueValueType.getValue().add(dialogueValueTypeValue);
			selectedDialogueTypeDialogue.getDialog().add(dialogueValueType);
		}
		selectedDialogueType.setDialogs(selectedDialogueTypeDialogue);
		lineItemType.setActiveDialogs(selectedDialogueType);
	}

	/**
	 * 
	 * Adding mailing address on customer address.
	 * 
	 * @param request
	 * @param salesCenterVo
	 */
	public void addMailingAddress(HttpServletRequest request,
			SalesCenterVO salesCenterVo) {
		String agentId = salesCenterVo.getValueByName("Agent");
		String GUID = salesCenterVo.getValueByName("GUID");
		String street1 = request.getParameter("MailingStreetAddress_disabled");
		String city = request.getParameter("MailingCity_disabled");
		String state = request.getParameter("MailingState_disabled");
		String zip = request.getParameter("MailingZipCode_disabled");
		String street2 = request.getParameter("MailingLine2type_disabled");
		String unitNumber = request.getParameter("MailingLine2Info_disabled");
		Long customerID = (Long) request.getSession().getAttribute("customerID");

		ObjectFactory oFactory = new ObjectFactory();

		com.AL.xml.cm.v4.AddressType address = oFactory.createAddressType();

		address.setExternalId(0L);
		address = getStreetNumberAndStreetName(address, street1);
		address.setCity(city);
		address.setStateOrProvince(state);
		address.setPostalCode(zip);
		address.setCountry("USA");
		address.setLine2(street2 + " " + unitNumber);
		StringBuilder dtFullAddress = new StringBuilder();
		dtFullAddress.append(getCapitalizationTextFromNormalText(street1));
		if (street2 != null && street2.trim().length() > 0) {
			dtFullAddress.append(" ");
			dtFullAddress.append(street2);
		}
		if (city != null && city.trim().length() > 0) {
			dtFullAddress.append(", ");
			dtFullAddress.append(getCapitalizationTextFromNormalText(city));
		}
		if (state != null && state.trim().length() > 0) {
			dtFullAddress.append(" ");
			dtFullAddress.append(state);
		}
		if (zip != null && zip.trim().length() > 0) {
			dtFullAddress.append(" ");
			dtFullAddress.append(zip);
		}
		address.setAddressBlock(dtFullAddress.toString());
		AddressRoles addrRoles = oFactory.createCustAddressAddressRoles();
		String status = "active";
		String[] roles = new String[1];
		roles = new String[1];
		roles[0] = "MAILING_ADDRESS";
		for (String role : roles) {
			if ((role != null) && (role.length() > 0)) {
				String cleanRole = resolveRole(role);
				RoleType rt = RoleType.fromValue(cleanRole);
				addrRoles.getRole().add(rt);
			}
		}
		Long tempAddressUniqueId = System.currentTimeMillis();
		CustomerContextType customerContext = CustomerFactory.INSTANCE
				.buildCustomerContext("source", "GUID", GUID);
		AddressService.INSTANCE.saveAddressUpdate(agentId,
				String.valueOf(customerID), roles,
				String.valueOf(address.getExternalId()),
				String.valueOf(tempAddressUniqueId), address, status,
				customerContext);
	}

	public String resolveRole(final String rawRole) {

		if ((rawRole == null) || (rawRole.length() == 0)) {
			return rawRole;
		}
		if ("CURRENT_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "CurrentAddress";
		else if ("SERVICE_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "ServiceAddress";
		else if ("BILLING_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "BillingAddress";
		else if ("PREVIOUS_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "PreviousAddress";
		else if ("MAILING_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "MailingAddress";
		else if ("HOME_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "HomeAddress";
		else if ("SHIPPING_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "ShippingAddress";
		else if ("CORRECTED_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "CorrectedAddress";
		else if ("DT_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "DTAddress";

		return rawRole;

	}

	public com.AL.xml.cm.v4.AddressType getStreetNumberAndStreetName(
			com.AL.xml.cm.v4.AddressType address, String add1) {
		String[] sp = add1.split(" ");
		String streetNumber = sp[0];
		String streetName = "";
		for (int i = 1; i < sp.length; i++) {
			streetName = streetName + sp[i] + " ";
		}
		streetName = streetName.trim();
		address.setStreetNumber(streetNumber);
		address.setStreetName(streetName);
		return address;

	}

	public String getCapitalizationTextFromNormalText(String normalText) {
		normalText = normalText.trim();
		StringBuilder capitalizedAddress = new StringBuilder();
		String[] stringArray = normalText.split(" ");

		for (String value : stringArray) {
			if (value.length() > 1) {
				capitalizedAddress.append(value.substring(0, 1).toUpperCase());
				capitalizedAddress.append(value.substring(1, value.length()).toLowerCase());
			} else if (value.length() == 1) {
				capitalizedAddress.append(value.toUpperCase());
			} else
				continue;
			if (!stringArray[stringArray.length - 1].equals(value)) {
				capitalizedAddress.append(" ");
			}
		}
		return capitalizedAddress.toString();

	}

	public List<String> getPECOProductDetails(String pecoDefaultSupplierValues) {
		List<String> productDetailsList = new ArrayList<String>();
		if (!Utils.isBlank(pecoDefaultSupplierValues)) {
			StringTokenizer stringTokenizer = new StringTokenizer(
					pecoDefaultSupplierValues, "|");
			while (stringTokenizer.hasMoreElements()) {
				productDetailsList
						.add(stringTokenizer.nextElement().toString());
			}
		}
		return productDetailsList;
	}

	
	public ProductSummaryVO getSupplierProductVO( List<ProductSummaryVO> details, String selectedSupplier, boolean isPECO ) throws Exception {
		ProductSummaryVO productSummaryVO = new ProductSummaryVO();
		try {
			if( isPECO )
			{
				for (ProductSummaryVO detailsVO : details) {
					if (detailsVO.getName().equalsIgnoreCase(selectedSupplier)) {
						productSummaryVO = detailsVO;
						break;
					}
				}
			}
			else
			{
				for (ProductSummaryVO detailsVO : details) {
					if (detailsVO.getProviderName().equalsIgnoreCase(selectedSupplier)) {
						productSummaryVO = detailsVO;
						break;
					}
				}
			}
			
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		logger.info("productSummaryVO=" + productSummaryVO);
		return productSummaryVO;
	}

	public String escapeSpecialCharacters(String str) {
		if (str != null) {
			str = str.replaceAll("&amp;", "&");
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("&quot;", "&#34;");

			str = str.replaceAll("&#10;", "&nbsp;");
			str = str.replaceAll("\u00a0", "&nbsp;");
			// this is for - mark
			str = str.replaceAll("\u2013", "&#8211;");
			// this is for trademark
			str = str.replaceAll("\u2122", "&#8482;");

			// this is for Copyright mark
			str = str.replaceAll("\u00a9", "&#169;");
			// this is for Registered trade mark
			str = str.replaceAll("\u00ae", "&#174;");

			// this is for bullet point
			str = str.replaceAll("\u2022", "&#8226;");
			// this is for exclamation point
			str = str.replaceAll("\u0021", "&#33;");
			// this is for colon
			str = str.replaceAll("\u003a", "&#58;");
			// this is for inverted question mark
			str = str.replaceAll("\u00bf", "&#191;");

			// this is for right single quotation mark
			str = str.replaceAll("\u2019", "&#8217;");
			// this is for left single quotation mark
			str = str.replaceAll("\u2018", "&#8216;");
			// this is for left double quotation mark
			str = str.replaceAll("\u201C", "&#8220;");
			// this is for right double quotation mark
			str = str.replaceAll("\u201D", "&#8221;");
		}
		return str;
	}
	
}
