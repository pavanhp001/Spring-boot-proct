package com.A.Vdao.dao;

import java.util.List;
import com.A.V.beans.entity.CustomerInteraction;



public interface CustomerInteractionDao {

	public void saveConsumerInteraction(CustomerInteraction ci);
	public List<CustomerInteraction> findInteractionByAgentId(String agentId, int offSet, int totalRows);
	public List<CustomerInteraction> findConsumerInteractionByConsumerId(Long consumerExtId, int offSet, int totalRows);
	public List<CustomerInteraction> findConsumerInteractionByLineItemId(Long lineItemExtId, int offSet, int totalRows );
	public List<CustomerInteraction> findConsumerInteractionByOrderId(Long orderExtId, int offSet, int totalRows );
	public CustomerInteraction findConsumerInteractionById(Long externalId);
}
