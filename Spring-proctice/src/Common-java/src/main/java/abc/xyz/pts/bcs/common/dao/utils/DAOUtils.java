/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Deepesh.Rathore
 *
 */
public final class DAOUtils {
    
    private DAOUtils(){}
	
	public static Object[] constructParameterValuesArray(final Object... parameterValues) {
		List<Object> values = new ArrayList<Object>();
		
		for (Object parameterValue : parameterValues) {
			values.add(parameterValue);
		}
		return values.toArray();
	}
	
	
	public static Long getLong(final ResultSet rs, final String columnName) throws SQLException {
        return convertStringToLong(rs.getString(columnName));
    }
    
	public static Integer getInteger(final ResultSet rs, final String columnName) throws SQLException {
        return convertStringToInteger(rs.getString(columnName));
    }

    private static Long convertStringToLong(final String string) {
        return string == null? null : new Long(string);
    }

    private static Integer convertStringToInteger(final String string) {
        return string == null? null : new Integer(string);
    }
	
	public static void setLong(final PreparedStatement ps, final int columnNumber, final Long value) throws SQLException {
            
        if (value != null){
            ps.setLong(columnNumber, value);
        } else {
            ps.setNull(columnNumber, Types.NULL);
        }
	}

	public static void setInt(final PreparedStatement ps, final int columnNumber, final Integer value) throws SQLException {
        if (value != null){
            ps.setInt(columnNumber, value);
        } else {
            ps.setNull(columnNumber, Types.NULL);
        }
	}
}
