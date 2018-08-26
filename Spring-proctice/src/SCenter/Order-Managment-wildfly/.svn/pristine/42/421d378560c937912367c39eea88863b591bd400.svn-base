package com.AL.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.V.beans.entity.BusinessParty;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.service.PartyService;

/**
 * @author ebthomas
 * 
 */
 
@Component
public class PartyServiceImpl implements PartyService
{
    
   @Autowired
   private OrderManagementDao orderManagementDao;

    /**
     * @param emf
     *            Entity Manager Factory
     */
    public PartyServiceImpl(  )
    {

       super();
    }

    /**
     * @param partyId
     *            id of the agent for selection
     * @return AgentBean based on id
     */
    public BusinessParty findPartyById( final String partyId )
    { 
        BusinessParty ab = orderManagementDao.findBusinessPartyById(   partyId );

        return ab;
    }

	 

	public OrderManagementDao getOrderManagementDao() {
		return orderManagementDao;
	}

	public void setOrderManagementDao(OrderManagementDao orderManagementDao) {
		this.orderManagementDao = orderManagementDao;
	}

     

}
