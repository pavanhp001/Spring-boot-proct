/**
 *
 */
package com.AL.factory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.AL.activity.impl.ActivityAllowProductPromotionStatusUpdate;
import com.AL.activity.lineitem.impl.ActivityLocateLineItem;
import com.AL.activity.lineitem.impl.ActivityUpdateStatusAttribute;
import com.AL.enums.LineItemStatus;
import com.AL.enums.OrderStatus;
import com.AL.ie.activity.impl.ActivityUpdateStatusHistory;
import com.AL.order.status.LineItemStatusFactory;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.LineItemAttribute;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.entity.User;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.service.OrderAgentService;
import com.AL.vm.vo.OrderChangeValueObject;

/**
 * @author ebthomas
 *
 */
public enum StatusFactory {
	INSTANCE;

	private static final String YES = "yes";
	private static final String STATUS_UPDATE = "StatusUpdate";
	private static final String IS_STATUS_UPDATED = "is_status_updated";
	private static final Logger logger = Logger.getLogger(StatusFactory.class);

	public enum ReasonCodes {

		new_sales("90", "New Sales");

		private String code;
		private String desc;

		private ReasonCodes(final String code, final String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

	}

	public static StatusRecordBean createNewSalesStatus(
			final SalesOrder orderBean, final String agentExternalId) {
		Calendar calNow = Calendar.getInstance();

		logger.debug("creating new order status entry");
		StatusRecordBean statusRecord = new StatusRecordBean();
		orderBean.setCurrentStatus(statusRecord);
		statusRecord.setStatus(OrderStatus.sales.name());
		statusRecord.setDateTimeStamp(calNow);
		statusRecord.setAgentExternalId(agentExternalId);

		List<String> reasons = new ArrayList<String>();
		reasons.add(StatusFactory.ReasonCodes.new_sales.getCode());
		statusRecord.setReasons(reasons);
		logger.debug("creating default status record ");

		return statusRecord;

	}

