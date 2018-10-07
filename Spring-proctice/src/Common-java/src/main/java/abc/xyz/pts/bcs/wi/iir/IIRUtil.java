/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
/**
 * @author MMotlib-Siddiqui
 */
package abc.xyz.pts.bcs.wi.iir;

import org.apache.log4j.Logger;

import ssa.ssase.ClieSock;
import ssa.ssautil.SSAAPIException;
import ssa.ssautil.SSASocketException;
import abc.xyz.pts.bcs.wi.iir.connection.IIRConnectionImpl;

public class IIRUtil
{
    private static final Logger log = Logger.getLogger(IIRConnectionImpl.class);

    public static String getIIRErrorMsg(final ClieSock cs)
    {
        try
        {
            if (cs == null) {
                return "No Socket connection - ClieSock is NULL";
            }

            String[] msg = new String[1];
            StringBuffer buf = new StringBuffer();

            while (cs.ids_error_get(msg, 300) == 0)
            {
                buf.append(msg[0]);
                buf.append(System.getProperty("line.separator"));
            }

            return buf.toString();
        }
        catch (SSASocketException e)
        {
            log.error(e, e);
            return null;
        }
        catch (SSAAPIException e)
        {
            log.error(e, e);
            return null;
        }
    }
}
