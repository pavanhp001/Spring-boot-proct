package com.A;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.job.Job;
import com.A.Vdao.dao.JobDao;

/**
 * @author ebthomas
 *
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "transactionalTransactionManager", defaultRollback = false)
public class ScheduleJobTest {

	@Autowired
	public JobDao jobDao;

	@Before
	public void setUp() {
		//System.out.println("Running ScheduleJob Test");
	}

//	@Test
//	public void testLock() throws Exception {
//
//		jobDao.deleteAll();
//
//		boolean isLocked = jobDao.isLocked("1", Boolean.TRUE, "1");
//
//		assertFalse(isLocked);
//
//		jobDao.lock("context", "1", Boolean.TRUE, "1");
//
//		isLocked = jobDao.isLocked("1", Boolean.TRUE, "1");
//		assertTrue(isLocked);
//
//		jobDao.unlock("1", Boolean.TRUE, "1");
//		isLocked = jobDao.isLocked("1", Boolean.TRUE, "1");
//		assertFalse(isLocked);
//
//
//		jobDao.deleteAll();
//
//	}
//
//
//	@Test
//	public void testUnLockButByLockedAnother() throws Exception {
//
//		jobDao.deleteAll();
//
//		boolean isLocked = jobDao.isLocked(1L, Boolean.TRUE, "1");
//
//		assertFalse(isLocked);
//
//		jobDao.lock("context", 1L, Boolean.TRUE, "1");
//
//		isLocked = jobDao.isLocked(2L, Boolean.TRUE, "1");
//		assertTrue(isLocked);
//
//		jobDao.unlock(2L, Boolean.FALSE, "1");
//		isLocked = jobDao.isLocked(1L, Boolean.TRUE, "1");
//		assertTrue(isLocked);
//
//
//		jobDao.deleteAll();
//
//	}
//
//
//	@Test
//	public void testUnLockByAdmin() throws Exception {
//
//		jobDao.deleteAll();
//
//		boolean isLocked = jobDao.isLocked(1L, Boolean.TRUE, "1");
//
//		assertFalse(isLocked);
//
//		jobDao.lock("context", 1L, Boolean.TRUE, "1");
//
//		isLocked = jobDao.isLocked(2L, Boolean.TRUE, "1");
//		assertTrue(isLocked);
//
//		jobDao.unlock(2L, Boolean.TRUE, "1");
//		isLocked = jobDao.isLocked(1L, Boolean.TRUE, "1");
//		assertFalse(isLocked);
//
//
//		jobDao.deleteAll();
//
//	}

	@Test
	public void testCreateJob(){
		Job isCreated = jobDao.create("abc", "User1", "1234", "parent");

		assertNotNull(isCreated);
	}

}
