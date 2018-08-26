package com.AL.ome.dao;



import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.Vdao.dao.CatalogProductDao;



/**
 * @author ebthomas
 *
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class ProductDaoTest {

	@Autowired
	public CatalogProductDao productDao;


	@Before
	public void setUp() {
		System.out.println("Running Product Dao Test");
	}

	@Test
	public void testOrderIdPresent() {
		try {
			assertNotNull(productDao);
			productDao.findCatalogProductById("123");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
