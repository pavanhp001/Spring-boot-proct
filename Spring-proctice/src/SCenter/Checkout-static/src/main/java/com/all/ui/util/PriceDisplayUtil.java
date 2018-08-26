package com.AL.ui.util;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.ajax.JSON;
import org.json.JSONException;
import org.json.JSONObject;

import com.AL.html.ObjectFactory;
import com.AL.html.Option;
import com.AL.html.Select;
import com.AL.ui.vo.PriceDisplayVO;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.PriceTierType;
import com.AL.xml.pr.v4.ProductPromotionType;

public enum PriceDisplayUtil {
	
	INSTANCE;
	private static final Logger logger = Logger.getLogger(PriceDisplayUtil.class);
	private static final String DEFAULT_CSS_CLASS = "styled";

	private static final ObjectFactory oFactory = new ObjectFactory();
	/**
	 * generates a map with externalID and List<PriceDisplayVO>
	 * @param finalFeaturesList
	 * @param promotions 
	 * @param object 
	 * @return
	 */
	public Map<String, List<PriceDisplayVO>> buildPriceDetails(
			List<FeatureType> finalFeaturesList, List<FeatureGroupType> finalFeatureGroupList, List<ProductPromotionType> promotions) {
		Map<String, List<PriceDisplayVO>> priceDisplayMap = new HashMap<String, List<PriceDisplayVO>>();
		
		List<PriceDisplayVO> priceDisplayVOList = new ArrayList<PriceDisplayVO>();
		PriceDisplayVO priceDisplayVO = new PriceDisplayVO();
		DecimalFormat format = new DecimalFormat("#0.00");
		if(finalFeaturesList != null){
			for(FeatureType featureType : finalFeaturesList){
				priceDisplayVOList = new ArrayList<PriceDisplayVO>();
				if (featureType.getDataConstraints().getIntegerConstraint() != null ) {
					if(featureType.getIncluded() == null && featureType.isAvailable() == true){
						if(featureType.getDataConstraints().getIntegerConstraint().getMinValue() != null || 
								featureType.getDataConstraints().getIntegerConstraint().getMaxValue() != null){
							
							BigInteger minValue = featureType.getDataConstraints().getIntegerConstraint().getMinValue();
							BigInteger maxValue = featureType.getDataConstraints().getIntegerConstraint().getMaxValue();
							
							Map<Integer, Double> priceTireMap = new TreeMap<Integer, Double>();
							Map<Integer, Double> baseNonPriceTireMap = new TreeMap<Integer, Double>();
							Map<Integer, String> priceTireMapDisplay = new TreeMap<Integer, String>();
							Map<Integer, Double> priceFinalTireMap = new TreeMap<Integer, Double>();
							Map<Integer, Double> baseNonPriceFinalTireMap = new TreeMap<Integer, Double>();
							
							if(featureType.getPriceTierList() != null){
								
								for(PriceTierType priceTire : featureType.getPriceTierList().getPriceTier()){
									priceTireMap.put(priceTire.getRangeStart(), priceTire.getPrice().getBaseRecurringPrice());
									baseNonPriceTireMap.put(priceTire.getRangeStart(), priceTire.getPrice().getBaseNonRecurringPrice());
								}
								
								Double recPrice = priceTireMap.get(Collections.min(priceTireMap.keySet()));
								Double recPriceSum = priceTireMap.get(Collections.min(priceTireMap.keySet()));
								
								
								Double nonRecPrice = baseNonPriceTireMap.get(Collections.min(priceTireMap.keySet()));
								Double nonRecPriceSum = baseNonPriceTireMap.get(Collections.min(priceTireMap.keySet()));

								

								for(int i = minValue.intValue(); i <= maxValue.intValue(); i++){
									if(priceTireMap.get(i) != null && baseNonPriceTireMap.get(i) != null){
										recPrice = priceTireMap.get(i);
										recPriceSum = priceTireMap.get(i);

										nonRecPrice = baseNonPriceTireMap.get(i);
										nonRecPriceSum = baseNonPriceTireMap.get(i);

										double incRecPrice = 0;
										double incNonRecPrice = 0;
										if(i > 0 && priceFinalTireMap.get(i-1) != null && baseNonPriceFinalTireMap.get(i-1) != null){
											incRecPrice = priceFinalTireMap.get(i-1);
											incNonRecPrice = baseNonPriceFinalTireMap.get(i-1);
										}
										recPrice += incRecPrice;
										nonRecPrice += incNonRecPrice;
										priceFinalTireMap.put(i, recPrice);
										baseNonPriceFinalTireMap.put(i, nonRecPrice);
									}
									else{
										recPrice += recPriceSum;
										nonRecPrice += nonRecPriceSum;
										priceFinalTireMap.put(i, recPrice);
										baseNonPriceFinalTireMap.put(i, nonRecPrice);
									}
								}

								if(priceFinalTireMap.size() == baseNonPriceFinalTireMap.size()){
									int i = minValue.intValue();
									for(; i <= maxValue.intValue(); i++){
										priceDisplayVO = new PriceDisplayVO();
										priceDisplayVO.setIncludedFlag(false);
										/*
										 * set the externalID, description, Quantity, recurring price, non recurring price and customization indicator  of the feature to the priceDIsplayVO Object
										 */
										priceDisplayVO.setFeatureExternalID(featureType.getExternalId());
										
										priceDisplayVO.setDescription(featureType.getType());
										priceDisplayVO.setQuantity(String.valueOf(i));
										priceDisplayVO.setRecurringPrice(format.format(priceFinalTireMap.get(i)));
										if(baseNonPriceFinalTireMap.get(i) != 0.0){
											priceDisplayVO.setNonRecurringPrice(format.format(baseNonPriceFinalTireMap.get(i)));	
										}
										priceDisplayVO.setType("feature");
										priceDisplayVO.setDataConstraint("integer");
										priceDisplayVOList.add(priceDisplayVO);
									}
								}
								priceDisplayMap.put(featureType.getExternalId(), priceDisplayVOList);
							}
							else if(featureType.getPrice() != null){
								Double baseRecPrice = featureType.getPrice().getBaseRecurringPrice();
								Double baseRec = featureType.getPrice().getBaseRecurringPrice();

								Double baseNonRecurr = featureType.getPrice().getBaseNonRecurringPrice();
								Double baseNonRecurrSum = featureType.getPrice().getBaseNonRecurringPrice();
								int i = minValue.intValue();

								for(; i <= maxValue.intValue(); i++){
									String incrementedStr = "";
									priceTireMap.put(i, baseRecPrice);
									baseNonPriceTireMap.put(i, baseNonRecurr);
									if(i == 0){
										incrementedStr = "0.00";
										priceTireMapDisplay.put(i, incrementedStr);
									}
									else {
										if(baseNonRecurr != 0.00){
											incrementedStr = format.format(baseNonRecurr)+"/"+format.format(baseRecPrice);
											baseNonRecurr += baseNonRecurrSum;
										}
										else{
											incrementedStr = format.format(baseRecPrice);
										}
										priceTireMapDisplay.put(i, incrementedStr);
										baseRecPrice += baseRec;
									}
								}

								for(Entry<Integer, String> entry : priceTireMapDisplay.entrySet()){

									priceDisplayVO = new PriceDisplayVO();
									priceDisplayVO.setIncludedFlag(false);
									/*
									 * set the externalID, description, Quantity, recurring price, non recurring price and customization indicator  of the feature to the priceDIsplayVO Object
									 */
									priceDisplayVO.setFeatureExternalID(featureType.getExternalId());
									
									priceDisplayVO.setDescription(featureType.getType());
									
									priceDisplayVO.setQuantity(String.valueOf(entry.getKey()));
									if(entry.getValue().indexOf("/") > 0){
										String[] recPriceSplitValue = entry.getValue().split("/");
										priceDisplayVO.setRecurringPrice(recPriceSplitValue[1]);
										priceDisplayVO.setNonRecurringPrice(recPriceSplitValue[0]);
									}
									else{
										priceDisplayVO.setRecurringPrice(entry.getValue());
									}
									priceDisplayVO.setDataConstraint("integer");
									priceDisplayVO.setType("feature");
									priceDisplayVOList.add(priceDisplayVO);

								}
								priceDisplayMap.put(featureType.getExternalId(), priceDisplayVOList);
							}
						}
					}
				}
				else if(featureType.getDataConstraints().getBooleanConstraint() != null){
					priceDisplayVOList = new ArrayList<PriceDisplayVO>();
					priceDisplayVO = new PriceDisplayVO();
					priceDisplayVO.setIncludedFlag(false);
					priceDisplayVO.setFeatureExternalID(featureType.getExternalId());
					
					priceDisplayVO.setDescription(featureType.getType());
					
					priceDisplayVO.setQuantity("Y");
					com.AL.xml.pr.v4.PriceInfoType price = featureType.getPrice();
					priceDisplayVO.setRecurringPrice(String.valueOf(price.getBaseRecurringPrice()));
					priceDisplayVO.setNonRecurringPrice(String.valueOf(price.getBaseNonRecurringPrice()));

					priceDisplayVO.setType("feature");
					priceDisplayVO.setDataConstraint("boolean");
					
					priceDisplayVOList.add(priceDisplayVO);
					
					priceDisplayMap.put(featureType.getExternalId(), priceDisplayVOList);
				}
			}
		}
		
		/*
		 * if we have a featureGroup list create a map with featureExternalID and ProductDisplayVO Objects
		 */
		else if(finalFeatureGroupList != null){
			/*
			 * Iterate over the list of feature groups
			 */
			for(FeatureGroupType featGroupType : finalFeatureGroupList){
				priceDisplayVOList = new ArrayList<PriceDisplayVO>();
				/* 
				 * Pick one type feature group is a select box type with options as the related features.
				 * The customer may select one of the feature from this list of features
				 */
				if(featGroupType.getSelectionType().getPickOne() != null){
					
					/*
					 * get all the features and create a price DisplayVO Object for every feature
					 */
					for(FeatureType features : featGroupType.getFeature()){
						priceDisplayVO = new PriceDisplayVO();
						priceDisplayVO.setFeatureGroupExternalID(featGroupType.getExternalId());
						/*
						 * checking whether the feature is included, then the included is set as true, else it is set as false.
						 */
						if(features.getIncluded() != null){
							priceDisplayVO.setIncludedFlag(true);
							if(features.getDataConstraints().getStringConstraint() != null && features.getDataConstraints().getStringConstraint().getValue() != null){
								priceDisplayVO.setQuantity(features.getDataConstraints().getStringConstraint().getValue());
							}
							priceDisplayVO.setDescription(features.getType());
							priceDisplayVO.setFeatureExternalID(features.getExternalId());
							priceDisplayVO.setRecurringPrice(format.format(features.getPrice().getBaseRecurringPrice()));
							priceDisplayVO.setNonRecurringPrice(format.format(features.getPrice().getBaseNonRecurringPrice()));
							priceDisplayVO.setDataConstraint("string");
							priceDisplayVO.setType("featureGroup");
							priceDisplayVOList.add(priceDisplayVO);
						}
						/*
						 * if the feature is an available feature, then the included is set as false
						 */
						else if(features.getIncluded() == null && features.isAvailable() == true){
							priceDisplayVO.setIncludedFlag(false);
							if(features.getDataConstraints().getStringConstraint() != null && features.getDataConstraints().getStringConstraint().getValue() != null){
								priceDisplayVO.setQuantity(features.getDataConstraints().getStringConstraint().getValue());
							}
							priceDisplayVO.setDescription(features.getType());
							priceDisplayVO.setFeatureExternalID(features.getExternalId());
							priceDisplayVO.setRecurringPrice(format.format(features.getPrice().getBaseRecurringPrice()));
							priceDisplayVO.setNonRecurringPrice(format.format(features.getPrice().getBaseNonRecurringPrice()));
							priceDisplayVO.setDataConstraint("string");
							priceDisplayVO.setType("featureGroup");
							priceDisplayVOList.add(priceDisplayVO);
							
						}
					}
					priceDisplayMap.put(featGroupType.getExternalId(), priceDisplayVOList);
				}
			}
		}
		
		if(promotions != null && ! promotions.isEmpty()){
			
			for(ProductPromotionType productPromotionType : promotions){
				priceDisplayVO = new PriceDisplayVO();
				priceDisplayVOList = new ArrayList<PriceDisplayVO>();
				priceDisplayVO.setFeatureExternalID(productPromotionType.getExternalId());
				priceDisplayVO.setDescription(productPromotionType.getDescription());
				if(productPromotionType.getType().equals("baseMonthlyDiscount")){
					priceDisplayVO.setRecurringPrice(String.valueOf(productPromotionType.getPriceValue()));
				}
				else if(productPromotionType.getType().equals("oneTimeFeeDiscount")){
					priceDisplayVO.setNonRecurringPrice(String.valueOf(productPromotionType.getPriceValue()));
				}
				priceDisplayVO.setType("promotion");
				priceDisplayVO.setPriceValueType(productPromotionType.getPriceValueType());
				priceDisplayVOList.add(priceDisplayVO);
				priceDisplayMap.put(productPromotionType.getExternalId(), priceDisplayVOList);
			}
		}
		return priceDisplayMap;
	}
	
	
	/**
	 * creates a select box with id as the feature external ID and value as PriceDisplayVoJSON 
	 * @param feature
	 * @param priceDisplayVOMap
	 * @return
	 * @throws JSONException 
	 */
	public Select populateOptionsListForSelect(FeatureType feature, Map<String, List<PriceDisplayVO>> priceDisplayVOMap, FeatureGroupType featureGroup) throws JSONException{
		Select select = oFactory.createSelect();
		
		select.setClazz(DEFAULT_CSS_CLASS);
		if(feature != null){
			if(priceDisplayVOMap.get(feature.getExternalId()) != null){
				select.setId(feature.getExternalId());
				select.setName(feature.getExternalId());
				
				Option baseOpt = null;
				baseOpt = oFactory.createOption();
				baseOpt.setStyle("text-align: center;");
				baseOpt.setContent("Please Select");
				baseOpt.setValue("");
				select.getOptionOrOptgroup().add(baseOpt);
				List<PriceDisplayVO> priceDisplayVOList = priceDisplayVOMap.get(feature.getExternalId());
				for(PriceDisplayVO priceDisplayVO : priceDisplayVOList){
					Option option = oFactory.createOption();
					if(priceDisplayVO.getNonRecurringPrice() != null){
						option.setContent(priceDisplayVO.getQuantity()+"-$"+priceDisplayVO.getNonRecurringPrice()+"/$"+priceDisplayVO.getRecurringPrice());
					}
					else{
						option.setContent(priceDisplayVO.getQuantity()+"-$"+priceDisplayVO.getRecurringPrice());
					}
					JSONObject jsonValue = convertToJSON(priceDisplayVO);
					
					option.setValue(jsonValue.toString());
					select.getOptionOrOptgroup().add(option);
				}
			}
		}
		else if(featureGroup != null){
			select.setId(featureGroup.getExternalId());
			select.setName(featureGroup.getExternalId());
			
			Option baseOpt = null;
			baseOpt = oFactory.createOption();
			baseOpt.setStyle("text-align: center;");
			baseOpt.setContent("Please Select");
			baseOpt.setValue("");
			select.getOptionOrOptgroup().add(baseOpt);
			
			List<PriceDisplayVO> priceDisplayVOList = priceDisplayVOMap.get(featureGroup.getExternalId());
			for(PriceDisplayVO priceDisplayVO : priceDisplayVOList){
				Option option = oFactory.createOption();
				if(priceDisplayVO.getNonRecurringPrice() != null && !priceDisplayVO.getNonRecurringPrice().equals("0.0")){
					String priceQty = priceDisplayVO.getQuantity();
					if(priceQty == null || priceQty.trim().length() == 0){
						priceQty = priceDisplayVO.getDescription();
					}
					
					if(priceDisplayVO.isIncludedFlag()){
						option.setContent(priceQty+"- INCLUDED");
					}
					else{
						option.setContent(priceQty+"-$"+priceDisplayVO.getNonRecurringPrice()+
								"/$"+priceDisplayVO.getRecurringPrice());	
					}
				}
				else{
					String priceQty = priceDisplayVO.getQuantity();
					if(priceQty == null || priceQty.trim().length() == 0){
						priceQty = priceDisplayVO.getDescription();
					}
					option.setContent(priceQty+"-$"+priceDisplayVO.getRecurringPrice());
				}
				JSONObject jsonValue = convertToJSON(priceDisplayVO);
				
				option.setValue(jsonValue.toString());
				select.getOptionOrOptgroup().add(option);
			}
		}
		return select;
	}
	
