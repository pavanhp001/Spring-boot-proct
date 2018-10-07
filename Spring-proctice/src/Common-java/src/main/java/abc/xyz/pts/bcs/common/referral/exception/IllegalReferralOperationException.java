/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.exception;

import abc.xyz.pts.bcs.common.exception.ApplicationException;

/**
 * Encapsulate Referral Operation exceptions.
 *
 * @author cwalker
 */
public class IllegalReferralOperationException extends ApplicationException
{
    private static final long serialVersionUID = 5296951422820902707L;
    private static final String MESSAGE_KEY = "temp-todo";


    public IllegalReferralOperationException() {
        super(MESSAGE_KEY);
    }
}
