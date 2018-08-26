package com.A.V.factory;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import com.A.ui.service.V.CustomerService;
import com.A.ui.vo.ErrorList;
import com.A.util.DateUtil;
import com.A.xml.v4.AttributeDetailType;
import com.A.xml.v4.AttributeEntityType;
import com.A.xml.v4.DateTimeOrTimeRangeType;
import com.A.xml.v4.LineItemPriceInfoType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.ObjectFactory;
import com.A.xml.v4.ProcessingMessage;
import com.A.xml.v4.SchedulingInfoType;

import org.apache.commons.lang.StringUtils;

public class DomainBuilder {

	private static final Logger logger = Logger.getLogger(DomainBuilder.class);
	public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.z");
	public static final String DATE_FORMAT_REGEX = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";
	
	public static void addLineItemComments(final String customerId, final String customerName, final String orderId, 
			final String lineItemId, final String agentId, final String providerId, final String comments) {
		if(comments != null && !comments.trim().equals("")){
			DomainBuilder db = new DomainBuilder();
			new Thread(db.new executeAddComments(customerId, customerName, orderId, lineItemId, agentId, providerId, comments)).start();
		}
	}
	
	public static void addLineItemAttribute(final LineItemType lineItem,
			final String source, final String key, String value) {
		
		logger.debug("addLineItemAttribute : "+source+"."+key+"."+value);

		List<AttributeDetailType> attrDetail = addAttribute(lineItem, source);
		
		if(StringUtils.isNotBlank(value) && value.matches(DATE_FORMAT_REGEX)){
			value = FORMAT_DATE.format(DateUtil.fromStringToDate(value));
			addKeyValue(attrDetail, key, value);
		} else if(StringUtils.isNotBlank(value)){
			addKeyValue(attrDetail, key, value);
		} else if(key.equals("CIDATE")){
			addKeyValue(attrDetail, key, value);
		}
	}
	
	public static void addLineItemConfNumber(final LineItemType lineItem,
			String confNumber) {
		logger.debug("addLineItemAttribute, confNumber : "+confNumber);

		if(StringUtils.isNotBlank(confNumber)){
			lineItem.setProviderConfirmationNumber(confNumber);
		}
	}
	
	public static void updateIVRTime(final LineItemType lineItem,
			final String source, final String key, final String value) {
		
		String[] keys = key.split("_");
		if(value != null && !value.trim().equals("")){
			String[] values = value.split(" ");
			logger.debug(source+"."+keys[0]+"."+values[0]);

			List<AttributeDetailType> attrDetail0 = addAttribute(lineItem, source);
			addKeyValue(attrDetail0, keys[0], values[0]);
			
			if(keys.length > 1 && values.length > 1 && !values[1].trim().equals("")){
				logger.debug(source+"."+keys[1]+"."+values[1]);

				List<AttributeDetailType> attrDetail1 = addAttribute(lineItem, source);
				addKeyValue(attrDetail1, keys[1], values[1]);
			}
		} else {
			List<AttributeDetailType> attributeDetailTypeList = addAttribute(lineItem, source);
					addKeyValue(attributeDetailTypeList, keys[0], value);
					addKeyValue(attributeDetailTypeList, keys[1], value);
		}
	}
	
	public static List<AttributeDetailType> addAttribute(LineItemType lit,
			final String source) {
		
		ObjectFactory oFactory = new ObjectFactory();
		
		if (lit.getLineItemAttributes() == null) {
			lit.setLineItemAttributes(oFactory.createLineItemAttributeType());
		}
		List<AttributeEntityType> attribEntityList = lit
				.getLineItemAttributes().getEntity();

		for (AttributeEntityType aet : attribEntityList) {

			if (source.equals(aet.getSource())) {
				return aet.getAttribute();
			}
		}

		return createAttributeDetailType(source, attribEntityList);

	}
	
	public static List<AttributeDetailType> createAttributeDetailType(
			final String source, List<AttributeEntityType> attribEntityList) {

		ObjectFactory oFactory = new ObjectFactory();
		AttributeEntityType attributeEntityType = oFactory
				.createAttributeEntityType();
		attributeEntityType.setSource(source);
		attribEntityList.add(attributeEntityType);

		return attributeEntityType.getAttribute();
	}

	

