package com.A.productResults.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.time.StopWatch;
import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import com.A.productResults.vo.PollingStatus;
import com.A.productResults.vo.ProductSearchIface;
import com.A.productResults.vo.ProductSummaryVO;
import com.A.ui.constants.Constants;
import com.A.ui.service.V.ESEService;
import com.A.ui.service.V.ProductService;
import com.A.ui.service.V.impl.DigitalProductServiceDataCache;
import com.A.ui.util.Utils;
import com.A.V.domain.SalesContext;
import com.A.xml.pr.v4.ProductInfoType;
import com.A.xml.pr.v4.ProductResponseType;
import com.A.xml.pr.v4.ProviderResults;
import com.A.xml.pr.v4.ProviderType;
import com.A.xml.pr.v4.ProductRequestType.ProviderList;
import com.A.xml.se.v4.ProcessingMessage;
import com.A.xml.se.v4.ServiceabilityResponse2;
import com.A.xml.se.v4.CandidateAddressList.CandidateAddress;
import com.A.ui.template.Dwelling;

public class WebProductResultsManager extends ProductResultsManager implements Runnable{

	private static final Logger logger = Logger.getLogger(WebProductResultsManager.class);

	private static int POLL_TIME_OUT = 45000;

	public WebProductResultsManager(ProductSearchIface prdSrcCtxt) {
		super(prdSrcCtxt);
	}

