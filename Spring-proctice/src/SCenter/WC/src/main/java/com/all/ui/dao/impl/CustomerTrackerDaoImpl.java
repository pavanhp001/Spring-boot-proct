package com.A.ui.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.A.ui.dao.CustomerTrackerDao;
import com.A.ui.dao.impl.BaseTransactionalJpaDao;
import com.A.ui.domain.CustomerCallsCount;
import com.A.ui.domain.CustomerTracker;
import com.A.ui.domain.CustomerTrackerDetails;

@Component
public class CustomerTrackerDaoImpl extends BaseTransactionalJpaDao implements CustomerTrackerDao {

	private static final Logger logger = Logger.getLogger(CustomerTrackerDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void insertCustomerTracker(CustomerTracker customerTracker) {
		try {
			logger.info("Inserting_data_into_CustomerTracker_table");
			getEntityManager().persist(customerTracker);
		} catch (Exception e) {
			logger.warn("Exception_in_Inserting_data_into_customer_calls_count_table"+e.getMessage());
		}
	}
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void updateCustomerTracker(Map<String,Float> updatedActualPointsMap) {
		try {
			logger.info("updating_data_into_CustomerTracker_table");
			
			if(!updatedActualPointsMap.isEmpty() && updatedActualPointsMap != null){
						for(String objectKey: updatedActualPointsMap.keySet()){
							String query = "UPDATE CustomerTracker SET isPointsUpdated ="+1+",actualPoints="+updatedActualPointsMap.get(objectKey)+" WHERE lineItemId="+objectKey;
							Query q = getEntityManager().createQuery(query);
							int updateCnt = (int)q.executeUpdate();
							logger.info("updated rows"+updateCnt);
						}
				}
			
			
		} catch (Exception e) {
			logger.warn("Exception_in_updating_data_into_CustomerTracker_table"+e.getMessage());
		}
	}


	public List<CustomerTracker> getCustomerTrackerDataByAgentId(String agentId) {
		try {
			if (getEntityManager() != null) {

				StringBuilder sb = new StringBuilder();
				sb.append("SELECT ct FROM CustomerTracker as ct WHERE ct.agentId =  '"+ agentId + "'" +
						"AND DATE(ct.createDate) = CURDATE()");

				Query q = getEntityManager().createQuery(sb.toString());

				Object obj = q.getResultList();

				if ((obj != null) && (obj instanceof List )) {
					@SuppressWarnings("unchecked")
					List<CustomerTracker> ctList = (List<CustomerTracker>) obj;
					return ctList;
				}
			}
		}
		catch (Exception e) {
			logger.warn("NoResult for getCustomerTrackerDataByAgentId =" + e.getMessage());
		}
		return null;

	}
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void insertCustomerCallsCount(CustomerCallsCount customerCallsCount) {
		try {
			logger.info("Inserting_data_into_CustomerCallsCount_table");
			getEntityManager().persist(customerCallsCount);
		} 
		catch (Exception e) {
			logger.warn("Exception_in_Inserting_data_into_customer_calls_count_table"+e.getMessage());
		}
	}

	public Integer getMaxCustomerCallNumberId(String agentId) {
		try {
			if (getEntityManager() != null) {

				StringBuilder sb = new StringBuilder();
				sb.append("SELECT max(ccc.callNumberId) FROM CustomerCallsCount as ccc WHERE ccc.agentId =  '"+ agentId + "'" +
						"AND DATE(ccc.createDate) = CURDATE()");

				Query q = getEntityManager().createQuery(sb.toString());

				Object obj = q.getSingleResult();

				if (obj != null) {
					return (Integer) obj;
				}
			}
		}
		catch (Exception e) {
			logger.warn("NoResult for getCustomerCallsCountByAgentId =" + e.getMessage());
		}
		return null;

	}
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void insertCustomerTrackerDetails(CustomerTrackerDetails customerTrackerDetails) {
		try {
			logger.info("Inserting_data_into_CustomerTrackerDetails_table");
			getEntityManager().persist(customerTrackerDetails);
		} catch (Exception e) {
			logger.warn("Exception_in_Inserting_data_into_CustomerTrackerDetails_table"+e.getMessage());
		}
	}
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void updateCustomerTrackerDetails(CustomerTrackerDetails customerTrackerDetails) {
		try {
			if (getEntityManager() != null) {
				logger.info("updating_data_into_CustomerTrackerDetails_table");

				String query = "UPDATE CustomerTrackerDetails SET actualCallCount = "+customerTrackerDetails.getActualCallCount()
						+", concertCallCount = "+customerTrackerDetails.getConcertCallCount()
						+", utilityPoints = "+customerTrackerDetails.getUtilityPoints()
						+", utilityPitchedCount = "+customerTrackerDetails.getUtilityPitchedCount()
						+" WHERE agentId ='"+ customerTrackerDetails.getAgentId() + "'" +
						"AND DATE(createDate)=CURDATE()";
				Query q = getEntityManager().createQuery(query);
				int updateCnt = (int)q.executeUpdate();
				logger.info("updated rows"+updateCnt);

			}
		} catch (Exception e) {
			logger.warn("Exception_in_updating_data_into_CustomerTrackerDetails_table"+e.getMessage());
		}
	}


	public CustomerTrackerDetails getCustomerTrackerDetailsByAgentId(String agentId) {
		try {
			if (getEntityManager() != null) {
				StringBuilder sb = new StringBuilder();
				sb.append("SELECT ct FROM CustomerTrackerDetails as ct WHERE ct.agentId =  '"+ agentId + "'" +
						"AND DATE(ct.createDate) = CURDATE()");

				Query q = getEntityManager().createQuery(sb.toString());

				Object obj = q.getResultList();

				if ((obj != null) && (obj instanceof List )) {
					@SuppressWarnings("unchecked")
					List<CustomerTrackerDetails> ctList = (List<CustomerTrackerDetails>) obj;
					CustomerTrackerDetails customerTrackerDetails = ctList.get(0);
					return customerTrackerDetails;
				}
			}
		}
		catch (Exception e) {
			logger.warn("NoResult for getCustomerTrackerDataByAgentId =" + e.getMessage());
			return null;
		}
		return null;

	}
	
}
