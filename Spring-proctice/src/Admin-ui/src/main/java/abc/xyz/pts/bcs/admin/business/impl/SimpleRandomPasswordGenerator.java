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
package abc.xyz.pts.bcs.admin.business.impl;

import java.util.Random;

import abc.xyz.pts.bcs.admin.business.PasswordGenerator;

public class SimpleRandomPasswordGenerator implements PasswordGenerator {

    private static final int PASSWORD_LENGTH = 8;
    private static final int NUM_BUCKETS = 4;
    private static final String[] BUCKETS = new String[NUM_BUCKETS];

    static {
        // we define the content of each bucket
        BUCKETS[0] = "abcdefghijklmnopqrstuvwxyz";
        BUCKETS[1] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        BUCKETS[2] = "0123456789";
        BUCKETS[3] = ".-_&*!:;@";
    }

    private int getBucketCount(final int[] bucketCounts) {
        int total = 0;
        for (int count : bucketCounts) {
            total = total + count;
        }
        return total;
    }

    public String generate() {
        StringBuilder buff = new StringBuilder(PASSWORD_LENGTH);
        // we must pick one from each bucket, then a random
        // amount from each bucket until the password has reached
        // the required length.
        int[] bucketCount = new int[NUM_BUCKETS];
        // set the constraints on the minimum occurances of the contents of each bucket
        bucketCount[0] = 1;
        bucketCount[1] = 1;
        bucketCount[2] = 1;
        bucketCount[3] = 1;
        Random random = new Random();
        // pick the buckets
        int countTotal = getBucketCount(bucketCount);
        while (countTotal < PASSWORD_LENGTH) {
            int bucketNum = random.nextInt(NUM_BUCKETS);
            bucketCount[bucketNum]++;
            countTotal = getBucketCount(bucketCount);
        }
        // pick the characters out of the buckets. (also randomise bucket selection)
        while (countTotal > 0) {
            int bucketNum = random.nextInt(NUM_BUCKETS);
            if (bucketCount[bucketNum] > 0) {
                String bucketContents = BUCKETS[bucketNum];
                int charNum = random.nextInt(bucketContents.length());
                char luckyChar = bucketContents.charAt(charNum); // we have a winner
                buff.append(luckyChar);
                bucketCount[bucketNum]--;
                countTotal = getBucketCount(bucketCount);
            }
        }
        return buff.toString();
    }
}
