/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query;

public enum Hints {

    TARGET_WL_DOC_NO_HINT(Table.TARGET_WATCH_LISTS, IndexName.TWL_DOC_NO_INDEX),
    TVLRS_DOC_NO_HINT(Table.TRAVELLERS, IndexName.TVLRS_DOC_NO_INDEX);


    private String hint;

    private Hints(final Table table, final IndexName indexName){
        this.hint= ("/*+ index(" + table.name() +  " " + indexName.getIndex() +")*/ ");
    }

    public String getHint(){
        return hint;
    }
}
