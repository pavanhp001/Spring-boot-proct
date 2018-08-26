package com.A.validation;

 

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import com.A.validation.ValidationReport;

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
	Boolean addSuccessful(final Object salesOrder);

	Boolean addSuccessful(final List<Object> salesOrderBeanList);

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
		 * @return Task Response
		 */
		public static OrchestrationContext create() {
			OrchestrationContext taskResponse = new ResponseItemBase();

			return taskResponse;
		}

	 

	 
	}

	Set<Object> getFacts();
}
