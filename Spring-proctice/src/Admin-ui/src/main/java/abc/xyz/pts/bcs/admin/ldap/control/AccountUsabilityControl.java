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
package abc.xyz.pts.bcs.admin.ldap.control;

import javax.naming.ldap.BasicControl;

import org.apache.log4j.Logger;

import com.sun.jmx.snmp.BerDecoder;
import com.sun.jmx.snmp.BerException;

/**
 * @author tterry
 */

/*
 * ASN description for Account Usability Control
 *
 * ACCOUNT_USABLE_RESPONSE ::= CHOICE { is_available [0] INTEGER, -- Seconds before expiration -- is_not_available [1]
 * MORE_INFO }
 *
 * MORE_INFO ::= SEQUENCE { inactive [0] BOOLEAN DEFAULT FALSE, reset [1] BOOLEAN DEFAULT FALSE, expired [2] BOOLEAN
 * DEFAULT_FALSE, remaining_grace [3] INTEGER OPTIONAL, seconds_before_unlock [4] INTEGER OPTIONAL }
 */

/*
 * Note that the boolean/integer values don't have the normal ASN tags when returned from Sun's directory server: e.g
 *
 * Not locked out: 80 01 FF Locked out: A1 04 84 02 02 52
 */
@SuppressWarnings("serial")
public class AccountUsabilityControl extends BasicControl {

    private static final Logger LOGGER = Logger.getLogger(AccountUsabilityControl.class);
    public static final String ID = "1.3.6.1.4.1.42.2.27.9.5.8";
    public static final int TYPE_MORE_INFO = 0xA1;
    public static final int TYPE_SECONDS_BEFORE_EXPIRATION = 0x80;
    public static final int INACTIVE = 0x80;
    public static final int RESET = 0x81;
    public static final int EXPIRED = 0x82;
    public static final int REMAINING_GRACE = 0x83;
    public static final int SECONDS_UNTIL_UNLOCK = 0x84;

    public AccountUsabilityControl(final byte[] value) {
        super(ID, true, value);
    }

    public boolean isInactive() {
        BerDecoder berDecoder = new BerDecoder(getEncodedValue());

        boolean result = false;

        try {
            int tag = berDecoder.getTag();

            if (tag == TYPE_SECONDS_BEFORE_EXPIRATION) {
                int expiryTime = berDecoder.fetchInteger(tag);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Account active, seconds until expiration:" + expiryTime);
                }

                return false;
            }

            berDecoder.openSequence(tag);

            // The response may contain multiple statuses - e.g password needs to
            // be reset and the account is locked out. Hunt through the list
            // looking for the "account locked out" status, ignoring the others
            while (berDecoder.cannotCloseSequence()) {
                // Get the account usability status value
                tag = berDecoder.getTag();

                // Get the value giving more information about the status code above.
                // Either a boolean or an integer, but both types parse as BER integers
                int resultValue = berDecoder.fetchInteger(tag);

                switch (tag) {
                    default:
                    case INACTIVE:
                    case RESET:
                    case EXPIRED:
                    case REMAINING_GRACE:

                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Account availability issue, reason: " + (tag ^ 0x80) + ", value: "
                                    + resultValue);
                        }

                        // Leave result value alone
                        break;
                    case SECONDS_UNTIL_UNLOCK:
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Account locked, seconds until unlock:" + resultValue);
                        }

                        result = true;
                        break;
                }
            }

            // Close the seq - a sanity check to ensure we parsed the sequence correctly
            berDecoder.closeSequence();

            return result;
        } catch (BerException e) {
            String message = "Unable to get inactive status";
            LOGGER.debug(message, e);
            throw new IllegalArgumentException(message, e);
        }

        // Can't get here - either result has been returned, or exception thrown
    }
}
