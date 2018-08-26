package com.AL.util.audit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.Vdao.dao.AuditDao;
import com.AL.Vdao.transactional.dao.impl.AuditDaoImpl;



/**
 * @author ebthomas
 *
 * @param <T>
 *            Context Data Type
 */

@Component
public class AuditServiceDefault<T> implements AuditService<T> {

	@Autowired
	private AuditDao<T> dataAccess;

	public AuditServiceDefault()
	{
		super();
	}

	public AuditDao<T> getAuditDao() {

		if (dataAccess == null) {
			dataAccess = new AuditDaoImpl<T>();
		}
		return (AuditDao<T>) dataAccess;

	}



	public AuditDao<T> getDataAccess() {

		return dataAccess;
	}

	public Boolean audit(T auditable) {

//		AuditDao<T> dao = getAuditDao();
//		return dao.audit(auditable);
	    return true;
	}

}
