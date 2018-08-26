package com.AL.ws.activity;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import com.AL.task.Task;
import com.AL.task.TaskBase;
import com.AL.task.TaskEnum;
import com.AL.task.impl.TaskAddLineItem;
import com.AL.task.impl.TaskCreateOrder;
import com.AL.task.impl.TaskDelete;
import com.AL.task.impl.TaskGetAllAccountHolders;
import com.AL.task.impl.TaskGetLineItemStatus;
import com.AL.task.impl.TaskGetOrder;
import com.AL.task.impl.TaskGetOrderByConfirmationNumber;
import com.AL.task.impl.TaskGetOrderByCustomer;
import com.AL.task.impl.TaskGetOrderByDate;
import com.AL.task.impl.TaskGetOrderByLineItem;
import com.AL.task.impl.TaskGetOrderByProvider;
import com.AL.task.impl.TaskGetOrderByScheduleDate;
import com.AL.task.impl.TaskGetOrderByStatus;
import com.AL.task.impl.TaskJob;
import com.AL.task.impl.TaskProcessOrderEvent;
import com.AL.task.impl.TaskRules;
import com.AL.task.impl.TaskSearchOrder;
import com.AL.task.impl.TaskSubmit;
import com.AL.task.impl.TaskUpdateLineItem;
import com.AL.task.impl.TaskUpdateLineItemLW;
import com.AL.task.impl.TaskUpdateLineItemStatus;
import com.AL.task.impl.TaskUpdateLineItemStatusLW;
import com.AL.task.impl.TaskUpdateOrder;
import com.AL.util.OmeSpringUtil;
import com.AL.ws.impl.WSHandlerBase;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

public enum OrderManagementActivity {

	INSTANCE;
	
 
	
	static { 
			// OME TASKS
			loadOMETasks(); 
	}
	
	public static void loadOMETasks() {

		tasks = new HashMap<String, Class<? extends TaskBase<OrderManagementRequestResponseDocument>>>();
		tasks.put(TaskEnum.createOrder.name(), TaskCreateOrder.class);
		tasks.put(TaskEnum.delete.name(), TaskDelete.class);
		tasks.put(TaskEnum.addLineItem.name(), TaskAddLineItem.class); 
		tasks.put(TaskEnum.updateLineItemStatus.name(),
				TaskUpdateLineItemStatus.class);
		tasks.put(TaskEnum.getOrder.name(), TaskGetOrder.class);
		tasks.put(TaskEnum.updateOrder.name(), TaskUpdateOrder.class);
		tasks.put(TaskEnum.updateLineItem.name(), TaskUpdateLineItem.class);
		tasks.put(TaskEnum.getOrderByCustomer.name(),
				TaskGetOrderByCustomer.class);
		tasks.put(TaskEnum.getOrderByConfirmationNumber.name(),
				TaskGetOrderByConfirmationNumber.class);
		tasks.put(TaskEnum.getOrderByDate.name(), TaskGetOrderByDate.class);
		tasks.put(TaskEnum.submit.name(), TaskSubmit.class);
		tasks.put(TaskEnum.eventNotification.name(),
				TaskProcessOrderEvent.class);
		tasks.put(TaskEnum.getOrderByLineItem.name(),
				TaskGetOrderByLineItem.class);
		tasks.put(TaskEnum.getOrderByProvider.name(),
				TaskGetOrderByProvider.class);
		tasks.put(TaskEnum.rules.name(), TaskRules.class);
		tasks.put(TaskEnum.getOrderByStatus.name(), TaskGetOrderByStatus.class);
		tasks.put(TaskEnum.getOrderByScheduleDate.name(),
				TaskGetOrderByScheduleDate.class);
		tasks.put(TaskEnum.taskJob.name(), TaskJob.class);
		tasks.put(TaskEnum.orderSearch.name(), TaskSearchOrder.class);
		tasks.put(TaskEnum.getAllAccountHolders.name(), TaskGetAllAccountHolders.class);
		tasks.put(TaskEnum.updateLineItemLW.name(), TaskUpdateLineItemLW.class);
		tasks.put(TaskEnum.updateLineItemStatusLW.name(), TaskUpdateLineItemStatusLW.class);
		tasks.put(TaskEnum.getLineItemStatus.name(), TaskGetLineItemStatus.class);
	}
	
	private static Logger logger = Logger
	.getLogger(OrderManagementActivity.class);
	
	public static Map<String, Class<? extends TaskBase<OrderManagementRequestResponseDocument>>> tasks;
	
	/**
	 * @param currentTaskName
	 *            taskname
	 * @return attempt to create seam version of the task
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public Task<OrderManagementRequestResponseDocument> getComponentOmeTask(
			final String currentTaskName) {

		logger.info("resolving " + currentTaskName);
		String jndiName = WSHandlerBase.transformTaskName(currentTaskName);
		Task<OrderManagementRequestResponseDocument> currentTask = null;

		try {

			logger.info("getting from order management tasks list:"
					+ currentTaskName);
			Class<?> taskClazz = tasks.get(currentTaskName);

			if (taskClazz == null) {
				logger.debug("not found in order management tasks list:"
						+ currentTaskName);
				return null;
			}
			logger.debug("found task class getting spring context:"
					+ taskClazz.getCanonicalName());
			currentTask = (Task<OrderManagementRequestResponseDocument>) OmeSpringUtil.INSTANCE
					.getBean(taskClazz);

			if (currentTask != null) {
				logger.debug("found task in spring context:" + currentTaskName);
				return currentTask;

			}

			logger.info("attempting JNDI lookup:" + currentTaskName);
			if (currentTask == null) {
				logger.debug("not found in spring context performing jndi lookup on."
						+ currentTaskName);

				Context jndiContext = new InitialContext();
				currentTask = (Task<OrderManagementRequestResponseDocument>) jndiContext
						.lookup("ome-app/" + jndiName + "/local");

				if (currentTask != null) {
					logger.debug("found.jndi.component." + currentTaskName);
					logger.debug("validate entity manager initialized for"
							+ currentTaskName);

					return currentTask;
				}
			}
			logger.info("unable to find task " + currentTaskName);
			return null;

		} catch (IllegalArgumentException iae) {
			logger.error(iae);
			throw iae;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
	}
	
	
	
	
}
