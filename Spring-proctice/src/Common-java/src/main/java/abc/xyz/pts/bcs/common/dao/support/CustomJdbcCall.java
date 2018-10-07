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
package abc.xyz.pts.bcs.common.dao.support;

import java.sql.Types;
import java.util.List;
import java.util.ListIterator;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class CustomJdbcCall extends SimpleJdbcCall {

    public CustomJdbcCall(final JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private void substitute(final int originalType, final int replacementType) {
        List<SqlParameter> params = getCallParameters();

        // Iterator< SqlParameter > pi = params.iterator();

        ListIterator<SqlParameter> li = (ListIterator<SqlParameter>) params.listIterator();
        while (li.hasNext()) {
            SqlParameter param = li.next();
            if (param instanceof SqlOutParameter) {
                int sqlType = param.getSqlType();
                SqlOutParameter oparam = (SqlOutParameter) param;
                if (sqlType == originalType) {
                    if (oparam.getResultSetExtractor() != null || oparam.getRowCallbackHandler() != null
                            || oparam.getRowMapper() != null || oparam.getScale() != null) {
                        throw new RuntimeException("cannot convert sql type of SqlOutParameter: " + oparam.getName()
                                + " type: " + oparam.getTypeName());
                    }
                    SqlParameter subs = new SqlOutParameter(oparam.getName(), replacementType, oparam.getTypeName(),
                            oparam.getSqlReturnType());
                    li.set(subs);
                }
            }
        }

        /*
         * for( int i = 0; i < params.size(); i++ ) { SqlParameter param = params.get( i ); if( param instanceof
         * SqlOutParameter ) { int sqlType = param.getSqlType(); SqlOutParameter oparam = ( SqlOutParameter )param; if(
         * sqlType == originalType ) { if( oparam.getResultSetExtractor() != null || oparam.getRowCallbackHandler() !=
         * null || oparam.getRowMapper() != null || oparam.getScale() != null ) { throw new RuntimeException(
         * "cannot convert sql type of SqlOutParameter: " + oparam.getName() + " type: " + oparam.getTypeName() ); }
         * SqlParameter subs = new SqlOutParameter( oparam.getName(), replacementType, oparam.getTypeName(),
         * oparam.getSqlReturnType() ); params.set( i, subs ); } } }
         */
    }

    @Override
    protected void onCompileInternal() {
        substitute(Types.DECIMAL, Types.BIGINT);
    }

}