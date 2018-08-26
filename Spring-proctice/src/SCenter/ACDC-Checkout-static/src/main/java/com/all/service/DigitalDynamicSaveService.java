package com.AL.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.AL.controller.DynamicSaveController;
import com.AL.ui.ProductVO;
import com.AL.ui.builder.LineItemBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.DigitalCacheKeys;
import com.AL.ui.factory.DialogueDataFeildFactory;
import com.AL.ui.factory.LineItemSelectionFactory;
import com.AL.ui.factory.PromotionHandlerFactory;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderServiceUI;
import com.AL.ui.service.V.impl.StaticCKOCacheManager;
import com.AL.ui.util.OrderUtil;
import com.AL.ui.vo.DataFeildFeatureVO;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.PromotionVO;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderLineItemDetailTypeType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SelectedDialogsType;
import com.AL.xml.v4.SelectedFeaturesType;



@Service
public class DigitalDynamicSaveService {

	private static final Logger logger = Logger.getLogger(DynamicSaveController.class);

	/**
	 * @param request
	 * @param orderQualVO
	 */
	public void excuteDynamicSaveService(HttpServletRequest request,OrderQualVO orderQualVO) throws Exception {
		try{
			Map<String,PromotionVO> promotionVOMap = new HashMap<String,PromotionVO>();
			//SessionVO sessionVO = CKOCacheService.INSTANCE.get(request.getSession().getId());
			ProductVO productVO = (ProductVO)StaticCKOCacheManager.INSTANCE.getObjectFromCache(DigitalCacheKeys.ProductVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
			for(PromotionVO promotionVO:productVO.getPromotionVOList()){
				promotionVOMap.put(promotionVO.getPromotionObj().getExternalId(), promotionVO);
			}
			Map<String,DataFeildFeatureVO> dataFeildMap =  DialogueDataFeildFactory.INSTANCE.buildDataFeildFeatureVOMap(productVO.getDialogueTypeList());
			LineItemType  itemType = orderQualVO.getLineItemType();
			String productJSONArray  = request.getParameter("productVOJSON");
			JSONArray jsonArray = new JSONArray(productJSONArray);
			SalesContextType context = SalesContextFactory.INSTANCE.getContextFromSession(request.getSession());
			ObjectFactory oFactory = new ObjectFactory();
			LineItemAttributeType lineItemAttribute = oFactory.createLineItemAttributeType();
			SelectedFeaturesType featureType = oFactory.createSelectedFeaturesType();
			SelectedFeaturesType.Features selFeatures = oFactory.createSelectedFeaturesTypeFeatures();

			//active dialog
			SelectedDialogsType selectedDialogueType = oFactory.createSelectedDialogsType();
			SelectedDialogsType.Dialogs selectedDialogueTypeDialogue = oFactory.createSelectedDialogsTypeDialogs();

			/*
			 * checking whether the order is already present in the cache, if present, we return the order
			 * if not present, we do a get order call
			 */

			OrderType order = OrderUtil.INSTANCE.returnOrderType(orderQualVO.getOrderId());
			Map<String,Long>  lineItemTypePromotionMap = PromotionHandlerFactory.INSTANCE.getLineItemTypePromotionMap(order,orderQualVO);
			List<String> cancelPromationLineItemIdList = new ArrayList<String>();
			List<String> enterDataFeildList = new ArrayList<String>();
			for (int i = 0, size = jsonArray.length(); i < size; i++)
			{
				JSONObject objectInArray = jsonArray.getJSONObject(i);
				if(objectInArray.has(Constants.DISPLAY_TYPE)){
					if(Constants.PROMOTION.equalsIgnoreCase(objectInArray.getString(Constants.DISPLAY_TYPE))){
						if("on".equalsIgnoreCase(objectInArray.getString(Constants.VALUE))){
							PromotionVO promotionVO = promotionVOMap.get(objectInArray.getString(Constants.NAME));
							promotionVO.setChecked(true);
							if(lineItemTypePromotionMap.get(promotionVO.getPromotionObj().getExternalId()) == null){
								LineItemCollectionType lineItemCollectionType = PromotionHandlerFactory.INSTANCE.buildPromotionLineItemCollectionType(promotionVO,orderQualVO);
								LineItemService.INSTANCE.addLineItem(orderQualVO.getAgentId(), orderQualVO.getOrderId(), lineItemCollectionType, context, false);
							}else{
								lineItemTypePromotionMap.remove(promotionVO.getPromotionObj().getExternalId());
							}
						}
					}else if(Constants.DIALOGUE.equalsIgnoreCase(objectInArray.getString(Constants.DISPLAY_TYPE))){
						DataFeildFeatureVO dataFeildFeatureVO = dataFeildMap.get(objectInArray.getString(Constants.NAME));
						if(dataFeildFeatureVO != null){
							selectedDialogueTypeDialogue.getDialog().add(LineItemSelectionFactory.INSTANCE.buildActiveDialogueType(dataFeildFeatureVO,oFactory,objectInArray));
							enterDataFeildList.add(objectInArray.getString(Constants.NAME));
						}
					}else if(Constants.FEATURE.equalsIgnoreCase(objectInArray.getString(Constants.DISPLAY_TYPE))){
						DataFeildFeatureVO dataFeildFeatureVO = dataFeildMap.get(objectInArray.getString(Constants.NAME));
						if(dataFeildFeatureVO != null ){
							FeatureValueType featureValueType = LineItemSelectionFactory.INSTANCE.buildFeatureValueType(dataFeildFeatureVO,objectInArray.getString(Constants.VALUE),oFactory);
							selFeatures.getFeatureValue().add(featureValueType);
							featureType.setFeatures(selFeatures);
							enterDataFeildList.add(objectInArray.getString(Constants.NAME));
						}
					} else if(Constants.FEATURE_GROUP_TYPE.equalsIgnoreCase(objectInArray.getString(Constants.DISPLAY_TYPE))){
						DataFeildFeatureVO dataFeildFeatureGroupVO = dataFeildMap.get(objectInArray.getString(Constants.NAME));
						if(dataFeildFeatureGroupVO != null){
							featureType.getFeatureGroup().add(LineItemSelectionFactory.INSTANCE.selctedFeatureGroup(dataFeildFeatureGroupVO,objectInArray.getString(Constants.VALUE),oFactory));
							enterDataFeildList.add(objectInArray.getString(Constants.NAME));
						}
					}
				}
			}
			for(Entry<String, DataFeildFeatureVO> entryVO:dataFeildMap.entrySet()){
				if(!enterDataFeildList.contains(entryVO.getKey())){
					DataFeildFeatureVO dataFeildFeatureVO = dataFeildMap.get(entryVO.getKey());
					dataFeildFeatureVO.setEnteredValue("");
				}
			}
			for(Entry<String, Long> cancelPromotionEntry:lineItemTypePromotionMap.entrySet()){
				if(promotionVOMap.get(cancelPromotionEntry.getKey()) != null){
					promotionVOMap.get(cancelPromotionEntry.getKey()).setChecked(true);
				}
				cancelPromationLineItemIdList.add(String.valueOf(cancelPromotionEntry.getValue()));
			}
			if(!cancelPromationLineItemIdList.isEmpty()){
				List<Integer> reasons = new ArrayList<Integer>(); 
				reasons.add(Constants.REASON_CODE);
				LineItemService.INSTANCE.updateLineItemStatus(orderQualVO.getAgentId(), orderQualVO.getOrderId(), cancelPromationLineItemIdList, 
						LineItemStatusCodesType.CANCELLED_REMOVED.value(), LineItemStatusCodesType.CANCELLED_REMOVED.value(), reasons, context,new ErrorList());	
			}
			selectedDialogueType.setDialogs(selectedDialogueTypeDialogue);
			itemType.setActiveDialogs(selectedDialogueType);
			itemType.setSelectedFeatures(featureType);
			Map<String,String> attributeMap = LineItemBuilder.INSTANCE.buildAttributeCollection("CKO","STATUS","CKO","CKO Ready");
			AttributeEntityType lineItemAttributeEntity = LineItemBuilder.INSTANCE.getLineItemAttributeSelction(attributeMap);
			lineItemAttribute.getEntity().add(lineItemAttributeEntity);
			itemType.setLineItemAttributes(lineItemAttribute);
			OrderServiceUI.INSTANCE.updateLineItem(orderQualVO,context,itemType);
		}catch (Exception e) {
			logger.error("error_occured_while_excute_DynamicSaveService",e);
			throw new Exception(e.getMessage());
		}
	}
}
