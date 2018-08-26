/**
 *
 */
package com.A.Vdao.logical.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.BusinessParty;
import com.A.Vdao.dao.BusinessPartyDao;

/**
 * @author ebthomas
 *
 */
@Component
public class BusinessPartyDaoImpl extends LogicalJpaDao implements BusinessPartyDao {
    private static final String FIND_BIZ_PARTY_BY_ID = "SELECT bpb FROM BusinessParty bpb WHERE bpb.externalId = :externalId ";
    private static final String BUSINESS_PARTY_ID = "externalId";
    private static final Logger logger = Logger.getLogger(BusinessPartyDaoImpl.class);

    /**
     * factory constructor.
     */
    public BusinessPartyDaoImpl() {
	super();
    }

    /**
     * @param em
     *            Entity Manager
     * @param externalId
     *            External Id
     * @return Business Party with unique External Id
     */
    public BusinessParty findBusinessPartyById(final String externalId) {
	if (getEntityManager() != null) {

	    try {

		Query query = getEntityManager().createQuery(FIND_BIZ_PARTY_BY_ID);
		query.setParameter(BUSINESS_PARTY_ID, externalId);
		Object obj = query.getSingleResult();

		if ((obj != null) && (obj instanceof BusinessParty)) {
		    return (BusinessParty) obj;
		}
	    }
	    catch (NoResultException nre) {

		logger.warn("Unable to locate business party : " + externalId);
		logger.warn(nre.getMessage());

	    }
	}

	return null;

    }
}
