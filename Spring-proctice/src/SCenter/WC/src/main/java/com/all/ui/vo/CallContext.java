package com.A.ui.vo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.A.productResults.managers.ProductResultsManager;
import com.A.xml.cm.v4.CustAddress;
import com.A.xml.v4.Disposition;
import com.A.xml.v4.OrderType;

public class CallContext {
	
	private String typeOfcheckBox;
	public String getTypeOfcheckBox() {
		return typeOfcheckBox;
	}
	public void setTypeOfcheckBox(String typeOfcheckBox) {
		this.typeOfcheckBox = typeOfcheckBox;
	}
	private String fromUtility;
	public String getFromUtility() {
		return fromUtility;
	}
	public void setFromUtility(String fromUtility) {
		this.fromUtility = fromUtility;
	}
	private JSONObject salescontextAuthAMB;
	private String ambDisconnectdatetime;
	private String currentdatetime;
	private Long salesCallId;
	private Calendar idlePageStartTime;
	public JSONObject getSalescontextAuthAMB() {
		return salescontextAuthAMB;
	}
	public void setSalescontextAuthAMB(JSONObject salescontextAuthAMB) {
		this.salescontextAuthAMB = salescontextAuthAMB;
	}
	public String getAmbDisconnectdatetime() {
		return ambDisconnectdatetime;
	}
	public void setAmbDisconnectdatetime(String ambDisconnectdatetime) {
		this.ambDisconnectdatetime = ambDisconnectdatetime;
	}
	public String getCurrentdatetime() {
		return currentdatetime;
	}
	public void setCurrentdatetime(String currentdatetime) {
		this.currentdatetime = currentdatetime;
	}
	public Long getSalesCallId() {
		return salesCallId;
	}
	public void setSalesCallId(Long salesCallId) {
		this.salesCallId = salesCallId;
	}
	public Calendar getIdlePageStartTime() {
		return idlePageStartTime;
	}
	public void setIdlePageStartTime(Calendar idlePageStartTime) {
		this.idlePageStartTime = idlePageStartTime;
	}
	public boolean isSalesCallPopulated() {
		return isSalesCallPopulated;
	}
	public void setSalesCallPopulated(boolean isSalesCallPopulated) {
		this.isSalesCallPopulated = isSalesCallPopulated;
	}
	private boolean isSalesCallPopulated;
	private Long billingInfoExternalId;
	public Long getBillingInfoExternalId() {
		return billingInfoExternalId;
	}
	public void setBillingInfoExternalId(Long billingInfoExternalId) {
		this.billingInfoExternalId = billingInfoExternalId;
	}
	private Integer primaryLanguage;
	public Integer getPrimaryLanguage() {
		return primaryLanguage;
	}
	public void setPrimaryLanguage(Integer primaryLanguage) {
		this.primaryLanguage = primaryLanguage;
	}
	private Long callStartTime;
	public Long getCallStartTime() {
		return callStartTime;
	}
	public void setCallStartTime(Long callStartTime) {
		this.callStartTime = callStartTime;
	}
	private String gotoofferpage;
	public String getGotoofferpage() {
		return gotoofferpage;
	}
	public void setGotoofferpage(String gotoofferpage) {
		this.gotoofferpage = gotoofferpage;
	}
	private String referralDet;
	public String getReferralDet() {
		return referralDet;
	}
	public void setReferralDet(String referralDet) {
		this.referralDet = referralDet;
	}
	public String getDtxml() {
		return dtxml;
	}
	public void setDtxml(String dtxml) {
		this.dtxml = dtxml;
	}
	public SalesCenterVO getSalescontext() {
		return salescontext;
	}
	public void setSalescontext(SalesCenterVO salescontext) {
		this.salescontext = salescontext;
	}
	public ConsumerVO getSalescontextDt() {
		return salescontextDt;
	}
	public void setSalescontextDt(ConsumerVO salescontextDt) {
		this.salescontextDt = salescontextDt;
	}
	public String getRapidResponsecustomer() {
		return RapidResponsecustomer;
	}
	public void setRapidResponsecustomer(String rapidResponsecustomer) {
		RapidResponsecustomer = rapidResponsecustomer;
	}
	public Long getSalesSessionId() {
		return salesSessionId;
	}
	public void setSalesSessionId(Long salesSessionId) {
		this.salesSessionId = salesSessionId;
	}
	
	public boolean isHasProducts() {
		return hasProducts;
	}
	public void setHasProducts(boolean hasProducts) {
		this.hasProducts = hasProducts;
	}
	public String getDtEmail() {
		return DtEmail;
	}
	public void setDtEmail(String dtEmail) {
		DtEmail = dtEmail;
	}
	public String getCycletime() {
		return cycletime;
	}
	public void setCycletime(String cycletime) {
		this.cycletime = cycletime;
	}
	public ProductResultsManager getProductResultManager() {
		return productResultManager;
	}
	public void setProductResultManager(ProductResultsManager productResultManager) {
		this.productResultManager = productResultManager;
	}
	public String getGUID() {
		return GUID;
	}
	public void setGUID(String gUID) {
		GUID = gUID;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public CustAddress getAddress() {
		return address;
	}
	public void setAddress(CustAddress address) {
		this.address = address;
	}
	public OrderType getOrder() {
		return order;
	}
	public void setOrder(OrderType order) {
		this.order = order;
	}
	public String getSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}
	public String getConfirm_NonRR_4() {
		return Confirm_NonRR_4;
	}
	public void setConfirm_NonRR_4(String confirmNonRR_4) {
		Confirm_NonRR_4 = confirmNonRR_4;
	}
	public String getUtilityOffer() {
		return utilityOffer;
	}
	public Double getCallTimeBeforeUtility() {
		return callTimeBeforeUtility;
	}
	public void setCallTimeBeforeUtility(Double callTimeBeforeUtility) {
		this.callTimeBeforeUtility = callTimeBeforeUtility;
	}
	public void setUtilityOffer(String utilityOffer) {
		this.utilityOffer = utilityOffer;
	}
	public String getOffer() {
		return offer;
	}
	public void setOffer(String offer) {
		this.offer = offer;
	}
	
