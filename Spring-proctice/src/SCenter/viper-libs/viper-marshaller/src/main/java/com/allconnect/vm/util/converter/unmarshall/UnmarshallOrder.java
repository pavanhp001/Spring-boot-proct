package com.A.vm.util.converter.unmarshall;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.A.util.XmlUtil;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.SalesOrder;
import com.A.V.beans.entity.SalesOrderContext;
import com.A.V.beans.entity.StatusRecordBean;
import com.A.V.beans.entity.User;
import com.A.vm.service.OrderAgentService;
import com.A.vm.service.PartyService;
import com.A.vm.util.converter.DynamicBuilder;
import com.A.vm.util.converter.mapper.OrderMapper;
import com.A.xml.v4.DateTimeOrTimeRangeType;
import com.A.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.A.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.A.xml.v4.OrderStatusHistoryType;
import com.A.xml.v4.OrderStatusWithTypeType;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.PriceInfoType;

/**
 * @author ebthomas
 *
 */
public class UnmarshallOrder extends UnmarshallBase<OrderType> {

	private static final Logger logger = Logger
			.getLogger(UnmarshallOrder.class);

	@Autowired(required = false)
	private OrderAgentService agentService;

	@Autowired
	private PartyService partyService;

	@Autowired
	private UnmarshallLineItem unmarshallLineItem;

	@Autowired
	private UnmarshallPriceInfo unmarshallPriceInfo;

	@Autowired
	private UnmarshallSalesOrderContext unmarshallSOContext;



	public UnmarshallOrder() {
		super();
	}

	/**
	 * @author ebthomas
	 *
	 */

	/**
	 * @param orderStatusSrc
	 *            Source
	 * @return Domain Status Record
	 */
	public StatusRecordBean copyStatusRecordBean(
			final OrderStatusWithTypeType orderStatusSrc) {
		StatusRecordBean dest = UnmarshallStatus
				.copyStatusRecordBean(orderStatusSrc);

		return dest;
	}

	/**
	 * @param historyContainer
	 *            Source
	 * @return Domain List of Status Record
	 */
	public List<StatusRecordBean> copyStatusRecordBeanList(
			final OrderStatusHistoryType historyContainer) {

		List<StatusRecordBean> statusRecordBeanList = new ArrayList<StatusRecordBean>();

		for (int i = 0; i < historyContainer.getPreviousStatusArray().length; i++)
			statusRecordBeanList.add(copyStatusRecordBean(historyContainer
					.getPreviousStatusArray()[i]));

		return statusRecordBeanList;
	}

	public SalesOrder copy(final OrderType salesOrderSource,
			final UnmarshallValidationEnum level) {
		SalesOrder salesOrderDestination = new SalesOrder();

		copy(salesOrderDestination, salesOrderSource, level);

		return salesOrderDestination;

	}

	public void customCopy(final SalesOrder salesOrderDestination,
			final OrderType orderType, final UnmarshallValidationEnum level) {
		// salesOrderDestination.setCurrentStatus(copyStatusRecordBean(orderType.getOrderStatus()));
		buildCurrentOrderStatus(salesOrderDestination, orderType);
		if (orderType.getOrderStatusHistory() != null) {
			salesOrderDestination
					.setHistoricStatus(copyStatusRecordBeanList(orderType
							.getOrderStatusHistory()));
		}
	}

