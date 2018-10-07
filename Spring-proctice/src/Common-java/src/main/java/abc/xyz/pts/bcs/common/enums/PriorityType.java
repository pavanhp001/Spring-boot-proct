/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.enums;

/**
 * Priority type.
 *
 * This is the actual value stored in the database.
 * Used by the AP-ui reports
 *
 * @author tbarak
 *
 */
public enum PriorityType implements HasDataDictionaryKey, HasLegend
{
      HIGH("priority.high","A")
    , MEDIUM("priority.medium","B")
    ;

      private String dataDictionaryKey;
      private String legend;

      private PriorityType(final String ddKey, final String legendKey)
      {
          this.dataDictionaryKey = ddKey;
          this.legend = legendKey;
      }

      public String getDataDictionaryKey()
      {
          return this.dataDictionaryKey;
      }

      @Override
      public String getLegend() {
          return legend;
      }
}
