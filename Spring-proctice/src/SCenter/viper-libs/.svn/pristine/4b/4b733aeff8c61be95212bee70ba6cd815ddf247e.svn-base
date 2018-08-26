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
import com.A.V.beans.entity.PhoneContactChannel;
import com.A.Vdao.dao.PhoneDao;

/**
 * @author ebthomas
 * 
 */

@Component
public class PhoneDaoImpl extends BaseTransactionalJpaDao implements PhoneDao {

	private static final String FIND_PHONE_BY_CUSTOMER_ID = "SELECT c FROM Consumer c WHERE c.externalId = ?";
	private static final Logger logger = Logger.getLogger(PhoneDaoImpl.class);

	/**
	 * factory constructor.
	 */
	public PhoneDaoImpl() {
		super();
	}

	/**
	 * Find consumer by external id
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<PhoneContactChannel> findPhoneByCustomerId(Long id) {
		logger.info("finding phone by customer id: " + id);
		Query q = getEntityManager().createQuery(FIND_PHONE_BY_CUSTOMER_ID);
		q.setParameter(1, Long.valueOf(id));
		Consumer c = (Consumer) q.getSingleResult();

		List<PhoneContactChannel> phoneList = new ArrayList<PhoneContactChannel>();

		if (c != null) {
			buildPhoneList(c, phoneList);
		}

		return phoneList;

	}

	public void buildPhoneList(final Consumer c,
			List<PhoneContactChannel> phoneList) {

		if (phoneList == null) {
			phoneList = new ArrayList<PhoneContactChannel>();
		}
		buildPhoneList(0, c.getCellPhoneValue(), "cell-phone", c, phoneList);
		buildPhoneList(1, c.getHomePhoneValue(), "home-phone", c, phoneList);
		buildPhoneList(2, c.getWorkPhoneValue(), "work-phone", c, phoneList);
	}

	public void buildPhoneList(int order, String value, String desc,
			Consumer c, List<PhoneContactChannel> phoneList) {
		if ((value != null) && (value.length() > 0)) {
			PhoneContactChannel p = new PhoneContactChannel();
			p.setConsumerExternalId(c.getExternalId());
			p.setDescription(desc);
			p.setId(order);
			p.setValue(value);
			phoneList.add(p);
		}

	}

	/**
	 * @param em
	 *            Entity Manager
	 * @param phoneContactChannel
	 *            phone contact channel
	 */
	public void persist(final PhoneContactChannel phoneContactChannel) {
		// if ( phoneContactChannel != null )
		// {
		// getEntityManager().persist( phoneContactChannel );
		// }
	}

	/**
	 * @param em
	 *            Entity Manager
	 * @param phoneContactChannel
	 *            phone contact channel
	 */
	public void remove(final PhoneContactChannel phoneContactChannel) {
		// if ( phoneContactChannel != null )
		// {
		// getEntityManager().remove(getEntityManager().getReference(
		// PhoneContactChannel.class, phoneContactChannel.getId() ));
		// }
	}

	/**
	 * @param em
	 *            Entity Manager
	 * @param phoneContactChannel
	 *            phone contact channel
	 */
	public void merge(final PhoneContactChannel phoneContactChannel) {
		// if ( phoneContactChannel != null )
		// {
		// getEntityManager().merge( phoneContactChannel );
		// }
	}

}
