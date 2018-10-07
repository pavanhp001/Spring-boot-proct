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
package abc.xyz.pts.bcs.wi.iir.search;

import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchInvalidDataFormatException;

public interface IIRSearch
{
    public IIRSearchResponse search(final IIRSearchRequest req
            ) throws IIRSearchException, IIRConnectException
                   , IIRSearchInvalidDataFormatException;
}
