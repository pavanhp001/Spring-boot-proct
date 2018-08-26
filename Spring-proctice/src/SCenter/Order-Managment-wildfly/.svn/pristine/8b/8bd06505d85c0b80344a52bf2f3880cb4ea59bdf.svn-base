package com.AL.ie.activity.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.enums.LineItemStatus;
import com.AL.ie.activity.Activity;
import com.AL.ie.domain.TransientLineItemContainer;
import com.AL.ie.factory.ArbiterStatusFactory;
import com.AL.ie.service.strategy.ArbiterFlow;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.OrchestrationParamName;
import com.AL.validation.Message;
import com.AL.validation.ValidationReport;
import com.AL.validation.impl.DefaultValidationReport;
import com.AL.V.beans.entity.BusinessParty;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.entity.User;
import com.AL.Vdao.dao.BusinessPartyDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.arbiter.converter.ArbiterMarshaller;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.NotificationEventCollectionType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.ProviderLineItemStatusType;
import com.AL.xml.v4.ProviderLineItemStatusType.LineItemStatusCode;
import com.AL.xml.v4.ProviderLineItemStatusType.ProcessingStatusCode;
import com.AL.xml.v4.TransientResponseContainerType;
import com.AL.xml.v4.TransientResponseType;

@Component("activityArbiterResponse")
public class ActivityArbiterResponse implements Activity {

    private Logger logger = Logger.getLogger(ActivityArbiterResponse.class);
    private static final String TOKEN_OBJECT = "TOKEN_OBJECT";

    @Autowired
    private BusinessPartyDao businessPartyDao;

    @Autowired
    private OrderManagementDao orderManagementDao;

    final int SCHEDULE_NON_RTIM_PROVIDER_INTEGRATION = 2;
    final int EXTERNAL_PROCESS_PROVIDER_INTEGRATION = 0;

    private static final String CKO = "CKO";
    private static final String TRUE = "true";
    private static final String IS_FALLOUT_ORDER = "isFalloutOrder";

