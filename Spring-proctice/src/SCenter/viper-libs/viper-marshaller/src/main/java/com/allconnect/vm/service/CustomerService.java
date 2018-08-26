/**
 *
 */
package com.A.vm.service;

import com.A.V.beans.entity.Consumer;

/**
 * @author ebthomas
 * 
 */
public interface CustomerService {
	void saveConsumerInteraction(Consumer consumer,   String agentUserId,
			String notes);
	
	void saveConsumerInteraction(Consumer consumer,  String source, String agentUserId,
			String notes);
}