	@Override
	public void run() {
		setIsProductServiceCallCompleted(false);
		ServiceabilityResponse2 sre = (ServiceabilityResponse2)sarRes.getResponse();
		ProviderList providerList = new ProviderList();
		String displayName = null;
		String qwestCapibilitiesStr = null;
		Map<String,String> rtPendingProvidersMap = new HashMap<String, String>();
		Map<Long,String> successProvidersData = new HashMap<Long, String>();
		Map<String, String> localRealTimeStatusMap = new HashMap<String, String>();
		if (sre != null && sre.getRtimRequestResponseCriteria() != null){
			for (com.A.xml.se.v4.ProviderCriteriaEntityType2 pr2:sre.getRtimRequestResponseCriteria().getProviderCriteria().getProviders()){
				Map<String, String> attributes = getNameValuePairs(pr2);
				String name  = pr2.getName();
				boolean isQwestProviderSuccess = false;
				if (name != null){
					if (name.equalsIgnoreCase("ATTSTI")){
						displayName = "AT&T";
					}
					else if (name.equalsIgnoreCase("G2B-Comcast")){
						displayName = "Comcast_G2B";
					}
					else if (name.equalsIgnoreCase("DISH Network")){
						displayName = "Dish";
					}
					else if (name.equalsIgnoreCase("Comcast")){
						displayName = "Comcast";
					}
					else{
						displayName = name;
					}
				}
				String extId = getExternalId(attributes);
				String statusMsg = getStatusMsg(attributes);
				String status = getStatus(attributes);

				if (!StringUtils.isEmpty(status)) {
					context.updateProviderStatus(extId, name, status, statusMsg);
				}
				if ((extId != null)&& PollingStatus.PENDING.equalsIgnoreCase(status)) {
					rtPendingProvidersMap.put(extId, name);
				}
				if ((extId != null)&& PollingStatus.MULTIADDRISSUE.equalsIgnoreCase(status)) {
					rtPendingProvidersMap.put(extId, name);
					status = "Pending";
				}

				if ((extId != null)&& PollingStatus.SUCCESS.equalsIgnoreCase(status)) {
					if (!name.equalsIgnoreCase("QWEST")){
						ProviderType provider = new ProviderType();
						provider = addRtimProvidersToProvidersList(extId,provider,name);
						providerList.getProvider().add(provider);
					}else{
						ProviderType provider = new ProviderType();
						provider = addRtimProvidersToProvidersList(extId,provider,name);
						providerList.getProvider().add(provider);
						isQwestProviderSuccess = true;
						status = "Sell";
					}
					successProvidersData.put(Long.parseLong(extId),name);
				}
				if (!StringUtils.isEmpty(status)) {
					if (name != null){
						if(name.equalsIgnoreCase("QWEST")){
							localRealTimeStatusMap.put("CenturyLink", status + "|" + statusMsg);
						}
						else{
							if ((extId != null)&& PollingStatus.FAILED.equalsIgnoreCase(status)) {
								if (!Utils.isBlank(statusMsg) && statusMsg.equalsIgnoreCase(PollingStatus.SYSTEM_ERROR)){
									status = PollingStatus.SYSTEM_ERROR;
								}
							}
							localRealTimeStatusMap.put(displayName, status + "|" + statusMsg);
						}
					}
				}
				if (name != null){
					if (name.equalsIgnoreCase("QWEST")){
						if (isQwestProviderSuccess){
							qwestCapibilitiesStr = getQwestAttributes(attributes);
							String[] strValue = qwestCapibilitiesStr.split("\\|");
							for (int i=0; i<strValue.length; i++){
								if (strValue[i].contains("=")){
									String[] str = strValue[i].split("=");
									qwestCapibilitiesList.add(str[0]);
									qwestCapibilitiesValueMap.put(str[0], str[1]);
								}else{
									qwestCapibilitiesList.add(strValue[i]);	
								}
							}
						}
					}
				}
			}
		}

		Map<String, String> curProviderMap = new HashMap<String, String>();
		logger.info("about_to_make_getProducts_call_from_product_results_manager");
		com.A.xml.se.v4.ServiceabilityResponse2.ProviderList proList = sre.getProviderList();
		StringBuffer strBuf = new StringBuffer();
		for (ProviderType provider:providerList.getProvider()){
			strBuf.append(provider.getExternalId());
			strBuf.append("=");
			strBuf.append(provider.getName());
			strBuf.append("|");
		}
		logger.info("getproducts Started="+strBuf.toString());
		com.A.xml.pr.v4.EnterpriseResponseDocumentType response 
		= ProductService.INSTANCE.getProducts(
				getProviderList(proList,providerList), 
				getAddress(sre.getCorrectedAddress()), 
				sarRes.getGUID(), getSalesContext(),serviceabilityTransactionType);
		logger.info("getproducts ends="+providerList);
		if (response != null) {
			buildProductSummaryVoBasedOnProductResponse(response);
		}
		getProductByIcon(true);	
		// take a count of each provider 
		logger.info("localRealTimeStatusMap=" +localRealTimeStatusMap);
		Map<String, Long> result = updateRTProductsCountToMap(this.context.getAllProductList());
		logger.info("result=" +result);
		for (Entry<String,String> provider :localRealTimeStatusMap.entrySet()){
			if (result.get(provider.getKey()) != null){
				localRealTimeStatusMap.put(provider.getKey(), provider.getValue() + "|" +String.valueOf(result.get(provider.getKey())));
			}
		}
		realTimeStatusMap.putAll(localRealTimeStatusMap);
		for (Entry<Long,String> provider :successProvidersData.entrySet()){
			if (rtProductCountMap.get(provider.getKey()) == null){
				rtPendingProvidersMap.put(String.valueOf(provider.getKey()), provider.getValue());
				rtProductCountMap.put(provider.getKey(), 0L);
				if (!Utils.isBlank(provider.getValue())){
					if (provider.getValue().equalsIgnoreCase("ATTSTI")){
						displayName = "AT&T";
					}
					else if (provider.getValue().equalsIgnoreCase("G2B-Comcast")){
						displayName = "Comcast_G2B";
					}
					else if (provider.getValue().equalsIgnoreCase("DISH Network")){
						displayName = "Dish";
					}
					else if (provider.getValue().equalsIgnoreCase("Comcast")){
						displayName = "Comcast";
					}
					else if(provider.getValue().equalsIgnoreCase("QWEST")){
						displayName = "CenturyLink";
					}
					else{
						displayName = provider.getValue();
					}
					realTimeStatusMap.put(displayName, "Pending|Pending");
				}
			}
		}
		logger.info("getproducts_polling_Completed="+providerList);
		setIsProductServiceCallCompleted(true);
		StopWatch s = new StopWatch();
		ProviderList proList1 = new ProviderList();
		logger.info("poll_start_time="+POLL_TIME_OUT);
		s.start();
		logger.info("rtPendingProvidersMap=" +rtPendingProvidersMap);
		logger.info("realTimeStatusMap=" +realTimeStatusMap);
		/*List<com.A.xml.se.v4.ProviderType> res = ProductService.INSTANCE.getProviders(sre.getCorrectedAddress().getAddressBlock(), Dwelling.house);
		StringBuffer strBuf1 = new StringBuffer();
		for (com.A.xml.se.v4.ProviderType list: res){
			strBuf1.append(list.getExternalId());
			strBuf1.append("|");
			strBuf1.append(list.getName());
			strBuf1.append(",");
		}
		logger.info("strBuf1=" +strBuf1.toString());*/
		while (rtPendingProvidersMap.size() !=0) {
			if (s.getTime() > POLL_TIME_OUT){
				break;
			}
			curProviderMap = new HashMap<String, String>();
			proList1 = new ProviderList();
			for (Entry<String,String> entry : rtPendingProvidersMap.entrySet()){
				if(curProviderMap.get(entry.getKey()) == null) {
					if(!entry.getKey().equalsIgnoreCase(QWESTPRODUCT)) {
						ProviderType provider = new ProviderType();
						provider = addRtimProvidersToProvidersList(entry.getKey(),provider,entry.getValue());
						proList1.getProvider().add(provider);
					}else{
						ProviderType provider = new ProviderType();
						provider = addRtimProvidersToProvidersList(entry.getKey(),provider,entry.getValue());
						proList1.getProvider().add(provider);
						proList1.getProvider().add(addQwestInternalProviderToList());
					}
				}
			}

			logger.info("getProducts_call_for_pending_providers="+rtPendingProvidersMap);
			response = ProductService.INSTANCE.getProducts(proList1, 
					getAddress(sre.getCorrectedAddress()), 
					sarRes.getGUID(), getSalesContext(),serviceabilityTransactionType);
			logger.info("getProducts_call_for_pending_providers_ends="+rtPendingProvidersMap);
			if (response != null) {
				buildProductSummaryVoBasedOnProductResponse(response);
			}
		}
		getProductByIcon(true);	
		result = updateRTProductsCountToMap(this.context.getAllProductList());
		for (Entry<String,String> provider :realTimeStatusMap.entrySet()){
			if (result.get(provider.getKey()) != null){
				realTimeStatusMap.put(provider.getKey(), provider.getValue() + "|" +String.valueOf(result.get(provider.getKey())));
			}
		}
		rtPendingProvidersMap = getPendingProvidersList(rtPendingProvidersMap, response,this.isProductPollingCompleted());
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("realTimeStatusMapInsideLoop=" +realTimeStatusMap);
		logger.info("rtPendingProvidersMapInsideLoop= " +rtPendingProvidersMap);
		result = updateRTProductsCountToMap(this.context.getAllProductList());
		for (Entry<String,String> provider :realTimeStatusMap.entrySet()){
			if (result.get(provider.getKey()) != null){
				realTimeStatusMap.put(provider.getKey(), provider.getValue() + "|" +String.valueOf(result.get(provider.getKey())));
			}
		}
		for (Entry<String, String> entry : realTimeStatusMap.entrySet()) {
			String[] result1 = entry.getValue().split("\\|");
			if (result1[0].equalsIgnoreCase("Pending")) {
				realTimeStatusMap.put(entry.getKey(), "Timeout");
			}
		}	
		logger.info("realTimeStatusMap_before_thread_kills=" +realTimeStatusMap);
		logger.info("Product_Results_Manager_Polling_is_Completed.");
		s.stop();
	}
	
	
	public Map<String, Object> isValidAddress(String completeAddress, SalesContext salesContext, String serviceAddrType,String se2TransactionType,boolean speedMode){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		//	sarRes = ESEService.INSTANCE.getServiceabilityResponse(completeAddress, salesContext, createProviderCriteria());
		setServiceabilityTransactionType(se2TransactionType);
		sarRes = ESEService.INSTANCE.getServiceabilityResponse(completeAddress, salesContext, createProviderCriteria(),se2TransactionType,speedMode);
		boolean isAddressExactMatch = false;
		ServiceabilityResponse2 sre = new ServiceabilityResponse2();
		if (sarRes.getStatus() != null){
			sre = (ServiceabilityResponse2)sarRes.getResponse();
			logger.info("valid_address=" +sarRes.getStatus());
			if (sarRes.getStatus().getProcessingMessages() != null 
					&& sarRes.getStatus().getProcessingMessages().getMessages() != null 
					&& sarRes.getStatus().getProcessingMessages().getMessages().size() > 0){
				for (ProcessingMessage status : sarRes.getStatus().getProcessingMessages().getMessages()){
					if (status != null 
							&& (status.getCode()  == 1.0 || status.getCode() == 1.1)){
						isAddressExactMatch = true;
						break;
					}
				}
			}
		}

		boolean isCandidateAddress = false;
		if((sre.getCandidateAddressList().getCandidateAddresses().size() > 0 && Utils.isBlank(((com.A.xml.se.v4.AddressType)sre.getCorrectedAddress()).getAddressBlock()))){
			isCandidateAddress = true;	
		} 
		logger.info("isCandidateAddress="+isCandidateAddress);
		boolean isCorrectedAddress = false;
		if(!Utils.isBlank(((com.A.xml.se.v4.AddressType)sre.getCorrectedAddress()).getAddressBlock())){
			isCorrectedAddress = true;	
		}
		logger.info("isCorrectedAddress="+isCorrectedAddress);
		if(!se2TransactionType.equals(Constants.ZIPFALLBACK)){
			if (sarRes.getStatus() == null){
				returnMap.put("No Address Match -- No Status Code from SE2", null);
				return returnMap;
			}else{
				//double statusCode = 0.0;
				if (sarRes.getStatus().getProcessingMessages() != null 
						&& sarRes.getStatus().getProcessingMessages().getMessages() != null 
						&& sarRes.getStatus().getProcessingMessages().getMessages().size() > 0){
					for (ProcessingMessage status : sarRes.getStatus().getProcessingMessages().getMessages()){
						if (isAddressExactMatch && isCorrectedAddress )
						{
							returnMap.put("Address Found - Proceed to next screen", sarRes);
							return returnMap;
						}else if(status != null && isCandidateAddress )
						{
							setMultipleAddress(new ArrayList<String>());
							sre = (ServiceabilityResponse2)sarRes.getResponse();
							for (CandidateAddress address :sre.getCandidateAddressList().getCandidateAddresses()){

								StringBuilder capitalizedAddress = new StringBuilder();
								String[] stringArray = address.getAddressBlock().split(" ");

								for (String value : stringArray) {

									if(stringArray[stringArray.length-2].equals(value)){
										capitalizedAddress.append(value);
										capitalizedAddress.append(" ");
									}
									else if(stringArray[stringArray.length-1].equals(value)){
										capitalizedAddress.append(value);
									}else{
										if(value.equalsIgnoreCase(address.getPrefixDirectional()) || value.equalsIgnoreCase(address.getPostfixDirectional()) || value.equalsIgnoreCase(address.getPostfixDirectional()+",")){
											capitalizedAddress.append(value);
										}else{
											capitalizedAddress.append(value.substring(0, 1).toUpperCase());
											capitalizedAddress.append(value.substring(1, value.length()).toLowerCase());
										}
										capitalizedAddress.append(" ");
									}
								}
								getMultipleAddress().add(capitalizedAddress.toString());
								if (!Utils.isBlank(address.getLine2())){
									getMultipleAddressLine2Map().put(capitalizedAddress.toString().toLowerCase(), address.getLine2());	
								}
								logger.info("multipleAddressLine2Map="+getMultipleAddressLine2Map());
							}
							getMultipleAddress().add("None of the above");
							returnMap.put("Multiple Address", null);
							return returnMap;
						}else{
							returnMap.put(status.getText(), null);
							return returnMap;
						}
					}
				}
			}
		}
		else
		{
			setZipFallBack(true);
			returnMap.put("Address Found - Proceed to next screen", sarRes);
		}
		logger.info("ends_isValidAddress");
		return returnMap;
	}
	
	
	private void buildProductSummaryVoBasedOnProductResponse(com.A.xml.pr.v4.EnterpriseResponseDocumentType productResponse){
		ProductResponseType productResponseType = (ProductResponseType) productResponse.getResponse();
		List<ProviderResults> results = productResponseType.getProviderResults();
		for (ProviderResults rs : results){
			for(ProductInfoType prodInfo : rs.getProductInfo())
			{
				boolean isOffer = false;
				
				if( prodInfo.getProduct().getProductCategoryList()!=null 
					&& prodInfo.getProduct().getProductCategoryList().getProductCategory()!=null
					&& !prodInfo.getProduct().getProductCategoryList().getProductCategory().isEmpty()
					&& ProductSearchIface.OFFERS.equalsIgnoreCase(prodInfo.getProduct().getProductCategoryList().getProductCategory().get(0).getDisplayName()) )
				{
					isOffer = true;
				}
				
				if( !isOffer )
				{
					ProductSummaryVO productVo = getProduct(prodInfo);
					if(!this.context.getWebChannelProductExtIDList().contains(productVo.getExternalId()))
					{
						this.context.getWebChannelProductExtIDList().add(productVo.getExternalId());
					}
					if(DigitalProductServiceDataCache.INSTANCE.get(productVo.getExternalId()) == null)
					{
						//DigitalProductServiceDataCache.INSTANCE.store(productVo.getExternalId(),productVo);
					}
				}
			}
		}

	}
}