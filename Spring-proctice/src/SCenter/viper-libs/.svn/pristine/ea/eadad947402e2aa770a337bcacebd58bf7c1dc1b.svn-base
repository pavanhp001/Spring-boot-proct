package com.A.vm.arbiter.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import com.A.factory.ProviderLineItemStatusFactory;
import com.A.ie.domain.TransientLineItem;
import com.A.ie.domain.TransientLineItemContainer;
import com.A.util.NotificationEvents;
import com.A.xml.common.ApplicationType;
import com.A.xml.common.ClientInfo;
import com.A.xml.common.RequestContext;
import com.A.xml.common.RtimError;
import com.A.xml.common.TransactionType;
import com.A.xml.v4.CustomerDocument.Customer;
import com.A.xml.v4.LineItemCollectionType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.NotificationEventType;
import com.A.xml.v4.OrderManagementRequestResponseDocument;
import com.A.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.A.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.A.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.ProviderLineItemStatusType;
import com.A.xml.v4.TransientResponseContainerType;
import com.A.xml.v4.TransientResponseType;
import com.A.xml.v4.ProviderLineItemStatusType.ProcessingStatusCode;
import com.A.xml.v4.orderFulfillment.OrderFulfillmentRequest;
import com.A.xml.v4.orderFulfillment.OrderFulfillmentRequestDocument;
import com.A.xml.v4.orderFulfillment.OrderFulfillmentResponse;
import com.A.xml.v4.rtimRequestResponse.RtimRequestResponse;
import com.A.xml.v4.rtimRequestResponse.RtimRequestResponseDocument;

public enum ArbiterMarshaller {

	INSTANCE;

	private static Logger logger = Logger.getLogger(ArbiterMarshaller.class);

	public OrderType getOrder(OrderManagementRequestResponseDocument oRRD) {
		OrderManagementRequestResponse oRR = oRRD
				.getOrderManagementRequestResponse();
		Request oReq = oRR.getRequest();

		OrderType orderType = oReq.getOrderInfo();

		return orderType;
	}

	private String endTag = "</ac:lineItems>";

	private String startTagWithNamespace = "<ac:lineItems xmlns:ac=\"http://xml.A.com/v4\">";
	private String startTag = "<ac:lineItems>";

	public String extractLineItemsResponseContainer(String doc,
			String originalNamespace) {

		String response = "<ac:response>";
		int responseIndex = doc.indexOf(response);
		String namespace = "ac";
		String parentElement = "lineItems";
		String element = "lineItem";
		int beginIndex = doc.indexOf(startTag, responseIndex);
		int endIndex = doc.indexOf(endTag, responseIndex);

		String container = "";

		if ((beginIndex > -1) && (endIndex > -1)) {
			container = doc.substring(beginIndex + startTag.length(), endIndex);

		} else {
			container = "<" + namespace + ":" + parentElement + "  xmlns:"
					+ namespace + "=\"http://xml.A.com/v4\"></"
					+ namespace + ":" + parentElement + ">";
		}

		// container = container.replaceAll("\\\r", "");
		container = container.replaceAll("\\\n", "");
		container = container.replaceAll("\\\t", "");
		container = container.replaceAll("<" + namespace + ":" + element + ">",
				"<" + namespace + ":" + element + " xmlns:" + namespace
						+ "=\"http://xml.A.com/v4\" >");

		return container.replaceAll(originalNamespace + ":", "" + namespace
				+ ":");
	}

	public List<OrderType> createSplitTemplate(OrderType order) {

		OrderType emptyOrder = clearLineItems(order);
		List<OrderType> result = new ArrayList<OrderType>();

		List<LineItemType> lits = order.getLineItems().getLineItemList();
		logger.debug("number of line items:" + lits.size());

		for (LineItemType lit : lits) {

			if (isAvailableForProviderProcessing(lit)) {
				String currentProvider = getProviderName(lit, lits);
				logger.debug(" order external Id:" + order.getExternalId()
						+ " line item number:" + lit.getLineItemNumber()
						+ " provider name:" + currentProvider);

				OrderType emptyOrderToBeSentToProvider = (OrderType) emptyOrder.copy();
				
				//Add Single line item to empty order
				//Possible future enhancement is to add provider groups; if required.
				emptyOrderToBeSentToProvider.getLineItems().getLineItemList().add(lit);
				
				addLineItemCustInfoToOrder(  emptyOrderToBeSentToProvider,   lit);

				result.add(emptyOrderToBeSentToProvider);
			}
		}

		return result;
	}

