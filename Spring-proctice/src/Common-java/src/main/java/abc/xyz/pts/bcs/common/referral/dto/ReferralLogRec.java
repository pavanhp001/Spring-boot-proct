/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.dto;

import java.util.Calendar;

public interface ReferralLogRec {

    Long getId() ;
    Long getRefId();
    String getCreatedBy();
    Calendar getCreatedDatetime();
    String getNotfAirportCode() ;
    String getNotfRolesNotified();
    String getNotfMediaType();
    String getNotfActionStatus();
    String getRemarks();
    String getEventType();
    String getHitType();
    Long getHitId();
}
