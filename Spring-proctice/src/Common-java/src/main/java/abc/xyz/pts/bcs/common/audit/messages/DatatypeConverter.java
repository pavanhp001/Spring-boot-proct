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

package abc.xyz.pts.bcs.common.audit.messages;

import java.util.Date;

public class DatatypeConverter {

    public static Date parseDateTime(final String dateStr) {
        long l = Long.parseLong(dateStr);
        return new Date(l);
    }

    public static String printDateTime(final Date date) {
        String result = null;
        if (date != null) {
            result = Long.toString(date.getTime());
        }
        return result;
    }
}