    @SuppressWarnings("unchecked")
	public void process(final OrchestrationContext params) {
		String agentId = (String) params.get(TaskContextParamEnum.agentId.name());
		User agent = orderManagementDao.findAgentById(agentId);
		SalesOrder salesOrder = (SalesOrder) params.get(TaskContextParamEnum.salesOrder.name());
		Map<String, String> lineItemStatus = new HashMap<String, String>();
		ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> flow = null;
		flow = (ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument>) params.get(TaskContextParamEnum.arbiterFlow.name());
		if (flow == null) {
		    return;
		}
		logger.debug("ActivityArbiterResponse:process():flow.getRequest(): " + flow.getRequest());
		logger.debug("ActivityArbiterResponse:process():flow.getResponse(): " + flow.getResponse());
		logger.trace("ActivityArbiterResponse:process():flow.getLineItemCollectionTypeList().size(): " + flow.getLineItemCollectionTypeList().size());
		TransientLineItemContainer transientLineItemContainer = ArbiterMarshaller.INSTANCE.createAggregateTemplate(flow.getLineItemCollectionTypeList());
		// Copy the request XML to be used as the response
		OrderManagementRequestResponseDocument copyOfOriginal = cloneByXMLCopy(flow.getRequest());
		// update
		updateLineItemsWithTransientInfo(params, transientLineItemContainer, copyOfOriginal);
		flow.setResponse(copyOfOriginal);
		OrderManagementRequestResponseDocument payload = copyOfOriginal;
		if ((flow != null) && (flow.getResponse() != null)) {
		    payload = flow.getResponse();
		}
		else if (flow != null) {
		    payload = flow.getRequest();
		}
		if (payload == null) {
		    return;
		}
		OrderManagementRequestResponse omRR = copyOfOriginal.getOrderManagementRequestResponse();
		if (omRR != null) {
		    Request requestWithTransient = omRR.getRequest();
		    Object requestAsObj = params.get(TaskContextParamEnum.request.name());
			if (requestAsObj instanceof Request) {
				Request originalTaskRequest = (Request) requestAsObj;
				if ((requestWithTransient != null) && (requestWithTransient.getOrderInfo() != null) && 
					(requestWithTransient.getOrderInfo().getLineItems() != null) && 
					(requestWithTransient.getOrderInfo().getLineItems().getLineItemList() != null)) {
				    OrderType ot = requestWithTransient.getOrderInfo();
					LineItemCollectionType liCollectionType = ot.getLineItems();
					List<LineItemType> liCollection = liCollectionType.getLineItemList();
					for (LineItemType lineItem : liCollection) {
					    int activeIndex = lineItem.getLineItemNumber();
					    long activeExternalId = lineItem.getExternalId();
					    logger.debug("examining transient response status- line item: " + activeIndex);
					    TransientResponseContainerType integrationTransientResponse = lineItem.getTransientResponseContainer();
					    boolean isRTIMLineItem = (integrationTransientResponse != null);
					    String providerExternalId = ActivityGetProvider.INSTANCE.getProvider(lineItem);
					    Boolean isFallBackOrder = Boolean.FALSE;
					    if (providerExternalId != null) {
							logger.info("Validating  fall out order [Provider : " + providerExternalId + "]");
							logger.info("Validating  fall out order [lineItem.getExternalId() : " + lineItem.getExternalId() + "]");
							isFallBackOrder = isFalloutOrder(lineItem);
							logger.info("Is fall out order : " + isFallBackOrder);
					    }
					    if (!isRTIMLineItem) {
							logger.info("Processing non-rtim lineitems");
							BusinessParty businessParty = null;
							if ((businessPartyDao != null) && (providerExternalId != null)) {
							    businessParty = businessPartyDao.findBusinessPartyById(providerExternalId);
							    if (businessParty != null) {
									if (businessParty.isRealtimeProvider() == SCHEDULE_NON_RTIM_PROVIDER_INTEGRATION) {
									    updateFulfillmentStatus(originalTaskRequest, activeExternalId, salesOrder);
									}
									else if (isFallBackOrder) { // Processing VZ fallout orders via FA
									    updateFulfillmentStatus(originalTaskRequest, activeExternalId, salesOrder);
									}
									else if (businessParty.isRealtimeProvider() == EXTERNAL_PROCESS_PROVIDER_INTEGRATION) {
									    updateExternalProcessStatus(originalTaskRequest, activeExternalId, salesOrder);
									}
							    }
							    else {
							    	logger.warn("Businessparty not found for provider ext id : " + providerExternalId);
							    }
							}
					    } else if (isRTIMLineItem) {
							logger.info("Processing RTIM lineitems");
							logger.debug("line item : " + activeIndex + " contains transient response:" + integrationTransientResponse.toString());
							TransientResponseType trans = integrationTransientResponse.getTransientResponse();
							if (trans != null) {
							    ProviderLineItemStatusType providerLineItemStatusType = trans.getProviderLineItemStatus();
							    String providerConfNo = trans.getOrderNumber();
							    if ((providerLineItemStatusType != null) && (salesOrder != null)) {
									logger.debug("adding status information to response validation report");
									List<LineItem> liList = salesOrder.getLineItems();
									for (LineItem li : liList) {
									    if (li.getLineItemNumber() == activeIndex) {
											updateContextWithProcessingStatus(li, activeIndex, params, providerLineItemStatusType.getProcessingStatusCode(),
												providerLineItemStatusType.getLineItemStatusCode(),
												providerLineItemStatusType.getReasonList());
											lineItemStatus.put(providerLineItemStatusType.getLineItemStatusCode().toString(), TOKEN_OBJECT);
											StatusRecordBean providerSRB = ActivityStatusFromArbiterResult.INSTANCE.resolveLineItemStatus(providerLineItemStatusType,
												params, agent);
					 						if (providerSRB.getStatus() != null) {
											    logger.info("Derived LineItem Status : " + providerSRB.getStatus());
											}
											ActivityUpdateStatusHistory.INSTANCE.addStatusRecordHistory(li, providerSRB);
											logger.info("Setting lineitem status : " + providerSRB.getStatus() + " for li ext id : " + li.getExternalId());
											li.setCurrentStatus(providerSRB);
											//orderManagementDao.saveCurrentStatus(providerSRB);
											if (providerConfNo != null) {
											    logger.debug("Provider confirmation number = " + providerConfNo);
											    li.setProviderConfirmationNumber(providerConfNo);
											}
											
											//condition added for avoiding multiple updateLineitemStatus on lineitem when ordersubmit gets failed
											if (!ProcessingStatusCode.ERROR.equals(providerLineItemStatusType.getProcessingStatusCode())) {
												orderManagementDao.updateLineItemAndStatus(li);
											}
											break;
									    }
									}
							    } else {
							    	logger.warn("RTIM did not sent back provider lineitem status type");
							    }
							}
					    }
					}
				}
			}
		    params.add(OrchestrationParamName.arbiterResponse.name(), flow.getResponse());
		    params.add(OrchestrationParamName.lineItemStatusMap.name(), lineItemStatus);
		}
	}

