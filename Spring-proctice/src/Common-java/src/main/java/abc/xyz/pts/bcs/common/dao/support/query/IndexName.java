/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query;

public enum IndexName {

    TWL_DOC_NO_INDEX("tarwl_5_i"),
    TVLRS_DOC_NO_INDEX("tra_10_i");

    private String index;

    private IndexName(final String index){
        this.index = index;
    }

    public String getIndex(){
        return this.index;
    }
}
