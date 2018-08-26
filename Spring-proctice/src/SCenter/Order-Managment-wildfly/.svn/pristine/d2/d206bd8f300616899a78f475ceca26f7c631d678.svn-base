package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.BaseALTest;
import com.AL.ome.system.BaseALTestX;
import com.AL.task.request.ProductServiceRequestBuilder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.ProductEnterpriseRequestDocument;
import com.AL.xml.v4.ProductRequestType;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class ProductServiceRequestBuilderTest
{
	String inputXml = "";
	private OrderManagementRequestResponseDocument  orderReqResDoc ;

	@Autowired
	private ProductServiceRequestBuilder reqBuilder;

	@Before
	public void setUp() throws Exception
	{
		inputXml = BaseALTest.getXMLFromFile( "src\\test\\resources\\xml\\testProductServiceRequestBuilder.xml" );
		orderReqResDoc = OrderManagementRequestResponseDocument.Factory.parse( inputXml );

	}

	@Test
	public void testBuildRequest()
	{
		assertNotNull( inputXml );
		assertNotNull( orderReqResDoc );
		ProductEnterpriseRequestDocument prodReq = reqBuilder.buildRequest( orderReqResDoc );
		assertNotNull( prodReq );
		ProductRequestType prod = (ProductRequestType) prodReq.getProductEnterpriseRequest().getRequest();
		System.out.println(prod.getProductList().getProductIdList().size());

		//Depending on the order xml(No of lineitems), the following test pass or failed, so make changes accordingly
		assert(prod.getProductList().getProductIdList().size() == 3);

		System.out.println(prodReq.toString());
	}

}
