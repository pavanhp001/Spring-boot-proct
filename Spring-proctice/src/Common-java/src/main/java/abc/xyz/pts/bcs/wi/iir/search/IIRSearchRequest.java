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

import java.util.Map;

import abc.xyz.pts.bcs.common.iir.IIRSearchFieldType;
import abc.xyz.pts.bcs.wi.iir.connection.IIRConnection;

public interface IIRSearchRequest
{
    public IIRSearchType getIirSearchType();

    public String getSearchName();
    public void setSearchName(String searchName);

    /**
     * fields for matching.
     *
     * @return fieldList
     */
    public Map<IIRSearchFieldType, Object> getCriteria();

    public void setConnection(IIRConnection connection);
    public IIRConnection getConnection();

    public boolean isMatch();
    public void setMatch(boolean isMatch);

}