	public static void addKeyValue(
			List<AttributeDetailType> attributeDetailTypeList,
			final String key, final String value) {

		for (AttributeDetailType attributeDetailType : attributeDetailTypeList) {

			if (key.equals(attributeDetailType.getName())) {
				attributeDetailType.setValue(value);
				return;
			}
		}

		ObjectFactory oFactory = new ObjectFactory();
		AttributeDetailType attributeDetailType = oFactory
				.createAttributeDetailType();
			attributeDetailType.setName(key);
			attributeDetailType.setValue(value);

		attributeDetailTypeList.add(attributeDetailType);
	}
	
	
	public static LineItemPriceInfoType buildLineItemPrice(
			final LineItemType lineItemType, double paymentPrice, double installationPrice, String onDeliveryPrice) {
		ObjectFactory oFactory = new ObjectFactory();

		LineItemPriceInfoType lineItemPriceInfo = lineItemType
				.getLineItemPriceInfo();

		if (lineItemType.getLineItemPriceInfo() == null) {
			lineItemPriceInfo = oFactory.createLineItemPriceInfoType();
			lineItemType.setLineItemPriceInfo(lineItemPriceInfo);
		}
		
		lineItemPriceInfo.setBaseNonRecurringPrice(installationPrice);
		lineItemPriceInfo.setBaseRecurringPrice(paymentPrice);
		lineItemPriceInfo.setOnDeliveryPrice(onDeliveryPrice);
		lineItemPriceInfo.setIncludeInTotalPrice(oFactory.createEmptyElementType());
		
		if (lineItemPriceInfo.getPriceInfoStatus() == null) {
			lineItemPriceInfo.setPriceInfoStatus(oFactory.createStatusType());
		}
		
		lineItemPriceInfo.getPriceInfoStatus().setProcessingMessages(oFactory.createStatusTypeProcessingMessages());
		ProcessingMessage pm = oFactory.createProcessingMessage();
		pm.setCode(0);
		pm.setText("fulfillment");
		lineItemPriceInfo.getPriceInfoStatus().getProcessingMessages().getMessage().add(pm);
		lineItemPriceInfo.getPriceInfoStatus().setStatusCode(1);
		lineItemPriceInfo.getPriceInfoStatus().setStatusMsg("fulfillment Price Update");
		lineItemPriceInfo.setPricingDate(DateUtil.getCurrentXMLDate());
		
		

		return lineItemPriceInfo;

	}

	public static DateTimeOrTimeRangeType buildDesiredStartDate(
			final LineItemType lineItemType,String dateAsString, String timeAsString ) {
		
		if(dateAsString == null || dateAsString.trim().equals("")){
			dateAsString = convertCurrDateToString();
		}
		
		if(timeAsString == null || timeAsString.trim().equals("")){
			timeAsString = "00:01 AM";
		}
		
		ObjectFactory oFactory = new ObjectFactory();

		SchedulingInfoType schedInfo = buildSchedulingInfo(lineItemType);

		if (schedInfo.getDesiredStartDate() == null) {

			DateTimeOrTimeRangeType dateTime = oFactory
					.createDateTimeOrTimeRangeType();
			schedInfo.setDesiredStartDate(dateTime);
		}
		
		  
		XMLGregorianCalendar desiredDate = DateUtil.fromStringToXMLDateTime(dateAsString+" "+timeAsString);
	 
		
		schedInfo.getDesiredStartDate().setEndTime(desiredDate);
		schedInfo.getDesiredStartDate().setDate(desiredDate);
		schedInfo.getDesiredStartDate().setStartTime(desiredDate);
		schedInfo.getDesiredStartDate().setTime(desiredDate);
		schedInfo.setInstallationFee(new BigDecimal(1));

		return schedInfo.getDesiredStartDate();

	}
	
