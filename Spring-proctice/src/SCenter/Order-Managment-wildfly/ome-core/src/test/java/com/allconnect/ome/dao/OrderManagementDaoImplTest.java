package com.AL.ome.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.OrderManagementDao;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
@TransactionConfiguration( defaultRollback = false)
public class OrderManagementDaoImplTest {

	@Autowired
	private OrderManagementDao orderManagementDao;
	
	@Test
	public void testFindById(){
		assertNotNull(orderManagementDao);
		List<SalesOrder> soList = orderManagementDao.findByIds("7595,7598");
		assertTrue(soList.size() == 2);
		for(SalesOrder so : soList){
			assertNotNull(so.getLineItems());
			List<LineItem> liList = so.getLineItems();
			assertNotNull(liList);
			for(LineItem li : liList){
				assertNotNull(li.getHistoricStatus());
			}
		}
		assertNotNull(soList);
	}
}
