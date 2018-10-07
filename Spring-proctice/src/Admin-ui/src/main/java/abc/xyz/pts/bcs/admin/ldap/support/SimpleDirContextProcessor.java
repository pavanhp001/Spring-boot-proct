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

import org.springframework.ldap.core.DirContextProcessor;

public class SimpleDirContextProcessor implements DirContextProcessor {

    @Override
    public void postProcess(final DirContext arg0) throws NamingException {

    }

    @Override
    public void preProcess(final DirContext arg0) throws NamingException {

    }

}