	public static DateTimeOrTimeRangeType buildOrderedStartDate(
			final LineItemType lineItemType,String dateAsString, String timeAsString ) {
		
		if(dateAsString == null || dateAsString.trim().equals("")){
			dateAsString = convertCurrDateToString();
		}
		
		if(timeAsString == null || timeAsString.trim().equals("")){
			timeAsString = "00:01 AM";
		}
		
		ObjectFactory oFactory = new ObjectFactory();

		SchedulingInfoType schedInfo = buildSchedulingInfo(lineItemType);

		if (schedInfo.getOrderDate() == null) {

			DateTimeOrTimeRangeType dateTime = oFactory
					.createDateTimeOrTimeRangeType();
			schedInfo.setOrderDate(dateTime);
		}
		
		  
		XMLGregorianCalendar orderDate = DateUtil.fromStringToXMLDateTime(dateAsString+" "+timeAsString);
	 
		
		schedInfo.getOrderDate().setEndTime(orderDate);
		schedInfo.getOrderDate().setDate(orderDate);
		schedInfo.getOrderDate().setStartTime(orderDate);
		schedInfo.getOrderDate().setTime(orderDate);
		schedInfo.setInstallationFee(new BigDecimal(1));

		return schedInfo.getScheduledStartDate();

	}
	
	public static DateTimeOrTimeRangeType buildScheduledStartDate(
			final LineItemType lineItemType,String dateAsString, String timeAsString ) {
		
		if(dateAsString == null || dateAsString.trim().equals("")){
			dateAsString = convertCurrDateToString();
		}
		
		if(timeAsString == null || timeAsString.trim().equals("")){
			timeAsString = "00:01 AM";
		}
		
		ObjectFactory oFactory = new ObjectFactory();

		SchedulingInfoType schedInfo = buildSchedulingInfo(lineItemType);

		if (schedInfo.getScheduledStartDate() == null) {

			DateTimeOrTimeRangeType dateTime = oFactory
					.createDateTimeOrTimeRangeType();
			schedInfo.setScheduledStartDate(dateTime);
		}
		
		  
		XMLGregorianCalendar scheduledDate = DateUtil.fromStringToXMLDateTime(dateAsString+" "+timeAsString);
	 
		
		schedInfo.getScheduledStartDate().setEndTime(scheduledDate);
		schedInfo.getScheduledStartDate().setDate(scheduledDate);
		schedInfo.getScheduledStartDate().setStartTime(scheduledDate);
		schedInfo.getScheduledStartDate().setTime(scheduledDate);
		schedInfo.setInstallationFee(new BigDecimal(1));

		return schedInfo.getScheduledStartDate();

	}
	
	
	public static DateTimeOrTimeRangeType buildScheduledStartEndDate(
			final LineItemType lineItemType,String dateAsString, String timeAsString ,String endTimeAsString) {	
		
		ObjectFactory oFactory = new ObjectFactory();
		XMLGregorianCalendar scheduledDate = null;
		XMLGregorianCalendar scheduledStartTime = null;
		XMLGregorianCalendar scheduledEndTime = null;

		SchedulingInfoType schedInfo = buildSchedulingInfo(lineItemType);

		if (schedInfo.getScheduledStartDate() == null) {
			DateTimeOrTimeRangeType dateTime = oFactory
					.createDateTimeOrTimeRangeType();
			schedInfo.setScheduledStartDate(dateTime);
		}
		
		if(StringUtils.isNotBlank(dateAsString)){
			scheduledDate = DateUtil.fromStringToXMLDate(dateAsString);
			
			if(StringUtils.isNotBlank(timeAsString)){
				scheduledStartTime = DateUtil.fromStringToXMLDateTime(dateAsString+" "+timeAsString);
			}
			
			if(StringUtils.isNotBlank(endTimeAsString)){
				scheduledEndTime = DateUtil.fromStringToXMLDateTime(dateAsString+" "+endTimeAsString);	
			}
		}
		
		schedInfo.getScheduledStartDate().setEndTime(scheduledEndTime);
		schedInfo.getScheduledStartDate().setDate(scheduledDate);
		schedInfo.getScheduledStartDate().setStartTime(scheduledStartTime);
		schedInfo.setInstallationFee(new BigDecimal(1));

		return schedInfo.getScheduledStartDate();

	}
	
