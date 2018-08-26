package com.A.V;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.A.ui.template.MerchandisingTemplateConstant;
import com.A.V.domain.SalesContext;
import com.A.V.factory.SalesContextFactory;
import com.A.V.gateway.MerchandisingClient;
import com.A.V.gateway.jms.MerchandisingClientJMS;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.me.v4.EnterpriseResponseDocumentType;
import com.A.xml.me.v4.MerchandisedProductType;
import com.A.xml.me.v4.MerchandisingRequestType.ProductList;
import com.A.xml.me.v4.MerchandisingResponseType;
import com.A.xml.pr.v4.ProviderResults;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class MerchandisingTest {

	//private static final JaxbUtil<EnterpriseResponseDocumentType> util = new JaxbUtil<EnterpriseResponseDocumentType>();

	@Test
	public void testMerchandisingTemplate() {
		ProductList productList = new ProductList();
		MerchandisedProductType product = new MerchandisedProductType();
		product.setExternalId("RTS:ATTSTI:ATT-9-2IV-MAX-INT-H");
		product.setType("product");
		productList.getProduct().add(product);

		String guidId = "1343318393026537810747303662276106B26D010CAF";

		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(createSalesContext());

		String merchReq = MerchandisingTemplateConstant.INSTANCE.getMerchandisingRequest(productList, guidId, salesContext);

		assertNotNull(merchReq);
		assertNotNull(merchReq);
	}


	@Test
	public void testGetMerchandisedProduct() {
		ProductList productList = new ProductList();
		MerchandisedProductType product = new MerchandisedProductType();
		product.setExternalId("RTS:ATTSTI:ATT-9-2IV-MAX-INT-H");
		product.setType("product");
		productList.getProduct().add(product);

		String guidId = "1343318393026537810747303662276106B26D010CAF";

		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(createSalesContext());

		String request = MerchandisingTemplateConstant.INSTANCE.getMerchandisingRequest(productList, guidId, salesContext);
		assertNotNull(request);
		assertNotNull(request);
		
		MerchandisingClient<String> client = new MerchandisingClientJMS();
		EnterpriseResponseDocumentType response = client.send(request);
		
		assertNotNull(response);
		assertNotNull(response);
	}

	@Test
	public void testJmsMEResponse() {
		String s;
		try {
			s = FileUtils.readFileToString(new File(".\\src\\test\\resources\\xml\\merchandisingResponse.xml"));

			//MerchandisingResponseType response = MerchandisingTemplateConstant.INSTANCE.toObject(s);
			//assertNotNull(response.getGUID());

			//JAXBElement<MerchandisingResponseType> doc = util.toObject(s, "com.A.se");
			//EnterpriseResponseDocumentType response = doc.getValue();
			//assertNotNull(response.getGUID());

			//JaxbUtil<MerchandisingResponseType> util = new JaxbUtil<MerchandisingResponseType>();
			//MerchandisingResponseType response = util.toObject(s, MerchandisingResponseType.class);

			//assertNotNull(response);

			//assertNotNull(response);
			/*assertNotNull(s);
			assertNotNull(response);
			assertNotNull(response.getProductList());
			assertNotNull(response.getProductList().getMerchandisedProduct());
			assertNotNull(response.getProductList().getMerchandisedProduct().get(0));*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	



	public Map<String, Map<String, String>> createSalesContext() {
		Map<String, Map<String, String>> salesContextData = new HashMap<String, Map<String, String>>();

		Map<String, String> context = new HashMap<String, String>();
		context.put("context.mode", "demo");
		salesContextData.put("context", context);		

		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("orderSource.source", "123");
		orderSource.put("orderSource.referrer", "utility");
		salesContextData.put("orderSource", orderSource);

		Map<String, String> consumer = new HashMap<String, String>();
		consumer.put("consumer.creditScore", "650");
		salesContextData.put("consumer", consumer);

		Map<String, String> dwelling = new HashMap<String, String>();
		dwelling.put("dwelling.dwellingType", "house");
		salesContextData.put("dwelling", dwelling);

		Map<String, String> salesFlow = new HashMap<String, String>();
		salesFlow.put("salesFlow.dialogueType", "core");
		salesContextData.put("salesFlow", salesFlow);

		Map<String, String> agent = new HashMap<String, String>();
		agent.put("agent.capability", "advanced");
		salesContextData.put("agent", agent);

		return salesContextData;
	}	

}
