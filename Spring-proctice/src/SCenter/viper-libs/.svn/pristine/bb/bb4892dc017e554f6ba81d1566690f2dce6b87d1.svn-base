package com.A.vm.util.converter.unmarshall;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.CustomerInteraction;
import com.A.V.beans.entity.User;
import com.A.Vdao.dao.CustomerInteractionDao;
import com.A.vm.service.CustomerAgentService;
import com.A.xml.v4.CustomerInteractionType;




@Component
public class UnmarshallCustomerInteraction
{
	private static final Logger logger  = Logger.getLogger( UnmarshallCustomerInteraction.class );

	@Autowired(required = false)
	private CustomerAgentService agentService;

	public CustomerInteraction copyConsumerInteractionInfo(CustomerInteractionDao customerInteractionDao, final CustomerInteractionType srcCustIntn)
	{
		Long externalId = Long.valueOf(srcCustIntn.getExternalId());
		logger.debug( "Unmarshalling Customer Interaction" + externalId);

		CustomerInteraction dest = null;
		if(srcCustIntn != null){
			if(externalId != null && externalId != 0L && externalId != -1L){
				dest = customerInteractionDao.findConsumerInteractionById(externalId);
			} else {
				dest = new CustomerInteraction();
			}

			dest.setCustomerExternalId( srcCustIntn.getCustomerId() );
			String agentId = srcCustIntn.getAgentId();

			Boolean isValid = validateAgent(agentId);
			if(isValid) {
				dest.setAgentExternalId( agentId );
			}else {
				throw new IllegalArgumentException("AgentId provided does not exist :" + agentId);
			}

			dest.setOrderExternalId( srcCustIntn.getOrderId() );
			dest.setSource( srcCustIntn.getSource() );
			dest.setNotes( srcCustIntn.getNotes() );
			dest.setDateOfInteraction( Calendar.getInstance() );
			dest.setLineItemExternalId(srcCustIntn.getLineItemId());
			if(srcCustIntn.getServiceType() != null) {
			    dest.setServiceType(srcCustIntn.getServiceType());
			}
			long providerId = Long.valueOf(srcCustIntn.getProviderId());
			dest.setProviderId(providerId);
		}
		return dest;
	}

	private Boolean validateAgent(String agentId) {
		User user = agentService.findAgentById(agentId);
		if(user == null) {
			return false;
		}
		return true;
	}

}
