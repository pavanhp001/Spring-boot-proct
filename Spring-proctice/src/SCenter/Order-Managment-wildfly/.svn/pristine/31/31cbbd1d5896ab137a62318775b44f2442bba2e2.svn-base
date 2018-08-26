package com.AL.ome.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.AL.V.beans.job.Job;
import com.AL.Vdao.dao.JobDao;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
@TransactionConfiguration( defaultRollback = false)
public class ScheduleJobTest {

	@Autowired
	public JobDao jobDao;

	@Before
	public void setUp() {
		System.out.println("Running ScheduleJob Test");
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
	
//	@Test
//	public void testCreateJob(){
//		Job job = jobDao.create("abc", "User1", "1234","9999");
//		assertNotNull(job);
//		if(job != null){
//			job = jobDao.schedule(job.getContext(), job.getLoginId(), job.getResourceExternalId());
//		}
//		//assert(isCreated);
//	}
	
//	@Test
//	public void testFindByContext(){
//		List<Job> jobList = jobDao.findByContext("18486164",3);
//		assertNotNull(jobList);
//		assertTrue(jobList.size() == 3);
//	}
	
	@Test
	public void testBeginTransaction(){
		String transId =  jobDao.beginTransaction("18486164", "111");
		assertNotNull(transId);
		System.out.println(transId);
	}
	
//	@Test
//	public void testCommitTransaction(){
//		String stat =  jobDao.commitTransaction("7124627f-fa19-4a49-a3f0-86aeaf8ac43e");
//		assertNotNull(stat);
//		System.out.println(stat);
//	}
	
//	@Test
//	public void testRollbackTransaction(){
//		String stat =  jobDao.rollbackTransaction("7124627f-fa19-4a49-a3f0-86aeaf8ac43e");
//		assertNotNull(stat);
//		System.out.println(stat);
//	}

}
