package com.A.productResults.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.A.ui.vo.DigitalSalesContextVO;
import com.A.ui.vo.SalesCenterVO;
import com.A.xml.pr.v4.ProviderSourceBaseType;
import com.A.xml.pr.v4.ProviderSourceType;
import com.A.xml.se.v4.AddressType;
import com.A.xml.se.v4.CandidateAddressList.CandidateAddress;
import com.A.xml.se.v4.ProcessingMessage;
import com.A.xml.se.v4.ServiceabilityEnterpriseResponse;
import com.A.xml.se.v4.ServiceabilityResponse2;
import com.A.xml.se.v4.StatusType.ProcessingMessages;

/**
 * Represents Search criteria and results obtained when searching for products
 * <BR>Product Results Manager updates this object as it polls for products from
 * <BR>realtime and static providers.
 *
 */
public class ProductSearchVO implements Serializable,ProductSearchIface {
	protected final class ScoreComparator implements Comparator<ProductSummaryVO> {
		/**
		 * @see Comparator#compare(Object, Object)
		 */
		public int compare(ProductSummaryVO o1, ProductSummaryVO o2) {
			Double score1 = null;
			if(o1.getScore() != null) {
				score1 = o1.getScore();
			}
			Double score2 = null;
			if(o2.getScore() != null) {
				score2 = o2.getScore();
			}
			if(score1 == null && score2 == null) {
				return 0;
			}
			else if(score1 == null) {
				return -1;
			}
			else if (score2 == null){
				return 1;
			}
			else {
				return score2.compareTo(score1);
			}
		}
	 }
	 
	 protected final class PriceComparator implements Comparator<ProductSummaryVO> {
		/**
		 * @see Comparator#compare(Object, Object)
		 */
		public int compare(ProductSummaryVO o1, ProductSummaryVO o2) {
			Double price1 = null;
			if(o1.getEffectivePrice() != null && o1.getEffectivePrice() > 0.0) {
				price1 = o1.getEffectivePrice();
			}
			Double price2 = null;
			if(o2.getEffectivePrice() != null && o2.getEffectivePrice() > 0.0) {
				price2 = o2.getEffectivePrice();
			}
			if(price1 == null && price2 == null) {
				return 0;
			}
			else if(price1 == null) {
				return -1;
			}
			else if (price2 == null){
				return 1;
			}
			else {
				return price1.compareTo(price2);
			}
		}
	}
	 
	 protected final class ProviderComparator implements Comparator<ProductSummaryVO> {
		/**
		 * @see Comparator#compare(Object, Object)
		 */
		 public int compare(ProductSummaryVO o1, ProductSummaryVO o2) {
			 
		      String providerName1 = o1.getProviderName().toUpperCase();
		      String providerName2 = o2.getProviderName().toUpperCase();
	 
		      //ascending order
		      return providerName1.compareTo(providerName2);
	 
		      //descending order
		      //return providerName2.compareTo(providerName1);
	    }
	}


	private static final Logger logger = Logger.getLogger(ProductSearchVO.class);
	
	private static Map<Double, String> statusMap = new HashMap<Double, String>();
	static{
			statusMap.put(1.0, ADDRESS_FOUND);
			statusMap.put(1.1, ADDRESS_FOUND);
			statusMap.put( 4.1, MULTIPLE_ADDRESS);
			statusMap.put(1.2, NO_ADDRESSES_MATCH);
			statusMap.put(5.0, STREET_NOT_FOUND);
			statusMap.put(10.1, STREET_NUMBER_MISSING);
			statusMap.put(12.1 ,STREET_NAME_MISSING);
			statusMap.put(13.1, STREET_TYPE_MISSING);
			statusMap.put( 15.1, UNIT_MISSING );
			statusMap.put(15.2, UNIT_NOT_FOUND);
			statusMap.put(16.1, CITY_MISSING);
			statusMap.put(16.3, CITY_NOT_FOUND_IN_STATE);
			statusMap.put(17.1, STATE_MISSING);
			statusMap.put(18.1, ZIP_CODE_MISSING);
	};
	
	
	//Search Criteria
	private String inputAddress;
	private String channel;
	private String dwellingType;
	private String  rentOrOwn;
	private Map<String, String> partnerSpecificDataMap;
	private boolean isPartnerSpecificMatchReqForUtility;
	private List<ProductSummaryVO> allProductList = new ArrayList<ProductSummaryVO>();
	private List<String> alternateAddrList = new ArrayList<String>();

