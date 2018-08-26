package com.A.V.jms;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import com.A.ui.service.V.PromotionService;

public class JMSPromotionTest {

	@Test
	public void testGetPromotionJMS() throws Exception {

		String promotionDate = PromotionService.INSTANCE
				.getPromotionInfo("6290");
		 
		assertNotNull(promotionDate);
		
	}
}
