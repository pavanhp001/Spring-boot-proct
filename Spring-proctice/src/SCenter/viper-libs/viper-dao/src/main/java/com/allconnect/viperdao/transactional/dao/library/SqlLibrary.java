package com.A.Vdao.transactional.dao.library;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public enum SqlLibrary {

	INSTANCE;

	private enum DBType {
		oracle, postgres;
	}

	private final DBType DATABASE_TYPE = DBType.postgres;

	private final String DELETE_COMPLETED = "delete from om_job where status = 1000";
	
	private Calendar cal = Calendar.getInstance();

	private String ORACLE_BASE_CLEAR_LOCKS = " UPDATE OM_JOB   SET IS_LOCKED = 0, TTL = -1, LOCKED_AT = null   WHERE   (TTL != -1) " + 
	                                         " AND (IS_LOCKED = 1)  AND (STATUS != 1000) ";
	private String POSTGRES_BASE_CLEAR_LOCKS = " UPDATE OM_JOB   SET IS_LOCKED = FALSE, TTL = -1, LOCKED_AT = null, effective_from_on = null, effective_to_on = null    WHERE   (TTL != -1) " + 
											   " AND (IS_LOCKED = TRUE)  AND (STATUS != 1000) ";

	private String ORACLE_CLEAR_LOCKS = " AND (ROUND( TO_NUMBER(TO_DATE(TO_CHAR(SYSTIMESTAMP, 'DDMMYYYY:HH24:MI:SS'), 'DDMMYYYY:HH24:MI:SS') -LOCKED_AT) * 24 * 60) > TTL) ";
	private String POSTGRES_CLEAR_LOCKS = new String();
	public String getClearLocks() {
		
		cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" ); 
        
		POSTGRES_CLEAR_LOCKS = " AND ( ((DATE_PART('day', CAST('"+formatter.format(cal.getTime())+"' AS TIMESTAMP)  - effective_from_on) * 86400 + DATE_PART('hour', CAST('"+formatter.format(cal.getTime())+"' AS TIMESTAMP)  - effective_from_on)) * 3600  + DATE_PART('minute', CAST('"+formatter.format(cal.getTime())+"' AS TIMESTAMP)  - effective_from_on )*60+ DATE_PART('second', CAST('"+formatter.format(cal.getTime())+"' AS TIMESTAMP)  - effective_from_on ))   > TTL ) ";
		switch (DATABASE_TYPE) {

		case oracle:
			return ORACLE_BASE_CLEAR_LOCKS + ORACLE_CLEAR_LOCKS;
		case postgres:
			return POSTGRES_BASE_CLEAR_LOCKS + POSTGRES_CLEAR_LOCKS;

		}

		return POSTGRES_BASE_CLEAR_LOCKS;
	}

	public String deleteCompletedJob() {
		return DELETE_COMPLETED;

	}

}