	private String addressValidationStatus;
	private List<String> addressErrors = new ArrayList<String>();
	private String GUID;
	private AddressType correctedAddress;
	//Results by category
	protected Map<String, List<ProductSummaryVO>> categoryMap = new HashMap<String, List<ProductSummaryVO>>();
	private List<String> categoryNameMap = new ArrayList<String>();
		
	//Polling Status by Provider 
	private Map<String, PollingStatus> providerStatus = new HashMap<String, PollingStatus>();
		
	//Response from the Serviceablilty call for the given address
	ServiceabilityEnterpriseResponse sarRes;

	private List<ProductSummaryVO> powerPitchList = new ArrayList<ProductSummaryVO>();
	private List<String> webChannelProducExtIDList = new ArrayList<String>();

	/**
	 * Contains Category and sortOrder used if any
	 */
	protected Map<String, String> categorySortOrderMap = new HashMap<String, String>();

	protected final ScoreComparator scoreComp = new ScoreComparator();

	protected final PriceComparator priceComp = new PriceComparator();

	protected final ProviderComparator providerComp = new ProviderComparator();
	
	protected final TotalPointsComparator totalPointsComp = new TotalPointsComparator();
	
	private String  operatingCompany;
	
	public String getOperatingCompany() {
		return operatingCompany;
	}

	public void setOperatingCompany(String operatingCompany) {
		this.operatingCompany = operatingCompany;
	}

	SalesCenterVO salesCenterVO;
	
	DigitalSalesContextVO digitalSalesContextVO;
	
	public ProductSearchVO(String inputAddress, String channel, String dwellingType, String rentOrOwn,Map<String, String> partnerSpecificDataMap,boolean isPartnerSpecificMatchReqForUtility,
			String operatingCompany) {
		this.inputAddress = inputAddress;
		this.channel = channel;
		this.dwellingType = dwellingType;
		this.rentOrOwn = rentOrOwn;
		this.partnerSpecificDataMap = partnerSpecificDataMap;
		this.isPartnerSpecificMatchReqForUtility = isPartnerSpecificMatchReqForUtility;
		this.operatingCompany = operatingCompany;
	}

	public Map<String, List<ProductSummaryVO>> getCategoryMap() {
		return categoryMap;
	}

	public void setCategoryMap(Map<String, List<ProductSummaryVO>> categoryMap) {
		this.categoryMap = categoryMap;
	}

	public List<String> getCategoryNameMap() {
		return categoryNameMap;
	}

	public void setCategoryNameMap(List<String> categoryNameMap) {
		this.categoryNameMap = categoryNameMap;
	}

	public void setPowerPitchList(List<ProductSummaryVO> powerPitchList) {
		this.powerPitchList = powerPitchList;
	}

	/**
	 * Returns Products associated to the given category.
	 * Products are sorted using specified sort order
	 * @param category
	 * @param newSortOrder
	 * @return List<ProductSummaryVO>
	 */
	public List<ProductSummaryVO> getProductsByCategory(String category, String newSortOrder) {		
		List<ProductSummaryVO> prdDtlsVoList = categoryMap.get(category);
		if(prdDtlsVoList == null){
			prdDtlsVoList = new ArrayList<ProductSummaryVO>();
		}
		String currentSortOrder = categorySortOrderMap.get(category);
		if (categorySortOrderMap.containsKey(category)) {
			if (!newSortOrder.equals(currentSortOrder)) {
				sortProducts(prdDtlsVoList, newSortOrder);
				categorySortOrderMap.put(category, newSortOrder);
			}
		} else {
			sortProducts(prdDtlsVoList, newSortOrder);
			categorySortOrderMap.put(category, newSortOrder);
		}
		return prdDtlsVoList;
	}
	
	public void sortProducts(List<ProductSummaryVO> prdDtlsList,String sortorder) {
		if(PRICE.equalsIgnoreCase(sortorder)){
			Collections.sort(prdDtlsList, priceComp);
		}else if(PROVIDER.equalsIgnoreCase(sortorder)){
			Collections.sort(prdDtlsList, providerComp);
		}else if(TOTALPOINTS.equalsIgnoreCase(sortorder)){
			Collections.sort(prdDtlsList, totalPointsComp);
		}
		else{
			Collections.sort(prdDtlsList, scoreComp);
		}
	}
	
	public void addProduct(String category, ProductSummaryVO product){
		 if (!categoryMap.containsKey(category)) {
			 categoryMap.put(category, new ArrayList<ProductSummaryVO>());
		 }
		 String externalId = product.getExternalId();
		 if(!categoryNameMap.contains(externalId)){
			 categoryMap.get(category).add(product);
			 categoryNameMap.add(externalId);
		 }
		 if (categorySortOrderMap.containsKey(category)) {
			 categorySortOrderMap.remove(category);
		 }
	}

