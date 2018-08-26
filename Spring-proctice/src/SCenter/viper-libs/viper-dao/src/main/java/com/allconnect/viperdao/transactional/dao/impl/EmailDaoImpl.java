/**
 *
 */
package com.A.Vdao.transactional.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.EMailContactChannel;
import com.A.Vdao.dao.EmailDao;

/**
 * @author ebthomas
 * 
 */
@Component
public class EmailDaoImpl extends BaseTransactionalJpaDao implements EmailDao {
	private static final String FIND_EMAIL_EMBED_BY_CUSTOMER_ID = "SELECT c FROM Consumer c WHERE c.externalId = ?";
	private static final String FIND_EMAIL_BY_CUSTOMER_ID = "SELECT a FROM EMailContactChannel a WHERE a.consumerExternalId = ?";

	private static final Logger logger = Logger.getLogger(EmailDaoImpl.class);

	/**
	 * factory constructor.
	 */
	public EmailDaoImpl() {
		super();
	}

	/**
	 * Find consumer by external id
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<EMailContactChannel> findEmailByCustomerId(Long id) {
		logger.info("finding EMail by customer id: " + id);
		Query q = getEntityManager().createQuery(FIND_EMAIL_BY_CUSTOMER_ID);
		q.setParameter(1, Long.valueOf(id));
		List<EMailContactChannel> EMailList = q.getResultList();

		if (EMailList == null) {
			EMailList = new ArrayList<EMailContactChannel>();
		}

		Query q2 = getEntityManager().createQuery(
				FIND_EMAIL_EMBED_BY_CUSTOMER_ID);
		q2.setParameter(1, Long.valueOf(id));
		
		try {
		Consumer c = (Consumer) q.getSingleResult();

		if (c != null) {
			buildEMailList(c, EMailList);
		}
		} catch(Exception e) {
			logger.warn(e.getMessage());
		}

		return EMailList;

	}

	public void buildEMailList(final Consumer c,
			List<EMailContactChannel> EMailList) {

		if (EMailList == null) {
			EMailList = new ArrayList<EMailContactChannel>();
		}
		buildEMailList(0, c.getWorkEmailValue(), "work-email", c, EMailList);
		buildEMailList(1, c.getHomeEmailValue(), "home-email", c, EMailList);
	}

	public void buildEMailList(int order, String value, String desc,
			Consumer c, List<EMailContactChannel> EMailList) {
		if ((value != null) && (value.length() > 0)) {
			EMailContactChannel e = new EMailContactChannel();
			e.setConsumerExternalId(c.getExternalId());
			e.setDescription(desc);
			e.setId(order);
			e.setValue(value);

			EMailList.add(e);
		}

	}

	/**
	 * @param em
	 *            Entity Manager
	 * @param email
	 *            Email Contact Bean that will be updated
	 */
	public void persist(final EMailContactChannel email) {
		if (email != null) {
			getEntityManager().persist(email);
		}
	}

	/**
	 * @param em
	 *            Entity Manager
	 * @param email
	 *            Email Contact Bean that will be updated
	 */
	public void remove(final EMailContactChannel email) {
		if (email != null) {
			getEntityManager().remove(email);
		}
	}

	/**
	 * @param em
	 *            Entity Manager
	 * @param email
	 *            Email Contact Bean that will be updated
	 */
	public void merge(final EMailContactChannel email) {
		if (email != null) {
			getEntityManager().merge(email);
		}
	}
}
