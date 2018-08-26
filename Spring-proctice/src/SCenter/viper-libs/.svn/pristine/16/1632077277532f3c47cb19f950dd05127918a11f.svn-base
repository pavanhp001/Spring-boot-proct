package com.ac.tests;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.A.V.beans.entity.SalesOrderContext;
import com.A.vm.util.converter.marshall.MarshallSalesOrderContext;
import com.A.xml.v4.SalesContextType;

public class MarshallSalesContextTest {

	private MarshallSalesOrderContext marshallSOContext;

	@Before
	public void setUp() throws Exception {
		marshallSOContext = new MarshallSalesOrderContext();

		assertNotNull(marshallSOContext);

	}

	@Test
	public void testMarshallContext(){
		List<SalesOrderContext> socList = getDummyData();
		SalesContextType scType = marshallSOContext.build(socList);
		assertNotNull(scType);
		System.out.println(scType);
	}

	private List<SalesOrderContext> getDummyData(){
		List<SalesOrderContext> soList = new ArrayList<SalesOrderContext>();
		SalesOrderContext ctx1 = new SalesOrderContext();
		ctx1.setEntityName("Test1");
		ctx1.setName("Name1");
		ctx1.setValue("Value1");
		soList.add(ctx1);


		SalesOrderContext ctx3 = new SalesOrderContext();
		ctx3.setEntityName("Test2");
		ctx3.setName("Name3");
		ctx3.setValue("Value3");
		soList.add(ctx3);


		SalesOrderContext ctx2 = new SalesOrderContext();
		ctx2.setEntityName("Test1");
		ctx2.setName("Name2");
		ctx2.setValue("Value2");
		soList.add(ctx2);


		SalesOrderContext ctx4 = new SalesOrderContext();
		ctx4.setEntityName("Test2");
		ctx4.setName("Name4");
		ctx4.setValue("Value4");
		soList.add(ctx4);

		return soList;
	}
}
