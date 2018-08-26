/**
 * This bean class representing a Search Results form

 */
package com.A.productResults.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.A.productResults.vo.PollingStatus;
import com.A.productResults.vo.ProductSearchVO;
import com.A.productResults.vo.ProductSummaryVO;

/**
 * Bean representing a Search Results form.
 */
public class SearchResults extends ProductSearchVO {

	private static final Logger logger = Logger.getLogger(SearchResults.class);

	private static final String SATELLITE_CAPABILITY = "satellite";

	private static final String SATELLITE = "SATELLITE";
	private static final String CABLE = "CABLE";
	private static final String OTHER_SERVICES = "OTHER_SERVICES";
	private static final String SERVICE_BUNDLES = "SERVICE_BUNDLES";

	public SearchResults(String inputAddress, String channel,
			String dwellingType, String rentOrOwn, Map<String, String> partnerSpecificDataMap, boolean isPartnerSpecificMatchReqForUtility, String operatingCompany) {
		super(inputAddress, channel, dwellingType, rentOrOwn,partnerSpecificDataMap,isPartnerSpecificMatchReqForUtility, operatingCompany);
	}

	
	static List<String> OtherCategories = new ArrayList<String>();
	static {
		OtherCategories.add(ELECTRICITY);
		OtherCategories.add(NATURALGAS);
	}
	
	static List<String> SkipOtherCategories = new ArrayList<String>();
	static {
		SkipOtherCategories.add(HOMESECURITY);
		SkipOtherCategories.add(WATER);
		SkipOtherCategories.add(WASTEREMOVAL);
		SkipOtherCategories.add(TECH);
		SkipOtherCategories.add(OFFERS);
	}
	
	static List<String> ServiceCategories = new ArrayList<String>();
	static { 
		ServiceCategories.add(DOUBLE_PLAY);
		ServiceCategories.add(TRIPLE_PLAY);
	}

	private Integer cableAndSatelliteCount = 0;
	private Integer cableCount = 0;
	private Integer satelliteCount = 0;
	private Integer internetCount = 0;
	private Integer phoneServiceCount = 0;
	private Integer serviceBundlesCount = 0;
	private Integer otherServicesCount = 0;

	private String selectedCategory = VIDEO;
	private String sortOrder = SCORE;

	

	/**
	 * @return the cableAndSatelliteCount
	 */
	public Integer getCableAndSatelliteCount() {
		return cableAndSatelliteCount;
	}

	/**
	 * @param cableAndSatelliteCount
	 *            the cableAndSatelliteCount to set
	 */
	public void setCableAndSatelliteCount(Integer cableAndSatelliteCount) {
		this.cableAndSatelliteCount = cableAndSatelliteCount;
	}

	/**
	 * @return the cableCount
	 */
	public Integer getCableCount() {
		return cableCount;
	}

	/**
	 * @param cableCount
	 *            the cableCount to set
	 */
	public void setCableCount(Integer cableCount) {
		this.cableCount = cableCount;
	}

	/**
	 * @return the satelliteCount
	 */
	public Integer getSatelliteCount() {
		return satelliteCount;
	}

	/**
	 * @param satelliteCount
	 *            the satelliteCount to set
	 */
	public void setSatelliteCount(Integer satelliteCount) {
		this.satelliteCount = satelliteCount;
	}

	/**
	 * @return the internetCount
	 */
	public Integer getInternetCount() {
		return internetCount;
	}

	/**
	 * @param internetCount
	 *            the internetCount to set
	 */
	public void setInternetCount(Integer internetCount) {
		this.internetCount = internetCount;
	}

	/**
	 * @return the phoneServiceCount
	 */
	public Integer getPhoneServiceCount() {
		return phoneServiceCount;
	}

	/**
	 * @param phoneServiceCount
	 *            the phoneServiceCount to set
	 */
	public void setPhoneServiceCount(Integer phoneServiceCount) {
		this.phoneServiceCount = phoneServiceCount;
	}

	/**
	 * @return the serviceBundlesCount
	 */
	public Integer getServiceBundlesCount() {
		return serviceBundlesCount;
	}

	/**
	 * @param serviceBundlesCount
	 *            the serviceBundlesCount to set
	 */
	public void setServiceBundlesCount(Integer serviceBundlesCount) {
		this.serviceBundlesCount = serviceBundlesCount;
	}

	/**
	 * @return the otherServicesCount
	 */
	public Integer getOtherServicesCount() {
		return otherServicesCount;
	}

	/**
	 * @param otherServicesCount
	 *            the otherServicesCount to set
	 */
	public void setOtherServicesCount(Integer otherServicesCount) {
		this.otherServicesCount = otherServicesCount;
	}

