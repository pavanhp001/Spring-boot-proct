/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */

package abc.xyz.pts.bcs.common.jasper.helper;

import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import abc.xyz.pts.bcs.common.enums.HasDataDictionaryKey;
import abc.xyz.pts.bcs.common.enums.HasLegend;

public final class JasperHelper
{
    private static final String COMMA = "comma";

    private JasperHelper()
    {
        super();
    }

    public static <T extends HasDataDictionaryKey> String formatListForJasper(final ResourceBundle bundle, final List<T> objList)
    {
        StringBuilder buffer = new StringBuilder();
        for (T obj : objList)
        {
            if (bundle.containsKey(obj.getDataDictionaryKey()))
            {
                if (buffer.length() > 0) {
                    buffer.append(bundle.getString(COMMA));
                    buffer.append(" ");
                }
                buffer.append(bundle.getString(obj.getDataDictionaryKey()));
            }
        }

        return buffer.toString();

    }

    /**
     * Gets legend for the specified Enumerate Object.
     *
     * The return string is in the following format
     * i.e.  I - Inbound, O - Outbound,....
     *
     * There needs to be a space between each character set to ensure Arabic text is translated correctly
     *
     * @param bundle
     * @param enumList
     * @return formatted string
     */
    public static <T extends Enum<T> & HasLegend & HasDataDictionaryKey> String getLegendFor(final ResourceBundle bundle, final T[] enumList)
    {
        StringBuilder buf = new StringBuilder();

        // i.e. I - Inbound, O - Outbound.....
        for (T obj : enumList)
        {
            // only process Enums that have a key
            if (StringUtils.isBlank(obj.getLegend())) {
                continue;
            }

            if (buf.length() > 0)
            {
                try {
                    buf.append(bundle.getString(COMMA));
                } catch (MissingResourceException mre) {
                    buf.append(',');
                }
                buf.append(" ");
            }

            buf.append(obj.getLegend());
            buf.append(" - ");
            try {
                buf.append(bundle.getString(obj.getDataDictionaryKey()));
            } catch (MissingResourceException mre) {
                buf.append(obj.getDataDictionaryKey());
            }
        }
        return buf.toString();
    }

}
