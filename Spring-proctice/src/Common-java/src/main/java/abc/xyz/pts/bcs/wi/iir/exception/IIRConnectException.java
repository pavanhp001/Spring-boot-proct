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

public class IIRConnectException extends Exception
{
    private static final long serialVersionUID = 3332371042840546342L;
    public IIRException e;

    public IIRConnectException(final String msg)
    {
        super(msg);
    }

    public IIRConnectException(final String msg, final Throwable e)
    {
        super(msg, e);
        if(e instanceof IIRException) {
           this.e = (IIRException)e;
        }
    }

    public IIRConnectException(final IIRException e)
    {
        super(e);
        this.e = e;
    }


    @Override
    public String getMessage()
    {
        if (e == null) {
            return super.getMessage();
        }
        return e.getMessage();
    }

    public boolean getFatal()
    {
        if (e == null) {
            return true;
        }

        return this.e.getFatal();
    }

    public String getFunc()
    {
        if (e == null) {
            return super.getMessage();
        }
        return this.e.getFunc();
    }
}
