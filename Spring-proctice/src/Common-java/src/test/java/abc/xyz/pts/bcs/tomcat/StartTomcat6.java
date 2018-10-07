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

package abc.xyz.pts.bcs.tomcat;

import org.apache.catalina.startup.Bootstrap;

/**
 * Start Tomcat.
 *
 * @author Geoff Carlier
 * @version $Id: StartTomcat6.java 4062 2009-08-11 16:31:06Z mcunliffe $
 */
public final class StartTomcat6 {

    private StartTomcat6() {
    }
    /**
     * Start tomcat.
     *
     * Startup VM Args:
     * -Dldap.user=uid=admin,ou=system
     * -Dldap.password=secret
     * -Dldap.server=ldap://localhost:10389
     *
     * @param args ignored
     */
    public static void main(final String[] args) {

        try {
            System.out.println("******************************************");
            System.out.println("*         Project ARAS platform          *");
            System.out.println("******************************************");
            System.out.println(">>> Basic logging initialised <<<");

            Bootstrap.main(new String[] {"start"});

        } catch (Throwable th) {
            System.err.println("Exception occurred:  " + th.getMessage());
            th.printStackTrace();
            Bootstrap.main(new String[] {"stop"});
        }
    }

}
