package com.A.V;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.xml.bind.JAXBElement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.A.ui.service.V.ProductService;
import com.A.ui.template.ProductTemplateConstant;
import com.A.V.domain.SalesContext;
import com.A.V.gateway.util.FileUtil;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.di.v4.NameValuePairType;
import com.A.xml.di.v4.SalesContextEntityType;
import com.A.xml.pr.v4.AddressType;
import com.A.xml.pr.v4.CapabilityType;
import com.A.xml.pr.v4.EnterpriseResponseDocumentType;
import com.A.xml.pr.v4.FeatureType;
import com.A.xml.pr.v4.ProductResponseType;
import com.A.xml.pr.v4.ProviderResults;
import com.A.xml.pr.v4.ProviderSourceBaseType;
import com.A.xml.pr.v4.ProviderSourceType;
import com.A.xml.pr.v4.ProviderType;
import com.A.xml.pr.v4.ProductRequestType.ProviderList;
import com.A.xml.pr.v4.ProviderType.CapabilityList;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class ProductsTest {

	private static final JaxbUtil<ProviderList> providerListUtil = new JaxbUtil<ProviderList>();
	
	@Test
	public void testUnmarshallOrderFromFile() {

		String xmlInput = FileUtil.INSTANCE
				.readFileToString("src\\test\\resources\\xml\\products-1.xml");

		JaxbUtil<ProviderResults> util = new JaxbUtil<ProviderResults>();
		ProviderResults pr = util.toObject(xmlInput, ProviderResults.class);
		
		assertNotNull(xmlInput);
		assertEquals(1,pr.getProductInfo().size());
		assertEquals(37,pr.getProductInfo().get(0).getProductDetails().getFeature()
						.size());

	}
	
	@Test
	public void testUnmarshallOrderFromFile2() {

		String xmlInput = FileUtil.INSTANCE
				.readFileToString("src\\test\\resources\\xml\\productResponse.xml");

		JaxbUtil<EnterpriseResponseDocumentType> util = new JaxbUtil<EnterpriseResponseDocumentType>();
		JAXBElement<EnterpriseResponseDocumentType> product = util.toObject(
				xmlInput, "com.A.xml.pr.v4");

		EnterpriseResponseDocumentType response = product.getValue();
		ProductResponseType productResponse = (ProductResponseType) response.getResponse();
		FeatureType feature =  productResponse.getProviderResults().get(0).
			getProductInfo().get(0).getProductDetails().getFeature().get(0);
		assertNotNull(feature.getExternalId());
		assertNotNull(feature.getCapabilities().getFeatureCapability().get(0).getName());
	}
	
	
	@Test
	public void testCreateGetProductsRequest() {
		String s = ProductTemplateConstant.INSTANCE.getProductsRequest(getProviderList(), getAddress(), "123456", getSalesContext());
		assertNotNull(s);
	}
	
	public SalesContext getSalesContext() {
		SalesContext salesContext = new SalesContext();
		SalesContextEntityType entity = new SalesContextEntityType();
		entity.setName("dwelling");
		NameValuePairType pair = new NameValuePairType();
		pair.setName("dwelling.dwellingType");
		pair.setValue("house");
		entity.getAttribute().add(pair);
		salesContext.getEntity().add(entity);
		//
		entity = new SalesContextEntityType();
		entity.setName("orderSource");
		pair = new NameValuePairType();
		pair.setName("orderSource.channel");
		pair.setValue("3");
		entity.getAttribute().add(pair);
		salesContext.getEntity().add(entity);
		return salesContext;
	}
	
	@Test
	public void testGetProducts() {
		ProductService.INSTANCE.getProducts(getProviderList(), getAddress(), "b00af7b1-1d3f-11e2-99a3-ad952dddb39e", getSalesContext());
	}
	
	public AddressType getAddress() {
		AddressType address = new AddressType();
		address.setPostalCode("30303");
		return address;
	}
	
	public ProviderList getProviderList() {
		ProviderList providerList = new ProviderList();
		ProviderType provider = new ProviderType();
		CapabilityList capabilityList = new CapabilityList();
		CapabilityType capability = new CapabilityType();
		capability.setName("wiredDataUpSpeed");
		capabilityList.getCapability().add(capability);
		provider.setCapabilityList(capabilityList);
		provider.setExternalId("5853");
		provider.setName("AT&T Southeast");
		ProviderSourceType source = new ProviderSourceType();
		source.setDatasource("ATTSTI");
		source.setValue(ProviderSourceBaseType.REALTIME);
		provider.setSource(source);
		ProviderType parent = new ProviderType();
		parent.setName("AT&T");
		parent.setExternalId("100");
		provider.setParent(parent);
		providerList.getProvider().add(provider);
		return providerList;
	}

}
