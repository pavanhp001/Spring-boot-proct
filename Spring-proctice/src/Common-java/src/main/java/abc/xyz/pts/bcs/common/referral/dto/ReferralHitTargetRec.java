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

import abc.xyz.pts.bcs.common.enums.QualificationStatusType;


/**
 *
 * @author mmotlib-siddiqui
 *
 */
public interface ReferralHitTargetRec
{
    Long getId();
    Long getRefId();
    String getHitType();
    Long getHitScore();
    Long getWatlrId();
    String getWlFullname();
    String getWlGender();
    Calendar getWlBirthDate();
    String getCountryOfBirth();
    String getWlBirthPlace();
    String getWlNationality();
    String getWlDocType();
    String getWlDocNo();
    String getWlProtocolNumber();
    String getWlRescCode();
    String getAppHitReason();
    String getAppHitReasonDescription();
    String getRescCodeDesc();
    QualificationStatusType getQualificationStatus();
    String getDocData();
    void setAppHitReasonDescription(String appHitReasonDescription);
    String getWlForename();
    String getWlLastName();
    Integer getUpdateVersionNo();
    String getRescCode();
    Long getWlTarwlId();
}
