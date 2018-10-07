/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.irisk.dao;

import java.sql.Types;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import abc.xyz.pts.bcs.common.bean.UserDetails;
import abc.xyz.pts.bcs.common.dao.exception.PersistenceProcedureException;
import abc.xyz.pts.bcs.common.dao.support.AbstractDAO;
import abc.xyz.pts.bcs.common.dao.support.CachingJdbcCallFactory;
import abc.xyz.pts.bcs.common.dao.support.JdbcCallConfigurer;

public class GetUserNameDAO extends AbstractDAO {
    private SimpleJdbcCall simpleJdbcCall;
    private static final String VERSION = "$Id: GetUserNameDAO  $";
    private static final Logger log = Logger.getLogger( GetUserNameDAO.class );

    private UserDetails coreUserDetails = null;


    public void setCoreUserDetails( final UserDetails userDetails ) {
        this.coreUserDetails = userDetails;
    }


    public static class GetUserNameConfigurer extends JdbcCallConfigurer< SimpleJdbcCall > {
        @Override
        public SimpleJdbcCall configure( SimpleJdbcCall simpleJdbcCall ) {
            simpleJdbcCall.withoutProcedureColumnMetaDataAccess();
            simpleJdbcCall = simpleJdbcCall.declareParameters(
                    new SqlOutParameter( "return", Types.VARCHAR )
            );
            simpleJdbcCall.compile();
            return simpleJdbcCall;
        }
    }

    public String execute( ) throws PersistenceProcedureException {
        simpleJdbcCall = CachingJdbcCallFactory.getSimpleJdbcCall( getJdbcTemplate(), getPackageName(), getProcedureName(), new GetUserNameConfigurer(), true );
        try {
            return simpleJdbcCall.executeFunction( String.class);
        }
        catch( final Exception e ) {
            log.error( "Error retrieving usernName " + VERSION, e );
            throw new PersistenceProcedureException( e );
        }

    }

}
