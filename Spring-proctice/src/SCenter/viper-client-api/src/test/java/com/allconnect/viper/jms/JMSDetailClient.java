package com.A.V.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.A.ui.service.V.DetailService;
import com.A.xml.dtl.v4.DetailsRequestResponse;
import com.A.xml.dtl.v4.DetailsRequestResponse.Response;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/V-client-api-app-context.xml" })
public class JMSDetailClient {
	
	@Test
	public void testGetAllOrderSources(){
	DetailsRequestResponse resp = DetailService.INSTANCE.getAllOrderSources();
	Response re = resp.getResponse();
	}
}
