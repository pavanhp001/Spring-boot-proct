package com.A.V.jms;


import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.A.ui.constants.Constants;
import com.A.ui.service.V.CustomerService;
import com.A.util.DateUtil;
import com.A.V.factory.PaymentFactory;
import com.A.V.gateway.CustomerClient;
import com.A.V.gateway.util.JaxbUtil;
import com.A.V.gateway.jms.CustomerClientJMS;
import com.A.xml.cm.v4.CustomerManagementRequestResponse;
import com.A.xml.cm.v4.CustomerSearch;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.cm.v4.ObjectFactory;
import com.A.xml.cm.v4.PaymentEventType;
import com.A.xml.cm.v4.PaymentEventTypeType;
import com.A.xml.cm.v4.PaymentStatusWithTypeType;
import com.A.xml.cm.v4.PaymentsType;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/V-client-api-app-context.xml" })
public class JMSCustomerTest {

/*	@Test
	public void testCustomerSendViaJMS() throws Exception {

		CustomerManagementRequestResponse cmrr = JMSTestHelper.INSTANCE
				.getCustomerType("7033");

		CustomerClient jmsClient = new CustomerClientJMS();
		CustomerManagementRequestResponse omrrFromQueue = jmsClient.send(cmrr);

		JaxbUtil<CustomerManagementRequestResponse> utilCust = new JaxbUtil<CustomerManagementRequestResponse>();

		assertNotNull("RESPONSE---->>>>>>"
				+ utilCust.toString(omrrFromQueue,
						CustomerManagementRequestResponse.class));

	}*/
	
	@Test
	public void testSearchCustomer() {
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("firstname", "Pritesh");
		CustomerSearch custSearchResults = CustomerService.INSTANCE.searchCustomer(searchMap, 0);
		int totalRows = custSearchResults.getTotalRows();
		assertTrue(totalRows >= 0);
		assertNotNull(custSearchResults);
		if(totalRows > 0){
			assertTrue(custSearchResults.getSearchResult().size() > 0);
		}
	}

	@Test
	public void testCustomerSSNUpdateJMS() throws Exception {
		ObjectFactory oFactory = new ObjectFactory();

		CustomerType customer = oFactory.createCustomerType();

		customer.setSsn("111-22-3333");

		customer = CustomerService.INSTANCE.submitCustomerType("default",
				"12317", "updateCustomer", customer);

		assertEquals("111-22-3333", customer.getSsn());

		customer.setSsn("111-22-4444");

		customer = CustomerService.INSTANCE.submitCustomerType("default",
				"12317", "updateCustomer", customer);

		assertEquals("111-22-4444", customer.getSsn());

	}

	public static QName qname = new QName("dob");
	@Test
	public void testCustomerDobUpdateJMS() throws Exception {
		ObjectFactory oFactory = new ObjectFactory();

		CustomerType customer = oFactory.createCustomerType();

		String updated_dob="04/12/1924";
		
		Date dob = new Date();
		dob.setYear(1924);
		dob.setMonth(04);
		dob.setDate(12);
		
		//orderType.setMoveDate(DateUtil.asXMLGregorianCalendar(dt));

		
		/*JAXBElement<XMLGregorianCalendar> dob = new JAXBElement<XMLGregorianCalendar>(qname, 
				XMLGregorianCalendar.class, DateUtil.asXMLGregorianCalendar(updated_dob, "MM/dd/yyyy"));*/

		customer.setFirstName("Tom");
		//customer.setDob(DateUtil.asXMLGregorianCalendar(dob));
		customer.setDob(DateUtil.asXMLGregorianCalendar(updated_dob, "MM/dd/yyyy"));
		


		customer = CustomerService.INSTANCE.submitCustomerType("default",
				"35308", "updateCustomer", customer);

		//assertEquals("111-22-3333", customer.getSsn());

		//customer.setSsn("111-22-4444");


	}

	
	@Test
	public void testAddPaymentEvents() {

//		CustomerType newCustomer = new CustomerType();
//		PaymentEventType paymentEvent = PaymentFactory.INSTANCE
//				.createPaymentEvent(PaymentEventTypeType.EQUIPMENT,
//						BigDecimal.valueOf(13L), "17762", "18471", "BI1349807785738",
//						String.valueOf(Integer.MAX_VALUE % System.currentTimeMillis()), "567");
//		paymentEvent.setTransactionDate(DateUtil.getCurrentXMLDate());
//		paymentEvent.setCustAgreedCCDisclosure("true");
//		//PaymentStatusWithTypeType statusType = PaymentFactory.INSTANCE.createPaymentStatus(1111, 1);
//		//paymentEvent.getPaymentStatus().add(statusType);
//		newCustomer.setPayments(new PaymentsType());
//		newCustomer.getPayments().getPaymentEvent().add(paymentEvent);
//		 
//		CustomerType ct = CustomerService.INSTANCE.submitCustomerType("default", "27856",
//				"updateCustomer", newCustomer);
//		
//		assertNotNull(ct);
	}

}
