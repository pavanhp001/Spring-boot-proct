package com.AL.ui.customer;

import static com.AL.ui.constants.Constants.BILLING_ADDR_ROLE_LIST;

import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

import com.AL.ui.service.V.AddressService;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.CustomerServiceUI;
import com.AL.ui.service.V.ProductServiceUI;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.BillingInfoList;
import com.AL.xml.cm.v4.CreditCardTypeType;
import com.AL.xml.cm.v4.CustBillingInfoType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.PaymentEventType;
import com.AL.xml.cm.v4.PaymentEventTypeType;
import com.AL.xml.cm.v4.PaymentsType;

public class CustomerTest {
	
	private static DatatypeFactory df = null;
	static {
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException dce) {
			throw new IllegalStateException(
					"Exception while obtaining DatatypeFactory instance", dce);
		}
	}
	
	@Test
	public void createCustomer() {
		CustomerType customer = createSampleCustomer();
		System.out.println(customer.getFirstName());
	}
	
	@Test
	public void getCustomer() {
		CustomerType customer = CustomerService.INSTANCE.getCustomerType("25362");
		System.out.println(customer.getFirstName()+ " "+customer.getLastName());
	}
	
	
	@Test
	public void addCustomerBillingInfo() {
		CustomerType newCustomer = new CustomerType();
		BillingInfoList billingInfoList = new BillingInfoList();
		CustBillingInfoType billingInfo = new CustBillingInfoType();
		billingInfo.setCardHolderName("Pankaj");
		billingInfo.setCreditCardNumber("123345456464");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(System.currentTimeMillis());
		billingInfo.setExpirationYearMonth(df.newXMLGregorianCalendar(gc));
		billingInfo.setVerificationCode("1234");
		billingInfo.setCreditCardType(CreditCardTypeType.AMERICAN_EXPRESS);
		billingInfo.setBillingUniqueId(String.valueOf(System.currentTimeMillis()));
		billingInfo.setAddressRef("SVC1349469541675");
		billingInfo.setStatus("active");
		billingInfoList.getBillingInfo().add(billingInfo);
		newCustomer.setBillingInfoList(billingInfoList);
		CustomerService.INSTANCE.submitCustomerType("default", "26171", 
				"updateCustomer", newCustomer);
	}
	
	@Test
	public void addPaymentEvents() {
		CustomerType newCustomer = new CustomerType();
		PaymentEventType paymentEvent = new PaymentEventType();
		paymentEvent.setEvent(PaymentEventTypeType.ADVANCED);
		paymentEvent.setAmount(BigDecimal.ONE);
		paymentEvent.setLineItemId("18171");
		paymentEvent.setBillingInfoId("BI1349711021712");
		paymentEvent.setConfirmNumber("pankaj");
		PaymentsType payments = new PaymentsType();
		payments.getPaymentEvent().add(paymentEvent);
		newCustomer.setPayments(payments);
		CustomerService.INSTANCE.submitCustomerType("default", "27513", 
				"updateCustomer", newCustomer);
	}
	
	
	@Test
	public void AddAddress() {
		AddressType address = new AddressType();
		address.setAddressBlock("2770 drew vally rd. , Atlanta GA 30319");
		AddressService.INSTANCE.saveNewAddress("default", "25482", 
				BILLING_ADDR_ROLE_LIST, null, "BLG001", address, "active");
	}
	
	public CustomerType createSampleCustomer() {
		String xmlInput =  ProductServiceUI.INSTANCE.readFileToString(new File(".\\src\\main\\resources\\xml\\customer.xml"));
		return toCustomerType(xmlInput);
	}
	
	public CustomerType toCustomerType(String xmlInput)  {
		CustomerType customerType = null;
		StringReader sr = null;
		try {
			JAXBContext customerJxbContext = JAXBContext.newInstance(CustomerType.class);
			sr = new StringReader( xmlInput );
			Unmarshaller unmarshaller = customerJxbContext.createUnmarshaller();
			JAXBElement<CustomerType> b = unmarshaller.unmarshal(new StreamSource(sr), 
					CustomerType.class);
			customerType = b.getValue();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sr.close();
		}
		return customerType;
	}

}
