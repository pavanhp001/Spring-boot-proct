package com.A.V;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.A.ui.service.V.DetailService;
import com.A.ui.template.DetailsTemplateConstant;
import com.A.xml.dtl.v4.DetailsRequestResponse;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class DetailManagementTest {
	
	@Test
	public void testReadDetailManagementResponse() {
		String s;
		try {
			 s = FileUtils.readFileToString(new File(".\\src\\test\\resources\\xml\\detailManagementResponse.xml"));
			 DetailsRequestResponse response = DetailsTemplateConstant.INSTANCE.toObject(s);
			 assertNotNull(response.getCorrelationId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetDetailsResponse() {
		
		DetailsRequestResponse response = DetailService.INSTANCE.getAllOrderSources("12345");
		assertNotNull(response);
		assertNotNull(response);
	}

}
