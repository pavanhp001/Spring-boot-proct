package com.A.common.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.xmlbeans.XmlException;
import org.junit.Before;
import org.junit.Test;

import com.A.ome.system.BaseATestX;
import com.A.validation.impl.CustomerValidationHelper;
import com.A.xml.v4.CustomerManagementRequestResponseDocument;
import com.A.xml.v4.CustomerType;

public class CustomerValidationHelperTest {

	String xml = null;
	
	@Before
	public void setUp() throws Exception {
		
		xml = BaseATestX.getXMLFromFile("src\\test\\resources\\xml\\customer.xml");
		
		assertNotNull(xml);
	}

	@Test
	public void testCheckDuplicateUniqueIdsExist() throws XmlException {
		CustomerManagementRequestResponseDocument custDoc = CustomerManagementRequestResponseDocument.Factory.parse(xml);
		assertNotNull(custDoc);
		CustomerType custType = custDoc.getCustomerManagementRequestResponse().getRequest().getCustomerInfo();
		assertNotNull(custType);
		Boolean isDuplicateExist = CustomerValidationHelper.checkDuplicateUniqueIdsExist(custType);
		System.out.println("isDuplicateExist : " + isDuplicateExist);
		assertFalse(isDuplicateExist);
	}

}