	public void backupCurrentStatusForRollback(SalesOrder salesOrder,
			final OrchestrationContext params) {
		try {

			if ((salesOrder != null) && (salesOrder.getCurrentStatus() != null)) {
				logger.debug("backing up current status:"
						+ salesOrder.getExternalId() + " "
						+ salesOrder.getCurrentStatus().toString());

				params.add(TaskContextParamEnum.priorStatus.name(),
						salesOrder.getCurrentStatus());

			} else {

				logger.info("current status is null.  creating default 'sales' status");

				User user = (User) params.get(TaskContextParamEnum.agentId
						.name());
				StatusRecordBean srb = null;

				if (user != null) {
					srb = StatusFactory.INSTANCE
							.create(user, OrderStatus.sales);
				} else {
					srb = StatusFactory.INSTANCE
							.create(user, OrderStatus.sales);
				}
				params.add(TaskContextParamEnum.priorStatus.name(), srb);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void persistCurrentStatusRecord(
			final OrderManagementDao orderManagementDao,
			final SalesOrder orderBean, final StatusRecordBean statusRecord) {
		orderManagementDao.saveCurrentStatus(statusRecord);
		orderManagementDao.update(orderBean);

	}

	public void persistHistoricalStatusRecord(
			final OrderManagementDao orderManagementDao,
			final SalesOrder orderBean, final StatusRecordBean statusRecord) {
		orderManagementDao.saveCurrentStatus(statusRecord);
		orderManagementDao.update(orderBean);

	}
	
	@SuppressWarnings("unchecked")
	public void changeLineItemToRequestedStatus(
			final OrderManagementDao orderManagementDao,
			final OrderAgentService agentService, final SalesOrder salesOrder,
			final List<Integer> reasons, final OrchestrationContext params,
			final User agent, final String finalStatus, final Calendar changedAt) {
		logger.debug("update requested status" + salesOrder.getExternalId());
		List<String> lineItemIds = (List<String>) params
				.get(TaskContextParamEnum.lineItemList.name());
		logger.info("Processing status update for the lineItems: lineItemIds Size: " + lineItemIds.size() );
		for (String lineItemId : lineItemIds) {
			LineItem liToChange = ActivityLocateLineItem.INSTANCE.execute(
					lineItemId, salesOrder);
			if (liToChange != null) {
				logger.info("Processing lineitem status change for lineitem ext id : " + liToChange.getExternalId());
				logger.debug("Saving final status for lineItem: " + liToChange.getExternalId() + " :status: " + finalStatus);
				if (finalStatus != null) {
					logger.debug("Requested lineitem status : " + finalStatus);
					StatusRecordBean newStatus = LineItemStatusFactory.INSTANCE
							.createNewStatus(agentService, salesOrder,
									finalStatus, reasons, agent);
					boolean isAllowPromotionUpdate = (ActivityAllowProductPromotionStatusUpdate.INSTANCE
							.process(liToChange, newStatus.getStatus()));
					if (isAllowPromotionUpdate) {
						logger.info("status change is " + newStatus.getStatus()
								+ ".  sales Order:" + salesOrder.getExternalId()
								+ " LineItem externalId:" + liToChange.getExternalId());
						// set the "is_status_updated" attribute value to "no" for 
						// lineitems for which status is not being updated and keep them in a set
						Set<LineItemAttribute> statusAttributes = ActivityUpdateStatusAttribute.
								INSTANCE.getStatusAttributes(lineItemIds, salesOrder);
						// update current status and historic status and attributes of the lineItem
						updateAndPersistLineItemStatus(orderManagementDao, salesOrder,
								liToChange, newStatus, Boolean.FALSE);
						// update current status, historic status, attribute of the current lineItem
						// and "is_status_updated" attribute of other lineItems
						orderManagementDao.updateLineItemStatusAndAttribute(liToChange, statusAttributes);
					}
				}
			}
		}
	}

	final static List<String> EMPTY_INTERIM_STATUS_LIST = new ArrayList<String>();

	public LineItemStatus convert(final String status) {

		if (status.equals("submitted")) {
			return LineItemStatus.sales_submitted;
		}
		return LineItemStatus.valueOf(status);

	}

	public void updateAndPersistLineItemStatus(
			final OrderManagementDao orderManagementDao,
			final SalesOrder salesOrder, final LineItem lineItem,
			final StatusRecordBean newStatus, final boolean doPersist) {
		Calendar calNow = Calendar.getInstance();
		if (lineItem.getHistoricStatus() == null) {
			logger.info("No historic status exists. Creating lineitem  historic status:"
					+ lineItem.getExternalId());
			List<StatusRecordBean> historicStatusList = new ArrayList<StatusRecordBean>();
			lineItem.setHistoricStatus(historicStatusList);
		}
		ActivityUpdateStatusHistory.INSTANCE.addStatusRecordHistory(lineItem,
				lineItem.getCurrentStatus());
		//DateTime will not be set if req is from harmony
		if(newStatus.getDateTimeStamp() == null){
			newStatus.setDateTimeStamp(calNow);
		}
		lineItem.setCurrentStatus(newStatus);
		lineItem.getHistoricStatus().add(newStatus);
		//Set attribute "is_status_updated" to "yes" for DW
		Set<LineItemAttribute> liAttribs = lineItem.getLineItemAttribute();
		if(liAttribs == null || liAttribs.size() == 0){
			logger.debug("Adding IS_STATUS_UPDATED attribute for updated lineitem");
			LineItemAttribute attrib = new LineItemAttribute();
			attrib.setName(IS_STATUS_UPDATED);
			attrib.setSource(STATUS_UPDATE);
			attrib.setValue(YES);
			liAttribs.add(attrib);
		}else{

			LineItemAttribute extAttrib = getLineItemAttribute(liAttribs);
			if(extAttrib != null){
				logger.debug("Updating IS_STATUS_UPDATED attribute for updated lineitem");
				extAttrib.setValue(YES);
				liAttribs.add(extAttrib);
			}else{
				logger.debug("Adding IS_STATUS_UPDATED attribute for updated lineitem");
				LineItemAttribute attrib = new LineItemAttribute();
				attrib.setName(IS_STATUS_UPDATED);
				attrib.setSource(STATUS_UPDATE);
				attrib.setValue(YES);
				liAttribs.add(attrib);
			}

		}


		if (doPersist) {
			orderManagementDao.updateLineItemStatus(lineItem);
		}
	}

	private LineItemAttribute getLineItemAttribute(
			Set<LineItemAttribute> liAttribs) {
		if(liAttribs != null && liAttribs.size() > 0){
			for(LineItemAttribute attrib : liAttribs){
				if(attrib.getSource().equalsIgnoreCase(STATUS_UPDATE) && attrib.getName().equalsIgnoreCase(IS_STATUS_UPDATED)){
					return attrib;
				}
			}
		}
		return null;
	}

	public void updateAndPersistAggregatedOrderStatus(
			final OrderManagementDao orderManagementDao,
			final SalesOrder salesOrder, final StatusRecordBean newStatus,
			final boolean doPersist) {
		Calendar calNow = Calendar.getInstance();
		StatusRecordBean currentOrderStatus = salesOrder.getCurrentStatus();
		salesOrder.setCurrentStatus(null);

		if (currentOrderStatus == null) {

			currentOrderStatus = newStatus;
		}

		if (salesOrder.getHistoricStatus() == null) {
			logger.info("no historic status exists.  creating  historic status:"
					+ salesOrder.getExternalId());
			List<StatusRecordBean> historicStatusList = new ArrayList<StatusRecordBean>();
			salesOrder.setHistoricStatus(historicStatusList);
		}

		logger.info("resolving order status:" + salesOrder.getExternalId());
		String resolvedStatus = OrderStatus
				.resolveStatus(newStatus.getStatus());
		newStatus.setStatus(resolvedStatus);
		logger.info(resolvedStatus + " <-- resolving order status:"
				+ salesOrder.getExternalId());

		currentOrderStatus.setDateTimeStamp(calNow);
		// orderBean.getHistoricStatus().add(currentOrderStatus);

		salesOrder.setCurrentStatus(newStatus);

		if (doPersist) {
			orderManagementDao.updateOrderStatus(salesOrder);
		}
	}

	public StatusRecordBean create(final User agent,
			final OrderStatus orderStatus) {
		Calendar calNow = Calendar.getInstance();
		StatusRecordBean statusRecord = new StatusRecordBean();
		statusRecord.setStatus(orderStatus.name());
		statusRecord.setAgentExternalId(agent.getUserLogin());
		statusRecord.setAgent(agent);
		statusRecord.setDateTimeStamp(calNow);

		return statusRecord;
	}

	public StatusRecordBean create(final OrderStatus orderStatus) {
		final User agent = getDefaultUser();
		Calendar calNow = Calendar.getInstance();
		StatusRecordBean statusRecord = new StatusRecordBean();
		statusRecord.setStatus(orderStatus.name());
		statusRecord.setAgentExternalId(agent.getUserLogin());
		statusRecord.setAgent(agent);
		statusRecord.setDateTimeStamp(calNow);

		return statusRecord;
	}

	public void createStatus(User agent, final LineItem lineitem,
			final LineItemStatus lineItemStatus, final List<String> reasons) {

		if (agent == null) {
			agent = getDefaultUser();
		}

		Calendar calNow = Calendar.getInstance();
		StatusRecordBean newStatus = new StatusRecordBean();
		newStatus.setStatus(lineItemStatus.name());

		newStatus.setAgentExternalId(agent.getUserLogin());
		newStatus.setAgent(agent);
		newStatus.setDateTimeStamp(calNow);

		lineitem.setCurrentStatus(newStatus);

		if (lineitem.getHistoricStatus() == null) {
			lineitem.setHistoricStatus(new ArrayList<StatusRecordBean>());
		}
		lineitem.getHistoricStatus().add(newStatus);

		if (newStatus.getReasons() == null) {
			newStatus.setReasons(new ArrayList<String>());
		}
		newStatus.getReasons().addAll(reasons);

	}

	public void createSubmitFailedStatus(User agent, final LineItem lineitem) {

		if (agent == null) {
			agent = getDefaultUser();
		}

		if (lineitem.getCurrentStatus() == null) {

			LineItemStatus lineItemStatus = LineItemStatus.submit_failed;

			List<String> reasons = new ArrayList<String>();
			reasons.add("9001");
			createStatus(agent, lineitem, lineItemStatus, reasons);

		}

	}

	public void createInitialStatus(User agent, final LineItem lineitem) {

		if (agent == null) {
			agent = getDefaultUser();
		}

		List<String> reasons = new ArrayList<String>();
		reasons.add("5001");

		createStatus(agent, lineitem, LineItemStatus.sales_pre_order, reasons);

		if (lineitem.getState() != -1) {
			createStatus(agent, lineitem, LineItemStatus.sales_new_order,
					reasons);
		}

	}

	public StatusRecordBean createNewStatus(final OrderAgentService agentService,
			final SalesOrder orderBean,
			final OrderChangeValueObject orderChangeValueObject,
			final User agent) {

		final OrderStatus orderStatus = OrderStatus
				.valueOf(orderChangeValueObject.getStatus());
		logger.debug("creating new order status entry");

		StatusRecordBean statusRecord = StatusFactory.INSTANCE.create(agent,
				orderStatus);

		if (orderChangeValueObject.getReasonListAsString() == null) {
			statusRecord.setReasons(Collections.EMPTY_LIST);
		} else {
			statusRecord.setReasons(orderChangeValueObject
					.getReasonListAsString());
		}

		return statusRecord;

	}

	public static User getDefaultUser() {
		User user = new User();
		user.setUserId(1);
		user.setUserLogin("default");
		user.setUserUpdatedDate(Calendar.getInstance().getTime());

		user.setUserName("default");

		return user;

	}

}
