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

import com.AL.ui.QualificationVO;
import com.AL.ui.builder.LineItemBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.DigitalCacheKeys;
import com.AL.ui.factory.DialogueDataFeildFactory;
import com.AL.ui.factory.LineItemSelectionFactory;
import com.AL.ui.factory.PrepopulateDialogue;
import com.AL.ui.service.V.OrderServiceUI;
import com.AL.ui.service.V.impl.StaticCKOCacheManager;
import com.AL.ui.util.OrderUtil;
import com.AL.ui.vo.CustomerInfoVO;
import com.AL.ui.vo.DataFeildFeatureVO;
import com.AL.ui.vo.OrderQualVO;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SelectedDialogsType;

/**
 * @author Sravan Kumar Nalajala
 *
 */
@Service
public class UpdateDigtialDialogueService {

	private static final Logger logger = Logger.getLogger(UpdateDigtialDialogueService.class);

	/**
	 * @param request
	 * @param orderQualVO
	 */
	public void excuteUpdateDailog(HttpServletRequest request,OrderQualVO orderQualVO)throws Exception{

		try{

			QualificationVO qualificationVO = (QualificationVO)StaticCKOCacheManager.INSTANCE.getObjectFromCache(DigitalCacheKeys.QualificationVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
			String qualificationJSONArray  = request.getParameter("qualificationSerializeJSON");
			ObjectFactory oFactory = new ObjectFactory();
			CustomerInfoVO customerInfoVO = new CustomerInfoVO();
			SalesContextType context = SalesContextFactory.INSTANCE.getContextFromSession(request.getSession());
			Map<String,DataFeildFeatureVO> dataFeildMap =  DialogueDataFeildFactory.INSTANCE.buildDataFeildFeatureVOMap(qualificationVO.getDialogueTypeList());
			OrderType updatedOrder = OrderUtil.INSTANCE.returnOrderType(orderQualVO.getOrderId());
			LineItemType  itemType = orderQualVO.getLineItemType();
			LineItemType orderLineItem = LineItemBuilder.INSTANCE.returnLineItemByLineItemID(updatedOrder,orderQualVO.getLineItemExternalId());
			LineItemAttributeType lineItemAttribute = oFactory.createLineItemAttributeType();
			SelectedDialogsType selectedDialogueType = null;
			SelectedDialogsType.Dialogs selectedDialogueTypeDialogue = oFactory.createSelectedDialogsTypeDialogs();
			List<String> enterDataFeildList = new ArrayList<String>();
			if(orderLineItem.getActiveDialogs() != null){
				selectedDialogueType = orderLineItem.getActiveDialogs();
				SelectedDialogsType.Dialogs lineItemselectedDialogueTypeDialogue = orderLineItem.getActiveDialogs().getDialogs();
				if(lineItemselectedDialogueTypeDialogue != null ){
					selectedDialogueTypeDialogue = lineItemselectedDialogueTypeDialogue;
				}
			}else{
				selectedDialogueType = oFactory.createSelectedDialogsType();
			}

			JSONArray jsonArray = new JSONArray(qualificationJSONArray);
			for (int i = 0, size = jsonArray.length(); i < size; i++)
			{
				JSONObject objectInArray = jsonArray.getJSONObject(i);
				buildCustomerVO(customerInfoVO,objectInArray,dataFeildMap,enterDataFeildList);
				if(updatedOrder.getCustomerInformation().getCustomer().isPhoneContactOptIn()){
					customerInfoVO.setPhoneContactOptIn(updatedOrder.getCustomerInformation().getCustomer().isPhoneContactOptIn());
				}
				if(updatedOrder.getCustomerInformation().getCustomer().isMarketingOptIn()){
					customerInfoVO.setMarketingOptIn(updatedOrder.getCustomerInformation().getCustomer().isMarketingOptIn());
				}
				/*if(objectInArray.has(Constants.VALUE_TARGET) && objectInArray.getString(Constants.VALUE_TARGET).equalsIgnoreCase("consumer.contractAccountNumber")){
					customerInfoVO.setContractAccountNumber(objectInArray.getString(Constants.VALUE));
				}*/
				if(objectInArray.has(Constants.DISPLAY_TYPE)){
					if(Constants.DIALOGUE.equalsIgnoreCase(objectInArray.getString(Constants.DISPLAY_TYPE))){
						DataFeildFeatureVO dataFeildFeatureVO = dataFeildMap.get(objectInArray.getString(Constants.NAME));
						if(dataFeildFeatureVO != null){
							selectedDialogueTypeDialogue.getDialog().add(LineItemSelectionFactory.INSTANCE.buildActiveDialogueType(dataFeildFeatureVO,oFactory,objectInArray));
							enterDataFeildList.add(objectInArray.getString(Constants.NAME));
						}
					}
				}
			}
			if(!"Home".equalsIgnoreCase(customerInfoVO.getPhoneNumberType())){
				customerInfoVO.setWorkPhoneNumber(customerInfoVO.getHomePhoneNumber());
				customerInfoVO.setHomePhoneNumber(null);
			}
			for(Entry<String, DataFeildFeatureVO> entryVO:dataFeildMap.entrySet()){
				if(!enterDataFeildList.contains(entryVO.getKey())){
					DataFeildFeatureVO dataFeildFeatureVO = dataFeildMap.get(entryVO.getKey());
					dataFeildFeatureVO.setEnteredValue("");
				}
			}
			selectedDialogueType.setDialogs(selectedDialogueTypeDialogue);
			itemType.setActiveDialogs(selectedDialogueType);
			Map<String,String> attributeMap = LineItemBuilder.INSTANCE.buildAttributeCollection("CKO","STATUS",Constants.CKO_READY,Constants.CKO_READY);
			AttributeEntityType lineItemAttributeEntity = LineItemBuilder.INSTANCE.getLineItemAttributeSelction(attributeMap);
			lineItemAttribute.getEntity().add(lineItemAttributeEntity);
			itemType.setLineItemAttributes(lineItemAttribute);
			
			
			//updateLineItem
			updatedOrder = OrderServiceUI.INSTANCE.updateLineItem(orderQualVO,context,itemType);
			
			//updateCustomer
			PrepopulateDialogue.INSTANCE.updateCustomer(updatedOrder,orderQualVO,customerInfoVO,context, new HashMap<String, String>() );
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("error_occured_while_excute_UpdateDigtialDialogueService",e);
			throw new Exception(e.getMessage());
		}
	}

	/** build customerVO based form input values for update customer
	 * @param customerInfoVO
	 * @param objectInArray
	 * @throws Exception
	 */
	private void buildCustomerVO(CustomerInfoVO customerInfoVO,JSONObject objectInArray,Map<String,DataFeildFeatureVO> dataFeildMap,List<String> enterDataFeildList) throws Exception {
		boolean isCustomerInformation = false;
		if(objectInArray.has(Constants.VALUE)){
			if(objectInArray.has(Constants.VALUE_TARGET) && objectInArray.getString(Constants.VALUE_TARGET).equalsIgnoreCase("consumer.socialSecurityNumber"))
			{
				customerInfoVO.setSsn(objectInArray.getString(Constants.VALUE));
			}else if(objectInArray.has(Constants.VALUE_TARGET) && objectInArray.getString(Constants.VALUE_TARGET).equalsIgnoreCase("consumer.dateOfBirth"))
			{
				customerInfoVO.setDob(objectInArray.getString(Constants.VALUE));
			}else if(objectInArray.has(Constants.VALUE_TARGET) && objectInArray.getString(Constants.VALUE_TARGET).equalsIgnoreCase("consumer.identification.driverLicense.number"))
			{
				customerInfoVO.setDriverLicense(objectInArray.getString(Constants.VALUE));
			}else if(objectInArray.has(Constants.VALUE_TARGET) && objectInArray.getString(Constants.VALUE_TARGET).equalsIgnoreCase("consumer.identification.driverLicense.state"))
			{
				customerInfoVO.setState(objectInArray.getString(Constants.VALUE));
			}
			else if(objectInArray.has(Constants.VALUE_TARGET) && objectInArray.getString(Constants.VALUE_TARGET).equalsIgnoreCase("consumer.contractAccountNumber"))
			{
				customerInfoVO.setContractAccountNumber(objectInArray.getString(Constants.VALUE));
			}
			else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase(Constants.HOME_CONTACTNUMBER))
			{
				isCustomerInformation = true;
				customerInfoVO.setBtn(objectInArray.getString(Constants.VALUE));
				customerInfoVO.setHomePhoneNumber(objectInArray.getString(Constants.VALUE));
			}
			else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase(Constants.FIRST_NAME))
			{
				isCustomerInformation = true;
				customerInfoVO.setFirstName(objectInArray.getString(Constants.VALUE));
			}
			else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase(Constants.MIDDLE_NAME))
			{
				isCustomerInformation = true;
				customerInfoVO.setMiddleName(objectInArray.getString(Constants.VALUE));
			}
			else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase(Constants.LAST_NAME))
			{
				isCustomerInformation = true;
				customerInfoVO.setLastName(objectInArray.getString(Constants.VALUE));
			}
			else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase(Constants.EMAIL_ADDRESS))
			{
				isCustomerInformation = true;
				customerInfoVO.setEmailAddress(objectInArray.getString(Constants.VALUE));
			}else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase(Constants.HOME_CONTACTNUMBER))
			{
				isCustomerInformation = true;
				customerInfoVO.setHomePhoneNumber(objectInArray.getString(Constants.VALUE));
			}else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase(Constants.CELL_CONTACTNUMBER))
			{
				isCustomerInformation = true;
				customerInfoVO.setBtn(objectInArray.getString(Constants.VALUE));
				customerInfoVO.setCellPhoneNumber(objectInArray.getString(Constants.VALUE));
			}else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase(Constants.WORK_CONTACT_NUMBER))
			{
				isCustomerInformation = true;
				customerInfoVO.setBtn(objectInArray.getString(Constants.VALUE));
				customerInfoVO.setWorkPhoneNumber(objectInArray.getString(Constants.VALUE));
			}else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase("BestContactNum")){
				isCustomerInformation = true;
				customerInfoVO.setPhoneNumberType(objectInArray.getString(Constants.VALUE));
			}else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase(Constants.MOBILE_PHONE_NUMBER)){
				isCustomerInformation = true;
				customerInfoVO.setCellPhoneNumber(objectInArray.getString(Constants.VALUE));
			}else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase(Constants.PRIMARY_PHONE_NUMBER)){
				isCustomerInformation = true;
				customerInfoVO.setBtn(objectInArray.getString(Constants.VALUE));
			}else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase(Constants.PHONE_OPT_IN))
			{
				isCustomerInformation = true;
				customerInfoVO.setPhoneContactOptIn(true);
			}
			else if(objectInArray.getString(Constants.NAME).equalsIgnoreCase(Constants.MARKET_OPT_IN))
			{
				isCustomerInformation = true;
				customerInfoVO.setMarketingOptIn(true);
			}
			if(isCustomerInformation && dataFeildMap.get(objectInArray.getString(Constants.NAME)) != null){
				dataFeildMap.get(objectInArray.getString(Constants.NAME)).setEnteredValue(objectInArray.getString(Constants.VALUE));
				enterDataFeildList.add(objectInArray.getString(Constants.NAME));	
			}
		}
	}
}