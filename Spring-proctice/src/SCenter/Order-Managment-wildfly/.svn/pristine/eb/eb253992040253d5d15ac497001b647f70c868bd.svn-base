package com.AL.ie.ws.impl;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.AL.ie.task.impl.TaskCreditQualification;
import com.AL.ie.task.impl.TaskGetProductCatalog;
import com.AL.ie.task.impl.TaskOrderDateQualification;
import com.AL.ie.task.impl.TaskOrderQualification;
import com.AL.ie.task.impl.TaskOrderStatus;
import com.AL.ie.task.impl.TaskOrderSubmission;
import com.AL.ie.task.impl.TaskServiceQualification;
import com.AL.ie.task.impl.TaskIeUpdateOrder;
import com.AL.ie.ws.IeWSHandler;
import com.AL.ie.task.IeTask;
import com.AL.ie.task.IeTaskBase;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.StatusType;
import com.AL.util.OmeSpringUtil;

/**
 * @author ebthomas
 * 
 */

@Component("ieWSHandler")
public final class IntegratedEngineWSHandler extends IeHandlerBase implements
		IeWSHandler<OrderManagementRequestResponseDocument> {
 
 

	private static Logger logger = Logger
			.getLogger(IntegratedEngineWSHandler.class);
	public static Map<String, Class<? extends IeTaskBase<OrderManagementRequestResponseDocument>>> tasks;
	
	private static final OmeSpringUtil omeSpringUtil =   OmeSpringUtil.INSTANCE;

	static {
		tasks = new HashMap<String, Class<? extends IeTaskBase<OrderManagementRequestResponseDocument>>>();
		tasks.put("creditQualification", TaskCreditQualification.class);
		tasks.put("getProductCatalog", TaskGetProductCatalog.class);
		tasks.put("orderDateQualification", TaskOrderDateQualification.class);
		tasks.put("orderQualification", TaskOrderQualification.class);
		tasks.put("orderStatus", TaskOrderStatus.class);
		tasks.put("orderSubmission", TaskOrderSubmission.class);
		tasks.put("serviceQualification", TaskServiceQualification.class);
		tasks.put("updateOrder", TaskIeUpdateOrder.class); 
	}

	/**
	 * Private Constructor.
	 */
	public IntegratedEngineWSHandler() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument execute(final OrderManagementRequestResponseDocument doc) {
		if (doc == null) {
			throw new NullPointerException(
					"valid value required for order management request/response document");
		}

		IeTask<OrderManagementRequestResponseDocument> currentTask = null;

		try {
			String currentTaskName = doc.getOrderManagementRequestResponse()
					.getTransactionType().toString();

			currentTask = getComponent(currentTaskName);

			if (currentTask == null) {
				logger.debug("cannot get component as jndi creating object");
				Class<? extends IeTaskBase<OrderManagementRequestResponseDocument>> clazz;
				clazz = tasks.get(currentTaskName);
				currentTask = clazz.newInstance();
				logger.debug("created pojo version of the task:"
						+ currentTaskName);
			}

			OrderManagementRequestResponseDocument response = currentTask
					.execute(doc);

			return response;
		} catch (Exception e) {
			StatusType status = doc.addNewOrderManagementRequestResponse()
					.addNewStatus();
			status.setStatusCode(1);
			status.setStatusMsg(e.getMessage());

			return doc;
		}

	}

	/**
	 * @param currentTaskName
	 *            taskname
	 * @return attempt to create seam version of the task
	 */
	@SuppressWarnings("unchecked")
	private IeTask<OrderManagementRequestResponseDocument> getComponent(
			final String currentTaskName) {

		logger.debug("resolving " + currentTaskName);

		IeTask<OrderManagementRequestResponseDocument> currentTask = null;

		try {

			logger.debug("getting from tasks list:" + currentTaskName);
			Class<?> taskClazz = tasks.get(currentTaskName);
 
			logger.debug("task class getting spring context:"
					+ taskClazz.getCanonicalName());
			currentTask = (IeTask<OrderManagementRequestResponseDocument>) omeSpringUtil
					.getBean(taskClazz);
 
		 
			if (currentTask != null)
			{
				logger.debug("found task in spring context");
				return currentTask;
				
			}
			if (currentTask == null) {
				logger.debug("not found in spring context performing jndi lookup on:" + currentTaskName);
			}

			logger.debug("unable to find task:" + currentTaskName);

			return null;
		} catch (Exception e) {
			logger.debug(e);

			return null;
		}
	}

}
