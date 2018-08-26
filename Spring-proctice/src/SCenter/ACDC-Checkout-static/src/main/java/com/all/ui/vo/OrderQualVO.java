package com.AL.ui.vo;

import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.json.JSONObject;

import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.PhoneNumberType;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.pr.v4.ProviderSourceBaseType;
import com.AL.xml.v4.LineItemSelectionType;
import com.AL.xml.v4.LineItemSelectionsType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.SelectionChoiceType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;

public class OrderQualVO {
	private String orderId;
	private String productExternalId;
	private long lineItemExternalId;
	private String customerExternalId;
	private String GUID;
	private LineItemSelectionsType lineItemSelections;
	private String ssn;
	private XMLGregorianCalendar dob;
	private String securityQuestion;
	private String securityAnswer;
	private String securityPin;
	private PhoneNumberType workPhoneNumber;
	private boolean isBillingAddressSameAsService;
	private CustAddress billingAddress;
	private String dataField;
	private String secondaryContactNumber;
	private String secondaryContactNumberExt;
	private Map<String, FeatureType> availFeatureMap ;
	private String providerExternalId;
	private String agentId;
	private List<DataField> availableDataField;
	private ProductInfoType productInfo;
	private DialogueVO dialogueVO; 
	private Map<String, FeatureGroupType> availableFeatureGroup;
	private Map<String, FeatureType> dialogueFeatureMap ;
	private Map<String, FeatureGroupType> dialogueFeatureGroup;
	private Map<String, Map<String, List<String>>> enableDependencyMap;
	private LineItemType lineItemType ;
	private List<String> viewDetailsSelFeatureList;
	private List<FeatureGroup> viewDetailsSelFeatureGroupList;
	private String errorMsg;
	private boolean recoverableError;
	private boolean unRecoverableError;
	private int lineItemNumber;
	private JSONObject promotionStatusJSON;
	private String productDetailType;
	private ProviderSourceBaseType providerBaseType;
	private String baseRecurringPrice;
	private String baseNonRecurringPrice;
	private boolean isAsisPlan;
	private String parentExternalId;
	private LineItemType newLineItemType ;
	private Map<String, List<PriceDisplayVO>> priceDisplayVOMap;
	private String providerConfirmationNumber;
	private String isUtilityOfferAvailOnOrder;
	public Long getOrderIdAsLong() {
		if (orderId == null) {
			return null;
		}
		return Long.valueOf(orderId);
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductExternalId() {
		return productExternalId;
	}

	public void setProductExternalId(String productExternalId) {
		this.productExternalId = productExternalId;
	}

	public long getLineItemExternalId() {
		return lineItemExternalId;
	}

	public void setLineItemExternalId(long lineItemExternalId) {
		this.lineItemExternalId = lineItemExternalId;
	}

	public String getCustomerExternalId() {
		return customerExternalId;
	}

	public void setCustomerExternalId(String customerExternalId) {
		this.customerExternalId = customerExternalId;
	}

	public String getGUID() {
		return GUID;
	}

	public void setGUID(String gUID) {
		GUID = gUID;
	}

	public LineItemSelectionsType getLineItemSelections() {
		return lineItemSelections;
	}

	public void setLineItemSelections(LineItemSelectionsType lineItemSelections) {
		this.lineItemSelections = lineItemSelections;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public XMLGregorianCalendar getDob() {
		return dob;
	}

	public void setDob(XMLGregorianCalendar dob) {
		this.dob = dob;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getSecurityPin() {
		return securityPin;
	}

	public void setSecurityPin(String securityPin) {
		this.securityPin = securityPin;
	}

	public PhoneNumberType getWorkPhoneNumber() {
		return workPhoneNumber;
	}

	public void setWorkPhoneNumber(PhoneNumberType workPhoneNumber) {
		this.workPhoneNumber = workPhoneNumber;
	}

	public boolean isBillingAddressSameAsService() {
		return isBillingAddressSameAsService;
	}

	public void setBillingAddressSameAsService(
			boolean isBillingAddressSameAsService) {
		this.isBillingAddressSameAsService = isBillingAddressSameAsService;
	}

	public CustAddress getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(CustAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	/**
	 * @return the secondaryContactNumber
	 */
	 public String getSecondaryContactNumber() {
		 return secondaryContactNumber;
	 }

	 /**
	  * @param secondaryContactNumber the secondaryContactNumber to set
	  */
	 public void setSecondaryContactNumber(String secondaryContactNumber) {
		 this.secondaryContactNumber = secondaryContactNumber;
	 }

	 /**
	  * @return the secondaryContactNumberExt
	  */
	 public String getSecondaryContactNumberExt() {
		 return secondaryContactNumberExt;
	 }

	 /**
	  * @param secondaryContactNumberExt the secondaryContactNumberExt to set
	  */
	 public void setSecondaryContactNumberExt(String secondaryContactNumberExt) {
		 this.secondaryContactNumberExt = secondaryContactNumberExt;
	 }

	 @Override
	 public String toString() {
		 StringBuilder sb = new StringBuilder();
		 sb.append("SSN: " + this.ssn);
		 if (this.dob != null) {
			 sb.append("\nDOB: " + this.dob.toXMLFormat());
		 }
		 sb.append("\nPin: " + this.securityPin);
		 sb.append("\nSecurity question: " + this.securityQuestion);
		 sb.append("\nSecurity answer: " + this.securityAnswer);
		 if (this.workPhoneNumber != null) {
			 sb.append("\nSecond contact: " + this.workPhoneNumber.getValue());
		 }
		 if (this.billingAddress != null) {
			 sb.append("\nBilling address: "
					 + this.billingAddress.getAddress().getAddressBlock());
		 }
		 if (this.lineItemSelections != null) {
			 for (LineItemSelectionType selection : this.lineItemSelections
					 .getSelection()) {
				 sb.append("\ncustomizationId = " + selection.getExternalId());
				 sb.append("\n  parentChoiceId = "
						 + selection.getParentChoiceExternalId());
				 for (SelectionChoiceType choice : selection
						 .getSelectionChoice()) {
					 sb.append("\n     choiceId = " + choice.getExternalId());
				 }
			 }
		 }
		 return sb.toString();
	 }

	 /**
	  * @return the availFeatureMap
	  */
	 public Map<String, FeatureType> getAvailFeatureMap() {
		 return availFeatureMap;
	 }

	 /**
	  * @param availFeatureMap the availFeatureMap to set
	  */
	 public void setAvailFeatureMap(Map<String, FeatureType> availFeatureMap) {
		 this.availFeatureMap = availFeatureMap;
	 }

	 public String getProviderExternalId() {
		 return providerExternalId;
	 }

	 public void setProviderExternalId(String providerExternalId) {
		 this.providerExternalId = providerExternalId;
	 }

	 public String getAgentId() {
		 return agentId;
	 }

	 public void setAgentId(String agentId) {
		 this.agentId = agentId;
	 }

	 public List<DataField> getAvailableDataField() {
		 return availableDataField;
	 }

	 public void setAvailableDataField(List<DataField> availableDataField) {
		 this.availableDataField = availableDataField;
	 }

	 public ProductInfoType getProductInfo() {
		 return productInfo;
	 }

	 public void setProductInfo(ProductInfoType productInfo) {
		 this.productInfo = productInfo;
	 }

	 public DialogueVO getDialogueVO() {
		 return dialogueVO;
	 }

	 public void setDialogueVO(DialogueVO dialogueVO) {
		 this.dialogueVO = dialogueVO;
	 }

	 public Map<String, FeatureGroupType> getAvailableFeatureGroup() {
		 return availableFeatureGroup;
	 }

	 public void setAvailableFeatureGroup(
			 Map<String, FeatureGroupType> availableFeatureGroup) {
		 this.availableFeatureGroup = availableFeatureGroup;
	 }

	 public Map<String, FeatureType> getDialogueFeatureMap() {
		 return dialogueFeatureMap;
	 }

	 public void setDialogueFeatureMap(Map<String, FeatureType> dialogueFeatureMap) {
		 this.dialogueFeatureMap = dialogueFeatureMap;
	 }

	 public Map<String, FeatureGroupType> getDialogueFeatureGroup() {
		 return dialogueFeatureGroup;
	 }

	 public void setDialogueFeatureGroup(
			 Map<String, FeatureGroupType> dialogueFeatureGroup) {
		 this.dialogueFeatureGroup = dialogueFeatureGroup;
	 }

	 public Map<String, Map<String, List<String>>> getEnableDependencyMap() {
		 return enableDependencyMap;
	 }

	 public void setEnableDependencyMap(
			 Map<String, Map<String, List<String>>> enableDependencyMap) {
		 this.enableDependencyMap = enableDependencyMap;
	 }

	 public LineItemType getLineItemType() {
		 return lineItemType;
	 }

	 public void setLineItemType(LineItemType lineItemType) {
		 this.lineItemType = lineItemType;
	 }

	 /**
	  * @return the lineItemNumber
	  */
	 public int getLineItemNumber() {
		 return lineItemNumber;
	 }

	 /**
	  * @param lineItemNumber the lineItemNumber to set
	  */
	 public void setLineItemNumber(int lineItemNumber) {
		 this.lineItemNumber = lineItemNumber;
	 }

	 /**
	  * @return the viewDetailsSelFeatureList
	  */
	 public List<String> getViewDetailsSelFeatureList() {
		 return viewDetailsSelFeatureList;
	 }

	 /**
	  * @param viewDetailsSelFeatureList the viewDetailsSelFeatureList to set
	  */
	 public void setViewDetailsSelFeatureList(List<String> viewDetailsSelFeatureList) {
		 this.viewDetailsSelFeatureList = viewDetailsSelFeatureList;
	 }

	 /**
	  * @return the viewDetailsSelFeatureGroupList
	  */
	 public List<FeatureGroup> getViewDetailsSelFeatureGroupList() {
		 return viewDetailsSelFeatureGroupList;
	 }

	 /**
	  * @param viewDetailsSelFeatureGroupList the viewDetailsSelFeatureGroupList to set
	  */
	 public void setViewDetailsSelFeatureGroupList(
			 List<FeatureGroup> viewDetailsSelFeatureGroupList) {
		 this.viewDetailsSelFeatureGroupList = viewDetailsSelFeatureGroupList;
	 }

	 /**
	  * @return the errorMsg
	  */
	 public String getErrorMsg() {
		 return errorMsg;
	 }

	 /**
	  * @param errorMsg the errorMsg to set
	  */
	 public void setErrorMsg(String errorMsg) {
		 this.errorMsg = errorMsg;
	 }

	 /**
	  * @return the recoverableError
	  */
	 public boolean isRecoverableError() {
		 return recoverableError;
	 }

	 /**
	  * @param recoverableError the recoverableError to set
	  */
	 public void setRecoverableError(boolean recoverableError) {
		 this.recoverableError = recoverableError;
	 }

	 /**
	  * @return the unRecoverableError
	  */
	 public boolean isUnRecoverableError() {
		 return unRecoverableError;
	 }

	 /**
	  * @param unRecoverableError the unRecoverableError to set
	  */
	 public void setUnRecoverableError(boolean unRecoverableError) {
		 this.unRecoverableError = unRecoverableError;
	 }

	 /**
	  * @return the promotionStatusJSON
	  */
	 public JSONObject getPromotionStatusJSON() {
		 return promotionStatusJSON;
	 }

	 /**
	  * @param promotionStatusJSON the promotionStatusJSON to set
	  */
	 public void setPromotionStatusJSON(JSONObject promotionStatusJSON) {
		 this.promotionStatusJSON = promotionStatusJSON;
	 }

	 /**
	  * @return the productDetailType
	  */
	 public String getProductDetailType() {
		 return productDetailType;
	 }

	 /**
	  * @param productDetailType the productDetailType to set
	  */
	 public void setProductDetailType(String productDetailType) {
		 this.productDetailType = productDetailType;
	 }

	 /**
	  * @return the providerBaseType
	  */
	 public ProviderSourceBaseType getProviderBaseType() {
		 return providerBaseType;
	 }

	 /**
	  * @param providerBaseType the providerBaseType to set
	  */
	 public void setProviderBaseType(ProviderSourceBaseType providerBaseType) {
		 this.providerBaseType = providerBaseType;
	 }

	 /**
	  * @return the baseRecurringPrice
	  */
	 public String getBaseRecurringPrice() {
		 return baseRecurringPrice;
	 }

	 /**
	  * @param baseRecurringPrice the baseRecurringPrice to set
	  */
	 public void setBaseRecurringPrice(String baseRecurringPrice) {
		 this.baseRecurringPrice = baseRecurringPrice;
	 }

	 /**
	  * @return the baseNonRecurringPrice
	  */
	 public String getBaseNonRecurringPrice() {
		 return baseNonRecurringPrice;
	 }

	 /**
	  * @param baseNonRecurringPrice the baseNonRecurringPrice to set
	  */
	 public void setBaseNonRecurringPrice(String baseNonRecurringPrice) {
		 this.baseNonRecurringPrice = baseNonRecurringPrice;
	 }

	 /**
	  * @return the isAsisPlan
	  */
	 public boolean isAsisPlan() {
		 return isAsisPlan;
	 }

	 /**
	  * @param isAsisPlan the isAsisPlan to set
	  */
	 public void setAsisPlan(boolean isAsisPlan) {
		 this.isAsisPlan = isAsisPlan;
	 }

	 /**
	  * @return the parentExternalId
	  */
	 public String getParentExternalId() {
		 return parentExternalId;
	 }

	 /**
	  * @param parentExternalId the parentExternalId to set
	  */
	 public void setParentExternalId(String parentExternalId) {
		 this.parentExternalId = parentExternalId;
	 }

	/**
	 * @return the newLineItemType
	 */
	public LineItemType getNewLineItemType() {
		return newLineItemType;
	}

	/**
	 * @param newLineItemType the newLineItemType to set
	 */
	public void setNewLineItemType(LineItemType newLineItemType) {
		this.newLineItemType = newLineItemType;
	}

	/**
	 * @return the priceDisplayVOMap
	 */
	public Map<String, List<PriceDisplayVO>> getPriceDisplayVOMap() {
		return priceDisplayVOMap;
	}

	/**
	 * @param priceDisplayVOMap the priceDisplayVOMap to set
	 */
	public void setPriceDisplayVOMap(
			Map<String, List<PriceDisplayVO>> priceDisplayVOMap) {
		this.priceDisplayVOMap = priceDisplayVOMap;
	}
	
	public String getProviderConfirmationNumber() {
		return providerConfirmationNumber;
	}

	public void setProviderConfirmationNumber(String providerConfirmationNumber) {
		this.providerConfirmationNumber = providerConfirmationNumber;
	}
	
	/**
	 * @return the isUtilityOfferAvailOnOrder
	 */
	
	public String getHasUtitliOfferLineitem() {
		return isUtilityOfferAvailOnOrder;
	}

	/**
	 * @param isUtilityOfferAvailOnOrder the isUtilityOfferAvailOnOrder to set
	 */
	public void setHasUtitliOfferLineitem(String isUtilityOfferAvailOnOrder) {
		this.isUtilityOfferAvailOnOrder = isUtilityOfferAvailOnOrder;
	}
	
}