package com.A.V.jms;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.A.ui.service.V.ProductService;
import com.A.ui.service.V.impl.ProductCacheService;
import com.A.ui.template.ProductTemplateConstant;
import com.A.V.domain.SalesContext;
import com.A.V.factory.SalesContextFactory;
import com.A.V.gateway.jms.ProductClientJMS;
import com.A.V.mo.dialog.ProductMotherObject;
import com.A.xml.pr.v4.EnterpriseResponseDocumentType;
import com.A.xml.pr.v4.ProductInfoType;
import com.A.xml.pr.v4.ProductResponseType;


/**
 *
 */

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/V-client-api-app-context.xml" })
public class JMSProductTest {

	//String provider = "12228509";
	//String product = "TW-CHARLOTTE-BUNDLE3";
	
	String provider = "24699452";
	String product = "RTS:ATTSTI:ATT-9-2TV-200-UNL-I";

	@Test
	public void testGetProductUsingCache() throws Exception {

		SalesContext salesContext = SalesContextFactory.INSTANCE
				.getSalesContext(ProductMotherObject.INSTANCE.getData());

		String context = SalesContextFactory.INSTANCE.toString(salesContext);

		ProductInfoType productResponse = ProductCacheService.INSTANCE
				.get(provider, product);

		assertNull(productResponse);

		productResponse = ProductService.INSTANCE.getProduct(provider, product,
				"", salesContext);
		assertNotNull(productResponse);

		productResponse = ProductCacheService.INSTANCE.get(
				provider, product);
		assertNotNull(productResponse);

		productResponse = ProductCacheService.INSTANCE.clear(
				provider, product);
		productResponse = ProductCacheService.INSTANCE.get(
				provider, product);
		assertNull(productResponse);
	}

	@Test
	public void testGetProductUsingServiceMap() throws Exception {

		ProductInfoType productResponse = ProductService.INSTANCE
				.getProduct(provider, product, ProductMotherObject.INSTANCE.getData());
		assertNotNull(productResponse);

	}
	

	@Test
	public void testGetProduct() throws Exception {

		SalesContext salesContext = SalesContextFactory.INSTANCE
				.getSalesContext(ProductMotherObject.INSTANCE.getData());

		String productRequestTemplate = ""; 
		//ProductTemplateConstant.INSTANCE.getProductRequest(provider, product, "f36dbeb1-0d70-11e2-99a3-ad952dddb39e", salesContext);

		ProductClientJMS jmsClient = new ProductClientJMS();

		EnterpriseResponseDocumentType response = (EnterpriseResponseDocumentType) jmsClient
				.send(productRequestTemplate);

		assertNotNull(response);
		assertNotNull(response.getGUID());

		ProductResponseType productResponse = (ProductResponseType) response
				.getResponse();
		assertNotNull(productResponse);

		assertNotNull(productResponse.getProviderResults());
		assertEquals(1, productResponse.getProviderResults().size());

		assertEquals(provider, productResponse.getProviderResults().get(0)
				.getProvider().getExternalId());

		ProductInfoType productInfo = productResponse.getProviderResults()
				.get(0).getProductInfo().get(0);
		assertNotNull(productInfo);

		assertEquals(product, productInfo.getExternalId());

	}
	
	

}
