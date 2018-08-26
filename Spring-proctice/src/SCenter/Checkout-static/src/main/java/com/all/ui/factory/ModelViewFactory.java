package com.AL.ui.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.AL.html.Fieldset;
import com.AL.html.ObjectFactory;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.PriceDisplayVO;
import com.AL.xml.pr.v4.FeatureDependencyListType;
import com.AL.xml.pr.v4.FeatureDependencyType;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.pr.v4.ProductPromotionType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;


public enum ModelViewFactory {

	INSTANCE;

	public static final Logger logger = Logger.getLogger(ModelViewFactory.class); 

	/**
	 * check whether the product contains promotions and features creates view
	 *  
	 * @param features
	 * @param featureGroup
	 * @param previousCKOSelFeatureList
	 * @param previouslySelectedFeatureGroups
	 * @param mav
	 * @param orderQualVO
	 * @param request
	 * @param promotions
	 * @param priceDisplayVOMap 
	 * @return
	 * @throws Exception
	 */
	public ModelAndView createProductInfoView(List<FeatureType> features, List<FeatureGroupType> featureGroup, 
			List<String> previousCKOSelFeatureList,List<FeatureGroup> previouslySelectedFeatureGroups, ModelAndView mav, 
			OrderQualVO orderQualVO, HttpServletRequest request, 
			List<ProductPromotionType> promotions) throws Exception {

		final ObjectFactory oFactory = new ObjectFactory();
		Map<String, List<PriceDisplayVO>> priceDisplayVOMap = orderQualVO.getPriceDisplayVOMap();
		
		/*
		 * creating fieldsets to display promotions and features
		 */
		Fieldset fieldset = oFactory.createFieldset();
		Fieldset promotionFieldset = oFactory.createFieldset();

		logger.info("Building the page using HTMLFactory");
		
		/*
		 * check whether we came to this page for the first time or by pressing back in the customer
		 * qualification page
		 * if we came to this page by pressing back in the customer qualification page,
		 * we show all the features, feature groups and promotions that are previously selected
		 */
		String prevSelectedFeatures = null;
		if(request.getParameter("selectedFeaturesJSON") != null ){
			prevSelectedFeatures = request.getParameter("selectedFeaturesJSON");	
		}
		
		/*
		 * sending features, featuregroups, previous CKO selected values and build features and feature group display
		 * sending promotion related details and build the promotion display
		 */
		if((features != null && ! features.isEmpty())|| (featureGroup != null && !featureGroup.isEmpty()) || (promotions != null && !promotions.isEmpty())){
			
			/*
			 * fieldset with the feature and feature group related display data
			 */
			fieldset = HtmlFactory.INSTANCE.getFeatureFieldSet(features, featureGroup, prevSelectedFeatures, request, 
					previousCKOSelFeatureList, previouslySelectedFeatureGroups, priceDisplayVOMap);
			
			/*
			 * fieldset with promotion related display data
			 */
			promotionFieldset = HtmlFactory.INSTANCE.getPromotionsFieldSet(promotions, orderQualVO.getProductDetailType(), orderQualVO.getProviderBaseType(), 
					orderQualVO.getPromotionStatusJSON(), request, priceDisplayVOMap);
			
			String element = HtmlBuilder.INSTANCE.toString(fieldset);
			String element1 = HtmlBuilder.INSTANCE.toString(promotionFieldset);
			
			/*
			 * removing special characters
			 */
			mav.addObject("viewObjMarketHighlt", escapeSpecialCharacters(element));
			if(promotions != null){
				mav.addObject("promotions", escapeSpecialCharacters(element1));	
			}
			else{
				mav.addObject("promotions", "");
			}
			
			/*
			 * checking whether this is the first time coming to this page or coming from 
			 * customer qualification page
			 */
			String from = request.getParameter("from");
			
			mav.addObject("parentExternalID", orderQualVO.getParentExternalId());
			mav.addObject("startingPoint", "");
			if(from !=null && from.equalsIgnoreCase("oqDemoContent")){
				mav.addObject("featureMonthlyPrice", request.getParameter("featureMonthlyPrice"));
				String oqSelectedFeaturesHIDDENValue = request.getParameter("oqSelectedFeaturesHIDDENValue");
				mav.addObject("oqSelectedFeaturesHIDDENValue", oqSelectedFeaturesHIDDENValue);
				mav.addObject("featureOneTimePrice", "0");
				mav.addObject("startingPoint", "oqDemo");
			}
		}
		
		return mav;
	}
	
	
	public String escapeSpecialCharacters(String str){
		if(str!=null){
			
			str = str.replaceAll("&amp;", "&");
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("&quot;", "&#34;");
			
			str = str.replaceAll("&#10;", "&nbsp;");
			str = str.replaceAll("\u00a0", "&nbsp;");
			//this is for - mark
			str = str.replaceAll("\u2013", "&#8211;");
			//this is for trademark
			str = str.replaceAll("\u2122", "&#8482;");
			
			//this is for Copyright mark
			str = str.replaceAll("\u00a9", "&#169;");
			//this is for Registered trade mark
			str = str.replaceAll("\u00ae", "&#174;");
			
			//this is for bullet point
			str = str.replaceAll("\u2022", "&#8226;");
			//this is for exclamation point
			str = str.replaceAll("\u0021", "&#33;");
			//this is for colon
			str = str.replaceAll("\u003a", "&#58;");
			//this is for inverted question mark
			str = str.replaceAll("\u00bf", "&#191;");
			
			//this is for right single quotation mark
			str = str.replaceAll("\u2019", "&#8217;");
			//this is for left single quotation mark
			str = str.replaceAll("\u2018", "&#8216;");
			//this is for left double quotation mark
			str = str.replaceAll("\u201C", "&#8220;");
			//this is for right double quotation mark
			str = str.replaceAll("\u201D", "&#8221;");
			
			
		}
		return str;
	}

