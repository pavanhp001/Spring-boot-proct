package com.AL.ui.service.V;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.hibernate.cfg.RecoverableException;

import com.AL.ui.constants.Constants;
import com.AL.ui.factory.CKOLineItemFactory;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.CartError;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.OrderQualVO;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;


public enum OrderServiceUI {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(OrderServiceUI.class);

	public OrderManagementRequestResponse getOrderManagementRequestResponseByOrderNumber(final String id ) {
		logger.info("Calling getOrderManagementRequestResponseByOrderNumber for OrderId: "+id);
		OrderManagementRequestResponse response = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(id);

		return response;
	}

	public OrderType updateLineItem(String orderId, LineItemType lineItem) {
		logger.info("Updating lineItemInfo info");
		OrderType order = LineItemService.INSTANCE.lineItemUpdate("default", orderId, lineItem, lineItem.getExternalId());
		logger.info("Finished updating lineItem info");
		return order;
	}

	public OrderType getOrder(final String id ) {
		logger.debug("Calling getOrderManagementRequestResponseByOrderNumber for OrderId: "+id);
		OrderManagementRequestResponse requestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(id,Boolean.TRUE);
		if ((requestResponse != null) && (requestResponse.getResponse() != null)) 
		{
			if( requestResponse.getResponse().getOrderInfo()!=null && !requestResponse.getResponse().getOrderInfo().isEmpty() )
			{
				return requestResponse.getResponse().getOrderInfo().get(0);
			}
		}
		return null;
	}

	public OrderType getOrderType() {
		String xmlInput =  ProductServiceUI.INSTANCE.readFileToString(new File(".\\src\\main\\resources\\xml\\uverse-product-order.xml"));
		OrderType orderInfo = toOrderTypeObject(xmlInput);
		return orderInfo;
	}

	public OrderType toOrderTypeObject(String xmlInput)  {
		OrderType orderType = null;
		StringReader sr = null;
		try {
			JAXBContext orderTypeJxbContext = JAXBContext.newInstance(OrderType.class);
			sr = new StringReader( xmlInput );
			Unmarshaller unmarshaller = orderTypeJxbContext.createUnmarshaller();
			JAXBElement<OrderType> b = unmarshaller.unmarshal(new StreamSource(sr),	OrderType.class);
			orderType = b.getValue();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sr.close();
		}
		return orderType;
	}	

	/**
	 * Update the CKO lineItemAttributes status to CKOError
	 * 
	 * 
	 * 
	 * @param CKOStatus
	 * @param order
	 * @param orderQualVO
	 * @param lineItemExternalID
	 * @param context
	 */
	public void updateLineItemAttribute(String CKOStatus, OrderQualVO orderQualVO, long lineItemExternalID, SalesContextType context) 
	{
		ObjectFactory oFactory = new ObjectFactory();

		LineItemType lineItem = oFactory.createLineItemType();
		lineItem.setExternalId(lineItemExternalID);

		lineItem.setLineItemNumber(orderQualVO.getLineItemNumber());

		AttributeEntityType entity = oFactory.createAttributeEntityType();
		entity.setSource("CKO");
		AttributeDetailType attr = oFactory.createAttributeDetailType();
		attr.setName("STATUS");
		attr.setValue(CKOStatus);
		entity.getAttribute().add(attr);

		LineItemAttributeType attribute = oFactory.createLineItemAttributeType();
		attribute.getEntity().add(entity);
		lineItem.setLineItemAttributes(attribute);

		LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
		liCollection.getLineItem().add(lineItem);
		ErrorList errorList = new ErrorList();
		LineItemService.INSTANCE.updateLineItem( orderQualVO.getAgentId(), orderQualVO.getOrderId(),VOperation.updateLineItem.toString(), liCollection, context, errorList);
		if(errorList != null && errorList.size() > 0)
		{
			Iterator<CartError> cartErrorIterator = errorList.iterator();
			String errorMessage = null;
			while(cartErrorIterator.hasNext()){
				CartError error = cartErrorIterator.next();
				errorMessage = error.getCode()+"-"+error.getMessage()+","; 
			}
			if(Utils.isBlank(errorMessage) && errorMessage.endsWith(",")){
				throw new RecoverableException(errorMessage.substring(0, errorMessage.length()-1));
			}
		}
	}

