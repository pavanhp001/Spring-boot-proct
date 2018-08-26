package com.AL.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.constants.Constants;
import com.AL.ui.util.Utils;

import edu.emory.mathcs.backport.java.util.Collections;

@Service
public class SalesServiceImpl {

	private static final Logger logger = Logger.getLogger(SalesServiceImpl.class);




	/** This method used to filtering product list based on filter sections
	 * @param request
	 * @param productResultManager
	 * @param details
	 * @param isLatinoExclude
	 * @return
	 */
	public List<ProductSummaryVO> filterProductList(HttpServletRequest request,final ProductResultsManager productResultManager,List<ProductSummaryVO>  details){

		List<ProductSummaryVO> filteringDetails = new ArrayList<ProductSummaryVO>();
		String latino = request.getParameter("latino");
		if (Utils.isBlank(request.getParameter("contractTerm")) && Utils.isBlank(request.getParameter("channels"))
				&& Utils.isBlank(request.getParameter("internetSpeed")))
		{
			filteringDetails.addAll(details);
		}else {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("contractTerm", request.getParameter("contractTerm"));
			paramMap.put("channels", request.getParameter("channels"));
			paramMap.put("internetSpeed", request.getParameter("internetSpeed"));
			for (ProductSummaryVO vo : details){
				if (vo.getPromotionMetaDataList() != null && vo.getPromotionMetaDataList().size() > 0)
				{
					if (productResultManager.ifFilterValidationPassed(vo.getPromotionMetaDataList(),paramMap,false))
					{
						filteringDetails.add(vo);
					}
				}
			}
		}

		if (!Utils.isBlank(request.getParameter("provider"))){
			if (filteringDetails.size() > 0) {
				List<ProductSummaryVO> filteringDetailsNew = new ArrayList<ProductSummaryVO>();
				for (ProductSummaryVO vo : filteringDetails){
					String[] providerBuf = request.getParameter("provider").split(",");
					for(int i=0; i<providerBuf.length; i++)
					{
						if (vo.getProviderExternalId().equals(providerBuf[i]))
						{
							filteringDetailsNew.add(vo);
							break;
						}
					}
				}
				filteringDetails = filteringDetailsNew;
			}
		}
		if (filteringDetails.size() > 0 &&!Utils.isBlank((latino)) 
				&& (Constants.LATINO_YES.equalsIgnoreCase(latino) || Constants.LATINO_NO.equalsIgnoreCase(latino))){
				List<ProductSummaryVO> filteringDetailsNew = new ArrayList<ProductSummaryVO>();
				for (ProductSummaryVO vo : filteringDetails){ 
					if (vo.getPromotionMetaDataList() != null && vo.getPromotionMetaDataList().size() > 0) {
						if(Constants.LATINO_YES.equalsIgnoreCase(latino) 
								&& productResultManager.ifLatinoNewFilter(vo.getPromotionMetaDataList())){
							filteringDetailsNew.add(vo);
						}else if(Constants.LATINO_NO.equalsIgnoreCase(latino)
								&& !productResultManager.ifLatinoNewFilter(vo.getPromotionMetaDataList())){
							filteringDetailsNew.add(vo);
						}
					}
				}
					filteringDetails = new ArrayList<ProductSummaryVO>();
					filteringDetails.addAll(filteringDetailsNew);
		}		
		return filteringDetails;
	}







