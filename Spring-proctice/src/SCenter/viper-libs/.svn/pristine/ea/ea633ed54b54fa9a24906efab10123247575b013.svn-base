/**
 *
 */
package com.A.ome.system;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.A.util.XmlUtil;
import com.A.xml.v4.CustomerManagementRequestResponseDocument;
import com.A.xml.v4.CustomerType;

/**
 * @author PPatel
 *
 */
public class XMLUtilTest {

	CustomerManagementRequestResponseDocument custRequest;
	CustomerType custType = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		String inputXml = BaseATestX.getXMLFromFile("src\\test\\resources\\xml\\customer.xml");
		assertNotNull(inputXml);
		custRequest = CustomerManagementRequestResponseDocument.Factory.parse(inputXml);
		assertNotNull(custRequest);
		custType = custRequest.getCustomerManagementRequestResponse().getRequest().getCustomerInfo();
		assertNotNull(custType);

	}

	@Test
	public void testNilElement() {

		Boolean isPrimLang = XmlUtil.isElementNull(custType.newCursor(), "primaryLanguage");
		//Boolean isWorkEmailEmpty = XmlUtil.checkEmptyOrNullElement(custType.newCursor(), "bestContactPhone");
		System.out.println(isPrimLang);
		//assertTrue("Expected work email to be empty!!!",isWorkEmailEmpty);

	}

}