	/**
	 * Saving LineItemAttributes,SelectedFeatures & Active Dialogues 
	 * 
	 * 
	 * @param CKOStatus
	 * @param order
	 * @param orderQualVO
	 * @param lineItemExternalID
	 * @param secondInsEntity
	 * @param context
	 * @return
	 */
	public OrderType updateLineItemAttributeBySelectedlineItem(String CKOStatus,OrderType order,OrderQualVO orderQualVO, long lineItemExternalID, 
			AttributeEntityType secondInsEntity, SalesContextType context, 
			AttributeEntityType firstInsEntity){
		logger.info("INSIDE update line item attributes");
		ObjectFactory oFactory = new ObjectFactory();
		LineItemType lineItem = oFactory.createLineItemType();
		/*List<DialogValueType> lineItemDialogues;

		SelectedFeaturesType lineItemSelectedFeatureType = oFactory.createSelectedFeaturesType();
		List<FeatureGroup> selFeaturesGroupList = new ArrayList<FeatureGroup>();
		Features selFets = oFactory.createSelectedFeaturesTypeFeatures();
		SelectedDialogsType.Dialogs dialogues = oFactory.createSelectedDialogsTypeDialogs();
		SelectedDialogsType selectedDialoguesType = oFactory.createSelectedDialogsType();*/
		logger.info("Before iterating over order line item type");
		/*for(LineItemType itemType : order.getLineItems().getLineItem()){
			logger.info("Inside LineItem type for loop");
			if(itemType.getExternalId() == lineItemExternalID){
				if(itemType.getActiveDialogs() != null && itemType.getActiveDialogs().getDialogs() != null && itemType.getActiveDialogs().getDialogs().getDialog() != null){
					logger.info("Getting all dialogues on the line item");
					lineItemDialogues = itemType.getActiveDialogs().getDialogs().getDialog();
					logger.info("After getting values from item");
					dialogues.getDialog().addAll(lineItemDialogues);
					logger.info("adding all dialogues to doalogue object");
					selectedDialoguesType.setDialogs(dialogues);
					logger.info("SETTING dialogues to selected dialogue object");
					lineItem.setActiveDialogs(selectedDialoguesType);
					logger.info("Setting active dialogues to lineitem");
				}
				if(itemType.getSelectedFeatures() != null && (itemType.getSelectedFeatures().getFeatureGroup() != null || itemType.getSelectedFeatures().getFeatures() != null)){
					logger.info("inside gettigng selected features");
					selFeaturesGroupList = itemType.getSelectedFeatures().getFeatureGroup();
					logger.info("After getting Selected Features list");
					selFets = itemType.getSelectedFeatures().getFeatures();
					logger.info("Before setting features to line item sel type");
					lineItemSelectedFeatureType.setFeatures(selFets);
					logger.info("adding feature group to selected features");
					lineItemSelectedFeatureType.getFeatureGroup().addAll(selFeaturesGroupList);
					logger.info("Before LLine Item Selected Featres");
					lineItem.setSelectedFeatures(lineItemSelectedFeatureType);
				}
			}
		}*/
		logger.info("Setting line item external ID");
		lineItem.setExternalId(lineItemExternalID);
		logger.info("Setting Line Item Numebr --- > "+orderQualVO.getLineItemType().getLineItemNumber());
		lineItem.setLineItemNumber(orderQualVO.getLineItemType().getLineItemNumber());
		logger.info("After setting lint item number");
		LineItemAttributeType lineItemAttribute = oFactory.createLineItemAttributeType();
		AttributeEntityType entity = oFactory.createAttributeEntityType();
		logger.info("SEtting entity source as CKO");
		entity.setSource("CKO");
		logger.info("After setting entity source as CKO");
		AttributeDetailType attr = oFactory.createAttributeDetailType();
		logger.info("Before setting attribute name as Status");
		attr.setName("STATUS");
		logger.info("Before setting attribute values ");
		attr.setValue(CKOStatus);
		logger.info("Adding attributes to entity");
		entity.getAttribute().add(attr);
		logger.info("Setting entity for the lint item attributes");
		lineItemAttribute.getEntity().add(entity);
		logger.info("Before setting second installation entity");
		if(secondInsEntity != null && secondInsEntity.getSource() != null && secondInsEntity.getSource().trim().equals("SECOND_DESIRED_DATE")){
			logger.info("Inside setting second installtion entity");
			lineItemAttribute.getEntity().add(secondInsEntity);
			logger.info("After setting second installtion entity");
		}
		logger.info("Before setting first installtion entity");
		if(firstInsEntity != null && firstInsEntity.getSource() != null && firstInsEntity.getSource().trim().equals("FIRST_INSTALLATION_TIME")){
			logger.info("Indise first installtion entity");
			lineItemAttribute.getEntity().add(firstInsEntity);
			logger.info("After settingg first installtion entity");
		}
		logger.info("Setting line item attributes to line item");
		lineItem.setLineItemAttributes(lineItemAttribute);

		LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
		logger.info("Adding line item to lone item collection type");
		liCollection.getLineItem().add(lineItem);
		logger.info("After setting item to line item collection type");
		ErrorList errorList = new ErrorList();
		OrderType orderTY = LineItemService.INSTANCE.updateLineItem(orderQualVO.getAgentId(), orderQualVO.getOrderId(), 
				VOperation.updateLineItem.toString(),liCollection,context);

		if(errorList != null && errorList.size() > 0){
			Iterator<CartError> cartErrorIterator = errorList.iterator();
			String errorMessage = null;
			while(cartErrorIterator.hasNext()){
				CartError error = cartErrorIterator.next();
				errorMessage = error.getCode()+"-"+error.getMessage()+","; 
			}
			if(Utils.isBlank(errorMessage) && errorMessage.endsWith(",")){
				throw new RecoverableException(errorMessage.substring(0, errorMessage.length()-1));
			}
		}

		logger.info("After updating line item");
		return orderTY;
	}


