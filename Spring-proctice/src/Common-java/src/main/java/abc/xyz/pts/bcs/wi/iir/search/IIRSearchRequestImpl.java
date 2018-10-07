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

import java.util.HashMap;
import java.util.Map;

import abc.xyz.pts.bcs.common.iir.IIRSearchFieldType;
import abc.xyz.pts.bcs.wi.iir.connection.IIRConnection;

public class IIRSearchRequestImpl implements IIRSearchRequest
{
    private IIRSearchType iirSearchType;
    private String searchName;
    private final Map<IIRSearchFieldType, Object> criteria;
    private boolean isMatch;
    private IIRConnection connection;

    public IIRSearchRequestImpl()
    {
        this.criteria = new HashMap<IIRSearchFieldType, Object>();
        this.isMatch = false;
    }

    @Override
    public String toString()
    {
        if (this.criteria == null) {
            return "";
        }

        StringBuffer buf = new StringBuffer();
        buf.append("IIRSearchRequestImpl[");

        buf.append("isMatch=").append(this.isMatch);
        buf.append(", searchType=(").append(this.getIirSearchType()).append(')');

        buf.append(", criteria={");
        boolean first = true;
        for (IIRSearchFieldType ft : this.criteria.keySet())
        {
            if (first == false) {
                buf.append(',');
            }

            buf.append(ft);
            buf.append('=');
            buf.append(this.criteria.get(ft));

            first = false;
        }
        buf.append('}');

        buf.append(']');

        return buf.toString();
    }

    public IIRSearchRequestImpl(final Map<IIRSearchFieldType, Object> criteria)
    {
        this.criteria = criteria;
    }

    public IIRSearchType getIirSearchType()
    {
        return iirSearchType;
    }


    @Override
    public Map<IIRSearchFieldType, Object> getCriteria()
    {
        return criteria;
    }

    public void setIirSearchType(final IIRSearchType iirSearchType)
    {
        this.iirSearchType = iirSearchType;
    }

    public boolean isMatch()
    {
        return isMatch;
    }

    public void setMatch(final boolean isMatch)
    {
        this.isMatch = isMatch;
    }

    @Override
    public void setConnection(final IIRConnection connection) {
        this.connection = connection;
    }

    @Override
    public IIRConnection getConnection() {
        return this.connection;
    }

    /**
     * @return the searchName
     */
    public String getSearchName() {
        return searchName;
    }

    /**
     * @param searchName the searchName to set
     */
    public void setSearchName(final String searchName) {
        this.searchName = searchName;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		final int isMatchTrueHashCode = 1231;
		final int isMatchFalseHashCode = 1237;
		result = prime * result + ((criteria == null) ? 0 : criteria.hashCode());
		result = prime * result + ((iirSearchType == null) ? 0 : iirSearchType.hashCode());
		result = prime * result + (isMatch ? isMatchTrueHashCode : isMatchFalseHashCode);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		IIRSearchRequestImpl other = (IIRSearchRequestImpl) obj;
		if (criteria == null) {
			if (other.criteria != null) {
				return false;
			}
		} else if (!criteria.equals(other.criteria)) {
			return false;
		}
		if (iirSearchType == null) {
			if (other.iirSearchType != null) {
				return false;
			}
		} else if (!iirSearchType.equals(other.iirSearchType)) {
			return false;
		}
		if (isMatch != other.isMatch) {
			return false;
		}
		return true;
	}
    
    
}
