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
 * This Enum contains basic delete, search, view operations
 * @author ryattapu
 *
 */

public enum Actions {

   ADD("Add"),
   SEARCH("Search"),
   DELETE("Delete"),
   VIEW("View"),
   UPDATE("Update"),
   EDIT("Edit");
   private final String value;

   Actions(final String v) {
       value = v;
   }

   public String value() {
       return value;
   }

   public static Actions fromValue(final String v) {
       for (Actions c: Actions.values()) {
           if (c.value.equals(v)) {
               return c;
           }
       }
       throw new IllegalArgumentException(v);
   }

}
