/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

import java.util.HashMap;

/**
 * This is Convenient class to read the System properties from JSP pages
 * @author Reddy.Yattapu
 *
 */
public class BcsSystemProperties extends HashMap<String, String> {

    private static final long serialVersionUID = 659328815864262758L;

    @Override
    public String get(final Object name) {
        return System.getProperty(name != null ? name.toString() : null);
    }

}

