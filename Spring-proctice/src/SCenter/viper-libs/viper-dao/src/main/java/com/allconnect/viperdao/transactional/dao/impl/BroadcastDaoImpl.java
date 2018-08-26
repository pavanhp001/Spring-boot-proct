package com.A.Vdao.transactional.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.Broadcast;
import com.A.Vdao.dao.BroadcastDao;

@Component
public class BroadcastDaoImpl extends BaseTransactionalJpaDao implements BroadcastDao {

	private static final Logger logger = Logger.getLogger(BroadcastDaoImpl.class);

	private static final String NEXT_VAL = "select nextval ('OM_BROADCAST_SEQ')";

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public long save(Broadcast broadcast) {
		logger.info("Saving broadcast message");
		long starttime = System.currentTimeMillis();
		if(getEntityManager() != null){
			if(broadcast.getId() == 0){
				broadcast.setExternalId(getNextExternalId());
				String msgHeaders = broadcast.getMessageHeaders();
				msgHeaders = msgHeaders + "broadcast_id#" +broadcast.getExternalId();
				broadcast.setMessageHeaders(msgHeaders);
				getEntityManager().persist(broadcast);
			}else{
				getEntityManager().merge(broadcast);
			}
		}
		logger.info("Saving brodacast message took : " + (System.currentTimeMillis() - starttime) + "ms");
		return broadcast.getExternalId();
	}

	/*public Long getNextExternalId() {
	Query q = getEntityManager().createNativeQuery(NEXT_VAL);

	Object obj = q.getResultList().get(0);

	return DataTypeConverter.INSTANCE.objToLong(obj);
	}*/
	
	public Long getNextExternalId() {
	  Session session = (Session)getEntityManager().getDelegate();
	  Connection conn = ((SessionImpl)session).connection();
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  long externalId = 0;
	  try {
	   ps = conn.prepareStatement(NEXT_VAL);
	   rs = ps.executeQuery();
	   rs.next();
	   externalId = rs.getLong("NEXTVAL");
	  } catch (SQLException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  } finally {
	   if(rs != null) {
	          try {
	           rs.close();
	          } catch(SQLException e) {
	           e.printStackTrace();
	          }
	      }
	   
	   if(ps != null) {
	          try {
	           ps.close();
	          } catch(SQLException e) {
	           e.printStackTrace();
	          }
	      }
	  }
	  return externalId;
	 }
}
