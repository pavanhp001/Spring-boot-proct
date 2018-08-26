package com.A.V;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBElement;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.A.ui.service.V.ESEService;
import com.A.ui.template.ServiceabilityTemplateConstant;
import com.A.V.domain.SalesContext;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.di.v4.SalesContextEntityType;
import com.A.xml.di.v4.NameValuePairType;
import com.A.xml.se.v4.ProviderCriteriaEntityType2;
import com.A.xml.se.v4.ProviderCriteriaType2;
import com.A.xml.se.v4.ProviderNameValuePairType2;
import com.A.xml.se.v4.ServiceabilityEnterpriseResponse;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class ServiceabilityTest {
	
	@Test
	public void testReadSEResponse() {
		String s;
		try {
			 s = FileUtils.readFileToString(new File(".\\src\\test\\resources\\xml\\serviceabilityResponse.xml"));
			 ServiceabilityEnterpriseResponse response = ServiceabilityTemplateConstant.INSTANCE.toObject(s);
			 assertNotNull(response.getGUID());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetServiceabilityResponse() {
		String naddr = "7 DECATUR ST SE, ATLANTA GA 30303-2906";
		ESEService.INSTANCE.getServiceabilityResponse(naddr, createSalesContext(), 
				createProviderCriteria());
	}

	
	@Test
	public void testSERequestTesplate() {
		String naddr = "7 DECATUR ST SE, ATLANTA GA 30303-2906";
		String request = ServiceabilityTemplateConstant.INSTANCE.getServiceabilityRequest(naddr, createSalesContext(), 
				createProviderCriteria());
		assertNotNull(request);
	}
	
	public SalesContext createSalesContext() {
		SalesContext salesContext = new SalesContext();
		salesContext.setOrderSource(3);
		SalesContextEntityType entity = new SalesContextEntityType();
		entity.setName("service");
		NameValuePairType pair = new NameValuePairType();
		pair.setName("foo");
		pair.setValue("bar");
		entity.getAttribute().add(pair);
		salesContext.getEntity().add(entity);
		return salesContext;
	}
	
	public ProviderCriteriaType2 createProviderCriteria() {
		ProviderCriteriaType2 criteria = new ProviderCriteriaType2();
		ProviderCriteriaEntityType2 entity = new ProviderCriteriaEntityType2();
		entity.setName("ATTSTI");
		ProviderNameValuePairType2 pair = new ProviderNameValuePairType2();
		pair.setName("salesCode");
		pair.setValueAttribute("KRAMERE");
		entity.getAttributes().add(pair);
		criteria.getProviders().add(entity);
		return criteria;
	}
}
