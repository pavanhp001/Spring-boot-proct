package com.A.V.jms;


import static org.junit.Assert.*;

import java.util.Date;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.A.ui.service.V.AddressService;
import com.A.util.DateUtil;
import com.A.util.XMLGregorianCalendarConverter;
import com.A.V.factory.CustomerFactory;
import com.A.xml.cm.v4.AddressType;
import com.A.xml.cm.v4.CustomerContextType;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.cm.v4.ObjectFactory;

/**
 * @author maheshn
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/V-client-api-app-context.xml" })
public class JMSAddressTest {

	@Test
	public void testCustomerAddressUpdateJMS() throws Exception {

		//below details works with vprdev001
		String agentId = "ethomas1";
		String id = "24799";
		String status = "active";
		String addressUniqueId = "SA90";
		
		String[] roles = new String[1];
		roles[0] = "ServiceAddress";
		
		String address_id = "81719";
		
		AddressType address = new AddressType();
		address.setExternalId(81719);
		address.setStreetName("WHITEFISH");
		address.setStreetNumber("2001");
		address.setStreetType("CT");
		address.setCity("DENTON");
		address.setStateOrProvince("TX");
		address.setPostalCode("76210");
		address.setCountry("USA");

		Date gasStartAtDate = DateUtil.fromStringToDate("03/19/2013");
		
		QName NAME_GAS = new QName("http://xml.A.com/v4","gasStartAt");
		JAXBElement<XMLGregorianCalendar> jCalGas = new JAXBElement<XMLGregorianCalendar>(
				NAME_GAS, XMLGregorianCalendar.class,
				XMLGregorianCalendarConverter.asXMLGregorianCalendar(gasStartAtDate));
		address.setGasStartAt(jCalGas);
		
		Date electricityStartAtDate = DateUtil.fromStringToDate("03/19/2013");
		
		QName NAME_ELECTRICITY = new QName("http://xml.A.com/v4","electricityStartAt");
		JAXBElement<XMLGregorianCalendar> jCalElectricity = new JAXBElement<XMLGregorianCalendar>(
				NAME_ELECTRICITY, XMLGregorianCalendar.class,
				XMLGregorianCalendarConverter.asXMLGregorianCalendar(electricityStartAtDate));
		address.setElectricityStartAt(jCalElectricity);
		
		CustomerContextType custContext = CustomerFactory.INSTANCE.buildCustomerContext("source", "source", "Fulfillment");
			
		CustomerType customer = AddressService.INSTANCE.saveAddressUpdate(agentId, id,
				roles, address_id, addressUniqueId, address, status, custContext);
		
		assertNotNull(customer);

	}
}
