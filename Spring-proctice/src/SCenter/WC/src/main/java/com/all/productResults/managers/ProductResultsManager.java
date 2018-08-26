package com.A.productResults.managers;

import static com.A.ui.util.ConfigProperties.ADVISORYPROMOTION;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.A.productResults.vo.PollingStatus;
import com.A.productResults.vo.ProductDetailsVO;
import com.A.productResults.vo.ProductFormatter;
import com.A.productResults.vo.ProductSearchIface;
import com.A.productResults.vo.ProductSearchVO;
import com.A.productResults.vo.ProductSummaryVO;
import com.A.ui.constants.Constants;
import com.A.ui.dao.FrontierPricingGridConfigDao;
import com.A.ui.dao.GrossCommissionableRevenueDao;
import com.A.ui.dao.HughesNetServedUpDataDao;
import com.A.ui.dao.QualificationPopUpRefDetailsDao;
import com.A.ui.domain.GrossCommissionableRevenue;
import com.A.ui.domain.HughesNetServedUpData;
import com.A.ui.domain.QualificationPopUpRefDetails;
import com.A.ui.domain.WarmtransferTpvRepo;
import com.A.ui.service.config.ConfigRepo;
import com.A.ui.service.V.ESEService;
import com.A.ui.service.V.ProductService;
import com.A.ui.service.V.impl.CoxZipCodesDataCache;
import com.A.ui.service.V.impl.FrontierPricingGridCache;
import com.A.ui.service.V.impl.MDUCacheService;
import com.A.ui.template.MerchandisingTemplateConstant;
import com.A.ui.util.Utils;
import com.A.ui.vo.DigitalSalesContextVO;
import com.A.ui.vo.ProductVOJSON;
import com.A.ui.vo.RoadMapCriteriaVO;
import com.A.ui.vo.SalesCenterVO;
import com.A.V.domain.SalesContext;
import com.A.V.factory.SalesContextFactory;
import com.A.V.gateway.MerchandisingClient;
import com.A.V.gateway.jms.MerchandisingClientJMS;
import com.A.xml.me.v4.EnterpriseResponseDocumentType;
import com.A.xml.me.v4.MerchandisedProductType;
import com.A.xml.me.v4.MerchandisingRequestType.ProductList;
import com.A.xml.me.v4.MerchandisingResponseType;
import com.A.xml.pr.v4.AddressType;
import com.A.xml.pr.v4.CapabilityType;
import com.A.xml.pr.v4.ProductInfoType;
import com.A.xml.pr.v4.ProductPromotionType;
import com.A.xml.pr.v4.ProductRequestType.ProviderList;
import com.A.xml.pr.v4.ProductResponseType;
import com.A.xml.pr.v4.ProductType;
import com.A.xml.pr.v4.ProviderCriteriaEntryType;
import com.A.xml.pr.v4.ProviderCriteriaListEntityType;
import com.A.xml.pr.v4.ProviderResults;
import com.A.xml.pr.v4.ProviderSourceBaseType;
import com.A.xml.pr.v4.ProviderSourceType;
import com.A.xml.pr.v4.ProviderType;
import com.A.xml.pr.v4.ProviderType.CapabilityList;
import com.A.xml.se.v4.CandidateAddressList.CandidateAddress;
import com.A.xml.se.v4.ProcessingMessage;
import com.A.xml.se.v4.ProviderCriteriaEntityType2;
import com.A.xml.se.v4.ProviderCriteriaType2;
import com.A.xml.se.v4.ProviderNameValuePairType2;
import com.A.xml.se.v4.ServiceabilityEnterpriseResponse;
import com.A.xml.se.v4.ServiceabilityResponse2;

import edu.emory.mathcs.backport.java.util.Arrays;
/**
 * 
 * @author Nanda Kishore Palapala
 *
 */

public class ProductResultsManager implements Runnable {

	private static final Logger logger = Logger.getLogger(ProductResultsManager.class);
 
	public ProductSearchIface context;
	ServiceabilityEnterpriseResponse sarRes;
	GrossCommissionableRevenueDao grossCommissionableRevenueDao;
	QualificationPopUpRefDetailsDao qualificationPopUpRefDetailsDao;
	HughesNetServedUpDataDao hughesNetServedUpDataDao;
	FrontierPricingGridConfigDao frontierPricingGridConfigDao;

	public ServiceabilityEnterpriseResponse getSarRes() {
		return sarRes;
	}

	public ProductResultsManager(ProductSearchIface prdSrcCtxt) {
		this.context = prdSrcCtxt;
	}

	public ProductResultsManager(ProductSearchIface prdSrcCtxt,GrossCommissionableRevenueDao grossCommissionableRevenueDao, 
			QualificationPopUpRefDetailsDao qualificationPopUpRefDetailsDao, HughesNetServedUpDataDao hughesNetServedUpDataDao, FrontierPricingGridConfigDao frontierPricingGridConfigDao) {
		this.context = prdSrcCtxt;
		this.grossCommissionableRevenueDao = grossCommissionableRevenueDao;
		this.qualificationPopUpRefDetailsDao = qualificationPopUpRefDetailsDao;
		this.hughesNetServedUpDataDao = hughesNetServedUpDataDao;
		this.frontierPricingGridConfigDao = frontierPricingGridConfigDao;
	}

	private List<ProductSummaryVO> prList = new ArrayList<ProductSummaryVO>();	
	//private static int POLL_TIME_OUT = 100000; 
	private static int POLL_TIME_OUT = ConfigRepo.getInt("*.poll_time_out") == 0 ? 200000 : ConfigRepo.getInt("*.poll_time_out");
	@SuppressWarnings("unchecked")
	private List<String> hideProviderList  = Utils.isBlank(ConfigRepo.getString("*.hide_providers")) ? null : Arrays.asList(ConfigRepo.getString("*.hide_providers").split("\\|"));
	private List<String> providerTransferAgentGroupList  = Utils.isBlank(ConfigRepo.getString("*.provider_transfer_agentgroup")) ? null : Arrays.asList(ConfigRepo.getString("*.provider_transfer_agentgroup").split("\\|"));
	private List<String> hideProvidersInRealtimeWidgetList  = Utils.isBlank(ConfigRepo.getString("*.hide_providers_in_realtime_widget")) ? null : Arrays.asList(ConfigRepo.getString("*.hide_providers_in_realtime_widget").split("\\|"));
	private List<String> hintProvidersList  = Utils.isBlank(ConfigRepo.getString("*.hint_providers")) ? null : Arrays.asList(ConfigRepo.getString("*.hint_providers").split("\\|"));
	private List<String> telcoProvidersList = Utils.isBlank(ConfigRepo.getString("*.telco_providers")) ? null : Arrays.asList(ConfigRepo.getString("*.telco_providers").split("\\|"));;
	private List<String> cableProvidersList = Utils.isBlank(ConfigRepo.getString("*.cable_providers")) ? null : Arrays.asList(ConfigRepo.getString("*.cable_providers").split("\\|"));;
	private static int attV6PointsCacheTimeout = ConfigRepo.getInt("*.points_cache_time_out") == 0 ? 7200000 : ConfigRepo.getInt("*.points_cache_time_out");
	private String ceiPowerPitchProviderId = ConfigRepo.getString("*.ceiPowerPitchProviderId") == null ? null : ConfigRepo.getString("*.ceiPowerPitchProviderId");
	private String frontierPhoneInternetPricingGrid = ConfigRepo.getString("*.frontier_phone_internet_pricing_grid") == null ? null : ConfigRepo.getString("*.frontier_phone_internet_pricing_grid");
	private String hideHughesNet = ConfigRepo.getString("*.hide_hughesNet") != null ? ConfigRepo.getString("*.hide_hughesNet") : "false";
	private String isQualificationPopupEnabled = ConfigRepo.getString("*.qualification_popup_enable") != null ? ConfigRepo.getString("*.qualification_popup_enable") : "false";
	private List<String> hughesnetProductGroupList  = Utils.isBlank(ConfigRepo.getString("*.hughesnet_product_group")) ? null : Arrays.asList(ConfigRepo.getString("*.hughesnet_product_group").split("\\|"));
	private List<String> rtProviderDescriptionSuppressList = Utils.isBlank(ConfigRepo.getString("*.rt_provider_description_suppress")) ? null : Arrays.asList(ConfigRepo.getString("*.rt_provider_description_suppress").split("\\|"));
	private List<String> warmtransferOptionProvidersList = Utils.isBlank(ConfigRepo.getString("*.warmtransfer_option_providers")) ? null : Arrays.asList(ConfigRepo.getString("*.warmtransfer_option_providers").split("\\|"));
	private List<String> hughesnetShowReferrersList  = Utils.isBlank(ConfigRepo.getString("*.hughesnet_show_referrers")) ? null : Arrays.asList(ConfigRepo.getString("*.hughesnet_show_referrers").split("\\|"));
	private List<String> frontierPricingGridStatesList  = Utils.isBlank(ConfigRepo.getString("*.frontier_pricing_grid_states")) ? null : Arrays.asList(ConfigRepo.getString("*.frontier_pricing_grid_states").split("\\|"));
	private boolean pollingCompleted = false;
	private List<String> multiAddrProviderList = new ArrayList<String>();
	List<String> verizonPriceGridContent = Utils.isBlank(ConfigRepo.getString("*.verizon_price_grid_content")) ? null : Arrays.asList(ConfigRepo.getString("*.verizon_price_grid_content").split("\\|"));
	List<String> verizonBasePriceContent = Utils.isBlank(ConfigRepo.getString("*.verizon_baseprice_content")) ? null : Arrays.asList(ConfigRepo.getString("*.verizon_baseprice_content").split("\\|"));
	
	public List<String> getMultiAddrProviderList() {
		return multiAddrProviderList;
	}
	
	public void setMultiAddrProviderList(List<String> multiAddrProviderList) {
		this.multiAddrProviderList = multiAddrProviderList;
	}

	public List<String> getHughesnetProductGroupList() {
		return hughesnetProductGroupList;
	}

	public void setHughesnetProductGroupList(List<String> hughesnetProductGroupList) {
		this.hughesnetProductGroupList = hughesnetProductGroupList;
	}

	public String getIsQualificationPopupEnabled() {
		return isQualificationPopupEnabled;
	}

	public void setIsQualificationPopupEnabled(String isQualificationPopupEnabled) {
		this.isQualificationPopupEnabled = isQualificationPopupEnabled;
	}

	public static Map<String, Integer> getWarmTransferOrderMap() {
		if(WarmtransferTpvRepo.wtProviderMap != null && !WarmtransferTpvRepo.wtProviderMap.isEmpty()){
			return WarmtransferTpvRepo.wtProviderMap;
		}else{
			return buildWarmTransferProvidersList();
		}
	}

	private SalesContext salesContext;

	protected HashMap<String, String> realTimeStatusMap = new HashMap<String, String>();

	protected List<String> qwestCapibilitiesList =  new ArrayList<String>();

	protected HashMap<String, String> qwestCapibilitiesValueMap = new HashMap<String, String>();

	private Map<String, List<ProductSummaryVO>> closeOfferMap = new HashMap<String, List<ProductSummaryVO>>();
	private HashMap<String, List<ProductSummaryVO>> saversOfferMap = new HashMap<String, List<ProductSummaryVO>>();
	private HashMap<String, List<ProductSummaryVO>> utilityOffersMap = new HashMap<String, List<ProductSummaryVO>>();
	private HashMap<String, List<ProductSummaryVO>> simpleChoiceOffersMap = new HashMap<String, List<ProductSummaryVO>>();
	private HashMap<String, List<ProductSummaryVO>> pecoOffersMap = new HashMap<String, List<ProductSummaryVO>>();
	private List<ProductSummaryVO> powerPitchList = new ArrayList<ProductSummaryVO>();
	private List<RoadMapCriteriaVO> roadMapPowerPitchList = new ArrayList<RoadMapCriteriaVO>();
	private List<ProductSummaryVO> syntheticBundlesList = new ArrayList<ProductSummaryVO>();
	private Map<String, String> providerTransferAgentGroupMap = new HashMap<String, String>();
	private List<String> multipleAddress = new ArrayList<String>();
	private Map<String, String> hintProvidersMap = new HashMap<String, String>();
	private Map<String,List<ProductVOJSON>> productsMap = new HashMap<String,List<ProductVOJSON>>();
	private static String siftFileVersion  = null;
	private boolean isConsumer  = false;
	private boolean isSynthetic  = false;
	private boolean isRoadmapCompleted  = false;
	private boolean isFrontierVideoAvailable  = false;
	private boolean isFrontierInternetAvailable  = false;
	
	public boolean isFrontierInternetAvailable() {
		return isFrontierInternetAvailable;
	}

	public void setFrontierInternetAvailable(boolean isFrontierInternetAvailable) {
		this.isFrontierInternetAvailable = isFrontierInternetAvailable;
	}

	public boolean isFrontierVideoAvailable() {
		return isFrontierVideoAvailable;
	}

	public void setFrontierVideoAvailable(boolean isFrontierVideoAvailable) {
		this.isFrontierVideoAvailable = isFrontierVideoAvailable;
	}

	public Map<String, List<ProductVOJSON>> getProductsMap() {
		return productsMap;
	}
	public List<RoadMapCriteriaVO> getRoadMapPowerPitchList() {
		return roadMapPowerPitchList;
	}

	public void setRoadMapPowerPitchList(
			List<RoadMapCriteriaVO> roadMapPowerPitchList) {
		this.roadMapPowerPitchList = roadMapPowerPitchList;
	}

	public Map<String, List<ProductSummaryVO>> getCloseOfferMap() {
		return closeOfferMap;
	}

	public void setCloseOfferMap(Map<String, List<ProductSummaryVO>> closeOfferMap) {
		this.closeOfferMap = closeOfferMap;
	}

	private HashMap<String, String> multipleAddressLine2Map = new HashMap<String, String>();

	public HashMap<String, String> getMultipleAddressLine2Map() {
		return multipleAddressLine2Map;
	}

	public void setMultipleAddressLine2Map(
			HashMap<String, String> multipleAddressLine2Map) {
		this.multipleAddressLine2Map = multipleAddressLine2Map;
	}

	public HashMap<String, List<ProductSummaryVO>> getPecoOffersMap() {
		return pecoOffersMap;
	}

	public void setPecoOffersMap(HashMap<String, List<ProductSummaryVO>> pecoOffersMap) {
		this.pecoOffersMap = pecoOffersMap;
	}
	public List<String> getTelcoProvidersList() {
		return telcoProvidersList;
	}

	public void setTelcoProvidersList(List<String> telcoProvidersList) {
		this.telcoProvidersList = telcoProvidersList;
	}

	private List<String> suppliersList = new ArrayList<String>();

	public List<ProductSummaryVO> getPowerPitchList() {
		return powerPitchList;
	}
	
	public List<ProductSummaryVO> getSyntheticBundlesList() {
		return syntheticBundlesList;
	}
	
	public void setSuppliersList(List<String> suppliersList) {
		this.suppliersList = suppliersList;
	}
	public List<String> getMultipleAddress() {
		return multipleAddress;
	}
	public void setMultipleAddress(List<String> multipleAddress) {
		this.multipleAddress = multipleAddress;
	}
	public List<String> getSuppliersList() {
		return suppliersList;
	}
	public List<String> getQwestCapibilitiesList() {
		return qwestCapibilitiesList;
	}
	public void setQwestCapibilitiesList(List<String> qwestCapibilitiesList) {
		this.qwestCapibilitiesList = qwestCapibilitiesList;
	}
	public HashMap<String, String> getQwestCapibilitiesValueMap() {
		return qwestCapibilitiesValueMap;
	}
	public void setQwestCapibilitiesValueMap(
			HashMap<String, String> qwestCapibilitiesValueMap) {
		this.qwestCapibilitiesValueMap = qwestCapibilitiesValueMap;
	}
	public HashMap<String, List<ProductSummaryVO>> getSaversOfferMap() {
		return saversOfferMap;
	}
	public void setSaversOfferMap(
			HashMap<String, List<ProductSummaryVO>> saversOfferMap) {
		this.saversOfferMap = saversOfferMap;
	}
	public HashMap<String, List<ProductSummaryVO>> getUtilityOffersMap() {
		return utilityOffersMap;
	}
	public void setUtilityOffersMap(
			HashMap<String, List<ProductSummaryVO>> utilityOffersMap) {
		this.utilityOffersMap = utilityOffersMap;
	}

	public HashMap<String, List<ProductSummaryVO>> getSimpleChoiceOffersMap() {
		return simpleChoiceOffersMap;
	}
	public void setSimpleChoiceOffersMap(
			HashMap<String, List<ProductSummaryVO>> simpleChoiceOffersMap) {
		this.simpleChoiceOffersMap = simpleChoiceOffersMap;
	}
	public List<String> getHideProvidersInRealtimeWidgetList() {
		return hideProvidersInRealtimeWidgetList;
	}
	private Map<String,List<ProductSummaryVO>> productByIconMap = new HashMap<String,List<ProductSummaryVO>>();

	protected Map<Long, Long> rtProductCountMap = new HashMap<Long,Long>();

	public Map<String, List<ProductSummaryVO>> getProductByIconMap() {
		return productByIconMap;
	}
	private com.A.xml.cm.v4.AddressType serviceAddress; 

