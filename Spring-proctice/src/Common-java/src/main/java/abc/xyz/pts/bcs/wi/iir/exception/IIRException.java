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
package abc.xyz.pts.bcs.wi.iir.exception;

import org.apache.log4j.Logger;

import ssa.ssautil.SSAException;


public abstract class IIRException extends Exception
{
    private static final long serialVersionUID = 8978924789L;
    private static final Logger log = Logger.getLogger(IIRException.class);

    private SSAException ssae;
    private String func;
    private String iirErrMsg;

    public IIRException(final String func, final SSAException ssae, final String iirErrMsg)
    {
        this.ssae = ssae;
        this.func = func;
        this.iirErrMsg = iirErrMsg;
    }


    /**
     * Meaningful message
     *
     * @return
     */
    protected String resolveException()
    {
        StringBuffer buf = new StringBuffer();

        // * what did we try to call?
        buf.append("iir-func-call=");
        buf.append(this.func);

        buf.append(", ");

        // * return code from function call
        buf.append("iir-func-rtn-code=");
        buf.append(ssae.getRc());  // not sure why it's deprecated

        buf.append(", ");

        // * IIR Exception - SSASocketException / SSAInterruptedException / SSAAPIException
        buf.append("IIR-Exception=");
        buf.append(ssae.getClass().getName());

        // * IIR Exception Root Cause
        buf.append("IIR-Exception-Root-Cause=");
        buf.append(getIirErrMsg());

        return buf.toString();

    }


    @Override
    public String getMessage()
    {
        return resolveException();
    }


    /**
     * IIR fatal error?
     *
     * @return
     */
    public boolean getFatal()
    {
        return ssae.getFatal();
    }

    /**
     * IIR function name
     *
     * @return name
     */
    public String getFunc()
    {
        return func;
    }


    /**
     * Error message from IIR.
     *
     * @return errorMsg
     */
    public String getIirErrMsg()
    {
        return iirErrMsg;
    }


}