    private void updateExternalProcessStatus(Request requestWithTransient, long activeExternalId, SalesOrder salesOrder) {
		List<LineItem> liList = salesOrder.getLineItems();
		List<String> requestLineItemIdList = requestWithTransient.getLineitemIdList();
		// Tweaking the code related to lineItemId or lineitemId list
		if (requestWithTransient.getLineItemId() != null && !requestWithTransient.getLineItemId().equals("")) {
		    if (requestLineItemIdList != null) {
		    	requestLineItemIdList.add(requestWithTransient.getLineItemId());
		    } else {
				requestLineItemIdList = new ArrayList<String>();
				requestLineItemIdList.add(requestWithTransient.getLineItemId());
		    }
		}
		searchLoop: for (LineItem li : liList) {
		    if ((li.getExternalId().longValue() == activeExternalId) && 
		    		(!LineItemStatus.cancelled_removed.name().equals(li.getCurrentStatus().getStatus()))) {
				for (String requestLineItemId : requestLineItemIdList) {
				    long requestLineItemIdAsLong = Long.valueOf(requestLineItemId);
				    // If this lineitem is one in the order that was submitted.
				    // Update
				    if (requestLineItemIdAsLong == li.getExternalId().longValue()) {
						String agentId = requestWithTransient.getAgentId();
						User agent = orderManagementDao.findAgentById(agentId);
						StatusRecordBean providerSRB = ArbiterStatusFactory.INSTANCE.create(agent, LineItemStatus.provision_ready);
						ActivityUpdateStatusHistory.INSTANCE.addStatusRecordHistory(li, li.getCurrentStatus());
						li.setCurrentStatus(providerSRB);
						orderManagementDao.saveCurrentStatus(providerSRB);
						ActivityUpdateStatusHistory.INSTANCE.addStatusRecordHistory(li, providerSRB);
						orderManagementDao.update(li);
						break searchLoop;
				    }
				}
		    }
		}
    }

    private void updateFulfillmentStatus(Request requestWithTransient, long activeExternalId, SalesOrder salesOrder) {
		List<LineItem> liList = salesOrder.getLineItems();
		List<String> requestLineItemIdList = requestWithTransient.getLineitemIdList();
		// Tweaking the code related to lineItemId or lineitemId list
		if (requestWithTransient.getLineItemId() != null && !requestWithTransient.getLineItemId().equals("")) {
		    if (requestLineItemIdList != null) {
		    	requestLineItemIdList.add(requestWithTransient.getLineItemId());
		    } else {
				requestLineItemIdList = new ArrayList<String>();
				requestLineItemIdList.add(requestWithTransient.getLineItemId());
		    }
		}
		searchLoop: for (LineItem li : liList) {
		    if (li.getExternalId().longValue() == activeExternalId) {
			for (String requestLineItemId : requestLineItemIdList) {
			    long requestLineItemIdAsLong = Long.valueOf(requestLineItemId);
			    // If this lineitem is one in the order that was submitted.
			    // Update
			    if ((requestLineItemIdAsLong == li.getExternalId().longValue() && 
			    		(!LineItemStatus.cancelled_removed.name().equals(li.getCurrentStatus().getStatus())))) {
					String agentId = requestWithTransient.getAgentId();
					User agent = orderManagementDao.findAgentById(agentId);
					StatusRecordBean providerSRB = ArbiterStatusFactory.INSTANCE.create(agent, LineItemStatus.provision_ready);
					ActivityUpdateStatusHistory.INSTANCE.addStatusRecordHistory(li, li.getCurrentStatus());
					li.setCurrentStatus(providerSRB);
					orderManagementDao.saveCurrentStatus(providerSRB);
					ActivityUpdateStatusHistory.INSTANCE.addStatusRecordHistory(li, providerSRB);
					orderManagementDao.update(li);
					break searchLoop;
			    }
			}
		    }
		}
    }

