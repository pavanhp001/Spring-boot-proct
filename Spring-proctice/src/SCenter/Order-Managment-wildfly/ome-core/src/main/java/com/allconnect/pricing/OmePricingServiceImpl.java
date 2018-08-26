package com.AL.pricing;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.AL.codes.Reason;
import com.AL.codes.Status;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.PricingRequestResponseDocument;
import com.AL.xml.v4.ProcessingMessage;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.StatusType;
import com.AL.xml.v4.PricingRequestResponseDocument.PricingRequestResponse;
import com.AL.xml.v4.PricingRequestResponseDocument.PricingRequestResponse.Response;
import com.AL.xml.v4.StatusType.ProcessingMessages;

@Component
public class OmePricingServiceImpl implements OmePricingService{

	private static final Logger logger = Logger.getLogger(OmePricingServiceImpl.class);
	
    private static final String ENTITY_SYP= "SYP";
	private static final String UPDATED_BY_ATTRIB_NAME = "updatedBy";
	private static final String SESSION_STATUS_ATTRIBUTE_NAME = "sessionStatus";
	private static final String SESSION_STATUS_ATTRIBUTE_VALUE = "completed";


	public PricingRequestResponseDocument processPricingRequest(
			PricingRequestResponseDocument incomingRequestResponseDocument) {
		logger.info("Started processing OmePricingRequest");
		PricingRequestResponseDocument outboundPricingReqRespDocument = PricingRequestResponseDocument.Factory
				.newInstance();
		PricingRequestResponse incomingRequestResponse = 
				incomingRequestResponseDocument.getPricingRequestResponse();
		List<OrderType> orderPriceRequests = incomingRequestResponse
				.getRequest().getPricingRequestOrderElementList();
		PricingServiceStatus serviceStatus = new PricingServiceStatus();
		String GUID = null;
		try {	
			GUID = incomingRequestResponse.getGUID();
			serviceStatus.setGuid(GUID);
            String updatedByValue = getSalesContextUpdatedByVal(incomingRequestResponse.getSalesContext());
			List<PricedOrderStatus> orderStatuses = calculateTotalOrderPrice(orderPriceRequests, 
					updatedByValue, incomingRequestResponse.getSalesContext());
			serviceStatus.setPricedOrderStatuses(orderStatuses);
			// Determine final service response messages
			serviceStatus.determineResponseMessages();
		} catch (Exception exp) {
			logger.error("Error Processing OmePricing Request : " + exp);
			exp.printStackTrace();
			serviceStatus.setStatus(Status.Pricing.FATAL_REQUEST_FAILED);
			serviceStatus.setReason(Reason.Pricing.UNKNOWN_REASON);
			serviceStatus.getMessages().add(exp.getMessage());
		}
		buildResponseFromPricedOrders(outboundPricingReqRespDocument,
				serviceStatus, incomingRequestResponse.getSalesContext());
		logger.info("Finished processing OmePricingRequest");
		return outboundPricingReqRespDocument;
	}

	/**
	 * Method to calculate just total order price
	 * 
	 * @param orders
	 * @param GUID
	 * 
	 * @return list of validated and priced orders
	 * @throws PricingException
	 */
	private List<PricedOrderStatus> calculateTotalOrderPrice(
			final List<OrderType> orders, String updatedByValue, SalesContextType salesContext) throws Exception {
		List<PricedOrderStatus> singleOrderResponses = new ArrayList<PricedOrderStatus>();

		// Perform checks for nulls
		for (OrderType order : orders) {
			PricedOrderStatus pricedOrderStatus = new PricedOrderStatus();
			pricedOrderStatus.setPricedOrder(order);

			PricingCalculator.INSTANCE.calculateTotalOrderPrice(order, updatedByValue, salesContext);
			pricedOrderStatus
					.setStatus(Status.Pricing.INFO_ORDER_PRICED_SUCCESSFULLY);

			singleOrderResponses.add(pricedOrderStatus);
		}

		return singleOrderResponses;
	}