	/**
	 * Saving LineItemAttributes,SelectedFeatures & Active Dialogues 
	 * 
	 * 
	 * @param CKOStatus
	 * @param order
	 * @param orderQualVO
	 * @param lineItemExternalID
	 * @param secondInsEntity
	 * @param context
	 * @return
	 */
	public OrderType updateLineItemBySelectedlineItem(String CKOStatus, OrderQualVO orderQualVO, long lineItemExternalID, 
			AttributeEntityType secondInsEntity, SalesContextType context, 
			AttributeEntityType firstInsEntity, LineItemType lineItem){

		logger.info("INSIDE update line item attributes");

		ObjectFactory oFactory = new ObjectFactory();

		LineItemAttributeType lineItemAttribute = oFactory.createLineItemAttributeType();
		AttributeEntityType entity = oFactory.createAttributeEntityType();
		logger.info("SEtting entity source as CKO");
		entity.setSource("CKO");
		logger.info("After setting entity source as CKO");
		AttributeDetailType attr = oFactory.createAttributeDetailType();
		logger.info("Before setting attribute name as Status");
		attr.setName("STATUS");
		if(Constants.CKO_COMPLETE.equals(CKOStatus)){
			attr.setDescription("CKO Completed");
		}
		logger.info("Before setting attribute values ");
		attr.setValue(CKOStatus);
		logger.info("Adding attributes to entity");
		entity.getAttribute().add(attr);
		logger.info("Setting entity for the lint item attributes");
		lineItemAttribute.getEntity().add(entity);
		logger.info("Before setting second installation entity");
		if(secondInsEntity != null && secondInsEntity.getSource() != null && secondInsEntity.getSource().trim().equals("SECOND_DESIRED_DATE")){
			logger.info("Inside setting second installtion entity");
			lineItemAttribute.getEntity().add(secondInsEntity);
			logger.info("After setting second installtion entity");
		}
		logger.info("Before setting first installtion entity");
		if(firstInsEntity != null && firstInsEntity.getSource() != null && firstInsEntity.getSource().trim().equals("FIRST_INSTALLATION_TIME")){
			logger.info("Indise first installtion entity");
			lineItemAttribute.getEntity().add(firstInsEntity);
			logger.info("After settingg first installtion entity");
		}
		logger.info("Setting line item attributes to line item");
		lineItem.setLineItemAttributes(lineItemAttribute);

		LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
		logger.info("Adding line item to lone item collection type");
		liCollection.getLineItem().add(lineItem);
		logger.info("After setting item to line item collection type");
		ErrorList errorList = new ErrorList();

		OrderType orderTY = LineItemService.INSTANCE.updateLineItem(orderQualVO.getAgentId(), orderQualVO.getOrderId(), 
				VOperation.updateLineItem.toString(), liCollection, context, errorList);

		if(errorList != null && errorList.size() > 0){
			Iterator<CartError> cartErrorIterator = errorList.iterator();
			String errorMessage = null;
			while(cartErrorIterator.hasNext()){
				CartError error = cartErrorIterator.next();
				errorMessage = error.getCode()+"-"+error.getMessage()+","; 
			}
			if(Utils.isBlank(errorMessage) && errorMessage.endsWith(",")){
				throw new RecoverableException(errorMessage.substring(0, errorMessage.length()-1));
			}
		}

		logger.info("After updating line item");
		return orderTY;
	}