    public void updateLineItemsWithTransientInfo(final OrchestrationContext params, TransientLineItemContainer transientLineItemContainer,
	    OrderManagementRequestResponseDocument copyOfOriginal) {
		logger.info("Updating lineitem with transient response");
		if ((copyOfOriginal != null) && (copyOfOriginal.getOrderManagementRequestResponse() != null) 
				&& (copyOfOriginal.getOrderManagementRequestResponse().getRequest() != null)
			&& (copyOfOriginal.getOrderManagementRequestResponse().getRequest().getOrderInfo() != null)
			&& (copyOfOriginal.getOrderManagementRequestResponse().getRequest().getOrderInfo().getLineItems() != null)
			&& (copyOfOriginal.getOrderManagementRequestResponse().getRequest().getOrderInfo().getLineItems().getLineItemList() != null)) {
		    logger.debug("ActivityArbiterResponse:updateLineItemsWithTransientInfo():copyOfOriginal: " + copyOfOriginal.xmlText());
		    List<LineItemType> liCollection = copyOfOriginal.getOrderManagementRequestResponse().getRequest().getOrderInfo().getLineItems().getLineItemList();
			for (LineItemType origLineItem : liCollection) {
				int activeIndex = origLineItem.getLineItemNumber();
				logger.debug("processing line item " + activeIndex + " with transient response");
				if (transientLineItemContainer == null) {
				    continue;
				}
				LineItemType updatedLineItem = transientLineItemContainer.get((int) origLineItem.getExternalId());
				if (updatedLineItem != null) {
				    NotificationEventCollectionType notEventCollType = origLineItem.getNotificationEvents();
				    logger.debug("ActivityArbiterResponse:updateLineItemsWithTransientInfo():notEventCollType: " + notEventCollType.toString());
				    updatedLineItem.setNotificationEvents(notEventCollType);
				} else {
				    logger.debug("ActivityArbiterResponse:updateLineItemsWithTransientInfo():updatedLineItem == null");
				}
				if (ArbiterMarshaller.INSTANCE.isAvailableForProviderProcessing(updatedLineItem)) {
				    TransientResponseContainerType providerTransientResponse = null;
				    if (updatedLineItem != null) {
						logger.info("line item " + activeIndex + " using transient container from provider");
						providerTransientResponse = updatedLineItem.getTransientResponseContainer();
						if ((providerTransientResponse != null) && (providerTransientResponse.getTransientResponse() != null)
							&& (providerTransientResponse.getTransientResponse().getProviderLineItemStatus() != null)
							&& (providerTransientResponse.getTransientResponse().getProviderLineItemStatus().getProcessingStatusCode() != null)) {
						    ProviderLineItemStatusType providerLIStatusType = providerTransientResponse.getTransientResponse().getProviderLineItemStatus();
						    if (providerLIStatusType.equals(ProcessingStatusCode.ERROR)) {
						    	logger.warn("Errors processing line item:" + activeIndex + " ERROR");
						    }
						}
				    } else {
						logger.debug("line item " + activeIndex + " generating default transient container");
						providerTransientResponse = TransientResponseContainerType.Factory.newInstance();
						TransientResponseType trt = providerTransientResponse.addNewTransientResponse();
						ProviderLineItemStatusType status = trt.addNewProviderLineItemStatus();
						status.setDateTimeStamp(Calendar.getInstance());
						status.setProcessingStatusCode(ProviderLineItemStatusType.ProcessingStatusCode.INFO);
						status.setLineItemStatusCode(ProviderLineItemStatusType.LineItemStatusCode.ORDER_INFO_REQUIRED);
						String[] reasons = { "generated transient response", "generated default line item status", "no transient response from provider" };
						status.setReasonArray(reasons);
				    }
				    origLineItem.setTransientResponseContainer(providerTransientResponse);
				} else {
				    logger.info("line item " + activeIndex + " not eligible for update with transient response");
				}
			}
		}
    }