	@SuppressWarnings("null")
	public ModelAndView createProductInfoView(ProductInfoType productInfo, OrderQualVO orderQualVO) {

		ModelAndView mav = new ModelAndView("product_info");

		List<FeatureType> features = productInfo.getProductDetails().getFeature();

		Map<String, List<String>> dependencyExcludeMap = new HashMap<String, List<String>>();
		Map<String, List<String>> dependencyRequiresMap = new HashMap<String, List<String>>();
		Map<String, FeatureType> availFeatureMap = new HashMap<String, FeatureType>();

		for (FeatureType fr : features) {
			if (fr.getDependencies() != null) {
				FeatureDependencyListType deps = fr.getDependencies();
				for (FeatureDependencyType fdt : deps.getDependency()) {
					if (fdt.getDependency() != null
							&& fdt.getDependency().equalsIgnoreCase("excludes")) {
						dependencyExcludeMap.put(fr.getExternalId(), fdt.getDependencyExternalId());
					} else if (fdt.getDependency() != null
							&& fdt.getDependency().equalsIgnoreCase("requires")) {
						dependencyRequiresMap.put(fr.getExternalId(), fdt.getDependencyExternalId());
					}
				}
			}
			availFeatureMap.put(fr.getExternalId(), fr);
		}
		List<Fieldset> fieldsetList = null;
		//HtmlFactory.INSTANCE.getFeatureFieldSet(features);
		StringBuilder events = new StringBuilder();
		for (Fieldset fieldset : fieldsetList) {
			String element = HtmlBuilder.INSTANCE.toString(fieldset);
			events.append(element);
		}
		orderQualVO.setAvailFeatureMap(availFeatureMap);
		mav.addObject("features", events.toString());
		mav.addObject("productInfo", productInfo);
		mav.setViewName("product_info");
		return mav;
	}
}
