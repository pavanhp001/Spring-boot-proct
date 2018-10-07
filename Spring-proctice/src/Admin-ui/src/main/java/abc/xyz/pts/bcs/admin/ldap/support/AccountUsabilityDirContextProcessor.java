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
package abc.xyz.pts.bcs.admin.ldap.support;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.ldap.Control;

import org.springframework.ldap.control.AbstractRequestControlDirContextProcessor;

import abc.xyz.pts.bcs.admin.ldap.control.AccountUsabilityControl;

public class AccountUsabilityDirContextProcessor extends AbstractRequestControlDirContextProcessor {

    public AccountUsabilityDirContextProcessor() {
    }

    public Control createRequestControl() {
        return new AccountUsabilityControl(null);
    }

    public void postProcess(final DirContext ctx) throws NamingException {
    }
}
