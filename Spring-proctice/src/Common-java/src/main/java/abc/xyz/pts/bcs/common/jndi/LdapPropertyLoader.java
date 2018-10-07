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
package abc.xyz.pts.bcs.common.jndi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.naming.NamingException;

/**
 * LDAP application property loader
 *
 */
public final class LdapPropertyLoader extends AbtractLdapLoader {

    public static final String BASE_NAME = "Default";

    /**
     * Return Data Dictionary properties from LDAP for given Locale
     *
     * @param locale
     *            Locale of resource to return
     * @throws NamingException
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static InputStream getLdapProperties(final Locale locale) throws NamingException {

        String lookupEntry = "cn=" + BASE_NAME;
        if (locale != null) {
            lookupEntry += "_" + locale.toString();
        }
        return getLdapData(lookupEntry);

    }
}
