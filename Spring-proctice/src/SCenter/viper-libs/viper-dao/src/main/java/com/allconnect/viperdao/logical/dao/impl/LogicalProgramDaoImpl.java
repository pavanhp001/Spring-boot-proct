package com.A.Vdao.logical.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.A.V.beans.entity.LogicalProgram;
import com.A.Vdao.dao.LogicalProgramDao;
import org.apache.log4j.Logger;

@Component
public class LogicalProgramDaoImpl extends LogicalJpaDao implements LogicalProgramDao{
	
	private static final Logger logger = Logger.getLogger( LogicalProgramDaoImpl.class );

	private static final String SLCT_CNL_FRM_PROG_EXT_ID_SQL = "SELECT channel FROM PROGRAM WHERE externalId= :externalId";
	private static final String PRGRAM_EXT_ID = "externalId";
	private static final String ONLINE = "Online";
	
	/**
	 * default factory constructor
	 */
	public LogicalProgramDaoImpl(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see com.A.Vdao.dao.LogicalProgramDao#isOnline(java.lang.String)
	 */
	public boolean isOnline(String programExtId) {

		EntityManager entityManager = getEntityManager();
		if(null != entityManager){
			logger.debug("programExtId:"+programExtId);
			Query query = entityManager.createNativeQuery(SLCT_CNL_FRM_PROG_EXT_ID_SQL);
			query.setParameter(PRGRAM_EXT_ID, programExtId);
			Object object = query.getSingleResult();
			
			logger.info("LogicalProgramDaoImpl Program Channel:"+object.toString());
			
			if(null != object){
				String program = (String)object;
				if(null != program && program.equalsIgnoreCase(ONLINE)){
					return true;
				}
			}
		}
		return false;
	}
}
