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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ryattapu
 * @version: $Id$
 */
public final class FileNameHelper {
    private FileNameHelper() {
    }

    /**
     * formats the current date + time into a string to be appended to the basename of the excel spreadsheet
     *
     * @return formatted string with this pattern _MM_dd_yyyy_HH_mm_ss 24hour format
     */
    public static String getDateTimeString() {
        SimpleDateFormat format = new SimpleDateFormat("_MM_dd_yyyy_HH_mm_ss");
        Date d = new Date();
        return format.format(d);
    }

}
