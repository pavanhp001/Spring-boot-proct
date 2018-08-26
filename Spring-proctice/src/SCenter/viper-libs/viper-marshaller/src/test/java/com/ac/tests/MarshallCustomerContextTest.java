package com.ac.tests;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerContext;
import com.A.vm.util.converter.marshall.MarshallCustomerContext;
import com.A.xml.v4.CustomerManagementRequestResponseDocument;
import com.A.xml.v4.CustomerManagementRequestResponseDocument.CustomerManagementRequestResponse.Response;

public class MarshallCustomerContextTest {

	private MarshallCustomerContext marshallCustContext;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMarshallContext(){
		List<CustomerContext> ctxList = getDummyData();
		Consumer consumer = new Consumer();
		consumer.setCustomerContexts(ctxList);

		CustomerManagementRequestResponseDocument custDoc = CustomerManagementRequestResponseDocument.Factory.newInstance();
		Response res = custDoc.addNewCustomerManagementRequestResponse().addNewResponse();
		marshallCustContext.buildCustomerContext(res, consumer);
		assertNotNull(res.getCustomerContext());
		System.out.println(res.getCustomerContext());
	}

	private List<CustomerContext> getDummyData(){
		List<CustomerContext> soList = new ArrayList<CustomerContext>();
		CustomerContext ctx1 = new CustomerContext();
		ctx1.setEntityName("Test1");
		ctx1.setName("Name1");
		ctx1.setValue("Value1");
		soList.add(ctx1);

		CustomerContext ctx2 = new CustomerContext();
		ctx2.setEntityName("Test1");
		ctx2.setName("Name2");
		ctx2.setValue("Value2");
		soList.add(ctx2);


		CustomerContext ctx3 = new CustomerContext();
		ctx3.setEntityName("Test2");
		ctx3.setName("Name3");
		ctx3.setValue("Value3");
		soList.add(ctx3);


		CustomerContext ctx4 = new CustomerContext();
		ctx4.setEntityName("Test2");
		ctx4.setName("Name4");
		ctx4.setValue("Value4");
		soList.add(ctx4);
//
//		CustomerContext ctx5 = new CustomerContext();
//		ctx5.setEntityName("Test3");
//		ctx5.setName("Name5");
//		ctx5.setValue("Value5");
//		soList.add(ctx5);

		return soList;
	}
}
