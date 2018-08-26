package com.A.validation;

 

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import com.A.validation.Message;
import com.A.validation.ValidationReport;
import com.A.validation.impl.DefaultValidationReport;

/**
* @author ebthomas
* 
*/
public class ResponseItemBase implements OrchestrationContext {
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
			responseList.put("statusReport", report);
		}
	}

	/**
	 * @return get Validation Report
	 */
	public ValidationReport getValidationReport() {

		if ((responseList != null) && (responseList.size() > 0)) {
			ValidationReport report = (ValidationReport) responseList
					.get("statusReport");
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

	public Set<Object> getFacts() {
		Object facts = this.get("facts");

		return (Set<Object>) facts;
	}

	public String getExecutionContextFilter() {
		Object objFilter = this.get("executionContextFilter");

		return (String) objFilter;
	}

	public Map<String, String> getRulesSelectionProperties() {
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
		return (String) get("sessionId");
	}

	public String getRegion() {
		return (String) get("region");
	}

	public String getAffiliateName() {
		return (String) get("affiliateName");
	}

	public String getSalesCode() {
		return (String) get("salesCode");
	}

	public String getCorrelationId() {
		return (String) get("correlationId");
	}

	public int getTransactionId() {

		return (Integer) get("transactionId");
	}

	public Object getSalesOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addBroadcastMessage(String broadcastMessage) {
		// TODO Auto-generated method stub
		
	}

	public String getBroadcastMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean addSuccessful(Object salesOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean addSuccessful(List<Object> salesOrderBeanList) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean addEntityManager(EntityManager em) {
		// TODO Auto-generated method stub
		return null;
	}

}
