package com.A.V.jms;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.A.ui.service.V.ProductService;
import com.A.ui.template.Dwelling;
import com.A.xml.pr.v4.ProductInfoType;

/**
 *
 */

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/V-client-api-app-context.xml" })
public class JMSProductsGetTest {

	
	@Test
	public void testGetProductUsingAddress() throws Exception {

		//String completeAddress =  "65 walnut road  amityville,  11701";
		String completeAddress = "945 Pebblestone Court, Alpharetta, Georgia 30009";
		 
		List<ProductInfoType> productList = ProductService.INSTANCE
				.getServiceabilityProductList(completeAddress, Dwelling.house);

		assertNotNull(productList);

		for (ProductInfoType pit : productList) {
			assertNotNull(pit.getProduct().getExternalId());
		}
	}

}
