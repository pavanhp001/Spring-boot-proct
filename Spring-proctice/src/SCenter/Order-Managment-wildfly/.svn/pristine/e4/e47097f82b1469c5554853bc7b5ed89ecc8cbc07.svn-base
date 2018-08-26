package com.AL.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.V.beans.entity.User;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.Vdao.dao.UserDao;
import com.AL.vm.service.OrderAgentService;
import com.AL.vm.vo.OrderChangeValueObject;
/**
 * @author ebthomas
 *
 */

@Component
public class AgentServiceImpl implements OrderAgentService {

	private Logger logger = Logger.getLogger(AgentServiceImpl.class);
	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private UserDao userDao;

	public AgentServiceImpl() {
		super();
	}

	/**
	 * @param agentId
	 *            id of the agent for selection
	 * @return AgentBean based on id
	 */
	public User findAgentById(final String agentId) {

		User ab = null;
		//If agent id values are all numeric then that agent id is comming from accord so load it based on user metadata
		if(StringUtils.isNumeric(agentId)){
			ab = userDao.findUserByAgentId(agentId);
		}else{
			ab = orderManagementDao.findAgentById(agentId);
		}



		return ab;
	}

	private User createDefaultUser(String agentId) {
		User u = new User();
		u.setUserId(1);
		u.setUserLogin(agentId);
		u.setUserName(agentId);


		return u;
	}

	public User getAgent(final String agentId) {

		if (agentId == null) {
			throw new IllegalArgumentException("invalid null agent Id");
		}

		User agentBean = findAgentById(agentId.trim());

		if ((agentBean == null) ) {
			logger.debug("using default test user");
			return createDefaultUser(agentId);
		}



		return agentBean;
	}

	public User getAgent(OrderChangeValueObject orderChangeValueObject) {

		logger.debug("validate agent id:" + orderChangeValueObject.getAgentId());

		if (orderChangeValueObject.getAgentId() == null) {

			throw new IllegalArgumentException("missing agent id");
		}

		User agent = null;

		try {
			agent = getAgent(orderChangeValueObject.getAgentId());
		} catch (Exception e) {
			logger.error("Exception thrown while searching for User", e);
			e.printStackTrace();

			throw new IllegalArgumentException("Illegal/Invalid Agent Information");

		}

		return agent;

	}


//	public User findAgentById(String agentId) {
//		return userDao.findUserByUserLogin(agentId);
//	}
//
//	public User getAgent(String agentId) {
//		return userDao.findUserByUserLogin(agentId);
//	}
//
//	public User getAgent(OrderChangeValueObject orderChangeValueObject) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	public OrderManagementDao getOrderManagementDao() {
		return orderManagementDao;
	}

	public void setOrderManagementDao(OrderManagementDao orderManagementDao) {
		this.orderManagementDao = orderManagementDao;
	}

}
