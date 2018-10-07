/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

/**
 * @author Deepesh.Rathore
 *
 */
public class CopyFailedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -5939864162899621694L;

    /**
     * @param string
     * @param e
     */
    public CopyFailedException(final String errorMessage, final Exception e) {
        super(errorMessage, e);
    }

}