    public OrderManagementRequestResponseDocument cloneByXMLCopy(final OrderManagementRequestResponseDocument doc) {
		XmlObject obj = doc.copy();
		return (OrderManagementRequestResponseDocument) obj;
    }

    public void updateContextWithProcessingStatus(LineItem lineItem, int activeIndex, 
    		final OrchestrationContext parameterContainer, ProcessingStatusCode.Enum processingStatus,
	    LineItemStatusCode.Enum lineItemStatus, List<String> reasons) {
		ValidationReport report = (ValidationReport) parameterContainer.get(OrchestrationParamName.statusReport.name());
		if (report == null) {
		    report = new DefaultValidationReport();
		    parameterContainer.add(OrchestrationParamName.statusReport.name(), report);
		}
		logger.info("Processing status : " + processingStatus);
		if (ProcessingStatusCode.INFO.equals(processingStatus)) {
		    report.addMessage(Message.Type.INFO, Long.valueOf(Message.Type.INFO.getCode()), "Line Item Number:" + activeIndex + " - Status :" + lineItemStatus.toString()
			    + "- reason:" + reasons.toString());
		}
		else if (ProcessingStatusCode.ERROR.equals(processingStatus)) {
		    logger.info("Processing status is ERROR from RTIM");
		    realTimeSubmitFailed(lineItem, parameterContainer);
		    report.addMessage(Message.Type.ERROR, Long.valueOf(Message.Type.ERROR.getCode()), "Line Item Number:" + activeIndex + " " + lineItemStatus.toString() + "- reason:"
			    + reasons.toString());
		}
    }

    public void realTimeSubmitFailed(LineItem li, final OrchestrationContext parameterContainer) {
		logger.info("Updating li status to submit_failed as provider status is error");
		String agentId = (String) parameterContainer.get(TaskContextParamEnum.agentId.name());
		User agent = orderManagementDao.findAgentById(agentId);
		StatusRecordBean providerSRB = ArbiterStatusFactory.INSTANCE.create(agent, LineItemStatus.submit_failed);
		ActivityUpdateStatusHistory.INSTANCE.addStatusRecordHistory(li, li.getCurrentStatus());
		li.setCurrentStatus(providerSRB);
		orderManagementDao.saveCurrentStatus(providerSRB);
		ActivityUpdateStatusHistory.INSTANCE.addStatusRecordHistory(li, providerSRB);
		orderManagementDao.update(li);
    }

    public static Boolean isFalloutOrder(LineItemType liType) {
		Boolean isFallout = false;
		if (liType != null && liType.getLineItemAttributes() != null && liType.getLineItemAttributes().getEntityList() != null) {
		    List<AttributeEntityType> attribsEntList = liType.getLineItemAttributes().getEntityList();
		    if (attribsEntList != null) {
				for (AttributeEntityType entType : attribsEntList) {
				    if (entType != null && entType.getSource() != null && entType.getSource().equalsIgnoreCase(CKO)) {
						List<AttributeDetailType> attrDetailList = entType.getAttributeList();
						if (attrDetailList != null && !attrDetailList.isEmpty()) {
						    for (AttributeDetailType dtlType : attrDetailList) {
								if (dtlType.getName().equalsIgnoreCase(IS_FALLOUT_ORDER) && dtlType.getValue().equalsIgnoreCase(TRUE)) {
								    isFallout = Boolean.TRUE;
								    break;
								}
						    }
						}
				    }
				}
		    }
		}
		return isFallout;
    }

}
