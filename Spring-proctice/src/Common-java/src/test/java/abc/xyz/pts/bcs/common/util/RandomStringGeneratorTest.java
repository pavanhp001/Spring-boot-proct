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

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class RandomStringGeneratorTest {
    private  int LENGTH_OF_RANDOM_STRING =16;

    @Test
    public void testGetRandomComment() {
        String randomString = RandomStringGenerator.getRandomComment(LENGTH_OF_RANDOM_STRING);        
        String randomString1 = RandomStringGenerator.getRandomComment(LENGTH_OF_RANDOM_STRING);       
        assertFalse(randomString1.equals(randomString));
     }
    
}