	public int getTotalCount() {
		
		int size = 0;
		for (String key : categoryMap.keySet()) {
			size = size + categoryMap.get(key).size();
		}
		logger.info("Total Count :: " + size);

		return size;
	}
	public int getCountByCategory(String category) {
		if (categoryMap.containsKey(category))
			return categoryMap.get(category).size();
		else 
			return 0;
	}

	public String getInputAddress() {
		return inputAddress;
	}

	public void setInputAddress(String inputAddress) {
		this.inputAddress = inputAddress;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDwellingType() {
		return dwellingType;
	}

	public void setDwellingType(String dwellingType) {
		this.dwellingType = dwellingType;
	}


	public List<String> getAlternateAddressList() {
		return alternateAddrList;
	}


	private void addAlternateAddress(String address) {
		this.alternateAddrList.add(address);		
	}
	public List<ProductSummaryVO> getAllProductList() {
		return allProductList;
	}

	public void setAllProductList(List<ProductSummaryVO> allProductList) {
		Collections.sort(allProductList, scoreComp);
		this.allProductList = allProductList;
	}

	public void setCorrectedAddress(
			com.A.xml.se.v4.AddressType correctedAddress) {
		this.correctedAddress= correctedAddress;		
	}
	
	public String getStatus() {
		return addressValidationStatus;
	}

	public void setStatus(String status) {
		this.addressValidationStatus = status;
	}

	/**
	 * @return the addressErrors
	 */
	public List<String> getAddressErrors() {
		return addressErrors;
	}

	/**
	 * @param addressError the addressError to set
	 */
	public void addAddressError(String addressError) {
		this.addressErrors.add(addressError);
	}

	public String getGUID() {
		return GUID;
	}


	public void setGUID(String GUID) {
		this.GUID = GUID;
	}


	public AddressType getCorrectedAddress() {
		return correctedAddress;
	}


	public void setSE2Response(ServiceabilityEnterpriseResponse response) {
		
		assert(response != null);
		this.sarRes = response;
		
		if (sarRes == null || sarRes.getStatus() == null){
			addressValidationStatus = NO_ADDRESS_MATCH;
		} else {			
			ProcessingMessages processingMessages = sarRes.getStatus().getProcessingMessages();
			if (processingMessages != null 
					&& processingMessages.getMessages() != null 
					&& processingMessages.getMessages().size() > 0){
				
				for (ProcessingMessage status : sarRes.getStatus().getProcessingMessages().getMessages()){
					double statusCode = status.getCode();
					if (statusMap.containsKey(statusCode)) {
						addressValidationStatus = statusMap.get(statusCode);
						if ((addressValidationStatus == ADDRESS_FOUND)|| (addressValidationStatus == MULTIPLE_ADDRESS)){
							break;
						}else{
							addAddressError(status.getText());
						}
					}
				}
				if (addressValidationStatus == ADDRESS_FOUND){
					setGUID(sarRes.getGUID());
					ServiceabilityResponse2 sre = (ServiceabilityResponse2)sarRes.getResponse();
					correctedAddress = sre.getCorrectedAddress();
				}else if (addressValidationStatus == MULTIPLE_ADDRESS){
					ServiceabilityResponse2 sre = (ServiceabilityResponse2)sarRes.getResponse();
					for (CandidateAddress address :sre.getCandidateAddressList().getCandidateAddresses()){
						addAlternateAddress(address.getAddressBlock());
					}
					//addAlternateAddress("None of the above");
				}
			}
		}
		
	}
	
	public ServiceabilityEnterpriseResponse getSE2Response() {
		return sarRes;
	}

	public boolean isValidAddress() {		
		return ADDRESS_FOUND.equals(addressValidationStatus);
	}

	public Map<String, PollingStatus> getProviderStatus() {		
		return providerStatus;
	}

	public boolean isPolling() {
		for (PollingStatus status : providerStatus.values()) {
			if (status.isPending())
				return true;
		}
		return false;
	}
	public int getProviderCount(){
			return providerStatus.size();
	}
	public List<PollingStatus> getPollingProviderList() {
		List<PollingStatus> retval = new ArrayList<PollingStatus>();
		for (PollingStatus status : providerStatus.values()) {
			if (status.isPending())
				retval.add(status);
		}
		return retval;
	}

	public void updateProviderStatus(String extId, String name, String status, String statusMsg) {
		PollingStatus prvdrStatus = new PollingStatus(extId, name, status, statusMsg);
		providerStatus.put(name, prvdrStatus);		
	}
	
	public List<ProductSummaryVO> getPowerPitchList() {		
		return powerPitchList;
	}


	public boolean skipPowerPitch() {
		return false;
	}
	
	public ProductDetailsFormatter getFormatter(ProviderSourceType source) {
		ProductDetailsFormatter frmtr = ProductDetailsFormatter.getInstance();
		populateRTFormatter(source, frmtr);
		return frmtr;
	}

	protected void populateRTFormatter(ProviderSourceType source, ProductDetailsFormatter frmtr) {
		ProviderSourceBaseType providerType = source.getValue();		
		RTProductHelper rtHelper = null;
		if (providerType == ProviderSourceBaseType.INTERNAL) {
			// return StaticProductFormatter...
		}
		else if (providerType == ProviderSourceBaseType.REALTIME) {
			// frmtr = RealtimeProductFormatter...
			if (source.getDatasource().equalsIgnoreCase("VZ")){
				//return VZProductFormatter
			}
			else if (source.getDatasource().equalsIgnoreCase("ATTSTI")){
				// return VZProductFormatter
			} else if (source.getDatasource().equalsIgnoreCase("G2B-Comcast")){
				//return G2B Comcast Formatter
			} else if (source.getDatasource().equalsIgnoreCase("DISH Network")
					|| source.getDatasource().equalsIgnoreCase("DISH")){
				//return DISH Network Formatter
				rtHelper = new DishRTProductHelper();
			}
		}
		frmtr.setRTHelper(rtHelper);
	}

	public SalesCenterVO getSalesCenterVO() {
		return salesCenterVO;
	}

	public void setSalesCenterVO(SalesCenterVO salesCenterVO) {
		this.salesCenterVO = salesCenterVO;
	}

	public String getRentOrOwn() {
		return rentOrOwn;
	}

	public void setRentOrOwn(String rentOrOwn) {
		this.rentOrOwn = rentOrOwn;
	}

	/**
	 * @return the partnerSpecificDataMap
	 */
	public Map<String, String> getPartnerSpecificDataMap() {
		return partnerSpecificDataMap;
	}

	/**
	 * @param partnerSpecificDataMap the partnerSpecificDataMap to set
	 */
	public void setPartnerSpecificDataMap(Map<String, String> partnerSpecificDataMap) {
		this.partnerSpecificDataMap = partnerSpecificDataMap;
	}

	/**
	 * @return the isPartnerSpecificMatchReqForUtility
	 */
	public boolean isPartnerSpecificMatchReqForUtility() {
		return isPartnerSpecificMatchReqForUtility;
	}

	/**
	 * @param isPartnerSpecificMatchReqForUtility the isPartnerSpecificMatchReqForUtility to set
	 */
	public void setPartnerSpecificMatchReqForUtility(boolean isPartnerSpecificMatchReqForUtility) {
		this.isPartnerSpecificMatchReqForUtility = isPartnerSpecificMatchReqForUtility;
	}

	public List<String> getWebChannelProductExtIDList() {
		return this.webChannelProducExtIDList;
	}
	
	public void setWebChannelProductExtIDList(List<String> webProviderExtIDList) {
		this.webChannelProducExtIDList = webProviderExtIDList;
	}

	public DigitalSalesContextVO getDigitalSalesContextVO() {
		return digitalSalesContextVO;
	}

	public void setDigitalSalesContextVO(DigitalSalesContextVO digitalSalesContextVO) {
		this.digitalSalesContextVO = digitalSalesContextVO;
	}
	
	protected final class TotalPointsComparator implements Comparator<ProductSummaryVO> {
		/**
		 * @see Comparator#compare(Object, Object)
		 */
		public int compare(ProductSummaryVO o1, ProductSummaryVO o2) {
			Double totalPoints1 = null;
			if(o1.getTotalPoints() != null) {
				totalPoints1 = o1.getTotalPoints();
			}
			Double totalPoints2 = null;
			if(o2.getTotalPoints() != null) {
				totalPoints2 = o2.getTotalPoints();
			}
			if(totalPoints1 == null && totalPoints2 == null) {
				return 0;
			}
			else if(totalPoints1 == null) {
				return -1;
			}
			else if (totalPoints2 == null){
				return 1;
			}
			else {
				return totalPoints2.compareTo(totalPoints1);
			}
		}
	 }

	
}