	public String getProviderIds() {
		return providerIds;
	}
	public void setProviderIds(String providerIds) {
		this.providerIds = providerIds;
	}
	public String getLineItemIds() {
		return lineItemIds;
	}
	public void setLineItemIds(String lineItemIds) {
		this.lineItemIds = lineItemIds;
	}
	public String getProductSrcs() {
		return productSrcs;
	}
	public void setProductSrcs(String productSrcs) {
		this.productSrcs = productSrcs;
	}
	public String getGotoRecommondation() {
		return gotoRecommondation;
	}
	public void setGotoRecommondation(String gotoRecommondation) {
		this.gotoRecommondation = gotoRecommondation;
	}
	public boolean isDisplayButton() {
		return displayButton;
	}
	public void setDisplayButton(boolean displayButton) {
		this.displayButton = displayButton;
	}
	public String getSubmitvalue() {
		return submitvalue;
	}
	public void setSubmitvalue(String submitvalue) {
		this.submitvalue = submitvalue;
	}
	public String getTypeOfService() {
		return typeOfService;
	}
	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}
	public String getIsServiceChecked() {
		return isServiceChecked;
	}
	public void setIsServiceChecked(String isServiceChecked) {
		this.isServiceChecked = isServiceChecked;
	}
	public String getFromQualification() {
		return fromQualification;
	}
	public void setFromQualification(String fromQualification) {
		this.fromQualification = fromQualification;
	}
	public Map<String, String> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(Map<String, String> questionList) {
		this.questionList = questionList;
	}
	public String getNotepadValue() {
		return notepadValue;
	}
	public void setNotepadValue(String notepadValue) {
		this.notepadValue = notepadValue;
	}
	public Map<String, String> getRealTimeMap() {
		return realTimeMap;
	}
	public void setRealTimeMap(Map<String, String> realTimeMap) {
		this.realTimeMap = realTimeMap;
	}
	public Map<String, String> getExistingService() {
		return existingService;
	}
	public void setExistingService(Map<String, String> existingService) {
		this.existingService = existingService;
	}
	public Map<String, String> getPreDiscoveryTransfer() {
		return preDiscoveryTransfer;
	}
	public void setPreDiscoveryTransfer(Map<String, String> preDiscoveryTransfer) {
		this.preDiscoveryTransfer = preDiscoveryTransfer;
	}
	public Map<String, String> getNewService() {
		return newService;
	}
	public void setNewService(Map<String, String> newService) {
		this.newService = newService;
	}
	public Map<String, String> getRecIconMap() {
		return recIconMap;
	}
	public void setRecIconMap(Map<String, String> recIconMap) {
		this.recIconMap = recIconMap;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public HashMap<String, Map<String, String>> getProductFeatureNameMap() {
		return productFeatureNameMap;
	}
	public void setProductFeatureNameMap(
			HashMap<String, Map<String, String>> productFeatureNameMap) {
		this.productFeatureNameMap = productFeatureNameMap;
	}
	public List<Disposition> getDispositionList() {
		return dispositionList;
	}
	public void setDispositionList(List<Disposition> dispositionList) {
		this.dispositionList = dispositionList;
	}
	private String dtxml;
	private SalesCenterVO salescontext;
	private ConsumerVO salescontextDt;
	private String RapidResponsecustomer;
	private Long salesSessionId;
	private Long customerID;
	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}
	public Long getCustomerID() {
		return customerID;
	}
	private boolean hasProducts;
	private String DtEmail;
	private String cycletime;
	private ProductResultsManager productResultManager;
	private String GUID;
	private Long addressId;
	private CustAddress address;
	private OrderType order;
	private Long orderId;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	private String sendEmail;
	private String Confirm_NonRR_4;
	private String utilityOffer;
	private String offer;
	private Double callTimeBeforeUtility;
	private String providerIds;
	private String lineItemIds;
	private String productSrcs;
	private String gotoRecommondation;
	private boolean displayButton;
	private String submitvalue;
	private String typeOfService;
	private String isServiceChecked;
	private String fromQualification;
	private Map<String, String> questionList;
	private String notepadValue;
	private Map<String, String> realTimeMap;
	private Map<String, String> existingService;
	private Map<String, String> preDiscoveryTransfer;
	private Map<String, String> newService;
	private Map<String, String> recIconMap;
	
	private String categoryName;
	private HashMap<String, Map<String, String>>  productFeatureNameMap;
	private List<Disposition> dispositionList;
}
