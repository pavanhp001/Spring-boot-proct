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


/**
 *
 * @author mmotlib-siddiqui
 *
 */
public interface ReferralTravellerRec
{
    Long getTravellerId();
    String getFullname();
    String getLastName();
    String getForename();
    String getGender();
    Calendar getBirthDate();
    String getNationality();
    String getTravellerType();
    String getMovementType();
    String getDocType();
    String getDocNo();
    String getDocData();
    String getTypeOfTravel();
    String getCountryOfBirth();
    String getBirthPlace();
}