	public static DateTimeOrTimeRangeType buildDisconnectDate(
			final LineItemType lineItemType, String dateAsString, String timeAsString ) {
		
		if(dateAsString == null || dateAsString.trim().equals("")){
			dateAsString = convertCurrDateToString();
		}
		
		if(timeAsString == null || timeAsString.trim().equals("")){
			timeAsString = "00:01 AM";
		}

		ObjectFactory oFactory = new ObjectFactory();

		SchedulingInfoType schedInfo = buildSchedulingInfo(lineItemType);

		if (schedInfo.getDisconnectDate() == null) {

			DateTimeOrTimeRangeType dateTime = oFactory
					.createDateTimeOrTimeRangeType();
			schedInfo.setDisconnectDate(dateTime);
		}
		
		  
		XMLGregorianCalendar disconnectDate = DateUtil.fromStringToXMLDateTime(dateAsString+" "+timeAsString);
	 
		
		schedInfo.getDisconnectDate().setEndTime(disconnectDate);
		schedInfo.getDisconnectDate().setDate(disconnectDate);
		schedInfo.getDisconnectDate().setStartTime(disconnectDate);
		schedInfo.getDisconnectDate().setTime(disconnectDate);
		schedInfo.setInstallationFee(new BigDecimal(1));

		return schedInfo.getDisconnectDate();

	}

	public static DateTimeOrTimeRangeType buildActualDate(
			final LineItemType lineItemType,String dateAsString, String timeAsString ) {
		
		if(dateAsString == null || dateAsString.trim().equals("")){
			dateAsString = convertCurrDateToString();
		}
		
		if(timeAsString == null || timeAsString.trim().equals("")){
			timeAsString = "00:01 AM";
		}
		
		ObjectFactory oFactory = new ObjectFactory();

		SchedulingInfoType schedInfo = buildSchedulingInfo(lineItemType);

		if (schedInfo.getActualStartDate() == null) {

			DateTimeOrTimeRangeType dateTime = oFactory
					.createDateTimeOrTimeRangeType();
			schedInfo.setActualStartDate(dateTime);
		}
		
		  
		XMLGregorianCalendar actualStartDate = DateUtil.fromStringToXMLDateTime(dateAsString+" "+timeAsString);
	 
		
		schedInfo.getActualStartDate().setEndTime(actualStartDate);
		schedInfo.getActualStartDate().setDate(actualStartDate);
		schedInfo.getActualStartDate().setStartTime(actualStartDate);
		schedInfo.getActualStartDate().setTime(actualStartDate);
		schedInfo.setInstallationFee(new BigDecimal(1));

		return schedInfo.getActualStartDate();

	}
	
	public static SchedulingInfoType buildSchedulingInfo(
			final LineItemType lineItemType) {
		ObjectFactory oFactory = new ObjectFactory();

		if (lineItemType.getSchedulingInfo() == null) {
			SchedulingInfoType sched = oFactory.createSchedulingInfoType();
			lineItemType.setSchedulingInfo(sched);
		}

		return lineItemType.getSchedulingInfo();

	}
	
	public static void buildNewPhoneNumber(
			final LineItemType lineItemType, String newPhoneNumber) {
 
		if (lineItemType != null) {
			lineItemType.setNewPhone(newPhoneNumber);
		}
	}
	 
	private static String convertCurrDateToString(){
		java.util.Date currDate = new java.util.Date();
		Format formatter = new SimpleDateFormat("MM/dd/yyyy");
		String dateAsString = formatter.format(currDate);
		return dateAsString;
	}
	
	class executeAddComments implements Runnable{
		String customerId = "";
		String customerName = "";
		String orderId = "";
		String lineItemId = "";
		String agentId = "";
		String providerId = "";
		String comments = "";
		public executeAddComments(String customerId, String customerName, String orderId,
				String lineItemId, String agentId, String providerId, String comments) {
			this.customerId = customerId;
			this.customerName = customerName;
			this.orderId = orderId;
			this.lineItemId = lineItemId;
			this.agentId = agentId;
			this.providerId = providerId;
			this.comments = comments;
		}

		@Override
		public void run() {
			String source = "Fulfillment";
			ErrorList errorList = new ErrorList();
			logger.debug("Adding Cust Notes, CustId: "+customerId+", Notes: "+comments);
			CustomerService.INSTANCE.saveCustomerInteraction(customerId, agentId, orderId, source, agentId+": "+comments,
					customerName, providerId, lineItemId, null, errorList);
			logger.info("Adding Cust Notes, CustId: "+customerId+", Error List Size : "+errorList.size());
		}
		
	}

}
