package com.AL.ws.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.AL.ie.task.IeTask;
import com.AL.ie.task.IeTaskBase;
import com.AL.ie.task.impl.TaskCreditQualification;
import com.AL.ie.task.impl.TaskGetProductCatalog;
import com.AL.ie.task.impl.TaskIeUpdateOrder;
import com.AL.ie.task.impl.TaskOrderDateQualification;
import com.AL.ie.task.impl.TaskOrderQualification;
import com.AL.ie.task.impl.TaskOrderStatus;
import com.AL.ie.task.impl.TaskOrderSubmission;
import com.AL.ie.task.impl.TaskServiceQualification;
import com.AL.util.OmeSpringUtil;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

public enum RealTimeActivity {

	INSTANCE;

	static {
		//Real Time Integration TASKS
		loadIETasks();
	}

	public static Map<String, Class<? extends IeTaskBase<OrderManagementRequestResponseDocument>>> ieTasks;

	private static Logger logger = Logger.getLogger(RealTimeActivity.class);

	public static void loadIETasks() {
		ieTasks = new HashMap<String, Class<? extends IeTaskBase<OrderManagementRequestResponseDocument>>>();
		ieTasks.put("creditQualification", TaskCreditQualification.class);
		ieTasks.put("getProductCatalog", TaskGetProductCatalog.class);
		ieTasks.put("orderDateQualification", TaskOrderDateQualification.class);
		ieTasks.put("orderQualification", TaskOrderQualification.class);
		ieTasks.put("orderStatus", TaskOrderStatus.class);
		ieTasks.put("orderSubmission", TaskOrderSubmission.class);
		ieTasks.put("serviceQualification", TaskServiceQualification.class);
		ieTasks.put("updateOrder", TaskIeUpdateOrder.class);
	}

	/**
	 * @param currentTaskName
	 *            taskname
	 * @return attempt to create seam version of the task
	 */
	@SuppressWarnings("unchecked")
	public IeTask<OrderManagementRequestResponseDocument> getComponentOmeIE(
			final String currentTaskName) {

		logger.debug("resolving " + currentTaskName);

		IeTask<OrderManagementRequestResponseDocument> currentTask = null;

		try {

			logger.debug("getting from tasks list:" + currentTaskName);
			Class<?> taskClazz = ieTasks.get(currentTaskName);

			logger.debug("task class getting spring context:"
					+ taskClazz.getCanonicalName());
			currentTask = (IeTask<OrderManagementRequestResponseDocument>) OmeSpringUtil.INSTANCE
					.getBean(taskClazz);

			if (currentTask != null) {
				logger.debug("found task in spring context");
				return currentTask;

			}
			if (currentTask == null) {
				logger.debug("not found in spring context performing jndi lookup on:"
						+ currentTaskName);
			}

			logger.info("unable to find task:" + currentTaskName);

			return null;
		} catch (Exception e) {
			logger.info(e);

			return null;
		}
	}

}
