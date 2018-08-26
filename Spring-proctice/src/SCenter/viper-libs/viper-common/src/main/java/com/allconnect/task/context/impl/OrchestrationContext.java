/**
 *
 */
package com.A.task.context.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import com.A.validation.ValidationReport;
import com.A.task.TaskResultEnum;
import com.A.V.beans.entity.SalesOrder;

/**
 * @author ebthomas
 * 
 */
public interface OrchestrationContext {

	Object getSalesOrder();

	/**
	 * @param key
	 *            key
	 * @param item
	 *            item
	 */
	void add(final String key, final Object item);

	/**
	 * @param additionResponseList
	 *            Additional Responses List
	 */
	void addAll(final Map<String, Object> additionResponseList);

	/**
	 * @param broadcastMessage
	 *            Message to broadcast
	 */
	void addBroadcastMessage(final String broadcastMessage);

	/**
	 * clear.
	 */
	void clear();

	/**
	 * @return broadcast message
	 */
	String getBroadcastMessage();

	/**
	 * @param salesOrder
	 *            add sales order bean
	 * @return boolean indicating success of the process
	 */
	Boolean addSuccessful(final SalesOrder salesOrder);

	Boolean addSuccessful(final List<SalesOrder> salesOrderBeanList);

	/**
	 * @param em
	 *            setter for Entity Manager
	 * @return boolean indicating success of the process
	 */
	Boolean addEntityManager(final EntityManager em);

	/**
	 * Task Response Object. Contains the response result of the task
	 */

	/**
	 * @param key
	 *            key of added response
	 * @param responseItem
	 *            response item to add
	 */
	void addResponseItem(final String key, final Object responseItem);

	/**
	 * @param key
	 *            getter
	 * @return retrieved object matching key
	 */
	void putValidationReport(final ValidationReport report);

	/**
	 * @param key
	 *            getter
	 * @return retrieved object matching key
	 */
	ValidationReport getValidationReport();

	/**
	 * @param key
	 *            getter
	 * @return retrieved object matching key
	 */
	Object get(final String key);

	public String getRegion();

	public String getAffiliateName();

	public String getSalesCode();

	String getCorrelationId();

	int getTransactionId();
	
	public String getSessionId();

	Boolean isEmpty();

	String getExecutionContextFilter();

	Map<String, String> getRulesSelectionProperties();

	/**
	 * @return getter for task result enum
	 */
	public TaskResultEnum getTaskResultEnum();

	/**
	 * @param taskResultEnum
	 *            setter for task result enum
	 */
	void setTaskResultEnum(final TaskResultEnum taskResultEnum);

	/**
	 * @return getter for response list
	 */
	Map<String, Object> getResponseList();

	/**
	 * @param responseList
	 *            setter for response list
	 */
	void setResponseList(final Map<String, Object> responseList);

	/**
	 * @author ebthomas
	 * 
	 */

	public static final class Factory {
		/**
		 * Factory that creates Task Response.
		 */
		private Factory() {
			super();
		}

		/**
		 * @param e
		 *            Exception
		 * @param showStackTrace
		 *            indicates if to display stack trace in log.
		 * @return Task Response
		 */
		public static OrchestrationContext createOme(final Exception e,
				final Boolean showStackTrace) {
			if (showStackTrace) {
				e.printStackTrace();
			}

			return createOme(e);
		}

		/**
		 * @param e
		 *            Exception
		 * @return Task Response
		 */
		public static OrchestrationContext createOme(final Exception e) {
			OrchestrationContext taskResponse = new ResponseItemOme();

			return taskResponse;
		}

		/**
		 * @return Task Response
		 */
		public static OrchestrationContext create() {
			OrchestrationContext taskResponse = new ResponseItemBase();

			return taskResponse;
		}

		/**
		 * @return Task Response
		 */
		public static ResponseItemOme createOme() {
			ResponseItemOme taskResponse = new ResponseItemOme();

			return taskResponse;
		}

		/**
		 * @return Task Response
		 */
		public static ResponseItemOme createOme(
				final OrchestrationContext orchestrationContext) {
			ResponseItemOme taskResponse = new ResponseItemOme();

			taskResponse.addAll(orchestrationContext.getResponseList());

			return taskResponse;
		}

		/**
		 * @return Task Response
		 */
		public static ResponseItemCustomer createCustomer(
				final OrchestrationContext orchestrationContext) {
			ResponseItemCustomer taskResponse = new ResponseItemCustomer();

			taskResponse.addAll(orchestrationContext.getResponseList());

			return taskResponse;
		}

		public static ResponseItemCustomer createCustomer() {
			ResponseItemCustomer taskResponse = new ResponseItemCustomer();

			return taskResponse;
		}
	}

	Set<Object> getFacts();
}
