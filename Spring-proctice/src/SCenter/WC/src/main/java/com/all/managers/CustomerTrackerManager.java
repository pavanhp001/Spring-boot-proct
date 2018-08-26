package com.A.managers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.A.productResults.managers.ProductResultsManager;
import com.A.productResults.vo.ProductSummaryVO;
import com.A.ui.constants.Constants;
import com.A.ui.dao.CustomerTrackerDao;
import com.A.ui.domain.CustomerTracker;
import com.A.ui.domain.CustomerTrackerDetails;
import com.A.ui.factory.CartLineItemFactory;
import com.A.ui.util.Utils;
import com.A.ui.vo.SalesCenterVO;
import com.A.V.exception.UnRecoverableException;
import com.A.xml.v4.AttributeDetailType;
import com.A.xml.v4.AttributeEntityType;
import com.A.xml.v4.LineItemStatusCodesType;
import com.A.xml.v4.LineItemStatusType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderType;

public class CustomerTrackerManager implements Runnable{
	
	private static Logger logger = Logger.getLogger(CustomerTrackerManager.class );
	
	private OrderType order;
	private HttpSession session;
	private CustomerTrackerDao customerTrackerDao;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

	public OrderType getOrder() {
		return order;
	}


	public void setOrder(OrderType order) {
		this.order = order;
	}


	public HttpSession getSession() {
		return session;
	}


	public void setSession(HttpSession session) {
		this.session = session;
	}


	public CustomerTrackerDao getCustomerTrackerDao() {
		return customerTrackerDao;
	}


	public void setCustomerTrackerDao(CustomerTrackerDao customerTrackerDao) {
		this.customerTrackerDao = customerTrackerDao;
	}

