/**
 *
 */
package com.A.ui.context.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.A.task.TaskResultEnum;
import com.A.validation.Message;
import com.A.validation.ValidationReport;

 

/**
 * @author ebthomas
 * 
 */
public class ResponseItemBase implements OrchestrationContext {
	private TaskResultEnum taskResultEnum;
	private Map<String, Object> responseList;

	/**
	 * Parameters Container Base.
	 */
	public ResponseItemBase() {
		super();
		setResponseList(new HashMap<String, Object>());
	}

	/**
	 * @param additionResponseList
	 *            add to response list
	 */
	public void addAll(final Map<String, Object> additionResponseList) {
		this.getResponseList().putAll(additionResponseList);
	}

	 

	/**
	 * clear list.
	 */
	public void clear() {

		if (responseList != null) {
			responseList.clear();
		}
	}

	public String getBroadcastMessage() {
		return (String) get(TaskContextParamEnum.broadcast.name());
	}

	/**
	 * @param salesOrder
	 *            add sales order bean
	 * @return boolean indicating success of the process
	 */
	 

	 

	/**
	 * @param key
	 *            key of added response
	 * @param responseItem
	 *            response item to add
	 */
	public void add(final String key, final Object item) {

		getResponseList().put(key, item);

	}

	/**
	 * @param key
	 *            key of added response
	 * @param responseItem
	 *            response item to add
	 */
	public void addResponseItem(final String key, final Object responseItem) {

		getResponseList().put(key, responseItem);

	}

	/**
	 * @param report
	 *            Validation report to add
	 */
	public void putValidationReport(final ValidationReport report) {

		if (responseList != null) {
			responseList.put(TaskContextParamEnum.statusReport.name(), report);
		}
	}

	/**
	 * @return get Validation Report
	 */
	public ValidationReport getValidationReport() {

		if ((responseList != null) && (responseList.size() > 0)) {
			ValidationReport report = (ValidationReport) responseList
					.get(TaskContextParamEnum.statusReport.name());
			if (report == null) {
				report = new DefaultValidationReport();
				putValidationReport(report);
			}

			return report;
		}

		return new DefaultValidationReport();
	}

	/**
	 * @param key
	 *            getter
	 * @return retrieved object matching key
	 */
	public Object getSalesOrder() {

		if ((responseList != null) && (responseList.size() > 0)) {
			return responseList.get(TaskContextParamEnum.salesOrder.name());
		}

		return null;
	}

	/**
	 * @param key
	 *            getter
	 * @return retrieved object matching key
	 */
	public Object get(final String key) {

		if ((responseList != null) && (responseList.size() > 0)) {
			return responseList.get(key);
		}

		return null;
	}

	public Boolean isEmpty() {
		return ((responseList == null) || (responseList.size() == 0));

	}

	/**
	 * @return getter for task result enum
	 */
	public TaskResultEnum getTaskResultEnum() {
		return taskResultEnum;
	}

	/**
	 * @param taskResultEnum
	 *            setter for task result enum
	 */
	public void setTaskResultEnum(final TaskResultEnum taskResultEnum) {
		this.taskResultEnum = taskResultEnum;
	}

	/**
	 * @return getter for response list
	 */
	public Map<String, Object> getResponseList() {
		if (responseList == null) {
			responseList = new HashMap<String, Object>();
		}

		return responseList;
	}

	/**
	 * @param responseList
	 *            setter for response list
	 */
	public void setResponseList(final Map<String, Object> responseList) {
		this.responseList = responseList;
	}

	@SuppressWarnings("unchecked")
	public Set<Object> getFacts() {
		Object facts = this.get("facts");

		return (Set<Object>) facts;
	}

	public String getExecutionContextFilter() {
		Object objFilter = this.get("executionContextFilter");

		return (String) objFilter;
	}

	public Map<String, String> getRulesSelectionProperties() {
		@SuppressWarnings("unchecked")
		Map<String, String> rulesSelectionProperties = (Map<String, String>) this
				.get("rulesSelectionProperties");

		return rulesSelectionProperties;
	}

	public void addInvalidCustomerData() {
		try {
			ValidationReport report = getValidationReport();
			report.addMessage(Message.Type.FATAL, 1L,
					"Address reference used in Billing Info does not exist in address list!!!");
			this.putValidationReport(report);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addInvalidCustomerData(String message) {
		try {
			ValidationReport report = getValidationReport();
			report.addMessage(Message.Type.FATAL, 1L, message);
			this.putValidationReport(report);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addInvalidLineitemData() {
		try {
			ValidationReport report = getValidationReport();
			report.addMessage(
					Message.Type.FATAL,
					1L,
					"Address reference/Billing reference used in Lineitem does not exist in Customer Details!!!");
			this.putValidationReport(report);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getSessionId() {
		return (String) get(TaskContextParamEnum.sessionId.name());
	}

	public String getRegion() {
		return (String) get(TaskContextParamEnum.region.name());
	}

	public String getAffiliateName() {
		return (String) get(TaskContextParamEnum.affiliateName.name());
	}

	public String getSalesCode() {
		return (String) get(TaskContextParamEnum.salesCode.name());
	}

	public String getCorrelationId() {
		return (String) get(TaskContextParamEnum.correlationId.name());
	}

	public int getTransactionId() {

		return (Integer) get(TaskContextParamEnum.transactionId.name());
	}

	public void addBroadcastMessage(String broadcastMessage) {
		// TODO Auto-generated method stub
		
	}

 

	 

}