	public void addLineItemCustInfoToOrder(OrderType ot, LineItemType lit) {

		if ((lit != null) && (lit.getCustomer() != null)) {
			Customer customer = lit.getCustomer();
			if (ot.getCustomerInformation() == null) {
				ot.addNewCustomerInformation();

			}
			ot.getCustomerInformation().setCustomer(customer);
		}
	}

	public String getProviderName(final LineItemType lit,
			final List<LineItemType> lits) {

		String businessParty = "";
		if (lit != null) {

			String lineItem = lit.toString();

			if (lineItem != null) {
				String PROVIDER_START = "<provider>";
				String PROVIDER_END = "</provider>";
				String NAME_START = "<name>";
				String NAME_END = "</name>";

				int start = lineItem.indexOf(PROVIDER_START);
				int end = lineItem.indexOf(PROVIDER_END);

				if ((start == -1) || (end == -1)) {
					return businessParty;
				}

				String providerInfo = lineItem.substring(
						start + PROVIDER_START.length(), end);

				if (providerInfo != null) {
					start = providerInfo.indexOf(NAME_START);
					end = providerInfo.indexOf(NAME_END);

					businessParty = providerInfo.substring(
							start + NAME_START.length(), end);
					logger.debug("businessParty:" + businessParty);
				}
			}

		}

		return businessParty;

	}

	public OrderType clearLineItems(OrderType originalOrder) {
		OrderType resultOrder = (OrderType) originalOrder.copy();

		List<LineItemType> lits = resultOrder.getLineItems().getLineItemList();

		lits.clear();

		return resultOrder;
	}

	public List<XmlObject> cloneByXMLCopy(
			final OrderManagementRequestResponseDocument doc, int copies) {
		List<XmlObject> list = new ArrayList<XmlObject>();

		for (int i = 0; i < copies; i++) {
			XmlObject obj = doc.copy();

			list.add(obj);
		}

		return list;
	}

	public List<XmlObject> cloneBySerialize(
			final OrderManagementRequestResponseDocument doc, int copies) {

		List<XmlObject> list = new ArrayList<XmlObject>();

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(doc);
			byte[] bytes = baos.toByteArray();

			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(bytes));

