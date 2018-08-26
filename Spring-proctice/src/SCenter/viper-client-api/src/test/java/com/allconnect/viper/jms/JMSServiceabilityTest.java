package com.A.V.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.A.ui.template.ServiceabilityTemplateConstant;
import com.A.V.domain.SalesContext;
import com.A.V.gateway.ServiceabilityClient;
import com.A.V.gateway.jms.ServiceabilityClientJMS;
import com.A.xml.di.v4.NameValuePairType;
import com.A.xml.di.v4.SalesContextEntityType;
import com.A.xml.se.v4.ProviderCriteriaEntityType2;
import com.A.xml.se.v4.ProviderCriteriaType2;
import com.A.xml.se.v4.ProviderNameValuePairType2;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class JMSServiceabilityTest {
	
	@Test
	public void getServiceability() {
		String naddr = "7 DECATUR ST SE, ATLANTA GA 30303-2906";
		String request = ServiceabilityTemplateConstant.INSTANCE.getServiceabilityRequest(naddr, createSalesContext(), 
				createProviderCriteria());
		ServiceabilityClient<String> client = new ServiceabilityClientJMS();
		client.send(request);	
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
