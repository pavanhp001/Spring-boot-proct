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
 * Travel type.
 *
 * This is the actual value stored in the database.
 *
 * @author chrisw
 *
 */
public enum TravelType implements HasDataDictionaryKey, HasLegend
{
      NORMAL("travel.type.N","N")
    , TRANSFER("travel.type.X","X")
    , TRANSIT("travel.type.T","T")
    , UNMATCHED("travel.type.U","U")
    ;

      private String dataDictionaryKey;
      private String legend;

      private TravelType(final String ddKey, final String legendKey)
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
