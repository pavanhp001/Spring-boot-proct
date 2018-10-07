package abc.xyz.pts.bcs.common.dao.utils;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.QueryBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.SimpleQueryArg;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.dto.TargetItem;


public class JdbcUtilTest extends TestCase {
	 private final Mockery mockery = new JUnit4Mockery() {
	        {
	            setImposteriser(ClassImposteriser.INSTANCE);
	        }
	    };
	private JdbcTemplate jdbcTemplate;
	private JdbcDaoSupport daoSupport;
	
	@Test
	public void testExecuteQuery() {
		TableActionCommand tableCommand = new TableActionCommand();
		CustomRowMapper<TargetItem> rowMapper = new CustomRowMapper<TargetItem>(TargetItem.class);
		QueryBuilder qb = new QueryBuilder(tableCommand, Field.ID, Table.TARGET_WATCH_LISTS);     	
		// * SELECT
        qb.addSelect(Field.ID, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.FORENAME, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.LAST_NAME, Table.TARGET_WATCH_LISTS);
        // * FROM
        qb.addTable(Table.TARGET_WATCH_LISTS);
        qb.addWhereClause(new SimpleQueryArg(Field.GENDER, Table.TARGET_WATCH_LISTS, "M"));
        
        this.jdbcTemplate = mockery.mock(JdbcTemplate.class);
    	this.daoSupport = new JdbcDaoSupport() {
		};
        daoSupport.setJdbcTemplate(jdbcTemplate);
        final List<TargetItem> results = new ArrayList<TargetItem>();
        mockery.checking(new Expectations() {{
        	one(jdbcTemplate).setFetchSize(with(any(Integer.class)));
            one(jdbcTemplate).query(with(any(String.class)),with(any(Object[].class)),with(any(RowMapper.class)));
            will(returnValue(results));
        }});

        List<TargetItem> targetItems = JdbcUtil.executeQuery(daoSupport.getJdbcTemplate(), rowMapper, qb);
        Assert.assertNotNull(targetItems);
        System.out.println("targetItems ==="+targetItems);
	}
	@Test
	public void testExecuteQueryAll() {
		TableActionCommand tableCommand = new TableActionCommand();
		CustomRowMapper<TargetItem> rowMapper = new CustomRowMapper<TargetItem>(TargetItem.class);
		QueryBuilder qb = new QueryBuilder(tableCommand, Field.ID, Table.TARGET_WATCH_LISTS);     	
		// * SELECT
        qb.addSelect(Field.ID, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.FORENAME, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.LAST_NAME, Table.TARGET_WATCH_LISTS);
        // * FROM
        qb.addTable(Table.TARGET_WATCH_LISTS);
        qb.addWhereClause(new SimpleQueryArg(Field.GENDER, Table.TARGET_WATCH_LISTS, "M"));
        
        this.jdbcTemplate = mockery.mock(JdbcTemplate.class);
    	this.daoSupport = new JdbcDaoSupport() {
		};
        daoSupport.setJdbcTemplate(jdbcTemplate);
        final List<TargetItem> results = new ArrayList<TargetItem>();
        mockery.checking(new Expectations() {{
            one(jdbcTemplate).query(with(any(String.class)),with(any(Object[].class)),with(any(RowMapper.class)));
            will(returnValue(results));
        }});

        List<TargetItem> targetItems = JdbcUtil.executeAllQuery(daoSupport.getJdbcTemplate(), rowMapper, qb);
        Assert.assertNotNull(targetItems);
        System.out.println("targetItems ==="+targetItems);
	}
	
	@Test
	public void testExecuteQueryById() {
		TableActionCommand tableCommand = new TableActionCommand();
		CustomRowMapper<TargetItem> rowMapper = new CustomRowMapper<TargetItem>(TargetItem.class);
		QueryBuilder qb = new QueryBuilder(tableCommand, Field.ID, Table.TARGET_WATCH_LISTS);     	
		// * SELECT
        qb.addSelect(Field.ID, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.FORENAME, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.LAST_NAME, Table.TARGET_WATCH_LISTS);
        // * FROM
        qb.addTable(Table.TARGET_WATCH_LISTS);
        qb.addWhereClause(new SimpleQueryArg(Field.GENDER, Table.TARGET_WATCH_LISTS, "M"));
        
        this.jdbcTemplate = mockery.mock(JdbcTemplate.class);
    	this.daoSupport = new JdbcDaoSupport() {
		};
        daoSupport.setJdbcTemplate(jdbcTemplate);
        final List<TargetItem> results = new ArrayList<TargetItem>();
        final TargetItem result = new TargetItem();
        results.add(result);
        mockery.checking(new Expectations() {{
            one(jdbcTemplate).query(with(any(String.class)),with(any(Object[].class)),with(any(RowMapper.class)));
            will(returnValue(results));
        }});

        TargetItem targetItem = JdbcUtil.executeQueryById(daoSupport.getJdbcTemplate(), rowMapper, qb);
        Assert.assertNotNull(targetItem);
        System.out.println("targetItem ==="+targetItem);
	}
	
	@Test
	public void testNoReCount() {
		
		TableActionCommand command = new TableActionCommand();
		command.setPageSize(2);
		command.setTotalResults(5);
		command.setRowCountEstimated(true);
		
		List results = new ArrayList();
		results.add("1");
		results.add("2");
		results.add("3");
		
		JdbcUtil.getRowEstimatedRowCount(null, null, null, command);
		
		assertEquals(5, command.getTotalResults()); // result stays the same as previously set
		assertTrue(command.isRowCountEstimated());
	}
}
