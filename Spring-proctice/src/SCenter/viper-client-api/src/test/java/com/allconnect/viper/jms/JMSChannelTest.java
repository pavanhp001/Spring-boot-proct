package com.A.V.jms;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import com.A.ui.service.V.ChannelService;

public class JMSChannelTest {

	@Test
	public void testGetPromotionJMS() throws Exception {

		String guid = "cd570dcf-6713-11e2-986a-5b0eea44b80f";
		
		String channelData = ChannelService.INSTANCE.getHdChannelInfo(guid);
		 
		assertNotNull("promotionDate.........................:"+channelData);
		
		assertNotNull(channelData);
		
		
	}
}