	/**
	 * Builds the response from the list of priced orders in the helper class
	 * PricedOrdersResponse.
	 * 
	 * @param pricingRequestRespDoc
	 * @param serviceStatus
	 *            pricedOrderResponse
	 * @param salesContextType
	 * 
	 * @return pricingReqRespDoc
	 */
	private PricingRequestResponseDocument buildResponseFromPricedOrders(
			final PricingRequestResponseDocument pricingRequestRespDoc,
			final PricingServiceStatus serviceStatus,
			SalesContextType salesContextType) {
		logger.info("Building XML Response ...");

		// Add the Request/Response and status elements to the doc.
		PricingRequestResponse pricingRequestResponse = pricingRequestRespDoc
				.addNewPricingRequestResponse();

		// Set the GUID for the response
		pricingRequestResponse.setGUID(serviceStatus.getGuid());

		// Depending on the outcome of the processing, we build the response
		// message.
		// If we processed this properly, we'll attempt to build the response of
		// priced orders
		Response response = pricingRequestResponse.addNewResponse();

		// To be used in the setResponseStatus() below.
		pricingRequestResponse.addNewStatus();

		// Now create the response for each order.
		for (PricedOrderStatus currOrderResponse : serviceStatus
				.getPricedOrderStatuses()) {
			OrderType newResponseOrder = response
					.addNewPricingResponseOrderElement();
			newResponseOrder.setTotalPriceInfo(currOrderResponse
					.getPricedOrder().getTotalPriceInfo());
			newResponseOrder.setLineItems(currOrderResponse.getPricedOrder()
					.getLineItems());
		}

		// Now set the overall status of the entire service request back in the
		// response.
		setResponseStatus(pricingRequestRespDoc.getPricingRequestResponse(),
				serviceStatus.getStatus(), serviceStatus.getReason(),
				serviceStatus.getMessages());

		// set salescontext from input to response xml
		pricingRequestRespDoc.getPricingRequestResponse().setSalesContext(
				salesContextType);

		return pricingRequestRespDoc;
	}

	private void setResponseStatus(
			final PricingRequestResponse requestResponse,
			final Status.Pricing pricingStatus,
			final Reason.Pricing pricingReason, final List<String> messages) {
		// Set the status type accordingly
		StatusType statusType = requestResponse.getStatus();

		if (statusType == null) {
			statusType = requestResponse.addNewStatus();
		}

		statusType.setStatusCode(Integer.parseInt(pricingStatus.getCode()));
		statusType.setStatusMsg(pricingStatus.toString());

		// We don't always specify a reason code.
		if (pricingReason == null) {
			return;
		}

		// Set the reason code for the status in the processMessages.
		ProcessingMessages processingMessages = requestResponse.getStatus()
				.getProcessingMessages();

		if (processingMessages == null) {
			processingMessages = requestResponse.getStatus()
					.addNewProcessingMessages();
		}

		// Check if any messages even exist. We only create them if we need
		// them.
		if (messages != null && messages.size() > 0) {
			for (String msg : messages) {
				ProcessingMessage responseMessage = processingMessages
						.addNewMessage();
				responseMessage.setText(msg);
			}
		} else {
			// No messages. So just stamp the response with the version number.
			// Good to go.
			ProcessingMessage responseMessage = processingMessages
					.addNewMessage();
			responseMessage.setText("");

		}
	}
	
	/**
     * Method to validate whether the 
     * 		request is from ExtProcess or not
     * 
     * @param salesContext
     * 
     * @return Boolean.TRUE if pricing request is 
     * 			from ExtProcess otherwise Boolean.FALSE
     */
    private String getSalesContextUpdatedByVal(SalesContextType salesContext) {

    	if(salesContext != null){
    		for(SalesContextEntityType sceType : salesContext.getEntityList()) {
    			if(sceType.getName() != null && sceType.getName().equalsIgnoreCase(ENTITY_SYP)) {
    				for(NameValuePairType nvType : sceType.getAttributeList()) {
    					if(nvType.getName().equalsIgnoreCase(UPDATED_BY_ATTRIB_NAME)) {
    						return nvType.getValue();
    					}
    				}
    			}
    		}
    	}
    	return "";
    }
    
    public boolean isSessionStatusCompleted(SalesContextType salesContext) {
		boolean isSessionStatusCompleted = false;
		if((salesContext != null) && (salesContext.getEntityList()!= null)) {
			entityListFor : for(SalesContextEntityType entity : salesContext.getEntityList()) {
				if(ENTITY_SYP.equalsIgnoreCase(entity.getName()) && (entity.getAttributeList() != null)) {
					for(NameValuePairType nameValuePair : entity.getAttributeList()) {
						if((nameValuePair != null) && 
								SESSION_STATUS_ATTRIBUTE_NAME.equalsIgnoreCase(nameValuePair.getName()) 
								&& SESSION_STATUS_ATTRIBUTE_VALUE.equalsIgnoreCase(nameValuePair.getValue())) {
							isSessionStatusCompleted = true;
							break entityListFor;
						}
					}
				}
			}
		}
		return isSessionStatusCompleted;
	}

}