	/**
	 * converts the PriceDisplayVO Object to JSONObject
	 * @param priveDisplayVO
	 * @return
	 * @throws JSONException
	 */
	public JSONObject convertToJSON(PriceDisplayVO priveDisplayVO) throws JSONException{
		JSONObject priceDisplayVOJSON = new JSONObject();
		
		if(priveDisplayVO.getFeatureGroupExternalID() != null){
			priceDisplayVOJSON.put("featureGroupExternalID", priveDisplayVO.getFeatureGroupExternalID());
		}
		priceDisplayVOJSON.put("featureExternalID", priveDisplayVO.getFeatureExternalID());
		priceDisplayVOJSON.put("description", priveDisplayVO.getDescription());
		priceDisplayVOJSON.put("quantity", priveDisplayVO.getQuantity());
		priceDisplayVOJSON.put("recuringPrice", priveDisplayVO.getRecurringPrice());
		priceDisplayVOJSON.put("nonRecuringPrice", priveDisplayVO.getNonRecurringPrice());
		priceDisplayVOJSON.put("type", priveDisplayVO.getType());
		priceDisplayVOJSON.put("dataConstraint", priveDisplayVO.getDataConstraint());
		priceDisplayVOJSON.put("includedFlag", priveDisplayVO.isIncludedFlag());
		if(priveDisplayVO.getPriceValueType() != null){
			priceDisplayVOJSON.put("priceValueType", priveDisplayVO.getPriceValueType());
		}
		return priceDisplayVOJSON;
	}
}
