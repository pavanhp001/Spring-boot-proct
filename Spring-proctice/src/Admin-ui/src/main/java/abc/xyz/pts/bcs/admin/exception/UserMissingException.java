/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008-2010
 * All rights reserved.
 */
package abc.xyz.pts.bcs.admin.exception;

public class UserMissingException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserMissingException(final Exception e) {
        super(e);
    }

    public UserMissingException(final String msg) {
        super(msg);
    }
}
