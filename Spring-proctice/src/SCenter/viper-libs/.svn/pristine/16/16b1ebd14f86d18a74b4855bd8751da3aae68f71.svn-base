/**
 *
 */
package com.A.Vdao.transactional.dao.impl;

import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.A.V.beans.entity.StatusRecordBean;
import com.A.Vdao.dao.StatusDao;

/**
 * @author ebthomas
 *
 */

@Component
public class StatusDaoImpl extends BaseTransactionalJpaDao implements StatusDao {

    private static final Logger logger = Logger.getLogger(StatusDaoImpl.class);

    /**
     * factory constructor.
     */
    private StatusDaoImpl() {
	super();
    }

    /**
     * @param statusRecordHistoryList
     *            Status history list with reasons to be removed
     * @param em
     *            Entity Manager that will perform remove operation
     */
    public void remove(final StatusRecordBean statusRecord) {
	if (statusRecord == null) {
	    return;
	}

	if (getEntityManager() == null) {
	    throw new IllegalArgumentException("Entity Manager should not be NULL");
	}

	getEntityManager().remove(statusRecord);

    }

    /**
     * @param statusRecordHistoryList
     *            Status history list with reasons to be removed
     * @param em
     *            Entity Manager that will perform remove operation
     */
    public void remove(final List<StatusRecordBean> statusRecordHistoryList) {
	if (statusRecordHistoryList == null) {
	    return;
	}

	if (getEntityManager() == null) {
	    throw new IllegalArgumentException("Entity Manager should not be NULL");
	}

	for (StatusRecordBean status : statusRecordHistoryList) {
	    getEntityManager().remove(getEntityManager().getReference(StatusRecordBean.class, status.getId()));
	}
    }

    private void validateSave(final StatusRecordBean sr) {

	if (sr.getDateTimeStamp() == null) {
	    sr.setDateTimeStamp(Calendar.getInstance());
	}

	if (sr.getAgentExternalId() == null) {
	    logger.warn("missing agent external Id for Status Record");
	}
    }

    /**
     * @param em
     *            entityManager used to execute operation
     * @param statusRecordBean
     *            status to be updated
     * @return boolean value that specify the value of the result
     */
    public Boolean merge(final StatusRecordBean statusRecordBean) {
	logger.info("Executing status merge");
	long start = System.currentTimeMillis();
	if ((statusRecordBean != null) && (getEntityManager() != null)) {

	    validateSave(statusRecordBean);

	    try {

		getEntityManager().merge(statusRecordBean);

		getEntityManager().flush();
		logger.info("Status merge took : " + (System.currentTimeMillis() - start) + "ms");
		return Boolean.TRUE;
	    }
	    catch (Exception e) {
		e.printStackTrace();

	    }

	}

	return Boolean.FALSE;
    }

    /**
     * @param em
     *            Entity Manager
     * @param statusRecordBean
     *            Status Record Bean
     */
    public void merge(final List<StatusRecordBean> statusRecordBeanList) {
	logger.info("Executing Status list merge");
	long start = System.currentTimeMillis();
	if (statusRecordBeanList != null) {
	    for (StatusRecordBean status : statusRecordBeanList) {
		validateSave(status);
		merge(status);
	    }
	}
	logger.info("Status list merge took : " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * @param em
     *            Entity Manager
     * @param statusRecordBean
     *            Status Record Bean
     */
    public void persist(final List<StatusRecordBean> statusRecordBeanList) {
	logger.info("Executing status list persist");
	long start = System.currentTimeMillis();
	if (statusRecordBeanList != null) {
	    for (StatusRecordBean status : statusRecordBeanList) {
		validateSave(status);
		persist(status);
	    }
	}
	logger.info("Status list save took : " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * @param em
     *            Entity Manager
     * @param statusRecordBean
     *            Status Record Bean
     */
    public void persist(final StatusRecordBean statusRecordBean) {
	logger.info("Executing status save");
	long start = System.currentTimeMillis();
	if (statusRecordBean != null) {
	    validateSave(statusRecordBean);
	    getEntityManager().persist(statusRecordBean);
	}
	logger.info("Status save took : " + (System.currentTimeMillis() - start) + "ms");
    }

}