	@Override
	public String getInputAddress() {
		if (getCorrectedAddress() != null) {
			return getCorrectedAddress().getAddressBlock();
		}
		return super.getInputAddress();
	}

	@Override
	public boolean skipPowerPitch() {
		return true;
	}

	/**
	 * Returns Products associated to the given category. Products are sorted
	 * using specified sort order
	 * 
	 * @param category
	 * @return List<ProductSummaryVO>
	 */
	public List<ProductSummaryVO> getProductsByCategory(String category) {
		if (category.equalsIgnoreCase(CABLE)) {
			return getCableProducts();
		} else if (category.equalsIgnoreCase(SATELLITE)) {
			return getSatelliteProducts();
		} else {
			return super.getProductsByCategory(category, sortOrder);
		}
	}

	/**
	 * Returns Products associated to the given category.
	 * 
	 * @return List<ProductSummaryVO>
	 */
	public List<ProductSummaryVO> getPrdDtlsList() {
		if (selectedCategory != null && !"".equals(selectedCategory))
			return getProductsByCategory(selectedCategory);
		else
			return getAllProductList();
	}

	@Override
	public void addProduct(String category, ProductSummaryVO product) {
		if(product.getBaseRecurringPrice() > 0.0 
				&& !SkipOtherCategories.contains(category)){
			if(ServiceCategories.contains(category)){
				category = SERVICE_BUNDLES;
			}else if(OtherCategories.contains(category)){
				category = OTHER_SERVICES;
			}
			super.addProduct(category, product);
		}
	}
	

	/**
	 * Returns Products associated to the given category. Cable Products are
	 * sorted using specified sort order
	 * 
	 * @return List<ProductSummaryVO>
	 */
	private List<ProductSummaryVO> getCableProducts() {
		List<ProductSummaryVO> prdDtlsList = new ArrayList<ProductSummaryVO>();
		List<ProductSummaryVO> cableAndSatellite = categoryMap.get(VIDEO);
		for (ProductSummaryVO prdDtlsVO : cableAndSatellite) {
			if (!prdDtlsVO.getCapabilityMap().containsKey(SATELLITE_CAPABILITY)) {
				prdDtlsList.add(prdDtlsVO);
			}
		}
		String category = VIDEO;
		String currentSortOrder = categorySortOrderMap.get(category);
		if (categorySortOrderMap.containsKey(category)) {
			if (!sortOrder.equals(currentSortOrder)) {
				sortProducts(prdDtlsList, sortOrder);
				categorySortOrderMap.put(category, sortOrder);
			}
		} else {
			sortProducts(prdDtlsList, sortOrder);
			categorySortOrderMap.put(category, sortOrder);
		}
		return prdDtlsList;
	}

	/**
	 * Returns Products associated to the given category. Satellite Products are
	 * sorted using specified sort order
	 * 
	 * @return List<ProductSummaryVO>
	 */
	private List<ProductSummaryVO> getSatelliteProducts() {
		List<ProductSummaryVO> prdDtlsList = new ArrayList<ProductSummaryVO>();
		List<ProductSummaryVO> cableAndSatellite = categoryMap.get(VIDEO);
		for (ProductSummaryVO prdDtlsVO : cableAndSatellite) {
			if (prdDtlsVO.getCapabilityMap().containsKey(SATELLITE_CAPABILITY)) {
				prdDtlsList.add(prdDtlsVO);
			}
		}
		String category = VIDEO;
		String currentSortOrder = categorySortOrderMap.get(category);
		if (categorySortOrderMap.containsKey(category)) {
			if (!sortOrder.equals(currentSortOrder)) {
				sortProducts(prdDtlsList, sortOrder);
				categorySortOrderMap.put(category, sortOrder);
			}
		} else {
			sortProducts(prdDtlsList, sortOrder);
			categorySortOrderMap.put(category, sortOrder);
		}
		return prdDtlsList;
	}

	/**
	 * @return the selectedCategory
	 */
	public String getSelectedCategory() {
		return selectedCategory;
	}

	/**
	 * @param selectedCategory
	 *            the selectedCategory to set
	 */
	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the percentage
	 */
	public Integer getPercentage() {
		Integer percentage;
		if (isPolling()) {
			Integer totalCount = getProviderCount();
			logger.info("Total_Provider_Count" + totalCount);
			Integer pendingCount = 0;
			for (PollingStatus entry : getProviderStatus().values()) {
				if (entry.isPending()) {
					pendingCount++;
				}
			}
			Integer count = (totalCount - pendingCount);
			percentage = Double.valueOf(
					(count.doubleValue() / totalCount) * 100).intValue();
			logger.info("PendingCount=" + pendingCount);
		} else {
			percentage = 100;
		}
		logger.info("Percentage(%)=" + percentage);
		return percentage;
	}
}