	/**
	 * Submits lineItem if PRODUCT_TYPE is UtilityOffer
	 * 
	 * @param responseOrder
	 * @param lineItemExternalID
	 * @param orderQualVO
	 * @param context
	 * @return OrderType
	 */
	public OrderType submitUtiltyOffer(OrderType responseOrder, long lineItemExternalID, OrderQualVO orderQualVO, SalesContextType context){

		logger.info("start_submit_Utilityoffer");
		List<String> lineitem_id_list = new ArrayList<String>();

		if(responseOrder != null && responseOrder.getExternalId() != 0){
			List<LineItemType> lineItems = responseOrder.getLineItems().getLineItem();

			if(lineItems != null){
				for(LineItemType lineItemType : lineItems){
					if(lineItemType.getExternalId() == lineItemExternalID){
						String productType = CKOLineItemFactory.INSTANCE.getLineItemAttr(lineItemType,"PRODUCT_TYPE","TYPE");
						if(!Utils.isBlank(productType) && productType.equalsIgnoreCase("UtilityOffer")){
							lineitem_id_list.add(Long.valueOf(lineItemType.getExternalId())
									.toString());

						}
					}
				}
			}

		}

		if(lineitem_id_list.size() > 0){
			/*    
			 * ServiceCall for placing the Order                        
			 */

			responseOrder = LineItemService.INSTANCE.submitMultipleLineItem( orderQualVO.getAgentId(), orderQualVO.getOrderId(), lineitem_id_list,
					context);                
		}
		logger.info("end_submit_Utilityoffer");
		return responseOrder;
	}

	public OrderType updateLineItem(OrderQualVO orderQualVO,SalesContextType context, LineItemType lineItem){
		logger.info("INSIDE update line item attributes");
		ObjectFactory oFactory = new ObjectFactory();
		
		LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
		logger.info("Adding line item to lone item collection type");
		liCollection.getLineItem().add(lineItem);
		logger.info("After setting item to line item collection type");
		ErrorList errorList = new ErrorList();
		OrderType orderType = LineItemService.INSTANCE.updateLineItem(orderQualVO.getAgentId(), orderQualVO.getOrderId(), 
				VOperation.updateLineItem.toString(), liCollection, context, errorList);
		if(errorList != null && errorList.size() > 0){
			Iterator<CartError> cartErrorIterator = errorList.iterator();
			String errorMessage = null;
			while(cartErrorIterator.hasNext()){
				CartError error = cartErrorIterator.next();
				errorMessage = error.getCode()+"-"+error.getMessage()+","; 
			}
			if(Utils.isBlank(errorMessage) && errorMessage.endsWith(",")){
				//throw new RecoverableException(errorMessage.substring(0, errorMessage.length()-1));
			}
		}
		logger.info("After updating line item");
		return orderType;
	}
}
