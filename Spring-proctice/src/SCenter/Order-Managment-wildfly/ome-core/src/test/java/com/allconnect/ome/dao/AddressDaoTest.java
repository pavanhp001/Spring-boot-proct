package com.AL.ome.dao;

 

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.AL.V.beans.entity.AddressBean;
import com.AL.V.beans.entity.CustomerAddressAssociation;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.AddressDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.xml.v4.RoleType;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
@TransactionConfiguration( defaultRollback = false)
public class AddressDaoTest {

	@Autowired
	private AddressDao addressDao;
	
	@Test
	public void testFindById(){
		assertNotNull(addressDao);
		List<AddressBean> addresses = addressDao.findAddressByCustomerId("157");
		 
		for(AddressBean a : addresses){
		 
			Set<CustomerAddressAssociation> consumers = a.getConsumers();
			assertNotNull(consumers);
			
			for (CustomerAddressAssociation consumer:consumers) {
				System.out.println(consumer.getAddressRole());
				String firstLetter = consumer.getAddressRole().substring(0, 1).toUpperCase(); 
				String rest = consumer.getAddressRole().substring(1);
				System.out.println(firstLetter+rest);
				RoleType.Enum rt = RoleType.Enum.forString(firstLetter+rest);
				
				assertNotNull(rt);
				
			}
			 
			
		}
	}
}

