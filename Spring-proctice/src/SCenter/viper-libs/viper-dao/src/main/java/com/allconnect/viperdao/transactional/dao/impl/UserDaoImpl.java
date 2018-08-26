package com.A.Vdao.transactional.dao.impl;

import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.User;
import com.A.Vdao.dao.UserDao;
//import com.whirlycott.cache.Cache;
//import com.whirlycott.cache.CacheException;
//import com.whirlycott.cache.CacheManager;

@Component
public class UserDaoImpl extends BaseTransactionalJpaDao implements UserDao {
    private static final String FIND_USER_BY_USERLOGIN = "SELECT u FROM User u WHERE u.userLogin = :userLogin ";
    private static final String FIND_USER_BY_AGENTID = "SELECT u.* FROM uam_user u, uam_user_metadata md where u.user_id=md.uam_user_id AND lower(md.name)='agentid' AND md.value=:value";
    private static final String USER_LOGIN = "userLogin";
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private static final long TIME_IN_CACHE = 1000 * 60 * 5; // 1 minutes
    private static final String USER_PREFIX = "user-";
    private static final User DEFAULT_NULL_USER_TYPE = null;
    private static final int CACHE_SIZE = 1000;

    private static Object rulesCacheKey = new Object();

    // TODO:Replace with Cache

    // public static LRUMap userCache = new LRUMap(CACHE_SIZE);

    // static {
    //
    // if (userCache == null) {
    // synchronized (rulesCacheKey) {
    // if (userCache == null) {
    // userCache = new LRUMap(CACHE_SIZE);
    // }
    // }
    // }
    //
    // }

    /**
     * Searching user by user login
     */
    public User findUserByUserLogin(String userLogin) {
	logger.info("Executing findUserByUserLogin : " + userLogin);
	long start = System.currentTimeMillis();
	User user = null;

	if (getEntityManager() != null) {

	    try {
		Query query = getEntityManager().createQuery(FIND_USER_BY_USERLOGIN);
		query.setParameter(USER_LOGIN, userLogin);
		Object obj = query.getSingleResult();

		if ((obj != null) && (obj instanceof User)) {
		    user = (User) obj;
		}
	    }
	    catch (NoResultException nre) {
		logger.warn("Unable to locate User with Id:" + userLogin);
		user = new User();
		user.setUserLogin(userLogin);
		user.setUserName(userLogin);
		user.setUserUpdatedDate(new Date());

	    }
	}
	logger.info("findUserByUserLogin took : " + (System.currentTimeMillis() - start) + "ms");
	return user;
    }

    /**
     * Searching user by user login
     */
    public User findUserByAgentId(String agentId) {
	logger.info("findUserByAgentId : " + agentId);
	long start = System.currentTimeMillis();
	User user = null;
	if (getEntityManager() != null) {

	    try {
		Query query = getEntityManager().createNativeQuery(FIND_USER_BY_AGENTID, User.class);
		//query.setParameter(1, agentId);
		query.setParameter("value", agentId);
		Object obj = query.getSingleResult();

		if ((obj != null) && (obj instanceof User)) {
		    user = (User) obj;
		}
	    }
	    catch (NoResultException nre) {
		logger.warn("Unable to locate User with Id:" + agentId);
		user = new User();
		user.setUserLogin(agentId);
		user.setUserName(agentId);
		user.setUserUpdatedDate(new Date());
	    }
	}
	logger.info("findUserByAgentId took : " + (System.currentTimeMillis() - start) + "ms");
	return user;
    }

}
