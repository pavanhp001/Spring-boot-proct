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
package abc.xyz.pts.bcs.common.web.tag;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class ImportTagConfiguration {

    private String baseUrl = null;

    private GeneralCacheAdministrator cacheAdministrator = null;

    /**
     * @return the cacheAdministrator
     */
    public GeneralCacheAdministrator getCacheAdministrator() {
        return cacheAdministrator;
    }

    /**
     * @param cacheAdministrator
     *            the cacheAdministrator to set
     */
    public void setCacheAdministrator(final GeneralCacheAdministrator cacheAdministrator) {
        this.cacheAdministrator = cacheAdministrator;
    }

    /**
     * @return the baseUrl
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @param baseUrl
     *            the baseUrl to set
     */
    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