	/**
	 * @param number
	 *            number that identifies a PhoneContactChannelBean
	 * @return gets the PhoneContactChannelBean identified by the number
	 */
	public SalesOrder copy(final SalesOrder salesOrderDestination,
			final OrderType salesOrderSource,
			final UnmarshallValidationEnum level) {
		DynamicBuilder<OrderType, SalesOrder> builder = new DynamicBuilder<OrderType, SalesOrder>(
				level);

		try {
			builder.copyInstanceAttributes(salesOrderSource,
					salesOrderDestination, OrderMapper.orderFields, false);

			if (salesOrderSource != null) {
				OrderType orderType = (OrderType) salesOrderSource;
				if (orderType.getMoveDate() != null) {
					Calendar moveDate = orderType.getMoveDate();
					if (moveDate != null) {
						salesOrderDestination.setMoveDate(moveDate);
					}
				}

				salesOrderDestination.setOrderDate(Calendar.getInstance());

				Calendar callbackWhen = null;
				if (!XmlUtil.isElementNull(orderType.newCursor(),
						"whenToCallBack")) {
					callbackWhen = orderType.getWhenToCallBack();
					salesOrderDestination.setWhenToCallBack(callbackWhen);
				}
				
				if (!XmlUtil.isElementNull(orderType.newCursor(), "isZipOnlySearch")) {
					int isZipOnlySearch = orderType.getIsZipOnlySearch();
					salesOrderDestination.setIsZipOnlySearch(isZipOnlySearch);
				}
				
				customCopy(salesOrderDestination, orderType, level);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(
					"unable.to.unmarshall.order.information");
		}

		return salesOrderDestination;
	}

	/**
	 * @param salesOrder
	 *            salesOrder
	 * @param orderType
	 *            orderType
	 * @param em
	 *            Entity Manager
	 */
	public void copyBasicOrderInfo(final SalesOrder salesOrder,
			final OrderType ucOrderType) {
		UnmarshallValidationEnum level = getValidationLevel(ucOrderType);
		level = UnmarshallValidationEnum.unconstrained;

		if ((salesOrder != null) && (ucOrderType != null)) {
			copy(salesOrder, ucOrderType, level);

			copyAgent(salesOrder, ucOrderType, level);
			copyReferrer(salesOrder, ucOrderType, level);

		}
	}

	public void copyReferrer(final SalesOrder salesOrder,
			final OrderType ucOrderType, final UnmarshallValidationEnum level) {

		String partyId = ucOrderType.getReferrerId();

		if ((partyId != null) && (isEligibleForCopy(level, partyId))) {

			salesOrder.setReferrer(partyService.findPartyById(partyId)
					.getExternalId());
		}
	}

	public void copyAgent(final SalesOrder salesOrder,
			final OrderType ucOrderType) {

		copyAgent(salesOrder, ucOrderType,
				UnmarshallValidationEnum.unconstrained);
	}

	public void copyAgent(final SalesOrder salesOrder,
			final OrderType ucOrderType, final UnmarshallValidationEnum level) {

		String agentId = ucOrderType.getAgentId() == null ? "-1" : ucOrderType.getAgentId();
		User agentBean = agentService.findAgentById(agentId.trim());
		if (agentBean == null) {
			throw new IllegalArgumentException("Agent Id not found : "
					+ agentId);
		}
		salesOrder.setAgentId(agentId);
	}

	/**
	 * @param order
	 *            SalesOrder
	 * @param orderType
	 *            input xml Order Type
	 * @param em
	 *            Entity Manager
	 * @return Unmarshalled SalesOrder
	 */
	public SalesOrder updateOrder(final SalesOrder order,
			final OrderType orderType, boolean isUpdateRequest) {

		if ((orderType == null) || (order == null)) {
			return null;
		}

		if (orderType != null) {

			if (!XmlUtil.isElementNull(orderType.newCursor(), "moveDate")) {
				Calendar moveDate = orderType.getMoveDate();
				order.setMoveDate(moveDate);
			}

			if (!XmlUtil.isElementNull(orderType.newCursor(), "whenToCallBack")) {
				Calendar callbackWhen = orderType.getWhenToCallBack();
				order.setWhenToCallBack(callbackWhen);
			}

			if (!XmlUtil.isElementNull(orderType.newCursor(), "source")) {
				String source = orderType.getSource();
				order.setSource(source);
			}


			if (!XmlUtil.isElementNull(orderType.newCursor(), "associatedWithMove")) {
				boolean associatedWithMove = orderType.getAssociatedWithMove();
				order.setAssociatedWithMove(associatedWithMove);
			}


			if (!XmlUtil.isElementNull(orderType.newCursor(), "campaignId")) {
				String campaignId = orderType.getCampaignId();
				order.setCampaignId(campaignId);
			}


			if (!XmlUtil.isElementNull(orderType.newCursor(), "AConfirmationNumber")) {
				String AConfirmationNumber = orderType.getAConfirmationNumber();
				order.setAConfirmNumber(AConfirmationNumber);
			}


			if (!XmlUtil.isElementNull(orderType.newCursor(), "ACustomerAccount")) {
				String ACustomerAccount = orderType.getACustomerAccountNumber();
				order.setAAccountNumber(ACustomerAccount);
			}

			if (!XmlUtil.isElementNull(orderType.newCursor(), "agentId")) {
				copyAgent( order,orderType,   UnmarshallValidationEnum.unconstrained);
			}
			
			if (!XmlUtil.isElementNull(orderType.newCursor(), "isZipOnlySearch")) {
				int isZipOnlySearch = orderType.getIsZipOnlySearch();
				logger.info("Updating ZipOnlySearch value : " + isZipOnlySearch);
				order.setIsZipOnlySearch(isZipOnlySearch);
			}
			
			// Add PUPUSA related entries
			if(!XmlUtil.isElementNull(orderType.newCursor(),  "programExternalId")){
				order.setProgramExternalId(orderType.getProgramExternalId());
			}
			if(!XmlUtil.isElementNull(orderType.newCursor(),  "ordersourceExternalId")){
				order.setOrdersourceExternalId(orderType.getOrdersourceExternalId());
			}
			if(!XmlUtil.isElementNull(orderType.newCursor(),  "inboundVdn")){
				order.setInboundVdn(orderType.getInboundVdn());
			}
			unmarshallPriceInfo.build(orderType.getTotalPriceInfo(),order.getTotalPrice() );
		}

		copyAgent(order, orderType);

		return order;
	}

	/**
	 * @param order
	 *            SalesOrder
	 * @param orderType
	 *            input xml Order Type
	 * @param em
	 *            Entity Manager
	 * @return Unmarshalled SalesOrder
	 */
	public SalesOrder doBuildOrder(final SalesOrder order,
			final OrderType orderType, boolean isUpdateRequest) {

		if ((orderType == null) || (order == null)) {
			return null;
		}
		copyBasicOrderInfo(order, orderType);

		PriceInfoType priceInfo = orderType.getTotalPriceInfo() != null ? orderType
				.getTotalPriceInfo() : orderType.addNewTotalPriceInfo();
		order.setTotalPrice(unmarshallPriceInfo.build(priceInfo));

		List<LineItem> lineItemBeanList = Collections.emptyList();
		if (orderType.getLineItems() != null) {
			lineItemBeanList = unmarshallLineItem.buildLineItem(
					orderType.getLineItems(), orderType.getAgentId());
		}

		order.setLineItems(lineItemBeanList);
		return order;

	}

	private void buildCurrentOrderStatus(final SalesOrder order,
			final OrderType orderType) {

		StatusRecordBean currentStatus = null;
		if (orderType.getOrderStatus() != null) {
			currentStatus = copyStatusRecordBean(orderType.getOrderStatus());
		} else {

			// If order status is not provided then its status would be set to
			// "SALES"
			currentStatus = new StatusRecordBean();
			currentStatus.setStatus("sales");
			List<String> reasonList = new ArrayList<String>();
			reasonList.add("0");
			currentStatus.setReasons(reasonList);
			currentStatus.setDateTimeStamp(Calendar.getInstance());
			if (orderType.getAgentId() != null) {
				currentStatus.setAgentExternalId(orderType.getAgentId());
			}

		}
		order.setCurrentStatus(currentStatus);
	}

	/**
	 * @param originalMessage
	 *            Source
	 * @param em
	 *            Entity Manager
	 * @return Sales Order Bean
	 */
	private SalesOrder doBuildOrder(final Response originalMessage,
			final EntityManager em, boolean isUpdateRequest) {

		OrderType responseOrder = originalMessage.getOrderInfoArray()[0];

		SalesOrder salesOrder = new SalesOrder();

		return doBuildOrder(salesOrder, responseOrder, isUpdateRequest);
	}

	/**
	 * @param originalMessage
	 *            Source
	 * @param em
	 *            Entity Manager
	 * @return Sales Order Bean
	 */
	private SalesOrder doBuildOrder(final Request originalMessage,
			boolean isUpdateRequest) {
		OrderType orderType = originalMessage.getOrderInfo();

		//Hack to set request level and order level agent id same
		if(originalMessage.getAgentId() != null && originalMessage.getAgentId().trim().length() > 0) {
		    orderType.setAgentId(originalMessage.getAgentId());
		}

		SalesOrder salesOrder = new SalesOrder();

		if (orderType == null) {
			return salesOrder;
		}

		if (originalMessage.getSalesContext() != null) {
			Set<SalesOrderContext> scList = unmarshallSOContext
					.build(originalMessage.getSalesContext());
			salesOrder.setSalesOrderContexts(scList);
		}

		// Enhancement for AM-1599 - Add PUPUSA related entries
		if(null != orderType.getProgramExternalId()){
			salesOrder.setProgramExternalId(orderType.getProgramExternalId());
		}
		if(null != orderType.getOrdersourceExternalId()){
			salesOrder.setOrdersourceExternalId(orderType.getOrdersourceExternalId());
		}
		if(null != orderType.getInboundVdn()){
			salesOrder.setInboundVdn(orderType.getInboundVdn());
		}
		return doBuildOrder(salesOrder, orderType, isUpdateRequest);
	}

	/**
	 * @param request
	 *            Source
	 * @return String value of Order Id
	 */
	public String buildConfirmationId(final Request request) {
		if (request == null) {
			throw new IllegalArgumentException(
					"null value not allowed in builder");
		}

		return request.getConfirmationNumber();
	}

	/**
	 * @param request
	 *            Source
	 * @return String value of Order Id
	 */
	public String buildCustomerId(final Request request) {
		if (request == null) {
			throw new IllegalArgumentException(
					"null value not allowed in builder");
		}

		return request.getCustomerId();
	}

	/**
	 * @param request
	 *            Source
	 * @return String value of Order Id
	 */
	public Calendar buildOrderScheduleDate(final Request request) {
		if (request == null) {
			throw new IllegalArgumentException(
					"null value not allowed in builder");
		}

		DateTimeOrTimeRangeType dt = request.getDateFilter();

		if (dt != null) {
			return dt.getDate();
		}

		return null;
	}

	/**
	 * @param request
	 *            Source
	 * @return String value of Order Id
	 */
	public Calendar buildOrderDate(final Request request) {
		if (request == null) {
			throw new IllegalArgumentException(
					"null value not allowed in builder");
		}

		DateTimeOrTimeRangeType dt = request.getDateFilter();

		if (dt != null) {
			return dt.getDate();
		}

		return null;
	}

	/**
	 * @param request
	 *            Source
	 * @return String value of Order Id
	 */
	public String buildOrderId(final Request request) {
		if (request == null) {
			throw new IllegalArgumentException("Invalid null request.");
		}

		return request.getOrderId();
	}

	/**
	 * @param request
	 *            Source
	 * @return String value of Order Id
	 */
	public String buildLineItemExternalId(final Request request) {
		if (request == null) {
			throw new IllegalArgumentException("Invalid null request.");
		}

		return request.getLineItemId();
	}

	/**
	 * @param request
	 *            Source
	 * @return String value of Order Id
	 */
	public String buildProviderExternalId(final Request request) {
		if (request == null) {
			throw new IllegalArgumentException("Invalid null request.");
		}
		return request.getProviderId();
	}

	/**
	 * @param request
	 *            Source
	 * @param entityManagerReference
	 *            Entity Manager
	 * @return Sales Order Bean
	 */
	public SalesOrder build(final Request request, boolean isUpdateRequest) {
		if (request == null) {
			throw new IllegalArgumentException("invalid null request");
		}

		return doBuildOrder(request, isUpdateRequest);

	}

	/**
	 * @param response
	 *            Source
	 * @param entityManagerReference
	 *            Entity Manager
	 * @return Sales Order Bean
	 */
	public SalesOrder build(final Response response,
			final EntityManager entityManagerReference, boolean isUpdateRequest) {
		if (response == null) {
			throw new IllegalArgumentException(
					"null value not allowed in builder");
		}

		return doBuildOrder(response, entityManagerReference, isUpdateRequest);

	}

	public OrderAgentService getAgentService() {
		return agentService;
	}

	public void setAgentService(OrderAgentService agentService) {
		this.agentService = agentService;
	}

	public PartyService getPartyService() {
		return partyService;
	}

	public void setPartyService(PartyService partyService) {
		this.partyService = partyService;
	}

	public UnmarshallLineItem getUnmarshallLineItem() {
		return unmarshallLineItem;
	}

	public void setUnmarshallLineItem(UnmarshallLineItem unmarshallLineItem) {
		this.unmarshallLineItem = unmarshallLineItem;
	}

}
