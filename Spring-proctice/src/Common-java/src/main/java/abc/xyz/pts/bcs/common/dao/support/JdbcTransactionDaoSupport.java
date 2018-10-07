/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * Abstract DOA Extend to implement specific DAO.
 */
@Repository("daoSupport")
public class JdbcTransactionDaoSupport extends JdbcDaoSupport {



    protected static final Logger LOG = Logger.getLogger(JdbcTransactionDaoSupport.class);

    private TransactionTemplate txTemplate;

    /**
     * Set by Spring for transaction support.
     *
     * @param txManager
     */
    public final void setTransactionManager(final PlatformTransactionManager txManager) {
        this.txTemplate = new TransactionTemplate(txManager);
        this.txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    protected final TransactionTemplate getTransactionTemplate() {
        return this.txTemplate;
    }

    /**
     * Stop clonability
     */
    @Override
    public final Object clone() throws java.lang.CloneNotSupportedException {
        throw new java.lang.CloneNotSupportedException();
    }

    /**
     * Stop Serialization
     *
     * @param out
     * @throws java.io.IOException
     */
    private void writeObject(final ObjectOutputStream out) throws java.io.IOException {
        throw new java.io.IOException("Object cannot be serialized");
    }

    /**
     * Stop Deserialization
     *
     * @param in
     * @throws java.io.IOException
     */
    private void readObject(final ObjectInputStream in) throws java.io.IOException {
        throw new java.io.IOException("Class cannot be deserialized");
    }
}