			for (int i = 0; i < copies; i++) {

				XmlObject copy = (XmlObject) in.readObject();

				list.add(copy);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public boolean isAvailableForProviderProcessing(LineItemType lit) {

		logger.info("Validating if lineitem is available for provider processing");
		if ((lit != null) && (lit.getNotificationEvents() != null)
				&& (lit.getNotificationEvents().getEventList() != null)) {

			List<NotificationEventType> eventList = lit.getNotificationEvents()
					.getEventList();

			for (NotificationEventType event : eventList) {
				if (NotificationEvents.processByProvider.getId() == event
						.getCode()) {
					return Boolean.TRUE;
				}
			}
		} else {
			logger.debug("line item unavailable for processing: notification event not found on lineitem:");
		}

		return Boolean.FALSE;
	}

	public OrderFulfillmentRequestDocument onOrderMessage(
			final OrderManagementRequestResponseDocument orderDoc) {

		OrderFulfillmentRequestDocument fulFillDoc = OrderFulfillmentRequestDocument.Factory
				.newInstance();
		OrderFulfillmentRequest payload = fulFillDoc
				.addNewOrderFulfillmentRequest();

		payload.setOrderManagementRequestResponse(orderDoc
				.getOrderManagementRequestResponse());
		ApplicationType appType = payload.addNewApplicationType();
		appType.setApplicationType(com.A.xml.common.ApplicationType.ApplicationType2.NC);

		ClientInfo clientInfo = com.A.xml.common.ClientInfo.Factory
				.newInstance();
		clientInfo.setName("ATTSTI");
		clientInfo.setClientId("A");

		clientInfo.setRequestId(orderDoc.getOrderManagementRequestResponse()
				.getCorrelationId());
		RequestContext requestContext = payload.addNewContext();
		requestContext.setClientInfo(clientInfo);
		requestContext.setCorrelationId(orderDoc
				.getOrderManagementRequestResponse().getCorrelationId());
		requestContext.setProviderId("ATTSTI");
		requestContext.setSessionId(orderDoc
				.getOrderManagementRequestResponse().getSessionId());

		requestContext.setChannell("12345");

		requestContext.setRegion((orderDoc.getOrderManagementRequestResponse()
				.getRegion()));
		requestContext.setSalesCode((orderDoc
				.getOrderManagementRequestResponse().getSalesCode()));
		requestContext.setAffiliateName((orderDoc
				.getOrderManagementRequestResponse().getAffiliateName()));

		requestContext.setOrderNumber(String.valueOf(orderDoc
				.getOrderManagementRequestResponse().getRequest()
				.getOrderInfo().getExternalId()));

		if (orderDoc.getOrderManagementRequestResponse()
				.getTransactionTimeStamp() != null) {
			requestContext.setRequestDate(orderDoc
					.getOrderManagementRequestResponse()
					.getTransactionTimeStamp().toString());
		}

		if (OrderManagementRequestResponse.TransactionType.ORDER_QUALIFICATION
				.equals(orderDoc.getOrderManagementRequestResponse()
						.getTransactionType())) {
			requestContext
					.setTransactionType(TransactionType.ORDER_QUALIFICATION);
		} else if (OrderManagementRequestResponse.TransactionType.ORDER_SUBMISSION
				.equals(orderDoc.getOrderManagementRequestResponse()
						.getTransactionType())) {
			requestContext
					.setTransactionType(TransactionType.SUBMIT_ORDER_TO_PROVIDER);
		}

		return fulFillDoc;

	}

	public OrderType getOrder(OrderFulfillmentRequest oRRD) {
		OrderManagementRequestResponse oRR = oRRD
				.getOrderManagementRequestResponse();
		Request oReq = oRR.getRequest();

		return oReq.getOrderInfo();
	}

	public TransientLineItemContainer createAggregateTemplate(
			List<LineItemCollectionType> lineItemCollectionList) {

		TransientLineItemContainer resultContainer = new TransientLineItemContainer();
		
		if (lineItemCollectionList.size() == 0) {
			return null;
		}

		for (LineItemCollectionType lineItemCollection : lineItemCollectionList) {

			List<LineItemType> litList = lineItemCollection.getLineItemList();
			
			if (litList != null) {
				logger.debug("ArbiterMarshaller:createAggregateTemplate():litList != null: litList Size: " + litList.size());
				for (LineItemType lit : litList) {
					if (lit != null) {
						resultContainer.add(lit);
					}
				}
			}
		}
		
		logger.debug("ArbiterMarshaller:createAggregateTemplate():resultContainer(0): " + resultContainer.getTransientResponseContainer(0).xmlText());
		
		return resultContainer;
	}

	public LineItemCollectionType getLineItemCollectionType(
			RtimRequestResponseDocument doc,
			TransientResponseContainerType transientResponseContainer) {

		LineItemCollectionType lict = null;

		if (doc == null) {
			doc = RtimRequestResponseDocument.Factory.newInstance();
		}

		if (doc != null) {

			if (doc.getRtimRequestResponse() == null) {
				RtimRequestResponse rrr = doc.addNewRtimRequestResponse();
				rrr.setResponse(OrderFulfillmentResponse.Factory.newInstance());
			}

			if (!(doc.getRtimRequestResponse().getResponse() instanceof OrderFulfillmentResponse)) {
				doc = RtimRequestResponseDocument.Factory.newInstance();
				RtimRequestResponse rrr = doc.addNewRtimRequestResponse();
				rrr.setResponse(OrderFulfillmentResponse.Factory.newInstance());
			}

			if (doc.getRtimRequestResponse() != null) {
				OrderFulfillmentResponse fulfillmentResponse = (OrderFulfillmentResponse) doc
						.getRtimRequestResponse().getResponse();

				if (fulfillmentResponse.getOrderManagementRequestResponse() == null) {
					fulfillmentResponse.addNewOrderManagementRequestResponse();
				}

				if (fulfillmentResponse != null) {
					OrderManagementRequestResponse omeRequestResponse = fulfillmentResponse
							.getOrderManagementRequestResponse();

					if (omeRequestResponse.getResponse() == null) {
						omeRequestResponse.addNewResponse();
					}

					if ((omeRequestResponse != null)
							&& (omeRequestResponse.getResponse() != null)) {

						Response response = omeRequestResponse.getResponse();

						if (response.getOrderInfoList() == null) {
							response.addNewOrderInfo();
						}

						if (response.getOrderInfoList() != null) {
							List<OrderType> orderList = response
									.getOrderInfoList();

							if (orderList == null) {
								throw new IllegalArgumentException(
										"order document missing in response");
							}

							try {
								if ((orderList.size() == 0)
										&& (orderList.get(0) == null)) {
									orderList.add(OrderType.Factory
											.newInstance());
								}

							} catch (IndexOutOfBoundsException ioobe) {
								logger.warn("unable to access size:"
										+ ioobe.getMessage());

								if (orderList != null) {
									orderList.add(OrderType.Factory
											.newInstance());
								}
							}

							OrderType order = orderList.get(0);

							if (order.getLineItems() == null) {
								LineItemCollectionType newLict = order
										.addNewLineItems();

								newLict.addNewLineItem();
							}

							if ((order != null) && //
									(order.getLineItems() != null)) {

								lict = order.getLineItems();

								if ((lict != null)
										&& (lict.getLineItemList() != null)
										&& (lict.getLineItemList().size() != 0)) {

									//LineItemType lit = lict.getLineItemList()
									//		.get(0);
									//lit.setTransientResponseContainer(transientResponseContainer);
									
									List<LineItemType> lit = lict.getLineItemList();
									
									for (LineItemType li: lit)
									{
										if (transientResponseContainer.getLineItemId() != null && !transientResponseContainer.getLineItemId().equals("")) {
											if (li.getExternalId() == Long.valueOf(transientResponseContainer.getLineItemId())) {
												li.setTransientResponseContainer(transientResponseContainer);
											}
										}
									}
								}

							}
						}
					}
				}
			}

		}

		if (lict == null) {
			lict = LineItemCollectionType.Factory.newInstance();
		}

		return lict;

	}

	public TransientResponseContainerType retrieveTransientFromDocument(
			RtimRequestResponseDocument doc) {

		logger.debug("Retrieving transientResContainer from : "
				+ doc.toString());
		TransientResponseContainerType transientContainerType = null;

		if (doc != null) {

			try {

				if ((doc.getRtimRequestResponse() != null)
						&& (doc.getRtimRequestResponse().getResponse() instanceof OrderFulfillmentResponse)) {
					OrderFulfillmentResponse fulfillmentResponse = (OrderFulfillmentResponse) doc
							.getRtimRequestResponse().getResponse();

					if (fulfillmentResponse != null) {
						OrderManagementRequestResponse omeRequestResponse = fulfillmentResponse
								.getOrderManagementRequestResponse();

						if ((omeRequestResponse != null)
								&& (omeRequestResponse.getResponse() != null)) {

							Response response = omeRequestResponse
									.getResponse();
							if (response.getOrderInfoList() != null) {
								List<OrderType> orderList = response
										.getOrderInfoList();

								OrderType order = orderList.get(0);

								if ((order != null) && //
										(order.getLineItems() != null) && //
										(order.getLineItems().getLineItemList() != null)) {

									LineItemType lit = order.getLineItems()
											.getLineItemList().get(0);

									transientContainerType = lit
											.getTransientResponseContainer();

								}
							}
						}
					}
				} else {
					transientContainerType = TransientResponseContainerType.Factory
							.newInstance();
					transientContainerType.addNewTransientResponse();

				}
			} catch (ClassCastException cce) {
				transientContainerType = TransientResponseContainerType.Factory
						.newInstance();

				logger.error(cce.getMessage() + doc.toString(), cce);
			} catch (Exception e) {

				transientContainerType = TransientResponseContainerType.Factory
						.newInstance();

				logger.error(e.getMessage() + doc.toString(), e);
			}

		}

		if (transientContainerType == null) {
			transientContainerType = TransientResponseContainerType.Factory
					.newInstance();

		}

		return transientContainerType;

	}

	public TransientResponseContainerType createResponseTransient(
			RtimRequestResponseDocument doc) {

		TransientResponseContainerType transientContainerType = retrieveTransientFromDocument(doc);

		if (hasErrors(doc)) {

			processErrors(doc, transientContainerType);
		}
		
		logger.debug("Creating transientContainerType from : " + transientContainerType.xmlText());
		
		return transientContainerType;

	}

	public void processErrors(RtimRequestResponseDocument doc,
			TransientResponseContainerType transientContainerType) {

		List<RtimError> rtimErrors = doc.getRtimRequestResponse().getResponse()
				.getRtimErrors().getRtimErrorList();

		TransientResponseType response = transientContainerType
				.getTransientResponse();
		
		ProviderLineItemStatusType plist = null;
		
		if (response == null) {
			response = transientContainerType.addNewTransientResponse();
			
			for (RtimError error : rtimErrors) {
				plist = ProviderLineItemStatusFactory.INSTANCE.createRtimError(response,
						error);
				response.setProviderLineItemStatus(plist);
			}

		}		
	}

	public void processMissingTransient(RtimRequestResponseDocument doc,
			TransientResponseContainerType transientContainerType) {

		if ((doc == null)
				|| (doc.getRtimRequestResponse() == null)
				|| (doc.getRtimRequestResponse().getResponse() == null)
				|| (doc.getRtimRequestResponse().getResponse().getRtimErrors() == null)
				|| (doc.getRtimRequestResponse().getResponse().getRtimErrors()
						.getRtimErrorList() == null)) {
			return;
		}

		List<RtimError> rtimErrors = doc.getRtimRequestResponse().getResponse()
				.getRtimErrors().getRtimErrorList();

		TransientResponseType response = transientContainerType
				.addNewTransientResponse();

		if ((rtimErrors != null) && (rtimErrors.size() > 0)) {

			ProviderLineItemStatusType lineItemStatus = response
					.getProviderLineItemStatus();

			if (lineItemStatus == null) {
				lineItemStatus = response.addNewProviderLineItemStatus();
			}

			lineItemStatus.setProcessingStatusCode(ProcessingStatusCode.ERROR);
			lineItemStatus.setDateTimeStamp(Calendar.getInstance());
			lineItemStatus
					.setLineItemStatusCode(ProviderLineItemStatusType.LineItemStatusCode.FAILED);

			for (RtimError error : rtimErrors) {
				String errorMsg = " code:" + error.getErrorCode() + //
						" type:" + error.getErrorType() + //
						" msg:" + error.getErrorMessage();//

				lineItemStatus.addReason(errorMsg);
			}
		}

	}

	public boolean hasErrors(RtimRequestResponseDocument doc) {

		if ((doc != null)
				&& (doc.getRtimRequestResponse() != null)
				&& (doc.getRtimRequestResponse().getResponse() != null)
				&& (doc.getRtimRequestResponse().getResponse().getRtimErrors() != null)) {

			return Boolean.TRUE;

		}

		return Boolean.FALSE;

	}
}
