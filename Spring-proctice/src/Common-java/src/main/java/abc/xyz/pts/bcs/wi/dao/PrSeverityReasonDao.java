/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dao;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import abc.xyz.pts.bcs.wi.dto.SeverityReason;


public interface PrSeverityReasonDao  {

    public List <SeverityReason> findAllSeverityReason(Locale locale);
    public Map<String, SeverityReason> findAllSeverityReasonMap(Locale locale);
}