	public void run() {
		try {
			ProductResultsManager productResultManager = (ProductResultsManager)session.getAttribute("productResultManager");
			SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
			String referrerName = salesCenterVo.getValueByName("referrer.businessParty.referrerName");
			String customerName = getCustomerName(order);
			String dwellingType = salesCenterVo.getValueByName("dwellingType"); 
			String rentown = salesCenterVo.getValueByName("rentown"); 
			MDC.put("sessionId", salesCenterVo.getValueByName("sessionId") != null ? salesCenterVo.getValueByName("sessionId") : "");
			MDC.put("agentId", salesCenterVo.getValueByName("agent.id") != null ? salesCenterVo.getValueByName("agent.id") : "");
			MDC.put("orderId", salesCenterVo.getValueByName("orderId") != null ? salesCenterVo.getValueByName("orderId") : "");
			MDC.put("GUID", salesCenterVo.getValueByName("GUID") != null ? salesCenterVo.getValueByName("GUID") : "");
			logger.info("CustomerTrackerManager starting");
			String agentIdVal = salesCenterVo.getValueByName("agent.id");
			Integer utilityPitched = 0;
			List<CustomerTracker> tsLineItemTrackerDataList= (List<CustomerTracker>)session.getAttribute("tsLineItemTrackerDataList");
			CustomerTrackerDetails customerTrackerDetails= (CustomerTrackerDetails)session.getAttribute("customerTrackerDetails");
			float utilityPoints = customerTrackerDetails.getUtilityPoints();
			int callnumber = customerTrackerDetails.getConcertCallCount();
			if( tsLineItemTrackerDataList == null || tsLineItemTrackerDataList.isEmpty()){
				tsLineItemTrackerDataList = new ArrayList<CustomerTracker>();
			}
			if(productResultManager != null && productResultManager.context.getAllProductList() != null && order.getLineItems()!= null){
				List<LineItemType> lineItems = order.getLineItems().getLineItem();
				
				if(lineItems != null){
					logger.info("lineItemsSize()="+lineItems.size());
					for(LineItemType lineItem : lineItems){
							boolean isUtilityProduct = false;
							boolean isProductFound = false;
							boolean isNotCustomerTrackerProduct = false;
							String populatedCustomerTrackerOrderId = (String)session.getAttribute("populatedCustomerTrackerOrderId"); 
						
							if(isProduct(lineItem) && (Utils.isBlank(populatedCustomerTrackerOrderId) || !populatedCustomerTrackerOrderId.equals(order.getExternalId()))){
								CustomerTracker customerTracker = new CustomerTracker();
								float points = 0.0f;
								if(lineItem != null && lineItem.getLineItemDetail() != null && lineItem.getLineItemDetail().getDetail() != null 
										&& lineItem.getLineItemDetail().getDetail().getProductLineItem() != null && lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId() != null){
									logger.info("lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId()="+lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId());
									for(ProductSummaryVO product : productResultManager.context.getAllProductList()){
										if(product.getExternalId().equalsIgnoreCase(lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId())){
											if(product != null && !Float.isNaN(product.getPoints())  && product.getPoints() >= 0.0){
												points = product.getPoints();
											}
											isProductFound = true;
											break;
										}
									}
									if(!isProductFound && (lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals(Constants.ATTV6))){
										points = productResultManager.getATTV6NewLineItemProductPoints(lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId());
									}
								}
								if (lineItem.getLineItemAttributes() != null
										&& lineItem.getLineItemAttributes().getEntity() != null
										&& !lineItem.getLineItemAttributes().getEntity().isEmpty()) 
								{
									for (AttributeEntityType entityType : lineItem.getLineItemAttributes().getEntity()) 
									{
										if(!Utils.isBlank(entityType.getSource()) && entityType.getSource() != null && entityType.getSource().equalsIgnoreCase("PRODUCT_TYPE")){

											String productType = CartLineItemFactory.INSTANCE.getLineItemAttr(lineItem,Constants.PRODUCT_TYPE,"TYPE");

											if(lineItem.getLineItemDetail().getDetailType().equalsIgnoreCase("product") &&
													!Utils.isBlank(productType) && productType.equals("UtilityOffer"))
											{
												customerTracker.setProductType(productType);
												utilityPoints = utilityPoints + points;
												isUtilityProduct = true;
												logger.info("isUtilityProduct ="+isUtilityProduct);
											}
											else if(lineItem.getLineItemDetail().getDetailType().equalsIgnoreCase("product") &&
													!Utils.isBlank(productType) && (productType.equals("SimpleChoice")||productType.equals("SaversOffer")))
											{
												isNotCustomerTrackerProduct = true;
												logger.info("isNotCustomerTrackerProduct="+isNotCustomerTrackerProduct);
											}
										}
										else if (!Utils.isBlank(entityType.getSource())
												&& entityType.getSource().equalsIgnoreCase("PROVIDER_NAME")) 
										{
											if (entityType.getAttribute() != null
													&& !entityType.getAttribute().isEmpty()) 
											{
												for (AttributeDetailType attributeDetailType : entityType.getAttribute()) {
													if (!Utils.isBlank(attributeDetailType.getName())
															&& attributeDetailType.getName().equalsIgnoreCase("name")
															&& !Utils.isBlank(attributeDetailType.getValue())) 
													{
														customerTracker.setProviderName(attributeDetailType.getValue());
														break;
													}
												}
											}
										}else if (!Utils.isBlank(entityType.getSource())
												&& entityType.getSource().equalsIgnoreCase("PRODUCT_NAME"))
										{
											if (entityType.getAttribute() != null
													&& !entityType.getAttribute().isEmpty()) 
											{
												for (AttributeDetailType attributeDetailType : entityType.getAttribute()) {
													if (!Utils.isBlank(attributeDetailType.getName())
															&& attributeDetailType.getName().equalsIgnoreCase("name")) 
													{
														customerTracker.setProductName(attributeDetailType.getValue());
														break;
													}
												}
											}
										}
										else if (!Utils.isBlank(entityType.getSource())
												&& entityType.getSource().equalsIgnoreCase("PRODUCT_CATEGORY"))
										{
											if (entityType.getAttribute() != null
													&& !entityType.getAttribute().isEmpty()) 
											{
												for (AttributeDetailType attributeDetailType : entityType.getAttribute()) {
													if (!Utils.isBlank(attributeDetailType.getName())
															&& attributeDetailType.getName().equalsIgnoreCase("productType")) 
													{
														customerTracker.setProductType(attributeDetailType.getValue());
														/*if(customerTracker.getProductType().equalsIgnoreCase("APPLIANCEPROTECTION")){
															utilityPoints = utilityPoints + points;
														}*/
														break;
													}
												}
											}
										}
									}
								}
								if(!isNotCustomerTrackerProduct){
									customerTracker.setCallNumberId(callnumber);
									customerTracker.setAgentId(order.getAgentId());
									customerTracker.setReferrer(referrerName);
									customerTracker.setCustomerName(customerName);
									customerTracker.setOrderId(order.getExternalId());
									customerTracker.setLineItemId(lineItem.getExternalId());
									customerTracker.setDwellingType(dwellingType);
									customerTracker.setOwn(rentown);
									if(lineItem.getLineItemDetail() != null && lineItem.getLineItemDetail().getDetail() != null && lineItem.getLineItemDetail().getDetail().getProductLineItem() != null && lineItem.getLineItemDetail().getDetail().getProductLineItem().getName() != null){
										customerTracker.setProductName(lineItem.getLineItemDetail().getDetail().getProductLineItem().getName());
									}
									customerTracker.setConcertPoints(points);
									customerTracker.setActualPoints(points);
									customerTracker.setProviderParentId(Long.valueOf(lineItem.getPartnerExternalId()));
									customerTracker.setIsPointsUpdated(0);
									customerTracker.setCreateDate(Calendar.getInstance());
									customerTracker.setUpdatedDate(Calendar.getInstance());
									long noOfDaysDiff = 0;
									customerTracker.setNoOfDays("N/A");
									if(order.getMoveDate() != null){
										XMLGregorianCalendar moveDate = order.getMoveDate();
										 XMLGregorianCalendar installDate = getInstallDateValue( lineItem);
										 if(installDate != null){
											 Date movingDate = ((XMLGregorianCalendar)moveDate).toGregorianCalendar().getTime();
											 Date installDateValue = ((XMLGregorianCalendar)installDate).toGregorianCalendar().getTime();
											 Date installDateVal = sdf.parse(sdf.format(installDateValue));
											 Date movingDateVal = sdf.parse(sdf.format(movingDate));
											 
											 customerTracker.setMoveDate(moveDate.toGregorianCalendar());
											 customerTracker.setInstallDate(installDate.toGregorianCalendar());
											 
											 noOfDaysDiff= Math.abs((installDateVal.getTime() - movingDateVal.getTime()) / (1000 * 60 * 60 * 24)); 
											 customerTracker.setNoOfDays(String.valueOf(noOfDaysDiff));
										 }
									}else {
										XMLGregorianCalendar orderDate = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) Calendar.getInstance());
										 XMLGregorianCalendar installDate = getInstallDateValue( lineItem);
										 if(installDate != null){
											 Date movingDate = ((XMLGregorianCalendar)orderDate).toGregorianCalendar().getTime();
											 Date installDateValue = ((XMLGregorianCalendar)installDate).toGregorianCalendar().getTime();
											 Date installDateVal = sdf.parse(sdf.format(installDateValue));
											 Date movingDateVal = sdf.parse(sdf.format(movingDate));
											 
											 customerTracker.setMoveDate(orderDate.toGregorianCalendar());
											 customerTracker.setInstallDate(installDate.toGregorianCalendar());
											 
											 noOfDaysDiff= Math.abs((installDateVal.getTime() - movingDateVal.getTime()) / (1000 * 60 * 60 * 24)); 
											 customerTracker.setNoOfDays(String.valueOf(noOfDaysDiff));
										 }
									}
									
									tsLineItemTrackerDataList.add(customerTracker);

									customerTrackerDao.insertCustomerTracker(customerTracker);
									logger.info("customerTrackerList_size"+tsLineItemTrackerDataList.size());
								}
							}
					}
					customerTrackerDetails.setUtilityPoints(utilityPoints);
				}
			}
			boolean isCustomerTrackerDetailsPopulated = (Boolean)session.getAttribute("isCustomerTrackerDetailsPopulated");
			if(customerTrackerDetails!= null){
				if(isCustomerTrackerDetailsPopulated){
					customerTrackerDao.updateCustomerTrackerDetails(customerTrackerDetails);
				}
				else{
					customerTrackerDao.insertCustomerTrackerDetails(customerTrackerDetails);
				}
				session.setAttribute("customerTrackerDetails",customerTrackerDetails);
			}
			if(!tsLineItemTrackerDataList.isEmpty()){
				tsLineItemTrackerDataList = Utils.sortCustomers(tsLineItemTrackerDataList);
			}
			if(session.getAttribute("updatedActualPointsMap") != null){
				Map<String,Float> updatedActualPointsMap = (Map<String,Float>)session.getAttribute("updatedActualPointsMap");
				customerTrackerDao.updateCustomerTracker(updatedActualPointsMap) ;
			}
			if(utilityPitched > 0){
				session.setAttribute("utilityPitched", utilityPitched);
			}
			session.setAttribute("populatedCustomerTrackerOrderId", order.getExternalId());
			session.setAttribute("tsLineItemTrackerDataList", tsLineItemTrackerDataList);
			session.setAttribute("currentOrderId", order.getExternalId());
			logger.info("CustomerTrackerManager ending");
		}  
		catch (Exception e) {
			logger.error("Error_while_getting_product_points"+e.getMessage());
		}
	}
	
	private String getCustomerName(OrderType order) {
		StringBuilder customerName = new StringBuilder();
		customerName.append(order.getCustomerInformation().getCustomer().getFirstName());
		customerName.append(" ");
		customerName.append(order.getCustomerInformation().getCustomer().getLastName());
		return customerName.toString();
	}
	 private boolean isProduct(LineItemType lineItem)
	{
		if(isProductIncludingRemoved(lineItem) && 
				lineItem.getLineItemStatus().getStatusCode() != null &&
				!lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.CANCELLED_REMOVED))
		{
			if( lineItem.getLineItemStatus().getStatusCode() != null 
					&& LineItemStatusCodesType.PROVISION_READY.equals(lineItem.getLineItemStatus().getStatusCode())){
				return true;
			}else if(lineItem.getLineItemStatusHistory() != null 
					&& lineItem.getLineItemStatusHistory().getPreviousStatus() != null
					&& !lineItem.getLineItemStatusHistory().getPreviousStatus().isEmpty()){
				for(LineItemStatusType lineItemStatus:lineItem.getLineItemStatusHistory().getPreviousStatus()){
					if(LineItemStatusCodesType.PROVISION_READY.equals(lineItemStatus.getStatusCode())){
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean isProductIncludingRemoved(LineItemType lineItem)
	{
		if(lineItem.getLineItemDetail().getDetailType().equals("product"))
		{
			return true;
		}
		return false;
	}
	/** 
	 * 
	 *   Setting date values to LineItemTypeVO from LineItemType.  
	 *    * @param lineItemType  
	 *    * @param lineItemTypeVO  
	 *    * @return  
	 *     */ 
	private XMLGregorianCalendar getInstallDateValue(LineItemType lineItemType) throws UnRecoverableException
	 {
			if (lineItemType.getSchedulingInfo().getDesiredStartDate() != null
					&& lineItemType.getSchedulingInfo().getDesiredStartDate()
							.getDate() != null) {
				return lineItemType.getSchedulingInfo().getDesiredStartDate().getDate();
			} else if (lineItemType.getSchedulingInfo().getScheduledStartDate() != null
					&& lineItemType.getSchedulingInfo().getScheduledStartDate()
							.getDate() != null) {
			   return lineItemType.getSchedulingInfo().getScheduledStartDate().getDate();
			} else if (lineItemType.getSchedulingInfo().getActualStartDate() != null
					&& lineItemType.getSchedulingInfo().getActualStartDate()
							.getDate() != null){
	
				return lineItemType.getSchedulingInfo().getActualStartDate().getDate();
			}else{
				return null;
			}
	}
}
