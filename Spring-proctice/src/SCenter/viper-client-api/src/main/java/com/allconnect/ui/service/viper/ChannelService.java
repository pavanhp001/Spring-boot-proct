/**
 * 
 */
package com.A.ui.service.V;

import com.A.V.gateway.jms.HdClientJMS;



/**
 * @author satish
 *
 */
public enum ChannelService {

	INSTANCE;

	public String getHdChannelInfo(String GUID) {

		HdClientJMS hdClientJMS = new HdClientJMS();
		
		String promotionResponse = hdClientJMS.send(GUID);

		return promotionResponse;
	}
}