	/** This method used to sorting product list based on sorting section
	 * @param sortOption
	 * @param productVoList
	 * @param productResultManager
	 */
	public void sortOfferBasedOnOption(String sortOption,List<ProductSummaryVO>  productVoList, final ProductResultsManager productResultManager){

		if(Constants.PRICE_LOW_HIGH.equalsIgnoreCase(sortOption)){
			Collections.sort(productVoList,new Comparator<ProductSummaryVO>(){
				public int compare(ProductSummaryVO productVO1, ProductSummaryVO productVO2) {
					Double sprtPrice1 = getProductSortPrice(productVO1,productResultManager);
					Double sprtPrice2 = getProductSortPrice(productVO2,productResultManager);
					return sprtPrice1.compareTo(sprtPrice2);
				}
			});
		}else if(Constants.PRICE_HIGH_LOW.equalsIgnoreCase(sortOption)){
			Collections.sort(productVoList,new Comparator<ProductSummaryVO>(){
				public int compare(ProductSummaryVO productVO1, ProductSummaryVO productVO2) {
					Double sprtPrice1 = getProductSortPrice(productVO1,productResultManager);
					Double sprtPrice2 = getProductSortPrice(productVO2,productResultManager);
					return sprtPrice2.compareTo(sprtPrice1);
				}
			});
			for(ProductSummaryVO vo :productVoList){
				logger.info("price"+getProductSortPrice(vo,productResultManager));
			}
		}else if(Constants.INTERNET_LOW_HIGH.equalsIgnoreCase(sortOption)){
			Collections.sort(productVoList,new Comparator<ProductSummaryVO>(){
				public int compare(ProductSummaryVO productVO1, ProductSummaryVO productVO2) {
					Float metaValue1 = getProductMetaDataBasedOnValue(productVO1,Constants.CONN_SPEED);
					Float metaValue2 = getProductMetaDataBasedOnValue(productVO2,Constants.CONN_SPEED);
					return metaValue1.compareTo(metaValue2);
				}
			});
		}else if(Constants.INTERNET_HIGH_LOW.equalsIgnoreCase(sortOption)){
			Collections.sort(productVoList,new Comparator<ProductSummaryVO>(){
				public int compare(ProductSummaryVO productVO1, ProductSummaryVO productVO2) {
					Float metaValue1 = getProductMetaDataBasedOnValue(productVO1,Constants.CONN_SPEED);
					Float metaValue2 = getProductMetaDataBasedOnValue(productVO2,Constants.CONN_SPEED);
					return metaValue2.compareTo(metaValue1);  
				}
			});
		}else if(Constants.CHANNELS_HIGH_LOW.equalsIgnoreCase(sortOption)){
			Collections.sort(productVoList,new Comparator<ProductSummaryVO>(){
				public int compare(ProductSummaryVO productVO1, ProductSummaryVO productVO2) {
					Float metaValue1 = getProductMetaDataBasedOnValue(productVO1,Constants.NUM_CHANNELS);
					Float metaValue2 = getProductMetaDataBasedOnValue(productVO2,Constants.NUM_CHANNELS);
					return metaValue2.compareTo(metaValue1);    
				}
			});
		}else if(Constants.CHANNELS_LOW_HIGH.equalsIgnoreCase(sortOption)){
			Collections.sort(productVoList,new Comparator<ProductSummaryVO>(){
				public int compare(ProductSummaryVO productVO1, ProductSummaryVO productVO2) {
					Float metaValue1 = getProductMetaDataBasedOnValue(productVO1,Constants.NUM_CHANNELS);
					Float metaValue2 = getProductMetaDataBasedOnValue(productVO2,Constants.NUM_CHANNELS);
					return metaValue1.compareTo(metaValue2);  
				}
			});
		}else if(Constants.PLAN_A_Z.equalsIgnoreCase(sortOption)){
			Collections.sort(productVoList,new Comparator<ProductSummaryVO>(){
				public int compare(ProductSummaryVO productVO1, ProductSummaryVO productVO2) {
					if(!Utils.isBlank(productVO1.getName())  && !Utils.isBlank(productVO2.getName())){
						return StringEscapeUtils.unescapeHtml(Jsoup.parse(productVO1.getName(), "UTF-8").text()).compareTo(StringEscapeUtils.unescapeHtml(Jsoup.parse(productVO2.getName(), "UTF-8").text()));  		
					}else{
						return -1;  	
					}
				}
			});
		}else if(Constants.PLAN_Z_A.equalsIgnoreCase(sortOption)){
			Collections.sort(productVoList,new Comparator<ProductSummaryVO>(){
				public int compare(ProductSummaryVO productVO1, ProductSummaryVO productVO2) {
					if(!Utils.isBlank(productVO1.getName())  && !Utils.isBlank(productVO2.getName())){
						return StringEscapeUtils.unescapeHtml(Jsoup.parse(productVO2.getName(), "UTF-8").text()).compareTo(StringEscapeUtils.unescapeHtml(Jsoup.parse(productVO1.getName(), "UTF-8").text()));  		
					}else{
						return -1;  	
					}
				}
			});
		}else if(Constants.Recommended.equalsIgnoreCase(sortOption)){
			Collections.sort(productVoList,new Comparator<ProductSummaryVO>(){
				public int compare(ProductSummaryVO productVO1, ProductSummaryVO productVO2) {
					Double score1 = productVO1.getScore();
					Double score2 = productVO2.getScore();
					if( score1 != null && score2 != null){  
						return score2.compareTo(score1);
					}else{ 
						return -1;  
					}  
				}
			});
		}
		logger.info("sortOffer_results_found_for="+sortOption);
	}


	/**
	 * @param summaryVO
	 * @param meataKey
	 * @return
	 */
	public Float getProductMetaDataBasedOnValue(ProductSummaryVO summaryVO, String meataKey){
		if (summaryVO.getPromotionMetaDataList() != null && summaryVO.getPromotionMetaDataList().size() > 0){
			for(String metaData :summaryVO.getPromotionMetaDataList()){	
				if(!Utils.isBlank(metaData) && metaData.contains("=")){
					String[] str1 = metaData.split("=");
					if (meataKey.equalsIgnoreCase(str1[0]) && isNumeric(str1[1])) {
						return Float.valueOf(str1[1]);
					}
				}
			}
		}
		return 0f;
	}

	/**
	 * @param summaryVO
	 * @param productResultManager
	 * @return
	 */
	public Double getProductSortPrice(ProductSummaryVO summaryVO,  ProductResultsManager productResultManager){  
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
					if(productResultManager.getSelectedExistingProvidersMap().containsValue(summaryVO.getProviderExternalId())){
						sortPrice = summaryVO.getBaseRecurringPrice();
					}					
				}
			}
		}
		return sortPrice;
	}

	public static boolean isNumeric(String str) {
		return str.matches("^-?[0-9]+(\\.[0-9]+)?$");
	}
}
