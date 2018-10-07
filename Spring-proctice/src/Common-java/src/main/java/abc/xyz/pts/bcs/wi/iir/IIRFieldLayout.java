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

import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;


public interface IIRFieldLayout
{

    public String getName();
    public int getLength();
    public int getOffset();
    public int getRepeat();
    public String getFormat();
    //public String getValue();

    // Fixed Length String
    public byte[] getSearchReqRecord(String value) throws IIRSearchException;

}
