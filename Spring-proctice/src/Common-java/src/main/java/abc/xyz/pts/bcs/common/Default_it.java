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
package abc.xyz.pts.bcs.common;

import java.io.IOException;
import java.util.Locale;
import java.util.PropertyResourceBundle;

import javax.naming.NamingException;

import abc.xyz.pts.bcs.common.jndi.LdapPropertyLoader;

public class Default_it extends PropertyResourceBundle {

    public Default_it() throws IOException, NamingException {
        super(LdapPropertyLoader.getLdapProperties(Locale.ITALIAN));
    }

}
