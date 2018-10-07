/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.enums;

public enum WatchListDataType implements HasDataDictionaryKey
{

    WLD_PERSON("person"),
    WLD_DOCUMENT("document"),
    WLD_AP_RULE("mail"),
    WLD_NOTIFY_HIT("notifyhit"),
    WLD_NO_HIT("nohit")
    ;

    private String dataDictionaryKey;

    private WatchListDataType(final String ddKey)
    {
        this.dataDictionaryKey = ddKey;
    }

    @Override
    public String getDataDictionaryKey()
    {
        return this.dataDictionaryKey;
    }
}
