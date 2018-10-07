/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.core.main;


public interface MainClassInterface {

    enum ArgumentOptions { LDAPCONTEXTNAME, LOG4JPATH, JNDIPATH;
        public static ArgumentOptions getInstance(final String argOption) {
            ArgumentOptions result = null;
            if (argOption != null) {
                try {
                    result =  ArgumentOptions.valueOf(argOption.trim().toUpperCase());
                } catch (final RuntimeException e) {
                    result = null;
                }
            }
            return result;
        }
    }
    public static final String ARG_FIELD_SEPERATOR = "=";

    public String getLdapContextName();
    public String getLog4jPath();
    public String getJndiPath();
    public String getAppName();
    public boolean initialise(String [] args) throws Exception;
}
