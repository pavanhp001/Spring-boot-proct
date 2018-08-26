/**
 * This bean class representing a Search Results form

 */
package com.A.productResults.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.A.productResults.vo.ProductSearchVO;
import com.A.productResults.vo.ProductSummaryVO;
import com.A.ui.service.V.impl.DigitalProductServiceDataCache;

/**
 * Bean representing a Search Results form.
 */
public class WebSearchResults extends ProductSearchVO {

	private static final Logger logger = Logger.getLogger(WebSearchResults.class);

	public WebSearchResults(String inputAddress, String channel,
			String dwellingType, String rentOrOwn, Map<String, String> partnerSpecificDataMap, boolean isPartnerSpecificMatchReqForUtility, String operatingCompany) {
		super(inputAddress, channel, dwellingType, rentOrOwn,partnerSpecificDataMap,isPartnerSpecificMatchReqForUtility, operatingCompany);
	}

	private ProductSummaryVO getproductSummaryVoListFromCache(String productExtID) {
		ProductSummaryVO productVo = (ProductSummaryVO)DigitalProductServiceDataCache.INSTANCE.get(productExtID);
		return productVo;
	}

	@Override
	public List<ProductSummaryVO> getAllProductList() {
		List<ProductSummaryVO> allProductList = new ArrayList<ProductSummaryVO>();
		for(String productExtID : getWebChannelProductExtIDList()){
			allProductList.add(getproductSummaryVoListFromCache(productExtID));
		}
		logger.info("allProductList_size="+allProductList.size());
		return allProductList;
	}

	@Override
	public void addProduct(String category, ProductSummaryVO product){

	}
}
