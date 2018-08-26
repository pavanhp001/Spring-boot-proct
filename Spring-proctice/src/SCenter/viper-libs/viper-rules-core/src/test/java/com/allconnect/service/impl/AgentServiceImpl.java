package com.A.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.A.V.beans.entity.User;
import com.A.Vdao.dao.OrderManagementDao;
import com.A.vm.service.AgentService;
import com.A.vm.vo.OrderChangeValueObject;

/**
 * @author ebthomas
 * 
 */

@Component
public class AgentServiceImpl implements AgentService {

	@Autowired
	private OrderManagementDao orderManagementDao;

	public AgentServiceImpl() {
		super();
	}

	/**
	 * @param agentId
	 *            id of the agent for selection
	 * @return AgentBean based on id
	 */
	public User findAgentById(final String agentId) {

		User ab = orderManagementDao.findAgentById(agentId);

		return ab;
	}

	private User createDefaultUser() {
		User u = new User();
		u.setUserId(1);
		u.setUserLogin("default");
		u.setUserName("default");
		 

		return u;
	}

	public User getAgent(final String agentId) {

		User agentBean = findAgentById(agentId.trim());

		if ((agentBean == null) && (agentId.equals("1"))) {
			return createDefaultUser();
		}

		if (agentBean == null) {
			throw new IllegalArgumentException("User login not found."
					+ agentId);
		}

		return agentBean;
	}

	public User getAgent(OrderChangeValueObject orderChangeValueObject) {


		if (orderChangeValueObject.getAgentId() == null) {
			throw new IllegalArgumentException("Invalid Agent Id");
			 
		}

		User agent = null;

		try {
			agent = getAgent(orderChangeValueObject.getAgentId());
		} catch (Exception e) {
			e.printStackTrace();
			/*
			 * agent = new User(); agent.setUserLevel( "User1" );
			 */
		}

		return agent;

	}

	public OrderManagementDao getOrderManagementDao() {
		return orderManagementDao;
	}

	public void setOrderManagementDao(OrderManagementDao orderManagementDao) {
		this.orderManagementDao = orderManagementDao;
	}

}
