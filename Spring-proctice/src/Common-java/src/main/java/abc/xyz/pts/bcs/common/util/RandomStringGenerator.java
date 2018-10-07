/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

public class RandomStringGenerator {

    private static final String ALPHA_NUM =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String getAlphaNumeric(final int len) {
       StringBuilder sb = new StringBuilder(len);
       for (int i=0;  i<len;  i++) {
          int ndx = (int)(Math.random()*ALPHA_NUM.length());
          sb.append(ALPHA_NUM.charAt(ndx));
       }
       return sb.toString();
    }

    public static String getRandomComment(final int len) {
        String randomString = getAlphaNumeric(len);
        return " /* " + randomString + " */ ";
     }

}
