/**
 * 
 */
package com.A.productResults.vo;

import java.util.List;
import java.util.Map;

import com.A.ui.vo.DigitalSalesContextVO;
import com.A.ui.vo.SalesCenterVO;
import com.A.xml.pr.v4.ProviderSourceType;
import com.A.xml.se.v4.ServiceabilityEnterpriseResponse;

/**
 * Represents Interface for fetch Products for a given Address Provides
 * interface to retrieve address validation and search results <BR>
 * Can plugin a formatter to format the results based on the product source. <BR>
 * Provides convenience methods to fetch products by category. <BR>
 * Allows products to be sorted for given sort preference.
 */
public interface ProductSearchIface {

	public static final String ZIP_CODE_MISSING = "No Addresses Match---ZIP code missing.";
	public static final String STATE_MISSING = "No Addresses Match---State missing.";
	public static final String CITY_NOT_FOUND_IN_STATE = "No Addresses Match---City could not be found in state.";
	public static final String CITY_MISSING = "No Addresses Match---City missing.";
	public static final String UNIT_NOT_FOUND = "No Addresses Match---Unit not found.";
	public static final String UNIT_MISSING = "No Addresses Match---Unit missing.";
	public static final String STREET_TYPE_MISSING = "No Addresses Match---Street type missing.";
	public static final String STREET_NAME_MISSING = "No Addresses Match---Street name missing.";
	public static final String STREET_NUMBER_MISSING = "No Addresses Match---Street number missing.";
	public static final String STREET_NOT_FOUND = "No Addresses Match---Street name not found.";
	public static final String NO_ADDRESSES_MATCH = "No Addresses Match";
	public static final String MULTIPLE_ADDRESS = "Multiple Address";
	public static final String ADDRESS_FOUND = "Address Found - Proceed to next screen";
	public static final String NO_ADDRESS_MATCH = "No Address Match";
	public static final String WEB = "web";
	public static final String HOUSE = "house";
	public static final String PRICE = "Price";
	public static final String PROVIDER = "Provider";
	public static final String SCORE = "Score";
	public static final String CONCERT = "Concert";
	public static final String APARTMENT = "Apartment";
	public static final String CONDO = "Condo";
	public static final String DUPLEX = "Duplex";
	public static final String MOBILE_HOME = "Mobile Home";
	
	public static String TRIPLE_PLAY = "TRIPLE_PLAY";
	public static String TRIPLE_PLAY_DIGITAL = "TV, Internet, Phone";
	public static String DOUBLE_PLAY = "DOUBLE_PLAY";
	public static String DOUBLE_PLAY_DIGITAL_1 = "TV, Internet";
	public static String DOUBLE_PLAY_DIGITAL_2 = "Internet, Phone";
	public static String DOUBLE_PLAY_DIGITAL_3 = "TV, Phone";
	public static String VIDEO = "VIDEO";
	public static String PHONE = "PHONE";
	public static String INTERNET = "INTERNET";
	public static String HOMESECURITY = "HOMESECURITY";
	public static String ELECTRICITY = "ELECTRICITY";
	public static String WATER = "WATER";
	public static String NATURALGAS = "NATURALGAS";
	public static String WASTEREMOVAL = "WASTEREMOVAL";
	public static String TECH = "TECHSUPPORT";
	public static String OFFERS = "OFFERS";
	public static String BUNDLES = "BUNDLES";
	public static String APPLIANCEPROTECTION = "APPLIANCEPROTECTION";
	
	public static final String PER_MONTH = "/mo";
	public static final String THERM = "therm";
	public static final String PER_THERM = "/therm";
	public static final String THERM_RATE = "THERM_RATE";
	public static final String TOTALPOINTS = "totalPoints";

	/**
	 * returns SE2 response for Address Validation
	 */
	ServiceabilityEnterpriseResponse getSE2Response();
	void setSE2Response(ServiceabilityEnterpriseResponse sarRes);
	/**
	 * Allows for updating the polling status for a given Provider
	 * 
	 * @param extId
	 * @param name
	 * @param status
	 * @param statusMsg
	 */
	void updateProviderStatus(String extId, String name, String status,
			String statusMsg);

	/**
	 * Add Product retrieved from a Provider while polling for results
	 * 
	 * @param category
	 * @param product
	 */
	void addProduct(String category, ProductSummaryVO product);

	/**
	 * Returns a Map containing polling status for all the Providers
	 * 
	 * @return Map<String, PollingStatus>
	 */
	Map<String, PollingStatus> getProviderStatus();

	/**
	 * Returns Products for the given address Contains products associated to
	 * all categories
	 * 
	 * @return List<ProductSummaryVO>
	 */
	List<ProductSummaryVO> getAllProductList();

	void setAllProductList(List<ProductSummaryVO> allProductList);

	String getGUID();

	void setGUID(String GUID);

	/**
	 * Dwelling Type used to fetch Products for the input address
	 * Defaults to HOUSE
	 * @return String
	 */
	String getDwellingType();

	void setDwellingType(String dwellingType);

	/**
	 * returns true if the SE2 Call did not result in validation errors 
	 * like address not found or multiple address suggestions
	 * @return
	 */
	boolean isValidAddress();

	String getInputAddress();

	int getTotalCount();

	List<ProductSummaryVO> getPowerPitchList();

	/**
	 * Allows to skip fetching power pitch results ex - PowerPitch Data is only
	 * needed for Sales Channel
	 * 
	 * @return boolean
	 */
	boolean skipPowerPitch();

	/**
	 * Formatter to format PlanDetails based on the Provider Source - RealTime
	 * (DISH, VERIZON, etc)
	 * 
	 * @param providerSourceType
	 * @return ProductDetailsFormatter
	 */
	ProductDetailsFormatter getFormatter(ProviderSourceType providerSourceType);
	
	String getChannel();
	
	SalesCenterVO getSalesCenterVO();
	
	String getRentOrOwn();
	
    Map<String, String> getPartnerSpecificDataMap();
	
	boolean isPartnerSpecificMatchReqForUtility();
	
	String getOperatingCompany();
	
	List<String> getWebChannelProductExtIDList();
	
	DigitalSalesContextVO getDigitalSalesContextVO();
	void sortProducts(List<ProductSummaryVO> prdDtlsList,String sortorder);
}