	public void setAddress(com.A.xml.cm.v4.AddressType serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public com.A.xml.cm.v4.AddressType getServiceAddress() {
		return serviceAddress;
	}

	private boolean isZipFallBack = false;
	private boolean isProductServiceCallCompleted;
	private boolean isProductPollingCompleted;

	private boolean isHNProductShow = false;

	public boolean isHNProductShow() {
		return isHNProductShow;
	}

	public void setHNProductShow(boolean isHNProductShow) {
		this.isHNProductShow = isHNProductShow;
	}

	public boolean IsProductServiceCallCompleted(){
		return isProductServiceCallCompleted;
	}
	public int getPollTimeOut(){
		return POLL_TIME_OUT;
	}
	public void setIsProductServiceCallCompleted(boolean isProductServiceCallCompleted){
		this.isProductServiceCallCompleted = isProductServiceCallCompleted;
	}

	public boolean isProductPollingCompleted() {
		return isProductPollingCompleted;
	}

	public void setProductPollingCompleted(boolean isProductPollingCompleted) {
		this.isProductPollingCompleted = isProductPollingCompleted;
	}
	
	private HashMap<String, String> transferCustomerEnabledProviderMap = new HashMap<String, String>();

	private HashMap<String, String> selectedExistingProvidersMap = new HashMap<String, String>();

	private HashMap<String, String> upgradeCustomerEnabledProviderMap = new HashMap<String, String>();

	private HashMap<String, String> providerNamesAndExtIdsMap = new HashMap<String, String>();

	private HashMap<String, String> selectedExistingCustomerStatusMap = new HashMap<String, String>();

	public void setZipFallBack(boolean isZipFallBack) {
		this.isZipFallBack = isZipFallBack;
	}

	public HashMap<String, String> getSelectedExistingCustomerStatusMap() {
		return selectedExistingCustomerStatusMap;
	}

	public void setSelectedExistingCustomerStatusMap(
			HashMap<String, String> selectedExistingCustomerStatusMap) {
		this.selectedExistingCustomerStatusMap = selectedExistingCustomerStatusMap;
	}

	public HashMap<String, String> getProviderNamesAndExtIdsMap() {
		return providerNamesAndExtIdsMap;
	}

	public HashMap<String, String> getTransferCustomerEnabledProviderMap() {
		return transferCustomerEnabledProviderMap;
	}

	public HashMap<String, String> getUpgradeCustomerEnabledProviderMap() {
		return upgradeCustomerEnabledProviderMap;
	}

	public void setSelectedExistingProvidersMap(
			HashMap<String, String> selectedExistingProvidersMap) {
		this.selectedExistingProvidersMap = selectedExistingProvidersMap;
	}

	public HashMap<String, String> getSelectedExistingProvidersMap() {
		return selectedExistingProvidersMap;
	}

	public Map<String, String> getHintProvidersMap() {
		return hintProvidersMap;
	}
	private Map<String, GrossCommissionableRevenue> revenueCommissionsMap = new HashMap<String, GrossCommissionableRevenue>();

	public Map<String, GrossCommissionableRevenue> getRevenueCommissionsMap() {
		return revenueCommissionsMap;
	}
	
	private Map<String, Map<String, String>> categoryWiseProductNameAndExternalId = new HashMap<String, Map<String, String>>();

	public void setCategoryWiseProductNameAndExternalId(
			Map<String, Map<String, String>> categoryWiseProductNameAndExternalId) {
		this.categoryWiseProductNameAndExternalId = categoryWiseProductNameAndExternalId;
	}

	public Map<String, Map<String, String>> getCategoryWiseProductNameAndExternalId() {
		return categoryWiseProductNameAndExternalId;
	}


	private static String TRIPLE_PLAY = "TRIPLE_PLAY";
	private static String DOUBLE_PLAY = "DOUBLE_PLAY";
	private static String VIDEO = "VIDEO";
	private static String PHONE = "PHONE";
	private static String INTERNET = "INTERNET";
	private static String HOMESECURITY = "HOMESECURITY";
	private static String ELECTRICITY = "ELECTRICITY";
	private static String WATER = "WATER";
	private static String NATURALGAS = "NATURALGAS";
	private static String WASTEREMOVAL = "WASTEREMOVAL";
	private static String TECH = "TECHSUPPORT";
	private static String ASIS = "ASIS_PLAN";
	private static String OFFERS = "OFFERS";
	private static String BUNDLES = "BUNDLES";
	private static String APPLIANCEPROTECTION = "APPLIANCEPROTECTION";

	private static String OWNERS = "OWNERS";
	private static String RENTERS = "RENTERS";
	private static String OWN = "OWN";
	private static String RENT = "RENT";
	private static String SIMPLECHOICE = "SIMPLECHOICE";
	private static String TPV = "SYSTEM_EVENT:TPV";
	private static String WARMTRANSFER = "SYSTEM_EVENT:WARMTRANSFER";
	protected static String QWESTPRODUCT = "179296";
	private static String RESIDENTIAL = "Residential";
	private static String COMMERCIAL = "Commercial";
	private static String MIXEDBUNDLES = "MIXEDBUNDLES";

	protected String serviceabilityTransactionType = "normal";

	protected boolean speedMode = false;

	public String getServiceabilityTransactionType() {
		return serviceabilityTransactionType;
	}

	public void setServiceabilityTransactionType(
			String serviceabilityTransactionType) {
		this.serviceabilityTransactionType = serviceabilityTransactionType;
	}

	protected String serviceAddrType = null;
	
	/**
	 * @return the serviceAddrType
	 */
	public String getServiceAddrType() {
		return serviceAddrType;
	}

	/**
	 * @param serviceAddrType the serviceAddrType to set
	 */
	public void setServiceAddrType(String serviceAddrType) {
		this.serviceAddrType = serviceAddrType;
	}

	public void run() {
		SalesCenterVO salesCenterVo = context.getSalesCenterVO();
		String unitType = salesCenterVo.getValueByName("correctedUnitType");
		String unitNumber = salesCenterVo.getValueByName("correctedUnitNumber");
		if(context.getChannel() != null && context.getChannel().equals(ProductSearchVO.CONCERT)) {
			this.setIsProductServiceCallCompleted(false);
			this.setProductPollingCompleted(false);
			MDC.put("sessionId", salesCenterVo.getValueByName("sessionId") != null ? salesCenterVo.getValueByName("sessionId") : "");
			MDC.put("agentId", salesCenterVo.getValueByName("agent.id") != null ? salesCenterVo.getValueByName("agent.id") : "");
			MDC.put("orderId", salesCenterVo.getValueByName("orderId") != null ? salesCenterVo.getValueByName("orderId") : "");
			MDC.put("GUID", salesCenterVo.getValueByName("GUID") != null ? salesCenterVo.getValueByName("GUID") : "");
			logger.info("order_id="+ context.getSalesCenterVO().getValueByName("orderId"));
		}
		setServiceAddrType(context.getSalesCenterVO().getValueByName("serviceAddrType"));
		logger.info("begin_run_method_channel_is=" +context.getChannel()+" serviceAddrType = "+serviceAddrType);
		//initTelcoProviders();
		Map<Long,String> successProvidersData = new HashMap<Long, String>();

		ProductList productList = new ProductList();
		Map<String,String> rtPendingProvidersMap = new HashMap<String, String>();
		List<ProductSummaryVO> remainingProductList = new ArrayList<ProductSummaryVO>();
	//	List<String> multiAddrProviderList = new ArrayList<String>();
		//SalesContext salesContext = null;
		if(context.getChannel() != null && !context.getChannel().equals(ProductSearchVO.CONCERT)) {
			logger.info("about_to_make_SE2_call_from_product_results_manager");
			sarRes = ESEService.INSTANCE.getServiceabilityResponse(context.getInputAddress(), getSalesContext(), createProviderCriteria());
		}

		ServiceabilityResponse2 sre = (ServiceabilityResponse2)sarRes.getResponse();
		String displayName = null;
		String qwestCapibilitiesStr = null;
		Map<String, String> localRealTimeStatusMap = new HashMap<String, String>();
		logger.info("hideProviderList="+hideProviderList);
		ProviderList providerList = new ProviderList();
		String referrerId = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
		if(hughesnetShowReferrersList!= null && hughesnetShowReferrersList.contains(referrerId)){
			this.setHideHughesNet("false");
		}
		if(providerTransferAgentGroupList != null){
			generateProviderTransferAgentGroupMap();
		}
		if (sre != null && sre.getRtimRequestResponseCriteria() != null){
			for (com.A.xml.se.v4.ProviderCriteriaEntityType2 pr2:sre.getRtimRequestResponseCriteria().getProviderCriteria().getProviders()){
				Map<String, String> attributes = getNameValuePairs(pr2);
				String extId = getExternalId(attributes);
				if (hideProviderList == null || !hideProviderList.contains(extId)){
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
					generateMultiAddrProviderList(pr2,multiAddrProviderList);
					String statusMsg = getStatusMsg(attributes);
					String status = getStatus(attributes);
					generateTransferExistingCustomer(attributes,extId,displayName);
					logger.info("transferCustomerEnabledProvider="+transferCustomerEnabledProviderMap);

					providerNamesAndExtIdsMap.put(extId, name);

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
		}
		Map<String, String> curProviderMap = new HashMap<String, String>();
		List<ProviderResults> results = null;
		logger.info("about_to_make_getProducts_call_from_product_results_manager");
		com.A.xml.se.v4.ServiceabilityResponse2.ProviderList proList = sre.getProviderList();

		com.A.xml.pr.v4.EnterpriseResponseDocumentType response 
		= ProductService.INSTANCE.getProducts(
				getProviderList(proList,providerList), 
				getAddress(sre.getCorrectedAddress()), 
				sarRes.getGUID(), getSalesContext(),serviceabilityTransactionType);
		if (response != null) {
			if(response.getStatus() != null 
					&& response.getStatus().getProcessingMessages() != null 
					      && response.getStatus().getProcessingMessages().getMessage() != null 
					       && !response.getStatus().getProcessingMessages().getMessage().isEmpty()){
				for(com.A.xml.pr.v4.ProcessingMessage processMsg:response.getStatus().getProcessingMessages().getMessage()){
					if(processMsg.getCode() == 100){
						siftFileVersion = String.valueOf(processMsg.getText());
					}
				}
			}
			logger.info("siftVersion="+siftFileVersion);
			buildQwestCapabilities(response);
			ProductResponseType productResponseType = (ProductResponseType) response.getResponse();
			results = productResponseType.getProviderResults();
			for (ProviderResults rs : results){
				//productList = new ProductList();	

				for(ProductInfoType prodInfo : rs.getProductInfo()){
					ProductSummaryVO productVo = getProduct(prodInfo);
					if(isMetaDataOwnOrRent(productVo,context.getRentOrOwn())){
						ProductType product = prodInfo.getProduct();
						curProviderMap.put(product.getProvider().getExternalId(), product.getProvider().getExternalId());
						addMerchandisedProduct(productList, prodInfo);
						checkTPVandWarmTransferOnProduct(productVo);
						//checkMetaDataAndAddSaversOrUtilityOfferToMap(productVo);
						//remainingProductList.add(getProduct(prodInfo));
						if (checkQwestProductCapabilities(productVo)){
							remainingProductList.add(productVo);	
						}
					}
				}
			}
			//this.setIsProductServiceCallCompleted(true);
		}

		/*
		 * This is for getting GrossCommissionalRevenue value from database to display on products display page....
		 * 
		 */

		if (productList != null && productList.getProduct().size() > 0){
			logger.info("about_to_make_Merchandising_call_from_product_results_manager");
			remainingProductList = getMerchandising(productList, remainingProductList, 
					sarRes.getGUID(), getSalesContext());	
		}
		this.context.setAllProductList(addRemainingProducts(remainingProductList, multiAddrProviderList));
		for(ProductSummaryVO productVO:this.context.getAllProductList()){
			buildClosingOfferMap(productVO,context.getRentOrOwn());
			if(isMetaDataOwnOrRent(productVO,context.getRentOrOwn())){
				checkMetaDataAndAddSaversOrUtilityOfferToMap(productVO);
			}
		}
		if(response != null){
			this.setIsProductServiceCallCompleted(true);
			if(hideHughesNet != null && !hideHughesNet.equalsIgnoreCase("true")){
				this.setProductPollingCompleted(true);
				this.setHNProductShow(true);
			}
		}
		// take a count of each provider 
		logger.info("localRealTimeStatusMap=" +localRealTimeStatusMap);
		updateRTProductsCountToMap(this.context.getAllProductList());
		realTimeStatusMap.putAll(localRealTimeStatusMap);
		if (context.getChannel() != null && context.getChannel().equalsIgnoreCase(ProductSearchVO.CONCERT)){
			List<ProductSummaryVO> powerPitchSyntheticBundleList = populateSyntheticBundlesData();
			populatePowerPitchData(powerPitchSyntheticBundleList); //Need to call this method based on flow
			productByIconMapData(this.context.getAllProductList());
			populateProductsData();
			populatePivotAssistData();
		}else{
			getProductByIcon(false);	
		}
		
		updateRealTimeStatusToDispalyATTandDirecTV(realTimeStatusMap,this.context.getAllProductList());
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

		StopWatch s = new StopWatch();
		ProviderList proList1 = new ProviderList();
		logger.info("poll_start_time="+POLL_TIME_OUT);
		s.start();
		logger.info("rtPendingProvidersMap=" +rtPendingProvidersMap);
		logger.info("realTimeStatusMap=" +realTimeStatusMap);

		while (rtPendingProvidersMap.size() !=0) {
			if (s.getTime() > POLL_TIME_OUT){
				 this.setPollingCompleted(true);
				break;
			}
			remainingProductList = new ArrayList<ProductSummaryVO>();

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

			logger.info("getProducts_call_for_pending_providers");
			response = ProductService.INSTANCE.getProducts(proList1, 
					getAddress(sre.getCorrectedAddress()), 
					sarRes.getGUID(), getSalesContext(),serviceabilityTransactionType);

			if (response != null) {
				buildQwestCapabilities(response);
				productList = new ProductList();
				ProductResponseType productResponseType = (ProductResponseType) response.getResponse();
				results = productResponseType.getProviderResults();
				for (ProviderResults rs : results){
					//productList = new ProductList();			
					for(ProductInfoType prodInfo : rs.getProductInfo()){
						ProductSummaryVO productVo = getProduct(prodInfo);
						if (isMetaDataOwnOrRent(productVo,context.getRentOrOwn())){
							checkTPVandWarmTransferOnProduct(productVo);
							addMerchandisedProduct(productList, prodInfo);
							if (checkQwestProductCapabilities(productVo)){
								remainingProductList.add(productVo);	
							}
						}
					}
				}
				this.setIsProductServiceCallCompleted(true);
				if(hideHughesNet != null && !hideHughesNet.equalsIgnoreCase("true")){
					this.setProductPollingCompleted(true);
					this.setHNProductShow(true);
				}
			}

			if (productList != null && productList.getProduct().size() > 0){
				logger.info("Merchandising_call_for_pending_providers");
				remainingProductList = getMerchandising(productList, 
						remainingProductList, 
						sarRes.getGUID(), 
						getSalesContext());	
			}
			remainingProductList.addAll(this.context.getAllProductList());
			if(!multiAddrProviderList.contains(Constants.ATTV6)){
				generateMultiAddrProviderList2(response, multiAddrProviderList);
			}
			
			this.context.setAllProductList(addRemainingProducts(remainingProductList, multiAddrProviderList )); 
			if (context.getChannel() != null && context.getChannel().equalsIgnoreCase(ProductSearchVO.CONCERT)){
				List<ProductSummaryVO> powerPitchSyntheticBundlesList = populateSyntheticBundlesData();
				populatePowerPitchData(powerPitchSyntheticBundlesList); //Need to call this method based on flow
				productByIconMapData(this.context.getAllProductList());
				populateProductsData();
				populatePivotAssistData();
			}else{
				getProductByIcon(false);	
			}
			updateRTProductsCountToMap(this.context.getAllProductList());
			rtPendingProvidersMap = getPendingProvidersList(rtPendingProvidersMap, response,this.isProductPollingCompleted());
			updateRealTimeStatusToDispalyATTandDirecTV(realTimeStatusMap,this.context.getAllProductList());
			try {
				Thread.sleep(10000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info("realTimeStatusMapInsideLoop=" +realTimeStatusMap);
			logger.info("rtPendingProvidersMapInsideLoop= " +rtPendingProvidersMap);
		}
		if(context.getChannel() != null && !context.getChannel().equals(ProductSearchVO.CONCERT)) {
			for (PollingStatus entry : context.getProviderStatus().values()) {
				if (entry.isPending()) {
					entry.setStatus(PollingStatus.ABORT);
				}
			}	
		}else{
			for (Entry<String, String> entry : realTimeStatusMap.entrySet()) {
				String[] result = entry.getValue().split("\\|");
				if (result[0].equalsIgnoreCase("Pending") && !entry.getKey().equalsIgnoreCase(Constants.HUGHESNET_NAME)) {
					realTimeStatusMap.put(entry.getKey(), "Timeout");
				}
			}	
		}
		updateRealTimeStatusToDispalyATTandDirecTV(realTimeStatusMap,this.context.getAllProductList());
		
		boolean isHughesNetServiceable = false; 
		if(hideHughesNet != null && hideHughesNet.equalsIgnoreCase("true")){
			isHughesNetServiceable = isHughesNetServiceable();
			this.setHNProductShow(isHughesNetServiceable);
			if(isHughesNetServiceable){
				List<ProductSummaryVO> powerPitchSyntheticBundlesList = populateSyntheticBundlesData();
				populatePowerPitchData(powerPitchSyntheticBundlesList); //Need to call this method based on flow
				productByIconMapData(this.context.getAllProductList());
				populateProductsData();
				populatePivotAssistData();
			}
		}else{
			this.setHNProductShow(true);
		}
		try{
			if(!Utils.isBlank(unitType) && !Utils.isBlank(unitNumber)){
				populateMDURoadMapPowerPitchData(); //Need to call this method based on flow 
			}
			else{
				populateSDURoadMapPowerPitchData(); //Need to call this method based on flow 
			}
		}
		catch(Exception e){
			logger.warn("Exception while populating RoadMap PowerPitch Data"+e.getMessage());
		}
		this.setProductPollingCompleted(true);
		this.setIsProductServiceCallCompleted(true);
		this.setRoadmapCompleted(true);
		logger.info("realTimeStatusMap_before_thread_kills=" +realTimeStatusMap);
		logger.info("Product_Results_Manager_Polling_is_Completed.");
		if(this.isFrontierInternetAvailable){
			logger.info("Hughesnet Products are Suppressed Due to Frontier Internet Products");
		}
		try{
			String userGroupProduct = salesCenterVo.getValueByName("userGroupProduct");
			logger.info("userGroupProduct"+userGroupProduct);
			logger.info("hughesnetProductGroupList"+hughesnetProductGroupList);
			if(hughesnetProductGroupList != null && !Utils.isBlank(userGroupProduct) && hughesnetProductGroupList.contains(userGroupProduct)){
				logger.info("PopulatingHughesNetServedUpDataWithUserGroupProduct"+userGroupProduct);
				HughesNetServedUpData hughesNetServedUpData = new HughesNetServedUpData();
				hughesNetServedUpData.setGuid(salesCenterVo.getValueByName("GUID"));
				hughesNetServedUpData.setCreateDate(Calendar.getInstance());
				if(realTimeStatusMap.get(Constants.HUGHESNET_NAME) != null){
					if((realTimeStatusMap.get(Constants.HUGHESNET_NAME).contains(PollingStatus.SUCCESS) || realTimeStatusMap.get(Constants.HUGHESNET_NAME).contains(PollingStatus.PENDING)) 
							&& (rtProductCountMap != null && rtProductCountMap.get(Long.parseLong(Constants.HUGHESNET)) != null && (rtProductCountMap.get(Long.parseLong(Constants.HUGHESNET)) > 0))){
						hughesNetServedUpData.setSe2Pass("true");
						if(isHughesNetServiceable){
							hughesNetServedUpData.setProductDisplay("true");
						}
						else{
							hughesNetServedUpData.setProductDisplay("false");
						}
					}
					else{
						hughesNetServedUpData.setSe2Pass("false");
						hughesNetServedUpData.setProductDisplay("false");
						String[] status = realTimeStatusMap.get(Constants.HUGHESNET_NAME).split("\\|");
						if(!Utils.isBlank(status[1])){
							String se2FailReason = status[1];
							if(se2FailReason.length() >= 600){
								se2FailReason.substring(0, 595);
								se2FailReason  = se2FailReason + " ...";
							}
							hughesNetServedUpData.setSe2FailReason(se2FailReason);
						}
					}
				}
				logger.info("PopulatingHughesNetServedUpDataWith GUID="+ hughesNetServedUpData.getGuid()+" SE2Pass="+hughesNetServedUpData.getSe2Pass()+
						" ProductDisplay="+hughesNetServedUpData.getProductDisplay()+" se2FailReason="+hughesNetServedUpData.getSe2FailReason());
				hughesNetServedUpDataDao.insertHughesNetServedUpData(hughesNetServedUpData);
			}
		}
		catch(Exception e){
			logger.warn("Exception while inseting HughesNetServedUpData"+e.getMessage());
		}
		s.stop();
	}

	private void generateMultiAddrProviderList(ProviderCriteriaEntityType2 pr2,
			List<String> multiAddrProviderList) {
		
		try {
			if(pr2 != null && pr2.getAttributes() != null){
				for (ProviderNameValuePairType2 pnvpt : pr2.getAttributes()) {
					if(pnvpt != null && !Utils.isBlank(pnvpt.getName()) && pnvpt.getName().equalsIgnoreCase("statusMessage")
							&& pnvpt.getValueAttribute().contains("Multiple Address")){
						String providerId = pnvpt.getValueAttribute().split("_")[1].split("#")[0];
						multiAddrProviderList.add(providerId);
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.info("Error while generateMultiAddrProviderList="+e.getMessage());
		}
	}
	
	private void generateMultiAddrProviderList2(com.A.xml.pr.v4.EnterpriseResponseDocumentType response,
			List<String> multiAddrProviderList) {
		
		try {
			ProductResponseType prs = (ProductResponseType)response.getResponse();
			if (prs.getRtimRequestResponseCriteria() != null){
				if (prs.getRtimRequestResponseCriteria().getProviderCriteria() != null){
					if (prs.getRtimRequestResponseCriteria().getProviderCriteria().getProvider() != null){
						for (ProviderCriteriaListEntityType pr2:prs.getRtimRequestResponseCriteria().getProviderCriteria().getProvider()){
							if(pr2 != null && pr2.getCriteria() != null){
								for (ProviderCriteriaEntryType provider : pr2.getCriteria()) {
									if(provider != null && !Utils.isBlank(provider.getCriteriaName()) && provider.getCriteriaName().equalsIgnoreCase("statusMessage")
											&& provider.getCriteriaValue().contains("Multiple Address")){
										String statusMessage = provider.getCriteriaValue().split("_")[1].split("#")[0];
										multiAddrProviderList.add(statusMessage);
										break;
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.info("Error while generateMultiAddrProviderList2="+e.getMessage());
		}
	}


	/*private void generateTransferExistingCustomer(Map<String, String> attributes, String extId, String displayName) {
		String val = null;
		SalesCenterVO salesCenterVo = context.getSalesCenterVO();
		String transferProviderIds = "none";
		boolean hasComcast = false;
		boolean hasATT = false;
		boolean hasATTV6 = false;
		if (salesCenterVo != null) {
			if(Utils.isBlank(salesCenterVo.getValueByName("enableExistingCustomerUserGroupControl"))||"false".equalsIgnoreCase(salesCenterVo.getValueByName("enableExistingCustomerUserGroupControl")) ) {
				if (attributes.containsKey("transferCustomerEnabled")) {
					val = attributes.get("transferCustomerEnabled");
				}
				if ("true".equalsIgnoreCase(val)) {
					transferCustomerEnabledProviderMap.put(displayName, extId);
				} 
			}
			else{
			if(!Utils.isBlank(salesCenterVo.getValueByName("transferProviderIds"))) {
				transferProviderIds = salesCenterVo.getValueByName("transferProviderIds");
			}
			if(salesCenterVo.getValueByName("hasComcastGroup") != null) {
				hasComcast = Boolean.valueOf(salesCenterVo.getValueByName("hasComcastGroup"));		
			}
			if(salesCenterVo.getValueByName("hasATTGroup") != null) {
				hasATT = Boolean.valueOf(salesCenterVo.getValueByName("hasATTGroup"));
			}if(salesCenterVo.getValueByName("hasATTV6Group") != null){
				hasATTV6 = Boolean.valueOf(salesCenterVo.getValueByName("hasATTV6Group"));
			}
		if (attributes.containsKey("transferCustomerEnabled")) {
			val = attributes.get("transferCustomerEnabled");
			if ("true".equalsIgnoreCase(val) && !hasComcast && !hasATT && !hasATTV6) {
				transferCustomerEnabledProviderMap.put(displayName, extId);
			}
			if ("true".equalsIgnoreCase(val)) {
				if(transferProviderIds.contains(extId)) {
					transferCustomerEnabledProviderMap.put(displayName, extId);
				} else {
					if (!displayName.equals("Comcast") && !displayName.equals("AT&T") && !displayName.equals("ATTV6")) {
						transferCustomerEnabledProviderMap.put(displayName, extId);
					} 
				}
			} 
		  }
		}
	}
		if (attributes.containsKey("upgradeCustomerEnabled")) {
			val = attributes.get("upgradeCustomerEnabled");
			if ("true".equalsIgnoreCase(val)) {
				upgradeCustomerEnabledProviderMap.put(displayName, extId);
			}
		}
	}
*/
	public SalesContext getSalesContext() {
		if (salesContext == null)
			salesContext = SalesContextFactory.INSTANCE.getSalesContext(
					createSalesContext(context.getDwellingType(),sarRes));
		return salesContext;
	}
	
	private static HashMap<String, Integer> buildWarmTransferProvidersList() {
		HashMap<String, Integer> wtProviderMap = new HashMap<String, Integer>();
		wtProviderMap.put(Constants.CSP, 1);
		wtProviderMap.put(Constants.ADT, 2);
		wtProviderMap.put(Constants.ADT_NEW, 3);
		wtProviderMap.put(Constants.ADT_INTEGRATION, 4);
		wtProviderMap.put(Constants.DISHTRANSFER, 5);
		wtProviderMap.put(Constants.DIRECTSTAR, 6);
		wtProviderMap.put(Constants.CABLEVISION, 7);
		wtProviderMap.put(Constants.CABLEONE, 8);
		wtProviderMap.put(Constants.RENTERS_INSURANCE, 9);
		wtProviderMap.put(Constants.SUNRUN, 10);
		//logger.info("build_WarmTransfer_ProvidersMap="+wtProviderMap);
		return wtProviderMap;
	}

	protected String getStatus(Map<String, String> attributes) {
		String status ="";
		if (attributes.containsKey("status")) {
			status = attributes.get("status");
			if ("Pending".equalsIgnoreCase(status)) {
				status = PollingStatus.PENDING;	
			}else if ("Success".equalsIgnoreCase(status)) {
				status = PollingStatus.SUCCESS;
			}else if ("Fail".equalsIgnoreCase(status)) {
				status = PollingStatus.FAILED;
			}

		}
		return status;
	}
	protected String getStatusMsg(Map<String, String> attributes) {
		String statusMsg="";
		if (attributes.containsKey("statusMessage"))
			statusMsg = attributes.get("statusMessage");
		return statusMsg;
	}

	protected String getQwestAttributes(Map<String, String> attributes) {
		String capabilities="";
		if (attributes.containsKey("capabilities"))
			capabilities = attributes.get("capabilities");
		return capabilities;
	}
	protected String getExternalId(Map<String, String> attributes) {
		String extId = null;
		if (attributes.containsKey("externalId"))
			extId = attributes.get("externalId");
		return extId;
	}
	protected Map<String, String> getNameValuePairs(
			com.A.xml.se.v4.ProviderCriteriaEntityType2 pr2) {
		Map <String, String> namevaluepairs = new HashMap<String, String>();
		for (ProviderNameValuePairType2 pr3 :pr2.getAttributes()){
			namevaluepairs.put(pr3.getName(), pr3.getValueAttribute());
		}
		return namevaluepairs;
	}


	public ProductSummaryVO getProduct(ProductInfoType prodInfo) {
		ProductSummaryVO productVo = new ProductSummaryVO();
		productVo.setExternalId(prodInfo.getExternalId());

		productVo.populateProductSummary(prodInfo.getProduct());
		productVo.populateCapabilities(prodInfo);
		if (prodInfo.getProductDetails() != null){
			if (prodInfo.getProductDetails().getMarketingHighlights() != null){
				productVo.setMarketingHighlightsList(prodInfo.getProductDetails().getMarketingHighlights().getMarketingHighlight());
			}
			if (prodInfo.getProductDetails().getDescriptiveInfo() != null && prodInfo.getProductDetails().getDescriptiveInfo().size() > 0){
				if (prodInfo.getProductDetails().getDescriptiveInfo().get(0) != null){
					String result = prodInfo.getProductDetails().getDescriptiveInfo().get(0).getValue();
					if (prodInfo.getProductDetails().getDescriptiveInfo().get(0).getType() != null 
							&& prodInfo.getProductDetails().getDescriptiveInfo().get(0).getType().equalsIgnoreCase("longDescription")){
						if (result != null && result.contains("[Channels]")){
							int index = result.indexOf("[Channels]");
							result = result.substring(0, index);
						}
					}
					productVo.setDescriptiveInfoValue(result);	
				}
			}
			productVo.populatePromotions(prodInfo);
		}

		if(prodInfo.getProductDetails() != null){
			productVo.setFeatureTypeList(prodInfo.getProductDetails().getFeature());
			productVo.setFeatureGroupTypeList(prodInfo.getProductDetails().getFeatureGroup());
			productVo.setDescriptiveInfoTypelist(prodInfo.getProductDetails().getDescriptiveInfo());	
		}
		return productVo;
	}

	public void addMerchandisedProduct(ProductList productList,
			ProductInfoType product) {
		MerchandisedProductType prod = new MerchandisedProductType();
		prod.setExternalId(product.getExternalId());
		prod.setType("product");
		productList.getProduct().add(prod);
	}

	public ProviderType addRtimProvidersToProvidersList(String providerExtId, ProviderType provider, String name) {
		CapabilityList capabilityList = new CapabilityList();
		CapabilityType capability = new CapabilityType();
		capabilityList.getCapability().add(capability);
		provider.setCapabilityList(capabilityList);
		provider.setName(name);
		provider.setExternalId(providerExtId);
		ProviderSourceType source = new ProviderSourceType();
		if (name != null && name.equalsIgnoreCase("Verizon")){
			source.setDatasource("VZ");	
		}
		else if (name.equalsIgnoreCase("ATTSTI")){
			source.setDatasource("ATTSTI");
		}
		else if (name.equalsIgnoreCase("G2B-Comcast")){
			source.setDatasource("G2B");
		}
		else if (name.equalsIgnoreCase("DISH Network")){
			source.setDatasource("DISH");
		}
		else if (name.equalsIgnoreCase("Comcast")){
			source.setDatasource("COMCAST");
		}
		else if (name.equalsIgnoreCase("QWEST")){
			source.setDatasource("QWEST");
		}else{
			source.setDatasource(name.toUpperCase());
		}

		source.setValue(ProviderSourceBaseType.REALTIME);
		provider.setSource(source);
		ProviderType parent = new ProviderType();
		provider.setParent(parent);
		return provider;
	}

	protected ProviderList getProviderList(
			com.A.xml.se.v4.ServiceabilityResponse2.ProviderList proList, ProviderList providerList) {
		List<String> externalIdList = new ArrayList<String>();
		for(com.A.xml.se.v4.ProviderType pr : proList.getProviders()){
			if (pr.getSource() != null && pr.getSource().getValue() != null 
					&& pr.getSource().getValue().value().equalsIgnoreCase(ProviderSourceBaseType.REALTIME.value()))
			{
				externalIdList.add(pr.getExternalId()); 
			}
		}
		/*externalIdList.add("27010360");
		externalIdList.add("4353598");
		externalIdList.add("24699452");
		externalIdList.add("26069940");
		externalIdList.add("26069942");
		externalIdList.add("32416075");*/
		for(com.A.xml.se.v4.ProviderType pr : proList.getProviders()){
			ProviderType provider = new ProviderType();
			CapabilityList capabilityList = new CapabilityList();
			CapabilityType capability = new CapabilityType();
			for (com.A.xml.se.v4.CapabilityType cab : pr.getCapabilityList().getCapabilities()){
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
			if (externalIdList != null && !externalIdList.contains(provider.getExternalId())){
				providerList.getProvider().add(provider);	
			}
		}
		ProviderList updatedProviderList = new ProviderList();
		if(hideProviderList != null && !hideProviderList.isEmpty())
		{
			for(ProviderType pr : providerList.getProvider()){
					if(!(pr.getExternalId() != null && hideProviderList.contains(pr.getExternalId()) 
							 || (pr.getParent().getExternalId() != null && hideProviderList.contains(pr.getParent().getExternalId())))){
						updatedProviderList.getProvider().add(pr);
					}
			}
			return updatedProviderList;
		}else{
			return providerList;
		}
	}

	public AddressType getAddress(com.A.xml.se.v4.AddressType addressType) {
		/*
        <v4:streetNumber>1106</v4:streetNumber>
        <v4:streetName>GREGG</v4:streetName>
        <v4:streetType>ST</v4:streetType>
        <v4:city>COLUMBIA</v4:city>
        <v4:stateOrProvince>SC</v4:stateOrProvince>
        <v4:postalCode>29201-3825</v4:postalCode>
        <v4:addressBlock>1106 GREGG ST, COLUMBIA SC 29201-3825</v4:addressBlock>
		 */
		AddressType address = new AddressType();
		if(isZipFallBack){
			address.setAddressBlock(serviceAddress.getAddressBlock());
			address.setStreetNumber(serviceAddress.getStreetNumber());
			address.setStreetName(serviceAddress.getStreetName());
			address.setStreetType(serviceAddress.getStreetType());
			address.setCity(serviceAddress.getCity());
			address.setStateOrProvince(serviceAddress.getStateOrProvince());
			address.setPostalCode(serviceAddress.getPostalCode());
		}else{
			address.setAddressBlock(addressType.getAddressBlock());
			address.setStreetNumber(addressType.getStreetNumber());
			address.setStreetName(addressType.getStreetName());
			address.setStreetType(addressType.getStreetType());
			address.setCity(addressType.getCity());
			address.setStateOrProvince(addressType.getStateOrProvince());
			address.setPostalCode(addressType.getPostalCode());
		}
		return address;
	}

	public Map<String, Map<String, String>> createSalesContext(String dwellingType, ServiceabilityEnterpriseResponse sarRes) {
		Map<String, Map<String, String>> salesContextData = new HashMap<String, Map<String, String>>();
		SalesCenterVO salesCenterVo = context.getSalesCenterVO();
		DigitalSalesContextVO digitalSalesContextVO = context.getDigitalSalesContextVO();
		String rentOrOwn = context.getRentOrOwn();
		String operatingCompany = context.getOperatingCompany();
		Map<String, String> context = new HashMap<String, String>();
		context.put("context.mode", "production");
		salesContextData.put("context", context);	

		Map<String, String> orderSource = new HashMap<String, String>();
		if (digitalSalesContextVO != null && digitalSalesContextVO.getValueByName("source") != null){
			orderSource.put("orderSource.source", digitalSalesContextVO.getValueByName("source"));
		}else{
			orderSource.put("orderSource.source", Constants.ORDER_SOURCE);	
		}


		logger.info("operatingCompany="+operatingCompany);
		if(operatingCompany != null){
			orderSource.put("orderSource.operatingCompany", operatingCompany);
		}

		if (salesCenterVo != null && salesCenterVo.getValueByName("DT Partner") != null){
			orderSource.put("orderSource.referrer",salesCenterVo.getValueByName("DT Partner"));
		}else if (digitalSalesContextVO != null && digitalSalesContextVO.getValueByName("referrerToSalesContext") != null){
			logger.info("referrerToSalesContext   " +digitalSalesContextVO.getValueByName("referrerToSalesContext"));
			orderSource.put("orderSource.referrer",digitalSalesContextVO.getValueByName("referrerToSalesContext"));
		}else{
			orderSource.put("orderSource.referrer", "utility");	
		}

		if (this.context.getChannel() != null && this.context.getChannel().equalsIgnoreCase(ProductSearchVO.CONCERT)){
			orderSource.put("orderSource.channel", "1");	
		}else{
			orderSource.put("orderSource.channel", "2");
		}
		if (salesCenterVo != null && salesCenterVo.getValueByName("simpleChoice.rateCode") != null) {
			orderSource.put("orderSource.rateCode", salesCenterVo.getValueByName("simpleChoice.rateCode"));
		}
		if (salesCenterVo != null && salesCenterVo.getValueByName("simpleChoice.eligibility") != null) {
			orderSource.put("orderSource.eligibility", salesCenterVo.getValueByName("simpleChoice.eligibility"));
		}
		if (salesCenterVo != null && salesCenterVo.getValueByName("ordersource.programId") != null) {
			orderSource.put("orderSource.programId", salesCenterVo.getValueByName("ordersource.programId"));
		}
		if (salesCenterVo != null && salesCenterVo.getValueByName("customer.providerData1") != null) {
			orderSource.put("orderSource.providerData1", salesCenterVo.getValueByName("customer.providerData1"));
		}
		if (salesCenterVo != null && salesCenterVo.getValueByName("customer.providerData2") != null) {
			orderSource.put("orderSource.providerData2", salesCenterVo.getValueByName("customer.providerData2"));
		}
		if (salesCenterVo != null && salesCenterVo.getValueByName("customer.providerData3") != null) {
			orderSource.put("orderSource.providerData3", salesCenterVo.getValueByName("customer.providerData3"));
		}
		if (salesCenterVo != null && salesCenterVo.getValueByName("customer.providerData4") != null) {
			orderSource.put("orderSource.providerData4", salesCenterVo.getValueByName("customer.providerData4"));
		}
		if (salesCenterVo != null && salesCenterVo.getValueByName("customer.providerData5") != null) {
			orderSource.put("orderSource.providerData5", salesCenterVo.getValueByName("customer.providerData5"));
		}
		if (salesCenterVo != null && salesCenterVo.getValueByName("ordersource.ID") != null) {
			orderSource.put("orderSource.ID", salesCenterVo.getValueByName("ordersource.ID"));
		}
		//In MDU Flow,we get ordersourceExID based on mdu property selection.This attribute build only in mdu flow
		if (salesCenterVo != null && !Utils.isBlank(salesCenterVo.getValueByName("orderSourceExternalId"))) {
			orderSource.put("ordersource.property.id", salesCenterVo.getValueByName("orderSourceExternalId"));
		}
		//priorEnrollSurge
		if (salesCenterVo != null && salesCenterVo.getValueByName("priorEnrollSurge") != null) {
			orderSource.put("orderSource.priorEnrollSurge", salesCenterVo.getValueByName("priorEnrollSurge"));
		}
		salesContextData.put("orderSource", orderSource);

		Map<String, String> consumer = new HashMap<String, String>();
		consumer.put("consumer.creditScore", Constants.CONSUMER_CREDITSCORE);
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
		if(sarRes != null){
			ServiceabilityResponse2 sre = (ServiceabilityResponse2)sarRes.getResponse();
			com.A.xml.se.v4.AddressType sreAddress = sre.getCorrectedAddress();
			dwelling.put("dwelling.stateOrProvince", sreAddress.getStateOrProvince());
		}
		logger.info("rentown"+rentOrOwn);
		dwelling.put("dwelling.rentOrOwn", rentOrOwn);
		salesContextData.put("dwelling", dwelling);

		Map<String, String> salesFlow = new HashMap<String, String>();
		if (salesCenterVo != null && salesCenterVo.getValueByName("callType") != null){
			salesFlow.put("salesFlow.referrer.callType", salesCenterVo.getValueByName("callType"));
		}
		if (salesCenterVo != null && salesCenterVo.getValueByName("dialogueType") != null){
			salesFlow.put("salesFlow.dialogueType", salesCenterVo.getValueByName("dialogueType"));
		}else{
			salesFlow.put("salesFlow.dialogueType", "core");
		}
		if (salesCenterVo != null && salesCenterVo.getValueByName("salesFlow.forceNonConfirm") != null){
			salesFlow.put("salesFlow.forceNonConfirm" ,salesCenterVo.getValueByName("salesFlow.forceNonConfirm"));
			logger.info("salesCenterVoForceNonConfirmFromPRM=" + salesCenterVo.getValueByName("salesFlow.forceNonConfirm"));
		}else{
			salesFlow.put("salesFlow.forceNonConfirm", "false");
			logger.info("salesCenterVoForceNonConfirmFromPRM_default");
		}
		salesContextData.put("salesFlow", salesFlow);

		Map<String, String> agent = new HashMap<String, String>();
		agent.put("agent.capability", "advanced");
		salesContextData.put("agent", agent);

		if (salesCenterVo != null && salesCenterVo.getValueByName("userGroup") != null) {
			logger.info("userGroup="+salesCenterVo.getValueByName("userGroup"));
			Map<String, String> product = new HashMap<String, String>();
			product.put("product.userGroup", salesCenterVo.getValueByName("userGroupProduct"));
			salesContextData.put("product", product);
		}
		if (salesCenterVo != null && !Utils.isBlank(salesCenterVo.getValueByName("creditScore"))){
			Map<String, String> map = new HashMap<String, String>();
			map.put("closingOffer.creditScore", salesCenterVo.getValueByName("creditScore"));
			salesContextData.put("closingOffer", map);
		}
		logger.info("salesContextData=" +salesContextData);
		return salesContextData;
	}
	public static Map<String, Map<String, String>> createSalesContext2(String dwellingType) {
		Map<String, Map<String, String>> salesContextData = new HashMap<String, Map<String, String>>();
		Map<String, String> context = new HashMap<String, String>();
		context.put("context.mode", "production");
		salesContextData.put("context", context);	

		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("orderSource.source", Constants.ORDER_SOURCE);
		orderSource.put("orderSource.referrer", "utility");	
		orderSource.put("orderSource.channel", "2");

		salesContextData.put("orderSource", orderSource);

		Map<String, String> consumer = new HashMap<String, String>();
		consumer.put("consumer.creditScore", Constants.CONSUMER_CREDITSCORE);
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
		logger.info("dwellingType=" + dwellingType);
		Map<String, String> dwelling = new HashMap<String, String>();
		dwelling.put("dwelling.dwellingType", dwellingType);
		//dwelling.put("dwelling.stateOrProvince", sreAddress.getStateOrProvince());
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

	protected ProviderCriteriaType2 createProviderCriteria() {
		ProviderCriteriaType2 criteria = new ProviderCriteriaType2();
		ProviderCriteriaEntityType2 entity = new ProviderCriteriaEntityType2();
		entity.setName("ATTSTI");
		ProviderNameValuePairType2 pair = new ProviderNameValuePairType2();
		pair.setName("salesCode");
		pair.setValueAttribute("KRAMERE");
		entity.getAttributes().add(pair);
		criteria.getProviders().add(entity);
		return criteria;
	}

	public List<ProductSummaryVO> getMerchandising(ProductList productList,
			List<ProductSummaryVO> remainingProductList, String guid, SalesContext salesContext) {
		String request = MerchandisingTemplateConstant.INSTANCE.getMerchandisingRequest(productList, sarRes.getGUID(), salesContext);
		MerchandisingClient<String> client = new MerchandisingClientJMS();
		EnterpriseResponseDocumentType response1 = client.send(request);
		MerchandisingResponseType merchandisedProductType = (MerchandisingResponseType) response1.getResponse();
		Map<String, Float> revenueCommissionsMap = new HashMap<String, Float>();
		logger.info("about_to_make_Gross_Comissionable_revenue_call_from_product_results_manager");
		if(context.getChannel() != null && context.getChannel().equals(ProductSearchVO.CONCERT) && grossCommissionableRevenueDao != null) {
			List<String> productExtIdList = new ArrayList<String>();
			for(ProductSummaryVO product: remainingProductList){
				productExtIdList.add(product.getExternalId());
			}
			revenueCommissionsMap = grossCommissionableRevenueDao.getRevCommissionsBySpIdStables(productExtIdList);
		}
		for ( MerchandisedProductType rs : merchandisedProductType.getProductList().getMerchandisedProduct()){
			for (ProductSummaryVO pr1:remainingProductList){
				boolean isClosingOfferProduct = false;
				if (pr1.getPromotionMetaDataList() != null && pr1.getPromotionMetaDataList().size() > 0){
					for(String metaData :pr1.getPromotionMetaDataList()){
						if (!Utils.isBlank(metaData) 
								&& (Constants.OFFERTYPE_CLOSINUTILITY.equalsIgnoreCase(metaData)
										|| Constants.OFFERTYPE_CLOSINGVIDEO.equalsIgnoreCase(metaData)
										|| Constants.OFFERTYPE_CLOSINGWARMTRANSFER.equalsIgnoreCase(metaData)
										|| (Constants.OFFERTYPE_CLOSINGRENTERS.equalsIgnoreCase(metaData)))){
							isClosingOfferProduct = true;
						}

					}
				}
				if (pr1.getExternalId().equals(rs.getExternalId())){
					//pr1.setScore(rs.getPriority());
					pr1.setSellable(rs.isSellable());
					if (!Utils.isEmpty(revenueCommissionsMap)&& revenueCommissionsMap.get(rs.getExternalId()) != null)
					{
						pr1.setScore(Double.valueOf(revenueCommissionsMap.get(rs.getExternalId())));
						pr1.setPoints(revenueCommissionsMap.get(rs.getExternalId()));
						//pr1.setRevCommissions(revenueCommissions.get(rs.getExternalId()).getRevCommission());	
					}
					else
					{
						pr1.setScore(0.0);
						pr1.setPoints(-1);
						//pr1.setRevCommissions(-1);	
					}
					if(isClosingOfferProduct){
						pr1.setScore(rs.getPriority());
					}
				}
			}
		}
		return remainingProductList;
	}

	public void getProductByIcon(boolean digital) {
		List<ProductSummaryVO> allProductList = this.context.getAllProductList();
		for (ProductSummaryVO product:allProductList){
			if(product.getProductCategory()==null){
				logger.debug("Product_category_is_null_for ::"+product.getExternalId());
			}else{
				//is this product having a well-defined category? if not, depend on the capability of the product
				String category = product.getProductCategory().toUpperCase();
				final String[] Categories = { ProductSearchIface.WATER, ProductSearchIface.WASTEREMOVAL,
						ProductSearchIface.HOMESECURITY,ProductSearchIface.ELECTRICITY, ProductSearchIface.NATURALGAS, 
						ProductSearchIface.TECH, ProductSearchIface.OFFERS, ProductSearchIface.APPLIANCEPROTECTION};
				List<String> categoryList = Arrays.asList(Categories);
				if(categoryList.contains(category)) {
					if(category.contains(ProductSearchIface.WATER)){
						product.setProductType(ProductSearchIface.WATER);
					}else if(category.contains(ProductSearchIface.ELECTRICITY)){
						product.setProductType(ProductSearchIface.ELECTRICITY);
					}else if(category.contains(ProductSearchIface.HOMESECURITY)){
						product.setProductType(ProductSearchIface.HOMESECURITY);
					}else if(category.contains(ProductSearchIface.NATURALGAS)){
						product.setProductType(ProductSearchIface.NATURALGAS);
					}else if(category.contains(ProductSearchIface.APPLIANCEPROTECTION)){
						product.setProductType(ProductSearchIface.APPLIANCEPROTECTION);
					}
				}
				//is this product a triple bundle or double bundle
				Map<String, String> capabilities = product.getCapabilityMap();
				if((capabilities.get("fiberDataDownSpeed") != null || //internet conditions
						capabilities.get("ipDslamDataDownSpeed") != null ||
						capabilities.get("wiredDataDownSpeed") != null ||
						capabilities.get("dialUpInternet") != null) && 
						(capabilities.get("voip") != null || //phone conditions
								capabilities.get("ipDslamVoip") != null ||
								capabilities.get("localPhone") != null ||
								capabilities.get("longDistancePhone") != null ||
								capabilities.get("wirelessPhone") != null) &&
								(capabilities.get("iptv") != null ||
										capabilities.get("ipDslamIptv") != null ||
										capabilities.get("analogCable") != null ||
										capabilities.get("digitalCable") != null ||
										capabilities.get("satellite") != null)) {
					//this product is a triple play 				
					product.setProductType(ProductSearchIface.TRIPLE_PLAY);
					if (digital) {
						product.setProductType(ProductSearchIface.TRIPLE_PLAY_DIGITAL);
					}
					context.addProduct(ProductSearchIface.TRIPLE_PLAY,product);	

				} else if(((capabilities.get("fiberDataDownSpeed") != null || 
						capabilities.get("ipDslamDataDownSpeed") != null ||
						capabilities.get("wiredDataDownSpeed") != null ||
						capabilities.get("dialUpInternet") != null) && 
						(capabilities.get("voip") != null || //phone conditions
								capabilities.get("ipDslamVoip") != null ||
								capabilities.get("localPhone") != null ||
								capabilities.get("longDistancePhone") != null ||
								capabilities.get("wirelessPhone") != null) ) ||

								(capabilities.get("fiberDataDownSpeed") != null || 
										capabilities.get("ipDslamDataDownSpeed") != null ||
										capabilities.get("wiredDataDownSpeed") != null ||
										capabilities.get("dialUpInternet") != null) &&
										(capabilities.get("iptv") != null ||
												capabilities.get("ipDslamIptv") != null ||
												capabilities.get("analogCable") != null ||
												capabilities.get("digitalCable") != null ||
												capabilities.get("satellite") != null) ||

												(capabilities.get("voip") != null || 
														capabilities.get("ipDslamVoip") != null ||
														capabilities.get("localPhone") != null ||
														capabilities.get("longDistancePhone") != null ||
														capabilities.get("wirelessPhone") != null) &&
														(capabilities.get("iptv") != null ||
																capabilities.get("ipDslamIptv") != null ||
																capabilities.get("analogCable") != null ||
																capabilities.get("digitalCable") != null ||
																capabilities.get("satellite") != null)									
				) {
					//this product is a DOUBLE_PLAY 	
					product.setProductType(ProductSearchIface.DOUBLE_PLAY);
					context.addProduct(ProductSearchIface.DOUBLE_PLAY,product);	
					if (digital) {
						if((capabilities.get("fiberDataDownSpeed") != null || 
								capabilities.get("ipDslamDataDownSpeed") != null ||
								capabilities.get("wiredDataDownSpeed") != null ||
								capabilities.get("dialUpInternet") != null) &&
								(capabilities.get("iptv") != null ||
										capabilities.get("ipDslamIptv") != null ||
										capabilities.get("analogCable") != null ||
										capabilities.get("digitalCable") != null ||
										capabilities.get("satellite") != null) ) {	
							product.setProductType(ProductSearchIface.DOUBLE_PLAY_DIGITAL_1);
						}
						if((capabilities.get("fiberDataDownSpeed") != null || 
								capabilities.get("ipDslamDataDownSpeed") != null ||
								capabilities.get("wiredDataDownSpeed") != null ||
								capabilities.get("dialUpInternet") != null) && 
								(capabilities.get("voip") != null || //phone conditions
										capabilities.get("ipDslamVoip") != null ||
										capabilities.get("localPhone") != null ||
										capabilities.get("longDistancePhone") != null ||
										capabilities.get("wirelessPhone") != null) ) {	
							product.setProductType(ProductSearchIface.DOUBLE_PLAY_DIGITAL_2);
						}						
						if((capabilities.get("voip") != null || 
								capabilities.get("ipDslamVoip") != null ||
								capabilities.get("localPhone") != null ||
								capabilities.get("longDistancePhone") != null ||
								capabilities.get("wirelessPhone") != null) &&
								(capabilities.get("iptv") != null ||
										capabilities.get("ipDslamIptv") != null ||
										capabilities.get("analogCable") != null ||
										capabilities.get("digitalCable") != null ||
										capabilities.get("satellite") != null)	 ) {	
							product.setProductType(ProductSearchIface.DOUBLE_PLAY_DIGITAL_3);
						}
					}

				} else if((capabilities.get("fiberDataDownSpeed") != null || //internet conditions
						capabilities.get("ipDslamDataDownSpeed") != null ||
						capabilities.get("wiredDataDownSpeed") != null ||
						capabilities.get("dialUpInternet") != null)) {

					product.setProductType(ProductSearchIface.INTERNET);
					context.addProduct(ProductSearchIface.INTERNET,product);	


				} else if((capabilities.get("voip") != null || //phone conditions
						capabilities.get("ipDslamVoip") != null ||
						capabilities.get("localPhone") != null ||
						capabilities.get("longDistancePhone") != null ||
						capabilities.get("wirelessPhone") != null)) {

					product.setProductType(ProductSearchIface.PHONE);
					context.addProduct(ProductSearchIface.PHONE,product);	

				} else if((capabilities.get("iptv") != null ||
						capabilities.get("ipDslamIptv") != null ||
						capabilities.get("analogCable") != null ||
						capabilities.get("digitalCable") != null ||
						capabilities.get("satellite") != null)) {
					//this product is a triple play 				
					product.setProductType(ProductSearchIface.VIDEO);
					context.addProduct(ProductSearchIface.VIDEO,product);	
				}
			}
		}
	}

	public List<ProductSummaryVO> populatePowerPitchData(List<ProductSummaryVO> powerPitchSyntheticBundleList) {
		powerPitchList = new ArrayList<ProductSummaryVO>();
		ProductSummaryVO[] powerPitchArray = new ProductSummaryVO[10];
		ProductSummaryVO dishRTISatelliteProduct = null;
		ProductSummaryVO dishInternalSatelliteProduct = null;
		List<ProductSummaryVO> allProductList = this.context.getAllProductList();
		boolean populateHughesnetProducts = populateHughesNetProducts();
		for (ProductSummaryVO product:allProductList){
			if((product.getCapabilityMap() != null)) {
				if(!populateHughesnetProducts && product.getProviderExternalId() != null && (product.getProviderExternalId().equalsIgnoreCase(Constants.HUGHESNET) || product.getProviderExternalId().equalsIgnoreCase("15500581"))){
					continue;
				}
				if(ifLatinoNewFilter(product.getPromotionMetaDataList())){
					continue;
				}
				if(product.getProviderExternalId() != null && (product.getProviderExternalId().equals("27010360")
						|| product.getProviderExternalId().equals("18063259"))
						&& product.getCapabilityMap().get("satellite") != null ) {
					if(product.getProviderExternalId().equals("27010360")){
						if (dishRTISatelliteProduct == null) {
							dishRTISatelliteProduct = product;
						} else { 
							//compare them for better score
							if(dishRTISatelliteProduct.getScore()< product.getScore()) {
								dishRTISatelliteProduct = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
					} else {
						if (dishInternalSatelliteProduct == null) {
							dishInternalSatelliteProduct = product;
						} else { 
							//compare them for better score
							if(dishInternalSatelliteProduct.getScore()< product.getScore()) {
								dishInternalSatelliteProduct = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
						//dishInternalSatellite.setDishProduct(true);						
					}
				}
				//is product a FiberTriplePlay product?
				if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
								product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
										product.getCapabilityMap().get("dialUpInternet") != null) && 
						(product.getCapabilityMap().get("voip") != null || //phone conditions
								product.getCapabilityMap().get("ipDslamVoip") != null ||
										product.getCapabilityMap().get("localPhone") != null ||
												product.getCapabilityMap().get("longDistancePhone") != null ||
														product.getCapabilityMap().get("wirelessPhone") != null) &&
								(product.getCapabilityMap().get("iptv") != null ||
										product.getCapabilityMap().get("ipDslamIptv") != null ||
												product.getCapabilityMap().get("analogCable") != null ||
														product.getCapabilityMap().get("digitalCable") != null ||
														product.getCapabilityMap().get("satellite") != null)) {
					//this product is a fiber triple play 
					//compare the score of already found fiber triple play product and retain the higher score product
					//in powerpitch data structure
					if (telcoProvidersList != null && (telcoProvidersList.contains(product.getParentExternalId()) 
							|| telcoProvidersList.contains(product.getProviderExternalId()))){
						if (powerPitchArray[0] == null) {
							product.setProductType(ProductSearchIface.TRIPLE_PLAY);
							getProductWithProductBonusPoints(product);
							powerPitchArray[0] = product;
						} else { 
							//compare them for better score
							if(powerPitchArray[0].getScore()< product.getScore()) {
								product.setProductType(ProductSearchIface.TRIPLE_PLAY);
								getProductWithProductBonusPoints(product);
								powerPitchArray[0] = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}	
					}
					else if (cableProvidersList != null && (cableProvidersList.contains(product.getParentExternalId()) 
							|| cableProvidersList.contains(product.getProviderExternalId()))){
						if (powerPitchArray[1] == null) {
							product.setProductType(ProductSearchIface.TRIPLE_PLAY);
							getProductWithProductBonusPoints(product);
							powerPitchArray[1] = product;
						} else { 
							//compare them for better score
							if(powerPitchArray[1].getScore()< product.getScore()) {
								product.setProductType(ProductSearchIface.TRIPLE_PLAY);
								getProductWithProductBonusPoints(product);
								powerPitchArray[1] = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
					}
				}
				else if (product.getProviderExternalId()!= null && (product.getProviderExternalId().equalsIgnoreCase(Constants.HUGHESNET) || product.getProviderExternalId().equalsIgnoreCase("15500581"))){
					if (powerPitchArray[3] == null) {
						getProductWithProductBonusPoints(product);
						powerPitchArray[3] = product;
					} 
					else { 
						//compare them for better score
						if(powerPitchArray[3].getScore()< product.getScore()) {
							getProductWithProductBonusPoints(product);
							powerPitchArray[3] = product;
						} 
						else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
				else if ((product.getCapabilityMap().get("wiredDataDownSpeed") != null || 
						product.getCapabilityMap().get("fiberDataDownSpeed") != null || 
						product.getCapabilityMap().get("dialUpInternet") != null ||
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ) &&
						(product.getCapabilityMap().get("localPhone") != null || 
						product.getCapabilityMap().get("voip") != null || 
						product.getCapabilityMap().get("longDistancePhone") != null ||
						product.getCapabilityMap().get("wirelessPhone") != null ||
						product.getCapabilityMap().get("ipDslamVoip") != null)&& 
						product.getCapabilityMap().get("analogCable") == null && 
						product.getCapabilityMap().get("digitalCable") == null &&
						product.getCapabilityMap().get("satellite") == null && 
						product.getCapabilityMap().get("ipDslamIptv") == null && 
						product.getCapabilityMap().get("iptv") == null){
					//telco double play wiredDataDownSpeed localPhone
					if (telcoProvidersList != null && (telcoProvidersList.contains(product.getParentExternalId()) 
							|| telcoProvidersList.contains(product.getProviderExternalId()))){
						if (powerPitchArray[3] == null) {
							product.setProductType(ProductSearchIface.DOUBLE_PLAY);
							getProductWithProductBonusPoints(product);
							powerPitchArray[3] = product;
						} 
						else { 
							//compare them for better score
							if(powerPitchArray[3].getScore()< product.getScore()) {
								product.setProductType(ProductSearchIface.DOUBLE_PLAY);
								getProductWithProductBonusPoints(product);
								powerPitchArray[3] = product;
							} 
							else {
								//the earlier product itself should be retained in this slot
							}
						}
					}
				}
				else if ((product.getCapabilityMap().get("wiredDataDownSpeed") != null && (product.getCapabilityMap().get("analogCable") != null || 
						   product.getCapabilityMap().get("digitalCable") != null))
						||(product.getCapabilityMap().get("wiredDataDownSpeed") != null &&
								product.getCapabilityMap().get("localPhone") != null)
						||(product.getCapabilityMap().get("localPhone") != null &&
										(product.getCapabilityMap().get("analogCable") != null || 
										product.getCapabilityMap().get("digitalCable") != null))){
					//cable double play
					if (cableProvidersList != null && (cableProvidersList.contains(product.getParentExternalId()) 
							|| cableProvidersList.contains(product.getProviderExternalId()))){
						if (powerPitchArray[4] == null) {
							product.setProductType(ProductSearchIface.DOUBLE_PLAY);
							getProductWithProductBonusPoints(product);
							powerPitchArray[4] = product;
						} 
						else { 
							//compare them for better score
							if(powerPitchArray[4].getScore()< product.getScore()) {
								product.setProductType(ProductSearchIface.DOUBLE_PLAY);
								getProductWithProductBonusPoints(product);
								powerPitchArray[4] = product;
							} 
							else {
								//the earlier product itself should be retained in this slot
							}
						}
					}
				}
				if(product.getProviderExternalId() != null  && product.getCapabilityMap().get("satellite") != null && (Constants.ATTV6.equals(product.getProviderExternalId())||product.getProviderExternalId().equals(Constants.DIRECTV))) {
					if (powerPitchArray[5] == null) {
						getProductWithProductBonusPoints(product);
						powerPitchArray[5] = product;
					} else { 
						//compare them for better score
						if(powerPitchArray[5].getScore()< product.getScore()) {
							getProductWithProductBonusPoints(product);
							powerPitchArray[5] = product;
						} else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
				if(ProductSearchIface.HOMESECURITY.equalsIgnoreCase(product.getProductCategory())) {
					if (powerPitchArray[6] == null) {
						getProductWithProductBonusPoints(product);
						powerPitchArray[6] = product;
					} else { 
						//compare them for better score
						if(powerPitchArray[6].getScore()< product.getScore()) {
							getProductWithProductBonusPoints(product);
							powerPitchArray[6] = product;
						} else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
			}
		}
		
		if(dishRTISatelliteProduct != null){
			powerPitchArray[2] = dishRTISatelliteProduct;
		}
		else if (dishInternalSatelliteProduct != null) {
			powerPitchArray[2] = dishInternalSatelliteProduct;
		}	
		
		int j = 0;
		if(powerPitchSyntheticBundleList != null && powerPitchSyntheticBundleList.size()>0){
			powerPitchList.addAll(powerPitchSyntheticBundleList);
			if (telcoProvidersList != null && (telcoProvidersList.contains(powerPitchSyntheticBundleList.get(0).getPairedProduct().getParentExternalId()) 
					|| telcoProvidersList.contains(powerPitchSyntheticBundleList.get(0).getPairedProduct().getProviderExternalId()))){
				powerPitchArray[3] = null;
			}
			j++;
		}
		for (int i=0; i<10; i++) {
			logger.debug("The raw power pitch product is:" + i + "   powerPitchArray[i]    " +powerPitchArray[i]);
			if(j != 3) {
				if(powerPitchArray[i] != null) {
					j++;
					powerPitchList.add(powerPitchArray[i]);
				} 
			}			
		}
		return powerPitchList;
	}

	public ProductSearchIface getProdSearchVO() {
		return context;
	}

	/**
	 * Returns formatted details for the given external ProductId and Parterner/Provider Id
	 * @param providerId
	 * @param productId
	 * @return
	 */
	public ProductDetailsVO getProduct(String providerExtId, String productExtId) {

		logger.info("Begin_get_Product+providerExtId=" +providerExtId + ",productExtId=" +productExtId);
		ProductInfoType prodInfo = getProductInfo(providerExtId, productExtId);

		ProductType product = prodInfo.getProduct();
		ProductFormatter frmtr = context.getFormatter(product.getProvider().getSource());
		ProductDetailsVO productVo = frmtr.format(prodInfo);
		logger.info("Returning_Product_Details=" + productVo);
		return productVo;
	}

	private void checkMetaDataAndAddSaversOrUtilityOfferToMap(ProductSummaryVO productVo) {
		boolean isUtilityOffer = false; 
		if (productVo.getPromotionMetaDataList() != null && productVo.getPromotionMetaDataList().size() > 0){
			for(String metaData :productVo.getPromotionMetaDataList()){
				if (metaData != null &&						
						metaData.equalsIgnoreCase(SIMPLECHOICE)){
					suppliersList.add(productVo.getProviderName());
					boolean isProductExists = false;
					if (simpleChoiceOffersMap.size() == 0){
						List<ProductSummaryVO> list = new ArrayList<ProductSummaryVO>();
						list.add(productVo);
						simpleChoiceOffersMap.put("SIMPLECHOICE", list);	
					}else{
						List<ProductSummaryVO> salesMdo = simpleChoiceOffersMap.get("SIMPLECHOICE");
						for (ProductSummaryVO newMdo :salesMdo){
							if (newMdo != null && newMdo.getExternalId() != null 
									&& !newMdo.getExternalId().equalsIgnoreCase(productVo.getExternalId())){
								isProductExists = true;
								break;
							}	
						}
						if (isProductExists){
							simpleChoiceOffersMap.get("SIMPLECHOICE").add(productVo);
						}
					}
				}else if (metaData != null &&						
						(metaData.equalsIgnoreCase(RESIDENTIAL) || metaData.equalsIgnoreCase(COMMERCIAL)))
				{

					boolean isProductExists = false;
					if (pecoOffersMap.size() == 0){
						logger.info("building_pecoOffersMap");
						List<ProductSummaryVO> productSummaryVOList = new ArrayList<ProductSummaryVO>();
						productSummaryVOList.add(productVo);
						pecoOffersMap.put("PECO", productSummaryVOList);	
					}else{

						List<ProductSummaryVO> productSummaryVOList = pecoOffersMap.get("PECO");
						for (ProductSummaryVO productSummaryVO : productSummaryVOList)
						{
							if ( productSummaryVO.getExternalId() != null 
									&& productSummaryVO.getExternalId().equalsIgnoreCase(productVo.getExternalId())){
								isProductExists = true;
								break;
							}	
						}
						if ( !isProductExists )
						{
							pecoOffersMap.get("PECO").add(productVo);
						}
					}
				}else if (metaData != null &&
						metaData.equalsIgnoreCase("OFFER_TYPE=SAVERS")){
					List<ProductSummaryVO> list = new ArrayList<ProductSummaryVO>();
					boolean isProductExists = false;
					if (saversOfferMap.size() == 0){
						list.add(productVo);
						saversOfferMap.put("SAVERS", list);	
					}else{
						List<ProductSummaryVO> salesMdo = saversOfferMap.get("SAVERS");
						for (ProductSummaryVO newMdo :salesMdo){
							if (newMdo != null && newMdo.getExternalId() != null 
									&& !newMdo.getExternalId().equalsIgnoreCase(productVo.getExternalId())){
								isProductExists = true;
								break;
							}	
						}
						if (isProductExists){
							saversOfferMap.get("SAVERS").add(productVo);
						}
					}
				}else if (metaData != null &&
						metaData.equalsIgnoreCase("OFFER_TYPE=UTILITY")){
					isUtilityOffer = true;
					break;
				}
			}
			if (isUtilityOffer){
				if(context.isPartnerSpecificMatchReqForUtility()){
					for(String metaData :productVo.getPromotionMetaDataList()){
						String productData = null;
						String[] productDataVal = null;
						if (metaData != null &&  metaData.contains("=")){
							productDataVal = metaData.split("=");
							if (productDataVal[0] != null && productDataVal[0].toString().equalsIgnoreCase("PS_PRODUCT")){
								productData = productDataVal[1];            
							}
						}else{
							productData = metaData;
						}
						for (Entry<String,String> entry : context.getPartnerSpecificDataMap().entrySet()){
							if (entry.getKey().equalsIgnoreCase(productData)){
								if (utilityOffersMap.size() == 0){
									List<ProductSummaryVO> list = new ArrayList<ProductSummaryVO>();
									list.add(productVo);
									utilityOffersMap.put("UTILITY", list);	
								}else{
									List<ProductSummaryVO> salesMdo = utilityOffersMap.get("UTILITY");
									boolean isProductExists = false;
									for (ProductSummaryVO newMdo :salesMdo){
										if (newMdo != null && newMdo.getExternalId() != null 
												&& !newMdo.getExternalId().equalsIgnoreCase(productVo.getExternalId())){
											isProductExists = true;
											break;
										}	
									}
									if (isProductExists){
										utilityOffersMap.get("UTILITY").add(productVo);
									}
								}	
							}
						}
					}
				}
				else{
					if (utilityOffersMap.size() == 0){
						List<ProductSummaryVO> list = new ArrayList<ProductSummaryVO>();
						list.add(productVo);
						utilityOffersMap.put("UTILITY", list);	
					}else{
						List<ProductSummaryVO> salesMdo = utilityOffersMap.get("UTILITY");
						boolean isProductExists = false;
						for (ProductSummaryVO newMdo :salesMdo){
							if (newMdo != null && newMdo.getExternalId() != null 
									&& !newMdo.getExternalId().equalsIgnoreCase(productVo.getExternalId())){
								isProductExists = true;
								break;
							}	
						}
						if (isProductExists){
							utilityOffersMap.get("UTILITY").add(productVo);
						}
					}
				}
			}
		}
	}

	public ProductInfoType getProductInfo(String providerExtId,String productExtId) {
		String GUID = context.getGUID();
		ProductInfoType prodInfo = ProductService.INSTANCE.getProduct(providerExtId,productExtId,GUID,getSalesContext());
		return prodInfo;
	}

	public HashMap<String, String> getRealTimeStatusMap() {
		return realTimeStatusMap;
	}

	public Map<String, Object> isValidAddress(String completeAddress, SalesContext salesContext, String serviceAddrType,String se2TransactionType){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		//	sarRes = ESEService.INSTANCE.getServiceabilityResponse(completeAddress, salesContext, createProviderCriteria());
		setServiceabilityTransactionType(se2TransactionType);
		logger.info("Se2 Started");
		sarRes = ESEService.INSTANCE.getServiceabilityResponse(completeAddress, salesContext, createProviderCriteria(),se2TransactionType,false);
		logger.info("Se2 End");
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
							multipleAddress = new ArrayList<String>();
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
								multipleAddress.add(capitalizedAddress.toString());
								if (!Utils.isBlank(address.getLine2())){
									multipleAddressLine2Map.put(capitalizedAddress.toString().toLowerCase(), address.getLine2());	
								}
								logger.info("multipleAddressLine2Map="+multipleAddressLine2Map);
							}
							multipleAddress.add("None of the above");
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
			isZipFallBack = true;
			returnMap.put("Address Found - Proceed to next screen", sarRes);
		}
		logger.info("ends_isValidAddress");
		return returnMap;
	}


	private boolean checkQwestProductCapabilities(ProductSummaryVO productVo) {
		boolean isValid = true;
		if (productVo != null && productVo.getProviderExternalId() != null && productVo.getProviderExternalId().equals(QWESTPRODUCT)){
			if (productVo.getCapabilityMap() != null){
				for (Entry<String, String> entry : productVo.getCapabilityMap().entrySet()) {
					if (qwestCapibilitiesList != null && !qwestCapibilitiesList.contains(entry.getKey())){
						isValid = false;
						return isValid;
					}
				}	
			}
			else{
				isValid = false;
				return isValid;
			}
			logger.info("qwestCapibilitiesValueMap="+qwestCapibilitiesValueMap);
			if (qwestCapibilitiesValueMap != null && qwestCapibilitiesValueMap.size() > 0){
				if (productVo.getPromotionMetaDataList() != null && productVo.getPromotionMetaDataList().size() > 0){
					for(String metaData :productVo.getPromotionMetaDataList()){
						if (metaData.contains("=")){
							String[] str =	metaData.split("=");
							for (Entry<String,String> entry : qwestCapibilitiesValueMap.entrySet()){
								String key = entry.getKey().toUpperCase() + "AVAIL";
								if (str[0].toString().equalsIgnoreCase(key) &&
										Long.parseLong(entry.getValue()) < Long.parseLong(str[1]) ){
									isValid = false;
									return isValid;
								}
							}
						}
					}
				}
			}
		}
		return isValid;
	}

	protected ProviderType addQwestInternalProviderToList() {
		ProviderType provider = new ProviderType();
		CapabilityList capabilityList = new CapabilityList();
		CapabilityType capability = new CapabilityType();
		capabilityList.getCapability().add(capability);
		provider.setCapabilityList(capabilityList);
		provider.setExternalId(QWESTPRODUCT);
		provider.setName("QWEST");
		ProviderSourceType source = new ProviderSourceType();
		source.setDatasource("QWEST");
		source.setValue(ProviderSourceBaseType.INTERNAL);
		provider.setSource(source);
		ProviderType parent = new ProviderType();
		parent.setName("QWEST");
		parent.setExternalId(QWESTPRODUCT);
		provider.setParent(parent);
		return provider;
	}

	private void buildCategoryWiseProviderData( String category,ProductSummaryVO product )
	{
		if(categoryWiseProductNameAndExternalId.get(category)!=null)
		{
			Map<String, String> providerNameAndExtId = categoryWiseProductNameAndExternalId.get(category);
			if( providerNameAndExtId.get(product.getProviderExternalId()) ==  null ){
				providerNameAndExtId.put(product.getProviderExternalId(), product.getProviderName());
			}
		}
		else
		{
			Map<String, String> providerNameAndExtId = new HashMap<String, String>();
			providerNameAndExtId.put(product.getProviderExternalId(), product.getProviderName());
			categoryWiseProductNameAndExternalId.put(category, providerNameAndExtId);
		}
	}

	public Map<String, List<ProductSummaryVO>> productByIconMapData(List<ProductSummaryVO> allProductList) 
	{
		productByIconMap = new HashMap<String,List<ProductSummaryVO>>();

		if(getSyntheticBundlesList() != null && !getSyntheticBundlesList().isEmpty()){
			this.context.sortProducts(syntheticBundlesList, "totalPoints");
			for (ProductSummaryVO mbProduct:getSyntheticBundlesList()){
				if(mbProduct.getProductCategory()!= null && mbProduct.getProductCategory().equals(TRIPLE_PLAY)){
					//this product is a triple play 				
					if (productByIconMap != null && productByIconMap.get(TRIPLE_PLAY) == null){
					//	mbProduct.setProductType(TRIPLE_PLAY); 
						prList = new ArrayList<ProductSummaryVO>();
						prList.add(mbProduct);
						productByIconMap.put(TRIPLE_PLAY,prList);	
					}else{
						mbProduct.setProductType(TRIPLE_PLAY);
						if(!isMixedBundleProductFoundInList(mbProduct, mbProduct.getPairedProduct(), productByIconMap.get(TRIPLE_PLAY))){
							productByIconMap.get(TRIPLE_PLAY).add(mbProduct);
						}
					}
					buildCategoryWiseProviderData( TRIPLE_PLAY, mbProduct );
				}
				else if(mbProduct.getProductCategory()!= null && mbProduct.getProductCategory().equals(DOUBLE_PLAY)){
					if (productByIconMap != null && productByIconMap.get(DOUBLE_PLAY) == null){
						//mbProduct.setProductType(DOUBLE_PLAY);
						prList = new ArrayList<ProductSummaryVO>();
						prList.add(mbProduct);
						productByIconMap.put(DOUBLE_PLAY,prList);	
					}else{
						mbProduct.setProductType(DOUBLE_PLAY);
						if(!isMixedBundleProductFoundInList(mbProduct, mbProduct.getPairedProduct(), productByIconMap.get(DOUBLE_PLAY))){
							productByIconMap.get(DOUBLE_PLAY).add(mbProduct);
						}
					}
					buildCategoryWiseProviderData( DOUBLE_PLAY, mbProduct );
				}
				if (productByIconMap != null && productByIconMap.get(MIXEDBUNDLES) == null){
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(mbProduct);
					productByIconMap.put(MIXEDBUNDLES,prList);
				}
				else{
					if(!isMixedBundleProductFoundInList(mbProduct, mbProduct.getPairedProduct(), productByIconMap.get("MIXEDBUNDLES"))){
						productByIconMap.get("MIXEDBUNDLES").add(mbProduct);
					}
				}
			}
			if(productByIconMap.get("MIXEDBUNDLES")!= null){
				logger.info("MixedBundlesIconMapSize ="+productByIconMap.get("MIXEDBUNDLES").size());
			}
			if(productByIconMap.get("TRIPLE_PLAY")!= null){
				logger.info("MixedBundlesTriplePlayIconMapSize ="+productByIconMap.get("TRIPLE_PLAY").size());
			}
			if(productByIconMap.get("DOUBLE_PLAY")!= null){
				logger.info("MixedBundlesDoublePlayIconMapSize ="+productByIconMap.get("DOUBLE_PLAY").size());
			}
			
		}
		
		boolean populateHughesnetProducts = populateHughesNetProducts();
		for (ProductSummaryVO product:allProductList){
			if(!populateHughesnetProducts && product.getProviderExternalId() != null && (product.getProviderExternalId().equalsIgnoreCase(Constants.HUGHESNET) || product.getProviderExternalId().equalsIgnoreCase("15500581"))){
				continue;
			}
			//is this product having a well-defined category? if not, depend on the capability of the product
			if(WATER.equalsIgnoreCase(product.getProductCategory())) {
				if (productByIconMap != null && productByIconMap.get(WATER) == null){
					product.setProductType(WATER);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(WATER,prList);	
				}else{
					product.setProductType(WATER);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(WATER))){
						productByIconMap.get(WATER).add(product);
					}
				}
				buildCategoryWiseProviderData( WATER, product );
			} else if(APPLIANCEPROTECTION.equalsIgnoreCase(product.getProductCategory())) {
				if (productByIconMap != null && productByIconMap.get(APPLIANCEPROTECTION) == null){
					product.setProductType(APPLIANCEPROTECTION);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(APPLIANCEPROTECTION,prList);	
				}else{
					product.setProductType(APPLIANCEPROTECTION);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(APPLIANCEPROTECTION))){
						productByIconMap.get(APPLIANCEPROTECTION).add(product);
					}
				}
				buildCategoryWiseProviderData( APPLIANCEPROTECTION, product );
			} else if(WASTEREMOVAL.equalsIgnoreCase(product.getProductCategory())) {
				if (productByIconMap != null && productByIconMap.get(WASTEREMOVAL) == null){
					product.setProductType(WASTEREMOVAL);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(WASTEREMOVAL,prList);	
				}else{
					product.setProductType(WASTEREMOVAL);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(WASTEREMOVAL))){
						productByIconMap.get(WASTEREMOVAL).add(product);
					}
				}
				buildCategoryWiseProviderData( WASTEREMOVAL, product );
			} else if(ELECTRICITY.equalsIgnoreCase(product.getProductCategory())) {
				if (productByIconMap != null && productByIconMap.get(ELECTRICITY) == null){
					product.setProductType(ELECTRICITY);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(ELECTRICITY,prList);	
				}else{
					product.setProductType(ELECTRICITY);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(ELECTRICITY))){
						productByIconMap.get(ELECTRICITY).add(product);
					}
				}
				buildCategoryWiseProviderData( ELECTRICITY, product );
			} else if(HOMESECURITY.equalsIgnoreCase(product.getProductCategory())) {
				if (productByIconMap != null && productByIconMap.get(HOMESECURITY) == null){
					product.setProductType(HOMESECURITY);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(HOMESECURITY,prList);	
				}else{
					product.setProductType(HOMESECURITY);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(HOMESECURITY))){
						productByIconMap.get(HOMESECURITY).add(product);
					}
				}
				buildCategoryWiseProviderData( HOMESECURITY, product );
			} else if(NATURALGAS.equalsIgnoreCase(product.getProductCategory())) {
				if (productByIconMap != null && productByIconMap.get(NATURALGAS) == null){
					product.setProductType(NATURALGAS);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(NATURALGAS,prList);	
				}else{
					product.setProductType(NATURALGAS);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(NATURALGAS))){
						productByIconMap.get(NATURALGAS).add(product);
					}
				}
				buildCategoryWiseProviderData( NATURALGAS, product );
			} else if(TECH.equalsIgnoreCase(product.getProductCategory())) {
				if (productByIconMap != null && productByIconMap.get(TECH) == null){
					product.setProductType(TECH);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(TECH,prList);	
				}else{
					product.setProductType(TECH);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(TECH))){
						productByIconMap.get(TECH).add(product);
					}
				}
				buildCategoryWiseProviderData( TECH, product );
			} else if(OFFERS.equalsIgnoreCase(product.getProductCategory())) {
				if (productByIconMap != null && productByIconMap.get(OFFERS) == null){
					product.setProductType(OFFERS);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(OFFERS,prList);	
				}else{
					product.setProductType(OFFERS);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(OFFERS))){
						productByIconMap.get(OFFERS).add(product);
					}
				}
			}else if(BUNDLES.equalsIgnoreCase(product.getProductCategory())) {
				if (productByIconMap != null && productByIconMap.get(BUNDLES) == null){
					product.setProductType(BUNDLES);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(BUNDLES,prList);	
				}else{
					product.setProductType(BUNDLES);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(BUNDLES))){
						productByIconMap.get(BUNDLES).add(product);
					}
				}
				buildCategoryWiseProviderData( BUNDLES, product );
			}
			if (product.getPromotionMetaDataList() != null && product.getPromotionMetaDataList().size() > 0){
				for(String metaData :product.getPromotionMetaDataList()){
					if (metaData != null && metaData.equalsIgnoreCase(ASIS)){
						if (productByIconMap != null && productByIconMap.get(ASIS) == null){
							product.setProductType(ASIS);
							getProductWithProductBonusPoints(product);
							prList = new ArrayList<ProductSummaryVO>();
							prList.add(product);
							productByIconMap.put(ASIS,prList); 
						}else{
							product.setProductType(ASIS);
							getProductWithProductBonusPoints(product);
							if(!isProductFoundInList(product, productByIconMap.get(ASIS))){
								productByIconMap.get(ASIS).add(product);
							}
						}
						buildCategoryWiseProviderData( ASIS, product );
					}
				}
			}
			
			if(product.getMetadata() != null && product.getMetadata().get(TRIPLE_PLAY) != null){
				//this product is a triple play 				
				if (productByIconMap != null && productByIconMap.get(TRIPLE_PLAY) == null){
					product.setProductType(TRIPLE_PLAY);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(TRIPLE_PLAY,prList);	
				}else{
					product.setProductType(TRIPLE_PLAY);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(TRIPLE_PLAY))){
						productByIconMap.get(TRIPLE_PLAY).add(product);
					}
				}
				buildCategoryWiseProviderData( TRIPLE_PLAY, product );
			}
			if(product.getMetadata() != null && product.getMetadata().get(DOUBLE_PLAY) != null){
				if (productByIconMap != null && productByIconMap.get(DOUBLE_PLAY) == null){
					product.setProductType(DOUBLE_PLAY);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(DOUBLE_PLAY,prList);	
				}else{
					product.setProductType(DOUBLE_PLAY);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(DOUBLE_PLAY))){
						productByIconMap.get(DOUBLE_PLAY).add(product);
					}
				}
				buildCategoryWiseProviderData( DOUBLE_PLAY, product );
			}

			//is this product a triple bundle or double bundle
			if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
					product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
					product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
					product.getCapabilityMap().get("dialUpInternet") != null) && 
					(product.getCapabilityMap().get("voip") != null || //phone conditions
							product.getCapabilityMap().get("ipDslamVoip") != null ||
							product.getCapabilityMap().get("localPhone") != null ||
							product.getCapabilityMap().get("longDistancePhone") != null ||
							product.getCapabilityMap().get("wirelessPhone") != null) &&
							(product.getCapabilityMap().get("iptv") != null ||
									product.getCapabilityMap().get("ipDslamIptv") != null ||
									product.getCapabilityMap().get("analogCable") != null ||
									product.getCapabilityMap().get("digitalCable") != null ||
									product.getCapabilityMap().get("satellite") != null)) {
				//this product is a triple play 				
				if (productByIconMap != null && productByIconMap.get(TRIPLE_PLAY) == null){
					product.setProductType(TRIPLE_PLAY);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(TRIPLE_PLAY,prList);	
				}else{
					product.setProductType(TRIPLE_PLAY);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(TRIPLE_PLAY))){
						productByIconMap.get(TRIPLE_PLAY).add(product);
					}
				}
				if(product.getProviderExternalId() != null && product.getProviderExternalId().equalsIgnoreCase("32937483")){
					this.setFrontierVideoAvailable(true);
					this.setFrontierInternetAvailable(true);
				}
				buildCategoryWiseProviderData( TRIPLE_PLAY, product );
			} else if(((product.getCapabilityMap().get("fiberDataDownSpeed") != null || 
					product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
					product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
					product.getCapabilityMap().get("dialUpInternet") != null) && 
					(product.getCapabilityMap().get("voip") != null || //phone conditions
							product.getCapabilityMap().get("ipDslamVoip") != null ||
							product.getCapabilityMap().get("localPhone") != null ||
							product.getCapabilityMap().get("longDistancePhone") != null ||
							product.getCapabilityMap().get("wirelessPhone") != null) ) ||

							(product.getCapabilityMap().get("fiberDataDownSpeed") != null || 
									product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
									product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
									product.getCapabilityMap().get("dialUpInternet") != null) &&
									(product.getCapabilityMap().get("iptv") != null ||
											product.getCapabilityMap().get("ipDslamIptv") != null ||
											product.getCapabilityMap().get("analogCable") != null ||
											product.getCapabilityMap().get("digitalCable") != null ||
											product.getCapabilityMap().get("satellite") != null) ||

											(product.getCapabilityMap().get("voip") != null || 
													product.getCapabilityMap().get("ipDslamVoip") != null ||
													product.getCapabilityMap().get("localPhone") != null ||
													product.getCapabilityMap().get("longDistancePhone") != null ||
													product.getCapabilityMap().get("wirelessPhone") != null) &&
													(product.getCapabilityMap().get("iptv") != null ||
															product.getCapabilityMap().get("ipDslamIptv") != null ||
															product.getCapabilityMap().get("analogCable") != null ||
															product.getCapabilityMap().get("digitalCable") != null ||
															product.getCapabilityMap().get("satellite") != null)									
			) {
				//this product is a DOUBLE_PLAY 	
				if (productByIconMap != null && productByIconMap.get(DOUBLE_PLAY) == null){
					product.setProductType(DOUBLE_PLAY);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(DOUBLE_PLAY,prList);	
				}else{
					product.setProductType(DOUBLE_PLAY);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(DOUBLE_PLAY))){
						productByIconMap.get(DOUBLE_PLAY).add(product);
					}
				}
				if(product.getProviderExternalId() != null && product.getProviderExternalId().equalsIgnoreCase("32937483") && (product.getCapabilityMap().get("iptv") != null ||
						product.getCapabilityMap().get("ipDslamIptv") != null ||
						product.getCapabilityMap().get("analogCable") != null ||
						product.getCapabilityMap().get("digitalCable") != null ||
						product.getCapabilityMap().get("satellite") != null)){
					this.setFrontierVideoAvailable(true);
				}
				if(product.getProviderExternalId() != null && product.getProviderExternalId().equalsIgnoreCase("32937483") && (product.getCapabilityMap().get("fiberDataDownSpeed") != null || 
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null)){
					this.setFrontierInternetAvailable(true);
				}
				buildCategoryWiseProviderData( DOUBLE_PLAY, product );
			} else if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
					product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
					product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
					product.getCapabilityMap().get("dialUpInternet") != null)) {
				if (productByIconMap != null && productByIconMap.get(INTERNET) == null){
					product.setProductType(INTERNET);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(INTERNET,prList);	
				}else{
					product.setProductType(INTERNET);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(INTERNET))){
						productByIconMap.get(INTERNET).add(product);
					}
				}
				if(product.getProviderExternalId() != null && product.getProviderExternalId().equalsIgnoreCase("32937483")){
					this.setFrontierInternetAvailable(true);
				}
				buildCategoryWiseProviderData( INTERNET, product );
			} else if((product.getCapabilityMap().get("voip") != null || //phone conditions
					product.getCapabilityMap().get("ipDslamVoip") != null ||
					product.getCapabilityMap().get("localPhone") != null ||
					product.getCapabilityMap().get("longDistancePhone") != null ||
					product.getCapabilityMap().get("wirelessPhone") != null)) {
				if (productByIconMap != null && productByIconMap.get(PHONE) == null){
					product.setProductType(PHONE);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(PHONE,prList);	
				}else{
					product.setProductType(PHONE);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(PHONE))){
						productByIconMap.get(PHONE).add(product);
					}
				}
				buildCategoryWiseProviderData( PHONE, product );
			} else if((product.getCapabilityMap().get("iptv") != null ||
					product.getCapabilityMap().get("ipDslamIptv") != null ||
					product.getCapabilityMap().get("analogCable") != null ||
					product.getCapabilityMap().get("digitalCable") != null ||
					product.getCapabilityMap().get("satellite") != null)) {
				//this product is a triple play 				
				if (productByIconMap != null && productByIconMap.get(VIDEO) == null){
					product.setProductType(VIDEO);
					getProductWithProductBonusPoints(product);
					prList = new ArrayList<ProductSummaryVO>();
					prList.add(product);
					productByIconMap.put(VIDEO,prList);	
				}else{
					product.setProductType(VIDEO);
					getProductWithProductBonusPoints(product);
					if(!isProductFoundInList(product, productByIconMap.get(VIDEO))){
						productByIconMap.get(VIDEO).add(product);
					}
				}
				if(product.getProviderExternalId() != null && product.getProviderExternalId().equalsIgnoreCase("32937483")){
					this.setFrontierVideoAvailable(true);
				}
				buildCategoryWiseProviderData( VIDEO, product );
			}
		}
		return productByIconMap;
	}

	private boolean isProductFoundInList(ProductSummaryVO product,
			List<ProductSummaryVO> list) {
		for (ProductSummaryVO mdo : list){
			if (mdo.getExternalId().equalsIgnoreCase(product.getExternalId())){
				return true;
			}
		}
		return false;
	}

	public List<ProductSummaryVO> getDoublePlayRenderData(List<ProductSummaryVO> details, String categoryName){
		List<ProductSummaryVO> list = new ArrayList<ProductSummaryVO>();
		for (ProductSummaryVO product : details){
			if(!Utils.isBlank(categoryName) && product.getMetadata() != null && product.getMetadata().get(DOUBLE_PLAY) != null){
				list.add(product);
				buildCategoryWiseProviderData( categoryName.toUpperCase(), product );
			}else if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI")){
				if((product.getCapabilityMap().get("iptv") != null || // Video Conditions
						product.getCapabilityMap().get("ipDslamIptv") != null ||
						product.getCapabilityMap().get("analogCable") != null ||
						product.getCapabilityMap().get("digitalCable") != null ||
						product.getCapabilityMap().get("satellite") != null) 
						&& (product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
								product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
								product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
								product.getCapabilityMap().get("dialUpInternet") != null) || (product.isSyntheticBundle() && product.getPairedProduct()!= null) )
				{
					list.add(product);
					buildCategoryWiseProviderData( "DOUBLE_PLAY_VI", product );
				}
			}else if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PV")){
				if((product.getCapabilityMap().get("iptv") != null || // Video Condtions
						product.getCapabilityMap().get("ipDslamIptv") != null ||
						product.getCapabilityMap().get("analogCable") != null ||
						product.getCapabilityMap().get("digitalCable") != null ||
						product.getCapabilityMap().get("satellite") != null) 
						&& (product.getCapabilityMap().get("voip") != null || //phone conditions
								product.getCapabilityMap().get("ipDslamVoip") != null ||
								product.getCapabilityMap().get("localPhone") != null ||
								product.getCapabilityMap().get("longDistancePhone") != null ||
								product.getCapabilityMap().get("wirelessPhone") != null)) 
				{
					list.add(product);
					buildCategoryWiseProviderData( "DOUBLE_PLAY_PV", product );
				}
			}else if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PI")){
				if((product.getCapabilityMap().get("voip") != null || //phone conditions
						product.getCapabilityMap().get("ipDslamVoip") != null ||
						product.getCapabilityMap().get("localPhone") != null ||
						product.getCapabilityMap().get("longDistancePhone") != null ||
						product.getCapabilityMap().get("wirelessPhone") != null) 
						&& (product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
								product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
								product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
								product.getCapabilityMap().get("dialUpInternet") != null))
				{
					list.add(product);
					buildCategoryWiseProviderData( "DOUBLE_PLAY_PI", product );
				}
			}
		}
		return list;
	}

	public boolean ifLatinoNewFilter(List<String> promotionMetaDataList) {
		boolean addVo = false;
		List<String> metaDataList = new ArrayList<String>();
		if(promotionMetaDataList!= null && promotionMetaDataList.size()>0){
			for (String metaData : promotionMetaDataList){
				if (metaData != null && metaData.contains("=")){
					String[] str = metaData.split("=");
					metaDataList.add(str[0]);
				}else{
					metaDataList.add(metaData);
				}
			}			
			if (metaDataList.size() > 0){
				for (String metaData : metaDataList){
					if (metaData.toUpperCase().contains("LATINO_PKG_INCL")){
						addVo = true;
					} 
				}
			} 
		}
		return addVo;
	}

	public boolean ifFilterValidationPassed(
			List<String> promotionMetaDataList, Map<String, String> paramMap, boolean isLatino) {
		List<String> metaDataList = new ArrayList<String>();
		//boolean isValid = true;
		for (String metaData : promotionMetaDataList){
			if (metaData != null && metaData.contains("=")){
				String[] str = metaData.split("=");
				metaDataList.add(str[0]);
			}else{
				metaDataList.add(metaData);
			}
		}

		if (isLatino){
			boolean isLatinoFound = false;
			if (metaDataList.size() > 0){
				for (String metaData : metaDataList){
					if (metaData.equalsIgnoreCase("LATINO_PKG_INCL")){
						isLatinoFound = true;
					}	
				}
			} else {
				isLatinoFound = true;
			}
			if(!isLatinoFound){
				return false;
			}
		}

		boolean isContractMatchFound = false;
		boolean isChannelMatchFound = false;
		boolean isInternetMatchFound = false;
		if (!Utils.isBlank(paramMap.get("contractTerm"))){
			if (metaDataList.size() > 0){
				for (String metaData : metaDataList){
					if (metaData.equalsIgnoreCase("CONTRACT_TERM")){
						isContractMatchFound = true;
						break;
					}	
				}
				if (isContractMatchFound){
					if(!generateProductsBasedOnContractTerm(paramMap.get("contractTerm"), metaDataList, promotionMetaDataList)){
						return false;
					}
				}
				else
				{
					return false;
				}
			}
		}
		if (!Utils.isBlank(paramMap.get("channels"))){
			if (metaDataList.size() > 0){
				for (String metaData : metaDataList){
					if (metaData.equalsIgnoreCase("NUM_CHANNELS")){
						isChannelMatchFound = true;
						break;
					}	
				}
				if (isChannelMatchFound){
					if(!generateProductsBasedOnChannels(paramMap.get("channels"), metaDataList, promotionMetaDataList)){
						return false;
					}
				}
				else
				{
					return false;
				}
			} 
		}

		if (!Utils.isBlank(paramMap.get("internetSpeed"))){
			if (metaDataList.size() > 0){
				for (String metaData : metaDataList){
					if (metaData.equalsIgnoreCase("CONN_SPEED")){
						isInternetMatchFound = true;
						break;
					}	
				}
				if (isInternetMatchFound){
					if(!generateProductsBasedOnInternet(paramMap.get("internetSpeed"), metaDataList, promotionMetaDataList)){
						return false;
					}
				}
				else
				{
					return false;
				}
			}
		}

		boolean isFilterPassed = false;

		if(isContractMatchFound || isChannelMatchFound || isInternetMatchFound)
		{
			isFilterPassed = true;
		}

		return isFilterPassed;
	}

	private boolean isInternetSpeedPassed(double productValue, String parameterValue) {
		return Utils.isInternetSpeedPassed(productValue,parameterValue);
	}


	private boolean isNumberOfChannlesPassed(long productValue, String parameterValue)
	{
		return Utils.isNumberofChannelsPassed(productValue,parameterValue);
	}

	private boolean isContractPassed(String productValue, String parameterValue) {
		if (productValue.equals(parameterValue)){
			return true;
		}
		return false;
	}

	private List<ProductSummaryVO> addRemainingProducts(
			List<ProductSummaryVO> remainingProductList, List<String> multiAddrProviderList) {
		List<ProductSummaryVO> isSellableList = new ArrayList<ProductSummaryVO>();
		//logger.info("hintProvidersList = "+hintProvidersList); 
		for (ProductSummaryVO vo : remainingProductList){
			if (getServiceAddrType() != null && getServiceAddrType().equalsIgnoreCase("apartment") ){
				if (vo != null && vo.getProviderExternalId() != null && vo.getProviderExternalId().equalsIgnoreCase("27010360")){
					if (vo.getMetadata() != null){
						for (Entry<String,String> entry :vo.getMetadata().entrySet()){
							if (entry.getValue().contains("SUBURBAN")){
								vo.setSellable(false);
								break;
							}
						}
					}
				}	
			}
			if(multiAddrProviderList!= null && multiAddrProviderList.size()>0){
				if (vo != null && vo.getProviderExternalId() != null && multiAddrProviderList.contains(vo.getProviderExternalId())){
					vo.setSellable(false);
				}	
			}
			if (vo != null && vo.isSellable() && (hideProviderList == null || !(vo.getProviderExternalId() != null && hideProviderList.contains(vo.getProviderExternalId()) 
					 || (vo.getParentExternalId() != null && hideProviderList.contains(vo.getParentExternalId()))))){
					isSellableList.add(vo);
				if(hintProvidersList != null && (hintProvidersList.contains(vo.getProviderExternalId()) || hintProvidersList.contains(vo.getParentExternalId()))){
					if(vo.getProviderExternalId()!= null && vo.getProviderExternalId().equals(Constants.FRONTIER)){
						String frontierProviderId = null;
						if(vo.getName()!= null && vo.getName().contains(Constants.FIOS)){
							frontierProviderId = vo.getProviderExternalId()+"_"+Constants.FIOS;
						}
						else{
							frontierProviderId = vo.getProviderExternalId();
						}
						if(hintProvidersMap != null && ! hintProvidersMap.containsKey(frontierProviderId)){
							hintProvidersMap.put(frontierProviderId, frontierProviderId);
						}
					}
					else if(vo.getParentExternalId() != null){
						if(hintProvidersMap != null && ! hintProvidersMap.containsKey(vo.getParentExternalId())){
							hintProvidersMap.put(vo.getParentExternalId(), vo.getParentExternalId());
						}
					}
					else{
						if(hintProvidersMap != null && ! hintProvidersMap.containsKey(vo.getProviderExternalId())){
							hintProvidersMap.put(vo.getProviderExternalId(), vo.getProviderExternalId());
						}
					}
					
				}

			}
		}
		return isSellableList;
	}
	protected Map<String, String> getPendingProvidersList(Map<String,String> rtRemExternalIdMap,
			com.A.xml.pr.v4.EnterpriseResponseDocumentType response,boolean isProductPollingCompleted) {
		Map<String,String> rtRemExternalIdMapNew = new HashMap<String, String>();
		ProductResponseType prs = (ProductResponseType)response.getResponse();
		if (prs.getRtimRequestResponseCriteria() != null){
			if (prs.getRtimRequestResponseCriteria().getProviderCriteria() != null){
				if (prs.getRtimRequestResponseCriteria().getProviderCriteria().getProvider() != null){
					for (ProviderCriteriaListEntityType pr2:prs.getRtimRequestResponseCriteria().getProviderCriteria().getProvider()){
						String name = pr2.getName();
						Map <String, String> attributes = new HashMap<String, String>();
						for (ProviderCriteriaEntryType pr3 :pr2.getCriteria()){
							attributes.put(pr3.getCriteriaName(), pr3.getCriteriaValue());
						}
						String extId = getExternalId(attributes);
						if (hideProviderList == null || !hideProviderList.contains(extId)){
							String displayName = null;
							boolean isQwestProviderSuccess = false;
							//String qwestCapibilitiesStr = null;
							if (!Utils.isBlank(name)){
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

							String rtimStatusMsg = getStatusMsg(attributes);
							String rtimStatus = getStatus(attributes);
							String qwestCapibilities = getQwestAttributes(attributes);
							String status = "";

							if (!Utils.isBlank(extId) && !Utils.isBlank(rtimStatus) && (rtimStatus.equalsIgnoreCase(PollingStatus.PENDING) || rtimStatus.equalsIgnoreCase(PollingStatus.MULTIADDRISSUE))){
								rtRemExternalIdMapNew.put(extId, name);
								status = PollingStatus.PENDING;		
							} else if (!Utils.isBlank(rtimStatus) && rtimStatus.equalsIgnoreCase(PollingStatus.SUCCESS)){
								if (name.equalsIgnoreCase("QWEST")){
									isQwestProviderSuccess = true;
								}
								logger.info("rtProductCountMap.get(Long.parseLong(extId))=" +rtProductCountMap.get(Long.parseLong(extId)));
								if (rtProductCountMap.get(Long.parseLong(extId)) == null){
									//even though it is success, as we don't have products for this provider
									//send it in the product polling. add it to the remaining provider ext id map
									rtProductCountMap.put(Long.valueOf(extId), 0L);
									rtRemExternalIdMapNew.put(extId, name);
									logger.info("adding_provider_for_re_polling="+ extId + ",rtRemExternalIdMapNew=" +rtRemExternalIdMapNew);	
									status = "Pending";
								}
								else {
									if (rtProductCountMap.get(Long.parseLong(extId)) == 0){
										//do nothing as we want to halt polling for providers with success and no products after one attempt
										// we will move the status to Not available 
										status = "Not Available";
										if(!Utils.isBlank(rtimStatusMsg) && rtimStatusMsg.contains("Multiple Address")){
											
										}else{
											rtimStatusMsg = "Not Available";
										}
									} else {
										status = "Sell";
										if(hideHughesNet != null && hideHughesNet.equalsIgnoreCase("true") && displayName != null && displayName.equalsIgnoreCase(Constants.HUGHESNET_NAME) && extId.equals(Constants.HUGHESNET) && !isProductPollingCompleted){
											status = "Pending";
										}
									}
								}

							} else if (!Utils.isBlank(rtimStatus) && rtimStatus.equalsIgnoreCase(PollingStatus.FAILED)){
								if (!Utils.isBlank(rtimStatusMsg) && rtimStatusMsg.equalsIgnoreCase(PollingStatus.SYSTEM_ERROR)){
									status = PollingStatus.SYSTEM_ERROR;
								}else{
									status = PollingStatus.FAILED;	
								}
							}

							if (!StringUtils.isEmpty(status)) {
								if (!Utils.isBlank(name)){
									if(name.equalsIgnoreCase("QWEST")){
										realTimeStatusMap.put("CenturyLink", status + "|" + rtimStatusMsg);
									}
									else{
										if (rtRemExternalIdMap.get(extId) != null){
											realTimeStatusMap.put(displayName, status + "|" + rtimStatusMsg);	
										}
									}
								}
								context.updateProviderStatus(extId, name, status, rtimStatusMsg);
							}

							if (!Utils.isBlank(name)){
								if (name.equalsIgnoreCase("QWEST")){
									if (isQwestProviderSuccess){
										String[] strValue = qwestCapibilities.split("\\|");
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
				}
			}
		}
		return rtRemExternalIdMapNew;
	}

	public boolean isMetaDataOwnOrRent(ProductSummaryVO productVo, String rentOrOwn) {
		boolean result = false;
		String metaDataVal = null;
		if (productVo.getPromotionMetaDataList() != null && productVo.getPromotionMetaDataList().size() > 0){
			for(String metaData :productVo.getPromotionMetaDataList()){
				if (metaData != null &&
						(metaData.equalsIgnoreCase(OWNERS) || metaData.equalsIgnoreCase(RENTERS))){
					metaDataVal = metaData;
				} 
			}
		}
		if (Utils.isBlank(metaDataVal)){
			result = true;
		}else if (metaDataVal != null && metaDataVal.equalsIgnoreCase(OWNERS)){
			if (rentOrOwn != null && rentOrOwn.equalsIgnoreCase(OWN)){
				result = true;
			}
		}else if (metaDataVal != null && metaDataVal.equalsIgnoreCase(RENTERS)){
			if (rentOrOwn != null && rentOrOwn.equalsIgnoreCase(RENT)){
				result = true;
			}
		}

		return result;
	}



	public void checkTPVandWarmTransferOnProduct(ProductSummaryVO productVo) {
		if (productVo.getPromotionMetaDataList() != null && productVo.getPromotionMetaDataList().size() > 0){
			for(String metaData :productVo.getPromotionMetaDataList()){
				if (metaData != null && metaData.equalsIgnoreCase(TPV)){
					productVo.setTPVProduct(Boolean.TRUE);
				}
			}
			if(productVo.getProviderExternalId()!= null && (warmtransferOptionProvidersList == null 
				|| warmtransferOptionProvidersList.size() == 0
				|| !warmtransferOptionProvidersList.contains(productVo.getProviderExternalId()))){
				if (getWarmTransferOrderMap().get(productVo.getParentExternalId()) !=  null 
						|| getWarmTransferOrderMap().get(productVo.getProviderExternalId()) != null){
					productVo.setWarmTransferProduct(true);
				}
			}
		}
	}


	protected Map<String,Long> updateRTProductsCountToMap(List<ProductSummaryVO> allProductList) {
		Long value = 0L;
		Map<Long,Long> resultMap = new HashMap<Long, Long>();
		Map<String,Long> providerCountMap = new HashMap<String, Long>();
		if (allProductList != null){
			for (ProductSummaryVO product :allProductList){
				if (product != null && product.getProviderExternalId() != null 
						&& product.getSource() != null && 
						(product.getSource().equalsIgnoreCase("REALTIME") || 
								(product.getProviderExternalId().equals(QWESTPRODUCT) && checkQwestProductCapabilities(product)))){
					if (resultMap.get(Long.parseLong(product.getProviderExternalId())) == null){
						resultMap.put(Long.parseLong(product.getProviderExternalId()), 1L);
						String displayName = null;
						if (product.getProviderName().equalsIgnoreCase("ATTSTI")){
							displayName = "AT&T";
						}
						else if (product.getProviderName().equalsIgnoreCase("G2B-Comcast")){
							displayName = "Comcast_G2B";
						}
						else if (product.getProviderName().equalsIgnoreCase("DISH Network")){
							displayName = "Dish";
						}
						else if (product.getProviderName().equalsIgnoreCase("Comcast")){
							displayName = "Comcast";
						}
						else if(product.getProviderName().equalsIgnoreCase("QWEST")){
							displayName = "CenturyLink";
						}
						else{
							displayName = product.getProviderName();
						}
						providerCountMap.put(displayName, 1L);
					}else{
						value = resultMap.get(Long.parseLong(product.getProviderExternalId()));
						resultMap.put(Long.parseLong(product.getProviderExternalId()), value + 1L);
						String displayName = null;
						if (product.getProviderName().equalsIgnoreCase("ATTSTI")){
							displayName = "AT&T";
						}
						else if (product.getProviderName().equalsIgnoreCase("G2B-Comcast")){
							displayName = "Comcast_G2B";
						}
						else if (product.getProviderName().equalsIgnoreCase("DISH Network")){
							displayName = "Dish";
						}
						else if (product.getProviderName().equalsIgnoreCase("Comcast")){
							displayName = "Comcast";
						}
						else if(product.getProviderName().equalsIgnoreCase("QWEST")){
							displayName = "CenturyLink";
						}
						else{
							displayName = product.getProviderName();
						}
						providerCountMap.put(displayName, value + 1L);
					}
				}
			}
		}
		for (Entry<Long,Long> entry :resultMap.entrySet()){
			rtProductCountMap.put(entry.getKey(), entry.getValue());
		}
		logger.info("Total_Number_of_products_for_each_provider" +rtProductCountMap);
		return providerCountMap;
	}

	private void buildQwestCapabilities(com.A.xml.pr.v4.EnterpriseResponseDocumentType response) {
		ProductResponseType prs = (ProductResponseType)response.getResponse();
		boolean isQwestSuccess = false;
		if (prs.getRtimRequestResponseCriteria() != null){
			if (prs.getRtimRequestResponseCriteria().getProviderCriteria() != null){
				if (prs.getRtimRequestResponseCriteria().getProviderCriteria().getProvider() != null){
					for (ProviderCriteriaListEntityType pr2:prs.getRtimRequestResponseCriteria().getProviderCriteria().getProvider()){
						String name = pr2.getName();
						if (!Utils.isBlank(name) && name.equalsIgnoreCase("QWEST")){
							String qwestCapibilitiesStr = null;
							for (ProviderCriteriaEntryType pr3 :pr2.getCriteria()){
								if (pr3.getCriteriaName() != null && pr3.getCriteriaName().equalsIgnoreCase("status")
										&& pr3.getCriteriaValue().equalsIgnoreCase("Success")){
									isQwestSuccess = true;
								}
							}
							if (isQwestSuccess){
								for (ProviderCriteriaEntryType pr3 :pr2.getCriteria()){
									if (pr3.getCriteriaName() != null && pr3.getCriteriaName().equalsIgnoreCase("capabilities")){
										qwestCapibilitiesStr = pr3.getCriteriaValue();
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
				}
			}
		}
	}


	private boolean generateProductsBasedOnContractTerm(String contractTerm, List<String> metaDataList, List<String> promotionMetaDataList)
	{
		String[] providerBuf = contractTerm.toString().split(",");
		for(int i=0; i<providerBuf.length; i++){
			for (String metaData1 : promotionMetaDataList){
				if (metaData1 != null && metaData1.contains("=")){
					String[] str1 = metaData1.split("=");
					if (str1[0].equalsIgnoreCase("CONTRACT_TERM")){
						if (isContractPassed(str1[1],providerBuf[i]))
						{
							logger.info("isContractMatchFound=" +true);
							return true;
						}	
					}	
				}
			}	
		}
		return false;
	}


	private boolean generateProductsBasedOnChannels(String channels,List<String> metaDataList, List<String> promotionMetaDataList)
	{
		String[] providerBuf = channels.toString().split(",");
		for(int i=0; i<providerBuf.length; i++){
			for (String metaData1 : promotionMetaDataList){
				if (metaData1 != null && metaData1.contains("=")){
					String[] str1 = metaData1.split("=");
					if (str1[0].equalsIgnoreCase("NUM_CHANNELS")){
						if (isNumberOfChannlesPassed(Long.parseLong(str1[1]),providerBuf[i])){
							logger.info("isChannelMatchFound=" +true);
							return true;
						}	
					}	
				}
			}	
		}
		return false;
	}


	public boolean generateProductsBasedOnInternet(String internetSpeed,List<String> metaDataList, List<String> promotionMetaDataList) 
	{
		String[] providerBuf = internetSpeed.toString().split(",");
		for(int i=0; i<providerBuf.length; i++){
			for (String metaData1 : promotionMetaDataList){
				if (metaData1 != null && metaData1.contains("=")){
					String[] str1 = metaData1.split("=");
					if (str1[0].equalsIgnoreCase("CONN_SPEED")){
						if (isInternetSpeedPassed(Double.parseDouble(str1[1]),providerBuf[i]))
						{
							logger.info("isInternetMatchFound=" +true);
							return true;
						}	
					}	
				}
			}	
		}
		return false;
	}

	public void putInternetInRange(int i,List<Integer> internetList) {
		if(i > 0 && i <= 5) {
			if (!internetList.contains(5)){
				internetList.add(5);
			}
		}
		if(i > 5 && i <= 10) {
			if (!internetList.contains(10)){
				internetList.add(10);
			}
		}
		if(i > 10 && i <= 15) {
			if (!internetList.contains(15)){
				internetList.add(15);
			}
		}
		if(i > 15 && i <= 20) {
			if (!internetList.contains(20)){
				internetList.add(20);
			}
		}
		if(i > 20 && i <= 25) {
			if (!internetList.contains(25)){
				internetList.add(25);
			}
		}
		if(i > 25 && i <= 30) {
			if (!internetList.contains(30)){
				internetList.add(30);
			}
		}
		if(i > 30 && i <= 35) {
			if (!internetList.contains(35)){
				internetList.add(35);
			}
		}
		if(i > 35 && i <= 40) {
			if (!internetList.contains(40)){
				internetList.add(40);
			}
		}
		if(i > 40 && i <= 45) {
			if (!internetList.contains(45)){
				internetList.add(45);
			}
		}
		if(i > 45 && i <= 50) {
			if (!internetList.contains(50)){
				internetList.add(50);
			}
		}		
		if(i > 50 && i <= 55) {
			if (!internetList.contains(55)){
				internetList.add(55);
			}
		}
		if(i > 55 && i <= 60) {
			if (!internetList.contains(60)){
				internetList.add(60);
			}
		}
		if(i > 60 && i <= 65) {
			if (!internetList.contains(65)){
				internetList.add(65);
			}
		}
		if(i > 65 && i <= 70) {
			if (!internetList.contains(70)){
				internetList.add(70);
			}
		}		
		if(i > 70 && i <= 75) {
			if (!internetList.contains(75)){
				internetList.add(75);
			}
		}
		if(i > 75 && i <= 100) {
			if (!internetList.contains(100)){
				internetList.add(100);
			}
		}
		if(i > 100 && i <= 125) {
			if (!internetList.contains(125)){
				internetList.add(125);
			}
		}
		if(i > 125 && i <= 150) {
			if (!internetList.contains(150)){
				internetList.add(150);
			}
		}
		if(i > 150 && i <= 175) {
			if (!internetList.contains(175)){
				internetList.add(175);
			}
		}
		if(i > 175 && i <= 200) {
			if (!internetList.contains(200)){
				internetList.add(200);
			}
		}
		if(i > 200 && i <= 225) {
			if (!internetList.contains(225)){
				internetList.add(225);
			}
		}
		if(i > 225 && i <= 250) {
			if (!internetList.contains(250)){
				internetList.add(250);
			}
		}
		if(i > 250 && i <= 275) {
			if (!internetList.contains(275)){
				internetList.add(275);
			}
		}		
		if(i > 275 && i <= 300) {
			if (!internetList.contains(300)){
				internetList.add(300);
			}
		}
		if(i > 300 && i <= 350) {
			if (!internetList.contains(350)){
				internetList.add(350);
			}
		}
		if(i > 350 && i <= 400) {
			if (!internetList.contains(400)){
				internetList.add(400);
			}
		}
		if(i > 400 && i <= 450) {
			if (!internetList.contains(450)){
				internetList.add(450);
			}
		}
		if(i > 450 && i <= 500) {
			if (!internetList.contains(500)){
				internetList.add(500);
			}
		}	
		if(i > 500 && i <= 550) {
			if (!internetList.contains(550)){
				internetList.add(550);
			}
		}
		if(i > 550 && i <= 600) {
			if (!internetList.contains(600)){
				internetList.add(600);
			}
		}	
		if(i > 600 && i <= 650) {
			if (!internetList.contains(650)){
				internetList.add(650);
			}
		}
		if(i > 650 && i <= 700) {
			if (!internetList.contains(700)){
				internetList.add(700);
			}
		}		
		if(i > 700 && i <= 750) {
			if (!internetList.contains(750)){
				internetList.add(750);
			}
		}
		if(i > 750 && i <= 800) {
			if (!internetList.contains(800)){
				internetList.add(800);
			}
		}	
		if(i > 800 && i <= 850) {
			if (!internetList.contains(850)){
				internetList.add(850);
			}
		}
		if(i > 850 && i <= 900) {
			if (!internetList.contains(900)){
				internetList.add(900);
			}
		}		
		if(i > 900 && i <= 950) {
			if (!internetList.contains(950)){
				internetList.add(950);
			}
		}
		if(i > 950 && i <= 1000) {
			if (!internetList.contains(1000)){
				internetList.add(1000);
			}
		}		

	}	


	public void putChannelInRange(int i,List<Integer> channelList) {
		if(i > 0 && i <= 50) {
			if (!channelList.contains(50)){
				channelList.add(50);
			}
		}
		if(i > 50 && i <= 75) {
			if (!channelList.contains(75)){
				channelList.add(75);
			}
		}
		if(i > 75 && i <= 100) {
			if (!channelList.contains(100)){
				channelList.add(100);
			}
		}
		if(i > 100 && i <= 125) {
			if (!channelList.contains(125)){
				channelList.add(125);
			}
		}
		if(i > 125 && i <= 150) {
			if (!channelList.contains(150)){
				channelList.add(150);
			}
		}
		if(i > 150 && i <= 175) {
			if (!channelList.contains(175)){
				channelList.add(175);
			}
		}
		if(i > 175 && i <= 200) {
			if (!channelList.contains(200)){
				channelList.add(200);
			}
		}
		if(i > 200 && i <= 225) {
			if (!channelList.contains(225)){
				channelList.add(225);
			}
		}
		if(i > 225 && i <= 250) {
			if (!channelList.contains(250)){
				channelList.add(250);
			}
		}
		if(i > 250 && i <= 275) {
			if (!channelList.contains(275)){
				channelList.add(275);
			}
		}		
		if(i > 275 && i <= 300) {
			if (!channelList.contains(300)){
				channelList.add(300);
			}
		}
		if(i > 300 && i <= 325) {
			if (!channelList.contains(325)){
				channelList.add(325);
			}
		}
		if(i > 325 && i <= 350) {
			if (!channelList.contains(350)){
				channelList.add(350);
			}
		}
		if(i > 350 && i <= 375) {
			if (!channelList.contains(375)){
				channelList.add(375);
			}
		}
		if(i > 375 && i <= 400) {
			if (!channelList.contains(400)){
				channelList.add(400);
			}
		}
		if(i > 400 && i <= 425) {
			if (!channelList.contains(425)){
				channelList.add(425);
			}
		}
		if(i > 425 && i <= 450) {
			if (!channelList.contains(450)){
				channelList.add(450);
			}
		}
		if(i > 450 && i <= 475) {
			if (!channelList.contains(475)){
				channelList.add(475);
			}
		}
		if(i > 475 && i <= 500) {
			if (!channelList.contains(500)){
				channelList.add(500);
			}
		}
		if(i > 500 && i <= 525) {
			if (!channelList.contains(525)){
				channelList.add(525);
			}
		}	
		if(i > 525 && i <= 550) {
			if (!channelList.contains(550)){
				channelList.add(550);
			}
		}
		if(i > 550 && i <= 575) {
			if (!channelList.contains(575)){
				channelList.add(575);
			}
		}
		if(i > 575 && i <= 600) {
			if (!channelList.contains(600)){
				channelList.add(600);
			}
		}
		if(i > 600 && i <= 625) {
			if (!channelList.contains(625)){
				channelList.add(625);
			}
		}
		if(i > 625 && i <= 650) {
			if (!channelList.contains(650)){
				channelList.add(650);
			}
		}
		if(i > 650 && i <= 675) {
			if (!channelList.contains(675)){
				channelList.add(675);
			}
		}
		if(i > 675 && i <= 700) {
			if (!channelList.contains(700)){
				channelList.add(700);
			}
		}				
	}

	/**  This method used to build closing offer map based on product meta data
	 * meta data has OFFER_TYPE=CLOSINGUTILITY 
	 * meta data has OFFER_TYPE=CLOSINGRENTERS 
	 * meta data has OFFER_TYPE=CLOSINGVIDEO
	 * @param productVO
	 * @param rentOrOwn
	 */
	private void buildClosingOfferMap(ProductSummaryVO productVO,String rentOrOwn) {
		if (productVO.getPromotionMetaDataList() != null && productVO.getPromotionMetaDataList().size() > 0){
			for(String metaData :productVO.getPromotionMetaDataList()){
				if (!Utils.isBlank(metaData) 
						&& (Constants.OFFERTYPE_CLOSINUTILITY.equalsIgnoreCase(metaData)
						         || Constants.OFFERTYPE_CLOSINGVIDEO.equalsIgnoreCase(metaData)
						         || Constants.OFFERTYPE_CLOSINGWARMTRANSFER.equalsIgnoreCase(metaData)
								 || (Constants.OFFERTYPE_CLOSINGRENTERS.equalsIgnoreCase(metaData) && RENT.equalsIgnoreCase(rentOrOwn)))){
					if (Constants.OFFERTYPE_CLOSINUTILITY.equalsIgnoreCase(metaData)){
						List<ProductSummaryVO> list = new ArrayList<ProductSummaryVO>();
						boolean isProductExists = false;
						if (closeOfferMap.isEmpty() || closeOfferMap.get("closeOfferUtility") == null){
							list.add(productVO);
							closeOfferMap.put("closeOfferUtility", list);	
						}else{
							List<ProductSummaryVO> salesMdo = closeOfferMap.get("closeOfferUtility");
							for (ProductSummaryVO newMdo :salesMdo){
								if (newMdo != null 
										&& newMdo.getExternalId() != null 
										&& !newMdo.getExternalId().equalsIgnoreCase(productVO.getExternalId())){
									isProductExists = true;
									break;
								}	
							}
							if (isProductExists){
								closeOfferMap.get("closeOfferUtility").add(productVO);
							}
						}
					}else if (Constants.OFFERTYPE_CLOSINGRENTERS.equalsIgnoreCase(metaData)){
						List<ProductSummaryVO> list = new ArrayList<ProductSummaryVO>();
						boolean isProductExists = false;
						if (closeOfferMap.isEmpty() || closeOfferMap.get("closeOfferRenters") == null){
							list.add(productVO);
							closeOfferMap.put("closeOfferRenters", list);	
						}else{
							List<ProductSummaryVO> salesMdo = closeOfferMap.get("closeOfferRenters");
							for (ProductSummaryVO newMdo :salesMdo){
								if (newMdo != null 
										&& newMdo.getExternalId() != null 
										&& !newMdo.getExternalId().equalsIgnoreCase(productVO.getExternalId())){
									isProductExists = true;
									break;
								}	
							}
							if (isProductExists){
								closeOfferMap.get("closeOfferRenters").add(productVO);
							}
						}
					}else if (Constants.OFFERTYPE_CLOSINGVIDEO.equalsIgnoreCase(metaData)){
						List<ProductSummaryVO> list = new ArrayList<ProductSummaryVO>();
						boolean isProductExists = false;
						if (closeOfferMap.isEmpty() || closeOfferMap.get("closeOfferVideo") == null){
							list.add(productVO);
							closeOfferMap.put("closeOfferVideo", list);	
						}else{
							List<ProductSummaryVO> salesMdo = closeOfferMap.get("closeOfferVideo");
							for (ProductSummaryVO newMdo :salesMdo){
								if (newMdo != null 
										&& newMdo.getExternalId() != null 
										&& !newMdo.getExternalId().equalsIgnoreCase(productVO.getExternalId())){
									isProductExists = true;
									break;
								}	
							}
							if (isProductExists){
								closeOfferMap.get("closeOfferVideo").add(productVO);
							}
						}
					}else if (Constants.OFFERTYPE_CLOSINGWARMTRANSFER.equalsIgnoreCase(metaData)){
						List<ProductSummaryVO> list = new ArrayList<ProductSummaryVO>();
						boolean isProductExists = false;
						if (closeOfferMap.isEmpty() || closeOfferMap.get("closeOfferWarmtransfer") == null){
							list.add(productVO);
							closeOfferMap.put("closeOfferWarmtransfer", list);	
						}else{
							List<ProductSummaryVO> salesMdo = closeOfferMap.get("closeOfferWarmtransfer");
							for (ProductSummaryVO newMdo :salesMdo){
								if (newMdo != null 
										&& newMdo.getExternalId() != null 
										&& !newMdo.getExternalId().equalsIgnoreCase(productVO.getExternalId())){
									isProductExists = true;
									break;
								}	
							}
							if (isProductExists){
								closeOfferMap.get("closeOfferWarmtransfer").add(productVO);
							}
						}
					}
				}
			}
		}
	}
	public static String getSiftFileVersion(){
		return siftFileVersion;
	}

	/** This method use to show AT&T and DirecTV in real time status box
	 * @param realTimeStatusMap
	 * @param summaryVOList
	 */
	
	private void updateRealTimeStatusToDispalyATTandDirecTV(Map<String,String> realTimeStatusMap,List<ProductSummaryVO> summaryVOList) {
		String dtvMessage = "";
		String[] dtvRealitimeMsg = {};
		if(realTimeStatusMap != null 
				&& realTimeStatusMap.get(Constants.ATTV6_NAME) != null){
			if(realTimeStatusMap.get(Constants.ATTV6_NAME).contains(PollingStatus.SUCCESS)){
				if( isATTDirecTVAvailable(summaryVOList)){
					realTimeStatusMap.put(Constants.DIRECTV_NAME, "Sell|SUCCESS");
				}else{
					 dtvMessage=realTimeStatusMap.get(Constants.ATTV6_NAME);
					if(dtvMessage !=null && dtvMessage.toLowerCase().contains("contact provider"))
					{
					   dtvRealitimeMsg = dtvMessage.split("\\|", 2);
					   if(dtvRealitimeMsg!=null && dtvRealitimeMsg.length >1){
						   realTimeStatusMap.put(Constants.DIRECTV_NAME, dtvRealitimeMsg[1]);
					   }
					}
					else
					{
						realTimeStatusMap.put(Constants.DIRECTV_NAME, PollingStatus.FAILED+"|"+PollingStatus.FAILED);
					}
				}
				if(!isATTAvailable(summaryVOList)){
					realTimeStatusMap.put(Constants.ATTV6_NAME, PollingStatus.FAILED+"|"+PollingStatus.FAILED);
				}
			}else if(realTimeStatusMap.get(Constants.DIRECTV_NAME) == null || !realTimeStatusMap.get(Constants.DIRECTV_NAME).contains(PollingStatus.SUCCESS)){
				if(realTimeStatusMap.get(Constants.ATTV6_NAME).toLowerCase().contains("pending"))
				{
					realTimeStatusMap.put(Constants.DIRECTV_NAME, realTimeStatusMap.get(Constants.ATTV6_NAME));
				}
				else{
					if(realTimeStatusMap.get(Constants.DIRECTV_NAME) != null)
					{
						realTimeStatusMap.put(Constants.DIRECTV_NAME,realTimeStatusMap.get(Constants.DIRECTV_NAME));
					}
					else
					{
						realTimeStatusMap.put(Constants.DIRECTV_NAME, PollingStatus.FAILED+"|"+PollingStatus.FAILED);	
					}
				}
			}
		}
	}

	public void updateRealTimeStatusForMAMToDispalyATTandDirecTV(Map<String,String> realTimeStatusMap,List<ProductSummaryVO> summaryVOList,String providerId,String status) {
		if(realTimeStatusMap != null && Constants.ATTV6.equals(providerId)
			&& realTimeStatusMap.get(Constants.ATTV6_NAME) != null && status != null && status.equalsIgnoreCase("SUCCESS")){
			if( isATTDirecTVAvailable(summaryVOList)){
				realTimeStatusMap.put(Constants.DIRECTV_NAME, "Sell|SUCCESS");
			}else{
				realTimeStatusMap.put(Constants.DIRECTV_NAME,  "Not Available|Not Available");
			}
			if(isATTAvailable(summaryVOList)){
				realTimeStatusMap.put(Constants.ATTV6_NAME,  "Sell|SUCCESS");
			}else{
				realTimeStatusMap.put(Constants.ATTV6_NAME,  "Not Available|Not Available");
			}
		}
		else if(realTimeStatusMap != null && providerNamesAndExtIdsMap!=null && providerNamesAndExtIdsMap.get(providerId)!= null && status != null && status.equalsIgnoreCase("SUCCESS")){
			realTimeStatusMap.put(providerNamesAndExtIdsMap.get(providerId),  "Sell|SUCCESS");
		}else if(realTimeStatusMap != null && providerNamesAndExtIdsMap!=null && providerNamesAndExtIdsMap.get(providerId)!= null && status != null && !status.equalsIgnoreCase("SUCCESS")){
			if(Constants.ATTV6.equals(providerId) && status != null && status.equalsIgnoreCase("none")){
				if( isATTDirecTVAvailable(summaryVOList)){
					realTimeStatusMap.put(Constants.DIRECTV_NAME, "Sell|SUCCESS");
				}else{
					realTimeStatusMap.put(Constants.DIRECTV_NAME,  "Not Available|Not Available");
				}
				if(isATTAvailable(summaryVOList)){
					realTimeStatusMap.put(Constants.ATTV6_NAME,  "Sell|SUCCESS");
				}else{
					realTimeStatusMap.put(Constants.ATTV6_NAME,  "Not Available|Not Available");
				}
			}
			else{
				if(Constants.ATTV6.equals(providerId)){
					realTimeStatusMap.put(Constants.DIRECTV_NAME,  "Not Available|Not Available");
				}
				realTimeStatusMap.put(providerNamesAndExtIdsMap.get(providerId),  "Not Available|Not Available");
			}
		}
	}

	/**
	 * @param summaryVOList
	 * @return
	 */
	public boolean isATTAvailable(List<ProductSummaryVO> summaryVOList) {
		for(ProductSummaryVO summaryVO : summaryVOList){
			if(Constants.ATTV6.equals(summaryVO.getProviderExternalId())) {
				if((Constants.ATT_BUILD_PRODUCT_EXID.equalsIgnoreCase(summaryVO.getExternalId())
						||Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equalsIgnoreCase(summaryVO.getExternalId()))
						&& !isATTBuildYourBundle(summaryVO)){
					continue;
				}
				if(!isATTProductHasSatellite(summaryVO)){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param summaryVOList
	 * @return
	 */
	public boolean isATTDirecTVAvailable(List<ProductSummaryVO> summaryVOList) {
		for(ProductSummaryVO summaryVO : summaryVOList){
			if(Constants.ATTV6.equals(summaryVO.getProviderExternalId()) 
					&& isATTProductHasSatellite(summaryVO)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param summaryVO
	 * @return
	 */
	public boolean isATTProductHasSatellite(ProductSummaryVO summaryVO){
		if(summaryVO.getCapabilityMap() != null
				&& summaryVO.getCapabilityMap().get(Constants.SATELLITE) != null){
			return true;
		}
		return false;
	}

	private boolean isATTBuildYourBundle(ProductSummaryVO summaryVO){
		if((Constants.ATT_BUILD_PRODUCT_EXID.equalsIgnoreCase(summaryVO.getExternalId())
				|| Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equalsIgnoreCase(summaryVO.getExternalId()))
				&& summaryVO.getMetadata() != null 
				&& (summaryVO.getMetadata().get(DOUBLE_PLAY) != null 
						|| summaryVO.getMetadata().get(TRIPLE_PLAY) != null)){
			return true;
		}
		return false;
		
	}
	
	/**
	 * @param summaryVOList
	 * @return
	 */
	public boolean isATTV5Available(List<ProductSummaryVO> summaryVOList) {
		for(ProductSummaryVO summaryVO : summaryVOList){
			if(Constants.ATT.equals(summaryVO.getProviderExternalId())) {
				return true;
			}
		}
		return false;
	}
	public boolean isComcastAvailable(List<ProductSummaryVO> summaryVOList) {
		for(ProductSummaryVO summaryVO : summaryVOList){
			if(Constants.COMCAST.equals(summaryVO.getProviderExternalId())) {
				return true;
			}
		}
		return false;
	}
	public boolean isCenturyLinkAvailable(List<ProductSummaryVO> summaryVOList) {
		for(ProductSummaryVO summaryVO : summaryVOList){
			if(Constants.CENTURY_LINK.equals(summaryVO.getProviderExternalId())) {
				return true;
			}
		}
		return false;
	}
	
	private Map<String,String> generateProviderTransferAgentGroupMap() 
	{
		for (String providerTransferAgentGroupdata : providerTransferAgentGroupList) {
			if(providerTransferAgentGroupdata.indexOf('=') > -1){
				String[] nameValuePair = providerTransferAgentGroupdata.split("=");
				if (nameValuePair.length == 2){
					providerTransferAgentGroupMap.put(nameValuePair[0], nameValuePair[1]);	
				}
			}
		}
		return providerTransferAgentGroupMap;
	}
	
	private void generateTransferExistingCustomer(Map<String, String> attributes, String extId, String displayName) {
		String val = null;
		SalesCenterVO salesCenterVo = context.getSalesCenterVO();
		String userGroup = null;
		if (salesCenterVo != null) {
			if(!Utils.isBlank(salesCenterVo.getValueByName("userGroup"))) {
				userGroup = salesCenterVo.getValueByName("userGroup");
			}
			if (attributes.containsKey("transferCustomerEnabled")) {
				val = attributes.get("transferCustomerEnabled");
				if ("true".equalsIgnoreCase(val)) {
					if(providerTransferAgentGroupMap != null && providerTransferAgentGroupMap.get(extId)!= null) {
						if(userGroup != null && providerTransferAgentGroupMap.get(extId).contains(userGroup)){
							transferCustomerEnabledProviderMap.put(displayName, extId);
						}
					} 
					else {
						transferCustomerEnabledProviderMap.put(displayName, extId);
					}
				} 
			}
		}
		if (attributes.containsKey("upgradeCustomerEnabled")) {
			val = attributes.get("upgradeCustomerEnabled");
			if ("true".equalsIgnoreCase(val)) {
				upgradeCustomerEnabledProviderMap.put(displayName, extId);
			}
		}
	}
	
	public List<ProductSummaryVO> populateSyntheticBundlesData() {
		logger.info("populateSyntheticBundlesData_begin_in_ProductResultsManager");
		List<ProductSummaryVO> powerPitchSyntheticBundlesList = new ArrayList<ProductSummaryVO>();
		syntheticBundlesList = new ArrayList<ProductSummaryVO>();
		if(realTimeStatusMap != null && realTimeStatusMap.get(Constants.DISH_NAME) != null){
			ProductSummaryVO[] syntheticBundlesArray = new ProductSummaryVO[4];
			ProductSummaryVO dish120RTISatelliteProduct = null;
			ProductSummaryVO dish200RTISatelliteProduct = null;
			List<ProductSummaryVO> allProductList = this.context.getAllProductList();
			ProductSummaryVO ppSyntheticBundleProduct = null;
			boolean populateHughesnetProducts = populateHughesNetProducts();
			for (ProductSummaryVO product:allProductList){
				
				if((product.getCapabilityMap() != null)) {
					if(!populateHughesnetProducts && product.getProviderExternalId() != null && (product.getProviderExternalId().equalsIgnoreCase(Constants.HUGHESNET) || product.getProviderExternalId().equalsIgnoreCase("15500581"))){
						continue;
					}
					if(product.getProviderExternalId() != null && (product.getProviderExternalId().equals(Constants.DISH))
							&& (product.getCapabilityMap().get("satellite") != null && product.getCapabilityMap().get("wiredDataDownSpeed") == null && 
							product.getCapabilityMap().get("fiberDataDownSpeed") == null &&  
							product.getCapabilityMap().get("ipDslamDataDownSpeed") == null &&
							product.getCapabilityMap().get("dialUpInternet") == null &&
							product.getCapabilityMap().get("localPhone") == null && 
							product.getCapabilityMap().get("longDistancePhone") == null && 
							product.getCapabilityMap().get("wirelessPhone") == null && 
							product.getCapabilityMap().get("voip") == null  && 
							product.getCapabilityMap().get("ipDslamVoip") == null&& 
							product.getCapabilityMap().get("analogCable") == null && 
							product.getCapabilityMap().get("digitalCable") == null &&
							product.getCapabilityMap().get("ipDslamIptv") == null && 
							product.getCapabilityMap().get("iptv") == null)) {
						if(product.getName()!= null && product.getName().contains("120")){
							if (dish120RTISatelliteProduct == null) {
								dish120RTISatelliteProduct = product;
							} 
							else { 
								//compare them for better score
								if(dish120RTISatelliteProduct.getScore()< product.getScore()) {
									dish120RTISatelliteProduct = product;
								} 
								else {
									//the earlier product itself should be retained in this slot
								}
							}
						} 
						else if(product.getName()!= null && product.getName().contains("200")){
							if (dish200RTISatelliteProduct == null) {
								dish200RTISatelliteProduct = product;
							} 
							else { 
								//compare them for better score
								if(dish200RTISatelliteProduct.getScore()< product.getScore()) {
									dish200RTISatelliteProduct = product;
								} 
								else {
									//the earlier product itself should be retained in this slot
								}
							}
						}
					}
					if ((product.getCapabilityMap().get("wiredDataDownSpeed") != null || 
							product.getCapabilityMap().get("fiberDataDownSpeed") != null || 
							product.getCapabilityMap().get("dialUpInternet") != null || 
							product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ) &&
							(product.getCapabilityMap().get("localPhone") != null || 
							product.getCapabilityMap().get("voip") != null || 
							product.getCapabilityMap().get("longDistancePhone") != null || 
							product.getCapabilityMap().get("wirelessPhone") != null || 
							product.getCapabilityMap().get("ipDslamVoip") != null)&& 
							product.getCapabilityMap().get("analogCable") == null && 
							product.getCapabilityMap().get("digitalCable") == null &&
							product.getCapabilityMap().get("satellite") == null && 
							product.getCapabilityMap().get("ipDslamIptv") == null && 
							product.getCapabilityMap().get("iptv") == null){
						//telco double play wiredDataDownSpeed localPhone
						if (telcoProvidersList != null && (telcoProvidersList.contains(product.getParentExternalId()) 
								|| telcoProvidersList.contains(product.getProviderExternalId()))){
							if (syntheticBundlesArray[0] == null) {
								product.setProductType(ProductSearchIface.DOUBLE_PLAY);
								syntheticBundlesArray[0] = product;
							} 
							else { 
								//compare them for better score
								if(syntheticBundlesArray[0].getScore()< product.getScore()) {
									product.setProductType(ProductSearchIface.DOUBLE_PLAY);
									syntheticBundlesArray[0] = product;
								}
								else {
									//the earlier product itself should be retained in this slot
								}
							}
						}
						else if(cableProvidersList!=null &&(cableProvidersList.contains(product.getParentExternalId()) 
								|| cableProvidersList.contains(product.getProviderExternalId()))){
							if (syntheticBundlesArray[1] == null) {
								product.setProductType(ProductSearchIface.DOUBLE_PLAY);
								syntheticBundlesArray[1] = product;
							} 
							else { 
								//compare them for better score
								if(syntheticBundlesArray[1].getScore()< product.getScore()) {
									product.setProductType(ProductSearchIface.DOUBLE_PLAY);
									syntheticBundlesArray[1] = product;
								} 
								else {
									//the earlier product itself should be retained in this slot
								}
							}
						}
					}
					else if((product.getCapabilityMap().get("wiredDataDownSpeed") != null || 
							product.getCapabilityMap().get("fiberDataDownSpeed") != null || 
							product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
							product.getCapabilityMap().get("dialUpInternet") != null)&&(product.getCapabilityMap().get("localPhone") == null && 
									product.getCapabilityMap().get("voip") == null &&
									product.getCapabilityMap().get("longDistancePhone") == null &&
									product.getCapabilityMap().get("wirelessPhone") == null &&
									product.getCapabilityMap().get("analogCable") == null && 
									product.getCapabilityMap().get("digitalCable") == null &&
									product.getCapabilityMap().get("satellite") == null && 
									product.getCapabilityMap().get("ipDslamIptv") == null && 
									product.getCapabilityMap().get("iptv") == null)){
						//top rated internet wiredDataDownSpeed  
						if (telcoProvidersList != null && (telcoProvidersList.contains(product.getParentExternalId())
								|| telcoProvidersList.contains(product.getProviderExternalId()))){
							if (syntheticBundlesArray[2] == null) {
								syntheticBundlesArray[2] = product;
							} 
							else { 
								//compare them for better score
								if(syntheticBundlesArray[2].getScore()< product.getScore()) {
									syntheticBundlesArray[2] = product;
								} 
								else {
									//the earlier product itself should be retained in this slot
								}
							}
						}
						else if(cableProvidersList!=null &&(cableProvidersList.contains(product.getParentExternalId()) 
								|| cableProvidersList.contains(product.getProviderExternalId()))){
							if (syntheticBundlesArray[3] == null) {
								syntheticBundlesArray[3] = product;
							} 
							else { 
								//compare them for better score
								if(syntheticBundlesArray[3].getScore()< product.getScore()) {
									syntheticBundlesArray[3] = product;
								} 
								else {
									//the earlier product itself should be retained in this slot
								}
							}
						}
					} 
				}
			}
			for (int i=0; i<4; i++) {
				logger.debug("The raw Synthetic Bundles product is:" + i + "   syntheticBundlesArray[i]    " +syntheticBundlesArray[i]);
				if(syntheticBundlesArray[i] != null) {
					getProductWithProductBonusPoints(syntheticBundlesArray[i]);
					if(dish120RTISatelliteProduct != null){
						dish120RTISatelliteProduct.setSyntheticBundle(true);
						if(i==0 || i==1){
							dish120RTISatelliteProduct.setProductCategory(TRIPLE_PLAY);
						}
						else{
							dish120RTISatelliteProduct.setProductCategory(DOUBLE_PLAY);
						}
						dish120RTISatelliteProduct.setTotalPoints(getTotalPoints(dish120RTISatelliteProduct,syntheticBundlesArray[i]));
						syntheticBundlesList.add(new ProductSummaryVO(dish120RTISatelliteProduct, syntheticBundlesArray[i]));
						logger.info("MixedBundleDish120ProductExternalId="+dish120RTISatelliteProduct.getExternalId());
						logger.info("MixedBundlePairedProductExternalId="+syntheticBundlesArray[i].getExternalId());
					}
					if(dish200RTISatelliteProduct != null){
						dish200RTISatelliteProduct.setSyntheticBundle(true);
						if(i==0 || i==1){
							dish200RTISatelliteProduct.setProductCategory(TRIPLE_PLAY);
						}
						else{
							dish200RTISatelliteProduct.setProductCategory(DOUBLE_PLAY);
						}
						dish200RTISatelliteProduct.setTotalPoints(getTotalPoints(dish200RTISatelliteProduct,syntheticBundlesArray[i]));
						syntheticBundlesList.add(new ProductSummaryVO(dish200RTISatelliteProduct, syntheticBundlesArray[i]));
						logger.info("MixedBundleDish200ProductExternalId="+dish200RTISatelliteProduct.getExternalId());
						logger.info("MixedBundlePairedProductExternalId="+syntheticBundlesArray[i].getExternalId());
					}
					if(i==0 || i==1){
						if (ppSyntheticBundleProduct == null) {
							ppSyntheticBundleProduct = syntheticBundlesArray[i];
						} 
						else { 
							//compare them for better score
							if(ppSyntheticBundleProduct.getScore()< syntheticBundlesArray[i].getScore()) {
								ppSyntheticBundleProduct = syntheticBundlesArray[i];
							} 
							else {
								//the earlier product itself should be retained in this slot
							}
						}
					}
				} 
			}
			if(dish200RTISatelliteProduct != null && ppSyntheticBundleProduct!= null){
				powerPitchSyntheticBundlesList.add(new ProductSummaryVO(dish200RTISatelliteProduct, ppSyntheticBundleProduct));
				logger.info("PowerPitchMixedBundleDish200ProductExternalId="+dish200RTISatelliteProduct.getExternalId());
				logger.info("MixedBundlePairedProductExternalId="+ppSyntheticBundleProduct.getExternalId());
			}
		}
		//logger.info("powerPitchSyntheticBundlesList ="+powerPitchSyntheticBundlesList);
		//logger.info("syntheticBundlesList ="+syntheticBundlesList);
		logger.info("populateSyntheticBundlesData_end_in_ProductResultsManager");
		return powerPitchSyntheticBundlesList;
	}
	
	private Double getTotalPoints(ProductSummaryVO dishProduct, ProductSummaryVO pairedProduct) {
		Double totalPoints = 0.0;
		String dishPoints = String.valueOf(dishProduct.getPoints());
		String pairedProductPoints = String.valueOf(pairedProduct.getPoints());
		if(!Utils.isBlank(dishPoints) && !dishPoints.equals("NA") && !dishPoints.equals("-1.0")){
			totalPoints = totalPoints + dishProduct.getPoints();
		}
		if(!Utils.isBlank(pairedProductPoints) && !pairedProductPoints.equals("NA") && !pairedProductPoints.equals("-1.0")){
			totalPoints = totalPoints + pairedProduct.getPoints();
		}
		return totalPoints;
	}

	private boolean isMixedBundleProductFoundInList(ProductSummaryVO product, ProductSummaryVO pairedProduct,
			List<ProductSummaryVO> list) {
		for (ProductSummaryVO mdo : list){
			if (mdo.getExternalId().equalsIgnoreCase(product.getExternalId())&&mdo.getPairedProduct().getExternalId().equalsIgnoreCase(pairedProduct.getExternalId())){
				return true;
			}
		}
		return false;
	}
	
	public String getProductPoints(String  extId) {
		String points = null;
		if(this.context.getAllProductList() != null){
			for (ProductSummaryVO product: this.context.getAllProductList()){
				if(product.getExternalId().equals(extId)){
					if(product.getPoints() <= -1){
						points = "NA";
					}else{
						points = Float.toString(product.getPoints());
					}
					break;
				}
			}
		}
		return points;
	}
	
	public float getATTV6NewLineItemProductPoints(String  productExtId) {
		float points = 0.0f;
		if(MDUCacheService.INSTANCE.get("attV6Points") == null){
			Map<String, Float> attV6RevenueCommissionsMap = new HashMap<String, Float>();
			List<String> productExtIdList = new ArrayList<String>();
			productExtIdList.add(productExtId);
			Map<String, Float> attV6PointsMap = grossCommissionableRevenueDao.getRevCommissionsBySpIdStables(productExtIdList);
			if(attV6PointsMap !=null && attV6PointsMap.get(productExtId)!= null){
				points = attV6PointsMap.get(productExtId);
				attV6RevenueCommissionsMap.put(productExtId, points);
				MDUCacheService.INSTANCE.storeATTV6Points(attV6RevenueCommissionsMap, attV6PointsCacheTimeout);
			}
		}
		else{
			Map<String, Float> attV6RevenueCommissionsMap = (Map<String, Float>) MDUCacheService.INSTANCE.get("attV6Points");
			if(attV6RevenueCommissionsMap.get(productExtId)!= null){
				points = attV6RevenueCommissionsMap.get(productExtId);
			}
			else{
				List<String> productExtIdList = new ArrayList<String>();
				productExtIdList.add(productExtId);
				Map<String, Float> attV6PointsMap = grossCommissionableRevenueDao.getRevCommissionsBySpIdStables(productExtIdList);
				if(attV6PointsMap !=null && attV6PointsMap.get(productExtId)!= null){
					points = attV6PointsMap.get(productExtId);
					attV6RevenueCommissionsMap.put(productExtId, points);
					MDUCacheService.INSTANCE.storeATTV6Points(attV6RevenueCommissionsMap, Long.valueOf(attV6PointsCacheTimeout));
				}
			}
		}

		return points;
	}
	
	public void populatePivotAssistData() {
		logger.info("populatePivotAssistData_begin_in_ProductResultsManager");
		boolean isFallbackCableProviderNotPopulated = true;
		boolean isFallbackTelcoProviderNotPopulated = true;
		List<ProductSummaryVO> allProductList = this.context.getAllProductList();
		SalesCenterVO salesCenterVo = this.context.getSalesCenterVO();
		if(salesCenterVo!= null && salesCenterVo.getValueByName("fallback.cableProvider")!= null){
			isFallbackCableProviderNotPopulated = false;
		}
		if(salesCenterVo!= null && salesCenterVo.getValueByName("fallback.telcoProvider")!= null){
			isFallbackTelcoProviderNotPopulated = false;
		}
		if(isFallbackCableProviderNotPopulated || isFallbackTelcoProviderNotPopulated){
			for (ProductSummaryVO product:allProductList){
				if (isFallbackTelcoProviderNotPopulated && telcoProvidersList != null && (telcoProvidersList.contains(product.getParentExternalId()) 
						|| telcoProvidersList.contains(product.getProviderExternalId()))){
					if (product.getProviderName().equalsIgnoreCase("ATTSTI")){
						salesCenterVo.setValueByName("fallback.telcoProviderName",Constants.ATT_NAME);
					}
					else{
						salesCenterVo.setValueByName("fallback.telcoProviderName",product.getProviderName());
					}
					salesCenterVo.setValueByName("fallback.telcoProviderId",product.getProviderExternalId());
					logger.info("fallbackTelcoProviderName="+product.getProviderName());
					isFallbackTelcoProviderNotPopulated = false;
				}
				if (isFallbackCableProviderNotPopulated && cableProvidersList != null && (cableProvidersList.contains(product.getParentExternalId()) 
						|| cableProvidersList.contains(product.getProviderExternalId()))){
					salesCenterVo.setValueByName("fallback.cableProviderName",product.getProviderName());
					salesCenterVo.setValueByName("fallback.cableProviderId",product.getProviderExternalId());
					logger.info("fallbackCableProviderName="+product.getProviderName());
					isFallbackCableProviderNotPopulated = false;
				}
			}
		}
		logger.info("populatePivotAssistData_End_in_ProductResultsManager");
	}
	
	
	public void populateProductsData() {
		logger.info("In_populateProductsData");
		String categories [] = {"PP","MIXEDBUNDLES","TRIPLE_PLAY","DOUBLE_PLAY","DOUBLE_PLAY_VI","DOUBLE_PLAY_PV","DOUBLE_PLAY_PI","VIDEO","INTERNET","PHONE","HOMESECURITY","ASIS_PLAN","ELECTRICITY","NATURALGAS","WATER","WASTEREMOVAL","APPLIANCEPROTECTION"};
		List<String> categoryList= Arrays.asList(categories);
		
		SalesCenterVO salesCenterVo = context.getSalesCenterVO();
		Long orderId = Long.valueOf(salesCenterVo.getValueByName("orderId"));
		String state = salesCenterVo.getValueByName("correctedState");
		Map<String, String> frontierPricingGridConfigMap = new HashMap<String, String>();
		Map<String, List<String>> frontierProductDetailsMap = new HashMap<String, List<String>>();
		List<String> fiberProductsList = new ArrayList<String>();
		List<String> broadbandProductsList = new ArrayList<String>();
		try{
			if (FrontierPricingGridCache.INSTANCE.get("frontierPricingGridConfigMap") != null){
				frontierPricingGridConfigMap = (Map<String, String>)FrontierPricingGridCache.INSTANCE.get("frontierPricingGridConfigMap");
			}
			else{
				frontierPricingGridConfigMap = frontierPricingGridConfigDao.getFrontierPricingGridConfig();
				if(frontierPricingGridConfigMap != null && !frontierPricingGridConfigMap.isEmpty()){
					FrontierPricingGridCache.INSTANCE.store("frontierPricingGridConfigMap",frontierPricingGridConfigMap);
				}
			}
			if (FrontierPricingGridCache.INSTANCE.get("frontierProductDetailsMap") != null){
				frontierProductDetailsMap = (Map<String, List<String>>)FrontierPricingGridCache.INSTANCE.get("frontierProductDetailsMap");
			}
			else{
				frontierProductDetailsMap = frontierPricingGridConfigDao.getFrontierProductDetails();
				if(frontierProductDetailsMap != null && !frontierProductDetailsMap.isEmpty()){
					FrontierPricingGridCache.INSTANCE.store("frontierProductDetailsMap",frontierProductDetailsMap);
				}
			}
			if(frontierProductDetailsMap != null && !frontierProductDetailsMap.isEmpty()){
				fiberProductsList = frontierProductDetailsMap.get("Fiber");
				broadbandProductsList = frontierProductDetailsMap.get("Broadband");
			}
		}catch(Exception e){
			logger.warn("Exception while getting PricingGridLink"+e.getMessage());
		}
		List<ProductVOJSON> productVOJSONList = new ArrayList<ProductVOJSON>();
		List<ProductVOJSON> syntheticProductVOJSONList = new ArrayList<ProductVOJSON>();
		List<ProductVOJSON> customerIntractionProductsList = new ArrayList<ProductVOJSON>();
		List<ProductSummaryVO> details = new ArrayList<ProductSummaryVO>();
		boolean isSyntheticBundle = false;
		details = (List<ProductSummaryVO>) getPowerPitchList();
		ProductSummaryVO customerIntractiionProduct = null;
		if(salesCenterVo!= null && salesCenterVo.getValueByName("flowTypeValue")!= null && salesCenterVo.getValueByName("flowTypeValue").contains("consumersInteractions")){
			for (ProductSummaryVO vo :this.context.getAllProductList()){
				if (vo != null && vo.getProviderExternalId() != null && !Utils.isBlank(ceiPowerPitchProviderId) && ceiPowerPitchProviderId.contains(vo.getProviderExternalId())){
					customerIntractiionProduct = vo;
					customerIntractiionProduct.setConsumersInteractionsProduct(true);
					break;
				}
			}
		}
		List<ProductSummaryVO> customerIntractionList = new ArrayList<ProductSummaryVO>();
		logger.info("customerIntractiionProduct=" +customerIntractiionProduct);
		logger.info("details.size()="+details.size());
		if (customerIntractiionProduct != null){
			customerIntractionList.add(customerIntractiionProduct);
			if (details.size() > 0){
				for(int i=0;i<details.size() - 1 ;i++){
					customerIntractionList.add(details.get(i));
				}	
			}
			details = new ArrayList<ProductSummaryVO>();
		}
		if (customerIntractionList.size() >0){
			details.addAll(customerIntractionList);	
		}
		if (details != null) {
			for(int i=0;i<details.size();i++){
				createJSON(details.get(i), orderId);
			}
		}
		logger.debug("product_details_for_power_pitch=" +details);
		for(String category:categoryList){
			List<ProductVOJSON> productList = new ArrayList<ProductVOJSON>();
			if(! category.equalsIgnoreCase("PP")){
				if (category.toUpperCase() != null && (category.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PI")
						|| category.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PV") 
						|| category.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI"))){

					details = (List<ProductSummaryVO>) getProductByIconMap().get("DOUBLE_PLAY");
					if(details != null){
						details = getDoublePlayRenderData(details,category.toUpperCase());
					}
				}
				else{
					details = (List<ProductSummaryVO>) getProductByIconMap().get(category.toUpperCase());
				}
			}
			if (details != null){
				int totalProductsList = 0;
				if(!CollectionUtils.isEmpty(details)) {
					totalProductsList = details.size();
					if(category.equalsIgnoreCase("PP")){
						if (details.size() > 3){
							totalProductsList = details.size() - 1;
						}else{
							totalProductsList = details.size();
						}
					}
					for(int i=0;i<totalProductsList;i++){
						if(category.equalsIgnoreCase("PP")){
							if(details.get(i).isConsumersInteractionsProduct()){
								createJSON(details.get(i), orderId);
								customerIntractionProductsList.add(buildProductJSONVO(details.get(i),category,i,0.0d,orderId, state, frontierPricingGridConfigMap, fiberProductsList, broadbandProductsList));
								productsMap.put("customerIntractionProductsList",customerIntractionProductsList);
								this.setConsumer(true);
							}
							else if(details.get(i).isSyntheticBundle() && details.get(i).getPairedProduct()!= null){
								isSyntheticBundle = true;
								createJSON(details.get(i), orderId);
						    	syntheticProductVOJSONList.add(buildProductJSONVO(details.get(i),category,i,0.0d,orderId, state, frontierPricingGridConfigMap, fiberProductsList, broadbandProductsList));
								this.setSynthetic(true);
							}else{
								createJSON(details.get(i), orderId);
								productVOJSONList.add(buildProductJSONVO(details.get(i),category,i,0.0d,orderId, state, frontierPricingGridConfigMap, fiberProductsList, broadbandProductsList));
								productsMap.put("productVOJSONList",productVOJSONList);
							}
						}else{
							createJSON(details.get(i), orderId);
							productList.add(buildProductJSONVO(details.get(i),category,i,0.0d,orderId, state, frontierPricingGridConfigMap, fiberProductsList, broadbandProductsList));
						}

					}
				}
			}
			if(!syntheticProductVOJSONList.isEmpty()){
				productsMap.put("syntheticProductVOJSONList", syntheticProductVOJSONList);
			}
			logger.info(category +" Products size="+productList.size());
			productsMap.put(category, productList);
			if (details != null){
				Map<String, String> filterProviderNamesAndExtIdsMap = getCategoryWiseProductNameAndExternalId().get(category);
				if(filterProviderNamesAndExtIdsMap != null 
						&& filterProviderNamesAndExtIdsMap.get(Constants.DIRECTV_NAME) == null
						&& getRealTimeStatusMap() != null ){
					if(getRealTimeStatusMap().get(Constants.DIRECTV_NAME) != null 
							&& getRealTimeStatusMap().get(Constants.DIRECTV_NAME).contains(PollingStatus.SUCCESS)
							&& isATTDirecTVAvailable(details)){
						filterProviderNamesAndExtIdsMap.put(Constants.DIRECTV_NAME, Constants.DIRECTV_NAME);
					}
					if(filterProviderNamesAndExtIdsMap.get(Constants.ATTV6) != null && !isATTAvailable(details)){
						filterProviderNamesAndExtIdsMap.remove(Constants.ATTV6);
					}
					if(filterProviderNamesAndExtIdsMap.get(Constants.ATTV6) != null){
						filterProviderNamesAndExtIdsMap.put(Constants.ATTV6,Constants.ATT_NAME);
					}
				}
			}
		}
		logger.info("allCategoryProductsMap size="+productsMap.size());
	}


	public void  createJSON(ProductSummaryVO detailsVO, Long orderId) {
		DecimalFormat format = new DecimalFormat("#0.00");
		JSONObject prodJson = new JSONObject();
		try {
			prodJson.put("orderId", orderId);
			prodJson.put("partnerExternalId", Utils.escapeSpecialCharacters(detailsVO.getProviderExternalId()));
			if(detailsVO.getParentExternalId() != null){
				prodJson.put("img_id", Utils.escapeSpecialCharacters(detailsVO.getParentExternalId()));
			}else{
				prodJson.put("img_id", Utils.escapeSpecialCharacters(detailsVO.getProviderExternalId()));
			}
			prodJson.put("productExernalId",Utils.escapeSpecialCharacters(detailsVO.getExternalId()));
			prodJson.put("productname",Utils.escapeSpecialCharacters(detailsVO.getName()));
			prodJson.put("isPromotion", false);
			prodJson.put("providerName", Utils.escapeSpecialCharacters(detailsVO.getProviderName()));
			prodJson.put("non_recuring", ("$"+format.format(detailsVO.getBaseNonRecurringPrice())));
			prodJson.put("recuring", ("$"+format.format(detailsVO.getBaseRecurringPrice())));
			prodJson.put("capabilityMap", detailsVO.getCapabilityMap());
			prodJson.put("productType", detailsVO.getProductType());
			prodJson.put("providerSourceBaseType", detailsVO.getSource());
			/*
			 * adding TPV and Warm Transfer to JSON
			 */
			prodJson.put("isTPVProduct", detailsVO.isTPVProduct());
			prodJson.put("isWarmTransfer", detailsVO.isWarmTransferProduct());
			String hybrisShell = "false";
			String noEmail = "false";
			String noOrderStatus = "false";
			Map<String, String> metadata = detailsVO.getMetadata();						    
			for(Map.Entry<String, String> mapEntry : metadata.entrySet()){
				if(mapEntry.getValue().equalsIgnoreCase(Constants.HYBRIS_SHELL) || mapEntry.getValue().equalsIgnoreCase(Constants.ATG_LINK)) {
					hybrisShell = "true";
					//break;
				}
				else if(mapEntry.getValue().equalsIgnoreCase(Constants.NO_EMAIL)) {
					noEmail = "true";
				}
				else if(mapEntry.getValue().equalsIgnoreCase(Constants.NO_ORDER_STATUS)) {
					noOrderStatus = "true";
				}
			}
			List<String> promotionMetadata = detailsVO.getPromotionMetaDataList();	
			if(promotionMetadata != null) {
				if(promotionMetadata.contains(Constants.HYBRIS_SHELL) || promotionMetadata.contains(Constants.ATG_LINK)) {
					hybrisShell = "true";
				}
				if(promotionMetadata.contains(Constants.NO_EMAIL)) {
					noEmail = "true";
				}
				if(promotionMetadata.contains(Constants.NO_ORDER_STATUS)) {
					noOrderStatus = "true";
				}
			}
			prodJson.put("hybrisShell", hybrisShell);
			prodJson.put("noEmail", noEmail);
			prodJson.put("noOrderStatus", noOrderStatus);
		} catch (JSONException e) {
			logger.error("error_while_preparing_product_json",e);
		}
		//logger.info("prodJson="+prodJson);
		detailsVO.setProdJson(prodJson);
	}
	
	
	
    private ProductVOJSON buildProductJSONVO(ProductSummaryVO product,String categoryName,int i, Double pairedProductpromoPrice,Long orderId, String state, Map<String,String> frontierPricingGridConfigMap, 
    		List<String> fiberProductsList, List<String> broadbandProductsList){
		
		ProductVOJSON  productVOJSON = new ProductVOJSON();
		String transferAuthenticatedProviders  = ConfigRepo.getString("*.transfer_authenticated_providers");
		Map<String,String> transferAuthenticatedProvidersMap = new HashMap<String, String>();
		List<String> transferAuthenticatedProviderExtIdsList = new ArrayList<String>();
		if (!Utils.isBlank(transferAuthenticatedProviders)) {
			String transferAuthenticatedProvidersList[] = transferAuthenticatedProviders.split("\\|");
			for (String providerIdWithName: transferAuthenticatedProvidersList) {
				String providerValues[] = providerIdWithName.split("=");
				if(!providerValues[0].equals("32416075")){
					transferAuthenticatedProvidersMap.put(providerValues[0], providerValues[1]);
				}
			}
		}
		String providersData = ConfigRepo.getString("*.channelLineupProviders") == null ? null : ConfigRepo.getString("*.channelLineupProviders");
		String[] metaDataKeyArray = {"CONTRACT_TERM","NUM_CHANNELS","CONN_SPEED","LATINO_PKG_INCL"};
		String providerExtId = product.getParentExternalId()!=null?product.getParentExternalId():product.getProviderExternalId();
		String providerName = Utils.escapeSpecialCharacters(product.getProviderName());
		DecimalFormat df = new DecimalFormat("#.##");
		//logger.info("providersData="+providersData);
		productVOJSON.setProviderExtId(product.getProviderExternalId());
		productVOJSON.setProductType(product.getProductType());
		try{
			if (providerExtId != null && providerExtId.equalsIgnoreCase("32937483") && !Utils.isBlank(state) && frontierPricingGridConfigMap!= null && !Utils.isBlank(product.getExternalId())) {
				if(! isFrontierVideoAvailable()){
					productVOJSON.setPricingGridLink(frontierPhoneInternetPricingGrid);
				}
				else{
					String productExtId = product.getExternalId();
					String updatedProductExtId = productExtId.replaceAll("RTS:FRONTIER:", "");
					if(frontierPricingGridStatesList!= null && !frontierPricingGridStatesList.contains(state)){
						state = "MN";
					}
					String pricingGridKey = state;
					
					if(fiberProductsList!= null && fiberProductsList.contains(updatedProductExtId)){
						pricingGridKey = pricingGridKey+"-"+"Fiber";
					}
					else if(broadbandProductsList!= null && broadbandProductsList.contains(updatedProductExtId)){
						pricingGridKey = pricingGridKey+"-"+"Broadband";
					}
					logger.info("pricingGridKey="+pricingGridKey);
					if(frontierPricingGridConfigMap.get(pricingGridKey)!= null){
						productVOJSON.setPricingGridLink(frontierPricingGridConfigMap.get(pricingGridKey));
					}
				}
			}
		}catch(Exception e){
			logger.warn("Exception while setting PricingGridLink"+e.getMessage());
		}

		if(!Utils.isBlank(providersData)){
			String providersList[] = providersData.split("\\|");
			for (String providerIdWithName : providersList){
				String providerValues[] = providerIdWithName.split("=");
				if(providerValues[0].trim().equalsIgnoreCase(providerExtId)){
					productVOJSON.setChannelLineupProvider(true);
					if(Utils.isBlank(providerName)){
						providerName = providerValues[1];
					}
					break;
				}
			}
		}
		if (providerName.equalsIgnoreCase("ATTSTI")){
			providerName = "AT&T";
		}else if (providerName.equalsIgnoreCase("DISH Network")){
			providerName = "Dish";
		}
		productVOJSON.setProductIconList(new ArrayList<String>());
		if(Utils.isProductVideoCapable(product)) {
			productVOJSON.getProductIconList().add("tv");
			productVOJSON.setVideoCapable(true);
		}
		if(Utils.isProductPhoneCapable(product)) {
			productVOJSON.getProductIconList().add("phone");
		}
		if(Utils.isProductInternetCapable(product)) {
			productVOJSON.getProductIconList().add("internet");
		}
		if( !getSelectedExistingProvidersMap().isEmpty() && getSelectedExistingProvidersMap().containsValue(product.getProviderExternalId()) ){
			productVOJSON.setExistingCustomer(true);
		}
		if(transferAuthenticatedProvidersMap!= null && transferAuthenticatedProvidersMap.get(product.getProviderExternalId())!=null){
			productVOJSON.setExistingCustomer(false);
		}
		if (product.getPromotionType() != null &&  product.getPromotionType().equalsIgnoreCase("baseMonthlyDiscount") ){
			if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("absolute")){
				productVOJSON.setPromoPrice(Double.valueOf(product.getPromotionPrice()));
			}else if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("relative")){
				productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - product.getPromotionPrice());
			}
		}else{
			ProductPromotionType productPromoType = Utils.isBaseMonthlyAvailable(product);
			if(productPromoType != null){
				if (Constants.ABSOLUTE.equalsIgnoreCase(productPromoType.getPriceValueType())){
					productVOJSON.setPromoPrice(Double.valueOf(productPromoType.getPriceValue()));
				}else if (Constants.RELATIVE.equalsIgnoreCase(productPromoType.getPriceValueType())){
					productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - productPromoType.getPriceValue());
				}
				product.setShortDescription(productPromoType.getShortDescription());
			}
		}
		List<ProductPromotionType> promotions = product.getPromotions();
		if(promotions != null && !promotions.isEmpty()){
			productVOJSON.setPromotions(promotions);
		}
		if(product.getPromotionDescription() != null){
			productVOJSON.setPromotionDescription(product.getPromotionDescription());
		}
		Map<Float,Float> promoTreeSplitMap = new TreeMap<Float, Float>();
		if (Constants.ATTV6.equals(providerExtId) ||Constants.ATT.equals(providerExtId)){
			if(!product.getPromotions().isEmpty()){
				double promoTotal = 0.0;
				boolean isRelativePromotionExist = false;
				for(ProductPromotionType promo : product.getPromotions()) {
					if (promo.getType() != null &&  promo.getType().equalsIgnoreCase("baseMonthlyDiscount") ){
						if (promo.getPriceValueType() != null && promo.getPriceValueType().equalsIgnoreCase("relative")){
							promoTotal = promoTotal + promo.getPriceValue();
							isRelativePromotionExist = true;
						}else if (promo.getPriceValueType() != null && promo.getPriceValueType().equalsIgnoreCase("absolute")){
							promoTreeSplitMap.put(promo.getPriceValue(), promo.getPriceValue());
						}
					}
				}
				if (promoTreeSplitMap != null && !promoTreeSplitMap.isEmpty() && promoTreeSplitMap.size() > 0){
					for (Float value :promoTreeSplitMap.keySet()){
						productVOJSON.setPromoPrice(value.doubleValue());
						break;
					}
				}else if(isRelativePromotionExist){
					productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - promoTotal);
				}
			}	
		}

		if(product.getPromotionType() != null &&  product.getPromotionCode().equals("Free Installation")){
			productVOJSON.setBaseNonRecurringPrice(0.00);
		}else{
			productVOJSON.setBaseNonRecurringPrice(product.getBaseNonRecurringPrice());
		}
		//logger.info("advisoryPromotion="+ADVISORYPROMOTION);
		if( productVOJSON.isExistingCustomer()){
				if (providerExtId != null && providerExtId.equalsIgnoreCase("32416075")) {
					productVOJSON.setProductDescription("");
				} else {
					productVOJSON.setProductDescription(ADVISORYPROMOTION);
				}
		}else{
			if (!Constants.ATTV6.equals(product.getProviderExternalId())&& !Constants.ATT.equals(product.getProviderExternalId()) && product.getShortDescription() != null){
				if(Constants.COX_RTS_PROVIDER_ID.equals(product.getProviderExternalId())
						&& Utils.isBaseMonthlyAvailable(product) != null
						&& !Utils.isBlank(Utils.getInformationalPromoShortDescription(product))){
					productVOJSON.setProductDescription(product.getShortDescription()+"</br>"+Utils.getInformationalPromoShortDescription(product));
				}
				else if(Constants.COMCAST.equals(product.getProviderExternalId()) && !Utils.isBlank(Utils.getPromoSummary(product))){
					productVOJSON.setProductDescription(Utils.getPromoSummary(product));
				}
				else{
					productVOJSON.setProductDescription(product.getShortDescription());
				}
			}else if(Constants.ATTV6.equals(product.getProviderExternalId()) 
					&& (Constants.ATT_BUILD_PRODUCT_EXID.equals(product.getExternalId())
					|| Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equals(product.getExternalId()))){
				productVOJSON.setProductDescription(product.getDescriptiveInfoValue());
			}else{
				if (product.getPromotionDescription() != null){
					productVOJSON.setProductDescription(product.getPromotionDescription());
				}else{
					productVOJSON.setProductDescription("");
				}
			}
			if(rtProviderDescriptionSuppressList != null && !rtProviderDescriptionSuppressList.isEmpty() ){
				for (String rtProvider : rtProviderDescriptionSuppressList){
					String providerData[] = rtProvider.split("=");
					if(!Utils.isBlank(providerData[0]) && providerData[0].equalsIgnoreCase(product.getProviderExternalId())){
						if(Boolean.valueOf(providerData[1])){
							productVOJSON.setProductDescription("");
						}
						break;
					}
				}
			}
		}
		productVOJSON.setFilterMetaDatMap(new HashMap<String,String>());
		if (product.getPromotionMetaDataList() != null && product.getPromotionMetaDataList().size() > 0){
			for (String metaData : product.getPromotionMetaDataList()){
				if (!Utils.isBlank(metaData)){
					for(String metaDataName:metaDataKeyArray){
						if(metaData.contains("=") && metaData.split("=").length > 1&& metaDataName.equalsIgnoreCase(metaData.split("=")[0])){
							productVOJSON.getFilterMetaDatMap().put(metaData.split("=")[0], metaData.split("=")[1]);
						}else if(metaDataName.equalsIgnoreCase(metaData.split("=")[0])){
							productVOJSON.getFilterMetaDatMap().put(metaData.split("=")[0], metaData.split("=")[0]);
						}
					}
				}
			}
		}
		if(product.getParentExternalId() != null){
			productVOJSON.setImageID(product.getParentExternalId());
		}else{
			productVOJSON.setImageID(product.getProviderExternalId());
		}
		if(Constants.ATTV6.equals(product.getProviderExternalId()) 
				&& isATTProductHasSatellite(product)){
			productVOJSON.setImageID(Constants.ATT_DIRECTV);
			productVOJSON.setExistingCustomer(false);
		}
		if(!Utils.isBlank(product.getEnergyUnitName())){
			productVOJSON.setUnitName("/"+product.getEnergyUnitName());
		}
		if(product.getEnergyTierMap() != null && !product.getEnergyTierMap().isEmpty()){
			productVOJSON.setEnergyTierMap(product.getEnergyTierMap());
			if(product.getEnergyTierMap().get(Constants.ENERGY_RATE) != null){
				productVOJSON.setUsageRate(product.getEnergyTierMap().get(Constants.ENERGY_RATE));
			}else{
				for(Entry<String,Double> map : product.getEnergyTierMap().entrySet()){
					productVOJSON.setUsageRate(map.getValue());
					break;
				}
			}
		}
		if(Constants.ATTV6.equals(product.getProviderExternalId()) 
				&& (product.getName().contains(Constants.BUILD_YOUR_BUNDLE) 
						|| Constants.ATT_BUILD_PRODUCT_EXID.equals(product.getExternalId())
						|| Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equals(product.getExternalId()))){
			if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY")){
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}else if(categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI") ){
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("internet");
			}else if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PI")){
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}else if(categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PV") ){
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("phone");
			}else{
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}
			productVOJSON.setBaseRecNA(Constants.NA);
		}
		
		if(product.getBaseRecurringPrice() != null 
				&& productVOJSON.getPromoPrice() != null){
			Double	promoRoundPrice = Math.round(productVOJSON.getPromoPrice() * 100.0) / 100.0;
			Double	baseRecRoundPrice = Math.round(product.getBaseRecurringPrice() * 100.0) / 100.0;
			if(promoRoundPrice.equals(baseRecRoundPrice)){
				productVOJSON.setPromoPrice(null);
				if(!Constants.COMCAST.equals(product.getProviderExternalId())){
					productVOJSON.setProductDescription("");
				}
			}
		}
		
		/*if(product.getDisplayBasePrice()!= null){
			productVOJSON.setDisplayBasePrice(product.getDisplayBasePrice());
		}
		if(product.getDisplayPromotionPrice()!= null){
			productVOJSON.setDisplayPromotionPrice(product.getDisplayPromotionPrice());
		}*/
		
		
		try{
			if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("LATINO_PKG_INCL") != null && !productVOJSON.getFilterMetaDatMap().get("LATINO_PKG_INCL").equalsIgnoreCase("NA")){
				productVOJSON.setLatinoProduct(productVOJSON.getFilterMetaDatMap().get("LATINO_PKG_INCL"));
			}
		  }catch(Exception e){
			logger.error("error_while_getting LATINO_PKG_INCL from ProductData"+e.getMessage());
		}
		try{
			if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS") != null && !productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS").equalsIgnoreCase("NA")){
				productVOJSON.setChannels(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS")));
				productVOJSON.setChannelsCount(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS")));
			}else{
				productVOJSON.setChannels(Float.valueOf(0));
				//productVOJSON.setChannelsCount(Float.valueOf(0));
			}
		}catch(Exception e){
			logger.error("error_while_getting NUM_CHANNELS from  ProductData"+e.getMessage());
		}
		try{
			if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("CONN_SPEED") != null && !productVOJSON.getFilterMetaDatMap().get("CONN_SPEED").equalsIgnoreCase("NA")){
				productVOJSON.setConnSpeed(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")));
				productVOJSON.setConnectionSpeedCount(String.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")));
			}else{
				productVOJSON.setConnSpeed(Float.valueOf(0));
				//productVOJSON.setConnectionSpeedCount(Float.valueOf(0));
			}
		}catch(Exception e){
			logger.error("error_while_getting CONN_SPEED from ProductData"+e.getMessage());
		}
		try{
			if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM") != null && !productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM").equalsIgnoreCase("NA")){
				productVOJSON.setContractTerm(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM")));
			}
		}catch(Exception e){
			logger.error("error_while_getting CONTRACT_TERM from ProductData"+e.getMessage());
		}
		productVOJSON.setCapabilitMap(product.getCapabilityMap());
		productVOJSON.setProductPointDisplay(product.getPoints() == -1 ? "NA":String.valueOf(product.getPoints()));
		productVOJSON.setProductScore(product.getScore());
		productVOJSON.setSortPrice(getProductSortPrice(product));
		if(product.getDisplayBasePrice()!= null){
			productVOJSON.setDisplayBasePrice(product.getDisplayBasePrice());
			productVOJSON.setSortPrice(product.getDisplayBasePrice());
		}
		if(product.getDisplayPromotionPrice()!= null){
			productVOJSON.setDisplayPromotionPrice(product.getDisplayPromotionPrice());
			productVOJSON.setSortPrice(product.getDisplayPromotionPrice());
		}
		productVOJSON.setBaseRecurringPrice(product.getBaseRecurringPrice());
		productVOJSON.setBaseRecurringPrice(product.getBaseRecurringPrice());
		if(product.getProdJson() != null){
			productVOJSON.setHiddenProductJSON(StringEscapeUtils.unescapeHtml(product.getProdJson().toString()));
		}
		productVOJSON.setProductType(product.getProductType());
		productVOJSON.setProviderName(providerName);
		productVOJSON.setProductName(product.getName());
		productVOJSON.setProviderExtId(product.getProviderExternalId());
		productVOJSON.setParentProviderExtId(providerExtId);
		productVOJSON.setProductExID(product.getExternalId());
		//verizonPriceGridContent verizonBasePriceContent
		if(verizonPriceGridContent != null && !verizonPriceGridContent.isEmpty()){
			for(String content : verizonPriceGridContent){
				if(content.contains(providerExtId)){
					String content1[] = content.split("-");
					productVOJSON.setVerizonPricingGridContent(content1[1]);
					break;
				}
			}
		}
		if(verizonBasePriceContent != null && !verizonBasePriceContent.isEmpty()){
			for(String content : verizonBasePriceContent){
				if(content.contains(providerExtId)){
					String content1[] = content.split("-");
					productVOJSON.setVerizonBasePriceContent(content1[1]);
					break;
				}
			}
		}
		
		if(product.getPairedProduct() != null){
			ProductVOJSON  pairedProductVOJSON = buildMixedBundleProductJSONVO(product.getPairedProduct(),categoryName,i,pairedProductpromoPrice,orderId, state, frontierPricingGridConfigMap, 
		    		fiberProductsList, broadbandProductsList);
			productVOJSON.setSortPrice(productVOJSON.getSortPrice() + pairedProductVOJSON.getSortPrice());
			productVOJSON.setTotalPoints((product.getPoints() == -1 ? 0:product.getPoints())+(product.getPairedProduct().getPoints() == -1 ? 0:product.getPairedProduct().getPoints()));
			productVOJSON.setTotalPrice((product.getBaseRecurringPrice()+product.getPairedProduct().getBaseRecurringPrice()));
			productVOJSON.setPairedProduct(Utils.toJson(pairedProductVOJSON));
			productVOJSON.setPairedProductVOJSON(pairedProductVOJSON);
			productVOJSON.setProductScore(product.getScore() + pairedProductVOJSON.getProductScore());
			try{
				if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("LATINO_PKG_INCL") != null && !productVOJSON.getFilterMetaDatMap().get("LATINO_PKG_INCL").equalsIgnoreCase("NA")){
					productVOJSON.setLatinoProduct(productVOJSON.getFilterMetaDatMap().get("LATINO_PKG_INCL"));
				}
			   }catch(Exception e){
					logger.error("error_while_getting LATINO_PKG_INCL from ProductData"+e.getMessage());
			   }
			try{
				if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS") != null && !productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS").equalsIgnoreCase("NA")){
					productVOJSON.setChannels(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS")));
					productVOJSON.setChannelsCount(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS")));
				}else{
					productVOJSON.setChannels(Float.valueOf(0));
					//productVOJSON.setChannelsCount(Float.valueOf(0));
				}
			   }catch(Exception e){
					logger.error("error_while_getting NUM_CHANNELS from ProductData"+e.getMessage());
			   }
			try{
				if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("CONN_SPEED") != null && !productVOJSON.getFilterMetaDatMap().get("CONN_SPEED").equalsIgnoreCase("NA")){
					productVOJSON.setConnSpeed(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")));
					productVOJSON.setConnectionSpeedCount(String.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")));
				}else{
					productVOJSON.setConnSpeed(Float.valueOf(0));
					//productVOJSON.setConnectionSpeedCount(Float.valueOf(0));
				}
			   }catch(Exception e){
					logger.error("error_while_getting CONN_SPEED from ProductData"+e.getMessage());
			   }
			try{
				if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM") != null && !productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM").equalsIgnoreCase("NA")){
					productVOJSON.setContractTerm(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM")));
				}
			   }catch(Exception e){
					logger.error("error_while_getting CONTRACT_TERM from ProductData"+e.getMessage());
			   }
			
			if(productVOJSON.getPromoPrice() != null && pairedProductVOJSON.getPromoPrice() != null){
				productVOJSON.setTotalPromotionPrice(productVOJSON.getPromoPrice() + pairedProductVOJSON.getPromoPrice());
			}else if(productVOJSON.getPromoPrice() == null && pairedProductVOJSON.getPromoPrice() != null){
				if(productVOJSON.getDisplayBasePrice()!= null){
					productVOJSON.setTotalPromotionPrice(productVOJSON.getDisplayBasePrice() + pairedProductVOJSON.getPromoPrice());
				}
				else{
					productVOJSON.setTotalPromotionPrice(productVOJSON.getBaseRecurringPrice() + pairedProductVOJSON.getPromoPrice());
				}
			}else if(productVOJSON.getPromoPrice() != null && pairedProductVOJSON.getPromoPrice() == null){
				productVOJSON.setTotalPromotionPrice(pairedProductVOJSON.getBaseRecurringPrice() + productVOJSON.getPromoPrice());
			}else{
				productVOJSON.setTotalPromotionPrice(0.0);
			}
			
			if(pairedProductVOJSON.getDisplayBasePrice() != null){
				if(productVOJSON.getDisplayBasePrice() != null){
					productVOJSON.setTotalDisplayBasePrice(productVOJSON.getDisplayBasePrice() + pairedProductVOJSON.getDisplayBasePrice());
					
				}else{
					productVOJSON.setTotalDisplayBasePrice(pairedProductVOJSON.getDisplayBasePrice() + productVOJSON.getBaseRecurringPrice());
				}
			}else if(productVOJSON.getDisplayBasePrice() != null){
				productVOJSON.setTotalDisplayBasePrice(productVOJSON.getDisplayBasePrice() + pairedProductVOJSON.getBaseRecurringPrice());
			}
			
			if(pairedProductVOJSON.getDisplayPromotionPrice() != null){
				if(productVOJSON.getDisplayPromotionPrice() != null){
					productVOJSON.setTotalDisplayPromotionPrice(productVOJSON.getDisplayPromotionPrice() + pairedProductVOJSON.getDisplayPromotionPrice());
				}else{
					if(productVOJSON.getPromoPrice() != null){
						productVOJSON.setTotalDisplayPromotionPrice(pairedProductVOJSON.getDisplayPromotionPrice() + productVOJSON.getPromoPrice());
					}else{
						if(productVOJSON.getDisplayBasePrice() != null){
							productVOJSON.setTotalDisplayPromotionPrice(pairedProductVOJSON.getDisplayPromotionPrice() + productVOJSON.getDisplayBasePrice());

						}else{
							productVOJSON.setTotalDisplayPromotionPrice(pairedProductVOJSON.getDisplayPromotionPrice() + productVOJSON.getBaseRecurringPrice());
						}
					}
				}
			}else if(productVOJSON.getDisplayPromotionPrice() != null){
				if(pairedProductVOJSON.getPromoPrice() != null){
					productVOJSON.setTotalDisplayPromotionPrice(productVOJSON.getDisplayPromotionPrice() + pairedProductVOJSON.getPromoPrice());
				}else{
					productVOJSON.setTotalDisplayPromotionPrice(productVOJSON.getDisplayPromotionPrice() + pairedProductVOJSON.getBaseRecurringPrice());
				}
			}else if(productVOJSON.getTotalPromotionPrice() != null && productVOJSON.getTotalPromotionPrice() !=0.0){
				//productVOJSON.setTotalDisplayPromotionPrice(productVOJSON.getTotalPromotionPrice());
			}
			if(productVOJSON.getTotalDisplayPromotionPrice() != null){
				productVOJSON.setSortPrice(productVOJSON.getTotalDisplayPromotionPrice());
			}else if(productVOJSON.getTotalPromotionPrice() != null && productVOJSON.getTotalPromotionPrice() !=0.0){
				productVOJSON.setSortPrice(productVOJSON.getTotalPromotionPrice());
			}
			else if(productVOJSON.getTotalDisplayBasePrice() != null){
				productVOJSON.setSortPrice(productVOJSON.getTotalDisplayBasePrice());
			}
			if(product.isSyntheticBundle() && product.getPairedProduct() != null && !Utils.isBlank(product.getDisplayPricingGrid()) && Utils.isDisplayPricingGridNotEmpty(product.getDisplayPricingGrid())){
				try {
					if(pairedProductVOJSON != null){
						if(pairedProductVOJSON.getDisplayPromotionPrice() != null ){
							pairedProductpromoPrice = pairedProductVOJSON.getDisplayPromotionPrice();
						}
						else if(pairedProductVOJSON.getPromoPrice() != null ){
							pairedProductpromoPrice = pairedProductVOJSON.getPromoPrice();
						}else if(pairedProductVOJSON.getBaseRecurringPrice() != null){
							pairedProductpromoPrice = pairedProductVOJSON.getBaseRecurringPrice();
						}
					}
					JSONObject jsonObj = new  JSONObject(product.getDisplayPricingGrid());
		            JSONArray arr = new JSONArray();
		        	arr.put("Plus with Hopper");
		        	arr.put("Total Mixed Bundle Price");
		            
		            JSONObject dvrObj = (JSONObject) jsonObj.opt("NO_DVR");
		            Map<String, Map<String, String>> pricingGridJsonMap = new LinkedHashMap<String, Map<String, String>>();
		           
		            for (int j = 1 ; j < dvrObj.length()+1; j++) {
		            	 JSONObject Tvs = (JSONObject) dvrObj.opt("TV"+j);
		            	 if(pricingGridJsonMap.get("TV"+j) == null){
		                	 pricingGridJsonMap.put("TV"+j, new LinkedHashMap<String, String>());
		                 }
		                 Map<String, String> priceData = pricingGridJsonMap.get("TV"+j);
		                 priceData.put("NoOfTv",j+" TV");
		                 String val = "";
		                 
		                 if(Tvs.opt("MTH") != null && Tvs.opt("MTH") != ""){
		                	 val = (String)Tvs.opt("MTH");
		                 } else if(Tvs.opt("M2M") != null && Tvs.opt("M2M") != ""){
		                	 val = (String)Tvs.opt("M2M");
		                 }
		                 
		                 String baseVal = "";
		                 String headerData = "";
		                 String Months = "";
		                 if(Tvs.opt("A24M") != null && Tvs.opt("A24M") != ""){
		                     baseVal = (String)Tvs.opt("A24M");
		                     headerData = "After 24 Months";
		                     Months = "A24M";
		                 } else if(Tvs.opt("A12M") != null && Tvs.opt("A12M") != ""){
		                     baseVal = (String)Tvs.opt("A12M");
		                     headerData = "After 12 Months";
		                     Months = "A12M";
		                   }
		                 priceData.put("header1","Monthly Price");
		                 priceData.put("header2",headerData);
		                 val = val.replace("$", "");
		                 baseVal = baseVal.replace("$", "");
		                 Double  MTHprice = Double.parseDouble(val);
		                 Double AMPrice = Double.parseDouble(baseVal);
		                 priceData.put("NO_DVR_MTH","$"+df.format(MTHprice));
		                 priceData.put("NO_DVR_"+Months,"$"+df.format(AMPrice));
		                 Double MTHtotal = MTHprice + pairedProductpromoPrice;
		                 Double AMTotal = AMPrice + pairedProductpromoPrice;

		                 priceData.put("TOTAL_MTH", "$"+df.format(MTHtotal));
		                 priceData.put("TOTAL_A12M", "$"+df.format(AMTotal));
		                 pricingGridJsonMap.put("TV"+j, priceData);
		                 
		            }
		            JSONObject bundlePriceJsonData  = new JSONObject(pricingGridJsonMap);
		            JSONObject pricingGridJsonData  = new JSONObject();
		            pricingGridJsonData.put("headers",arr);
		            pricingGridJsonData.put("PRICE_JSON",bundlePriceJsonData);
					productVOJSON.setPricingGridJson(pricingGridJsonData.toString());
					
				} catch (Exception e) {
					logger.error("error_while_getting DisplayPricingGrid"+e.getMessage());
				}
			}
		}
		else if(!Utils.isBlank(product.getDisplayPricingGrid()) && Utils.isDisplayPricingGridNotEmpty(product.getDisplayPricingGrid())){
			productVOJSON.setPricingGridJson(buildPriceGrid(product.getDisplayPricingGrid(),product.getProviderExternalId()));
		}
		return productVOJSON;
		
	}
    
    private String buildPriceGrid(String displayPricingGrid, String providerExtId) {
		 
		JSONArray arr = new JSONArray();
		JSONObject priceGridJsonData  = new JSONObject();
		try{
	         
	         JSONObject jsonObj = new  JSONObject(displayPricingGrid);
	         Map<String, Map<String, String>> pricingGridJsonMap = new LinkedHashMap<String, Map<String, String>>();
	         
	         for (int i = 1 ; i < jsonObj.length()+1; i++) {
	         	JSONObject dvrObj = null;
	         	String headerValue = "";
	         	if(i == 1){
	                dvrObj = (JSONObject) jsonObj.opt("NO_DVR");
	                if(Constants.DISH.equals(providerExtId)){
	                 headerValue = "NO_DVR";
	                 arr.put("Plus with Hopper");
	                }else{
	                	headerValue = "NO_DVR";
	                	arr.put("NO DVR");
	                }
	               }else if(i == 2){
	                dvrObj = (JSONObject) jsonObj.opt("EN_DVR");
	                headerValue = "EN_DVR";
	                arr.put("ENH DVR");
	               }
	               else if(i == 3){
	                dvrObj = (JSONObject) jsonObj.opt("PR_DVR");
	                headerValue = "PR_DVR";
	                arr.put("PREM DVR");
	               }
	         
		            if(dvrObj != null){
			            for (int j = 1 ; j < dvrObj.length()+1; j++) {
			            	 JSONObject noOfTv = (JSONObject) dvrObj.opt("TV"+j);
			                 String val = (String)noOfTv.get("MTH");
			                 
			                 String baseVal = "";
			                 String headerData = "";
			                 if(noOfTv.opt("A24M") != null && noOfTv.opt("A24M") != ""){
			                     baseVal = (String)noOfTv.opt("A24M");
			                     headerData = "After 24 Months";
			                 }else if(noOfTv.opt("A12M") != null && noOfTv.opt("A12M") != ""){
			                     baseVal = (String)noOfTv.opt("A12M");
			                     headerData = "After 12 Months";
			                 }
			                 if(pricingGridJsonMap.get("TV"+j) == null){
			                	 pricingGridJsonMap.put("TV"+j, new LinkedHashMap<String, String>());
			                 }
			                 Map<String, String> priceData = pricingGridJsonMap.get("TV"+j);
			                 
			                 priceData.put("NoOfTv",j+" TV");
			                 priceData.put("header1","Monthly Price");
			                 priceData.put("header2",headerData);
			                 priceData.put(headerValue+"_MTH", val);
			                 priceData.put(headerValue+"_A12M", baseVal);
			                 pricingGridJsonMap.put("TV"+j, priceData);
			            }
		            }
	         }
	         JSONObject bundlePriceJsonData  = new JSONObject(pricingGridJsonMap);
	         priceGridJsonData.put("headers",arr);
	         priceGridJsonData.put("PRICE_JSON",bundlePriceJsonData);
		} catch (Exception e) {
			logger.error("error_while_buildPriceGrid"+e.getMessage());
		}
		 return priceGridJsonData.toString();
		
	}
    
    public Double getProductSortPrice(ProductSummaryVO summaryVO){  
		Double sortPrice = summaryVO.getBaseRecurringPrice();
		boolean isBaseMonthly = false;
		if(summaryVO.getPromotionPrice()!=null){
			if(!summaryVO.getPromotions().isEmpty()){
				if(Constants.BASE_MONTHLY_DISCOUNT.equalsIgnoreCase(summaryVO.getPromotionType())){
					if( Constants.ABSOLUTE.equalsIgnoreCase(summaryVO.getPromotionPriceValueType()) ){
						isBaseMonthly = true;
						sortPrice  = Double.valueOf(summaryVO.getPromotionPrice());
					}else if(Constants.RELATIVE.equalsIgnoreCase(summaryVO.getPromotionPriceValueType())){
						isBaseMonthly = true;
						sortPrice = summaryVO.getBaseRecurringPrice() - summaryVO.getPromotionPrice();
					}
				}
				if(isBaseMonthly && summaryVO.getBaseRecurringPrice()!= null 
						&& sortPrice < summaryVO.getBaseRecurringPrice() 
						&& !summaryVO.getProviderExternalId().equals("4353598")){
					//is exiting customer 
					if(selectedExistingProvidersMap.containsValue(summaryVO.getProviderExternalId())){
						sortPrice = summaryVO.getBaseRecurringPrice();
					}			
				}
			}
		}
		return sortPrice;
	}

private ProductVOJSON buildMixedBundleProductJSONVO(ProductSummaryVO product,String categoryName,int i, Double pairedProductpromoPrice,Long orderId, String state, Map<String,String> frontierPricingGridConfigMap, 
		List<String> fiberProductsList, List<String> broadbandProductsList){
		createJSON(product, orderId);
		ProductVOJSON  productVOJSON = new ProductVOJSON();
		String providersData = ConfigRepo.getString("*.channelLineupProviders") == null ? null : ConfigRepo.getString("*.channelLineupProviders");
		String[] metaDataKeyArray = {"CONTRACT_TERM","NUM_CHANNELS","CONN_SPEED","LATINO_PKG_INCL"};
		String providerExtId = product.getParentExternalId()!=null?product.getParentExternalId():product.getProviderExternalId();
		String providerName = Utils.escapeSpecialCharacters(product.getProviderName());
		DecimalFormat df = new DecimalFormat("#.##");
		//logger.info("providersData="+providersData);
		productVOJSON.setProviderExtId(product.getProviderExternalId());
		productVOJSON.setProductType(product.getProductType());
		if(product.isSyntheticBundle() && product.getPairedProduct() != null && !Utils.isBlank(product.getDisplayPricingGrid()) && Utils.isDisplayPricingGridNotEmpty(product.getDisplayPricingGrid())){
			try {
				JSONObject jsonObj = new  JSONObject(product.getDisplayPricingGrid());
	            JSONArray arr = new JSONArray();
	        	arr.put("Plus with Hopper");
	        	arr.put("Total Mixed Bundle Price");
	            
	            JSONObject dvrObj = (JSONObject) jsonObj.opt("NO_DVR");
	            Map<String, Map<String, String>> pricingGridJsonMap = new LinkedHashMap<String, Map<String, String>>();
	           
	            for (int j = 1 ; j < dvrObj.length()+1; j++) {
	            	 JSONObject Tvs = (JSONObject) dvrObj.opt("TV"+j);
	            	 if(pricingGridJsonMap.get("TV"+j) == null){
	                	 pricingGridJsonMap.put("TV"+j, new LinkedHashMap<String, String>());
	                 }
	                 Map<String, String> priceData = pricingGridJsonMap.get("TV"+j);
	                 priceData.put("NoOfTv",j+" TV");
	                 String val = "";
	                 
	                 if(Tvs.opt("MTH") != null && Tvs.opt("MTH") != ""){
	                	 val = (String)Tvs.opt("MTH");
	                 } else if(Tvs.opt("M2M") != null && Tvs.opt("M2M") != ""){
	                	 val = (String)Tvs.opt("M2M");
	                 }
	                 
	                 String baseVal = "";
	                 String headerData = "";
	                 String Months = "";
	                 if(Tvs.opt("A24M") != null && Tvs.opt("A24M") != ""){
	                     baseVal = (String)Tvs.opt("A24M");
	                     headerData = "After 24 Months";
	                     Months = "A24M";
	                 } else if(Tvs.opt("A12M") != null && Tvs.opt("A12M") != ""){
	                     baseVal = (String)Tvs.opt("A12M");
	                     headerData = "After 12 Months";
	                     Months = "A12M";
	                   }
	                 priceData.put("header1","Monthly Price");
	                 priceData.put("header2",headerData);
	                 val = val.replace("$", "");
	                 baseVal = baseVal.replace("$", "");
	                 Double  MTHprice = Double.parseDouble(val);
	                 Double AMPrice = Double.parseDouble(baseVal);
	                 priceData.put("NO_DVR_MTH","$"+df.format(MTHprice));
	                 priceData.put("NO_DVR_"+Months,"$"+df.format(AMPrice));
	                 Double MTHtotal = MTHprice + pairedProductpromoPrice;
	                 Double AMTotal = AMPrice + pairedProductpromoPrice;

	                 priceData.put("TOTAL_MTH", "$"+df.format(MTHtotal));
	                 priceData.put("TOTAL_A12M", "$"+df.format(AMTotal));
	                 pricingGridJsonMap.put("TV"+j, priceData);
	                 
	            }
	            JSONObject bundlePriceJsonData  = new JSONObject(pricingGridJsonMap);
	            JSONObject pricingGridJsonData  = new JSONObject();
	            pricingGridJsonData.put("headers",arr);
	            pricingGridJsonData.put("PRICE_JSON",bundlePriceJsonData);
				productVOJSON.setPricingGridJson(pricingGridJsonData.toString());
				
			} catch (Exception e) {
				logger.error("error_while_getting DisplayPricingGrid"+e.getMessage());
			}
		}else if(!Utils.isBlank(product.getDisplayPricingGrid()) && Utils.isDisplayPricingGridNotEmpty(product.getDisplayPricingGrid())){
			productVOJSON.setPricingGridJson(buildPriceGrid(product.getDisplayPricingGrid(),product.getProviderExternalId()));
		}
		try{
			if (providerExtId != null && providerExtId.equalsIgnoreCase("32937483") && !Utils.isBlank(state) && frontierPricingGridConfigMap!= null && !Utils.isBlank(product.getExternalId())) {
				if(! isFrontierVideoAvailable()){
					productVOJSON.setPricingGridLink(frontierPhoneInternetPricingGrid);
				}
				else{
					String productExtId = product.getExternalId();
					String updatedProductExtId = productExtId.replaceAll("RTS:FRONTIER:", "");
					if(frontierPricingGridStatesList!= null && !frontierPricingGridStatesList.contains(state)){
						state = "MN";
					}
					String pricingGridKey = state;
					if(fiberProductsList!= null && fiberProductsList.contains(updatedProductExtId)){
						pricingGridKey = pricingGridKey+"-"+"Fiber";
					}
					else if(broadbandProductsList!= null && broadbandProductsList.contains(updatedProductExtId)){
						pricingGridKey = pricingGridKey+"-"+"Broadband";
					}
					logger.info("pricingGridKey="+pricingGridKey);
					if(frontierPricingGridConfigMap.get(pricingGridKey)!= null){
						logger.info("frontierPricingGrid="+frontierPricingGridConfigMap.get(pricingGridKey));
						productVOJSON.setPricingGridLink(frontierPricingGridConfigMap.get(pricingGridKey));
					}
				}
			}
		}catch(Exception e){
			logger.warn("Exception while setting PricingGridLink"+e.getMessage());
		}

		if(!Utils.isBlank(providersData)){
			String providersList[] = providersData.split("\\|");
			for (String providerIdWithName : providersList){
				String providerValues[] = providerIdWithName.split("=");
				if(providerValues[0].trim().equalsIgnoreCase(providerExtId)){
					productVOJSON.setChannelLineupProvider(true);
					if(Utils.isBlank(providerName)){
						providerName = providerValues[1];
					}
					break;
				}
			}
		}
		if (providerName.equalsIgnoreCase("ATTSTI")){
			providerName = "AT&T";
		}else if (providerName.equalsIgnoreCase("DISH Network")){
			providerName = "Dish";
		}
		productVOJSON.setProductIconList(new ArrayList<String>());
		if(Utils.isProductVideoCapable(product)) {
			productVOJSON.getProductIconList().add("tv");
			productVOJSON.setVideoCapable(true);
		}
		if(Utils.isProductPhoneCapable(product)) {
			productVOJSON.getProductIconList().add("phone");
		}
		if(Utils.isProductInternetCapable(product)) {
			productVOJSON.getProductIconList().add("internet");
		}
		if( !getSelectedExistingProvidersMap().isEmpty() && getSelectedExistingProvidersMap().containsValue(product.getProviderExternalId()) ){
			productVOJSON.setExistingCustomer(true);
		}
		if (product.getPromotionType() != null &&  product.getPromotionType().equalsIgnoreCase("baseMonthlyDiscount") ){
			if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("absolute")){
				productVOJSON.setPromoPrice(Double.valueOf(product.getPromotionPrice()));
			}else if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("relative")){
				productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - product.getPromotionPrice());
			}
		}else{
			ProductPromotionType productPromoType = Utils.isBaseMonthlyAvailable(product);
			if(productPromoType != null){
				if (Constants.ABSOLUTE.equalsIgnoreCase(productPromoType.getPriceValueType())){
					productVOJSON.setPromoPrice(Double.valueOf(productPromoType.getPriceValue()));
				}else if (Constants.RELATIVE.equalsIgnoreCase(productPromoType.getPriceValueType())){
					productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - productPromoType.getPriceValue());
				}
				product.setShortDescription(productPromoType.getShortDescription());
			}
		}
		List<ProductPromotionType> promotions = product.getPromotions();
		if(promotions != null && !promotions.isEmpty()){
			productVOJSON.setPromotions(promotions);
		}
		if(product.getPromotionDescription() != null){
			productVOJSON.setPromotionDescription(product.getPromotionDescription());
		}
		Map<Float,Float> promoTreeSplitMap = new TreeMap<Float, Float>();
		if (Constants.ATTV6.equals(providerExtId) ||Constants.ATT.equals(providerExtId)){
			if(!product.getPromotions().isEmpty()){
				double promoTotal = 0.0;
				boolean isRelativePromotionExist = false;
				for(ProductPromotionType promo : product.getPromotions()) {
					if (promo.getType() != null &&  promo.getType().equalsIgnoreCase("baseMonthlyDiscount") ){
						if (promo.getPriceValueType() != null && promo.getPriceValueType().equalsIgnoreCase("relative")){
							promoTotal = promoTotal + promo.getPriceValue();
							isRelativePromotionExist = true;
						}else if (promo.getPriceValueType() != null && promo.getPriceValueType().equalsIgnoreCase("absolute")){
							promoTreeSplitMap.put(promo.getPriceValue(), promo.getPriceValue());
						}
					}
				}
				if (promoTreeSplitMap != null && !promoTreeSplitMap.isEmpty() && promoTreeSplitMap.size() > 0){
					for (Float value :promoTreeSplitMap.keySet()){
						productVOJSON.setPromoPrice(value.doubleValue());
						break;
					}
				}else if(isRelativePromotionExist){
					productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - promoTotal);
				}
			}	
		}

		if(product.getPromotionType() != null &&  product.getPromotionCode().equals("Free Installation")){
			productVOJSON.setBaseNonRecurringPrice(0.00);
		}else{
			productVOJSON.setBaseNonRecurringPrice(product.getBaseNonRecurringPrice());
		}
		//logger.info("advisoryPromotion="+ADVISORYPROMOTION);
		if( productVOJSON.isExistingCustomer()){
			if (providerExtId != null && providerExtId.equalsIgnoreCase("32416075")) {
				productVOJSON.setProductDescription("");
			} else {
				productVOJSON.setProductDescription(ADVISORYPROMOTION);
			}
		}else{
			if (!Constants.ATTV6.equals(product.getProviderExternalId())&& !Constants.ATT.equals(product.getProviderExternalId()) && product.getShortDescription() != null){
				if(Constants.COX_RTS_PROVIDER_ID.equals(product.getProviderExternalId())
						&& Utils.isBaseMonthlyAvailable(product) != null
						&& !Utils.isBlank(Utils.getInformationalPromoShortDescription(product))){
					productVOJSON.setProductDescription(product.getShortDescription()+"</br>"+Utils.getInformationalPromoShortDescription(product));
				}
				else if(Constants.COMCAST.equals(product.getProviderExternalId()) && !Utils.isBlank(Utils.getPromoSummary(product))){
					productVOJSON.setProductDescription(Utils.getPromoSummary(product));
				}
				else{
					productVOJSON.setProductDescription(product.getShortDescription());
				}
			}else if(Constants.ATTV6.equals(product.getProviderExternalId()) 
					&& (Constants.ATT_BUILD_PRODUCT_EXID.equals(product.getExternalId())
					|| Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equals(product.getExternalId()))){
				productVOJSON.setProductDescription(product.getDescriptiveInfoValue());
			}else{
				if (product.getPromotionDescription() != null){
					productVOJSON.setProductDescription(product.getPromotionDescription());
				}else{
					productVOJSON.setProductDescription("");
				}
			}
			if(rtProviderDescriptionSuppressList != null && !rtProviderDescriptionSuppressList.isEmpty() ){
				for (String rtProvider : rtProviderDescriptionSuppressList){
					String providerData[] = rtProvider.split("=");
					if(!Utils.isBlank(providerData[0]) && providerData[0].equalsIgnoreCase(product.getProviderExternalId())){
						if(Boolean.valueOf(providerData[1])){
							productVOJSON.setProductDescription("");
						}
						break;
					}
				}
			}
			
		}
		productVOJSON.setFilterMetaDatMap(new HashMap<String,String>());
		if (product.getPromotionMetaDataList() != null && product.getPromotionMetaDataList().size() > 0){
			for (String metaData : product.getPromotionMetaDataList()){
				if (!Utils.isBlank(metaData)){
					for(String metaDataName:metaDataKeyArray){
						if(metaData.contains("=") && metaData.split("=").length > 1&& metaDataName.equalsIgnoreCase(metaData.split("=")[0])){
							productVOJSON.getFilterMetaDatMap().put(metaData.split("=")[0], metaData.split("=")[1]);
						}else if(metaDataName.equalsIgnoreCase(metaData.split("=")[0])){
							productVOJSON.getFilterMetaDatMap().put(metaData.split("=")[0], metaData.split("=")[0]);
						}
					}
				}
			}
		}
		if(product.getParentExternalId() != null){
			productVOJSON.setImageID(product.getParentExternalId());
		}else{
			productVOJSON.setImageID(product.getProviderExternalId());
		}
		if(Constants.ATTV6.equals(product.getProviderExternalId()) 
				&& isATTProductHasSatellite(product)){
			productVOJSON.setImageID(Constants.ATT_DIRECTV);
			productVOJSON.setExistingCustomer(false);
		}
		if(!Utils.isBlank(product.getEnergyUnitName())){
			productVOJSON.setUnitName("/"+product.getEnergyUnitName());
		}
		if(product.getEnergyTierMap() != null && !product.getEnergyTierMap().isEmpty()){
			productVOJSON.setEnergyTierMap(product.getEnergyTierMap());
			if(product.getEnergyTierMap().get(Constants.ENERGY_RATE) != null){
				productVOJSON.setUsageRate(product.getEnergyTierMap().get(Constants.ENERGY_RATE));
			}else{
				for(Entry<String,Double> map : product.getEnergyTierMap().entrySet()){
					productVOJSON.setUsageRate(map.getValue());
					break;
				}
			}
		}
		if(Constants.ATTV6.equals(product.getProviderExternalId()) 
				&& (product.getName().contains(Constants.BUILD_YOUR_BUNDLE) 
						|| Constants.ATT_BUILD_PRODUCT_EXID.equals(product.getExternalId())
						|| Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equals(product.getExternalId()))){
			if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY")){
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}else if(categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI") ){
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("internet");
			}else if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PI")){
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}else if(categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PV") ){
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("phone");
			}else{
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}
			productVOJSON.setBaseRecNA(Constants.NA);
		}
		
		if(product.getBaseRecurringPrice() != null 
				&& productVOJSON.getPromoPrice() != null){
			Double	promoRoundPrice = Math.round(productVOJSON.getPromoPrice() * 100.0) / 100.0;
			Double	baseRecRoundPrice = Math.round(product.getBaseRecurringPrice() * 100.0) / 100.0;
			if(promoRoundPrice.equals(baseRecRoundPrice)){
				productVOJSON.setPromoPrice(null);
				if(!Constants.COMCAST.equals(product.getProviderExternalId())){
					productVOJSON.setProductDescription("");
				}
			}
		}
		
		if(product.getDisplayBasePrice()!= null){
			productVOJSON.setDisplayBasePrice(product.getDisplayBasePrice());
		}
		if(product.getDisplayPromotionPrice()!= null){
			productVOJSON.setDisplayPromotionPrice(product.getDisplayPromotionPrice());
		}
		
		try{
			if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("LATINO_PKG_INCL") != null && !productVOJSON.getFilterMetaDatMap().get("LATINO_PKG_INCL").equalsIgnoreCase("NA")){
				productVOJSON.setLatinoProduct(productVOJSON.getFilterMetaDatMap().get("LATINO_PKG_INCL"));
			}
		}catch(Exception e){
			logger.error("error_while_getting  LATINO_PKG_INCL from ProductData"+e.getMessage());
		}
		try{
			if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS") != null && !productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS").equalsIgnoreCase("NA")){
				productVOJSON.setChannels(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS")));
				productVOJSON.setChannelsCount(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS")));
			}else{
				productVOJSON.setChannels(Float.valueOf(0));
				//productVOJSON.setChannelsCount(Float.valueOf(0));
			}
		}catch(Exception e){
			logger.error("error_while_getting NUM_CHANNELS from ProductData"+e.getMessage());
		}
		try{
			if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("CONN_SPEED") != null && !productVOJSON.getFilterMetaDatMap().get("CONN_SPEED").equalsIgnoreCase("NA")){
				productVOJSON.setConnSpeed(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")));
					productVOJSON.setConnectionSpeedCount(String.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")));
			}else{
				productVOJSON.setConnSpeed(Float.valueOf(0));
				//productVOJSON.setConnectionSpeedCount(Float.valueOf(0));
			}
		}catch(Exception e){
			logger.error("error_while_getting CONN_SPEED from ProductData"+e.getMessage());
		}
		try{
			if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM") != null && !productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM").equalsIgnoreCase("NA")){
				productVOJSON.setContractTerm(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM")));
			}
		}catch(Exception e){
			logger.error("error_while_getting CONTRACT_TERM from ProductData"+e.getMessage());
		}
		productVOJSON.setCapabilitMap(product.getCapabilityMap());
		productVOJSON.setProductPointDisplay(product.getPoints() == -1 ? "NA":String.valueOf(product.getPoints()));
		productVOJSON.setProductScore(product.getScore());
		productVOJSON.setSortPrice(getProductSortPrice(product));
		if(product.getDisplayBasePrice()!= null){
			productVOJSON.setDisplayBasePrice(product.getDisplayBasePrice());
			productVOJSON.setSortPrice(product.getDisplayBasePrice());
		}
		if(product.getDisplayPromotionPrice()!= null){
			productVOJSON.setDisplayPromotionPrice(product.getDisplayPromotionPrice());
			productVOJSON.setSortPrice(product.getDisplayPromotionPrice());
		}
		productVOJSON.setBaseRecurringPrice(product.getBaseRecurringPrice());
		productVOJSON.setBaseRecurringPrice(product.getBaseRecurringPrice());
		if(product.getProdJson() != null){
			productVOJSON.setHiddenProductJSON(StringEscapeUtils.unescapeHtml(product.getProdJson().toString()));
		}
		productVOJSON.setProductType(product.getProductType());
		productVOJSON.setProviderName(providerName);
		productVOJSON.setProductName(product.getName());
		productVOJSON.setProviderExtId(product.getProviderExternalId());
		productVOJSON.setParentProviderExtId(providerExtId);
		productVOJSON.setProductExID(product.getExternalId());
		if(verizonPriceGridContent != null && !verizonPriceGridContent.isEmpty()){
			for(String content : verizonPriceGridContent){
				if(content.contains(providerExtId)){
					String content1[] = content.split("-");
					productVOJSON.setVerizonPricingGridContent(content1[1]);
					break;
				}
			}
		}
		if(verizonBasePriceContent != null && !verizonBasePriceContent.isEmpty()){
			for(String content : verizonBasePriceContent){
				if(content.contains(providerExtId)){
					String content1[] = content.split("-");
					productVOJSON.setVerizonBasePriceContent(content1[1]);
					break;
				}
			}
		}
		return productVOJSON;
		
	}

	public boolean isHughesNetServiceable(){
		if(productsMap != null && !productsMap.isEmpty()){
			if(this.isFrontierInternetAvailable){
				return false; 
			}
			else{
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
												logger.error("error_while_to_convert_connectionSpeed_from_String_to_Double=", e);    
										}              
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

	private boolean populateHughesNetProducts(){
		boolean populateHughesNetProducts = false;
		boolean isHNProductShow = this.isHNProductShow;
		if(!Utils.isBlank(hideHughesNet) && hideHughesNet.equalsIgnoreCase("true")){
				if(isHNProductShow){
					populateHughesNetProducts = true;
				}else{
					populateHughesNetProducts = false;
				}
		}else{
			populateHughesNetProducts = true;
		}
		return populateHughesNetProducts;
	}
	private ProductSummaryVO getProductWithProductBonusPoints(ProductSummaryVO product){
		try{
			if(this.isQualificationPopupEnabled!= null && this.isQualificationPopupEnabled.equalsIgnoreCase("true")){
				if(! product.isBonusPointsAdded()){
					SalesCenterVO salesCenterVo = context.getSalesCenterVO();
					String referrerId = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
					String providerName = product.getProviderName();
					String points = String.valueOf(product.getPoints());
					if (!Utils.isBlank(providerName) && providerName.equalsIgnoreCase("ATTSTI")){
						providerName = "AT&T";
					}else if (!Utils.isBlank(providerName) && providerName.equalsIgnoreCase("DISH Network")){
						providerName = "Dish";
					}
					Map<String, Map<String, QualificationPopUpRefDetails>> qualPopUpRefDetailsMap = new HashMap<String, Map<String,QualificationPopUpRefDetails>>();
					float productPoints = product.getPoints();
					if (CoxZipCodesDataCache.INSTANCE.get("qualificationPopUpRefDetails") != null){
						qualPopUpRefDetailsMap = (Map<String, Map<String, QualificationPopUpRefDetails>>)CoxZipCodesDataCache.INSTANCE.get("qualificationPopUpRefDetails");
					}
					else{
						qualPopUpRefDetailsMap = qualificationPopUpRefDetailsDao.getAllQualificationPopUpRefDetails();
						if(qualPopUpRefDetailsMap != null && !qualPopUpRefDetailsMap.isEmpty()){
							CoxZipCodesDataCache.INSTANCE.store("qualificationPopUpRefDetails",qualPopUpRefDetailsMap);
						}
					}
					
					if(qualPopUpRefDetailsMap != null && (qualPopUpRefDetailsMap.get(referrerId)!= null || qualPopUpRefDetailsMap.get("All")!= null)){
						Map<String, QualificationPopUpRefDetails> qualPopUpDetailsMap = null;
						QualificationPopUpRefDetails qualPopUpRefDetails = null;
						if(qualPopUpRefDetailsMap.get("All") != null){
							qualPopUpDetailsMap = qualPopUpRefDetailsMap.get("All");
							qualPopUpRefDetails = qualPopUpDetailsMap.get(providerName);
						}
						if(qualPopUpRefDetails == null && qualPopUpRefDetailsMap.get(referrerId)!= null){
							qualPopUpDetailsMap = qualPopUpRefDetailsMap.get(referrerId);
							qualPopUpRefDetails = qualPopUpDetailsMap.get(providerName);
						}
						if(qualPopUpRefDetails!= null && isBonusPointsZipCode(qualPopUpRefDetails.getZipCodesList())){
							if((product.getProductCategory()!= null && product.getProductCategory().equals(TRIPLE_PLAY)) || (product.getProductType()!= null && product.getProductType().equals(TRIPLE_PLAY))){
								if(points.equals("NA")|| points.equals("0.0")|| points.equals("-1.0") ){
									//productPoints = 40;
								}
								else{
									productPoints = productPoints+qualPopUpRefDetails.getTriplePlayPoints();
									product.setPoints(productPoints);
									product.setScore(Double.valueOf(productPoints));
								}
							}
							else if((product.getProductCategory()!= null && product.getProductCategory().equals(DOUBLE_PLAY)) || (product.getProductType()!= null && product.getProductType().equals(DOUBLE_PLAY))){
								if(points.equals("NA")|| points.equals("0.0")|| points.equals("-1.0") ){
									//productPoints = 30;
								}
								else{
									if(qualPopUpRefDetails.getDoublePlayPoints()!=0.0){
										productPoints = productPoints+qualPopUpRefDetails.getDoublePlayPoints();
									}
									else{
										String subCategoryName = getDoublePlayCategoryName(product);
										if(!Utils.isBlank(subCategoryName) && subCategoryName.equalsIgnoreCase("DOUBLE_PLAY_VI")){
											productPoints = productPoints+qualPopUpRefDetails.getDoublePlayVIPoints();
										}
										else if(!Utils.isBlank(subCategoryName) && subCategoryName.equalsIgnoreCase("DOUBLE_PLAY_PV")){
											productPoints = productPoints+qualPopUpRefDetails.getDoublePlayPVPoints();
										}
										else if(!Utils.isBlank(subCategoryName) && subCategoryName.equalsIgnoreCase("DOUBLE_PLAY_PI")){
											productPoints = productPoints+qualPopUpRefDetails.getDoublePlayPIPoints();
										}
									}
									product.setPoints(productPoints);
									product.setScore(Double.valueOf(productPoints));
								}
							}
							else{
								if(points.equals("NA")|| points.equals("0.0")|| points.equals("-1.0") ){
									//productPoints = 8;
								}
								else{
									String categoryName = getSinglePlayCategoryName(product);
									if(qualPopUpRefDetails.getSinglePlayPoints()!=0.0){
										productPoints = productPoints+qualPopUpRefDetails.getSinglePlayPoints();
									}
									else if((product.getProductType()!= null && product.getProductType().equals(PHONE))||(!Utils.isBlank(categoryName)&& categoryName.equalsIgnoreCase("PHONE"))){
										productPoints = productPoints+qualPopUpRefDetails.getPhonePoints();
									}
									else if((product.getProductType()!= null && product.getProductType().equals(VIDEO))||(!Utils.isBlank(categoryName)&& categoryName.equalsIgnoreCase("VIDEO"))){
										productPoints = productPoints+qualPopUpRefDetails.getVideoPoints();
									}
									else if((product.getProductType()!= null && product.getProductType().equals(INTERNET))||(!Utils.isBlank(categoryName)&& categoryName.equalsIgnoreCase("INTERNET"))){
										productPoints = productPoints+qualPopUpRefDetails.getInternetPoints();
									}
									product.setPoints(productPoints);
									product.setScore(Double.valueOf(productPoints));
								}
							}
						}
					}
					product.setBonusPointsAdded(true);
				}
			}
		}
		catch(Exception e){
			logger.warn("Error updating product bonus points"+e.getMessage());
			return product;
		}
		return product;
	}

	private boolean isBonusPointsZipCode(List<String> zipCodesList){
		boolean isBonusPointsZipCode = false; 
		try{
			if(zipCodesList != null ){
				SalesCenterVO salesCenterVo = context.getSalesCenterVO();
				String zipCode = null;
				if (salesCenterVo.getValueByName("correctedZipCode") != null){
					zipCode = salesCenterVo.getValueByName("correctedZipCode");
					if(zipCode.length()>5){
						zipCode = zipCode.substring(0, 5);
					}
				}
				if (zipCode != null && zipCodesList != null && zipCodesList.contains(zipCode)){
					isBonusPointsZipCode = true;
				}
			}
		}
		catch(Exception e){
			logger.warn("Error updating product bonus points"+e.getMessage());
			return isBonusPointsZipCode;
		}
		return isBonusPointsZipCode;
	}
	
	public String getDoublePlayCategoryName(ProductSummaryVO product){
		String categoryName = "";
		if((product.getCapabilityMap().get("iptv") != null || // Video Conditions
				product.getCapabilityMap().get("ipDslamIptv") != null ||
				product.getCapabilityMap().get("analogCable") != null ||
				product.getCapabilityMap().get("digitalCable") != null ||
				product.getCapabilityMap().get("satellite") != null) 
				&& (product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
				product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
				product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
				product.getCapabilityMap().get("dialUpInternet") != null))
		{
			categoryName = "DOUBLE_PLAY_VI";
		}
		else if((product.getCapabilityMap().get("iptv") != null || // Video Condtions
				product.getCapabilityMap().get("ipDslamIptv") != null ||
				product.getCapabilityMap().get("analogCable") != null ||
				product.getCapabilityMap().get("digitalCable") != null ||
				product.getCapabilityMap().get("satellite") != null) 
				&& (product.getCapabilityMap().get("voip") != null || //phone conditions
				product.getCapabilityMap().get("ipDslamVoip") != null ||
				product.getCapabilityMap().get("localPhone") != null ||
				product.getCapabilityMap().get("longDistancePhone") != null ||
				product.getCapabilityMap().get("wirelessPhone") != null)) 
		{
			categoryName = "DOUBLE_PLAY_PV";
		}
		else if((product.getCapabilityMap().get("voip") != null || //phone conditions
				product.getCapabilityMap().get("ipDslamVoip") != null ||
				product.getCapabilityMap().get("localPhone") != null ||
				product.getCapabilityMap().get("longDistancePhone") != null ||
				product.getCapabilityMap().get("wirelessPhone") != null) 
				&& (product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
				product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
				product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
				product.getCapabilityMap().get("dialUpInternet") != null))
		{
			categoryName = "DOUBLE_PLAY_PI";
		}
		return categoryName;
	}
	
	public String getSinglePlayCategoryName(ProductSummaryVO product){
		String categoryName = "";
		if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || 
				product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
				product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
				product.getCapabilityMap().get("dialUpInternet") != null)){
			categoryName = "INTERNET";
		}
		else if((product.getCapabilityMap().get("iptv") != null ||
				product.getCapabilityMap().get("ipDslamIptv") != null ||
				product.getCapabilityMap().get("analogCable") != null ||
				product.getCapabilityMap().get("digitalCable") != null ||
				product.getCapabilityMap().get("satellite") != null)) {
			categoryName = "VIDEO";
		}
		else if((product.getCapabilityMap().get("voip") != null || 
				product.getCapabilityMap().get("ipDslamVoip") != null ||
				product.getCapabilityMap().get("localPhone") != null ||
				product.getCapabilityMap().get("longDistancePhone") != null ||
				product.getCapabilityMap().get("wirelessPhone") != null)) {
			categoryName = "PHONE";
		}
		return categoryName;
	}
	
	
	public List<RoadMapCriteriaVO> populateSDURoadMapPowerPitchData() {
		roadMapPowerPitchList = new ArrayList<RoadMapCriteriaVO>();
		SalesCenterVO salesCenterVo = context.getSalesCenterVO();
		String state = salesCenterVo.getValueByName("correctedState");
		ProductSummaryVO[] powerPitchArray = new ProductSummaryVO[10];
		ProductSummaryVO dishRTISatelliteProduct = null;
		ProductSummaryVO dishInternalSatelliteProduct = null;
		ProductSummaryVO attV6Product = null;
		ProductSummaryVO vzProduct = null;
		ProductSummaryVO clProduct = null;
		ProductSummaryVO comcastProduct = null;
		ProductSummaryVO attV6InternetProduct = null;
		ProductSummaryVO directvProduct = null;

		List<ProductSummaryVO> allProductList = this.context.getAllProductList();
		Map<String, ProductSummaryVO> triplePlayProductsMap = new HashMap<String, ProductSummaryVO>();
		Map<String, ProductSummaryVO> internetPhoneProductsMap = new HashMap<String, ProductSummaryVO>();
		boolean populateHughesnetProducts = populateHughesNetProducts();
		Map<String,Double> maxSpeedMap = new HashMap<String,Double>();
		for (ProductSummaryVO product:allProductList){
			if((product.getCapabilityMap() != null)) {
				if(!populateHughesnetProducts && product.getProviderExternalId() != null 
						&& (product.getProviderExternalId().equalsIgnoreCase(Constants.HUGHESNET) || product.getProviderExternalId().equalsIgnoreCase("15500581"))){
					continue;
				}
				if(product.getProviderExternalId() != null && (product.getProviderExternalId().equals("27010360")
						|| product.getProviderExternalId().equals("18063259"))
						&& product.getCapabilityMap().get("satellite") != null ) {
					if(product.getProviderExternalId().equals("27010360")){
						if (dishRTISatelliteProduct == null) {
							dishRTISatelliteProduct = product;
						} else { 
							//compare them for better score
							if(dishRTISatelliteProduct.getScore()< product.getScore()) {
								dishRTISatelliteProduct = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
					} else {
						if (dishInternalSatelliteProduct == null) {
							dishInternalSatelliteProduct = product;
						} else { 
							//compare them for better score
							if(dishInternalSatelliteProduct.getScore()< product.getScore()) {
								dishInternalSatelliteProduct = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
						//dishInternalSatellite.setDishProduct(true);						
					}
				}
				else if(product.getProviderExternalId().equals(Constants.ATTV6) && !Utils.isBlank(state) && state.equalsIgnoreCase("MI")){
					if (attV6Product == null) {
						attV6Product = product;
					} else { 
						//compare them for better score
						if(attV6Product.getScore()< product.getScore()) {
							attV6Product = product;
						} else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
				else if(product.getProviderExternalId().equals(Constants.VERIZON2) && !Utils.isBlank(state) && state.equalsIgnoreCase("VA")){
					if (vzProduct == null) {
						vzProduct = product;
					} else { 
						//compare them for better score
						if(vzProduct.getScore()< product.getScore()) {
							vzProduct = product;
						} else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
				else if(product.getProviderExternalId().equals(Constants.CENTURY_LINK)){
					if (clProduct == null) {
						clProduct = product;
					} else { 
						//compare them for better score
						if(clProduct.getScore()< product.getScore()) {
							clProduct = product;
						} else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
				if((product.getCapabilityMap().get("iptv") != null || // Video Conditions
						product.getCapabilityMap().get("ipDslamIptv") != null ||
						product.getCapabilityMap().get("analogCable") != null ||
						product.getCapabilityMap().get("digitalCable") != null ||
						product.getCapabilityMap().get("satellite") != null) 
						&& (product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null)
						&& (product.getCapabilityMap().get("voip") == null &&  
						product.getCapabilityMap().get("ipDslamVoip") == null && 
						product.getCapabilityMap().get("localPhone") == null && 
						product.getCapabilityMap().get("longDistancePhone") == null && 
						product.getCapabilityMap().get("wirelessPhone") == null)){

					if(product.getProviderExternalId().equals(Constants.COMCAST)){
						if (comcastProduct == null) {
							comcastProduct = product;
						} else { 
							//compare them for better score
							if(comcastProduct.getScore()< product.getScore()) {
								comcastProduct = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
						getMaxSpeedMap(maxSpeedMap,product);
					}
				}
				if((product.getCapabilityMap().get("fiberDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null ||
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null)
						&& (product.getCapabilityMap().get("iptv") == null && 
						product.getCapabilityMap().get("ipDslamIptv") == null && 
						product.getCapabilityMap().get("analogCable") == null && 
						product.getCapabilityMap().get("digitalCable") == null && 
						product.getCapabilityMap().get("satellite") == null)
						&& (product.getCapabilityMap().get("voip") == null &&  
						product.getCapabilityMap().get("ipDslamVoip") == null && 
						product.getCapabilityMap().get("localPhone") == null && 
						product.getCapabilityMap().get("longDistancePhone") == null && 
						product.getCapabilityMap().get("wirelessPhone") == null)) {
					//this product is a fiber internet 
					//compare the score of already found fiber internet product and retain the higher score product
					//in powerpitch data structure
					if (telcoProvidersList != null && (telcoProvidersList.contains(product.getParentExternalId()) 
							|| telcoProvidersList.contains(product.getProviderExternalId()))){
						if (powerPitchArray[2] == null) {
							powerPitchArray[2] = product;
						} else { 
							//compare them for better score
							if(powerPitchArray[2].getScore()< product.getScore()) {
								powerPitchArray[2] = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}	
						getMaxSpeedMap(maxSpeedMap,product);
					}
				}
				else if((product.getCapabilityMap().get("fiberDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null ||
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null) &&
						(product.getCapabilityMap().get("iptv") != null ||
						product.getCapabilityMap().get("ipDslamIptv") != null||
						product.getCapabilityMap().get("analogCable") != null||
						product.getCapabilityMap().get("digitalCable") != null)
						&& (product.getCapabilityMap().get("voip") == null &&  
						product.getCapabilityMap().get("ipDslamVoip") == null && 
						product.getCapabilityMap().get("localPhone") == null && 
						product.getCapabilityMap().get("longDistancePhone") == null && 
						product.getCapabilityMap().get("wirelessPhone") == null)) {
					//this product is a fiber double play 
					//compare the score of already found fiber double play product and retain the higher score product
					//in powerpitch data structure
					if (telcoProvidersList != null && (telcoProvidersList.contains(product.getParentExternalId()) 
							|| telcoProvidersList.contains(product.getProviderExternalId()))){
						if (powerPitchArray[3] == null) {
							powerPitchArray[3] = product;
						} else { 
							//compare them for better score
							if(powerPitchArray[3].getScore()< product.getScore()) {
								powerPitchArray[3] = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}	
						getMaxSpeedMap(maxSpeedMap,product);
					}
				}
				if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null) && 						
						(product.getCapabilityMap().get("iptv") != null ||
						product.getCapabilityMap().get("ipDslamIptv") != null ||
						product.getCapabilityMap().get("analogCable") != null ||
						product.getCapabilityMap().get("digitalCable") != null ||
						product.getCapabilityMap().get("satellite") != null)
						&& (product.getCapabilityMap().get("voip") == null &&  
						product.getCapabilityMap().get("ipDslamVoip") == null && 
						product.getCapabilityMap().get("localPhone") == null && 
						product.getCapabilityMap().get("longDistancePhone") == null && 
						product.getCapabilityMap().get("wirelessPhone") == null)){
					//Cable double play
					if (cableProvidersList != null && (cableProvidersList.contains(product.getParentExternalId()) 
							|| cableProvidersList.contains(product.getProviderExternalId()))){
						if (powerPitchArray[4] == null) {
							powerPitchArray[4] = product;
						} else { 
							//compare them for better score
							if(powerPitchArray[4].getScore()< product.getScore()) {
								powerPitchArray[4] = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
						getMaxSpeedMap(maxSpeedMap,product);
					}
				}
				if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null)
						&& (product.getCapabilityMap().get("iptv") == null && 
						product.getCapabilityMap().get("ipDslamIptv") == null && 
						product.getCapabilityMap().get("analogCable") == null && 
						product.getCapabilityMap().get("digitalCable") == null && 
						product.getCapabilityMap().get("satellite") == null)
						&& (product.getCapabilityMap().get("voip") == null &&  
						product.getCapabilityMap().get("ipDslamVoip") == null && 
						product.getCapabilityMap().get("localPhone") == null && 
						product.getCapabilityMap().get("longDistancePhone") == null && 
						product.getCapabilityMap().get("wirelessPhone") == null)
						&& isInternetSpeedGreaterThan3MB(product)){

						if (powerPitchArray[5] == null) {
							powerPitchArray[5] = product;
						} else { 
							//compare them for better score
							if(powerPitchArray[5].getConnectionSpeedVal()< product.getConnectionSpeedVal()) {
								powerPitchArray[5] = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
						getMaxSpeedMap(maxSpeedMap,product);
				}
				if (product.getProviderExternalId()!= null && (product.getProviderExternalId().equalsIgnoreCase(Constants.HUGHESNET) || product.getProviderExternalId().equalsIgnoreCase("15500581"))){
					if (powerPitchArray[6] == null) {
						powerPitchArray[6] = product;
					} 
					else { 
						//compare them for better score
						if(powerPitchArray[6].getScore()< product.getScore()) {
							powerPitchArray[6] = product;
						} 
						else {
							//the earlier product itself should be retained in this slot
						}
					}
					getMaxSpeedMap(maxSpeedMap,product);
				}
				
				if (product.getProviderExternalId()!= null && product.getProviderExternalId().equalsIgnoreCase(Constants.ATTV6)){
					if((product.getCapabilityMap().get("fiberDataDownSpeed") != null ||
							product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
							product.getCapabilityMap().get("dialUpInternet") != null ||
							product.getCapabilityMap().get("ipDslamDataDownSpeed") != null)
							&& (product.getCapabilityMap().get("iptv") == null && 
							product.getCapabilityMap().get("ipDslamIptv") == null && 
							product.getCapabilityMap().get("analogCable") == null && 
							product.getCapabilityMap().get("digitalCable") == null && 
							product.getCapabilityMap().get("satellite") == null)
							&& (product.getCapabilityMap().get("voip") == null &&  
							product.getCapabilityMap().get("ipDslamVoip") == null && 
							product.getCapabilityMap().get("localPhone") == null && 
							product.getCapabilityMap().get("longDistancePhone") == null && 
							product.getCapabilityMap().get("wirelessPhone") == null)) {
						if (attV6InternetProduct == null) {
							attV6InternetProduct = product;
						} else { 
							//compare them for better score
							if(attV6InternetProduct.getScore()< product.getScore()) {
								attV6InternetProduct = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
						getMaxSpeedMap(maxSpeedMap,product);
					}
					else if((product.getCapabilityMap().get("fiberDataDownSpeed") == null && //internet conditions
							product.getCapabilityMap().get("ipDslamDataDownSpeed") == null &&
							product.getCapabilityMap().get("wiredDataDownSpeed") == null &&
							product.getCapabilityMap().get("dialUpInternet") == null) && 
							(product.getCapabilityMap().get("voip") == null || //phone conditions
									product.getCapabilityMap().get("ipDslamVoip") == null ||
									product.getCapabilityMap().get("localPhone") == null ||
									product.getCapabilityMap().get("longDistancePhone") == null ||
									product.getCapabilityMap().get("wirelessPhone") == null) &&
									(product.getCapabilityMap().get("satellite") != null)) {
						if (directvProduct == null) {
							directvProduct = product;
						} else { 
							//compare them for better score
							if(directvProduct.getScore()< product.getScore()) {
								directvProduct = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
					}
				}
				
				if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null) && 
						(product.getCapabilityMap().get("voip") != null || //phone conditions
								product.getCapabilityMap().get("ipDslamVoip") != null ||
								product.getCapabilityMap().get("localPhone") != null ||
								product.getCapabilityMap().get("longDistancePhone") != null ||
								product.getCapabilityMap().get("wirelessPhone") != null) &&
								(product.getCapabilityMap().get("iptv") != null ||
										product.getCapabilityMap().get("ipDslamIptv") != null ||
										product.getCapabilityMap().get("analogCable") != null ||
										product.getCapabilityMap().get("digitalCable") != null ||
										product.getCapabilityMap().get("satellite") != null)) {
					//this product is a triple play 				
					if (triplePlayProductsMap != null && product.getProviderExternalId()!= null && triplePlayProductsMap.get(product.getProviderExternalId()) == null){
						triplePlayProductsMap.put(product.getProviderExternalId(),product);	
					}else{
						//compare them for better score
						if(triplePlayProductsMap.get(product.getProviderExternalId()).getScore()< product.getScore()) {
							triplePlayProductsMap.put(product.getProviderExternalId(),product);
						} 
						else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
				
				if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null) && 
						(product.getCapabilityMap().get("voip") != null || //phone conditions
								product.getCapabilityMap().get("ipDslamVoip") != null ||
								product.getCapabilityMap().get("localPhone") != null ||
								product.getCapabilityMap().get("longDistancePhone") != null ||
								product.getCapabilityMap().get("wirelessPhone") != null) &&
								(product.getCapabilityMap().get("iptv") == null &&
										product.getCapabilityMap().get("ipDslamIptv") == null &&
										product.getCapabilityMap().get("analogCable") == null &&
										product.getCapabilityMap().get("digitalCable") == null &&
										product.getCapabilityMap().get("satellite") == null)) {
					//this product is a phone and internet product 				
					if (internetPhoneProductsMap != null && product.getProviderExternalId()!= null && internetPhoneProductsMap.get(product.getProviderExternalId()) == null){
						internetPhoneProductsMap.put(product.getProviderExternalId(),product);	
					}else{
						//compare them for better score
						if(internetPhoneProductsMap.get(product.getProviderExternalId()).getScore()< product.getScore()) {
							internetPhoneProductsMap.put(product.getProviderExternalId(),product);
						} 
						else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
			}
		}



		if(comcastProduct != null){
			if(attV6Product!= null || vzProduct!= null || clProduct != null){
				powerPitchArray[0] = comcastProduct;
			}

			if(powerPitchArray[4]!= null && powerPitchArray[0]!= null && powerPitchArray[0].getProviderExternalId()!= null && powerPitchArray[4].getProviderExternalId()!= null && 
					powerPitchArray[0].getProviderExternalId().equals(powerPitchArray[4].getProviderExternalId())){
				powerPitchArray[4] = null;
			}
		}
		
		if(attV6InternetProduct!= null && directvProduct!= null){
			powerPitchArray[1] = attV6InternetProduct;
		}
		if(powerPitchArray[1]!=null){
			powerPitchArray[2] = null;
		}

		if(powerPitchArray[2]!= null && powerPitchArray[1]!=null && powerPitchArray[1].getProviderExternalId()!= null 
				&& powerPitchArray[2].getProviderExternalId()!= null && powerPitchArray[1].getProviderExternalId().equals(powerPitchArray[2].getProviderExternalId())) {
			powerPitchArray[2] = null;
		}
		else if(dishRTISatelliteProduct==null && dishInternalSatelliteProduct==null && powerPitchArray[2]!= null){
			powerPitchArray[2] = null;
		}
		
		if(powerPitchArray[5]!= null && powerPitchArray[6]!= null && powerPitchArray[5].getProviderExternalId()!= null && powerPitchArray[6].getProviderExternalId()!= null && 
				powerPitchArray[5].getProviderExternalId().equals(powerPitchArray[6].getProviderExternalId())){
			powerPitchArray[6] = null;
		}

		int j = 0;
		for (int i=0; i<10; i++) {
			logger.info("The raw power pitch product is:" + i + "   powerPitchArray[i]    " +powerPitchArray[i]);
			if(j != 3) {
				if(powerPitchArray[i] != null) {
					j++;
					float totalWithOutPhonePoints = 0.00f;
					float dishPoints = 0.00f;
					float directvPoints = 0.00f;
					RoadMapCriteriaVO rmCriteriaVO = new RoadMapCriteriaVO();
					if (telcoProvidersList != null && (telcoProvidersList.contains(powerPitchArray[i].getParentExternalId()) 
							|| telcoProvidersList.contains(powerPitchArray[i].getProviderExternalId()))){
						rmCriteriaVO.setInternetProviderName(powerPitchArray[i].getProviderName()+ " Fiber");
					}
					else{
						rmCriteriaVO.setInternetProviderName(powerPitchArray[i].getProviderName());
					}
					if(!Float.isNaN(powerPitchArray[i].getPoints())  && powerPitchArray[i].getPoints() >= 0.0){
						totalWithOutPhonePoints = powerPitchArray[i].getPoints();
					}
					if(i!=5 && i!=6){
						if(i==1){
							rmCriteriaVO.setVideoProviderName("ATT DTV");
							if(!Float.isNaN(directvProduct.getPoints())  && directvProduct.getPoints() >= 0.0){
								totalWithOutPhonePoints = totalWithOutPhonePoints + directvProduct.getPoints();
								directvPoints = directvProduct.getPoints();
							}
						}
						else if(i==2){
							if(dishRTISatelliteProduct!= null){
								rmCriteriaVO.setVideoProviderName(dishRTISatelliteProduct.getProviderName());
								if(!Float.isNaN(dishRTISatelliteProduct.getPoints())  && dishRTISatelliteProduct.getPoints() >= 0.0){
									dishPoints = dishRTISatelliteProduct.getPoints();
									totalWithOutPhonePoints = totalWithOutPhonePoints + dishRTISatelliteProduct.getPoints();
								}
							}
							else if(dishInternalSatelliteProduct!= null){
								rmCriteriaVO.setVideoProviderName(dishInternalSatelliteProduct.getProviderName());
								if(!Float.isNaN(dishInternalSatelliteProduct.getPoints())  && dishInternalSatelliteProduct.getPoints() >= 0.0){
									totalWithOutPhonePoints = totalWithOutPhonePoints + dishInternalSatelliteProduct.getPoints();
									dishPoints = dishInternalSatelliteProduct.getPoints();
								}
							}
						}
						else{
							if(powerPitchArray[i].getProviderName()!= null && powerPitchArray[i].getProviderExternalId().equals(Constants.ATTV6)){
								rmCriteriaVO.setVideoProviderName("ATTV6 Uverse");
							}
							else{
								rmCriteriaVO.setVideoProviderName(powerPitchArray[i].getProviderName());
							}
						}
					}
					rmCriteriaVO.setWithoutPhonePoints(totalWithOutPhonePoints);
					if(i==0 || i==3 || i==4){
						if(triplePlayProductsMap!= null && powerPitchArray[i].getProviderExternalId()!= null && triplePlayProductsMap.get(powerPitchArray[i].getProviderExternalId())!= null){
							rmCriteriaVO.setWithPhonePoints(triplePlayProductsMap.get(powerPitchArray[i].getProviderExternalId()).getPoints());
						}
					}
					else if(internetPhoneProductsMap!= null && powerPitchArray[i].getProviderExternalId()!= null && internetPhoneProductsMap.get(powerPitchArray[i].getProviderExternalId())!= null){
						if(i==1){
							if(!Float.isNaN(internetPhoneProductsMap.get(powerPitchArray[i].getProviderExternalId()).getPoints())  && internetPhoneProductsMap.get(powerPitchArray[i].getProviderExternalId()).getPoints() >= 0.0){
								rmCriteriaVO.setWithPhonePoints(internetPhoneProductsMap.get(powerPitchArray[i].getProviderExternalId()).getPoints()+directvPoints);
							}
						}
						else if(i==5 || i==6){
							rmCriteriaVO.setWithPhonePoints(internetPhoneProductsMap.get(powerPitchArray[i].getProviderExternalId()).getPoints());
						}
						else if(!Float.isNaN(internetPhoneProductsMap.get(powerPitchArray[i].getProviderExternalId()).getPoints())  && internetPhoneProductsMap.get(powerPitchArray[i].getProviderExternalId()).getPoints() >= 0.0){
							rmCriteriaVO.setWithPhonePoints(internetPhoneProductsMap.get(powerPitchArray[i].getProviderExternalId()).getPoints()+dishPoints);
						}
					}
					if(maxSpeedMap != null && maxSpeedMap.get(powerPitchArray[i].getProviderExternalId())!= null){
						rmCriteriaVO.setConnectionSpeedVal(maxSpeedMap.get(powerPitchArray[i].getProviderExternalId())); 
					}
					roadMapPowerPitchList.add(rmCriteriaVO);
				} 
			}			
		}
		logger.info("roadMapPowerPitchList_5652="+roadMapPowerPitchList);
		return roadMapPowerPitchList;
	}

	private void getMaxSpeedMap(Map<String, Double> maxSpeedMap, ProductSummaryVO productVO) {
		try{ 
			double connectionSpeedVal = getInternetSpeed(productVO);
			if(maxSpeedMap != null && maxSpeedMap.get(productVO.getProviderExternalId()) == null){
				maxSpeedMap.put(productVO.getProviderExternalId(), connectionSpeedVal);
			}else{
				if(maxSpeedMap != null && maxSpeedMap.get(productVO.getProviderExternalId()) < connectionSpeedVal){
					maxSpeedMap.put(productVO.getProviderExternalId(), connectionSpeedVal);
				}
			}
		}catch(Exception e){ 
			logger.warn("error_while_getting_InternetSpeed="+ e.getMessage());    
		} 
		
	}
	
	
	public List<RoadMapCriteriaVO> populateMDURoadMapPowerPitchData() {
		roadMapPowerPitchList = new ArrayList<RoadMapCriteriaVO>();
		SalesCenterVO salesCenterVo = context.getSalesCenterVO();
		String state = salesCenterVo.getValueByName("correctedState");
		ProductSummaryVO[] powerPitchArray = new ProductSummaryVO[10];
		ProductSummaryVO attV6Product = null;
		ProductSummaryVO vzProduct = null;
		ProductSummaryVO clProduct = null;
		ProductSummaryVO comcastProduct = null;

		List<ProductSummaryVO> allProductList = this.context.getAllProductList();
		Map<String, ProductSummaryVO> triplePlayProductsMap = new HashMap<String, ProductSummaryVO>();
		Map<String, ProductSummaryVO> internetPhoneProductsMap = new HashMap<String, ProductSummaryVO>();
		boolean populateHughesnetProducts = populateHughesNetProducts();
		Map<String,Double> maxSpeedMap = new HashMap<String,Double>();
		for (ProductSummaryVO product:allProductList){
			if((product.getCapabilityMap() != null)) {
				if(!populateHughesnetProducts && product.getProviderExternalId() != null 
						&& (product.getProviderExternalId().equalsIgnoreCase(Constants.HUGHESNET) || product.getProviderExternalId().equalsIgnoreCase("15500581"))){
					continue;
				}
				if(product.getProviderExternalId().equals(Constants.ATTV6) && !Utils.isBlank(state) && state.equalsIgnoreCase("MI")){
					if (attV6Product == null) {
						attV6Product = product;
					} else { 
						//compare them for better score
						if(attV6Product.getScore()< product.getScore()) {
							attV6Product = product;
						} else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
				else if(product.getProviderExternalId().equals(Constants.VERIZON2) && !Utils.isBlank(state) && state.equalsIgnoreCase("VA")){
					if (vzProduct == null) {
						vzProduct = product;
					} else { 
						//compare them for better score
						if(vzProduct.getScore()< product.getScore()) {
							vzProduct = product;
						} else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
				else if(product.getProviderExternalId().equals(Constants.CENTURY_LINK)){
					if (clProduct == null) {
						clProduct = product;
					} else { 
						//compare them for better score
						if(clProduct.getScore()< product.getScore()) {
							clProduct = product;
						} else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
				if((product.getCapabilityMap().get("iptv") != null || // Video Conditions
						product.getCapabilityMap().get("ipDslamIptv") != null ||
						product.getCapabilityMap().get("analogCable") != null ||
						product.getCapabilityMap().get("digitalCable") != null ||
						product.getCapabilityMap().get("satellite") != null) 
						&& (product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null)
						&& (product.getCapabilityMap().get("voip") == null &&  
						product.getCapabilityMap().get("ipDslamVoip") == null && 
						product.getCapabilityMap().get("localPhone") == null && 
						product.getCapabilityMap().get("longDistancePhone") == null && 
						product.getCapabilityMap().get("wirelessPhone") == null)){

					if(product.getProviderExternalId().equals(Constants.COMCAST)){
						if (comcastProduct == null) {
							comcastProduct = product;
						} else { 
							//compare them for better score
							if(comcastProduct.getScore()< product.getScore()) {
								comcastProduct = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
						getMaxSpeedMap(maxSpeedMap,product);
					}
				}
				if((product.getCapabilityMap().get("fiberDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null ||
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null) &&
						(product.getCapabilityMap().get("iptv") != null ||
						product.getCapabilityMap().get("ipDslamIptv") != null||
						product.getCapabilityMap().get("analogCable") != null||
						product.getCapabilityMap().get("digitalCable") != null)
						&& (product.getCapabilityMap().get("voip") == null &&  
						product.getCapabilityMap().get("ipDslamVoip") == null && 
						product.getCapabilityMap().get("localPhone") == null && 
						product.getCapabilityMap().get("longDistancePhone") == null && 
						product.getCapabilityMap().get("wirelessPhone") == null)) {
					//this product is a fiber double play 
					//compare the score of already found fiber double play product and retain the higher score product
					//in powerpitch data structure
					if (telcoProvidersList != null && (telcoProvidersList.contains(product.getParentExternalId()) 
							|| telcoProvidersList.contains(product.getProviderExternalId()))){
						if (powerPitchArray[1] == null) {
							powerPitchArray[1] = product;
						} else { 
							//compare them for better score
							if(powerPitchArray[1].getScore()< product.getScore()) {
								powerPitchArray[1] = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}	
						getMaxSpeedMap(maxSpeedMap,product);
					}
				}
				if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null) && 						
						(product.getCapabilityMap().get("iptv") != null ||
						product.getCapabilityMap().get("ipDslamIptv") != null ||
						product.getCapabilityMap().get("analogCable") != null ||
						product.getCapabilityMap().get("digitalCable") != null ||
						product.getCapabilityMap().get("satellite") != null)
						&& (product.getCapabilityMap().get("voip") == null &&  
						product.getCapabilityMap().get("ipDslamVoip") == null && 
						product.getCapabilityMap().get("localPhone") == null && 
						product.getCapabilityMap().get("longDistancePhone") == null && 
						product.getCapabilityMap().get("wirelessPhone") == null)){
					//Cable double play
					if (cableProvidersList != null && (cableProvidersList.contains(product.getParentExternalId()) 
							|| cableProvidersList.contains(product.getProviderExternalId()))){
						if (powerPitchArray[2] == null) {
							powerPitchArray[2] = product;
						} else { 
							//compare them for better score
							if(powerPitchArray[2].getScore()< product.getScore()) {
								powerPitchArray[2] = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
						getMaxSpeedMap(maxSpeedMap,product);
					}
				}
				if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null)
						&& (product.getCapabilityMap().get("iptv") == null && 
						product.getCapabilityMap().get("ipDslamIptv") == null && 
						product.getCapabilityMap().get("analogCable") == null && 
						product.getCapabilityMap().get("digitalCable") == null && 
						product.getCapabilityMap().get("satellite") == null)
						&& (product.getCapabilityMap().get("voip") == null &&  
						product.getCapabilityMap().get("ipDslamVoip") == null && 
						product.getCapabilityMap().get("localPhone") == null && 
						product.getCapabilityMap().get("longDistancePhone") == null && 
						product.getCapabilityMap().get("wirelessPhone") == null)
						&& isInternetSpeedGreaterThan3MB(product)){

						if (powerPitchArray[3] == null) {
							powerPitchArray[3] = product;
						} else { 
							//compare them for better score
							if(powerPitchArray[3].getConnectionSpeedVal()< product.getConnectionSpeedVal()) {
								powerPitchArray[3] = product;
							} else {
								//the earlier product itself should be retained in this slot
							}
						}
						getMaxSpeedMap(maxSpeedMap,product);
				}
				if (product.getProviderExternalId()!= null && (product.getProviderExternalId().equalsIgnoreCase(Constants.HUGHESNET) || product.getProviderExternalId().equalsIgnoreCase("15500581"))){
					if (powerPitchArray[4] == null) {
						powerPitchArray[4] = product;
					} 
					else { 
						//compare them for better score
						if(powerPitchArray[4].getScore()< product.getScore()) {
							powerPitchArray[4] = product;
						} 
						else {
							//the earlier product itself should be retained in this slot
						}
					}
					getMaxSpeedMap(maxSpeedMap,product);
				}
				
				if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null) && 
						(product.getCapabilityMap().get("voip") != null || //phone conditions
								product.getCapabilityMap().get("ipDslamVoip") != null ||
								product.getCapabilityMap().get("localPhone") != null ||
								product.getCapabilityMap().get("longDistancePhone") != null ||
								product.getCapabilityMap().get("wirelessPhone") != null) &&
								(product.getCapabilityMap().get("iptv") != null ||
										product.getCapabilityMap().get("ipDslamIptv") != null ||
										product.getCapabilityMap().get("analogCable") != null ||
										product.getCapabilityMap().get("digitalCable") != null ||
										product.getCapabilityMap().get("satellite") != null)) {
					//this product is a triple play 				
					if (triplePlayProductsMap != null && product.getProviderExternalId()!= null && triplePlayProductsMap.get(product.getProviderExternalId()) == null){
						triplePlayProductsMap.put(product.getProviderExternalId(),product);	
					}else{
						//compare them for better score
						if(triplePlayProductsMap.get(product.getProviderExternalId()).getScore()< product.getScore()) {
							triplePlayProductsMap.put(product.getProviderExternalId(),product);
						} 
						else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
				
				if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
						product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
						product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
						product.getCapabilityMap().get("dialUpInternet") != null) && 
						(product.getCapabilityMap().get("voip") != null || //phone conditions
								product.getCapabilityMap().get("ipDslamVoip") != null ||
								product.getCapabilityMap().get("localPhone") != null ||
								product.getCapabilityMap().get("longDistancePhone") != null ||
								product.getCapabilityMap().get("wirelessPhone") != null) &&
								(product.getCapabilityMap().get("iptv") == null &&
										product.getCapabilityMap().get("ipDslamIptv") == null &&
										product.getCapabilityMap().get("analogCable") == null &&
										product.getCapabilityMap().get("digitalCable") == null &&
										product.getCapabilityMap().get("satellite") == null)) {
					//this product is a phone and internet product 				
					if (internetPhoneProductsMap != null && product.getProviderExternalId()!= null && internetPhoneProductsMap.get(product.getProviderExternalId()) == null){
						internetPhoneProductsMap.put(product.getProviderExternalId(),product);	
					}else{
						//compare them for better score
						if(internetPhoneProductsMap.get(product.getProviderExternalId()).getScore()< product.getScore()) {
							internetPhoneProductsMap.put(product.getProviderExternalId(),product);
						} 
						else {
							//the earlier product itself should be retained in this slot
						}
					}
				}
			}
		}



		if(comcastProduct != null){
			if(attV6Product!= null || vzProduct!= null || clProduct != null){
				powerPitchArray[0] = comcastProduct;
			}

			if(powerPitchArray[2]!= null && powerPitchArray[0]!= null && powerPitchArray[0].getProviderExternalId()!= null && powerPitchArray[2].getProviderExternalId()!= null && 
					powerPitchArray[0].getProviderExternalId().equals(powerPitchArray[2].getProviderExternalId())){
				powerPitchArray[2] = null;
			}
		}

		if(powerPitchArray[3]!= null && powerPitchArray[4]!= null && powerPitchArray[3].getProviderExternalId()!= null && powerPitchArray[4].getProviderExternalId()!= null && 
				powerPitchArray[3].getProviderExternalId().equals(powerPitchArray[4].getProviderExternalId())){
			powerPitchArray[4] = null;
		}
		
		int j = 0;
		for (int i=0; i<10; i++) {
			logger.info("The raw power pitch product is:" + i + "   powerPitchArray[i]    " +powerPitchArray[i]);
			if(j != 3) {
				if(powerPitchArray[i] != null) {
					j++;
					RoadMapCriteriaVO rmCriteriaVO = new RoadMapCriteriaVO();
					if (telcoProvidersList != null && (telcoProvidersList.contains(powerPitchArray[i].getParentExternalId()) 
							|| telcoProvidersList.contains(powerPitchArray[i].getProviderExternalId()))){
						rmCriteriaVO.setInternetProviderName(powerPitchArray[i].getProviderName()+ " Fiber");
					}
					else{
						rmCriteriaVO.setInternetProviderName(powerPitchArray[i].getProviderName());
					}
					
					if(!Float.isNaN(powerPitchArray[i].getPoints())  && powerPitchArray[i].getPoints() >= 0.0){
						rmCriteriaVO.setWithoutPhonePoints(powerPitchArray[i].getPoints()); 
					}
					if(i!=3 && i!=4){
						if(powerPitchArray[i].getProviderName()!= null && powerPitchArray[i].getProviderExternalId().equals(Constants.ATTV6)){
							rmCriteriaVO.setVideoProviderName("ATTV6 Uverse");
						}
						else{
							rmCriteriaVO.setVideoProviderName(powerPitchArray[i].getProviderName());
						}
					}
					if(i==0 || i==1 || i==2){
						if(triplePlayProductsMap!= null && powerPitchArray[i].getProviderExternalId()!= null && triplePlayProductsMap.get(powerPitchArray[i].getProviderExternalId())!= null){
							rmCriteriaVO.setWithPhonePoints(triplePlayProductsMap.get(powerPitchArray[i].getProviderExternalId()).getPoints());
						}
						
					}
					else if(internetPhoneProductsMap!= null && powerPitchArray[i].getProviderExternalId()!= null && internetPhoneProductsMap.get(powerPitchArray[i].getProviderExternalId())!= null){
						rmCriteriaVO.setWithPhonePoints(internetPhoneProductsMap.get(powerPitchArray[i].getProviderExternalId()).getPoints());
					}
					if(maxSpeedMap != null && maxSpeedMap.get(powerPitchArray[i].getProviderExternalId())!= null){
						rmCriteriaVO.setConnectionSpeedVal(maxSpeedMap.get(powerPitchArray[i].getProviderExternalId()));
					}
					roadMapPowerPitchList.add(rmCriteriaVO);
					
				} 
			}			
		}
		logger.info("roadMapPowerPitchList_5845="+roadMapPowerPitchList);
		return roadMapPowerPitchList;
	}
	
	private boolean isInternetSpeedGreaterThan3MB(ProductSummaryVO productVO) {
		if (productVO.getPromotionMetaDataList() != null && productVO.getPromotionMetaDataList().size() > 0){
			for(String metaData :productVO.getPromotionMetaDataList()){
				if (metaData != null &&	metaData.contains("=") && metaData.split("=").length > 1 && "CONN_SPEED".equalsIgnoreCase(metaData.split("=")[0])){
					String connectionSpeed = metaData.split("=")[1];
					if(!Utils.isBlank(connectionSpeed)){
						try{     
							double connectionSpeedVal = Double.valueOf(connectionSpeed);
							productVO.setConnectionSpeedVal(connectionSpeedVal);
							if(connectionSpeedVal > 3 ){   
								return true;         
							}        
						}catch(Exception e){ 
							logger.warn("error_while_to_convert_connectionSpeed_from_String_to_Double="+ e.getMessage());    
						}              
					}
				}
			}
		}
		return false;
	}
	
	private double getInternetSpeed(ProductSummaryVO productVO) {
		double connectionSpeedVal = 0;
		try{ 
			if (productVO.getPromotionMetaDataList() != null && productVO.getPromotionMetaDataList().size() > 0){
				for(String metaData :productVO.getPromotionMetaDataList()){
					if (metaData != null &&	metaData.contains("=") && metaData.split("=").length > 1 && "CONN_SPEED".equalsIgnoreCase(metaData.split("=")[0])){
						String connectionSpeed = metaData.split("=")[1];
						if(!Utils.isBlank(connectionSpeed)){
							connectionSpeedVal = Double.valueOf(connectionSpeed);
							productVO.setConnectionSpeedVal(connectionSpeedVal);
						}
					}
				}
			}
		}catch(Exception e){ 
			logger.warn("error_while_getting_InternetSpeed="+ e.getMessage());    
		} 
		return connectionSpeedVal;
	}
	
	public boolean isConsumer() {
		return isConsumer;
	}

	public void setConsumer(boolean isConsumer) {
		this.isConsumer = isConsumer;
	}

	public boolean isSynthetic() {
		return isSynthetic;
	}

	public void setSynthetic(boolean isSynthetic) {
		this.isSynthetic = isSynthetic;
	}

	public String getHideHughesNet() {
		return hideHughesNet;
	}

	/**
	 * @return the pollingCompleted
	 */
	public boolean isPollingCompleted() {
		return pollingCompleted;
	}

	/**
	 * @param pollingCompleted the pollingCompleted to set
	 */
	public void setPollingCompleted(boolean pollingCompleted) {
		this.pollingCompleted = pollingCompleted;
	}

	public void setHideHughesNet(String hideHughesNet) {
		this.hideHughesNet = hideHughesNet;
	}

	public Map<Long, Long> getRtProductCountMap() {
		return rtProductCountMap;
	}

	public boolean isRoadmapCompleted() {
		return isRoadmapCompleted;
	}

	public void setRoadmapCompleted(boolean isRoadmapCompleted) {
		this.isRoadmapCompleted = isRoadmapCompleted;
	}

	public void setRtProductCountMap(Map<Long, Long> rtProductCountMap) {
		this.rtProductCountMap = rtProductCountMap;
	}
}